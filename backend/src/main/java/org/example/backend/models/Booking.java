package org.example.backend.models;

import org.example.backend.Annotations.ValidDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;
    @NotNull
    private Map<String,String> flightIds= new HashMap<>();
    private String userId;
    @NotNull
    private String type;
    @NotNull
    private ArrayList<Passenger> passengers;
    @NotNull
    private double price;
    @NotNull
    @ValidDate
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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