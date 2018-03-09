package com.taixingzhineng.android.ui;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.model.MobileMenu;
import com.taixingzhineng.android.ui.service.HttpService;
import com.taixingzhineng.android.ui.xdgl.activity.XDGLIndexActivity;
import com.taixingzhineng.android.ui.wd.WDIndexActivity;
import com.taixingzhineng.android.ui.yjgl.activity.YJGLIndexActivity;
import com.taixingzhineng.android.ui.zyyh.ZYYHIndexActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/12/15.
 */

public class IndexActivity  extends AppCompatActivity implements View.OnClickListener{

    private MyHandler myhandler = new MyHandler(this);
    private String initPath = "";
    private String initData = "";
    JSONArray jsonArray;

    List<MobileMenu> mobileMenus = new ArrayList<MobileMenu>();

    private List<View> views = new ArrayList<View>();
    private ViewPager viewPager;
    private LinearLayout llChat, llFriends, llContacts, llSettings;
    private ImageView ivChat, ivFriends, ivContacts, ivSettings, ivCurrent;

    private LocalActivityManager mactivityManager = null;
    private String[] mlistTag = {"YJGL","SJGL","ZZYH","WD"}; //activity标识

    IndexAdapter adapter = null;
    //private String paramentName = "IndexActivityParamentName";

    //private int itemNumber = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Log.i("ceshi", "index onCreate");

        mactivityManager = new LocalActivityManager(this, true);
        mactivityManager.dispatchCreate(savedInstanceState);

