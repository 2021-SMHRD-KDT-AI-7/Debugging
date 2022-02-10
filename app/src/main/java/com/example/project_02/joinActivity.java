package com.example.project_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class joinActivity extends AppCompatActivity {

    EditText id, pw, pw2, name, bd;
    ImageView back;
    Button btn_join;
    StringRequest sr;
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        back = findViewById(R.id.join_back);
        id = findViewById(R.id.et_id);
        pw = findViewById(R.id.et_pw);
        pw2 = findViewById(R.id.et_pw2);
        name = findViewById(R.id.et_name);
        //bd = findViewById(R.id.et_birthday);
        btn_join = findViewById(R.id.btn_join_); // btn_join_ 아이디 중복 방지 언더바

        rq = Volley.newRequestQueue(getApplicationContext());
        sr = new StringRequest(Request.Method.POST,
                "http://121.147.52.64:8081/Mirror/join", response -> {
            if (!response.equals("회원가입 실패")) {
                Toast.makeText(getApplicationContext(),
                        "회원가입되었습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(joinActivity.this, frontActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(),
                        response + "", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("id", id.getText().toString());
                data.put("pw", pw.getText().toString());
                data.put("name", name.getText().toString());
                //data.put("bd", bd.getText().toString());
                return data;
            }
        };
        btn_join.setOnClickListener(view -> {
            if (pw.getText().equals(pw2.getText())) {
                Toast.makeText(getApplicationContext(),
                        "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                rq.add(sr);
            }
        });
        back.setOnClickListener(view -> {
            Intent intent = new Intent(
                    joinActivity.this, frontActivity.class);
            startActivity(intent);
        });
    }
}