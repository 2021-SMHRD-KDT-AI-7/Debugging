package com.example.project_02;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class guideActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new guideFragment00()).commit();
    }
}
