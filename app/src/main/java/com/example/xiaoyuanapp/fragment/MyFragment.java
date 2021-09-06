package com.example.xiaoyuanapp.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.xiaoyuanapp.MainActivity;
import com.example.xiaoyuanapp.R;
import com.king.zxing.CaptureFragment;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFragment extends CaptureFragment {

    public static final String KEY_TITLE = "key_title";
    public static final String KEY_IS_QR_CODE = "key_code";
    public static final String KEY_IS_CONTINUOUS = "key_continuous_scan";

    public static final int REQUEST_CODE_SCAN = 0X01;
    public static final int REQUEST_CODE_PHOTO = 0X02;

    public static final int RC_CAMERA = 0X01;

    public static final int RC_READ_PHOTO = 0X02;

    private Class<?> cls;
    private String title;
    private boolean isContinuousScan;

    private Toast toast;

    public MyFragment() {
    }

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my, container, false);

        //获取Shared Preferencesd对象
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("XiaoYuan", MODE_PRIVATE);
        //获取内容
        String sp_account = sharedPreferences.getString("account", null);
        //更新用户
        TextView uid = v.findViewById(R.id.te_uid);
        uid.setText(sp_account);

        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //二维码
        ImageButton imageButton_zxing = view.findViewById(R.id.ibtn_ZXing);
        imageButton_zxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.example.xiaoyuanapp","com.example.xiaoyuanapp.zxing.CustomCaptureActivity");
                intent.setComponent(componentName);
                startActivity(intent);
            }
        });

        //退出登录
        View Loginout = view.findViewById(R.id.rl_loginout);
        Loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //清除自动登录
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("XiaoYuan", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("account", null);
                editor.putString("password", null);
                editor.commit();

                //清除已打开的Activity
                Intent intent = new Intent();
                intent.setClass(getActivity(), MainActivity.class);
                //设置不要刷新将要跳到的界面
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //它可以关掉所要到的界面中间的activity
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //生成二维码
        View code = view.findViewById(R.id.rl_code);
        code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.example.xiaoyuanapp","com.example.xiaoyuanapp.zxing.CodeActivity");
                intent.setComponent(componentName);
                startActivity(intent);
            }
        });

        //联系人
        View contactperson = view.findViewById(R.id.rl_contact_person);
        contactperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName("com.example.xiaoyuanapp","com.example.xiaoyuanapp.activity.ContactActivity");
                intent.setComponent(componentName);
                startActivity(intent);
            }
        });
    }
}