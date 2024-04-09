package com.example.food_ordering_app.services;

import android.content.Context;
import android.widget.Toast;

import com.example.food_ordering_app.controllers.OrderController;
import com.example.food_ordering_app.controllers.PaymentController;
import com.example.food_ordering_app.models.Cart;
import com.example.food_ordering_app.models.Order;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderService {
    private OrderController orderController = ServiceBuilder.buildService(OrderController.class);
    private Context context;

    public OrderService(Context context) {
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

    public void getOrder(String orderId){
        Call<Order> request = orderController.getOrder(orderId);
        request.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                responseFailure(t);
            }
        });
    }
    public void getAllOrder(String userId){
        Call<List<Order>> request = orderController.getAllOrder(userId);
        request.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                responseFailure(t);
            }
        });
    }
    public void createOrder(Cart cart){
        Call<String> request = orderController.createOrder(cart);
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
    public void updateShipperId(Order order){
        Call<String> request = orderController.updateShipperId(order);
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
