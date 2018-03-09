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
import com.taixingzhineng.android.ui.model.UserPowerTransformer;
import com.taixingzhineng.android.ui.model.importantUser;
import com.taixingzhineng.android.ui.service.HttpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/12/21.
 */

public class UserPowerEquipmentActivity extends AppCompatActivity {
    private String userPowerEquipment = "PowerInformation_userPowerEquipment";
    importantUser importantUser;

    private MyHandler myhandler = new MyHandler(this);
    private String initPath = "";
    private String initData = "";
    JSONArray jsonArray;

    LinearLayout user_power_equipment_return;
    TextView user_power_equipment_username;

    TextView user_power_equipment_switch_model;//开关型号
    TextView user_power__equipment_rated_current;//额定电流
    TextView user_power_equipment_voltage_level;//电压等级
    TextView user_power_equipment_operating_mechanism;//操作机构
    TextView user_power_equipment_number;//台数
    TextView user_power_equipment_operating_condition_;//运行工况
    TextView user_power_equipment_picture;//图片
    TextView user_power_equipment_update_time;//修改时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_power_equipment);

        user_power_equipment_return = findViewById(R.id.user_power_equipment_return);
        user_power_equipment_username = findViewById(R.id.user_power_equipment_username);

        user_power_equipment_switch_model = findViewById(R.id.user_power_equipment_switch_model);
        user_power__equipment_rated_current = findViewById(R.id.user_power__equipment_rated_current);
        user_power_equipment_voltage_level = findViewById(R.id.user_power_equipment_voltage_level);
        user_power_equipment_operating_mechanism = findViewById(R.id.user_power_equipment_operating_mechanism);
        user_power_equipment_number = findViewById(R.id.user_power_equipment_number);
        user_power_equipment_operating_condition_ = findViewById(R.id.user_power_equipment_operating_condition_);
        user_power_equipment_picture = findViewById(R.id.user_power_equipment_picture);
        user_power_equipment_update_time = findViewById(R.id.user_power_equipment_update_time);

        //返回按钮监听
        user_power_equipment_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPowerEquipmentActivity.this.finish();
            }
        });

        //获取传递过来的数据
        importantUser = (importantUser)getIntent().getSerializableExtra(userPowerEquipment);

        //将重要用户实际名称显示在页面顶部
        user_power_equipment_username.setText(importantUser.getUserName() + " — 电源情况 — 高低压设备");

        //访问web后台路径,参数
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");
        String myPath = "a/mobile/UserPowerHLEquipment/findUserPowerHLEquipment;JSESSIONID=" + sessionid;
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
        HttpService.showDialog(UserPowerEquipmentActivity.this,"正在加载数据...");
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
        private final WeakReference<UserPowerEquipmentActivity> userPowerActivity;

        public MyHandler(UserPowerEquipmentActivity activity) {
            userPowerActivity = new WeakReference<UserPowerEquipmentActivity>(activity);
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
        HLEquipment hlEquipment = new HLEquipment();
        try {
            jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++)    {//jsonArray.length()
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                hlEquipment.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                hlEquipment.setUpdateTime(jsonObject.has("updateDate")?jsonObject.getString("updateDate"):"");
                hlEquipment.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                hlEquipment.setSwitchType(jsonObject.has("switchType")?jsonObject.getString("switchType"):"");
                hlEquipment.setRatedCurrent(jsonObject.has("ratedCurrent")?jsonObject.getString("ratedCurrent"):"");
                hlEquipment.setVoltageRank(jsonObject.has("voltageRank")?jsonObject.getString("voltageRank"):"");
                hlEquipment.setOperatMechanism(jsonObject.has("operatMechanism")?jsonObject.getString("operatMechanism"):"");
                hlEquipment.setCount(jsonObject.has("count")?jsonObject.getString("count"):"");
                hlEquipment.setOperatConditions(jsonObject.has("operatConditions")?jsonObject.getString("operatConditions"):"");
                hlEquipment.setImage(jsonObject.has("image")?jsonObject.getString("image"):"");
            }
        } catch (JSONException e) {
            Log.d("YJGLIndexActivity", "json解析报错");
            e.printStackTrace();
        }

        user_power_equipment_switch_model.setText(hlEquipment.getSwitchType());//开关型号
        user_power__equipment_rated_current.setText(hlEquipment.getRatedCurrent());//额定电流
        user_power_equipment_voltage_level.setText(hlEquipment.getVoltageRank());//电压等级
        user_power_equipment_operating_mechanism.setText(hlEquipment.getOperatMechanism());//操作机构
        user_power_equipment_number.setText(hlEquipment.getCount());//台数
        user_power_equipment_operating_condition_.setText(hlEquipment.getOperatConditions());//运行工况
        user_power_equipment_picture.setText(hlEquipment.getImage());//图片
        user_power_equipment_update_time.setText(hlEquipment.getUpdateTime());//修改时间

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
