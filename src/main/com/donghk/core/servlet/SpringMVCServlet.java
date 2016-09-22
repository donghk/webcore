package com.donghk.core.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.DispatcherServlet;

import com.donghk.core.exception.BaseException;
import com.donghk.core.json.JsonResult;

/**
 * SpringMVC 处理请求的Servlet
 *
 */
@SuppressWarnings("serial")
public class SpringMVCServlet extends DispatcherServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			logger.debug("doService:" + request.getRequestURI());
			super.doService(request, response);
		} catch (BaseException e) {
			logger.debug("doService:" + e.getMessage());
			try {
				JSONObject json = new JSONObject();
				json.put("status", JsonResult.FAIL);
				json.put("msg", e.getMessage());

				e.printStackTrace();
				PrintWriter pw = response.getWriter();
				pw.write(json.toString());
				pw.close();
			} catch (Exception ee) {
				logger.error("doService:", ee);
			}
		} catch (Exception e) {
			logger.error("doService:", e);
			try {
				JSONObject json = new JSONObject();
				json.put("status", JsonResult.FAIL);
				json.put("msg", e.toString());

				e.printStackTrace();
				PrintWriter pw = response.getWriter();
				pw.write(json.toString());
				pw.close();
			} catch (Exception ee) {
				logger.error("doService:", ee);
			}
		}

	}
}
