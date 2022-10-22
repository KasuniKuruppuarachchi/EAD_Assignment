package com.example.fuelquemanagement_client.vehicle_owner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import android.content.DialogInterface;
import android.util.Log;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.android.volley.Request;
import com.android.volley.NetworkResponse;
import com.android.volley.AuthFailureError;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.VolleyError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.fuelquemanagement_client.LoginScreen;
import com.example.fuelquemanagement_client.R;
import com.example.fuelquemanagement_client.Registration;
import com.example.fuelquemanagement_client.models.User;
import com.example.fuelquemanagement_client.models.Queue;
import com.example.fuelquemanagement_client.models.FuelStation;
import com.example.fuelquemanagement_client.constants.Constants;
import com.example.fuelquemanagement_client.vehicle_owner.controllers.vehicleDashboardController;


public class JoinQueue extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private FuelStation selectedFuelStation = new FuelStation();
    private Spinner dropdownVehicleType, dropdownFuelType;
    private TextView txtTimeDuration;
    private FuelStation fuelStation;
    private Button btnConfirmJoin;
    private String timeDuration = "0";
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

        loggedUser = (User) getIntent().getSerializableExtra(Constants.LOGGED_USER);
        fuelStation = (FuelStation) getIntent().getSerializableExtra(Constants.STATION);
        fuelStation = (FuelStation) getIntent().getSerializableExtra(Constants.STATION);
        timeDuration = getIntent().getStringExtra(Constants.WAITING_TIME);
        //getFuelStationById(fuelStation.getId());
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

       // txtTimeDuration.setText("People are waiting from "+timeDuration);
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
               // queue = new Queue("", vehicle, String.valueOf(loggedUser.getId()), fuelType, fuelStation.getId());
                getFuelStationById(fuelStation.getId(),fuelType);
           //     addQueueAPI(vehicle, String.valueOf(loggedUser.getId()), fuelStation.getId(), fuelType);
//                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                Date date = new Date();
//                Intent i = new Intent(JoinQueue.this, ExitQueue.class);
//                i.putExtra(Constants.JOINED_TIME, dateFormat.format(date).toString());
//                i.putExtra(Constants.STATION, fuelStation);
//               // i.putExtra(Constants.JOINED_QUEUE, queue);
//                i.putExtra(Constants.LOGGED_USER, loggedUser);
//                startActivity(i);
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
        if (id == android.R.id.home) {
            Intent intent = new Intent(JoinQueue.this, VehicleOwnerDashboard.class);
            intent.putExtra(Constants.STATION, fuelStation);
            intent.putExtra(Constants.LOGGED_USER, loggedUser);
            startActivity(intent);
        }
        return true;
    }

    /*When user clicks on the Confirm Join button to enter to a queue this method executes and this update relevant the fuel station
    along with the entered queue details*/
