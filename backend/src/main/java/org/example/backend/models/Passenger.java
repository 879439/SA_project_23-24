package org.example.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
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
    private String Seat;
    private ArrayList<String> foods;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return Seat;
    }

    public void setSeat(String seat) {
        Seat = seat;
    }

    public ArrayList<String> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<String> foods) {
        this.foods = foods;
    }
}
