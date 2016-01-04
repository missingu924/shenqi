package com.hz.auth.obj;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.wuyg.common.dao.BaseDbObj;

public class AuthDepartment extends BaseDbObj
{
	private String departmentId;// �����ڲ���ţ�����SD.LJ�ı�ʾ
	private String departmentName;
	private String city;// ��������
	private String districtName;// ��������
	private String commentInfo;//��ע��Ϣ
	private List<AuthUser> users = new ArrayList<AuthUser>();

	@Override
	public String findKeyColumnName()
	{
		return "departmentId";
	}

	@Override
	public String findParentKeyColumnName()
	{
		return "district";
	}

	@Override
	public String findTableName()
	{
		return "auth_department";
	}

	public String getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(String departmentId)
	{
		this.departmentId = departmentId;
	}

	public String getCommentInfo()
	{
		return commentInfo;
	}

	public void setCommentInfo(String comment)
	{
		this.commentInfo = comment;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getDistrictName()
	{
		return districtName;
	}

	public void setDistrictName(String districtName)
	{
		this.districtName = districtName;
	}

	public List<AuthUser> getUsers()
	{
		return users;
	}

	public void fillUsers(List<AuthUser> users)
	{
		this.users = users;
	}

	public String getDepartmentName()
	{
		return departmentName;
	}

	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
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
