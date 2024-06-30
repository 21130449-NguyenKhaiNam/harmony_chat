package com.example.harmony_chat.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Profile implements Serializable {
    private long id;
    private User user;
    private String username;
    private String avatar;
    private LocalDate dob;
    private Gender gender;
    private Country country;
    private String phone;

    public Profile(long id, String username, String avatar) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
    }

    public Profile() {

    }

    // Getter và Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void inject(Profile other) {
        this.id = other.id;
        this.user = other.user;
        this.username = other.username;
        this.avatar = other.avatar;
        this.dob = other.dob;
        this.gender = other.gender;
        this.country = other.country;
        this.phone = other.phone;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", user=" + user +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", dob=" + dob +
                ", gender=" + gender +
                ", country=" + country +
                ", phone='" + phone + '\'' +
                '}';
    }
}
