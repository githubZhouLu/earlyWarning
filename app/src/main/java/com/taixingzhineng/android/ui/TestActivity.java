package com.taixingzhineng.android.ui;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.taixingzhineng.android.R;


/**
 * Created by Administrator on 2018/1/18.
 */

public class TestActivity extends AppCompatActivity {
    private Button send;
    private Button cancle;

    NotificationManager manager;
    int notification_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        send = (Button) findViewById(R.id.send);
        cancle = (Button) findViewById(R.id.cancle);

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notification.Builder builder = new Notification.Builder(TestActivity.this);
                builder.setSmallIcon(R.drawable.p001);
                builder.setTicker("World");
                builder.setWhen(System.currentTimeMillis());
                builder.setContentTitle("标题栏");
                builder.setContentText("这个是显示出来的内容部分");

                Intent intent = new Intent(TestActivity.this, LoginActivity.class);
                PendingIntent ma = PendingIntent.getActivity(TestActivity.this,0,intent,0);
                builder.setContentIntent(ma);//设置点击过后跳转的activity

                /*builder.setDefaults(Notification.DEFAULT_SOUND);//设置声音
                builder.setDefaults(Notification.DEFAULT_LIGHTS);//设置指示灯
                builder.setDefaults(Notification.DEFAULT_VIBRATE);//设置震动*/
                builder.setDefaults(Notification.DEFAULT_ALL);//设置全部

                Notification notification = builder.build();//4.1以上用.build();
                notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击通知的时候cancel掉
                manager.notify(notification_id,notification);
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.cancel(notification_id);
            }
        });
    }
}
