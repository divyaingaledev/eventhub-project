package com.eventhub.mapper;

import org.springframework.stereotype.Component;

import com.eventhub.dto.EventRequest;
import com.eventhub.dto.EventResponse;
import com.eventhub.entity.Category;
import com.eventhub.entity.Event;
import com.eventhub.entity.Venue;
import com.eventhub.enums.EventStatus;

@Component
public class EventMapper {

    // Convert request DTO to Event entity
    public Event toEntity(
            EventRequest request,
            Category category,
            Venue venue) {

        Event event = new Event();

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setEventTime(request.getEventTime());

        // Event entity uses price while DTO uses ticketPrice
        event.setPrice(request.getTicketPrice());

        event.setAvailableSeats(request.getAvailableSeats());
        event.setImageUrl(request.getImageUrl());

        if (request.getStatus() != null) {
            event.setStatus(request.getStatus());
        } else {
            event.setStatus(EventStatus.UPCOMING);
        }

        event.setCategory(category);
        event.setVenue(venue);

        return event;
    }

    // Convert Event entity to response DTO
    public EventResponse toResponse(Event event) {

        if (event == null) {
            return null;
        }

        EventResponse response = new EventResponse();

        response.setId(event.getId());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setEventDate(event.getEventDate());
        response.setEventTime(event.getEventTime());

        // Convert price back to ticketPrice
        response.setTicketPrice(event.getPrice());

        response.setAvailableSeats(event.getAvailableSeats());
        response.setImageUrl(event.getImageUrl());
        response.setStatus(event.getStatus());

        // Category information
        if (event.getCategory() != null) {

            Category category = event.getCategory();

            response.setCategoryId(category.getId());
            response.setCategoryName(category.getName());
        }

        // Venue information
        if (event.getVenue() != null) {

            Venue venue = event.getVenue();

            response.setVenueId(venue.getId());
            response.setVenueName(venue.getName());
            response.setVenueAddress(venue.getAddress());
            response.setCity(venue.getCity());
            response.setState(venue.getState());
            response.setPincode(venue.getPincode());
        }

        return response;
    }

    // Update existing Event entity
    public void updateEntity(
            Event event,
            EventRequest request,
            Category category,
            Venue venue) {

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setEventTime(request.getEventTime());

        // Convert ticketPrice to price
        event.setPrice(request.getTicketPrice());

        event.setAvailableSeats(request.getAvailableSeats());
        event.setImageUrl(request.getImageUrl());

        if (request.getStatus() != null) {
            event.setStatus(request.getStatus());
        } else if (event.getStatus() == null) {
            event.setStatus(EventStatus.UPCOMING);
        }

        event.setCategory(category);
        event.setVenue(venue);
    }
}