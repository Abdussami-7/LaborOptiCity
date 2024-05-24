package com.example.laboropticity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
    DatabaseReference workersRef = FirebaseDatabase.getInstance().getReference().child("Workers");
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    String uid = mUser.getUid();
    String role = "";


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LibraryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView p_phone=view.findViewById(R.id.p_phone);
        TextView p_address=view.findViewById(R.id.p_address);
        TextView p_email=view.findViewById(R.id.p_email);
        TextView p_name=view.findViewById(R.id.p_name);
        Button logoutbtn=view.findViewById(R.id.Logoutbtn);
        String dName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        p_name.setText(dName);




        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(uid)){
                    role = "User";
                    for (DataSnapshot user : snapshot.getChildren()) {
                        if (user.getKey().equals(uid)){
                            if (user.child("Name").getValue() != null && user.child("Phone").getValue() != null && user.child("Address").getValue() != null && user.child("Email").getValue()!=null){
                                p_email.setText(p_email.getText()+user.child("Email").getValue(String.class));
                                p_phone.setText(p_phone.getText()+user.child("Phone").getValue(String.class));
                                p_address.setText(p_address.getText()+user.child("Address").getValue(String.class));
                            }
                        }
                    }


                }
                else {
                    role = "Worker";
                    workersRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot wSnapshot) {
                            for (DataSnapshot worker : wSnapshot.getChildren()) {
                                if (worker.getKey().equals(uid)){
                                    if (worker.child("Name").getValue() != null && worker.child("Phone").getValue() != null && worker.child("Address").getValue() != null && worker.child("Email").getValue()!=null){
                                        p_email.setText(p_email.getText()+worker.child("Email").getValue(String.class));
                                        p_phone.setText(p_phone.getText()+worker.child("Phone").getValue(String.class));
                                        p_address.setText(p_address.getText()+worker.child("Address").getValue(String.class));
//                                        Toast.makeText(getContext(), "hello", Toast.LENGTH_SHORT).show();
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




        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAUth = FirebaseAuth.getInstance();
                mAUth.signOut();

                Intent logOutIntent = new Intent(getContext(), login.class);
                startActivity(logOutIntent);
                getActivity().finish();
            }
        });
    }
}