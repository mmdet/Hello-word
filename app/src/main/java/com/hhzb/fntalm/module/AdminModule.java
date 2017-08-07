package com.hhzb.fntalm.module;


import com.hhzb.fntalm.bean.Admin;
import com.hhzb.fntalm.bean.BaseModel;
import com.hhzb.fntalm.bean.Cabin;
import com.hhzb.fntalm.module.db.AdminDao;
import com.hhzb.fntalm.module.db.CabinDao;
import com.hhzb.fntalm.module.manager.DBManager;

import java.util.List;

/**
 * Created by c on 2017-02-28.
 */

public class AdminModule {


    private AdminDao mAdminDao;
    private static AdminModule mInstance;

    public static AdminModule getInstance() {
        if (mInstance == null) {
            synchronized (AdminModule.class) {
                if (mInstance == null) {
                    mInstance = new AdminModule();
                }
            }
        }
        return mInstance;
    }

    public AdminModule(){
        mAdminDao = DBManager.getInstance().getDaoSession().getAdminDao();
    }


    /**
     * 新增单条数据
     * @param admin
     */
    public void insertOrReplace(Admin admin){
        mAdminDao.insertOrReplace(admin);
    };


    /**
     * 删除所有数据信息
     */
    public void delete(){
        mAdminDao.deleteAll();
    }

    /**
     * 删除一条数据信息
     */
    public void delete(Admin admin)
    {
        if(admin == null){
            return;
        }
        mAdminDao.delete(admin);
    }

    /**
     * 更改单条数据信息
     * @param admin
     */
    public void update(Admin admin){
        if(admin == null){
            return;
        }
        mAdminDao.update(admin);
    }

    /**
     * 更改多条数据信息
     * @param admins
     */
    public void update(List<Admin> admins){
        if(admins == null || admins.isEmpty()){
            return;
        }
        mAdminDao.updateInTx(admins);
    }



    /**
     * 获取所有数据
     * @return
     */
    public List<Admin> getAll(){
        List<Admin> admins = mAdminDao.queryBuilder().build().list();
        return  admins;
    };


    /**
     * 获取一个登陆口令或者账号
     * @param name
     * @return
     */
    public Admin getCommand(String name){
        return mAdminDao.queryBuilder().where(AdminDao.Properties.Name.eq(name)).build().unique();
    }

}
