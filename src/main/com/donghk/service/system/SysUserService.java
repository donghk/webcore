package com.donghk.service.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donghk.core.util.EmptyUtil;
import com.donghk.core.util.PasswordUtil;
import com.donghk.dao.system.SysUserMapper;
import com.donghk.entity.system.SysUser;

/**
 * 
 * @author: donghaikang
 * @date: 2015年7月23日
 * @Description 用户管理
 */
@Service
@SuppressWarnings("unchecked")
public class SysUserService {

	@Autowired
	private SysUserMapper sysUserMapper;

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月23日
	 * @Description 按条件获取用户列表
	 * @param map
	 * @return
	 */
	public List<SysUser> getAllUser(Map<String, Object> map) {

		List<SysUser> list = sysUserMapper.getAll(map);

		return list;
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年8月11日
	 * @Description 根据用户ID获取用户信息
	 * @param id
	 *            用户ID
	 * @return
	 */
	public SysUser getUserById(String id) {
		return (SysUser) sysUserMapper.get(id);
	}

	/**
	 * 
	 * @author donghaikang
	 * @date 2015年8月12日 下午4:39:23
	 * @Description: 保存用户信息
	 */
	public void saveUser(SysUser user) {

		if (EmptyUtil.isEmpty(user.getId())) {
			user.setPasswd(PasswordUtil.md5Encode(user.getPasswd()));
			sysUserMapper.insert(user);
		} else {
			sysUserMapper.update(user);
		}

	}

	/**
	 * 
	 * @author donghaikang
	 * @date 2015年8月18日 下午2:14:07
	 * @Description: 删除用户
	 */
	public void deleteUserByIds(String ids) {
		String[] delIds = ids.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", delIds);
		sysUserMapper.delete(map);
	}
	
	/**
	 * 
	 * @author donghaikang 
	 * @date 2015年8月18日 下午3:43:17 
	 * @Description: 根据用户名称获取用户信息
	 */
	public SysUser getUserByUserName(String userName, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("id", id);
		return sysUserMapper.getUserByUserName(map);
	}

}
