package com.eventhub.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.eventhub.enums.BookingStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "booking_reference", nullable = false, unique = true, length = 50)
    private String bookingReference;

    @NotNull
    @Min(1)
    @Column(name = "ticket_quantity", nullable = false)
    private Integer ticketQuantity;

    @NotNull
    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BookingStatus status = BookingStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @OneToOne(mappedBy = "booking")
    private Payment payment;

    public Booking() {
    }

    public Booking(
            String bookingReference,
            Integer ticketQuantity,
            BigDecimal totalAmount,
            User user,
            Event event) {

        this.bookingReference = bookingReference;
        this.ticketQuantity = ticketQuantity;
        this.totalAmount = totalAmount;
        this.user = user;
        this.event = event;
        this.status = BookingStatus.PENDING;
    }

    @PrePersist
    protected void onCreate() {

        if (bookingReference == null || bookingReference.isBlank()) {
            bookingReference = "BK-" + UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 12)
                    .toUpperCase();
        }

        if (bookingDate == null) {
            bookingDate = LocalDateTime.now();
        }

        if (status == null) {
            status = BookingStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
    }

    public Long getId() {
        return id;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public Integer getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(Integer ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}