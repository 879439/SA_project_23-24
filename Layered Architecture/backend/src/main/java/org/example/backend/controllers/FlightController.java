package org.example.backend.controllers;

import org.example.backend.Annotations.ValidDate;
import org.example.backend.models.Flight;
import org.example.backend.models.Seat;
import org.example.backend.requests.CreateFlight;
import org.example.backend.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
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

    @PutMapping
    public Flight saveFlight(@Valid @RequestBody CreateFlight flight) throws IOException {
        Flight newFlight = new Flight(flight.getCompany(), flight.getDeparture(), flight.getArrival(), flight.getDate(), flight.getDeparture_time(),flight.getArrival_time(),flight.getDiscountCode(),flight.getFoods(), flight.getTravelClass());
        return flightService.saveFlight(newFlight, flight.getSize());
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
    }
}
