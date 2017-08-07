package com.hhzb.fntalm.card;

import android.content.Context;
import android.widget.Toast;
import com.hhzb.fntalm.MyApplication;
import com.hhzb.fntalm.card.bean.JoinCard;
import com.hhzb.fntalm.fargment.home.output.PayShitiCard;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.hhzb.serialport.SerialDataUtils;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;

import java.util.Arrays;

/**
 * 联盟卡相关操作
 * Created by Administrator on 2017/7/24.
 */

public class MCSJoinReader {

    // 联盟卡服务器地址
    private final static String SERVERURL = "http://124.65.126.74:4869";
    /**
     * 联盟卡读卡
     */
    public static String ReadTest(){
        //ReadChar参数设置
        short sAddr = 42,sLen = 6;
        byte[] retbuf = new byte[sLen];
        //读取6位卡号
        int retv = MCSReaderAPI.MCS_4442ReadChar(sAddr, sLen, retbuf);
        //参数设置
        String str = DataUtils.bytes2String(retbuf);
    return str;}
    /**
     * 联盟卡读卡
     */
    public static void ReadCardNo(DisposeDataListener resListener){
        //ReadChar参数设置
        short sAddr = 32,sLen = 6;
        byte[] retbuf = new byte[sLen];
        //读取6位卡号
        int retv = MCSReaderAPI.MCS_4442ReadChar(sAddr, sLen, retbuf);
        //参数设置
        String str = "cardNo="+DataUtils.bytes2String(retbuf);
        byte[] bTemp = str.getBytes();
        //发送请求
        RequestCenter.postBytes(SERVERURL+"/fornet/client/cardSearch.jhtml", bTemp, resListener,null);
    }
    /**
     * 联盟卡扣费
     */
    public static void FeeMoney(JoinCard joinCard,String money,DisposeDataListener resListener){
        //读取验证码
        //验证验证码的有效性
        String amount = (int)(Float.parseFloat(money)*10)+"";
        //参数设置
        String str = "cardNo="+joinCard.getCardNo()+"&terminalCode="+joinCard.getTerminalCode()+"&amount="+amount+"&cardRfic="+joinCard.getCardRfic();
        byte[] bTemp = str.getBytes();
        //发送请求
        RequestCenter.postBytes(SERVERURL+"/fornet/client/consume.jhtml", bTemp, resListener,null);
    }


    /**
     * 联盟卡 读取验证码
     * @return
     */
    public static String read(){
        short sAddr = 0x40,sLen = 5;
        byte[] retbuf = new byte[sLen];
        int res = MCSReaderAPI.MCS_4442ReadChar(sAddr,sLen,retbuf);
        if(res != 0)
         return null;
        return DataUtils.bytes2String(retbuf) ;
    }
    /**
     * 读取联盟卡终端号
     */
    public static String ReadEE(){
        //ReadChar参数设置
        short sAddr = 0,sLen = 18;
        byte[] retbuf = new byte[sLen];
        int retv = MCSReaderAPI.MCS_4442ReadChar(sAddr, sLen, retbuf);
        if(retv != 0)
            return null;
        return DataUtils.bytes2String(retbuf);
    }
}
