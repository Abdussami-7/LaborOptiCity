package com.example.laboropticity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.laboropticity.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
//    private FirebaseAuth mAuth;
//    private DatabaseReference mDatabase, workersRef, usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        locClient = LocationServices.getFusedLocationProviderClient(this);
//        getLastLocation();
//        map.put("Latitude", latitude);
//        map.put("Longitude", longitude);
//        map.put("City", city);
//        map.put("Address", address);
//        map.put("Country", country);

//        mDatabase = FirebaseDatabase.getInstance().getReference();



//        usersRef = FirebaseDatabase.getInstance().getReference().child(uID);
        /*
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    usersRef.updateChildren(map);
                }
                else {
                    Toast.makeText(MainActivity.this, "User doesnt exist!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, snapshot.getKey().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
         */

        /*
        if(role.equals("User")){
//                                        FirebaseDatabase.getInstance().getReference().child("Users").child(pathEmail).updateChildren(map);
            FirebaseDatabase.getInstance().getReference().child("Users").child(uid).updateChildren(map);
        } else if (role.equals("Worker")) {
//                                        FirebaseDatabase.getInstance().getReference().child("Workers").child(pathEmail).updateChildren(map);
            FirebaseDatabase.getInstance().getReference().child("Workers").child(uid).updateChildren(map);
            intent.setClass(getApplicationContext(), worker.class);
//                                        intent.putExtra("User_Mail", pathEmail);
            startActivity(intent);
            finish();
        }
         */


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id=item.getItemId();
            if(id==R.id.home)
            {
                replaceFragment(new HomeFragment());
            }
            else if(id==R.id.shorts)
            {
                replaceFragment(new ShortsFragment());
            }
            else if(id==R.id.subscriptions)
            {
                replaceFragment(new SubFragment());
            }
            else if(id==R.id.library)
            {
                replaceFragment(new ProfileFragment());
            }

            return true;
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}