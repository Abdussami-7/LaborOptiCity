package com.example.laboropticity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeWorkerList extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    String uID = mUser.getUid();
    ListView workerListView;
    DatabaseReference workerRef = FirebaseDatabase.getInstance().getReference().child("Workers");
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<String> skillList = new ArrayList<String>();
    ArrayList<String> locationList = new ArrayList<String>();
    ArrayList<LatLng> coordList = new ArrayList<LatLng>();
    ArrayList<String> wUidList = new ArrayList<String>();
    String skill = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_worker_list);
        showWorkers();
    }

    public void showWorkers(){
        workerListView = (ListView) findViewById(R.id.homeWorkerListView);

        nameList = new ArrayList<String>();
        skillList = new ArrayList<String>();
        locationList = new ArrayList<String>();
        coordList = new ArrayList<LatLng>();
        wUidList = new ArrayList<String>();

//        WorkerListAdapter myWorkerAdapter = new WorkerListAdapter(getActivity(), nameList, locationList);
//        workerListView.setAdapter(myWorkerAdapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Workers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//                Toast.makeText(getActivity(), "Im Here", Toast.LENGTH_SHORT).show();
                nameList.clear();
                skillList.clear();
                locationList.clear();
                wUidList.clear();
                skill = getIntent().getStringExtra("SKILL");

                for (DataSnapshot snapshot : datasnapshot.getChildren()){
//                    Toast.makeText(getApplicationContext(), snapshot.child("Name").getValue().toString(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "Im Here", Toast.LENGTH_SHORT).show();
//                    if (snapshot.child("Name").getValue() != null && snapshot.child("Location").getValue() != null){
//                        if(snapshot.child("Skill").getValue().toString() != null && snapshot.child("Skill").getValue().toString().equalsIgnoreCase(searchkey)){
                    if(snapshot.child("Name").getValue() != null && snapshot.child("Skill").getValue(String.class).equals(skill) && snapshot.child("City").getValue() != null){
//                        Toast.makeText(HomeWorkerList.this, "Not Null", Toast.LENGTH_SHORT).show();
                        nameList.add(snapshot.child("Name").getValue().toString());
                        skillList.add(snapshot.child("Skill").getValue().toString());
                        locationList.add(snapshot.child("City").getValue().toString());
//                      locationList.add(snapshot.child("Location").getValue().toString());
                        wUidList.add(snapshot.getKey().toString());
                        double lat = (double) snapshot.child("Latitude").getValue();
                        double lng = (double) snapshot.child("Longitude").getValue();
                        LatLng wCoord = new LatLng(lat, lng);
                        coordList.add(wCoord);
                    }

//                    }
                }
                WorkerListAdapter myWorkerAdapter = new WorkerListAdapter(getApplicationContext(), nameList, skillList, locationList, wUidList);
                workerListView.setAdapter(myWorkerAdapter);
                myWorkerAdapter.notifyDataSetChanged();

                workerListView.setDivider(null);
                workerListView.setDividerHeight(5);
                workerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

//                    @Overrides
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Toast.makeText(getContext(), "ID is: "+view.getId(), Toast.LENGTH_SHORT).show();
                        TextView clickedTV = view.findViewById(R.id.wUids);
                        String clickedUid = clickedTV.getText().toString();
//                        Toast.makeText(getContext(), clickedUid, Toast.LENGTH_SHORT).show();
                        Intent hireIntent = new Intent(getApplicationContext(), hireworker.class);
                        hireIntent.putExtra("CLICKED_UID", clickedUid);
                        startActivity(hireIntent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}