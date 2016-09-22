package com.donghk.dao.common;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.donghk.entity.system.SysButton;
import com.donghk.entity.system.SysMenu;
import com.donghk.entity.system.SysRole;
import com.donghk.entity.system.SysUser;

/**
 * 
 * @author: donghaikang
 * @date: 2015年5月18日
 * @Description 用户登录Mapper
 */
@Component
public interface LoginMapper {

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月11日
	 * @Description 根据用户姓名获取用户信息
	 * @param userName
	 *            用户名
	 * @return
	 */
	public SysUser getUserByUserName(String userName);

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月11日
	 * @Description 获取用户的角色信息
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public List<SysRole> getRoleByUserId(String userId);

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月11日
	 * @Description 获取用户菜单权限
	 * @param map
	 * @param roleList
	 *            角色
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public List<SysMenu> getMenuByUserIdRole(Map<String, Object> map);

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月11日
	 * @Description 获取用户上级菜单权限
	 * @param map
	 * @param roleList
	 *            角色
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public List<SysMenu> getParentMenuByUserIdRole(Map<String, Object> map);

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月11日
	 * @Description 获取用户按钮权限
	 * @param map
	 * @param roleList
	 *            角色
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public List<SysButton> getButtonByUserIdRole(Map<String, Object> map);

}
