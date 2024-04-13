package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.food_ordering_app.services.OrderService;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class ShipperOrderActivity extends AppCompatActivity {
    private RecyclerView ordersRecyclerview;
    private OrderService orderService = new OrderService(this);
    private LinearProgressIndicator linearProgressIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipper_orders);
        ordersRecyclerview = findViewById(R.id.recyclerView_orderList);
        linearProgressIndicator=findViewById(R.id.progressBar);
        orderService.getAllOrder(ordersRecyclerview,linearProgressIndicator);
    }

    @Override
    protected void onResume() {
        super.onResume();
        linearProgressIndicator.setVisibility(View.VISIBLE);
        orderService.getAllOrder(ordersRecyclerview,linearProgressIndicator);
    }
}