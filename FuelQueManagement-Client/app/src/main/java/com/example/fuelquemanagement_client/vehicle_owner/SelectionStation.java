package com.example.fuelquemanagement_client.vehicle_owner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fuelquemanagement_client.R;
import com.example.fuelquemanagement_client.models.FuelStation;

import java.util.ArrayList;
import java.util.List;

public class SelectionStation extends AppCompatActivity {

    private ListView stationView;
    private List<FuelStation> stations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_station);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select Nearest Station");

        stationView = (ListView)findViewById(R.id.idStationView);
        stations = new ArrayList<>();


        stations = new FuelStation().readAll();

        StationAdapter adapter = new StationAdapter(this , R.layout.single_station, stations);
        stationView.setAdapter(adapter);

    }
}