package com.example.mycalendar.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mycalendar.R;

public class StartActivity extends AppCompatActivity {
    private static final int SPLASH_SCREEN = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainintent = new Intent(StartActivity.this,MainActivity.class);
                startActivity(mainintent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}