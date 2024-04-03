package com.example.food_ordering_app;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.adapter.AdminFoodAdapter;
import com.example.food_ordering_app.adapter.FoodAdapter;
import com.example.food_ordering_app.models.Food;
import com.example.food_ordering_app.services.ServiceBuilder;
import com.example.food_ordering_app.controllers.FoodController;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Context context = this;
        final RecyclerView recyclerView = findViewById(R.id.recyclerView_foodList);
        FoodAdapter foodAdapter = new FoodAdapter(context, sampleFoodList());
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FoodController foodController = ServiceBuilder.buildService(FoodController.class);
        Call<ArrayList<Food>> request = foodController.getAllFoods();
        request.enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> request, Response<ArrayList<Food>> response) {
                if (response.isSuccessful()) {
                    //recyclerView.setAdapter(new FoodAdapter(FoodActivity.this, response.body()));
                    System.out.println(response.body().toString());
                } else if (response.code() == 401) {
                    Toast.makeText(context, "Your session has expired", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> request, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(context, "A connection error occured", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private ArrayList<Food> sampleFoodList() {
        ArrayList<Food> foodList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String name = "Food " + i;
            String description = "Description " + i;
            double price = 10.99 + i;

            Food food = new Food();
            food.setName(name);
            food.setDescription(description);
            food.setPrice(price);
            foodList.add(food);
        }
        return foodList;
    }
}
