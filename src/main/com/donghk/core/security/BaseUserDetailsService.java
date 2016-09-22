package com.donghk.core.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.donghk.core.util.EmptyUtil;
import com.donghk.dto.common.LoginUserDetail;
import com.donghk.entity.system.SysButton;
import com.donghk.entity.system.SysMenu;
import com.donghk.entity.system.SysRole;
import com.donghk.entity.system.SysUser;
import com.donghk.service.common.LoginService;
import com.google.common.collect.Sets;

/**
 * 
 * @author: donghaikang
 * @date: 2015年7月11日
 * @Description 登录验证并获取用户信息
 */
@SuppressWarnings({ "deprecation" })
public class BaseUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(BaseUserDetailsService.class);

	@Autowired
	private LoginService loginService;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// 获取用户信息
		SysUser user = loginService.getUserByUserName(username);
		// 判断用户是否存在
		if (user == null) {
			throw new UsernameNotFoundException("用户名或密码不正确!");
		}

		// 登录信息
		LoginUserDetail loginUser = new LoginUserDetail();
		// 用户ID
		loginUser.setUserId(user.getId());
		// 用户名称
		loginUser.setUserName(user.getUserName());
		// 用户真实姓名
		loginUser.setRealName(user.getRealName());

		Set<GrantedAuthority> authSet = Sets.newHashSet();

		// 获取用户的角色信息
		List<SysRole> roleList = loginService.getRoleByUserId(user.getId());

		if (roleList.size() == 0) {
			log.warn(username + "用户无角色信息");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getId());
		map.put("roleList", roleList);

		// 获取用户菜单权限
		List<SysMenu> menuList = loginService.getMenuByUserIdRole(map);

		if (menuList.size() == 0) {
			log.warn(username + "用户无菜单信息");
		}

		// 把用户菜单权限存入SS3中
		for (SysMenu item : menuList) {
			if (!EmptyUtil.isEmpty(item.getId())) {
				authSet.add(new GrantedAuthorityImpl(item.getId()));
			}
		}

		// 获取用户上级菜单权限
		List<SysMenu> parentMenuList = loginService.getParentMenuByUserIdRole(map);

		if (parentMenuList.size() == 0) {
			log.warn(username + "用户无上级菜单信息");
		}

		// 合并上下级菜单
		for (SysMenu parent : parentMenuList) {
			for (SysMenu child : menuList) {
				if (parent.getId().equals(child.getParentId())) {
					parent.getChildren().add(child);
				}
			}
		}

		// 把用户菜单存入登录用户信息中
		loginUser.setMenuList(parentMenuList);

		// 获取用户按钮权限
		List<SysButton> buttonList = loginService.getButtonByUserIdRole(map);

		if (buttonList.size() > 0) {
			// 把用户按钮权限存入SS3中
			for (SysButton item : buttonList) {
				authSet.add(new GrantedAuthorityImpl(item.getId()));
			}
		}

		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		BaseUserDetails baseUserDetails = new BaseUserDetails(user.getUserName(), user.getPasswd(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authSet);
		// 设置登录用户信息
		baseUserDetails.setUser(loginUser);

		return baseUserDetails;
	}

}
