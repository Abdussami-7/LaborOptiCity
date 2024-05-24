package com.example.laboropticity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubFragment extends Fragment {

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
    String role = "";
    View v;
    ListView reqsListView;
    ArrayList reqTitleList, reqDescList, reqAmtList;

    Button redirect, viewMyReqs, viewMyJobs;
    Intent redirectIntent;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SubFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubFragment newInstance(String param1, String param2) {
        SubFragment fragment = new SubFragment();
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
        return inflater.inflate(R.layout.fragment_subscriptions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        redirect = view.findViewById(R.id.redirectBtn);
        viewMyReqs = view.findViewById(R.id.viewReqsBtn);
        viewMyJobs = view.findViewById(R.id.viewJobsBtn);

        uReference = FirebaseDatabase.getInstance().getReference().child("Users");
        uReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(uid)){
                    role = "User";
                    redirect.setText("Post a Job");
                    redirect.setVisibility(View.VISIBLE);
                    viewMyReqs.setVisibility(View.VISIBLE);
                    viewMyJobs.setVisibility(View.VISIBLE);

                    redirect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            redirectIntent = new Intent(getContext(), JobPostingForm.class);
                            startActivity(redirectIntent);
                        }
                    });

                    viewMyReqs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            redirectIntent = new Intent(getContext(), ViewMyRequests.class);
                            startActivity(redirectIntent);
                        }
                    });

                    viewMyJobs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            redirectIntent = new Intent(getContext(), ViewMyJobs.class);
                            startActivity(redirectIntent);
                        }
                    });
                }
                else {
                    role = "Worker";
                    redirect.setText("View Requests");
                    redirect.setVisibility(View.VISIBLE);
                    redirect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            redirectIntent = new Intent(getContext(), RequestList.class);
                            startActivity(redirectIntent);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}