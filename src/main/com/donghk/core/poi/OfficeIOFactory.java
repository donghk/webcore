package com.donghk.core.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.donghk.core.poi.PoiEnums.CellIsErrorDisplay;
import com.donghk.core.util.DateUtils;
import com.donghk.core.util.EmptyUtil;
import com.donghk.core.util.ExcelUtils;
import com.donghk.core.util.StringUtil;

@SuppressWarnings({ "unchecked", "rawtypes", "incomplete-switch" })
public class OfficeIOFactory {

	private final static Logger log = Logger.getLogger(OfficeIOFactory.class);

	private final java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("0");

	protected final ValidResult importXlsx(File file, SheetOptions[] sheets) {
		// 按文件取出工作簿
		Workbook wb = null;
		try {
			wb = create(new FileInputStream(file));
		} catch (InvalidFormatException e) {
			log.error(e.getMessage());
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return loadWorkbook(wb, sheets);
	}

	public static Workbook create(InputStream in) throws IOException, InvalidFormatException {
		if (!in.markSupported()) {
			in = new PushbackInputStream(in, 8);
		}
		// xls
		if (POIFSFileSystem.hasPOIFSHeader(in)) {
			return new HSSFWorkbook(in);
		}
		// xlsx
		if (POIXMLDocument.hasOOXMLHeader(in)) {
			return new XSSFWorkbook(OPCPackage.open(in));
		}
		throw new IllegalArgumentException("你的excel版本目前poi解析不了;");
	}

	/**
	 * 
	 * @author: dong.haikang@fescoadecco.com
	 * @date: 2015年7月15日
	 * @Description 初始化excel
	 * @param wb
	 * @param sheets
	 * @return
	 */
	private ValidResult loadWorkbook(Workbook wb, SheetOptions[] sheets) {

		ValidResult result = new ValidResult(sheets);

		// 文件异常时处理
		if (wb == null) {
			result.setExceptionError("文件无法读取或读取异常;");
			return result;
		}

		int sheetNumbers = wb.getNumberOfSheets();

		for (int sheetIndex = 0; sheetIndex < sheets.length; sheetIndex++) {
			if (sheets[sheetIndex].getSheetSeq() > sheetNumbers) {
				result.setExceptionError("无法在文件中找到指定的sheet序号;");
				continue;
			}
			// 取提对应的sheet
			Sheet sheet = wb.getSheetAt(sheets[sheetIndex].getSheetSeq());
			// 获取表中的总行数
			int rowsNum = sheet.getLastRowNum();
			// 对每张表中的列进行读取处理
			CellOptions[] cells = sheets[sheetIndex].getCellOptions();
			// 循环每一行
			for (int row = 0; row <= rowsNum; row++) {
				// 判断是否是在skipRow之内
				if (row < sheets[sheetIndex].getSkipRows()) {
					continue;
				}
				// 取的当前行
				Row activeRow = sheet.getRow(row);
				// 判断当前行记录是否有有效
				if (activeRow != null && !(activeRow.equals(""))) {
					// 第一行的各列放在一个MAP中
					Map resultMap = new HashMap();
					StringBuffer sb = new StringBuffer("");
					// 循环每一列按列所给的参数进行处理
					for (int cellIndex = 0; cellIndex < cells.length; cellIndex++) {
						Cell cell = activeRow.getCell(cellIndex);
						try {
							Object obj = getCellValue(cell, cells[cellIndex], wb);
							checkRule(cells[cellIndex], obj);
							resultMap.put(cells[cellIndex].getKey(), obj);
						} catch (PoiException e) {
							resultMap.put(cells[cellIndex].getKey(), cell);
							sb.append(e.getMessage());
						}
					}
					// 成功数据
					if (StringUtils.isBlank(sb.toString())) {
						result.getSuccessList().add(resultMap);
					} else {
						// 错误数据
						ErrorRecord errorRecord = new ErrorRecord();
						errorRecord.setSheetNum(sheetIndex);
						errorRecord.setRow(row + 1);
						errorRecord.setErrorMsg(sb.toString());
						resultMap.put("errorRecord", errorRecord);
						result.getFailList().add(resultMap);
					}
					// 原始数据
					result.getOriginalList().add(resultMap);
				}
			}
		}
		return result;
	}

	/**
	 * 
	 * @author: dong.haikang@fescoadecco.com
	 * @date: 2015年7月15日
	 * @Description 按 options 取出列中的值
	 * @param cell
	 * @param options
	 * @param wb
	 * @return
	 * @throws PoiException
	 */
	private Object getCellValue(Cell cell, CellOptions options, Workbook wb) throws PoiException {

		// 如果有静态值，直接返回
		if (options != null && options.getCellIsStatic().value) {
			return options.getStaticValue();
		}

		String item = "";

		try {
			item = StringUtil.nullValue(cell).trim();
		} catch (Exception e) {
			log.error("excel取值发生异常: " + options.getKey());
			throw new PoiException("excel取值发生异常: " + options.getKey() + ";");
		}

		if (StringUtils.isBlank(item)) {
			return null;
		}

		try {
			switch (options.getCellDataType()) {
			case VARCHAR:
				// 如果数字类型先获取，在转换成字符串
				if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					return decimalFormat.format(cell.getNumericCellValue());
				} else {
					if (cell.getCellType() != XSSFCell.CELL_TYPE_STRING) {
						cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					}
					return cell.getStringCellValue();
				}
			case NUMBER:
				if (cell.getCellType() == XSSFCell.CELL_TYPE_FORMULA) {
					return cell.getNumericCellValue();
				} else {
					BigDecimal varDou = BigDecimal.valueOf(Double.parseDouble(item));
					return varDou;
				}
			case DATE:
				String cellDate = null;
				Date date = null;
				if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					double cellDou = cell.getNumericCellValue();
					cellDate = String.valueOf(cellDou);
				} else {
					cellDate = cell.getStringCellValue();
				}
				if (StringUtil.isNotBlank(cellDate)) {
					cellDate = cellDate.replaceAll("-", "");
					cellDate = cellDate.replaceAll("/", "");
					date = DateUtils.parseFromStr(cellDate, DateUtils.SDF_YMD1);
					if (EmptyUtil.isEmpty(date)) {
						date = DateUtils.parseFromStr(cellDate, DateUtils.SDF_YMD4);
					}
				}
				return date;
			default:
				return "";
			}
		} catch (Exception e) {
			throw new PoiException(options.getColName() + "格式错误;");
		}
	}

	/**
	 * 
	 * @author: dong.haikang@fescoadecco.com
	 * @date: 2015年7月15日
	 * @Description 判断规则
	 * @param cell
	 * @param obj
	 * @return
	 */
	private void checkRule(CellOptions cell, Object obj) throws PoiException {
		if (cell.getCellRule() != null) {

			String item = StringUtil.nullValue(obj).trim();

			switch (cell.getCellRule()) {
			case REQUIRED:
				if (StringUtils.isBlank(item)) {
					throw new PoiException(cell.getColName() + "不能为空;");
				}
			}
		}
	}

	/**
	 * 
	 * @author: dong.haikang@fescoadecco.com
	 * @date: 2015年7月16日
	 * @Description 导出错误消息
	 * @param validResult
	 * @return
	 */
	protected final HSSFWorkbook exportErrorWB(List<Map<String, Object>> errorList, CellOptions[] cells) {
		HSSFWorkbook feedBackWorkbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = feedBackWorkbook.createSheet("错误信息");
		int col = 0;
		// 需要显示的列数
		int colNum = 0;
		// 需要显示的列key
		Map<Integer, String> colKey = new HashMap<Integer, String>();
		// 需要显示的列名称
		Map<Integer, String> colName = new HashMap<Integer, String>();

		for (CellOptions item : cells) {
			if (CellIsErrorDisplay.TRUE.equals(item.getIsErrorDisplay())) {
				col = col + 1;
				colKey.put(colNum, item.getKey());
				colName.put(colNum, item.getColName());
				colNum = colNum + 1;
			}
		}
		// 创建sheet的行和列
		ExcelUtils.createRows(sheet, errorList.size() + 1, col + 2);
		Row rowFirst = sheet.getRow(0);
		rowFirst.getCell(0).setCellValue("行号");
		for (int i = 0; i <= colNum; i++) {
			// 需要显示的列名称
			rowFirst.getCell(i + 1).setCellValue(colName.get(i));
		}
		rowFirst.getCell(col + 1).setCellValue("错误信息");
		int rowStart = 1;
		for (Map<String, Object> item : errorList) {
			// 错误信息
			ErrorRecord errorRecord = (ErrorRecord) item.get("errorRecord");
			// 获得行
			Row row = sheet.getRow(rowStart);
			// 设置行号
			row.getCell(0).setCellValue(errorRecord.getRow());
			// 设置需要显示的列信息
			for (int i = 0; i < colNum; i++) {
				row.getCell(i + 1).setCellValue(StringUtil.nullValue(item.get(colKey.get(i))));
			}
			row.getCell(col + 1).setCellValue(errorRecord.getErrorMsg());
			rowStart++;
		}

		return feedBackWorkbook;

	}
}
