package com.wuyg.task.obj;

import com.wuyg.common.dao.BaseDbObj;

public abstract class BaseBusinessObj extends BaseDbObj
{
	private long taskId;// �������

	public long getTaskId()
	{
		return taskId;
	}

	public void setTaskId(long taskId)
	{
		this.taskId = taskId;
	}
}
