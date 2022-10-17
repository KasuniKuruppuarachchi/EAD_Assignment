package com.example.fuelquemanagement_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fuelquemanagement_client.station_owner.StationOwnerDashboard;
import com.example.fuelquemanagement_client.vehicle_owner.VehicleOwnerDashboard;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnStationOwner, btnVehicleOwner;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.btn_shed_owner:
//                System.out.println("Clicked123");
//                Intent i = new Intent(MainActivity.this, StationOwnerDashboard.class);
//                startActivity(i);
//                break;
//            case R.id.btn_vehicle_owner:
//                System.out.println("Clicked123");
//                break;
//            default:
//                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().setTitle(R.string.e_home_name);

        btnStationOwner = findViewById(R.id.btn_shed_owner);
        btnVehicleOwner = findViewById(R.id.btn_vehicle_owner);

        btnStationOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, StationOwnerDashboard.class);
                startActivity(i);
            }
        });

        btnVehicleOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, VehicleOwnerDashboard.class);
                startActivity(i);
            }
        });
    }
}