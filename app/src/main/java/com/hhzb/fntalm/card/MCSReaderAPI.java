package com.hhzb.fntalm.card;

import android.content.Context;
import android.hardware.usb.UsbManager;
import com.hhzb.fntalm.MyApplication;
import cn.wch.ch37xdriver.CH37xDriver;

/**
 * 读卡器API封装
 * Created by hj on 2017/7/24.
 */

public class MCSReaderAPI {

    public static final String ACTION_USB_PERMISSION = "cn.wch.ch37xdriver.USB_PERMISSION";

    /**
     * 是否支持USB HOST
     * @param ch37xdrv
     * @return
     */
    public static boolean isSupportUsbHost(CH37xDriver ch37xdrv){
        if (ch37xdrv.UsbFeatureSupported())// 判断系统是否支持USB HOST
            return true;
        return false;
    }

    /**
     * 是否有卡插入卡座
     * @return
     */

    public static boolean isCardIn()
    {
        byte[] rebuf = new byte[1];
        int res = MyApplication.ch37xdrv.MCS_4442CardIn(rebuf);
        if(res != 0)
            return false;
        if(rebuf[0] == 0x01)
            return true;
        return false;
    }

    /**
     * 检测是否是IC卡
     * @return
     */

    public static boolean isIcCard()
    {
        byte bMode = 0x00;
        byte[] rebuf = new byte[2];
        int res = MyApplication.ch37xdrv.MCS_Request(bMode,rebuf);
        //请求成功说明是射频卡,请求错误，说明是芯片卡（IC卡）
        if(res != 0)
            return true;
        return false;
    }

    /**
     * 设备与读卡器是否打开连接
     * @return
     */
    public static boolean isOpen(){
        return MyApplication.ch37xdrv.isConnected();
    }

    /**
     * 打开设备
     * @return
     */
    public static boolean openDevice(){
        if(MyApplication.ch37xdrv.OpenDevice())
            return true;
        return false;
    }
    /**
     * 关闭设备
     * @return
     */
    public static void closeDevice(){
        MyApplication.ch37xdrv.CloseDevice();
    }

    /**
     *读取指定长度的内容
     * @param sAddr 0
     * @param sLen 卡号长度
     * @param retbuf 读取到的内容
     * @return 操作成功，返回0；否则，返回错误码
     */
    public static int MCS_4442ReadChar(short sAddr, short sLen, byte[] retbuf){
        return  MyApplication.ch37xdrv.MCS_4442ReadChar(sAddr, sLen, retbuf);
    }

    /**
     * 返回一个DWORD值，4个字节
     * @param sAddr
     * @param retbuf
     * @return
     */
    public static int MCS_4442ReadValue(short sAddr, byte[] retbuf){
        return MyApplication.ch37xdrv.MCS_4442ReadValue(sAddr,retbuf);
    }

    /**
     * 4442卡验证密码
     * @param bPwd1
     * @param bPwd2
     * @param bPwd3
     * @return
     */
    public static int MCS_4442VerifyPWD(byte bPwd1, byte bPwd2, byte bPwd3){
        return MyApplication.ch37xdrv.MCS_4442VerifyPWD(bPwd1,bPwd2,bPwd3);
    }

    /**
     * 4442卡写密码
     * @param bPwd1
     * @param bPwd2
     * @param bPwd3
     * @return
     */
    public static int MCS_4442WritePWD(byte bPwd1, byte bPwd2, byte bPwd3){
        return MyApplication.ch37xdrv.MCS_4442WritePWD(bPwd1,bPwd2,bPwd3);
    }

    /**
     * 读取4442卡密码
     * @param retBuf
     * @return
     */
    public static int MCS_4442ReadPWD(byte[] retBuf){
        return MyApplication.ch37xdrv.MCS_4442ReadPWD(retBuf);
    }

    /**
     * 4442卡写卡
     * @param sAddr
     * @param sLen
     * @param bData
     * @return
     */
    public static int MCS_4442WriteChar(short sAddr, short sLen, byte[] bData){
        return MyApplication.ch37xdrv.MCS_4442WriteChar(sAddr,sLen,bData);
    }




    ////////////

    public static int MCS_Request(byte bMode, byte[] retbuf){
        return MyApplication.ch37xdrv.MCS_Request(bMode,retbuf);
    }

}
