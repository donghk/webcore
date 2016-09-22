package com.donghk.core.poi;

/**
 * 
 * @author: dong.haikang@fescoadecco.com
 * @date: 2015年7月15日
 * @Description 错误记录
 */
public class ErrorRecord {

	/**
	 * sheet序号
	 */
	private int sheetNum;

	/**
	 * 行号
	 */
	private int row;

	/**
	 * 错误信息
	 */
	private String errorMsg;

	public int getSheetNum() {
		return sheetNum;
	}

	public void setSheetNum(int sheetNum) {
		this.sheetNum = sheetNum;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
