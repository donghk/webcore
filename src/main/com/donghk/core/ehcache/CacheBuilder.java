package com.donghk.core.ehcache;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

import com.donghk.entity.system.SysDict;
import com.donghk.service.cache.CacheService;

public class CacheBuilder {

	public CacheService cacheService;

	/**
	 * @throws IOException
	 * @Description: 自动载入
	 */
	private void init() throws IOException {
		putRolePath();
		putAllDataDict();
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年6月2日
	 * @Description 权限缓存
	 * @throws IOException
	 */
	private void putRolePath() throws IOException {
		Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

		// 将所有权限读取出来
		List<Map<String, String>> list = cacheService.getAllUrlAuthRel();

		Collection<ConfigAttribute> anonymousAtts = new ArrayList<ConfigAttribute>();
		ConfigAttribute anonymousCa = new SecurityConfig("ROLE_ANONYMOUS");
		anonymousAtts.add(anonymousCa);

		// 静态路径
		resourceMap.put("/", anonymousAtts);
		resourceMap.put("/login", anonymousAtts);
		resourceMap.put("/ace/**", anonymousAtts);
		resourceMap.put("/plugins/**", anonymousAtts);
		resourceMap.put("/statics/**", anonymousAtts);

		// 将所有权限下的URL对应的权限放入resourceMap中
		for (Map<String, String> map : list) {
			String path = map.get("URL");
			Collection<ConfigAttribute> atts = resourceMap.get(path);
			if (atts == null) {
				atts = new ArrayList<ConfigAttribute>();
			}
			ConfigAttribute ca = new SecurityConfig(map.get("RESOURCEID"));
			atts.add(ca);
			resourceMap.put(path, atts);
		}

		CacheEntity cacheEntity = new CacheEntity(CacheName.MENUCACHE, "MENU", resourceMap);
		CacheUtils.put(cacheEntity);
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年6月11日
	 * @Description 数据字典缓存
	 * @throws IOException
	 */
	private void putAllDataDict() throws IOException {

		// 获取所有的数据字典
		List<SysDict> allList = cacheService.getDataDictAll();
		// 初始化结果集
		List<SysDict> resultList = new ArrayList<SysDict>();

		Map<Integer, List<SysDict>> levelDictMap = new HashMap<Integer, List<SysDict>>();
		// 按层级分类
		for (SysDict item : allList) {
			List<SysDict> itemList = levelDictMap.get(item.getDictLevel());
			if (itemList == null) {
				itemList = new ArrayList<SysDict>();
				levelDictMap.put(item.getDictLevel(), itemList);
			}
			itemList.add(item);
		}
		Set<Integer> keySet = levelDictMap.keySet();
		// 讲子集对应放入
		for (int i = 1; i <= keySet.size(); i++) {
			if (i == 1) {
				resultList = levelDictMap.get(i);
			} else {
				for (SysDict partentItem : levelDictMap.get(i - 1)) {
					for (SysDict item : levelDictMap.get(i)) {
						if (partentItem.getId().equals(item.getParentId())) {
							List<SysDict> childrenList = partentItem.getChildren();
							if (childrenList == null) {
								childrenList = new ArrayList<SysDict>();
								partentItem.setChildren(childrenList);
							}
							childrenList.add(item);
							partentItem.setChildren(childrenList);
						}
					}
				}
			}
		}

		// 放入缓存
		for (SysDict resultItem : resultList) {
			CacheEntity cacheEntity = new CacheEntity(CacheName.DATADICTCACHE, resultItem.getDictCode(), resultItem);
			CacheUtils.put(cacheEntity);
		}

	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年6月2日
	 * @Description 更新所有缓存
	 * @throws IOException
	 */
	public void updateAll() throws IOException {
		init();
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

}
