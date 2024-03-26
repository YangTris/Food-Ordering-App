package com.example.food_ordering_app.services;

import android.content.Context;
import android.widget.Toast;

import com.example.food_ordering_app.models.CartItem;
import com.example.food_ordering_app.controllers.CartController;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartService {
    private CartController cartController = ServiceBuilder.buildService(CartController.class);
    private Context context;

    public CartService(Context context) {
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

    public void getAllCartItems(String id){
        Call<List<CartItem>> request = cartController.getAllCartItems(id);
        request.enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {
                responseFailure(t);
            }
        });
    }
    public void getTotalPrice(String id){
        Call<Double> request = cartController.getTotalPrice(id);
        request.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                responseFailure(t);
            }
        });
    }
    public void getTotalQuantity(String id){
        Call<Integer> request = cartController.getTotalQuantity(id);
        request.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                responseFailure(t);
            }
        });
    }
    public void addCartItem(String userId, String foodId){
        Call<CartItem> request = cartController.checkFoodExists(userId,foodId);
        request.enqueue(new Callback<CartItem>() {
            @Override
            public void onResponse(Call<CartItem> call, Response<CartItem> response) {
                CartItem item = response.body();
                if(item == null){
                    addFoodToCart(item);
                }else{
                    updateFoodCart(item);
                }
            }

            @Override
            public void onFailure(Call<CartItem> call, Throwable t) {
                responseFailure(t);
            }
        });
    }
    public void addFoodToCart(CartItem cartItem){
        Call<String> request = cartController.addFoodToCart(cartItem);
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
    public void updateFoodCart(CartItem cartItem){
        Call<String> request = cartController.updateFoodCart(cartItem);
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
    public void deleteCartItem(String cartId){
        Call<String> request = cartController.deleteCartItem(cartId);
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
    public void clearCart(String userId){
        Call<String> request = cartController.clearCart(userId);
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
