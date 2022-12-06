package com.example.aj_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OTPVerification extends AppCompatActivity {

    private EditText otpET1, otpET2, otpET3, otpET4;
    private TextView resendBtn;

    //True after every 60 seconds
    private boolean resendEnabled = false;

    //Resend time in seconds
    private int resendTime = 60;

    private int selectedETPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        otpET1 = findViewById(R.id.otpET1);
        otpET2 = findViewById(R.id.otpET2);
        otpET3 = findViewById(R.id.otpET3);
        otpET4 = findViewById(R.id.otpET4);

        resendBtn = findViewById(R.id.resendBtn);
        final Button verifyBtn = findViewById(R.id.verifyBtn);

        final TextView otpEmail = findViewById(R.id.otpEmail);
        final TextView otpMobile = findViewById(R.id.otpMobile);

        //Getting email and mobile from Register activity through Intent
        final String getEmail = getIntent().getStringExtra("email");
        final String getMobile = getIntent().getStringExtra("mobile");

        //Setting email and mobile to Textview
        otpEmail.setText(getEmail);
        otpMobile.setText(getMobile);

        otpET1.addTextChangedListener(textWatcher);
        otpET2.addTextChangedListener(textWatcher);
        otpET3.addTextChangedListener(textWatcher);
        otpET4.addTextChangedListener(textWatcher);

        //By default open keyboard at otpEt1
        showKeyBoard(otpET1);

        //Start resend count down timer
        startCountDownTimer();

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (resendEnabled){
                    //Handle resend code here

                    //Start new resend count down timer
                    startCountDownTimer();
                }
            }
        });

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String generateOTP = otpET1.getText().toString()+otpET2.getText().toString()+otpET3.getText().toString()+otpET4.getText().toString();

                if (generateOTP.length() == 4){
                    //Handle OTP Verification here
                    Intent dashIntent = new Intent(OTPVerification.this, Dashboard_activity.class);
                    startActivity(dashIntent);
                }
            }
        });

    }

    private void showKeyBoard(EditText otpET){
        otpET.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpET, InputMethodManager.SHOW_IMPLICIT);
    }

    private void startCountDownTimer(){

        resendEnabled = false;
        resendBtn.setTextColor(Color.parseColor("#FFFFFF"));

        new CountDownTimer(resendTime * 1000, 1000){

            @Override
            public void onTick(long l) {
                resendBtn.setText("Resend Code("+(l/1000)+")");
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(getResources().getColor(R.color.orange));
            }
        }.start();

    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 0){
                if (selectedETPosition == 0){
                    selectedETPosition = 1;
                    showKeyBoard(otpET2);
                }
                else if (selectedETPosition == 1){
                    selectedETPosition = 2;
                    showKeyBoard(otpET3);
                }
                else if (selectedETPosition == 2){
                    selectedETPosition = 3;
                    showKeyBoard(otpET4);
                }
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DEL){
            if (selectedETPosition == 3){
                selectedETPosition = 2;
                showKeyBoard(otpET3);
            }
            else if (selectedETPosition == 2){
                selectedETPosition = 1;
                showKeyBoard(otpET2);
            }
            else if (selectedETPosition == 1){
                selectedETPosition = 0;
                showKeyBoard(otpET1);
            }
            return true;
        }
        else {
            return super.onKeyUp(keyCode, event);
        }
    }
}