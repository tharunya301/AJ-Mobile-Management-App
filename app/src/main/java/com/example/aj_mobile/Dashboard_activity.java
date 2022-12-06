package com.example.aj_mobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class Dashboard_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final RelativeLayout stockBtn = findViewById(R.id.stockBtn);
        final RelativeLayout salesBtn = findViewById(R.id.salesBtn);
        final RelativeLayout reportBtn = findViewById(R.id.reportBtn);
        final RelativeLayout customerBtn = findViewById(R.id.customerBtn);
        final RelativeLayout staffAttendBtn = findViewById(R.id.staffAttendBtn);

        final ImageView backBtn = findViewById(R.id.backBtn);

        stockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stockIntent = new Intent(Dashboard_activity.this, com.example.aj_mobile.List.class);
                startActivity(stockIntent);
            }
        });

        salesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent salesIntent = new Intent(Dashboard_activity.this, com.example.aj_mobile.List.class);
                startActivity(salesIntent);
            }
        });

        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reportIntent = new Intent(Dashboard_activity.this, com.example.aj_mobile.List.class);
                startActivity(reportIntent);
            }
        });

        customerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customerIntent = new Intent(Dashboard_activity.this, com.example.aj_mobile.List.class);
                startActivity(customerIntent);
            }
        });

        staffAttendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent staffAttendIntent = new Intent(Dashboard_activity.this, List.class);
                startActivity(staffAttendIntent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}