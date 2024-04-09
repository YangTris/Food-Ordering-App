package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class CategoryActivity extends AppCompatActivity {
    private CardView foodsCard;
    private CardView drinksCard;
    private CardView dessertsCard;
    private CardView breakfastCard;
    private CardView lunchCard;
    private CardView dinnerCard;
    private TextView welcomeText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        foodsCard = findViewById(R.id.foods);
        drinksCard = findViewById(R.id.drinks);
        dessertsCard = findViewById(R.id.derserts);
        breakfastCard = findViewById(R.id.breakfast);
        lunchCard = findViewById(R.id.lunch);
        dinnerCard = findViewById(R.id.dinner);
        welcomeText = findViewById(R.id.welcome_text);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        welcomeText.setText("Welcome, " + bundle.get("name").toString());
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
