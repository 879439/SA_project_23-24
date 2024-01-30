package org.example.bookingservice.models;


import jakarta.validation.constraints.NotNull;
import org.example.bookingservice.Annotations.ValidDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;

@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;
    @NotNull
    private String  flightId1;
    private String  flightId2;
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

    public Booking() {
    }

    public Booking(String flightId1, String flightId2, String type){
        this.flightId1 = flightId1;
        this.flightId2 = flightId2;
        this.type=type;
        this.passengers = new ArrayList<Passenger>();


    }
    public Booking(String flightId, String type){
        this.flightId1=flightId;
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

    public String getFlightId1() {
        return flightId1;
    }

    public void setFlightId1(String flightId1) {
        this.flightId1 = flightId1;
    }

    public String getFlightId2() {
        return flightId2;
    }

    public void setFlightId2(String flightId2) {
        this.flightId2 = flightId2;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

}