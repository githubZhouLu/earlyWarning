package com.taixingzhineng.android.ui.service;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.taixingzhineng.android.ui.util.LoadingDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/12/27.
 */

public class HttpService {
    private static Dialog dialog;

    private static Context context = null;

    public static String ServiceByPost(String initPath,String initData){
        Log.d("HttpService",initPath+"启动登录线程");
        String msg = "";
        try {
            //初始化URL
            URL url = new URL("http://192.168.9.122:8080/2017017/" + initPath);//taixin_1
            //URL url = new URL("http://192.168.8.168:8080/2017017/" + initPath);//taixin_2
            //URL url = new URL("http://116.247.116.45:9127/2017017/" + initPath);//外网
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式
            conn.setRequestMethod("POST");

            //设置超时信息
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);

            //设置允许输入
            conn.setDoInput(true);
            //设置允许输出
            conn.setDoOutput(true);

            //post方式不能设置缓存，需手动设置为false
            conn.setUseCaches(false);

            //获取输出流
            OutputStream out = conn.getOutputStream();

            out.write(initData.getBytes());
            out.flush();
            out.close();
            conn.connect();

            if (conn.getResponseCode() == 200) {
                // 获取响应的输入流对象
                InputStream is = conn.getInputStream();
                // 创建字节输出流对象
                ByteArrayOutputStream message = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte buffer[] = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while ((len = is.read(buffer)) != -1) {
                    // 根据读取的长度写入到os对象中
                    message.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                message.close();
                // 返回字符串
                msg = new String(message.toByteArray());
                Log.d("HttpService",initPath+"线程返回结果");
                return msg;
            }else {
                Log.d("HttpService","http状态码:" + conn.getResponseCode());
            }
        } catch (MalformedURLException e) {
            Log.d("HttpService","MalformedURL异常");
            e.printStackTrace();
        } catch (IOException e) {
            msg = new String("IO异常");
            Log.d("HttpService","IO异常");
            e.printStackTrace();
        }
        Log.d("HttpService","exit");
        return msg;
    }

    /**
     * 显示Dialog
     */
    public static void showDialog(Context mcontext,String msg) {
        context = mcontext;

        if (dialog == null) {
            dialog = LoadingDialog.createLoadingDialog(mcontext, msg);
            dialog.show();
        }
    }
    /**
     * 关闭Dialog
     */
    public static void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
