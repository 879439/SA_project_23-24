package org.example.backend.requests;


import javax.validation.constraints.NotBlank;

import org.example.backend.Annotations.ValidDate;
import org.example.backend.models.Food;

import java.util.ArrayList;

public class CreateFlight {
    @NotBlank
    private String company;
    @NotBlank
    private String departure;
    @NotBlank
    private String arrival;
    @NotBlank
    @ValidDate
    private String date;
    @NotBlank
    private String departure_time;
    @NotBlank
    private String arrival_time;
    private ArrayList<Food> foods;
    private String size;
    private String discountCode;

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
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

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
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
