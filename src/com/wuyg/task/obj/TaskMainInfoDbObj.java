package com.wuyg.task.obj;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;

import com.hz.auth.obj.AuthUser;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.common.util.StringUtil;
import com.wuyg.common.util.TimeUtil;
import com.wuyg.task.TaskConstant;

public class TaskMainInfoDbObj extends BaseDbObj
{
	private Long taskId; // �������
	private String taskType; // ��������
	private String taskSubType; // ��������
	// private String title;// ��������
	private String criticalLevel;// ��Ҫ�̶�
	private Long timeLimit;// ����ʱ�ޣ���λ��
	private String taskStatus;// ��ǰ״̬
	private Timestamp latestProcessTime = new Timestamp(System.currentTimeMillis());;// ���²���ʱ��
	private Long telNum;// �û��绰
	private String workDiscription;// ������Ϣ
	private String custColValues;// ���ƻ��ֶ���Ϣ
	private String customerInfo;// �û�������Ϣ
	private String processResult;// ����������������ɹ�������ʧ��
	private String processResultComment;// ����������˵��

	// �ɵ���Ϣ
	private String createUserAccount; // �������˺�
	private String createUserName; // ����������
	private String createUserDepartmentId; // �����˲��ű��
	private String createUserDepartmentName; // ����������
	private String createUserDistrict;// ����������
	private Timestamp createTime; // ����ʱ��

	// ������Ϣ
	private String auditSubjectType; // ���������ͣ����ˡ����š������飩
	private String auditSubjectId; // �����˱��
	private String auditSubjectName; // ����������
	private String auditSubjectDepartmentId; // �����˲��ű��
	private String auditSubjectDepartmentName; // �����˲�������
	private String auditSubjectDistrict;// ����������

	private String auditUserAccount;// ����ִ����������Ա�˺�
	private String auditUserName;// ����ִ����������Ա����
	private Timestamp auditTime; // ��������ʱ�䣬������������ֻ��¼���µ�ʱ��
	private String auditResult;// �������������������������ֻ��¼���µĽ��

	// ������Ϣ
	private String acceptSubjectType; // ���������ͣ����ˡ����š������飩
	private String acceptSubjectId; // �����˱��
	private String acceptSubjectName; // ����������
	private String acceptSubjectDepartmentId; // �����˲��ű��
	private String acceptSubjectDepartmentName; // �����˲�������
	private String acceptSubjectDistrict;// ����������

	// �ظ���Ϣ
	private String revertUserAccount;// ����ظ�����Ա�˺�
	private String revertUserName;// ����ִ�лظ�����Ա����
	private Timestamp revertTime; // ���»ظ�ʱ�䣬�����λظ���ֻ��¼���µ�ʱ��
	private String revertResult;// ���»ظ�����������λظ���ֻ��¼���µĽ��

	// �鵵�����Ϣ
	private Timestamp archiveVerifyTime; // ���¹鵵���ʱ�䣬�����ι鵵�����ֻ��¼���µ�ʱ��
	private String archiveVerifyResult;// ���¹鵵��˽���������ι鵵�����ֻ��¼���µĽ��

	// ��ǰ������Ϣ
	private String currentProcessName; // ��ǰ��������
	private String currentProcessSubjectType; // ��ǰ���ڴ��������ͣ����ˡ����š������飩
	private String currentProcessSubjectId; // ��ǰ���ڴ����˱��
	private String currentProcessSubjectName;// ��ǰ���ڴ���������
	private String currentProcessSubjectDepartId; // ��ǰ���ڴ����˲��ű��
	private String currentProcessSubjectDepartNm; // ��ǰ���ڴ����˲�������
	private String currentProcessSubjectDistrict; // ��ǰ���ڴ���������
	private Long currentProcessTimeLimit; // ��ǰ���ڴ���ʱ�ޣ���λСʱ

	// ��һ��������Ϣ����һ�����ڿ϶����ɾ�����Ա����ģ�����ֱ�Ӿ��嵽��
	private String previousProcessName; // ��һ��������
	private String previousProcessUserAccount; // ��һ���ڴ������˺�
	private String previousProcessUserName; // ��һ���ڴ���������
	private String previousProcessUserDepartId; // ��һ���ڴ����˲��ű��
	private String previousProcessUserDepartNm; // ��һ���ڴ����˲�������
	private String previousProcessUserDistrict; // ��һ���ڴ���������
	private Timestamp previousProcessTime; // ��һ���ڴ���ʱ��

