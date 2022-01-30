package com.example.project_02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.CursorTreeAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;

public class splashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(splashActivity.this, guideActivity.class);
            startActivity(intent);
            finish();
        }, 4000);


    }
}
