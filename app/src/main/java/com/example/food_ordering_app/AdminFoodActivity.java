package com.example.food_ordering_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.food_ordering_app.adapter.AdminFoodAdapter;
import com.example.food_ordering_app.adapter.FoodAdapter;
import com.example.food_ordering_app.models.Food;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminFoodActivity extends AppCompatActivity {
    private StorageReference storageRef;
    private FirebaseStorage storage;
    private FloatingActionButton addFoodButton;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_foods);
        addFoodButton = findViewById(R.id.fab);

        // Retrieve data from Firebase
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        firestore = FirebaseFirestore.getInstance();

        final Context context = this;
        final RecyclerView recyclerView = findViewById(R.id.recyclerView_foodList);
        AdminFoodAdapter foodAdapter = new AdminFoodAdapter(context, sampleFoodList());
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminFoodActivity.this, EditFoodActivity.class));
            }
        });
    }

    private List<Food> sampleFoodList() {
        List<Food> foodList = new ArrayList<>();
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