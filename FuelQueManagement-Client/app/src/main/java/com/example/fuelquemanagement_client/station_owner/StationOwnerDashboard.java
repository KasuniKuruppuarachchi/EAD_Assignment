package com.example.fuelquemanagement_client.station_owner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fuelquemanagement_client.R;

public class StationOwnerDashboard extends AppCompatActivity {
    private Button btn_update_fuel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_owner_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Station Owner Dashboard");

        btn_update_fuel = findViewById(R.id.btn_update_fuel);

        btn_update_fuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StationOwnerDashboard.this, FuelUpdateForm.class);
                startActivity(intent);
            }
        });
    }
}