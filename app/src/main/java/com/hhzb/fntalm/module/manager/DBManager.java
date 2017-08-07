package com.hhzb.fntalm.module.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hhzb.fntalm.module.db.DaoMaster;
import com.hhzb.fntalm.module.db.DaoSession;

/**
 * 类名：GreenDaoHelper
 * 类描述：用于数据库实例化的类
 * 创建人：hj
 * 创建日期： 2016/11/28.
 */
public class DBManager {

    private String dataBaseName = "Almirah_db";
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mSession;
    private static DBManager mInstance;

    /**
     * 获取单例引用
     * @return
     */
    public static DBManager getInstance() {
        if(mInstance == null){
            synchronized (DBManager.class) {
                if(mInstance == null){
                    mInstance= new DBManager();
                }
            }
        }
        return mInstance;
    }
    public void init(Context context) {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        db = new DaoMaster.DevOpenHelper(context, dataBaseName, null).getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mSession;
    }
    public SQLiteDatabase getDB() {
        return db;
    }


}
