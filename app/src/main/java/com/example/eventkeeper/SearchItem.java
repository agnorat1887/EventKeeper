package com.example.eventkeeper;

public class SearchItem {

    private String mGroup;
    private String mLocation;

    public SearchItem(String Group, String Location) {
        mGroup = Group;
        mLocation = Location;
    }

    public String getGroup() {
        return mGroup;
    }

    public String getLocation() {
        return mLocation;
    }

}
