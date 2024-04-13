package com.example.food_ordering_app.services;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.food_ordering_app.controllers.PaymentController;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentService {
    private PaymentController paymentController = ServiceBuilder.buildService(PaymentController.class);
    private Context context;

    public PaymentService(Context context) {
        this.context = context;
    }

    public void responseFailure(Throwable throwable){
        if (throwable instanceof IOException) {
            Toast.makeText(context, "A connection error occured", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show();
        }
    }

    public void createPayment(Integer ammount, WebView webView){
        Call<String> request = paymentController.createPayment(ammount);
        request.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String url=response.body();
                webView.loadUrl(url);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                responseFailure(t);
            }
        });
    }
}
