package com.eventhub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eventhub.dto.PaymentRequest;
import com.eventhub.dto.PaymentResponse;
import com.eventhub.dto.RazorpayOrderResponse;
import com.eventhub.dto.RazorpayVerifyRequest;
import com.eventhub.service.PaymentService;

import jakarta.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(
            PaymentService paymentService) {

        this.paymentService = paymentService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PaymentResponse> processPayment(
            @Valid @RequestBody PaymentRequest request) {

        PaymentResponse response =
                paymentService.processPayment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PaymentResponse> getPaymentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(id));
    }

    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PaymentResponse>
    getPaymentByBookingId(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                paymentService.getPaymentByBookingId(
                        bookingId));
    }
    
    @PostMapping("/create-order/{bookingId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<RazorpayOrderResponse> createRazorpayOrder(
            @PathVariable Long bookingId) {

        return ResponseEntity.ok(
                paymentService.createRazorpayOrder(bookingId));
    }

    @PostMapping("/verify")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PaymentResponse> verifyRazorpayPayment(
            @RequestBody RazorpayVerifyRequest request) {

        PaymentResponse response =
                paymentService.verifyRazorpayPayment(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}