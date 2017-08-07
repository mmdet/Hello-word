package com.hhzb.fntalm.bean;

/**
 * Created by c on 2017-01-15.
 */

public class WxOuath {
    String state;
    String code;

    public WxOuath(String state, String code) {
        this.state = state;
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
