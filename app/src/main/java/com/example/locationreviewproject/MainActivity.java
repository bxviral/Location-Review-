package com.example.locationreviewproject;

import static com.example.locationreviewproject.MyApplicationClass.a1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView bnvHome, bnvFav, toolbarImage;
    TextView navHome, navFav;

    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        bnvHome = findViewById(R.id.bnvHome);
        bnvFav = findViewById(R.id.bnvFav);
        toolbarImage = findViewById(R.id.toolbarImage);
        navHome = findViewById(R.id.navHome);
        navFav = findViewById(R.id.navFav);
        toolbarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, HomeFragment.getFragment());
        ft.commit();
        bnvHome.setImageResource(R.drawable.home_filled);
        bnvFav.setImageResource(R.drawable.baseline_favorite_24);

        bnvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, HomeFragment.getFragment());
                ft.commit();
                bnvHome.setImageResource(R.drawable.home_filled);
                bnvFav.setImageResource(R.drawable.baseline_favorite_24);
            }
        });
        bnvFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, FavoriteFragment.getFragment());
                ft.commit();
                bnvHome.setImageResource(R.drawable.baseline_home_24);
                bnvFav.setImageResource(R.drawable.fav_filled);
            }
        });
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentTransaction ft = fm.beginTransaction();
//                ft.replace(R.id.container, HomeFragment.getFragment());
//                ft.commit();
//                bnvHome.setImageResource(R.drawable.home_filled);
//                bnvFav.setImageResource(R.drawable.baseline_favorite_24);
//                drawerLayout.closeDrawer(GravityCompat.START);
                a1.clear();
                Pref.writeListInPref(MainActivity.this, a1);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        navFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.container, HomeFragment.getFragment());
                ft.commit();
                bnvHome.setImageResource(R.drawable.baseline_home_24);
                bnvFav.setImageResource(R.drawable.fav_filled);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });


    }
}