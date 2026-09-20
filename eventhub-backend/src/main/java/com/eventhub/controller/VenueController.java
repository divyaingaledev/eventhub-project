package com.eventhub.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dto.VenueRequest;
import com.eventhub.dto.VenueResponse;
import com.eventhub.service.VenueService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(
            VenueService venueService) {

        this.venueService = venueService;
    }

    @PostMapping
    public ResponseEntity<VenueResponse> createVenue(
            @Valid @RequestBody VenueRequest request) {

        VenueResponse response =
                venueService.createVenue(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueResponse> getVenueById(
            @PathVariable Long id) {

        VenueResponse response =
                venueService.getVenueById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<VenueResponse>>
            getAllVenues() {

        List<VenueResponse> venues =
                venueService.getAllVenues();

        return ResponseEntity.ok(venues);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueResponse> updateVenue(
            @PathVariable Long id,
            @Valid @RequestBody VenueRequest request) {

        VenueResponse response =
                venueService.updateVenue(
                        id,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(
            @PathVariable Long id) {

        venueService.deleteVenue(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}