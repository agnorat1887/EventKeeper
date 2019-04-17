package com.example.eventkeeper;

public class GroupItem {
    private String mGroup;
    private String mLocaiton;
    private String mid;

    public GroupItem(String Group, String Location, String id) {
        mGroup = Group;
        mLocaiton = Location;
        mid = id;
    }

    public String getGroup() {
        return mGroup;
    }

    public String getLocation() {
        return mLocaiton;
    }

    public String getId() {
        return mid;
    }
}
