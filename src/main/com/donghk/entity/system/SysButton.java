package com.donghk.entity.system;

import org.apache.ibatis.type.Alias;

import com.donghk.entity.BaseEntity;

@Alias("SysButton")
@SuppressWarnings("serial")
public class SysButton extends BaseEntity {

	/**
	 * 按钮名称
	 */
	private String buttonName;

	/**
	 * 按钮编码
	 */
	private String buttonCode;

	/**
	 * 备注
	 */
	private String remark;

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getButtonCode() {
		return buttonCode;
	}

	public void setButtonCode(String buttonCode) {
		this.buttonCode = buttonCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
