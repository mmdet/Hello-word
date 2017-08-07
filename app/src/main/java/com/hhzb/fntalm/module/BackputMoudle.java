package com.hhzb.fntalm.module;

import com.hhzb.fntalm.bean.BackPut;
import com.hhzb.fntalm.module.db.BackPutDao;
import com.hhzb.fntalm.module.db.InputDao;
import com.hhzb.fntalm.module.manager.DBManager;

import java.util.List;

/**
 * Created by c on 2017-02-28.
 */

public class BackputMoudle {

    private BackPutDao mBackputDao;
    private static BackputMoudle mInstance;

    public static BackputMoudle getInstance() {
        if (mInstance == null) {
            synchronized (BackputMoudle.class) {
                if (mInstance == null) {
                    mInstance = new BackputMoudle();
                }
            }
        }
        return mInstance;
    }

    public BackputMoudle(){
        mBackputDao = DBManager.getInstance().getDaoSession().getBackPutDao();
    }

    /**
     * 获取数据表的总条数
     * @return
     */
    public long getCount(){
        return  mBackputDao.count();
    };

    /**
     * 新增单条数据
     * @param backPut
     */
    public void insertOrReplace(BackPut backPut){
        mBackputDao.insertOrReplace(backPut);
    };

    /**
     * 新增多条数据
     * @param backPuts
     */
    public void insertOrReplace(List<BackPut> backPuts){
        if(backPuts == null || backPuts.isEmpty()){
            return;
        }
        mBackputDao.insertOrReplaceInTx(backPuts);
    };


    /**
     * 删除单条数据信息
     * @param backPut
     */
    public void delete(BackPut backPut){
        if(backPut == null){
            return;
        }
        mBackputDao.delete(backPut);
    }

    /**
     * 根据ID删除单条数据信息
     * @param id
     */
    public void delete(Long id){
        mBackputDao.deleteByKey(id);
    }
    /**
     * 根据ID删除多条数据信息
     * @param ids
     */
    public void delete(Long... ids){
        mBackputDao.deleteByKeyInTx(ids);
    }
    /**
     * 删除多条数据信息
     * @param inputs
     */
    public void delete(List<BackPut> inputs){
        if(inputs == null || inputs.isEmpty()){
            return;
        }
        mBackputDao.deleteInTx(inputs);
    }
    /**
     * 删除所有数据信息
     */
    public void delete(){
        mBackputDao.deleteAll();
    }

    /**
     * 更改单条数据信息
     * @param input
     */
    public void update(BackPut input){
        if(input == null){
            return;
        }
        mBackputDao.update(input);
    }

    /**
     * 更改多条数据信息
     * @param inputs
     */
    public void update(List<BackPut> inputs){
        if(inputs == null || inputs.isEmpty()){
            return;
        }
        mBackputDao.updateInTx(inputs);
    }


    public List<BackPut> get(){
        List<BackPut> inputLists = mBackputDao.queryBuilder().build().list();
        return  inputLists;
    };


    /**
     * 获取未取走的衣物
     * @param mobile
     * @param isOut
     * @return
     */

    public List<BackPut> get(String mobile,Boolean isOut){
        List<BackPut> backPuts = mBackputDao.queryBuilder()
                .where(BackPutDao.Properties.Mobile.eq(mobile),BackPutDao.Properties.IsOut.eq(isOut),
                        BackPutDao.Properties.IsBackShop.eq(false))
                .build().list();
        return backPuts;
    };

    /**
     * 获取用户已经取走，没有上传的衣物列表
     * @return
     */
    public List<BackPut> getOutPutCloths(){
        List<BackPut> backPuts = mBackputDao.queryBuilder()
                .where(BackPutDao.Properties.IsOut.eq(true),
                        BackPutDao.Properties.IsUpload.eq(false))
                .build().list();
        return backPuts;
    };

    /**
     * 获取需要返店的衣物列表
     * @return
     */
    public List<BackPut> getBackShopCloths(){
        List<BackPut> backPuts = mBackputDao.queryBuilder()
                .where(BackPutDao.Properties.IsOut.eq(false),BackPutDao.Properties.IsBackShop.eq(false))
                .build().list();
        return backPuts;
    };

}
