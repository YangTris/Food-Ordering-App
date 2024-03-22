package com.example.food_ordering_app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.food_ordering_app.controller.FoodController;
import com.example.food_ordering_app.models.Food;

public class APITestActivity extends AppCompatActivity {
    private Button btnGet;
    private Button btnPost;
    private Button btnPut;
    private Button btnDelete;
    private EditText textID;
    private FoodController foodController = new FoodController(APITestActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_apitest);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textID = findViewById(R.id.textID);
        btnGet = findViewById(R.id.btnGet);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textID.getText().toString().matches("")) {
                    foodController.getAllFoods();
                }else{
                    foodController.getFoodDetails(textID.getText().toString());
                }
            }
        });
        btnPost = findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodController.createFood(new Food("drink","Delicious","TestFood",100,null));
            }
        });
        btnPut = findViewById(R.id.btnPut);
        btnPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodController.updateFood("QaXtFzKpqvugpMwsmbvx",new Food("drink","Delicious","TestFood",100,null));
            }
        });
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodController.deleteFood("QaXtFzKpqvugpMwsmbvx");
            }
        });
    }
}