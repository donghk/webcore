package com.donghk.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author: donghaikang
 * @date: 2015年6月3日
 * @Description
 */
@SuppressWarnings("serial")
public class BaseEntity implements Serializable {

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 创建人ID
	 */
	private String createUserId;

	/**
	 * 创建人姓名
	 */
	private String createUserName;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 修改人ID
	 */
	private String updateUserId;

	/**
	 * 修改人姓名
	 */
	private String updateUserName;

	/**
	 * 修改时间
	 */
	private Date updateDate;

	/**
	 * 是否有效（1:是, 0:否）
	 */
	private Integer isValid;

	/**
	 * 是否已删除（1:是, 0:否）
	 */
	private Integer isDel;

	/**
	 * @author: donghaikang
	 * @date: 2015年6月2日
	 * @Description 是否
	 */
	public enum YN {

		/**
		 * 否
		 */
		NO("否", 0),

		/**
		 * 是
		 */
		YES("是", 1);

		private String name;

		private Integer value;

		private YN(String displayName, Integer value) {
			this.name = displayName;
			this.value = value;
		}

		public static String getDisPlayName(Integer index) {

			for (YN item : YN.values()) {
				if (item.value.equals(index)) {
					return item.name;
				}
			}
			return "";
		}

		public static Integer getDisPlayValue(String index) {

			for (YN item : YN.values()) {
				if (item.name.equals(index)) {
					return item.value;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年6月5日
	 * @Description 是否有效
	 */
	public enum ISVALID {

		/**
		 * 无效
		 */
		NO("无效", 0),

		/**
		 * 有效
		 */
		YES("有效", 1);

		private String name;

		private Integer value;

		private ISVALID(String displayName, Integer value) {
			this.name = displayName;
			this.value = value;
		}

		public static String getDisPlayName(Integer index) {

			for (ISVALID item : ISVALID.values()) {
				if (item.value.equals(index)) {
					return item.name;
				}
			}
			return "";
		}

		public static Integer getDisPlayValue(String index) {

			for (ISVALID item : ISVALID.values()) {
				if (item.name.equals(index)) {
					return item.value;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

}
