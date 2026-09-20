package com.eventhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.entity.Booking;
import com.eventhub.entity.User;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {

    List<Booking> findByUser(User user);

    List<Booking> findByUserId(Long userId);
}