	public TaskMainInfoDbObj()
	{
	}

	// �����ڹ�����תʱ���¹���״̬��Ϣʹ��
	public TaskMainInfoDbObj(long taskId, TaskProcessDbObj currentTp, TaskProcessDbObj nextTp, String taskStatus)
	{
		this.taskId = taskId;
		this.taskStatus = taskStatus;
		this.latestProcessTime = new Timestamp(System.currentTimeMillis());

		this.currentProcessName = nextTp.getProcessName();
		this.currentProcessSubjectType = nextTp.getProcessSubjectType();
		this.currentProcessSubjectId = nextTp.getProcessSubjectId();
		this.currentProcessSubjectName = nextTp.getProcessSubjectName();
		this.currentProcessSubjectDepartId = nextTp.getProcessSubjectDepartmentId();
		this.currentProcessSubjectDepartNm = nextTp.getProcessSubjectDepartmentName();
		this.currentProcessSubjectDistrict = nextTp.getProcessSubjectDistrict();
		this.currentProcessTimeLimit = nextTp.getProcessTimeLimit();

		this.previousProcessName = currentTp.getProcessName();
		this.previousProcessUserAccount = currentTp.getProcessSubjectId();
		this.previousProcessUserName = currentTp.getProcessSubjectName();
		this.previousProcessUserDepartId = currentTp.getProcessSubjectDepartmentId();
		this.previousProcessUserDepartNm = currentTp.getProcessSubjectDepartmentName();
		this.previousProcessUserDistrict = currentTp.getProcessSubjectDistrict();
		this.previousProcessTime = new Timestamp(System.currentTimeMillis());

		// ��������
		if (TaskConstant.TP_AUDIT.equals(currentTp.getProcessName()))
		{
			this.setAuditUserAccount(currentTp.getProcessSubjectId());
			this.setAuditUserName(currentTp.getProcessSubjectName());
			this.setAuditTime(currentTp.getProcessTime());
			this.setAuditResult(currentTp.getProcessResult());
		}

		// �����ظ�
		if (TaskConstant.TP_REVERT.equals(currentTp.getProcessName()))
		{
			this.setRevertUserAccount(currentTp.getProcessSubjectId());
			this.setRevertUserName(currentTp.getProcessSubjectName());
			this.setRevertTime(currentTp.getProcessTime());
			this.setRevertResult(currentTp.getProcessResult());
			this.setProcessResult(currentTp.getProcessResult());

			// �Ѿ��洢�������ظ���Ϣ���Ͳ��ٸ�����
			TaskMainInfoDbObj task = (TaskMainInfoDbObj) new DefaultBaseDAO(new TaskMainInfoDbObj()).searchByKey(TaskMainInfoDbObj.class, taskId + "");
			if (!StringUtil.isEmpty(task.getProcessResultComment()))
			{	
				this.setProcessResultComment(null);
			} else
			{
				this.setProcessResultComment(currentTp.getData());
			}
		}

		// �����鵵���
		if (TaskConstant.TP_ARCHIVE_VERIFY.equals(currentTp.getProcessName()))
		{
			this.setArchiveVerifyTime(currentTp.getProcessTime());
			this.setArchiveVerifyResult(currentTp.getProcessResult());
		}
	}

	// �ж��û��Ƿ���Ȩ�޴���ù���
	public boolean canProcessByTheUser(AuthUser user)
	{
		if (TaskConstant.SUBJECT_TYPE_PERSON.equals(this.getCurrentProcessSubjectType()))
		{
			return user.getAccount().equals(this.getCurrentProcessSubjectId());
		} else if (TaskConstant.SUBJECT_TYPE_DEPARTMENT.equals(this.getCurrentProcessSubjectType()))
		{
			return user.getDepartmentId().equals(this.getCurrentProcessSubjectId());
		} else if (TaskConstant.SUBJECT_TYPE_GROUP.equals(this.getCurrentProcessSubjectType()))
		{
			// @toDo
			return false;
		}

		return false;
	}

	public String getIsOverTime()
	{
		return isOverTime() ? "��" : "��";
	}

