package com.example.project_02;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new Fragment1()).commit();

        bn = findViewById(R.id.bnview);

        bn.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.tab1) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Fragment1()).commit();
            } else if (item.getItemId() == R.id.tab2) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Fragment2()).commit();
            } else if (item.getItemId() == R.id.tab3) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Fragment3()).commit();
            } else if (item.getItemId() == R.id.tab4) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Fragment4()).commit();
            } else if (item.getItemId() == R.id.tab5) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Fragment5()).commit();
            }
            return true;
        });
    }
}