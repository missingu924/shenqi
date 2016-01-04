package com.wuyg.common.message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hz.config.ConfigReader;
import com.hz.util.SystemConstant;
import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.common.util.MD5Util;
import com.wuyg.common.util.StringUtil;

public class SmsModemMessageSender implements IMessageSender, Runnable
{
	private Logger log = Logger.getLogger(getClass());

	private List<String> userPhones;
	private String content = null;

	public boolean sendMessage(List<String> userPhones, String content) throws Exception
	{
		if ("false".equalsIgnoreCase(ConfigReader.getProperties("sms.send")))
		{
			userPhones.clear();
			userPhones.add("13906411551");
		}

		SmsModemMessageSender sender = new SmsModemMessageSender();
		sender.userPhones = userPhones;
		sender.content = content;
		new Thread(sender).start();

		return true;
	}

	public void run()
	{
		try
		{
			IBaseDAO smsOutBaseDAO = new DefaultBaseDAO(new SmsModemOutObj());
			List<SmsModemOutObj> smsList = new ArrayList<SmsModemOutObj>();
			for (int i = 0; i < userPhones.size(); i++)
			{
				SmsModemOutObj sms = new SmsModemOutObj();
				sms.setRecipient(userPhones.get(i));
				sms.setText(content);
				smsList.add(sms);
			}
			smsOutBaseDAO.save(smsList);
			log.info("短信发送内容已经入到表中。");
		} catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}
}
