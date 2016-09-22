package com.donghk.core.poi;

import java.util.Map;

import com.donghk.core.poi.PoiEnums.CellDataType;
import com.donghk.core.poi.PoiEnums.CellIsBaseData;
import com.donghk.core.poi.PoiEnums.CellIsErrorDisplay;
import com.donghk.core.poi.PoiEnums.CellIsFixed;
import com.donghk.core.poi.PoiEnums.CellIsStatic;
import com.donghk.core.poi.PoiEnums.CellRule;

/**
 * @ClassName: CellOptions
 * @Description: 单元格设置
 * @author wu.jinglei@fescoadecco.com
 * @date 2014年6月10日 下午3:15:07
 * 
 */
public class CellOptions {

	/**
	 * 对象的属性名
	 */
	private String key;

	/**
	 * 列名
	 */
	private String colName;

	/**
	 * 是否有规则
	 */
	private CellRule cellRule;

	/**
	 * 规则内容
	 */
	private Object cellRuleValue;

	/**
	 * 规则异常时，是否跳过异常
	 */
	private boolean isKeepInput;

	/**
	 * 是否是数据字典
	 */
	private CellIsBaseData isBaseData = CellIsBaseData.FALSE;

	/**
	 * 数据字典CODE
	 */
	private String baseDataCode;

	/**
	 * 是否是固定值
	 */
	private CellIsFixed isFixedValue = CellIsFixed.FALSE;
	
	/**
	 * 固定值内容
	 */
	private Map<String, Object> fixedMap;

	/**
	 * 是否是常量
	 */
	private CellIsStatic cellIsStatic = CellIsStatic.FALSE;

	/**
	 * 常量值
	 */
	private String staticValue;
	
	/**
	 * 是否在导出错误信息时显示
	 */
	private CellIsErrorDisplay isErrorDisplay = CellIsErrorDisplay.FALSE;

	/**
	 * 子项
	 */
	private CellOptions[] subCells;

	/**
	 * cell类型
	 */
	private CellDataType cellDataType = CellDataType.AUTO;

	/**
	 * @author: jingleiw@rayootech.com
	 * @date: 2014年6月10日 下午5:00:42
	 * @Description:隐藏构造方法
	 */
	@SuppressWarnings("unused")
	private CellOptions() {

	}

