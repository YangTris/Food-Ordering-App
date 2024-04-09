package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText username;
    private TextInputEditText password;
    private AutoCompleteTextView city;
    private AutoCompleteTextView ward;
    private AutoCompleteTextView district;
    private TextInputEditText houseNumber;
    private TextView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.register_username);
        password = findViewById((R.id.register_password));
        city = findViewById(R.id.register_city);
        ward = findViewById(R.id.register_ward);
        district = findViewById(R.id.register_district);
        houseNumber = findViewById(R.id.register_house_number);
        loginButton = findViewById(R.id.sign_in);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });


    }
}