package com.taixingzhineng.android.ui.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017/12/18.
 */
@DatabaseTable(tableName = "tb_user")
public class user {
    @DatabaseField(generatedId = true)
    //表示id为主键且自动增长
    private long id;
    @DatabaseField(columnName = "name",canBeNull = false,uniqueCombo = true)
    //非空且唯一
    private String name;//名字
    @DatabaseField(columnName = "password",canBeNull = false)
    private String password;//名字
    @DatabaseField(columnName = "pinyin")
    private String pinyin;//姓名的拼音
    @DatabaseField(columnName = "firstLetter")
    private String firstLetter;//用户姓名拼音的首字母
    @DatabaseField(columnName = "icon")
    private String icon;//图标

    public user() {
    }

    public user(String name, String pinyin, String firstLetter) {
        this.name = name;
        this.pinyin = pinyin;
        this.firstLetter = firstLetter;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
