package com.example.fuelquemanagement_client.vehicle_owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
import com.example.fuelquemanagement_client.LoginScreen;
import com.example.fuelquemanagement_client.R;
import com.example.fuelquemanagement_client.Registration;
import com.example.fuelquemanagement_client.constants.Constants;
import com.example.fuelquemanagement_client.models.FuelStation;
import com.example.fuelquemanagement_client.models.Queue;
import com.example.fuelquemanagement_client.models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class JoinQueue extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    private Spinner dropdownVehicleType, dropdownFuelType;
    private FuelStation fuelStation;
    private Button btnConfirmJoin;
    private User loggedUser;
    private String fuelType;
    private String vehicle;
    private Queue queue;

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

        dropdownFuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                fuelType = (String) arg0.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        btnConfirmJoin = findViewById(R.id.btn_confirmJoin);
        btnConfirmJoin.setOnClickListener(this);

        loggedUser = (User) getIntent().getSerializableExtra(Constants.LOGGED_USER);
        fuelStation = (FuelStation) getIntent().getSerializableExtra(Constants.STATION);
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
                System.out.println("Confirm Join");
                System.out.println("Vehicle"+vehicle);
                System.out.println("VehicleOwner"+loggedUser.getId());
                System.out.println("StationId"+fuelStation.getId());
                System.out.println("Fuel Type"+fuelType);
                queue = new Queue("",vehicle, String.valueOf(loggedUser.getId()), fuelType, fuelStation.getId());
                addQueueAPI(vehicle, String.valueOf(loggedUser.getId()),fuelStation.getId(), fuelType);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                Intent i = new Intent(JoinQueue.this, ExitQueue.class);
                i.putExtra(Constants.JOINED_TIME, dateFormat.format(date).toString());
                i.putExtra(Constants.STATION, fuelStation);
                i.putExtra(Constants.JOINED_QUEUE, queue);
                i.putExtra(Constants.LOGGED_USER, loggedUser);
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
            intent.putExtra(Constants.STATION, fuelStation);
            startActivity(intent);
        }
        return true;
    }

    private void addQueueAPI(String vehicleType, String vehicleOwnerId, String stationId, String fuelType){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String[] arr = new String[0];


            String URL = Constants.BASE_URL+"/Queue";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", null);
            jsonObject.put("vehicleType", vehicleType);
            jsonObject.put("vehicleOwner", vehicleOwnerId);
            //jsonObject.put("fuel", new Fuel[0]);
            //jsonObject.put("queue", new Fuel[0]);
            jsonObject.put("stationsId", stationId);
            jsonObject.put("fuelType", fuelType);
            final String mRequestBody = jsonObject.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);
                    try {
                        System.out.println("Inside"+   response);
                        JSONObject singleObject = new JSONObject(response);
                        System.out.println("Inside" +   singleObject);
                        System.out.println("Inside" +   singleObject.getString("name"));
                    }catch (JSONException e){

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}