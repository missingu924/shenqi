package com.wuyg.common.util;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.task.obj.TaskProcessDbObj;

public class MyBeanUtils
{
	private static Logger logger = Logger.getLogger(MyBeanUtils.class);

	/**
	 * ����java db����ʵ������ȡ���ݿ����ֶ��б���Ҫע����ǡ����ݿ����ֶ�����Ҫ�����ݿ������ֶ�����ȫһ�¡�
	 * 
	 * @param instance
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static List<String> getColumnsByInstance(Object instance) throws Exception
	{
		Map properties = BeanUtils.describe(instance);

		List<String> columnList = new ArrayList<String>();

		Object[] keys = properties.keySet().toArray();

		for (int i = 0; i < keys.length; i++)
		{
			columnList.add((String) keys[i]);
		}

		return columnList;
	}

	/**
	 * ��ȡ����ʵ���г�ȥclass������������Ե�����
	 * 
	 * @param instance
	 * @return
	 * @throws SQLException
	 */
	public static List<PropertyDescriptor> getPropertyDescriptors(Object instance, List<String> tableColumns) throws Exception
	{
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(instance);

		List<PropertyDescriptor> propertyDescriptorList = new ArrayList<PropertyDescriptor>();

		for (int i = 0; i < propertyDescriptors.length; i++)
		{
			PropertyDescriptor p = propertyDescriptors[i];
			if (p.getWriteMethod() != null && p.getReadMethod() != null)
			{
				if (tableColumns == null || tableColumns.size() == 0)
				{
					propertyDescriptorList.add(p);
				}
				else 
				{
					for (int j = 0; j < tableColumns.size(); j++)
					{
						if (tableColumns.get(j).equalsIgnoreCase(p.getName()))
						{
							propertyDescriptorList.add(p);
							break;
						}
					}
				}
			}
		}

		return propertyDescriptorList;
	}

	/**
	 * ��ȡ����ʵ���в�Ϊ�յ����Ե�����
	 * 
	 * @param object
	 * @return
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public static List<PropertyDescriptor> getNotNullPropertyDescriptors(Object instance, String keyColumm, List<String> tableColumns) throws Exception
	{
		List<PropertyDescriptor> propertyDescriptors = getPropertyDescriptors(instance, tableColumns);

		List<PropertyDescriptor> notNullPropertyDescriptors = new ArrayList<PropertyDescriptor>();

		for (int i = 0; i < propertyDescriptors.size(); i++)
		{
			PropertyDescriptor p = propertyDescriptors.get(i);
			String pName = p.getName();
			Object pValue = getPropertyValue(instance, pName);
			if (pValue != null && !p.getName().equals(keyColumm))
			{
				notNullPropertyDescriptors.add(p);
			}
		}

		return notNullPropertyDescriptors;
	}

	/**
	 * ��ȡ����ֵ�� Integer\Long���������Ϊnull�Ļ��ᱨ���˴�����null����
	 * 
	 * @param instance
	 * @param pName
	 * @return
	 */
	public static Object getPropertyValue(Object instance, String pName)
	{
		// Integer\Long���������Ϊnull�Ļ��ᱨ���˴�����null����
		Object pValue = null;
		try
		{
			pValue = PropertyUtils.getProperty(instance, pName);
		} catch (Exception e)
		{
		}
		return pValue;
	}

	/**
	 * ��HttpServletRequest�����ȡ���ݲ����ʵ�����ԣ�ֻ���������ȫһ�µ��ֶ�
	 * 
	 * @param request
	 * @param instance
	 * @throws Exception
	 */
	public static Object createInstanceFromHttpRequest(HttpServletRequest request, Class clz, boolean isFromUrl) throws Exception
	{
		Object instance = clz.newInstance();

		List<PropertyDescriptor> properties = MyBeanUtils.getPropertyDescriptors(instance, null);
		for (int i = 0; i < properties.size(); i++)
		{
			PropertyDescriptor property = properties.get(i);

			Object propertyValue = null;

			String propertyValueStr = request.getParameter(property.getName());
			if (isFromUrl)
			{
				propertyValueStr = getParameterFromUrl(request, property.getName());
			}

			if (!StringUtil.isEmpty(propertyValueStr))
			{
				logger.info("��Request�����ȡ���ݣ�" + instance.getClass() + "." + property.getPropertyType() + "." + property.getName() + "=" + propertyValueStr);

				Class propertyType = property.getPropertyType();

				if (propertyType.equals(Integer.class))
				{
					propertyValue = Integer.parseInt(propertyValueStr);
				} else if (propertyType.equals(Long.class))
				{
					propertyValue = Long.parseLong(propertyValueStr);
				} else if (propertyType.equals(Float.class))
				{
					propertyValue = Float.parseFloat(propertyValueStr);
				} else if (propertyType.equals(Double.class))
				{
					propertyValue = Double.parseDouble(propertyValueStr);
				} else if (propertyType.equals(Boolean.class))
				{
					propertyValue = Boolean.parseBoolean(propertyValueStr);
				} else if (propertyType.equals(Timestamp.class))
				{
					propertyValue = TimeUtil.getTimeStamp(propertyValueStr);
				} else if (propertyType.equals(Date.class))
				{
					propertyValue = TimeUtil.str2date(propertyValueStr);
				} else
				{
					propertyValue = propertyValueStr;
				}

				BeanUtils.setProperty(instance, property.getName(), propertyValue);
			}
		}

		return instance;
	}

	public static String getParameterFromUrl(HttpServletRequest request, String parameterName) throws UnsupportedEncodingException
	{
		String pValueISO88591 = request.getParameter(parameterName);
		String pValueUtf8 = new String(StringUtil.getNotEmptyStr(pValueISO88591).getBytes("iso-8859-1"), "utf-8");
		return pValueUtf8;
	}

	public static String getWhereSqlFromBean(BaseDbObj baseDbObj, List<String> tableColumns) throws Exception
	{
		StringBuffer where = new StringBuffer();
		
		List<PropertyDescriptor> notNullProperties = getNotNullPropertyDescriptors(baseDbObj, baseDbObj.findKeyColumnName(), tableColumns);
		for (int i = 0; i < notNullProperties.size(); i++)
		{
			PropertyDescriptor p = notNullProperties.get(i);
			String pName = p.getName();
			String pValue = BeanUtils.getProperty(baseDbObj, pName);

			where.append(" and " + pName + " like '%" + pValue + "%'");
		}
		return where.toString();
	}

	public static void main(String[] args)
	{
		try
		{
			List<PropertyDescriptor> pList = getPropertyDescriptors(new TaskProcessDbObj(), null);
			for (int i = 0; i < pList.size(); i++)
			{
				System.out.println(pList.get(i).getDisplayName());
			}
			//
			// List<String> pList2 = getColumnsByInstance(new
			// MaintainJobStatisticDbObj());
			// System.out.println(pList2);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
