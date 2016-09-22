package com.donghk.core.util;

import java.util.ResourceBundle;

/**
 * 读取配置
 *
 */
public class ConfigUtil {

	/**
	 * 读取配置文件
	 */
	private static final ResourceBundle resource = ResourceBundle.getBundle("config/sys");

	private ConfigUtil() {

	}

	/**
	 * 读取配置的值
	 */
	public static String readValue(String key) {
		return resource.getString(key);
	}

}
