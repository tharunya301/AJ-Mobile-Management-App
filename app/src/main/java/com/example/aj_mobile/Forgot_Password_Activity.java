package com.example.aj_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Forgot_Password_Activity extends AppCompatActivity {

    private Button forgetBtn;
    private EditText emailEt;
    private String email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEt = findViewById(R.id.emailEt);
        forgetBtn = findViewById(R.id.forgetBtn);

        final TextView loginBtn = findViewById(R.id.loginBtn);

        auth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(Forgot_Password_Activity.this, SignIn_Activity.class);
                startActivity(backIntent);
            }
        });

        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEmail();
            }
        });
    }

    private void validateEmail() {
        email = emailEt.getText().toString();
        if (email.isEmpty()){
            emailEt.setError("Required");
        }
        else {
            forgetPass();
        }
    }

    private void forgetPass() {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Forgot_Password_Activity.this, "Check your Email",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Forgot_Password_Activity.this, SignIn_Activity.class));
                    finish();
                }
                else {
                    Toast.makeText(Forgot_Password_Activity.this, "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}