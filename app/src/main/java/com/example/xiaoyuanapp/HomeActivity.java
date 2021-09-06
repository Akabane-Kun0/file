package com.example.xiaoyuanapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.xiaoyuanapp.adapter.MyPagerAdapter;
import com.example.xiaoyuanapp.entity.TabEntity;
import com.example.xiaoyuanapp.fragment.FeaturesFragment;
import com.example.xiaoyuanapp.fragment.LineFragment;
import com.example.xiaoyuanapp.fragment.MyFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    //设置tab标签，主题文字与图标
    private String[] mTitles = {"功能", "话题", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.features_unselect, R.mipmap.line_unselect,
            R.mipmap.my_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.features_selected, R.mipmap.line_selected,
            R.mipmap.my_selected};

    //存放fragment对象集合
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    //添加tab按钮
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    //默认动画效果，切换tab无滑动
    private ViewPager viewPager;
    private CommonTabLayout commonTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = findViewById(R.id.viewpager);
        commonTabLayout = findViewById(R.id.commonTabLayout);

        mFragments.add(FeaturesFragment.newInstance());
        mFragments.add(LineFragment.newInstance());
        mFragments.add(MyFragment.newInstance());

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        commonTabLayout.setTabData(mTabEntities);
        //tab回调页面,根据下标切换
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles, mFragments));
    }

}