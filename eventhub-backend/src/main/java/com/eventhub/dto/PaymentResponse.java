package com.eventhub.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.eventhub.enums.PaymentMethod;
import com.eventhub.enums.PaymentStatus;

public class PaymentResponse {

    private Long id;
    private String transactionId;
    private Long bookingId;
    private String bookingReference;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private LocalDateTime paymentDate;
    private PaymentStatus status;

    // Default Constructor
    public PaymentResponse() {
    }

    // Parameterized Constructor
    public PaymentResponse(
            Long id,
            String transactionId,
            Long bookingId,
            String bookingReference,
            BigDecimal amount,
            PaymentMethod paymentMethod,
            LocalDateTime paymentDate,
            PaymentStatus status) {

        this.id = id;
        this.transactionId = transactionId;
        this.bookingId = bookingId;
        this.bookingReference = bookingReference;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.status = status;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}