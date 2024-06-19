package com.example.appnghenhac.model;

import java.util.Date;

public class User {
    private Date birthDate;
    private String fullname;
    private String gender;
    private String phoneNumber;

    public User(Date birthDate, String fullname, String gender, String phoneNumber) {
        this.birthDate = birthDate;
        this.fullname = fullname;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
