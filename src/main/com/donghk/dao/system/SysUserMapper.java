package com.donghk.dao.system;

import java.util.Map;

import com.donghk.dao.BaseMapper;
import com.donghk.entity.system.SysUser;

/**
 * 
 * @author: donghaikang
 * @date: 2015年7月23日
 * @Description 用户管理
 */
public interface SysUserMapper extends BaseMapper {

	/**
	 * 
	 * @author donghaikang 
	 * @date 2015年8月18日 下午3:43:17 
	 * @Description: 根据用户名称获取用户信息
	 */
	public SysUser getUserByUserName(Map<String, Object> map);

}
