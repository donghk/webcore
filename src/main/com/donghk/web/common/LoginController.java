package com.donghk.web.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.donghk.core.json.JsonResult;
import com.donghk.core.security.BaseUserDetails;
import com.donghk.core.util.LoginUtils;
import com.donghk.entity.system.SysMenu;
import com.donghk.web.BaseController;

@Controller
public class LoginController extends BaseController {

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年5月20日
	 * @Description 登录页
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("common/login.jsp");
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年5月20日
	 * @Description 首页
	 * @param request
	 * @return
	 */
	@RequestMapping("/welcome")
	public ModelAndView welcome(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("common/welcome.jsp");
		return mv;
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年5月20日
	 * @Description 错误页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/error")
	public ModelAndView error(HttpServletRequest request) {
		return new ModelAndView("common/error.jsp");
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年5月20日
	 * @Description 权限判断页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/noauth")
	public ModelAndView noauth(HttpServletRequest request) {
		return new ModelAndView("common/noauth.jsp");
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年5月20日
	 * @Description 获取菜单
	 * @return
	 */
	@RequestMapping("/menu")
	@ResponseBody
	public JsonResult<String> menu() {
		BaseUserDetails userDetails = LoginUtils.getCurrentUser();
		List<SysMenu> menuList = userDetails.getUser().getMenuList();
		JSONArray jsonArray = JSONArray.fromObject(menuList);
		JsonResult<String> json = new JsonResult<String>();
		json.setResult(jsonArray.toString());
		return json;
	}

}
