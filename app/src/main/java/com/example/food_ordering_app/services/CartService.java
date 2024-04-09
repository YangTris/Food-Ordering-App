package com.example.food_ordering_app.services;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.adapter.CartAdapter;
import com.example.food_ordering_app.models.Cart;
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

    public void getCartId(String userId, CartItem item){
        Call<String> request = cartController.getCartId(userId);
        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body().toString();
                if(res==""){
                    createCart(userId,item);
                }
                else{
                    addFoodToCart(res,item);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void getUserCart(String userId, RecyclerView recyclerView, TextView txtTotal){
        Call<List<CartItem>> request = cartController.getUserCart(userId);
        request.enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                List<CartItem> items = response.body();
                CartAdapter adapter = new CartAdapter(context,items,txtTotal);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void createCart(String cartId,CartItem cartItem){
        Call<String> request = cartController.createCart(cartId,cartItem);
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

    public void addFoodToCart(String cartId, CartItem cartItem){
        Call<String> request = cartController.addFoodToCart(cartId,cartItem);
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

    public void deleteCartItem(String cartId,String itemId){
        Call<String> request = cartController.deleteCartItem(cartId,itemId);
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

    public void deleteCart(String userId){
        Call<String> request = cartController.deleteCart(userId);
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

