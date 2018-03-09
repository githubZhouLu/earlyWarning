package com.taixingzhineng.android.ui.database;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.taixingzhineng.android.ui.model.earlyWarning;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.taixingzhineng.android.ui.database.DatabaseHelper.getHelper;

/**
 * Created by Administrator on 2017/12/25.
 */

public class earlyWarningDao {
    private Context context;
    private Dao<earlyWarning, Integer> earlyWarningDao;
    private DatabaseHelper databaseHelper;

    public earlyWarningDao(Context context)
    {
        this.context = context;
        getEarlyWarningDao(context);
    }
    /**
     * 获得earlyWarningDao
     */
    private void getEarlyWarningDao(Context context){
        try {
            databaseHelper = getHelper(context);
            earlyWarningDao = databaseHelper.getDao(earlyWarning.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * 查询所有未结束预警(返回List<earlyWarning>类型)
     * @return List<earlyWarning>
     */
    public List<earlyWarning> queryAllNotFinished()
    {
        List<earlyWarning> earlyWarnings = new ArrayList<earlyWarning>();
        try {
            earlyWarnings = earlyWarningDao.queryBuilder().where().ne("STATE","已结束").query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return earlyWarnings;
    }
    /**
     * 查询所有预警(返回List<earlyWarning>类型)
     * @return List<earlyWarning>
     */
    public List<earlyWarning> queryAll()
    {
        List<earlyWarning> earlyWarnings = new ArrayList<earlyWarning>();
        try {
            earlyWarnings = earlyWarningDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return earlyWarnings;
    }
    /**
     * 根据id查询预警
     * @return List<earlyWarning>
     */
    /*public List<earlyWarning> queryById(earlyWarning earlyWarning)
    {
        List<earlyWarning> earlyWarnings = new ArrayList<earlyWarning>();
        try {
            earlyWarnings = earlyWarningDao.queryBuilder().where().eq("ID",earlyWarning.getID()).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return earlyWarnings;
    }*/
    /**
     *新增预警
     * @return int 新增成功的条数
     */
    public int add(earlyWarning earlyWarning)
    {
        int num = 0;
        try
        {
            num = earlyWarningDao.create(earlyWarning);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return num;
    }
}
