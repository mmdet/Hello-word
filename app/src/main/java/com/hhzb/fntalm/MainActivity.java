package com.hhzb.fntalm;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hhzb.fntalm.fargment.BannerFragment;
import com.hhzb.fntalm.fargment.TestFragment;
import com.hhzb.fntalm.fargment.home.output.PayShitiCard;
import com.hhzb.fntalm.service.PollingUtils;
import com.hhzb.fntalm.service.UploadService;
import com.hhzb.serialport.SerialDataUtils;
import com.hhzb.serialport.SerialPortUtil;
import com.mmdet.lib.utils.ToastUtils;

import org.lynxz.zzplayerlibrary.controller.IPlayerImpl;
import org.lynxz.zzplayerlibrary.widget.VideoPlayer;

public class MainActivity extends BaseActivity {

    VideoPlayer mVp;

    TextView test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            //loadRootFragment(R.id.fl_content, BannerFragment.newInstance());  // 加载根Fragment
            loadRootFragment(R.id.fl_content, PayShitiCard.newInstance());  // 加载根Fragment
        }

        PollingUtils.startPollingService(this, 60, UploadService.class);
        mVp = (VideoPlayer) findViewById(R.id.vp);
        mVp.loadAndStartVideo(this, Environment.getExternalStorageDirectory() + "/fnt/ad.mp4"); // 设置视频播放路径并开始播放
        mVp.setPlayerController(new IPlayerImpl() {
            @Override
            public void onComplete() {
                mVp.loadAndStartVideo(MainActivity.this, Environment.getExternalStorageDirectory() + "/ad.mp4");
            }
        });
    }

    @Override
    protected void onDestroy() {
        PollingUtils.stopPollingService(this, UploadService.class);
        super.onDestroy();
    }

}