package com.example.bookingapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingapp.data.database.ShareReferenceHelper;
import com.example.bookingapp.data.model.User;
import com.example.bookingapp.databinding.ActivitySplashBinding;
import com.squareup.picasso.Picasso;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 5000;
    private ActivitySplashBinding binding;
    private ShareReferenceHelper shareReferenceHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        shareReferenceHelper = new ShareReferenceHelper(this);

        String imageUrl = "https://icon-library.com/images/icon-png-logos/icon-png-logos-7.jpg";
        Picasso.get()
                .load(imageUrl)
                .into(binding.image);


        new Handler().postDelayed(() -> {
            if (shareReferenceHelper.get("user", User.class) != null) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else{
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish();
        }, SPLASH_TIME_OUT);
    }
}