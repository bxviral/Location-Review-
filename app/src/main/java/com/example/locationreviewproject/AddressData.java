package com.example.locationreviewproject;

import java.io.Serializable;

public class AddressData implements Serializable {
    String address,description,path;
    double latitude,longitude;
    int index;
    boolean selected;

    public AddressData(String address, String description, double latitude, double longitude, int index,String path,boolean selected) {
        this.address = address;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.index = index;
        this.path = path;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
