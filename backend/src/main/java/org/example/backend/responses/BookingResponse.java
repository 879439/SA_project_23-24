package org.example.backend.responses;

import org.example.backend.models.Booking;

public class BookingResponse {
    private Booking booking;
    private String departure;
    private String arrival;
    private String date;
    private String time;
    private String travelClass;

    public BookingResponse(Booking booking, String departure, String arrival, String date, String time, String travelClass) {
        this.booking = booking;
        this.departure = departure;
        this.arrival = arrival;
        this.date = date;
        this.time = time;
        this.travelClass = travelClass;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
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

    public String getTravelClass() {
        return travelClass;
    }

    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }
}
