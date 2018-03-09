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

public class UserPowerTransformerActivity extends AppCompatActivity {
    private String userPowerTransformer = "PowerInformation_userPowerTransformer";
    importantUser importantUser;

    private MyHandler myhandler = new MyHandler(this);
    private String initPath = "";
    private String initData = "";
    JSONArray jsonArray;

    LinearLayout user_power_transformer_return;
    TextView user_power_transformer_username;

    TextView user_power_transformer_model;//型号
    TextView user_power_transformer_transformer_nameplate;//变压器铭牌
    TextView user_power_transformer_manufacturer;//制造厂
    TextView user_power_transformer_type;//类型
    TextView user_power_transformer_capacity;//容量
    TextView user_power_transformer_first_test_voltage;//一次测电压
    TextView user_power_transformer_second_voltage_measurement;//二次测电压
    TextView user_power_transformer_connection_group;//接线组别
    TextView user_power_transformer_impedance;//阻抗
    TextView user_power_transformer_date_of_production;//出厂日期
    TextView user_power_transformer_operating_condition;//运行工况
    TextView user_power_transformer_picture;//图片
    TextView user_power_transformer_modification_time;//修改时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_power_transformer);

        user_power_transformer_return = findViewById(R.id.user_power_transformer_return);
        user_power_transformer_username = findViewById(R.id.user_power_transformer_username);

        user_power_transformer_model = findViewById(R.id.user_power_transformer_model);
        user_power_transformer_transformer_nameplate = findViewById(R.id.user_power_transformer_transformer_nameplate);
        user_power_transformer_manufacturer = findViewById(R.id.user_power_transformer_manufacturer);
        user_power_transformer_type = findViewById(R.id.user_power_transformer_type);
        user_power_transformer_capacity = findViewById(R.id.user_power_transformer_capacity);
        user_power_transformer_first_test_voltage = findViewById(R.id.user_power_transformer_first_test_voltage);
        user_power_transformer_second_voltage_measurement = findViewById(R.id.user_power_transformer_second_voltage_measurement);
        user_power_transformer_connection_group = findViewById(R.id.user_power_transformer_connection_group);
        user_power_transformer_impedance = findViewById(R.id.user_power_transformer_impedance);
        user_power_transformer_date_of_production = findViewById(R.id.user_power_transformer_date_of_production);
        user_power_transformer_operating_condition = findViewById(R.id.user_power_transformer_operating_condition);
        user_power_transformer_picture = findViewById(R.id.user_power_transformer_picture);
        user_power_transformer_modification_time = findViewById(R.id.user_power_transformer_modification_time);

        //返回按钮监听
        user_power_transformer_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserPowerTransformerActivity.this.finish();
            }
        });

        //获取传递过来的数据
        importantUser = (importantUser)getIntent().getSerializableExtra(userPowerTransformer);

        //将重要用户实际名称显示在页面顶部
        user_power_transformer_username.setText(importantUser.getUserName() + " — 电源情况 — 变压器");

        //访问web后台路径,参数
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");
        String myPath = "a/mobile/userPowerTransformer/findUserPowerTransformer;JSESSIONID=" + sessionid;
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
        HttpService.showDialog(UserPowerTransformerActivity.this,"正在加载数据...");
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
        private final WeakReference<UserPowerTransformerActivity> userPowerActivity;

        public MyHandler(UserPowerTransformerActivity activity) {
            userPowerActivity = new WeakReference<UserPowerTransformerActivity>(activity);
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
        UserPowerTransformer userPowerTransformer = new UserPowerTransformer();
        try {
            jsonArray = new JSONArray(result);
            for (int i=0; i < 1; i++)    {//jsonArray.length()
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                userPowerTransformer.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                userPowerTransformer.setUpdateTime(jsonObject.has("updateDate")?jsonObject.getString("updateDate"):"");
                userPowerTransformer.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                userPowerTransformer.setTransformerType(jsonObject.has("transformerType")?jsonObject.getString("transformerType"):"");
                userPowerTransformer.setNameplate(jsonObject.has("nameplate")?jsonObject.getString("nameplate"):"");
                userPowerTransformer.setFactory(jsonObject.has("factory")?jsonObject.getString("factory"):"");
                userPowerTransformer.setType(jsonObject.has("type")?jsonObject.getString("type"):"");
                userPowerTransformer.setCapacity(jsonObject.has("capacity")?jsonObject.getString("capacity"):"");
                userPowerTransformer.setLinkGroup(jsonObject.has("linkGroup")?jsonObject.getString("linkGroup"):"");
                userPowerTransformer.setImpedance(jsonObject.has("impedance")?jsonObject.getString("impedance"):"");
                userPowerTransformer.setOutfactoryDate(jsonObject.has("outfactoryDate")?jsonObject.getString("outfactoryDate"):"");
                userPowerTransformer.setOperatConditions(jsonObject.has("operatConditions")?jsonObject.getString("operatConditions"):"");
                userPowerTransformer.setUrl(jsonObject.has("url")?jsonObject.getString("url"):"");
                userPowerTransformer.setfVoltage(jsonObject.has("fvoltage")?jsonObject.getString("fvoltage"):"");
                userPowerTransformer.setsVoltage(jsonObject.has("svoltage")?jsonObject.getString("svoltage"):"");
            }
        } catch (JSONException e) {
            Log.d("YJGLIndexActivity", "json解析报错");
            e.printStackTrace();
        }

         user_power_transformer_model.setText(userPowerTransformer.getTransformerType());
         user_power_transformer_transformer_nameplate.setText(userPowerTransformer.getNameplate());
         user_power_transformer_manufacturer.setText(userPowerTransformer.getFactory());
         user_power_transformer_type.setText(userPowerTransformer.getType());
         user_power_transformer_capacity.setText(userPowerTransformer.getCapacity());
         user_power_transformer_first_test_voltage.setText(userPowerTransformer.getfVoltage());
         user_power_transformer_second_voltage_measurement.setText(userPowerTransformer.getsVoltage());
         user_power_transformer_connection_group.setText(userPowerTransformer.getLinkGroup());
         user_power_transformer_impedance.setText(userPowerTransformer.getImpedance());
         user_power_transformer_date_of_production.setText(userPowerTransformer.getOutfactoryDate());
         user_power_transformer_operating_condition.setText(userPowerTransformer.getOperatConditions());
         user_power_transformer_picture.setText(userPowerTransformer.getUrl());
         user_power_transformer_modification_time.setText(userPowerTransformer.getUpdateTime());

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
