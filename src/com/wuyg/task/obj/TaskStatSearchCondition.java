package com.wuyg.task.obj;

import java.sql.Timestamp;

import com.hz.auth.obj.AuthUser;

public class TaskStatSearchCondition
{
	private AuthUser user; // ��ǰ�û�
	private String district;// ����
	private Timestamp createStartTime; // ��ʼʱ��
	private Timestamp createEndTime; // ����ʱ��
	private boolean groupByDistrict = false;// �Ƿ�����ͳ�ƣ�Ĭ��Ϊ��
	private boolean groupByDepartment = false;// �Ƿ񰴲���ͳ�ƣ�Ĭ��Ϊ��
	private boolean groupByTaskSubType = false;// �Ƿ񰴹�������ͳ�ƣ�Ĭ��Ϊ��
	private int pageNo;// �ڼ�ҳ
	private int pageCount;// һҳ������

	public boolean isGroupByDistrict()
	{
		if (isGroupByDepartment())// ������ͳ�ƵĻ���Ĭ�Ͼ͵ü��ϰ�����ͳ��
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
