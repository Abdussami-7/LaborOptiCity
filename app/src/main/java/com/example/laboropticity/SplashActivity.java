package com.example.laboropticity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    /*
    FusedLocationProviderClient locClient;
    private final static int REQUEST_CODE = 100;
    double longitude, latitude;
    String city, address, country;

    HashMap<String, Object> map = new HashMap<>();
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //getSupportActionBar().hide();

        ImageView splash = findViewById(R.id.splash_img);
        Animation splashAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_anim);
        splash.startAnimation(splashAnim);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iHome = new Intent(getApplicationContext(), login.class);
                startActivity(iHome);
                finish();
            }
        }, 3000);
    }

    /*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        locClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        map.put("Latitude", latitude);
        map.put("Longitude", longitude);
        map.put("City", city);
        map.put("Address", address);
        map.put("Country", country);

    }

    private void getLastLocation(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location != null){
                                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                                List<Address> addresses = null;

                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    latitude = addresses.get((0)).getLatitude();
                                    longitude = addresses.get((0)).getLongitude();
                                    address = addresses.get(0).getAddressLine(0);
                                    city = addresses.get(0).getLocality();
                                    country = addresses.get(0).getCountryName();
                                }
                                catch (IOException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }
        else {
            askPermission();
        }
    }

    private void askPermission(){
        ActivityCompat.requestPermissions(register.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else {
                Toast.makeText(this, "Location Permission Required!", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
     */
}