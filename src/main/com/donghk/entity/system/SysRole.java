package com.donghk.entity.system;

import org.apache.ibatis.type.Alias;

import com.donghk.entity.BaseEntity;

@Alias("SysRole")
@SuppressWarnings("serial")
public class SysRole extends BaseEntity {

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色编码
	 */
	private String roleCode;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

}
