package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.food_ordering_app.models.map.AddressHelper;
import com.example.food_ordering_app.models.User;
import com.example.food_ordering_app.services.UserService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText phone;
    private TextInputEditText name;
    private TextInputEditText password;
    private AutoCompleteTextView city;
    private AutoCompleteTextView ward;
    private AutoCompleteTextView district;
    private TextInputEditText houseNumber;
    private TextView loginButton;

    private Button registerButton;
    private ArrayAdapter<String> cityAdapter;
    private ArrayAdapter<String> wardAdapter;
    private ArrayAdapter<String> districtAdapter;
    private ArrayList<String> wards = null;

    private AddressHelper addressHelper = new AddressHelper();

    UserService userService = new UserService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phone = findViewById(R.id.register_phone);
        name = findViewById(R.id.register_name);
        password = findViewById((R.id.register_password));
        city = findViewById(R.id.register_city);
        ward = findViewById(R.id.register_ward);
        district = findViewById(R.id.register_district);
        houseNumber = findViewById(R.id.register_house_number);
        registerButton = findViewById(R.id.register_button);
        loginButton = findViewById(R.id.sign_in);


        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, addressHelper.getCities());
        districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, addressHelper.getDistrictsByCity("Hồ Chí Minh"));


        city.setAdapter(cityAdapter);
        district.setAdapter(districtAdapter);


        district.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                wards = addressHelper.getWardsByDistrict(districtAdapter.getItem(i));
                wardAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_dropdown_item_1line, wards);
                ward.setAdapter(wardAdapter);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle == null) {
            phone.setText("0774600374");
        } else {
            // Display phone number again
            phone.setText(bundle.get("phoneNumber").toString());
        }
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get user data from register layout and assign to a new user
                User newUser = new User();
                // Phone (from authentication screen)
                Intent i = getIntent();
                Bundle bundle = i.getExtras();


                if (bundle == null) {
                    newUser.setPhone("0774600374");
                } else {
                    // Set phone number for new user
                    newUser.setPhone(bundle.get("phoneNumber").toString());
                }

                // Set id
                // TODO: Auto-generated id
                newUser.setUserId("19uasihgid8a7wdasdihasuhdiu");

                //Set name
                newUser.setName(String.valueOf(name.getText()));

                // Set password
                newUser.setPassword(String.valueOf(password.getText()));

                // Set email
                newUser.setEmail("phamphukhanh.sgu.edu@gmail.com");
                // Set address
                String address = houseNumber.getText() + ", " + ward.getText() + ", " + district.getText() + ", " + city.getText();
                newUser.setAddress(address);
                newUser.setRoleId(0);

                // Create new user in Firebase
                userService.createCustomer(newUser);
                Intent categoryIntent = new Intent(RegisterActivity.this, CategoryActivity.class);
                categoryIntent.putExtra("name", String.valueOf(name.getText()));
                startActivity(categoryIntent);
            }
        });
    }
}