package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.food_ordering_app.services.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminUserActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    UserService userService = new UserService(this);
    private BottomNavigationView adminMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_user_list);
        adminMenu = findViewById(R.id.admin_bottom_navigation);
        adminMenu.setSelectedItemId(R.id.admin_users);
        recyclerView = findViewById(R.id.recyclerView_userList);
        userService.getAllUsers(recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        userService.getAllUsers(recyclerView);
    }

}