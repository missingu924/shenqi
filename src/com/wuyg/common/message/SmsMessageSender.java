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
import com.wuyg.common.util.MD5Util;
import com.wuyg.common.util.StringUtil;

public class SmsMessageSender implements IMessageSender, Runnable
{
	private Logger log = Logger.getLogger(getClass());

	private List<String> userIds;
	private String content = null;

	/**
	 * 100 ���ͳɹ� 101 ��֤ʧ�� 102 ���Ų��� 103 ����ʧ�� 104 �Ƿ��ַ� 105 ���ݹ��� 106 ������� 107 Ƶ�ʹ���
	 * 108 �������ݿ� 109 �˺Ŷ��� 110 ��ֹƵ���������� 111 ϵͳ�ݶ����� 112 �д������ 113 ��ʱʱ�䲻�� 114
	 * �˺ű�����10���Ӻ��¼ 115 ����ʧ�� 116 ��ֹ�ӿڷ��� 117 ��IP����ȷ 120 ϵͳ����
	 */

	public boolean sendMessage(List<String> userIds, String content) throws Exception
	{
		if ("false".equalsIgnoreCase(ConfigReader.getProperties("sms.send")))
		{
			userIds.clear();
			userIds.add("13906411551");
		}
		

		SmsMessageSender sender = new SmsMessageSender();
		sender.userIds = userIds;
		sender.content = content;
		new Thread(sender).start();

		return true;
	}

	public void run()
	{
		try
		{
			// ����StringBuffer�������������ַ���
			StringBuffer sb = new StringBuffer("http://localhost:8848/sendsms?");

			// ��StringBuffer׷���û���
			sb.append("User=wuyg");

			// ��StringBuffer׷�����루�������MD5 32λ Сд��
			sb.append("&Pwd=");
			
			sb.append("&MsgID=");

			// ��StringBuffer׷���ֻ�����
			String mobile = StringUtil.getStringByList(userIds, false);
			sb.append("&Phone=").append(mobile);

			// ��StringBuffer׷����Ϣ����תURL��׼��
			sb.append("&Msg=" + URLEncoder.encode(content));

			// ����url����
			URL url = new URL(sb.toString());

			// ��url����
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// ����url����ʽ ��get�� ���� ��post��
			connection.setRequestMethod("POST");

			// ����
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			// ���ط��ͽ��
			String inputline = in.readLine();

			log.info("���ŷ���:mobile=" + mobile + ";����=" + content + ";������ϢΪ" + inputline);
		} catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	public static void main(String[] args) throws IOException
	{
		try
		{
			IMessageSender msSender = new SmsMessageSender();

			List<String> userPhones = new ArrayList<String>();

			userPhones.add("13906411551");

//			msSender.sendMessage(userPhones, "xx��8��9��10��5���ύΪxx�ͻ�����ı��޵�������8��9��12��05��ǰ����ɹ���");
			
			msSender.sendMessage(userPhones, "test");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
