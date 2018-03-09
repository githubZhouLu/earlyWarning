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
import com.taixingzhineng.android.ui.model.UserApplyMessage;
import com.taixingzhineng.android.ui.model.UserLoadCharge;
import com.taixingzhineng.android.ui.model.importantUser;
import com.taixingzhineng.android.ui.service.HttpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/12/21.
 */

public class UserApplyMessageActivity extends AppCompatActivity {
    private String userApplyMessage = "powerinfor_UserApplyMessage";
    importantUser importantUser;

    private MyHandler myhandler = new MyHandler(this);
    private String initPath = "";
    private String initData = "";
    JSONArray jsonArray;

    private MyHandler1 myhandler1 = new MyHandler1(this);
    private String initPath1 = "";
    private String initData1 = "";
    JSONArray jsonArray1;

    LinearLayout user_apply_message_return;
    TextView user_apply_message_username;

    TextView user_apply_message_confirm_year;//核定年份
    TextView user_apply_message_ipt_rank;//重要等级
    TextView user_apply_message_confirm_state;//重要用户确认情况
    TextView user_apply_message_give_at;//申报表已发放
    TextView user_apply_message_submit_at;//申报表是否提交
    TextView user_apply_message_apply_formid;//申报表
    TextView user_apply_message_meet_state;//供电电源是否满足配置要求
    TextView user_apply_message_yn_down;//是否地下站
    TextView user_apply_message_yn_generatrix;//是否专有应急母线
    TextView user_apply_message_low_voltage;//低电压释放装置情况
    TextView user_apply_message_security_protocal;//安全协议
    TextView user_apply_message_protect_protocal;//安全保障协议
    TextView user_apply_message_superior;//上级行政主管部门

