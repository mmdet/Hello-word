package com.hhzb.fntalm.service.timer;

/**
 * 观察者
 * Created by c on 2016-11-28.
 */
public interface TimerObserver {
    /**
     * 倒计时每秒回调
     */
    public void onTick(long millisUntilFinished);


    /**
     * 倒计时结束回调
     */
    public void onFinish();

}
