package com.taixingzhineng.android.ui.database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.taixingzhineng.android.ui.model.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.taixingzhineng.android.ui.database.DatabaseHelper.getHelper;

/**
 * Created by Administrator on 2017/12/25.
 */

public class userDao {
    private Context context;
    private Dao<user, Integer> userDao;
    private DatabaseHelper databaseHelper;

    public userDao(Context context)
    {
        this.context = context;
        getUserDao(context);
    }
    /**
     * 获得UserDao
     */
    private void getUserDao(Context context){
        try {
            databaseHelper = getHelper(context);
            userDao = databaseHelper.getDao(user.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
    *新增用户
     * @return int 新增成功的条数
    */
    public int add(user user)
    {
        int id = 0;
        try
        {
            id = userDao.create(user);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }
    /**
     * 更改用户密码
     * @param user
     */
    public int updatePassword(user user)
    {
        int i = 0;
        try {
            i = userDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }
    /** 根据对象id删除某条数据
    * @param user
    */
    public void deleteById(user user){
        DeleteBuilder deleteBuilder = userDao.deleteBuilder();
        try {
            deleteBuilder.where().eq("id",user.getId());
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 查询所有用户(返回List<User>类型)
     * @return List<user>
     */
    public List<user> queryAll()
    {
        List<user> users = new ArrayList<user>();
        try {
            users = userDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    /**
     * 根据用户名查询用户
     * @return List<user>
     * */
    public List<user> queryByName(user user)
    {
        List<user> users = new ArrayList<user>();
        try {
            users = userDao.queryBuilder().where().eq("name",user.getName()) .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
