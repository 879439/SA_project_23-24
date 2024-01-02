package org.example.backend.models;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

@Document(collection = "flights")
public class Flight {

    @Id
    private String id;
    @NotBlank
    private String company;
    @NotBlank
    private String departure;
    @NotBlank
    private String arrival;
    @NotBlank
    private String date;
    @NotBlank
    private String time;
    private ArrayList<Food> foods;
    private ArrayList<Seat> seats;
    private ArrayList<Booking> bookings;

    public Flight(String company, String departure, String arrival, String date, String time) {
        this.company = company;
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public ArrayList<Seat> getSeats() {
        return seats;
    }

    public void setSeats(ArrayList<Seat> seats) {
        this.seats = seats;
    }
}
