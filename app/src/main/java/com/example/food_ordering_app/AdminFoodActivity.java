package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.example.food_ordering_app.adapter.AdminFoodAdapter;
import com.example.food_ordering_app.models.Food;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AdminFoodActivity extends AppCompatActivity {
    private FloatingActionButton addFoodButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_foods); // Set your layout here
        addFoodButton = findViewById(R.id.fab);

        RecyclerView rvFoods = (RecyclerView) findViewById(R.id.recyclerView_foodList);

        AdminFoodAdapter foodAdapter = new AdminFoodAdapter(sampleFoodList());

        rvFoods.setAdapter(foodAdapter);

        rvFoods.setLayoutManager(new LinearLayoutManager(this));
        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminFoodActivity.this, EditFoodActivity.class));
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