package com.example.project_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;


public class loginActivity extends AppCompatActivity {

    private EditText et_id, et_pw;
    private Button btn_login, btn_join;
    CheckBox cb_;
    StringRequest sr;
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        cb_ = findViewById(R.id.cb_);
        btn_login = findViewById(R.id.btn_join_);
        btn_join = findViewById(R.id.btn_join2);

        btn_login.setOnClickListener(view -> {
            Intent intent = new Intent(
                    loginActivity.this, MainActivity.class);
            startActivity(intent);
            /*sr = new StringRequest(Request.Method.POST,
                    "링크 입력 요망",
                    response -> {
                        if (response.equals("true")) {
                            Toast.makeText(getApplicationContext(),
                                    "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                    }) {
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    data.put("user_id", et_id.getText().toString());
                    data.put("user_pw", et_pw.getText().toString());
                    return data;
                }
            };
            rq.add(sr);*/
        });
        btn_join.setOnClickListener(view -> {
            Intent intent = new Intent(
                    loginActivity.this, joinActivity.class);
            startActivity(intent);
        });
    }
}