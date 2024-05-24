package com.example.laboropticity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    String[] skills;
    ArrayAdapter<String> skillsAdapter;
    AutoCompleteTextView skillsATV;
    Button postBtn;
    FirebaseAuth mAuth;
    DatabaseReference jReference;
    String city;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
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
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        skills = getResources().getStringArray(R.array.Skill);
        skillsAdapter = new ArrayAdapter<String>(requireContext(), R.layout.dropdown_item, skills);
        skillsATV = view.findViewById(R.id.jSkill);

        skillsATV.setAdapter(skillsAdapter);

        postBtn = view.findViewById(R.id.jPostBtn);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmpty(view);
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser mUser = mAuth.getCurrentUser();
                String uid = mUser.getUid();
                jReference = FirebaseDatabase.getInstance().getReference().child("Job Posts").child(uid);
                checkEmpty(view);
            }
        });

    }

    private void checkEmpty(View view){
        String title, desc, skill, amount, id;

        EditText jTitle = view.findViewById(R.id.jTitle);
        EditText jDesc = view.findViewById(R.id.jDesc);
        AutoCompleteTextView jSkillsATV = view.findViewById(R.id.jSkill);
        EditText jAmount = view.findViewById(R.id.jAmount);

        title = jTitle.getText().toString();
        desc = jDesc.getText().toString();
        skill = jSkillsATV.getText().toString();
        amount = jAmount.getText().toString();
        id = jReference.push().getKey();
        DatabaseReference cityRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getUid()).child("City");
        cityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    city = snapshot.getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(skill) || TextUtils.isEmpty(amount)){
            Toast.makeText(getContext(), "All the fields are mandatory!", Toast.LENGTH_SHORT).show();
            return;
        }

//        JobData jData = new JobData(title, desc, skill, amount, city, id, "Pending");
//        jReference.child(id).setValue(jData);
    }
}