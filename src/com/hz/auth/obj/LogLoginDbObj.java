package com.hz.auth.obj;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;

public class LogLoginDbObj extends BaseDbObj
{
	private long id;
	private String userAccount;
	private String userName;
	private String userDistrict;
	private String userDepartmentId;
	private String userDepartmentName;
	private Timestamp timeStamp = new Timestamp(System.currentTimeMillis());

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
		return "log_login";
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getUserAccount()
	{
		return userAccount;
	}

	public void setUserAccount(String userAccount)
	{
		this.userAccount = userAccount;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserDistrict()
	{
		return userDistrict;
	}

	public void setUserDistrict(String userDistrict)
	{
		this.userDistrict = userDistrict;
	}

	public String getUserDepartmentId()
	{
		return userDepartmentId;
	}

	public void setUserDepartmentId(String userDepartmentId)
	{
		this.userDepartmentId = userDepartmentId;
	}

	public String getUserDepartmentName()
	{
		return userDepartmentName;
	}

	public void setUserDepartmentName(String userDepartmentName)
	{
		this.userDepartmentName = userDepartmentName;
	}

	public Timestamp getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp)
	{
		this.timeStamp = timeStamp;
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
