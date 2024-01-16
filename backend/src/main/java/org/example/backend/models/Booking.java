package org.example.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;
    private Map<String,String> flightIds= new HashMap<>();
    private String type;
    private ArrayList<Passenger> passengers;
    private double price;
    private String date;

    public Booking(String flightId1, String flightId2, String type){
        this.flightIds.put("one-way",flightId1);
        this.flightIds.put("round-trip",flightId2);
        this.type=type;
        this.passengers = new ArrayList<Passenger>();


    }
    public Booking(String flightId, String type){
        this.flightIds.put("one-way",flightId);
        this.type=type;
        this.passengers = new ArrayList<Passenger>();


    }
    // Getters and setters

    public String getType() {
        return type;
    }

    public Map<String, String> getFlightIds() {
        return flightIds;
    }

    public void setFlightIds(Map<String, String> flightIds) {
        this.flightIds = flightIds;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

}