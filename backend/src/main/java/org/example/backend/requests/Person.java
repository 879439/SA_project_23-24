package org.example.backend.requests;

import org.example.backend.Annotations.ValidDate;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

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
    private String email;
    @NotNull
    private String seat;
    private ArrayList<String> foods;

    public String getFirstname() {
        return firstname;
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

    public ArrayList<String> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<String> foods) {
        this.foods = foods;
    }
}
