package com.donghk.entity.system;

import org.apache.ibatis.type.Alias;

import com.donghk.entity.BaseEntity;

@Alias("SysUser")
@SuppressWarnings("serial")
public class SysUser extends BaseEntity {

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String passwd;

	/**
	 * 真实姓名
	 */
	private String realName;

	/**
	 * 备注
	 */
	private String remark;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
