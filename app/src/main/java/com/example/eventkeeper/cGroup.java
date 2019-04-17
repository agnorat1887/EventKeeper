package com.example.eventkeeper;

import com.google.gson.annotations.SerializedName;

public class cGroup {
    private String name, description;
    @SerializedName("location_address") Address address;
    @SerializedName("creator_id")String _id;

    public cGroup(String name, String description, String _id, Address address) {
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
