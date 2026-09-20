package com.eventhub.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dto.BookingRequest;
import com.eventhub.dto.BookingResponse;
import com.eventhub.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(
            BookingService bookingService) {

        this.bookingService = bookingService;
    }


    // CREATE BOOKING
  

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request) {

        BookingResponse response =
                bookingService.createBooking(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // MY BOOKINGS
 

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings() {

        return ResponseEntity.ok(
                bookingService.getMyBookings()
        );
    }

 
    // GET SINGLE BOOKING

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.getBookingById(id)
        );
    }

  
    // ADMIN - ALL BOOKINGS
    

    @GetMapping
    @org.springframework.security.access.prepost.PreAuthorize(
            "hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {

        return ResponseEntity.ok(
                bookingService.getAllBookings()
        );
    }

    
    // CANCEL BOOKING

    @DeleteMapping("/{id}")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.cancelBooking(id)
        );
    }
}