    TextView user_apply_message_load_type;//重要负荷信息
    TextView user_apply_message_supply_eletric;//连续供电需求
    TextView user_apply_message_capacity;//容量
    TextView user_apply_message_remarks;//备注
    TextView user_apply_message_update_time;//修改时间


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_apply_message);

        user_apply_message_return = findViewById(R.id.user_apply_message_return);
        user_apply_message_username = findViewById(R.id.user_apply_message_username);

        user_apply_message_confirm_year = findViewById(R.id.user_apply_message_confirm_year);//核定年份
        user_apply_message_ipt_rank = findViewById(R.id.user_apply_message_ipt_rank);//重要等级
        user_apply_message_confirm_state = findViewById(R.id.user_apply_message_confirm_state);//重要用户确认情况
        user_apply_message_give_at = findViewById(R.id.user_apply_message_give_at);//申报表已发放
        user_apply_message_submit_at = findViewById(R.id.user_apply_message_submit_at);//申报表是否提交
        user_apply_message_apply_formid = findViewById(R.id.user_apply_message_apply_formid);//申报表
        user_apply_message_meet_state = findViewById(R.id.user_apply_message_meet_state);//供电电源是否满足配置要求
        user_apply_message_yn_down = findViewById(R.id.user_apply_message_yn_down);//是否地下站
        user_apply_message_yn_generatrix = findViewById(R.id.user_apply_message_yn_generatrix);//是否专有应急母线
        user_apply_message_low_voltage = findViewById(R.id.user_apply_message_low_voltage);//低电压释放装置情况
        user_apply_message_security_protocal = findViewById(R.id.user_apply_message_security_protocal);//安全协议
        user_apply_message_protect_protocal = findViewById(R.id.user_apply_message_protect_protocal);//安全保障协议
        user_apply_message_superior = findViewById(R.id.user_apply_message_superior);//上级行政主管部门

        user_apply_message_load_type = findViewById(R.id.user_apply_message_load_type);//重要负荷信息
        user_apply_message_supply_eletric = findViewById(R.id.user_apply_message_supply_eletric);//连续供电需求
        user_apply_message_capacity = findViewById(R.id.user_apply_message_capacity);//容量
        user_apply_message_remarks = findViewById(R.id.user_apply_message_remarks);//备注
        user_apply_message_update_time = findViewById(R.id.user_apply_message_update_time);//修改时间

        //返回按钮监听
        user_apply_message_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApplyMessageActivity.this.finish();
            }
        });

        //获取传递过来的数据
        importantUser = (importantUser)getIntent().getSerializableExtra(userApplyMessage);

        //将重要用户实际名称显示在页面顶部
        user_apply_message_username.setText(importantUser.getUserName() + " — 申报信息");

        //访问web后台路径,参数
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");
        String myPath = "a/mobile/userApplyMessage/findApplyMessageById;JSESSIONID=" + sessionid;
        String myData = "&mobileLogin=true&impuserNo="+importantUser.getUserNo();
        //获取电源情况数据
        HttpService(myPath,myData);

        //访问web后台路径,参数
        String myPath1 = "a/mobile/userLoadCharge/findLoadChargeByImpuserNo;JSESSIONID=" + sessionid;
        String myData1 = "&mobileLogin=true&impuserNo="+importantUser.getUserNo();
        //获取电源情况数据
        HttpService1(myPath1,myData1);
    }
    /**
     *
     * @param myPath 路径
     * @param myData 参数
     */
    private void HttpService(String myPath,String myData){
        HttpService.showDialog(UserApplyMessageActivity.this,"正在加载数据...");
        initPath = myPath;
        initData = myData;
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
        private final WeakReference<UserApplyMessageActivity> userPowerActivity;

        public MyHandler(UserApplyMessageActivity activity) {
            userPowerActivity = new WeakReference<UserApplyMessageActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (userPowerActivity.get() == null) {
                return;
            }
            userPowerActivity.get().updateUIThread(msg);
        }
    }
    //配合子线程更新UI线程
    private void updateUIThread(Message msg){
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        String result = bundle.getString("result");
        Log.d("zhoulu", "updateUIThread: "+result);
        isSession(result);
    }
    private void isSession(String result) {
        HttpService.closeDialog();//关闭加载框
        UserApplyMessage userApplyMessage = new UserApplyMessage();
        try {
            jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++)    {//jsonArray.length()
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                userApplyMessage.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                userApplyMessage.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                userApplyMessage.setConfirmYear(jsonObject.has("confirmYear")?jsonObject.getString("confirmYear"):"");
                userApplyMessage.setIptRank(jsonObject.has("iptRank")?jsonObject.getString("iptRank"):"");
                userApplyMessage.setConfirmState(jsonObject.has("confirmState")?jsonObject.getString("confirmState"):"");
                userApplyMessage.setGiveAt(jsonObject.has("giveAt")?jsonObject.getString("giveAt"):"");
                userApplyMessage.setSubmitAt(jsonObject.has("submitAt")?jsonObject.getString("submitAt"):"");
                userApplyMessage.setApplyFormid(jsonObject.has("applyFormid")?jsonObject.getString("applyFormid"):"");
                userApplyMessage.setMeetState(jsonObject.has("meetState")?jsonObject.getString("meetState"):"");
                userApplyMessage.setYnDown(jsonObject.has("ynDown")?jsonObject.getString("ynDown"):"");
                userApplyMessage.setYnGeneratrix(jsonObject.has("ynGeneratrix")?jsonObject.getString("ynGeneratrix"):"");
                userApplyMessage.setLowVoltage(jsonObject.has("lowVoltage")?jsonObject.getString("lowVoltage"):"");
                userApplyMessage.setSecurityProtocal(jsonObject.has("securityProtocal")?jsonObject.getString("securityProtocal"):"");
                userApplyMessage.setProtectProtocal(jsonObject.has("protectProtocal")?jsonObject.getString("protectProtocal"):"");
                userApplyMessage.setSuperior(jsonObject.has("superior")?jsonObject.getString("superior"):"");
            }
        } catch (JSONException e) {
            Log.d("YJGLIndexActivity", "json解析报错");
            e.printStackTrace();
        }

        user_apply_message_confirm_year.setText(userApplyMessage.getConfirmYear());//核定年份
        user_apply_message_ipt_rank.setText(userApplyMessage.getIptRank());//重要等级
        user_apply_message_confirm_state.setText(userApplyMessage.getConfirmState());//重要用户确认情况
        user_apply_message_give_at.setText(userApplyMessage.getGiveAt());//申报表已发放
        user_apply_message_submit_at.setText(userApplyMessage.getSubmitAt());//申报表是否提交
        user_apply_message_apply_formid.setText(userApplyMessage.getApplyFormid());//申报表
        user_apply_message_meet_state.setText(userApplyMessage.getMeetState());//供电电源是否满足配置要求
        user_apply_message_yn_down.setText(userApplyMessage.getYnDown());//是否地下站
        user_apply_message_yn_generatrix.setText(userApplyMessage.getYnGeneratrix());//是否专有应急母线
        user_apply_message_low_voltage.setText(userApplyMessage.getLowVoltage());//低电压释放装置情况
        user_apply_message_security_protocal.setText(userApplyMessage.getSecurityProtocal());//安全协议
        user_apply_message_protect_protocal.setText(userApplyMessage.getProtectProtocal());//安全保障协议
        user_apply_message_superior.setText(userApplyMessage.getSuperior());//上级行政主管部门

    }


    /**
     *
     * @param myPath 路径
     * @param myData 参数
     */
    private void HttpService1(String myPath,String myData){
        HttpService.showDialog(UserApplyMessageActivity.this,"正在加载数据...");
        initPath1 = myPath;
        initData1 = myData;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = HttpService.ServiceByPost(initPath1,initData1);
                Bundle bundle = new Bundle();
                bundle.putString("result",str);
                Message msg = new Message();
                msg.setData(bundle);
                myhandler1.sendMessage(msg);
            }
        }).start();
    }
    //弱引用，防止内存泄露
    private static class MyHandler1 extends Handler {
        private final WeakReference<UserApplyMessageActivity> userPowerActivity;

        public MyHandler1(UserApplyMessageActivity activity) {
            userPowerActivity = new WeakReference<UserApplyMessageActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (userPowerActivity.get() == null) {
                return;
            }
            userPowerActivity.get().updateUIThread1(msg);
        }
    }
    //配合子线程更新UI线程
    private void updateUIThread1(Message msg){
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        String result = bundle.getString("result");
        Log.d("zhoulu", "updateUIThread1: "+result);
        isSession1(result);
    }
    private void isSession1(String result) {
        HttpService.closeDialog();//关闭加载框
        UserLoadCharge userLoadCharge = new UserLoadCharge();
        try {
            jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++)    {//jsonArray.length()
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                userLoadCharge.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                userLoadCharge.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                userLoadCharge.setRemarks(jsonObject.has("remarks")?jsonObject.getString("remarks"):"");
                userLoadCharge.setUpdateDate(jsonObject.has("updateDate")?jsonObject.getString("updateDate"):"");
                userLoadCharge.setLoadType(jsonObject.has("loadType")?jsonObject.getString("loadType"):"");
                userLoadCharge.setSupplyEletric(jsonObject.has("supplyEletric")?jsonObject.getString("supplyEletric"):"");
                userLoadCharge.setCapacity(jsonObject.has("capacity")?jsonObject.getString("capacity"):"");
            }
        } catch (JSONException e) {
            Log.d("YJGLIndexActivity", "json解析报错");
            e.printStackTrace();
        }

        user_apply_message_load_type.setText(userLoadCharge.getLoadType());//重要负荷信息
        user_apply_message_supply_eletric.setText(userLoadCharge.getSupplyEletric());//连续供电需求
        user_apply_message_capacity.setText(userLoadCharge.getCapacity());//容量
        user_apply_message_remarks.setText(userLoadCharge.getRemarks());//备注
        user_apply_message_update_time.setText(userLoadCharge.getUpdateDate());//修改时间
    }
}
