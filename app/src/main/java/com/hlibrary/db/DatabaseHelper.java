package com.hlibrary.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private List<Class> clses;
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    public DatabaseHelper(Context context, String databaseName, int databaseVersion,
                          List<Class> clses) {
        super(context, databaseName, null, databaseVersion);
        this.clses = clses;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            for (Class cls : clses) {
                TableUtils.createTable(connectionSource, cls);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            for (Class cls : clses) {
                TableUtils.dropTable(connectionSource, cls, true);
            }
            onCreate(database, connectionSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
        daos.clear();
    }
}
