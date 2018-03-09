package com.taixingzhineng.android.ui.yjgl.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.model.earlyWarning;
import com.taixingzhineng.android.ui.service.HttpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/16.
 */

public class YJGLIndexAdapter extends ArrayAdapter<earlyWarning>{
    public static boolean editMode = false;
    public static HashMap<Integer, Boolean> isSelected;

    private  Context mcontext;
    private LayoutInflater mInflater;
    private int mResourceId;

    public YJGLIndexAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.mResourceId = textViewResourceId;
        this.mcontext = context;
        this.mInflater = LayoutInflater.from(context);

    }
    // 初始化 设置所有checkbox都为未选择
    public void init() {
        isSelected = new HashMap<Integer, Boolean>();
        for (int i = 0; i < getCount(); i++) {
            isSelected.put(i, false);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获得某一行数据
        earlyWarning earlyWarning = getItem(position);
        //获得子页面各元素
        View view = mInflater.inflate(mResourceId, null);
        ImageView yjgl_index_list_img = view.findViewById(R.id.yjgl_index_list_img);
        TextView yjgl_index_list_title = view.findViewById(R.id.yjgl_index_list_title);
        TextView yjgl_index_list_data = view.findViewById(R.id.yjgl_index_list_data);
        TextView yjgl_index_list_state = view.findViewById(R.id.yjgl_index_list_state);
        TextView yjgl_index_list_impuserNo = view.findViewById(R.id.yjgl_index_list_impuserNo);

        CheckBox yjgl_index_list_cb = view.findViewById(R.id.yjgl_index_list_cb);
        if(isSelected.get(position)){
            yjgl_index_list_cb.setChecked(true);
        }else{
            yjgl_index_list_cb.setChecked(false);
        }
        if (editMode) {
            yjgl_index_list_cb.setVisibility(View.VISIBLE);
        } else {
            yjgl_index_list_cb.setVisibility(View.GONE);
        }

        //预警图标
        String ImageName = "";
        if ((earlyWarning.getImage() != null) && (earlyWarning.getImage().length() > 0)) {
            int dot = earlyWarning.getImage().lastIndexOf('.');
            if ((dot >-1) && (dot < (earlyWarning.getImage().length()))) {
                ImageName = earlyWarning.getImage().substring(0, dot);
            }
        }
        int resID = mcontext.getResources().getIdentifier(ImageName,"drawable", mcontext.getPackageName());
        yjgl_index_list_img.setImageDrawable(mcontext.getResources().getDrawable(resID));

        yjgl_index_list_title.setText(earlyWarning.getName());

        if(earlyWarning.getState().equals("106")){
            yjgl_index_list_state.setText("新增待发布");
        }else if(earlyWarning.getState().equals("107")){
            yjgl_index_list_state.setText("调整待发布");
        }else if(earlyWarning.getState().equals("108")){
            yjgl_index_list_state.setText("结束待发布");
        }else if(earlyWarning.getState().equals("206")){
            yjgl_index_list_state.setText("新增已发布");
        }else if(earlyWarning.getState().equals("207")){
            yjgl_index_list_state.setText("调整已发布");
        }

        //设置发布状态文字颜色
        // 显示发布或上报时间
        if(earlyWarning.getState().equals("106")||earlyWarning.getState().equals("107")||earlyWarning.getState().equals("108")){
            yjgl_index_list_data.setText(earlyWarning.getReportTime()+"上报");
            yjgl_index_list_state.setTextColor(mcontext.getResources().getColor(R.color.colorRed));
        }else if(earlyWarning.getState().equals("206")||earlyWarning.getState().equals("207")){
            yjgl_index_list_data.setText(earlyWarning.getReleaseTime()+"发布");
            yjgl_index_list_state.setTextColor(mcontext.getResources().getColor(R.color.colorBlue));
        }

        yjgl_index_list_impuserNo.setText(earlyWarning.getImpuserName());

        return view;
    }
}

