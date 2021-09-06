package com.example.xiaoyuanapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xiaoyuanapp.R;
import com.example.xiaoyuanapp.adapter.ContactAdapter;
import com.example.xiaoyuanapp.entity.Sms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ContactActivity extends AppCompatActivity {

    List<Map<String,Object>> listitem = new ArrayList<Map<String,Object>>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        listView = findViewById(R.id.recyclerView_contact);

        //获取Shared Preferencesd对象
        SharedPreferences sharedPreferences = getSharedPreferences("XiaoYuan", MODE_PRIVATE);
        //获取内容
        String sp_account = sharedPreferences.getString("account", null);

        //数据库查询
        BmobQuery<Sms> query = new BmobQuery<Sms>();
        //查询条件：数据库字段talk_type==title
        query.addWhereEqualTo("contact_person", sp_account);
        //排序升序
        query.order("id");
//        //设置查询条数
//        query.setLimit();
        //执行查询方法
        query.findObjects(new FindListener<Sms>() {
            @Override
            public void done(List<Sms> object, BmobException e) {
                if (e == null) {
                    //该类别项为空
                    if (object.isEmpty()) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("id", "暂无");
                        map.put("phone", "暂无");
                        listitem.add(map);
                    }
                    else {
                        for (Sms sms : object) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("id", sms.getId());
                            map.put("phone", sms.getPhone());
                            listitem.add(map);
                        }
                        ContactAdapter adapter = new ContactAdapter(getApplicationContext(), listitem, R.layout.item_sms_layout, new String[]{"id", "phone"}, new int[]{R.id.tv_cid, R.id.tv_phone});
                        listView.setAdapter(adapter);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                }

            }

        });


    }

}