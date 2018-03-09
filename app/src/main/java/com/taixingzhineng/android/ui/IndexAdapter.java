package com.taixingzhineng.android.ui;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class IndexAdapter extends PagerAdapter{  //继承适配器

    private List<View>viewList;
    //private List<String> titleList;

    //实现构造方法
    public IndexAdapter(List<View> viewList){//,List<String> titleList
        this.viewList=viewList;
        //this.titleList=titleList;
    }

    @Override
    public int getCount() {
        return viewList.size();  //返回当前页卡数量
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {   //View是否来自对象
        return view==object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //实例化一个页卡
        container.addView(viewList.get(position));  //position代表当前的位置（所定位的View）
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {  //销毁页卡
        container.removeView(viewList.get(position));
    }


    /*
        设置Viewpager页卡的标题
        在main_activity.xml文件中的<ViewPager/>里添加<android.support.v4.view.PagerTabStrip/>子标签 才起作用
         */
    /*@Override
    public CharSequence getPageTitle(int position) {  //返回当前view对应的标题
        return titleList.get(position);
    }*/

}