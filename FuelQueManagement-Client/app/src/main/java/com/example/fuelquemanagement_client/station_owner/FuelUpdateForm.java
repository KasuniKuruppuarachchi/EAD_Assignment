package com.example.fuelquemanagement_client.station_owner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.fuelquemanagement_client.R;

public class FuelUpdateForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_update_form);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Fuel Status");
    }
}