package com.example.food_ordering_app.controllers;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PaymentController {
    @POST("payment")
    Call<String> createPayment(@Query("amount") Integer amount);
}
