package com.example.laboropticity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    CardView plumberCard, electricianCard, cleanerCard, laundryCard, painterCard, carpenterCard;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public HomeFragment() {
        // Required empty public constructor
    }

//    DatabaseReference mDatabase, workersRef, usersRef;
//    FirebaseAuth mAuth;
//    ImageView loc;
//    String uID = "";
//    FirebaseUser currentUser;
//    HashMap<String, Object> map = new HashMap<>();
//    private final static int REQUEST_CODE = 100;
//    FusedLocationProviderClient locClient;
//    double longitude, latitude;
//    String city, address, country;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView greet = view.findViewById(R.id.greet);
        String dName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
//        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
        greet.setText("Hello "+dName+",");

        plumberCard = view.findViewById(R.id.plumberCard);
        electricianCard = view.findViewById(R.id.electricianCard);
        cleanerCard = view.findViewById(R.id.cleanerCard);
        laundryCard = view.findViewById(R.id.laundryCard);
        painterCard = view.findViewById(R.id.painterCard);
        carpenterCard = view.findViewById(R.id.carpenterCard);

        plumberCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeWorkers = new Intent(getContext(), HomeWorkerList.class);
                homeWorkers.putExtra("SKILL", "Plumber");
                startActivity(homeWorkers);
            }
        });
        electricianCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeWorkers = new Intent(getContext(), HomeWorkerList.class);
                homeWorkers.putExtra("SKILL", "Electrician");
                startActivity(homeWorkers);
            }
        });
        cleanerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeWorkers = new Intent(getContext(), HomeWorkerList.class);
                homeWorkers.putExtra("SKILL", "Cleaner");
                startActivity(homeWorkers);
            }
        });
        laundryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeWorkers = new Intent(getContext(), HomeWorkerList.class);
                homeWorkers.putExtra("SKILL", "Laundry");
                startActivity(homeWorkers);
            }
        });
        painterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeWorkers = new Intent(getContext(), HomeWorkerList.class);
                homeWorkers.putExtra("SKILL", "Painter");
                startActivity(homeWorkers);
            }
        });
        carpenterCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeWorkers = new Intent(getContext(), HomeWorkerList.class);
                homeWorkers.putExtra("SKILL", "Carpenter");
                startActivity(homeWorkers);
            }
        });

        /*
        locClient = LocationServices.getFusedLocationProviderClient(getActivity());
        getLastLocation();
//        loc = view.findViewById(R.id.loc);
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        workersRef = FirebaseDatabase.getInstance().getReference().child("Workers");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        uID = currentUser.getUid();

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if(uID.equals(dataSnapshot.getKey())){
//                        usersRef.child(dataSnapshot.getKey()).updateChildren(map);
                                usersRef.child(uID).updateChildren(map);
                            }
                            else {
//                                Toast.makeText(getActivity(), "no match", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
         */
    }

    /*
    private void getLastLocation(){
        if(ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if(location != null){
                                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                                List<Address> addresses = null;

                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    latitude = addresses.get((0)).getLatitude();
                                    longitude = addresses.get((0)).getLongitude();
                                    address = addresses.get(0).getAddressLine(0);
                                    city = addresses.get(0).getLocality();
                                    country = addresses.get(0).getCountryName();

                                    map.put("Latitude", latitude);
                                    map.put("Longitude", longitude);
                                    map.put("City", city);
                                    map.put("Address", address);
                                    map.put("Country", country);
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
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else {
                Toast.makeText(getContext(), "Location Permission Required!", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
         */
}