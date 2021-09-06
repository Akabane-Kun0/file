/*
 * Copyright (C) 2018 Jenly Yu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.xiaoyuanapp.zxing;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xiaoyuanapp.R;
import com.example.xiaoyuanapp.fragment.MyFragment;
import com.google.zxing.BarcodeFormat;
import com.king.zxing.util.CodeUtils;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class CodeActivity extends AppCompatActivity {

    private TextView tvTitle;
    private ImageView ivCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_activity);
        ivCode = findViewById(R.id.ivCode);
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(getIntent().getStringExtra(MyFragment.KEY_TITLE));
        boolean isQRCode = getIntent().getBooleanExtra(MyFragment.KEY_IS_QR_CODE,false);

        if(!isQRCode){
            //获取Shared Preferencesd对象
            SharedPreferences sharedPreferences = getSharedPreferences("XiaoYuan", MODE_PRIVATE);
            //获取内容
            String sp_account = sharedPreferences.getString("account", null);
            createQRCode(sp_account);
        }else{
            createBarCode("1234567890");
        }
    }

    /**
     * 生成二维码
     * @param content
     */
    private void createQRCode(String content){
        new Thread(() -> {
            //生成二维码相关放在子线程里面
            Bitmap logo = BitmapFactory.decodeResource(getResources(),R.mipmap.head);
            Bitmap bitmap =  CodeUtils.createQRCode(content,600,logo);
            runOnUiThread(()->{
                //显示二维码
                ivCode.setImageBitmap(bitmap);
            });
        }).start();

    }

    /**
     * 生成条形码
     * @param content
     */
    private void createBarCode(String content){
        new Thread(() -> {
            //生成条形码相关放在子线程里面
            Bitmap bitmap = CodeUtils.createBarCode(content, BarcodeFormat.CODE_128,800,200,null,true);
            runOnUiThread(()->{
                //显示条形码
                ivCode.setImageBitmap(bitmap);
            });
        }).start();
    }



    public void onClick(View v){
        switch (v.getId()){
            case R.id.ivLeft:
                onBackPressed();
                break;
        }
    }
}
