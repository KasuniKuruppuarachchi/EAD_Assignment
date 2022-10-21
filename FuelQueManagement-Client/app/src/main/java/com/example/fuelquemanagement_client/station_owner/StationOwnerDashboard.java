package com.example.fuelquemanagement_client.station_owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelquemanagement_client.MainActivity;
import com.example.fuelquemanagement_client.R;
import com.example.fuelquemanagement_client.constants.Constants;
import com.example.fuelquemanagement_client.models.FuelStation;


public class StationOwnerDashboard extends AppCompatActivity {
    private Button btn_update_fuel;
    private RadioGroup radioGroupPetrol, radioGroupDiesel;
    private RadioButton rbPetrol, rbPetrolAvailable, rbPetrolFinish;
    private RadioButton rbDiesel, rbDieselAvailable, rbDieselFinish;
    private String stationID;
    private boolean status_petrol, status_diesel;
    private FuelStation fuelStation;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // App Bar
        setContentView(R.layout.activity_station_owner_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Station Owner Dashboard");

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        fuelStation = (FuelStation) getIntent().getSerializableExtra(Constants.STATION);
        stationID = fuelStation.getId();
        btn_update_fuel = findViewById(R.id.btn_update_fuel);
        radioGroupPetrol = findViewById(R.id.rad_group_petrol);
        radioGroupDiesel = findViewById(R.id.rad_group_diesel);
        rbDieselFinish = findViewById(R.id.rad_diesel_finish);
        rbDieselAvailable = findViewById(R.id.rad_diesel_available);
        rbPetrolFinish = findViewById(R.id.rad_petrol_finish);
        rbPetrolAvailable = findViewById(R.id.rad_petrol_available);
        textView = findViewById(R.id.txt_station_name);

        // Setting station name and location on the interface
        textView.setText(fuelStation.getStationName() + " - " + fuelStation.getLocation());

        status_petrol = fuelStation.isPetrolStatus();
        status_diesel = fuelStation.isDieselStatus();

        // Setting the current Diesel Status on the interface
        if(status_diesel == false) {
            rbDieselFinish.setChecked(true);
        } else {
            rbDieselAvailable.setChecked(true);
        }

        // Setting the current Petrol Status on the interface
        if(status_petrol == false) {
            rbPetrolFinish.setChecked(true);
        } else {
            rbPetrolAvailable.setChecked(true);
        }

        // Onclick function for Petrol Status Radio Button
        radioGroupPetrol.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String URL_petrol ;
                rbPetrol = findViewById(i);

                String URL_to_send;
                System.out.println("Radio Result " + rbPetrol.getText().toString());

                if(rbPetrol.getText().toString().equals("Available")) {
                    System.out.println("Send Petrol as available");
                    URL_petrol = Constants.BASE_URL + "/FuelStation/UpdatePetrolStatus?status=true&id=";
                } else {
                    System.out.println("Send Petrol as finished");
                    URL_petrol = Constants.BASE_URL + "/FuelStation/UpdatePetrolStatus?status=false&id=";
                }

                URL_to_send = URL_petrol.concat(stationID);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_to_send, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("LOG_VOLLEY", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LOG_VOLLEY", error.toString());
                    }
                }){
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        // Onclick function for Diesel Status Radio Button
        radioGroupDiesel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String URL_diesel;
                rbDiesel = findViewById(i);

                String URL_to_send;
                System.out.println("Radio Result " + rbDiesel.getText().toString());

                if(rbDiesel.getText().toString().equals("Available")) {
                    System.out.println("Send Diesel as available");
                    URL_diesel = Constants.BASE_URL + "/FuelStation/UpdateDieselStatus?status=true&id=";
                } else {
                    System.out.println("Send Diesel as finished");
                    URL_diesel = Constants.BASE_URL + "/FuelStation/UpdateDieselStatus?status=false&id=";
                }

                URL_to_send = URL_diesel.concat(stationID);

                System.out.println("Send this " + URL_to_send);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_to_send, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("LOG_VOLLEY", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LOG_VOLLEY", error.toString());
                    }
                }){
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        // Update Fuel station button navigation
        btn_update_fuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StationOwnerDashboard.this, FuelUpdateForm.class);
                intent.putExtra(Constants.STATION, fuelStation);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //If user clicks on the back button
        if(id == android.R.id.home){
            Intent intent = new Intent(StationOwnerDashboard.this, MainActivity.class);
            startActivity(intent);
        }
        return true;
    }
}