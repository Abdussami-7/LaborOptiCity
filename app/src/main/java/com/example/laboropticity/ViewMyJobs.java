package com.example.laboropticity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewMyJobs extends AppCompatActivity {
    ListView jobsListView;
    ArrayList<String> jobTitleList, jobDescList, jobSkillList, jobCityList, jobAmtList, wUidList, jobIdList;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    String uid = mUser.getUid();
    DatabaseReference jobReference;
    SwitchMaterial jobsSwitch;
    String status = "Pending";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_jobs);

        jobsSwitch = findViewById(R.id.reqToolBarSwitch);

        showReqsList();
        jobsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (!checked){
                    status = "Pending";
                    showReqsList();
                }
                else {
                    status = "Accepted";
                    showReqsList();
                }
            }
        });

    }

    private void showReqsList(){

        jobsListView = findViewById(R.id.jobsListView);

        jobTitleList = new ArrayList<String>();
        jobDescList = new ArrayList<String>();
        jobSkillList = new ArrayList<String>();
        jobCityList = new ArrayList<String>();
        jobAmtList = new ArrayList<String>();
        wUidList = new ArrayList<String>();
        jobIdList = new ArrayList<String>();

        jobReference = FirebaseDatabase.getInstance().getReference().child("Job Posts");
//        Toast.makeText(getContext(), "path: "+reqReference.getKey(), Toast.LENGTH_SHORT).show();
        jobReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { //workerNodes

                jobTitleList.clear();
                jobDescList.clear();
                jobSkillList.clear();
                jobCityList.clear();
                jobAmtList.clear();
                wUidList.clear();
                jobIdList.clear();

                for (DataSnapshot userNode:snapshot.getChildren()) {
                    if (userNode.getKey().equals(uid)){
                        for (DataSnapshot userReqs: userNode.getChildren()) {
                            if (userReqs.child("title").getValue(String.class) != null && userReqs.child("desc").getValue(String.class) != null && userReqs.child("skill").getValue(String.class) != null && userReqs.child("city").getValue(String.class) != null && userReqs.child("amount").getValue(String.class) != null && userReqs.child("status").getValue(String.class).equals(status)){
//                                Toast.makeText(RequestList.this, "Adding list", Toast.LENGTH_SHORT).show();
                                jobTitleList.add(userReqs.child("title").getValue(String.class));
                                jobDescList.add(userReqs.child("desc").getValue(String.class));
                                jobSkillList.add(userReqs.child("skill").getValue(String.class));
                                jobCityList.add(userReqs.child("city").getValue(String.class));
                                jobAmtList.add(userReqs.child("amt").getValue(String.class));
                                wUidList.add(userReqs.child("Accepted By").getValue(String.class));
                                jobIdList.add(userReqs.getKey());
                            }
                        }
                    }
                }
                if (jobTitleList.toArray().length == 0){
                    Toast.makeText(getApplicationContext(), "No Requests!", Toast.LENGTH_LONG).show();
                }
                else {
//                    ReqListAdapter myReqListAdapter = new ReqListAdapter(ViewMyJobs.this, jobTitleList, jobDescList, jobAmtList, wUidList, jobIdList);
                    JobListAdapter myJobListAdapter = new JobListAdapter(ViewMyJobs.this, jobTitleList, jobDescList, jobSkillList, jobCityList, jobAmtList, wUidList, jobIdList);
                    jobsListView.setAdapter(myJobListAdapter);
                    myJobListAdapter.notifyDataSetChanged();
                    jobsListView.setDivider(null);
                    jobsListView.setDividerHeight(5);

                    jobsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String clickedWUid = ((TextView) view.findViewById(R.id.jUUidTV)).getText().toString();
                            String clickedJobId = ((TextView) view.findViewById(R.id.jPReqIdTV)).getText().toString();
                            Intent reqDetailsIntent = new Intent(getApplicationContext(), JobPostDetails.class);
                            reqDetailsIntent.putExtra("CLICKED_JP_UUID", clickedWUid);
                            reqDetailsIntent.putExtra("CLICKED_JP_RID", clickedJobId);
                            startActivity(reqDetailsIntent);
                        }
                    });
                }

//                    Toast.makeText(RequestList.this, reqTitleList.get(0), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewMyJobs.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}