package com.hhzb.fntalm.card;

import com.hhzb.fntalm.card.bean.ICC;
import com.hhzb.fntalm.card.bean.JoinCard;
import com.hhzb.fntalm.network.http.RequestCenter;
import com.mmdet.lib.okhttp.listener.DisposeDataListener;

/**
 * Created by Administrator on 2017/7/24.
 */

public class MCSNewReader {

    //IC会员卡密码
    static final String PASS = "B0802F";
    /**
     * IC新卡读卡
     */
    public static ICC ReadCard(){
        //ReadChar参数设置
        short sAddr = 20,sLen = 180;
        byte[] retbuf = new byte[sLen];
        int retv = MCSReaderAPI.MCS_4442ReadChar(sAddr, sLen, retbuf);
        String str = DataUtils.DecryptText(DataUtils.bytes2String(retbuf));
        return StringToIcc(str);
    }
    private static ICC StringToIcc(String iccString) {
        String[] items = iccString.split("!");
        ICC icc =new ICC();
        if (items.length >= 26) {
            icc.setCardNo(items[0]);
            icc.setMobile(items[1]);
            icc.setRemain(Float.parseFloat(items[2]));
            icc.setRebate(Float.parseFloat(items[3]));
            icc.setName(items[4]);
            icc.setFeeAmount(Float.parseFloat(items[5]));
            icc.setExpiryDate(items[6]);
            icc.setCardType(items[7]);
            icc.setShopName(items[8]);
            icc.setSaleDate(items[9]);
            icc.setFeeTimes(Integer.parseInt(items[10]));
            icc.setRechargeAmount(Float.parseFloat(items[11]));
            icc.setRechargeTimes(Integer.parseInt(items[12]));
            icc.setSex(items[13]);
            icc.setAuther(items[14]);
            icc.setCompany(items[15]);
            icc.setAddress(items[16]);
            icc.setBirthday(items[17]);
            icc.setKind(items[18]);
            icc.setZjno(items[19]);
            icc.setJf(Float.parseFloat(items[20]));
            icc.setQe(Integer.parseInt(items[21]));
            icc.setShopPass(items[22]);
            icc.setCompanyCode(items[23]);
            icc.setFreeAmount(Float.parseFloat(items[24]));
            icc.setFaceValue(Float.parseFloat(items[25]));
        }
        if (items.length >= 28)
        {
            icc.setShopAuther(items[26]);
            icc.setConvexCode(items[27]);
        }
        if (items.length >= 29)
        {//福奈特卡合法验证
            icc.setIsValid(items[28]);
        }

        if (icc.getMobile().equals(""))
            icc.setMobile("未发");
        return  icc;
    }

    /**
     * IC新卡扣费
     */
    public static boolean PayCard(ICC icc,float money){
        //再次验证要扣费的卡是否为原来刷的卡，预防写错卡
        ICC iccRead =  ReadCard();
        if (!iccRead.getCardNo().equals(icc.getCardNo()))
            return false;
        iccRead.setRemain(iccRead.getRemain() - money);
        iccRead.setFeeAmount(iccRead.getFeeAmount() + money);
        String errorMsg = "";
        if(!WriteCard(iccRead,errorMsg)){
            return false;
        };
        return true;
    }


    /**写IC新卡信息
     * @param icc
     * @param errorMsg
     * @return
     */
    public static boolean WriteCard(ICC icc, String errorMsg)
    {
        //验证密码
       // byte pw1 = Byte.parseByte(PASS.substring(0, 2));
      //  byte pw2 = Byte.parseByte(PASS.substring(2, 2));
       // byte pw3 = Byte.parseByte(PASS.substring(4, 2));

       // int result = MCSReaderAPI.MCS_4442VerifyPWD(pw1, pw2, pw3);
       // if (result != 0){
       //     errorMsg = "卡密码验证失败";
       //     return  false;
       // }

        String str = IccToString(icc);

        str = DataUtils.EncryptText(str);
        byte[] b = str.getBytes();
        int result = MCSReaderAPI.MCS_4442WriteChar((short) 20, (short) 180, b);
        if (result != 0) {
            errorMsg = "卡信息写入失败";
            return  false;
        }

        return true;
    }
    private static String IccToString(ICC icc) {
       StringBuilder sb = new StringBuilder();
        if (icc!= null) {
            sb.append(icc.getCardNo()+"!");
            sb.append(icc.getMobile()+"!");
            sb.append(icc.getRemain()+"!");
            sb.append(icc.getRebate()+"!");
            sb.append(icc.getName()+"!");
            sb.append(icc.getFeeAmount()+"!");
            sb.append(icc.getExpiryDate()+"!");
            sb.append(icc.getCardType()+"!");
            sb.append(icc.getShopName()+"!");
            sb.append(icc.getSaleDate()+"!");
            sb.append(icc.getFeeTimes()+"!");
            sb.append(icc.getRechargeAmount()+"!");
            sb.append(icc.getRechargeTimes()+"!");
            sb.append(icc.getSex()+"!");
            sb.append(icc.getAuther()+"!");
            sb.append(icc.getCompany()+"!");
            sb.append(icc.getAddress()+"!");
            sb.append(icc.getBirthday()+"!");
            sb.append(icc.getKind()+"!");
            sb.append(icc.getZjno()+"!");
            sb.append(icc.getJf()+"!");
            sb.append(icc.getQe()+"!");
            sb.append(icc.getShopPass()+"!");
            sb.append(icc.getCompanyCode()+"!");
            sb.append(icc.getFreeAmount()+"!");
            sb.append(icc.getFaceValue()+"!");
            sb.append(icc.getShopAuther()+"!");
            sb.append(icc.getConvexCode()+"!");
            sb.append(icc.getIsValid()+"!aaaaaaaaaaaaa");
        }

        return sb.toString();
    }
}
