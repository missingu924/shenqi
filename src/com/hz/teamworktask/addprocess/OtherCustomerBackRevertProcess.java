package com.hz.teamworktask.addprocess;

import com.hz.customer.obj.ReservedTelNumDbObj;
import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.task.TaskConstant;
import com.wuyg.task.obj.TaskMainInfoDbObj;
import com.wuyg.task.process.ITaskProcess;

public class OtherCustomerBackRevertProcess implements ITaskProcess
{
	// ���ݹ��������������ԤԼ������е�״̬
	public boolean process(String taskType, String taskSubType, String processName, TaskMainInfoDbObj task) throws Exception
	{
		ReservedTelNumDbObj reservedTelNum = new ReservedTelNumDbObj();
		reservedTelNum.setTaskId(task.getTaskId());
	
		if (TaskConstant.RESULT_SUCCESS.equals(task.getProcessResult()))
		{
			reservedTelNum.setStatus(TaskConstant.TEL_REVERSE_STATUS_SUCCESS);
		}
		else if (TaskConstant.RESULT_FAILED.equals(task.getProcessResult()))
		{
			reservedTelNum.setStatus(TaskConstant.TEL_REVERSE_STATUS_FAILED);
		}
		
		return new DefaultBaseDAO(reservedTelNum).update(reservedTelNum);
	}

}
