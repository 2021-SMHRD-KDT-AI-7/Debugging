package com.example.project_02;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Fragment_tab4 extends Fragment {
    ArrayList<CosVO> cos_list = new ArrayList<>();
    ListView cos_lv;
    TextView cos_count, user;
    String userName;
    int cnt = 0;
    Bundle bundle;
    Context mContext;
    RequestQueue rq;
    StringRequest sr;
    JSONObject jsonObject, cos_json;
    JSONArray jArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab4, container, false);

        cos_lv = v.findViewById(R.id.cos_lv);
        cos_count = v.findViewById(R.id.cos_count);
        user = v.findViewById(R.id.tv_user);
        bundle = getArguments();
        mContext = v.getContext();
        userName = PreferenceManager.getString(mContext, "user_id") + "님을 위한 추천상품입니다.";
        user.setText(userName);
        rq = Volley.newRequestQueue(
                Objects.requireNonNull(getContext()).getApplicationContext());

        sr = new StringRequest(Request.Method.POST,
                "",
                response -> {
                    if (response != null) {
                        try {
                            jsonObject = new JSONObject(response); //데이터 받아옴
                            jArray = jsonObject.optJSONArray("list"); //데이터 어레이로 분리
                            cos_json = (JSONObject) Objects.requireNonNull(jArray).opt(cnt); //다시 분리
                            String cos_name = cos_json.optString("cos_name");
                            String cos_brand = cos_json.optString("cos_brand");
                            String cos_price = cos_json.optString("cos_price");
                            String cos_img = cos_json.optString("cos_img");
                            cos_list.add(new CosVO(cos_name, cos_brand, cos_price, cos_img));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> {
        }) {
            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("conn", Integer.toString(cnt));

                return data;
            }
        };
        Toast.makeText( getContext(),"추천 상품의 효과는 개인마다 차이가 있을 수 있습니다. ", Toast.LENGTH_LONG).show();
        Toast.makeText( getContext(),"또한, 미러는 추천 상품의 정보를 제공 할 뿐 어떠한 책임도 지지않습니다.", Toast.LENGTH_LONG).show();

        // cos_list
        // 바우만 테스트 결과 = cos DB에 바우만 컬럼의 값이 일치하는 화장품 가져와서 add
        cos_list.add(new CosVO("닥터자르트 세라마이딘 크림 50ml", "38,000", "닥터자르트", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0016/A00000016073403ko.jpg?l=ko"));
        cos_list.add(new CosVO("리얼베리어 익스트림 크림 50ml", "26,900", "리얼베리어", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0016/A00000016072107ko.jpg?l=ko"));
        cos_list.add(new CosVO("에스티 로더 갈색병 세럼 30ML", "89,400", "에스티로더", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0014/A00000014663504ko.jpg?l=ko"));
        cos_list.add(new CosVO("가히 김고은 멀티밤", "29,400", "가히", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0015/A00000015499111ko.jpeg?l=ko"));
        cos_list.add(new CosVO("크리니크 모이스춰 써지 쏙보습크림 50ml", "41,000", "크리니크", "https://image.oliveyoung.co.kr/uploads/images/goods/550/10/0000/0015/A00000015887308ko.jpg?l=ko"));

        CosAdapter adapter = new CosAdapter(R.layout.cos_custom, cos_list, Objects.requireNonNull(getContext()).getApplicationContext(), Fragment_tab4.this);
        cos_lv.setAdapter(adapter);
        // template  >> R.layout.custom  우리가 디자인한 템플릿
        // data >> 데이터를 저장한 어레이 리스트

        return v;
    }
}