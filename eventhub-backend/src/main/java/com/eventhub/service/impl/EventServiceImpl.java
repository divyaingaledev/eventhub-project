package com.eventhub.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eventhub.dto.EventRequest;
import com.eventhub.dto.EventResponse;
import com.eventhub.entity.Category;
import com.eventhub.entity.Event;
import com.eventhub.entity.Venue;
import com.eventhub.enums.EventStatus;
import com.eventhub.exception.ResourceNotFoundException;
import com.eventhub.mapper.EventMapper;
import com.eventhub.repository.CategoryRepository;
import com.eventhub.repository.EventRepository;
import com.eventhub.repository.VenueRepository;
import com.eventhub.service.EventService;

@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final VenueRepository venueRepository;
    private final EventMapper eventMapper;

    // Constructor injection
    public EventServiceImpl(
            EventRepository eventRepository,
            CategoryRepository categoryRepository,
            VenueRepository venueRepository,
            EventMapper eventMapper) {

        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.venueRepository = venueRepository;
        this.eventMapper = eventMapper;
    }

    // Create event
    @Override
    public EventResponse createEvent(EventRequest request) {

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: "
                                        + request.getCategoryId()
                        )
                );

        Venue venue = venueRepository
                .findById(request.getVenueId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Venue not found with id: "
                                        + request.getVenueId()
                        )
                );

        Event event = eventMapper.toEntity(
                request,
                category,
                venue
        );

        if (event.getStatus() == null) {
            event.setStatus(EventStatus.UPCOMING);
        }

        Event savedEvent = eventRepository.save(event);

        return eventMapper.toResponse(savedEvent);
    }

    // Get event by ID
    @Override
    @Transactional(readOnly = true)
    public EventResponse getEventById(Long id) {

        Event event = eventRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found with id: " + id
                        )
                );

        return eventMapper.toResponse(event);
    }

    // Get all events
    @Override
    @Transactional(readOnly = true)
    public List<EventResponse> getAllEvents() {

        return eventRepository
                .findAll()
                .stream()
                .map(eventMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Update event
    @Override
    public EventResponse updateEvent(
            Long id,
            EventRequest request) {

        Event event = eventRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found with id: " + id
                        )
                );

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: "
                                        + request.getCategoryId()
                        )
                );

        Venue venue = venueRepository
                .findById(request.getVenueId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Venue not found with id: "
                                        + request.getVenueId()
                        )
                );

        eventMapper.updateEntity(
                event,
                request,
                category,
                venue
        );

        Event updatedEvent = eventRepository.save(event);

        return eventMapper.toResponse(updatedEvent);
    }

    // Delete event
    @Override
    public void deleteEvent(Long id) {

        Event event = eventRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Event not found with id: " + id
                        )
                );

        eventRepository.delete(event);
    }
}