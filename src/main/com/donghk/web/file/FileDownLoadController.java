package com.donghk.web.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.donghk.core.util.CommonUtil;
import com.donghk.core.util.DateUtils;
import com.donghk.web.BaseController;

@Controller
@RequestMapping("/file")
public class FileDownLoadController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(FileDownLoadController.class);

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月23日
	 * @Description 批量数据中错误数据提取本地存储
	 * @param feedBackWorkbook
	 * @return
	 * @throws Exception
	 */
	public static String writeExcelFileLocal(HSSFWorkbook feedBackWorkbook) throws Exception {

		// 生成随机名称
		String tempName = UUID.randomUUID().toString();

		FileOutputStream outFile = new FileOutputStream(CommonUtil.getTempPath() + tempName + ".xls");
		feedBackWorkbook.write(outFile);
		outFile.close();

		return tempName + ".xls";
	}

	/**
	 * 下载本地文件
	 * 
	 * @param request
	 * @param response
	 * @param file
	 *        服务器临时文件的本地文件名
	 * @param fileName
	 *        下载后的文件名
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadFile")
	@ResponseBody
	public ModelAndView downloadFile(HttpServletRequest request, HttpServletResponse response, String file, String fileName) throws Exception {

		fileName = new String(fileName.getBytes("iso8859-1"), "utf-8");

		// 临时目录
		String uri = CommonUtil.getTempPath();

		String newFileName = (DateUtils.parseFromDate(new Date(), DateUtils.SDF_YMD1) + fileName);

		this.dumpFileToClient(request, response, uri, file, newFileName);

		return null;
	}

	/**
	 * 文件下载
	 * 
	 * @param path
	 *        文件下载路径
	 * @param fileName
	 *        文件名称
	 * @param newfileName
	 *        新文件名称
	 * @param charset
	 *        文件指定编码(编码默认为UTF-8)
	 * @throws Exception
	 */
	public void dumpFileToClient(HttpServletRequest request, HttpServletResponse response, String path, String fileName, String newFileName) throws Exception {
		String browserType = request.getHeader("user-agent");

		File file = new File(path + fileName);
		if (!file.exists()) {
			log.error("download file not exist:" + path + fileName);
			return;
		}

		if (null != browserType && -1 != browserType.indexOf("MSIE")) {
			newFileName = URLEncoder.encode(newFileName, "UTF-8");
		} else if (null != browserType && -1 != browserType.indexOf("Mozilla")) {
			newFileName = new String(newFileName.getBytes("UTF-8"), "ISO-8859-1");

		}
		int length = (int) file.length();

		response.reset();
		response.setContentLength(length);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + newFileName + "\"");
		response.setContentType("application/octet-stream;charset=" + "UTF-8");

		OutputStream out = null;
		FileInputStream fis = null;
		try {
			out = response.getOutputStream();
			fis = new FileInputStream(file);
			byte[] buffer = new byte[2048 * 5];
			int readTemp = 0;
			while ((readTemp = fis.read(buffer)) > -1) {
				out.write(buffer, 0, readTemp);
			}
			out.flush();
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		} finally {
			if (null != fis) {
				fis.close();
			}
			if (null != out) {
				out.close();
			}
		}
	}
}
