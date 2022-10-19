package com.example.fuelquemanagement_client.vehicle_owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.fuelquemanagement_client.LoginScreen;
import com.example.fuelquemanagement_client.R;
import com.example.fuelquemanagement_client.Registration;
import com.example.fuelquemanagement_client.constants.Constants;

public class JoinQueue extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    private Spinner dropdownVehicleType, dropdownFuelType;
    private Button btnConfirmJoin;
    private String vehicle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_queue);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Join Queue Process");

        dropdownVehicleType = (Spinner) findViewById(R.id.drpdwn_vehicleType);
        ArrayAdapter<CharSequence> adapterVehicleType = ArrayAdapter.createFromResource(this,
                R.array.vehicles_array, android.R.layout.simple_spinner_item);
        adapterVehicleType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownVehicleType.setAdapter(adapterVehicleType);
        dropdownVehicleType.setOnItemSelectedListener(this);

        dropdownVehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                vehicle = (String) arg0.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        dropdownFuelType = (Spinner) findViewById(R.id.drpdwn_fuelType);
        ArrayAdapter<CharSequence> adapterFuelType = ArrayAdapter.createFromResource(this,
                R.array.fuel_array, android.R.layout.simple_spinner_item);
        adapterFuelType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdownFuelType.setAdapter(adapterFuelType);
        dropdownFuelType.setOnItemSelectedListener(this);

        btnConfirmJoin = findViewById(R.id.btn_confirmJoin);
        btnConfirmJoin.setOnClickListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String selectedItem = (String) parent.getItemAtPosition(pos);
        Toast.makeText(this, selectedItem, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirmJoin:
                System.out.println("Vehicle"+vehicle);

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                Intent i = new Intent(JoinQueue.this, ExitQueue.class);
                i.putExtra(Constants.JOINED_TIME, dateFormat.format(date).toString());
                startActivity(i);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        //If user clicks on the back button
        if(id == android.R.id.home){
            Intent intent = new Intent(JoinQueue.this, VehicleOwnerDashboard.class);
            startActivity(intent);
        }
        return true;
    }


}