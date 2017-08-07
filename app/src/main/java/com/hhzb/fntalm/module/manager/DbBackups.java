package com.hhzb.fntalm.module.manager;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.mmdet.lib.utils.AppUtils;
import com.mmdet.lib.utils.FileUtils;
import com.mmdet.lib.utils.SDCardUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

public class DbBackups {


    /**
     * 系统文件路径/data/data...
     */
    private String APP_PATH;

    /**
     * 配置文件路径
     */
    private String SHARED_PREFS;
    /**
     * 数据库路径
     */
    private String DATABASES;
    private Context mContext;

    /**
     * 备份的data路径 /mnt/sdcard/data..
     */
    private String BACKUP_PATH;
    /**
     * 备份的数据库路径
     */
    private String BACKUP_DATABASES;
    /**
     * 备份的配置文件名
     */
    private String BACKUP_SHARED_PREFS;

    private DbBackups mInstance;
    public DbBackups(Context context) {
        mContext = context;
        APP_PATH = new StringBuilder("/data/data/").append(AppUtils.getAppPackageName(mContext)).toString();

        SHARED_PREFS = APP_PATH + "/shared_prefs";
        DATABASES = APP_PATH + "/databases";


        BACKUP_PATH = SDCardUtils.getDataPath()+"fntalm";

        BACKUP_DATABASES = BACKUP_PATH + "/database";
        BACKUP_SHARED_PREFS = BACKUP_PATH + "/shared_prefs";
    }


    /**
     * 备份文件
     *
     * @return 当且仅当数据库及配置文件都备份成功时返回true。
     */
    public boolean doBackup() {
        return backupDB() && backupSharePrefs();
    }

    private boolean backupDB() {
        return copyDir(DATABASES, BACKUP_DATABASES, "备份数据库文件成功:"
                + BACKUP_DATABASES, "备份数据库文件失败");
    }

    private boolean backupSharePrefs() {
        return copyDir(SHARED_PREFS, BACKUP_SHARED_PREFS, "备份系统配置文件成功:"
                + BACKUP_SHARED_PREFS, "备份配置文件失败");
    }

    /**
     * 恢复
     *
     * @return 当且仅当数据库及配置文件都恢复成功时返回true。
     */
    public boolean doRestore() {
        return restoreDB() && restoreSharePrefs();
    }

    private boolean restoreDB() {
        return copyDir(BACKUP_DATABASES, DATABASES, "恢复数据库文件成功", "恢复数据库文件失败");
    }

    private boolean restoreSharePrefs() {
        return copyDir(BACKUP_SHARED_PREFS, SHARED_PREFS, "恢复配置文件成功",
                "恢复配置文件失败");
    }

    private final void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 复制目录
     *
     * @param srcDir
     *            源目录
     * @param destDir
     *            目标目录
     * @param successMsg
     *            复制成功的提示语
     * @param failedMsg
     *            复制失败的提示语
     * @return 当复制成功时返回true, 否则返回false。
     */
    private final boolean copyDir(String srcDir, String destDir,
                                  String successMsg, String failedMsg) {
        try {
            Boolean isSuccess = FileUtils.copyDir(new File(srcDir), new File(destDir));
            if(isSuccess == true){
                showToast(successMsg);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            showToast(failedMsg);
            return false;
        }
        showToast(failedMsg);
        return false;
    }
}
