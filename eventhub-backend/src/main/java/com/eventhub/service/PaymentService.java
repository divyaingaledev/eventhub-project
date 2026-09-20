package com.eventhub.service;

import com.eventhub.dto.PaymentRequest;
import com.eventhub.dto.PaymentResponse;
import com.eventhub.dto.RazorpayOrderResponse;
import com.eventhub.dto.RazorpayVerifyRequest;

public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest request);

    PaymentResponse getPaymentById(Long id);

    PaymentResponse getPaymentByBookingId(Long bookingId);

    RazorpayOrderResponse createRazorpayOrder(Long bookingId);

    PaymentResponse verifyRazorpayPayment(RazorpayVerifyRequest request);
}