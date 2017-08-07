package com.hhzb.fntalm.fargment;

import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by c on 2016-12-08.
 */
public class CommonData {
    //参数传递
    public static final String ARG_ARGS = "arg_args";
    public static final String ARG_Mobile = "arg_mobile";
    public static final String ARG_MENUID = "arg_menuid";
    //页面倒计时
    public static long totaltimes = 120000;//120s
    public static long space = 1000;//1s

    //handler .what
    public static final int STEP_ONE = 1;
    public static final int STEP_TWO = 2;
    public static final int STEP_THREE = 3;

    //柜子类型  0叠式  1挂式
    public static final int CABIN_TYPE_DIESHI = 0;
    public static final int CABIN_TYPE_GUASHI = 1;

    //payType支付方式
    public static final int PAY_TYPE_WALLET = 0;
    public static final int PAY_TYPE_XUNIKA = 1;
    public static final int PAY_TYPE_SHITIKA = 2;
    public static final int PAY_TYPE_WEIXIN = 3;
    public static final int PAY_TYPE_ZHIFUBAO = 4;



    //首页菜单ID
    public static final int MENU_INPUT = 0;
    public static final int MENU_OUTPUT = 1;
    public static final int MENU_CANCEL = 2;
    public static final int MENU_BUYCARD = 3;
    public static final int MENU_RECHARGE = 4;
    public static final int MENU_SEARCH = 5;
    public static final int MENU_ADMIN = 900;


}
