package com.hhzb.fntalm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class DateUtils {

    static String date(String dates){
        String str=dates.replace("/Date(","").replace(")/","");
        final String time = str.substring(0,str.length()-5);
        Date date = new Date(Long.parseLong(time));
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


}
