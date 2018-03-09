package com.taixingzhineng.android.ui.xdgl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.model.EarlyWarningAction;
import com.taixingzhineng.android.ui.model.earlyWarning;

/**
 * Created by Administrator on 2017/12/19.
 * 预警行动管理-》相关预警-》历史预警
 */

public class HistoricalWarningAdapter extends ArrayAdapter<EarlyWarningAction> {
    private Context mcontext;
    private LayoutInflater mInflater;
    private int mResourceId;

    public HistoricalWarningAdapter(Context context, int textViewResourceId) {
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
        TextView historical_warning_item_name = view.findViewById(R.id.historical_warning_item_name);//预警名称
        TextView historical_warning_item_level = view.findViewById(R.id.historical_warning_item_level);//预警级别
        ImageView historical_warning_item_icon = view.findViewById(R.id.historical_warning_item_icon);//预警图标
        TextView historical_warning_item_content = view.findViewById(R.id.historical_warning_item_content);//预警内容
        TextView historical_warning_item_start_time = view.findViewById(R.id.historical_warning_item_start_time);//开始时间
        TextView historical_warning_item_end_time = view.findViewById(R.id.historical_warning_item_end_time);//结束时间
        TextView historical_warning_item_source_unit = view.findViewById(R.id.historical_warning_item_source_unit);//来源单位
        TextView historical_warning_item_release_time = view.findViewById(R.id.historical_warning_item_release_time);//发布时间
        TextView historical_warning_item_scope_influence = view.findViewById(R.id.historical_warning_item_scope_influence);//影响范围
        TextView historical_warning_item_action = view.findViewById(R.id.historical_warning_item_action);//预警行动
        TextView historical_warning_item_users = view.findViewById(R.id.historical_warning_item_users);//涉及用户清单
        //将数据填充到子元素中
        historical_warning_item_name.setText(earlyWarningAction.getName());

        if(earlyWarningAction.getRank().equals("001")){
            historical_warning_item_level.setText("Ⅰ级");
        }else if(earlyWarningAction.getRank().equals("002")){
            historical_warning_item_level.setText("Ⅱ级");
        }else if(earlyWarningAction.getRank().equals("003")){
            historical_warning_item_level.setText("Ⅲ级");
        }else if(earlyWarningAction.getRank().equals("004")){
            historical_warning_item_level.setText("Ⅳ级");
        }

        //预警图标
        String ImageName = "";
        if ((earlyWarningAction.getImage() != null) && (earlyWarningAction.getImage().length() > 0)) {
            int dot = earlyWarningAction.getImage().lastIndexOf('.');
            if ((dot >-1) && (dot < (earlyWarningAction.getImage().length()))) {
                ImageName = earlyWarningAction.getImage().substring(0, dot);
            }
        }
        int resID = mcontext.getResources().getIdentifier(ImageName,"drawable", mcontext.getPackageName());
        historical_warning_item_icon.setImageDrawable(mcontext.getResources().getDrawable(resID));

        historical_warning_item_content.setText(earlyWarningAction.getContent());
        historical_warning_item_start_time.setText(earlyWarningAction.getStartTime());
        historical_warning_item_end_time.setText(earlyWarningAction.getEndTime());
        historical_warning_item_source_unit.setText(earlyWarningAction.getCompanyId());
        historical_warning_item_release_time.setText(earlyWarningAction.getReleaseTime());
        historical_warning_item_scope_influence.setText(earlyWarningAction.getEffectRange());
        historical_warning_item_action.setText(earlyWarningAction.getWarnAction());
        historical_warning_item_users.setText(earlyWarningAction.getUserName());

        return view;
    }
}
