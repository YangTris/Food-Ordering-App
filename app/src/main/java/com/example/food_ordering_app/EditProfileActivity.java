package com.example.food_ordering_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


import com.example.food_ordering_app.models.User;
import com.example.food_ordering_app.services.UserService;
import com.google.android.material.textfield.TextInputEditText;

public class EditProfileActivity extends AppCompatActivity {
    private final UserService userService = new UserService(this);
    private SharedPreferences sharedPreferences;
    private TextInputEditText txtName;
    private TextInputEditText txtEmail;
    private TextInputEditText txtPhone;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        sharedPreferences = getSharedPreferences("sharedPrefKey", Context.MODE_PRIVATE);
        txtName = findViewById(R.id.customer_name);
        txtEmail = findViewById(R.id.customer_mail);
        txtPhone = findViewById(R.id.customer_phone);
        btnSave = findViewById(R.id.save_customer);
        userService.getUserDetails(sharedPreferences.getString("userIdKey",null),txtName,txtEmail,txtPhone);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setUserId(sharedPreferences.getString("userIdKey",null));
                user.setName(txtName.getText().toString());
                user.setEmail(txtEmail.getText().toString());
                user.setPhone(txtPhone.getText().toString());
                user.setAddress(sharedPreferences.getString("addressKey",null));
                user.setPassword(sharedPreferences.getString("passwordKey",null));
                userService.updateUser(user.getUserId(),user);
                finish();
            }
        });
    }
}