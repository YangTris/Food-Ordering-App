package com.example.food_ordering_app;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.food_ordering_app.models.Order;
import com.example.food_ordering_app.services.OrderService;

import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {
    private AutoCompleteTextView orderStatus;
    private TextView orderId;
    private TextView receiver;
    private TextView address;
    private TextView total;
    private RecyclerView orderItems;
    private TextView paymentStatus;
    private View deliveringStatus;
    private View deliveredStatus;
    private Button navigate;
    private final OrderService orderService = new OrderService(this);
    private SharedPreferences sharedPreferences;
    private String orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.shipper_order_detail);
        orderId = findViewById(R.id.order_detail_id);
        total = findViewById(R.id.order_detail_total);
        receiver = findViewById(R.id.order_detail_customer);
        address = findViewById(R.id.order_detail_address);
        navigate = findViewById(R.id.navigate);
        deliveringStatus = findViewById(R.id.progress_delivering);
        deliveredStatus = findViewById(R.id.progress_delivered);
        orderItems = findViewById(R.id.recyclerView_orderFoodList);
        paymentStatus = findViewById(R.id.payment_status);

        sharedPreferences = getSharedPreferences("sharedPrefKey", Context.MODE_PRIVATE);
        int roleId = sharedPreferences.getInt("roleIdKey", 0);
        Log.d("ROLE", String.valueOf(roleId));
        if (roleId == 0 || roleId == 2) {
            navigate.setVisibility(View.INVISIBLE);
        }
        Intent i = getIntent();
        Bundle b = i.getExtras();
        orderID = b.get("orderId").toString();
        orderId.setText("Order ID:" + orderID);
        orderService.getOrder(orderID, receiver, address,paymentStatus, orderItems, total, deliveringStatus, deliveredStatus, navigate);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent intent = null;
                if(roleId == 0){
                    intent = new Intent(OrderDetailActivity.this,OrderActivity.class);
                } else if(roleId == 1){
                    intent = new Intent(OrderDetailActivity.this,ShipperOrderActivity.class);
                } else if (roleId == 2){
                    intent = new Intent(OrderDetailActivity.this,AdminOrderActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        orderService.getOrder(orderID, receiver, address,paymentStatus, orderItems, total, deliveringStatus, deliveredStatus, navigate);
    }
}