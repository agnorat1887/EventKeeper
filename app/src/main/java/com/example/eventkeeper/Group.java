package com.example.eventkeeper;

import com.google.gson.annotations.SerializedName;

public class Group {
    private String name, description;
    @SerializedName
   ("location_address") Address address;

    public Group(String name, String description, Address address) {
        this.name = name;
        this.description = description;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Address getAddress() {
        return address;
    }
}
