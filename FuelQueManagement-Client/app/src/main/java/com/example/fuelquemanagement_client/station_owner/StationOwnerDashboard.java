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
import com.example.fuelquemanagement_client.models.Fuel;
import com.example.fuelquemanagement_client.models.FuelStation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class StationOwnerDashboard extends AppCompatActivity {
    private Button btn_update_fuel;
    private RadioGroup radioGroupPetrol;
    private RadioGroup radioGroupDiesel;
    private RadioButton rbPetrol, rbDiesel;
    private int petrolStatus;
    private int dieselStatus;
    private String stationID = "63516919644eac12a2f7eb9f";
    private FuelStation fuelStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_owner_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fuelStation = (FuelStation) getIntent().getSerializableExtra(Constants.STATION);

        getSupportActionBar().setTitle(fuelStation.getStationName() + " - " + fuelStation.getLocation());

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        btn_update_fuel = findViewById(R.id.btn_update_fuel);
        radioGroupPetrol = findViewById(R.id.rad_group_petrol);
        radioGroupDiesel = findViewById(R.id.rad_group_diesel);

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

        btn_update_fuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StationOwnerDashboard.this, FuelUpdateForm.class);
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

//    public void requestWithHttpHeaders(String url, String id, String status) {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        StringRequest getRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.d("Response", response);
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO Auto-generated method stub
//                        Log.d("ERROR","error => "+error.toString());
//                    }
//                }
//        ) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("status", status);
//                params.put("id", id);
//
//                return params;
//            }
//        };
//        queue.add(getRequest);
//    }

//    public Request post(String url, String status, String password, Listener listener, ErrorListener errorListener) {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        try {
//            JSONObject params = new JSONObject();
//            params.put("status", status);
//            params.put("id", password);
//            RequestQueue req = new RequestQueue(
//                    Request.Method.POST,
//                    url,
//                    params.toString(),
//                    listener,
//                    errorListener
//            );
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return req;
//    }

}