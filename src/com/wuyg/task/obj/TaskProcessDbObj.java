package com.wuyg.task.obj;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import com.hz.auth.obj.AuthUser;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.util.TimeUtil;
import com.wuyg.task.TaskConstant;

public class TaskProcessDbObj extends BaseDbObj
{
	private Long processId; // 序列号
	private Long taskId; // 工单编号
	private String processName; // 环节名称
	private String processResult; // 环节名称

	private String processSubjectType; // 当前环节处理人类型（个人、部门、虚拟组）
	private String processSubjectId; // 当前环节处理人编号
	private String processSubjectName;// 当前环节处理人姓名
	private String processSubjectDepartmentId; // 处理人部门编号
	private String processSubjectDepartmentName; // 处理人姓名
	private String processSubjectDistrict; // 处理人区县

	private String nextProcessSubjectType; // 下一环节处理人类型（个人、部门、虚拟组）
	private String nextProcessSubjectId; // 下一环节处理人编号
	private String nextProcessSubjectName;// 下一环节处理人姓名
	private String nextProcessSubjectDepartId; // 下一处理人部门编号
	private String nextProcessSubjectDepartName; // 下一处理人姓名
	private String nextProcessSubjectDistrict; // 下一处理人区县

	private String overtime; // 是否超时
	private Long processUsedTime; // 处理用时 单位分钟
	private Long processTimeLimit; // 处理时限 单位分钟
	private String data = "无"; // 相关数据
	private String attachment;// 附件
	private Timestamp processTime = new Timestamp(System.currentTimeMillis()); // 处理时间

	public TaskProcessDbObj()
	{
	}

	/**
	 * 根据当前处理人创建环节实例
	 * 
	 * @param taskId
	 * @param processName
	 * @param user
	 * @param processData
	 */
	public TaskProcessDbObj(long taskId, AuthUser user, String processName, String processResult, String processData, String attachment)
	{
		this.setTaskId(taskId);
		this.setProcessName(processName);
		this.setProcessResult(processResult);

		this.setProcessSubjectType(TaskConstant.SUBJECT_TYPE_PERSON);
		this.setProcessSubjectId(user.getAccount());
		this.setProcessSubjectName(user.getName());
		this.setProcessSubjectDepartmentId(user.getDepartmentId());
		this.setProcessSubjectDepartmentName(user.getDepartmentName());
		this.setProcessSubjectDistrict(user.getDistrict());

		this.setData(processData);
		this.setAttachment(attachment);
	}

	/**
	 * 根据处理主体创建环节实例
	 * 
	 * @param taskId
	 * @param processName
	 * @param processSubject
	 * @param processData
	 */
	public TaskProcessDbObj(long taskId, TaskProcessSubject processSubject, String processName, String processResult, String processData, String attachment)
	{
		this.setTaskId(taskId);
		this.setProcessName(processName);
		this.setProcessResult(processResult);

		this.setProcessSubjectType(processSubject.getSubjectType());
		this.setProcessSubjectId(processSubject.getSubjectId());
		this.setProcessSubjectName(processSubject.getSubjectName());
		this.setProcessSubjectDepartmentId(processSubject.getSubjectDepartmentId());
		this.setProcessSubjectDepartmentName(processSubject.getSubjectDepartmentName());
		this.setProcessSubjectDistrict(processSubject.getSubjectDistrict());

		this.setData(processData);
		this.setAttachment(attachment);
	}

	@Override
	public String findKeyColumnName()
	{
		return "processId";
	}

	@Override
	public String findParentKeyColumnName()
	{
		return "taskId";
	}

	@Override
	public String findTableName()
	{
		return "task_process";
	}

	public void setNextProcessSubject(TaskProcessSubject nextProcessSubject)
	{
		if (nextProcessSubject != null)
		{
			this.setNextProcessSubjectType(nextProcessSubject.getSubjectType());
			this.setNextProcessSubjectId(nextProcessSubject.getSubjectId());
			this.setNextProcessSubjectName(nextProcessSubject.getSubjectName());
			this.setNextProcessSubjectDepartId(nextProcessSubject.getSubjectDepartmentId());
			this.setNextProcessSubjectDepartName(nextProcessSubject.getSubjectDepartmentName());
			this.setNextProcessSubjectDistrict(nextProcessSubject.getSubjectDistrict());
		}
	}

	public TaskProcessSubject getProcessSubject()
	{
		TaskProcessSubject processSubject = new TaskProcessSubject();

		processSubject.setSubjectType(this.processSubjectType);
		processSubject.setSubjectId(this.processSubjectId);
		processSubject.setSubjectName(this.processSubjectName);
		processSubject.setSubjectDepartmentId(this.processSubjectDepartmentId);
		processSubject.setSubjectDepartmentName(this.processSubjectDepartmentName);
		processSubject.setSubjectDistrict(this.processSubjectDistrict);

		return processSubject;
	}

	public String getProcessSubjectDistrict()
	{
		return processSubjectDistrict;
	}

