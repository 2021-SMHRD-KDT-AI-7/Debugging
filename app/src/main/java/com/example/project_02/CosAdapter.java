package com.example.project_02;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CosAdapter extends BaseAdapter {
    // xml 에다가 Data로 꾸며줘야함!
    // xml 을 Java로 컨트롤 할 수 있도록 객체로 만들어줌 >> inflate
    // 하지만.. inflate는 Activity 클래스에서 밖에 못함

    // 그래서  Activity한테 추출해내야돼! Inflator 를.

    // 1) Adapter에서 사용할 데이터들 변수(저장할 공간)로 만들어놓기~

    private int template;  // ListView 한 칸에  들어갈 디자인~
    private ArrayList<CosVO> data;
    private Context context;  // Activity 에서 보내준 화면정보!  // 화면을 구성하는 정보를 담고있는 객체( Activity 핵심)  MainActivity 일듯??
    private LayoutInflater inflater; // 추출한 inflater를 저장할 공간~
    private Fragment_tab4 activity;
    // int[] cos_sample_list = { R.drawable.cos_sample,R.drawable.cos_sample2,R.drawable.cos_sample3,R.drawable.cos_sample4,R.drawable.cos_sample5};

    public CosAdapter(int template, ArrayList<CosVO> data, Context context, Fragment_tab4 activity) {
        this.template = template;
        this.data = data;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.activity = activity;
    }

    @Override
    public int getCount() {  // 출력할 view의 개수    = VO의 개수
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

        if (view == null) {
            view = inflater.inflate(template, viewGroup, false);
        }
        ImageView img = view.findViewById(R.id.cos_img);

        TextView name = view.findViewById(R.id.tv_cos_name);
        TextView brand = view.findViewById(R.id.tv_cos_brand);
        TextView price = view.findViewById(R.id.tv_cos_price);
        TextView cos_count = view.findViewById(R.id.cos_count);

        // img.setImageResource(cos_sample_list[i]);

        cos_count.setText(i+1+"");

        name.setText(data.get(i).getCos_name());
        brand.setText(data.get(i).getCos_brand());
        price.setText(data.get(i).getCos_price() + "원");

        return view;
    }
}
