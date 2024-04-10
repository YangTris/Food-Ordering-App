package com.example.food_ordering_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class ProfileActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private RelativeLayout btnEditProfile;
    private RelativeLayout btnEditAddress;
    private LinearLayout btnLogOut;
    private TextView profileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPreferences = getSharedPreferences("sharedPrefKey", Context.MODE_PRIVATE);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnEditAddress = findViewById(R.id.btnEditAddress);
        btnLogOut = findViewById(R.id.btnLogOut);
        profileName = findViewById(R.id.profile_name);
        profileName.setText(sharedPreferences.getString("usernameKey",null));
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,EditProfileActivity.class);
                startActivity(i);
            }
        });
        btnEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent i = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        profileName.setText(sharedPreferences.getString("usernameKey",null));
    }
}