package com.example.project_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class joinActivity extends AppCompatActivity {

    private EditText id, pw, pw2, name, bd;
    ImageView back;
    Button btn_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        back = findViewById(R.id.join_back);
        id = findViewById(R.id.et_id);
        pw = findViewById(R.id.et_pw);
        pw2 = findViewById(R.id.et_pw2);
        name = findViewById(R.id.et_name);
        bd = findViewById(R.id.et_birthday);
        btn_join = findViewById(R.id.btn_login_); // btn_join_ 아이디 중복 방지 언더바

        btn_join.setOnClickListener(v -> {
            if (!pw.getText().toString().equals(pw2.getText().toString())) {
                Toast.makeText(getApplicationContext(),
                        "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    String user_id = id.getText().toString();
                    String user_pw = pw.getText().toString();
                    String user_name = name.getText().toString();
                    String user_bd = bd.getText().toString();
                    String admin = "n";

                    registerActivity task = new registerActivity();
                    task.execute(user_id, user_pw, user_name, user_bd, admin).get();
                } catch (Exception e) {
                    Log.i("DB_test", ".....ERROR.....!");
                }
                Toast.makeText(getApplicationContext(),
                        "회원가입되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(joinActivity.this, frontActivity.class);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(view -> finish());
    }
}