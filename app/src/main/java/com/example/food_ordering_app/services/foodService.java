package com.example.food_ordering_app.services;


import com.example.food_ordering_app.models.Food;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface foodService {
    @GET("ideas")
    Call<ArrayList<Food>> getIdeas();

    @GET("ideas/{id}")
    Call<Food> getIdea(@Path("id")int id);

    @POST("ideas")
    Call<Food> createIdea(@Body Food newIdea);

    @FormUrlEncoded
    @PUT("ideas/{id}")
    Call<Food> updateIdea(
            @Path("id")int id,
            @Field("name")String name,
            @Field("description")String desc,
            @Field("status")String status,
            @Field("owner")String owner
    );

    @DELETE("ideas/{id}")
    Call<Void> deleteIdea(@Path("id")int id);
}
