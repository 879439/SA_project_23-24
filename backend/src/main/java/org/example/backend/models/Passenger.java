package org.example.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "passengers")
public class Passenger {
    @Id
    private String id;
    private String userId;
    private String firstname;
    private String lastname;
    private String birthday;
    private String sex;
    private String email;
    private String seat;
    private String returnSeat;
    private boolean isAdult;
    private List<String> foods;
    private List<String> returnFoods;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAdult() {
        return isAdult;
    }

    public void setAdult(boolean adult) {
        isAdult = adult;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public List<String> getFoods() {
        return foods;
    }

    public void setFoods(List<String> foods) {
        this.foods = foods;
    }

    public String getReturnSeat() {
        return returnSeat;
    }

    public void setReturnSeat(String returnSeat) {
        this.returnSeat = returnSeat;
    }

    public List<String> getReturnFoods() {
        return returnFoods;
    }

    public void setReturnFoods(List<String> returnFoods) {
        this.returnFoods = returnFoods;
    }
}
