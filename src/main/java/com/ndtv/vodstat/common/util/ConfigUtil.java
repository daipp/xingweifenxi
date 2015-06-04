package com.ndtv.vodstat.common.util;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 项目参数工具类
 * 
 * @author 孙宇
 * 
 */
public class ConfigUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("config");

	/**
	 * 获得sessionInfo名字
	 * 
	 * @return
	 */
	public static final String getSessionInfoName() {
		return bundle.getString("sessionInfoName");
	}

	/**
	 * 通过键获取值
	 * 
	 * @param key
	 * @return
	 */
	public static final String get(String key) {
		return bundle.getString(key);
	}
	
	/**
	 * 通过键获取值
	 * @param key
	 * @return
	 */
	public static final Long getLong(String key) {
		return Long.parseLong(bundle.getString(key));
	}
	
	/**
	 * 通过键获取值
	 * @param key
	 * @return
	 */
	public static Set<String> getSet(String splitRegex,String key) {
		String[] sa = bundle.getString(key).split(splitRegex);
		Set<String> st = new HashSet();
		for(String str : sa){
			st.add(str);
		}
		return st;
	}
	
	/**
	 * 通过键获取值
	 * @param key
	 * @return
	 */
	public static Set<Long> getLongSet(String splitRegex,String key) {
		String[] sa = bundle.getString(key).split(splitRegex);
		Set<Long> st = new HashSet();
		for(String str : sa){
			st.add(Long.parseLong(str));
		}
		return st;
	}
}
