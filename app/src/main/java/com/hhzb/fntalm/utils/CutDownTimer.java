package com.hhzb.fntalm.utils;

import android.util.Log;
import com.hhzb.fntalm.fargment.CommonData;

/**
 * 单例全局倒计时类
 * 控制页面的倒计时
 * Created by c on 2016-12-23.
 */
public class CutDownTimer {

    private static CutDownTimer mInstance;

    public static CutDownTimer getmInstance() {
        if(mInstance ==null){
            synchronized (CountDownTimer.class){
                if(mInstance ==null){
                    mInstance = new CutDownTimer();
                }
            }
        }
        return mInstance;
    }

    private MyCountDownTimer timer;

    /**
     * 开始倒计时
     * @param iTimeListener
     */
    public synchronized void start(ITimeListener iTimeListener){
        if(timer == null){
            timer =  new MyCountDownTimer(CommonData.totaltimes, CommonData.space);
            timer.setiTimeListener(iTimeListener);
            timer.start();
        }else{
            timer.cancel();
            timer.start();
        }
    }

    /**
     * 倒计时取消、停止
     */
    public void stop(){
        if(timer !=null){
            timer.cancel();
        }
    }

    /**
     * 倒计时重新开始
     */
    public void restart(){
        if(timer !=null){
            timer.cancel();
            timer.start();
        }else{

        }
    }

    /**
     * 倒计时监听回调接口
     */
    public interface ITimeListener {
        public void onTick(long millisUntilFinished);
        public void onFinish();
    }

    /**
     * 重写的CountDownTimer类
     * 接受一个回调接口
     */
    class MyCountDownTimer extends CountDownTimer{
        private ITimeListener iTimeListener;
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            if(iTimeListener != null){
                iTimeListener.onTick(millisUntilFinished);
            }
        }

        @Override
        public void onFinish() {
            if(iTimeListener != null) {
                iTimeListener.onFinish();
            }
        }

        private void setiTimeListener(ITimeListener iTimeListener) {
            this.iTimeListener = iTimeListener;
        }
    }


}
