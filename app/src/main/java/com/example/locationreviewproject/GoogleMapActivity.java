package com.example.locationreviewproject;

import static com.example.locationreviewproject.MyApplicationClass.a1;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import java.io.IOException;
import java.util.ArrayList;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    com.google.android.gms.location.LocationRequest locationRequest;
    int counter;
    MarkerOptions markerOptions;
    SharedPreferences.Editor myEdit;
    ArrayList<Address> arr_adr;
    double latitude, longitude;
    ArrayList<AddressData> a2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map2);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
        //counterG = 0;
        counter = 0;
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000L)
                .setWaitForAccurateLocation(true)
                .setMinUpdateIntervalMillis(2000)
                .setMaxUpdateDelayMillis(2000)
                .build();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        Log.e("TAG", "onMapReady: is called ");
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(GoogleMapActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e("ZZZ", "getCurrentLocation is called ");
            if (isGPSEnabled()) {
                Log.e("TAG", "isGPSEnabled: is called ");
                Log.e("ZZZ", "fetchMyLocation is called ");
                fetchMyLocation();
            } else {
                Log.e("TAG", "else: is called ");
                Log.e("ZZZ", "turnOnGPS is called ");
                turnOnGPS();
            }
        } else {
            Log.e("TAG", "request not allowed ");
            Log.e("ZZZ", "requestPermissions is called ");
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager;
        boolean isEnabled;
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }

    private void fetchMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(GoogleMapActivity.this)
                .requestLocationUpdates(locationRequest,
                        new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                super.onLocationResult(locationResult);

                                LocationServices.getFusedLocationProviderClient(GoogleMapActivity.this)
                                        .removeLocationUpdates(this);

                                if (locationResult.getLocations().size() > 0) {

                                    int index = locationResult.getLocations().size() - 1;
                                    final double[] latitude = {locationResult.getLocations().get(index).getLatitude()};
                                    final double[] longitude = {locationResult.getLocations().get(index).getLongitude()};

                                    Log.e("KKK", "longitude and latitude is " + latitude[0] + " " + longitude[0]);
                                    LatLng latLng = new LatLng(latitude[0], longitude[0]);
//                                    MarkerOptions markerOptions2 = new MarkerOptions().position(latLng).title("Im here")
//                                            .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_location_five)).anchor(0.5f, 0.0f);
                                    markerOptions = new MarkerOptions().position(latLng).title("Im here").anchor(0.5f, 0.0f);
                                    googleMap.addMarker(markerOptions);
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
//                                    sharedPreferences = getSharedPreferences("Google", MODE_PRIVATE);
//                                    myEdit = sharedPreferences.edit();
                                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                        @Override
                                        public void onMapClick(@NonNull LatLng latLng) {

                                            googleMap.addMarker(new MarkerOptions().position(latLng).title("jii"));
                                            Geocoder geocoder = new Geocoder(GoogleMapActivity.this);
                                            try {
                                                arr_adr = (ArrayList<Address>) geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                                                Log.e("LLL", "ADR IS " + arr_adr.get(0).getAddressLine(0));
                                                Log.e("LLL", "Latlng IS " + latLng.latitude + " " + latLng.longitude);
                                                a2 = new ArrayList<>();
                                                a2.add(new AddressData(arr_adr.get(0).getAddressLine(0), null, latLng.latitude, latLng.longitude, 0,null,false));


                                                for (int i = 0; i < a1.size(); i++) {
                                                    Log.e("UUU", "size is: " + a1.size());
                                                    Log.e("UUU", "address is: " + a1.get(i).getAddress());
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            }
                        }, Looper.getMainLooper());
    }

    private void turnOnGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response = task.getResult(ApiException.class);
                Log.e("TAG", "turnOnGPS: " + response);
                Toast.makeText(GoogleMapActivity.this, "GPS is already turned on", Toast.LENGTH_SHORT).show();
                Log.e("ZZZ", "LocationSettingsStatusCodes.RESOLUTION_REQUIRED: TRY " + LocationSettingsStatusCodes.RESOLUTION_REQUIRED);
                fetchMyLocation();
            } catch (ApiException e) {

                switch (e.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.e("ZZZ", "LocationSettingsStatusCodes.RESOLUTION_REQUIRED:: " + LocationSettingsStatusCodes.RESOLUTION_REQUIRED);
                        Log.e("ZZZ", "counter is " + counter);
                        if (counter == 0) {
                            try {
                                counter++;
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                Log.e("ZZZ", "INSIDE TRY  " + LocationSettingsStatusCodes.RESOLUTION_REQUIRED);
                                resolvableApiException.startResolutionForResult(GoogleMapActivity.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.e("ZZZ", "LocationSettingsStatusCodes.RESOLUTION_REQUIRED: SECOND CASE" + LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE);
                        //Device does not have location
                        break;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("TAG", "inside onRequestPermissionsResult: is  called");
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {
                    Log.e("TAG", "inside onRequestPermissionsResult: isGPSEnabled() is  called");
                    getCurrentLocation();

                } else {
                    Log.e("TAG", "inside onRequestPermissionsResult: turnOnGPS() is  called");
                    turnOnGPS();
                }
            } else {
                Log.e("TAG", "inside onRequestPermissionsResult: requestPermissions() is  called");
                androidx.appcompat.app.AlertDialog.Builder delDailog = new AlertDialog.Builder(GoogleMapActivity.this);

                delDailog.setTitle("Delete");
                delDailog.setIcon(R.drawable.ic_launcher_background);
                delDailog.setMessage("PLEASE ALLOW");
                delDailog.setPositiveButton("Allow", (dialogInterface, i) -> {
                    //for yes
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivityForResult(intent, 2);
                });
                delDailog.setNegativeButton("Cancel", (dialogInterface, i) -> {
                    //for No
                    Toast.makeText(GoogleMapActivity.this, "ITEM NOT DELETED", Toast.LENGTH_SHORT).show();
                });
                delDailog.show();
            }
        }
    }

//    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
//
//        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
//        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
//        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        vectorDrawable.draw(canvas);
//        return BitmapDescriptorFactory.fromBitmap(bitmap);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "inside onActivityResult is  called");
        if (requestCode == 2) {
            Log.e("TAG", "requestCode is  called" + requestCode);
            getCurrentLocation();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("QQQ", "onBackPressed: " + a2.get(0).getAddress());
        Log.e("QQQ", "onBackPressed: " + a2.get(0).getLatitude());
        Log.e("QQQ", "onBackPressed: " + a2.get(0).getLongitude());
        int index = a1.size();
        a1.add(new AddressData(a2.get(0).getAddress(), null, a2.get(0).getLatitude(), a2.get(0).getLongitude(), 0,null,false));
        a1.get(index).setIndex(index);
        Pref.writeListInPref(GoogleMapActivity.this,a1);


    }
}