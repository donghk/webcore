package com.donghk.entity.system;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.donghk.entity.BaseEntity;

/**
 * 
 * @author: donghaikang
 * @date: 2015年7月12日
 * @Description 数据字典
 */
@Alias("SysDict")
@SuppressWarnings("serial")
public class SysDict extends BaseEntity {

	/**
	 * 上级ID
	 */
	private String parentId;

	/**
	 * 名称
	 */
	private String dictName;

	/**
	 * 编码
	 */
	private String dictCode;

	/**
	 * 层级
	 */
	private Integer dictLevel;

	/**
	 * 排序
	 */
	private Integer dictOrder;

	/**
	 * 子集
	 */
	private List<SysDict> children;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public Integer getDictLevel() {
		return dictLevel;
	}

	public void setDictLevel(Integer dictLevel) {
		this.dictLevel = dictLevel;
	}

	public Integer getDictOrder() {
		return dictOrder;
	}

	public void setDictOrder(Integer dictOrder) {
		this.dictOrder = dictOrder;
	}

	public List<SysDict> getChildren() {
		return children;
	}

	public void setChildren(List<SysDict> children) {
		this.children = children;
	}

}
