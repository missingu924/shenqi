package com.wuyg.task.process;

import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;

public class TaskAddProcessDbObj extends BaseDbObj
{
	private String taskType;// 工单类型
	private String taskSubType;// 工单虾类
	private String processName; // 环节名称
	private String processClass;// 处理类
	
	@Override
	public String findKeyColumnName()
	{
		// TODO Auto-generated method stub
		return null;
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
		return "task_add_process";
	}
	
	public String getTaskType()
	{
		return taskType;
	}
	public void setTaskType(String taskType)
	{
		this.taskType = taskType;
	}
	public String getTaskSubType()
	{
		return taskSubType;
	}
	public void setTaskSubType(String taskSubType)
	{
		this.taskSubType = taskSubType;
	}
	public String getProcessName()
	{
		return processName;
	}
	public void setProcessName(String processName)
	{
		this.processName = processName;
	}
	public String getProcessClass()
	{
		return processClass;
	}
	public void setProcessClass(String processClass)
	{
		this.processClass = processClass;
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
