package com.eventhub.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BookingRequest {

    @NotNull(message = "Event ID is required")
    private Long eventId;

    @NotNull(message = "Ticket quantity is required")
    @Min(value = 1, message = "At least 1 ticket is required")
    private Integer ticketQuantity;

    public BookingRequest() {
    }

    public BookingRequest(
            Long eventId,
            Integer ticketQuantity) {

        this.eventId = eventId;
        this.ticketQuantity = ticketQuantity;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(Integer ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }
}