package com.example.food_ordering_app.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.food_ordering_app.AdminFoodActivity;
import com.example.food_ordering_app.FoodActivity;
import com.example.food_ordering_app.LoginActivity;
import com.example.food_ordering_app.models.User;
import com.example.food_ordering_app.controllers.UserController;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {
    private UserController userController = ServiceBuilder.buildService(UserController.class);
    private Context context;
    public UserService(Context context){
        this.context = context;
    }
    public void responseSuccess(Response response){
        if (response.isSuccessful()) {
            response.body().toString();
        } else if (response.code() == 401) {
            Toast.makeText(context, "Your session has expired", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show();
        }
    }

    public void responseFailure(Throwable throwable){
        if (throwable instanceof IOException) {
            Toast.makeText(context, "A connection error occured", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show();
        }
    }

    public void getAllUsers(){
        Call<List<User>> request = userController.getAllUsers();
        request.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void getUserDetails(String id, TextInputEditText txtName,TextInputEditText txtEmail,TextInputEditText txtPhone){
        Call<User> request = userController.getUserDetails(id);
        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user =response.body();
                Intent intent;
                SharedPreferences sharedpref = context.getSharedPreferences("sharedPrefKey",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpref.edit();
                editor.putString("usernameKey",user.getName());
                editor.putInt("roleIdKey", user.getRoleId());
                editor.putString("userIdKey",user.getUserId());
                editor.putString("passwordKey",user.getPassword());
                editor.putString("addressKey",user.getAddress());
                editor.commit();
                //If login
                if(context instanceof LoginActivity){
                    if(user.getRoleId()==0){
                        intent= new Intent(context, FoodActivity.class);
                        context.startActivity(intent);
                    }else if(user.getRoleId()==1){
                        intent= new Intent(context, FoodActivity.class);
                        context.startActivity(intent);
                    }else {
                        intent= new Intent(context, AdminFoodActivity.class);
                        context.startActivity(intent);
                    }
                }
                //If edit profile
                else{
                    txtName.setText(user.getName());
                    txtEmail.setText(user.getEmail());
                    txtPhone.setText(user.getPhone());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void loginUser(String phoneNumber, String password){
        Call<String> request = userController.loginUser(phoneNumber, password);
        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String userId=response.body();
                if(userId==""){
                    Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show();
                }else {
                    getUserDetails(userId,null,null,null);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void createCustomer(User user){
        Call<String> request = userController.createCustomer(user);
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

    public void updateUser(String id, User user){
        SharedPreferences sharedpref = context.getSharedPreferences("sharedPrefKey",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putString("usernameKey",user.getName());
        editor.commit();
        Call<String> request = userController.updateUser(id,user);
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

    public void deleteUser(String id){
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
