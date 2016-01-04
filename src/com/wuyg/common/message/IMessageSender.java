package com.wuyg.common.message;

import java.util.List;

public interface IMessageSender
{
	/**
	 * 给指定用户发送提醒信息，可以同时给多个用户发送
	 * @param userPhones
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public boolean sendMessage(List<String> userPhones,String content) throws Exception;
}
