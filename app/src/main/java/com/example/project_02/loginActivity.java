package com.example.project_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {

    private EditText et_id, et_pw;
    private Button btn_login, btn_join;
    ImageView back;
    CheckBox cb_;

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

        btn_login.setOnClickListener(view -> {
            Intent intent = new Intent(
                    loginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        btn_join.setOnClickListener(view -> {
            Intent intent = new Intent(
                    loginActivity.this, joinActivity.class);
            startActivity(intent);
            finish();
        });
        back.setOnClickListener(view -> {
                    Intent intent = new Intent(
                            loginActivity.this, frontActivity.class);
                    startActivity(intent);
                    finish();
                }
        );
    }
}