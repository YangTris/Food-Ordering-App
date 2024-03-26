package com.example.food_ordering_app.controllers;

import com.example.food_ordering_app.models.CartItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CartController {
    @GET("cart/{id}")
    Call<List<CartItem>> getAllCartItems(@Path("id") String id);
    @GET("cart/{id}/totalPrice")
    Call<Double> getTotalPrice(@Path("id") String id);
    @GET("cart/{id}/totalQuantity")
    Call<Integer> getTotalQuantity(@Path("id")String id);
    @GET("cart/{userId}/{foodId}")
    Call<CartItem> checkFoodExists(@Path("id")String userId, @Path("id")String foodId);
    @POST("cart")
    Call<String> addFoodToCart(@Body CartItem cartItem);
    @PUT("cart")
    Call<String> updateFoodCart(@Body CartItem cartItem);
    @DELETE("cartItem/{cartId}")
    Call<String> deleteCartItem(@Path("cartId") String cartId);
    @DELETE("cart/{userId}")
    Call<String> clearCart(@Path("cartId") String userId);
}