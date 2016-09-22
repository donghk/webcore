package com.donghk.core.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 字符串处理工具类
 */
public class StringUtil {

	/**
	 * 将email转换成带*的email。 邮箱加密规则。例如：jianjunwang@163.com 加密为jianjunwa**@**3.com
	 * 
	 * @param email
	 *            邮箱
	 * @return
	 */
	public static String getEnctyptEmail(String email) {
		return StringUtil.trim(email).replaceAll("[a-z0-9]{2}@[a-z0-9]{2}", "**@**");
	}

	/**
	 * 将phone转换成带*的phone
	 * 
	 * @param phone
	 *            电话号码
	 * @return
	 */
	public static String getEnctyptPhone(String phone) {
		return StringUtil.trim(phone).replaceAll("(^[0-9]{4})([0-9]{4})", "$1****");
	}

	/**
	 * 将地址转换成带*的地址
	 * 
	 * @param address
	 *            地址
	 * @return
	 */
	public static String getEnctyptAddress(String address) {
		return StringUtil.trim(address).replaceAll("\\d", "*");
	}

	/**
	 * 将中文地址转换成带*的地址
	 * 
	 * @param address
	 *            地址
	 * @return
	 */
	public static String getEnctyptAddressZh(String address) {
		if (Pattern.compile("\\d", Pattern.CASE_INSENSITIVE).matcher(StringUtil.trim(address)).find()) {
			return StringUtil.trim(address).replaceAll("\\d", "*");
		} else if (StringUtil.trim(address).length() > 5) {
			return StringUtil.trim(address).replaceAll("[\\S\\s]{5}", "*****");
		} else {
			return "*****";
		}
	}

	/**
	 * 获取验证码
	 * 
	 * @return
	 */
	public static String getAuthenCode() {
		StringBuffer code = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int num = (int) (Math.random() * 10);
			code.append(num + "");
		}
		return code.toString();
	}

	/**
	 * 重载方法
	 * 
	 * @param s
	 *            对象
	 * @return
	 */
	public static String nullValue(Object s) {
		return s == null ? "" : s.toString();
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param s
	 *            字符串
	 * @return
	 */
	public static boolean isBlank(Object s) {
		return s == null || nullValue(s).trim().length() == 0;
	}

	/**
	 * @param queryString
	 * @return
	 */
	public static boolean isNotBlank(Object s) {
		return !isBlank(s);
	}

	public static String trim(Object o) {
		String s = StringUtil.nullValue(o);
		s = s.replaceAll("　", " ");
		s = s.replaceAll("\\s+", " ");
		return s.trim();
	}

	public static String upperCase(String s) {
		return StringUtil.nullValue(s).toUpperCase();
	}

	public static Integer toInt(String s) {
		try {
			return Integer.valueOf(s);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 
	 * @param source
	 *            类中数据
	 * @param compare
	 *            数据库中数据
	 * @return 需要忽略的数据
	 */
	public static List<String> CompareNotExist(List<String> source, List<String> compare) {
		source.removeAll(compare);
		return source;
	}

	public static String getNumberFromString(String s) {
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(s);
		return m.replaceAll("").trim();
	}

	/**
	 * 统计一个字符串中出现某个子字符串中的次数
	 * 
	 * @param original
	 * @param sub
	 * @return
	 */
	public static int stringCount(String original, String sub) {
		if (StringUtil.isBlank(original) || StringUtil.isBlank(sub)) {
			return 0;
		}
		int count = 0;
		int index = 0;
		while (index != -1) {
			index = original.indexOf(sub);
			if (index != -1) {
				count++;
				if (original.length() >= sub.length()) {
					original = original.substring(index + sub.length());
				} else {
					break;
				}
			}
		}
		return count;
	}

	/**
	 * 去除字符串中的空格、回车、换行符、制表符，本方法采用的是java的正则表达式 魏佳
	 * 
	 * @return
	 */
	public static String trimStr(String str) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		str = str.replace((char) 12288, ' ');// 去除全角空格
		Matcher m = p.matcher(str);
		String returnString = m.replaceAll("");
		return returnString;
	}
}
