package com.taixingzhineng.android.ui.wd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taixingzhineng.android.R;

/**
 * Created by Administrator on 2017/12/21.
 */

public class UserInformationActivity extends AppCompatActivity {
    LinearLayout account_return;//返回按钮
    TextView user_edit;//编辑按钮
    TextView user_cancel;//取消按钮
    TextView user_save;//保存按钮
    TextView user_name;//姓名
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        initView();
        initData();
    }
    private void initView(){
        account_return = findViewById(R.id.account_return);
        user_edit = findViewById(R.id.user_edit);
        user_cancel = findViewById(R.id.user_cancel);
        user_save = findViewById(R.id.user_save);
        user_name = findViewById(R.id.user_name);
    }
    private void initData(){
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);

        user_name.setText(preferences.getString("name", ""));

        account_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInformationActivity.this.finish();
            }
        });

        user_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInformationActivity.this.finish();
            }
        });
        user_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInformationActivity.this.finish();
            }
        });
    }
}
