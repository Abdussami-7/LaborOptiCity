package com.example.laboropticity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class JobRequestForm extends AppCompatActivity {

    TextInputEditText jReqTitle, jReqDesc, jReqAmount;
    Button jReqPostBtn;
    String title, desc, amt, uid, wid;
    FirebaseAuth mAuth;
    DatabaseReference reqRef;
    HashMap<String, Object> map = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_request_form);

        jReqTitle = findViewById(R.id.jReqTitle);
        jReqDesc = findViewById(R.id.jReqDesc);
        jReqAmount = findViewById(R.id.jReqAmount);
        jReqPostBtn = findViewById(R.id.jReqPostBtn);

        jReqPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are mandatory!", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(JobRequestForm.this, title, Toast.LENGTH_SHORT).show();
                    mAuth = FirebaseAuth.getInstance();
                    FirebaseUser mUser = mAuth.getCurrentUser();
                    uid = mUser.getUid();
                    wid = getIntent().getStringExtra("TARGET_W_UID");
                    reqRef = FirebaseDatabase.getInstance().getReference().child("Job Requests");

                    map.put("title", title);
                    map.put("desc", desc);
                    map.put("amt", amt);
                    map.put("status", "Pending");

                    reqRef.child(wid).child(uid).push().updateChildren(map);
                    Toast.makeText(JobRequestForm.this, "Request Posted!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isEmpty(){
//        String title, desc, amt;
        title = String.valueOf(jReqTitle.getText());
        desc = String.valueOf(jReqDesc.getText());
        amt = String.valueOf(jReqAmount.getText());

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(amt)){
            return true;
        }
        return false;
    }
}