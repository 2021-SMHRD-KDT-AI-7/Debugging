package com.example.project_02;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bn;
    private long lastTimeBackPressed;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        String text = PreferenceManager.getString(mContext, "user_name");
        if (text.equals("")) {
            text = "데이터 없음";
            PreferenceManager.setString(mContext, "user_name", "채정배");
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new Fragment_tab1()).commit();

        bn = findViewById(R.id.bnview);

        bn.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.tab1) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Fragment_tab1()).commit();
            } else if (item.getItemId() == R.id.tab2) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new Fragment_tab2_camera()).commit();
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

    @Override
    public void onBackPressed() { // 뒤로가기 버튼 막기
        //두 번 클릭시 어플 종료
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish();
            return;
        }
        lastTimeBackPressed = System.currentTimeMillis();
        Toast.makeText(this,
                "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }
}
