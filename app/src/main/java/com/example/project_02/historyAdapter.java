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
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(data.get(i).getImg_src());
                    // Web에서 이미지를 가져온 뒤 ImageView에 지정할 Bitmap을 만든다
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // 서버로 부터 응답 수신
                    conn.connect();
                    InputStream is = conn.getInputStream(); // InputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start(); // Thread 실행
        try {
            // join()를 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 한다
            mThread.join();
            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤 UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다
            face.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        date.setText(data.get(i).getAnal_date());
        Bauman.setText(data.get(i).getBauman());
        res.setText(Integer.toString(data.get(i).getSk_res()));
        notifyDataSetChanged();
        return view;
    }
}
