package com.donghk.web.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.donghk.core.page.PageForJqGrid;
import com.donghk.entity.system.SysLog;
import com.donghk.service.system.SysLogService;
import com.donghk.web.BaseController;

/**
 * 
 * @author: donghaikang
 * @date: 2015年5月20日
 * @Description 日志管理
 */
@Controller
@RequestMapping("/system/log")
public class SysLogController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(SysLogController.class);

	@Autowired
	private SysLogService sysLogService;

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年5月20日
	 * @Description 进入日志管理页面
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("system/log/log-index.jsp");
		return view;
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年5月20日
	 * @Description 查询日志列表
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageForJqGrid<SysLog> list(PageForJqGrid<SysLog> page, SysLog search) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map = page.pageToMap(page, map);
		try {
			List<SysLog> list = sysLogService.getAllLog(map);
			if (list.size() > 0) {
				page.listToPage(page, list);
			}
		} catch (Exception e) {
			log.error("查询日志列表出现错误：{}", e.getMessage());
		}
		return page;
	}
}