        initView();
        getMenu();//获得底部菜单
    }
    private void getMenu(){
        //访问web后台路径,参数
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");
        String id = preferences.getString("id", "");
        String myPath = "a/mobile/mobileMenu/findMobileMenu;JSESSIONID=" + sessionid;
        String myData = "&mobileLogin=true&id=" + id;
        //获取电源情况数据
        HttpService(myPath,myData);
    }
    /**
     *
     * @param myPath 路径
     * @param myData 参数
     */
    private void HttpService(String myPath,String myData){
        HttpService.showDialog(IndexActivity.this,"正在加载数据...");
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
        private final WeakReference<IndexActivity> indexActivity;

        public MyHandler(IndexActivity activity) {
            indexActivity = new WeakReference<IndexActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (indexActivity.get() == null) {
                return;
            }
            indexActivity.get().updateUIThread(msg);
        }
    }
    //配合子线程更新UI线程
    private void updateUIThread(Message msg){
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        String result = bundle.getString("result");
        isSession(result);
    }

    private void isSession(String result){
        HttpService.closeDialog();//关闭加载框
        try {
            jsonArray = new JSONArray(result);
            for (int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MobileMenu mobileMenu = new MobileMenu();
                mobileMenu.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                mobileMenu.setCreateDate(jsonObject.has("createDate")?jsonObject.getString("createDate"):"");
                mobileMenu.setUpdateDate(jsonObject.has("updateDate")?jsonObject.getString("updateDate"):"");
                mobileMenu.setParentId(jsonObject.has("parentId")?jsonObject.getString("parentId"):"");
                mobileMenu.setParentIds(jsonObject.has("parentIds")?jsonObject.getString("parentIds"):"");
                mobileMenu.setName(jsonObject.has("name")?jsonObject.getString("name"):"");
                mobileMenu.setIsShow(jsonObject.has("isShow")?jsonObject.getString("isShow"):"");
                mobileMenus.add(mobileMenu);
            }
        } catch (JSONException e) {
            Log.d("IndexActivity", "json解析报错");
            e.printStackTrace();
        }
        for (MobileMenu mobileMenu : mobileMenus){
            if(mobileMenu.getId().equals("7a656001cea84b0a998b23165c5316ea")){
                llChat.setVisibility(View.VISIBLE);
            }
            if(mobileMenu.getId().equals("ef8738669b804b21832139969616279f")){
                llFriends.setVisibility(View.VISIBLE);
            }
            if(mobileMenu.getId().equals("706b891815d84c1cbfa6e87417e52835")){
                llContacts.setVisibility(View.VISIBLE);
            }
        }
        llSettings.setVisibility(View.VISIBLE);

        initData();

        if(llChat.getVisibility()==View.VISIBLE){
            changeTab(llChat.getId());
        }else if(llFriends.getVisibility()==View.VISIBLE){
            changeTab(llFriends.getId());
        }else if(llContacts.getVisibility()==View.VISIBLE){
            changeTab(llContacts.getId());
        }else{
            changeTab(llSettings.getId());
        }
    }

    private void initView(){
        viewPager = findViewById(R.id.viewPager);

        llChat = findViewById(R.id.llChat);
        llFriends = findViewById(R.id.llFriends);
        llContacts = findViewById(R.id.llContacts);
        llSettings = findViewById(R.id.llSettings);

        llChat.setOnClickListener(this);
        llFriends.setOnClickListener(this);
        llContacts.setOnClickListener(this);
        llSettings.setOnClickListener(this);

        ivChat = findViewById(R.id.ivChat);
        ivFriends = findViewById(R.id.ivFriends);
        ivContacts = findViewById(R.id.ivContacts);
        ivSettings = findViewById(R.id.ivSettings);
    }

    @Override
    public void onClick(View v) {
        initData();
        changeTab(v.getId());
    }

    private void changeTab(int id) {
        //ivCurrent.setSelected(false);
        switch (id) {
            case R.id.llChat:
                viewPager.setCurrentItem(0);
            case 0:
                ivChat.setSelected(true);
                ivCurrent = ivChat;
                break;
            case R.id.llFriends:
                viewPager.setCurrentItem(1);
            case 1:
                ivFriends.setSelected(true);
                ivCurrent = ivFriends;
                break;
            case R.id.llContacts:
                viewPager.setCurrentItem(2);
            case 2:
                ivContacts.setSelected(true);
                ivCurrent = ivContacts;
                break;
            case R.id.llSettings:
                viewPager.setCurrentItem(3);
            case 3:
                ivSettings.setSelected(true);
                ivCurrent = ivSettings;
                break;
            default:
                break;
        }
    }

    private void initData() {
        //加载要显示的页卡
        Intent intent1 = new Intent(IndexActivity.this, YJGLIndexActivity.class);
        //intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        View v1 = mactivityManager.startActivity(mlistTag[0], intent1).getDecorView();
        views.add(v1);
        Intent intent2 = new Intent(IndexActivity.this, XDGLIndexActivity.class);
        //intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        View v2 = mactivityManager.startActivity(mlistTag[1], intent2).getDecorView();
        views.add(v2);
        Intent intent3 = new Intent(IndexActivity.this, ZYYHIndexActivity.class);
        //intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        View v3 = mactivityManager.startActivity(mlistTag[2], intent3).getDecorView();
        views.add(v3);
        Intent intent4 = new Intent(IndexActivity.this, WDIndexActivity.class);
        //intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        View v4 = mactivityManager.startActivity(mlistTag[3], intent4).getDecorView();
        views.add(v4);

        adapter = new IndexAdapter(views);
        viewPager.setAdapter(adapter);
    }

    //Activity创建或者从后台重新回到前台时被调用
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("ceshi", "index onStart");
    }

    //Activity从后台重新回到前台时被调用
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("ceshi", "index onRestart");
    }

    //Activity创建或者从被覆盖、后台重新回到前台时被调用
    @Override
    protected void onResume() {
        super.onResume();
        mactivityManager.dispatchResume();

        //调用YJGLIndexActivity伪生命周期onResume
        if(viewPager != null){
            Activity _activity = mactivityManager.getActivity("YJGL");
            if(_activity != null && _activity instanceof YJGLIndexActivity){
                ((YJGLIndexActivity)_activity ).invisibleOnScreen();
            }

            Activity activity_ = mactivityManager.getActivity("SJGL");
            if(activity_ != null && activity_ instanceof XDGLIndexActivity){
                ((XDGLIndexActivity)activity_ ).invisibleOnScreen();
            }
        }

        //private String[] mlistTag = {"YJGL","SJGL","ZZYH","WD"}; //activity标识
        //initData();
        Log.i("ceshi", "index onResume");
    }

    //Activity被覆盖到下面或者锁屏时被调用
    @Override
    protected void onPause() {
        super.onPause();
        //mactivityManager.dispatchPause(true);
        Log.i("ceshi", "index onPause");
        //有可能在执行完onPause或onStop后,系统资源紧张将Activity杀死,所以有必要在此保存持久数据
    }

    //退出当前Activity或者跳转到新Activity时被调用
    @Override
    protected void onStop() {
        super.onStop();
        //mactivityManager.dispatchStop();
        Log.i("ceshi", "index onStop");
    }

    //退出当前Activity时被调用,调用之后Activity就结束了
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mactivityManager.dispatchDestroy(true);
        Log.i("ceshi", "index onDestory");
    }

}
