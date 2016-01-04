package com.wuyg.task.process;

import com.wuyg.task.obj.TaskMainInfoDbObj;

public interface ITaskProcess
{
	/**
	 * �Ըù������и��Ӵ���
	 * @param taskType ��������
	 * @param taskSubType ����С��
	 * @param processName ��������
	 * @param task
	 * @return
	 * @throws Exception
	 */
	public boolean process(String taskType, String taskSubType, String processName, TaskMainInfoDbObj task) throws Exception;
}
