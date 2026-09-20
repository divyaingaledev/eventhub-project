package com.eventhub.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.entity.Event;
import com.eventhub.enums.EventStatus;

public interface EventRepository
        extends JpaRepository<Event, Long> {

    List<Event> findByStatus(
            EventStatus status
    );

    List<Event> findByCategoryId(
            Long categoryId
    );

    List<Event> findByVenueId(
            Long venueId
    );

    List<Event> findByEventDateGreaterThanEqual(
            LocalDate date
    );

    List<Event> findByTitleContainingIgnoreCase(
            String title
    );

    List<Event> findByVenueCityIgnoreCase(
            String city
    );
}