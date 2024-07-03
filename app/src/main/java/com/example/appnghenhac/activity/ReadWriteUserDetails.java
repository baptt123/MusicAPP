package com.example.appnghenhac.activity;

public class ReadWriteUserDetails {
    public String birthDate, gender, phoneNumber;

    public ReadWriteUserDetails() {

    }

    public ReadWriteUserDetails(String textGender, String textBirthDate, String textPhoneNum) {
        this.gender = textGender;
        this.birthDate = textBirthDate;
        this.phoneNumber = textPhoneNum;

    }
}
