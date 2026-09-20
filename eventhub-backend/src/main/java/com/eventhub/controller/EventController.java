package com.eventhub.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dto.EventRequest;
import com.eventhub.dto.EventResponse;
import com.eventhub.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> createEvent(
            @Valid @RequestBody EventRequest request) {

        EventResponse response =
                eventService.createEvent(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                eventService.getEventById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {

        return ResponseEntity.ok(
                eventService.getAllEvents()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventRequest request) {

        return ResponseEntity.ok(
                eventService.updateEvent(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id) {

        eventService.deleteEvent(id);

        return ResponseEntity.noContent().build();
    }
}