package com.donghk.core.tags;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.donghk.entity.system.SysDict;

public class DataDictTag extends TagSupport {

	private static final long serialVersionUID = -8067977943211881109L;

	private String code;

	private String id;

	private String name;

	private String classStyle;

	private String resultType;

	private String readonly;

	private String value;

	private String placeholder;

	@Override
	public int doStartTag() throws JspException {
		try {

			JspWriter out = this.pageContext.getOut();

			if (StringUtils.isBlank(code)) {
				out.println("错误：找不到'" + code + "'");
				return SKIP_BODY;
			}

			List<SysDict> itemList = DataDictUtils.getDataDictItems(code);

			if (itemList == null) {
				out.println("错误: 值为空");
				return SKIP_BODY;
			}

			if ("text".equals(resultType)) {
				if (!StringUtils.isBlank(value)) {
					for (SysDict dictItem : itemList) {
						if (value.equals(dictItem.getDictCode())) {
							out.print(dictItem.getDictName());
							break;
						}
					}
				} else {
					out.print("");
				}
			} else if ("select".equals(resultType)) {
				out.print("<select ");
				if (!StringUtils.isBlank(id)) {
					out.print("id='" + id + "' ");
				}
				if (!StringUtils.isBlank(name)) {
					out.print("name='" + name + "' ");
				}
				if (!StringUtils.isBlank(classStyle)) {
					out.print("class='" + classStyle + "' ");
				}
				if (!"false".equals(readonly)) {
					out.print("readonly='true' ");
				}
				out.print(">");

				if (StringUtils.isBlank(placeholder)) {
					placeholder = "";
				}

				out.print("<option value=''>请选择" + placeholder + "</option>");
				for (SysDict dictItem : itemList) {
					out.print("<option value='" + dictItem.getDictCode() + "'");
					if (!StringUtils.isBlank(value)) {
						if (value.equals(dictItem.getDictCode())) {
							out.print(" selected='true' ");
						}
					}
					out.print(">" + dictItem.getDictName() + "</option>");
				}
				out.print("</select>");
			} else if ("dictList".equals(resultType)) {
				out.print("<ul id='dict-" + code + "' style='display: none'>");
				for (SysDict dict : itemList) {
					out.print("<li id='dict-" + dict.getDictCode() + "'");
					out.print(">" + dict.getDictName() + "</li>");
				}
				out.print("</ul>");
			} else if ("input".equals(resultType)) {
				out.print("<input type='text' ");
				if (!StringUtils.isBlank(id)) {
					out.print("id='" + id + "' ");
				}
				if (!StringUtils.isBlank(name)) {
					out.print("name='" + name + "' ");
				}
				if (!StringUtils.isBlank(classStyle)) {
					out.print("class='" + classStyle + "' ");
				}
				if (!"false".equals(readonly)) {
					out.print("readonly='true' ");
				}
				for (SysDict dictItem : itemList) {
					if (!StringUtils.isBlank(value)) {
						if (value.equals(dictItem.getDictCode())) {
							out.print(" value='" + dictItem.getDictName() + "' ");
						}
					}
				}
				out.print(">");
			}
		} catch (Exception e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the classStyle
	 */
	public String getClassStyle() {
		return classStyle;
	}

	/**
	 * @param classStyle
	 *            the classStyle to set
	 */
	public void setClassStyle(String classStyle) {
		this.classStyle = classStyle;
	}

	/**
	 * @return the resultType
	 */
	public String getResultType() {
		return resultType;
	}

	/**
	 * @param resultType
	 *            the resultType to set
	 */
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the readonly
	 */
	public String getReadonly() {
		return readonly;
	}

	/**
	 * @param readonly
	 *            the readonly to set
	 */
	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	/**
	 * @return the placeholder
	 */
	public String getPlaceholder() {
		return placeholder;
	}

	/**
	 * @param placeholder
	 *            the placeholder to set
	 */
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}

}