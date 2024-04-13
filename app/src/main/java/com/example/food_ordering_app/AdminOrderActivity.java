package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.food_ordering_app.services.OrderService;
import com.example.food_ordering_app.services.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class AdminOrderActivity extends AppCompatActivity {
    private BottomNavigationView adminMenu;
    private RecyclerView recyclerView;
    private OrderService orderService = new OrderService(this);
    private LinearProgressIndicator linearProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_all_orders);
        adminMenu = findViewById(R.id.admin_bottom_navigation);
        adminMenu.setSelectedItemId(R.id.admin_orders);
        recyclerView = findViewById(R.id.recyclerView_userList);
//        orderService.getAllOrder(recyclerView, );
    }
}