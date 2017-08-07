package com.mmdet.lib.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by c on 2017-02-27.
 */

public class ImageLoader {

    //Context支持 Activity Context Fragment FragmentActivity中四种类型

    /**
     * 加载网络静态图片或者GIF动态图片
     * @param context
     * @param url
     * @param imageView
     */
    public static void displayImage(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

    /**
     * 加载资源文件
     * @param context
     * @param resID
     * @param imageView
     */
    public static void displayImage(Context context, int resID, ImageView imageView){
        Glide.with(context).load(resID).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }
    public static void displayGifImage(Context context, int resID, ImageView imageView){
        Glide.with(context).load(resID).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
    }

}
