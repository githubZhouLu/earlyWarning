package com.taixingzhineng.android.ui.zyyh.userInfor.powerInformation;

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

public class UserPowerActivity extends AppCompatActivity {
    private String userPowerPower = "powerinfor_userpower";
    importantUser importantUser;

    private MyHandler myhandler = new MyHandler(this);
    private String initPath = "";
    private String initData = "";
    JSONArray jsonArray;

    LinearLayout user_power_return;
    TextView user_power_username;

    TextView user_power_power;
    TextView user_power_line_name;
    TextView user_power_line_way;
    TextView user_power_lock_way;
    TextView user_power_pline_way;
    TextView user_power_voltage_way;
    TextView user_power_power_url;
    TextView user_power_update_date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_power);

        user_power_return = findViewById(R.id.user_power_return);
        user_power_username = findViewById(R.id.user_power_username);

        user_power_power = findViewById(R.id.user_power_power);
        user_power_line_name = findViewById(R.id.user_power_line_name);
        user_power_line_way = findViewById(R.id.user_power_line_way);
        user_power_lock_way = findViewById(R.id.user_power_lock_way);
        user_power_pline_way = findViewById(R.id.user_power_pline_way);
        user_power_voltage_way = findViewById(R.id.user_power_voltage_way);
        user_power_power_url = findViewById(R.id.user_power_power_url);
        user_power_update_date = findViewById(R.id.user_power_update_date);

        //返回按钮监听
        user_power_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPowerActivity.this.finish();
            }
        });

        //获取传递过来的数据
        importantUser = (importantUser)getIntent().getSerializableExtra(userPowerPower);

        //将重要用户实际名称显示在页面顶部
        user_power_username.setText(importantUser.getUserName() + " — 电源情况 — 电源情况");

        //访问web后台路径,参数
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");
        String myPath = "a/mobile/UserPowerPower/findUserPowerPower;JSESSIONID=" + sessionid;
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
        HttpService.showDialog(UserPowerActivity.this,"正在加载数据...");
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
        private final WeakReference<UserPowerActivity> userPowerActivity;

        public MyHandler(UserPowerActivity activity) {
            userPowerActivity = new WeakReference<UserPowerActivity>(activity);
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
        UserPowerCondition userPowerCondition = new UserPowerCondition();
        try {
            jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++)    {//jsonArray.length()
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                userPowerCondition = new UserPowerCondition();
                userPowerCondition.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                userPowerCondition.setUpdatetime(jsonObject.has("updateDate")?jsonObject.getString("updateDate"):"");
                userPowerCondition.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                userPowerCondition.setElectricNo(jsonObject.has("electricNo")?jsonObject.getString("electricNo"):"");
                userPowerCondition.setLineName(jsonObject.has("lineName")?jsonObject.getString("lineName"):"");
                userPowerCondition.setLineWay(jsonObject.has("lineWay")?jsonObject.getString("lineWay"):"");
                userPowerCondition.setLockWay(jsonObject.has("lockWay")?jsonObject.getString("lockWay"):"");
                userPowerCondition.setPlineWay(jsonObject.has("plineWay")?jsonObject.getString("plineWay"):"");
                userPowerCondition.setVoltageWay(jsonObject.has("voltageWay")?jsonObject.getString("voltageWay"):"");
                userPowerCondition.setPowerUrl(jsonObject.has("powerUrl")?jsonObject.getString("powerUrl"):"");
            }
        } catch (JSONException e) {
            Log.d("YJGLIndexActivity", "json解析报错");
            e.printStackTrace();
        }

        user_power_power.setText(userPowerCondition.getElectricNo());
        user_power_line_name.setText(userPowerCondition.getLineName());
        user_power_line_way.setText(userPowerCondition.getLineWay());
        user_power_lock_way.setText(userPowerCondition.getLockWay());
        user_power_pline_way.setText(userPowerCondition.getPlineWay());
        user_power_voltage_way.setText(userPowerCondition.getVoltageWay());
        user_power_power_url.setText(userPowerCondition.getPowerUrl());
        user_power_update_date.setText(userPowerCondition.getUpdatetime());

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
