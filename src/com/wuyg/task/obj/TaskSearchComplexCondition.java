package com.wuyg.task.obj;

import java.sql.Timestamp;

import com.hz.auth.obj.AuthUser;

public class TaskSearchComplexCondition
{
	private TaskMainInfoDbObj taskMainInfo;// ����������Ϣ

	private AuthUser user; // ��ǰ�û�
	private String relationshipWithUser;// �뵱ǰ�û��Ĺ�ϵ
	private Timestamp createStartTime; // ��ʼʱ��
	private Timestamp createEndTime; // ����ʱ��
	private String overTime;// �Ƿ�ʱ
	private int toBeOverTimeHours;// ��Сʱ�ڽ���ʱ
	private boolean showCondition = false;// �Ƿ���ʾ��ѯ������Ĭ�ϲ���ʾ
	private int pageNo;// �ڼ�ҳ
	private int pageCount;// һҳ������

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
