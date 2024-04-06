package com.example.food_ordering_app.controllers;

import com.example.food_ordering_app.models.Cart;
import com.example.food_ordering_app.models.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderController {
    @GET("order/{orderId}")
    Call<Order> getOrder(@Path("orderId") String orderId);
    @GET("order/orderhistory/{userId}")
    Call<List<Order>> getAllOrder(@Path("userId") String userId);
    @POST("order")
    Call<String> createOrder(@Body Cart cart);
    @PUT("order")
    Call<String> updateShipperId(@Body Order order);
}
