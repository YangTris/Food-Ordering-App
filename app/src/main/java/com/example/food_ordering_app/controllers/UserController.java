package com.example.food_ordering_app.controllers;

import com.example.food_ordering_app.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserController {
    @GET("user")
    Call<List<User>> getAllUsers(@Query("query") String query);
    @GET("user/{id}")
    Call<User> getUserDetails(@Path("id") String id);

    @POST("login/{phoneNumber}/{password}")
    Call<String> loginUser(@Path("phoneNumber") String phoneNumber,@Path("password") String password);

    @POST("user")
    Call<String> createCustomer(@Body User user);

    @PUT("user/{id}")
    Call<String> updateUser(@Path("id") String id, @Body User user);

    @DELETE("user/{id}")
    Call<String> deleteUser(@Path("id") String id);
}
