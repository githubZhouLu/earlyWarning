package com.taixingzhineng.android.ui.xdgl.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.model.EarlyWarningAction;
import com.taixingzhineng.android.ui.model.EarlyWarningActionComments;
import com.taixingzhineng.android.ui.model.user;
import com.taixingzhineng.android.ui.model.earlyWarning;
import com.taixingzhineng.android.ui.service.HttpService;
import com.taixingzhineng.android.ui.xdgl.adapter.HistoricalWarningAdapter;
import com.taixingzhineng.android.ui.xdgl.adapter.confirmReplyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/12/19.
 * 预警行动管理-》相关预警详细页
 */

public class RelatedWarningActivity extends AppCompatActivity {
    EarlyWarningAction earlyWarningAction = new EarlyWarningAction();;
    private MyHandler myhandler = new MyHandler(this);
    private String initPath = "";
    private String initData = "";

    private MyHandler1 myhandler1 = new MyHandler1(this);
    private String initPath1 = "";
    private String initData1 = "";

    private MyHandler2 myhandler2 = new MyHandler2(this);
    private String initPath2 = "";
    private String initData2 = "";

    LinearLayout related_switch_button;//相关预警折叠按钮
    ImageButton related_switch_button_image;//相关预警折叠按钮图标
    ListView Historical_warning_list;//历史预警listview
    TextView related_warning_user_reply_menu;//回复个数
    ListView confirm_listView;//回复listview
    LinearLayout related_warning_user_reply_confirm_switch;//回复按钮框
    LinearLayout related_warning_user_reply_cancel_button_layout;//取消或确认回复按钮框
    TextView related_warning_user_reply_cancel_button;//取消按钮
    TextView related_warning_user_reply_confirm_button;//确认回复按钮
    EditText related_warning_user_reply_context;//回复输入框


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatedwarning);

        related_switch_button = findViewById(R.id.related_switch_button);//相关预警折叠按钮
        related_switch_button_image = findViewById(R.id.related_switch_button_image);//相关预警折叠按钮图标

        Historical_warning_list = this.findViewById(R.id.Historical_warning_list);//历史预警listview
        related_warning_user_reply_menu = this.findViewById(R.id.related_warning_user_reply_menu);//回复个数
        confirm_listView = this.findViewById(R.id.related_warning_user_confirm);//回复listview

        related_warning_user_reply_cancel_button_layout = findViewById(R.id.related_warning_user_reply_cancel_button_layout);//取消或确认回复按钮框
        related_warning_user_reply_confirm_switch = findViewById(R.id.related_warning_user_reply_confirm_switch);//回复按钮框
        related_warning_user_reply_context = findViewById(R.id.related_warning_user_reply_context);//回复输入框
        related_warning_user_reply_cancel_button = findViewById(R.id.related_warning_user_reply_cancel_button);//取消按钮
        related_warning_user_reply_confirm_button = findViewById(R.id.related_warning_user_reply_confirm_button);//确认回复按钮

        //获得上一个activity传递过来的值并获得此对象所有值
        initData();

        //回复模块显示影藏相关操作
        replyOperation();

        //判断属于哪一条预警
        //Long id = getIntent().getLongExtra("XDGLIndexActivity_RelatedWarningActivity",0);

        //设置页面顶部返回按钮
        warningReturn();
        //设置相关预警折叠按钮
        relatedSwitch();
        //历史预警adapter
        historicalAdapter();
        //重要用户回复查询adapter
        userConfirmAdapter();
    }
    //获得上一个activity传递过来的值并获得此对象所有值
    private void initData(){
        String earlyWarningJson = getIntent().getStringExtra("XDGLIndexActivity_RelatedWarningActivity");
        //Log.d("RelatedWarningActivity", "initData1: "+earlyWarningJson);
        try {
            JSONObject dataJson = new JSONObject(earlyWarningJson);
            earlyWarningAction.setImpuserNo(dataJson.has("impuserNo")?dataJson.getString("impuserNo"):"");
            earlyWarningAction.setEffectRange(dataJson.has("effectRange")?dataJson.getString("effectRange"):"");
            earlyWarningAction.setWarnMessageno(dataJson.has("warnMessageno")?dataJson.getString("warnMessageno"):"");
            earlyWarningAction.setAdjustFlag(dataJson.has("adjustFlag")?dataJson.getString("adjustFlag"):"");
            earlyWarningAction.setState(dataJson.has("state")?dataJson.getString("state"):"");
            earlyWarningAction.setImage(dataJson.has("image")?dataJson.getString("image"):"");
            earlyWarningAction.setEndTime(dataJson.has("endTime")?dataJson.getString("endTime"):"");
            earlyWarningAction.setContent(dataJson.has("content")?dataJson.getString("content"):"");
            earlyWarningAction.setStartTime(dataJson.has("startTime")?dataJson.getString("startTime"):"");
            earlyWarningAction.setId(dataJson.has("id")?dataJson.getString("id"):"");

            if(dataJson.has("rank")){
                if(dataJson.getString("rank").equals("001")){
                    earlyWarningAction.setRank("Ⅰ级");
                }else if(dataJson.getString("rank").equals("002")){
                    earlyWarningAction.setRank("Ⅱ级");
                }else if(dataJson.getString("rank").equals("003")){
                    earlyWarningAction.setRank("Ⅲ级");
                }else if(dataJson.getString("rank").equals("004")){
                    earlyWarningAction.setRank("Ⅳ级");
                }
            }else{
                earlyWarningAction.setRank("");
            }

            earlyWarningAction.setWarnAction(dataJson.has("warnAction")?dataJson.getString("warnAction"):"");
            earlyWarningAction.setName(dataJson.has("name")?dataJson.getString("name"):"");
            earlyWarningAction.setUserName(dataJson.has("userName")?dataJson.getString("userName"):"");
            earlyWarningAction.setReleaseTime(dataJson.has("releaseTime")?dataJson.getString("releaseTime"):"");
            earlyWarningAction.setCompanyId(dataJson.has("companyId")?dataJson.getString("companyId"):"");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //初始化页面view对象的值
        ((TextView)findViewById(R.id.related_early_warning_name)).setText(earlyWarningAction.getName());//预警名称
        ((TextView)findViewById(R.id.related_warning_level)).setText(earlyWarningAction.getRank());//预警级别

        //预警图标
        String ImageName = "";
        if ((earlyWarningAction.getImage() != null) && (earlyWarningAction.getImage().length() > 0)) {
            int dot = earlyWarningAction.getImage().lastIndexOf('.');
            if ((dot >-1) && (dot < (earlyWarningAction.getImage().length()))) {
                ImageName = earlyWarningAction.getImage().substring(0, dot);
            }
        }
        int resID = getResources().getIdentifier(ImageName,"drawable", getPackageName());

        ((ImageView)findViewById(R.id.related_warning_icon)).setImageDrawable(getResources().getDrawable(resID));

        ((TextView)findViewById(R.id.related_warning_content)).setText(earlyWarningAction.getContent());//预警内容
        ((TextView)findViewById(R.id.related_warning_start_time)).setText(earlyWarningAction.getStartTime());//开始时间
        ((TextView)findViewById(R.id.related_warning_end_time)).setText(earlyWarningAction.getEndTime());//结束时间
        ((TextView)findViewById(R.id.related_warning_source_unit)).setText(earlyWarningAction.getCompanyId());//来源单位
        ((TextView)findViewById(R.id.related_warning_release_time)).setText(earlyWarningAction.getReleaseTime());//发布时间
        ((TextView)findViewById(R.id.related_possible_scope_of_impact)).setText(earlyWarningAction.getEffectRange());//影响范围
        ((TextView)findViewById(R.id.related_early_warning_action)).setText(earlyWarningAction.getWarnAction());//预警行动
        ((TextView)findViewById(R.id.related_important_users)).setText(earlyWarningAction.getUserName());//涉及重要用户清单
    }
    //回复模块显示影藏相关操作
    private void replyOperation(){
        //单击回复显示回复框
        related_warning_user_reply_confirm_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                related_warning_user_reply_cancel_button_layout.setVisibility(View.VISIBLE);
                related_warning_user_reply_confirm_switch.setVisibility(View.GONE);
                related_warning_user_reply_context.setVisibility(View.VISIBLE);
            }
        });
        //单击取消隐藏回复框
        related_warning_user_reply_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                related_warning_user_reply_cancel_button_layout.setVisibility(View.GONE);
                related_warning_user_reply_confirm_switch.setVisibility(View.VISIBLE);
                related_warning_user_reply_context.setVisibility(View.GONE);
            }
        });
        //单击确认回复隐藏回复框
        related_warning_user_reply_confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(related_warning_user_reply_context.getText().toString().length() == 0){
                    Toast toast = Toast.makeText(RelatedWarningActivity.this,"回复内容不能为空！",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }else{
                    related_warning_user_reply_cancel_button_layout.setVisibility(View.GONE);
                    related_warning_user_reply_confirm_switch.setVisibility(View.VISIBLE);
                    related_warning_user_reply_context.setVisibility(View.GONE);
                    userConfirmContext();
                }
            }
        });
    }
    //返回按钮
    private void warningReturn(){
        LinearLayout related_warning_return = findViewById(R.id.related_warning_return);
        related_warning_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RelatedWarningActivity.this.finish();
            }
        });
    }
    //相关预警折叠按钮
    private void relatedSwitch(){
        related_switch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListView Historical_warning_list = findViewById(R.id.Historical_warning_list);
                if(Historical_warning_list.getVisibility()==View.VISIBLE){
                    //如果相关预警显示则变为隐藏
                    Historical_warning_list.setVisibility(View.GONE);
                    //设置相关预警按钮图标
                    related_switch_button_image.setBackground(getResources().getDrawable(R.drawable.off_u237));
                }else if(Historical_warning_list.getVisibility()==View.GONE){
                    //如果相关预警隐藏则变为显示
                    Historical_warning_list.setVisibility(View.VISIBLE);
                    //设置相关预警按钮图标
                    related_switch_button_image.setBackground(getResources().getDrawable(R.drawable.on_u35));
                }
            }
        });
    }
    //历史预警adapter
    private void historicalAdapter(){
        //访问web后台路径,参数
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");
        String myPath = "a/mobile/earlyWarningAction/findEarlyWarningActionByWarnMessageno;JSESSIONID=" + sessionid;
        String myData = "&mobileLogin=true&warnMessageNo=" + earlyWarningAction.getWarnMessageno();
        //获取预警数据
        HttpService(myPath,myData);
    }
    /**
     *
     * @param myPath 路径
     * @param myData 参数
     */
    private void HttpService(String myPath,String myData){
        HttpService.showDialog(RelatedWarningActivity.this,"正在加载数据...");
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
        private final WeakReference<RelatedWarningActivity> relatedActivity;

        public MyHandler(RelatedWarningActivity activity) {
            relatedActivity = new WeakReference<RelatedWarningActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (relatedActivity.get() == null) {
                return;
            }
            relatedActivity.get().updateUIThread(msg);
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
        Log.d("RelatedWarningActivity", "isSession: "+result);
        HistoricalWarningAdapter adapter = new HistoricalWarningAdapter(this, R.layout.activity_historicalwarningitem);
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i=1; i < jsonArray.length(); i++)    {
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
                adapter.add(earlyWarningAction);
            }
        } catch (JSONException e) {
            Log.d("RelatedWarningActivity", "isSession/json解析报错");
            e.printStackTrace();
        }
        Historical_warning_list.setAdapter(adapter);

        //动态设置listview的高度
        DisplayMetrics dm =getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int listViewWidth = w_screen-(int)(5 * (getResources().getDisplayMetrics().density) + 0.5f);
        int widthSpec = View.MeasureSpec.makeMeasureSpec(listViewWidth, View.MeasureSpec.AT_MOST);

        int height = 0;
        int count = adapter.getCount();
        for(int i=0;i<count;i++){
            View temp = adapter.getView(i,null,Historical_warning_list);
            temp.measure(widthSpec,0);
            int j = temp.getMeasuredHeight();
            height += j;
        }
        ViewGroup.LayoutParams params = Historical_warning_list.getLayoutParams();
        params.height = height + (Historical_warning_list.getDividerHeight() * (count - 1));
        Historical_warning_list.setLayoutParams(params);
    }
    //重要用户回复adapter
    private void userConfirmAdapter(){
        //访问web后台路径,参数
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");
        String myPath = "a/mobile/earlyWarningActionComments/findActionComments;JSESSIONID=" + sessionid;
        String myData = "&mobileLogin=true&warnMessageNo=" + earlyWarningAction.getWarnMessageno();
        //获取预警数据
        HttpService1(myPath,myData);
    }
    /**
     *
     * @param myPath 路径
     * @param myData 参数
     */
    private void HttpService1(String myPath,String myData){
        HttpService.showDialog(RelatedWarningActivity.this,"正在加载数据...");
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
        private final WeakReference<RelatedWarningActivity> relatedActivity1;

        public MyHandler1(RelatedWarningActivity activity) {
            relatedActivity1 = new WeakReference<RelatedWarningActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (relatedActivity1.get() == null) {
                return;
            }
            relatedActivity1.get().updateUIThread1(msg);
        }
    }
    //配合子线程更新UI线程
    private void updateUIThread1(Message msg){
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        String result = bundle.getString("result");
        isSession1(result);
    }
    //获取预警数据
    private void isSession1(String result) {
        HttpService.closeDialog();//关闭加载框
        //Log.d("RelatedWarningActivity", "isSession1: "+result);
        confirmReplyAdapter adapter = new confirmReplyAdapter(this, R.layout.activity_confirmreply);
        try {
            JSONArray jsonArray = new JSONArray(result);
            for (int i=0; i < jsonArray.length(); i++)    {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                EarlyWarningActionComments earlyWarningActionComments = new EarlyWarningActionComments();
                earlyWarningActionComments.setId(jsonObject.has("id")?jsonObject.getString("id"):"");
                earlyWarningActionComments.setCuserName(jsonObject.has("cuserName")?jsonObject.getString("cuserName"):"");
                earlyWarningActionComments.setCuserId(jsonObject.has("cuserId")?jsonObject.getString("cuserId"):"");
                earlyWarningActionComments.setComments(jsonObject.has("comments")?jsonObject.getString("comments"):"");
                earlyWarningActionComments.setCommentId(jsonObject.has("commentId")?jsonObject.getString("commentId"):"");
                earlyWarningActionComments.setTitleId(jsonObject.has("titleId")?jsonObject.getString("titleId"):"");
                earlyWarningActionComments.setAdjustFlag(jsonObject.has("adjustFlag")?jsonObject.getString("adjustFlag"):"");
                earlyWarningActionComments.setCommentTime(jsonObject.has("commentTime")?jsonObject.getString("commentTime"):"");
                adapter.add(earlyWarningActionComments);
            }
            related_warning_user_reply_menu.setText("重要用户回复（" + jsonArray.length() + "）");
        } catch (JSONException e) {
            Log.d("RelatedWarningActivity", "isSession1/json解析报错");
            e.printStackTrace();
        }
        confirm_listView.setAdapter(adapter);

        //动态设置listview的高度
        DisplayMetrics dm =getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        //5表示页面左边padding的宽度
        int listViewWidth = w_screen-(int)(5 * (getResources().getDisplayMetrics().density) + 0.5f);
        int widthSpec = View.MeasureSpec.makeMeasureSpec(listViewWidth, View.MeasureSpec.AT_MOST);

        int height = 0;
        int count = adapter.getCount();
        for(int i=0;i<count;i++){
            View temp = adapter.getView(i,null,confirm_listView);
            temp.measure(widthSpec,0);
            int j = temp.getMeasuredHeight();
            height += j;
        }
        ViewGroup.LayoutParams params = this.confirm_listView.getLayoutParams();
        params.height = height + (confirm_listView.getDividerHeight() * (count - 1));
        confirm_listView.setLayoutParams(params);
    }
    //重要用户回复
    private void userConfirmContext(){
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String id = preferences.getString("id", "");
        EarlyWarningActionComments earlyWarningActionComments = new EarlyWarningActionComments();
        earlyWarningActionComments.setCuserId(id);
        earlyWarningActionComments.setTitleId(earlyWarningAction.getWarnMessageno());//标题id（对应预警信息编号）
        earlyWarningActionComments.setComments(related_warning_user_reply_context.getText().toString());//评论内容
        //访问web后台路径,参数
        String sessionid = preferences.getString("sessionid", "");
        String myPath = "a/mobile/earlyWarningActionComments/addActionComments;JSESSIONID=" + sessionid;
        String myData = "&mobileLogin=true&commentsJson=" + new Gson().toJson(earlyWarningActionComments);
        //获取预警数据
        HttpService2(myPath,myData);
    }
    /**
     *
     * @param myPath 路径
     * @param myData 参数
     */
    private void HttpService2(String myPath,String myData){
        //Log.d("RelatedWarningActivity", "HttpService2: " + myData);
        HttpService.showDialog(RelatedWarningActivity.this,"正在加载数据...");
        initPath2 = myPath;
        initData2 = myData;
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
        private final WeakReference<RelatedWarningActivity> relatedActivity2;

        public MyHandler2(RelatedWarningActivity activity) {
            relatedActivity2 = new WeakReference<RelatedWarningActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (relatedActivity2.get() == null) {
                return;
            }
            relatedActivity2.get().updateUIThread2(msg);
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
        userConfirmAdapter();
    }
}
