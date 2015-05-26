package com.manager.sys.common;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;

public class StringUtil {
	public static DateFormat FORMATE = new SimpleDateFormat("yyyy-MM-dd-hhmmss");
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ");
	/**
	 * 获取当前时间
	 * @return
	 */
	public  String getDate(){
		return df.format(new Date());
	}

	/**
	 * 获取当前的Timestamp时间
	 * 
	 * @return
	 */
	public static Timestamp getNow() {
		Calendar cal = Calendar.getInstance();
		long date = cal.getTimeInMillis();
		return new Timestamp(date);
	}

	/**
	 * 转换Json数据
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	/**
	 * 将date转换为Timestamp
	 * 
	 * @param date
	 * @return
	 */
	public Timestamp formateToTimestamp(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		long time = cal.getTimeInMillis();
		return new Timestamp(time);
	}

	/**
	 * Date类型转换
	 * 
	 * @param date
	 * @return
	 */
	public static String formate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
		if (!isEmpty(date)) {
			return sdf.format(date);
		}
		return null;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object str) {
		if (!(null != str && !"".equals(str))) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (!(null != str && str.trim().length() > 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否为数字 '0'转化为数字时为 48 ,'9'转化为数字时为57
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str) {
		boolean isdigit = true;
		if (!isEmpty(str)) {
			for (int i = 0, j = str.length(); i < j; i++) {
				char c = str.charAt(i);
				int temp = c;
				if (temp >= 48 && temp <= 57) {
					continue;
				} else {
					isdigit = false;
				}
			}
		}
		return isdigit;
	}
	/**
	 * 将字符串数组转换为整型数组
	 * 
	 * @param idss
	 * @return
	 */
	public static Integer[] stringToInteger(String[] idss) {
		Integer[] iid = new Integer[0];
		if (idss != null && idss.length > 0) {
			iid = new Integer[idss.length];
			for (int i = 0; i < idss.length; i++) {
				iid[i] = Integer.valueOf(idss[i]);
			}
		}
		return iid;
	}
}
