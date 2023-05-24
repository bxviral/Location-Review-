package com.example.viral;

import java.util.ArrayList;

public class Json {
    ArrayList<Data> data;

    public Json(ArrayList<Data> data) {
        this.data = data;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}
