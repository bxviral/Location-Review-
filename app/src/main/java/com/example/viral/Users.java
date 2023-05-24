package com.example.viral;

import java.util.ArrayList;

public class Users {
    String user_id, user, userImageURL;
    ArrayList<Photos> photos;

    public Users(String user_id, String user, String userImageURL, ArrayList<Photos> photos) {
        this.user_id = user_id;
        this.user = user;
        this.userImageURL = userImageURL;
        this.photos = photos;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public ArrayList<Photos> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photos> photos) {
        this.photos = photos;
    }
}