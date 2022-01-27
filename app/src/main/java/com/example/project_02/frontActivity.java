package com.example.project_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class frontActivity extends AppCompatActivity {

    Button btn_login, btn_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        btn_login = findViewById(R.id.btn_login); // 로그인
        btn_join = findViewById(R.id.btn_join); // 회원가입
        // 로그인 페이지 이동
        btn_login.setOnClickListener(view -> {
            Intent intent = new Intent(frontActivity.this,
                    loginActivity.class);
            startActivity(intent);
        });
        // 회원가입 페이지 이동
        btn_join.setOnClickListener(view -> {
            Intent intent = new Intent(frontActivity.this,
                    joinActivity.class);
            startActivity(intent);
        });
    }
}