package com.example.fuelquemanagement_client.vehicle_owner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.AtomicFile;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fuelquemanagement_client.R;
import com.example.fuelquemanagement_client.SSL.RetrofitClient;
import com.example.fuelquemanagement_client.constants.Constants;
import com.example.fuelquemanagement_client.models.Fuel;
import com.example.fuelquemanagement_client.models.FuelStation;
import com.example.fuelquemanagement_client.models.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.Retrofit;

public class SelectionStation extends AppCompatActivity {

    private ListView stationView;
    private ArrayList<FuelStation> stations;
    private RequestQueue mRequestQueue;
    private User loggedUser;
    String api = "http://192.168.8.118:5000/FuelStation";
    //String api = "https://jsonplaceholder.typicode.com/photos";

    private static final String TAG = "AndroidRESTClientActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_station);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select Nearest Station");

        stationView = (ListView)findViewById(R.id.idStationView);
        stations = new ArrayList<FuelStation>();
        loadStations();






    }

     private void loadStations() {


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                       // textView.setText("Response is: " + response.substring(0,500));
                   //     System.out.println("Response is: " + response.substring(0,500));
                        try {
                            JSONArray array = new JSONArray(response);
                            int length = array.length();
                            for(int i=0;i<array.length();i++){
                                JSONObject singleObject = array.getJSONObject(i);
                                Log.e("api", "onResponse: "+   singleObject.getString("id"));
                                FuelStation fuelStation = new FuelStation(
                                        singleObject.getString("id"),
                                        singleObject.getString("name"),
                                        singleObject.getString("location"),
                                        singleObject.getString("stationOwner"),
                                        singleObject.getString("lastModified"),
                                        singleObject.getBoolean("dieselStatus"),
                                        singleObject.getBoolean("petrolStatus")
                                );
                                stations.add(fuelStation);
                                Log.e("api", "onResponse: "+   stations.size());
                            }

                            Log.e("api", "onResponse: "+stations.size());
                            loggedUser = (User) getIntent().getSerializableExtra(Constants.LOGGED_USER);
                            StationAdapter adapter = new StationAdapter(SelectionStation.this , R.layout.single_station, stations,loggedUser);
                            stationView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
                System.out.println("That didn't work!");
                System.out.println("That didn't work! +" + error.getLocalizedMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);



    }
}