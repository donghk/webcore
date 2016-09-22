package com.donghk.dto.system;

import java.util.Date;

/**
 * 
 * @author: donghaikang
 * @date: 2015年5月24日
 * @Description 日志查询搜索条件
 */
public class SysLogSearchDTO {

	/**
	 * 操作人
	 */
	private String createUserName;

	/**
	 * 操作时间段开始
	 */
	private Date startDate;

	/**
	 * 操作时间段结束
	 */
	private Date endDate;

	/**
	 * 日志类型
	 */
	private String logType;

	/**
	 * 日志内容
	 */
	private String logDetail;

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

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
