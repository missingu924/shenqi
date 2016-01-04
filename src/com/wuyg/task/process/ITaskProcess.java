package com.wuyg.task.process;

import com.wuyg.task.obj.TaskMainInfoDbObj;

public interface ITaskProcess
{
	/**
	 * 对该工单进行附加处理
	 * @param taskType 工单大类
	 * @param taskSubType 工单小类
	 * @param processName 环节名称
	 * @param task
	 * @return
	 * @throws Exception
	 */
	public boolean process(String taskType, String taskSubType, String processName, TaskMainInfoDbObj task) throws Exception;
}
