package com.taixingzhineng.android.ui.zyyh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.model.importantUser;
import com.taixingzhineng.android.ui.zyyh.userInfor.PowerInformationActivity;
import com.taixingzhineng.android.ui.zyyh.userInfor.UserApplyMessageActivity;
import com.taixingzhineng.android.ui.zyyh.userInfor.UserDutyMessageActivity;
import com.taixingzhineng.android.ui.zyyh.userInfor.UserElectricianCertificateActivity;
import com.taixingzhineng.android.ui.zyyh.userInfor.UserEquipmentTroubleActivity;
import com.taixingzhineng.android.ui.zyyh.userInfor.UserEssentialInformationActivity;
import com.taixingzhineng.android.ui.zyyh.userInfor.UserOwnPowerActivity;
import com.taixingzhineng.android.ui.zyyh.userInfor.UserProtectProtectiveActivity;
import com.taixingzhineng.android.ui.zyyh.userInfor.UserSecuritySituationActivity;

/**
 * Created by Administrator on 2017/12/21.
 */

public class UserInforActivity extends AppCompatActivity {
    private String userInforName = "ZZYHIndex_UserInfor_Name";
    private String userPowerName = "userInfor_powerinfor";
    private String userEssentialInformation = "powerinfor_UserEssentialInformation";
    private String userApplyMessage = "powerinfor_UserApplyMessage";
    private String UserOwnPower = "UserInfor_UserOwnPower";
    private String UserSecuritySituation = "UserInfor_UserSecuritySituationActivity";
    private String UserDutyMessage = "UserInfor_UserDutyMessage";
    private String UserEquipmentTrouble = "UserInfor_UserEquipmentTrouble";
    private String UserELectricianCertificate = "UserInfor_UserELectricianCertificate";
    private String UserProtectProtective = "UserInfor_UserProtectProtective";

    importantUser importantUser;

    LinearLayout returnButton;//返回按钮
    TextView user_infor_username;//标题
    TextView user_information_essential_information;//基本信息
    TextView user_information_declaration_information;//申报信息
    TextView user_information_power_information;//电源情况
    TextView user_information_self_power;//自备电源
    TextView user_information_safety_responsibility;//安全责任落实情况
    TextView user_information_duty_on_duty;//值班情况
    TextView user_information_electrical__information;//电工证信息
    TextView user_information_equipment_hidden_danger;//设备隐患
    TextView user_information_power_protection;//保电管理
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfor);

        user_infor_username = findViewById(R.id.user_infor_username);
        returnButton = findViewById(R.id.user_infro_return);

        user_information_essential_information = findViewById(R.id.user_information_essential_information);
        user_information_declaration_information = findViewById(R.id.user_information_declaration_information);
        user_information_power_information = findViewById(R.id.user_information_power_information);
        user_information_self_power = findViewById(R.id.user_information_self_power);
        user_information_safety_responsibility = findViewById(R.id.user_information_safety_responsibility);
        user_information_duty_on_duty = findViewById(R.id.user_information_duty_on_duty);
        user_information_electrical__information = findViewById(R.id.user_information_electrical__information);
        user_information_equipment_hidden_danger = findViewById(R.id.user_information_equipment_hidden_danger);
        user_information_power_protection = findViewById(R.id.user_information_power_protection);

        //获取传递过来的数据
        importantUser = (importantUser)getIntent().getSerializableExtra(userInforName);
        //将重要用户实际名称显示在页面顶部
        user_infor_username.setText(importantUser.getUserName());

        //实现返回上一个页面功能
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInforActivity.this.finish();
            }
        });

        //基本信息点击监听
        user_information_essential_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInforActivity.this,UserEssentialInformationActivity.class);
                intent.putExtra(userEssentialInformation,importantUser);
                startActivity(intent);
            }
        });

        //申报信息点击监听
        user_information_declaration_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInforActivity.this, UserApplyMessageActivity.class);
                intent.putExtra(userApplyMessage,importantUser);
                startActivity(intent);
            }
        });

        //电源情况点击监听
        user_information_power_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInforActivity.this,PowerInformationActivity.class);
                intent.putExtra(userPowerName,importantUser);
                startActivity(intent);
            }
        });

        //自备电源点击监听
        user_information_self_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInforActivity.this,UserOwnPowerActivity.class);
                intent.putExtra(UserOwnPower,importantUser);
                startActivity(intent);
            }
        });

        //安全责任落实情况点击监听
        user_information_safety_responsibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInforActivity.this,UserSecuritySituationActivity.class);
                intent.putExtra(UserSecuritySituation,importantUser);
                startActivity(intent);
            }
        });

        //值班情况点击监听
        user_information_duty_on_duty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInforActivity.this,UserDutyMessageActivity.class);
                intent.putExtra(UserDutyMessage,importantUser);
                startActivity(intent);
            }
        });
        //电工证信息点击监听
        user_information_electrical__information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInforActivity.this,UserElectricianCertificateActivity.class);
                intent.putExtra(UserELectricianCertificate,importantUser);
                startActivity(intent);
            }
        });
        //设备隐患点击监听
        user_information_equipment_hidden_danger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInforActivity.this,UserEquipmentTroubleActivity.class);
                intent.putExtra(UserEquipmentTrouble,importantUser);
                startActivity(intent);
            }
        });
        //保电管理点击监听
        user_information_power_protection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserInforActivity.this,UserProtectProtectiveActivity.class);
                intent.putExtra(UserProtectProtective,importantUser);
                startActivity(intent);
            }
        });
    }
}
