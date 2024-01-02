package org.example.backend.models;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

public class Seat {
    private String name;
    private boolean isAvailable;

    public Seat(String name, boolean isAvailable) {
        this.name = name;
        this.isAvailable = isAvailable;
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
