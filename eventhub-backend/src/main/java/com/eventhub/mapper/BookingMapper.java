package com.eventhub.mapper;

import org.springframework.stereotype.Component;

import com.eventhub.dto.BookingRequest;
import com.eventhub.dto.BookingResponse;
import com.eventhub.entity.Booking;
import com.eventhub.entity.Event;
import com.eventhub.entity.User;

@Component
public class BookingMapper {

    public Booking toEntity(
            BookingRequest request,
            User user,
            Event event) {

        Booking booking = new Booking();

        booking.setUser(user);
        booking.setEvent(event);
        booking.setTicketQuantity(request.getTicketQuantity());

        return booking;
    }

    public BookingResponse toResponse(Booking booking) {

        if (booking == null) {
            return null;
        }

        BookingResponse response = new BookingResponse();

        response.setId(booking.getId());
        response.setBookingReference(booking.getBookingReference());
        response.setTicketQuantity(booking.getTicketQuantity());
        response.setTotalAmount(booking.getTotalAmount());
        response.setBookingDate(booking.getBookingDate());
        response.setStatus(booking.getStatus());

        Event event = booking.getEvent();

        if (event != null) {

            response.setEventId(event.getId());
            response.setEventName(event.getTitle());
            response.setEventDate(event.getEventDate());
            response.setEventTime(event.getEventTime());

            if (event.getVenue() != null) {
                response.setVenueName(event.getVenue().getName());
                response.setCity(event.getVenue().getCity());
            }
        }

        return response;
    }
}