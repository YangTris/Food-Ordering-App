package com.example.food_ordering_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import com.example.food_ordering_app.services.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.mancj.materialsearchbar.MaterialSearchBar;

public class AdminUserActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserService userService = new UserService(this);
    private BottomNavigationView adminMenu;
    private LinearProgressIndicator linearProgressIndicator;
    private SharedPreferences sharedPreferences;
    private BottomNavigationView menu;
    private MaterialSearchBar searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_list);
        sharedPreferences = getSharedPreferences("sharedPrefKey", Context.MODE_PRIVATE);
        adminMenu = findViewById(R.id.admin_bottom_navigation);
        adminMenu.setSelectedItemId(R.id.admin_users);
        recyclerView = findViewById(R.id.recyclerView_userList);
        linearProgressIndicator = findViewById(R.id.progressBar);
        searchBar=findViewById(R.id.search_bar);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query=s.toString();
                userService.getAllUsers(recyclerView, linearProgressIndicator,query);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        menu = findViewById(R.id.admin_bottom_navigation);
        menu.getMenu().getItem(2).setChecked(true);
        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                Intent intent;
                if (id == R.id.admin_foods) {
                    intent = new Intent(AdminUserActivity.this, AdminFoodActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.admin_orders) {
                    intent = new Intent(AdminUserActivity.this, AdminOrderActivity.class);
                    startActivity(intent);
                    return true;
                }
                if(id==R.id.admin_payment){
                    intent = new Intent(AdminUserActivity.this, AdminPaymentActivity.class);
                    startActivity(intent);
                    return true;
                }
                if (id == R.id.logout) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent i = new Intent(AdminUserActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
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
        userService.getAllUsers(recyclerView, linearProgressIndicator,"");
    }

}