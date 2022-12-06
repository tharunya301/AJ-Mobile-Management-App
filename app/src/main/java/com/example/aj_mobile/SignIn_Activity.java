package com.example.aj_mobile;

import static com.example.aj_mobile.R.id.passwordET;
import static com.example.aj_mobile.R.id.signInWithGoogleBtn;
import static com.example.aj_mobile.R.id.usernameET;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SignIn_Activity extends AppCompatActivity {

    private boolean passwordShowing = false;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    private String usernameET;
    private String passwordET;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://aj-mobiles-default-rtdb.asia-southeast1.firebasedatabase.app/");

    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        final EditText username = findViewById(R.id.usernameET);
        final EditText passwordET = findViewById(R.id.passwordET);

        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final TextView signInBtn = findViewById(R.id.signInBtn);
        final TextView signUpBtn = findViewById(R.id.signUpBtn);
        final TextView forgetPasswordBtn = findViewById(R.id.forgetPasswordBtn);

        final RelativeLayout signInWithGoogleBtn = findViewById(R.id.signInWithGoogleBtn);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            navigateToSecondActivity();
        }

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // checking if password is showing or not
                if (passwordShowing){
                    passwordShowing = false;

                    passwordET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.pasword_show);
                }
                else {
                    passwordShowing = true;

                    passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_hide);
                }
                // move the cursor at last of the text
                passwordET.setSelection(passwordET.length());
            }
        });

        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgetIntent = new Intent(SignIn_Activity.this, Forgot_Password_Activity.class);
                startActivity(forgetIntent);
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String usernameTxt = username.getText().toString();
                final String passwordTxt = passwordET.getText().toString();

                Intent signInIntent = new Intent(SignIn_Activity.this, Dashboard_activity.class);
                startActivity(signInIntent);

                if (usernameTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(SignIn_Activity.this, "Please enter your username or password", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("MobileLogin").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if username is exist in firebase database
                            /*if (snapshot.hasChild(usernameTxt)){

                                //username is exist in firebase database
                                //now get password of user from firebase data and match it with user entered password
                                final String getPassword = snapshot.child(usernameTxt).child("username").getValue(String.class);

                                if (getPassword.equals(passwordTxt)){
                                    Toast.makeText(SignIn_Activity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();

                                    //open dashboard activity on success
                                    Intent signInIntent = new Intent(SignIn_Activity.this, Dashboard_activity.class);
                                    startActivity(signInIntent);
                                    //finish();
                                }
                                else {
                                    //Toast.makeText(SignIn_Activity.this, "Incorrect password", Toast.LENGTH_SHORT).show();


                                }
                            }
                            else {
                                Toast.makeText(SignIn_Activity.this, "Incorrect username", Toast.LENGTH_SHORT).show();
                            }*/

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open SignUp Activity
                Intent signUpIntent = new Intent(SignIn_Activity.this, SignUp_Activity.class);
                startActivity(signUpIntent);
            }
        });

        signInWithGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent googleIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(googleIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToSecondActivity() {
        finish();
        Intent intent = new Intent(SignIn_Activity.this, SecondActivity.class);
        startActivity(intent);
    }
}