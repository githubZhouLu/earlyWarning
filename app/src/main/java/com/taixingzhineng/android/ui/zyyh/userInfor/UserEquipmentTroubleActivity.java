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
import com.taixingzhineng.android.ui.model.DutyMessage;
import com.taixingzhineng.android.ui.model.EquipmentTrouble;
import com.taixingzhineng.android.ui.model.importantUser;
import com.taixingzhineng.android.ui.service.HttpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;


/**
 * Created by Administrator on 2017/12/21.
 */

public class UserEquipmentTroubleActivity extends AppCompatActivity {
    private String UserEquipmentTrouble = "UserInfor_UserEquipmentTrouble";

    private importantUser importantUser;//父页面传递过来的重要用户信息

    LinearLayout user_equipment_trouble_return;//返回
    TextView user_equipment_trouble_username;//标题

    TextView user_equipment_trouble_examination_results;//检查结果
    TextView user_equipment_trouble_inspector;//检查人员
    TextView user_equipment_trouble_actual_inspection_time;//实际检查时间
    TextView user_equipment_trouble_hidden_danger_type;//安全隐患类型
    TextView user_equipment_trouble_rectification_proposal;//整改建议
    TextView user_equipment_trouble_planned_remedial_time;//计划整改时间
    TextView user_equipment_trouble_actual_rectify_time;//实际整改时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_equipment_trouble);

        initView();//初始化视图
        initListener();//初始化监听
        initData();//初始化数据
    }
    //初始化视图
    private void initView(){
        user_equipment_trouble_return = findViewById(R.id.user_equipment_trouble_return);
        user_equipment_trouble_username = findViewById(R.id.user_equipment_trouble_username);

        user_equipment_trouble_examination_results = findViewById(R.id.user_equipment_trouble_examination_results);
        user_equipment_trouble_inspector = findViewById(R.id.user_equipment_trouble_inspector);
        user_equipment_trouble_actual_inspection_time = findViewById(R.id.user_equipment_trouble_actual_inspection_time);
        user_equipment_trouble_hidden_danger_type = findViewById(R.id.user_equipment_trouble_hidden_danger_type);
        user_equipment_trouble_rectification_proposal = findViewById(R.id.user_equipment_trouble_rectification_proposal);
        user_equipment_trouble_planned_remedial_time = findViewById(R.id.user_equipment_trouble_planned_remedial_time);
        user_equipment_trouble_actual_rectify_time = findViewById(R.id.user_equipment_trouble_actual_rectify_time);
    }
    //初始化监听
    private void initListener(){
        //返回按钮监听
        user_equipment_trouble_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEquipmentTroubleActivity.this.finish();
            }
        });
    }
    //初始化数据
    private void initData(){
        //获取传递过来的数据
        importantUser = (importantUser)getIntent().getSerializableExtra(UserEquipmentTrouble);

        //标题
        user_equipment_trouble_username.setText(importantUser.getUserName() + " — 设备隐患");

        //查询值班情况数据
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");

        String initPath1 = "a/mobile/equipmentTrouble/findEquipmentTroubleByNo;JSESSIONID=" + sessionid;
        String initData1 = "&mobileLogin=true&impuserNo=" + importantUser.getUserNo();
        HttpService1(initPath1,initData1);
    }

    /**
     * 查询设备隐患数据
     * @param initPath
     * @param initData
     */
    private void HttpService1(final String initPath,final String initData){
        //Log.d("UserOwnPowerActivity", "HttpService1: " + initData);
        HttpService.showDialog(UserEquipmentTroubleActivity.this,"正在加载数据...");
        final MyHandler1 myHandler1 = new MyHandler1(UserEquipmentTroubleActivity.this);
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
        private final WeakReference<UserEquipmentTroubleActivity> relatedActivity;

        public MyHandler1(UserEquipmentTroubleActivity activity) {
            relatedActivity = new WeakReference<UserEquipmentTroubleActivity>(activity);
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
        Log.d("UserEquipmentTrouble", "isSession1: " + result);
        HttpService.closeDialog();
        EquipmentTrouble equipmentTrouble = new EquipmentTrouble();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                equipmentTrouble.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                equipmentTrouble.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                equipmentTrouble.setResult(jsonObject.has("result")?jsonObject.getString("result"):"");
                equipmentTrouble.setChecker(jsonObject.has("checker")?jsonObject.getString("checker"):"");
                equipmentTrouble.setCheckTime(jsonObject.has("checkTime")?jsonObject.getString("checkTime"):"");
                equipmentTrouble.setTroubleType(jsonObject.has("troubleType")?jsonObject.getString("troubleType"):"");
                equipmentTrouble.setSuggestion(jsonObject.has("suggestion")?jsonObject.getString("suggestion"):"");
                equipmentTrouble.setPlanTime(jsonObject.has("planTime")?jsonObject.getString("planTime"):"");
                equipmentTrouble.setRealTime(jsonObject.has("realTime")?jsonObject.getString("realTime"):"");
            }
        } catch (JSONException e) {
            Log.d("UserEquipmentTrouble", "json解析报错");
            e.printStackTrace();
        }

        user_equipment_trouble_examination_results.setText(equipmentTrouble.getResult());//检查结果
        user_equipment_trouble_inspector.setText(equipmentTrouble.getChecker());//检查人员
        user_equipment_trouble_actual_inspection_time.setText(equipmentTrouble.getCheckTime());//实际检查时间
        user_equipment_trouble_hidden_danger_type.setText(equipmentTrouble.getTroubleType());//安全隐患类型
        user_equipment_trouble_rectification_proposal.setText(equipmentTrouble.getSuggestion());//整改建议
        user_equipment_trouble_planned_remedial_time.setText(equipmentTrouble.getPlanTime());//计划整改时间
        user_equipment_trouble_actual_rectify_time.setText(equipmentTrouble.getRealTime());//实际整改时间
    }
}
