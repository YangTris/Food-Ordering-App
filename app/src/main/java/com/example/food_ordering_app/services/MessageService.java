package com.example.food_ordering_app.services;


import retrofit2.Call;
import retrofit2.http.GET;

public interface MessageService {
    @GET("messages")
    Call<String> getMessages();
}
