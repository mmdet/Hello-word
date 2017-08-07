package com.hhzb.fntalm.service.timer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者
 * Created by c on 2016-11-28.
 */
public interface TimerSubject {

    /**
     * 增加观察者
     */
    public  void Attach(TimerObserver observer);


    /**
     * 删除观察者
     */
    public void remove(TimerObserver observer);


    /**
     * 通知
     */
    public void notifyObserver(long millisUntilFinished);

}
