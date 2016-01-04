package com.wuyg.system.obj;

import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;

public class SystemConfigDbObj extends BaseDbObj
{
	private String key;
	private String value;

	public SystemConfigDbObj()
	{
		// TODO Auto-generated constructor stub
	}
	
	public SystemConfigDbObj(String key, String value)
	{
		this.key = key;
		this.value = value;
	}

	@Override
	public String findKeyColumnName()
	{
		// TODO Auto-generated method stub
		return "key";
	}

	@Override
	public String findParentKeyColumnName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findTableName()
	{
		// TODO Auto-generated method stub
		return "system_config";
	}

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return key + "=" + value;
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
