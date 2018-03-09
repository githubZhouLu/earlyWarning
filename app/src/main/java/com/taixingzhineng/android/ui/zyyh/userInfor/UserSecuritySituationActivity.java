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
import com.taixingzhineng.android.ui.model.DieselGenerator;
import com.taixingzhineng.android.ui.model.EquipmentCondition;
import com.taixingzhineng.android.ui.model.MobileOwnPower;
import com.taixingzhineng.android.ui.model.SecuritySituation;
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

public class UserSecuritySituationActivity extends AppCompatActivity {
    private String UserSecuritySituation = "UserInfor_UserSecuritySituationActivity";

    private importantUser importantUser;//父页面传递过来的重要用户信息

    LinearLayout user_security_situation_return;//返回
    TextView user_security_situation_username;//标题

    TextView user_security_situation_contract_information;//供用电合同信息
    TextView user_security_situation_contract_update_time;//合同修改时间
    TextView user_security_situation_scheduling_protocol;//调度协议书信息
    TextView user_security_situation_agreement_update_time;//协议书修改时间

    TextView user_security_situation_equipment_test;//电器设备按周期开展试验
    TextView user_security_situation_tool;//安全工器具齐全
    TextView user_security_situation_tool_test;//安全工器具按周期开展试验
    TextView user_security_situation_relay_protection_check;//继电保护定值按周期校验

    TextView user_security_situation_organization_emergency_plan;//编制应急预案，且具有针对性
    TextView user_security_situation_emergency_rehearsal;//定期开展应急演练
    TextView user_security_situation_old_drill_time;//上次开展应急演练的时间
    TextView user_security_situation_plan_develop_time;//近期计划开展时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_security_situation);

