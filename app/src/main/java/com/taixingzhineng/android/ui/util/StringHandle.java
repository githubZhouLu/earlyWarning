package com.taixingzhineng.android.ui.util;

/**
 * Created by Administrator on 2018/1/9.
 */

public class StringHandle {
    /**
     *获得一定长度的字符串后面的加上...
     * @param str 待处理字符串
     * @param length 显示的字符串的长度
     * @return
     */
    public static String StringEllipsis(String str,int length){
        if(length>str.length()||length<0){
            return str;
        }else{
            return str.substring(0,length)+".....";
        }
    }
}
