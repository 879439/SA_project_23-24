package org.example.backend.services;

import org.example.backend.models.Booking;
import org.example.backend.models.User;
import org.example.backend.repositories.BookingRepository;
import org.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
    public List<Booking> getBookingsByUserId(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userRepository.findByUsername(name);
        System.out.println(name);
        AtomicReference<String> userId = new AtomicReference<>("");
        user.ifPresent(user1 -> {
            userId.set(user1.getId());
        });
        System.out.println("This is my Id:"+userId.get());
        return bookingRepository.findBookingsByUserId(userId.get());
    }
    public void cancelBooking(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            bookingRepository.delete(booking);
        }
    }

}
