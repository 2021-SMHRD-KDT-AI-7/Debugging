package com.example.project_02;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class CosAdapter extends BaseAdapter {
    // xml 에다가 Data로 꾸며줘야함
    // xml 을 Java로 컨트롤 할 수 있도록 객체로 만들어줌 >> inflate
    // 하지만.. inflate는 Activity 클래스에서 밖에 못함

    // 그래서  Activity한테 추출해내야돼! Inflator 를
    // 1) Adapter에서 사용할 데이터들 변수(저장할 공간)로 만들어놓기

    private int template;  // ListView 한 칸에 들어갈 디자인
    private ArrayList<CosVO> data;
    private Context context;  // Activity 에서 보내준 화면정보 / 화면을 구성하는 정보를 담고있는 객체(Activity 핵심) MainActivity 일듯??
    private LayoutInflater inflater; // 추출한 inflater를 저장할 공간
    private Fragment_tab4 activity;
    Bitmap bitmap;

    // int[] cos_sample_list = {R.drawable.cos_sample, R.drawable.cos_sample2, R.drawable.cos_sample3, R.drawable.cos_sample4, R.drawable.cos_sample5};

    public CosAdapter(int template, ArrayList<CosVO> data, Context context, Fragment_tab4 activity) {
        this.template = template;
        this.data = data;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
    }
    // 화장품 테이블
    // 964개
    // ORNT >> { 1, 3, 5}
    // ORPT >> { 1, 4, 6}
    // 1.  16가지 결과에 대한 화장품 5종류 선정 (중복가능)
    // D \ O,  S \ R,  P \ N,  W \ T

    // 2. 그 결과를 listview 보여줄 수 있도록 쿼리문 작성
    // sql >> select * from 화장품 테이블 seq >> ORNT의 배열 의 숫자들과 일치하는 애를 가져와라
    // 3. 태욱 안드-DB 연동

    // 5. DB img_view >> url

    @Override
    public int getCount() {  // 출력할 view의 개수 = VO의 개수
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ArrayList<Integer> DRPT = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> DRNT = new ArrayList<>(Arrays.asList(6, 7, 8, 9, 10));
        ArrayList<Integer> DSPT = new ArrayList<>(Arrays.asList(11, 12, 13, 14, 15));
        ArrayList<Integer> DSNT = new ArrayList<>(Arrays.asList(16, 17, 18, 19, 20));
        ArrayList<Integer> DRPW = new ArrayList<>(Arrays.asList(21, 22, 23, 24, 25));
        ArrayList<Integer> DRNW = new ArrayList<>(Arrays.asList(26, 27, 28, 29, 30));
        ArrayList<Integer> DSPW = new ArrayList<>(Arrays.asList(31, 32, 33, 34, 35));
        ArrayList<Integer> DSNW = new ArrayList<>(Arrays.asList(36, 37, 38, 39, 40));
        ArrayList<Integer> ORPT = new ArrayList<>(Arrays.asList(41, 42, 43, 44, 45));
        ArrayList<Integer> ORNT = new ArrayList<>(Arrays.asList(46, 47, 48, 49, 50));
        ArrayList<Integer> OSPT = new ArrayList<>(Arrays.asList(51, 52, 53, 54, 55));
        ArrayList<Integer> OSNT = new ArrayList<>(Arrays.asList(56, 57, 58, 59, 60));
        ArrayList<Integer> ORPW = new ArrayList<>(Arrays.asList(61, 62, 63, 64, 65));
        ArrayList<Integer> ORNW = new ArrayList<>(Arrays.asList(66, 67, 68, 69, 70));
        ArrayList<Integer> OSPW = new ArrayList<>(Arrays.asList(71, 72, 73, 74, 75));
        ArrayList<Integer> OSNW = new ArrayList<>(Arrays.asList(76, 77, 78, 79, 80));

        if (view == null) {
            view = inflater.inflate(template, viewGroup, false);
        }
        TextView name = view.findViewById(R.id.tv_cos_name);
        TextView brand = view.findViewById(R.id.tv_cos_brand);
        TextView price = view.findViewById(R.id.tv_cos_price);
        TextView cos_count = view.findViewById(R.id.cos_count);
        ImageView cos_img = view.findViewById(R.id.cos_img);

        // img.setImageResource(cos_sample_list[i]);
        cos_count.setText(i + 1 + "");

        name.setText(data.get(i).getCos_name());
        brand.setText(data.get(i).getCos_brand());
        price.setText(data.get(i).getCos_price());

        // ---------------------------------------------------------- URL ImageView 출력 --------------------------------------
        // 안드로이드에서 네트워크와 관련된 작업을 할 때,
        // 반드시 메인 Thread가 아닌 별도의 작업 Thread를 생성하여 작업해야 한다.
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(data.get(i).getCos_img());
                    // Web에서 이미지를 가져온 뒤
                    // ImageView에 지정할 Bitmap을 만든다
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
            // 메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야한다
            // join()를 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 한다
            mThread.join();
            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            // UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다
            cos_img.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ---------------------------------------------------------- URL ImageView 출력 --------------------------------------
        return view;
    }
}
