package com.taixingzhineng.android.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.service.HttpService;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity {
    private MyHandler myhandler = new MyHandler(this);
    private String initPath = "";
    private String initData = "";

    Button login_button;
    EditText login_username;
    EditText login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_username = findViewById(R.id.login_username);
                login_password = findViewById(R.id.login_password);

                //判断用户名密码是否为空
                String username = login_username.getText().toString();
                String password = login_password.getText().toString();
                //是否自动登录
                //login_automatic = findViewById(R.id.login_automatic);
                if(username.length()>0 && password.length()>0){

                    String myPath = "a/login";
                    String myData = "username=" + username +
                            "&password=" + password +
                            "&mobileLogin=true";
                    HttpService(myPath,myData);
                }else{
                    Toast toast = Toast.makeText(LoginActivity.this,R.string.login_verification_null,Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
            }
        });

    }
    private void HttpService(String myPath,String myData){
        HttpService.showDialog(LoginActivity.this,"正在登录...");
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
        private final WeakReference<LoginActivity> loginActivity;

        public MyHandler(LoginActivity activity) {
            loginActivity = new WeakReference<LoginActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            System.out.println(msg);
            if (loginActivity.get() == null) {
                return;
            }
            loginActivity.get().updateUIThread(msg);
        }
    }
    //配合子线程更新UI线程
    private void updateUIThread(Message msg){
        Bundle bundle = new Bundle();
        bundle = msg.getData();
        String result = bundle.getString("result");
        isSession(result);
    }
    //判断登录是否成功
    private void isSession(String result) {
        HttpService.closeDialog();
        if(result.equals("IO异常")){
            Toast toast = Toast.makeText(LoginActivity.this, "服务器连接失败，请检查网络连接！", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }else{
            try {
                JSONObject dataJson = new JSONObject(result);
                if (dataJson.has("loginName")) {
                    //登录成功
                    SharedPreferences preferences = getSharedPreferences("userInformation", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("id", dataJson.getString("id"));
                    editor.putString("loginName", dataJson.getString("loginName"));
                    editor.putString("name", dataJson.getString("name"));
                    editor.putString("sessionid", dataJson.getString("sessionid"));
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                } else{
                    Toast toast = Toast.makeText(LoginActivity.this, R.string.login_verification_error, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } catch (JSONException e) {
                Log.d("LogingActivity", "报错");
                e.printStackTrace();
            }
        }
    }
                    /*//根据用户名验证此用户
                    user user = new user();
                    user.setName(username);
                    userDao userDao = new userDao(LoginActivity.this);
                    List<user> users = userDao.queryByName(user);
                    //判断密码是否正确
                    if(users!=null && users.size()>0 && users.get(0).getPassword().equals(password)){*/
        //判断用户是否勾选自动登录并重置状态
            /*SharedPreferences preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            if(login_automatic.isChecked()){
                editor.putBoolean("automaticLogin", true);
            }else{
                editor.putBoolean("automaticLogin", false);
            }
            editor.commit();*/
        //跳转到IndexActivity
}


