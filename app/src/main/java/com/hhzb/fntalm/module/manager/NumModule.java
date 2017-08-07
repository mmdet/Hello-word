package com.hhzb.fntalm.module.manager;


import com.hhzb.fntalm.bean.Num;
import com.hhzb.fntalm.module.db.NumDao;

/**
 * Created by c on 2017-02-28.
 */

public class NumModule {


    private NumDao mNumDao;
    private static NumModule mInstance;

    public static NumModule getInstance() {
        if (mInstance == null) {
            synchronized (NumModule.class) {
                if (mInstance == null) {
                    mInstance = new NumModule();
                }
            }
        }
        return mInstance;
    }

    public NumModule(){
        mNumDao = DBManager.getInstance().getDaoSession().getNumDao();
    }



    /**
     * 新增单条数据
     * @param num
     */
    public void insert(Num num){
       mNumDao.insert(num);
    };

    public Num getLastData(){
        long rowId = mNumDao.count();
        return mNumDao.loadByRowId(rowId);
    };

}
