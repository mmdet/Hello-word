package com.hhzb.fntalm.network.http;

import android.content.Context;
import android.util.Log;

import com.mmdet.lib.utils.SPUtils;

/**
 * @author: qndroid
 * @function: 所有请求相关地址
 * @date: 16/8/12
 */
public class HttpConstants {

    //Token
    public static final String Token = "123";
    //
    public static String Customer_WxCheck = "http://guifu.chinahighsoft.com/TSC.asmx/Customer_WxCheck";
    public static String Customer_GetMobileByOpenID = "http://guifu.chinahighsoft.com/TSC.asmx/Customer_GetMobileByOpenID";
    /**
     * 新增衣物接口 -- 柜子上传到门店
     */
    public static String CLOTH_INPUT(Context context){
        return addressApi(context)+"/inputclothes/";
    }
    /**
     * 被取走衣物接口 -- 柜子上传到门店
     */
    public static String CLOTH_BACKPUT(Context context){
        return addressApi(context)+"/backclothes/";
    }
    /**
     * 返店接口
     */
    public static String CLOTH_BACKSHOP(Context context){
        return addressApi(context)+"/backshop/";
    }

    /**
     * 订单信息
     */
    public static String CLOTH_INFOS(Context context){
        return addressApi(context)+"/clothinfo/";
    }

    /**
     * 获取手机验证码
     */
    public static String CAPTCHAS_GET(Context context){
        return LSS(context)+"/Customer_SendCaptchaByShop2";
    }

    /**
     * 验证手机验证码
     */
    public static String CAPTCHAS_CHECK(Context context){
        return LSS(context)+"/Customer_CheckCaptcha";
    }
    /**
     * 微信与手机绑定
     */
    public static String BindOpenID(Context context){
        return LSS(context)+"/Customer_BindOpenID";
    }

    /**
     * 微信与手机绑定
     */
    public static String Customer_GetMobileByOpenID(Context context){
        return LSS(context)+"/Customer_GetMobileByOpenID";
    }
    /**
     * 微信支付码获取
     */
    public static String PAY_CODE_WEIXIN(Context context){
        return LC(context)+"/Wxpay_QRCode";
    }

    /**
     * 支付宝支付码获取
     */
    public static String PAY_CODE_ZHIFUBAO(Context context){
        return LC(context)+"/Alipay_QRCode";
    }

    /**
     * 获取微信二维码付款状态
     */
    public static String PAY_STATE_WEIXIN(Context context){
        return LC(context)+"/Wxpay_CheckQR";
    }

    /**
     * 获取支付宝二维码付款状态
     */
    public static String PAY_STATE_ZHIFUBAO(Context context){
        return LC(context)+"/Alipay_CheckQR";
    }


   //线上卡列表获取
    public static String CARD_GET(Context context){
        return LSS(context)+"/Card_Get";
    }
   //卡类型获取
    public static String CARD_TYPE_GET(Context context){
        return LSS(context)+"/CardType_Get";
    }

   //线上卡充值（门店端）
    public static String CARD_RECHARGE(Context context){
        return LSS(context)+"/ServerCard_RechargeByShop2";
    }
   //钱包充值
    public static String VirtualCardCARD_RECHARGE(Context context){
        return LSS(context)+"/VirtualCard_RechargeByShop";
    }
    //线上卡购买
    public static String CARD_BUY(Context context){
        return LSS(context)+"/ServerCard_CreateByShop3";
    }

   //线上卡扣费
    public static String CARD_PAY(Context context){
        return LSS(context)+"/ServerCard_Pay";
    }

    //钱包扣费
    public static String virtualCARD_PAY(Context context){
        return LSS(context)+"/VirtualCard_Pay";
    }

    //钱包扣费
    public static String Version_Check(Context context){
        return LOS(context)+"/Version_Check";
    }


//----------------------------------------------------------------------------------------------------------------------
    //shopId获取
    public static String shopId(Context context){
        return SPUtils.getInstance(context).getString("shop_id","0000");
    }

   //前台接口地址
    private static String addressApi(Context context){
        return SPUtils.getInstance(context).getString("addres_api","http://192.168.4.206:12345/LaundryPS/api");
    }

    //总部接口LOS地址
    private static String LOS(Context context){
        return addressLos(context)+"/LOS.asmx";
    }
    private static String addressLos(Context context){
        return SPUtils.getInstance(context).getString("addres_los","http://101.200.124.196:6789");
    }

    //总部接口LOS地址
    private static String LSS(Context context){
        return addressLss(context)+"/LSS.asmx";
    }

    //总部接口LC地址
    private static String LC(Context context){
        return addressLss(context)+"/LC.asmx";
    }
    private static String addressLss(Context context){
        return SPUtils.getInstance(context).getString("addres_lss","http://101.200.124.196:4567");
    }


}


