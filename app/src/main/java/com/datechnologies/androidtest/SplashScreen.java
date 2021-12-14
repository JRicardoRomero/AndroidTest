package com.datechnologies.androidtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        handler.postDelayed(new Runnable(){

            @Override
            public void run(){

                SplashScreen.this.startActivity(new Intent(SplashScreen.this,MainActivity.class));
                finish();

            }}, 2000);


    }
}