package com.hz.teamworktask.obj;

import java.util.LinkedHashMap;

import com.wuyg.task.obj.BaseBusinessObj;

public class TeamworkTaskDbObj extends BaseBusinessObj
{
	private long telNum;// 用户号码
	// 入网时间
	// 话务情况
	// 主被叫情况
	// ARPU
	// 产品
	// 套餐
	// 手机
	// 区县
	// 区域
	// 客户经理
	// 归属集团
	private String workDiscription;// 工作描述
	private String attachment;// 工单附件

	public long getTelNum()
	{
		return telNum;
	}

	public void setTelNum(long telNum)
	{
		this.telNum = telNum;
	}

	public String getWorkDiscription()
	{
		return workDiscription;
	}

	public void setWorkDiscription(String workDiscription)
	{
		this.workDiscription = workDiscription;
	}

	public String getAttachment()
	{
		return attachment;
	}

	public void setAttachment(String attachment)
	{
		this.attachment = attachment;
	}

	@Override
	public String findKeyColumnName()
	{
		// TODO Auto-generated method stub
		return "taskId";
	}

	@Override
	public String findParentKeyColumnName()
	{
		// TODO Auto-generated method stub
		return "taskId";
	}

	@Override
	public String findTableName()
	{
		// TODO Auto-generated method stub
		return "teamwork_task";
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
