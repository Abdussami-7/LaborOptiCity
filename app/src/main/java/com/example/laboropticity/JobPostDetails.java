package com.example.laboropticity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class JobPostDetails extends AppCompatActivity {
    TextView JPDetNameTV, JPDetPhoneTV, JPDetAddressTV;
    Button JPDetCallBtn, JPDetDirectionsBtn, JPDetAcceptBtn;
    String cJPUUid, cJPReqId;
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference workersRef = FirebaseDatabase.getInstance().getReference().child("Workers");
    DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference().child("Job Posts");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    String uid = mUser.getUid();
    String role = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_post_details);

        JPDetNameTV = findViewById(R.id.JPDetNameTV);
        JPDetPhoneTV = findViewById(R.id.JPDetPhoneTV);
        JPDetAddressTV = findViewById(R.id.JPDetAddressTV);

        JPDetCallBtn = findViewById(R.id.JPDetCallBtn);
        JPDetDirectionsBtn = findViewById(R.id.JPDetDirectionsBtn);
        JPDetAcceptBtn = findViewById(R.id.JPDetAcceptBtn);

        cJPUUid = getIntent().getStringExtra("CLICKED_JP_UUID");
        cJPReqId = getIntent().getStringExtra("CLICKED_JP_RID");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(uid)){
                    role = "User";
                    JPDetAcceptBtn.setText("Work Done");
                    workersRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot wSnapshot) {
                            for (DataSnapshot user : wSnapshot.getChildren()) {
                                if (user.getKey().equals(cJPUUid)){
                                    if (user.child("Name").getValue() != null && user.child("Phone").getValue() != null && user.child("Address").getValue() != null){
                                        JPDetNameTV.setText(JPDetNameTV.getText()+user.child("Name").getValue(String.class));
                                        JPDetPhoneTV.setText(JPDetPhoneTV.getText()+user.child("Phone").getValue(String.class));
                                        JPDetAddressTV.setText(JPDetAddressTV.getText()+user.child("Address").getValue(String.class));

                                        JPDetCallBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Uri phone_no = Uri.parse("tel:"+user.child("Phone").getValue(String.class));
                                                Intent callIntent = new Intent(Intent.ACTION_DIAL, phone_no);
                                                startActivity(callIntent);
                                            }
                                        });

                                        JPDetDirectionsBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                String lat = user.child("Latitude").getValue().toString();
                                                String lng = user.child("Longitude").getValue().toString();
                                                String destination = lat+","+lng;

                                                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destination);
                                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                                mapIntent.setPackage("com.google.android.apps.maps");

                                                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                                    startActivity(mapIntent);
                                                } else {
                                                    Toast.makeText(JobPostDetails.this, "Map is not available in your device", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                        JPDetAcceptBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
//                                                reqsRef.child(uid).child(cUUid).child(cReqId).child("status").setValue("Accepted");
//                                                Intent acceptIntent = new Intent(getApplicationContext(), AcceptRequest.class);
//                                                acceptIntent.putExtra("CURRENT_REQ_ID", cReqId);
//                                                startActivity(acceptIntent);

                                                IntentIntegrator integrator = new IntentIntegrator(JobPostDetails.this);
                                                integrator.setCaptureActivity(MyCaptureActivity.class);
                                                integrator.setPrompt("Scan QR Code");
                                                integrator.setBeepEnabled(true);
                                                integrator.setOrientationLocked(false);
                                                integrator.initiateScan();
                                            }
                                        });
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else {
                    role = "Worker";
                    usersRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot wSnapshot) {
                            for (DataSnapshot user : wSnapshot.getChildren()) {
                                if (user.getKey().equals(cJPUUid)){
                                    if (user.child("Name").getValue() != null && user.child("Phone").getValue() != null && user.child("Address").getValue() != null){
                                        JPDetNameTV.setText(JPDetNameTV.getText()+user.child("Name").getValue(String.class));
                                        JPDetPhoneTV.setText(JPDetPhoneTV.getText()+user.child("Phone").getValue(String.class));
                                        JPDetAddressTV.setText(JPDetAddressTV.getText()+user.child("Address").getValue(String.class));

                                        JPDetCallBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Uri phone_no = Uri.parse("tel:"+user.child("Phone").getValue(String.class));
                                                Intent callIntent = new Intent(Intent.ACTION_DIAL, phone_no);
                                                startActivity(callIntent);
                                            }
                                        });

                                        JPDetDirectionsBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                String lat = user.child("Latitude").getValue().toString();
                                                String lng = user.child("Longitude").getValue().toString();
                                                String destination = lat+","+lng;

                                                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + destination);
                                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                                mapIntent.setPackage("com.google.android.apps.maps");

                                                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                                                    startActivity(mapIntent);
                                                } else {
                                                    Toast.makeText(JobPostDetails.this, "Map is not available in your device", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                        JPDetAcceptBtn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                jobsRef.child(cJPUUid).child(cJPReqId).child("status").setValue("Accepted");
                                                jobsRef.child(cJPUUid).child(cJPReqId).child("Accepted By").setValue(uid);
                                                Intent acceptIntent = new Intent(getApplicationContext(), AcceptRequest.class);
                                                acceptIntent.putExtra("CURRENT_REQ_ID", cJPReqId);
                                                startActivity(acceptIntent);
                                            }
                                        });
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // Handle canceled scan
                Toast.makeText(this, "Scanning Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                String scannedReqID = result.getContents();
                String currentReqID = cJPReqId;
                if (scannedReqID.equals(currentReqID)) {
                    // User ID validation successful, proceed with desired action
                    Toast.makeText(this, "QR code scanned successfully!", Toast.LENGTH_SHORT).show();
                    // Add your logic here to handle successful QR code scan
                    if (role.equals("User")){
//                        jobsRef.child(cJPUUid).child(uid).child(currentReqID).removeValue()
                        jobsRef.child(uid).child(currentReqID).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(JobPostDetails.this, "Work completed successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(JobPostDetails.this, "Scanning Failed, try again", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                } else {
                    // User ID validation failed, display error message
                    Toast.makeText(this, "Unauthorized QR code", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}