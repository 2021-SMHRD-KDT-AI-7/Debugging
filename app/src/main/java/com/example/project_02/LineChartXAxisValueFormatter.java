package com.example.project_02;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class LineChartXAxisValueFormatter extends IndexAxisValueFormatter {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String getFormattedValue(float value) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();

        long emissionsMilliSince1970Time = TimeUnit.DAYS.toMillis((long) value);
        Date timeMilliseconds = new Date(emissionsMilliSince1970Time);
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM-dd");

        return year + "-" + dateTimeFormat.format(timeMilliseconds);
    }
}