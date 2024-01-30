package org.example.bookingservice.controllers;

import jakarta.validation.Valid;
import org.example.bookingservice.models.Booking;
import org.example.bookingservice.requests.BookFlight;
import org.example.bookingservice.responses.BookingResponse;
import org.example.bookingservice.responses.MessageResponse;
import org.example.bookingservice.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200","http://FlightService:8081"})
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBooking(@PathVariable String bookingId){
        List<BookingResponse> newBooking = bookingService.getBooking(bookingId);
        return ResponseEntity.ok(newBooking);
    }
    @GetMapping("/myBookings")
    public ResponseEntity<?> getMyBookings(){
        List<BookingResponse> newBookings = bookingService.getMyBookings();
        return ResponseEntity.ok(newBookings);

    }
    @GetMapping("/flight/{flightId}")
    public List<Booking> getBookingsForFlight(@PathVariable String flightId) {
        return bookingService.getBookingsForFlight(flightId);
    }

    @PostMapping("/flight")
    public ResponseEntity<?> bookFlight(@Valid @RequestBody BookFlight bookFlight) {
        Booking booking = bookingService.bookFlight(bookFlight);
        if(booking==null){
            return ResponseEntity.badRequest().body(new MessageResponse("Seat not available or flight not existing!"));
        }else{

            return ResponseEntity.ok().body(booking);
        }
    }
    @DeleteMapping("/{bookingId}")
    public void cancelBooking(@PathVariable String bookingId) {
        bookingService.cancelBooking(bookingId);
    }
}
