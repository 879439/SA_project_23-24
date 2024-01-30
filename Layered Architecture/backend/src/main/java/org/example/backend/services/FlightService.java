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
