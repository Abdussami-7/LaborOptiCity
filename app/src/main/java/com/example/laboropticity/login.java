package com.example.laboropticity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class login extends AppCompatActivity {
    Button LoginBTN;
    TextView RegTv;
    FirebaseAuth mAuth;
    ProgressBar Pbar;
    TextInputEditText EmailTv,PasswordTv;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent=getIntent();

        EmailTv=findViewById(R.id.EmailTv);
        PasswordTv=findViewById(R.id.PasswordTv);
        LoginBTN=findViewById(R.id.LoginBTN);
        Pbar=findViewById(R.id.Pbar);
        RegTv=findViewById(R.id.RegTv);
        mAuth= FirebaseAuth.getInstance();

        LoginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pbar.setVisibility(View.VISIBLE);
                String email,password;

                email= String.valueOf(EmailTv.getText());
                password=String.valueOf(PasswordTv.getText());


                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(email))
                {
                    Toast.makeText(login.this, "Email/Password Cannot Be Empty", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Pbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(login.this, "Login Successful ",
                                            Toast.LENGTH_LONG).show();

                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();


                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(login.this, "Authentication failed.",
                                            Toast.LENGTH_LONG).show();

                                }
                            }
                        });
            }
        });


        RegTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),register.class);
                startActivity(intent);

            }
        });
    }
}