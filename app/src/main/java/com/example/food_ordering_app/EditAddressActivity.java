package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.food_ordering_app.models.User;
import com.example.food_ordering_app.models.map.AddressHelper;
import com.example.food_ordering_app.services.UserService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class EditAddressActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private AutoCompleteTextView city;
    private AutoCompleteTextView district;
    private AutoCompleteTextView ward;
    private ArrayList<String> wards;
    private ArrayAdapter<String> cityAdapter;
    private ArrayAdapter<String> wardAdapter;
    private ArrayAdapter<String> districtAdapter;
    private TextInputEditText houseNumber;
    private TextView currentAddress;
    private Button saveButton;
    private UserService userService = new UserService(this);
    private AddressHelper addressHelper = new AddressHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_address);
        city = findViewById(R.id.edit_city);
        district = findViewById(R.id.edit_district);
        ward = findViewById(R.id.edit_ward);
        currentAddress = findViewById(R.id.current_address);
        saveButton = findViewById(R.id.save_address);

        sharedPreferences = getSharedPreferences("sharedPrefKey", Context.MODE_PRIVATE);
        String address = sharedPreferences.getString("addressKey", null).toString();
        String userId = sharedPreferences.getString("userIdKey", null).toString();
        currentAddress.setText(address);
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, addressHelper.getCities());
        districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, addressHelper.getDistrictsByCity("Hồ Chí Minh"));

        city.setAdapter(cityAdapter);
        district.setAdapter(districtAdapter);

        district.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                wards = addressHelper.getWardsByDistrict(districtAdapter.getItem(i));
                wardAdapter = new ArrayAdapter<>(EditAddressActivity.this, android.R.layout.simple_dropdown_item_1line, wards);
                ward.setAdapter(wardAdapter);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = city + " " + district + " " + ward;
                User user = new User();
                user.setAddress(address);
                userService.updateUser(userId, user);
            }
        });
    }
}