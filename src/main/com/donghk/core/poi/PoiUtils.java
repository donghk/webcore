package com.donghk.core.poi;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @author: dong.haikang@fescoadecco.com
 * @date: 2015年7月16日
 * @Description 导入导出工具类
 */
public class PoiUtils {

	private final static OfficeIOFactory ioFactory = new OfficeIOFactory();

	/**
	 * 
	 * @author: dong.haikang@fescoadecco.com
	 * @date: 2015年7月16日
	 * @Description 验证导入信息
	 * @param file
	 * @param sheets
	 * @return
	 */
	public static ValidResult ImportXlsx(File file, SheetOptions[] sheets) {
		return ioFactory.importXlsx(file, sheets);
	}

	/**
	 * 
	 * @author: dong.haikang@fescoadecco.com
	 * @date: 2015年7月16日
	 * @Description 导出错误消息
	 * @param validResult
	 * @return
	 */
	public static HSSFWorkbook exportErrorWB(List<Map<String, Object>> errorList, CellOptions[] cells) {
		return ioFactory.exportErrorWB(errorList, cells);
	}

}
