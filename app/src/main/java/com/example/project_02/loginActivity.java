package com.example.project_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {

    EditText et_id, et_pw;
    Button btn_login, btn_join;
    ImageView back;
    CheckBox cb_;

    Context mContext;
    StringRequest sr, sr2;
    RequestQueue rq;
    RequestQueue queue;
    String user_id;

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
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = pref.edit();

        rq = Volley.newRequestQueue(getApplicationContext());
        sr = new StringRequest(Request.Method.POST,
                "http://121.147.52.64:8081/Mirror/login", response -> {
            if (!response.equals("없는 계정입니다.")) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    editor.putString("user_id", jsonObject.getString("user_id"));
                    Log.d("userID", jsonObject.getString("user_id"));
                    user_id = jsonObject.getString("user_id");
                    editor.putString("user_pw", jsonObject.getString("user_pw"));
                    editor.putString("user_name", jsonObject.getString("user_name"));
                    editor.apply();
                    Toast.makeText(getApplicationContext(),
                            "로그인 되었습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(
                            loginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        response + "", Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(getApplicationContext(),
                "오류 발생", Toast.LENGTH_SHORT).show()) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("id", et_id.getText().toString());
                data.put("pw", et_pw.getText().toString());
                return data;
            }
        };

        String flask_url = "http://220.80.203.107:5000/login_id"; //경로
        sr2 = new StringRequest(Request.Method.POST, flask_url,
                new Response.Listener<String>() {
                    public void onResponse(String response) {
                        //Flask서버의 return문에 작성한 결과값을 response변수를 통해서 접근
                        Log.v("Flask응답값>> ", response);
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.v("Flask응답값>> ", "Flask 통신 실패");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                user_id = pref.getString("user_id", "");
                params.put("user_id", user_id);
                return params;
            }
        };


        btn_login.setOnClickListener(view -> {
            if (TextUtils.isEmpty(et_id.getText()) || TextUtils.isEmpty(et_pw.getText())) {
                Toast.makeText(getApplicationContext(),
                        "아이디 혹은 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else {
                rq.add(sr);
                rq.add(sr2);
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