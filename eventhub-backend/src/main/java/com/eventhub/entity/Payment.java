package com.eventhub.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.eventhub.enums.PaymentMethod;
import com.eventhub.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "transaction_id",
        nullable = false,
        unique = true,
        length = 100
    )
    private String transactionId;

    @NotNull(message = "Payment amount is required")
    @Column(
        nullable = false,
        precision = 12,
        scale = 2
    )
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(
        name = "payment_method",
        nullable = false,
        length = 30
    )
    private PaymentMethod paymentMethod;

    @Column(
        name = "payment_date",
        nullable = false
    )
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(
        nullable = false,
        length = 20
    )
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(
        name = "razorpay_order_id",
        length = 100
    )
    private String razorpayOrderId;

    @Column(
        name = "razorpay_payment_id",
        length = 100
    )
    private String razorpayPaymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "booking_id",
        nullable = false,
        unique = true
    )
    private Booking booking;

    public Payment() {
    }

    public Payment(
            String transactionId,
            BigDecimal amount,
            PaymentMethod paymentMethod,
            Booking booking) {

        this.transactionId = transactionId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.booking = booking;
        this.status = PaymentStatus.PENDING;
    }

    @PrePersist
    protected void onCreate() {

        if (this.paymentDate == null) {
            this.paymentDate = LocalDateTime.now();
        }

        if (this.status == null) {
            this.status = PaymentStatus.PENDING;
        }
    }

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

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}

