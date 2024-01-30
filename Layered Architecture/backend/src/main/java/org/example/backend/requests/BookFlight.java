package org.example.backend.requests;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.ArrayList;

public class BookFlight {
    @Valid
    private ArrayList<Person> people;
    @NotNull
    private String type;
    @NotNull
    private String flightId1;
    private String flightId2;
    private String discountCode;


    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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


    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }
}
