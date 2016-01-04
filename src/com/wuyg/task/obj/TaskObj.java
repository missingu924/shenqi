package com.wuyg.task.obj;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hz.auth.obj.AuthUser;
import com.wuyg.common.util.TimeUtil;
import com.wuyg.task.TaskConstant;

public class TaskObj
{
	private TaskMainInfoDbObj taskMainInfo;// 工单主要信息
	private Object businessData;// 工单业务数据信息
	private List<TaskProcessDbObj> taskProcessList = new ArrayList<TaskProcessDbObj>();// 环节列表

	// 判断工单是否经过了某个环节
	public boolean hasTaskProcess(String processName)
	{
		for (TaskProcessDbObj tp : this.taskProcessList)
		{
			if (tp.getProcessName().equalsIgnoreCase(processName))
			{
				return true;
			}
		}
		return false;
	}

	// 获取工单编号
	public Long getTaskId()
	{
		if (taskMainInfo != null)
		{
			return taskMainInfo.getTaskId();
		}

		return null;
	}

	// 设置工单编号
	public void setTaskId(Long taskId)
	{
		if (taskMainInfo != null)
		{
			taskMainInfo.setTaskId(taskId);
		}

		if (businessData != null)
		{
			((BaseBusinessObj) this.getBusinessData()).setTaskId(taskId);
		}
	}

	public TaskMainInfoDbObj getTaskMainInfo()
	{
		return taskMainInfo;
	}

	public List<TaskProcessDbObj> getTaskProcessList()
	{
		return taskProcessList;
	}

	public void setTaskProcessList(List<TaskProcessDbObj> taskProcessList)
	{
		this.taskProcessList = taskProcessList;
	}

	public void setTaskMainInfo(TaskMainInfoDbObj taskMainInfo)
	{
		this.taskMainInfo = taskMainInfo;
	}

	public Object getBusinessData()
	{
		return businessData;
	}

	public void setBusinessData(Object businessData)
	{
		this.businessData = businessData;
	}

}
