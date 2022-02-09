package com.example.project_02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Fragment_tab1 extends Fragment {

    RecyclerView mRecyclerView = null;
    Cos_Home_Adapter_Second mAdapter = null;
    ArrayList<CosVO> cos_list = new ArrayList<>();
    String readDay = null, str = null, name;
    CalendarView calendarView;
    Button cha_Btn, del_Btn, save_Btn;
    TextView diaryTextView, textView2, textView3, user_name;
    EditText contextEditText;

    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab1, container, false);
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mRecyclerView = v.findViewById(R.id.cos_listview_home);
        user_name = v.findViewById(R.id.user_name);
        mContext = v.getContext();

        mAdapter = new Cos_Home_Adapter_Second(cos_list);
        mRecyclerView.setAdapter(mAdapter);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        name = pref.getString("user_name", "") + "님";
        user_name.setText(name);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        cos_list.add(new CosVO("닥터자르트 세라마이딘 크림 50ml", "38,000", "닥터자르트", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0016/A00000016073403ko.jpg?l=ko"));
        cos_list.add(new CosVO("리얼베리어 익스트림 크림 50ml", "26,900", "리얼베리어", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0016/A00000016072107ko.jpg?l=ko"));
        cos_list.add(new CosVO("에스티 로더 갈색병 세럼 30ML", "89,400", "에스티로더", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0014/A00000014663504ko.jpg?l=ko"));
        cos_list.add(new CosVO("가히 김고은 멀티밤", "29,400", "가히", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0015/A00000015499111ko.jpeg?l=ko"));
        cos_list.add(new CosVO("크리니크 모이스춰 써지 쏙보습크림 50ml", "41,000", "크리니크", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0015/A00000015887308ko.jpg?l=ko"));

        mAdapter.notifyDataSetChanged();

        calendarView = v.findViewById(R.id.calendarView3);
        save_Btn = v.findViewById(R.id.save_Btn);
        del_Btn = v.findViewById(R.id.del_Btn);
        textView2 = v.findViewById(R.id.textView2);
        textView3 = v.findViewById(R.id.textView3);
        contextEditText = v.findViewById(R.id.contextEditText);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // diaryTextView.setVisibility(View.VISIBLE);
            save_Btn.setVisibility(View.VISIBLE);
            contextEditText.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
            // cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);
            // diaryTextView.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
            contextEditText.setText("");
            checkDay(year, month, dayOfMonth);
        });
        save_Btn.setOnClickListener(view -> {
            saveDiary(readDay);
            Log.d("b", "onClick: ");
            str = contextEditText.getText().toString();
            textView2.setText(str);
            save_Btn.setVisibility(View.VISIBLE);
            // cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);
            contextEditText.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
        });

        return v;
    }

    public void checkDay(int cYear, int cMonth, int cDay) {
        readDay = "" + cYear + "-" + (cMonth + 1) + "" + "-" + cDay + ".txt";
        FileInputStream fis;

        try {
            fis = getActivity().openFileInput(readDay);

            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            str = new String(fileData);

            contextEditText.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(str);

            save_Btn.setVisibility(View.VISIBLE);
            // cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);

            del_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textView2.setVisibility(View.VISIBLE);
                    contextEditText.setText("");
                    contextEditText.setVisibility(View.VISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);
//                  cha_Btn.setVisibility(View.VISIBLE);
                    del_Btn.setVisibility(View.VISIBLE);
                    removeDiary(readDay);
                }
            });
            if (textView2.getText() == null) {
                textView2.setVisibility(View.VISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay) {
        FileOutputStream fos;
        try {
            fos = getActivity().openFileOutput(readDay, getActivity().MODE_NO_LOCALIZED_COLLATORS);
            String content = "";
            fos.write((content).getBytes());
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay) {
        FileOutputStream fos;
        try {
            fos = getActivity().openFileOutput(readDay, getActivity().MODE_NO_LOCALIZED_COLLATORS);
            String content = contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

// cos_list
// 바우만 테스트 결과 = cos DB에 바우만 컬럼의 값이 일치하는 화장품 가져와서 add

//CosAdapter adapter = new CosAdapter(R.layout.cos_custom, cos_list, getContext().getApplicationContext(),Fragment_tab4.this);


// template  >> R.layout.custom  우리가 디자인한 템플릿
// data >> 데이터를 저장한 어레이 리스트


