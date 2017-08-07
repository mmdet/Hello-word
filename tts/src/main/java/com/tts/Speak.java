package com.tts;

import android.content.Context;
import android.util.Log;

public class Speak {

    //播放自定义内容
    public static void speck(Context context,String text){
        TTSOfflineManager.getInstance(context).speck(text);
    }

    //播放资源文件设置的内容
    public static String getResStr(Context context,int resId){
        return context.getResources().getString(resId);
    }
}
