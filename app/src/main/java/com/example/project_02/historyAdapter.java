package com.example.project_02;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class historyAdapter extends BaseAdapter {

    private int template;
    private ArrayList<historyListVO> data;
    private Context context;
    private LayoutInflater inflater;
    private Activity activity;

    public historyAdapter(int template, ArrayList<historyListVO> data,
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
        TextView time = view.findViewById(R.id.tv_time);
        TextView res = view.findViewById(R.id.tv_res);

        face.setImageResource(data.get(i).getImgID());
        date.setText(Integer.toString(data.get(i).getdate()));
        time.setText(data.get(i).getTime());
        res.setText(Integer.toString(data.get(i).getRes()));
        notifyDataSetChanged();
        return view;
    }
}
