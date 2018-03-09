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

public class UserOwnPowerActivity extends AppCompatActivity {
    private String UserOwnPower = "UserInfor_UserOwnPower";

    private importantUser importantUser;//父页面传递过来的重要用户信息

    LinearLayout user_own_power_return;//返回
    TextView user_own_power_username;//标题

    TextView user_own_power_load_capacity;//自备电源满足负荷容量
    TextView user_own_power_mode_charge;//重要电荷保障方式
    TextView user_own_power_generator_position;//发电车位置

    TextView user_diesel_generator_capacity;//容量
    TextView user_diesel_generator_level;//电压等级
    TextView user_diesel_generator_starting_condition;//启动条件
    TextView user_diesel_generator_load_supply;//所供负荷
    TextView user_diesel_generator_remarks;//备注
    TextView user_diesel_generator_picture;//图片
    TextView user_diesel_generator_modification_time;//修改时间

    TextView user_ups_eps_capacity;//容量
    TextView user_ups_eps_support_time;//支持时间
    TextView user_ups_eps_access_mode;//接入方式
    TextView user_ups_eps_installation_method;//安装方式
    TextView user_ups_eps_load_supply;//所供负荷
    TextView user_ups_eps_remarks;//备注
    TextView user_ups_eps_picture;//图片
    TextView user_ups_eps_modification_time;//修改时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_own_power);

