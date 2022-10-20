package com.example.fuelquemanagement_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fuelquemanagement_client.constants.Constants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnStationOwner, btnVehicleOwner, btnLogin;

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

        btnStationOwner = findViewById(R.id.btn_shed_owner);
        btnVehicleOwner = findViewById(R.id.btn_vehicle_owner);
        btnLogin = findViewById(R.id.btn_login);

        btnStationOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Registration.class);
                i.putExtra(Constants.ROLE, Constants.STATION);
                startActivity(i);
            }
        });

        btnVehicleOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Registration.class);
                i.putExtra(Constants.ROLE, Constants.VEHICLE);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginScreen.class);
                startActivity(i);
            }
        });
    }
}