package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.food_ordering_app.services.FoodService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;


public class AdminFoodActivity extends AppCompatActivity {
    private final FoodService foodService = new FoodService(this);
    private LinearProgressIndicator progressIndicator;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_foods);
        FloatingActionButton addFoodButton = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView_foodList);
        progressIndicator = findViewById(R.id.progressBar);
        foodService.getAllFoods(recyclerView,progressIndicator,"");
        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminFoodActivity.this, EditFoodActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressIndicator.setVisibility(View.VISIBLE);
        foodService.getAllFoods(recyclerView,progressIndicator,"");
    }
}