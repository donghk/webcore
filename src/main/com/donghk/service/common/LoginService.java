package com.donghk.service.common;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donghk.dao.common.LoginMapper;
import com.donghk.entity.system.SysButton;
import com.donghk.entity.system.SysMenu;
import com.donghk.entity.system.SysRole;
import com.donghk.entity.system.SysUser;

/**
 * 
 * @author: donghaikang
 * @date: 2015年5月18日
 * @Description 用户登录Service
 */
@Service
public class LoginService {

	@Autowired
	private LoginMapper loginMapper;

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月11日
	 * @Description 根据用户姓名获取用户信息
	 * @param userName
	 *            用户名
	 * @return
	 */
	public SysUser getUserByUserName(String userName) {
		SysUser user = loginMapper.getUserByUserName(userName);
		if (user != null) {
			if (user.getUserName().toLowerCase().equals(userName.toLowerCase())) {
				return user;
			}
		}
		return null;
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月11日
	 * @Description 获取用户的角色信息
	 * @param userId
	 *            用户ID
	 * @return
	 */
	public List<SysRole> getRoleByUserId(String userId) {
		return loginMapper.getRoleByUserId(userId);
	}

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
	public List<SysMenu> getMenuByUserIdRole(Map<String, Object> map) {
		return loginMapper.getMenuByUserIdRole(map);
	}

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
	public List<SysMenu> getParentMenuByUserIdRole(Map<String, Object> map) {
		return loginMapper.getParentMenuByUserIdRole(map);
	}

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
	public List<SysButton> getButtonByUserIdRole(Map<String, Object> map) {
		return loginMapper.getButtonByUserIdRole(map);
	}

}