        initView();//初始化视图
        initListener();//初始化监听
        initData();//初始化数据
    }
    //初始化视图
    private void initView(){
        user_own_power_return = findViewById(R.id.user_own_power_return);
        user_own_power_username = findViewById(R.id.user_own_power_username);

        user_own_power_load_capacity = findViewById(R.id.user_own_power_load_capacity);
        user_own_power_mode_charge = findViewById(R.id.user_own_power_mode_charge);
        user_own_power_generator_position = findViewById(R.id.user_own_power_generator_position);

        user_diesel_generator_capacity = findViewById(R.id.user_diesel_generator_capacity);
        user_diesel_generator_level = findViewById(R.id.user_diesel_generator_level);
        user_diesel_generator_starting_condition = findViewById(R.id.user_diesel_generator_starting_condition);
        user_diesel_generator_load_supply = findViewById(R.id.user_diesel_generator_load_supply);
        user_diesel_generator_remarks = findViewById(R.id.user_diesel_generator_remarks);
        user_diesel_generator_picture = findViewById(R.id.user_diesel_generator_picture);
        user_diesel_generator_modification_time = findViewById(R.id.user_diesel_generator_modification_time);

        user_ups_eps_capacity = findViewById(R.id.user_ups_eps_capacity);
        user_ups_eps_support_time = findViewById(R.id.user_ups_eps_support_time);
        user_ups_eps_access_mode = findViewById(R.id.user_ups_eps_access_mode);
        user_ups_eps_installation_method = findViewById(R.id.user_ups_eps_installation_method);
        user_ups_eps_load_supply = findViewById(R.id.user_ups_eps_load_supply);
        user_ups_eps_remarks = findViewById(R.id.user_ups_eps_remarks);
        user_ups_eps_picture = findViewById(R.id.user_ups_eps_picture);
        user_ups_eps_modification_time = findViewById(R.id.user_ups_eps_modification_time);

    }
    //初始化监听
    private void initListener(){
        //返回按钮监听
        user_own_power_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserOwnPowerActivity.this.finish();
            }
        });
    }
    //初始化数据
    private void initData(){
        //获取传递过来的数据
        importantUser = (importantUser)getIntent().getSerializableExtra(UserOwnPower);

        //标题
        user_own_power_username.setText(importantUser.getUserName() + " — 自备电源");

        //查询自备电源数据
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");

        String initPath1 = "a/mobile/ownPower/findOwnPowerByImpuserNo;JSESSIONID=" + sessionid;
        String initData1 = "&mobileLogin=true&impuserNo=" + importantUser.getUserNo();
        HttpService1(initPath1,initData1);

        //查询柴油发电机数据
        String initPath2 = "a/mobile/dieselGenerator/findDieselGeneratorByImpuserNo;JSESSIONID=" + sessionid;
        String initData2 = "&mobileLogin=true&impuserNo=" + importantUser.getUserNo();
        HttpService2(initPath2,initData2);

        //查询ups/eps数据
        String initPath3 = "a/mobile/upsEps/findUpsEpsByImpuserNo;JSESSIONID=" + sessionid;
        String initData3 = "&mobileLogin=true&impuserNo=" + importantUser.getUserNo();
        HttpService3(initPath3,initData3);
    }

    /**
     * 查询自备电源数据
     * @param initPath
     * @param initData
     */
    private void HttpService1(final String initPath,final String initData){
        //Log.d("UserOwnPowerActivity", "HttpService1: " + initData);
        HttpService.showDialog(UserOwnPowerActivity.this,"正在加载数据...");
        final MyHandler1 myHandler1 = new MyHandler1(UserOwnPowerActivity.this);
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
        private final WeakReference<UserOwnPowerActivity> relatedActivity;

        public MyHandler1(UserOwnPowerActivity activity) {
            relatedActivity = new WeakReference<UserOwnPowerActivity>(activity);
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
        //Log.d("UserOwnPowerActivity", "isSession1: " + result);
        HttpService.closeDialog();
        MobileOwnPower mobileOwnPower = new MobileOwnPower();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                mobileOwnPower.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                mobileOwnPower.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                mobileOwnPower.setLoadCap(jsonObject.has("loadCap")?jsonObject.getString("loadCap"):"");
                mobileOwnPower.setProWay(jsonObject.has("proWay")?jsonObject.getString("proWay"):"");
                mobileOwnPower.setCarPos(jsonObject.has("carPos")?jsonObject.getString("carPos"):"");
            }
        } catch (JSONException e) {
            Log.d("UserOwnPowerActivity", "json解析报错");
            e.printStackTrace();
        }

        user_own_power_load_capacity.setText(mobileOwnPower.getLoadCap());//自备电源满足负荷容量
        user_own_power_mode_charge.setText(mobileOwnPower.getProWay());//重要电荷保障方式
        user_own_power_generator_position.setText(mobileOwnPower.getCarPos());//发电车位置
    }

    /**
     * 查询柴油发电机数据
     * @param initPath
     * @param initData
     */
    private void HttpService2(final String initPath,final String initData){
        HttpService.showDialog(UserOwnPowerActivity.this,"正在加载数据...");
        final MyHandler2 myHandler2 = new MyHandler2(UserOwnPowerActivity.this);
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
        private final WeakReference<UserOwnPowerActivity> relatedActivity;

        public MyHandler2(UserOwnPowerActivity activity) {
            relatedActivity = new WeakReference<UserOwnPowerActivity>(activity);
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
        DieselGenerator dieselGenerator = new DieselGenerator();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                dieselGenerator.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                dieselGenerator.setRemarks(jsonObject.has("remarks")?jsonObject.getString("remarks"):"");
                dieselGenerator.setUpdateDate(jsonObject.has("updateDate")?jsonObject.getString("updateDate"):"");
                dieselGenerator.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                dieselGenerator.setCapacity(jsonObject.has("capacity")?jsonObject.getString("capacity"):"");
                dieselGenerator.setVoltageRank(jsonObject.has("voltageRank")?jsonObject.getString("voltageRank"):"");
                dieselGenerator.setStartCondition(jsonObject.has("startCondition")?jsonObject.getString("startCondition"):"");
                dieselGenerator.setOfferCharge(jsonObject.has("offerCharge")?jsonObject.getString("offerCharge"):"");
                dieselGenerator.setUrl(jsonObject.has("url")?jsonObject.getString("url"):"");

            }
        } catch (JSONException e) {
            Log.d("UserOwnPowerActivity", "json解析报错");
            e.printStackTrace();
        }

        user_diesel_generator_capacity.setText(dieselGenerator.getCapacity());//容量
        user_diesel_generator_level.setText(dieselGenerator.getVoltageRank());//电压等级
        user_diesel_generator_starting_condition.setText(dieselGenerator.getStartCondition());//启动条件
        user_diesel_generator_load_supply.setText(dieselGenerator.getOfferCharge());//所供负荷
        user_diesel_generator_remarks.setText(dieselGenerator.getRemarks());//备注
        user_diesel_generator_picture.setText(dieselGenerator.getUrl());//图片
        user_diesel_generator_modification_time.setText(dieselGenerator.getUpdateDate());//修改时间
    }
    /**
     * 查询UPS/EPS数据
     * @param initPath
     * @param initData
     */
    private void HttpService3(final String initPath,final String initData){
        HttpService.showDialog(UserOwnPowerActivity.this,"正在加载数据...");
        final MyHandler3 myHandler3 = new MyHandler3(UserOwnPowerActivity.this);
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
        private final WeakReference<UserOwnPowerActivity> relatedActivity;

        public MyHandler3(UserOwnPowerActivity activity) {
            relatedActivity = new WeakReference<UserOwnPowerActivity>(activity);
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
        //Log.d("UserOwnPowerActivity", "isSession3: " + result);
        HttpService.closeDialog();
        UpsEps upsEps = new UpsEps();
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                upsEps.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                upsEps.setRemarks(jsonObject.has("remarks")?jsonObject.getString("remarks"):"");
                upsEps.setUpdateDate(jsonObject.has("updateDate")?jsonObject.getString("updateDate"):"");
                upsEps.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                upsEps.setCapacity(jsonObject.has("capacity")?jsonObject.getString("capacity"):"");
                upsEps.setHour(jsonObject.has("hour")?jsonObject.getString("hour"):"");
                upsEps.setLinkWay(jsonObject.has("linkWay")?jsonObject.getString("linkWay"):"");
                upsEps.setOfferCharge(jsonObject.has("offerCharge")?jsonObject.getString("offerCharge"):"");
                upsEps.setImage(jsonObject.has("image")?jsonObject.getString("image"):"");
                upsEps.setInstallWay(jsonObject.has("installWay")?jsonObject.getString("installWay"):"");
            }
        } catch (JSONException e) {
            Log.d("UserOwnPowerActivity", "json解析报错");
            e.printStackTrace();
        }

        user_ups_eps_capacity.setText(upsEps.getCapacity());//容量
        user_ups_eps_support_time.setText(upsEps.getHour());//支持时间
        user_ups_eps_access_mode.setText(upsEps.getLinkWay());//接入方式
        user_ups_eps_installation_method.setText(upsEps.getInstallWay());//安装方式
        user_ups_eps_load_supply.setText(upsEps.getOfferCharge());//所供负荷
        user_ups_eps_remarks.setText(upsEps.getRemarks());//备注
        user_ups_eps_picture.setText(upsEps.getImage());//图片
        user_ups_eps_modification_time.setText(upsEps.getUpdateDate());//修改时间
    }
}
