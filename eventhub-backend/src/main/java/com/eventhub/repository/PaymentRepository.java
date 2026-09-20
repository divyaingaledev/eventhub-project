package com.eventhub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.entity.Payment;
import com.eventhub.enums.PaymentStatus;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionId(
            String transactionId);

    Optional<Payment> findByBookingId(
            Long bookingId);

    boolean existsByTransactionId(
            String transactionId);

    long countByStatus(PaymentStatus status);
}