package com.example.food_ordering_app.services;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.example.food_ordering_app.R;

public class AdminFoodActivity extends AppCompatActivity {

    private ArrayAdapter<String> adapter;
    private String[] items = {"Catgory 1", "Catgory 2", "Catgory 3", "Catgory 4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit_food); // Set your layout here

        // Assuming you have a LinearLayout with id 'containerLayout' and AutoCompleteTextView with id 'textField'
        LinearLayout containerLayout = findViewById(R.id.edit_food);
        AutoCompleteTextView textField = findViewById(R.id.food_autocomplete);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items);
        textField.setAdapter(adapter);
    }
}