package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class CategoryActivity extends AppCompatActivity {
    private CardView foodsCard;
    private CardView drinksCard;
    private CardView dessertsCard;
    private CardView breakfastCard;
    private CardView lunchCard;
    private CardView dinnerCard;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        CardView foodsCard = findViewById(R.id.foods);
        CardView drinksCard = findViewById(R.id.drinks);
        CardView dessertsCard = findViewById(R.id.derserts);
        CardView breakfastCard = findViewById(R.id.breakfast);
        CardView lunchCard = findViewById(R.id.lunch);
        CardView dinnerCard = findViewById(R.id.dinner);
        foodsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryActivity.this, FoodActivity.class));
            }
        });
        drinksCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryActivity.this, FoodActivity.class));
            }
        });
        dessertsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryActivity.this, FoodActivity.class));
            }
        });
        breakfastCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryActivity.this, FoodActivity.class));
            }
        });
        lunchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryActivity.this, FoodActivity.class));
            }
        });
        dinnerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategoryActivity.this, FoodActivity.class));
            }
        });
    }
}
