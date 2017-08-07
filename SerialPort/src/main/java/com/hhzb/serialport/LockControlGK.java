package com.hhzb.serialport;


/**
 * 福州果壳锁板程序封装
 * 没有特殊说明的情况下 一律采用16进制格式发送与接收数据包
 * Created by Administrator on 2017/3/28.
 */

public class LockControlGK {
    /**
     * 设置锁控地址命令
     * @param addrA 地址A
     * @param addrB 地址B
     *              地址均为普通的数字格式
     * @return
     */
    public static byte[] setAddress(String addrA,String addrB){
        if(addrA.isEmpty() || addrB.isEmpty()){
            return null;
        }
        //地址转为16进制字符串
        String addrAhex = SerialDataUtils.Byte2Hex(Byte.parseByte(addrA));
        String addrBhex = SerialDataUtils.Byte2Hex(Byte.parseByte(addrB));

        //计算校检码 =》命令码+地址A+地址B
        String hex =  "FE"+addrAhex +addrBhex;
        byte[] bs = SerialDataUtils.HexToByteArr(hex);
        byte b = (byte)(bs[0]+ bs[1]+ bs[2]);
        String checkHex =  SerialDataUtils.Byte2Hex(b);

        //命令组装
        String hexString = "BB" +//同步码
                                "FE"+//命令码（设置地址AB）
                                addrAhex+//A地址
                                addrBhex+//B地址
                                checkHex+//校检码
                                "03";//结束吗
        return SerialDataUtils.HexToByteArr(hexString);

        //应答格式 BB FE FE 00 00 FC FE
    }


    /**
     * 开锁命令
     * @param address 柜门地址
     *                开锁命令 0A
     *
     *                返回状态   同步码 柜子地址 返回命令  门状态     物品状态            校检码 结束吗
     *                              BB   01     0A     01开、00关    01有物、00没有     00     FE
     * @return
     */
    public static byte[] open(int address){
        //将地址转换为16进制字符串
        String addrAhex = SerialDataUtils.Byte2Hex(Byte.parseByte(address+""));
        //计算校检码 =》柜子地址+命令+参数
        String hex =  addrAhex +"0A"+"FF";
        byte[] bs = SerialDataUtils.HexToByteArr(hex);
        byte b = (byte)(bs[0]+ bs[1]+ bs[2]);
        String checkHex =  SerialDataUtils.Byte2Hex(b);

        String hexString = "BB" +//数据包开始
                addrAhex+//柜子地址码
                "0A"+//开锁指令
                "FF"+//预留位
                checkHex+//校检码
                "03";//结束吗
        return SerialDataUtils.HexToByteArr(hexString);
        //return hexString.getBytes();
    }

    /**
     * 柜门是否打开成功
     * @param address 柜门地址
     * @param resData 返回的数据信息BB010A010000FE
     * @return
     */
    public static boolean isOpenSuccess(int address,String resData){
        if(resData.isEmpty()){ return false;}
        //将地址转换为16进制字符串
        String addrhex = SerialDataUtils.Byte2Hex(Byte.parseByte(address+""));
        String openData = "BB"+addrhex+"0A01";
        if(resData.substring(0,8).equals(openData)){
            return true;
        }
        return false;
    }
    /**
     * 查询命令
     * @param address 柜门地址
     *                开锁命令 0B
     *
     *                返回状态   同步码 柜子地址 返回命令  门状态     物品状态            校检码 结束吗
     *                              BB   01     0A     01开、00关    01有物、00没有     00     FE
     * @return
     */
    public static byte[] query(String address){
        if(address.isEmpty()){
            return null;
        }
        //将地址转换为16进制字符串
        String addrAhex = SerialDataUtils.Byte2Hex(Byte.parseByte(address));
        //计算校检码 =》柜子地址+命令+参数
        String hex =  addrAhex +"0B"+"FF";
        byte[] bs = SerialDataUtils.HexToByteArr(hex);
        byte b = (byte)(bs[0]+ bs[1]+ bs[2]);
        String checkHex =  SerialDataUtils.Byte2Hex(b);

        String hexString = "BB" +//数据包开始
                "01"+//柜子地址码
                "0B"+//开锁指令
                "FF"+//预留位
                checkHex+//校检码
                "03";//结束吗
        return SerialDataUtils.HexToByteArr(hexString);
    }

}
