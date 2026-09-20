package com.eventhub.service;

import java.util.List;

import com.eventhub.dto.VenueRequest;
import com.eventhub.dto.VenueResponse;

public interface VenueService {

    // Create
    VenueResponse createVenue(VenueRequest request);

    // Read one
    VenueResponse getVenueById(Long id);

    // Read all
    List<VenueResponse> getAllVenues();

    // Update
    VenueResponse updateVenue(
            Long id,
            VenueRequest request
    );

    // Delete
    void deleteVenue(Long id);
}