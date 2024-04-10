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
    Call<String> getCartId(@Path("id") String id);

    @GET("getUserCart/{userId}")
    Call<List<CartItem>> getUserCart(@Path("userId") String userId);

    @POST("cart/{cartId}")
    Call<String> createCart(@Path("cartId") String cartId, @Body CartItem cartItem);

    @PUT("cart/{cartId}")
    Call<String> addFoodToCart(@Path("cartId") String cartId, @Body CartItem cartItem);

    @DELETE("cart/{cartId}/{foodId}")
    Call<String> deleteCartItem(@Path("cartId") String cartId, @Path("foodId") String foodId);

    @DELETE("cart/{userId}")
    Call<String> deleteCart(@Path("userId") String userId);
}