package com.example.eventkeeper;

public class Address {
    private String street, city, state;
    private int zip_code;


    public Address(String street, String city, String state, int zip_code) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip_code = zip_code;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getZipCode() {
        return zip_code;
    }
}
