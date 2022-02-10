package com.example.project_02;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public BottomNavigationView bn;
    private long lastTimeBackPressed;
    public static Activity mainActivity;
    String user_id;

    RequestQueue rq;
    StringRequest sr;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivity = MainActivity.this;
        mContext = this;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        rq = Volley.newRequestQueue(getApplicationContext());
        String flask_url = "http://172.30.1.47:5000/login_id"; //경로
        sr = new StringRequest(Request.Method.POST, flask_url,
                response -> {
                    //Flask서버의 return문에 작성한 결과값을 response변수를 통해서 접근
                    Log.v("Flask응답값>> ", response);
                },
                error -> Log.v("Flask응답값>> ", "Flask 통신 실패")) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                user_id = pref.getString("user_id", "");
                params.put("user_id", user_id);
                return params;
            }
        };
        rq.add(sr);
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
                "뒤로 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
    }
}
