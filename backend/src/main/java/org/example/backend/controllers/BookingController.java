package org.example.backend.controllers;
import org.example.backend.models.Booking;
import org.example.backend.requests.BookFlight;
import org.example.backend.responses.MessageResponse;
import org.example.backend.services.BookingService;
import org.example.backend.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @GetMapping("")
    public ResponseEntity<?> getMyBookings(){
        return ResponseEntity.ok(bookingService.getBookingsByUserId());

    }
    @GetMapping("/flight/{flightId}")
    public List<Booking> getBookingsForFlight(@PathVariable String flightId) {
        return bookingService.getBookingsForFlight(flightId);
    }

    @PostMapping("/flight")
    public ResponseEntity<?> bookFlight(@Valid @RequestBody BookFlight bookFlight) {
        Booking booking = flightService.bookFlight(bookFlight);
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
