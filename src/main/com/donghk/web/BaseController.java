package com.donghk.web;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 
 * @author: donghaikang
 * @date: 2015年7月12日
 * @Description
 */
@Controller
public class BaseController {
	
	/**
	 * @Description: 处理页面参数序列化后数据类型的问题
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dataSdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dataTimesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dataSdf, true));
		binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(dataTimesdf, true));
		binder.registerCustomEditor(List.class, new CustomCollectionEditor(List.class, true));
	}
	
}
