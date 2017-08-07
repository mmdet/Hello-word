package com.hhzb.fntalm.module;


import com.hhzb.fntalm.bean.Cabin;
import com.hhzb.fntalm.bean.Input;
import com.hhzb.fntalm.module.db.CabinDao;
import com.hhzb.fntalm.module.manager.DBManager;

import java.util.List;

/**
 * Created by c on 2017-02-28.
 */

public class CabinModule {


    private CabinDao mCabinDao;
    private static CabinModule mInstance;

    public static CabinModule getInstance() {
        if (mInstance == null) {
            synchronized (CabinModule.class) {
                if (mInstance == null) {
                    mInstance = new CabinModule();
                }
            }
        }
        return mInstance;
    }

    public CabinModule (){
        mCabinDao = DBManager.getInstance().getDaoSession().getCabinDao();
    }

    /**
     * 获取数据表的总条数
     * @return
     */
    public long getCount(){
        return  mCabinDao.count();
    };

    /**
     * 新增单条数据
     * @param cabin
     */
    public void insertOrReplace(Cabin cabin){
        mCabinDao.insertOrReplace(cabin);
    };

    /**
     * 新增多条数据
     * @param cabins
     */
    public void insertOrReplace(List<Cabin> cabins){
        if(cabins == null || cabins.isEmpty()){
            return;
        }
        mCabinDao.insertOrReplaceInTx(cabins);
    };
    /**
     * 删除所有数据信息
     */
    public void delete(){
        mCabinDao.deleteAll();
    }

    /**
     * 更改单条数据信息
     * @param cabin
     */
    public void update(Cabin cabin){
        if(cabin == null){
            return;
        }
        mCabinDao.update(cabin);
    }

    /**
     * 更改多条数据信息
     * @param cabins
     */
    public void update(List<Cabin> cabins){
        if(cabins == null || cabins.isEmpty()){
            return;
        }
        mCabinDao.updateInTx(cabins);
    }

    /**
     * 获取一个没有被占用的柜子的柜号
     * @type表示柜子类型（叠式，还是挂式）
     * @return
     */
    public Cabin getNoUsedCabin(int type){
        Cabin cabin = mCabinDao.queryBuilder()
                .where(CabinDao.Properties.IsUsed.eq(false),CabinDao.Properties.CabinType.eq(type)).limit(1).unique();
        if(cabin == null)
            return  null;
        return cabin;
    };

    /**
     * 根据柜号找到一个柜子
     * @return
     */
    public Cabin getOneCabin(int cabinNo){
        Cabin cabin = mCabinDao.queryBuilder()
                .where(CabinDao.Properties.CabinNo.eq(cabinNo)).unique();
        if(cabin == null)
            return  null;
        return cabin;
    };

    /**
     * 获取所有数据
     * @return
     */
    public List<Cabin> getAll(){
        List<Cabin> cabinLists = mCabinDao.queryBuilder().build().list();
        return  cabinLists;
    };

    /**
     * 设置柜子的使用状态
     * @param cabin
     * @param used
     */
    public void setCabinUsed(Cabin cabin,boolean used){
        cabin.setIsUsed(used);
        update(cabin);
    }
    /**
     * 根据柜门号设置柜子的使用状态
     * @param cabinetNo
     * @param used
     */
    public void setCabinUsed(int cabinetNo,boolean used){
        Cabin cabin = mCabinDao.queryBuilder().where(CabinDao.Properties.CabinNo.eq(cabinetNo)).unique();
        if(cabin != null) {
            cabin.setIsUsed(used);
            update(cabin);
        }
    }

}
