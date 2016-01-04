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
	private Long taskId; // 工单编号
	private String taskType; // 工单类型
	private String taskSubType; // 工单类型
	// private String title;// 工单标题
	private String criticalLevel;// 紧要程度
	private Long timeLimit;// 处理时限，单位天
	private String taskStatus;// 当前状态
	private Timestamp latestProcessTime = new Timestamp(System.currentTimeMillis());;// 最新操作时间
	private Long telNum;// 用户电话
	private String workDiscription;// 其他信息
	private String custColValues;// 定制化字段信息
	private String customerInfo;// 用户附加信息
	private String processResult;// 工单处理结果：处理成功、处理失败
	private String processResultComment;// 工单处理结果说明

	// 派单信息
	private String createUserAccount; // 发起人账号
	private String createUserName; // 发起人姓名
	private String createUserDepartmentId; // 发起人部门编号
	private String createUserDepartmentName; // 发起人姓名
	private String createUserDistrict;// 发起人区县
	private Timestamp createTime; // 发起时间

	// 审批信息
	private String auditSubjectType; // 审批人类型（个人、部门、虚拟组）
	private String auditSubjectId; // 审批人编号
	private String auditSubjectName; // 审批人名称
	private String auditSubjectDepartmentId; // 审批人部门编号
	private String auditSubjectDepartmentName; // 审批人部门名称
	private String auditSubjectDistrict;// 发起人区县

	private String auditUserAccount;// 具体执行审批的人员账号
	private String auditUserName;// 具体执行审批的人员姓名
	private Timestamp auditTime; // 最新审批时间，如果多次审批则只记录最新的时间
	private String auditResult;// 最新审批结果，如果多次审批则只记录最新的结果

	// 受理信息
	private String acceptSubjectType; // 受理人类型（个人、部门、虚拟组）
	private String acceptSubjectId; // 受理人编号
	private String acceptSubjectName; // 受理人名称
	private String acceptSubjectDepartmentId; // 受理人部门编号
	private String acceptSubjectDepartmentName; // 受理人部门名称
	private String acceptSubjectDistrict;// 受理人区县

	// 回复信息
	private String revertUserAccount;// 具体回复的人员账号
	private String revertUserName;// 具体执行回复的人员姓名
	private Timestamp revertTime; // 最新回复时间，如果多次回复则只记录最新的时间
	private String revertResult;// 最新回复结果，如果多次回复则只记录最新的结果

	// 归档审核信息
	private Timestamp archiveVerifyTime; // 最新归档审核时间，如果多次归档审核则只记录最新的时间
	private String archiveVerifyResult;// 最新归档审核结果，如果多次归档审核则只记录最新的结果

	// 当前环节信息
	private String currentProcessName; // 当前环节名称
	private String currentProcessSubjectType; // 当前环节处理人类型（个人、部门、虚拟组）
	private String currentProcessSubjectId; // 当前环节处理人编号
	private String currentProcessSubjectName;// 当前环节处理人姓名
	private String currentProcessSubjectDepartId; // 当前环节处理人部门编号
	private String currentProcessSubjectDepartNm; // 当前环节处理人部门名称
	private String currentProcessSubjectDistrict; // 当前环节处理人区县
	private Long currentProcessTimeLimit; // 当前环节处理时限，单位小时

	// 上一个环节信息，上一个环节肯定是由具体人员处理的，所以直接具体到人
	private String previousProcessName; // 上一环节名称
	private String previousProcessUserAccount; // 上一环节处理人账号
	private String previousProcessUserName; // 上一环节处理人姓名
	private String previousProcessUserDepartId; // 上一环节处理人部门编号
	private String previousProcessUserDepartNm; // 上一环节处理人部门名称
	private String previousProcessUserDistrict; // 上一环节处理人区县
	private Timestamp previousProcessTime; // 上一环节处理时间

	public TaskMainInfoDbObj()
	{
	}

	// 仅用于工单流转时更新工单状态信息使用
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

		// 工单审批
		if (TaskConstant.TP_AUDIT.equals(currentTp.getProcessName()))
		{
			this.setAuditUserAccount(currentTp.getProcessSubjectId());
			this.setAuditUserName(currentTp.getProcessSubjectName());
			this.setAuditTime(currentTp.getProcessTime());
			this.setAuditResult(currentTp.getProcessResult());
		}

		// 工单回复
		if (TaskConstant.TP_REVERT.equals(currentTp.getProcessName()))
		{
			this.setRevertUserAccount(currentTp.getProcessSubjectId());
			this.setRevertUserName(currentTp.getProcessSubjectName());
			this.setRevertTime(currentTp.getProcessTime());
			this.setRevertResult(currentTp.getProcessResult());
			this.setProcessResult(currentTp.getProcessResult());

			// 已经存储过工单回复信息，就不再更新了
			TaskMainInfoDbObj task = (TaskMainInfoDbObj) new DefaultBaseDAO(new TaskMainInfoDbObj()).searchByKey(TaskMainInfoDbObj.class, taskId + "");
			if (!StringUtil.isEmpty(task.getProcessResultComment()))
			{	
				this.setProcessResultComment(null);
			} else
			{
				this.setProcessResultComment(currentTp.getData());
			}
		}

		// 工单归档审核
		if (TaskConstant.TP_ARCHIVE_VERIFY.equals(currentTp.getProcessName()))
		{
			this.setArchiveVerifyTime(currentTp.getProcessTime());
			this.setArchiveVerifyResult(currentTp.getProcessResult());
		}
	}

	// 判断用户是否有权限处理该工单
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
		return isOverTime() ? "是" : "否";
	}

	// // 工单是否超时
	// public boolean isOverTime()
	// {
	// // 如果还没有 派单审批 或者 派单审批驳回了，则自然未超时（因为受理人此时还看不到工单）
	// if (!isAudited() || (isAudited() &&
	// TaskConstant.RESULT_REJECT.equals(this.auditResult)))
	// {
	// return false;
	// } else
	// {
	// // 如果工单已经回复，则计算从 派单审批通过 到 工单回复 之间的时间间隔；否则计算从 派单审批通过 的时间 到当前的时间间隔
	// Date date = new Date();
	// if (revertTime != null)
	// {
	// date = revertTime;
	// }
	// return date.getTime() > getDeadlineTime().getTime();
	// }
	// }
	//	
	// // 获取工单超时的时间
	// public String getOverTimeShow()
	// {
	// if (!isAudited())
	// {
	// return "审批后" + timeLimit + "天";
	// } else
	// {
	// return TimeUtil.getEasyReadTimeMi(getDeadlineTime());// 审批时间+处理时限
	// }
	// }
	//
	// // 获取工单最后处理时限
	// private Date getDeadlineTime()
	// {
	// return new Date(auditTime.getTime() + this.getTimeLimit() * 24 * 3600 *
	// 1000);
	// }
	//
	// // 获取工单超时的时间
	// public String getOverTimeStr()
	// {
	// if (!isAudited())
	// {
	// return "审批后" + timeLimit + "天";
	// } else
	// {
	// return TimeUtil.date2str(new Date(this.auditTime.getTime() +
	// this.getTimeLimit() * 24 * 3600 * 1000));
	// }
	// }

	// 工单是否超时
	public boolean isOverTime()
	{
		Date date = new Date();
		if (revertTime != null)
		{
			date = revertTime;
		}
		return date.getTime() > getDeadlineTime().getTime();
	}

	// 获取工单超时的时间
	public String getOverTimeShow()
	{
		return TimeUtil.getEasyReadTimeMi(getDeadlineTime());// 审批时间+处理时限
	}

	// 获取工单最后处理时限
	private Date getDeadlineTime()
	{
		return new Date(createTime.getTime() + this.getTimeLimit() * 24 * 3600 * 1000);
	}

	// 获取工单超时的时，仅供导出使用
	public String getOverTimeStr()
	{
		return TimeUtil.date2str(getDeadlineTime());
	}

	// 获取当前环节的英文名
	public String getCurrentProcessEnName()
	{
		return TaskConstant.getProcessEnName(this.getCurrentProcessName());
	}

	// 获取前一环节的英文名
	public String getPreviousProcessEnName()
	{
		return TaskConstant.getProcessEnName(this.getPreviousProcessName());
	}

	// 获取工单创建时间的易读表示
	public String getCreateTimeShow()
	{
		if (getCreateTime() == null)
		{
			return "";
		}
		return TimeUtil.getEasyReadTimeMi(getCreateTime());
	}

	// 获取工单累计耗时的易读表示
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

	// 判断工单是否可以修改
	public boolean canModify(AuthUser user)
	{
		// 创建人是该用户
		if (user.getAccount().equals(this.createUserAccount) && (
		// 用户只保存了草稿未提交
				TaskConstant.TP_DRAFT.equalsIgnoreCase(this.previousProcessName) ||
				// 用户提交了但是被驳回了
				(TaskConstant.TP_SUBMIT.equals(this.currentProcessName) && TaskConstant.TP_AUDIT.equals(this.previousProcessName))))
		{
			return true;
		} else
		{
			return false;
		}
	}

	// 获取工单创建主体信息
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

	// 获取工单审批主体信息
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

	// 获取工单受理主体信息
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

	// 获取当前处理主体信息
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

	// 获取上一个环节的处理主体信息
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

	// 是否审批了
	public boolean isAudited()
	{
		return this.auditTime != null;
	}

	// 是否回复了
	public boolean isReverted()
	{
		return this.revertTime != null;
	}

	// 是否归档审核了
	public boolean isArchiveVerifyed()
	{
		return this.archiveVerifyTime != null;
	}

	// 处理失败后的逐级回复
	public boolean isFailedRevert()
	{
		// 当前环节和上一环节都是 回复 ，就需要逐级回复
		return TaskConstant.TP_REVERT.equals(this.currentProcessName) && TaskConstant.TP_REVERT.equals(this.previousProcessName);
	}

	// 导出excel时使用的字段映射关系
	public LinkedHashMap<String, String> getProperties()
	{
		LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
		p.put("taskId", "工单编号");
		p.put("telNum", "用户号码");
		p.put("taskType", "工单大类");
		p.put("taskSubType", "工单小类");
		p.put("criticalLevel", "紧急程度");
		p.put("createUserDistrict", "派单人区县");
		p.put("createUserDepartmentName", "派单人部门");
		p.put("createUserName", "派单人");
		p.put("createTime", "派单时间");
		p.put("auditSubjectDistrict", "审批人区县");
		p.put("auditSubjectDepartmentName", "审批人部门");
		p.put("auditSubjectName", "审批人");
		p.put("auditTime", "审批时间");
		p.put("acceptSubjectDistrict", "受理区县");
		p.put("acceptSubjectDepartmentName", "受理部门");
		p.put("acceptSubjectName", "受理人");
		p.put("revertTime", "回复时间");
		p.put("processResult", "处理结果");
		p.put("processResultComment", "处理说明");
		p.put("currentProcessSubjectDistrict", "当前处理人区县");
		p.put("currentProcessSubjectDepartNm", "当前处理人部门");
		p.put("currentProcessName", "当前处理人");
		p.put("taskStatus", "当前状态");
		p.put("currentProcessName", "当前环节");
		p.put("isOverTime", "是否超时");
		p.put("overTimeStr", "超时时间");
		p.put("custColValues", "工单其他信息");
		p.put("customerInfo", "用户附加信息");
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
