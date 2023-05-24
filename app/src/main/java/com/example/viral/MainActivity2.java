package com.example.viral;

import static com.example.viral.MyApplicationClass.d;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.locationreviewproject.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        try {
            String jsonString = loadJSONFromAsset();
            Json json = new Gson().fromJson(jsonString, new TypeToken<Json>() {
            }.getType());
            Log.e("PPP", "onCreate: " + jsonString);
            d.addAll(json.getData());
            Log.e("JJJ", "onCreate: " + d.get(0).getDate());
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(e);
        }

    }

    public String loadJSONFromAsset() {
        String json;

        try {
            InputStream is = getResources().openRawResource(R.raw.message);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;

    }
}