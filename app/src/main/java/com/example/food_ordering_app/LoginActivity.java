package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.food_ordering_app.models.User;
import com.example.food_ordering_app.services.FoodService;
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
                Log.d("Username", username);
                Log.d("Password", password);
                userService.loginUser(username,password);

                if (username.equals("user") && password.equals("user")) {
                    startActivity(new Intent(LoginActivity.this, CategoryActivity.class));
                }
//                else {
//                    Toast.makeText(getApplicationContext(), "Incorrect information, please check again", Toast.LENGTH_SHORT).show();
//                }
                if (username.equals("admin") && password.equals("admin")) {
                    startActivity(new Intent(LoginActivity.this, AdminFoodActivity.class));
                }
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
