package com.example.food_ordering_app.services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.EditFoodActivity;
import com.example.food_ordering_app.MapsActivity;
import com.example.food_ordering_app.OrderActivity;
import com.example.food_ordering_app.PaymentActivity;
import com.example.food_ordering_app.R;
import com.example.food_ordering_app.adapter.CartAdapter;
import com.example.food_ordering_app.adapter.OrderAdapter;
import com.example.food_ordering_app.controllers.OrderController;
import com.example.food_ordering_app.models.Cart;
import com.example.food_ordering_app.models.Order;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderService {
    private OrderController orderController = ServiceBuilder.buildService(OrderController.class);
    private CartService cartService;
    private Context context;

    public OrderService(Context context) {
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

    public void getAllOrder(RecyclerView recyclerView, LinearProgressIndicator linearProgressIndicator){
        Call<List<Order>> request = orderController.getAllOrder();
        request.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                linearProgressIndicator.setVisibility(View.INVISIBLE);
                OrderAdapter adapter = new OrderAdapter(context, response.body());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });
    }
    public void getOrder(String orderId, TextView receiver, TextView address,TextView paymentStatus, RecyclerView orderItems, TextView total, View delivering, View delivered, Button navigate) {
        Call<Order> request = orderController.getOrder(orderId);
        request.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Order order = response.body();
                receiver.setText("Receiver: " + order.getUserPhone());
                address.setText("Address: " + order.getUserAddress());
                paymentStatus.setText("Payment Status: "+order.getPaymentStatus());
                CartAdapter cartAdapter = new CartAdapter(context, order.getCart().getCartItems(), total);
                orderItems.setAdapter(cartAdapter);
                orderItems.setLayoutManager(new LinearLayoutManager(context));
                if (order.getOrderStatus().equals("Delivering")) {
                    delivering.setBackgroundResource(R.drawable.delivery_done);
                }
                if (order.getOrderStatus().equals("Delivered")) {
                    delivering.setBackgroundResource(R.drawable.delivery_done);
                    delivered.setBackgroundResource(R.drawable.delivery_done);
                }

                navigate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(context, MapsActivity.class);
                        i.putExtra("orderId", order.getOrderId());
                        i.putExtra("userAddress",order.getUserAddress());
                        i.putExtra("userName", order.getUserName());
                        i.putExtra("userPhone", order.getUserPhone());
                        context.startActivity(i);
                    }
                });
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void getAllOrder(String userId, RecyclerView recyclerView, LinearProgressIndicator linearProgressIndicator) {
        Call<List<Order>> request = orderController.getAllOrder(userId);
        request.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                linearProgressIndicator.setVisibility(View.INVISIBLE);
                OrderAdapter adapter = new OrderAdapter(context, response.body());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void createOrder(Cart cart, String userId) {
        Call<Order> request = orderController.createOrder(cart);
        request.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                ((Activity)context).finish();
                responseSuccess(response);
                cartService = new CartService(context);
                cartService.deleteCart(userId);
                Intent intent = new Intent(context, PaymentActivity.class);
                Log.d("orderTotal", Double.valueOf(response.body().getOrderTotal()).toString());
                intent.putExtra("ammount",Double.valueOf(response.body().getOrderTotal()));
                intent.putExtra("orderId",response.body().getOrderId());
                context.startActivity(intent);
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                responseFailure(t);
            }
        });
    }
    public void updateOrderStatus(String orderId,String shipperID,String status){
        Call<String> request = orderController.updateOrderStatus(orderId,shipperID,status);
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

    public void updatePaymentStatus(String orderId, String status){
        Call<String> request = orderController.updatePaymentStatus(orderId,status);
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
