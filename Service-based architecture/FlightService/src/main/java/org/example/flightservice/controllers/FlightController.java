package org.example.flightservice.controllers;


import jakarta.validation.Valid;
import org.example.flightservice.annotations.ValidDate;
import org.example.flightservice.models.Booking;
import org.example.flightservice.models.Flight;
import org.example.flightservice.requests.BookFlight;
import org.example.flightservice.requests.CreateFlight;
import org.example.flightservice.responses.MessageResponse;
import org.example.flightservice.services.FlightService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = {"http://localhost:4200","http://BookingService:8082"})
@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}")
    public Flight getFlightById(@PathVariable String id) {
        return flightService.getFlightById(id);
    }
    @GetMapping("/{departure}/{arrival}")
    public List<Flight> getFlightsByCities(@PathVariable String departure,@PathVariable String arrival){
        return flightService.getFlightsByCities(departure,arrival);
    }

    @GetMapping("/one-way")
    public ResponseEntity<List<Flight>> getFlights(
            @RequestParam String departure,
            @RequestParam String arrival,
            @RequestParam @ValidDate String date,
            @RequestParam String travelClass,
            @RequestParam int nPerson) {

        List<Flight> flights = flightService.findFlights(departure, arrival, date, travelClass, nPerson);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
    @GetMapping("/round-trip")
    public ResponseEntity<List<Map<String,Flight>>> getFlights(
            @RequestParam String departure,
            @RequestParam String arrival,
            @RequestParam @ValidDate String date,
            @RequestParam @ValidDate String returnDate,
            @RequestParam String travelClass,
            @RequestParam int nPerson) {
        List<Flight> flights = flightService.findFlights(departure, arrival, date,travelClass,nPerson);
        List<Flight> flights2 = flightService.findFlights(arrival, departure, returnDate,travelClass,nPerson);
        List<Map<String,Flight>> flights1 = new ArrayList<>();
        for(Flight f: flights){
            for(Flight f1:flights2){
                Map<String,Flight> p= new HashMap<>();
                System.out.println(f.getId());
                System.out.println(f1.getId());
                p.put("flight1",f);
                p.put("flight2",f1);
                flights1.add(p);
            }
        }
        System.out.println(flights1.get(0));

            return new ResponseEntity<>(flights1, HttpStatus.OK);

    }
    @PostMapping("/bookFlight")
    public Booking bookFlight(@Valid @RequestBody BookFlight bookFlight){
        System.out.println("IM HEREEE");
        return this.flightService.bookFlight(bookFlight);

    }
    @PostMapping("/seatsReset")
    public ResponseEntity bookFlight(@Valid @RequestBody Booking booking){
        if(this.flightService.resetSeats(booking)){
            return ResponseEntity.ok().body(new MessageResponse("operation complete"));
        }else{
            return ResponseEntity.badRequest().body(new MessageResponse("operation not complete"));
        }

    }

    @PutMapping("")
    public ResponseEntity<?> saveFlight(@Valid @RequestBody CreateFlight flight) throws IOException {
        Flight newFlight = new Flight(flight.getCompany(), flight.getDeparture(), flight.getArrival(), flight.getDate(), flight.getDeparture_time(),flight.getArrival_time(),flight.getDiscountCode(),flight.getFoods(), flight.getTravelClass());
        System.out.println("Im HEREEE");
        System.out.println("IM HEREEE");
        List <String> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(Objects::toString).toList();
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
        if(authorities.contains("admin")) {
            return ResponseEntity.ok(flightService.saveFlight(newFlight, flight.getSize()));
        }else{
            return ResponseEntity.badRequest().body("You are not an Admin!");
        }

    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
    }
}
