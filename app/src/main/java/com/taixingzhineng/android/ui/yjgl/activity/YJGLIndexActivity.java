package com.taixingzhineng.android.ui.yjgl.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.model.earlyWarning;
import com.taixingzhineng.android.ui.service.HttpService;
import com.taixingzhineng.android.ui.yjgl.adapter.YJGLIndexAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/16.
 */

public class YJGLIndexActivity extends AppCompatActivity{
    private MyHandler myhandler = new MyHandler(this);
    private String initPath = "";
    private String initData = "";
    JSONArray jsonArray;

    ListView listView;

    TextView yjgl_index_menu_release ;
    TextView yjgl_index_menu_return;
    TextView yjgl_index_menu ;

    YJGLIndexAdapter adapter;

    CheckBox checkBox;//子xml页面复选框

    JSONArray earlyWarningJson = new JSONArray();//已选择的预警

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Log.i("ceshi", "yjgl onCreate");

        setContentView(R.layout.activity_yjglindex);

        initView();
        initData();
        initMenu();
    }
    private void initView(){
        /**
         * 菜单
         */
        yjgl_index_menu_release = this.findViewById(R.id.yjgl_index_menu_release);
        yjgl_index_menu_return = this.findViewById(R.id.yjgl_index_menu_return);
        yjgl_index_menu = this.findViewById(R.id.yjgl_index_menu);

        listView = this.findViewById(R.id.yjgl_index_list);

        LayoutInflater factory = LayoutInflater.from(YJGLIndexActivity.this);
        View layout = factory.inflate(R.layout.activity_yjglindex_list, null);
        checkBox = layout.findViewById(R.id.yjgl_index_list_cb);
    }
    private void initMenu(){
        //切换为预警编辑状态
        yjgl_index_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEdit();
            }
        });
        //切换为非编辑状态
        yjgl_index_menu_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkNotEdit();
            }
        });
        //批量发布预警
        yjgl_index_menu_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                earlyWarningJson = null;
                Iterator iter = YJGLIndexAdapter.isSelected.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    if((Boolean) entry.getValue()){
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject((int)entry.getKey());
                            if(jsonObject.getString("state").equals("106")||jsonObject.getString("state").equals("107")||jsonObject.getString("state").equals("108")){
                                if(jsonObject.getString("rank").equals("001")){
                                    jsonObject.put("rank","Ⅰ级");
                                }else if(jsonObject.getString("rank").equals("002")){
                                    jsonObject.put("rank","Ⅱ级");
                                }else if(jsonObject.getString("rank").equals("003")){
                                    jsonObject.put("rank","Ⅲ级");
                                }else if(jsonObject.getString("rank").equals("004")){
                                    jsonObject.put("rank","Ⅳ级");
                                }
                                earlyWarningJson.put(jsonObject);
                            }else{
                                earlyWarningJson = null;
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(earlyWarningJson == null){
                    Toast toast = Toast.makeText(YJGLIndexActivity.this, "选择有误，请重新选择！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    new AlertDialog.Builder(YJGLIndexActivity.this).setTitle("确认发布吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("YJGLIndexActivity", "onClick: "+earlyWarningJson);
                                    //访问web后台路径,参数
                                    SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
                                    String sessionid = preferences.getString("sessionid", "");
                                    String myPath = "a/mobile/earlyWarning/batchReleaseEarlyWarning;JSESSIONID=" + sessionid;
                                    String myData = "&mobileLogin=true&earlyWarningJson=" + earlyWarningJson.toString();

                                    HttpService2(myPath,myData);
                                }
                            })
                            .setNegativeButton("返回", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            }).show();
                }
            }
        });
    }
    //切换为编辑状态
    private void checkEdit(){
        checkBox.setVisibility(View.VISIBLE);
        YJGLIndexAdapter.editMode = true;
        adapter.notifyDataSetChanged();

        yjgl_index_menu.setVisibility(View.GONE);
        yjgl_index_menu_release.setVisibility(View.VISIBLE);
        yjgl_index_menu_return.setVisibility(View.VISIBLE);

        //设置单击item选中该item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(YJGLIndexAdapter.isSelected.get(position)){
                    YJGLIndexAdapter.isSelected.put(position,false);
                }else{
                    YJGLIndexAdapter.isSelected.put(position,true);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
    //切换为非编辑状态
    private void checkNotEdit(){
        checkBox.setVisibility(View.GONE);
        YJGLIndexAdapter.editMode = false;
        adapter.notifyDataSetChanged();

        yjgl_index_menu.setVisibility(View.VISIBLE);
        yjgl_index_menu_release.setVisibility(View.GONE);
        yjgl_index_menu_return.setVisibility(View.GONE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String earlyWarningJson = jsonArray.getJSONObject(position).toString();
                    //position 点击的Item位置，从0开始算
                    Intent intent = new Intent(YJGLIndexActivity.this,YJXXActivity.class);
                    intent.putExtra("YJGLIndexActivity_YJXXActivity",earlyWarningJson);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void initData(){
        //访问web后台路径,参数
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");
        String myPath = "a/mobile/earlyWarning/findNotEndList;JSESSIONID=" + sessionid;
        String myData = "&mobileLogin=true";
        //获取预警数据
        HttpService(myPath,myData);
    }
    /**
     *
     * @param myPath 路径
     * @param myData 参数
     */
    private void HttpService(String myPath,String myData){
        HttpService.showDialog(YJGLIndexActivity.this,"正在加载数据...");
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
        private final WeakReference<YJGLIndexActivity> yjglActivity;

        public MyHandler(YJGLIndexActivity activity) {
            yjglActivity = new WeakReference<YJGLIndexActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (yjglActivity.get() == null) {
                return;
            }
            yjglActivity.get().updateUIThread(msg);
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
        Log.d("YJGLIndexActivity", "session:"+result);
        adapter = new YJGLIndexAdapter(this, R.layout.activity_yjglindex_list);

        try {
            jsonArray = new JSONArray(result);
            for (int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                earlyWarning earlyWarning = new earlyWarning();
                earlyWarning.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                earlyWarning.setWarnMessageno(jsonObject.has("warnMessageno")?jsonObject.getString("warnMessageno"):"");
                earlyWarning.setName(jsonObject.has("name")?jsonObject.getString("name"):"");
                earlyWarning.setRank(jsonObject.has("rank")?jsonObject.getString("rank"):"");
                earlyWarning.setCompanyId(jsonObject.has("companyId")?jsonObject.getString("companyId"):"");
                earlyWarning.setStartTime(jsonObject.has("startTime")?jsonObject.getString("startTime"):"");
                earlyWarning.setEndTime(jsonObject.has("endTime")?jsonObject.getString("endTime"):"");
                earlyWarning.setState(jsonObject.has("state")?jsonObject.getString("state"):"");
                earlyWarning.setContent(jsonObject.has("content")?jsonObject.getString("content"):"");
                earlyWarning.setReleaseTime(jsonObject.has("releaseTime")?jsonObject.getString("releaseTime"):"");
                earlyWarning.setEffectRange(jsonObject.has("effectRange")?jsonObject.getString("effectRange"):"");
                earlyWarning.setSugAction(jsonObject.has("sugAction")?jsonObject.getString("sugAction"):"");
                earlyWarning.setImpuserNo(jsonObject.has("impuserNo")?jsonObject.getString("impuserNo"):"");
                earlyWarning.setImpuserName(jsonObject.has("userName")?jsonObject.getString("userName"):"");
                earlyWarning.setUserOrgan(jsonObject.has("userOrgan")?jsonObject.getString("userOrgan"):"");
                earlyWarning.setImage(jsonObject.has("image")?jsonObject.getString("image"):"");
                earlyWarning.setAdjustFlag(jsonObject.has("adjustFlag")?jsonObject.getString("adjustFlag"):"");
                earlyWarning.setReportTime(jsonObject.has("reportTime")?jsonObject.getString("reportTime"):"");
                adapter.add(earlyWarning);
            }
        } catch (JSONException e) {
            Log.d("YJGLIndexActivity", "json解析报错");
            e.printStackTrace();
        }
        adapter.init();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String earlyWarningJson = jsonArray.getJSONObject(position).toString();
                    //position 点击的Item位置，从0开始算
                    Intent intent = new Intent(YJGLIndexActivity.this,YJXXActivity.class);
                    intent.putExtra("YJGLIndexActivity_YJXXActivity",earlyWarningJson);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     *
     * @param initPath2 路径
     * @param initData2 参数
     */
    private void HttpService2(final String initPath2,final String initData2){
        HttpService.showDialog(YJGLIndexActivity.this,"正在发布...");
        final MyHandler2 myhandler2 = new MyHandler2(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = HttpService.ServiceByPost(initPath2,initData2);
                Bundle bundle = new Bundle();
                bundle.putString("result",str);
                Message msg = new Message();
                msg.setData(bundle);
                myhandler2.sendMessage(msg);
            }
        }).start();
    }
    //弱引用，防止内存泄露
    private static class MyHandler2 extends Handler {
        private final WeakReference<YJGLIndexActivity> yjglActivity;

        public MyHandler2(YJGLIndexActivity activity) {
            yjglActivity = new WeakReference<YJGLIndexActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (yjglActivity.get() == null) {
                return;
            }
            yjglActivity.get().updateUIThread2(msg);
        }
    }
    //配合子线程更新UI线程
    private void updateUIThread2(Message msg){
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        String result = bundle.getString("result");
        isSession2(result);
    }
    private void isSession2(String result){
        HttpService.closeDialog();//关闭加载框
        initData();
        checkNotEdit();
    }

    //Activity创建或者从后台重新回到前台时被调用
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ceshi", "yjgl onStart");
    }

    //Activity从后台重新回到前台时被调用
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("ceshi", "yjgl onRestart");
    }

    //Activity创建或者从被覆盖、后台重新回到前台时被调用
    @Override
    protected void onResume() {
        super.onResume();
        initData();
        Log.i("ceshi", "yjgl onResume");
    }
    //伪生命周期onResume
    public void invisibleOnScreen(){
        initData();
        checkNotEdit();
        Log.i("ceshi", "yjgl invisibleOnScreen");
    }

    //Activity被覆盖到下面或者锁屏时被调用
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("ceshi", "yjgl onPause");
        //有可能在执行完onPause或onStop后,系统资源紧张将Activity杀死,所以有必要在此保存持久数据
    }

    //退出当前Activity或者跳转到新Activity时被调用
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("ceshi", "yjgl onStop");
    }

    //退出当前Activity时被调用,调用之后Activity就结束了
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("ceshi", "yjgl onDestory");
    }
}
