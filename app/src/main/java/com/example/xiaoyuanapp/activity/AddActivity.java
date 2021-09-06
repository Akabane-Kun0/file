package com.example.xiaoyuanapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xiaoyuanapp.R;
import com.example.xiaoyuanapp.entity.Line;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button bt_sub = findViewById(R.id.bt_submit);
        Button bt_gu = findViewById(R.id.bt_giveup);

        bt_gu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //发表话题
        bt_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = findViewById(R.id.sp_type);
                EditText editText = findViewById(R.id.et_description);

                if(editText.getText().toString()==null||editText.getText().toString().length()==0) {
                    Toast.makeText(getApplicationContext(), "请输入话题内容", Toast.LENGTH_SHORT).show();
                }
                else {
                    //获取Shared Preferencesd对象
                    SharedPreferences sharedPreferences = getSharedPreferences("XiaoYuan", MODE_PRIVATE);
                    //获取内容
                    String sp_account = sharedPreferences.getString("account", null);

                    //添加实时时间
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm E");
                    sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                    Date dt = new Date();
                    String str_time = sdf.format(dt);

                    Line p2 = new Line();
                    p2.setTalk_uid(sp_account);
                    p2.setTalk_content(editText.getText().toString());
                    p2.setTalk_type(spinner.getSelectedItem().toString());
                    p2.setTalk_time(str_time);
                    p2.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if(e==null){
                                Toast.makeText(getApplicationContext(), "发表成功，刷新后可查看", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "发表失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

    }
}