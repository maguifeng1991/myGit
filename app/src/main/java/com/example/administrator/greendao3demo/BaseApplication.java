package com.example.administrator.greendao3demo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.greendao3demo.gen.DaoMaster;
import com.example.administrator.greendao3demo.gen.DaoSession;


/**
 * Created by Administrator on 2017/3/31.
 */

public class BaseApplication extends Application {
    public DaoSession daoSession;

    public SQLiteDatabase basedb;

    public DaoMaster.DevOpenHelper helper;

    public DaoMaster daoMaster;
    @Override
    public void onCreate() {
        super.onCreate();
        setupDatanase();
    }

    private void setupDatanase() {
        helper = new DaoMaster.DevOpenHelper(this,Constants.DB_NAME,null);
        basedb = helper.getWritableDatabase();
        daoMaster = new DaoMaster(basedb);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {

        return daoSession;

    }

    public SQLiteDatabase getDb() {

        return basedb;

    }
}
