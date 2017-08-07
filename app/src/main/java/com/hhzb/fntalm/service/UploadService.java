package com.hhzb.fntalm.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hhzb.fntalm.bean.BackPut;
import com.hhzb.fntalm.bean.Input;
import com.hhzb.fntalm.module.BackputMoudle;
import com.hhzb.fntalm.module.InputMoudle;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.fntalm.utils.TimeUtils;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;
import com.mmdet.lib.okhttp.response.JsonUtils;
import com.mmdet.lib.utils.NetworkUtils;
import com.mmdet.lib.utils.ToastUtils;

import java.util.Date;
import java.util.List;

/**
 * 后台上传数据服务
 * Created by c on 2016-12-15.
 */
public class UploadService extends Service{
    List<Input> Inputs;
    List<BackPut> BackPuts;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new PollingThread().start();
        return super.onStartCommand(intent, flags, startId);
    }

    private synchronized void upload() {
        if(!NetworkUtils.isConnected(this)){
            return;
        }
        /**
         * 上传员工送洗信息
         */
        Inputs = InputMoudle.getInstance().getSendCloth();
        if(!Inputs.isEmpty()){
            RequestCenter.uploadInputList(this,JsonUtils.BeanToJson(Inputs), new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    if(responseObj.toString().equals("true")){
                        for(Input input:Inputs){
                            input.setIsUpload(true);
                            input.setOutDate(TimeUtils.date2String(new Date()));
                        }
                        InputMoudle.getInstance().insertOrReplace(Inputs);
                        Inputs.clear();
                    }
                }
                @Override
                public void onFailure(Object reasonObj) {

                }
            });
        }

        /**
         * 上传 客户取走信息
         */
       BackPuts = BackputMoudle.getInstance().getOutPutCloths();
        Log.i("BackPut数量",BackPuts.size()+"");
        if(!BackPuts.isEmpty()){
            RequestCenter.backputList(this,JsonUtils.BeanToJson(BackPuts), new DisposeDataListener() {
                @Override
                public void onSuccess(Object responseObj) {
                    Log.i("取衣上传结果",responseObj.toString());
                    if(responseObj.toString().equals("true")){
                        for(BackPut backput:BackPuts){
                            backput.setIsUpload(true);
                            backput.setOutDate(TimeUtils.date2String(new Date()));
                        }
                        BackputMoudle.getInstance().insertOrReplace(BackPuts);
                        BackPuts.clear();
                    }
                }
                @Override
                public void onFailure(Object reasonObj) {

                }
            });
        }
    }

    class PollingThread extends Thread {
        @Override
        public void run()
        {
            /**
             * 开启一个线程进行事物操作
             */
            upload();
        }
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        stopSelf();
    }
}
