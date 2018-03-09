package com.taixingzhineng.android.ui.zyyh;

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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.model.importantUser;
import com.taixingzhineng.android.ui.service.HttpService;
import com.taixingzhineng.android.ui.util.ChineseToPinyinHelper;
import com.taixingzhineng.android.ui.model.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2017/12/16.
 * 重要用户首页
 */

public class ZYYHIndexActivity extends AppCompatActivity {
    private String userInforName = "ZZYHIndex_UserInfor_Name";

    String parameter = "";

    private MyHandler myhandler = new MyHandler(this);
    private String initPath = "";
    private String initData = "";
    JSONArray jsonArray;

    ArrayList<String> allUserNames = new ArrayList<String>();//所有姓名数据
    List<importantUser> importantUsers = new ArrayList<importantUser>();//所有重要用户数据

    private List<user> list = new ArrayList<>();
    private ListView listView;
    private ZYYHIndexAdapter adapter;
    private LetterIndexView letterIndexView;
    private int positionForSection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zyyhindex);

        listView = findViewById(R.id.zyyh_index_list);//通讯录

        parameter = getIntent().getStringExtra("parameter");

        //初始化list，并对list排序
        initData();
    }

    private void initData(){
        //访问web后台路径,参数
        SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
        String sessionid = preferences.getString("sessionid", "");
        String myPath = "a/mobile/ImportantUser/findAll;JSESSIONID=" + sessionid;
        String myData = "&mobileLogin=true";
        //获取重要用户数据
        HttpService(myPath,myData);
    }
    /**
     *
     * @param myPath 路径
     * @param myData 参数
     */
    private void HttpService(String myPath,String myData){
        HttpService.showDialog(ZYYHIndexActivity.this,"正在加载数据...");
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
        private final WeakReference<ZYYHIndexActivity> zyyhActivity;

        public MyHandler(ZYYHIndexActivity activity) {
            zyyhActivity = new WeakReference<ZYYHIndexActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (zyyhActivity.get() == null) {
                return;
            }
            zyyhActivity.get().updateUIThread(msg);
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
        try {
            jsonArray = new JSONArray(result);
            for (int i=0; i < jsonArray.length(); i++)    {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                allUserNames.add(jsonObject.getString("userName"));

                importantUser importantUser = new importantUser();
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
                //获得中文所对应的拼音
                String convert = ChineseToPinyinHelper.getInstance().getPinyin(importantUser.getUserName()).toUpperCase();

                importantUser.setPinyin(convert);
                //如果拼音的首字母时A-Z则直接初始化,否则设置到#组
                String substring = convert.substring(0, 1);
                if (substring.matches("[A-Z]")) {
                    importantUser.setFirstLetter(substring);
                }else{
                    importantUser.setFirstLetter("#");
                }
                //如果是所有用户则不添加
                if(importantUser.getId().equals("0")){
                    if(parameter!=null&&parameter.equals("select")){
                        importantUsers.add(importantUser);
                    }
                }else{
                    importantUsers.add(importantUser);
                }
            }
        } catch (JSONException e) {
            Log.d("ZYYHIndexActivity", "json解析报错");
            e.printStackTrace();
        }

        //排序
        Collections.sort(importantUsers, new Comparator<importantUser>() {
            @Override
            public int compare(importantUser lhs, importantUser rhs) {
                if (lhs.getFirstLetter()==rhs.getFirstLetter()){
                    return 0;
                }else {
                    if (lhs.getFirstLetter().contains("#")) {
                        return 1;
                    } else if (rhs.getFirstLetter().contains("#")) {
                        return -1;
                    }else{
                        return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
                    }
                }
            }
        });
        if(importantUsers.size()>0){
            initView();
        }
    }
    private void initView(){
        //通讯录模块adapter
        adapter = new ZYYHIndexAdapter(this, importantUsers);

        listView.setAdapter(adapter);
        //获得点击字母弹出的提示框
        TextView textView = findViewById(R.id.show_zyyh_letter_index_list);
        //获得字母检索view
        letterIndexView = findViewById(R.id.zyyh_letter_index_list);
        //设置手指滑动时在显示字母
        letterIndexView.setTextViewDialog(textView);
        //
        letterIndexView.setUpdateListView(new LetterIndexView.UpdateListView() {
            @Override
            public void updateListView(String currentChar) {
                //获得该项的索引号
                positionForSection = adapter.getPositionForSection(currentChar.charAt(0));
                //将该索引号显示在最上面
                listView.setSelection(positionForSection);
            }
        });
        //滑动监听
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int sectionForPosition = adapter.getSectionForPosition(firstVisibleItem);
                //if(sectionForPosition!=-1){
                    letterIndexView.updateLetterIndexView(sectionForPosition);
                //}
            }
        });
        //单击用户跳转
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject jsonobj = new JSONObject();
                try {
                    jsonobj = jsonArray.getJSONObject(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(parameter!=null&&parameter.equals("select")){
                    Intent intent = new Intent();
                    intent.putExtra("data", importantUsers.get(position));
                    setResult(2, intent);
                    //  结束当前页面(关闭当前界面)
                    finish();
                }else{
                    //position 点击的Item位置，从0开始算
                    Intent intent = new Intent(ZYYHIndexActivity.this,UserInforActivity.class);
                    //importantUser对象实现Serializable接口即可以传递对象
                    intent.putExtra(userInforName,importantUsers.get(position));
                    startActivity(intent);
                }
            }
        });
    }
}
