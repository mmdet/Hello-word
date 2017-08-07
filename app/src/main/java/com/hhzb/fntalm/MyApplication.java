package com.hhzb.fntalm;

/**
 * Created by c on 2017-02-28.
 */
import android.app.Application;

import com.hhzb.fntalm.bean.Admin;
import com.hhzb.fntalm.module.AdminModule;
import com.hhzb.fntalm.module.manager.DBManager;
import com.mmdet.lib.utils.SPUtils;
import com.zxy.recovery.callback.RecoveryCallback;
import com.zxy.recovery.core.Recovery;

import cn.wch.ch37xdriver.CH37xDriver;

public class MyApplication extends Application{
    public static CH37xDriver ch37xdrv;
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库
        DBManager.getInstance().init(this);
        //初始化一个管理员账号
        initAdmin();

        //初始化异常处理
        initCarsh();
    }

    private void initAdmin() {
        Admin admin = new Admin(null,"0000","",0,true,"2017-03-16 13:37:40");
        Admin admins = new Admin(null,"1357924680","",0,true,"2017-03-16 13:37:40");
        AdminModule.getInstance().insertOrReplace(admin);
        AdminModule.getInstance().insertOrReplace(admins);
    }

    private void initCarsh() {
        Recovery.getInstance()
                .debug(true)
                .recoverInBackground(false)
                .recoverStack(true)
                .mainPage(MainActivity.class)
                .recoverEnabled(true)
                .callback(new MyCrashCallback())
                .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
                .skip(MainActivity.class)
                .init(this);
    }
    static final class MyCrashCallback implements RecoveryCallback {
        @Override
        public void stackTrace(String exceptionMessage) {
           // Log.e("zxy", "exceptionMessage:" + exceptionMessage);
        }

        @Override
        public void cause(String cause) {
          ///  Log.e("zxy", "cause:" + cause);
        }

        @Override
        public void exception(String exceptionType, String throwClassName, String throwMethodName, int throwLineNumber) {
           /// Log.e("zxy", "exceptionClassName:" + exceptionType);
            //Log.e("zxy", "throwClassName:" + throwClassName);
           // Log.e("zxy", "throwMethodName:" + throwMethodName);
           // Log.e("zxy", "throwLineNumber:" + throwLineNumber);
        }

        @Override
        public void throwable(Throwable throwable) {

        }
    }
}
