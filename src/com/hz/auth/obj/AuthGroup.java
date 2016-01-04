package com.hz.auth.obj;

import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;

public class AuthGroup extends BaseDbObj
{
	private int groupId;
	private String groupName;
	private String groupDiscription;

	@Override
	public String findKeyColumnName()
	{
		return "groupId";
	}

	@Override
	public String findParentKeyColumnName()
	{
		return null;
	}

	@Override
	public String findTableName()
	{
		return "auth_group";
	}

	public int getGroupId()
	{
		return groupId;
	}

	public void setGroupId(int groupId)
	{
		this.groupId = groupId;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}

	public String getGroupDiscription()
	{
		return groupDiscription;
	}

	public void setGroupDiscription(String groupDiscription)
	{
		this.groupDiscription = groupDiscription;
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
