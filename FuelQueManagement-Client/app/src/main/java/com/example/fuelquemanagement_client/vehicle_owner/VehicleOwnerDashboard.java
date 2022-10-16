package com.example.fuelquemanagement_client.vehicle_owner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fuelquemanagement_client.R;

public class VehicleOwnerDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_owner_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Vehicle Owner Dashboard");
    }


}