package com.example.laboropticity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class hireworker extends AppCompatActivity {

    TextView hire_name,hire_pnumber,hire_skill,hire_address;
    Button hire_btn;
    DatabaseReference workerRef = FirebaseDatabase.getInstance().getReference().child("Workers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hireworker);

        hire_name = findViewById(R.id.hire_name);
        hire_pnumber = findViewById(R.id.hire_pnum);
        hire_skill = findViewById(R.id.hire_skill);
        hire_address = findViewById(R.id.hire_address);
        hire_btn = findViewById(R.id.hire_btn);

        Intent i = getIntent();
        String c_uid = i.getStringExtra("CLICKED_UID");

        workerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datasnap: snapshot.getChildren()) {
                    if (datasnap.getKey().toString().equals(c_uid)){
                        if (datasnap.child("Name").getValue() != null && datasnap.child("Phone").getValue() != null && datasnap.child("Skill").getValue() != null && datasnap.child("Address").getValue() != null){
                            hire_name.setText(hire_name.getText()+datasnap.child("Name").getValue().toString());
                            hire_pnumber.setText(hire_pnumber.getText()+datasnap.child("Phone").getValue().toString());
                            hire_skill.setText(hire_skill.getText()+datasnap.child("Skill").getValue().toString());
                            hire_address.setText(hire_address.getText()+datasnap.child("Address").getValue().toString());
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        hire_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reqIntent = new Intent(getApplicationContext(), JobRequestForm.class);
                reqIntent.putExtra("TARGET_W_UID", c_uid);
                startActivity(reqIntent);
            }
        });
    }
}