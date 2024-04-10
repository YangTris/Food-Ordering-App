package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.food_ordering_app.services.FoodService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class FoodActivity extends AppCompatActivity {
    private final FoodService foodService = new FoodService(this);
    private BottomNavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        menu = findViewById(R.id.bottom_navigation);

        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Intent intent;
                if(id == R.id.cart) {
                    intent = new Intent(FoodActivity.this, CartActivity.class);
                    startActivity(intent);
                    return true;
                }
                if(id == R.id.home){
                    Log.d("case","home");
                    return true;
                }
                return false;
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView_foodList);
        foodService.getAllFoods(recyclerView,"");
    }
}
