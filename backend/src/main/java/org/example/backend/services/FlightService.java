package org.example.backend.services;

import org.example.backend.models.*;
import org.example.backend.repositories.BookingRepository;
import org.example.backend.repositories.FlightRepository;
import org.example.backend.repositories.UserRepository;
import org.example.backend.requests.BookFlight;
import org.example.backend.requests.Person;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.bson.Document;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import org.json.JSONArray;


@Service
public class FlightService {
    private final MongoTemplate mongoTemplate;
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    @Autowired
    private PdfService pdfService;

    @Autowired
    private EmailService emailService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository,MongoTemplate mongoTemplate, BookingRepository bookingRepository) {
        this.flightRepository = flightRepository;
        this.mongoTemplate = mongoTemplate;
        this.bookingRepository = bookingRepository;
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }
    public List<Flight> getFlightsByCities(String departure, String arrival){
        return flightRepository.findByCities(departure,arrival);
    }

    public Flight getFlightById(String id) {
        return flightRepository.findById(id).orElse(null);
    }

    public Flight saveFlight(Flight flight, String size) throws IOException {
        int c=0,r=0;
        double price = priceCalculation(flight.getArrival(), flight.getDeparture(),flight.getTravelClass());
        double childrenPrice = (price*3)/4;
        System.out.println("Il prezzo "+price);
        if(size.equals("small")){
            c=10;
            r=4;

        }else if(size.equals("big")){
            c=25;
            r=6;
        }else{
            c=15;
            r=6;
        }
        ArrayList<Seat> seats= new ArrayList<>();
        char a='A';
        for(int i=0;i<c;i++){
            for(int j=0;j<r;j++){
                Seat s = new Seat(a+""+j,true,price,childrenPrice);
                seats.add(s);
            }
            int charValue = a;
            charValue++;
            a = (char) charValue;
        }
        flight.setSeats(seats);
        return flightRepository.save(flight);
    }
    public double priceCalculation(String departure, String arrival, String travelClass) throws IOException {
        double[] DepartureCoordinates = getCoordinates(departure);
        double[] ArrivalCoordinates = getCoordinates(arrival);
        System.out.println(DepartureCoordinates[0] + " E "+ArrivalCoordinates[1]);
        double theta = DepartureCoordinates[1]- ArrivalCoordinates[1];
        double dist = Math.sin(Math.toRadians(DepartureCoordinates[0])) * Math.sin(Math.toRadians(ArrivalCoordinates[0])) + Math.cos(Math.toRadians(DepartureCoordinates[0])) * Math.cos(Math.toRadians(ArrivalCoordinates[0])) * Math.cos(Math.toRadians(theta));

        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515*1.609344*0.5;
        if(travelClass.equals("business")){
            dist = dist *1.4;
        } else if (travelClass.equals("firstclass")) {
            dist = dist *1.8;
        }
        return dist;
    }

    public double[] getCoordinates(String city) throws IOException {
        URL url = new URL("https://nominatim.openstreetmap.org/search?city=" + city.replaceAll(" ", "%20") + "&format=json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        Scanner scanner = new Scanner(conn.getInputStream());
        String response = scanner.useDelimiter("\\A").next();
        scanner.close();

        JSONArray jsonArray = new JSONArray(response);
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        double lat = jsonObject.getDouble("lat");
        double lon = jsonObject.getDouble("lon");

        return new double[]{lat, lon};
    }

    public void deleteFlight(String id) {
        List<Booking> bookings = bookingRepository.findByFlightIds(id);
        bookingRepository.deleteAll(bookings);
        flightRepository.deleteById(id);
    }
    public boolean setSeat(Booking booking,boolean isAdult,String seat,List<Seat> seats){
        for(Seat s: seats){
            if(s.getName().equals(seat)){
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

    public List<Flight> findFlights(String departure, String arrival, String date, String travelClass, int nPerson){
        List<Flight> flights = flightRepository.findByCitiesAndDate(departure,arrival,date);
        System.out.println("SO-->"+departure+" "+arrival);
        System.out.println("SO-->"+flights.get(0).getTravelClass());
        System.out.println("SO-->"+travelClass);
        if(!Objects.equals(travelClass, "")) {
            flights.removeIf(f -> !f.getTravelClass().equals(travelClass));
        }
        for(Flight f: flights){
            f.getSeats().removeIf(s -> !s.isAvailable());
        }

        flights=flights.stream().filter(flight -> flight.getSeats().size()>nPerson).toList();

        return flights;
    }
    public List<Flight> findFlights(String departure, String arrival, String date, String returnDate,String travelClass, int nPerson){
        List<Flight> flights = flightRepository.findByCitiesAndDate(departure,arrival,date,returnDate);
        flights.removeIf(f -> !f.getTravelClass().equals(travelClass));
        for(Flight f: flights){
            f.getSeats().removeIf(s -> !s.isAvailable());
        }

        flights=flights.stream().filter(flight -> flight.getSeats().size()>nPerson).toList();
        return flights;
    }


}
