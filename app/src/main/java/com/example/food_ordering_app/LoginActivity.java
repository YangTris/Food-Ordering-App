package com.example.food_ordering_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.food_ordering_app.models.User;
import com.example.food_ordering_app.services.UserService;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;


public class LoginActivity extends AppCompatActivity {
    private TextInputLayout usernameInput;
    private TextInputLayout passwordInput;
    private Button loginButton;
    private TextView registerButton;
    private UserService userService = new UserService(this);

    private CircularProgressIndicator circularProgressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput = findViewById(R.id.username);
        passwordInput = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.sign_up);
        circularProgressIndicator = findViewById(R.id.progress_circular);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circularProgressIndicator.setVisibility(View.VISIBLE);
                String username = usernameInput.getEditText().getText().toString().trim();
                String password = passwordInput.getEditText().getText().toString().trim();
                userService.loginUser(username,password,circularProgressIndicator);
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