        initView();//初始化视图
        initListener();//初始化监听
        initData();//初始化数据
    }
    //初始化视图
    private void initView(){
        user_security_situation_return = findViewById(R.id.user_security_situation_return);
        user_security_situation_username = findViewById(R.id.user_security_situation_username);

        user_security_situation_contract_information = findViewById(R.id.user_security_situation_contract_information);
        user_security_situation_contract_update_time = findViewById(R.id.user_security_situation_contract_update_time);
        user_security_situation_scheduling_protocol = findViewById(R.id.user_security_situation_scheduling_protocol);
        user_security_situation_agreement_update_time = findViewById(R.id.user_security_situation_agreement_update_time);

        user_security_situation_equipment_test = findViewById(R.id.user_security_situation_equipment_test);
        user_security_situation_tool = findViewById(R.id.user_security_situation_tool);
        user_security_situation_tool_test = findViewById(R.id.user_security_situation_tool_test);
        user_security_situation_relay_protection_check = findViewById(R.id.user_security_situation_relay_protection_check);

        user_security_situation_organization_emergency_plan = findViewById(R.id.user_security_situation_organization_emergency_plan);
        user_security_situation_emergency_rehearsal = findViewById(R.id.user_security_situation_emergency_rehearsal);
        user_security_situation_old_drill_time = findViewById(R.id.user_security_situation_old_drill_time);
        user_security_situation_plan_develop_time = findViewById(R.id.user_security_situation_plan_develop_time);

    }
    //初始化监听
    private void initListener(){
        //返回按钮监听
        user_security_situation_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSecuritySituationActivity.this.finish();
            }
        });
    }
    //初始化数据
    private void initData(){
        //获取传递过来的数据
        importantUser = (importantUser)getIntent().getSerializableExtra(UserSecuritySituation);

        //标题
        user_security_situation_username.setText(importantUser.getUserName() + " — 安全责任落实情况");

        //查询安全责任落实情况
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");

        String initPath1 = "a/mobile/securitySituation/findSecuritySituationByImpuserNo;JSESSIONID=" + sessionid;
        String initData1 = "&mobileLogin=true&impuserNo=" + importantUser.getUserNo();
        HttpService1(initPath1,initData1);

        //查询安全工器具、电器设备试验等情况
        String initPath2 = "a/mobile/equipmentCondition/findEquipmentConditionByImpuserNo;JSESSIONID=" + sessionid;
        String initData2 = "&mobileLogin=true&impuserNo=" + importantUser.getUserNo();
        HttpService2(initPath2,initData2);

        //停电事故应急预案、应急演练情况
        String initPath3 = "a/mobile/continPlan/findContinPlanByImpuserNo;JSESSIONID=" + sessionid;
        String initData3 = "&mobileLogin=true&impuserNo=" + importantUser.getUserNo();
        HttpService3(initPath3,initData3);
    }

    /**
     * 查询安全责任落实情况
     * @param initPath
     * @param initData
     */
    private void HttpService1(final String initPath,final String initData){
        //Log.d("UserSecuritySituation", "HttpService1: " + initData);
        HttpService.showDialog(UserSecuritySituationActivity.this,"正在加载数据...");
        final MyHandler1 myHandler1 = new MyHandler1(UserSecuritySituationActivity.this);
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
        private final WeakReference<UserSecuritySituationActivity> relatedActivity;

        public MyHandler1(UserSecuritySituationActivity activity) {
            relatedActivity = new WeakReference<UserSecuritySituationActivity>(activity);
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
        Log.d("UserSecuritySituation", "isSession1: " + result);
        HttpService.closeDialog();
        SecuritySituation securitySituation = new SecuritySituation();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                securitySituation.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                securitySituation.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                securitySituation.setPowerContract(jsonObject.has("powerContract")?jsonObject.getString("powerContract"):"");
                securitySituation.setContractUpdateTime(jsonObject.has("contractUpdateTime")?jsonObject.getString("contractUpdateTime"):"");
                securitySituation.setDispatchAgreement(jsonObject.has("dispatchAgreement")?jsonObject.getString("dispatchAgreement"):"");
                securitySituation.setAgreementUpdateTime(jsonObject.has("agreementUpdateTime")?jsonObject.getString("agreementUpdateTime"):"");
            }
        } catch (JSONException e) {
            Log.d("UserOwnPowerActivity", "json解析报错");
            e.printStackTrace();
        }

        user_security_situation_contract_information.setText(securitySituation.getPowerContract());//供用电合同信息
        user_security_situation_contract_update_time.setText(securitySituation.getContractUpdateTime());//合同修改时间
        user_security_situation_scheduling_protocol.setText(securitySituation.getDispatchAgreement());//调度协议书信息
        user_security_situation_agreement_update_time.setText(securitySituation.getAgreementUpdateTime());//协议书修改时间
    }

    /**
     * 查询安全工器具、电器设备试验等情况
     * @param initPath
     * @param initData
     */
    private void HttpService2(final String initPath,final String initData){
        HttpService.showDialog(UserSecuritySituationActivity.this,"正在加载数据...");
        final MyHandler2 myHandler2 = new MyHandler2(UserSecuritySituationActivity.this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = HttpService.ServiceByPost(initPath,initData);
                Bundle bundle = new Bundle();
                bundle.putString("result",str);
                Message msg = new Message();
                msg.setData(bundle);
                myHandler2.sendMessage(msg);
            }
        }).start();
    }
    //弱引用，防止内存泄露
    private static class MyHandler2 extends Handler {
        private final WeakReference<UserSecuritySituationActivity> relatedActivity;

        public MyHandler2(UserSecuritySituationActivity activity) {
            relatedActivity = new WeakReference<UserSecuritySituationActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (relatedActivity.get() == null) {
                return;
            }
            relatedActivity.get().updateUIThread2(msg);
        }
    }
    //配合子线程更新UI线程
    private void updateUIThread2(Message msg){
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        String result = bundle.getString("result");
        isSession2(result);
    }
    private void isSession2(String result) {
        //Log.d("UserOwnPowerActivity", "isSession2: " + result);
        HttpService.closeDialog();
        EquipmentCondition equipmentCondition = new EquipmentCondition();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                equipmentCondition.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                equipmentCondition.setEquipmentfile(jsonObject.has("equipmentfile")?jsonObject.getString("equipmentfile"):"");
                equipmentCondition.setToolsfile(jsonObject.has("toolsfile")?jsonObject.getString("toolsfile"):"");
                equipmentCondition.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                equipmentCondition.setAqgqjfile(jsonObject.has("aqgqjfile")?jsonObject.getString("aqgqjfile"):"");
                equipmentCondition.setJdbhfile(jsonObject.has("jdbhfile")?jsonObject.getString("jdbhfile"):"");
            }
        } catch (JSONException e) {
            Log.d("UserOwnPowerActivity", "json解析报错");
            e.printStackTrace();
        }

        user_security_situation_equipment_test.setText(equipmentCondition.getEquipmentfile());//电器设备按周期开展试验
        user_security_situation_tool.setText(equipmentCondition.getToolsfile());//安全工器具齐全
        user_security_situation_tool_test.setText(equipmentCondition.getAqgqjfile());//安全工器具按周期开展试验
        user_security_situation_relay_protection_check.setText(equipmentCondition.getJdbhfile());//继电保护定值按周期校验
    }
    /**
     * 停电事故应急预案、应急演练情况
     * @param initPath
     * @param initData
     */
    private void HttpService3(final String initPath,final String initData){
        HttpService.showDialog(UserSecuritySituationActivity.this,"正在加载数据...");
        final MyHandler3 myHandler3 = new MyHandler3(UserSecuritySituationActivity.this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = HttpService.ServiceByPost(initPath,initData);
                Bundle bundle = new Bundle();
                bundle.putString("result",str);
                Message msg = new Message();
                msg.setData(bundle);
                myHandler3.sendMessage(msg);
            }
        }).start();
    }
    //弱引用，防止内存泄露
    private static class MyHandler3 extends Handler {
        private final WeakReference<UserSecuritySituationActivity> relatedActivity;

        public MyHandler3(UserSecuritySituationActivity activity) {
            relatedActivity = new WeakReference<UserSecuritySituationActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (relatedActivity.get() == null) {
                return;
            }
            relatedActivity.get().updateUIThread3(msg);
        }
    }
    //配合子线程更新UI线程
    private void updateUIThread3(Message msg){
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        String result = bundle.getString("result");
        isSession3(result);
    }
    private void isSession3(String result) {
        Log.d("UserOwnPowerActivity", "isSession3: " + result);
        HttpService.closeDialog();
        ContinPlan continPlan = new ContinPlan();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                continPlan.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                continPlan.setPlanmessages(jsonObject.has("planmessages")?jsonObject.getString("planmessages"):"");
                continPlan.setEmergencyRehearsal(jsonObject.has("emergencyRehearsal")?jsonObject.getString("emergencyRehearsal"):"");
                continPlan.setLastTime(jsonObject.has("lastTime")?jsonObject.getString("lastTime"):"");
                continPlan.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                continPlan.setPlanTime(jsonObject.has("planTime")?jsonObject.getString("planTime"):"");
            }
        } catch (JSONException e) {
            Log.d("UserOwnPowerActivity", "isSession3：json解析报错");
            e.printStackTrace();
        }

        user_security_situation_organization_emergency_plan.setText(continPlan.getPlanmessages());//编制应急预案，且具有针对性
        user_security_situation_emergency_rehearsal.setText(continPlan.getEmergencyRehearsal());//定期开展应急演练
        user_security_situation_old_drill_time.setText(continPlan.getLastTime());//上次开展应急演练的时间
        user_security_situation_plan_develop_time.setText(continPlan.getPlanTime());//近期计划开展时间
    }
}
