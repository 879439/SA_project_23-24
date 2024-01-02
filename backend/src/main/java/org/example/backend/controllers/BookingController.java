package org.example.backend.controllers;
import org.example.backend.models.Booking;
import org.example.backend.requests.BookFlight;
import org.example.backend.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final FlightService flightService;

    @Autowired
    public BookingController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flight/{flightId}")
    public List<Booking> getBookingsForFlight(@PathVariable String flightId) {
        return flightService.getBookingsForFlight(flightId);
    }

    @PostMapping("/flight")
    public Booking bookFlight(@RequestBody BookFlight bookFlight) {

        return flightService.bookFlight(bookFlight.getFlightId(), bookFlight.getFirstname(),bookFlight.getLastname(),bookFlight.getEmail(),bookFlight.getBirthday(),bookFlight.getSex(),bookFlight.getSeat(),bookFlight.getFoods());
    }

    @DeleteMapping("/flight/{flightId}/{bookingId}")
    public void cancelBooking(@PathVariable String flightId, @PathVariable String bookingId) {
        flightService.cancelBooking(flightId, bookingId);
    }
}
