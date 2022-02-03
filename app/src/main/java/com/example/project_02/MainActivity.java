package com.example.project_02;

import android.app.FragmentManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new Fragment_tab1()).commit();

        bn = findViewById(R.id.bnview);

        bn.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.tab1) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Fragment_tab1()).commit();
            } else if (item.getItemId() == R.id.tab2) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new cameraFragment()).commit();
            } else if (item.getItemId() == R.id.tab3) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Fragment_tab3()).commit();
            } else if (item.getItemId() == R.id.tab4) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Fragment_tab4()).commit();
            } else if (item.getItemId() == R.id.tab5) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Fragment_tab5()).commit();
            }
            return true;
        });
    }

}