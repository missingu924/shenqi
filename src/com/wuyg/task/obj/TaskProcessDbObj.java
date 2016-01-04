package com.wuyg.task.obj;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import com.hz.auth.obj.AuthUser;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.util.TimeUtil;
import com.wuyg.task.TaskConstant;

public class TaskProcessDbObj extends BaseDbObj
{
	private Long processId; // ���к�
	private Long taskId; // �������
	private String processName; // ��������
	private String processResult; // ��������

	private String processSubjectType; // ��ǰ���ڴ��������ͣ����ˡ����š������飩
	private String processSubjectId; // ��ǰ���ڴ����˱��
	private String processSubjectName;// ��ǰ���ڴ���������
	private String processSubjectDepartmentId; // �����˲��ű��
	private String processSubjectDepartmentName; // ����������
	private String processSubjectDistrict; // ����������

	private String nextProcessSubjectType; // ��һ���ڴ��������ͣ����ˡ����š������飩
	private String nextProcessSubjectId; // ��һ���ڴ����˱��
	private String nextProcessSubjectName;// ��һ���ڴ���������
	private String nextProcessSubjectDepartId; // ��һ�����˲��ű��
	private String nextProcessSubjectDepartName; // ��һ����������
	private String nextProcessSubjectDistrict; // ��һ����������

	private String overtime; // �Ƿ�ʱ
	private Long processUsedTime; // ������ʱ ��λ����
	private Long processTimeLimit; // ����ʱ�� ��λ����
	private String data = "��"; // �������
	private String attachment;// ����
	private Timestamp processTime = new Timestamp(System.currentTimeMillis()); // ����ʱ��

	public TaskProcessDbObj()
	{
	}

	/**
	 * ���ݵ�ǰ�����˴�������ʵ��
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
	 * ���ݴ������崴������ʵ��
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
			return "0����";
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
