package com.hhzb.fntalm.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.hhzb.fntalm.MainActivity;

/**
 * Created by Administrator on 2017/5/17.
 */

public class BootBroadcastReceiver  extends BroadcastReceiver{
    static final String action_boot="android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        //开机自启
        if (intent.getAction().equals(action_boot)){
            Intent StartIntent=new Intent(context,MainActivity.class); //接收到广播后，跳转到MainActivity
            StartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(StartIntent);
        }
    }
}
