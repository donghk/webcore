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

import com.donghk.core.exception.BaseException;
import com.donghk.core.json.JsonResult;
import com.donghk.core.page.PageForJqGrid;
import com.donghk.core.util.EmptyUtil;
import com.donghk.entity.system.SysUser;
import com.donghk.service.system.SysLogService;
import com.donghk.service.system.SysUserService;
import com.donghk.web.BaseController;

@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(SysLogController.class);

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysLogService sysLogService;

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月23日
	 * @Description 进入用户管理页面
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("system/user/user-index.jsp");
		return view;
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月23日
	 * @Description 查询用户列表
	 * @param page
	 * @param search
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public PageForJqGrid<SysUser> list(PageForJqGrid<SysUser> page, SysUser search) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("search", search);
		map = page.pageToMap(page, map);
		try {
			List<SysUser> list = sysUserService.getAllUser(map);
			if (list.size() > 0) {
				page.listToPage(page, list);
			}
		} catch (Exception e) {
			log.error("查询用户列表出现错误：{}", e.getMessage());
		}
		return page;
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年8月11日
	 * @Description 进入用户编辑页面
	 * @param id
	 *            用户ID
	 * @return
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(String id) {
		ModelAndView view = new ModelAndView("system/user/user-edit.jsp");
		try {
			if (!EmptyUtil.isEmpty(id)) {
				SysUser user = sysUserService.getUserById(id);
				view.addObject("user", user);
			}
		} catch (Exception e) {
			log.error("进入用户编辑页面出现错误：{}", e.getMessage());
		}
		return view;
	}

	/**
	 * 
	 * @author donghaikang
	 * @date 2015年8月12日 下午4:39:12
	 * @Description: 保存用户信息
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JsonResult<String> save(SysUser user) {
		JsonResult<String> json = new JsonResult<String>();
		String result = JsonResult.SUCCESS;
		try {
			sysUserService.saveUser(user);
		} catch (Exception e) {

			result = JsonResult.FAIL;

			if (e instanceof BaseException) {
				log.warn("保存用户出现异常：{}", e.getMessage());
				json.setMsg(e.getMessage());
			} else {
				// TODO 设定通用错误信息
				log.error("保存用户错误: {}", e.getMessage());
				json.setMsg("保存用户出现系统问题，请重新操作");
			}

		}
		sysLogService.AddLog("用户", "保存用户;用户名称:" + user.getUserName() + ";结果:" + result);
		json.setResult(result);
		return json;
	}
	
	/**
	 * 
	 * @author donghaikang 
	 * @date 2015年8月18日 下午2:12:31 
	 * @Description: 删除用户
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public JsonResult<String> delete(String delIds) {
		String result = "success";
		try {
			sysUserService.deleteUserByIds(delIds);
		} catch (Exception e) {
			result = "fail";
			log.warn("删除用户出现错误：{}", e.getMessage());
		}
		sysLogService.AddLog("删除用户", "删除用户;id:" + delIds + ";结果:" + result);
		JsonResult<String> json = new JsonResult<String>();
		json.setStatus(result);
		return json;
	}
	
	/**
	 * 
	 * @author donghaikang 
	 * @date 2015年8月18日 下午3:40:26 
	 * @Description: 验证用户名是否存在
	 */
	@RequestMapping("/validUserNameIsExist")
	@ResponseBody
	public JsonResult<Boolean> validUserNameIsExist(String userName, String id) {
		JsonResult<Boolean> json = new JsonResult<Boolean>();
		try {
			// 根据用户名称获取报价单
			SysUser user = sysUserService.getUserByUserName(userName, id);
			if (EmptyUtil.isEmpty(user)) {
				json.setResult(true);
			} else {
				json.setResult(false);
			}
		} catch (Exception e) {
			log.error("验证用户名发生错误:" + e.getMessage());
		}
		return json;
	}

}
