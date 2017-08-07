package com.hhzb.fntalm.fargment;

import android.content.Context;

import com.hhzb.fntalm.fargment.admin.sendwash.SendWashFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class Fg {


    /**
     * 启动fargment,SINGLETASK模式
     * @param supportFragment
     * @param toFragment
     */
    public static void startSingleTask(SupportFragment supportFragment, final SupportFragment toFragment){
        supportFragment.start(toFragment,SupportFragment.SINGLETASK);
    }
}
