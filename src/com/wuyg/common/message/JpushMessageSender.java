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

	private final String appKey = "7361c6b403d6def1fb9dba83";// "57b9ef19d4be5de08df12aa0";//�������466f7032ac604e02fb7bda89

	private final String masterSecret = "27163061fdd81abb519b83f5";// "13ac09b17715bd117163d8a1";//���ÿ��Ӧ�ö���Ӧһ��masterSecret

	/*
	 * �������ߵ�ʱ������Ϊ��λ�����֧��10�죨864000�룩�� 0 ��ʾ����Ϣ���������ߡ������û��������Ϸ�������ǰ�������û��������յ�����Ϣ��
	 * �˲������������ʾĬ�ϣ�Ĭ��Ϊ����1���������Ϣ��86400��
	 */
	private long timeToLive = 60 * 60 * 24;

	private JPushClient jpush = null;

	private List<String> userIds;
	private String content = null;

	/**
	 * ���� sendNo ��Ψһ�����б�Ҫ�� It is very important to keep sendNo unique.
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
		// ��ʵ��ҵ���У����� sendNo ��һ�����Լ���ҵ����Դ����һ���������֡�
		// ������Ҫ���ǣ���ȷ����Ҫ�ظ�ʹ�á�������ο� API �ĵ����˵����
		int sendNo = getRandomSendNo();
		String msgTitle = "�����л�е��Ϣ����";
		String msgContent = content;

		/*
		 * IOS�豸��չ����, ����badge����������
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

			String head = "JPUSH��Ϣ����," + alias + "," + msgContent + ",";
			if (null != msgResult)
			{
				log.info(head + "��������������: " + msgResult.toString());
				if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value())
				{
					log.info(head + "���ͳɹ��� sendNo=" + msgResult.getSendno());
					rst = rst & true;
				} else
				{
					log.info(head + "����ʧ�ܣ� �������=" + msgResult.getErrcode() + ", ������Ϣ=" + msgResult.getErrmsg());
					rst = rst & false;
				}
			} else
			{
				log.info(head + "�޷���ȡ��������������");
				rst = rst & false;
			}
		}

		// �������û�����֪ͨ, ���෽����ο��ĵ�
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

			msSender.sendMessage(userPhones, "xx��8��9��10��5���ύΪxx�ͻ�����ı��޵�������8��9��12��05��ǰ����ɹ���");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
