package com.example.food_ordering_app;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.bumptech.glide.Glide;

import java.util.UUID;

public class EditFoodActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private String[] items = {"Catgory 1", "Catgory 2", "Catgory 3", "Catgory 4"};
    private Button chooseImage;

    private ImageView imageView;
    private Uri image;
    private Button saveFood;

    private StorageReference storageRef;
    private FirebaseStorage storage;
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image = result.getData().getData();
                    saveFood.setEnabled(true);
                    imageView = findViewById(R.id.food_image);
                    Glide.with(getApplicationContext()).load(image).error(R.drawable.error).into(imageView);

                }
            } else {
                Toast.makeText(EditFoodActivity.this, "Select a image", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_edit_food);
        AutoCompleteTextView textField = findViewById(R.id.food_autocomplete);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        chooseImage = findViewById(R.id.choose_image);
        saveFood = findViewById(R.id.save_food);
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });


        saveFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(image);
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items);
        textField.setAdapter(adapter);
    }

    private void uploadImage(Uri image) {
        StorageReference reference = storageRef.child("images/" + UUID.randomUUID().toString());
        reference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(EditFoodActivity.this, "Image upload successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}