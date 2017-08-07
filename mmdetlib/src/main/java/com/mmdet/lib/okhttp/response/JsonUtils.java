package com.mmdet.lib.okhttp.response;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JsonUtils {
	 private static Gson gson = null;
	    static {
	        if (gson == null) {
	            gson = new Gson();
	        }
	    }
	/**
	 * json数组转list
	 * @param s
	 * @param cls
	 * @return
	 */
	public static <T> List<T> JsonToList(String s, Class<T[]> cls) {
        T[] arr = new Gson().fromJson(s, cls);
        List list = Arrays.asList(arr);
        List arrayList = new ArrayList(list);
        return  arrayList;
    }
	
	/**
     * json转成bean
     * 
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T JsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }
    
    /**
     * json转成list中有map的类
     * 
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> JsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }
    
    /**
     * json转成map
     * 
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> JsonToMap(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
    
    /**
     * 对象转成json
     * 
     * @param object
     * @return
     */
    public static String BeanToJson(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }
}
