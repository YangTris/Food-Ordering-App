package com.example.food_ordering_app.services;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapServiceBuilder {

    private static final String URL = "https://maps.googleapis.com/maps/api/directions/";

    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        if(retrofit==null){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

}
