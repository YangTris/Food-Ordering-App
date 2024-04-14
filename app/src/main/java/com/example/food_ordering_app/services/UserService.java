package com.example.food_ordering_app.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_ordering_app.AdminFoodActivity;
import com.example.food_ordering_app.FoodActivity;
import com.example.food_ordering_app.LoginActivity;
import com.example.food_ordering_app.R;
import com.example.food_ordering_app.ShipperOrderActivity;
import com.example.food_ordering_app.adapter.AdminFoodAdapter;
import com.example.food_ordering_app.adapter.AdminUserAdapter;
import com.example.food_ordering_app.models.User;
import com.example.food_ordering_app.controllers.UserController;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {
    private UserController userController = ServiceBuilder.buildService(UserController.class);
    private Context context;
    private SharedPreferences sharedpref;

    public UserService(Context context) {
        this.context = context;
    }

    public void responseSuccess(Response response) {
        if (response.isSuccessful()) {
            response.body().toString();
        } else if (response.code() == 401) {
            Toast.makeText(context, "Your session has expired", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show();
        }
    }

    public void responseFailure(Throwable throwable) {
        if (throwable instanceof IOException) {
            Toast.makeText(context, "A connection error occured", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show();
        }
    }

    public void getAllUsers(RecyclerView userList, LinearProgressIndicator linearProgressIndicator,String query) {
        Call<List<User>> request = userController.getAllUsers(query);
        request.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                linearProgressIndicator.setVisibility(View.INVISIBLE);
                AdminUserAdapter adapter = new AdminUserAdapter(context, response.body());
                userList.setAdapter(adapter);
                userList.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void getUserDetails(String id, TextInputEditText txtName, TextInputEditText txtEmail, TextInputEditText txtPhone, ImageView imageView, AutoCompleteTextView txtRole, CircularProgressIndicator circularProgressIndicator) {
        Call<User> request = userController.getUserDetails(id);
        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                //If login
                if (context instanceof LoginActivity) {
                    circularProgressIndicator.setVisibility(View.INVISIBLE);
                    Intent intent;
                    sharedpref = context.getSharedPreferences("sharedPrefKey", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putString("usernameKey", user.getName());
                    editor.putInt("roleIdKey", user.getRoleId());
                    editor.putString("userIdKey", user.getUserId());
                    editor.putString("passwordKey", user.getPassword());
                    editor.putString("addressKey", user.getAddress());
                    editor.putString("imgKey", user.getUserImg());
                    editor.putString("phoneKey",user.getPhone());
                    editor.putString("emailKey",user.getEmail());
                    editor.commit();
                    if (user.getRoleId() == 0) {
                        intent = new Intent(context, FoodActivity.class);
                        context.startActivity(intent);
                    } else if (user.getRoleId() == 1) {
                        intent = new Intent(context, ShipperOrderActivity.class);
                        context.startActivity(intent);
                    } else if (user.getRoleId() == 2) {
                        intent = new Intent(context, AdminFoodActivity.class);
                        context.startActivity(intent);
                    }
                }
                //If edit profile
                else {
                    Glide.with(context).load(user.getUserImg()).error(R.drawable.error).into(imageView);
                    txtName.setText(user.getName());
                    txtEmail.setText(user.getEmail());
                    txtPhone.setText(user.getPhone());
                    if(txtRole != null){

                        txtRole.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                String role = "";
                                switch (user.getRoleId()){
                                    case 0: role = "Customer";
                                        break;
                                    case 1: role = "Shipper";
                                        break;
                                    case 2: role = "Admin";
                                        break;
                                }
                                txtRole.setText(role, false);
                            }}, 10);
                    }
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void loginUser(String phoneNumber, String password, CircularProgressIndicator circularProgressIndicator) {
        Call<String> request = userController.loginUser(phoneNumber, password);
        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String userId = response.body();
                if (userId == "") {
                    Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show();
                    circularProgressIndicator.setVisibility(View.INVISIBLE);
                } else {
                    getUserDetails(userId, null, null, null,null,null, circularProgressIndicator);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void createCustomer(User user) {
        Call<String> request = userController.createCustomer(user);
        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(context, response.body(), Toast.LENGTH_LONG).show();
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void updateUser(String id, User user) {
        SharedPreferences sharedpref = context.getSharedPreferences("sharedPrefKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putString("usernameKey", user.getName());
        editor.putString("addressKey", user.getAddress());
        editor.commit();
        Call<String> request = userController.updateUser(id, user);
        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void deleteUser(String id) {
        Call<String> request = userController.deleteUser(id);
        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                responseFailure(t);
            }
        });
    }
}
