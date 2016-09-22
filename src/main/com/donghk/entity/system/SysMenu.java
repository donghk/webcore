package com.donghk.entity.system;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.donghk.entity.BaseEntity;

@Alias("SysMenu")
@SuppressWarnings("serial")
public class SysMenu extends BaseEntity {

	/**
	 * 上级ID
	 */
	private String parentId;

	/**
	 * 菜单名称
	 */
	private String menuName;

	/**
	 * 菜单路径
	 */
	private String menuPath;

	/**
	 * 菜单图标
	 */
	private String menuIcon;

	/**
	 * 菜单排序
	 */
	private String menuOrder;

	/**
	 * 菜单的子菜单集合，不对应数据库字段
	 */
	private List<SysMenu> children = new ArrayList<SysMenu>();

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(String menuOrder) {
		this.menuOrder = menuOrder;
	}

	public List<SysMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}

}