	// // �����Ƿ�ʱ
	// public boolean isOverTime()
	// {
	// // �����û�� �ɵ����� ���� �ɵ����������ˣ�����Ȼδ��ʱ����Ϊ�����˴�ʱ��������������
	// if (!isAudited() || (isAudited() &&
	// TaskConstant.RESULT_REJECT.equals(this.auditResult)))
	// {
	// return false;
	// } else
	// {
	// // ��������Ѿ��ظ��������� �ɵ�����ͨ�� �� �����ظ� ֮���ʱ�������������� �ɵ�����ͨ�� ��ʱ�� ����ǰ��ʱ����
	// Date date = new Date();
	// if (revertTime != null)
	// {
	// date = revertTime;
	// }
	// return date.getTime() > getDeadlineTime().getTime();
	// }
	// }
	//	
	// // ��ȡ������ʱ��ʱ��
	// public String getOverTimeShow()
	// {
	// if (!isAudited())
	// {
	// return "������" + timeLimit + "��";
	// } else
	// {
	// return TimeUtil.getEasyReadTimeMi(getDeadlineTime());// ����ʱ��+����ʱ��
	// }
	// }
	//
	// // ��ȡ���������ʱ��
	// private Date getDeadlineTime()
	// {
	// return new Date(auditTime.getTime() + this.getTimeLimit() * 24 * 3600 *
	// 1000);
	// }
	//
	// // ��ȡ������ʱ��ʱ��
	// public String getOverTimeStr()
	// {
	// if (!isAudited())
	// {
	// return "������" + timeLimit + "��";
	// } else
	// {
	// return TimeUtil.date2str(new Date(this.auditTime.getTime() +
	// this.getTimeLimit() * 24 * 3600 * 1000));
	// }
	// }

	// �����Ƿ�ʱ
	public boolean isOverTime()
	{
		Date date = new Date();
		if (revertTime != null)
		{
			date = revertTime;
		}
		return date.getTime() > getDeadlineTime().getTime();
	}

	// ��ȡ������ʱ��ʱ��
	public String getOverTimeShow()
	{
		return TimeUtil.getEasyReadTimeMi(getDeadlineTime());// ����ʱ��+����ʱ��
	}

	// ��ȡ���������ʱ��
	private Date getDeadlineTime()
	{
		return new Date(createTime.getTime() + this.getTimeLimit() * 24 * 3600 * 1000);
	}

	// ��ȡ������ʱ��ʱ����������ʹ��
	public String getOverTimeStr()
	{
		return TimeUtil.date2str(getDeadlineTime());
	}

	// ��ȡ��ǰ���ڵ�Ӣ����
	public String getCurrentProcessEnName()
	{
		return TaskConstant.getProcessEnName(this.getCurrentProcessName());
	}

	// ��ȡǰһ���ڵ�Ӣ����
	public String getPreviousProcessEnName()
	{
		return TaskConstant.getProcessEnName(this.getPreviousProcessName());
	}

	// ��ȡ��������ʱ����׶���ʾ
	public String getCreateTimeShow()
	{
		if (getCreateTime() == null)
		{
			return "";
		}
		return TimeUtil.getEasyReadTimeMi(getCreateTime());
	}

	// ��ȡ�����ۼƺ�ʱ���׶���ʾ
	public String getSpentTimeShow()
	{
		if (getCreateTime() == null)
		{
			return "";
		}

		Date lastTime = new Date();
		if (TaskConstant.TP_ARCHIVE.equalsIgnoreCase(this.getPreviousProcessName()))
		{
			lastTime = this.getPreviousProcessTime();
		}
		return TimeUtil.timeInerval2str(lastTime.getTime() - this.getCreateTime().getTime());
	}

	// �жϹ����Ƿ�����޸�
	public boolean canModify(AuthUser user)
	{
		// �������Ǹ��û�
		if (user.getAccount().equals(this.createUserAccount) && (
		// �û�ֻ�����˲ݸ�δ�ύ
				TaskConstant.TP_DRAFT.equalsIgnoreCase(this.previousProcessName) ||
				// �û��ύ�˵��Ǳ�������
				(TaskConstant.TP_SUBMIT.equals(this.currentProcessName) && TaskConstant.TP_AUDIT.equals(this.previousProcessName))))
		{
			return true;
		} else
		{
			return false;
		}
	}

