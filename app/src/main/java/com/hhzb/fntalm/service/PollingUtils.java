package com.hhzb.fntalm.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * 后台轮询任务管理
 * Created by Administrator on 2017/3/10 0010.
 */

public class PollingUtils {

    //开启轮询服务
    public static void startPollingService(Context context, int seconds, Class<?> cls) {
        //获取AlarmManager系统服务
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //包装需要执行Service的Intent
        Intent intent = new Intent(context, cls);
        intent.setAction("sssssss.ssss");
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //触发服务的起始时间
        long triggerAtTime = SystemClock.elapsedRealtime();
        //使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME, triggerAtTime, 60 * 1000, pendingIntent);
    }
    //停止轮询服务
    public static void stopPollingService(Context context, Class<?> cls) {
        AlarmManager manager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction("sssssss.ssss");
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //取消正在执行的服务
        manager.cancel(pendingIntent);
        Log.i("mm","stopPollingService");
    }
}
