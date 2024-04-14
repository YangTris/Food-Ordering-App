package com.example.food_ordering_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.services.CartService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class CartActivity extends AppCompatActivity {
    private final CartService cartService = new CartService(this);
    private Button orderButton;
    private Button clearCart;
    private TextView txtTotal;
    private SharedPreferences sharedPreferences;
    private BottomNavigationView menu;
    private RecyclerView recyclerView;
    private LinearProgressIndicator linearProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_cart);
        clearCart = findViewById(R.id.btnClearCart);
        orderButton = findViewById(R.id.btnOrderCart);
        txtTotal = findViewById(R.id.total);
        menu = findViewById(R.id.bottom_navigation);
        linearProgressIndicator = findViewById(R.id.progressBar);
        menu.getMenu().getItem(1).setChecked(true);
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Intent intent;
                if(id == R.id.home) {
                    intent = new Intent(CartActivity.this, FoodActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.orders) {
                    intent = new Intent(CartActivity.this, OrderActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.profile) {
                    intent = new Intent(CartActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        recyclerView = findViewById(R.id.recyclerView_cartList);
        sharedPreferences = getSharedPreferences("sharedPrefKey", Context.MODE_PRIVATE);
        cartService.getUserCart(sharedPreferences.getString("userIdKey",null),recyclerView,txtTotal,orderButton,linearProgressIndicator);
        clearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartService.deleteCart(sharedPreferences.getString("userIdKey",null));
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        linearProgressIndicator.setVisibility(View.VISIBLE);
        cartService.getUserCart(sharedPreferences.getString("userIdKey",null),recyclerView,txtTotal,orderButton,linearProgressIndicator);
    }
}