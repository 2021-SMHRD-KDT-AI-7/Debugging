package com.example.project_02;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class GuideAdapter extends FragmentStateAdapter {
    public int mCount;

    public GuideAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if (index == 0) return new GuideFragment_1();
        else if (index == 1) return new GuideFragment_2();
        else if (index == 2) return new GuideFragment_3();
        else if (index == 3) return new GuideFragment_4();
        else if (index == 4) return new GuideFragment_5();
        else return new GuideFragment_6();
    }

    @Override
    public int getItemCount() {
        return 2000;
    } // RecyclerView는 데이터 세트 크기를 가져올 때 이 메서드 호출

    public int getRealPosition(int position) {
        return position % mCount;
    }
}
