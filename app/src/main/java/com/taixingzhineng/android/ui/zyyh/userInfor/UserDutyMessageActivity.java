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
import com.taixingzhineng.android.ui.model.DieselGenerator;
import com.taixingzhineng.android.ui.model.DutyMessage;
import com.taixingzhineng.android.ui.model.MobileOwnPower;
import com.taixingzhineng.android.ui.model.UpsEps;
import com.taixingzhineng.android.ui.model.importantUser;
import com.taixingzhineng.android.ui.service.HttpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;


/**
 * Created by Administrator on 2017/12/21.
 */

public class UserDutyMessageActivity extends AppCompatActivity {
    private String UserDutyMessage = "UserInfor_UserDutyMessage";

    private importantUser importantUser;//父页面传递过来的重要用户信息

    LinearLayout user_duty_return;//返回
    TextView user_duty_username;//标题

    TextView user_duty_electrical_Supervisor;//电器主管
    TextView user_duty_electrical_supervisor_phone;//电器主管电话
    TextView user_duty_electrician;//电工负责人
    TextView user_duty_electrician_phone;//电工负责人电话
    TextView user_duty_user_manager;//用户负责人
    TextView user_duty_user_manager_phone;//用户负责人电话
    TextView user_duty_way_duty;//值班方式
    TextView user_duty_number;//值班人数
    TextView user_duty_certificates_number_configure;//持证人数是否满足配置要求
    TextView user_duty_certificates_total_number;//持证总人数

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_duty_message);

        initView();//初始化视图
        initListener();//初始化监听
        initData();//初始化数据
    }
    //初始化视图
    private void initView(){
        user_duty_return = findViewById(R.id.user_duty_return);
        user_duty_username = findViewById(R.id.user_duty_username);

        user_duty_electrical_Supervisor = findViewById(R.id.user_duty_electrical_Supervisor);
        user_duty_electrical_supervisor_phone = findViewById(R.id.user_duty_electrical_supervisor_phone);
        user_duty_electrician = findViewById(R.id.user_duty_electrician);
        user_duty_electrician_phone = findViewById(R.id.user_duty_electrician_phone);
        user_duty_user_manager = findViewById(R.id.user_duty_user_manager);
        user_duty_user_manager_phone = findViewById(R.id.user_duty_user_manager_phone);
        user_duty_way_duty = findViewById(R.id.user_duty_way_duty);
        user_duty_number = findViewById(R.id.user_duty_number);
        user_duty_certificates_number_configure = findViewById(R.id.user_duty_certificates_number_configure);
        user_duty_certificates_total_number = findViewById(R.id.user_duty_certificates_total_number);

    }
    //初始化监听
    private void initListener(){
        //返回按钮监听
        user_duty_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDutyMessageActivity.this.finish();
            }
        });
    }
    //初始化数据
    private void initData(){
        //获取传递过来的数据
        importantUser = (importantUser)getIntent().getSerializableExtra(UserDutyMessage);

        //标题
        user_duty_username.setText(importantUser.getUserName() + " — 值班情况");

        //查询值班情况数据
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");

        String initPath1 = "a/mobile/dutyMessage/findDutyMessageByNo;JSESSIONID=" + sessionid;
        String initData1 = "&mobileLogin=true&impuserNo=" + importantUser.getUserNo();
        HttpService1(initPath1,initData1);
    }

    /**
     * 查询值班情况数据
     * @param initPath
     * @param initData
     */
    private void HttpService1(final String initPath,final String initData){
        //Log.d("UserOwnPowerActivity", "HttpService1: " + initData);
        HttpService.showDialog(UserDutyMessageActivity.this,"正在加载数据...");
        final MyHandler1 myHandler1 = new MyHandler1(UserDutyMessageActivity.this);
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
        private final WeakReference<UserDutyMessageActivity> relatedActivity;

        public MyHandler1(UserDutyMessageActivity activity) {
            relatedActivity = new WeakReference<UserDutyMessageActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
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
        Log.d("UserDutyMessageActivity", "isSession1: " + result);
        HttpService.closeDialog();
        DutyMessage dutyMessage = new DutyMessage();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                dutyMessage.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                dutyMessage.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                dutyMessage.setDqmanager(jsonObject.has("dqmanager")?jsonObject.getString("dqmanager"):"");
                dutyMessage.setDgperson(jsonObject.has("dgperson")?jsonObject.getString("dgperson"):"");
                dutyMessage.setYhperson(jsonObject.has("yhperson")?jsonObject.getString("yhperson"):"");
                dutyMessage.setDutyWay(jsonObject.has("dutyWay")?jsonObject.getString("dutyWay"):"");
                dutyMessage.setPersonYb(jsonObject.has("personYb")?jsonObject.getString("personYb"):"");
                dutyMessage.setPersonYb(jsonObject.has("personYb")?jsonObject.getString("personYb"):"");
                dutyMessage.setPeoples(jsonObject.has("peoples")?jsonObject.getString("peoples"):"");
                dutyMessage.setManagerTel(jsonObject.has("managerTel")?jsonObject.getString("managerTel"):"");
                dutyMessage.setDutyTel(jsonObject.has("dutyTel")?jsonObject.getString("dutyTel"):"");
                dutyMessage.setUserTel(jsonObject.has("userTel")?jsonObject.getString("userTel"):"");
                dutyMessage.setDutyCount(jsonObject.has("dutyCount")?jsonObject.getString("dutyCount"):"");
                dutyMessage.setDutyDate(jsonObject.has("dutyDate")?jsonObject.getString("dutyDate"):"");
            }
        } catch (JSONException e) {
            Log.d("UserOwnPowerActivity", "json解析报错");
            e.printStackTrace();
        }

        user_duty_electrical_Supervisor.setText(dutyMessage.getDqmanager());//电器主管
        user_duty_electrical_supervisor_phone.setText(dutyMessage.getManagerTel());//电器主管电话
        user_duty_electrician.setText(dutyMessage.getDgperson());//电工负责人
        user_duty_electrician_phone.setText(dutyMessage.getDutyTel());//电工负责人电话
        user_duty_user_manager.setText(dutyMessage.getYhperson());//用户负责人
        user_duty_user_manager_phone.setText(dutyMessage.getUserTel());//用户负责人电话
        user_duty_way_duty.setText(dutyMessage.getDutyWay());//值班方式
        user_duty_number.setText(dutyMessage.getDutyCount());//值班人数
        user_duty_certificates_number_configure.setText(dutyMessage.getPersonYb());//持证人数是否满足配置要求
        user_duty_certificates_total_number.setText(dutyMessage.getPeoples());//持证总人数
    }
}
