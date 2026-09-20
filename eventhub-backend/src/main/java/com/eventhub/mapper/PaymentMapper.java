package com.eventhub.mapper;

import org.springframework.stereotype.Component;

import com.eventhub.dto.PaymentRequest;
import com.eventhub.dto.PaymentResponse;
import com.eventhub.entity.Booking;
import com.eventhub.entity.Payment;

@Component
public class PaymentMapper {

    public Payment toEntity(
            PaymentRequest request,
            Booking booking) {

        Payment payment = new Payment();

        payment.setBooking(booking);

        return payment;
    }

    public PaymentResponse toResponse(
            Payment payment) {

        Long bookingId = null;
        String bookingReference = null;

        if (payment.getBooking() != null) {

            Booking booking =
                    payment.getBooking();

            bookingId = booking.getId();
            bookingReference =
                    booking.getBookingReference();
        }

        return new PaymentResponse(
                payment.getId(),
                payment.getTransactionId(),
                bookingId,
                bookingReference,
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentDate(),
                payment.getStatus()
        );
    }
}