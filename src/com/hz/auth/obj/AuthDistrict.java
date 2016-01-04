package com.hz.auth.obj;

import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;

public class AuthDistrict extends BaseDbObj
{
	private String id;
	private String name;

	@Override
	public String findKeyColumnName()
	{
		// TODO Auto-generated method stub
		return "id";
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
		return "dict_district";
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
