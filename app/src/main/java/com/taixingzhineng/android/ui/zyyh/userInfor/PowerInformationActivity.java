package com.taixingzhineng.android.ui.zyyh.userInfor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.model.importantUser;
import com.taixingzhineng.android.ui.zyyh.userInfor.powerInformation.UserPowerActivity;
import com.taixingzhineng.android.ui.zyyh.userInfor.powerInformation.UserPowerCapacitanceActivity;
import com.taixingzhineng.android.ui.zyyh.userInfor.powerInformation.UserPowerEquipmentActivity;
import com.taixingzhineng.android.ui.zyyh.userInfor.powerInformation.UserPowerTransformerActivity;

/**
 * Created by Administrator on 2017/12/21.
 */

public class PowerInformationActivity extends AppCompatActivity {
    private String userPowerName = "userInfor_powerinfor";
    private String userPowerPower = "powerinfor_userpower";
    private String userPowerTransformer = "PowerInformation_userPowerTransformer";
    private String userPowerEquipment = "PowerInformation_userPowerEquipment";
    private String userPowerCapacitance = "PowerInformation_userPowerCapacitance";

    importantUser importantUser;

    LinearLayout user_information_power_return;//返回按钮
    TextView user_information_power_username;//标题

    TextView user_information_power_power;//电源情况
    TextView user_information_power_transformer;//变压器
    TextView user_information_power_high_low_equipment;//高低压设备
    TextView user_information_power_capacitance;//电容器
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_information);

        user_information_power_return = findViewById(R.id.user_information_power_return);
        user_information_power_username = findViewById(R.id.user_information_power_username);

        user_information_power_power = findViewById(R.id.user_information_power_power);
        user_information_power_transformer = findViewById(R.id.user_information_power_transformer);
        user_information_power_high_low_equipment = findViewById(R.id.user_information_power_high_low_equipment);
        user_information_power_capacitance = findViewById(R.id.user_information_power_capacitance);

        //获取传递过来的数据
        importantUser = (importantUser)getIntent().getSerializableExtra(userPowerName);

        //实现返回上一个页面功能
        user_information_power_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PowerInformationActivity.this.finish();
            }
        });

        //将重要用户实际名称显示在页面顶部
        user_information_power_username.setText(importantUser.getUserName() + " — 电源情况");

        //电源情况点击监听
        user_information_power_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PowerInformationActivity.this,UserPowerActivity.class);
                intent.putExtra(userPowerPower,importantUser);
                startActivity(intent);
            }
        });

        //变压器点击监听
        user_information_power_transformer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PowerInformationActivity.this,UserPowerTransformerActivity.class);
                intent.putExtra(userPowerTransformer,importantUser);
                startActivity(intent);
            }
        });

        //高低压设备点击监听
        user_information_power_high_low_equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PowerInformationActivity.this,UserPowerEquipmentActivity.class);
                intent.putExtra(userPowerEquipment,importantUser);
                startActivity(intent);
            }
        });

        //电容器点击监听
        user_information_power_capacitance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PowerInformationActivity.this,UserPowerCapacitanceActivity.class);
                intent.putExtra(userPowerCapacitance,importantUser);
                startActivity(intent);
            }
        });
    }
}
