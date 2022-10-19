package com.example.fuelquemanagement_client;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelquemanagement_client.constants.Constants;
import com.example.fuelquemanagement_client.database.DatabaseHelper;
import com.example.fuelquemanagement_client.database.InputValidatorHelper;
import com.example.fuelquemanagement_client.models.Fuel;
import com.example.fuelquemanagement_client.models.FuelStation;
import com.example.fuelquemanagement_client.models.StationOwner;
import com.example.fuelquemanagement_client.models.User;
import com.example.fuelquemanagement_client.models.VehicleOwner;
import com.example.fuelquemanagement_client.vehicle_owner.VehicleOwnerDashboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


        // calling a method to post the data and passing our name and job.
       // postDataUsingVolley(edStationName.getText().toString(), edLocation.getText().toString());
        if (success) {
            String id = databaseHelper.getLastStationOwnerId();
            System.out.println("Last Owner Id:"+id);
            postDataUsingVolley( edStationName.getText().toString(), edLocation.getText().toString(), id);
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

//    private void postDataUsingVolley(String stationName, String locationName, String stationOwnerId) {
//        try {
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//
//            String[] arr = new String[0];
//
//            String URL = "http://192.168.8.118:5000/Fuel";
//            JSONObject jsonBody = new JSONObject();
//            jsonBody.put("id", null);
//            jsonBody.put("type", "Diesel");
//            jsonBody.put("amount", 10);
//            jsonBody.put("date", "date");
//            jsonBody.put("time", "time");
//            jsonBody.put("stationsId", "634da9932f4682566f56114d");
//            jsonBody.put("lastModified", "634da9932f4682566f56114d");
//            final String mRequestBody = jsonBody.toString();
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.i("LOG_VOLLEY", response);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("LOG_VOLLEY", error.toString());
//                    Log.e("LOG_VOLLEY", error.getLocalizedMessage());
//                }
//            }) {
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
//
//                @Override
//                public byte[] getBody() throws AuthFailureError {
//                    try {
//                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                    } catch (UnsupportedEncodingException uee) {
//                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                        return null;
//                    }
//                }
//
//                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                    String responseString = "";
//                    if (response != null) {
//
//                        responseString = String.valueOf(response.statusCode);
//
//                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                }
//            };
//
//            System.out.println("StringRequest" + stringRequest);
//            requestQueue.add(stringRequest);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }


    private void postDataUsingVolley(String stationName, String locationName, String stationOwnerId){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String[] arr = new String[0];

            System.out.println("stationName"+ stationName);
            System.out.println("locationName"+ locationName);
            System.out.println("stationOwnerId"+ stationOwnerId);
            String URL = "http://192.168.8.118:5000/FuelStation";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", null);
            jsonObject.put("name", stationName);
            jsonObject.put("location", locationName);
            //jsonObject.put("fuel", new Fuel[0]);
            //jsonObject.put("queue", new Fuel[0]);
            jsonObject.put("stationOwner", stationOwnerId);
            jsonObject.put("fuelStatus", false);
            jsonObject.put("lastModified", null);
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


//    public void postDataUsingVolley(String stationName, String locationName, String stationOwnerId){
//        JsonObjectRequest
//                jsonObjReq
//                = new JsonObjectRequest(
//                Request.Method.POST,
//                "http://192.168.8.118:5000/FuelStation",
//                null,
//                new Response.Listener() {
//
//                    @Override
//                    public void onResponse(JSONObject response)
//                    {
//                        Log.d("api", response.toString());
//
//                    }
//                },
//                new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
//                        VolleyLog.d(TAG, "Error: "
//                                + error.getMessage());
//                        pDialog.hide();
//                    }
//                }) {
//
//            @Override
//            protected Map getParams()
//            {
//                Map params = new HashMap();
//                params.put("name", "Androidhive");
//                params.put("email", "abc@androidhive.info");
//                params.put("password", "password123");
//
//                return params;
//            }
//
//        };
//
//        AppController.getInstance()
//                .addToRequestQueue(jsonObjReq, tag_json_obj);
//
//    }


}