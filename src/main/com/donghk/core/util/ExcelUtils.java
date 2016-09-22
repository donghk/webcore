package com.donghk.core.util;

import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 操作Excel表格的功能类
 */
public class ExcelUtils {

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月23日
	 * @Description 创建sheet的行和列
	 * @param sheet
	 * @param rowCount
	 *            行数
	 * @param colCount
	 *            列数
	 */
	public static void createRows(HSSFSheet sheet, int rowCount, int colCount) {
		for (int rownum = 0; rownum < rowCount; rownum++) {
			Row row = sheet.createRow(rownum);
			for (int cellnum = 0; cellnum < colCount; cellnum++) {
				row.createCell(cellnum);
			}
		}
	}

	/**
	 * 
	 * @author: donghaikang
	 * @date: 2015年7月23日
	 * @Description 解析excel
	 * @param in
	 *            传入文件
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws Exception
	 */
	public static Workbook newWorkbook(InputStream in, String fileName) throws Exception {
		Workbook workbook = null;
		try {
			// 根据不同的文件名返回不同类型的WorkBook
			String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
			if (extension.equals("xls")) {
				workbook = new HSSFWorkbook(in);
			} else if (extension.equals("xlsx")) {
				workbook = new XSSFWorkbook(in);
			} else {
				throw new Exception("不支持该格式的文件！");
			}
		} finally {
			if (in != null)
				in.close();
		}
		return workbook;
	}
}
