package com.example.laboropticity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laboropticity.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class register extends AppCompatActivity {

    Button RegBTN;
    TextInputEditText EmailRtv,PasswordRtv;

    FirebaseAuth mAuth;

    ProgressBar Pbar;

    TextView LoginNow;
    ActivityMainBinding binding;
    TextInputEditText RegName, RegPhone, RegPass;
    //    AutoCompleteTextView RegGender;
    Intent intent=new Intent();

    // Location Part
    FusedLocationProviderClient locClient;
    private final static int REQUEST_CODE = 100;
    double longitude, latitude;
    String city, address, country;

    HashMap<String, Object> map = new HashMap<>();

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);


        getLastLocation();
        mAuth= FirebaseAuth.getInstance();

        EmailRtv=findViewById(R.id.EmailRtv);
        PasswordRtv=findViewById(R.id.PasswordRtv);
        RegBTN=findViewById(R.id.RegBTN);
        Pbar=findViewById(R.id.Pbar);
        LoginNow=findViewById(R.id.LoginNow);

        RegName = findViewById(R.id.UserName);
        RegPhone = findViewById(R.id.UserPhone);
        RegPass = findViewById(R.id.Password);

        locClient = LocationServices.getFusedLocationProviderClient(this);


//        RegGender = findViewById(R.id.autoCompleteTextView);


        // Get a reference to the string array that we just created
        String[] languages = getResources().getStringArray(R.array.Role);

// Create an ArrayAdapter and pass the required parameters: context, drop down layout, and the array.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, languages);

// Get a reference to the AutoCompleteTextView
        AutoCompleteTextView autocompleteTV = findViewById(R.id.autoCompleteTextView);

// Set the adapter to the AutoCompleteTextView
        autocompleteTV.setAdapter(arrayAdapter);


        RegBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pbar.setVisibility(View.VISIBLE);
                String name, email, phone, password, confirm, role;

                email= String.valueOf(EmailRtv.getText());
                confirm=String.valueOf(PasswordRtv.getText());

                name = String.valueOf(RegName.getText());
                phone = String.valueOf(RegPhone.getText());
                password = String.valueOf(RegPass.getText());
                role = String.valueOf(autocompleteTV.getText());

//                getLastLocation();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirm) || TextUtils.equals(role, "Role"))
                {
                    Toast.makeText(register.this, "All Fields Are Mandatory!", Toast.LENGTH_LONG).show();
                    return;
                } else if (!password.equals(confirm)) {
                    Toast.makeText(register.this, "Passwords Must Match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Pbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(register.this, "Account Created",
                                            Toast.LENGTH_SHORT).show();

                                    String pathEmail = email.replace("@", "");
                                    pathEmail = pathEmail.replace(".", "");
//                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("Name", name);
                                    map.put("Email", email);
                                    map.put("Phone", phone);

                                    map.put("Latitude", latitude);
                                    map.put("Longitude", longitude);
                                    map.put("City", city);
                                    map.put("Address", address);
                                    map.put("Country", country);

                                    // Set Display Name
                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = "";
                                    if(user != null){
                                        uid = user.getUid();
                                    }
                                    else {
                                        Toast.makeText(register.this, "UID not found!", Toast.LENGTH_SHORT).show();
                                    }
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(name).build();

                                    user.updateProfile(profileUpdates)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Log.d("DisplayName", "User profile updated.");
                                                    }
                                                }
                                            });

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

                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
        LoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(),login.class);
                String role = autocompleteTV.getText().toString();
                if (role.equals("User")){
//                    String name = String.valueOf(RegName.getText());
                    intent.setClass(getApplicationContext(), login.class);
//                    intent.putExtra("Reg_Name", name);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void getLastLocation(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
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
}