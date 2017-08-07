package com.hhzb.fntalm.network.http;

import android.content.Context;
import android.util.Log;

import com.hhzb.fntalm.fargment.CommonData;
import com.hhzb.fntalm.fargment.CommonFun;
import com.hhzb.fntalm.utils.Des3;
import com.hhzb.fntalm.utils.SignUtils;
import com.mmdet.lib.okhttp.CommonOkHttpClient;
import com.mmdet.lib.okhttp.https.HttpsUtils;
import com.mmdet.lib.okhttp.listener.DisposeDataHandle;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;
import com.mmdet.lib.okhttp.request.CommonRequest;
import com.mmdet.lib.okhttp.request.RequestParams;
import com.mmdet.lib.utils.EncodeUtils;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by c on 2017-02-27.
 */

public class RequestCenter {

    //post params
    public static void postRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, params), new DisposeDataHandle(listener, clazz));
    }
    //post  json
    public static void postRequest(String url, String json, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.post(url, json, new DisposeDataHandle(listener, clazz));
    }
    //post  bytes
    public static void postBytes(String url, byte[] bytes, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.postBytes(url, bytes, new DisposeDataHandle(listener, clazz));
    }
    //get
    public static void getRequest(String url,DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.get(url, new DisposeDataHandle(listener, clazz));
    }

    /**
     * 上传存衣
     * @param parm
     * @param listener
     */
    public static void uploadInputs(Context context,String parm,DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.CLOTH_INPUT(context),parm,listener, null);
    }
    /**
     * 批量上传存衣
     * @param parm
     * @param listener
     */
    public static void uploadInputList(Context context,String parm,DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.CLOTH_INPUT(context),parm,listener, null);
    }
    /**
     * 返店
     * @param parm
     * @param listener
     */
    public static void clothBackShop(Context context,String parm,DisposeDataListener listener) {
        Log.i("res",parm);
        RequestCenter.postRequest(HttpConstants.CLOTH_BACKSHOP(context),parm,listener, null);
    }
    /**
     * 批量上传取衣信息
     * @param parm
     * @param listener
     */
    public static void backputList(Context context,String parm,DisposeDataListener listener) {

        RequestCenter.postRequest(HttpConstants.CLOTH_BACKPUT(context),parm,listener, null);
    }
    /**
     * 获取手机验证码（总部接口）
     * @param context
     * @param mobile
     * @param listener
     */
    public static void getCaptchas(Context context, String mobile, DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("shopID",new Des3().encode(HttpConstants.shopId(context)));
        params.put("mobile", mobile);
        params.put("token", HttpConstants.Token);
        RequestCenter.postRequest(HttpConstants.CAPTCHAS_GET(context), params, listener, null);
    }

    /**
     * 验证手机验证码
     * @param mobile
     * @param captcha
     * @param listener
     */
    public static void checkCaptchas(Context context,String mobile,String captcha,DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("captcha",captcha);
        params.put("token", HttpConstants.Token);
        RequestCenter.postRequest(HttpConstants.CAPTCHAS_CHECK(context),params,listener, null);
    }


    /**
     * 获取订单信息
     * @param parm
     * @param listener
     */
    public static void clothInfos(Context context,String parm,DisposeDataListener listener) {
        RequestCenter.getRequest(HttpConstants.CLOTH_INFOS(context)+parm,  listener, null);
    }


    /**
     * 支付码获取
     * @param listener
     */
    public static void payCode(Context context,int PayType,String shopId,String billNo,String money,DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("shopID",shopId);
        params.put("billNo", billNo);
        params.put("amount", money);
        params.put("comment", billNo);
        params.put("token", HttpConstants.Token);
        if(PayType == CommonData.PAY_TYPE_WEIXIN){
            RequestCenter.postRequest(HttpConstants.PAY_CODE_WEIXIN(context), params,listener, null);
        }else if(PayType == CommonData.PAY_TYPE_ZHIFUBAO){
            RequestCenter.postRequest(HttpConstants.PAY_CODE_ZHIFUBAO(context), params,listener, null);
        }
    }

    /**
     * 支付结果获取
     * @param listener
     */
    public static void payResult(Context context,int PayType,String shopId,String billNo,DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("shopID",shopId);
        params.put("billNo", billNo);
        params.put("token", HttpConstants.Token);
        if(PayType == CommonData.PAY_TYPE_WEIXIN) {
            RequestCenter.postRequest(HttpConstants.PAY_STATE_WEIXIN(context), params, listener, null);
        }else if(PayType == CommonData.PAY_TYPE_ZHIFUBAO){
            RequestCenter.postRequest(HttpConstants.PAY_STATE_ZHIFUBAO(context), params, listener, null);
        }

    }

    /**
     * 线上卡列表
     * @param context
     * @param mobile
     * @param listener
     */
    public static void cardGet(Context context,String mobile,DisposeDataListener listener){
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("token", HttpConstants.Token);
        RequestCenter.postRequest(HttpConstants.CARD_GET(context),params,listener,null);
    }

    /**
     * 线上卡列表
     * @param context
     * @param shopId
     * @param listener
     */
    public static void cardTypeGet(Context context,String shopId,DisposeDataListener listener){
        RequestParams params = new RequestParams();
        params.put("shopID", shopId);
        params.put("token", HttpConstants.Token);
        RequestCenter.postRequest(HttpConstants.CARD_TYPE_GET(context),params,listener,null);
    }

    /**
     * 线上卡充值
     * @param context
     * @param shopId
     * @param listener
     */
    public static void cardRecharge(Context context,String shopId,String value,
                                    String cardNo,String cardType,String faceValue,
                                    String rebate,String freeMoney,String validMonths,DisposeDataListener listener){
        //account/sign
        Map<String, String> map_sig = new HashMap<String,String>();
        map_sig.put("shopID", UrlEncode(new Des3().encode(shopId)));
        map_sig.put("cardNo", cardNo);
        map_sig.put("cardType", cardType);
        map_sig.put("faceValue", faceValue);
        map_sig.put("value", value);
        map_sig.put("rebate", rebate);
        map_sig.put("freeMoney", freeMoney);
        map_sig.put("validMonths", validMonths);
        map_sig.put("account", "btf");
        String sign = SignUtils.BuildMysign(map_sig,"betterlife");
        map_sig.put("sign", sign);

        RequestParams params = new RequestParams();
        params.put("shopID", new Des3().encode(shopId));
        params.put("cardNo", map_sig.get("cardNo"));
        params.put("cardType", map_sig.get("cardType"));
        params.put("faceValue", map_sig.get("faceValue"));
        params.put("value", map_sig.get("value"));
        params.put("rebate", map_sig.get("rebate"));
        params.put("freeMoney", map_sig.get("freeMoney"));
        params.put("validMonths", map_sig.get("validMonths"));
        params.put("account", "btf");
        params.put("sign", map_sig.get("sign"));

        RequestCenter.postRequest(HttpConstants.CARD_RECHARGE(context),params,listener,null);
    }

    public static void virtuaCardRecharge(Context context,String shopId,String value,
                                    String mobile,String cardType,String faceValue,
                                    String rebate,String freeMoney,String validMonths,DisposeDataListener listener){
        //account/sign
        Map<String, String> map_sig = new HashMap<String,String>();
        map_sig.put("shopID", UrlEncode(new Des3().encode(shopId)));
        map_sig.put("mobile", mobile);
        map_sig.put("cardType", cardType);
        map_sig.put("faceValue", faceValue);
        map_sig.put("value", value);
        map_sig.put("rebate", rebate);
        map_sig.put("freeMoney", freeMoney);
        map_sig.put("validMonths", validMonths);
        map_sig.put("account", "btf");
        String sign = SignUtils.BuildMysign(map_sig,"betterlife");
        map_sig.put("sign", sign);

        RequestParams params = new RequestParams();
        params.put("shopID", new Des3().encode(shopId));
        params.put("mobile", map_sig.get("mobile"));
        params.put("cardType", map_sig.get("cardType"));
        params.put("faceValue", map_sig.get("faceValue"));
        params.put("value", map_sig.get("value"));
        params.put("rebate", map_sig.get("rebate"));
        params.put("freeMoney", map_sig.get("freeMoney"));
        params.put("validMonths", map_sig.get("validMonths"));
        params.put("account", "btf");
        params.put("sign", map_sig.get("sign"));

        RequestCenter.postRequest(HttpConstants.VirtualCardCARD_RECHARGE(context),params,listener,null);
    }
    /**
     * 线上卡购买
     * @param context
     * @param shopId
     * @param listener
     */
    public static void cardBuy(Context context,String shopId,String mobile,
                                    String cardNo,String convexCode,String cardType,String faceValue,
                                    String value,String rebate,String freeMoney,String validMonths,DisposeDataListener listener){
        //account/sign
        Map<String, String> map_sig = new HashMap<String,String>();
        map_sig.put("shopID", UrlEncode(new Des3().encode(shopId)));
        map_sig.put("mobile", mobile);
        map_sig.put("cardNo", cardNo);
        map_sig.put("convexCode", convexCode);
        map_sig.put("cardType", cardType);
        map_sig.put("faceValue", faceValue);
        map_sig.put("value", value);
        map_sig.put("rebate", rebate);
        map_sig.put("freeMoney", freeMoney);
        map_sig.put("validMonths", validMonths);
        map_sig.put("account", "btf");
        String sign = SignUtils.BuildMysign(map_sig,"betterlife");
        map_sig.put("sign", sign);

        RequestParams params = new RequestParams();
        params.put("shopID", new Des3().encode(shopId));
        params.put("mobile", map_sig.get("mobile"));
        params.put("cardNo", map_sig.get("cardNo"));
        params.put("convexCode", map_sig.get("convexCode"));
        params.put("cardType", map_sig.get("cardType"));
        params.put("faceValue", map_sig.get("faceValue"));
        params.put("value", map_sig.get("value"));
        params.put("rebate", map_sig.get("rebate"));
        params.put("freeMoney", map_sig.get("freeMoney"));
        params.put("validMonths", map_sig.get("validMonths"));
        params.put("account", "btf");
        params.put("sign", map_sig.get("sign"));

        RequestCenter.postRequest(HttpConstants.CARD_BUY(context),params,listener,null);
    }

    /**
     * 线上卡扣费
     */
    public static void cardPay(Context context,String shopId,String cardNo,String money,DisposeDataListener listener){
        Map<String, String> map_sig = new HashMap<String,String>();
        map_sig.put("shopID", shopId);
        map_sig.put("cardNo", cardNo);
        //map_sig.put("money", new DecimalFormat("#.00").format(money));
        map_sig.put("money", money);
        map_sig.put("account", "btf");
        map_sig.put("sign", SignUtils.BuildMysign(map_sig,"betterlife"));

        RequestParams params = new RequestParams();
        params.put("shopID", shopId);
        params.put("cardNo", map_sig.get("cardNo"));
        params.put("money", map_sig.get("money"));
        params.put("account", map_sig.get("account"));
        params.put("sign", map_sig.get("sign"));
        RequestCenter.postRequest(HttpConstants.CARD_PAY(context),params,listener,null);
    }


    /**
     * 线上卡扣费
     */
    public static void virtualCardPay(Context context,String mobile,String money,DisposeDataListener listener){
        Map<String, String> map_sig = new HashMap<String,String>();
        map_sig.put("mobile", mobile);
        map_sig.put("money", new DecimalFormat("#.00").format(money));
        map_sig.put("account", "btf");
        map_sig.put("sign", SignUtils.BuildMysign(map_sig,"betterlife"));

        RequestParams params = new RequestParams();

        params.put("mobile", map_sig.get("mobile"));
        params.put("money", map_sig.get("money"));
        params.put("account", map_sig.get("account"));
        params.put("sign", map_sig.get("sign"));
        RequestCenter.postRequest(HttpConstants.virtualCARD_PAY(context),params,listener,null);
    }




    /**
     * 微信code获取
     * @param context
     * @param shopId
     * @param listener
     */
    public static void customer_WxCheck(Context context,String shopId,String state,DisposeDataListener listener){
        RequestParams params = new RequestParams();
        params.put("shopID", shopId);
        params.put("state", state);
        params.put("token", HttpConstants.Token);
        RequestCenter.postRequest(HttpConstants.Customer_WxCheck,params,listener,null);
    }

    /**
     * 根据openID获取手机号
     * @param openID
     * @param listener
     */
    public static void customer_GetMobileByOpenID(Context context,String openID,DisposeDataListener listener){
        RequestParams params = new RequestParams();
        params.put("openID", openID);
        params.put("token", HttpConstants.Token);
        RequestCenter.postRequest(HttpConstants.Customer_GetMobileByOpenID(context),params,listener,null);
    }


    /**
     * 获取微信TOKEN
     * @param url
     * @param listener
     */
    public static void customer_WxToken(String url,DisposeDataListener listener){
        RequestParams params = new RequestParams();
        RequestCenter.postRequest(url,params,listener,null);
    }


    public static void bindOpenID(Context context,String mobile,String captcha,String openID,DisposeDataListener listener){
        RequestParams params = new RequestParams();

        params.put("mobile", mobile);
        params.put("captcha", captcha);
        params.put("openID", openID);
        params.put("token", HttpConstants.Token);
        RequestCenter.postRequest(HttpConstants.BindOpenID(context),params,listener,null);
    }
    /**
     * 版本更新（总部接口）
     * @param context
     * @param versionNo
     * @param listener
     */
    public static void versionCheck(Context context, String versionNo, DisposeDataListener listener) {
        RequestParams params = new RequestParams();
        params.put("softType","2");
        params.put("versionNo", versionNo);
        params.put("token", HttpConstants.Token);
        RequestCenter.postRequest(HttpConstants.Version_Check(context), params, listener, null);
    }


    /**
     * URL转码，并且将转码的部分转换为大写
     * @param temp
     * @return
     */
    private static String UrlEncode(String temp) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < temp.length(); i++) {
            String t = String.valueOf(temp.charAt(i));
            String k = EncodeUtils.urlEncode(t);
            if (t.equals(k))
            {
                stringBuilder.append(t);
            }
            else
            {
                stringBuilder.append(k.toLowerCase());
            }
        }
        return stringBuilder.toString();
    }




}
