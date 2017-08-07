package com.tts;

import android.content.Context;
import android.os.Environment;
import com.unisound.client.SpeechConstants;
import com.unisound.client.SpeechSynthesizer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 单例模式封装语音合成管理
 * 集成 云知声语音合成技术
 * http://dev.hivoice.cn/
 * Created by hj on 2017/5/5.
 */
public class TTSOfflineManager {
    static TTSOfflineManager mInstance;
    static Context mContext;
    private SpeechSynthesizer mTTSPlayer;
    private  String mDirPath;
    private String mFrontendModel= "frontend_model";
    private String mBackendModel = "backend_female";

    public static TTSOfflineManager getInstance(Context context){
        mContext = context;
        if(mInstance == null){
            mInstance = new TTSOfflineManager();
        }
        return mInstance;
    };
    private TTSOfflineManager(){
        //复制离线模型文件
        initialEnv();
        // 初始化语音合成对象
        mTTSPlayer = new SpeechSynthesizer(
                mContext, "5qw5pn5qos6izhq5443jos74t5tecxvzke4vcyi3", "ade54024966f1bcedaffe0ade89d410a");
        // 设置本地合成
        mTTSPlayer.setOption(SpeechConstants.TTS_SERVICE_MODE, SpeechConstants.TTS_SERVICE_MODE_LOCAL);
        // 设置前端模型
        mTTSPlayer.setOption(SpeechConstants.TTS_KEY_FRONTEND_MODEL_PATH, mDirPath+"/"+mFrontendModel);
        // 设置后端模型
        mTTSPlayer.setOption(SpeechConstants.TTS_KEY_BACKEND_MODEL_PATH, mDirPath+"/"+mBackendModel);
        // 设置回调监听
        mTTSPlayer.setTTSListener(null);
        // 初始化合成引擎
        mTTSPlayer.init("");
    }


    public void stop() {
        // 主动停止识别
        if (mTTSPlayer != null) {
            mTTSPlayer.stop();
        }
    }

    public void release() {
        // 主动释放离线引擎
        if (mTTSPlayer != null) {
            mTTSPlayer.release(SpeechConstants.TTS_RELEASE_ENGINE, null);
        }
    }

    public void speck(String text) {
        //文字转语音
        if (mTTSPlayer != null) {
            mTTSPlayer.playText(text);
        }
    }

    ///////////////////////复制离线模型至SD卡//////////////////////////////
    private void initialEnv() {
        if (mDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mDirPath = sdcardPath + "/unisound/tts/";
        }
        makeDir(mDirPath);
        copyFromAssetsToSdcard(false, mFrontendModel, mDirPath + "/" + mFrontendModel);
        copyFromAssetsToSdcard(false, mBackendModel, mDirPath + "/" + mBackendModel);
    }
    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    /**
     * 将工程需要的资源文件拷贝到SD卡中使用
     * @param isCover 是否覆盖已存在的目标文件
     * @param source
     * @param dest
     */
    private void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = mContext.getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
