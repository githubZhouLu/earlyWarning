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
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.model.earlyWarning;
import com.taixingzhineng.android.ui.model.importantUser;
import com.taixingzhineng.android.ui.service.HttpService;
import com.taixingzhineng.android.ui.zyyh.ZYYHIndexActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/12/18.
 * 预警详细-》预警详细
 */

public class YJXXActivity extends AppCompatActivity {
    private String paramentName = "YJGLIndexActivity_YJXXActivity";

    String UserNo = "";//重要用户编号

    EditText yjxx_scope_impact;//影响范围
    EditText yjxx_early_warning_action;//预警行动
    EditText yjxx_list_important_users;//涉及重要用户清单
    TextView yjxx_important_user_manager;//重要用户主管

    LinearLayout yjxx_scope_impact_layout;//影响范围
    LinearLayout yjxx_early_warning_action_layout;//预警行动
    LinearLayout yjxx_list_important_users_layout;//涉及重要用户清单
    LinearLayout yjxx_important_user_manager_layout;//重要用户主管

    TextView yjxx_early_warning_return;//取消按钮
    TextView yuxx_early_warning_release;//发布按钮

    earlyWarning earlyWarning;

    private MyHandler1 myhandler1 = new MyHandler1(this);
    private String initPath1 = "";
    private String initData1 = "";

