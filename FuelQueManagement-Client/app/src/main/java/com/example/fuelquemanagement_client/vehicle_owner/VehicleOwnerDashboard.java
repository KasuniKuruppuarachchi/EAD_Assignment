package com.example.fuelquemanagement_client.vehicle_owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.fuelquemanagement_client.LoginScreen;
import com.example.fuelquemanagement_client.R;
import com.example.fuelquemanagement_client.Registration;
import com.example.fuelquemanagement_client.constants.Constants;

public class VehicleOwnerDashboard extends AppCompatActivity implements View.OnClickListener {

    private Button btnJoinQueue;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_join:
                Intent i = new Intent(VehicleOwnerDashboard.this, JoinQueue.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_owner_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Vehicle Owner Dashboard - Kaduwela Station");

        btnJoinQueue = findViewById(R.id.btn_join);
        btnJoinQueue.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        //If user clicks on the back button
        if(id == android.R.id.home){
            Intent intent = new Intent(VehicleOwnerDashboard.this,LoginScreen.class);
            startActivity(intent);
        }
        return true;
    }


}