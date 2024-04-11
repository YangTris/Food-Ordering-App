package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.food_ordering_app.services.OrderService;

public class ShipperOrderActivity extends AppCompatActivity {
    RecyclerView ordersRecyclerview;
    OrderService orderService = new OrderService(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String sampleUserId = "hkkBz8WdHEyq5Qc0asTK";
        setContentView(R.layout.shipper_orders);
        ordersRecyclerview = findViewById(R.id.recyclerView_orderList);
        orderService.getAllOrder(sampleUserId, ordersRecyclerview);


    }
}