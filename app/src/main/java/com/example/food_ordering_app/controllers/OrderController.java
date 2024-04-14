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
    @GET("order")
    Call<List<Order>> getAllOrder();
    @GET("order/{orderId}")
    Call<Order> getOrder(@Path("orderId") String orderId);
    @GET("order/orderhistory/{userId}")
    Call<List<Order>> getAllOrder(@Path("userId") String userId);
    @POST("order")
    Call<Order> createOrder(@Body Cart cart);
    @PUT("order/{orderId}/{shipperId}/{status}")
    Call<String> updateOrderStatus(@Path("orderId") String orderId,@Path("shipperId") String shipperId,@Path("status") String status);
    @PUT("order/{orderId}/{status}")
    Call<String> updatePaymentStatus(@Path("orderId") String orderId, @Path("status") String status);
}
