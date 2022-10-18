package com.example.fuelquemanagement_client.vehicle_owner;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.fuelquemanagement_client.R;
import com.example.fuelquemanagement_client.constants.Constants;
import com.example.fuelquemanagement_client.models.FuelStation;

import java.util.List;


public class StationAdapter extends ArrayAdapter<FuelStation>{

    private Context context;
    private int resource;
    List<FuelStation> stations;

    public StationAdapter(@NonNull Context context, int resource, List<FuelStation> stations) {
        super(context, resource, stations);
        this.context = context;
        this.resource = resource;
        this.stations = stations;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(resource,parent,false);

        //Create objects from the elements that are in the single_task xml file and set their id numbers.
        TextView stationName = row.findViewById((R.id.idStationName));
        TextView StationLocation = row.findViewById((R.id.idLocation));

        final FuelStation station = stations.get(position); //Get the row details of the task records inside the Task table in the database

        //Set details of that Task record into the text views in the single_task xml file
        stationName.setText(station.getStationName());
        StationLocation.setText(station.getLocation());

        stationName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Reload the same interface after delete a task
                Intent myIntent = new Intent(context,VehicleOwnerDashboard.class);
                myIntent.putExtra(Constants.STATION, station);
                context.startActivity(myIntent);

            }
        });

        return row;

    }

    public StationAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
