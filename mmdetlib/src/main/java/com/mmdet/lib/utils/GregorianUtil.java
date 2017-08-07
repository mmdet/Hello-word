package com.mmdet.lib.utils;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * 对公历日期的处理类
 * User: zhouxin@easier.cn
 * Date: --
 * Time: 下午4:06
 * To change this template use File | Settings | File Templates.
 */
public class GregorianUtil {
    private final static String[][] GRE_FESTVIAL = {
            //一月
            {"元旦", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    ""},
            //二月
            {"", "", "", "", "", "", "", "", "", "", "", "", "", "情人", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", ""},
            //三月
            {"", "", "", "", "", "", "", "妇女", "", "", "", "植树", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", ""},
            //四月
            {"愚人", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", ""},
            //五月
            {"劳动", "", "", "青年", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", ""},
            //六月
            {"儿童", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", ""},
            //七月
            {"建党", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", ""},
            //八月
            {"建军", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", ""},
            //九月
            {"", "", "", "", "", "", "", "", "", "教师", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", ""},
            //十月
            {"国庆", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", ""},
            //十一月
            {"", "", "", "", "", "", "", "", "", "", "光棍", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", ""},
            //十二月
            {"艾滋病", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                    "", "", "", "", "", "圣诞", "", "", "", "", "", ""},
    };
    private int mMonth;
    private int mDay;

    public GregorianUtil(Calendar calendar) {
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DATE);
    }

    public String getGremessage() {
        return GRE_FESTVIAL[mMonth][mDay - 1];
    }

}
