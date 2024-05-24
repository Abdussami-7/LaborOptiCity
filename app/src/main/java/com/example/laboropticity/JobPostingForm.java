package com.example.laboropticity;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobPostingForm extends AppCompatActivity {
    String[] skills;
    ArrayAdapter<String> skillsAdapter;
    AutoCompleteTextView skillsATV;
    Button postBtn;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    String uid = mUser.getUid();
    DatabaseReference jReference, uReference, reqReference;
    String city;

    TextInputEditText jTitle;
    TextInputEditText jDesc;
    AutoCompleteTextView jSkillsATV;
    TextInputEditText jAmount;
    String title, desc, skill, amount, jId, status;
    long mCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_posting_form);
//        postJob();
        jTitle = findViewById(R.id.jTitle);
        jDesc = findViewById(R.id.jDesc);
        jSkillsATV = findViewById(R.id.jSkill);
        jAmount = findViewById(R.id.jAmount);

        skills = getResources().getStringArray(R.array.Skill);
        skillsAdapter = new ArrayAdapter<String>(JobPostingForm.this, R.layout.dropdown_item, skills);
//        skillsATV = view.findViewById(R.id.jSkill);

        jSkillsATV.setAdapter(skillsAdapter);

    /* trial
    DatabaseReference tempRef = FirebaseDatabase.getInstance().getReference().child("Job Posts").child("5NS1uKF8esNUEZx17dPX9Mxoc782");
    tempRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                if(dataSnapshot.getKey().equals("5NS1uKF8esNUEZx17dPX9Mxoc782_1")){
                    dataSnapshot.
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
//        tempRef.child("5NS1uKF8esNUEZx17dPX9Mxoc782_2").setValue
    // end
     */

        postBtn = findViewById(R.id.jPostBtn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser mUser = mAuth.getCurrentUser();
                uid = mUser.getUid();
                checkEmpty();
                jReference = FirebaseDatabase.getInstance().getReference().child("Job Posts").child(uid);

                //trial
                /*
                jReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mCount = snapshot.getChildrenCount();

//                        id = uid+"_"+mCount;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                jId = uid+"_"+mCount;
                 */

                DatabaseReference cityRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("City");
                cityRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Toast.makeText(getContext(), snapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                        city = snapshot.getValue(String.class);
                        JobData jData = new JobData(title, desc, skill, amount, city, status);
                        jReference.push().setValue(jData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                JobData jData = new JobData(title, desc, skill, amount, city, status);
//                Toast.makeText(getContext(), "Data: "+jData, Toast.LENGTH_SHORT).show();
//                jReference.child(jId).setValue(jData);
//                jReference.push().setValue(jData);
            }
        });
    }

    private void postJob(){

        jTitle = findViewById(R.id.jTitle);
        jDesc = findViewById(R.id.jDesc);
        jSkillsATV = findViewById(R.id.jSkill);
        jAmount = findViewById(R.id.jAmount);

        skills = getResources().getStringArray(R.array.Skill);
        skillsAdapter = new ArrayAdapter<String>(JobPostingForm.this, R.layout.dropdown_item, skills);
//        skillsATV = view.findViewById(R.id.jSkill);

        jSkillsATV.setAdapter(skillsAdapter);

    /* trial
    DatabaseReference tempRef = FirebaseDatabase.getInstance().getReference().child("Job Posts").child("5NS1uKF8esNUEZx17dPX9Mxoc782");
    tempRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                if(dataSnapshot.getKey().equals("5NS1uKF8esNUEZx17dPX9Mxoc782_1")){
                    dataSnapshot.
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
//        tempRef.child("5NS1uKF8esNUEZx17dPX9Mxoc782_2").setValue
    // end
     */

        postBtn = findViewById(R.id.jPostBtn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser mUser = mAuth.getCurrentUser();
                uid = mUser.getUid();
                checkEmpty();
                jReference = FirebaseDatabase.getInstance().getReference().child("Job Posts").child(uid);

                //trial
                /*
                jReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mCount = snapshot.getChildrenCount();

//                        id = uid+"_"+mCount;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                jId = uid+"_"+mCount;
                 */

                DatabaseReference cityRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("City");
                cityRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Toast.makeText(getContext(), snapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                        city = snapshot.getValue(String.class);
                        JobData jData = new JobData(title, desc, skill, amount, city, status);
                        jReference.push().setValue(jData);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                JobData jData = new JobData(title, desc, skill, amount, city, status);
//                Toast.makeText(getContext(), "Data: "+jData, Toast.LENGTH_SHORT).show();
//                jReference.child(jId).setValue(jData);
//                jReference.push().setValue(jData);
            }
        });
    }

    private void checkEmpty(){
//        String title, desc, skill, amount, id;

        title = String.valueOf(jTitle.getText());
        desc = String.valueOf(jDesc.getText());
        skill = String.valueOf(jSkillsATV.getText());
        amount = String.valueOf(jAmount.getText());
        status = "Pending";
//        id = jReference.push().getKey();
//        id = uid+"_data";
//        city = "Kurla";
//        Toast.makeText(getContext(), "fine", Toast.LENGTH_SHORT).show();

        /*
        DatabaseReference cityRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("City");
        cityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getContext(), snapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
//                city = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
         */

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(skill) || TextUtils.isEmpty(amount)){
            Toast.makeText(getApplicationContext(), "All the fields are mandatory!", Toast.LENGTH_SHORT).show();
            return;
        }


//        Toast.makeText(getContext(), cityRef.getKey(), Toast.LENGTH_SHORT).show();
//        System.out.println(cityRef.getKey());
//        JobData jData = new JobData(title, desc, skill, amount, city, id);
//        jReference.child(id).setValue(jData);
    }
}

