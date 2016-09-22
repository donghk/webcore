package com.donghk.core.util;

import java.io.File;

import org.springframework.web.context.ContextLoader;

public class CommonUtil {

	/**
	 * @Description: 取出当前应用的物理地址
	 */
	public static final String getRealPath() {
		return System.getProperty("webcore.root");
	}

	/**
	 * 上下文变量，用于返回前台JSP使用，作为整个项目的上下文
	 */
	public static String CONTEXT_PATH = ConfigUtil.readValue("CONTEXT_PATH");

	/**
	 * 临时文件目录
	 */
	public static final String getTempPath() {
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		path = path + "temp" + File.separator;
		return path;
	}

}
