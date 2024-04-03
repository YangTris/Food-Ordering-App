package com.example.food_ordering_app.controllers;

import com.example.food_ordering_app.models.map.MapInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapController {
    @GET("json")
    Call<MapInfo> getDirection(@Query("origin") String origin,
                               @Query("destination") String destination,
                               @Query("key") String key);
}
