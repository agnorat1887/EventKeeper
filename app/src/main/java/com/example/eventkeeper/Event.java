package com.example.eventkeeper;

import com.google.gson.annotations.SerializedName;

public class Event {
    private String name, date, groupid, description, type;
    private boolean isPrivate;
    @SerializedName
            ("event_address") Address address;


    public Event(String name, String date, String groupid, String description, String type, boolean isPrivate, Address address) {
        this.name = name;
        this.date = date;
        this.groupid = groupid;
        this.description = description;
        this.type = type;
        this.isPrivate = isPrivate;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getGroupid() {
        return groupid;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public Address getAddress() {
        return address;
    }
}