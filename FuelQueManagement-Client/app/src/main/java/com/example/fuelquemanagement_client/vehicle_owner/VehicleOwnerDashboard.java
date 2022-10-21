package com.example.fuelquemanagement_client.vehicle_owner;

import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.widget.Button;
import android.graphics.Color;
import android.content.Intent;
import android.widget.TextView;
import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fuelquemanagement_client.R;
import com.example.fuelquemanagement_client.models.User;
import com.example.fuelquemanagement_client.models.Queue;
import com.example.fuelquemanagement_client.constants.Constants;
import com.example.fuelquemanagement_client.models.FuelStation;
import com.example.fuelquemanagement_client.vehicle_owner.controllers.vehicleDashboardController;

public class VehicleOwnerDashboard extends AppCompatActivity implements View.OnClickListener {

    private User loggedUser;
    private FuelStation fuelStation;
    private TextView txtPetrolStatus;
    private TextView txtDieselStatus;
    private FuelStation selectedFuelStation = new FuelStation();
    private CardView cardPetrolStatus, cardDieselStatus;
    private TextView txtVanCountDiesel, txtVanCountPetrol;
    private TextView txtCarCountDiesel, txtCarCountPetrol;
    private TextView txtBusCountDiesel, txtBusCountPetrol;
    private TextView txt3WheelCountDiesel, txt3WheelCountPetrol;
    private TextView txtMotorBikeCountDiesel, txtMotorBikeCountPetrol;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_join:
                Intent i = new Intent(VehicleOwnerDashboard.this, JoinQueue.class);
                i.putExtra(Constants.LOGGED_USER, loggedUser);
                i.putExtra(Constants.STATION, fuelStation);
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
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // To retrieve object in second Activity
        fuelStation = (FuelStation) getIntent().getSerializableExtra(Constants.STATION);
        getSupportActionBar().setTitle(fuelStation.getStationName() + " - " + fuelStation.getLocation());
        loggedUser = (User) getIntent().getSerializableExtra(Constants.LOGGED_USER);

        getFuelStationById(fuelStation.getId());

        Button btnJoinQueue = findViewById(R.id.btn_join);
        btnJoinQueue.setOnClickListener(this);

        txtPetrolStatus = findViewById(R.id.txt_petrolStatus);
        txtDieselStatus = findViewById(R.id.txt_dieselStatus);

        cardPetrolStatus = findViewById(R.id.card_petrolStatus);
        cardDieselStatus = findViewById(R.id.card_dieselStatus);

        txtMotorBikeCountDiesel = findViewById(R.id.txt_bikeCountDiesel);
        txtMotorBikeCountPetrol = findViewById(R.id.txt_bikeCountPetrol);

        txtVanCountDiesel = findViewById(R.id.txt_vanCountDiesel);
        txtVanCountPetrol = findViewById(R.id.txt_vanCountPetrol);

        txt3WheelCountDiesel = findViewById(R.id.txt_3wheelCountDiesel);
        txt3WheelCountPetrol = findViewById(R.id.txt_3wheelCountPetrol);

        txtBusCountDiesel = findViewById(R.id.txt_bussLorryCountDiesel);
        txtBusCountPetrol = findViewById(R.id.txt_bussLorryCountPetrol);

        txtCarCountDiesel = findViewById(R.id.txt_carCountDiesel);
        txtCarCountPetrol = findViewById(R.id.txt_carCountPetrol);

        setFuelStatsInView();

    }

    //Change the state of the fuel ( Available / Finish ) in the view according to the selected station's details
    private void setFuelStatsInView() {
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
    }

    // Get the selected station's object through the id via api call from remote database
    private void getFuelStationById(String id) {

        JSONObject jsonObject = new JSONObject();
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        String URL = Constants.BASE_URL + "/FuelStation/"+id;
        ArrayList<Queue> joinedQueues = new ArrayList<>();

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
                            setQueueCountsInTexts(vehicleCounts);

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

    //Set the count of each queues texts in the view according tot eh fuel type and vehicle type
    private void setQueueCountsInTexts(Map<String,Integer> vehicleCounts) {

        txtMotorBikeCountDiesel.setText(String.valueOf(vehicleCounts.get(Constants.MOTOR_BIKE+Constants.DIESEL)));
        txtMotorBikeCountPetrol.setText(String.valueOf(vehicleCounts.get(Constants.MOTOR_BIKE+Constants.PETROL)));

        txtVanCountDiesel.setText(String.valueOf(vehicleCounts.get(Constants.VAN+Constants.DIESEL)));
        txtVanCountPetrol.setText(String.valueOf(vehicleCounts.get(Constants.VAN+Constants.PETROL)));

        txt3WheelCountDiesel.setText(String.valueOf(vehicleCounts.get(Constants.THREE_WHEEL+Constants.DIESEL)));
        txt3WheelCountPetrol.setText(String.valueOf(vehicleCounts.get(Constants.THREE_WHEEL+Constants.PETROL)));

        txtCarCountDiesel.setText(String.valueOf(vehicleCounts.get(Constants.CAR+Constants.DIESEL)));
        txtCarCountPetrol.setText(String.valueOf(vehicleCounts.get(Constants.CAR+Constants.PETROL)));

        txtBusCountDiesel.setText(String.valueOf(vehicleCounts.get(Constants.BUS+Constants.DIESEL)));
        txtBusCountPetrol.setText(String.valueOf(vehicleCounts.get(Constants.BUS+Constants.PETROL)));
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