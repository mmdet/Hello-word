package com.hhzb.fntalm.bean;

/**
 * 手机验证码类
 * Created by Administrator on 2017/3/9 0009.
 */

public class Mobiles {

    String Mobile;
    String Captcha;

    public Mobiles(String mobile, String captcha) {
        Mobile = mobile;
        Captcha = captcha;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getCaptcha() {
        return Captcha;
    }

    public void setCaptcha(String captcha) {
        Captcha = captcha;
    }
}
