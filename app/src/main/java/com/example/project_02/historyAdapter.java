package com.example.project_02;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class historyAdapter extends BaseAdapter {

    int template;
    ArrayList<historyVO> data;
    Context context;
    LayoutInflater inflater;
    Activity activity;
    Bitmap bitmap;

    public historyAdapter(int template, ArrayList<historyVO> data,
                          Context context, Activity activity) {
        this.template = template;
        this.data = data;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(template, viewGroup, false);
        }
        ImageView face = view.findViewById(R.id.iv_face);
        TextView date = view.findViewById(R.id.tv_date);
        TextView Bauman = view.findViewById(R.id.tv_time);
        TextView res = view.findViewById(R.id.tv_res);

        date.setText(data.get(i).getAnal_date());
        Bauman.setText(data.get(i).getBauman());
        res.setText(Integer.toString(data.get(i).getSk_res()));
        notifyDataSetChanged();
        return view;
    }
}
