package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.services.FoodService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.mancj.materialsearchbar.MaterialSearchBar;


public class FoodActivity extends AppCompatActivity {
    private final FoodService foodService = new FoodService(this);
    private BottomNavigationView menu;
    private LinearProgressIndicator progressIndicator;
    private RecyclerView recyclerView;
    private MaterialSearchBar searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        progressIndicator = findViewById(R.id.progressBar);
        menu = findViewById(R.id.bottom_navigation);
        menu.getMenu().getItem(0).setChecked(true);
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Intent intent;
                if (id == R.id.cart) {
                    intent = new Intent(FoodActivity.this, CartActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.profile) {
                    intent = new Intent(FoodActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                }
                if(id == R.id.orders){
                    intent = new Intent(FoodActivity.this,OrderActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        recyclerView = findViewById(R.id.recyclerView_foodList);
        searchBar=findViewById(R.id.search_bar);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query= s.toString();
                Log.d("onTextChanged ", query);
                foodService.getAllFoods(recyclerView, progressIndicator, query);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        menu.getMenu().getItem(0).setChecked(true);
        progressIndicator.setVisibility(View.VISIBLE);
        foodService.getAllFoods(recyclerView,progressIndicator,"");
    }
}
