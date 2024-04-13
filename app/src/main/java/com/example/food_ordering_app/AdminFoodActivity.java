package com.example.food_ordering_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.food_ordering_app.services.FoodService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.LinearProgressIndicator;


public class AdminFoodActivity extends AppCompatActivity {
    private final FoodService foodService = new FoodService(this);
    private LinearProgressIndicator progressIndicator;
    private RecyclerView recyclerView;
    private BottomNavigationView menu;
    private SharedPreferences sharedPreferences;
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
        menu = findViewById(R.id.bottom_navigation);
        menu.getMenu().getItem(0).setChecked(true);
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Intent intent;
                if (id == R.id.admin_orders) {
                    intent = new Intent(AdminFoodActivity.this, AdminOrderActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.admin_users) {
                    intent = new Intent(AdminFoodActivity.this, AdminUserActivity.class);
                    startActivity(intent);
                    return true;
                }
                if(id == R.id.logout){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent i = new Intent(AdminFoodActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                    return true;
                }
                return false;
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