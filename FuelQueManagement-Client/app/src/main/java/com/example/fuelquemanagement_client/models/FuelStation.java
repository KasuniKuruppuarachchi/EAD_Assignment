package com.example.fuelquemanagement_client.models;

import java.util.ArrayList;
import java.util.List;

public class FuelStation {

    private int id;
    private String stationName, location;

    public FuelStation() {
    }

    public FuelStation(int id, String stationName, String location) {
        this.id = id;
        this.stationName = stationName;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<FuelStation> readAll(){
        List<FuelStation> stations = new ArrayList<>();

        stations.add(new FuelStation(1, "A", "Kaduwela"));
        stations.add(new FuelStation(2, "B", "Kaduwela123"));
        stations.add(new FuelStation(3, "C", "Kaduwela12345"));

        return  stations;
    }
}
