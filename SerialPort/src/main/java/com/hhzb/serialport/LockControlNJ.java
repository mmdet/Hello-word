package com.hhzb.serialport;


/**
 * 南京锁板程序封装
 * 没有特殊说明的情况下 一律采用16进制格式发送与接收数据包
 * Created by Administrator on 2017/3/28.
 */

public class LockControlNJ {
    private static int EBLN = 24; //每一个锁板上柜门的数量
    /**
     * 开锁命令
     * @param cabinCode 柜门地址
     *                开锁命令 0A
     *
     *                返回状态          锁板号 柜门号
     *                              68   01     01     02   FF     FF     16
     * @return
     */
    public static byte[] open(int cabinCode){
        byte[] writeBytes = new byte[7];
        writeBytes[0] = SerialDataUtils.HexToByte("68");
        writeBytes[1] = SerialDataUtils.HexToByte(getNum(EBLN, cabinCode)[0]);
        writeBytes[2] = SerialDataUtils.HexToByte(getNum(EBLN, cabinCode)[1]);
        writeBytes[3] = SerialDataUtils.HexToByte("02");
        writeBytes[4] = SerialDataUtils.HexToByte("FF");
        writeBytes[5] = SerialDataUtils.HexToByte("FF");
        writeBytes[6] = SerialDataUtils.HexToByte("16");
        return writeBytes;
    }
    public static boolean isOpenSuccess(int cabinCode,String resData){
        if(resData.isEmpty()){ return false;}
        //开锁是否成功判断方式
        if(resData.equals("68"+getNum(EBLN, cabinCode)[0]+getNum(EBLN, cabinCode)[1]+"02000016")){
            return true;
         }
        return false;
    }


    /**
     *根据lockNum计算出锁门号与柜门
     * @param lockNum 柜门
     * @return
     */
    public static String[] getNum(int EBLN,int lockNum){
        String[] nums =new String[2];
        int boardNum = (lockNum - 1) / EBLN + 1;
        int codeNum = (lockNum - 1) % EBLN + 1;
        nums[0] = ""+boardNum;
        nums[1] = ""+codeNum;
        //1位数字补0
        if(boardNum<10){
            nums[0] = "0"+boardNum;
        }
        if(codeNum<10){
            nums[1] = "0"+codeNum;
        }
        return nums;
    }

}
