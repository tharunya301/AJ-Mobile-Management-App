package com.example.aj_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.preference.EditTextPreference;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp_Activity extends AppCompatActivity {

    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;

    //create object of Database Reference class to access firebase's realtime database
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://aj-mobiles-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText fullName = findViewById(R.id.fullNameET);
        final EditText email = findViewById(R.id.emailET);
        final EditText mobile = findViewById(R.id.mobileET);
        final EditText username = findViewById(R.id.unET);

        final EditText password = findViewById(R.id.passwordET);
        final EditText conPassword = findViewById(R.id.conPasswordET);
        final ImageView passwordIcon = findViewById(R.id.passwordIcon);
        final ImageView conPasswordIcon = findViewById(R.id.conPasswordIcon);

        final AppCompatButton signUpBtn = findViewById(R.id.signUpBtn);
        final TextView signInBtn = findViewById(R.id.signInBtn);


        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // checking if password is showing or not
                if (passwordShowing){
                    passwordShowing = false;

                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.pasword_show);
                }
                else {
                    passwordShowing = true;

                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_hide);
                }
                // move the cursor at last of the text
                password.setSelection(password.length());
            }
        });

        conPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // checking if password is showing or not
                if (conPasswordShowing){
                    conPasswordShowing = false;

                    conPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.pasword_show);
                }
                else {
                    conPasswordShowing = true;

                    conPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.password_hide);
                }
                // move the cursor at last of the text
                conPassword.setSelection(conPassword.length());
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Get data from EditTexts into String variables
                final String fullnameTxt = fullName.getText().toString();
                final String emailTxt = email.getText().toString();
                final String mobileTxt = mobile.getText().toString();
                final String usernameTxt = username.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String conPasswordTxt = conPassword.getText().toString();

                final String getMobileTxt = mobile.getText().toString();
                final String getEmailTxt = email.getText().toString();

                //check if user fill all the fields before sending data to firebase
                if (fullnameTxt.isEmpty() || emailTxt.isEmpty() || mobileTxt.isEmpty() || usernameTxt.isEmpty() || passwordTxt.isEmpty() || conPasswordTxt.isEmpty()){
                    Toast.makeText(SignUp_Activity.this, "All fields required", Toast.LENGTH_SHORT).show();
                }
                //check if passwords are not matching with each other
                //if not matching with each other then show a toast message
                else if (!passwordTxt.equals(conPasswordTxt)){
                    Toast.makeText(SignUp_Activity.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
                }
                else {

                    reference.child("MobileLogin").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if mobile number is not registered before
                            if (snapshot.hasChild(mobileTxt)){
                                Toast.makeText(SignUp_Activity.this, "Mobile number is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //sending data to firebase realtime database
                                //mobile number is the unique identity of every user
                                //all the other details of Mobile Login comes under mobile number
                                reference.child("MobileLogin").child(mobileTxt).child("fullName").child(fullnameTxt);
                                reference.child("MobileLogin").child(mobileTxt).child("email").child(emailTxt);
                                reference.child("MobileLogin").child(mobileTxt).child("username").child(usernameTxt);
                                reference.child("MobileLogin").child(mobileTxt).child("password").child(passwordTxt);

                                //show a success message then finish the activity
                                Toast.makeText(SignUp_Activity.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                                //finish();

                                // opening OTP Verification Activity along with mobile and email
                                Intent otpIntent = new Intent(SignUp_Activity.this, OTPVerification.class);

                                otpIntent.putExtra("mobile", getMobileTxt);
                                otpIntent.putExtra("email", getEmailTxt);

                                startActivity(otpIntent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                ;
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean validateName(){
        //String fullname = fullNameET.getEditText()
        return false;
    }

}