//    private void addQueueAPI(String vehicleType, String vehicleOwnerId, String stationId, String fuelType) {
//        try {
//
//            RequestQueue requestQueue = Volley.newRequestQueue(this);
//            String URL = Constants.BASE_URL + "/Queue";
//
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("id", null);
//            jsonObject.put("vehicleType", vehicleType);
//            jsonObject.put("vehicleOwner", vehicleOwnerId);
//            jsonObject.put("stationsId", stationId);
//            jsonObject.put("fuelType", fuelType);
//
//            final String mRequestBody = jsonObject.toString();
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.i("LOG_VOLLEY", response);
//                    System.out.println("Join Queue : "+response);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("LOG_VOLLEY", error.toString());
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
//                        responseString = String.valueOf(response.statusCode);
//                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                }
//            };
//            requestQueue.add(stringRequest);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    private void addQueueAPI(String vehicleType, String vehicleOwnerId, String stationId, String fuelType) {
        JSONObject jsonObject = new JSONObject();
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        String URL = Constants.BASE_URL + "/Queue";
        final Queue[] joinedQueue = new Queue[1];

        try {

            jsonObject.put("id", null);
            jsonObject.put("vehicleType", vehicleType);
            jsonObject.put("vehicleOwner", vehicleOwnerId);
            jsonObject.put("stationsId", stationId);
            jsonObject.put("fuelType", fuelType);

            final String mRequestBody = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Join Queue : "+response);
                        try {
                            JSONArray queueArray = new JSONArray(response.getString("queue"));
                            JSONObject singleObject = queueArray.getJSONObject(queueArray.length()-1);
                            joinedQueue[0] = new Queue(
                                    singleObject.getString("id"),
                                    singleObject.getString("vehicleType"),
                                    singleObject.getString("vehicleOwner"),
                                    singleObject.getString("fuelType"),
                                    singleObject.getString("stationsId"),
                                    singleObject.getString("arivalTime"),
                                    singleObject.getString("departTime")
                            );
                            System.out.println("Joined Queue Id : "+joinedQueue[0].getId());
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date date = new Date();
                            Intent i = new Intent(JoinQueue.this, ExitQueue.class);
                            i.putExtra(Constants.JOINED_TIME, dateFormat.format(date).toString());
                            i.putExtra(Constants.STATION, fuelStation);
                            i.putExtra(Constants.JOINED_QUEUE, joinedQueue[0]);
                            i.putExtra(Constants.LOGGED_USER, loggedUser);
                            startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(jsonObjReq);
    }


    // Get the selected station's object through the id via api call from remote database
    private void getFuelStationById(String id, String fuelType) {

        JSONObject jsonObject = new JSONObject();
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        String URL = Constants.BASE_URL + "/FuelStation/"+id;
        ArrayList<Queue> joinedQueues = new ArrayList<>();
        final JsonArrayRequest[] jsonObjReqArray = new JsonArrayRequest[1];

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Inside new Api 123" + response);
                        try {

                            System.out.println(response);
                            System.out.println("StationIdNewLoaded" + response.getString("id"));
                            JSONArray queueArray = new JSONArray(response.getString("queue"));

                            System.out.println("Joined Queues : "+ queueArray);

                            for(int i=0;i<queueArray.length();i++){
                                JSONObject singleObject = queueArray.getJSONObject(i);
                                Log.e("api", "onResponse: "+   singleObject.getString("id"));
                                Queue queue = new Queue(
                                        singleObject.getString("id"),
                                        singleObject.getString("vehicleType"),
                                        singleObject.getString("vehicleOwner"),
                                        singleObject.getString("fuelType"),
                                        singleObject.getString("stationsId")
                                );
                                joinedQueues.add(queue);
                                Log.e("api", "onResponse: "+   joinedQueues.size());
                            }
                            selectedFuelStation.setId(response.getString("id"));
                            selectedFuelStation.setStationName( response.getString("name"));
                            selectedFuelStation.setLocation(response.getString("location"));
                            selectedFuelStation.setStationOwner( response.getString("stationOwner"));
                            selectedFuelStation.setLastModified(response.getString("lastModified"));
                            selectedFuelStation.setDieselStatus(response.getBoolean("dieselStatus"));
                            selectedFuelStation.setPetrolStatus(response.getBoolean("petrolStatus"));
                            selectedFuelStation.setQueues(joinedQueues);
                            Map<String,Integer> vehicleCounts = vehicleDashboardController.getVehicleCounts(joinedQueues);
                            getFuelStationCountById(id, vehicleCounts);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(jsonObjReq);
        // mRequestQueue.add(jsonObjReqArray[0]);
    }

    private void getFuelStationCountById(String id, Map<String,Integer> vehicleCounts) {

        JSONArray jsonArray = new JSONArray();
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        String URL = Constants.BASE_URL + "/Queue/GetQueueLength?stationId="+id;
        //ArrayList<Queue> joinedQueues = new ArrayList<>();

        JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                URL, jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            System.out.println("Arrayyyy Kasuni: " + response.get(1).toString());
                            System.out.println("Arrayyyy Kasuni: " + vehicleCounts.get(Constants.TOTAL_PETROL_COUNT));

                            if (fuelType.equals(Constants.DIESEL) && vehicleCounts.get(Constants.TOTAL_DIESEL_COUNT) >= Integer.valueOf(response.get(1).toString())) {
                                showAlertDialog(Constants.DIESEL);
                                btnConfirmJoin.setEnabled(false);

                            } else if (fuelType.equals(Constants.PETROL) && vehicleCounts.get(Constants.TOTAL_PETROL_COUNT) >= Integer.valueOf(response.get(0).toString()))
                            {
                                showAlertDialog(Constants.PETROL);
                                btnConfirmJoin.setEnabled(false);
                            }
                            else{
                                btnConfirmJoin.setEnabled(true);
                                addQueueAPI(vehicle, String.valueOf(loggedUser.getId()), fuelStation.getId(), fuelType);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println(vehicleCounts.get(Constants.TOTAL_DIESEL_COUNT));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(jsonObjReq);
    }

    private void showAlertDialog(String fuelType) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(JoinQueue.this);
        alertDialog.setTitle("Warning! ");
        //String[] items = {"Vehicle Owner", "Station Owner"};
       // int checkedItem = 1;
        alertDialog.setMessage(
                "" +
                        "You can not entered to the "+fuelType+"\n " +
                        "Queue, Because queue is full or " + fuelType+
                        " is finished"
        );
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }
}