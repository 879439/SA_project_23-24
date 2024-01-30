package org.example.flightservice.models;

import java.util.ArrayList;
import java.util.List;

public class TicketInfo {
    private List<Passenger> passengers;
    private String bookingId;
    private double price;
    private String type;
    private List<String> departures = new ArrayList<>();
    private List<String> arrivals = new ArrayList<>();
    private List<String> dates = new ArrayList<>();
    private List<String> times = new ArrayList<>();

    public TicketInfo() {
    }

    public TicketInfo(String type) {
        this.type = type;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDepartures() {
        return departures;
    }

    public void setDepartures(List<String> departures) {
        this.departures = departures;
    }

    public List<String> getArrivals() {
        return arrivals;
    }

    public void setArrivals(List<String> arrivals) {
        this.arrivals = arrivals;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
