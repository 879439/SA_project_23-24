package org.example.backend.models;

import java.util.ArrayList;
import java.util.List;

public class Seat {
    private String name;
    private boolean isAvailable;
    private double price;
    private double childrenPrice;

    public Seat(String name, boolean isAvailable, double price, double childrenPrice) {
        this.name = name;
        this.isAvailable = isAvailable;
        this.price = price;
        this.childrenPrice = childrenPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getChildrenPrice() {
        return childrenPrice;
    }

    public void setChildrenPrice(double childrenPrice) {
        this.childrenPrice = childrenPrice;
    }
}
