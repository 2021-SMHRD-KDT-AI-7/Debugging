package com.example.project_02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Fragment_tab1 extends Fragment {
    ArrayList<CosVO> cos_list = new ArrayList<>();
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab1, container, false);

        lv = v.findViewById(R.id.cos_listview_home);

        // cos_list
        // 바우만 테스트 결과 = cos DB에 바우만 컬럼의 값이 일치하는 화장품 가져와서 add

        // sample 데이터
        cos_list.add(new CosVO("닥터자르트 세라마이딘 크림 50ml", "38,000", "닥터자르트", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0016/A00000016073403ko.jpg?l=ko"));
        cos_list.add(new CosVO("리얼베리어 익스트림 크림 50ml", "26,900", "리얼베리어", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0016/A00000016072107ko.jpg?l=ko"));
        cos_list.add(new CosVO("에스티 로더 갈색병 세럼 30ML", "89,400", "에스티로더", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0014/A00000014663504ko.jpg?l=ko"));
        cos_list.add(new CosVO("가히 김고은 멀티밤", "29,400", "가히", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0015/A00000015499111ko.jpeg?l=ko"));
        cos_list.add(new CosVO("크리니크 모이스춰 써지 쏙보습크림 50ml", "41,000", "크리니크", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0015/A00000015887308ko.jpg?l=ko"));

        Cos_home_Adapter adapter = new Cos_home_Adapter(R.layout.cos_custom_home, cos_list, getContext().getApplicationContext(),Fragment_tab1.this);
        lv.setAdapter(adapter);

        // template  >> R.layout.custom  우리가 디자인한 템플릿
        // data >> 데이터를 저장한 어레이 리스트

        return v;
    }
}