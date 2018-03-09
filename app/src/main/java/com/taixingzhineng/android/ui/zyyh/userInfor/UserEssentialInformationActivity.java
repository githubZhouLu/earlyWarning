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
import com.taixingzhineng.android.ui.model.UserPowerCondition;
import com.taixingzhineng.android.ui.model.importantUser;
import com.taixingzhineng.android.ui.service.HttpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/12/21.
 */

public class UserEssentialInformationActivity extends AppCompatActivity {
    private String userEssentialInformation = "powerinfor_UserEssentialInformation";
    importantUser importantUser;

    private MyHandler myhandler = new MyHandler(this);
    private String initPath = "";
    private String initData = "";
    JSONArray jsonArray;

    LinearLayout user_return;
    TextView user_username;

    TextView user_power_supply_unit;//供电单位
    TextView user_account_number;//户号
    TextView user_user_real_name;//用户实际名称
    TextView user_electricity_actual_address;//用电实际地址
    TextView user_industry_classification;//行业分类
    TextView user_user_voltage_level;//电压等级
    TextView user_power_supply_number;//供电电源数
    TextView user_date_of_connection;//接电日期
    TextView user_contract_capacity;//合同容量
    TextView user_user_update_time;//修改时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_essential_information);

        user_return = findViewById(R.id.user_return);
        user_username = findViewById(R.id.user_username);

        user_power_supply_unit = findViewById(R.id.user_power_supply_unit);
        user_account_number = findViewById(R.id.user_account_number);
        user_user_real_name = findViewById(R.id.user_user_real_name);
        user_electricity_actual_address = findViewById(R.id.user_electricity_actual_address);
        user_industry_classification = findViewById(R.id.user_industry_classification);
        user_user_voltage_level = findViewById(R.id.user_user_voltage_level);
        user_power_supply_number = findViewById(R.id.user_power_supply_number);
        user_date_of_connection = findViewById(R.id.user_date_of_connection);
        user_contract_capacity = findViewById(R.id.user_contract_capacity);
        user_user_update_time = findViewById(R.id.user_user_update_time);

        //返回按钮监听
        user_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEssentialInformationActivity.this.finish();
            }
        });

        //获取传递过来的数据
        importantUser = (importantUser)getIntent().getSerializableExtra(userEssentialInformation);

        //将重要用户实际名称显示在页面顶部
        user_username.setText(importantUser.getUserName() + " — 基本信息");

        //访问web后台路径,参数
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");
        String myPath = "a/mobile/ImportantUser/findImportantUserById;JSESSIONID=" + sessionid;
        String myData = "&mobileLogin=true&impuserNo="+importantUser.getUserNo();
        //获取电源情况数据
        HttpService(myPath,myData);
    }
    /**
     *
     * @param myPath 路径
     * @param myData 参数
     */
    private void HttpService(String myPath,String myData){
        HttpService.showDialog(UserEssentialInformationActivity.this,"正在加载数据...");
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
        private final WeakReference<UserEssentialInformationActivity> userPowerActivity;

        public MyHandler(UserEssentialInformationActivity activity) {
            userPowerActivity = new WeakReference<UserEssentialInformationActivity>(activity);
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
        importantUser importantUser = new importantUser();
        try {
            jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++)    {//jsonArray.length()
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                importantUser.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                importantUser.setUserNo(jsonObject.has("userNo")?jsonObject.getString("userNo"):"");
                importantUser.setDoorNo(jsonObject.has("doorNo")?jsonObject.getString("doorNo"):"");
                importantUser.setUserName(jsonObject.has("userName")?jsonObject.getString("userName"):"");
                importantUser.setElectricAddress(jsonObject.has("electricAddress")?jsonObject.getString("electricAddress"):"");
                importantUser.setBusinessType(jsonObject.has("businessType")?jsonObject.getString("businessType"):"");
                importantUser.setImportRank(jsonObject.has("importRank")?jsonObject.getString("importRank"):"");
                importantUser.setPowerCount(jsonObject.has("powerCount")?jsonObject.getString("powerCount"):"");
                importantUser.setVoltageRank(jsonObject.has("voltageRank")?jsonObject.getString("voltageRank"):"");
                importantUser.setJdDate(jsonObject.has("jdDate")?jsonObject.getString("jdDate"):"");
                importantUser.setUpdateDate(jsonObject.has("updateDate")?jsonObject.getString("updateDate"):"");
                importantUser.setSupLeader(jsonObject.has("supLeader")?jsonObject.getString("supLeader"):"");
                importantUser.setTel(jsonObject.has("tel")?jsonObject.getString("tel"):"");
                importantUser.setTotalCapacity(jsonObject.has("totalCapacity")?jsonObject.getString("totalCapacity"):"");
                importantUser.setHtrl(jsonObject.has("htrl")?jsonObject.getString("htrl"):"");
                importantUser.setPowerCompany(jsonObject.has("powerCompany")?jsonObject.getString("powerCompany"):"");
                importantUser.setCmsName(jsonObject.has("cmsName")?jsonObject.getString("cmsName"):"");
            }
        } catch (JSONException e) {
            Log.d("YJGLIndexActivity", "json解析报错");
            e.printStackTrace();
        }

        user_power_supply_unit.setText(importantUser.getPowerCompany());//供电单位
        user_account_number.setText(importantUser.getDoorNo());//户号
        user_user_real_name.setText(importantUser.getUserName());//用户实际名称
        user_electricity_actual_address.setText(importantUser.getElectricAddress());//用电实际地址
        user_industry_classification.setText(importantUser.getBusinessType());//行业分类
        user_user_voltage_level.setText(importantUser.getVoltageRank());//电压等级
        user_power_supply_number.setText(importantUser.getPowerCount());//供电电源数
        user_date_of_connection.setText(importantUser.getJdDate());//接电日期
        user_contract_capacity.setText(importantUser.getHtrl());//合同容量
        user_user_update_time.setText(importantUser.getUpdateDate());//修改时间

        /*ListView listView = this.findViewById(R.id.yjgl_index_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String earlyWarningJson = jsonArray.getJSONObject(position).toString();
                    //position 点击的Item位置，从0开始算
                    Intent intent = new Intent(YJGLIndexActivity.this,YJXXActivity.class);
                    intent.putExtra("YJGLIndexActivity_YJXXActivity",earlyWarningJson);//earlyWarnings.get(position).getId()
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }
}
