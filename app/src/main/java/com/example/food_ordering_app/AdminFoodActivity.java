package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.food_ordering_app.services.FoodService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class AdminFoodActivity extends AppCompatActivity {
    private final FoodService foodService = new FoodService(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_foods);
        FloatingActionButton addFoodButton = findViewById(R.id.fab);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_foodList);
        foodService.getAllFoods(recyclerView);
        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminFoodActivity.this, EditFoodActivity.class));
            }
        });
    }
}