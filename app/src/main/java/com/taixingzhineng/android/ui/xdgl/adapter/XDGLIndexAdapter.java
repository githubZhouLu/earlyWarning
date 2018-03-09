package com.taixingzhineng.android.ui.xdgl.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.model.EarlyWarningAction;
import com.taixingzhineng.android.ui.model.earlyWarning;
import com.taixingzhineng.android.ui.util.StringHandle;
import com.taixingzhineng.android.ui.xdgl.activity.RelatedWarningActivity;

/**
 * Created by Administrator on 2017/12/16.
 */

public class XDGLIndexAdapter extends ArrayAdapter<EarlyWarningAction>{
    private  Context mcontext;
    private LayoutInflater mInflater;
    private int mResourceId;

    public XDGLIndexAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.mResourceId = textViewResourceId;
        this.mcontext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获得某一行数据
        EarlyWarningAction earlyWarningAction = getItem(position);
        //获得子页面各元素
        View view = mInflater.inflate(mResourceId, null);
        TextView xdgl_index_item_name = view.findViewById(R.id.xdgl_index_item_name);
        ImageView xdgl_index_item_icon = view.findViewById(R.id.xdgl_index_item_icon);
        TextView xdgl_index_item_range = view.findViewById(R.id.xdgl_index_item_range);
        TextView xdgl_index_item_action = view.findViewById(R.id.xdgl_index_item_action);
        TextView xdgl_index_item_users = view.findViewById(R.id.xdgl_index_item_users);
        TextView xdgl_index_item_data = view.findViewById(R.id.xdgl_index_item_data);
        TextView xdgl_index_item_reply = view.findViewById(R.id.xdgl_index_item_reply);
        //将数据填充到子元素中
        xdgl_index_item_name.setText(earlyWarningAction.getName());

        //预警图标
        String ImageName = "";
        if ((earlyWarningAction.getImage() != null) && (earlyWarningAction.getImage().length() > 0)) {
            int dot = earlyWarningAction.getImage().lastIndexOf('.');
            if ((dot >-1) && (dot < (earlyWarningAction.getImage().length()))) {
                ImageName = earlyWarningAction.getImage().substring(0, dot);
            }
        }
        int resID = mcontext.getResources().getIdentifier(ImageName,"drawable", mcontext.getPackageName());
        xdgl_index_item_icon.setImageDrawable(mcontext.getResources().getDrawable(resID));


        xdgl_index_item_range.setText(earlyWarningAction.getEffectRange());
        xdgl_index_item_action.setText(StringHandle.StringEllipsis(earlyWarningAction.getWarnAction(),80));
        xdgl_index_item_users.setText(earlyWarningAction.getUserName());
        xdgl_index_item_data.setText(earlyWarningAction.getReleaseTime()+"发布");
        if(earlyWarningAction.getNum() == ""){
            xdgl_index_item_reply.setText("回复（0）");
        }else{
            xdgl_index_item_reply.setText("回复（"+ earlyWarningAction.getNum() +"）");
        }
        return view;
    }
}

