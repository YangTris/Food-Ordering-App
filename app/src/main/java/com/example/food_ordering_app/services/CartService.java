package com.example.food_ordering_app.services;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.adapter.CartAdapter;
import com.example.food_ordering_app.models.Cart;
import com.example.food_ordering_app.models.CartItem;
import com.example.food_ordering_app.controllers.CartController;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartService {
    private CartController cartController = ServiceBuilder.buildService(CartController.class);
    private OrderService orderService;
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

    public void getCartId(String userId, CartItem item, CircularProgressIndicator circularProgressIndicator){
        Call<String> request = cartController.getCartId(userId);
        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body().toString();
                if(res==""){
                    createCart(userId,item,circularProgressIndicator);
                }
                else{
                    addFoodToCart(res,item,circularProgressIndicator);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void getUserCart(String userId, RecyclerView recyclerView, TextView txtTotal, Button orderButton, LinearProgressIndicator linearProgressIndicator){
        Call<List<CartItem>> request = cartController.getUserCart(userId);
        request.enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                linearProgressIndicator.setVisibility(View.INVISIBLE);
                List<CartItem> items = response.body();
                CartAdapter adapter = new CartAdapter(context,items,txtTotal);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                if(items!=null){
                    double total = 0.0;
                    for (CartItem item:items) {
                        total += item.getTotal();
                    }
                    double finalTotal = total;
                    orderButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Cart cart = new Cart();
                            cart.setUserId(userId);
                            cart.setCartId(userId);
                            cart.setTotalPrice(finalTotal);
                            cart.setCartItems(items);
                            orderService = new OrderService(context);
                            orderService.createOrder(cart,userId);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {

            }
        });
    }

    public void createCart(String cartId,CartItem cartItem,CircularProgressIndicator circularProgressIndicator){
        Call<String> request = cartController.createCart(cartId,cartItem);
        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(circularProgressIndicator!=null)
                    circularProgressIndicator.setVisibility(View.INVISIBLE);
                responseSuccess(response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void addFoodToCart(String cartId, CartItem cartItem,CircularProgressIndicator circularProgressIndicator){
        Call<String> request = cartController.addFoodToCart(cartId,cartItem);
        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(circularProgressIndicator!=null)
                    circularProgressIndicator.setVisibility(View.INVISIBLE);
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

