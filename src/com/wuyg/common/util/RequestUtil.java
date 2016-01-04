package com.wuyg.common.util;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import sun.nio.cs.ext.ISCII91;

import com.hz.customer.obj.ReservedTelNumDbObj;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.excel.ExcelConstructor;

public class RequestUtil
{
	private static Logger logger = Logger.getLogger(RequestUtil.class);

	/**
	 * ��request�л�ȡ�������ݹ������ʵ����Ҫ��ǰ̨���ݵĲ������ƺ�java�����е�����������ȫһ��
	 * 
	 * @param request
	 * @param clz
	 * @return
	 * @throws Exception
	 */
	public static Object createInstatce(HttpServletRequest request, Class clz) throws Exception
	{
		Object instance = clz.newInstance();

		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(instance);

		for (int i = 0; i < propertyDescriptors.length; i++)
		{
			PropertyDescriptor p = propertyDescriptors[i];
			if (p.getWriteMethod() != null)
			{
				BeanUtils.setProperty(instance, p.getName(), request.getParameter(p.getName()));
			}
		}

		return instance;
	}

	// �ļ�����
	public static void downloadFile(HttpServlet servlet, HttpServletResponse response, List dataList, LinkedHashMap<String, String> instanceProperties,
			String fileName) throws Exception
	{
		// ����excel�ļ�
		String filePath = servlet.getServletContext().getRealPath("/download/") + "/" + fileName + "_" + TimeUtil.nowTime2str("yyyyMMddHHmmss") + ".xls";
		File xlsFile = new File(filePath);

		logger.info("�����ļ�,���ɿ�ʼ " + filePath);
		ExcelConstructor.construct(fileName, dataList, instanceProperties, filePath);
		logger.info("�����ļ�,������� " + filePath + ",�ļ���С��" + (xlsFile.length() / 1024) + "K");

		// ����
		downloadFile(response, filePath);
	}

	public static String getParameterAndSetAttribute(HttpServletRequest request, String parameterName)
	{
		String parameterValue = StringUtil.getNotEmptyStr(request.getParameter(parameterName)).trim();
		request.setAttribute(parameterName, parameterValue);
		return parameterValue;
	}

	public static int getPageNo(HttpServletRequest request)
	{
		int pageNo = 1;
		String pageNoStr = request.getParameter("pageNo");
		if (!StringUtil.isEmpty(pageNoStr))
		{
			pageNo = Integer.parseInt(pageNoStr);
		}
		return pageNo;
	}

	public static int getPageCount(HttpServletRequest request)
	{
		int pageCount = 10;
		String pageCountStr = request.getParameter("pageCount");
		if (!StringUtil.isEmpty(pageCountStr))
		{
			pageCount = Integer.parseInt(pageCountStr);
		}
		return pageCount;
	}

	public static void downloadFile(HttpServletResponse response, String filePath) throws Exception
	{
		File file = new File(filePath);
		if (file.isFile() && file.exists())
		{
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "utf-8"));
			InputStream in = null;
			OutputStream out = null;
			try
			{
				in = new FileInputStream(filePath);
				out = response.getOutputStream();
				byte b[] = new byte[1024];
				int length = -1;
				while ((length = in.read(b)) != -1)
				{
					out.write(b, 0, length);
				}
				out.flush();
			} catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			} finally
			{
				if (in != null)
				{
					in.close();
				}
				if (out != null)
				{
					out.close();
				}
			}
		}

	}
	
	/**
	 * �ж��Ƿ����ƶ��豸����
	 * @param request
	 * @return
	 */
	public static boolean is4m(HttpServletRequest request)
	{
		String is4m = (request.getSession().getAttribute("4m"))+"";
		return "TRUE".equalsIgnoreCase(is4m);
	}
}
