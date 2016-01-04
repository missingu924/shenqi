package com.wuyg.common.message;

import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;

public class SmsModemOutObj extends BaseDbObj
{
	private String recipient;
	private String text;

	@Override
	public String findKeyColumnName()
	{
		return null;
	}

	@Override
	public String findParentKeyColumnName()
	{
		return null;
	}

	@Override
	public String findTableName()
	{
		return "smsserver_out";
	}

	public String getRecipient()
	{
		return recipient;
	}

	public void setRecipient(String recipient)
	{
		this.recipient = recipient;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	@Override
	public String getBasePath()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCnName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap<String, String> getProperties()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
