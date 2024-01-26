package org.example.backend.services;

import org.example.backend.models.Booking;
import org.example.backend.models.Flight;
import org.example.backend.models.Passenger;
import org.example.backend.models.User;
import org.example.backend.repositories.BookingRepository;
import org.example.backend.repositories.FlightRepository;
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
    private final FlightRepository flightRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository,MongoTemplate mongoTemplate,FlightRepository flightRepository) {
        this.bookingRepository = bookingRepository;
        this.mongoTemplate = mongoTemplate;
        this.flightRepository = flightRepository;
    }
    public Optional<Booking> getBookingById(String id){
        return bookingRepository.findBookingById(id);
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
            for(Passenger p: booking.getPassengers()){
                if(booking.getFlightId1()!=null){
                    Optional<Flight> f = flightRepository.findById(booking.getFlightId1());
                    resetSeatsAndFoods(f,p);
                }
                if(booking.getFlightId2()!=null){
                    Optional<Flight> f2 = flightRepository.findById(booking.getFlightId2());
                    resetSeatsAndFoods(f2,p);
                }
            }
            bookingRepository.delete(booking);
        }
    }

    public void resetSeatsAndFoods(Optional<Flight> f,Passenger p){
        if(f.isPresent()) {
            Flight flight = f.get();
            flight.getSeats().forEach(seat -> {
                if (seat.getName().equals(p.getSeat())) {
                    seat.setAvailable(true);
                }
            });
            flight.getFoods().forEach(food -> {
                if (food.getName().equals(p.getFood())) {
                    food.setQuantity(food.getQuantity() + 1);
                }
            });
            flightRepository.save(flight);
        }
    }
}
