package com.example.food_ordering_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.food_ordering_app.services.CartService;
import com.example.food_ordering_app.services.FoodService;
import com.example.food_ordering_app.models.Food;

public class APITestActivity extends AppCompatActivity {
    private Button btnGet;
    private Button btnPost;
    private Button btnPut;
    private Button btnDelete;
    private EditText textID;
    private FoodService foodService = new FoodService(APITestActivity.this);
    private CartService cartService = new CartService(APITestActivity.this);

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
                    foodService.getAllFoods();
                }else{
                    foodService.getFoodDetails(textID.getText().toString());
                }
            }
        });
        btnPost = findViewById(R.id.btnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btnPut = findViewById(R.id.btnPut);
        btnPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodService.updateFood("QaXtFzKpqvugpMwsmbvx",new Food("drink","Delicious","TestFood",100,null));
            }
        });
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodService.deleteFood("QaXtFzKpqvugpMwsmbvx");
            }
        });
    }
}