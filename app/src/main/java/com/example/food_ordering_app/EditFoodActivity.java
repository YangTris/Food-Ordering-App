package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class EditFoodActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private String[] items = {"Catgory 1", "Catgory 2", "Catgory 3", "Catgory 4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit_food);
        AutoCompleteTextView textField = findViewById(R.id.food_autocomplete);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items);
        textField.setAdapter(adapter);
    }
}