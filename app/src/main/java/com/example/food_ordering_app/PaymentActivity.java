package com.example.food_ordering_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.food_ordering_app.services.PaymentService;

import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {
    WebView webView;
    private PaymentService paymentService = new PaymentService(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        webView=findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                if(url.contains("https://truyenqqvn.com") ){
                    Intent intent = new Intent(PaymentActivity.this, FoodActivity.class);
                    startActivity(intent);
                //    finish();
                }
                return true;
            }
        });

        paymentService.createPayment("50000",webView);

    }

}