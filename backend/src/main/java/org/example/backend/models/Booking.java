package org.example.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;
    private String flightId;
    private ArrayList<Passenger> passengers;
    private double price;
    private String date;

    public Booking(String flightId){
        this.flightId = flightId;
        this.passengers = new ArrayList<Passenger>();


    }
    // Getters and setters

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

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

}