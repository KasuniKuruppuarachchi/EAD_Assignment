package com.example.fuelquemanagement_client.SSL;

import com.example.fuelquemanagement_client.models.FuelStation;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = " http://127.0.0.1:5000";
    @GET("/name")
    Call<List<FuelStation>> loadStations();
}