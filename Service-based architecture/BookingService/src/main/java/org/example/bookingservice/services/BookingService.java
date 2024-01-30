package org.example.bookingservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookingservice.jwt.JwtUtils;
import org.example.bookingservice.models.*;
import org.example.bookingservice.repositories.BookingRepository;
import org.example.bookingservice.requests.BookFlight;
import org.example.bookingservice.requests.PdfRequest;
import org.example.bookingservice.requests.Person;
import org.example.bookingservice.responses.BookingResponse;
import org.example.bookingservice.responses.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BookingService {
    private final MongoTemplate mongoTemplate;
    private final BookingRepository bookingRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public BookingService(BookingRepository bookingRepository, MongoTemplate mongoTemplate) {
        this.bookingRepository = bookingRepository;
        this.mongoTemplate = mongoTemplate;
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
        if(!Objects.equals(name, "anonymousUser")) {
            return bookingRepository.findBookingsByUserId(name);
        }else{
            return new ArrayList<>();
        }
    }
    public Booking bookFlight(BookFlight bookFlight){
        TicketInfo ticketInfo = new TicketInfo(bookFlight.getType());
        setTicketInfo(ticketInfo, bookFlight.getFlightId1());
        if(bookFlight.getType().equals("round-trip")){
            setTicketInfo(ticketInfo, bookFlight.getFlightId2());
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer "+jwtUtils.generateTokenFromUsername(username));
        Booking booking = restTemplate.exchange("http://FlightService:8081/api/flights/bookFlight", HttpMethod.POST, new HttpEntity<>(bookFlight,headers), Booking.class).getBody();
        if (booking!=null) {
            bookingRepository.save(booking);
            ticketInfo.setPassengers(booking.getPassengers());
            ticketInfo.setBookingId(booking.getId());
            ticketInfo.setPrice(booking.getPrice());
            restTemplate.put("http://PdfService:8083/", new PdfRequest(booking, ticketInfo));
        }
        return booking;

    }
    public void setTicketInfo(TicketInfo ticketInfo, String flightId){
        Flight flight = restTemplate.getForObject("http://FlightService:8081/api/flights/"+flightId,Flight.class);
        if(flight!=null) {
            ticketInfo.getArrivals().add(flight.getArrival());
            ticketInfo.getDepartures().add(flight.getDeparture());
            ticketInfo.getDates().add(flight.getDate());
            ticketInfo.getTimes().add(flight.getDeparture_time());
        }

    }
    public List<BookingResponse> getMyBookings(){
        List <Booking> bookings= getBookingsByUserId();
        List<BookingResponse> newBookings = new ArrayList<>();
        for(Booking b: bookings){

            if(b.getFlightId1()!=null) {
                Flight flight = restTemplate.getForObject("http://FlightService:8081/api/flights/"+b.getFlightId1(),Flight.class);
                if(flight!=null) {
                    BookingResponse newBooking = new BookingResponse(b, flight.getDeparture(), flight.getArrival(), flight.getDate(), flight.getDeparture_time(), flight.getTravelClass());
                    newBookings.add(newBooking);
                }
            }
            if(b.getFlightId2()!=null) {
                Flight flight2 = restTemplate.getForObject("http://FlightService:8081/api/flights/"+b.getFlightId2(),Flight.class);
                if(flight2!=null) {
                    BookingResponse newBooking = new BookingResponse(b, flight2.getDeparture(), flight2.getArrival(), flight2.getDate(), flight2.getDeparture_time(), flight2.getTravelClass());
                    newBookings.add(newBooking);
                }
            }

        }
        return newBookings;
    }
    public List<BookingResponse> getBooking(String bookingId){
        Optional<Booking> booking = getBookingById(bookingId);
        List<BookingResponse> newBookings = new ArrayList<>();
        if(booking.isPresent()){
            Booking b= booking.get();
            if(b.getFlightId1()!=null) {
                Flight flight = restTemplate.getForObject("http://FlightService:8081/api/flights/"+b.getFlightId1(),Flight.class);
                if(flight!=null) {
                    BookingResponse newBooking = new BookingResponse(b, flight.getDeparture(), flight.getArrival(), flight.getDate(), flight.getDeparture_time(), flight.getTravelClass());
                    newBookings.add(newBooking);
                }
            }
            if(b.getFlightId2()!=null) {
                Flight flight2 = restTemplate.getForObject("http://FlightService:8081/api/flights/"+b.getFlightId1(),Flight.class);
                if(flight2!=null) {
                    BookingResponse newBooking = new BookingResponse(b, flight2.getDeparture(), flight2.getArrival(), flight2.getDate(), flight2.getDeparture_time(), flight2.getTravelClass());
                    newBookings.add(newBooking);
                }
            }
        }
        return newBookings;
    }
    public void cancelBooking(String bookingId) {

        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            RequestCallback requestCallback = new RequestCallback() {
                @Override
                public void doWithRequest(ClientHttpRequest request) throws IOException {
                    request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    // Serialize bookFlightDetails to JSON and write to request body
                    // This depends on how you handle serialization, e.g., using ObjectMapper if you're using Jackson
                    OutputStream outputStream = request.getBody();
                    ObjectMapper objectMapper= new ObjectMapper();
                    // Assuming objectMapper is an instance of com.fasterxml.jackson.databind.ObjectMapper
                    objectMapper.writeValue(outputStream, booking);
                    outputStream.flush();
                }
            };

            ResponseExtractor<ResponseEntity<?>> responseExtractor = new ResponseExtractor<ResponseEntity<?>>() {
                @Override
                public ResponseEntity<String> extractData(ClientHttpResponse response) throws IOException {
                    // Check response status code if necessary
                    if (response.getStatusCode().is2xxSuccessful()) {
                        // Deserialize JSON from response body to Booking object
                        // This also depends on your JSON handling, e.g., using ObjectMapper
                        ObjectMapper objectMapper= new ObjectMapper();
                        return objectMapper.readValue(response.getBody(), ResponseEntity.class);
                    }
                    // Handle non-successful response or throw an exception
                    return null;
                }
            };
            //ResponseEntity<?> response = restTemplate.execute("http://FlightService:8081/api/flights/seatsReset", HttpMethod.POST,requestCallback,responseExtractor);
            ResponseEntity<MessageResponse> response= restTemplate.postForEntity("http://FlightService:8081/api/flights/seatsReset",booking,MessageResponse.class);
            if(response.getStatusCode().is2xxSuccessful()) {
                bookingRepository.delete(booking);
            }
        }
    }


}
