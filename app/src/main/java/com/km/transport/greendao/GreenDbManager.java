package com.km.transport.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by PengSong on 17/9/18.
 */

public class GreenDbManager {

    private static final String DATE_BASE_NAME = "transport_db";
    private DaoSession mDaoSession;

    private GreenDbManager(){

    }

    private static GreenDbManager mInstance;
    public static GreenDbManager getInstances(){
        if (mInstance == null){
            synchronized (GreenDbManager.class){
                if (mInstance == null){
                    mInstance = new GreenDbManager();
                }
            }
        }
        return mInstance;
    }
    /**
     * 初始化数据库相关
     * */
    public void initDbHelp(Context mContext) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, DATE_BASE_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
