package com.eventhub.service;

import java.util.List;

import com.eventhub.dto.EventRequest;
import com.eventhub.dto.EventResponse;

public interface EventService {

    EventResponse createEvent(EventRequest request);

    EventResponse getEventById(Long id);

    List<EventResponse> getAllEvents();

    EventResponse updateEvent(
            Long id,
            EventRequest request
    );

    void deleteEvent(Long id);
}