	// ��ȡ��������������Ϣ
	public TaskProcessSubject getCreateSubject()
	{
		TaskProcessSubject subject = new TaskProcessSubject();
		subject.setSubjectType(TaskConstant.SUBJECT_TYPE_PERSON);
		subject.setSubjectId(this.createUserAccount);
		subject.setSubjectName(this.createUserName);
		subject.setSubjectDepartmentId(this.createUserDepartmentId);
		subject.setSubjectDepartmentName(this.createUserDepartmentName);
		subject.setSubjectDistrict(this.createUserDistrict);
		return subject;
	}

	// ��ȡ��������������Ϣ
	public TaskProcessSubject getAuditSubject()
	{
		TaskProcessSubject subject = new TaskProcessSubject();
		subject.setSubjectType(this.auditSubjectType);
		subject.setSubjectId(this.auditSubjectId);
		subject.setSubjectName(this.auditSubjectName);
		subject.setSubjectDepartmentId(this.auditSubjectDepartmentId);
		subject.setSubjectDepartmentName(this.auditSubjectDepartmentName);
		subject.setSubjectDistrict(this.auditSubjectDistrict);
		return subject;
	}

	// ��ȡ��������������Ϣ
	public TaskProcessSubject getAcceptSubject()
	{
		TaskProcessSubject subject = new TaskProcessSubject();
		subject.setSubjectType(this.acceptSubjectType);
		subject.setSubjectId(this.acceptSubjectId);
		subject.setSubjectName(this.acceptSubjectName);
		subject.setSubjectDepartmentId(this.acceptSubjectDepartmentId);
		subject.setSubjectDepartmentName(this.acceptSubjectDepartmentName);
		subject.setSubjectDistrict(this.acceptSubjectDistrict);
		return subject;
	}

	// ��ȡ��ǰ����������Ϣ
	public TaskProcessSubject getCurrentProcessSubject()
	{
		TaskProcessSubject subject = new TaskProcessSubject();
		subject.setSubjectType(this.currentProcessSubjectType);
		subject.setSubjectId(this.currentProcessSubjectId);
		subject.setSubjectName(this.currentProcessSubjectName);
		subject.setSubjectDepartmentId(this.currentProcessSubjectDepartId);
		subject.setSubjectDepartmentName(this.currentProcessSubjectDepartNm);
		subject.setSubjectDistrict(this.currentProcessSubjectDistrict);
		return subject;
	}

	// ��ȡ��һ�����ڵĴ���������Ϣ
	public TaskProcessSubject getPreviousProcessSubject()
	{
		TaskProcessSubject subject = new TaskProcessSubject();
		subject.setSubjectType(TaskConstant.SUBJECT_TYPE_PERSON);
		subject.setSubjectId(this.previousProcessUserAccount);
		subject.setSubjectName(this.previousProcessUserName);
		subject.setSubjectDepartmentId(this.previousProcessUserDepartId);
		subject.setSubjectDepartmentName(this.previousProcessUserDepartNm);
		subject.setSubjectDistrict(this.previousProcessUserDistrict);
		return subject;
	}

	// �Ƿ�������
	public boolean isAudited()
	{
		return this.auditTime != null;
	}

	// �Ƿ�ظ���
	public boolean isReverted()
	{
		return this.revertTime != null;
	}

	// �Ƿ�鵵�����
	public boolean isArchiveVerifyed()
	{
		return this.archiveVerifyTime != null;
	}

	// ����ʧ�ܺ���𼶻ظ�
	public boolean isFailedRevert()
	{
		// ��ǰ���ں���һ���ڶ��� �ظ� ������Ҫ�𼶻ظ�
		return TaskConstant.TP_REVERT.equals(this.currentProcessName) && TaskConstant.TP_REVERT.equals(this.previousProcessName);
	}

	// ����excelʱʹ�õ��ֶ�ӳ���ϵ
	public LinkedHashMap<String, String> getProperties()
	{
		LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
		p.put("taskId", "�������");
		p.put("telNum", "�û�����");
		p.put("taskType", "��������");
		p.put("taskSubType", "����С��");
		p.put("criticalLevel", "�����̶�");
		p.put("createUserDistrict", "�ɵ�������");
		p.put("createUserDepartmentName", "�ɵ��˲���");
		p.put("createUserName", "�ɵ���");
		p.put("createTime", "�ɵ�ʱ��");
		p.put("auditSubjectDistrict", "����������");
		p.put("auditSubjectDepartmentName", "�����˲���");
		p.put("auditSubjectName", "������");
		p.put("auditTime", "����ʱ��");
		p.put("acceptSubjectDistrict", "��������");
		p.put("acceptSubjectDepartmentName", "������");
		p.put("acceptSubjectName", "������");
		p.put("revertTime", "�ظ�ʱ��");
		p.put("processResult", "������");
		p.put("processResultComment", "����˵��");
		p.put("currentProcessSubjectDistrict", "��ǰ����������");
		p.put("currentProcessSubjectDepartNm", "��ǰ�����˲���");
		p.put("currentProcessName", "��ǰ������");
		p.put("taskStatus", "��ǰ״̬");
		p.put("currentProcessName", "��ǰ����");
		p.put("isOverTime", "�Ƿ�ʱ");
		p.put("overTimeStr", "��ʱʱ��");
		p.put("custColValues", "����������Ϣ");
		p.put("customerInfo", "�û�������Ϣ");
		return p;
	}

