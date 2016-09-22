package com.donghk.dao.cache;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.donghk.dao.BaseMapper;
import com.donghk.entity.system.SysDict;

/**
 * 
 * @author: donghaikang
 * @date: 2015年5月20日
 * @Description 缓存管理
 */
@Component
public interface CacheMapper extends BaseMapper {
	
	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年5月20日
	 * @Description 获取所有URL对应的权限
	 * @return
	 */
	public List<Map<String, String>> getAllUrlAuthRel();
	
	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年6月2日
	 * @Description 获取所有的数据字典
	 * @return
	 */
	public List<SysDict> getDataDictAll();
	
}