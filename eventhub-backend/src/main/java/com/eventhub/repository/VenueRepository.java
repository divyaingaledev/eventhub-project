package com.eventhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventhub.entity.Venue;

public interface VenueRepository
        extends JpaRepository<Venue, Long> {

    List<Venue> findByCityIgnoreCase(
            String city
    );

    List<Venue> findByStateIgnoreCase(
            String state
    );
}