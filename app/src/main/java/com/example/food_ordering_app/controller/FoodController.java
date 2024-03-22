package com.example.food_ordering_app.controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.food_ordering_app.models.Food;
import com.example.food_ordering_app.services.ServiceBuilder;
import com.example.food_ordering_app.services.FoodService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodController {
    private FoodService foodService = ServiceBuilder.buildService(FoodService.class);
    private Context context;

    public FoodController(Context context) {
        this.context = context;
    }

    public void responseSuccess(Response response){
        if (response.isSuccessful()) {
            response.body().toString();
        } else if (response.code() == 401) {
            Toast.makeText(context, "Your session has expired", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show();
        }
    }

    public void responseFailure(Throwable throwable){
        if (throwable instanceof IOException) {
            Toast.makeText(context, "A connection error occured", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show();
        }
    }

    public void getAllFoods(){
        Call<ArrayList<Food>> request = foodService.getAllFoods();
        request.enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> request, Response<ArrayList<Food>> response) {
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> request, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void getFoodDetails(String id){
        Call<Food> request = foodService.getFoodDetails(id);
        request.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void createFood(Food food){
        Call<Food> request = foodService.createFood(food);
        request.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void updateFood(String id, Food food){
        Call<Food> request = foodService.updateFood(id,food);
        request.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void deleteFood(String id){
        Call<Void> request = foodService.deleteFood(id);
        request.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                //responseSuccess(response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                responseFailure(t);
            }
        });
    }
}
