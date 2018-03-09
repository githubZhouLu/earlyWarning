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
import com.taixingzhineng.android.ui.model.HLEquipment;
import com.taixingzhineng.android.ui.model.UserPowerCapacitance;
import com.taixingzhineng.android.ui.model.importantUser;
import com.taixingzhineng.android.ui.service.HttpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/12/21.
 */

public class UserPowerCapacitanceActivity extends AppCompatActivity {
    private String userPowerCapacitance = "PowerInformation_userPowerCapacitance";
    importantUser importantUser;

    private MyHandler myhandler = new MyHandler(this);
    private String initPath = "";
    private String initData = "";
    JSONArray jsonArray;

    LinearLayout user_power_capacitance_return;
    TextView user_power_capacitance_username;

    TextView user_power_capacitance_model;//型号
    TextView user_power_capacitance_single_capacity;//单台容量
    TextView user_power_capacitance_voltage_level;//电压等级
    TextView user_power_capacitance_total_capacity;//总容量
    TextView user_power_capacitance_number;//台数
    TextView user_power_capacitance_operating_condition;//运行工况
    TextView user_power_capacitance_picture;//图片
    TextView user_power_capacitance_update_time;//修改时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_power_capacitance);

        user_power_capacitance_return = findViewById(R.id.user_power_capacitance_return);
        user_power_capacitance_username = findViewById(R.id.user_power_capacitance_username);

        user_power_capacitance_model = findViewById(R.id.user_power_capacitance_model);
        user_power_capacitance_single_capacity = findViewById(R.id.user_power_capacitance_single_capacity);
        user_power_capacitance_voltage_level = findViewById(R.id.user_power_capacitance_voltage_level);
        user_power_capacitance_total_capacity = findViewById(R.id.user_power_capacitance_total_capacity);
        user_power_capacitance_number = findViewById(R.id.user_power_capacitance_number);
        user_power_capacitance_operating_condition = findViewById(R.id.user_power_capacitance_operating_condition);
        user_power_capacitance_picture = findViewById(R.id.user_power_capacitance_picture);
        user_power_capacitance_update_time = findViewById(R.id.user_power_capacitance_update_time);

        //返回按钮监听
        user_power_capacitance_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPowerCapacitanceActivity.this.finish();
            }
        });

        //获取传递过来的数据
        importantUser = (importantUser)getIntent().getSerializableExtra(userPowerCapacitance);

        //将重要用户实际名称显示在页面顶部
        user_power_capacitance_username.setText(importantUser.getUserName() + " — 电源情况 — 电容器");

        //访问web后台路径,参数
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");
        String myPath = "a/mobile/UserPowerCapacitance/findUserPowerCapacitance;JSESSIONID=" + sessionid;
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
        HttpService.showDialog(UserPowerCapacitanceActivity.this,"正在加载数据...");
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
        private final WeakReference<UserPowerCapacitanceActivity> userPowerActivity;

        public MyHandler(UserPowerCapacitanceActivity activity) {
            userPowerActivity = new WeakReference<UserPowerCapacitanceActivity>(activity);
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
        UserPowerCapacitance userPowerCapacitance = new UserPowerCapacitance();
        try {
            jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++)    {//jsonArray.length()
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                userPowerCapacitance.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                userPowerCapacitance.setUpdateTime(jsonObject.has("updateDate")?jsonObject.getString("updateDate"):"");
                userPowerCapacitance.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                userPowerCapacitance.setCapacitorType(jsonObject.has("capacitorType")?jsonObject.getString("capacitorType"):"");
                userPowerCapacitance.setSingleCap(jsonObject.has("singleCap")?jsonObject.getString("singleCap"):"");
                userPowerCapacitance.setVoltageRank(jsonObject.has("voltageRank")?jsonObject.getString("voltageRank"):"");
                userPowerCapacitance.setTotalCap(jsonObject.has("totalCap")?jsonObject.getString("totalCap"):"");
                userPowerCapacitance.setCount(jsonObject.has("count")?jsonObject.getString("count"):"");
                userPowerCapacitance.setOperatConditions(jsonObject.has("operatConditions")?jsonObject.getString("operatConditions"):"");
                userPowerCapacitance.setImage(jsonObject.has("image")?jsonObject.getString("image"):"");
            }
        } catch (JSONException e) {
            Log.d("YJGLIndexActivity", "json解析报错");
            e.printStackTrace();
        }

        user_power_capacitance_model.setText(userPowerCapacitance.getCapacitorType());//型号
        user_power_capacitance_single_capacity.setText(userPowerCapacitance.getSingleCap());//单台容量
        user_power_capacitance_voltage_level.setText(userPowerCapacitance.getVoltageRank());//电压等级
        user_power_capacitance_total_capacity.setText(userPowerCapacitance.getTotalCap());//总容量
        user_power_capacitance_number.setText(userPowerCapacitance.getCount());//台数
        user_power_capacitance_operating_condition.setText(userPowerCapacitance.getOperatConditions());//运行工况
        user_power_capacitance_picture.setText(userPowerCapacitance.getImage());//图片
        user_power_capacitance_update_time.setText(userPowerCapacitance.getUpdateTime());//修改时间

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
