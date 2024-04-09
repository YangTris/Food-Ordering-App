package com.example.food_ordering_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.services.CartService;

public class CartActivity extends AppCompatActivity {
    private final CartService cartService = new CartService(this);
    private Button orderButton;
    private TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_cart);
        orderButton = findViewById(R.id.btnOrderCart);
        txtTotal = findViewById(R.id.total);
        RecyclerView recyclerView = findViewById(R.id.recyclerView_cartList);
        cartService.getUserCart("123456",recyclerView,txtTotal);
    }
}