package com.example.xiaoyuanapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.xiaoyuanapp.R;
import com.example.xiaoyuanapp.adapter.ShopAdapter;
import com.example.xiaoyuanapp.entity.DemandEntity;
import com.example.xiaoyuanapp.fragment.DemandFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity<onViewCreated> extends AppCompatActivity {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "食堂1", "食堂2", "食堂3", "食堂4", "超市1", "超市2"
    };
    private List<DemandEntity> datas = new ArrayList<>();
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //获取Shared Preferencesd对象,创建XiaoYuan_Select文件，初始化搜素语句为null
        SharedPreferences sharedPreferences = getSharedPreferences("XiaoYuan", MODE_PRIVATE);
        String sp_ds = sharedPreferences.getString("demand_search", null);

        //获取页面与选项卡组件
        viewPager = findViewById(R.id.fixedViewPager_shop);
        slidingTabLayout = findViewById(R.id.slidingTabLayout_shop);
        //创建各个fragment页面
        for (String title : mTitles) {
            mFragments.add(DemandFragment.newInstance(title));
        }
        //组合整个ShopActivity，及预加载
        viewPager.setOffscreenPageLimit(mFragments.size());
        viewPager.setAdapter(new ShopAdapter(getSupportFragmentManager(), mTitles, mFragments));
        slidingTabLayout.setViewPager(viewPager);


        //下拉刷新功能
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //图案回收
                swipeRefreshLayout.setRefreshing(false);
                //刷新fragment页面
                mFragments.clear();
                for (String title : mTitles) {
                    mFragments.add(DemandFragment.newInstance(title));
                }
                viewPager.setAdapter(new ShopAdapter(getSupportFragmentManager(), mTitles, mFragments));
            }
        });


        //搜索功能
        ImageButton imageButton_search = findViewById(R.id.ib_search);
        imageButton_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取输入框EditText内容
                EditText editText_search = findViewById(R.id.et_search);
                String et_search = editText_search.getText().toString();

                //内容不为空，则搜索
                if(!et_search.isEmpty()) {

                    //获取Shared Preferencesd对象
                    SharedPreferences sharedPreferences = getSharedPreferences("XiaoYuan", MODE_PRIVATE);
                    //向Shared Preferences保存输入内容
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("demand_search", et_search);
                    editor.commit();

                    //重加载fragment页面
                    mFragments.clear();
                    for (String title : mTitles) {
                        mFragments.add(DemandFragment.newInstance(title));
                    }
                    viewPager.setAdapter(new ShopAdapter(getSupportFragmentManager(), mTitles, mFragments));

                    //加载结束，清除XiaoYuan_Select文件select语句
                    editor.putString("demand_search", null);
                    editor.commit();
                }
                else {

                }

            }
        });


    }
}