package com.hhzb.fntalm.fargment;

import android.os.Bundle;
import com.hhzb.fntalm.service.timer.TimerManager;
import com.hhzb.fntalm.service.timer.TimerObserver;
import com.hhzb.fntalm.utils.CutDownTimer;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 实现TimerObserver类成为观察者
 * Fragment基类
 * Created by c on 2017-02-21.
 */

public  class BaseFragment extends SupportFragment implements TimerObserver{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        TimerManager.getmInstace().Attach(this);
        CutDownTimer.getmInstance().start(new CutDownTimer.ITimeListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                //给观察者发送通知
                TimerManager.getmInstace().notifyObserver(millisUntilFinished);
            }
            @Override
            public void onFinish() {
                //结束通知，没有添加。默认为跳转到首页
                start(BannerFragment.newInstance(),SupportFragment.SINGLETASK);
            }
        });
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        TimerManager.getmInstace().remove(this);
    }

}
