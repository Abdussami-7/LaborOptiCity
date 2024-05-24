package com.example.laboropticity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.laboropticity.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class worker extends AppCompatActivity {

    ActivityMainBinding binding;
    Uri imageUri;
    Button Wsel, Wupload, Wsubmit;
    ImageView firebaseimage;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    TextInputEditText Wskills, Wlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.worker_details);
        Wsel=findViewById(R.id.Wsel);
        Wupload=findViewById(R.id.Wupload);
        firebaseimage=findViewById(R.id.firebaseimage);

        Wsubmit = findViewById(R.id.Wsubmit);
        Wskills = findViewById(R.id.Wskills);
//        Wlocation = findViewById(R.id.Wlocation);

        Wsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                selectImage();


            }
        });

        Wupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                uploadImage();

            }
        });

        Wsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userIntent, loginIntent;
                String pathEmail, skills;
                FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
                String uid = mUser.getUid();

                userIntent = getIntent();
                loginIntent = new Intent(getApplicationContext(), MainActivity.class);
//                pathEmail = userIntent.getStringExtra("User_Mail");
                skills = Wskills.getText().toString();
//                location = Wlocation.getText().toString();

                FirebaseDatabase.getInstance().getReference().child("Workers").child(uid).child("Skill").setValue(skills);
//                FirebaseDatabase.getInstance().getReference().child("Workers").child(pathEmail).child("Location").setValue(location);
                startActivity(loginIntent);
                finish();
            }
        });

    }

    private void uploadImage() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);


        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        firebaseimage.setImageURI(null);
                        Toast.makeText(worker.this,"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(worker.this,"Failed to Upload",Toast.LENGTH_SHORT).show();


                    }
                });

    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            firebaseimage.setImageURI(imageUri);


        }
    }

}
