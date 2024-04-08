package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.food_ordering_app.services.FoodService;
import com.google.android.material.textfield.TextInputEditText;

public class FoodDetailActivity extends AppCompatActivity {
    private final FoodService foodService = new FoodService(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail);
        TextInputEditText txtFoodName = findViewById(R.id.detail_food_name);
        TextInputEditText txtFoodDes = findViewById(R.id.detail_food_description);
        TextInputEditText txtFoodPrice = findViewById(R.id.detail_food_price);
        ImageView imageView = findViewById(R.id.detail_food_img);
        Button btnAddToCart = findViewById(R.id.add_to_cart_button);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        String foodId = bundle.get("foodId").toString();
        foodService.getFoodDetails(foodId,txtFoodName,txtFoodDes,txtFoodPrice,null,imageView,btnAddToCart);
    }
}