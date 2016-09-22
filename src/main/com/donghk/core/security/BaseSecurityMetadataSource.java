package com.donghk.core.security;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import com.donghk.core.ehcache.CacheName;
import com.donghk.core.ehcache.CacheUtils;

/**
 * 
 * @author: donghaikang
 * @date: 2015年5月18日
 * @Description URL权限控制
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BaseSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private AntPathMatcher urlMatcher = new AntPathMatcher();

	private final Logger log = Logger.getLogger(getClass().getName());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.SecurityMetadataSource#getAttributes
	 * (java.lang.Object)
	 */
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl();
		if (log.isDebugEnabled()) {
			log.debug("请求地址为：" + url);
		}
		try {
			// 获取所有的菜单权限
			Map<String, Collection<ConfigAttribute>> resourceMap = (Map) CacheUtils.get(CacheName.MENUCACHE, "MENU");
			// 匹配当前路径的权限，交由SS3处理
			Iterator<String> ite = resourceMap.keySet().iterator();
			while (ite.hasNext()) {
				String resURL = ite.next();
				if (urlMatcher.match(resURL, url)) {
					return resourceMap.get(resURL);
				}
			}
		} catch (Exception e) {
		}

		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

}
