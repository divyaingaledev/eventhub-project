
package com.eventhub.service.impl;

import java.math.BigDecimal;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

import com.eventhub.dto.PaymentRequest;
import com.eventhub.dto.PaymentResponse;
import com.eventhub.dto.RazorpayOrderResponse;
import com.eventhub.dto.RazorpayVerifyRequest;
import com.eventhub.entity.Booking;
import com.eventhub.entity.Payment;
import com.eventhub.entity.User;
import com.eventhub.enums.BookingStatus;
import com.eventhub.enums.PaymentMethod;
import com.eventhub.enums.PaymentStatus;
import com.eventhub.mapper.PaymentMapper;
import com.eventhub.repository.BookingRepository;
import com.eventhub.repository.PaymentRepository;
import com.eventhub.repository.UserRepository;
import com.eventhub.service.PaymentService;

import jakarta.transaction.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final PaymentMapper paymentMapper;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    public PaymentServiceImpl(
            PaymentRepository paymentRepository,
            BookingRepository bookingRepository,
            UserRepository userRepository,
            PaymentMapper paymentMapper) {

        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {

        Booking booking =
                bookingRepository.findById(request.getBookingId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Booking not found"));

        checkBookingAccess(booking);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException(
                    "Cancelled booking cannot be paid");
        }

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            throw new IllegalArgumentException(
                    "Booking is already confirmed");
        }

        if (paymentRepository.findByBookingId(booking.getId()).isPresent()) {
            throw new IllegalArgumentException(
                    "Payment already exists for this booking");
        }

        PaymentMethod paymentMethod;

        try {
            paymentMethod = PaymentMethod.valueOf(
                    request.getPaymentMethod()
                            .trim()
                            .toUpperCase());

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Invalid payment method");
        }

        Payment payment =
                paymentMapper.toEntity(request, booking);

        payment.setAmount(booking.getTotalAmount());
        payment.setPaymentMethod(paymentMethod);

        payment.setTransactionId(
                "TXN-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 16)
                        .toUpperCase());

        payment.setStatus(PaymentStatus.SUCCESS);

        Payment savedPayment =
                paymentRepository.save(payment);

        booking.setStatus(BookingStatus.CONFIRMED);

        bookingRepository.save(booking);

        return paymentMapper.toResponse(savedPayment);
    }

    @Override
    @Transactional
    public RazorpayOrderResponse createRazorpayOrder(Long bookingId) {

        Booking booking =
                bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Booking not found"));

        checkBookingAccess(booking);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException(
                    "Cancelled booking cannot be paid");
        }

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            throw new IllegalArgumentException(
                    "Booking is already confirmed");
        }

        if (paymentRepository.findByBookingId(bookingId).isPresent()) {
            throw new IllegalArgumentException(
                    "Payment already exists for this booking");
        }

        BigDecimal totalAmount = booking.getTotalAmount();

        if (totalAmount == null ||
                totalAmount.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Invalid booking amount");
        }

        try {

            RazorpayClient razorpayClient =
                    new RazorpayClient(
                            razorpayKeyId,
                            razorpayKeySecret);

            long amountInPaise =
                    totalAmount
                            .multiply(BigDecimal.valueOf(100))
                            .longValueExact();

            JSONObject orderRequest =
                    new JSONObject();

            orderRequest.put(
                    "amount",
                    amountInPaise);

            orderRequest.put(
                    "currency",
                    "INR");

            orderRequest.put(
                    "receipt",
                    booking.getBookingReference());

            Order razorpayOrder =
                    razorpayClient.orders.create(
                            orderRequest);

            String orderId =
                    razorpayOrder.get("id");

            return new RazorpayOrderResponse(
                    orderId,
                    booking.getId(),
                    booking.getBookingReference(),
                    amountInPaise,
                    "INR",
                    razorpayKeyId);

        } catch (Exception e) {

            throw new IllegalArgumentException(
                    "Unable to create Razorpay order: "
                    + e.getMessage());
        }
    }

    @Override
    @Transactional
    public PaymentResponse verifyRazorpayPayment(
            RazorpayVerifyRequest request) {

        if (request == null ||
                request.getBookingId() == null ||
                request.getRazorpayOrderId() == null ||
                request.getRazorpayPaymentId() == null ||
                request.getRazorpaySignature() == null) {

            throw new IllegalArgumentException(
                    "Razorpay payment details are required");
        }

        Booking booking =
                bookingRepository.findById(
                        request.getBookingId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Booking not found"));

        checkBookingAccess(booking);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException(
                    "Cancelled booking cannot be paid");
        }

        if (booking.getStatus() == BookingStatus.CONFIRMED) {
            throw new IllegalArgumentException(
                    "Booking is already confirmed");
        }

        if (paymentRepository
                .findByBookingId(booking.getId())
                .isPresent()) {

            throw new IllegalArgumentException(
                    "Payment already exists for this booking");
        }

        try {

            JSONObject attributes =
                    new JSONObject();

            attributes.put(
                    "razorpay_order_id",
                    request.getRazorpayOrderId());

            attributes.put(
                    "razorpay_payment_id",
                    request.getRazorpayPaymentId());

            attributes.put(
                    "razorpay_signature",
                    request.getRazorpaySignature());

            boolean signatureValid =
                    Utils.verifyPaymentSignature(
                            attributes,
                            razorpayKeySecret);

            if (!signatureValid) {
                throw new IllegalArgumentException(
                        "Invalid Razorpay payment signature");
            }

            Payment payment =
                    new Payment();

            payment.setBooking(booking);

            payment.setAmount(
                    booking.getTotalAmount());

            payment.setPaymentMethod(
                    PaymentMethod.UPI);

            payment.setTransactionId(
                    "RZP-" +
                    UUID.randomUUID()
                            .toString()
                            .replace("-", "")
                            .substring(0, 16)
                            .toUpperCase());

            payment.setStatus(
                    PaymentStatus.SUCCESS);

            payment.setRazorpayOrderId(
                    request.getRazorpayOrderId());

            payment.setRazorpayPaymentId(
                    request.getRazorpayPaymentId());

            Payment savedPayment =
                    paymentRepository.save(payment);

            booking.setStatus(
                    BookingStatus.CONFIRMED);

            bookingRepository.save(booking);

            return paymentMapper.toResponse(
                    savedPayment);

        } catch (IllegalArgumentException e) {

            throw e;

        } catch (Exception e) {

            throw new IllegalArgumentException(
                    "Unable to verify Razorpay payment: "
                    + e.getMessage());
        }
    }

    @Override
    public PaymentResponse getPaymentById(Long id) {

        Payment payment =
                paymentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Payment not found"));

        checkBookingAccess(payment.getBooking());

        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentByBookingId(
            Long bookingId) {

        Payment payment =
                paymentRepository
                        .findByBookingId(bookingId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Payment not found"));

        checkBookingAccess(payment.getBooking());

        return paymentMapper.toResponse(payment);
    }

    private void checkBookingAccess(
            Booking booking) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication.getAuthorities()
                .stream()
                .anyMatch(a ->
                        a.getAuthority()
                                .equals("ROLE_ADMIN"))) {

            return;
        }

        User user = getLoggedInUser();

        if (!booking.getUser()
                .getId()
                .equals(user.getId())) {

            throw new IllegalArgumentException(
                    "You are not allowed to access this payment");
        }
    }

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Logged in user not found"));
    }
}
