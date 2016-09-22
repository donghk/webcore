package com.donghk.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author: donghaikang
 * @date: 2015年7月12日
 * @Description 日期类型工具类
 */
public class DateUtils {

	public final static String SDF_YMD1 = "yyyyMMdd";
	public final static String SDF_YMD2 = "yyyy-MM-dd";
	public final static String SDF_YMD3 = "yyyy/MM/dd";
	public final static String SDF_YMD4 = "yyyyMM";
	public final static String SDF_YMD5 = "yyyy-MM";
	public final static String SDF_YMD6 = "yyyy/MM";

	/**
	 * 时间转String
	 * 
	 * @param date
	 * @param patten
	 * @return
	 */
	public static String parseFromDate(Date date, String patten) {
		if (date != null) {
			if (patten == null) {
				patten = "yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(patten);
			return sdf.format(date);
		}
		return null;
	}

	/**
	 * String转时间
	 * 
	 * @param src
	 * @param patten
	 * @return
	 */
	public static Date parseFromStr(String str, String patten) {
		if (!StringUtils.isBlank(str)) {
			if (patten == null) {
				patten = "yyyy-MM-dd";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(patten);
			try {
				return sdf.parse(str);
			} catch (ParseException e) {
				return null;
			}
		}
		return null;
	}

}
