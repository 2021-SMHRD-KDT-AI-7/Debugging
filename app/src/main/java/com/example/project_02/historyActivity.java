package com.example.project_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class historyActivity extends AppCompatActivity {

    LineChart lineChart; // 차트
    ListView lv; // 리스트뷰
    ArrayList<historyVO> data = new ArrayList<>();
    String id, anal_date;

    RequestQueue rq;
    StringRequest sr;
    JSONObject his_json;
    JSONArray jArray;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lv = findViewById(R.id.lv_);
        lineChart = findViewById(R.id.chart);
        mContext = this;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = pref.edit();
        id = pref.getString("user_id", "");
        rq = Volley.newRequestQueue(getApplicationContext());
        sr = new StringRequest(Request.Method.POST,
                "http://121.147.52.64:8081/Mirror/bauman", response -> {
            if (!response.equals("데이터가 없습니다.")) {
                try {
                    jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        his_json = (JSONObject) jArray.opt(i); // 해체
                        Log.d("json", String.valueOf(his_json));
                        String bauman = his_json.optString("bauman");
                        int sk_res = his_json.optInt("sk_res") / 4;
                        int sk_oil = his_json.optInt("sk_oil");
                        int sk_sen = his_json.optInt("sk_sen");
                        int sk_pig = his_json.optInt("sk_pig");
                        int sk_ela = his_json.optInt("sk_ela");
                        String user_id = his_json.optString("user_id");
                        String date = his_json.optString("anal_date");
                        anal_date = his_json.optString("anal_date");
                        String img_src = his_json.optString("img_src");

                        editor.putString("bauman", bauman);
                        editor.putString("anal_date", date);
                        editor.apply();

                        data.add(new historyVO(
                                bauman, sk_res, sk_oil, sk_sen, sk_pig, sk_ela,
                                user_id, date, img_src));
                    }
                    List<Entry> entries = new ArrayList<>(); // 차트 데이터값
                    for (int i = 0; i < data.size(); i++) {
                        entries.add(new Entry(i, data.get(i).getSk_res()));
                    }
                    LineDataSet lineDataSet = new LineDataSet(entries, null); // 차트 데이터 디자인
                    lineDataSet.setLineWidth(2); // 선 굵기
                    lineDataSet.setCircleRadius(2); // 원 크기
                    lineDataSet.setCircleHoleRadius(1);
                    lineDataSet.setCircleColor(Color.parseColor("#242424")); // 원 색상
                    lineDataSet.setGradientColor(Color.parseColor("#00FFA7C4"), Color.parseColor("#FFFFA7C4")); // 선 색상
                    lineDataSet.setDrawCircleHole(false); // 원 구멍
                    lineDataSet.setDrawCircles(true); // 원 그리기
                    lineDataSet.setDrawHighlightIndicators(true); // 눌렀을때 라인 표시
                    lineDataSet.setDrawValues(true); // 값 보여주기
                    lineDataSet.setValueTextSize(10);

                    LineData lineData = new LineData(lineDataSet); // 차트 그리기
                    XAxis xAxis = lineChart.getXAxis(); // X축 생성
                    xAxis.setSpaceMax(0.3f); // X축 여백
                    xAxis.setSpaceMin(0.3f); // X축 여백
                    xAxis.setLabelCount(2, /*force: */true); // X축을 count개로 분할
                    String[] xAxisdate = {data.get(0).getAnal_date(), anal_date};
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisdate));
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // X축 위치
                    xAxis.setTextColor(Color.parseColor("#242424")); // 텍스트 색상
                    xAxis.setTextSize(12);
                    xAxis.setDrawGridLines(false);

                    YAxis yLAxis = lineChart.getAxisLeft(); // 왼쪽 y축 생성
                    YAxis yRAxis = lineChart.getAxisRight(); // 오른쪽 y축 생성
                    yLAxis.setTextColor(Color.parseColor("#242424")); // y축 텍스트 색상
                    yRAxis.setTextColor(Color.parseColor("#242424"));
                    yLAxis.setDrawGridLines(false);
                    yRAxis.setDrawGridLines(false);
                    yLAxis.setAxisMinimum(0); // y축 최소값
                    yRAxis.setAxisMinimum(0);
                    yLAxis.setAxisMaximum(110); // y축 최대값
                    yRAxis.setAxisMaximum(110);

                    Description description = new Description(); // 차트 주석 생성
                    description.setText(""); // 주석 미기입

                    lineChart.setData(lineData); // 차트 생성
                    lineChart.setDoubleTapToZoomEnabled(false);
                    lineChart.setPinchZoom(false);
                    lineChart.setDrawGridBackground(false); // 배경에 점선 표시?
                    lineChart.setDescription(description); // 주석 그리기
                    lineChart.invalidate();
                    lineChart.animateXY(1000, 2000,
                            Easing.EaseInOutCubic, Easing.EaseInOutCubic); // 차트 그리기 애니메이션
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), response + "", Toast.LENGTH_SHORT).show();
                List<Entry> entries = new ArrayList<>(); // 차트 데이터값
                LineDataSet lineDataSet = new LineDataSet(entries, null); // 차트 데이터 디자인
                LineData lineData = new LineData(lineDataSet); // 차트 그리기
                Description description = new Description(); // 차트 주석 생성
                description.setText("");
                lineChart.setData(lineData); // 차트 생성
                lineChart.setDoubleTapToZoomEnabled(false);
                lineChart.setPinchZoom(false);
                lineChart.setDrawGridBackground(false); // 배경에 점선 표시?
                lineChart.setDescription(description); // 주석 그리기
                lineChart.invalidate();
            }
            historyAdapter adapter = new historyAdapter(R.layout.history_custom, data,
                    getApplicationContext(), historyActivity.this);
            lv.setAdapter(adapter);
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
                data.put("user_id", id);
                return data;
            }
        };
        rq.add(sr);
    }
}