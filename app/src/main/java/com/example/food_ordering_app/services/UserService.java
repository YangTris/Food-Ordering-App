package com.example.food_ordering_app.services;

import android.content.Context;
import android.widget.Toast;

import com.example.food_ordering_app.models.User;
import com.example.food_ordering_app.controllers.UserController;

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

    public void getUserDetails(String id){
        Call<User> request = userController.getUserDetails(id);
        request.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void loginUser(User user){
        Call<String> request = userController.loginUser(user);
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
