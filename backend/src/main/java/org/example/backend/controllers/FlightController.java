package org.example.backend.controllers;

import org.example.backend.Annotations.ValidDate;
import org.example.backend.models.Flight;
import org.example.backend.models.Seat;
import org.example.backend.requests.CreateFlight;
import org.example.backend.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    @GetMapping("/")
    public ResponseEntity<List<Flight>> getFlights(
            @RequestParam String departure,
            @RequestParam String arrival,
            @RequestParam @ValidDate String date) {
        List<Flight> flights = flightService.findFlights(departure, arrival, date);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @PutMapping
    public Flight saveFlight(@Valid @RequestBody CreateFlight flight) throws IOException {
        Flight newFlight = new Flight(flight.getCompany(), flight.getDeparture(), flight.getArrival(), flight.getDate(), flight.getDeparture_time(),flight.getArrival_time(),flight.getDiscountCode(),flight.getFoods());
        return flightService.saveFlight(newFlight, flight.getSize());
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
    }
}
