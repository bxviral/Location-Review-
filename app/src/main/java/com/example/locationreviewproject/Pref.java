package com.example.locationreviewproject;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Pref {

    public static void writeListInPref(Context context, ArrayList<AddressData> data) {
        String jsonString = new Gson().toJson(data);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("key", jsonString);
        editor.apply();


    }

    public static ArrayList<AddressData> readListFromPref(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonString = sharedPreferences.getString("key", null);

        return new Gson().fromJson(jsonString, new TypeToken<ArrayList<AddressData>>() {
        }.getType());


    }
}
