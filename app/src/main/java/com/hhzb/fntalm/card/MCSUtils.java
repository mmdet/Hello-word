package com.hhzb.fntalm.card;

/**
 * Created by Administrator on 2017/7/27.
 */

public class MCSUtils {
    public static int getCardType(){
        byte[] buff = new byte[2];
        if(MCSReaderAPI.MCS_Request((byte)0X01,buff) == 0){
            //射频卡
            if(buff[0]==0x04)
                return CardType.RFID;
        }else{
            //IC卡
            short sAddr = 42,sLen = 6;
            byte[] retbuf = new byte[sLen];
            int retv = MCSReaderAPI.MCS_4442ReadChar(sAddr, sLen, retbuf);
            String str42 = DataUtils.bytes2String(retbuf).trim();
            if(str42.equals("") || str42 == null)
            {
                //联盟卡
                short addr = 32,len = 6;
                byte[] buffs = new byte[sLen];
                int ret = MCSReaderAPI.MCS_4442ReadChar(addr, len, buffs);
                if (buffs.length != 0) {
                    return CardType.JOIN;//联盟卡
                } else {
                    return CardType.ICEMPTY;//IC空卡
                }
            }else{
                return CardType.ICNEW;//IC新卡
            }

        }


        return CardType.UNKNOW;
    }
}
