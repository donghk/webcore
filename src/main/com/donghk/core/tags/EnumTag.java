package com.donghk.core.tags;

import java.lang.reflect.Method;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author donghaikang
 * 
 */
public class EnumTag extends TagSupport {

	private static final long serialVersionUID = -8067977943211881109L;

	private String path;

	private String id;

	private String name;

	private String classStyle;

	private String resultType;

	private String readonly;

	private String value;

	private String placeholder;

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int doStartTag() throws JspException {
		try {

			JspWriter out = this.pageContext.getOut();

			Class mapperClass = Class.forName("com.donghk.entity." + path);
			Method getValue = mapperClass.getDeclaredMethod("getValue");
			Method getName = mapperClass.getDeclaredMethod("getName");

			if (StringUtils.isBlank(path)) {
				out.println("错误: 找不到'" + path + "'路径");
				return SKIP_BODY;
			}

			if (mapperClass.getEnumConstants() == null) {
				out.println("错误: 值为空");
				return SKIP_BODY;
			}

			if ("text".equals(resultType)) {
				if (!StringUtils.isBlank(value)) {
					for (Object obj : mapperClass.getEnumConstants()) {
						if (value.equals(getValue.invoke(obj).toString())) {
							out.print(getName.invoke(obj));
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
				if ("true".equals(readonly)) {
					out.print("readonly='true' ");
				}
				out.print(">");

				if (StringUtils.isBlank(placeholder)) {
					placeholder = "";
				}

				out.print("<option value=''>请选择" + placeholder + "</option>");
				for (Object obj : mapperClass.getEnumConstants()) {
					out.print("<option value='" + getValue.invoke(obj) + "'");
					if (!StringUtils.isBlank(value)) {
						if (value.equals(getValue.invoke(obj).toString())) {
							out.print(" selected='true' ");
						}
					}
					out.print(">" + getName.invoke(obj) + "</option>");
				}
				out.print("</select>");
			} else if ("enumList".equals(resultType)) {
				out.print("<ul id='path-" + name + "' style='display: none'>");
				for (Object obj : mapperClass.getEnumConstants()) {
					out.print("<li id='path-" + getValue.invoke(obj) + "'");
					out.print(">" + getName.invoke(obj) + "</li>");
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
				if ("true".equals(readonly)) {
					out.print("readonly='true' ");
				}
				for (Object obj : mapperClass.getEnumConstants()) {
					if (!StringUtils.isBlank(value)) {
						if (value.equals(getValue.invoke(obj).toString())) {
							out.print(" value='" + getName.invoke(obj) + "' ");
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
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
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