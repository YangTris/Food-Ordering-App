package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.food_ordering_app.services.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class AdminUserActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserService userService = new UserService(this);
    private BottomNavigationView adminMenu;
    private LinearProgressIndicator linearProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_list);
        adminMenu = findViewById(R.id.admin_bottom_navigation);
        adminMenu.setSelectedItemId(R.id.admin_users);
        recyclerView = findViewById(R.id.recyclerView_userList);
        linearProgressIndicator = findViewById(R.id.progressBar);
        userService.getAllUsers(recyclerView,linearProgressIndicator);
    }

    @Override
    protected void onResume() {
        super.onResume();
        linearProgressIndicator.setVisibility(View.VISIBLE);
        userService.getAllUsers(recyclerView,linearProgressIndicator);
    }

}