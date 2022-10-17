package com.example.fuelquemanagement_client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fuelquemanagement_client.constants.Constants;
import com.example.fuelquemanagement_client.database.DatabaseHelper;
import com.example.fuelquemanagement_client.database.InputValidatorHelper;
import com.example.fuelquemanagement_client.models.StationOwner;
import com.example.fuelquemanagement_client.models.User;
import com.example.fuelquemanagement_client.models.VehicleOwner;
import com.example.fuelquemanagement_client.vehicle_owner.VehicleOwnerDashboard;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private EditText edFullName, edUserName, edPassword, edConfirmPassword, edLocation, edStationName;
    private DatabaseHelper databaseHelper;
    private VehicleOwner vehicleOwner;
    private StationOwner stationOwner;
    private Button btnRegister;
    private String role;
    private User user;

    @Override
    public void onClick(View view) {
        boolean allowSave = checkValidations(role);
        switch (view.getId()) {
            case R.id.btn_register:
                if (role.equals(Constants.VEHICLE)) {
                    if (allowSave) {
                        user = addVehicleUser();
                    }
                } else {
                    if (allowSave) {
                        user = addStationUser();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Registration");

        role = getIntent().getStringExtra(Constants.ROLE);

        edFullName = findViewById(R.id.edtTxt_fname);
        edUserName = findViewById(R.id.edtTxt_username);
        edPassword = findViewById(R.id.edtTxt_password);
        edConfirmPassword = findViewById(R.id.edtTxt_confirmPassword);
        edLocation = findViewById(R.id.edtTxt_location);
        edStationName = findViewById(R.id.edtTxt_stationName);

        if (role.equals(Constants.VEHICLE)) {
            edLocation.setVisibility(View.GONE);
            edStationName.setVisibility(View.GONE);
        } else {
            edLocation.setVisibility(View.VISIBLE);
            edStationName.setVisibility(View.VISIBLE);
        }

        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(this);
    }

    //This function executes when user clicks the Add button in the view
    public boolean checkValidations(String role) {

        InputValidatorHelper inputTaskValidatorHelper = new InputValidatorHelper();

        //Validate and Save
        boolean allowSave = true;

        if (inputTaskValidatorHelper.isNullOrEmpty(edFullName.getText().toString())) {
            Toast.makeText(this, "Full Name Should not be Empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (inputTaskValidatorHelper.isNullOrEmpty(edUserName.getText().toString())) {
            Toast.makeText(this, "Username Should not be Empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(role.equals(Constants.STATION)){
            if (inputTaskValidatorHelper.isNullOrEmpty(edLocation.getText().toString())) {
                Toast.makeText(this, "Station Location Should not be Empty", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (inputTaskValidatorHelper.isNullOrEmpty(edStationName.getText().toString())) {
                Toast.makeText(this, "Station Location Name Should not be Empty", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if (inputTaskValidatorHelper.isNullOrEmpty(edPassword.getText().toString())) {
            Toast.makeText(this, "Password Should not be Empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!edPassword.getText().toString().equals(edConfirmPassword.getText().toString())) {
            Toast.makeText(this, "Password and Confirm Password are mismatched", Toast.LENGTH_SHORT).show();
            return false;
        }

        return allowSave;
    }

    public User addVehicleUser() {
        user = new User(-1, edFullName.getText().toString(), edUserName.getText().toString(), "", "", edPassword.getText().toString(), Constants.VEHICLE);
        boolean success = databaseHelper.addUserRecord(user);
        if (success) {
            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Registration.this, LoginScreen.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        return user;
    }

    public User addStationUser() {
        user = new User(-1, edFullName.getText().toString(), edUserName.getText().toString(), edLocation.getText().toString(), edStationName.getText().toString(), edPassword.getText().toString(), Constants.STATION);
        boolean success = databaseHelper.addUserRecord(user);
        if (success) {
            Toast.makeText(this, "Successfully Registered", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Registration.this, LoginScreen.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
        return user;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        //If user clicks on the back button
        if(id == android.R.id.home){
            Intent intent = new Intent(Registration.this,LoginScreen.class);
            startActivity(intent);
        }
//        //If user clicks on the cross button in the top right corner of AddTask interface
//        if(id == R.id.cancel){
//
//            finish();
//            startActivity(getIntent());
//
//        }
        return true;
    }
}