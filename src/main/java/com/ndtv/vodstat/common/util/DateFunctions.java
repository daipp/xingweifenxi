package com.ndtv.vodstat.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFunctions {

	public static int dateCompare(Date firstDate, Date secondDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			return df.parse(df.format(firstDate)).compareTo(df.parse(df.format(secondDate)));
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("被比较参数错误!");
		}
	}

	public static Date addCalendarFeild(Date date,int calendarField, int amount){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(calendarField, amount);
		return ca.getTime();
	}
	
	public static int getCalendarFeild(Date date,int calendarField){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		return ca.get(calendarField);
	}
	
	public static Date setCalendarFeild(Date date,int calendarField,int calendarValue){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(calendarField, calendarValue);
		return ca.getTime();
	}
	
	public static Date strToDate(String yyyyMMdd){
		if(yyyyMMdd == null){
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		if(yyyyMMdd.indexOf("-") > 0){
			df = new SimpleDateFormat("yyyy-MM-dd");
		}
		Date d;
		try {
			d = df.parse(yyyyMMdd);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return d;
	}
	
	public static String dateToStr(Date d, String simpleDateFormat){
		SimpleDateFormat df = new SimpleDateFormat(simpleDateFormat);
		return df.format(d);
	}
	
	/**
	 * 截掉时间部分
	 * @param date 日期时间
	 * @return 日期
	 */
	public static Date dateTruncate(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			return df.parse(df.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 取该日最大时间
	 * @param date 日期时间
	 * @return 日期
	 */
	public static Date dateMax(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}
	
	public static Date getMonthFirstDay(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DATE, 1);
		return dateTruncate(ca.getTime());
	}
	
	public static Date getMonthLastDay(Date date){
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateMax(ca.getTime());
	}
}
