package com.example.xiaoyuanapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xiaoyuanapp.entity.Person;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //默认初始化Bmob
        Bmob.initialize(this, "d6666ca12f493fb7129aa68c4c97f634");

        //捆绑组件
        EditText etAccount = findViewById(R.id.account);
        EditText etPassword = findViewById(R.id.password);
        Button button = findViewById(R.id.btn_login);

        //获取Shared Preferencesd对象
        final SharedPreferences sharedPreferences = getSharedPreferences("XiaoYuan", MODE_PRIVATE);
        //获取内容
        String sp_account = sharedPreferences.getString("account", null);
        String sp_password = sharedPreferences.getString("password", null);

        //判断是否存在已存储
        if(sp_account!=null&&sp_password!=null) {
            //登录
            Person.loginByAccount(sp_account, sp_password, new LogInListener<Person>() {
                public void done(Person user, BmobException e) {
                    if (e == null) {
                        //成功登陆，进入页面
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                        //页面加载
                        Intent intent = new Intent();
                        ComponentName componentName = new ComponentName("com.example.xiaoyuanapp","com.example.xiaoyuanapp.HomeActivity");
                        intent.setComponent(componentName);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(MainActivity.this, "账号或密码失效", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            //按钮事件
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //获取组件内容
                    String account = etAccount.getText().toString().trim();
                    String password = etPassword.getText().toString().trim();

                    //登录判断
                    if(account.isEmpty()) {
                        Toast.makeText(MainActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    }
                    else if(password.isEmpty()) {
                        Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    }
                    else {
//                    //查询验证账号
//                    Account user= new Account();
//                    user.setUsername(account);
//                    user.setPassword(password);
//                    user.login(new SaveListener<Account>() {

                        Person.loginByAccount(account, password, new LogInListener<Person>() {
                            public void done(Person user, BmobException e) {
                                if (e == null) {

                                    //向Shared Preferences保存输入内容
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("account", account);
                                    editor.putString("password", password);
                                    editor.commit();

                                    //页面加载
                                    Intent intent = new Intent();
                                    ComponentName componentName = new ComponentName("com.example.xiaoyuanapp","com.example.xiaoyuanapp.HomeActivity");
                                    intent.setComponent(componentName);
                                    startActivity(intent);

                                    //成功登陆，进入页面
                                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

                }

            });

        }


    }

}