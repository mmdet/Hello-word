package com.hhzb.fntalm.fargment;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.hhzb.fntalm.bean.Num;
import com.hhzb.fntalm.module.manager.NumModule;
import com.mmdet.lib.utils.SPUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by c on 2016-12-08.
 */
public class CommonFun {

    //每次生成的len位的并且不同的数
    public static String getRandom(){
        return (int)((Math.random()*9+1)*100000)+"";
    }
    //根据6位随机数，取得验证码
    public static String getRelCode(String randoms){
        List<String> codes = CreateData();
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<codes.size();i++){
            int j = Integer.parseInt((String.valueOf(randoms.charAt(i))));
            sb.append(String.valueOf(codes.get(i).charAt(j)));
        }
        return sb.toString();
    }
    static List<String> mCodes = null;
    static String str0 = "8962881839",str1 = "4854691883",
            str2 = "3175300964",str3 = "1073169507",str4 = "5948831156",str5 = "9189872188";
    public static List<String> CreateData(){
        if(mCodes != null){
            return mCodes;
        }
        mCodes = new ArrayList<String>();
        mCodes.add(str0);
        mCodes.add(str1);
        mCodes.add(str2);
        mCodes.add(str3);
        mCodes.add(str4);
        mCodes.add(str5);
        return mCodes;
    }



    //获取输入的内容
    public static String getInput(TextView view) {
        return view.getText().toString().trim();
    }
    //增加
    public static void insertInput(TextView view,String text,boolean isClear) {
        if(isClear){
            view.setText("");
            view.setText(text);
        }else{
            view.append(text);
        }

    }
    //删除
    public static void delInput(TextView view) {
        String text = view.getText().toString();
        if(text.length() != 0){
            view.setText(text.substring(0, text.length()-1));
        }}
    //清除当前输入的内容
    public static void clearInput(TextView view) {view.setText("");}


    /**
     * 获取shopId
     * @param context
     * @return
     */
    public static String getShopId(Context context) {
        return SPUtils.getInstance(context).getString("shop_id",null);
    }
    /**
     * 获取siteId
     * @param context
     * @return
     */
    public static String getSiteId(Context context) {
        return SPUtils.getInstance(context).getString("site_id","00");
    }


    //0001 0117 0324 0001
    /**
     * 根据当前时间生成一个订单号
     */
    public static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss", Locale.getDefault());
        String key = format.format(new Date());
        key = key + new Random().nextInt(10000);
        return key;
    }

    //0001 0117 0324 0001
    /**
     * 根据当前时间生成一个线上卡号
     * 门店ID+站点ID+时间+4位序号
     */
    public static String getCardTradeNo(Context content) {
        String shopId = getShopId(content) == null?"0000": getShopId(content);
        String siteId = getSiteId(content) == null?"0000": getShopId(content);
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd", Locale.getDefault());
        String key = format.format(new Date());
        int xuhao = 1;
        Num num = NumModule.getInstance().getLastData();
        if(num == null){

        }else if(num.getNum()== 1000){
            xuhao = 1;
        }else{
            xuhao = num.getNum()+1;
        }
        NumModule.getInstance().insert(new Num(null,xuhao));
        key = shopId+siteId+key+new DecimalFormat("0000").format(xuhao);

        //序号
        return key;
    }

}
