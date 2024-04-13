package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class Test extends AppCompatActivity {
    TextInputLayout textField;
    Button buttonField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textField=findViewById(R.id.textField);
        buttonField=findViewById(R.id.buttonField);
        buttonField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Test.this, RegisterActivity.class);
                intent.putExtra("phoneNumberTest",textField.getEditText().getText().toString());
                Log.d("phoneNumberTest",textField.getEditText().getText().toString());
                startActivity(intent);
            }
        });

    }
}