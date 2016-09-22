package com.donghk.entity.system;

import com.donghk.entity.BaseEntity;

/**
 * 
 * @author: donghaikang
 * @date: 2015年5月24日
 * @Description 日志表
 */
@SuppressWarnings("serial")
public class SysLog extends BaseEntity {

	/**
	 * 日志类型
	 */
	private String logType;

	/**
	 * 日志内容
	 */
	private String logDetail;

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogDetail() {
		return logDetail;
	}

	public void setLogDetail(String logDetail) {
		this.logDetail = logDetail;
	}

}
