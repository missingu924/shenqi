package com.wuyg.common.message;

import java.util.List;

public interface IMessageSender
{
	/**
	 * ��ָ���û�����������Ϣ������ͬʱ������û�����
	 * @param userPhones
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public boolean sendMessage(List<String> userPhones,String content) throws Exception;
}
