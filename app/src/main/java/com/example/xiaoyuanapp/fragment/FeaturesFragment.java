package com.example.xiaoyuanapp.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.xiaoyuanapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeaturesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeaturesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeaturesFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FeaturesFragment.
//     */
    // TODO: Rename and change types and number of parameters
    public static FeaturesFragment newInstance() {
        FeaturesFragment fragment = new FeaturesFragment();
//不传参，参数：String param1, String param2
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_features, container, false);

        //校历公告按钮事件
        Button buttonCalendar = view.findViewById(R.id.btn_calendar);
        buttonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //页面加载
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.example.xiaoyuanapp","com.example.xiaoyuanapp.activity.CalendarActivity");
                intent.setComponent(componentName);
                startActivity(intent);
            }
        });

        //校园网店按钮事件
        Button buttonShop = view.findViewById(R.id.btn_shop);
        buttonShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //页面加载
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.example.xiaoyuanapp","com.example.xiaoyuanapp.activity.ShopActivity");
                intent.setComponent(componentName);
                startActivity(intent);
            }
        });

        //地图定位按钮事件
        Button buttonMap = view.findViewById(R.id.btn_map);
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //页面加载
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.example.xiaoyuanapp","com.example.xiaoyuanapp.activity.MapActivity");
                intent.setComponent(componentName);
                startActivity(intent);
            }
        });

        //图书馆按钮事件
        Button buttonLibrary = view.findViewById(R.id.btn_library);
        buttonLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //页面加载
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.lib.ahu.edu.cn/"));
//                ComponentName componentName = new ComponentName("com.example.xiaoyuanapp","com.example.xiaoyuanapp.activity.LibraryActivity");
//                intent.setComponent(componentName);
                startActivity(intent);
            }
        });

        //教务系统按钮事件
        Button buttonEsystem = view.findViewById(R.id.btn_esystem);
        buttonEsystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //页面加载
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://jwxt4.ahu.edu.cn/"));
//                ComponentName componentName = new ComponentName("com.example.xiaoyuanapp","com.example.xiaoyuanapp.activity.EsystemActivity");
//                intent.setComponent(componentName);
                startActivity(intent);
            }
        });

        return view;

    }
}