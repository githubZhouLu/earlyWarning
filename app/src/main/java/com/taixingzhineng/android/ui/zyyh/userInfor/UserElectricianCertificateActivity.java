package com.taixingzhineng.android.ui.zyyh.userInfor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.model.ContinPlan;
import com.taixingzhineng.android.ui.model.ElectricianCertificate;
import com.taixingzhineng.android.ui.model.EquipmentCondition;
import com.taixingzhineng.android.ui.model.SecuritySituation;
import com.taixingzhineng.android.ui.model.importantUser;
import com.taixingzhineng.android.ui.service.HttpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;


/**
 * Created by Administrator on 2017/12/21.
 */

public class UserElectricianCertificateActivity extends AppCompatActivity {
    private String UserELectricianCertificate = "UserInfor_UserELectricianCertificate";

    private importantUser importantUser;//父页面传递过来的重要用户信息

    LinearLayout user_electrician_certificate_return;//返回
    TextView user_electrician_certificate_username;//标题

    TextView user_electrician_certificate_electrician_name;//电工姓名
    TextView user_electrician_certificate_electrician_number;//电工证编号
    TextView user_electrician_certificate_validity_date;//有效期
    TextView user_electrician_certificate_registration_unit;//注册单位
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_electrician_certificate);

        initView();//初始化视图
        initListener();//初始化监听
        initData();//初始化数据
    }
    //初始化视图
    private void initView(){
        user_electrician_certificate_return = findViewById(R.id.user_electrician_certificate_return);
        user_electrician_certificate_username = findViewById(R.id.user_electrician_certificate_username);

        user_electrician_certificate_electrician_name = findViewById(R.id.user_electrician_certificate_electrician_name);
        user_electrician_certificate_electrician_number = findViewById(R.id.user_electrician_certificate_electrician_number);
        user_electrician_certificate_validity_date = findViewById(R.id.user_electrician_certificate_validity_date);
        user_electrician_certificate_registration_unit = findViewById(R.id.user_electrician_certificate_registration_unit);
    }
    //初始化监听
    private void initListener(){
        //返回按钮监听
        user_electrician_certificate_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserElectricianCertificateActivity.this.finish();
            }
        });
    }
    //初始化数据
    private void initData(){
        //获取传递过来的数据
        importantUser = (importantUser)getIntent().getSerializableExtra(UserELectricianCertificate);

        //标题
        user_electrician_certificate_username.setText(importantUser.getUserName() + " — 电工证信息");

        //查询电工证信息
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");

        String initPath1 = "a/mobile/electricianCertificate/findElectricianCertificateByNo;JSESSIONID=" + sessionid;
        String initData1 = "&mobileLogin=true&impuserNo=" + importantUser.getUserNo();
        HttpService1(initPath1,initData1);
    }

    /**
     * 查询电工证信息
     * @param initPath
     * @param initData
     */
    private void HttpService1(final String initPath,final String initData){
        //Log.d("UserSecuritySituation", "HttpService1: " + initData);
        HttpService.showDialog(UserElectricianCertificateActivity.this,"正在加载数据...");
        final MyHandler1 myHandler1 = new MyHandler1(UserElectricianCertificateActivity.this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = HttpService.ServiceByPost(initPath,initData);
                Bundle bundle = new Bundle();
                bundle.putString("result",str);
                Message msg = new Message();
                msg.setData(bundle);
                myHandler1.sendMessage(msg);
            }
        }).start();
    }
    //弱引用，防止内存泄露
    private static class MyHandler1 extends Handler {
        private final WeakReference<UserElectricianCertificateActivity> relatedActivity;

        public MyHandler1(UserElectricianCertificateActivity activity) {
            relatedActivity = new WeakReference<UserElectricianCertificateActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (relatedActivity.get() == null) {
                return;
            }
            relatedActivity.get().updateUIThread1(msg);
        }
    }
    //配合子线程更新UI线程
    private void updateUIThread1(Message msg){
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        String result = bundle.getString("result");
        isSession1(result);
    }
    private void isSession1(String result) {
        Log.d("ElectricianCertificate", "isSession1: " + result);
        HttpService.closeDialog();
        ElectricianCertificate electricianCertificate = new ElectricianCertificate();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                electricianCertificate.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                electricianCertificate.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                electricianCertificate.setElectricianName(jsonObject.has("electricianName")?jsonObject.getString("electricianName"):"");
                electricianCertificate.setElectricianNumber(jsonObject.has("electricianNumber")?jsonObject.getString("electricianNumber"):"");
                electricianCertificate.setValidityDate(jsonObject.has("validityDate")?jsonObject.getString("validityDate"):"");
                electricianCertificate.setRegistrationUnit(jsonObject.has("registrationUnit")?jsonObject.getString("registrationUnit"):"");
            }
        } catch (JSONException e) {
            Log.d("UserOwnPowerActivity", "json解析报错");
            e.printStackTrace();
        }

        user_electrician_certificate_electrician_name.setText(electricianCertificate.getElectricianName());//电工姓名
        user_electrician_certificate_electrician_number.setText(electricianCertificate.getElectricianNumber());//电工证编号
        user_electrician_certificate_validity_date.setText(electricianCertificate.getValidityDate());//有效期
        user_electrician_certificate_registration_unit.setText(electricianCertificate.getRegistrationUnit());//注册单位
    }
}
