/*
 * Copyright (c) 2003 - 2005 Langchao LG Information Systems Co.,Ltd.
 * All Rights Reserved.
 */
package com.hz.config;

import org.apache.log4j.Logger;

import com.hz.util.SystemConstant;
import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.common.util.StringUtil;
import com.wuyg.system.obj.SystemConfigDbObj;

/**
 * ������Ϣ��ȡ��
 */
public class ConfigReader
{
	public static Logger logger = Logger.getLogger(ConfigReader.class);

	/**
	 * ����������Ϣ
	 */
	private synchronized static void loadProperties()
	{
		if (properties == null)
		{
			// Create a manager with the full path to the xml config file.
			try
			{
				// get the config
				String filepath = Thread.currentThread().getContextClassLoader().getResource(CONFIG_PATH).getPath();
				properties = new XMLProperties("file:///" + filepath);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��ȡSystemConfig.xml�����õĲ���ֵ��
	 * 
	 * @param key
	 *            ����ֵ��·����������ʽΪX.Y.Z����ʾȡ�ļ���<Config><X><Y><Z>value</Z></Y></X></Config>��ֵ��
	 * @return
	 */
	public static String getProperties(String key)
	{
		if (properties == null)
		{
			loadProperties();
		}
		String value = properties.getPorperty(key);
		if (StringUtil.isEmpty(value))
		{
			SystemConfigDbObj keyValue = (SystemConfigDbObj) configDao.searchByKey(SystemConfigDbObj.class, key);
			if (keyValue != null)
			{
				value = keyValue.getValue();
			}
		}
		return value;
	}

	private static XMLProperties properties;
	private static final String CONFIG_PATH = "SystemConfig.xml";

	private static IBaseDAO configDao = new DefaultBaseDAO(SystemConfigDbObj.class, SystemConstant.INNER_DB);
}
