package com.hhzb.fntalm.utils;

import android.content.Context;

import com.tts.Speak;

/**
 * 应用程序语音合成统一管理类
 * Created by hj on 2017/5/5.
 */
public class TTS {
    public static void speck_welcome(Context context){
        Speak.speck(context,"欢迎您使用自助柜收发系统，请选择");
    }
    public static void speck_input(Context context){
        Speak.speck(context,"请输入内容");
    }
    public static void speck_input_mobile(Context context){
        Speak.speck(context,"请输入手机号码");
    }
    public static void speck_input_admin(Context context){
        Speak.speck(context,"请输入管理员口令/手机号码");
    }
    public static void speck_input_qcode(Context context){
        Speak.speck(context,"请用微信扫一扫二维码进行身份验证");
    }
    public static void speck_input_err_mobile(Context context){
        Speak.speck(context,"请输入正确的手机号码");
    }
    public static void speck_input_password(Context context){
        Speak.speck(context,"请输入密码");
    }
    public static void speck_input_colthcount(Context context){
        Speak.speck(context,"请输入衣物件数");
    }
    public static void speck_input_cabincount(Context context){
        Speak.speck(context,"请输入初始柜子数量");
    }
    public static void speck_input_cloth_success(Context context){
        Speak.speck(context,"柜门已经打开,请放好衣物，关好柜门");
    }
    public static void speck_input_mobileCode(Context context){
        Speak.speck(context,"请输入验证码");
    }
    public static void speck_err_password(Context context){
        Speak.speck(context,"密码错误请重新输入");
    }
    public static void speck_no_user(Context context){
        Speak.speck(context,"没有此用户登陆失败");
    }
    public static void speck_cant_used_kouling(Context context){
        Speak.speck(context,"该口令被禁用");
    }
    public static void speck_cant_used_connt(Context context){
        Speak.speck(context,"该账号被禁用");
    }
    public static void speck_no_cabin(Context context){
        Speak.speck(context,"抱歉没有可用的柜门");
    }
    public static void speck_add_success(Context context){
        Speak.speck(context,"添加成功");
    }
    public static void speck_set_success(Context context){
        Speak.speck(context,"设置成功");
    }
    public static void speck_set_fail(Context context){
        Speak.speck(context,"设置失败请重试");
    }
    public static void speck_bind_success(Context context){
        Speak.speck(context,"绑定成功");
    }


    public static void speck_choose_card(Context context){
        Speak.speck(context,"请选择需要充值的选项");
    }
    public static void speck_recharge_succcess(Context context){
        Speak.speck(context,"充值成功");
    }
    public static void speck_buy_succcess(Context context){
        Speak.speck(context,"购买成功");
    }
    public static void speck_choose_pay_type(Context context){
        Speak.speck(context,"请选择支付方式");
    }
    public static void speck_choose_card_type(Context context){
        Speak.speck(context,"请选择卡类型");
    }

}
