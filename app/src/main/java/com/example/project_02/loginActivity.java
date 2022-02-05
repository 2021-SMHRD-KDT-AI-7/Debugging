package com.example.project_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {

    EditText et_id, et_pw;
    Button btn_login, btn_join;
    ImageView back;
    CheckBox cb_;

    Context mContext;
    StringRequest sr;
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        back = findViewById(R.id.login_back);
        et_id = findViewById(R.id.et_id);
        et_pw = findViewById(R.id.et_pw);
        btn_login = findViewById(R.id.btn_login_);
        btn_join = findViewById(R.id.btn_join2);
        cb_ = findViewById(R.id.cb_);
        mContext = this;
        rq = Volley.newRequestQueue(getApplicationContext());
        sr = new StringRequest(Request.Method.POST, "", response -> {
            if (response != null) {
                try {
                    JSONObject member_json = new JSONObject(response);
                    PreferenceManager.setString(mContext, "user_id", member_json.getString("id"));
                    PreferenceManager.setString(mContext, "user_pw", member_json.getString("pw"));
                    PreferenceManager.setString(mContext, "user_name", member_json.getString("name"));
                    PreferenceManager.setString(mContext, "user_bd", member_json.getString("bd"));

                    Intent intent = new Intent(
                            loginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            Toast.makeText(getApplicationContext(),
                    "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("id", et_id.getText().toString());
                data.put("pw", et_pw.getText().toString());
                return data;
            }
        };
        btn_login.setOnClickListener(view -> {
            if (TextUtils.isEmpty(et_id.getText()) || TextUtils.isEmpty(et_pw.getText())) {
                Toast.makeText(getApplicationContext(),
                        "아이디 혹은 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                rq.add(sr);
            }
        });
        btn_join.setOnClickListener(view -> {
            Intent intent = new Intent(
                    loginActivity.this, joinActivity.class);
            startActivity(intent);
        });
        back.setOnClickListener(view -> {
            Intent intent = new Intent(
                    loginActivity.this, frontActivity.class);
            startActivity(intent);
        });
    }
}