package com.example.project_02;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
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
    TextView user, user_type, tv_type, type_memo;
    ImageView user_image;
    String userName;
    String result2, result = "", type_explanation;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab3, container, false);

        user_type = v.findViewById(R.id.user_type);
        tv_type = v.findViewById(R.id.tv_type);
        user_image = v.findViewById(R.id.user_image);
        type_memo = v.findViewById(R.id.type_memo);
        user = v.findViewById(R.id.tv_user2);
        barChart = v.findViewById(R.id.barchart);
        mContext = v.getContext();

        Bundle bundle = getArguments();
        assert bundle != null;
        scoreoil = bundle.getDouble("scoreoil");
        scoresen = bundle.getDouble("scoresen");
        scoremel = bundle.getDouble("scoremel");
        scoretin = bundle.getDouble("scoretin");

        result = bundle.getString("result") + " ?????? ?????????.";
        result2 = bundle.getString("result");
        type_explanation = bundle.getString("memos");

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);
        userName = pref.getString("user_name", "") + " ??????";
        user.setText(userName);
        user_type.setText(result2);
        tv_type.setText(result);
        type_memo.setText(type_explanation);

        // ???????????? ????????? ???????????????
        Bitmap bitmap = getArguments().getParcelable("a");
        user_image.setImageBitmap(bitmap);

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

        BarData barData = new BarData(barDataSet);

        XAxis xAxis = barChart.getXAxis(); // X???

        xAxis.setDrawGridLines(false); // ?????? ??????
        xAxis.setDrawLabels(false);
        //  xAxis.setValueFormatter(new IndexAxisValueFormatter(skin)); // X?????? ?????????????????? ??????

        YAxis yLAxis = barChart.getAxisLeft(); // y??? ??????
        yLAxis.setDrawGridLines(false); // ?????? ??????
        yLAxis.setTextSize(16); // ????????? ??????
        yLAxis.setAxisMinimum(0); // y??? ?????????
        yLAxis.setAxisMaximum(115); // y??? ?????????
        barChart.getAxisRight().setDrawLabels(false); //

        Description description = new Description(); // ?????? ?????? ??????
        description.setText(""); // ?????? ?????????

        barChart.setData(barData); // ?????? ??????
        barChart.setDoubleTapToZoomEnabled(false); // ?????????????????? ????????????
        barChart.setTouchEnabled(false); // ?????? ??????
        barChart.setDescription(description); // ??????
        barChart.invalidate();
        barChart.animateXY(1000, 2000,
                Easing.EaseInOutCubic, Easing.EaseInOutCubic);
        return v;
    }

    public String getByteData() {
        SharedPreferences prefs = this.getActivity().getSharedPreferences("response_bitmap", Context.MODE_PRIVATE);
        String value = prefs.getString("response", null);

        return value;
    }
}