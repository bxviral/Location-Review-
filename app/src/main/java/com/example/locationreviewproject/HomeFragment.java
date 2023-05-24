package com.example.locationreviewproject;

import static com.example.locationreviewproject.MyApplicationClass.a1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    static HomeFragment homeFragment;

    public static HomeFragment getFragment() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return homeFragment;

    }

    FloatingActionButton floatingButton;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Log.e("KKK", "onPause:of home frag is called");
        recyclerView = view.findViewById(R.id.rvAddress);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        floatingButton = view.findViewById(R.id.floatingButton);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GoogleMapActivity.class);
                startActivity(intent);
            }
        });
        a1 = Pref.readListFromPref(getContext());
        if(a1 == null){
            a1 = new ArrayList<>();
        }
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), a1);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), a1);
        recyclerView.setAdapter(recyclerViewAdapter);
        Log.e("KKK", "onResume:of home frag is called");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.e("KKK", "onPause:of home frag is called");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("KKK", "onDestroy:of home frag is called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("KKK", "onStop:of home frag is called");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("KKK", "onStart:of home frag ");


    }

}