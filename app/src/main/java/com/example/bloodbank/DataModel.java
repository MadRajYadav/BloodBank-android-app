package com.example.bloodbank;

public class DataModel {
    String name, number, address, state, city, country, pinCode, Dob, last_donation_date, habits,blood_group,donor_type,member_since;
    int points,eye;

    public DataModel(String name, String number, String address, String state, String city, String country, String pinCode, String dob, String last_donation_date, String habits, String blood_group, String donor_type, String member_since, int points, int eye) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.state = state;
        this.city = city;
        this.country = country;
        this.pinCode = pinCode;
        Dob = dob;
        this.last_donation_date = last_donation_date;
        this.habits = habits;
        this.blood_group = blood_group;
        this.donor_type = donor_type;
        this.member_since = member_since;
        this.points = points;
        this.eye = eye;
    }
}
