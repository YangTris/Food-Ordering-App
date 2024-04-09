package com.example.food_ordering_app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.food_ordering_app.services.FoodService;


public class FoodActivity extends AppCompatActivity {
    private final FoodService foodService = new FoodService(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_foodList);
        foodService.getAllFoods(recyclerView,"");
    }
}
