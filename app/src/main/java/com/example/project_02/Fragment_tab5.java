package com.example.project_02;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class Fragment_tab5 extends Fragment {

    ListView lv;
    TextView name, gender, birthdate, skintype;
    String userName;
    Context mContext;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab5, container, false);
        name = v.findViewById(R.id.tv_name);
        gender = v.findViewById(R.id.tv_gender);
        birthdate = v.findViewById(R.id.tv_birthdate);
        skintype = v.findViewById(R.id.tv_skintype);
        lv = v.findViewById(R.id.lv_);

        mContext = v.getContext();

        userName = PreferenceManager.getString(mContext, "user_name" + " 님 환영합니다.");
        name.setText(userName);

        List<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), R.layout.simple, list);
        lv.setAdapter(adapter);

        list.add("회원정보수정");
        list.add("히스토리 보기");
        list.add("로그아웃");

        lv.setOnItemClickListener((adapterView, view, i, l) -> {
            if (i == 0) {
                Intent intent = new Intent(getActivity(),
                        loginActivity.class); // 이동할 페이지 완성되면 작성 요망
                startActivity(intent);
            } else if (i == 1) {
                Intent intent = new Intent(getActivity(),
                        historyActivity.class);
                startActivity(intent);
            } else if (i == 2) {
                Intent intent = new Intent(getActivity(),
                        frontActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(),
                        "로그아웃되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        adapter.notifyDataSetChanged();
        return v;
    }
}