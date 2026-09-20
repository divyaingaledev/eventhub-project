package com.eventhub.service;

import java.util.List;

import com.eventhub.dto.BookingRequest;
import com.eventhub.dto.BookingResponse;

public interface BookingService {

    BookingResponse createBooking(
            BookingRequest request);

    BookingResponse getBookingById(
            Long id);

    List<BookingResponse> getMyBookings();

    List<BookingResponse> getAllBookings();

    BookingResponse cancelBooking(
            Long id);
}