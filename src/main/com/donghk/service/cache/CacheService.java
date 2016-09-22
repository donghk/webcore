package com.donghk.service.cache;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donghk.dao.cache.CacheMapper;
import com.donghk.entity.system.SysDict;

/**
 * 
 * @author: donghaikang
 * @date: 2015年5月20日
 * @Description 缓存管理
 */
@Service
public class CacheService {

	@Autowired
	public CacheMapper cacheMapper;

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年5月20日
	 * @Description 获取所有URL对应的权限
	 * @return
	 */
	public List<Map<String, String>> getAllUrlAuthRel() {
		return cacheMapper.getAllUrlAuthRel();
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年6月2日
	 * @Description 获取所有的数据字典
	 * @return
	 */
	public List<SysDict> getDataDictAll() {
		return cacheMapper.getDataDictAll();
	}

}
