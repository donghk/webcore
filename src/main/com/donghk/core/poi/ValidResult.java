package com.donghk.core.poi;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author: dong.haikang@fescoadecco.com
 * @date: 2015年7月15日
 * @Description 验证结果
 */
@SuppressWarnings({ "rawtypes" })
public class ValidResult {

	/**
	 * 原始数据
	 */
	private final List originalList = new ArrayList();

	/**
	 * 验证成功的结果集
	 */
	private final List successList = new ArrayList();

	/**
	 * 验证失败的结果集
	 */
	private final List failList = new ArrayList();

	/**
	 * 异常信息
	 */
	private String exceptionError;

	public ValidResult(SheetOptions[] sheets) {
	}

	public List getOriginalList() {
		return originalList;
	}

	public List getSuccessList() {
		return successList;
	}

	public List getFailList() {
		return failList;
	}

	public String getExceptionError() {
		return exceptionError;
	}

	public void setExceptionError(String exceptionError) {
		this.exceptionError = exceptionError;
	}

}
