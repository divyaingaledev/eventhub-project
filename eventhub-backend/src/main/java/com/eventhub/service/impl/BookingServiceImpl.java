package com.eventhub.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.eventhub.dto.BookingRequest;
import com.eventhub.dto.BookingResponse;
import com.eventhub.entity.Booking;
import com.eventhub.entity.Event;
import com.eventhub.entity.User;
import com.eventhub.enums.BookingStatus;
import com.eventhub.mapper.BookingMapper;
import com.eventhub.repository.BookingRepository;
import com.eventhub.repository.EventRepository;
import com.eventhub.repository.UserRepository;
import com.eventhub.service.BookingService;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            EventRepository eventRepository,
            UserRepository userRepository,
            BookingMapper bookingMapper) {

        this.bookingRepository = bookingRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {

        User user = getLoggedInUser();

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Event not found"));

        Integer quantity = request.getTicketQuantity();

        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException(
                    "Ticket quantity must be at least 1");
        }

        if (event.getAvailableSeats() < quantity) {
            throw new IllegalArgumentException(
                    "Only " + event.getAvailableSeats()
                    + " seats are available");
        }

        if (event.getPrice() == null ||
                event.getPrice().compareTo(BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Invalid event price");
        }

        BigDecimal totalAmount =
                event.getPrice()
                        .multiply(BigDecimal.valueOf(quantity));

        Booking booking =
                bookingMapper.toEntity(request, user, event);

        booking.setTotalAmount(totalAmount);
        booking.setStatus(BookingStatus.PENDING);

        event.setAvailableSeats(
                event.getAvailableSeats() - quantity);

        eventRepository.save(event);

        Booking savedBooking =
                bookingRepository.save(booking);

        return bookingMapper.toResponse(savedBooking);
    }

    @Override
    public BookingResponse getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Booking not found"));

        checkBookingAccess(booking);

        return bookingMapper.toResponse(booking);
    }

    @Override
    public List<BookingResponse> getMyBookings() {

        User user = getLoggedInUser();

        return bookingRepository.findByUserId(user.getId())
                .stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    @Override
    public List<BookingResponse> getAllBookings() {

        return bookingRepository.findAll()
                .stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Booking not found"));

        checkBookingAccess(booking);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException(
                    "Booking is already cancelled");
        }

        Event event = booking.getEvent();

        event.setAvailableSeats(
                event.getAvailableSeats()
                + booking.getTicketQuantity());

        eventRepository.save(event);

        booking.setStatus(BookingStatus.CANCELLED);

        Booking savedBooking =
                bookingRepository.save(booking);

        return bookingMapper.toResponse(savedBooking);
    }

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new IllegalArgumentException(
                    "User is not authenticated");
        }

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Logged in user not found"));
    }

    private void checkBookingAccess(Booking booking) {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication.getAuthorities()
                .stream()
                .anyMatch(a ->
                        a.getAuthority().equals("ROLE_ADMIN"))) {

            return;
        }

        User user = getLoggedInUser();

        if (!booking.getUser().getId().equals(user.getId())) {

            throw new IllegalArgumentException(
                    "You are not allowed to access this booking");
        }
    }
}