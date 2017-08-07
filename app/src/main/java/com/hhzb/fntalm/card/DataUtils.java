package com.hhzb.fntalm.card;

import android.util.Xml;
import com.hhzb.fntalm.card.bean.JoinCard;
import com.hhzb.fntalm.card.bean.JoinRes;

import org.xmlpull.v1.XmlPullParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 返回数据处理工具
 * Created by Administrator on 2017/7/25.
 */

public class DataUtils {

    /*
   * 字节数组转字符串
   */
    public static String bytes2String(byte[] b) {
        try {
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 将byte[]数组转化为String类型
     *
     * @param arg    需要转换的byte[]数组
     * @param length 需要转换的数组长度
     * @return 转换后的String队形
     */
    public static String toHexString(byte[] arg, int length) {
        if (arg != null) {
            String result = new String();
            for (int i = 0; i < length; i++) {
                result = result
                        + (Integer.toHexString(
                        arg[i] < 0 ? arg[i] + 256 : arg[i]).length() == 1 ? "0"
                        + Integer.toHexString(arg[i] < 0 ? arg[i] + 256
                        : arg[i])
                        : Integer.toHexString(arg[i] < 0 ? arg[i] + 256
                        : arg[i])) + " ";
            }
            return result;
        }
        return null;
    }

    /**
     * 获取联盟卡的xml信息
     * @param xml
     * @return
     * @throws Exception
     */
    public static JoinCard getJoinCard(String xml) throws Exception {
        // 获取person文件的输入流
        InputStream is = getStringStream(xml);
        // 一个标记
        boolean flag = false;
        JoinCard joinCard = null;
        // 实例化一个XmlPullParser对象
        XmlPullParser parser = Xml.newPullParser();
        // 设置输入流的编码
        parser.setInput(is, "UTF-8");
        // 设置第一个事件，从这个事件开始解析文档
        int eventCode = parser.getEventType();
        // 设定结束标记，如果是END_DOCUEMNT,解析就结束了
        while (eventCode != XmlPullParser.END_DOCUMENT) {
            switch (eventCode) {
                case XmlPullParser.START_TAG:
                    if ("card".equals(parser.getName())) {
                        joinCard = new JoinCard();
                    } else if ("id".equals(parser.getName())) {
                        joinCard.setId(Integer.parseInt(parser.nextText()));
                    } else if ("cardNo".equals(parser.getName())) {
                        String cardNo =parser.nextText();
                        joinCard.setCardNo(cardNo);
                    } else if ("cardRfic".equals(parser.getName())) {
                        String cardRfic = parser.nextText();
                        joinCard.setCardRfic(Integer.parseInt(cardRfic));
                    }else if ("faceAmount".equals(parser.getName())) {
                        String faceAmount = parser.nextText();
                        joinCard.setFaceAmount(Float.parseFloat(faceAmount)/10);
                    } else if ("balance".equals(parser.getName())) {
                        String balance = parser.nextText();
                        joinCard.setBalance(Float.parseFloat(balance)/10);
                    }else if ("lastConsumeDate".equals(parser.getName())) {
                        String lastConsumeDate = parser.nextText();
                        joinCard.setLastConsumeDate(lastConsumeDate);
                    }else if ("consumerName".equals(parser.getName())) {
                        String consumerName = parser.nextText();
                        joinCard.setConsumerName(consumerName);
                    }else if ("consumerPhone".equals(parser.getName())) {
                        String consumerPhone = parser.nextText();
                        joinCard.setConsumerPhone(consumerPhone);
                    }
                    break;
            }
            // 这一步很重要，该方法返回一个事件码，也是触发下一个事件的方法
            eventCode = parser.next(); // 取下个标签
        }
        return joinCard;
    }

    /**
     * 获取联盟卡扣费返回的xml信息
     * @param xml
     * @return
     * @throws Exception
     */
    public static JoinRes getJoinRes(String xml) throws Exception {
        // 获取person文件的输入流
        InputStream is = getStringStream(xml);
        // 一个标记
        boolean flag = false;
        JoinRes joinRes = null;
        // 实例化一个XmlPullParser对象
        XmlPullParser parser = Xml.newPullParser();
        // 设置输入流的编码
        parser.setInput(is, "UTF-8");
        // 设置第一个事件，从这个事件开始解析文档
        int eventCode = parser.getEventType();
        // 设定结束标记，如果是END_DOCUEMNT,解析就结束了
        while (eventCode != XmlPullParser.END_DOCUMENT) {
            switch (eventCode) {
                case XmlPullParser.START_TAG:
                    if ("root".equals(parser.getName())) {
                        joinRes = new JoinRes();
                    } else if ("success".equals(parser.getName())) {
                        joinRes.setSuccess(Boolean.parseBoolean(parser.nextText()));
                    } else if ("cardNo".equals(parser.getName())) {
                        String cardNo =parser.nextText();
                        joinRes.setCardNo(Integer.parseInt(cardNo));
                    } else if ("amount".equals(parser.getName())) {
                        String amount = parser.nextText();
                        joinRes.setAmount(Float.parseFloat(amount)/10);
                    } else if ("blance".equals(parser.getName())) {
                        String balance = parser.nextText();
                        joinRes.setBlance(Float.parseFloat(balance)/10);
                    }else if ("consumerName".equals(parser.getName())) {
                        String consumerName = parser.nextText();
                        joinRes.setConsumerName(consumerName);
                    }else if ("consumerPhone".equals(parser.getName())) {
                        String consumerPhone = parser.nextText();
                        joinRes.setConsumerPhone(consumerPhone);
                    }
                    break;
            }
            // 这一步很重要，该方法返回一个事件码，也是触发下一个事件的方法
            eventCode = parser.next(); // 取下个标签
        }
        return joinRes;
    }

    /**
     * 将String转换为字节流
     * @param sInputString
     * @return
     */
    public static InputStream getStringStream(String sInputString){
        if (sInputString != null && !sInputString.trim().equals("")){
            try{
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return null;
    }

    /// <summary>
    /// 解密字符串
    /// </summary>
    /// <param name="strText">要解密的字符串</param>
    /// <returns>解密后的字符串</returns>
    public static String DecryptText(String strText) {
        StringBuffer sb = new StringBuffer();
        char[] chars = strText.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            int ascii = (int)chars[i];
            if (ascii < 0)
                sb.append((char)(ascii + 10));
            else
                sb.append((char)(ascii - 2));
        }
        return sb.toString();
    }

    public static String EncryptText(String strText) {
        StringBuffer sb = new StringBuffer();
        char[] chars = strText.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            int ascii = (int)chars[i];
            if (ascii < 0)
                sb.append((char)(ascii - 10));
            else
                sb.append((char)(ascii + 2));
        }
        return sb.toString();
    }

    /// <summary>
    /// 加密RFID卡加密区中的密码
    /// </summary>
    /// <param name="Str_P">用于加密或者解密的字符串</param>
    /// <returns>返回加密后或者解密后的字符串</returns>
    public static String DenCrypt_4(String Str_P)
    {
        StringBuffer sb = new StringBuffer();
        String Str = Str_P;
        String Key = "shanxi";
        String str1;
        String key1;
        int wh1;
        int wh2;

        for (int i = 0; i < Str_P.length(); i++)
        {
            str1 = Str.substring(0, 1);
            key1 = Key.substring(0, 1);
            Str = Str.substring(1, Str.length() - 1);
            Key = Key.substring(1, Key.length() - 1);
            //wh1 = Integer.parseInt(str1[0]);
           // wh2 = Asc(key1[0]);
            //tmpstr = tmpstr + Chr(wh1 ^ wh2);   // ord() 函数显示字符A 的ASCII 码
        }

        return sb.toString();
    }

}
