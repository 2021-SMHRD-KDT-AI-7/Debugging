package com.example.project_02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class myActivity extends AppCompatActivity {

    ListView lv;
    TextView name, gender, birthdate, skintype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab5_my);

        name = findViewById(R.id.tv_name);
        gender = findViewById(R.id.tv_gender);
        birthdate = findViewById(R.id.tv_birthdate);
        skintype = findViewById(R.id.tv_skintype);
        lv = findViewById(R.id.lv_);

        List<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.simple, list);
        lv.setAdapter(adapter);

        list.add("회원정보수정");
        list.add("히스토리 보기");
        list.add("로그아웃");

        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            if (i == 0) {
                Intent intent = new Intent(myActivity.this,
                        loginActivity.class); // 이동할 페이지 완성되면 작성 요망
                startActivity(intent);
            } else if (i == 1) {
                Intent intent = new Intent(myActivity.this,
                        historyActivity.class);
                startActivity(intent);
            } else if (i == 2) {
                Intent intent = new Intent(myActivity.this,
                        frontActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        adapter.notifyDataSetChanged();
    }
}