package com.example.project_02;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Cos_Home_Adapter_Second extends RecyclerView.Adapter<Cos_Home_Adapter_Second.ViewHolder> {

    // 생성자에서 데이터 리스트 객체를 전달받음.
    private ArrayList<CosVO> mdata;
    private Bitmap bitmap;

    Cos_Home_Adapter_Second(ArrayList<CosVO> list){
        mdata = list;
    }
    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public Cos_Home_Adapter_Second.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.cos_custom_home, parent, false) ;
        Cos_Home_Adapter_Second.ViewHolder vh = new Cos_Home_Adapter_Second.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull Cos_Home_Adapter_Second.ViewHolder holder, int position) {
        CosVO item = mdata.get(position) ;

        // ---------------------------------------------------------- URL ImageView 출력 --------------------------------------
        // 안드로이드에서 네트워크와 관련된 작업을 할 때,
        // 반드시 메인 Thread가 아닌 별도의 작업 Thread를 생성하여 작업해야 한다.
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(item.getCos_img());

                    // Web에서 이미지를 가져온 뒤
                    // ImageView에 지정할 Bitmap을 만든다
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // 서버로 부터 응답 수신
                    conn.connect();

                    InputStream is = conn.getInputStream(); // InputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환

                    is.close();
                    conn.disconnect();

                    Log.d("Cost_Home_Adapter_Second", bitmap.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start(); // Thread 실행

        try {
            // 메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야한다
            // join()를 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 한다
            mThread.join();

            Log.d("Cost_Home_Adapter_Second", bitmap.toString());

            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            // UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다
            holder.cos_img_home.setImageBitmap(bitmap);
            holder.cos_img_home.setBackground(new ShapeDrawable(new OvalShape()));
            holder.cos_img_home.setClipToOutline(true);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ---------------------------------------------------------- URL ImageView 출력 --------------------------------------
        String temp = item.getCos_name();

        temp = temp.substring(0,7);
        holder.cos_name_home.setText(temp+"...");
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mdata.size();
    }
    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cos_img_home ;
        TextView cos_name_home ;

        ViewHolder(View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            cos_img_home = itemView.findViewById(R.id.cos_img_home);
            cos_name_home = itemView.findViewById(R.id.cos_name_home);
        }
    }
}
