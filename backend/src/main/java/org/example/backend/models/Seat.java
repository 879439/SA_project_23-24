package org.example.backend.models;

public class Seat {
    private String name;
    private boolean isAvailable;
    private double price;

    public Seat(String name, boolean isAvailable, double price) {
        this.name = name;
        this.isAvailable = isAvailable;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
}
