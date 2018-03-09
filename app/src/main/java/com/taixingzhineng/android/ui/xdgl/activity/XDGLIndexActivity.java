package com.taixingzhineng.android.ui.xdgl.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.database.earlyWarningDao;
import com.taixingzhineng.android.ui.model.EarlyWarningAction;
import com.taixingzhineng.android.ui.model.earlyWarning;
import com.taixingzhineng.android.ui.service.HttpService;
import com.taixingzhineng.android.ui.xdgl.adapter.XDGLIndexAdapter;
import com.taixingzhineng.android.ui.yjgl.activity.YJGLIndexActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Administrator on 2017/12/16.
 * 预警行动管理
 */

public class XDGLIndexActivity extends AppCompatActivity {
    private MyHandler myhandler = new MyHandler(this);
    private String initPath = "";
    private String initData = "";
    JSONArray jsonArray;

    List<earlyWarning> earlyWarnings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("ceshi", "xdgl onCreate");

        setContentView(R.layout.activity_xdglindex);

        initData();
    }
    private void initData(){
        //访问web后台路径,参数
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");
        String id = preferences.getString("id", "");
        String myPath = "a/mobile/earlyWarningAction/findEarlyWarningActivityNotEnd;JSESSIONID=" + sessionid;
        String myData = "&mobileLogin=true&id=" + id;
        //获取预警行动数据
        HttpService(myPath,myData);
    }
    /**
     *
     * @param myPath 路径
     * @param myData 参数
     */
    private void HttpService(String myPath,String myData){
        HttpService.showDialog(XDGLIndexActivity.this,"正在加载数据...");
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
        private final WeakReference<XDGLIndexActivity> xdglActivity;

        public MyHandler(XDGLIndexActivity activity) {
            xdglActivity = new WeakReference<XDGLIndexActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (xdglActivity.get() == null) {
                return;
            }
            xdglActivity.get().updateUIThread(msg);
        }
    }
    //配合子线程更新UI线程
    private void updateUIThread(Message msg){
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        String result = bundle.getString("result");
        isSession(result);
    }
    //获取预警数据
    private void isSession(String result) {
        HttpService.closeDialog();//关闭加载框
        Log.d("XDGLIndexActivity", "isSession: " + result);
        XDGLIndexAdapter adapter = new XDGLIndexAdapter(this, R.layout.activity_xdglindex_list);
        try {
            jsonArray = new JSONArray(result);
            for (int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                EarlyWarningAction earlyWarningAction = new EarlyWarningAction();
                earlyWarningAction.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                earlyWarningAction.setWarnMessageno(jsonObject.has("warnMessageno")?jsonObject.getString("warnMessageno"):"");
                earlyWarningAction.setName(jsonObject.has("name")?jsonObject.getString("name"):"");
                earlyWarningAction.setRank(jsonObject.has("rank")?jsonObject.getString("rank"):"");
                earlyWarningAction.setCompanyId(jsonObject.has("companyId")?jsonObject.getString("companyId"):"");
                earlyWarningAction.setStartTime(jsonObject.has("startTime")?jsonObject.getString("startTime"):"");
                earlyWarningAction.setEndTime(jsonObject.has("endTime")?jsonObject.getString("endTime"):"");
                earlyWarningAction.setState(jsonObject.has("state")?jsonObject.getString("state"):"");
                earlyWarningAction.setContent(jsonObject.has("content")?jsonObject.getString("content"):"");
                earlyWarningAction.setReleaseTime(jsonObject.has("releaseTime")?jsonObject.getString("releaseTime"):"");
                earlyWarningAction.setEffectRange(jsonObject.has("effectRange")?jsonObject.getString("effectRange"):"");
                earlyWarningAction.setWarnAction(jsonObject.has("warnAction")?jsonObject.getString("warnAction"):"");
                earlyWarningAction.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                earlyWarningAction.setUserName(jsonObject.has("userName")?jsonObject.getString("userName"):"");
                earlyWarningAction.setImage(jsonObject.has("image")?jsonObject.getString("image"):"");
                earlyWarningAction.setAdjustFlag(jsonObject.has("adjustFlag")?jsonObject.getString("adjustFlag"):"");
                earlyWarningAction.setNum(jsonObject.has("num")?jsonObject.getString("num"):"");
                adapter.add(earlyWarningAction);
            }
        } catch (JSONException e) {
            Log.d("XDGLIndexActivity", "json解析报错");
            e.printStackTrace();
        }

        ListView listView = this.findViewById(R.id.xdgl_index_list);
        listView.setAdapter(adapter);

        //设置预警详细按钮跳转
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView related_early_warning = view.findViewById(R.id.related_early_warning);
                //TextView xdgl_index_item_reply = view.findViewById(R.id.xdgl_index_item_reply);
                try {
                    //position 点击的Item位置，从0开始算
                    String earlyWarningJson = jsonArray.getJSONObject(position).toString();
                    //Log.d("RelatedWarningActivity", "earlyWarningJson: "+earlyWarningJson);
                    Intent intent = new Intent(XDGLIndexActivity.this,RelatedWarningActivity.class);
                    intent.putExtra("XDGLIndexActivity_RelatedWarningActivity",earlyWarningJson);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //Activity创建或者从后台重新回到前台时被调用
    @Override
    protected void onStart() {
        super.onStart();
        //Log.i("ceshi", "xdgl onStart");
    }

    //Activity从后台重新回到前台时被调用
    @Override
    protected void onRestart() {
        super.onRestart();
        //Log.i("ceshi", "xdgl onRestart");
    }


    //Activity创建或者从被覆盖、后台重新回到前台时被调用
    @Override
    protected void onResume() {
        super.onResume();
        initData();
        //Log.i("ceshi", "yjgl onResume");
    }

    //伪生命周期onResume
    public void invisibleOnScreen(){
        initData();
        Log.i("ceshi", "xdgl invisibleOnScreen");
    }

    //Activity被覆盖到下面或者锁屏时被调用
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ceshi", "xdgl onPause");
        //有可能在执行完onPause或onStop后,系统资源紧张将Activity杀死,所以有必要在此保存持久数据
    }

    //退出当前Activity或者跳转到新Activity时被调用
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ceshi", "xdgl onStop");
    }

    //退出当前Activity时被调用,调用之后Activity就结束了
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ceshi", "xdgl onDestory");
    }
}
