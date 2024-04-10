package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class OrderActivity extends AppCompatActivity {
    private BottomNavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_orders);
        menu = findViewById(R.id.bottom_navigation);
        menu.setSelectedItemId(R.id.orders);

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
                if (id == R.id.orders) {
                    intent = new Intent(OrderActivity.this, FoodActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}
