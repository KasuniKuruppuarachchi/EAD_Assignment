package com.example.fuelquemanagement_client;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fuelquemanagement_client.constants.Constants;
import com.example.fuelquemanagement_client.database.DatabaseHelper;
import com.example.fuelquemanagement_client.database.InputValidatorHelper;
import com.example.fuelquemanagement_client.models.StationOwner;
import com.example.fuelquemanagement_client.models.User;
import com.example.fuelquemanagement_client.models.VehicleOwner;
import com.example.fuelquemanagement_client.station_owner.StationOwnerDashboard;
import com.example.fuelquemanagement_client.vehicle_owner.VehicleOwnerDashboard;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener{

    private EditText edUserName, edPassword;
    private DatabaseHelper databaseHelper;
    private TextView txtRegisterLink;
    private Button btnLogin;
    private User user;

    @Override
    public void onClick(View view) {
        boolean allowSave = checkValidations();
        switch (view.getId()){
            case R.id.txt_linkRegister:
                showAlertDialog();
                break;
            case R.id.btn_logIn:
                if(allowSave) {
                    user = databaseHelper.loginValidate(edUserName.getText().toString(), edPassword.getText().toString());
                    if (user == null) {
                        Toast.makeText(LoginScreen.this, "Invalid Credentials, Please try again", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginScreen.this, "Successfully Logged", Toast.LENGTH_LONG).show();
                        if (user.getRole().equals(Constants.VEHICLE)) {
                            Intent i = new Intent(LoginScreen.this, VehicleOwnerDashboard.class);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(LoginScreen.this, StationOwnerDashboard.class);
                            startActivity(i);
                        }
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
        setContentView(R.layout.activity_login_screen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Log In");

        edUserName = findViewById(R.id.edtTxt_username);
        edPassword = findViewById(R.id.edtTxt_password);

        btnLogin = findViewById(R.id.btn_logIn);
        btnLogin.setOnClickListener(this);

        txtRegisterLink = findViewById(R.id.txt_linkRegister);
        txtRegisterLink.setOnClickListener(this);

        databaseHelper = new DatabaseHelper(this);


    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginScreen.this);
        alertDialog.setTitle("Register As ");
        String[] items = {"Vehicle Owner","Station Owner"};
        int checkedItem = 1;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Toast.makeText(LoginScreen.this, "You are going to register as a Vehicle Owner", Toast.LENGTH_LONG).show();
                        Intent intentVehicle = new Intent(LoginScreen.this, Registration.class);
                        intentVehicle.putExtra(Constants.ROLE, Constants.VEHICLE);
                        startActivity(intentVehicle);
                        break;
                    case 1:
                        Toast.makeText(LoginScreen.this, "You are going to register as a Station Owner", Toast.LENGTH_LONG).show();
                        Intent intentStation = new Intent(LoginScreen.this, Registration.class);
                        intentStation.putExtra(Constants.ROLE, Constants.STATION);
                        startActivity(intentStation);
                        break;
                }
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    public boolean checkValidations() {

        InputValidatorHelper inputTaskValidatorHelper = new InputValidatorHelper();

        //Validate and Save
        boolean allowSave = true;

        if (inputTaskValidatorHelper.isNullOrEmpty(edUserName.getText().toString())) {
            Toast.makeText(this, "Username Should not be Empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (inputTaskValidatorHelper.isNullOrEmpty(edPassword.getText().toString())) {
            Toast.makeText(this, "Password Should not be Empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return allowSave;
    }
}