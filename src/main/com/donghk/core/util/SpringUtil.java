package com.donghk.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @ClassName: SpringUtil
 * @Description: Spring工具类，提供取得Spring配置文件中定义的Bean的方法
 *
 */
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {

		// 在 SpringContextLoaderListener 中调用此方法

		SpringUtil.applicationContext = arg0;
	}

	public static Object getBean(String name) {

		// applicationContext 已在 SpringContextLoaderListener 中装载

		return applicationContext.getBean(name);
	}
}
