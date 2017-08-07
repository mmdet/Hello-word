package com.hhzb.fntalm.utils;

import android.util.Log;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
public class SignUtils {

	private static final String ALGORITHM = "RSA";

	private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

	private static final String DEFAULT_CHARSET = "UTF-8";
	
	
	//支付宝签名

	public static String sign(String content, String privateKey) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
					Base64.decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);

			java.security.Signature signature = java.security.Signature
					.getInstance(SIGN_ALGORITHMS);

			signature.initSign(priKey);
			signature.update(content.getBytes(DEFAULT_CHARSET));

			byte[] signed = signature.sign();

			return Base64.encode(signed);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	
	
	
    /// 签名(进行升序排列)
    /// </summary>
    /// <param name="dicArrayPre">要签名的数组</param>
    /// <param name="key">安全校验码</param>
    /// <returns>签名结果字符串</returns>
    public static String BuildMysign(Map<String,String> dicArrayPre, String key)
    {
    	Map<String, String> dicArray = FilterPara(dicArrayPre);
        String prestr = CreateLinkString(dicArray);
        prestr = prestr + key;
        Log.i("prestr",prestr);
        String mysign = Sign(prestr);
        return mysign;
    }
	
 
    /// 除去数组中的空值和签名参数并以字母a到z的顺序排序
    /// </summary>
    /// <param name="dicArrayPre">过滤前的参数组</param>
    /// <returns>过滤后的参数组</returns>
    public static Map<String, String> FilterPara(Map<String,String> dicArrayPre)
    {
    	Map<String, String> dicArray = new TreeMap<String, String>();
    	Set<String> keySet = dicArrayPre.keySet();
    	Iterator<String> iter = keySet.iterator();
    	while (iter.hasNext()) {
            String key = iter.next();
            dicArray.put(key.toLowerCase(), dicArrayPre.get(key));
            //按Key值的 a-z的升序排列
        }
        return dicArray;
    }
    
   
    /// 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
    /// </summary>
    /// <param name="sArray">需要拼接的数组</param>
    /// <returns>拼接完成以后的字符串</returns>
    public static String CreateLinkString(Map<String, String> dicArray)
    {
        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> entry : dicArray.entrySet()) {  
        	sb.append(entry.getKey() + "=" + entry.getValue() + "&");
        }  
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
    

    /// 签名（具体实现方法）
    /// </summary>
    /// <param name="prestr">过滤后升序的字符串</param>
    /// <returns>签名结果MD5字符串</returns>
    public static String Sign(String prestr)
    
    {
    	return MD5Util.MD5Encode(prestr, "UTF-8");
    }
    

}

