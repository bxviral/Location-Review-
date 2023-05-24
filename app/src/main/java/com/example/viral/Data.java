package com.example.viral;

import java.util.ArrayList;

public class Data {
    String date;
    ArrayList<Users> users;

    public Data(String date, ArrayList<Users> users) {
        this.date = date;
        this.users = users;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Users> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Users> users) {
        this.users = users;
    }
}
