package com.example.fuelquemanagement_client.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FuelStation implements Serializable {

    private String id;
    private String stationName, location, stationOwner, lastModified;
    private boolean dieselStatus, petrolStatus;

    public FuelStation() {
    }

    public FuelStation(String id, String stationName, String location) {
        this.id = id;
        this.stationName = stationName;
        this.location = location;
    }

    public FuelStation(String id, String stationName, String location, String stationOwner, String lastModified, boolean dieselStatus, boolean petrolStatus) {
        this.id = id;
        this.stationName = stationName;
        this.location = location;
        this.stationOwner = stationOwner;
        this.lastModified = lastModified;
        this.dieselStatus = dieselStatus;
        this.petrolStatus = petrolStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

        stations.add(new FuelStation("1", "A", "Kaduwela"));
        stations.add(new FuelStation("2", "B", "Kaduwela123"));
        stations.add(new FuelStation("3", "C", "Kaduwela12345"));

        return  stations;
    }

    public String getStationOwner() {
        return stationOwner;
    }

    public void setStationOwner(String stationOwner) {
        this.stationOwner = stationOwner;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public boolean isDieselStatus() {
        return dieselStatus;
    }

    public void setDieselStatus(boolean dieselStatus) {
        this.dieselStatus = dieselStatus;
    }

    public boolean isPetrolStatus() {
        return petrolStatus;
    }

    public void setPetrolStatus(boolean petrolStatus) {
        this.petrolStatus = petrolStatus;
    }
}
