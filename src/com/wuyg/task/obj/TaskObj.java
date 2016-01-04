package com.wuyg.task.obj;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hz.auth.obj.AuthUser;
import com.wuyg.common.util.TimeUtil;
import com.wuyg.task.TaskConstant;

public class TaskObj
{
	private TaskMainInfoDbObj taskMainInfo;// ������Ҫ��Ϣ
	private Object businessData;// ����ҵ��������Ϣ
	private List<TaskProcessDbObj> taskProcessList = new ArrayList<TaskProcessDbObj>();// �����б�

	// �жϹ����Ƿ񾭹���ĳ������
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

	// ��ȡ�������
	public Long getTaskId()
	{
		if (taskMainInfo != null)
		{
			return taskMainInfo.getTaskId();
		}

		return null;
	}

	// ���ù������
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
