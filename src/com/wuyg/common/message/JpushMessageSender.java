package com.wuyg.common.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.nntp.NewGroupsOrNewsQuery;
import org.apache.log4j.Logger;

import com.hz.config.ConfigReader;

import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.IOSExtra;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

public class JpushMessageSender implements IMessageSender, Runnable
{
	private Logger log = Logger.getLogger(JpushMessageSender.class);

	public final int MAX = Integer.MAX_VALUE;

	public final int MIN = (int) MAX / 2;

	private final String appKey = "7361c6b403d6def1fb9dba83";// "57b9ef19d4be5de08df12aa0";//必填，例如466f7032ac604e02fb7bda89

	private final String masterSecret = "27163061fdd81abb519b83f5";// "13ac09b17715bd117163d8a1";//必填，每个应用都对应一个masterSecret

	/*
	 * 保存离线的时长。秒为单位。最多支持10天（864000秒）。 0 表示该消息不保存离线。即：用户在线马上发出，当前不在线用户将不会收到此消息。
	 * 此参数不设置则表示默认，默认为保存1天的离线消息（86400秒
	 */
	private long timeToLive = 60 * 60 * 24;

	private JPushClient jpush = null;

	private List<String> userIds;
	private String content = null;

	/**
	 * 保持 sendNo 的唯一性是有必要的 It is very important to keep sendNo unique.
	 * 
	 * @return sendNo
	 */
	public int getRandomSendNo()
	{
		return (int) (MIN + Math.random() * (MAX - MIN));
	}

	public boolean sendMessage(List<String> userIds, String content) throws Exception
	{
		if ("false".equalsIgnoreCase(ConfigReader.getProperties("jpush.send")))
		{
			userIds.clear();
			userIds.add("13906411551");
		}

		JpushMessageSender sender = new JpushMessageSender();
		sender.userIds = userIds;
		sender.content = content;
		new Thread(sender).start();

		return true;
	}

	public void run()
	{
		// 在实际业务中，建议 sendNo 是一个你自己的业务可以处理的一个自增数字。
		// 除非需要覆盖，请确保不要重复使用。详情请参考 API 文档相关说明。
		int sendNo = getRandomSendNo();
		String msgTitle = "利星行机械消息中心";
		String msgContent = content;

		/*
		 * IOS设备扩展参数, 设置badge，设置声音
		 */
		Map<String, Object> extra = new HashMap<String, Object>();
		IOSExtra iosExtra = new IOSExtra(10, "WindowsLogonSound.wav");
		extra.put("ios", iosExtra);

		// MessageResult msgResult = jpush.sendNotificationWithAppKey(sendNo,
		// msgTitle, msgContent, 0, extra);

		jpush = new JPushClient(masterSecret, appKey, timeToLive);

		boolean rst = true;
		for (int i = 0; i < userIds.size(); i++)
		{
			String alias = userIds.get(i);

			MessageResult msgResult = jpush.sendNotificationWithAlias(sendNo, alias, msgTitle, msgContent);

			String head = "JPUSH消息发送," + alias + "," + msgContent + ",";
			if (null != msgResult)
			{
				log.info(head + "服务器返回数据: " + msgResult.toString());
				if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value())
				{
					log.info(head + "发送成功， sendNo=" + msgResult.getSendno());
					rst = rst & true;
				} else
				{
					log.info(head + "发送失败， 错误代码=" + msgResult.getErrcode() + ", 错误消息=" + msgResult.getErrmsg());
					rst = rst & false;
				}
			} else
			{
				log.info(head + "无法获取服务器返回数据");
				rst = rst & false;
			}
		}

		// 对所有用户发送通知, 更多方法请参考文档
		// MessageResult msgResult =
		// jpush.sendCustomMessageWithAppKey(sendNo,msgTitle, msgContent);
		// return rst;
	}

	public static void main(String[] args)
	{
		try
		{
			IMessageSender msSender = new JpushMessageSender();

			List<String> userPhones = new ArrayList<String>();

			userPhones.add("13906411551");

			msSender.sendMessage(userPhones, "xx于8月9日10点5分提交为xx客户服务的报修单，请于8月9日12点05分前完成派工。");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
