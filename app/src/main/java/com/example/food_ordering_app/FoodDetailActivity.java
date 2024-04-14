package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.food_ordering_app.services.FoodService;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

public class FoodDetailActivity extends AppCompatActivity {
    private final FoodService foodService = new FoodService(this);
    private CircularProgressIndicator circularProgressIndicator;
    private Integer count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail);
        TextInputEditText txtFoodName = findViewById(R.id.detail_food_name);
        TextInputEditText txtFoodDes = findViewById(R.id.detail_food_description);
        TextInputEditText txtFoodPrice = findViewById(R.id.detail_food_price);
        ImageView imageView = findViewById(R.id.detail_food_img);
        Button btnAddToCart = findViewById(R.id.add_to_cart_button);
        circularProgressIndicator = findViewById(R.id.progress_circular);
        Button addBtn = findViewById(R.id.addBtn);
        Button minusBtn = findViewById(R.id.removeBtn);
        EditText text = findViewById(R.id.itemQuanEt);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                text.setText(String.valueOf(count));
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count>=2){
                    count--;
                    text.setText(String.valueOf(count));
                }
            }
        });
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        String foodId = bundle.get("foodId").toString();
        foodService.getFoodDetails(foodId,txtFoodName,txtFoodPrice,txtFoodDes,null,imageView,text,btnAddToCart,circularProgressIndicator);
    }
}