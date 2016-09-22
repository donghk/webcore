package com.donghk.core.tags;

import java.io.IOException;
import java.util.List;

import com.donghk.core.ehcache.CacheName;
import com.donghk.core.ehcache.CacheUtils;
import com.donghk.entity.system.SysDict;

/**
 * 
 * @author: donghaikang
 * @date: 2015年6月11日
 * @Description
 */
public class DataDictUtils {

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年6月11日
	 * @Description 获取数据字典
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public static List<SysDict> getDataDictItems(String code) throws IOException {
		String[] codes = code.split(",");
		// 只有1级的数据字典，直接从缓存中返回
		if (codes.length == 1) {
			return ((SysDict) CacheUtils.get(CacheName.DATADICTCACHE, code)).getChildren();
			// 多级的数据字典，从缓存中逐级获取
		} else {
			SysDict result = (SysDict) CacheUtils.get(CacheName.DATADICTCACHE, codes[0]);
			for (int i = 1; i < codes.length; i++) {
				result = getAuDictResult(codes[i], result);
			}
			return result.getChildren();
		}
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年6月11日
	 * @Description
	 * @param code
	 *            本级数据字典编码
	 * @param dict
	 *            上级数据字典
	 * @return 本级数据字典
	 * @throws IOException
	 */
	public static SysDict getAuDictResult(String code, SysDict dict) throws IOException {
		SysDict result = new SysDict();
		List<SysDict> list = dict.getChildren();
		for (SysDict item : list) {
			if (code.equals(item.getDictCode())) {
				result = item;
			}
		}
		return result;
	}

	public static String getNameByValue(String code, String value) throws IOException {
		List<SysDict> itemList = getDataDictItems(code);
		if (itemList != null && itemList.size() > 0) {
			for (SysDict dataDictItem : itemList) {
				if (dataDictItem.getDictCode().equals(value)) {
					return dataDictItem.getDictName();
				}
			}
		}
		return value;
	}

}
