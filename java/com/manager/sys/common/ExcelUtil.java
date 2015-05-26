package com.manager.sys.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.manager.sys.common.StringUtil.isEmpty;

public class ExcelUtil {
	private static Logger LOG = LoggerFactory.getLogger(ExcelUtil.class);

	public void testStr() {
		String str = "123.0";
		String[] strs = str.split(".");
		int i = str.indexOf(".");
		String temp = str.substring(i);
		if (".0".equals(temp)) {
			LOG.debug("--OK---");
		}
		LOG.debug("测试字符串：{},{}", strs, str.substring(i));
	}

	/**
	 * 读取数据
	 * 
	 * @param is
	 * @param startRows
	 * @return
	 * @throws IOException
	 */
	public static List<Object[]> readExcel(InputStream is, String filename, Integer startRows) throws IOException {
		LOG.info("-------读取Excel的信息----start----");
		List<Object[]> list = new ArrayList<Object[]>();
		LOG.debug("输入流：{}", is);
		Workbook workbook = null;
		/* 处理2007以上的版本 */
		if (filename.endsWith(".xlsx")) {
			workbook = new XSSFWorkbook(is);
		} else {
			/* 处理2003版本 */
			workbook = new HSSFWorkbook(is);
		}
		LOG.debug("workbook:{}", workbook);
		Sheet sheet = workbook.getSheetAt(0);
		/* 总行数 */
		int rows = sheet.getLastRowNum();
		int first = sheet.getFirstRowNum();
		LOG.debug("总行数：{},第一行：{}", rows, first);
		Row firstRow = sheet.getRow(0);
		short totalCells = firstRow.getLastCellNum();
		Object[] obj = null;
		if (rows > 0) {
			for (int i = startRows - 1; i <= rows; i++) {
				Row row = sheet.getRow(i);
				short cells = row.getLastCellNum();
				if (cells > 0) {
					obj = new Object[totalCells];
					for (int j = 0; j < totalCells; j++) {
						Cell cell = row.getCell(j);
						obj[j] = getValue(cell);
					}
				}
				list.add(obj);
			}
		}
		LOG.info("------读取Excel的信息----end----");
		return list;
	}

	/**
	 * 获取单元格数据的值
	 * 
	 * @param cell
	 * @return
	 */
	private static Object getValue(Cell cell) {
		if (isEmpty(cell)) {
			return null;
		}
		if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			double d = cell.getNumericCellValue();
			String str = String.valueOf(d);
			if (!isEmpty(str) && str != "null") {
				int i = str.indexOf(".");
				if (i > 0) {
					String temp = str.substring(i);
					if (".0".equals(temp)) {
						return (int) d;
					}
				}
			}
			return d;
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
			return cell.getBooleanCellValue();
		} else if (HSSFCell.CELL_TYPE_ERROR == cell.getCellType()) {
			return cell.getErrorCellValue();
		} else if (HSSFCell.CELL_TYPE_FORMULA == cell.getCellType()) {
			return cell.getCellFormula();
		} else if (HSSFCell.CELL_TYPE_BLANK == cell.getCellType()) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
			return cell.getStringCellValue();
		}
		return null;
	}