	/**
	 * @author: jingleiw@rayootech.com
	 * @date: 2014年6月10日 下午5:08:32
	 * @Description:设置关键字与显示列名
	 * @param key
	 *        :属性名
	 * @param colName
	 *        :列名
	 */
	public CellOptions(String key, String colName) {
		this.key = key;
		this.colName = colName;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the colName
	 */
	public String getColName() {
		return colName;
	}

	/**
	 * @return the isBaseData
	 */
	public CellIsBaseData getIsBaseData() {
		return isBaseData;
	}

	/**
	 * @return the baseDataCode
	 */
	public String getBaseDataCode() {
		return baseDataCode;
	}

	/**
	 * @return the isFixedValue
	 */
	public CellIsFixed getIsFixedValue() {
		return isFixedValue;
	}

	/**
	 * @return the fixedMap
	 */
	public Map<String, Object> getFixedMap() {
		return fixedMap;
	}

	/**
	 * @return the cellRule
	 */
	public CellRule getCellRule() {
		return cellRule;
	}

	public CellIsErrorDisplay getIsErrorDisplay() {
		return isErrorDisplay;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "key : " + this.key + ", " + "colName : " + this.colName + ", " + "isBaseData : " + this.isBaseData.value + ", " + "baseDataCode : " + this.baseDataCode + ", " + "isFixedValue : "
				+ this.isFixedValue.value + ", " + "cellRule : " + this.cellRule.value;
	}

	/**
	 * @return the hasStaticValue
	 */
	public CellIsStatic getCellIsStatic() {
		return cellIsStatic;
	}

	/**
	 * @return the staticValue
	 */
	public String getStaticValue() {
		return staticValue;
	}

	/**
	 * @return the cellRuleValue
	 */
	public Object getCellRuleValue() {
		return cellRuleValue;
	}

	/**
	 * @return the subCells
	 */
	public CellOptions[] getSubCells() {
		return subCells;
	}

	/**
	 * @return the isKeepInput
	 */
	public boolean isKeepInput() {
		return isKeepInput;
	}

	/**
	 * @return the cellDataType
	 */
	public CellDataType getCellDataType() {
		return cellDataType;
	}

	/**
	 * @author: wujinglei
	 * @date: 2014-8-13 下午5:49:06
	 * @Description: 设置读取XLS单元格数据类型，按设置类型读取，出错时记录异常，跳过添加数据
	 * @param cellDataType
	 * @return
	 */
	public CellOptions addCellDataType(CellDataType cellDataType) {
		this.cellDataType = cellDataType;
		this.isKeepInput = false;
		return this;
	}

	/**
	 * @author: wujinglei
	 * @date: 2014-8-13 下午5:50:25
	 * @Description: 设置读取XLS单元格数据类型，按设置类型读取，出错时记录异常，按设置是否跳过添加数据
	 * @param cellDataType
	 * @param isKeepInput
	 * @return
	 */
	public CellOptions addCellDataType(CellDataType cellDataType, boolean isKeepInput) {
		this.cellDataType = cellDataType;
		this.isKeepInput = isKeepInput;
		return this;
	}

	/**
	 * @author: wujinglei
	 * @date: 2014-8-13 下午5:50:43
	 * @Description: 添加CELL规则功能
	 * @param cellRule
	 * @param cellRuleValue
	 * @param isKeepInput
	 * @return
	 */
	public CellOptions addCellRule(CellRule cellRule, Object cellRuleValue, boolean isKeepInput) {
		this.cellRule = cellRule;
		this.cellRuleValue = cellRuleValue;
		this.isKeepInput = isKeepInput;
		return this;
	}

	/**
	 * @author: wujinglei
	 * @date: 2014-8-13 下午5:51:00
	 * @Description: 添加数据字典CODE
	 * @param baseDataCode
	 * @return
	 */
	public CellOptions addBaseDataCode(String baseDataCode) {
		this.isBaseData = CellIsBaseData.TRUE;
		this.baseDataCode = baseDataCode;
		return this;
	}

	/**
	 * @author: wujinglei
	 * @date: 2014-8-13 下午5:51:17
	 * @Description: 添加固定项
	 * @param fixedMap
	 * @return
	 */
	public CellOptions addFixedMap(Map<String, Object> fixedMap) {
		this.isFixedValue = CellIsFixed.TRUE;
		this.fixedMap = fixedMap;
		return this;
	}

	/**
	 * @author: wujinglei
	 * @date: 2014-8-13 下午5:51:31
	 * @Description: 添加静态项
	 * @param hasStaticValue
	 * @param staticValue
	 * @return
	 */
	public CellOptions addStaticValue(CellIsStatic cellIsStatic, String staticValue) {
		this.cellIsStatic = CellIsStatic.TRUE;
		this.staticValue = staticValue;
		return this;
	}
	
	/**
	 * 
	 * @author: dong.haikang@fescoadecco.com
	 * @date: 2015年7月16日
	 * @Description 是否在导出错误信息时显示
	 * @param cellIsErrorDisplay
	 * @return
	 */
	public CellOptions addIsErrorDisplay(CellIsErrorDisplay cellIsErrorDisplay) {
		this.isErrorDisplay = cellIsErrorDisplay;
		return this;
	}

	/**
	 * @author: wujinglei
	 * @date: 2014-8-13 下午5:51:42
	 * @Description: 添加子项
	 * @param subCells
	 * @return
	 */
	public CellOptions addSubCells(CellOptions[] subCells) {
		this.subCells = subCells;
		return this;
	}
	
	
}