	public void setProcessSubjectDistrict(String processSubjectDistrict)
	{
		this.processSubjectDistrict = processSubjectDistrict;
	}

	public String getProcessResult()
	{
		return processResult;
	}

	public void setProcessResult(String processResult)
	{
		this.processResult = processResult;
	}

	public String getAttachment()
	{
		return attachment;
	}

	public void setAttachment(String attachment)
	{
		this.attachment = attachment;
	}

	public String getProcessSubjectType()
	{
		return processSubjectType;
	}

	public void setProcessSubjectType(String processSubjectType)
	{
		this.processSubjectType = processSubjectType;
	}

	public String getProcessSubjectId()
	{
		return processSubjectId;
	}

	public void setProcessSubjectId(String processSubjectId)
	{
		this.processSubjectId = processSubjectId;
	}

	public String getProcessSubjectName()
	{
		return processSubjectName;
	}

	public void setProcessSubjectName(String processSubjectName)
	{
		this.processSubjectName = processSubjectName;
	}

	public String getProcessSubjectDepartmentId()
	{
		return processSubjectDepartmentId;
	}

	public void setProcessSubjectDepartmentId(String processSubjectDepartmentId)
	{
		this.processSubjectDepartmentId = processSubjectDepartmentId;
	}

	public String getProcessSubjectDepartmentName()
	{
		return processSubjectDepartmentName;
	}

	public void setProcessSubjectDepartmentName(String processSubjectDepartmentName)
	{
		this.processSubjectDepartmentName = processSubjectDepartmentName;
	}

	public Long getProcessUsedTime()
	{
		return processUsedTime;
	}

	public String getProcessUsedTimeShow()
	{
		if (this.processUsedTime == null)
		{
			return "0分钟";
		}
		return TimeUtil.timeInerval2str(this.processUsedTime * 60 * 1000l);
	}

	public void setProcessUsedTime(Long processUsedTime)
	{
		this.processUsedTime = processUsedTime;
	}

	public Long getProcessTimeLimit()
	{
		return processTimeLimit;
	}

	public void setProcessTimeLimit(Long processTimeLimit)
	{
		this.processTimeLimit = processTimeLimit;
	}

	public Long getProcessId()
	{
		return processId;
	}

	public void setProcessId(Long processId)
	{
		this.processId = processId;
	}

	public Long getTaskId()
	{
		return taskId;
	}

	public void setTaskId(Long taskId)
	{
		this.taskId = taskId;
	}

	public String getProcessName()
	{
		return processName;
	}

	public void setProcessName(String processName)
	{
		this.processName = processName;
	}

	// public String getProcessUserAccount()
	// {
	// return processUserAccount;
	// }
	//
	// public void setProcessUserAccount(String processUserAccount)
	// {
	// this.processUserAccount = processUserAccount;
	// }
	//
	// public String getProcessUserName()
	// {
	// return processUserName;
	// }
	//
	// public void setProcessUserName(String processUserName)
	// {
	// this.processUserName = processUserName;
	// }

	public Timestamp getProcessTime()
	{
		return processTime;
	}

	public void setProcessTime(Timestamp processTime)
	{
		this.processTime = processTime;
	}

	public String getProcessTimeShow()
	{
		return TimeUtil.getEasyReadTimeMi(processTime);
	}

	public String getOvertime()
	{
		return overtime;
	}

	public void setOvertime(String overtime)
	{
		this.overtime = overtime;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public String getNextProcessSubjectType()
	{
		return nextProcessSubjectType;
	}

	public void setNextProcessSubjectType(String nextProcessSubjectType)
	{
		this.nextProcessSubjectType = nextProcessSubjectType;
	}

	public String getNextProcessSubjectId()
	{
		return nextProcessSubjectId;
	}

	public void setNextProcessSubjectId(String nextProcessSubjectId)
	{
		this.nextProcessSubjectId = nextProcessSubjectId;
	}

	public String getNextProcessSubjectName()
	{
		return nextProcessSubjectName;
	}

	public void setNextProcessSubjectName(String nextProcessSubjectName)
	{
		this.nextProcessSubjectName = nextProcessSubjectName;
	}

	public String getNextProcessSubjectDepartId()
	{
		return nextProcessSubjectDepartId;
	}

	public void setNextProcessSubjectDepartId(String nextProcessSubjectDepartId)
	{
		this.nextProcessSubjectDepartId = nextProcessSubjectDepartId;
	}

	public String getNextProcessSubjectDepartName()
	{
		return nextProcessSubjectDepartName;
	}

	public void setNextProcessSubjectDepartName(String nextProcessSubjectDepartName)
	{
		this.nextProcessSubjectDepartName = nextProcessSubjectDepartName;
	}

	public String getNextProcessSubjectDistrict()
	{
		return nextProcessSubjectDistrict;
	}

	public void setNextProcessSubjectDistrict(String nextProcessSubjectDistrict)
	{
		this.nextProcessSubjectDistrict = nextProcessSubjectDistrict;
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
