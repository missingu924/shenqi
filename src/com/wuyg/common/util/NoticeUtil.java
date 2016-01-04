package com.wuyg.common.util;

import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;

public class NoticeUtil
{
	static Logger logger = Logger.getLogger(NoticeUtil.class);
	public static Properties notices = null;

	public static String getNotice()
	{
		try
		{
			notices = new Properties();
			notices.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("���Ծ���.txt"));

			Random rd = new Random();
			String str = notices.getProperty(Calendar.getInstance().get(Calendar.DAY_OF_YEAR) % (notices.size() - 5) + "");
			if (str == null)
			{
				return "ÿ�춼���µ�һ�죬ף��ÿ�춼�и������顣";
			} else
			{
				return new String(str.getBytes("iso-8859-1"), "utf-8");
			}

		} catch (IOException e)
		{
			logger.error(e.getMessage(), e);
		}
		return "ÿ�춼���µ�һ�죬ף��ÿ�춼�и������顣";
	}
}
