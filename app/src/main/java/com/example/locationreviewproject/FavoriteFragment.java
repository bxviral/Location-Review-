package com.example.locationreviewproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FavoriteFragment extends Fragment {
    static FavoriteFragment favoriteFragment;

    public static FavoriteFragment getFragment() {
        if (favoriteFragment == null) {
            favoriteFragment = new FavoriteFragment();
        }
        return favoriteFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);



        return view;
    }
}