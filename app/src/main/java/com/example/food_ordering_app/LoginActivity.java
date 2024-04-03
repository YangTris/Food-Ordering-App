package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;


public class LoginActivity extends AppCompatActivity {
    private TextInputLayout usernameInput;
    private TextInputLayout passwordInput;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameInput.getEditText().getText().toString().trim();
                String password = passwordInput.getEditText().getText().toString().trim();
                Log.d("Username", username);
                Log.d("Password", password);
                if (username.equals("user") && password.equals("user")) {
                    startActivity(new Intent(LoginActivity.this, CategoryActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect information, please check again", Toast.LENGTH_SHORT).show();
                }
                if (username.equals("test") && password.equals("test")) {
                    Log.d("key", BuildConfig.MAPS_API_KEY);
                    startActivity(new Intent(LoginActivity.this, MapsActivity.class));
                }
            }
        });

    }
}
