package com.hhzb.serialport;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import android_serialport_api.SerialPort;

/**
 * Created by c on 2017-02-20.
 */

public class SerialPortUtil {

    private static SerialPortUtil mInstance = null;
    private SerialPort mSerialPort = null;
    private static InputStream mInputStream;
    private static OutputStream mOutputStream;
    //返回的数据
    public  String resultData = null;

    public static SerialPortUtil getInstance(){
        if(mInstance == null){
            synchronized (SerialPortUtil.class){
                if (mInstance == null) {
                    mInstance = new SerialPortUtil();
                }
            }
        }
        return mInstance;
    }

    //私有构造方法完成初始化工作
    private SerialPortUtil()  {
        if (mSerialPort == null) {
            try{
                mSerialPort = new SerialPort(new File("/dev/ttyS1"), 9600, 0);
                mOutputStream = mSerialPort.getOutputStream();
                mInputStream = mSerialPort.getInputStream();
            }catch (Exception e){
            }
        }
    }

    //开锁（单个柜门）
    public Boolean open(int cabinCode) {
        //获取开锁命令
        //在这里更换不同的锁控
        byte[] writeBytes = LockControlGK.open(cabinCode);
        //发送命令
        sendData(writeBytes);
        //读取返回数据 16进账字符串格式
        readDataThread();
        //判断开锁是否成功
        //if(LockControlGK.isOpenSuccess(cabinCode,resultData)){
        //    return true;
        //}
        if(resultData != null && !resultData.equals("")){
            return true;
        }
        return false;
    }

    /**
     * 果壳锁控设置地址的方法
     * @param addrA
     * @param addrB
     * @return
     */
    public Boolean setAddress(String addrA,String addrB) {
        byte[] writeBytes = LockControlGK.setAddress(addrA,addrB);
        //发送命令
        sendData(writeBytes);
        readDataThread();
        if(resultData.equals("BBFEFE0000FCFE")){
            return  true;
        }
        return  false;
    }

    //发串口数据
    public  void sendData(byte[] writeBytes){
        resultData = "";
        try {
            if (mOutputStream != null) {
                mOutputStream.write(writeBytes);
            }
        }catch (Exception e){

        }
    }


    //线程式接受串口数据
    public  String  readDataThread(){
        ReadThread mReadThread = new ReadThread();
        mReadThread.start();

        for(int i = 0;i<30;i++){
            try{Thread.sleep(200);}catch (InterruptedException e){}

            if(resultData != null && !resultData.equals("")){
                //有返回值
                if(mReadThread != null){
                    mReadThread.interrupt();
                }
                break;
            }
        }
        return resultData;
    }

    public void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
            mInstance = null;
        }
    }
    private class ReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                synchronized(resultData){
                    try {
                        if (mInputStream == null)
                            break;
                        byte[] buffer = new byte[7];
                        int size = mInputStream.read(buffer);
                        if (size > 0) {
                            //证明有返回数据
                            //处理数据
                            resultData = SerialDataUtils.ByteArrToHex(buffer);
                            interrupt();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
