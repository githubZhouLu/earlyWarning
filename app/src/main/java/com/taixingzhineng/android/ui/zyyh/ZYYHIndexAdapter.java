package com.taixingzhineng.android.ui.zyyh;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.taixingzhineng.android.R;
import com.taixingzhineng.android.ui.model.importantUser;
import com.taixingzhineng.android.ui.model.user;

import java.util.List;

/**
 * Created by Administrator on 2017/12/18.
 */

public class ZYYHIndexAdapter extends BaseAdapter implements SectionIndexer {
    private List<importantUser> list;
    private Context context;
    private LayoutInflater inflater;
    private int k = 0;
    private importantUser importantUserItem;
    ViewHolder holder;

    public ZYYHIndexAdapter(Context context, List<importantUser> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_zyyhindex_item, null);
            holder = new ViewHolder();
            holder.showLetter = convertView.findViewById(R.id.zyyh_index_item_show_letter);
            holder.username = convertView.findViewById(R.id.zyyh_index_item_username);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        importantUserItem = list.get(position);
        holder.username.setText(importantUserItem.getUserName());
        //获得当前position是属于哪个分组
        int sectionForPosition = getSectionForPosition(position);
        //获得该分组第一项的position
        int positionForSection = getPositionForSection(sectionForPosition);
        //查看当前position是不是当前item所在分组的第一个item
        //如果是，则显示showLetter，否则隐藏
        if (position == positionForSection) {
            holder.showLetter.setVisibility(View.VISIBLE);
            holder.showLetter.setText(importantUserItem.getFirstLetter());
        } else {
            holder.showLetter.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    //传入一个分组值[A....Z],获得该分组的第一项的position
    @Override
    public int getPositionForSection(int sectionIndex) {
        for (k = 0; k < list.size(); k++) {
            int m = list.get(k).getFirstLetter().charAt(0);
            if (m == sectionIndex) {
                return k;
            }
        }
        return -1;
    }

    //传入一个position，获得该position所在的分组
    @Override
    public int getSectionForPosition(int position) {
        //if(list.size()==0){
        //    return -1;
        //}else{
            return list.get(position).getFirstLetter().charAt(0);
        //}
    }

    class ViewHolder {
        TextView username, showLetter;
    }
}
