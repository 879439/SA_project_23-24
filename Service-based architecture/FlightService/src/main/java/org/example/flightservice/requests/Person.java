package org.example.flightservice.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.example.flightservice.annotations.ValidDate;

;

public class Person {
    @NotNull
    private String firstname;
    @NotNull
    private String lastname;
    @NotNull
    @ValidDate
    private String birthday;
    @NotNull
    private String sex;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String seat;
    @NotNull
    private Boolean isAdult;
    private String returnSeat;
    private String food;
    private String returnFood;

    public String getFirstname() {
        return firstname;
    }

    public Boolean isAdult() {
        return isAdult;
    }

    public void setAdult(Boolean isAdult) {
        this.isAdult = isAdult;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }



    public String getReturnSeat() {
        return returnSeat;
    }

    public void setReturnSeat(String returnSeat) {
        this.returnSeat = returnSeat;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getReturnFood() {
        return returnFood;
    }

    public void setReturnFood(String returnFood) {
        this.returnFood = returnFood;
    }
}