	public String getProcessResult()
	{
		return processResult;
	}

	public void setProcessResult(String processResult)
	{
		this.processResult = processResult;
	}

	public String getCustomerInfo()
	{
		return customerInfo;
	}

	public void setCustomerInfo(String customerInfo)
	{
		this.customerInfo = customerInfo;
	}

	public String getCustColValues()
	{
		return custColValues;
	}

	public void setCustColValues(String custColValues)
	{
		this.custColValues = custColValues;
	}

	public String getCurrentProcessSubjectDistrict()
	{
		return currentProcessSubjectDistrict;
	}

	public String getProcessResultComment()
	{
		return processResultComment;
	}

	public void setProcessResultComment(String processResultComment)
	{
		this.processResultComment = processResultComment;
	}

	public void setCurrentProcessSubjectDistrict(String currentProcessSubjectDistrict)
	{
		this.currentProcessSubjectDistrict = currentProcessSubjectDistrict;
	}

	public String getPreviousProcessUserDistrict()
	{
		return previousProcessUserDistrict;
	}

	public void setPreviousProcessUserDistrict(String previousProcessUserDistrict)
	{
		this.previousProcessUserDistrict = previousProcessUserDistrict;
	}

	public String getCreateUserDistrict()
	{
		return createUserDistrict;
	}

	public void setCreateUserDistrict(String createUserDistrict)
	{
		this.createUserDistrict = createUserDistrict;
	}

	public String getAuditSubjectDistrict()
	{
		return auditSubjectDistrict;
	}

	public void setAuditSubjectDistrict(String auditSubjectDistrict)
	{
		this.auditSubjectDistrict = auditSubjectDistrict;
	}

	public String getAcceptSubjectDistrict()
	{
		return acceptSubjectDistrict;
	}

	public void setAcceptSubjectDistrict(String acceptSubjectDistrict)
	{
		this.acceptSubjectDistrict = acceptSubjectDistrict;
	}

	public String getAuditUserAccount()
	{
		return auditUserAccount;
	}

	public void setAuditUserAccount(String auditUserAccount)
	{
		this.auditUserAccount = auditUserAccount;
	}

	public String getAuditUserName()
	{
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName)
	{
		this.auditUserName = auditUserName;
	}

	public Timestamp getAuditTime()
	{
		return auditTime;
	}

	public void setAuditTime(Timestamp auditTime)
	{
		this.auditTime = auditTime;
	}

	public String getAuditResult()
	{
		return auditResult;
	}

	public void setAuditResult(String auditResult)
	{
		this.auditResult = auditResult;
	}

	public String getRevertUserAccount()
	{
		return revertUserAccount;
	}

	public void setRevertUserAccount(String revertUserAccount)
	{
		this.revertUserAccount = revertUserAccount;
	}

	public String getRevertUserName()
	{
		return revertUserName;
	}

	public void setRevertUserName(String revertUserName)
	{
		this.revertUserName = revertUserName;
	}

	public Timestamp getRevertTime()
	{
		return revertTime;
	}

	public void setRevertTime(Timestamp revertTime)
	{
		this.revertTime = revertTime;
	}

	public String getRevertResult()
	{
		return revertResult;
	}

	public void setRevertResult(String revertResult)
	{
		this.revertResult = revertResult;
	}

	public Timestamp getArchiveVerifyTime()
	{
		return archiveVerifyTime;
	}

	public void setArchiveVerifyTime(Timestamp archiveVerifyTime)
	{
		this.archiveVerifyTime = archiveVerifyTime;
	}

	public String getArchiveVerifyResult()
	{
		return archiveVerifyResult;
	}

