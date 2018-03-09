package com.taixingzhineng.android.ui.xdgl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.model.EarlyWarningActionComments;
import com.taixingzhineng.android.ui.model.user;


/**
 * Created by Administrator on 2017/12/19.
 */

public class confirmReplyAdapter extends ArrayAdapter<EarlyWarningActionComments> {
    private Context mcontext;
    private LayoutInflater mInflater;
    private int mResourceId;

    public confirmReplyAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.mResourceId = textViewResourceId;
        this.mcontext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获得子页面各元素
        View view = mInflater.inflate(mResourceId, null);
        ImageView reply_user_icon = view.findViewById(R.id.reply_user_icon);
        TextView reply_user_name = view.findViewById(R.id.reply_user_name);
        TextView reply_user_time = view.findViewById(R.id.reply_user_time);
        TextView reply_user_content = view.findViewById(R.id.reply_user_content);

        //获得某一行数据,将数据填充到子元素中
        EarlyWarningActionComments earlyWarningActionComments = getItem(position);
        /*//获得图片资源id
        int id = mcontext.getResources().getIdentifier(user.getIcon(),  "drawable", mcontext.getPackageName());
        reply_user_icon.setImageResource(id);*/
        reply_user_name.setText(earlyWarningActionComments.getCuserName());
        reply_user_time.setText(earlyWarningActionComments.getCommentTime());
        reply_user_content.setText(earlyWarningActionComments.getComments());
        return view;
    }
}
