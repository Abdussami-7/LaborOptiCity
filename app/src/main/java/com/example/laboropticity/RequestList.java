package com.example.laboropticity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RequestList extends AppCompatActivity {

    ListView reqsListView;
    ArrayList<String> reqTitleList, reqDescList, reqAmtList, uUidList, reqIdList;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    String uid = mUser.getUid();
    DatabaseReference reqReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        showReqsList();
    }

    private void showReqsList(){

        reqsListView = findViewById(R.id.reqsListView);

        reqTitleList = new ArrayList<String>();
        reqDescList = new ArrayList<String>();
        reqAmtList = new ArrayList<String>();
        uUidList = new ArrayList<String>();
        reqIdList = new ArrayList<String>();

        reqReference = FirebaseDatabase.getInstance().getReference().child("Job Requests");
//        Toast.makeText(getContext(), "path: "+reqReference.getKey(), Toast.LENGTH_SHORT).show();
        reqReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { //workerNodes
                if (snapshot.hasChild(uid)){

//                    Toast.makeText(RequestList.this, "Loading Requests..."+uid, Toast.LENGTH_SHORT).show();

                    reqTitleList.clear();
                    reqDescList.clear();
                    reqAmtList.clear();
                    uUidList.clear();
                    reqIdList.clear();

                    DataSnapshot workerNode = snapshot.child(uid);
                    for (DataSnapshot userNodes : workerNode.getChildren()){
                        for (DataSnapshot userReqs: userNodes.getChildren()) {
                            if (userReqs.child("title").getValue(String.class) != null && userReqs.child("desc").getValue(String.class) != null && userReqs.child("amt").getValue(String.class) != null){
//                                Toast.makeText(RequestList.this, "Adding list", Toast.LENGTH_SHORT).show();
                                reqTitleList.add(userReqs.child("title").getValue(String.class));
                                reqDescList.add(userReqs.child("desc").getValue(String.class));
                                reqAmtList.add(userReqs.child("amt").getValue(String.class));
                                uUidList.add(userNodes.getKey());
                                reqIdList.add(userReqs.getKey());
                            }
                        }
                    }
//                    Toast.makeText(RequestList.this, reqTitleList.get(0), Toast.LENGTH_SHORT).show();

                    ReqListAdapter myReqListAdapter = new ReqListAdapter(RequestList.this, reqTitleList, reqDescList, reqAmtList, uUidList, reqIdList);
                    reqsListView.setAdapter(myReqListAdapter);
                    myReqListAdapter.notifyDataSetChanged();
                    reqsListView.setDivider(null);
                    reqsListView.setDividerHeight(5);

                    reqsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String clickedUuid = ((TextView) view.findViewById(R.id.jReqUUidTV)).getText().toString();
                            String clickedReqId = ((TextView) view.findViewById(R.id.jReqIdTV)).getText().toString();
                            Intent reqDetailsIntent = new Intent(getApplicationContext(), RequestDetails.class);
                            reqDetailsIntent.putExtra("CLICKED_UUID", clickedUuid);
                            reqDetailsIntent.putExtra("CLICKED_RID", clickedReqId);
                            startActivity(reqDetailsIntent);
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "No Requests!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RequestList.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}