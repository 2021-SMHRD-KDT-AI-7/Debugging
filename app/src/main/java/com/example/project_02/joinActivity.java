package com.example.project_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class joinActivity extends AppCompatActivity {

    EditText id, pw, pw2, name, birthday;
    Button btn_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        id = findViewById(R.id.et_id);
        pw = findViewById(R.id.et_pw);
        pw2 = findViewById(R.id.et_pw2);
        name = findViewById(R.id.et_name);
        birthday = findViewById(R.id.et_birthday);
        btn_join = findViewById(R.id.btn_join_); // btn_join_ 아이디 중복 방지 언더바

        btn_join.setOnClickListener(v -> {
            if (pw != pw2) {
                Toast.makeText(getApplicationContext(),
                        "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "회원가입되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(joinActivity.this, frontActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}