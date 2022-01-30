package com.example.project_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import me.relex.circleindicator.CircleIndicator3;

public class GuideActivity extends AppCompatActivity {

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 6;
    private CircleIndicator3 mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //ViewPager2
        mPager = findViewById(R.id.viewpager);

        //Adapter
        pagerAdapter = new GuideAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);

        //Indicator
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page,0);

        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(1002); // 시작지점, 왼쪽 오른쪽 반으로 나눠서 1000장씩 왓다리 갓다리
        mPager.setOffscreenPageLimit(6); // 최대 이미지수

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {//선택한 페이지의 상태 변경에 응답하기 위한 콜백 인터페이스
            // 여기서 indicator의 위치를 이미지의 위치와 동일하게 설정
            @Override
            public void onPageSelected(int position) {//이 메서드는 새 페이지가 선택되면 호출
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position%num_page);
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {//스크롤 상태가 변경될 때 호출
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                //positionOffset-[0, 1)의 값은 해당 위치의 페이지로부터의 오프셋
                if (positionOffsetPixels == 0) {
                    //위치에서의 오프셋을 나타내는 픽셀 단위 값
                    mPager.setCurrentItem(position);
                }
            }
        });

        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);

        mPager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float myOffset = position * -(2 * pageOffset + pageMargin);
                if (mPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(mPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.setTranslationX(-myOffset);
                    } else {
                        page.setTranslationX(myOffset);
                    }
                } else {
                    page.setTranslationY(myOffset);
                }
            }
        });

    }
}