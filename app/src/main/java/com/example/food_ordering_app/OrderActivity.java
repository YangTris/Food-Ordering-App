package com.example.food_ordering_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.services.OrderService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class OrderActivity extends AppCompatActivity {
    private BottomNavigationView menu;
    private final OrderService orderService = new OrderService(this);
    private SharedPreferences sharedPreferences;
    private RecyclerView recyclerView;
    private LinearProgressIndicator linearProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_orders);
        linearProgressIndicator = findViewById(R.id.progressBar);
        menu = findViewById(R.id.bottom_navigation);
        recyclerView = findViewById(R.id.recyclerView_orderList);
        sharedPreferences = getSharedPreferences("sharedPrefKey", Context.MODE_PRIVATE);
        orderService.getAllOrder(sharedPreferences.getString("userIdKey",null),recyclerView,linearProgressIndicator);

        menu.getMenu().getItem(2).setChecked(true);
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Intent intent;
                if (id == R.id.cart) {
                    intent = new Intent(OrderActivity.this, CartActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.home) {
                    intent = new Intent(OrderActivity.this, FoodActivity.class);
                    startActivity(intent);
                    return true;
                }
                if(id == R.id.profile){
                    intent = new Intent(OrderActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        linearProgressIndicator.setVisibility(View.VISIBLE);
        orderService.getAllOrder(sharedPreferences.getString("userIdKey",null),recyclerView,linearProgressIndicator);
    }
}
