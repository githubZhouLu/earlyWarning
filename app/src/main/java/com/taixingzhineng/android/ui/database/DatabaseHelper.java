package com.taixingzhineng.android.ui.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.taixingzhineng.android.ui.model.earlyWarning;
import com.taixingzhineng.android.ui.model.user;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/23.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TABLE_NAME = "EarlyWarning.db";
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    private DatabaseHelper(Context context)
    {
        super(context, TABLE_NAME, null, 2);
    }
    /**
     * 创建表，直接使用ormlite提供的TableUtils.createTable(connectionSource, User.class);进行创建
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource)
    {
        try
        {
            TableUtils.createTable(connectionSource, user.class);
            TableUtils.createTable(connectionSource, earlyWarning.class);
            //TableUtils.createTable(connectionSource, Student.class);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * 更新表，使用ormlite提供的TableUtils.dropTable(connectionSource, User.class, true);进行删除操作~
     * 删除完成后，别忘了，创建操作：onCreate(database, connectionSource);
    */
    @Override
    public void onUpgrade(SQLiteDatabase database,
                          ConnectionSource connectionSource, int oldVersion, int newVersion)
    {
        try
        {
            TableUtils.dropTable(connectionSource, user.class, true);
            TableUtils.dropTable(connectionSource, earlyWarning.class, true);
            //TableUtils.dropTable(connectionSource, Student.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    private static DatabaseHelper instance;
    public static synchronized DatabaseHelper getHelper(Context context)
    {
        if (instance == null)
        {
            synchronized (DatabaseHelper.class)
            {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }

        return instance;
    }

    public synchronized Dao getDao(Class clazz) throws SQLException
    {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className))
        {
            dao = daos.get(className);
        }
        if (dao == null)
        {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }
    /**
     * 释放资源
     */
    @Override
    public void close()
    {
        super.close();
        for (String key : daos.keySet())
        {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
