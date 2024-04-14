package com.example.food_ordering_app;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.food_ordering_app.models.Food;
import com.example.food_ordering_app.services.FoodService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.bumptech.glide.Glide;

import java.util.UUID;

public class EditFoodActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private String[] items = {"món chính", "fast food", "slow food", "món fụ", "khai zị", "tráng mịn", "món xế"};
    private Button chooseImage;
    private ImageView imageView;
    private Uri image;
    private Button saveFood;
    private StorageReference storageRef;
    private FirebaseStorage storage;
    private TextInputEditText txtFoodName;
    private TextInputEditText txtFoodDes;
    private TextInputEditText txtFoodPrice;
    private AutoCompleteTextView txtCategory;
    private CircularProgressIndicator circularProgressIndicator;
    private FoodService foodService = new FoodService(this);
    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image = result.getData().getData();
                    Glide.with(EditFoodActivity.this).load(image).error(R.drawable.error).into(imageView);
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
        txtCategory = findViewById(R.id.food_autocomplete);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        imageView = findViewById(R.id.food_image);
        chooseImage = findViewById(R.id.choose_image);
        saveFood = findViewById(R.id.save_food);
        txtFoodDes = findViewById(R.id.food_description);
        txtFoodName = findViewById(R.id.food_name);
        txtFoodPrice = findViewById(R.id.food_price);
        circularProgressIndicator = findViewById(R.id.progress_circular);
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items);
        txtCategory.setAdapter(adapter);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            String id = bundle.get("foodId").toString();
            foodService.getFoodDetails(id, txtFoodName, txtFoodPrice, txtFoodDes, txtCategory, imageView,null,null,null);
        }
        saveFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularProgressIndicator.setVisibility(View.VISIBLE);
                if(image!=null){
                    uploadImage(image);
                }
                else{
                    editFood(null);
                }
            }
        });
    }

    private void uploadImage(Uri image) {
        StorageReference reference = storageRef.child("images/" + UUID.randomUUID().toString());
        reference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(EditFoodActivity.this, "Image upload successfully", Toast.LENGTH_SHORT).show();
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        editFood(uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditFoodActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void editFood(String imgUrl){
        Food food = new Food();
        food.setCategory(txtCategory.getText().toString());
        food.setDescription(txtFoodDes.getText().toString());
        food.setPrice(Double.valueOf(txtFoodPrice.getText().toString()));
        food.setName(txtFoodName.getText().toString());
        food.setImgURL(imgUrl);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        //If food exists
        if (bundle != null) {
            String id = bundle.get("foodId").toString();
            if(imgUrl == null){
                food.setImgURL(bundle.get("imgUrl").toString());
            }
            foodService.updateFood(id, food);
        }
        //If not
        else {
            foodService.createFood(food);
        }
        circularProgressIndicator.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(EditFoodActivity.this, AdminFoodActivity.class);
        startActivity(intent);
        Toast.makeText(EditFoodActivity.this, "get link url successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}