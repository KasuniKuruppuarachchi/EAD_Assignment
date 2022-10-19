package com.example.fuelquemanagement_client.vehicle_owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fuelquemanagement_client.LoginScreen;
import com.example.fuelquemanagement_client.R;
import com.example.fuelquemanagement_client.Registration;
import com.example.fuelquemanagement_client.constants.Constants;
import com.example.fuelquemanagement_client.models.FuelStation;
import com.example.fuelquemanagement_client.models.User;

public class VehicleOwnerDashboard extends AppCompatActivity implements View.OnClickListener {

    private Button btnJoinQueue;
    private FuelStation fuelStation;
    private User loggedUser;
    private TextView txtPetrolStatus, txtDieselStatus;
    private CardView cardPetrolStatus, cardDieselStatus;

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


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_owner_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // To retrieve object in second Activity
        fuelStation = (FuelStation) getIntent().getSerializableExtra(Constants.STATION);
        getSupportActionBar().setTitle(fuelStation.getStationName() + " - " + fuelStation.getLocation());

        btnJoinQueue = findViewById(R.id.btn_join);
        btnJoinQueue.setOnClickListener(this);

        txtPetrolStatus = findViewById(R.id.txt_petrolStatus);
        txtDieselStatus = findViewById(R.id.txt_dieselStatus);

        cardPetrolStatus = findViewById(R.id.card_petrolStatus);
        cardDieselStatus = findViewById(R.id.card_dieselStatus);

        if(fuelStation.isDieselStatus()){
            txtDieselStatus.setText("Available");
            cardDieselStatus.setCardBackgroundColor(Color.parseColor("#ff99cc00"));
        }else{
            txtDieselStatus.setText("Finished");
            cardDieselStatus.setCardBackgroundColor(Color.parseColor("#ffff4444"));
        }

        if(fuelStation.isPetrolStatus()){
            txtPetrolStatus.setText("Available");
            cardPetrolStatus.setCardBackgroundColor(Color.parseColor("#ff99cc00"));
        }else{
            txtPetrolStatus.setText("Finished");
            cardPetrolStatus.setCardBackgroundColor(Color.parseColor("#ffff4444"));
        }

        loggedUser = (User) getIntent().getSerializableExtra(Constants.LOGGED_USER);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        //If user clicks on the back button
        if(id == android.R.id.home){
            Intent intent = new Intent(VehicleOwnerDashboard.this,SelectionStation.class);
            startActivity(intent);
        }
        return true;
    }


}