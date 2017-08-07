package com.mmdet.lib.okhttp;



import android.test.suitebuilder.TestMethod;
import android.util.Log;

import com.mmdet.lib.input.Tests;
import com.mmdet.lib.okhttp.cookie.SimpleCookieJar;
import com.mmdet.lib.okhttp.https.HttpsUtils;
import com.mmdet.lib.okhttp.listener.DisposeDataHandle;
import com.mmdet.lib.okhttp.response.CommonFileCallback;
import com.mmdet.lib.okhttp.response.CommonJsonCallback;
import com.mmdet.lib.okhttp.response.JsonUtils;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author qndroid
 * @function 用来发送get, post请求的工具类，包括设置一些请求的共用参数
 */
public class CommonOkHttpClient {
    private static final int TIME_OUT = 60;
    private static OkHttpClient mOkHttpClient;

    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        /**
         *  为所有请求添加请求头，看个人需求
         */
//        okHttpClientBuilder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request()
//                        .newBuilder()
//                        .addHeader("Accept", "application/json") // 标明发送本次请求的客户端
//                        .addHeader("Content-Type", "application/json") // 标明发送本次请求的客户端
//                        .addHeader("User-Agent", "Imooc-Mobile") // 标明发送本次请求的客户端
//                        .build();
//                return chain.proceed(request);
//            }
//        });
        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.followRedirects(true);
        /**
         * trust all the https point
         */
        okHttpClientBuilder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager());
        mOkHttpClient = okHttpClientBuilder.build();
    }

    public static OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

//    /**
//     * 指定cilent信任指定证书
//     *
//     * @param certificates
//     */
//    public static void setCertificates(InputStream... certificates) {
//        mOkHttpClient.newBuilder().sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null)).build();
//    }

    /**
     * 通过构造好的Request,Callback去发送请求
     *
     * @param request
     */
    public static Call get(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    public static Call post(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallback(handle));
        return call;
    }

    public static Call downloadFile(Request request, DisposeDataHandle handle) {
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new CommonFileCallback(handle));
        return call;
    }

    public static void post(String url,String json,DisposeDataHandle handle) {
        MediaType JSON= MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Accept", "application/json") // 标明发送本次请求的客户端
                .addHeader("Content-Type", "application/json") // 标明发送本次请求的客户端
                .addHeader("User-Agent", "Imooc-Mobile") // 标明发送本次请求的客户端
                .build();
        //发送请求获取响应
        mOkHttpClient.newCall(request).enqueue(new CommonJsonCallback(handle));
    }
    public static void postBytes(String url,byte[] param,DisposeDataHandle handle) {

        MediaType stream= MediaType.parse("application/x-www-form-urlencoded;charset=GB2312");
        RequestBody requestBody = RequestBody.create(stream, param);

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Accept", "application/x-www-form-urlencoded;charset=GB2312") // 标明发送本次请求的客户端
                .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=GB2312") // 标明发送本次请求的客户端
                .addHeader("User-Agent", "Imooc-Mobile") // 标明发送本次请求的客户端
                .build();
        //发送请求获取响应
        mOkHttpClient.newCall(request).enqueue(new CommonJsonCallback(handle));
    }

    /**
     * 直接get请求
     * @param url
     * @param handle
     */
    public static void get(String url,DisposeDataHandle handle) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        mOkHttpClient.newCall(request).enqueue(new CommonJsonCallback(handle));
    }

}