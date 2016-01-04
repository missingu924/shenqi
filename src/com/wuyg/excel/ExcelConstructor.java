package com.wuyg.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.wuyg.common.util.StringUtil;

public class ExcelConstructor
{
	public static void construct(String head, List dataList, LinkedHashMap props, String filePath) throws Exception
	{
		HSSFWorkbook wk = new HSSFWorkbook();
		int sheetNum = 0;
		// 构造样式
		HSSFCellStyle headStyle = getHeadStyle(wk);
		HSSFCellStyle dataRowStyle1 = getDataRowStyle1(wk);
		HSSFCellStyle dataRowStyle2 = getDataRowStyle2(wk);
		// 构造sheet
		HSSFSheet sheet = createSheet(wk, sheetNum++, head);

		int rowNum = 0;

		List<Integer> columnsWidth = new ArrayList<Integer>();// 列宽

		// 构造sheet标题
		// constructHead(sheet, head, headStyle, rowNum++, props.size());
		// 构造表头
		constructDataHead(sheet, props, headStyle, rowNum++, columnsWidth);
		// 写数据
		constructDataRows(sheet, dataList, props, dataRowStyle1, dataRowStyle2, rowNum++, columnsWidth);
		// 自动调整列宽
		autoSizeColumns(sheet, columnsWidth);
		// 保存excel
		saveToFile(wk, filePath);
	}

	private static void autoSizeColumns(HSSFSheet sheet, List<Integer> columnsWidth)
	{
		for (int i = 0; i < columnsWidth.size(); i++)
		{
			int width= columnsWidth.get(i) + 2;
			if (width<=255)
			{
				sheet.setColumnWidth(i, width * 256);
			}
			// sheet.autoSizeColumn(i);
		}
	}

	private static HSSFSheet createSheet(HSSFWorkbook wk, int sheetNum, String sheetName)
	{
		HSSFSheet sheet = wk.createSheet();
		// wk.setSheetName(sheetNum, sheetName, HSSFCell.ENCODING_UTF_16);
		wk.setSheetName(sheetNum, sheetName);
		return sheet;
	}

	private static void constructHead(HSSFSheet sheet, String head, HSSFCellStyle style, int fromRow, int colCount)
	{
		HSSFRow timeRangeRow = sheet.createRow(fromRow);
		HSSFCell cell = timeRangeRow.createCell((short) 0);
		sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (colCount - 1)));
		// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellStyle(style);
		cell.setCellValue(head);
	}

	private static void constructDataHead(HSSFSheet sheet, LinkedHashMap props, HSSFCellStyle headStyle, int fromRow, List<Integer> columnsWidth)
	{
		HSSFRow head = sheet.createRow(fromRow);
		Iterator keys = props.keySet().iterator();
		Map map = null;
		int i = 0;
		while (keys.hasNext())
		{
			String key = keys.next().toString();
			String name = props.get(key).toString();
			columnsWidth.add(name.getBytes().length);// 计算列宽
			HSSFCell cell = head.createCell((short) i++);
			// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellStyle(headStyle);
			cell.setCellValue(name);
		}
	}

	private static void constructDataRows(HSSFSheet sheet, List dataList, LinkedHashMap props, HSSFCellStyle dataRowStyle1, HSSFCellStyle dataRowStyle2,
			int fromRow, List<Integer> columnsWidth) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException
	{
		for (int i = 0; i < dataList.size(); i++)
		{
			HSSFRow row = sheet.createRow(i + fromRow);
			Object data = dataList.get(i);

			Iterator keys = props.keySet().iterator();
			int j = 0;
			while (keys.hasNext())
			{
				String key = keys.next().toString();
				String value = BeanUtilsBean.getInstance().getProperty(data, key);
				// 列宽
				if (!StringUtil.isEmpty(value) && columnsWidth.get(j) < value.getBytes().length)
				{
					columnsWidth.set(j, value.getBytes().length);
				}

				HSSFCell cell = row.createCell((short) j++);
				// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				if (i % 2 == 0)
				{
					cell.setCellStyle(dataRowStyle1);
				} else
				{
					cell.setCellStyle(dataRowStyle2);
				}

				cell.setCellValue(value);
			}
		}
	}

	private static HSSFCellStyle getHeadStyle(HSSFWorkbook wk)
	{
		HSSFCellStyle style = wk.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
		style.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFFont font = wk.createFont();
		font.setFontHeightInPoints((short) 11); // 12号字
		font.setBoldweight((short) 600); // 加粗
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);

		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		return style;
	}

	private static HSSFCellStyle getDataRowStyle1(HSSFWorkbook wk)
	{
		HSSFCellStyle style = wk.createCellStyle();

		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		return style;
	}

	private static HSSFCellStyle getDataRowStyle2(HSSFWorkbook wk)
	{
		HSSFCellStyle style = wk.createCellStyle();

		style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);

		return style;
	}

	private static void saveToFile(HSSFWorkbook wk, String filePath) throws FileNotFoundException, IOException
	{
		File file = new File(filePath);
		if (file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wk.write(fileOut);
		fileOut.close();
	}
}
