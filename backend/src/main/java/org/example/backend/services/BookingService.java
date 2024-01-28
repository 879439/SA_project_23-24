package org.example.backend.services;

import org.example.backend.models.*;
import org.example.backend.repositories.BookingRepository;
import org.example.backend.repositories.FlightRepository;
import org.example.backend.repositories.UserRepository;
import org.example.backend.requests.BookFlight;
import org.example.backend.requests.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
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
    private PdfService pdfService;

    @Autowired
    private EmailService emailService;

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
    public Booking bookFlight(BookFlight bookFlight){
        Booking booking = new Booking(bookFlight.getFlightId1(), bookFlight.getType());
        TicketInfo ticketInfo = new TicketInfo(bookFlight.getType());
        setTicketInfo(ticketInfo, booking.getFlightId1());
        if(bookFlight.getType().equals("one-way")){
            for (Person p: bookFlight.getPeople()){
                if(!makeBookingOneWay(booking,bookFlight.getFlightId1(),p)){
                    return null;
                }
            }
        }else{
            setTicketInfo(ticketInfo, bookFlight.getFlightId2());
            booking.setFlightId2(bookFlight.getFlightId2());
            for (Person p: bookFlight.getPeople()){
                if(!makeBookingRoundTrip(booking,bookFlight.getFlightId1(), booking.getFlightId2(),p)){
                    return null;
                }
            }
        }
        booking.setDate(LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth());
        Flight flight = flightRepository.findById(bookFlight.getFlightId1()).orElse(null);
        if ( bookFlight.getDiscountCode()!=null && bookFlight.getDiscountCode().equals(flight.getDiscountCode())) {
            booking.setPrice((float) (booking.getPrice() - 0.1 * booking.getPrice()));
        }
        bookingRepository.save(booking);
        ticketInfo.setPassengers(booking.getPassengers());
        ticketInfo.setBookingId(booking.getId());
        ticketInfo.setPrice(booking.getPrice());
        createAndSendTicket(booking,ticketInfo);
        return booking;

    }
    public boolean setSeat(Booking booking,boolean isAdult,String seat,List<Seat> seats){
        for(Seat s: seats){
            if(s.getName().equals(seat) && s.isAvailable()){
                s.setAvailable(false);
                if(isAdult) {
                    booking.setPrice(booking.getPrice() + s.getPrice());
                }else{
                    booking.setPrice(booking.getPrice() + s.getChildrenPrice());
                }
                return true;
            }
        }
        return false;
    }
    public boolean setFood(Booking booking,String food,List<Food> foods){
        if(food!=null) {
            for (Food f : foods) {
                if (food.equals(f.getName()) && f.getQuantity() > 0) {
                    f.setQuantity(f.getQuantity() - 1);
                    booking.setPrice(booking.getPrice() + f.getPrice());
                    return true;
                }
            }
        }
        return false;
    }
    public Passenger setPassenger(Person p){
        Passenger p1 = new Passenger();
        p1.setFirstname(p.getFirstname());
        p1.setLastname(p.getLastname());
        p1.setEmail(p.getEmail());
        p1.setSex(p.getSex());
        p1.setBirthday(p.getBirthday());
        p1.setFood(p.getFood());
        p1.setSeat(p.getSeat());
        p1.setAdult(p.isAdult());
        return p1;
    }
    public boolean makeBookingRoundTrip(Booking booking, String flightId1,String flightId2,Person p){
        AtomicReference<String> name = new AtomicReference<>(SecurityContextHolder.getContext().getAuthentication().getName());
        Flight flight = flightRepository.findById(flightId1).orElse(null);
        Flight flight2 = flightRepository.findById(flightId2).orElse(null);
        Optional<User> u = userRepository.findByUsername(name.get());
        boolean flag=false;
        u.ifPresent(user->{
            name.set(user.getId());
        });
        booking.setUserId(name.get());
        if(flight==null || flight2==null){
            return false;
        }
        if(!setSeat(booking,p.isAdult(),p.getSeat(),flight.getSeats())){
            return false;
        }
        if(!setSeat(booking,p.isAdult(),p.getReturnSeat(),flight2.getSeats())){
            return false;
        }
        setFood(booking,p.getFood(),flight.getFoods());
        setFood(booking,p.getReturnFood(),flight2.getFoods());
        flightRepository.save(flight);
        flightRepository.save(flight2);
        Passenger p1 = setPassenger(p);
        p1.setReturnSeat(p.getReturnSeat());
        p1.setReturnFood(p.getReturnFood());
        booking.getPassengers().add(p1);
        return true;
    }
    public boolean makeBookingOneWay(Booking booking, String flightId, Person p){
        AtomicReference<String> name = new AtomicReference<>(SecurityContextHolder.getContext().getAuthentication().getName());
        Flight flight = flightRepository.findById(flightId).orElse(null);
        Optional<User> u = userRepository.findByUsername(name.get());
        boolean flag=false;
        u.ifPresent(user->{
            name.set(user.getId());
        });
        booking.setUserId(name.get());
        if(flight==null){
            return false;
        }
        if(!setSeat(booking,p.isAdult(),p.getSeat(),flight.getSeats())){
            return false;
        }
        setFood(booking,p.getFood(),flight.getFoods());
        flightRepository.save(flight);
        Passenger p1 = setPassenger(p);
        booking.getPassengers().add(p1);
        return true;

    }
    public void setTicketInfo(TicketInfo ticketInfo, String flightId){
        Flight flight = flightRepository.findById(flightId).orElse(null);
        if(flight!=null) {
            ticketInfo.getArrivals().add(flight.getArrival());
            ticketInfo.getDepartures().add(flight.getDeparture());
            ticketInfo.getDates().add(flight.getDate());
            ticketInfo.getTimes().add(flight.getDeparture_time());
        }

    }

    public void createAndSendTicket(Booking booking,TicketInfo ticketInfo) {
        byte[] pdfBytes = pdfService.generateTicketPdf(ticketInfo);
        String outputPath = "tickets/"+ticketInfo.getBookingId()+".pdf"; // Percorso dove vuoi salvare il file PDF

        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            fos.write(pdfBytes);
            System.out.println("Il file PDF è stato salvato in: " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        emailService.sendTicketEmail(ticketInfo.getPassengers(), "Your Ticket id:"+ticketInfo.getBookingId(), "Here is your ticket", pdfBytes);
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
