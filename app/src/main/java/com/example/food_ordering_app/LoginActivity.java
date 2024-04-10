package com.example.food_ordering_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.food_ordering_app.models.User;
import com.example.food_ordering_app.services.UserService;
import com.google.android.material.textfield.TextInputLayout;


public class LoginActivity extends AppCompatActivity {
    private TextInputLayout usernameInput;
    private TextInputLayout passwordInput;
    private Button loginButton;
    private TextView registerButton;
    private UserService userService = new UserService(this);

    // Dummies:
    User customer = new User("0", "John Doe", 0, "123 Main St", "1234567890", "john@example.com", "cus");

    // Creating an admin
    User admin = new User("1", "Admin User", 1, "456 Admin St", "0987654321", "admin@example.com", "admin");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.sign_up);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameInput.getEditText().getText().toString().trim();
                String password = passwordInput.getEditText().getText().toString().trim();
                userService.loginUser(username,password);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Authentication.class));
            }
        });

    }
}
