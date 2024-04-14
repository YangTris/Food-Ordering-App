package com.example.food_ordering_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.example.food_ordering_app.models.Food;
import com.example.food_ordering_app.models.User;
import com.example.food_ordering_app.services.UserService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {
    private final UserService userService = new UserService(this);
    private SharedPreferences sharedPreferences;
    private TextInputEditText txtName;
    private TextInputEditText txtEmail;
    private TextInputEditText txtPhone;
    private ImageView imageView;
    private CircularProgressIndicator circularProgressIndicator;
    private Button btnSave, chooseImage;
    private Uri image;
    private StorageReference storageRef;
    private FirebaseStorage storage;
    private String[] roles = {"Customer","Shipper","Admin"};
    private ArrayAdapter<String> adapter;
    private AutoCompleteTextView txtRole;
    private TextInputLayout roleLayout;


    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    image = result.getData().getData();
                    btnSave.setEnabled(true);
                    Glide.with(getApplicationContext()).load(image).error(R.drawable.error).into(imageView);
                }
            } else {
                Toast.makeText(EditProfileActivity.this, "Select a image", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        sharedPreferences = getSharedPreferences("sharedPrefKey", Context.MODE_PRIVATE);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        circularProgressIndicator = findViewById(R.id.progress_circular);
        txtName = findViewById(R.id.customer_name);
        txtEmail = findViewById(R.id.customer_mail);
        txtPhone = findViewById(R.id.customer_phone);
        btnSave = findViewById(R.id.save_customer);
        imageView = findViewById(R.id.user_image);
        chooseImage = findViewById(R.id.choose_image);
        txtRole = findViewById(R.id.roleauto);
        roleLayout = findViewById(R.id.role);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, roles);
        txtRole.setAdapter(adapter);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            String id = bundle.get("userId").toString();
            roleLayout.setVisibility(View.VISIBLE);
            userService.getUserDetails(id, txtName, txtEmail, txtPhone,imageView,txtRole, null);
        } else {
            userService.getUserDetails(sharedPreferences.getString("userIdKey", null), txtName, txtEmail, txtPhone,imageView,null, null);
        }
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circularProgressIndicator.setVisibility(View.VISIBLE);
                if(image!=null){
                    uploadImg(image);
                }
                else{
                    editUser(null);
                }
            }
        });
    }

    private void uploadImg(Uri image) {
        StorageReference reference = storageRef.child("users/" + UUID.randomUUID().toString());
        reference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(EditProfileActivity.this, "Image upload successfully", Toast.LENGTH_SHORT).show();
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        editUser(uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void editUser(String imgUrl){
        User user = new User();
        user.setName(txtName.getText().toString());
        user.setEmail(txtEmail.getText().toString());
        user.setPhone(txtPhone.getText().toString());
        user.setUserImg(imgUrl);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        Intent intent;
        //if admin edit
        if (bundle != null) {
            String id = bundle.get("userId").toString();
            user.setUserId(id);
            user.setAddress(bundle.get("address").toString());
            user.setPassword(bundle.get("password").toString());
            if(imgUrl == null)
                user.setUserImg(bundle.get("userImg")==null?null:bundle.get("userImg").toString());
            int role =-1;
            switch (txtRole.getText().toString()){
                case "Customer": role = 0;
                    break;
                case "Shipper": role = 1;
                    break;
                case "Admin": role = 2;
                    break;
            }
            user.setRoleId(role);
            userService.updateUser(id, user);
            intent = new Intent(EditProfileActivity.this, AdminUserActivity.class);
        }
        //if user edit
        else {
            user.setUserId(sharedPreferences.getString("userIdKey", null));
            user.setAddress(sharedPreferences.getString("addressKey", null));
            user.setPassword(sharedPreferences.getString("passwordKey", null));
            if(imgUrl == null)
                user.setUserImg(sharedPreferences.getString("imgKey", null));
            userService.updateUser(sharedPreferences.getString("userIdKey", null), user);
            intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
        }
        circularProgressIndicator.setVisibility(View.INVISIBLE);
        startActivity(intent);
        finish();
    }
}