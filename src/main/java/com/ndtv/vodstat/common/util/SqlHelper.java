package com.ndtv.vodstat.common.util;

public class SqlHelper {
	
	public static String blurQuery(String arg0) {
		String str = arg0;
		str = str.replace("/", "//");
		str = str.replaceAll("%", "/%");
		str = str.replaceAll("_", "/_");
		str = "%" + str + "%";
		return str;
	}

}
