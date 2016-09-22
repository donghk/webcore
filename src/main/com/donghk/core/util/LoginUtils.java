package com.donghk.core.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.donghk.core.security.BaseUserDetails;

/**
 * 
 * @author: donghaikang
 * @date: 2015年5月20日
 * @Description 登录信息
 */
public class LoginUtils {

	private LoginUtils() {

	}

	@SuppressWarnings("unchecked")
	public static <T extends BaseUserDetails> T getCurrentUser() {
		Authentication authentication = getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			if (principal instanceof BaseUserDetails) {
				return (T) principal;
			}
		}
		return null;
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月11日
	 * @Description 获取登录用户姓名
	 * @return
	 */
	public static String getCurrentUserName() {
		Authentication authentication = getAuthentication();
		if (authentication != null && authentication.getPrincipal() != null) {
			return authentication.getName();
		}
		return "";
	}
	
	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月11日
	 * @Description 获取登录用户ID
	 * @return
	 */
	public static String getLoginUserId(){
		BaseUserDetails userDetails = (BaseUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails.getUser().getUserId();
	}
	
	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月11日
	 * @Description 获取登录用户真实姓名
	 * @return
	 */
	public static String getLoginUserRealName(){
		BaseUserDetails userDetails = (BaseUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails.getUser().getRealName();
	}
	
	public static String getCurrentUserIp() {
		Authentication authentication = getAuthentication();
		if (authentication != null) {
			Object details = authentication.getDetails();
			if (details instanceof WebAuthenticationDetails) {
				WebAuthenticationDetails webDetails = (WebAuthenticationDetails) details;
				return webDetails.getRemoteAddress();
			}
		}
		return "";
	}

	public static void saveUserDetailsToContext(UserDetails userDetails, HttpServletRequest request) {
		PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDetails,
				userDetails.getPassword(), userDetails.getAuthorities());

		authentication.setDetails(new WebAuthenticationDetails(request));

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public static void clearUserDetailsToContext() {
		SecurityContextHolder.clearContext();
	}

	private static Authentication getAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			return context.getAuthentication();
		}
		return null;
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月11日
	 * @Description 是否登录
	 * @return
	 */
	public static boolean isLogin(){
		try {
			Authentication auth = getAuthentication();
			if (auth == null){
				return false;
			}
			Object principal = auth.getPrincipal();
			if (principal != null && principal instanceof BaseUserDetails) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