	public void setArchiveVerifyResult(String archiveVerifyResult)
	{
		this.archiveVerifyResult = archiveVerifyResult;
	}

	public String getWorkDiscription()
	{
		return workDiscription;
	}

	public void setWorkDiscription(String workDiscription)
	{
		this.workDiscription = workDiscription;
	}

	public Timestamp getLatestProcessTime()
	{
		return latestProcessTime;
	}

	public void setLatestProcessTime(Timestamp latestProcessTime)
	{
		this.latestProcessTime = latestProcessTime;
	}

	public String getTaskStatus()
	{
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus)
	{
		this.taskStatus = taskStatus;
	}

	public void setTimeLimit(Long timeLimit)
	{
		this.timeLimit = timeLimit;
	}

	public void setCurrentProcessTimeLimit(Long currentProcessTimeLimit)
	{
		this.currentProcessTimeLimit = currentProcessTimeLimit;
	}

	public String getAuditSubjectId()
	{
		return auditSubjectId;
	}

	public void setAuditSubjectId(String auditSubjectId)
	{
		this.auditSubjectId = auditSubjectId;
	}

	public String getAuditSubjectName()
	{
		return auditSubjectName;
	}

	public void setAuditSubjectName(String auditSubjectName)
	{
		this.auditSubjectName = auditSubjectName;
	}

	public String getAuditSubjectDepartmentId()
	{
		return auditSubjectDepartmentId;
	}

	public Long getTelNum()
	{
		return telNum;
	}

	public void setTelNum(Long telNum)
	{
		this.telNum = telNum;
	}

	public void setAuditSubjectDepartmentId(String auditSubjectDepartmentId)
	{
		this.auditSubjectDepartmentId = auditSubjectDepartmentId;
	}

	public String getAuditSubjectDepartmentName()
	{
		return auditSubjectDepartmentName;
	}

	public void setAuditSubjectDepartmentName(String auditSubjectDepartmentName)
	{
		this.auditSubjectDepartmentName = auditSubjectDepartmentName;
	}

	public String getCurrentProcessSubjectDepartId()
	{
		return currentProcessSubjectDepartId;
	}

	public void setCurrentProcessSubjectDepartId(String currentProcessSubjectDepartId)
	{
		this.currentProcessSubjectDepartId = currentProcessSubjectDepartId;
	}

	public String getCurrentProcessSubjectDepartNm()
	{
		return currentProcessSubjectDepartNm;
	}

	public void setCurrentProcessSubjectDepartNm(String currentProcessSubjectDepartName)
	{
		this.currentProcessSubjectDepartNm = currentProcessSubjectDepartName;
	}

	public String getPreviousProcessUserDepartId()
	{
		return previousProcessUserDepartId;
	}

	public void setPreviousProcessUserDepartId(String previousProcessUserDepartId)
	{
		this.previousProcessUserDepartId = previousProcessUserDepartId;
	}

	public String getPreviousProcessUserDepartNm()
	{
		return previousProcessUserDepartNm;
	}

	public void setPreviousProcessUserDepartNm(String previousProcessUserDepartName)
	{
		this.previousProcessUserDepartNm = previousProcessUserDepartName;
	}

	public Long getTaskId()
	{
		return taskId;
	}

	public void setTaskId(Long taskId)
	{
		this.taskId = taskId;
	}

	public String getTaskType()
	{
		return taskType;
	}

	public void setTaskType(String taskType)
	{
		this.taskType = taskType;
	}

	public String getCreateUserAccount()
	{
		return createUserAccount;
	}

	public void setCreateUserAccount(String createUserAccount)
	{
		this.createUserAccount = createUserAccount;
	}

	public String getCreateUserName()
	{
		return createUserName;
	}

	public void setCreateUserName(String createUserName)
	{
		this.createUserName = createUserName;
	}

