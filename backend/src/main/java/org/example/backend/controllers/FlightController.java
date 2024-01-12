package org.example.backend.controllers;

import org.example.backend.models.Flight;
import org.example.backend.models.Seat;
import org.example.backend.requests.CreateFlight;
import org.example.backend.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Flight saveFlight(@RequestBody CreateFlight flight) {
        Flight newFlight = new Flight(flight.getCompany(), flight.getDeparture(), flight.getArrival(), flight.getDate(), flight.getTime());
        int c=0,r=0;
        if(flight.getSize().equals("small")){
            c=10;
            r=4;

        }else if(flight.getSize().equals("big")){
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
                Seat s = new Seat(a+""+j,true);
                seats.add(s);
            }
            int charValue = a;
            charValue++;
            a = (char) charValue;
        }

        newFlight.setSeats(seats);
        newFlight.setFoods(flight.getFoods());
        return flightService.saveFlight(newFlight);
    }

    @DeleteMapping("/{id}")
    public void deleteFlight(@PathVariable String id) {
        flightService.deleteFlight(id);
    }
}
