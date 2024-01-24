package org.example.backend.services;

import org.example.backend.models.Booking;
import org.example.backend.models.Flight;
import org.example.backend.repositories.BookingRepository;
import org.example.backend.repositories.FlightRepository;
import org.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BookingService {
    private final MongoTemplate mongoTemplate;
    private final BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository,MongoTemplate mongoTemplate) {
        this.bookingRepository = bookingRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Booking> getBookingsForFlight(String flightId) {
        List<Booking> bookings = bookingRepository.findByFlightIds(flightId);
        return bookings != null ? bookings : Collections.emptyList();
    }
    public void cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            bookingRepository.delete(booking);
        }
    }

}
