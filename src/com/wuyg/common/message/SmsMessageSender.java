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
	 * 100 发送成功 101 验证失败 102 短信不足 103 操作失败 104 非法字符 105 内容过多 106 号码过多 107 频率过快
	 * 108 号码内容空 109 账号冻结 110 禁止频繁单条发送 111 系统暂定发送 112 有错误号码 113 定时时间不对 114
	 * 账号被锁，10分钟后登录 115 连接失败 116 禁止接口发送 117 绑定IP不正确 120 系统升级
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
			// 创建StringBuffer对象用来操作字符串
			StringBuffer sb = new StringBuffer("http://localhost:8848/sendsms?");

			// 向StringBuffer追加用户名
			sb.append("User=wuyg");

			// 向StringBuffer追加密码（密码采用MD5 32位 小写）
			sb.append("&Pwd=");
			
			sb.append("&MsgID=");

			// 向StringBuffer追加手机号码
			String mobile = StringUtil.getStringByList(userIds, false);
			sb.append("&Phone=").append(mobile);

			// 向StringBuffer追加消息内容转URL标准码
			sb.append("&Msg=" + URLEncoder.encode(content));

			// 创建url对象
			URL url = new URL(sb.toString());

			// 打开url连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");

			// 发送
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

			// 返回发送结果
			String inputline = in.readLine();

			log.info("短信发送:mobile=" + mobile + ";内容=" + content + ";返回信息为" + inputline);
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

//			msSender.sendMessage(userPhones, "xx于8月9日10点5分提交为xx客户服务的报修单，请于8月9日12点05分前完成派工。");
			
			msSender.sendMessage(userPhones, "test");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
