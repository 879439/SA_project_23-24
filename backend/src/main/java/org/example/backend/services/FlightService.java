package org.example.backend.services;

import org.example.backend.models.*;
import org.example.backend.repositories.FlightRepository;
import org.example.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Update.update;

@Service
public class FlightService {
    private final MongoTemplate mongoTemplate;
    private final FlightRepository flightRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository,MongoTemplate mongoTemplate) {
        this.flightRepository = flightRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(String id) {
        return flightRepository.findById(id).orElse(null);
    }

    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public void deleteFlight(String id) {
        flightRepository.deleteById(id);
    }
    public List<Booking> getBookingsForFlight(String flightId) {
        Flight flight = flightRepository.findById(flightId).orElse(null);
        return flight != null ? flight.getBookings() : Collections.emptyList();
    }

    public Booking bookFlight(String flightId, String firstname, String lastname, String email, String birthday, String sex, String seat, ArrayList<String> foods) {

        Flight flight = flightRepository.findById(flightId).orElse(null);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(name);
        boolean flag=false;
        for(Seat seat1: flight.getSeats()) {
            if (seat1.getName().equals(seat)) {
                if (seat1.isAvailable()) {

                    seat1.setAvailable(false);
                    flightRepository.save(flight);
                    flag = true;
                }
            }
        }
        if(!flag){
            return null;
        }
        for(Food f:flight.getFoods()){
            if(foods.contains(f.getName())){
                f.setQuantity(f.getQuantity()-1);
                flightRepository.save(flight);
            }
        }
        if ("anonymousUser".equals(name)) {
            if (flight != null) {
                Booking booking = new Booking();
                booking.setFlightId(flightId);
                booking.setFirstname(firstname);
                booking.setLastname(lastname);
                booking.setEmail(email);
                booking.setSex(sex);
                booking.setBirthday(birthday);
                booking.setFoods(foods);
                booking.setSeat(seat);

                if (flight.getBookings() == null) {
                    flight.setBookings(new ArrayList<>());
                }
                flight.getBookings().add(booking);

                flightRepository.save(flight);
                return booking;
            }
        }else{
            Booking booking = new Booking();
            booking.setFlightId(flightId);
            Optional<User> u = userRepository.findByUsername(name);
            u.ifPresent(user -> {
                booking.setUserId(user.getId());
                booking.setBirthday(user.getBirthday());
                booking.setEmail(user.getEmail());
                booking.setFirstname(user.getFirstname());
                booking.setLastname(user.getLastname());
                booking.setSex(user.getSex());

            });
            booking.setFoods(foods);
            booking.setSeat(seat);
            if (flight.getBookings() == null) {
                flight.setBookings(new ArrayList<>());
            }
            flight.getBookings().add(booking);

            flightRepository.save(flight);
            return booking;
        }
        return null;
    }

    public void cancelBooking(String flightId, String bookingId) {
        Flight flight = flightRepository.findById(flightId).orElse(null);
        if (flight != null && flight.getBookings() != null) {
            flight.getBookings().removeIf(booking -> booking.getId().equals(bookingId));
            flightRepository.save(flight);
        }
    }
}
