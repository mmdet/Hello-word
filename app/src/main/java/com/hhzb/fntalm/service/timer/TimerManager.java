package com.hhzb.fntalm.service.timer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/24.
 */

public class TimerManager implements TimerSubject {


    private static TimerManager mInstace;
    private  List<TimerObserver> timerObserverList;

    public static TimerManager getmInstace(){
        if(mInstace == null){
            mInstace = new TimerManager();
        }
        return mInstace;
    }

    private TimerManager(){
        // 存放观察者
        timerObserverList = new ArrayList<TimerObserver>();
    }


    @Override
    public void Attach(TimerObserver observer) {
        if(timerObserverList == null){
            timerObserverList = new ArrayList<TimerObserver>();
        }
        timerObserverList.add(observer);
    }

    @Override
    public void remove(TimerObserver observer) {

        if(timerObserverList != null){
            timerObserverList.remove(observer);
        }
    }

    /**
     * 向所有的观察者发送通知：网络状态发生改变咯...
     */
    @Override
    public void notifyObserver(long millisUntilFinished) {
        if(timerObserverList !=null && timerObserverList.size() >0){
            for(TimerObserver observer : timerObserverList){
                if(observer != null){
                    observer.onTick(millisUntilFinished);
                }
            }
        }
    }


}
