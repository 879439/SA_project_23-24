package org.example.backend.controllers;
import org.example.backend.Annotations.ValidDate;
import org.example.backend.models.Booking;
import org.example.backend.models.Flight;
import org.example.backend.requests.BookFlight;
import org.example.backend.responses.BookingResponse;
import org.example.backend.responses.MessageResponse;
import org.example.backend.services.BookingService;
import org.example.backend.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
    @GetMapping("/{{bookingId}}")
    public ResponseEntity<?> getBooking(@PathVariable String bookingId){
        Optional<Booking> booking = bookingService.getBookingById(bookingId);
        List<BookingResponse> newBookings = new ArrayList<>();
        if(booking.isPresent()){
            Booking b= booking.get();
            if(b.getFlightId1()!=null) {
                Flight flight = flightService.getFlightById(b.getFlightId1());
                if(flight!=null) {
                    BookingResponse newBooking = new BookingResponse(b, flight.getDeparture(), flight.getArrival(), flight.getDate(), flight.getDeparture_time(), flight.getTravelClass());
                    newBookings.add(newBooking);
                }
            }
            if(b.getFlightId2()!=null) {
                Flight flight2 = flightService.getFlightById(b.getFlightId2());
                if(flight2!=null) {
                    BookingResponse newBooking = new BookingResponse(b, flight2.getDeparture(), flight2.getArrival(), flight2.getDate(), flight2.getDeparture_time(), flight2.getTravelClass());
                    newBookings.add(newBooking);
                }
            }
        }
        return ResponseEntity.ok(newBookings);
    }
    @GetMapping("/myBookings")
    public ResponseEntity<?> getMyBookings(){
        List <Booking> bookings=bookingService.getBookingsByUserId();
        List<BookingResponse> newBookings = new ArrayList<>();
        for(Booking b: bookings){

            if(b.getFlightId1()!=null) {
                Flight flight = flightService.getFlightById(b.getFlightId1());
                if(flight!=null) {
                    BookingResponse newBooking = new BookingResponse(b, flight.getDeparture(), flight.getArrival(), flight.getDate(), flight.getDeparture_time(), flight.getTravelClass());
                    newBookings.add(newBooking);
                }
            }
            if(b.getFlightId2()!=null) {
                Flight flight2 = flightService.getFlightById(b.getFlightId2());
                if(flight2!=null) {
                    BookingResponse newBooking = new BookingResponse(b, flight2.getDeparture(), flight2.getArrival(), flight2.getDate(), flight2.getDeparture_time(), flight2.getTravelClass());
                    newBookings.add(newBooking);
                }
            }

        }
        return ResponseEntity.ok(newBookings);

    }
    @GetMapping("/ticket")
    public ResponseEntity<Resource> getTicket(
            @RequestParam String bookingId) {
        try {
            Path file = Paths.get("tickets/"+bookingId+".pdf").normalize();
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

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
