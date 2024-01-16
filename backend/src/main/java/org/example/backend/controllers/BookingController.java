package org.example.backend.controllers;
import org.example.backend.models.Booking;
import org.example.backend.requests.BookFlight;
import org.example.backend.services.BookingService;
import org.example.backend.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final FlightService flightService;
    private final BookingService bookingService;

    @Autowired
    public BookingController(FlightService flightService, BookingService bookingService) {
        this.flightService = flightService;
        this.bookingService = bookingService;
    }

    @GetMapping("/flight/{flightId}")
    public List<Booking> getBookingsForFlight(@PathVariable String flightId) {
        return bookingService.getBookingsForFlight(flightId);
    }

    @PostMapping("/flight")
    public Booking bookFlight(@RequestBody BookFlight bookFlight) {
        System.out.println("IM HERE!!!!!");
        return flightService.bookFlight(bookFlight);
    }
    @DeleteMapping("/{bookingId}")
    public void cancelBooking(@PathVariable String bookingId) {
        bookingService.cancelBooking(bookingId);
    }
}
