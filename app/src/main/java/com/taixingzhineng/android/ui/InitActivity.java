package com.taixingzhineng.android.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/12/15.
 */

public class InitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获得是否勾选自动登录数据
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        Boolean automaticLogin = preferences.getBoolean("automaticLogin", false);
        Intent intent;
        //判断是否勾选自动登录
        if(automaticLogin){
            intent = new Intent(InitActivity.this,IndexActivity.class);
        }else{
            intent = new Intent(InitActivity.this,LoginActivity.class);
            //intent = new Intent(InitActivity.this,TestActivity.class);//消息提示测试
        }
        startActivity(intent);
    }
}
