package com.example.project_02;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
public class historyActivity extends AppCompatActivity {

                int scoreavg;
                private LineChart lineChart; // 차트
                ListView lv; // 리스트뷰
                ArrayList<historyListVO> data = new ArrayList<>();

                @Override
                protected void onCreate (Bundle savedInstanceState){
                        super.onCreate(savedInstanceState);
                        setContentView(R.layout.activity_history);

                        lv = findViewById(R.id.lv_);
                        lineChart = findViewById(R.id.chart);
                        scoreavg = (int)BaumannFragment.scoreavg;

                        data.add(new historyListVO(R.drawable.front_face1, 22_02_07, "09 : 30", scoreavg));
                        data.add(new historyListVO(R.drawable.front_face2, 22_02_10, "15 : 10", 57));
                        data.add(new historyListVO(R.drawable.front_face3, 22_02_11, "11 : 45", 89));

                        List<Entry> entries = new ArrayList<>(); // 차트 데이터값
                        entries.add(new Entry(data.get(0).getdate(), data.get(0).getRes()));
                        entries.add(new Entry(data.get(1).getdate(), data.get(1).getRes()));
                        entries.add(new Entry(data.get(2).getdate(), data.get(2).getRes()));

                        LineDataSet lineDataSet = new LineDataSet(entries, null); // 차트 데이터 디자인
                        lineDataSet.setLineWidth(2); // 선 굵기
                        lineDataSet.setCircleRadius(2); // 원 크기
                        lineDataSet.setCircleHoleRadius(1);
                        lineDataSet.setCircleColor(Color.parseColor("#242424")); // 원 색상
                        lineDataSet.setColor(Color.parseColor("#ffa7c4")); // 선 색상
                        lineDataSet.setDrawCircleHole(false); // 원 구멍
                        lineDataSet.setDrawCircles(true); // 원 그리기
                        lineDataSet.setDrawHighlightIndicators(true); // 눌렀을때 라인 표시
                        lineDataSet.setDrawValues(true); // 값 보여주기
                        lineDataSet.setValueTextSize(10);

                        LineData lineData = new LineData(lineDataSet); // 차트 그리기
                        XAxis xAxis = lineChart.getXAxis(); // X축 생성
                        xAxis.setSpaceMax(0.5f); // X축 여백
                        xAxis.setSpaceMin(0.5f); // X축 여백
                        xAxis.setValueFormatter(new LineChartXAxisValueFormatter()); // X축 포맷 변경
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // X축 위치
                        xAxis.setTextColor(Color.parseColor("#242424")); // 텍스트 색상
                        xAxis.setLabelCount(2, /*force: */true); // X축을 count개로 분할
                        xAxis.setDrawGridLines(false);

                        YAxis yLAxis = lineChart.getAxisLeft(); // 왼쪽 y축 생성
                        YAxis yRAxis = lineChart.getAxisRight(); // 오른쪽 y축 생성
                        yLAxis.setTextColor(Color.parseColor("#242424")); // y축 텍스트 색상
                        yRAxis.setTextColor(Color.parseColor("#242424"));
                        yLAxis.setDrawGridLines(false);
                        yRAxis.setDrawGridLines(false);
                        yLAxis.setAxisMinimum(0); // y축 최소값
                        yRAxis.setAxisMinimum(0);
                        yLAxis.setAxisMaximum(115); // y축 최대값
                        yRAxis.setAxisMaximum(115);

                        Description description = new Description(); // 차트 주석 생성
                        description.setText(""); // 주석 미기입

                        lineChart.setData(lineData); // 차트 생성
                        lineChart.setDoubleTapToZoomEnabled(true); // 더블터치하면 확대하기
                        lineChart.setDrawGridBackground(true); // 배경에 점선 표시?
                        lineChart.setDescription(description); // 주석 그리기
                        lineChart.invalidate();
                        lineChart.animateXY(1000, 2000,
                                Easing.EaseInOutCubic, Easing.EaseInOutCubic); // 차트 그리기 애니메이션

                        // 차트 끝
                        historyAdapter adapter = new historyAdapter(R.layout.history_custom, data,
                                getApplicationContext(), historyActivity.this);
                        lv.setAdapter(adapter);
                }

}