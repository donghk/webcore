package com.donghk.core.json;

import java.util.HashMap;
import java.util.Map;

/**
 * json返回结果 DTO
 * 
 * @param <T>
 *            结果类
 */
public class JsonResult<T> {

	/**
	 * 成功状态
	 */
	public static final String SUCCESS = "success";

	/**
	 * 出错状态
	 */
	public static final String FAIL = "fail";

	/**
	 * 结果状态，默认成功
	 */
	private String status = SUCCESS;

	/**
	 * 结果信息
	 */
	private String msg = "";

	private Map<String, Object> property = new HashMap<String, Object>();

	/**
	 * 具体操作的结果信息对象
	 */
	private T result;

	public Map<String, Object> getProperty() {
		return property;
	}

	public void setProperty(Map<String, Object> property) {
		this.property = property;
	}

	public void setProperty(String key, Object val) {
		property.put(key, val);
	}

	/**
	 * @return 结果状态
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            结果状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return 结果信息
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            结果信息
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return 结果对象
	 */
	public T getResult() {
		return result;
	}

	/**
	 * @param result
	 *            结果对象
	 */
	public void setResult(T result) {
		this.result = result;
	}

	public JsonResult(String status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public JsonResult() {

	}
}