	public Timestamp getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
	}

	public String getCurrentProcessName()
	{
		return currentProcessName;
	}

	public void setCurrentProcessName(String currentProcessName)
	{
		this.currentProcessName = currentProcessName;
	}

	public Long getCurrentProcessTimeLimit()
	{
		return currentProcessTimeLimit;
	}

	public void setCurrentProcessTimeLimit(long currentProcessTimeLimit)
	{
		this.currentProcessTimeLimit = currentProcessTimeLimit;
	}

	public String getPreviousProcessName()
	{
		return previousProcessName;
	}

	public void setPreviousProcessName(String previousProcessName)
	{
		this.previousProcessName = previousProcessName;
	}

	public String getPreviousProcessUserAccount()
	{
		return previousProcessUserAccount;
	}

	public void setPreviousProcessUserAccount(String previousProcessUserAccount)
	{
		this.previousProcessUserAccount = previousProcessUserAccount;
	}

	public String getPreviousProcessUserName()
	{
		return previousProcessUserName;
	}

	public void setPreviousProcessUserName(String previousProcessUserName)
	{
		this.previousProcessUserName = previousProcessUserName;
	}

	public Timestamp getPreviousProcessTime()
	{
		return previousProcessTime;
	}

	public void setPreviousProcessTime(Timestamp previousProcessTime)
	{
		this.previousProcessTime = previousProcessTime;
	}

	public Long getTimeLimit()
	{
		return timeLimit;
	}

	public void setTimeLimit(long timeLimit)
	{
		this.timeLimit = timeLimit;
	}

	public String getTaskSubType()
	{
		return taskSubType;
	}

	public void setTaskSubType(String taskSubType)
	{
		this.taskSubType = taskSubType;
	}

	// public String getTitle()
	// {
	// return title;
	// }
	//
	// public void setTitle(String title)
	// {
	// this.title = title;
	// }

	public String getCriticalLevel()
	{
		return criticalLevel;
	}

	public void setCriticalLevel(String criticalLevel)
	{
		this.criticalLevel = criticalLevel;
	}

	public String getCreateUserDepartmentId()
	{
		return createUserDepartmentId;
	}

	public void setCreateUserDepartmentId(String createUserDepartmentId)
	{
		this.createUserDepartmentId = createUserDepartmentId;
	}

	public String getCreateUserDepartmentName()
	{
		return createUserDepartmentName;
	}

	public void setCreateUserDepartmentName(String createUserDepartmentName)
	{
		this.createUserDepartmentName = createUserDepartmentName;
	}

	public String getAcceptSubjectType()
	{
		return acceptSubjectType;
	}

	public void setAcceptSubjectType(String acceptSubjectType)
	{
		this.acceptSubjectType = acceptSubjectType;
	}

	public String getAcceptSubjectId()
	{
		return acceptSubjectId;
	}

	public void setAcceptSubjectId(String acceptSubjectId)
	{
		this.acceptSubjectId = acceptSubjectId;
	}

	public String getAcceptSubjectName()
	{
		return acceptSubjectName;
	}

	public void setAcceptSubjectName(String acceptSubjectName)
	{
		this.acceptSubjectName = acceptSubjectName;
	}

	public String getAcceptSubjectDepartmentId()
	{
		return acceptSubjectDepartmentId;
	}

	public void setAcceptSubjectDepartmentId(String acceptSubjectDepartmentId)
	{
		this.acceptSubjectDepartmentId = acceptSubjectDepartmentId;
	}

	public String getAcceptSubjectDepartmentName()
	{
		return acceptSubjectDepartmentName;
	}

	public void setAcceptSubjectDepartmentName(String acceptSubjectDepartmentName)
	{
		this.acceptSubjectDepartmentName = acceptSubjectDepartmentName;
	}

	public String getAuditSubjectType()
	{
		return auditSubjectType;
	}

	public void setAuditSubjectType(String auditSubjectType)
	{
		this.auditSubjectType = auditSubjectType;
	}

	public String getCurrentProcessSubjectType()
	{
		return currentProcessSubjectType;
	}

	public void setCurrentProcessSubjectType(String currentProcessSubjectType)
	{
		this.currentProcessSubjectType = currentProcessSubjectType;
	}

	public String getCurrentProcessSubjectId()
	{
		return currentProcessSubjectId;
	}

	public void setCurrentProcessSubjectId(String currentProcessSubjectId)
	{
		this.currentProcessSubjectId = currentProcessSubjectId;
	}

	public String getCurrentProcessSubjectName()
	{
		return currentProcessSubjectName;
	}

	public void setCurrentProcessSubjectName(String currentProcessSubjectName)
	{
		this.currentProcessSubjectName = currentProcessSubjectName;
	}

	@Override
	public String findKeyColumnName()
	{
		return "taskId";
	}

	@Override
	public String findParentKeyColumnName()
	{
		return null;
	}

	@Override
	public String findTableName()
	{
		return "task_main_info";
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
}
