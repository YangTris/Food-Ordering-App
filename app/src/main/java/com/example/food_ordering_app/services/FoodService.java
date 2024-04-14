package com.example.food_ordering_app.services;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_ordering_app.AdminFoodActivity;
import com.example.food_ordering_app.R;
import com.example.food_ordering_app.adapter.AdminFoodAdapter;
import com.example.food_ordering_app.adapter.FoodAdapter;
import com.example.food_ordering_app.models.CartItem;
import com.example.food_ordering_app.models.Food;
import com.example.food_ordering_app.controllers.FoodController;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodService {
    private FoodController foodController = ServiceBuilder.buildService(FoodController.class);
    private Context context;
    private SharedPreferences sharedPreferences;
    public FoodService(Context context) {
        this.context = context;
    }

    public void responseSuccess(Response response) {
        if (response.isSuccessful()) {
            Log.d("response",response.body().toString());
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

    public void getAllFoods(RecyclerView recyclerView, LinearProgressIndicator progressIndicator, String query) {
        Call<ArrayList<Food>> request = foodController.getAllFoods(query);
        request.enqueue(new Callback<ArrayList<Food>>() {
            @Override
            public void onResponse(Call<ArrayList<Food>> request, Response<ArrayList<Food>> response) {
                progressIndicator.setVisibility(View.INVISIBLE);
                if(context instanceof AdminFoodActivity){
                    AdminFoodAdapter foodAdapter = new AdminFoodAdapter(context, response.body());
                    recyclerView.setAdapter(foodAdapter);
                } else {
                    FoodAdapter foodAdapter = new FoodAdapter(context,response.body());
                    recyclerView.setAdapter(foodAdapter);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onFailure(Call<ArrayList<Food>> request, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void getFoodDetails(String id, TextInputEditText txtFoodName, TextInputEditText txtFoodPrice, TextInputEditText txtFoodDes, AutoCompleteTextView txtCategory, ImageView imageView, EditText quantity, Button btnAddToCart, CircularProgressIndicator circularProgressIndicator) {
        Call<Food> request = foodController.getFoodDetails(id);
        request.enqueue(new Callback<Food>() {
            @Override
            public void onResponse(Call<Food> call, Response<Food> response) {
                Food food = response.body();
                txtFoodName.setText(food.getName());
                txtFoodPrice.setText(Double.valueOf(food.getPrice()).toString());
                txtFoodDes.setText(food.getDescription());
                Glide.with(context).load(food.getImgURL()).error(R.drawable.error).into(imageView);
                //If Admin get FoodDetails
                if(txtCategory != null){
                    txtCategory.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtCategory.setText(food.getCategory(), false);
                        }
                    }, 10);
                }
                //If User get FoodDetails
                if(btnAddToCart!=null){
                    CartItem item = new CartItem();
                    item.setImgURL(food.getImgURL());
                    item.setFoodName(food.getName());
                    item.setFoodId(food.getId());
                    item.setPrice(food.getPrice());
                    item.setTotal(food.getPrice()*item.getQuantity());
                    btnAddToCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            item.setQuantity(Integer.valueOf(quantity.getText().toString()));
                            circularProgressIndicator.setVisibility(View.VISIBLE);
                            CartService cartService = new CartService(context);
                            sharedPreferences = context.getSharedPreferences("sharedPrefKey",Context.MODE_PRIVATE);
                            cartService.getCartId(sharedPreferences.getString("userIdKey",null),item,circularProgressIndicator);
                            ((Activity)context).finish();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Food> call, Throwable t) {
                responseFailure(t);
            }
        });
    }

    public void createFood(Food food) {
        Call<String> request = foodController.createFood(food);
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

    public void updateFood(String id, Food food) {
        Call<String> request = foodController.updateFood(id, food);
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

    public void deleteFood(String id) {
        Call<String> request = foodController.deleteFood(id);
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
