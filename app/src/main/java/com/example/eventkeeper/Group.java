package com.example.eventkeeper;

import com.google.gson.annotations.SerializedName;

public class Group {
    private String name, description, _id;
    @SerializedName
   ("location_address") Address address;

    public Group(String name, String description, String _id, Address address) {
        this.name = name;
        this.description = description;
        this._id = _id;
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

    public String get_id() {
        return _id;
    }
}
