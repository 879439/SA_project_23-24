package org.example.backend.requests;

import jakarta.validation.constraints.NotBlank;
import org.example.backend.models.Food;
import org.example.backend.models.Seat;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class CreateFlight {
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
    private String size;

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
