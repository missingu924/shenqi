package com.wuyg.task.obj;

import java.sql.Timestamp;

import com.hz.auth.obj.AuthUser;

public class TaskStatSearchCondition
{
	private AuthUser user; // 当前用户
	private String district;// 区县
	private Timestamp createStartTime; // 开始时间
	private Timestamp createEndTime; // 结束时间
	private boolean groupByDistrict = false;// 是否按区县统计，默认为否
	private boolean groupByDepartment = false;// 是否按部门统计，默认为否
	private boolean groupByTaskSubType = false;// 是否按工单子类统计，默认为否
	private int pageNo;// 第几页
	private int pageCount;// 一页多少行

	public boolean isGroupByDistrict()
	{
		if (isGroupByDepartment())// 按部门统计的话，默认就得加上按区县统计
			groupByDistrict = true;

		return groupByDistrict;
	}

	public void setGroupByDistrict(boolean groupByDistrict)
	{
		this.groupByDistrict = groupByDistrict;
	}

	public AuthUser getUser()
	{
		return user;
	}

	public void setUser(AuthUser user)
	{
		this.user = user;
	}

	public String getDistrict()
	{
		return district;
	}

	public void setDistrict(String district)
	{
		this.district = district;
	}

	public Timestamp getCreateStartTime()
	{
		return createStartTime;
	}

	public void setCreateStartTime(Timestamp createStartTime)
	{
		this.createStartTime = createStartTime;
	}

	public Timestamp getCreateEndTime()
	{
		return createEndTime;
	}

	public void setCreateEndTime(Timestamp createEndTime)
	{
		this.createEndTime = createEndTime;
	}

	public boolean isGroupByDepartment()
	{
		return groupByDepartment;
	}

	public void setGroupByDepartment(boolean groupByDepartment)
	{
		this.groupByDepartment = groupByDepartment;
	}

	public boolean isGroupByTaskSubType()
	{
		return groupByTaskSubType;
	}

	public void setGroupByTaskSubType(boolean groupByTaskSubType)
	{
		this.groupByTaskSubType = groupByTaskSubType;
	}

	public int getPageNo()
	{
		return pageNo;
	}

	public void setPageNo(int pageNo)
	{
		this.pageNo = pageNo;
	}

	public int getPageCount()
	{
		return pageCount;
	}

	public void setPageCount(int pageCount)
	{
		this.pageCount = pageCount;
	}

}
