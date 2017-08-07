package com.hhzb.fntalm.module;

import com.hhzb.fntalm.bean.Cabin;
import com.hhzb.fntalm.bean.Input;
import com.hhzb.fntalm.module.db.CabinDao;
import com.hhzb.fntalm.module.db.InputDao;
import com.hhzb.fntalm.module.manager.DBManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by c on 2017-02-28.
 */

public class InputMoudle {

    private InputDao mInputDao;
    private static InputMoudle mInstance;

    public static InputMoudle getInstance() {
        if (mInstance == null) {
            synchronized (InputMoudle.class) {
                if (mInstance == null) {
                    mInstance = new InputMoudle();
                }
            }
        }
        return mInstance;
    }

    public InputMoudle (){
        mInputDao = DBManager.getInstance().getDaoSession().getInputDao();
    }

    /**
     * 获取数据表的总条数
     * @return
     */
    public long getCount(){
        return  mInputDao.count();
    };

    /**
     * 新增单条数据
     * @param input
     */
    public void insertOrReplace(Input input){
        mInputDao.insertOrReplace(input);
    };

    /**
     * 新增多条数据
     * @param inputs
     */
    public void insertOrReplace(List<Input> inputs){
        if(inputs == null || inputs.isEmpty()){
            return;
        }
        mInputDao.insertOrReplaceInTx(inputs);
    };


    /**
     * 删除单条数据信息
     * @param input
     */
    public void delete(Input input){
        if(input == null){
            return;
        }
        mInputDao.delete(input);
    }

    /**
     * 根据ID删除单条数据信息
     * @param id
     */
    public void delete(Long id){
        mInputDao.deleteByKey(id);
    }
    /**
     * 根据ID删除多条数据信息
     * @param ids
     */
    public void delete(Long... ids){
        mInputDao.deleteByKeyInTx(ids);
    }
    /**
     * 删除多条数据信息
     * @param inputs
     */
    public void delete(List<Input> inputs){
        if(inputs == null || inputs.isEmpty()){
            return;
        }
        mInputDao.deleteInTx(inputs);
    }
    /**
     * 删除所有数据信息
     */
    public void delete(){
        mInputDao.deleteAll();
    }

    /**
     * 更改单条数据信息
     * @param input
     */
    public void update(Input input){
        if(input == null){
            return;
        }
        mInputDao.update(input);
    }

    /**
     * 更改多条数据信息
     * @param inputs
     */
    public void update(List<Input> inputs){
        if(inputs == null || inputs.isEmpty()){
            return;
        }
        mInputDao.updateInTx(inputs);
    }


    public List<Input> get(){
        List<Input> inputLists = mInputDao.queryBuilder().build().list();
        return  inputLists;
    };

    /**
     * 未上传，未取消，已经送洗
     * 未送洗的单子
     * @param isOut
     * @return
     */
    public List<Input> get(Boolean isOut){
        List<Input> Inputs = mInputDao.queryBuilder()
                .where(
                        InputDao.Properties.IsCancel.eq(false),
                        InputDao.Properties.IsOut.eq(false),
                        InputDao.Properties.IsUpload.eq(false))
                .build().list();
        return Inputs;
    };

    /**
     * 获取员工已经送洗，并且还未上传的衣物列表
     * @return
     */
    public List<Input> getSendCloth(){
        List<Input> Inputs = mInputDao.queryBuilder()
                .where(InputDao.Properties.IsUpload.eq(false),
                        InputDao.Properties.IsOut.eq(true))
                .build().list();
        return Inputs;
    };

    /**
     * 获取未被送洗的单子
     * 未被取走并且未被取消的
     * @param mobile
     * @param isCancel
     * @return
     */
    public List<Input> get(String mobile,Boolean isCancel){
        List<Input> Inputs = mInputDao.queryBuilder()
                .where(
                        InputDao.Properties.Mobile.eq(mobile),
                        InputDao.Properties.IsOut.eq(false),
                        InputDao.Properties.IsCancel.eq(isCancel))
                .build().list();
        return Inputs;
    };


}
