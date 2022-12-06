package com.example.aj_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash_Actitvity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000;

    //splash time for loading when opening the application
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_actitvity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(Splash_Actitvity.this, SignIn_Activity.class);
                startActivity(homeIntent);
                //finish();
            }
        }, SPLASH_TIME_OUT);
    }
}