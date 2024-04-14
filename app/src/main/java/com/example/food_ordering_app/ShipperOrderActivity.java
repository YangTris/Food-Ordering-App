package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.food_ordering_app.services.OrderService;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class ShipperOrderActivity extends AppCompatActivity {
    private RecyclerView ordersRecyclerview;
    private OrderService orderService = new OrderService(this);
    private LinearProgressIndicator linearProgressIndicator;
    private SharedPreferences sharedPreferences;
    private LinearLayout btnLogOut;

    private TextView id, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipper_orders);
        sharedPreferences = getSharedPreferences("sharedPrefKey", Context.MODE_PRIVATE);
        ordersRecyclerview = findViewById(R.id.recyclerView_orderList);
        id = findViewById( R.id.shipper_id);
        name = findViewById(R.id.shipper_name);
        btnLogOut = findViewById(R.id.btnLogOut);
        id.setText("ID:" +sharedPreferences.getString("userIdKey", null));
        name.setText("Name: " + sharedPreferences.getString("usernameKey", null));
        linearProgressIndicator = findViewById(R.id.progressBar);
        orderService.getAllOrder(ordersRecyclerview, linearProgressIndicator);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent i = new Intent(ShipperOrderActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        linearProgressIndicator.setVisibility(View.VISIBLE);
        orderService.getAllOrder(ordersRecyclerview, linearProgressIndicator);
    }
}