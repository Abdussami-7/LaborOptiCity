package com.example.laboropticity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShortsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShortsFragment extends Fragment implements OnMapReadyCallback {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    String uID = mUser.getUid();
    DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
//    String userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uID).getKey();
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users");
//    String workerRef = FirebaseDatabase.getInstance().getReference().child("Workers").child(uID).getKey();
    DatabaseReference workerRef = FirebaseDatabase.getInstance().getReference().child("Workers");
    DatabaseReference jobPostsRef = FirebaseDatabase.getInstance().getReference().child("Job Posts");
    private GoogleMap gMap;
    LinearLayout mapContainer;
    LinearLayout fragContainer;
    SupportMapFragment mapFragment;
    ListView workerListView;

    // Workers Data Lists from showWorkers()
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<String> skillList = new ArrayList<String>();
    ArrayList<String> locationList = new ArrayList<String>();
    ArrayList<LatLng> coordList = new ArrayList<LatLng>();
    ArrayList<String> wUidList = new ArrayList<String>();

//    int flag = 0;
    LatLng uLoc;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShortsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShortsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShortsFragment newInstance(String param1, String param2) {
        ShortsFragment fragment = new ShortsFragment();
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
//        View v = inflater.inflate(R.layout.fragment_shorts, container, false);
//        SwitchMaterial mapSwitch = v.findViewById(R.id.jobToolBarSwitch);
//        mapSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b){
//                    flag = 1;
//                }
//                else{
//                    flag = 0;
//                }
//            }
//        });
//        if (flag == 0){
//            return inflater.inflate(R.layout.fragment_shorts, container, false);
//        }
//        else {
//            return inflater.inflate(R.layout.activity_map, container, false);
//        }
        return inflater.inflate(R.layout.fragment_shorts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Tiral (comp centre)
        /*
        if (mRole != null){
            Toast.makeText(getContext(), mRole, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "idREf is null", Toast.LENGTH_SHORT).show();
        }
         */

        Toolbar toolbar = view.findViewById(R.id.jobToolBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");

        mapContainer = view.findViewById(R.id.mapContainer);
        fragContainer = view.findViewById(R.id.fragContainer);
//        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.mapFragment);
//        mapFragment.getMapAsync(ShortsFragment.this);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(uID)){
//                    Toast.makeText(getContext(), "User - "+uID, Toast.LENGTH_SHORT).show();
                    for (DataSnapshot user: snapshot.getChildren()) {
                        if (user.getKey().toString().equals(uID)){
                            double uLat = (double) user.child("Latitude").getValue(double.class);
                            double uLng = (double) user.child("Longitude").getValue(double.class);
                            uLoc = new LatLng(uLat, uLng);
                        }
                    }

                    SwitchMaterial mapSwitch = view.findViewById(R.id.jobToolBarSwitch);
//                    mapSwitch.setChecked(true);
                    showWorkers(view);
                    mapSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                            if (!checked){
                                showWorkers(view);
                                showList();
                            }
                            else {
//                                Toast.makeText(getContext(), "Switch is on", Toast.LENGTH_SHORT).show();
//                                showMap();
                                workerListView.setVisibility(View.GONE);
                                fragContainer.setVisibility(View.VISIBLE);
                                mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
                                /*
                                if (mapFragment != null){
                                    Toast.makeText(getContext(), "map fragg not null", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getContext(), "Map frag is null", Toast.LENGTH_SHORT).show();
                                }
                                 */
                                mapFragment.getMapAsync(ShortsFragment.this);
                                /*
                                mapFragment.getMapAsync(ShortsFragment.this::onMapReady);
                                Intent i = new Intent(getContext(), MapTestActivity.class);
                                startActivity(i);

                                double dist = calculateDistance(19.167230389005137, 73.02720386402444, 19.1904293024682, 73.02335161092714);
                                Toast.makeText(getContext(), "Dist: "+dist, Toast.LENGTH_SHORT).show();
                                 */
                            }
                        }
                    });
                    
                    
                }
                else {
//                    Toast.makeText(getContext(), "Worker - "+uID, Toast.LENGTH_SHORT).show();
                    showJobs(view);
                    SwitchMaterial jAcceptedSwitch = view.findViewById(R.id.jobToolBarSwitch);
                    jAcceptedSwitch.setText("See Accepted Jobs");
                    jAcceptedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                            if (!checked){
                                showJobs(view);
                            }
                            else {
                                showAcceptedJobs(view);
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void showJobs(View view){
        ArrayList<String> titleList = new ArrayList<String>();
        ArrayList<String> descList = new ArrayList<String>();
        ArrayList<String> skillList = new ArrayList<String>();
        ArrayList<String> cityList = new ArrayList<String>();
        ArrayList<String> amountList = new ArrayList<String>();
        ArrayList<String> uUidList = new ArrayList<String>();
        ArrayList<String> jPReqIdList = new ArrayList<String>();

        ListView jobListView = (ListView) view.findViewById(R.id.workerListView);

        jobPostsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                titleList.clear();
                descList.clear();
                skillList.clear();
                cityList.clear();
                amountList.clear();
                uUidList.clear();
                jPReqIdList.clear();

                for (DataSnapshot uIDs: snapshot.getChildren()){ // Unique uIDs
                    for (DataSnapshot job: uIDs.getChildren()){ // Jobs related to specific users

//                                    if(title != null && desc != null && skill != null && city != null && amount != null){
                        if (job.child("title").getValue(String.class) != null && job.child("desc").getValue(String.class) != null && job.child("skill").getValue(String.class) != null && job.child("city").getValue(String.class) != null && job.child("amount").getValue(String.class) != null && job.child("status").getValue(String.class).equals("Pending")){

                            String title = job.child("title").getValue(String.class);
                            String desc = job.child("desc").getValue(String.class);
                            String skill = job.child("skill").getValue(String.class);
                            String city = job.child("city").getValue(String.class);
                            String amount = job.child("amount").getValue(String.class);
                            String uUid = uIDs.getKey();
                            String jPReqId = job.getKey();

                            titleList.add(title);
                            descList.add(desc);
                            skillList.add(skill);
                            cityList.add(city);
                            amountList.add(amount);
                            uUidList.add(uUid);
                            jPReqIdList.add(jPReqId);
                        }
                    }
                }

                JobListAdapter myJobAdapter = new JobListAdapter(getActivity(), titleList, descList, skillList, cityList, amountList, uUidList, jPReqIdList);
                jobListView.setAdapter(myJobAdapter);
                myJobAdapter.notifyDataSetChanged();

                jobListView.setDivider(null);
                jobListView.setDividerHeight(5);
                jobListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String clickedJUuid = ((TextView) view.findViewById(R.id.jUUidTV)).getText().toString();
                        String clickedJReqId = ((TextView) view.findViewById(R.id.jPReqIdTV)).getText().toString();
                        Intent jPDetailsIntent = new Intent(getContext(), JobPostDetails.class);
                        jPDetailsIntent.putExtra("CLICKED_JP_UUID", clickedJUuid);
                        jPDetailsIntent.putExtra("CLICKED_JP_RID", clickedJReqId);
                        startActivity(jPDetailsIntent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void showAcceptedJobs(View view){
        ArrayList<String> titleList = new ArrayList<String>();
        ArrayList<String> descList = new ArrayList<String>();
        ArrayList<String> skillList = new ArrayList<String>();
        ArrayList<String> cityList = new ArrayList<String>();
        ArrayList<String> amountList = new ArrayList<String>();
        ArrayList<String> uUidList = new ArrayList<String>();
        ArrayList<String> jPReqIdList = new ArrayList<String>();

        ListView jobListView = (ListView) view.findViewById(R.id.workerListView);

        jobPostsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                titleList.clear();
                descList.clear();
                skillList.clear();
                cityList.clear();
                amountList.clear();
                uUidList.clear();
                jPReqIdList.clear();

                for (DataSnapshot uIDs: snapshot.getChildren()){ // Unique uIDs
                    for (DataSnapshot job: uIDs.getChildren()){ // Jobs related to specific users

//                                    if(title != null && desc != null && skill != null && city != null && amount != null){
                        if (job.child("title").getValue(String.class) != null && job.child("desc").getValue(String.class) != null && job.child("skill").getValue(String.class) != null && job.child("city").getValue(String.class) != null && job.child("amount").getValue(String.class) != null && job.child("status").getValue(String.class).equals("Accepted") && job.child("Accepted By").getValue(String.class).equals(uID)){

                            String title = job.child("title").getValue(String.class);
                            String desc = job.child("desc").getValue(String.class);
                            String skill = job.child("skill").getValue(String.class);
                            String city = job.child("city").getValue(String.class);
                            String amount = job.child("amount").getValue(String.class);
                            String uUid = uIDs.getKey();
                            String jPReqId = job.getKey();

                            titleList.add(title);
                            descList.add(desc);
                            skillList.add(skill);
                            cityList.add(city);
                            amountList.add(amount);
                            uUidList.add(uUid);
                            jPReqIdList.add(jPReqId);
                        }
                    }
                }

                JobListAdapter myJobAdapter = new JobListAdapter(getActivity(), titleList, descList, skillList, cityList, amountList, uUidList, jPReqIdList);
                jobListView.setAdapter(myJobAdapter);
                myJobAdapter.notifyDataSetChanged();

                jobListView.setDivider(null);
                jobListView.setDividerHeight(5);
                jobListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String clickedJUuid = ((TextView) view.findViewById(R.id.jUUidTV)).getText().toString();
                        String clickedJReqId = ((TextView) view.findViewById(R.id.jPReqIdTV)).getText().toString();
                        Intent jPDetailsIntent = new Intent(getContext(), JobPostDetails.class);
                        jPDetailsIntent.putExtra("CLICKED_JP_UUID", clickedJUuid);
                        jPDetailsIntent.putExtra("CLICKED_JP_RID", clickedJReqId);
                        startActivity(jPDetailsIntent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void showWorkers(View view){
        workerListView = (ListView) view.findViewById(R.id.workerListView);
        SearchView search = (SearchView) view.findViewById(R.id.search);

//        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                String searchkey = search.getQuery().toString();
//                return true;
//            }
//        });
        String searchkey="";
        if (search.getQuery() != null){
            searchkey = search.getQuery().toString();
        }

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
                for (DataSnapshot snapshot : datasnapshot.getChildren()){
//                    Toast.makeText(getActivity(), snapshot.child("Name").getValue().toString(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), "Im Here", Toast.LENGTH_SHORT).show();
//                    if (snapshot.child("Name").getValue() != null && snapshot.child("Location").getValue() != null){
//                        if(snapshot.child("Skill").getValue().toString() != null && snapshot.child("Skill").getValue().toString().equalsIgnoreCase(searchkey)){
                    if(snapshot.child("Name").getValue() != null && snapshot.child("Skill").getValue() != null && snapshot.child("City").getValue() != null){
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
                WorkerListAdapter myWorkerAdapter = new WorkerListAdapter(getActivity(), nameList, skillList, locationList, wUidList);
                workerListView.setAdapter(myWorkerAdapter);
                myWorkerAdapter.notifyDataSetChanged();

                workerListView.setDivider(null);
                workerListView.setDividerHeight(5);
                workerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                        Toast.makeText(getContext(), "ID is: "+view.getId(), Toast.LENGTH_SHORT).show();
                        TextView clickedTV = view.findViewById(R.id.wUids);
                        String clickedUid = clickedTV.getText().toString();
//                        Toast.makeText(getContext(), clickedUid, Toast.LENGTH_SHORT).show();
                        Intent hireIntent = new Intent(getContext(), hireworker.class);
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

    private void showMap() {
        mapContainer.removeAllViews();
        mapContainer.setVisibility(View.VISIBLE);

        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.mapContainer, mapFragment)
                    .commit();
            mapFragment.getMapAsync(this);
        }

        workerListView.setVisibility(View.GONE);
    }

    private void showList() {
//        mapContainer.setVisibility(View.GONE);
        fragContainer.setVisibility(View.GONE);
        workerListView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
//        LatLng loc = new com.google.android.gms.maps.model.LatLng(55.6761,12.5683);
        googleMap.addMarker(new MarkerOptions().position(uLoc).title("My Loc"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uLoc, 12));

        for (LatLng wLoc : coordList) {
            gMap = googleMap;
            int pos = coordList.indexOf(wLoc);
            double dist = calculateDistance(wLoc.latitude, wLoc.longitude, uLoc.latitude, uLoc.longitude);
            double roundedDist = Math.round(dist*100.0)/100.0;
            if (dist < 5){
                Marker m = gMap.addMarker(new MarkerOptions().position(wLoc).title(nameList.get(pos)).snippet(roundedDist+" km"));
                m.setTag(wUidList.get(pos));
//                m.showInfoWindow();
            }
        }

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                marker.showInfoWindow();
                if (marker.getTitle().equals("My Loc")){
                    return true;
                }
                String clickedUid = marker.getTag().toString();
                Intent hireIntent = new Intent(getContext(), hireworker.class);
                hireIntent.putExtra("CLICKED_UID", clickedUid);
                try {
                    marker.showInfoWindow();
                    Thread.sleep(1000);
                    startActivity(hireIntent);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
        });
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }

}