package com.wuyg.dbmodel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.common.util.MyBeanUtils;
import com.wuyg.common.util.StringUtil;
import com.wuyg.dbmodel.obj.ColumnObj;
import com.wuyg.dbmodel.obj.TableObj;

public class DbModelServlet extends HttpServlet
{
	private Logger logger = Logger.getLogger(getClass());

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			// 获取执行的方法
			String method = request.getParameter("method");
			if ("generateSrc".equalsIgnoreCase(method))
			{
				generateSrc(request, response);
			}
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	private void generateSrc(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 1、获取前端设置信息
		TableObj tableObj = (TableObj) MyBeanUtils.createInstanceFromHttpRequest(request, TableObj.class, false);

		String[] columnNames = request.getParameterValues("columnName");
		String[] columnTypes = request.getParameterValues("columnType");
		String[] columnScales = request.getParameterValues("columnScale");
		String[] columnCnNames = request.getParameterValues("columnCnName");
		String[] dictNames = request.getParameterValues("dictName");
		String[] notNulls = request.getParameterValues("notNull");
		String[] columnShows = request.getParameterValues("columnShow");
		String[] columnForSearchs = request.getParameterValues("columnForSearch");

		for (int i = 0; i < columnNames.length; i++)
		{
			String columnName = columnNames[i];
			ColumnObj col = new ColumnObj();

			col.setColumnName(columnName);
			col.setColumnType(getColumnType(StringUtil.parseInt(columnTypes[i]), StringUtil.parseInt(columnScales[i])));
			col.setColumnScale(StringUtil.parseInt(columnScales[i]));
			col.setColumnCnName(columnCnNames[i]);
			col.setDictName(dictNames[i]);
			col.setNotNull(StringUtil.contains(notNulls, columnName));
			col.setColumnShow(StringUtil.contains(columnShows, columnName) && !StringUtil.isEmpty(columnCnNames[i]));// 选中且中文名不为空
			col.setColumnForSearch(StringUtil.contains(columnForSearchs, columnName));
			col.setIsKeyColumn(columnName.equalsIgnoreCase(tableObj.getKeyColumn()));

			tableObj.getColumns().add(col);
		}

		// 保存入库
		save2db(tableObj);

		// 2、生成javabean代码
		generateJavaBeanSrc(tableObj);

		// 3、生成servlet
		JspModelMain.genarateJavaSrc(tableObj);

		// 4、生成jsp
		JspModelMain.generateJsps(tableObj);

		// 4、生成jsp

		logger.info("");
	}

	private void save2db(TableObj tableObj) throws Exception
	{
		IBaseDAO tableDAO = new DefaultBaseDAO(TableObj.class);
		IBaseDAO columnDAO = new DefaultBaseDAO(ColumnObj.class);
		
		tableDAO.deleteByClause("tableName='"+tableObj.getTableName()+"'");
		
		tableObj.setId(tableDAO.getMaxKeyValue());
		
		tableDAO.save(tableObj);
		
		List<ColumnObj> columns=tableObj.getColumns();
		for (int i = 0; i < columns.size(); i++)
		{
			ColumnObj column = columns.get(i);
			column.setTableId(tableObj.getId());
			column.setId(columnDAO.getMaxKeyValue());
		}
		
		columnDAO.save(columns);
	}

	public void generateJavaBeanSrc(TableObj tableObj) throws FileNotFoundException
	{
		System.out.println("--" + tableObj.getTableName() + "\t" + tableObj.getCnName() + " javabean begin--");
		File srcFile = new File(tableObj.getJavaSrcDir() + "/" + tableObj.getJavaPackage().replace('.', '/') + "/obj/" + StringUtil.toClassName(tableObj.getTableName()) + "Obj.java");
		if (!srcFile.getParentFile().exists())
		{
			srcFile.getParentFile().mkdirs();
		}
		PrintWriter pw = new PrintWriter(srcFile);
		pw.println(tableObj.toJavaSrc());
		pw.close();
		System.out.println("--" + tableObj.getTableName() + "\t" + tableObj.getCnName() + " javabean end--");
	}

	private static String getColumnType(int columnTypeInt, int columnScale)
	{
		if (columnTypeInt == Types.SMALLINT || columnTypeInt == Types.INTEGER || columnTypeInt == Types.BIGINT || (columnTypeInt == Types.NUMERIC && columnScale == 0))
		{
			return "Long";
		} else if (columnTypeInt == Types.DOUBLE || columnTypeInt == Types.FLOAT || (columnTypeInt == Types.NUMERIC && columnScale > 0))
		{
			return "Double";
		} else if (columnTypeInt == Types.VARCHAR || columnTypeInt == Types.CHAR || columnTypeInt == Types.NCHAR || columnTypeInt == Types.NVARCHAR)
		{
			return "String";
		} else if (columnTypeInt == Types.DATE || columnTypeInt == Types.TIMESTAMP || columnTypeInt == Types.TIME)
		{
			return "Timestamp";
		} else if (columnTypeInt == Types.BIT)
		{
			return "Boolean";
		}

		return null;
	}

	public static void main(String[] args)
	{
		System.out.println(Boolean.parseBoolean("on"));
	}
}
