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
import com.taixingzhineng.android.ui.model.ElectricianCertificate;
import com.taixingzhineng.android.ui.model.ProtectProtective;
import com.taixingzhineng.android.ui.model.importantUser;
import com.taixingzhineng.android.ui.service.HttpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;


/**
 * Created by Administrator on 2017/12/21.
 */

public class UserProtectProtectiveActivity extends AppCompatActivity {
    private String UserProtectProtective = "UserInfor_UserProtectProtective";

    private importantUser importantUser;//父页面传递过来的重要用户信息

    LinearLayout user_protect_protective_return;//返回
    TextView user_protect_protective_username;//标题

    TextView user_protect_protective_grade;//重要保电任务等级
    TextView user_protect_protective_state;//保电状态
    TextView user_protect_protective_inspector;//检查人员
    TextView user_protect_protective_check_time;//检查时间
    TextView user_protect_protective_start_time;//开始时间
    TextView user_protect_protective_end_time;//结束时间
    TextView user_protect_protective_long;//保电时长
    TextView user_protect_protective_programming_time;//保电方案编制时间
    TextView user_protect_protective_programming_personnel;//方案编制人员
    TextView user_protect_protective_protective_agreement;//签收保电协议
    TextView user_protect_protective_finish_time;//完成专项检查时间
    TextView user_protect_protective_manoeuvre_time;//演习时间
    TextView user_protect_protective_manoeuvre_department;//演习参与部门
    TextView user_protect_protective_programme_examination_results;//方案内容检查结果
    TextView user_protect_protective_exercise_content;//演习内容
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_protect_protective);

        initView();//初始化视图
        initListener();//初始化监听
        initData();//初始化数据
    }
    //初始化视图
    private void initView(){
        user_protect_protective_return = findViewById(R.id.user_protect_protective_return);
        user_protect_protective_username = findViewById(R.id.user_protect_protective_username);

        user_protect_protective_grade = findViewById(R.id.user_protect_protective_grade);
        user_protect_protective_state = findViewById(R.id.user_protect_protective_state);
        user_protect_protective_inspector = findViewById(R.id.user_protect_protective_inspector);
        user_protect_protective_check_time = findViewById(R.id.user_protect_protective_check_time);
        user_protect_protective_start_time = findViewById(R.id.user_protect_protective_start_time);
        user_protect_protective_end_time = findViewById(R.id.user_protect_protective_end_time);
        user_protect_protective_long = findViewById(R.id.user_protect_protective_long);
        user_protect_protective_programming_time = findViewById(R.id.user_protect_protective_programming_time);
        user_protect_protective_programming_personnel = findViewById(R.id.user_protect_protective_programming_personnel);
        user_protect_protective_protective_agreement = findViewById(R.id.user_protect_protective_protective_agreement);
        user_protect_protective_finish_time = findViewById(R.id.user_protect_protective_finish_time);
        user_protect_protective_manoeuvre_time = findViewById(R.id.user_protect_protective_manoeuvre_time);
        user_protect_protective_manoeuvre_department = findViewById(R.id.user_protect_protective_manoeuvre_department);
        user_protect_protective_programme_examination_results = findViewById(R.id.user_protect_protective_programme_examination_results);
        user_protect_protective_exercise_content = findViewById(R.id.user_protect_protective_exercise_content);


    }
    //初始化监听
    private void initListener(){
        //返回按钮监听
        user_protect_protective_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProtectProtectiveActivity.this.finish();
            }
        });
    }
    //初始化数据
    private void initData(){
        //获取传递过来的数据
        importantUser = (importantUser)getIntent().getSerializableExtra(UserProtectProtective);

        //标题
        user_protect_protective_username.setText(importantUser.getUserName() + " — 保电管理");

        //查询保电管理
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");

        String initPath1 = "a/mobile/protectProtective/findProtectProtectiveByNo;JSESSIONID=" + sessionid;
        String initData1 = "&mobileLogin=true&impuserNo=" + importantUser.getUserNo();
        HttpService1(initPath1,initData1);
    }

    /**
     * 查询保电管理
     * @param initPath
     * @param initData
     */
    private void HttpService1(final String initPath,final String initData){
        //Log.d("UserSecuritySituation", "HttpService1: " + initData);
        HttpService.showDialog(UserProtectProtectiveActivity.this,"正在加载数据...");
        final MyHandler1 myHandler1 = new MyHandler1(UserProtectProtectiveActivity.this);
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
        private final WeakReference<UserProtectProtectiveActivity> relatedActivity;

        public MyHandler1(UserProtectProtectiveActivity activity) {
            relatedActivity = new WeakReference<UserProtectProtectiveActivity>(activity);
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
        //Log.d("ProtectProtective", "isSession1: " + result);
        HttpService.closeDialog();
        ProtectProtective protectProtective = new ProtectProtective();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                protectProtective.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                protectProtective.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                protectProtective.setProtectiveGrade(jsonObject.has("protectiveGrade")?jsonObject.getString("protectiveGrade"):"");
                protectProtective.setProtectiveState(jsonObject.has("protectiveState")?jsonObject.getString("protectiveState"):"");
                protectProtective.setInspector(jsonObject.has("inspector")?jsonObject.getString("inspector"):"");
                protectProtective.setCheckTime(jsonObject.has("checkTime")?jsonObject.getString("checkTime"):"");
                protectProtective.setStartTime(jsonObject.has("startTime")?jsonObject.getString("startTime"):"");
                protectProtective.setEndTime(jsonObject.has("endTime")?jsonObject.getString("endTime"):"");
                protectProtective.setProtectiveLong(jsonObject.has("protectiveLong")?jsonObject.getString("protectiveLong"):"");
                protectProtective.setProgrammingTime(jsonObject.has("programmingTime")?jsonObject.getString("programmingTime"):"");
                protectProtective.setProgrammingPersonnel(jsonObject.has("programmingPersonnel")?jsonObject.getString("programmingPersonnel"):"");
                protectProtective.setProtectiveAgreement(jsonObject.has("protectiveAgreement")?jsonObject.getString("protectiveAgreement"):"");
                protectProtective.setFinishTime(jsonObject.has("finishTime")?jsonObject.getString("finishTime"):"");
                protectProtective.setManoeuvreTime(jsonObject.has("manoeuvreTime")?jsonObject.getString("manoeuvreTime"):"");
                protectProtective.setManoeuvreDepartment(jsonObject.has("manoeuvreDepartment")?jsonObject.getString("manoeuvreDepartment"):"");
                protectProtective.setExaminationResults(jsonObject.has("examinationResults")?jsonObject.getString("examinationResults"):"");
                protectProtective.setExerciseContent(jsonObject.has("exerciseContent")?jsonObject.getString("exerciseContent"):"");
            }
        } catch (JSONException e) {
            Log.d("UserOwnPowerActivity", "json解析报错");
            e.printStackTrace();
        }

        user_protect_protective_grade.setText(protectProtective.getProtectiveGrade());//重要保电任务等级
        user_protect_protective_state.setText(protectProtective.getProtectiveState());//保电状态
        user_protect_protective_inspector.setText(protectProtective.getInspector());//检查人员
        user_protect_protective_check_time.setText(protectProtective.getCheckTime());//检查时间
        user_protect_protective_start_time.setText(protectProtective.getStartTime());//开始时间
        user_protect_protective_end_time.setText(protectProtective.getEndTime());//结束时间
        user_protect_protective_long.setText(protectProtective.getProtectiveLong());//保电时长
        user_protect_protective_programming_time.setText(protectProtective.getProgrammingTime());//保电方案编制时间
        user_protect_protective_programming_personnel.setText(protectProtective.getProgrammingPersonnel());//方案编制人员
        user_protect_protective_protective_agreement.setText(protectProtective.getProtectiveAgreement());//签收保电协议
        user_protect_protective_finish_time.setText(protectProtective.getFinishTime());//完成专项检查时间
        user_protect_protective_manoeuvre_time.setText(protectProtective.getManoeuvreTime());//演习时间
        user_protect_protective_manoeuvre_department.setText(protectProtective.getManoeuvreDepartment());//演习参与部门
        user_protect_protective_programme_examination_results.setText(protectProtective.getExaminationResults());//方案内容检查结果
        user_protect_protective_exercise_content.setText(protectProtective.getExerciseContent());//演习内容
    }
}
