package com.example.food_ordering_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.food_ordering_app.services.OrderService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class AdminOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BottomNavigationView menu;
    private OrderService orderService = new OrderService(this);
    private LinearProgressIndicator linearProgressIndicator;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_orders);
        sharedPreferences = getSharedPreferences("sharedPrefKey", Context.MODE_PRIVATE);
        recyclerView = findViewById(R.id.recyclerView_orderFoodList);
        linearProgressIndicator = findViewById(R.id.progressBar);
        orderService.getAllOrder(recyclerView, linearProgressIndicator);

        menu = findViewById(R.id.admin_bottom_navigation);
        menu.getMenu().getItem(1).setChecked(true);
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Intent intent;
                if (id == R.id.admin_foods) {
                    intent = new Intent(AdminOrderActivity.this, AdminFoodActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.admin_users) {
                    intent = new Intent(AdminOrderActivity.this, AdminUserActivity.class);
                    startActivity(intent);
                    return true;
                }
                if(id==R.id.admin_payment){
                    intent = new Intent(AdminOrderActivity.this, AdminPaymentActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.logout) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent i = new Intent(AdminOrderActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}