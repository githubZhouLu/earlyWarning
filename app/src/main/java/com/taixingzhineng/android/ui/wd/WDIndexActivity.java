package com.taixingzhineng.android.ui.wd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.IndexActivity;
import com.taixingzhineng.android.ui.LoginActivity;
import com.taixingzhineng.android.ui.service.HttpService;
import com.taixingzhineng.android.ui.zyyh.UserInforActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/12/16.
 */

public class WDIndexActivity extends AppCompatActivity{
    Button exitButton;
    TextView user_detailed_information;
    TextView wd_index_username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wdindex);

        initView();
    }
    private void initView(){
        exitButton = findViewById(R.id.exit_system_button);
        user_detailed_information = findViewById(R.id.user_detailed_information);
        wd_index_username = findViewById(R.id.wd_index_username);

        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String name = preferences.getString("name", "");
        wd_index_username.setText(name);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("automaticLogin", false);
                editor.commit();

                String sessionid = preferences.getString("sessionid", "");
                String myPath = "a/logout";
                String myData = "&mobileLogin=true";
                HttpService(myPath,myData);

            }
        });
        //详细信息
        user_detailed_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WDIndexActivity.this, UserInformationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void HttpService(final String initPath,final String initData){
        HttpService.showDialog(WDIndexActivity.this,"退出中...");
        final MyHandler myhandler = new MyHandler(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = HttpService.ServiceByPost(initPath,initData);
                Bundle bundle = new Bundle();
                bundle.putString("result",str);
                Message msg = new Message();
                msg.setData(bundle);
                myhandler.sendMessage(msg);
            }
        }).start();
    }
    //弱引用，防止内存泄露
    private static class MyHandler extends Handler {
        private final WeakReference<WDIndexActivity> yjglActivity;

        public MyHandler(WDIndexActivity activity) {
            yjglActivity = new WeakReference<WDIndexActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (yjglActivity.get() == null) {
                return;
            }
            yjglActivity.get().updateUIThread(msg);
        }
    }
    //配合子线程更新UI线程
    private void updateUIThread(Message msg){
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        String result = bundle.getString("result");
        isSession(result);
    }
    private void isSession(String result){
        HttpService.closeDialog();//关闭加载框

        //跳转到IndexActivity
        Intent intent = new Intent(WDIndexActivity.this,LoginActivity.class);
        startActivity(intent);
    }
}
