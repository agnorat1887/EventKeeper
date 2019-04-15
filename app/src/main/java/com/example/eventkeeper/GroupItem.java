package com.example.eventkeeper;

public class GroupItem {
    private String mGroup;
    private String mLocaiton;

    public GroupItem(String Group, String Location) {
        mGroup = Group;
        mLocaiton = Location;
    }

    public String getGroup() {
        return mGroup;
    }

    public String getLocation() {
        return mLocaiton;
    }
}
