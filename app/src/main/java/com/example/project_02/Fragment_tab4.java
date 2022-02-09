package com.example.project_02;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
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
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Fragment_tab4 extends Fragment {
    ArrayList<CosVO> cos_list = new ArrayList<>();
    ListView cos_lv;
    TextView cos_count, user;
    String userName;
    int cnt = 10;
    Bundle bundle;
    Context mContext;
    RequestQueue rq;
    StringRequest sr;
    JSONObject jsonObject, cos_json;
    JSONArray jArray;
    int[] temp = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab4, container, false);
        cos_lv = v.findViewById(R.id.cos_lv);
        cos_count = v.findViewById(R.id.cos_count);
        user = v.findViewById(R.id.tv_user);
        bundle = getArguments();
        mContext = v.getContext();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        userName = pref.getString("user_id", "");
        user.setText(userName);
        rq = Volley.newRequestQueue(
                Objects.requireNonNull(getContext()).getApplicationContext());
        sr = new StringRequest(Request.Method.POST,
                "http://121.147.52.64:8081/Mirror/tab4",
                response -> {
                    if (response != null) {
                        try {
                            jsonObject = new JSONObject(response); //데이터 받아옴
                            jArray = jsonObject.optJSONArray("list"); //데이터 어레이로 분리
                            for (int i = 0; i < Objects.requireNonNull(jArray).length(); i++) {
                                cos_json = (JSONObject) jArray.opt(i); //다시 분리
                                Log.d("json", String.valueOf(cos_json));
                                String cos_name = cos_json.optString("cos_name");
                                String cos_price = cos_json.optString("cos_price");
                                String cos_brand = cos_json.optString("cos_brand");
                                String cos_img = cos_json.optString("cos_img");
                                cos_list.add(new CosVO(cos_name, cos_price, cos_brand, cos_img));
                            }
                            CosAdapter adapter = new CosAdapter(R.layout.cos_custom, cos_list,
                                    Objects.requireNonNull(getContext()).getApplicationContext(),
                                    Fragment_tab4.this);
                            cos_lv.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> {
        }) {
            @Override //response를 UTF8로 변경해주는 소스코드
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, StandardCharsets.UTF_8);
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (Exception e) {
                    return Response.error(new ParseError(e));
                }
            }

            @NonNull
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                for (int i = cnt - 10; i < cnt; i++) {
                    data.put("seq" + (i + 1), Integer.toString(temp[i]));
                }
                return data;
            }
        };
        rq.add(sr);
        return v;
    }
}