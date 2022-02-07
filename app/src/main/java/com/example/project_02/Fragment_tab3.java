package com.example.project_02;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Fragment_tab3 extends Fragment {

    ArrayList<Integer> baumanScore = new ArrayList<>();

    double scoreoil;
    double scoremel;
    double scoretin;
    double scoresen;

    ArrayList<String> skintype = new ArrayList<>();

    HorizontalBarChart barChart;
    TextView user, tv_type;
    TextView testView;  //  테스트용
    int testInt; // 테스트용2
    ImageView user_image;
    String userName, result;
    Bitmap user_analyze_image;

    Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab3, container, false);

        tv_type = v.findViewById(R.id.tv_type);
        user_image = v.findViewById(R.id.user_image);
        testView = v.findViewById(R.id.testView);

        user = v.findViewById(R.id.tv_user2);
        if (getArguments() != null) {
            scoreoil = getArguments().getDouble("scoreoil");
            scoresen = getArguments().getDouble("scoresen");
            scoremel = getArguments().getDouble("scoremel");
            scoretin = getArguments().getDouble("scoretin");

            result = getArguments().getString("result");
            testInt = getArguments().getInt("testInt");
        }
        // userName = PreferenceManager.getString(mContext, "user_name") + "님은 ";
        user_analyze_image = getArguments().getParcelable("BitmapImage");
        user_image.setImageBitmap(user_analyze_image);
        testView.setText(testInt+"");


        user.setText(userName);
        tv_type.setText(result + " 타입 입니다.");

        //String[] skin = {"T", "", "N", "", "S", "", "O"};

        barChart = v.findViewById(R.id.barchart);

        baumanScore.add((int) scoreoil);
        baumanScore.add((int) scoresen);
        baumanScore.add((int) scoremel);
        baumanScore.add((int) scoretin);

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, baumanScore.get(3)));
        entries.add(new BarEntry(2, baumanScore.get(2)));
        entries.add(new BarEntry(4, baumanScore.get(1)));
        entries.add(new BarEntry(6, baumanScore.get(0)));

        BarDataSet barDataSet = new BarDataSet(entries, null);
        barDataSet.setDrawValues(true);
        barDataSet.setValueTextSize(9);
        barDataSet.setGradientColor(Color.parseColor("#000000"), Color.parseColor("#FFFFFF"));

        BarData barData = new BarData(barDataSet);

        XAxis xAxis = barChart.getXAxis(); // X축
        xAxis.setTextSize(18); // 텍스트 크기
        xAxis.setDrawLabels(false); // 라벨 삭제
        xAxis.setDrawGridLines(false); // 격자 삭제
        //xAxis.setValueFormatter(new IndexAxisValueFormatter(skin)); // X축을 피부타입으로 변경

        YAxis yLAxis = barChart.getAxisLeft(); // y축 왼쪽
        yLAxis.setDrawGridLines(false); // 격자 삭제
        yLAxis.setAxisMinimum(0); // y축 최소값
        yLAxis.setAxisMaximum(115); // y축 최대값
        barChart.getAxisRight().setDrawLabels(false); //

        Description description = new Description(); // 차트 주석 생성
        description.setText(""); // 주석 미기입

        barChart.setData(barData); // 차트 생성
        barChart.setDoubleTapToZoomEnabled(false); // 더블터치하면 확대하기
        barChart.setTouchEnabled(false); // 터치 가능
        barChart.setDescription(description); // 주석
        barChart.invalidate();
        barChart.animateXY(1000, 2000,
                Easing.EaseInOutCubic, Easing.EaseInOutCubic);
        return v;
    }
}