/*	*//**
	 * 测试
	 * 
	 * @throws IOException
	 *//*
	public void testLoadExcel() throws IOException {
		List<String> columns = new ArrayList<String>();
		columns.add("Year");
		columns.add("Course NO");
		columns.add("计算机");
		columns.add("网");
		List<Object[]> data = new ArrayList<Object[]>();
		for (int i = 0; i < 3; i++) {
			Object[] obj = { 2001, "116100", "王武", "fsd" };
			data.add(obj);
		}
		LOG.debug("行：{},列：{}", data.size(), columns);
		// File file = new File("F:/testLoad.xls");
		// FileOutputStream os = new FileOutputStream(file);
		// createExcel(data, columns, os);

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");

		Header header = sheet.getHeader();
		LOG.debug("header:{}", header);
		header.setCenter("Center Header");
		header.setLeft("Left Header");
		header.setRight(HSSFHeader.font("Stencil-Normal", "Italic") + HSSFHeader.fontSize((short) 16)
				+ "Right w/ Stencil-Normal Italic font and size 16");

		FileOutputStream fileOut = new FileOutputStream("f:/workbook.xls");
		wb.write(fileOut);
		fileOut.close();
	}*/

	/**
	 * 创建Excel
	 * 
	 * @param data
	 *            导出的数据内容
	 * @param columns
	 *            excel的列名
	 * @param os
	 *            文件的输出流
	 * @throws IOException
	 */
	public static void createExcel(String title, List<Object[]> data, List<String> columns, OutputStream os)
			throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		/* 设置表头的格式 */
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.CORNFLOWER_BLUE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
		cellStyle.setBorderLeft((short) 1);
		cellStyle.setBorderRight((short) 1);
		cellStyle.setBorderTop((short) 1);
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("宋体");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		/* 内容的样式 */
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		// contentStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
		// contentStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		contentStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
		contentStyle.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
		contentStyle.setBorderLeft((short) 1);
		contentStyle.setBorderRight((short) 1);
		contentStyle.setBorderTop((short) 1);
		HSSFFont contentfont = workbook.createFont();
		contentfont.setFontHeightInPoints((short) 10);
		contentfont.setFontName("宋体");

		int indexRow = 0;
		if (!isEmpty(title)) {
			HSSFCellStyle titleStyle = workbook.createCellStyle();
			titleStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
			titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			titleStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
			titleStyle.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
			titleStyle.setBorderLeft((short) 1);
			titleStyle.setBorderRight((short) 1);
			titleStyle.setBorderTop((short) 1);
			titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			HSSFFont titleFont = workbook.createFont();
			titleFont.setFontHeightInPoints((short) 18);
			titleFont.setFontName("宋体");
			titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			titleStyle.setFont(titleFont);
			HSSFRow titleRow = sheet.createRow(0);
			titleRow.setHeight((short) 30);
			titleRow.setHeightInPoints(30);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, !isEmpty(columns) ? (columns.size() > 1) ? columns
					.size() - 1 : 1 : 1));
			Cell cell = titleRow.createCell(0);
			cell.setCellValue(new HSSFRichTextString(title));
			cell.setCellStyle(titleStyle);
			indexRow = 1;
		}
		/* 设置表头的行 */
		HSSFRow firstRow = sheet.createRow(indexRow);
		/* 设置表头 */
		if (columns != null && columns.size() > 0) {
			for (int i = 0; i < columns.size(); i++) {
				HSSFCell cell = firstRow.createCell(i);
				cell.setCellType(HSSFCell.ENCODING_UTF_16);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(new HSSFRichTextString(columns.get(i)));
			}
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
		/* 输出数据 */
		if (data != null && data.size() > 0) {
			for (int i = 0; i < data.size(); i++) {
				HSSFRow row = sheet.createRow(i + indexRow + 1);
				Object[] objs = data.get(i);
				if (objs != null && objs.length > 0) {
					for (int j = 0, k = objs.length; j < k; j++) {
						HSSFCell cells = row.createCell(j);
						cells.setCellType(HSSFCell.ENCODING_UTF_16);
						if (!isEmpty(objs[j]) && objs[j] instanceof Timestamp) {
							Timestamp time = (Timestamp) objs[j];
							cells.setCellStyle(contentStyle);
							cells.setCellValue(new HSSFRichTextString(df.format(time)));
						} else if (!isEmpty(objs[j]) && (objs[j] instanceof Integer || objs[j] instanceof Double)) {
							contentStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
							cells.setCellStyle(contentStyle);
							cells.setCellValue(Double.valueOf(objs[j].toString()));
						} else {
							cells.setCellStyle(contentStyle);
							cells.setCellValue(new HSSFRichTextString(!isEmpty(objs[j]) ? objs[j].toString() : null));
						}
						sheet.autoSizeColumn(j);// 调整每一列的宽度
					}
				}
			}
		}
		workbook.write(os);
		os.close();
	}
}
