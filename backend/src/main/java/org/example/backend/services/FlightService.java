package org.example.backend.services;

import org.example.backend.models.*;
import org.example.backend.repositories.BookingRepository;
import org.example.backend.repositories.FlightRepository;
import org.example.backend.repositories.UserRepository;
import org.example.backend.requests.BookFlight;
import org.example.backend.requests.Person;
import org.example.backend.responses.MessageResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
        double price = priceCalculation(flight.getArrival(), flight.getDeparture());
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
                Seat s = new Seat(a+""+j,true,price);
                seats.add(s);
            }
            int charValue = a;
            charValue++;
            a = (char) charValue;
        }
        flight.setSeats(seats);
        return flightRepository.save(flight);
    }
    public double priceCalculation(String departure, String arrival) throws IOException {
        double[] DepartureCoordinates = getCoordinates(departure);
        double[] ArrivalCoordinates = getCoordinates(arrival);
        System.out.println(DepartureCoordinates[0] + " E "+ArrivalCoordinates[1]);
        double theta = DepartureCoordinates[1]- ArrivalCoordinates[1];
        double dist = Math.sin(Math.toRadians(DepartureCoordinates[0])) * Math.sin(Math.toRadians(ArrivalCoordinates[0])) + Math.cos(Math.toRadians(DepartureCoordinates[0])) * Math.cos(Math.toRadians(ArrivalCoordinates[0])) * Math.cos(Math.toRadians(theta));

        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515*1.609344;
        return dist*0.5;
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
        flightRepository.deleteById(id);
    }
    public Booking bookFlightAux(Booking newBooking,String flightId,String discountCode, ArrayList<Person> people, TicketInfo ticketInfo){
        AtomicReference<String> name = new AtomicReference<>(SecurityContextHolder.getContext().getAuthentication().getName());
        Flight flight = flightRepository.findById(flightId).orElse(null);
        Optional<User> u = userRepository.findByUsername(name.get());
        u.ifPresent(user->{
            name.set(user.getId());
        });
        newBooking.setUserId(name.get());
        boolean flag=false;
        if (flight==null){
            return null;
        }
        ticketInfo.getArrivals().add(flight.getArrival());
        ticketInfo.getDepartures().add(flight.getDeparture());
        ticketInfo.getDates().add(flight.getDate());
        ticketInfo.getTimes().add(flight.getDeparture_time());
        for(Person p: people) {
            for (Seat seat1 : flight.getSeats()) {

                if (seat1.getName().equals(p.getSeat())) {

                    if (seat1.isAvailable()) {
                        System.out.println(seat1.getName()+"CIAO "+p.getSeat());
                        seat1.setAvailable(false);
                        newBooking.setPrice(newBooking.getPrice()+seat1.getPrice());
                        flightRepository.save(flight);
                        flag = true;
                    }
                }
            }
        }

        if(!flag){
            return null;
        }
        System.out.println("CIAO ");
        for(Person p: people) {
            if(p.getFoods()!=null) {
                for (Food f : flight.getFoods()) {
                    if (p.getFoods().contains(f.getName()) && f.getQuantity() > 0) {
                        f.setQuantity(f.getQuantity() - 1);
                        newBooking.setPrice(newBooking.getPrice() + f.getPrice());
                        flightRepository.save(flight);
                    }
                }
            }
        }
        if(newBooking.getFlightIds().get("one-way").equals(flightId)) {
            for (Person p : people) {
                Passenger p1 = new Passenger();
                p1.setFirstname(p.getFirstname());
                p1.setLastname(p.getLastname());
                p1.setEmail(p.getEmail());
                p1.setSex(p.getSex());
                p1.setBirthday(p.getBirthday());
                p1.setFoods(p.getFoods());
                p1.setSeat(p.getSeat());
                p1.setFoods(p.getFoods());
                p1.setSeat(p.getSeat());
                newBooking.getPassengers().add(p1);


            }
        }
        if(newBooking.getFlightIds().get("round-trip")==null || newBooking.getFlightIds().get("round-trip").equals(flightId)) {
            newBooking.setDate(LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getDayOfMonth());
            if ( discountCode!=null && discountCode.equals(flight.getDiscountCode())) {
                newBooking.setPrice((float) (newBooking.getPrice() - 0.1 * newBooking.getPrice()));
            }
        }
        System.out.println("Ho finito"+ newBooking.getPrice());
        newBooking=bookingRepository.save(newBooking);
        ticketInfo.setPassengers(newBooking.getPassengers());
        ticketInfo.setBookingId(newBooking.getId());
        ticketInfo.setPrice(newBooking.getPrice());
        return newBooking;
    }
    public Booking bookFlight(BookFlight booking) {
        Booking newBooking = new Booking(booking.getFlightId1(), booking.getType());
        TicketInfo ticketInfo = new TicketInfo(booking.getType());
        if(booking.getType().equals("one-way")){
            return bookFlightAux(newBooking,booking.getFlightId1(), booking.getDiscountCode(), booking.getPeople(), ticketInfo);
        }else{
            Map<String,String > flightIds = newBooking.getFlightIds();
            flightIds.put("round-trip", booking.getFlightId2());
            newBooking.setFlightIds(flightIds);
            newBooking = bookFlightAux(newBooking,booking.getFlightId1(), booking.getDiscountCode(), booking.getPeople(),ticketInfo);
            if(newBooking==null){
                return null;
            }
            newBooking = bookFlightAux(newBooking,booking.getFlightId2(), booking.getDiscountCode(), booking.getPeople(),ticketInfo);
            createAndSendTicket(ticketInfo);
            return newBooking;
        }

    }
    public void createAndSendTicket(TicketInfo ticketInfo) {
        byte[] pdfBytes = pdfService.generateTicketPdf(ticketInfo);
        emailService.sendTicketEmail(ticketInfo.getPassengers(), "Your Ticket id:"+ticketInfo.getBookingId(), "Here is your ticket", pdfBytes);
    }

    public List<Flight> findFlights(String departure, String arrival, String date){
        return flightRepository.findByCitiesAndDate(departure,arrival,date);
    }
    public List<Flight> findFlights(String departure, String arrival, String date, String returnDate){
        return flightRepository.findByCitiesAndDate(departure,arrival,date,returnDate);
    }


}