    private MyHandler myhandler = new MyHandler(this);
    private String initPath = "";
    private String initData = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_yjxx);

        initView();

        //取消按钮
        early_warning_return();
        //选择重要用户
        selectImportantUser();
        //发布按钮
        early_warning_release();
        //获得上一个activity传递过来的值并获得此对象所有值
        initData();
    }

    private void initView(){
        yjxx_scope_impact = findViewById(R.id.yjxx_scope_impact);//影响范围
        yjxx_early_warning_action = findViewById(R.id.yjxx_early_warning_action);//预警行动
        yjxx_list_important_users = findViewById(R.id.yjxx_list_important_users);//涉及重要用户清单
        yjxx_list_important_users.setInputType(InputType.TYPE_NULL);//禁止手机软键盘。
        //editText.setInputType(InputType.TYPE_CLASS_TEXT);//开启软键盘。
        yjxx_important_user_manager = findViewById(R.id.yjxx_important_user_manager);//重要用户主管

        yjxx_scope_impact_layout = findViewById(R.id.yjxx_scope_impact_layout);//影响范围
        yjxx_early_warning_action_layout = findViewById(R.id.yjxx_early_warning_action_layout);//预警行动
        yjxx_list_important_users_layout = findViewById(R.id.yjxx_list_important_users_layout);//涉及重要用户清单
        yjxx_important_user_manager_layout = findViewById(R.id.yjxx_important_user_manager_layout);//重要用户主管

        yjxx_early_warning_return = findViewById(R.id.yjxx_early_warning_return);//取消按钮
        yuxx_early_warning_release = findViewById(R.id.yuxx_early_warning_release);//发布按钮
    }

    //取消按钮
    private void early_warning_return(){
        yjxx_early_warning_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YJXXActivity.this.finish();
            }
        });
    }
    //选择重要用户
    private void selectImportantUser(){
        yjxx_list_important_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YJXXActivity.this, ZYYHIndexActivity.class);
                intent.putExtra("parameter", "select");
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  如果请求码与返回码等于预期设置的值  则进行后续操作
        if (requestCode == 1 && resultCode == 2){
            // 获取返回的数据
            importantUser backImportantUser = (importantUser)data.getSerializableExtra("data");
            yjxx_list_important_users.setText(backImportantUser.getUserName());
            UserNo = backImportantUser.getUserNo();
            //查询重要用户主管
            findUserOrgan(backImportantUser.getUserNo());
        }
    }
    //supLeader 主管部门
    private void findUserOrgan(String userNo){
        //查询重要用户主管
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");
        String myPath = "a/mobile/dict/findDictByUserNo;JSESSIONID=" + sessionid;
        String myData = "&mobileLogin=true&userNo=" + userNo;
        HttpService1(myPath,myData);
    }
    /**
     *
     * @param myPath 路径
     * @param myData 参数
     */
    private void HttpService1(String myPath,String myData){
        HttpService.showDialog(YJXXActivity.this,"正在加载数据...");
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
        private final WeakReference<YJXXActivity> yjxxActivity;

        public MyHandler1(YJXXActivity activity) {
            yjxxActivity = new WeakReference<YJXXActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (yjxxActivity.get() == null) {
                return;
            }
            yjxxActivity.get().updateUIThread1(msg);
        }
    }
    //配合子线程更新UI线程
    private void updateUIThread1(Message msg){
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        String result = bundle.getString("result");
        isSession1(result);
    }
    private void isSession1(String result){
        HttpService.closeDialog();
        String label = "";
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            label = jsonObject.has("label")?jsonObject.getString("label"):"";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        yjxx_important_user_manager.setText(label);
    }
    //发布按钮
    private void early_warning_release(){
        yuxx_early_warning_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String effectRange = yjxx_scope_impact.getText().toString();
                String sugAction = yjxx_early_warning_action.getText().toString();
                String impuserNo = yjxx_list_important_users.getText().toString();
                //String userOrgan = yjxx_important_user_manager.getText().toString();
                if(effectRange.length()==0){
                    Toast toast = Toast.makeText(YJXXActivity.this, "影响范围不能为空！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else if(sugAction.length()==0){
                    Toast toast = Toast.makeText(YJXXActivity.this, "预警行动不能为空！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else if(UserNo.length()==0){
                    Toast toast = Toast.makeText(YJXXActivity.this, "重要用户清单不能为空！", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    earlyWarning.setEffectRange(effectRange);
                    earlyWarning.setSugAction(sugAction);
                    earlyWarning.setImpuserNo(UserNo);

                    new AlertDialog.Builder(YJXXActivity.this).setTitle("确认发布吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //访问web后台路径,参数
                                    SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
                                    String sessionid = preferences.getString("sessionid", "");
                                    String myPath = "a/mobile/earlyWarning/releaseEarlyWarning;JSESSIONID=" + sessionid;
                                    String myData = "&mobileLogin=true&earlyWarningJson=" + new Gson().toJson(earlyWarning);

                                    HttpService(myPath,myData);
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
    /**
     *
     * @param myPath 路径
     * @param myData 参数
     */
    private void HttpService(String myPath,String myData){
        HttpService.showDialog(YJXXActivity.this,"正在加载数据...");
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
        private final WeakReference<YJXXActivity> yjxxActivity;

        public MyHandler(YJXXActivity activity) {
            yjxxActivity = new WeakReference<YJXXActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (yjxxActivity.get() == null) {
                return;
            }
            yjxxActivity.get().updateUIThread(msg);
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
        YJXXActivity.this.finish();
    }
    //获得上一个activity传递过来的值并获得此对象所有值
    private void initData(){
        //获得上一个activity传递过来的值并获得此对象所有值
        String earlyWarningJson = getIntent().getStringExtra(paramentName);
        //Log.d("YJXXActivity", "initData:" + earlyWarningJson);
        earlyWarning = new earlyWarning();
        try {
            JSONObject dataJson = new JSONObject(earlyWarningJson);
            earlyWarning.setId(dataJson.getString("id"));
            earlyWarning.setWarnMessageno(dataJson.getString("warnMessageno"));
            earlyWarning.setName(dataJson.getString("name"));

            if(dataJson.getString("rank").equals("001")){
                earlyWarning.setRank("Ⅰ级");
            }else if(dataJson.getString("rank").equals("002")){
                earlyWarning.setRank("Ⅱ级");
            }else if(dataJson.getString("rank").equals("003")){
                earlyWarning.setRank("Ⅲ级");
            }else if(dataJson.getString("rank").equals("004")){
                earlyWarning.setRank("Ⅳ级");
            }

            earlyWarning.setCompanyId(dataJson.has("companyId")?dataJson.getString("companyId"):"");
            earlyWarning.setStartTime(dataJson.has("startTime")?dataJson.getString("startTime"):"");
            earlyWarning.setEndTime(dataJson.has("endTime")?dataJson.getString("endTime"):"");
            earlyWarning.setState(dataJson.has("state")?dataJson.getString("state"):"");
            earlyWarning.setContent(dataJson.has("content")?dataJson.getString("content"):"");
            earlyWarning.setReleaseTime(dataJson.has("releaseTime")?dataJson.getString("releaseTime"):"");
            earlyWarning.setEffectRange(dataJson.has("effectRange")?dataJson.getString("effectRange"):"");
            earlyWarning.setSugAction(dataJson.has("sugAction")?dataJson.getString("sugAction"):"");
            UserNo = dataJson.has("impuserNo")?dataJson.getString("impuserNo"):"";
            earlyWarning.setImpuserNo(dataJson.has("impuserNo")?dataJson.getString("impuserNo"):"");
            earlyWarning.setImage(dataJson.has("image")?dataJson.getString("image"):"");
            earlyWarning.setAdjustFlag(dataJson.has("adjustFlag")?dataJson.getString("adjustFlag"):"");
            //earlyWarning.setUserOrgan(dataJson.has("userOrgan")?dataJson.getString("userOrgan"):"");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //初始化页面view对象的值
        ((TextView)findViewById(R.id.yjxx_warning_name)).setText(earlyWarning.getName());//预警名称

        if(earlyWarning.getState().equals("206")||earlyWarning.getState().equals("207")){
            yuxx_early_warning_release.setVisibility(View.GONE);
            yjxx_scope_impact_layout.setVisibility(View.GONE);
            yjxx_early_warning_action_layout.setVisibility(View.GONE);
            yjxx_list_important_users_layout.setVisibility(View.GONE);
            yjxx_important_user_manager_layout.setVisibility(View.GONE);
        }else{
            yuxx_early_warning_release.setVisibility(View.VISIBLE);
            yjxx_scope_impact_layout.setVisibility(View.VISIBLE);
            yjxx_early_warning_action_layout.setVisibility(View.VISIBLE);
            yjxx_list_important_users_layout.setVisibility(View.VISIBLE);
            yjxx_important_user_manager_layout.setVisibility(View.VISIBLE);
        }

        if(earlyWarning.getState().equals("106")){
            ((TextView)findViewById(R.id.yjxx_warning_state)).setText("新增待发布");
        }else if(earlyWarning.getState().equals("107")){
            ((TextView)findViewById(R.id.yjxx_warning_state)).setText("调整待发布");
        }else if(earlyWarning.getState().equals("108")){
            ((TextView)findViewById(R.id.yjxx_warning_state)).setText("结束待发布");
        }else if(earlyWarning.getState().equals("206")){
            ((TextView)findViewById(R.id.yjxx_warning_state)).setText("新增已发布");
        }else if(earlyWarning.getState().equals("207")){
            ((TextView)findViewById(R.id.yjxx_warning_state)).setText("调整已发布");
        }

        ((TextView)findViewById(R.id.yjxx_warning_level)).setText(earlyWarning.getRank());//预警级别

        ImageView yjxx_warning_icon = findViewById(R.id.yjxx_warning_icon);//预警图标
        String ImageName = "";
        if ((earlyWarning.getImage() != null) && (earlyWarning.getImage().length() > 0)) {
            int dot = earlyWarning.getImage().lastIndexOf('.');
            if ((dot >-1) && (dot < (earlyWarning.getImage().length()))) {
                ImageName = earlyWarning.getImage().substring(0, dot);
            }
        }
        int resID = getResources().getIdentifier(ImageName,"drawable", getPackageName());
        yjxx_warning_icon.setImageDrawable(getResources().getDrawable(resID));

        ((TextView)findViewById(R.id.yjxx_warning_content)).setText(earlyWarning.getContent());//预警内容
        ((TextView)findViewById(R.id.yjxx_start_time)).setText(earlyWarning.getStartTime());//开始时间
        ((TextView)findViewById(R.id.yjxx_end_time)).setText(earlyWarning.getEndTime());//结束时间
        ((TextView)findViewById(R.id.yjxx_source_unit)).setText(earlyWarning.getCompanyId());//来源单位
        ((TextView)findViewById(R.id.yjxx_release_time)).setText(earlyWarning.getReleaseTime());//发布时间
        ((TextView)findViewById(R.id.yjxx_possible_scope_impact)).setText(earlyWarning.getEffectRange());//可能影响范围
        ((TextView)findViewById(R.id.yjxx_recommendations_early_warning_action)).setText(earlyWarning.getSugAction());//建议预警行动
        findUsernameByNo(earlyWarning.getImpuserNo());//涉及重要用户清单
        //((TextView)findViewById(R.id.yjxx_proposal_list_important_users)).setText(earlyWarning.getImpuserNo());//建议涉及重要用户清单

        ((TextView)findViewById(R.id.yjxx_scope_impact)).setText(earlyWarning.getEffectRange());//影响范围
        ((TextView)findViewById(R.id.yjxx_early_warning_action)).setText(earlyWarning.getSugAction());//预警行动

        findUserOrgan(earlyWarning.getImpuserNo());//重要用户主管
    }
    private void findUsernameByNo(String impuserNo){
        //查询重要用户主管
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");
        String myPath = "a/mobile/ImportantUser/findImportantUserById;JSESSIONID=" + sessionid;
        String myData = "&mobileLogin=true&impuserNo="+impuserNo;
        HttpService2(myPath,myData);
    }

    private void HttpService2(final String initPath,final String initData){
        final MyHandler2 myhandler2 = new MyHandler2(this);
        //Log.d("YJXXActivity", "HttpService2:" + initData);
        HttpService.showDialog(YJXXActivity.this,"正在加载数据...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = HttpService.ServiceByPost(initPath,initData);
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
        private final WeakReference<YJXXActivity> yjxxActivity;

        public MyHandler2(YJXXActivity activity) {
            yjxxActivity = new WeakReference<YJXXActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (yjxxActivity.get() == null) {
                return;
            }
            yjxxActivity.get().updateUIThread2(msg);
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
        //Log.d("YJXXActivity", "session2:" + result);
        HttpService.closeDialog();
        String userName = "";
        try {
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            userName = jsonObject.has("userName")?jsonObject.getString("userName"):"";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ((TextView)findViewById(R.id.yjxx_proposal_list_important_users)).setText(userName);
        ((TextView)findViewById(R.id.yjxx_list_important_users)).setText(userName);
    }
}
