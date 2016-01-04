package com.wuyg.task.obj;

import java.sql.Timestamp;

import com.hz.auth.obj.AuthUser;

public class TaskSearchComplexCondition
{
	private TaskMainInfoDbObj taskMainInfo;// 工单基本信息

	private AuthUser user; // 当前用户
	private String relationshipWithUser;// 与当前用户的关系
	private Timestamp createStartTime; // 开始时间
	private Timestamp createEndTime; // 结束时间
	private String overTime;// 是否超时
	private int toBeOverTimeHours;// 几小时内将超时
	private boolean showCondition = false;// 是否显示查询条件，默认不显示
	private int pageNo;// 第几页
	private int pageCount;// 一页多少行

	public boolean isShowCondition()
	{
		return showCondition;
	}

	public void setShowCondition(boolean showCondition)
	{
		this.showCondition = showCondition;
	}

	public TaskMainInfoDbObj getTaskMainInfo()
	{
		return taskMainInfo;
	}

	public void setTaskMainInfo(TaskMainInfoDbObj taskMainInfo)
	{
		this.taskMainInfo = taskMainInfo;
	}

	public AuthUser getUser()
	{
		return user;
	}

	public void setUser(AuthUser user)
	{
		this.user = user;
	}

	public String getRelationshipWithUser()
	{
		return relationshipWithUser;
	}

	public void setRelationshipWithUser(String relationshipWithUser)
	{
		this.relationshipWithUser = relationshipWithUser;
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

	public String getOverTime()
	{
		return overTime;
	}

	public void setOverTime(String overTime)
	{
		this.overTime = overTime;
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

	public int getToBeOverTimeHours()
	{
		return toBeOverTimeHours;
	}

	public void setToBeOverTimeHours(int toBeOverTimeHours)
	{
		this.toBeOverTimeHours = toBeOverTimeHours;
	}

}
