package com.example.food_ordering_app.controllers;


import com.example.food_ordering_app.models.Food;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FoodController {
    @GET("food")
    Call<ArrayList<Food>> getAllFoods();

    @GET("food/{id}")
    Call<Food> getFoodDetails(@Path("id") String id);

    @POST("food")
    Call<Food> createFood(@Body Food food);

    @PUT("food/{id}")
    Call<Food> updateFood(@Path("id") String id, @Body Food food);

    @DELETE("food/{id}")
    Call<String> deleteFood(@Path("id") String id);
}
