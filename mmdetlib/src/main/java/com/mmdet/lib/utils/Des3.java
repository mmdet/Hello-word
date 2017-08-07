package com.mmdet.lib.utils;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;


public class Des3 {
	byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8 };
	
	static String key;
	
	public Des3(String key){
		this.key = key;
	}
	
	//加密(返回加密后的Base64字符串)
	 public String encode(String datas){
		 try {
			 byte[] keys=Key(key).getBytes("UTF-8");
			 byte[] data=datas.getBytes("UTF-8");
			 byte[] encode =des3EncodeECB(keys,data);
			// return new BASE64Encoder().encode(encode);
             return Base64.encodeToString(encode, Base64.DEFAULT);
         } catch (Exception e) {
			
		}
		 return null;
	 }
	 
	 //解密(返回解密后的字符串)
	 public String decode(String datas){
		 try {
			 byte[] keys=Key(key).getBytes("UTF-8");
			 //加密串
			// byte[] data=new BASE64Decoder().decodeBuffer(datas);
             byte[] data= Base64.decode(datas.getBytes(), Base64.DEFAULT);
			 byte[] decode = Des3.ees3DecodeECB(keys,data);
			 return new String(decode,"UTF-8");
		 } catch (Exception e) {
			 
		 }
		 return null;
	 }
	
	
	
	 /**
     * ECB加密,不要IV
     * @param key 密钥
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeECB(byte[] key, byte[] data)
            throws Exception {
 
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    }
    
    /**
     * ECB解密,不要IV
     * @param key 密钥
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] ees3DecodeECB(byte[] key, byte[] data)throws Exception {
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, deskey);
        byte[] bOut = cipher.doFinal(data);
        return bOut;
    } 
    
    
    /**
     * CBC加密
     * @param key 密钥
     * @param keyiv IV
     * @param data 明文
     * @return Base64编码的密文
     * @throws Exception
     */
    public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
 
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
 
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] bOut = cipher.doFinal(data);
 
        return bOut;
    }
    /**
     * CBC解密
     * @param key 密钥
     * @param keyiv IV
     * @param data Base64编码的密文
     * @return 明文
     * @throws Exception
     */
    public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
            throws Exception {
 
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
 
        Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(keyiv);
 
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
 
        byte[] bOut = cipher.doFinal(data);
 
        return bOut;
 
    }
    
    
    //设置key
    //不足24位用空格补齐，大于24位截取前24位
    //key建议不用中文
    private static String Key(String key)
	{
		int leng = key.length();
		String k = key;
		if (leng > 24)
		{
			k = key.substring(0, 23);
		}
		else if(leng < 24)
		{
			
			for(int i = 0;i < 24 - leng;i++)
				k+=" ";
		}
		
		return k;
	}
    
    
}
