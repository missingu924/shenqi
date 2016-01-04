package com.wuyg.task.service;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.hz.auth.obj.AuthUser;
import com.hz.util.SystemConstant;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.common.message.IMessageSender;
import com.wuyg.common.message.SmsModemMessageSender;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.common.util.MyBeanUtils;
import com.wuyg.common.util.MySqlUtil;
import com.wuyg.common.util.StringUtil;
import com.wuyg.common.util.TimeUtil;
import com.wuyg.task.TaskConstant;
import com.wuyg.task.obj.TaskCustomizableColumnDbObj;
import com.wuyg.task.obj.TaskCustomizableColumns;
import com.wuyg.task.obj.TaskMainInfoDbObj;
import com.wuyg.task.obj.TaskObj;
import com.wuyg.task.obj.TaskProcessDbObj;
import com.wuyg.task.obj.TaskProcessSubject;
import com.wuyg.task.obj.TaskSearchComplexCondition;
import com.wuyg.task.process.ITaskProcess;
import com.wuyg.task.process.TaskAddProcessDbObj;

public class TaskService implements ITaskService
{
	private Logger logger = Logger.getLogger(getClass());
	private IBaseDAO taskMainInfoBaseDAO = new DefaultBaseDAO(new TaskMainInfoDbObj());
	private IBaseDAO taskProcessBaseDAO = new DefaultBaseDAO(new TaskProcessDbObj());
	private IBaseDAO taskAddProcessBaseDAO = new DefaultBaseDAO(new TaskAddProcessDbObj());
	private IBaseDAO customizableColumnsDAO = new DefaultBaseDAO(new TaskCustomizableColumnDbObj());
	private IBaseDAO authUserBaseDAO = new DefaultBaseDAO(new AuthUser());
	// private IMessageSender messageSender = new SmsMessageSender();
	private IMessageSender messageSender = new SmsModemMessageSender();

	public TaskObj getTaskById(long taskId, Class businessDataClz) throws Exception
	{
		TaskObj taskObj = new TaskObj();

		// 工单主信息
		TaskMainInfoDbObj taskMainInfo = (TaskMainInfoDbObj) taskMainInfoBaseDAO.searchByKey(TaskMainInfoDbObj.class, taskId + "");
		taskObj.setTaskMainInfo(taskMainInfo);

		// 业务数据
		// IBaseDAO businessBaseDAO = new DefaultBaseDAO((BaseDbObj)
		// businessDataClz.newInstance());
		// Object businessData = businessBaseDAO.searchByKey(businessDataClz,
		// taskId + "");
		// taskObj.setBusinessData(businessData);

		// 环节数据
		IBaseDAO tpBaseDAO = new DefaultBaseDAO(new TaskProcessDbObj());
		List<TaskProcessDbObj> tpList = tpBaseDAO.searchByParentKey(TaskProcessDbObj.class, taskId + "", (new TaskProcessDbObj().findKeyColumnName() + " asc"));
		taskObj.setTaskProcessList(tpList);

		return taskObj;
	}

	// 复杂条件查询
	public PaginationObj searchTaskPaginationByClause(TaskSearchComplexCondition condition)
	{
		try
		{
			StringBuffer where = new StringBuffer(" 1=1 ");

			// 1、首先把非空的基本条件设置上
			TaskMainInfoDbObj taskMainInfo = condition.getTaskMainInfo();
			// 几个默认时间要置空，使用前台页面设置的时间
			taskMainInfo.setLatestProcessTime(null);
			List<PropertyDescriptor> notNullProperties = MyBeanUtils.getNotNullPropertyDescriptors(taskMainInfo, null, null);
			for (int i = 0; i < notNullProperties.size(); i++)
			{
				PropertyDescriptor p = notNullProperties.get(i);
				String pName = p.getName();
				String pValue = BeanUtils.getProperty(taskMainInfo, pName);
				where.append(" and " + pName + "='" + pValue + "'");
			}

			// 2、然后再设置其他条件

			// 2.1派单时间
			if (condition.getCreateStartTime() != null)
			{
				where.append(" and createTime>='" + TimeUtil.date2str(condition.getCreateStartTime()) + "'");
			}
			if (condition.getCreateEndTime() != null)
			{
				where.append(" and createTime<='" + TimeUtil.date2str(condition.getCreateEndTime()) + "'");
			}

			// 2.2是否超时
			if ("是".equals(condition.getOverTime()))
			{
				where.append(" and " + getOverTimeClause());
			} else if ("否".equals(condition.getOverTime()))
			{
				where.append(" and " + getNotOverTimeClause());
			}

			// 2.3与当前用户的关系
			if (TaskConstant.RELATIONSHITP_NEED_PROCESS.equals(condition.getRelationshipWithUser()))
			{
				where.append(" and " + getRelatedNeedProcessTaskClause(condition.getUser()));
			} else if (TaskConstant.RELATIONSHITP_PROCESSED.equals(condition.getRelationshipWithUser()))
			{
				where.append(" and " + getRelatedProcessedTaskClause(condition.getUser()));
			} else if (TaskConstant.RELATIONSHITP_ARCHIVED.equals(condition.getRelationshipWithUser()))
			{
				where.append(" and " + getRelatedArchivedTaskClause(condition.getUser()));
			} else if (TaskConstant.RELATIONSHITP_RELEATED.equals(condition.getRelationshipWithUser()))
			{
				where.append(" and " + getRelatedTaskClause(condition.getUser()));
			}

			// 2.4几小时内将到期
			if (condition.getToBeOverTimeHours() > 0)
			{
				where.append(" and " + getToBeOverTimeClause(condition.getToBeOverTimeHours()));
			}

			// 2.5权限限制
			where.append(" and " + getAuthClause(condition.getUser()));

			return taskMainInfoBaseDAO.searchPaginationByClause(TaskMainInfoDbObj.class, "(" + where.toString() + ")", "latestProcessTime desc", condition.getPageNo(), condition
					.getPageCount());
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			return new PaginationObj();
		}
	}

	public long taskCreate(TaskObj taskObj, boolean submit)
	{
		// 1、获取工单编号
		long taskId = taskMainInfoBaseDAO.getMaxKeyValue() + 1;
		taskObj.setTaskId(taskId);

		// 2、保存工单信息
		TaskMainInfoDbObj taskMainInfo = taskObj.getTaskMainInfo();
		taskMainInfoBaseDAO.save(taskMainInfo);

		// 3、保存业务数据
		// BaseDbObj businessData = (BaseDbObj) taskObj.getBusinessData();
		// IBaseDAO businessDataBaseDAO = new DefaultBaseDAO(businessData);
		// businessDataBaseDAO.save(businessData);

		if (submit)
		{
			// 4、提交审批
			taskSubmit(taskId, taskMainInfo);
		} else
		{
			// 4、保存草稿
			taskSaveDraft(taskId, taskMainInfo);
		}

		return taskId;
	}

	public boolean taskModify(TaskObj taskObj, boolean submit)
	{
		// 1、更新工单主信息
		TaskMainInfoDbObj taskMainInfo = taskObj.getTaskMainInfo();
		taskMainInfoBaseDAO.update(taskMainInfo);

		// 2、更新业务数据
		BaseDbObj businessData = (BaseDbObj) taskObj.getBusinessData();
		IBaseDAO businessDataBaseDAO = new DefaultBaseDAO(businessData);
		businessDataBaseDAO.update(businessData);

		if (submit)
		{
			// 3、提交审批
			taskSubmit(taskObj.getTaskId(), taskObj.getTaskMainInfo());
		} else
		{
			// 4、保存草稿
			taskSaveDraft(taskObj.getTaskId(), taskMainInfo);
		}

		return true;
	}

	public boolean taskAudit(long taskId, AuthUser user, boolean agreed, String auditInfomation)
	{
		// 审核：如果审核通过，则流转到 受理人 处理；否则回退到上一环节

		if (agreed)
		{
			TaskMainInfoDbObj taskMainInfo = (TaskMainInfoDbObj) taskMainInfoBaseDAO.searchByKey(TaskMainInfoDbObj.class, taskId + "");

			// 当前环节：审批环节
			TaskProcessDbObj auditTp = new TaskProcessDbObj(taskId, user, TaskConstant.TP_AUDIT, TaskConstant.RESULT_AGREED, auditInfomation, null);

			// 审批通过，流转至 受理人 处理
			TaskProcessDbObj revertPt = new TaskProcessDbObj(taskId, taskMainInfo.getAcceptSubject(), TaskConstant.TP_REVERT, null, null, null);

			return commonProcess(taskId, auditTp, revertPt, TaskConstant.STATUS_NEED_REVERT);
		} else
		{
			// 审批不通过，流转至 发单人 重新编辑

			return taskReject(taskId, user, TaskConstant.TP_AUDIT, auditInfomation);
		}

	}

	public boolean taskForward(long taskId, AuthUser user, TaskProcessSubject processSubject, String forwardInfomation)
	{
		// 转派:转派给下一个处理主体

		// 当前环节：转发环节
		TaskProcessDbObj forwardTp = new TaskProcessDbObj(taskId, user, TaskConstant.TP_FORWRD, null, forwardInfomation, null);

		// 下一环节：处理环节
		TaskProcessDbObj processTp = new TaskProcessDbObj(taskId, processSubject, TaskConstant.TP_REVERT, null, null, null);

		return commonProcess(taskId, forwardTp, processTp, TaskConstant.STATUS_FORWRDED);
	}

	public boolean taskFeedback(long taskId, AuthUser user, String feedbackInformation)
	{
		// 阶段反馈:工单状态不变，只记录反馈信息
		TaskProcessDbObj feedbackTp = new TaskProcessDbObj(taskId, user, TaskConstant.TP_FEEDBACK, null, feedbackInformation, null);
		// 用时
		long usedTime = getUsedTime(feedbackTp);
		feedbackTp.setProcessUsedTime(usedTime);

		// 下一环节：处理环节
		TaskProcessDbObj processTp = new TaskProcessDbObj(taskId, user, TaskConstant.TP_REVERT, null, null, null);

		return commonProcess(taskId, feedbackTp, processTp, TaskConstant.STATUS_FEEDBACKED);
	}

	public boolean taskRevert(long taskId, AuthUser user, String processResult, String revertInformation, String attachment)
	{
		// 工单回复：提请工单发起人进行归档核实

		boolean processSuccess = TaskConstant.RESULT_SUCCESS.equals(processResult);

		// 当前环节：工单回复
		TaskProcessDbObj revertTp = new TaskProcessDbObj(taskId, user, TaskConstant.TP_REVERT, processResult, revertInformation + (processSuccess ? "" : " 。工单处理失败，需要逐级回复。"),
				attachment);

		// 如果处理结果为 处理失败 ，则需要按照流转过程逐级回复
		TaskProcessDbObj nextTp = null;
		boolean canArchiveVerify = false;// 是否可以进行归档审核
		if (processSuccess)// 处理成功
		{
			canArchiveVerify = true;
		} else
		// 处理失败
		{
			// 获取流转步骤中 转派给当前用户 的处理人，即上一个转派人
			String clause = "taskId=" + taskId + " and processName='" + TaskConstant.TP_FORWRD + "' and ((nextProcessSubjectType='" + TaskConstant.SUBJECT_TYPE_PERSON
					+ "' and nextProcessSubjectId='" + user.getAccount() + "') or (nextProcessSubjectType='" + TaskConstant.SUBJECT_TYPE_DEPARTMENT
					+ "' and nextProcessSubjectDepartId='" + user.getDepartmentId() + "'))";
			List<TaskProcessDbObj> preForwardProcessList = taskProcessBaseDAO.searchByClause(TaskProcessDbObj.class, clause, "processId desc", 0, 1);
			if (preForwardProcessList.size() > 0)
			{
				// 之前有转派人
				TaskProcessDbObj preForwardProcess = preForwardProcessList.get(0);
				// 下一环节：前一个转派人继续回复
				TaskProcessDbObj revertProcess = new TaskProcessDbObj(taskId, preForwardProcess.getProcessSubject(), TaskConstant.TP_REVERT, null, null, null);
				nextTp = revertProcess;
			} else
			{
				// 当前用户已经第一个处理人
				canArchiveVerify = true;
			}
		}

		if (canArchiveVerify)
		{
			// 下一环节：提请工单发起人进行归档核实
			TaskMainInfoDbObj taskMainInfo = (TaskMainInfoDbObj) taskMainInfoBaseDAO.searchByKey(TaskMainInfoDbObj.class, taskId + "");
			TaskProcessDbObj archiveVerifyTp = new TaskProcessDbObj(taskId, taskMainInfo.getCreateSubject(), TaskConstant.TP_ARCHIVE_VERIFY, null, null, null);
			nextTp = archiveVerifyTp;
		}

		return commonProcess(taskId, revertTp, nextTp, (processSuccess || canArchiveVerify) ? TaskConstant.STATUS_NEED_ARCHIVE_VERIFY : TaskConstant.STATUS_NEED_REVERT);
	}

	public boolean taskArchiveVerify(long taskId, AuthUser user, boolean agreed, String archiveVerifytInformation)
	{
		if (agreed)
		{
			return taskArchive(taskId, user, "归档审核通过。" + archiveVerifytInformation);
		} else
		{
			return taskReject(taskId, user, TaskConstant.TP_ARCHIVE_VERIFY, archiveVerifytInformation);
		}
	}

	// 通用处理过程
	public boolean commonProcess(long taskId, TaskProcessDbObj currentTp, TaskProcessDbObj nextTp, String taskStatus)
	{
		// 1、更新工单主信息:主要是更新当前处理环节、处理人等信息
		TaskMainInfoDbObj task4Update = new TaskMainInfoDbObj(taskId, currentTp, nextTp, taskStatus);
		boolean rst = taskMainInfoBaseDAO.update(task4Update);

		TaskMainInfoDbObj taskInDb = (TaskMainInfoDbObj) taskMainInfoBaseDAO.searchByKey(TaskMainInfoDbObj.class, taskId + "");

		// 2、工单处理环节信息入库：工单环节表 task_process
		if (rst)
		{
			// 用时
			long usedTime = getUsedTime(currentTp);
			currentTp.setProcessUsedTime(usedTime);
			// 下一环节处理对象
			currentTp.setNextProcessSubject(nextTp.getProcessSubject());
			// 保存
			long processId = taskProcessBaseDAO.getMaxKeyValue() + 1;
			currentTp.setProcessId(processId);
			rst = taskProcessBaseDAO.save(currentTp);

			// 3、执行该环节的附加动作
			try
			{
				List<TaskAddProcessDbObj> taskAddProcessList = taskAddProcessBaseDAO.searchByClause(TaskAddProcessDbObj.class, "taskType='" + taskInDb.getTaskType()
						+ "' and taskSubType='" + taskInDb.getTaskSubType() + "' and processName='" + currentTp.getProcessName() + "'", null, 0, 1);

				if (taskAddProcessList.size() > 0)
				{
					ITaskProcess addProcess = (ITaskProcess) Thread.currentThread().getContextClassLoader().loadClass(taskAddProcessList.get(0).getProcessClass()).newInstance();
					logger.info("执行附加动作：taskType=" + taskInDb.getTaskType() + ", taskSubType=" + taskInDb.getTaskSubType() + ", processName=" + currentTp.getProcessName()
							+ ", process=" + addProcess.getClass());
					addProcess.process(taskInDb.getTaskType(), taskInDb.getTaskSubType(), currentTp.getProcessName(), taskInDb);
				}
			} catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}

			// 4、给下一环节的处理人发送短信，1、下一环节是提交审批（即草稿状态，驳回除外）、归档的时候不用发；2、当前处理人和下一环节处理人相同不用发
			sendSms(taskInDb, currentTp, nextTp);

		}
		return rst;
	}

	private void taskSubmit(long taskId, TaskMainInfoDbObj taskMainInfo)
	{
		// 当前环节为工单提交环节
		TaskProcessDbObj taskSubmitTp = new TaskProcessDbObj(taskId, taskMainInfo.getCreateSubject(), TaskConstant.TP_SUBMIT, null, "提交审批", null);

		// 下一个环节根据情况看是审批环节还是处理环节
		TaskProcessDbObj nextTp = new TaskProcessDbObj();
		if (!StringUtil.isEmpty(taskMainInfo.getAuditSubjectId()))
		{
			// 需要审批的工单，转给审批人进行审批
			TaskProcessDbObj taskAuditTp = new TaskProcessDbObj(taskId, taskMainInfo.getAuditSubject(), TaskConstant.TP_AUDIT, null, null, null);

			nextTp = taskAuditTp;
		} else if (!StringUtil.isEmpty(taskMainInfo.getAcceptSubjectId()))
		{
			// 不需要审批的工单，转给受理人处理
			TaskProcessDbObj taskProcessTp = new TaskProcessDbObj(taskId, taskMainInfo.getAcceptSubject(), TaskConstant.TP_REVERT, null, null, null);

			nextTp = taskProcessTp;
		}
		this.commonProcess(taskId, taskSubmitTp, nextTp, TaskConstant.STATUS_NEED_AUDIT);
	}

	private boolean taskReject(long taskId, AuthUser user, String currentProcessName, String rejectInformation)
	{
		// 驳回：记录驳回信息，工单回退到上一环节

		// 当前环节：驳回环节信息
		TaskProcessDbObj rejectTp = new TaskProcessDbObj(taskId, user, currentProcessName, TaskConstant.RESULT_REJECT, rejectInformation, null);

		// 下一环节：工单回退到上一处理人环节
		TaskMainInfoDbObj taskMainInfo = (TaskMainInfoDbObj) taskMainInfoBaseDAO.searchByKey(TaskMainInfoDbObj.class, taskId + "");
		TaskProcessDbObj nextTp = new TaskProcessDbObj(taskId, taskMainInfo.getPreviousProcessSubject(), taskMainInfo.getPreviousProcessName(), null, null, null);

		return commonProcess(taskId, rejectTp, nextTp, currentProcessName + TaskConstant.STATUS_REJECTED);
	}

	private boolean taskArchive(long taskId, AuthUser user, String archiveInfomation)
	{
		// 归档：工单结束

		// 当前环节：归档审核环节
		TaskProcessDbObj archiveVerifyTp = new TaskProcessDbObj(taskId, user, TaskConstant.TP_ARCHIVE_VERIFY, TaskConstant.RESULT_AGREED, archiveInfomation, null);

		// 工单归档
		AuthUser systemAuthUser = new AuthUser();
		systemAuthUser.setAccount("SYSTEM");
		systemAuthUser.setName("系统");
		systemAuthUser.setDepartmentName("工单系统");
		systemAuthUser.setDistrict("市公司");
		TaskProcessDbObj archiveTp = new TaskProcessDbObj(taskId, systemAuthUser, TaskConstant.TP_ARCHIVE, null, archiveInfomation, null);

		return commonProcess(taskId, archiveVerifyTp, archiveTp, TaskConstant.STATUS_ARCHIVED);
	}

	private void taskSaveDraft(long taskId, TaskMainInfoDbObj taskMainInfo)
	{
		// 保存草稿

		// 当前环节：保存草稿
		TaskProcessDbObj draftTp = new TaskProcessDbObj(taskId, taskMainInfo.getCreateSubject(), TaskConstant.TP_DRAFT, null, "保存草稿", null);

		// 一下环节：提交审批
		TaskProcessDbObj submitTp = new TaskProcessDbObj(taskId, taskMainInfo.getCreateSubject(), TaskConstant.TP_SUBMIT, null, null, null);

		commonProcess(taskId, draftTp, submitTp, TaskConstant.STATUS_NEED_SUBMIT);
	}

	// 发短信
	private void sendSms(TaskMainInfoDbObj task, TaskProcessDbObj currentTp, TaskProcessDbObj nextTp)
	{
		if ((!TaskConstant.TP_SUBMIT.equals(nextTp.getProcessName())// 下一环节不是提交审批（即草稿状态，驳回除外）
				&& !TaskConstant.TP_ARCHIVE.equals(nextTp.getProcessName())// 下一环节不是归档（即已归档状态）
		&& !currentTp.getProcessSubjectId().equals(nextTp.getProcessSubjectId()))// 当前处理人和下一环节处理人不同
				|| (TaskConstant.TP_SUBMIT.equals(nextTp.getProcessName()) && TaskConstant.TP_AUDIT.equals(currentTp.getProcessName()))// 草稿状态，驳回除外
		)
		{
			// 发短信
			List<AuthUser> nextUsers = new ArrayList<AuthUser>();
			if (TaskConstant.SUBJECT_TYPE_PERSON.equals(nextTp.getProcessSubjectType()))
			{
				// 个人
				nextUsers = authUserBaseDAO.searchByClause(AuthUser.class, "account='" + nextTp.getProcessSubjectId() + "'", null, 0, 1);
			}
			if (TaskConstant.SUBJECT_TYPE_DEPARTMENT.equals(nextTp.getProcessSubjectType()))
			{
				// 部门
				nextUsers = authUserBaseDAO.searchByClause(AuthUser.class, "departmentId='" + nextTp.getProcessSubjectId() + "'", null, 0, Integer.MAX_VALUE);
			}

			List<String> userPhones = new ArrayList<String>();
			for (int i = 0; i < nextUsers.size(); i++)
			{
				userPhones.add(nextUsers.get(i).getTelephone());
			}
			try
			{
				// xx号工单需要您进行xx，派单人xx，用户号码xx，派发人xx。【10088协同工单系统】
				String smsContent = task.getTaskSubType() + currentTp.getTaskId() + "号工单需要您"
						+ (TaskConstant.SUBJECT_TYPE_DEPARTMENT.equals(nextTp.getProcessSubjectType()) ? "部门" : "") + "进行" + nextTp.getProcessName() + "，派单人"
						+ task.getCreateUserDepartmentName() + task.getCreateUserName() + "。"//
						+ "【" + SystemConstant.SYSTEM_NAME + "】";
				messageSender.sendMessage(userPhones, smsContent);
			} catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}
		}
	}

	private long getUsedTime(TaskProcessDbObj currentTp)
	{
		// 获取上一个环节的信息，计算该环节使用的时间，单位为分钟o
		List<TaskProcessDbObj> tpList = taskProcessBaseDAO.searchByClause(TaskProcessDbObj.class, "taskId=" + currentTp.getTaskId(), "processId desc", 0, 1);
		if (tpList.size() > 0)
		{
			TaskProcessDbObj previousTP = tpList.get(0);
			return (long) Math.ceil(((currentTp.getProcessTime().getTime() - previousTP.getProcessTime().getTime()) / 60 / 1000));
		}
		return 0;
	}

	public boolean updateTask(TaskObj taskObj)
	{
		// TODO Auto-generated method stub
		return false;
	}

	// 获取即将超时的工单 查询条件
	private String getToBeOverTimeClause(int toBeOverTimeHours)
	{
		// String where = " date_add(ifnull(auditTime,now()),INTERVAL
		// timeLimit*24-" + toBeOverTimeHours
		// + " hour)<=now() and date_add(ifnull(auditTime,now()),INTERVAL
		// timeLimit day)>now() and currentProcessName!='" +
		// TaskConstant.TP_ARCHIVE + "'";

		String where = "";
		if (MySqlUtil.isMySql(taskMainInfoBaseDAO.getDbName()))
		{
			where = " date_add(ifnull(createTime,now()),INTERVAL timeLimit*24-" + toBeOverTimeHours
					+ " hour)<=now() and date_add(ifnull(createTime,now()),INTERVAL timeLimit day)>now() and currentProcessName!='" + TaskConstant.TP_ARCHIVE + "'";

		} else if (MySqlUtil.isOracle(taskMainInfoBaseDAO.getDbName()))
		{
			where = " (createTime+timeLimit-" + toBeOverTimeHours + "/24)<=sysdate " //
					+ "and (createTime+ timeLimit)>sysdate " //
					+ " and currentProcessName!='" + TaskConstant.TP_ARCHIVE + "'";

		}

		return " (" + where + ") ";
	}

	// 获取超时的工单 查询条件
	private String getOverTimeClause()
	{
		// String where = " (currentProcessName!='" + TaskConstant.TP_ARCHIVE +
		// "' and date_add(ifnull(auditTime,now()),INTERVAL timeLimit
		// day)<=now()) ";// 未归档的超时用审批时间和当前时间比较
		// where += " or (currentProcessName='" + TaskConstant.TP_ARCHIVE + "'
		// and date_add(auditTime,INTERVAL timeLimit day)<=revertTime)";//
		// 已归档的超时用审批时间和工单回复时间比较

		String where = "";
		if (MySqlUtil.isMySql(taskMainInfoBaseDAO.getDbName()))
		{
			where = " (revertTime is null and date_add(ifnull(createTime,now()),INTERVAL timeLimit day)<=now()) ";// 未回复的超时用派单时间和当前时间比较
			where += " or (revertTime is not null and date_add(createTime,INTERVAL timeLimit day)<=revertTime)";// 已回复的超时用派单时间和工单回复时间比较

		} else if (MySqlUtil.isOracle(taskMainInfoBaseDAO.getDbName()))
		{
			where = " (revertTime is null and createTime<=(sysdate-timeLimit)) ";// 未回复的超时用派单时间和当前时间比较
			where += " or (revertTime is not null and createTime<=(revertTime-timeLimit))";// 已回复的超时用派单时间和工单回复时间比较

		}
		return " (" + where + ") ";
	}

	// 获取未超时的工单 查询条件
	private String getNotOverTimeClause()
	{
		// String where = " (currentProcessName!='" + TaskConstant.TP_ARCHIVE +
		// "' and date_add(ifnull(auditTime,now()),INTERVAL timeLimit
		// day)>now()) ";// 未归档的超时用审批时间和当前时间比较
		// where += " or (currentProcessName='" + TaskConstant.TP_ARCHIVE + "'
		// and date_add(auditTime,INTERVAL timeLimit day)>revertTime)";//
		// 已归档的超时用审批时间和工单回复时间比较

		String where = "";
		if (MySqlUtil.isMySql(taskMainInfoBaseDAO.getDbName()))
		{
			where = " (revertTime is null and date_add(ifnull(createTime,now()),INTERVAL timeLimit day)>now()) ";// 未回复的超时用派单时间和当前时间比较
			where += " or (revertTime is not null and date_add(createTime,INTERVAL timeLimit day)>revertTime)";// 已回复的超时用派单时间和工单回复时间比较

		} else if (MySqlUtil.isOracle(taskMainInfoBaseDAO.getDbName()))
		{
			where = " (revertTime is null and (createTime+timeLimit)>sysdate) ";// 未回复的超时用派单时间和当前时间比较
			where += " or (revertTime is not null and (createTime+timeLimit)>revertTime)";// 已回复的超时用派单时间和工单回复时间比较

		}
		return " (" + where + ") ";
	}

	// 获取与我相关的所有工单 查询条件
	private String getRelatedTaskClause(AuthUser user)
	{
		// 处理记录中有该用户操作过的环节
		String where = "taskId in (select taskId from task_process where processSubjectId='" + user.getAccount() + "') ";
		where += " or ";
		// 或者 需要该用户后续处理的
		where += getRelatedNeedProcessTaskClause(user);

		return " (" + where + ") ";
	}

	// 获取与我相关且"已归档"的工单 查询条件
	private String getRelatedArchivedTaskClause(AuthUser user)
	{
		// 处理记录中有该用户操作过的环节 且 工单当前环节是 归档
		String where = "taskId in (select taskId from task_process where processSubjectId='" + user.getAccount() + "') and currentProcessName='" + TaskConstant.TP_ARCHIVE + "'";
		return " (" + where + ") ";
	}

	// 获取与我相关且"已处理未归档"的工单 查询条件
	private String getRelatedProcessedTaskClause(AuthUser user)
	{
		// 处理记录中有该用户操作过的环节 且 工单当前环节不是归档 且 当前处理人不是该用户
		String where = "taskId in (select taskId from task_process where processSubjectId='" + user.getAccount() + "') and currentProcessName!='" + TaskConstant.TP_ARCHIVE
				+ "' and currentProcessSubjectId!='" + user.getAccount() + "'";
		return " (" + where + ") ";
	}

	// 获取与我相关且"待处理”工单 查询条件
	private String getRelatedNeedProcessTaskClause(AuthUser user)
	{
		// 当前处理人为该用户
		String where = " (currentProcessSubjectType='" + TaskConstant.SUBJECT_TYPE_PERSON + "' and currentProcessSubjectId='" + user.getAccount() + "') ";
		// 或者 当前处理部门为该用户所在部门
		where += "or (currentProcessSubjectType='" + TaskConstant.SUBJECT_TYPE_DEPARTMENT + "' and currentProcessSubjectId='" + user.getDepartmentId() + "') ";
		// 或者 当前处理组为该用户所在组
		if (user.getGroupIds() != null && user.getGroupIds().size() > 0)
		{
			where += "or (currentProcessSubjectType='" + TaskConstant.SUBJECT_TYPE_GROUP + "' and currentProcessSubjectId in ("
					+ StringUtil.getStringByListWithQuotation(user.getGroupIds()) + ")) ";
		}

		return " (" + where + ") ";
	}

	private String getAuthClause(AuthUser user)
	{
		String where = "";
		if (user.isCityUser())
		{
			where = "1=1";
		} else if (user.isDistrictUser())
		{
			where = " taskId in (select taskId from task_process where processSubjectDistrict='" + user.getDistrict() + "' or nextProcessSubjectDistrict='" + user.getDistrict()
					+ "')";
		} else if (user.isDepartmentUser())
		{
			where = " taskId in (select taskId from task_process where processSubjectDepartmentId='" + user.getDepartmentId() + "' or nextProcessSubjectDepartId='"
					+ user.getDepartmentId() + "')";
		}
		// add by wuyg 2013-10-25 防止用户所属部门变化后看不到待办工工单，当前处理人为该用户
		where += " or (currentProcessSubjectType='" + TaskConstant.SUBJECT_TYPE_PERSON + "' and currentProcessSubjectId='" + user.getAccount() + "') ";

		return " (" + where + ") ";
	}

	public boolean saveOrUpdateCustomizableColumns(TaskCustomizableColumns taskCustomizableColumns)
	{
		// 先删除原有的字段
		customizableColumnsDAO.deleteByClause(" taskType='" + taskCustomizableColumns.getTaskType() + "' and taskSubType='" + taskCustomizableColumns.getTaskSubType()
				+ "' and taskProcessName='" + taskCustomizableColumns.getTaskProcessName() + "'");

		// 然后保存新的字段
		List<TaskCustomizableColumnDbObj> columns = taskCustomizableColumns.getColumns();
		for (int i = 0; i < columns.size(); i++)
		{
			TaskCustomizableColumnDbObj column = columns.get(i);
			long id = customizableColumnsDAO.getMaxKeyValue() + 1;
			column.setId(id);
			customizableColumnsDAO.save(column);
		}

		return true;
	}

	public TaskCustomizableColumns getTaskCustomizableColumns(String taskType, String taskSubType, String taskProcessName)
	{
		List<TaskCustomizableColumnDbObj> columns = customizableColumnsDAO.searchByClause(TaskCustomizableColumnDbObj.class, " taskType='" + taskType + "' and taskSubType='"
				+ taskSubType + "' and taskProcessName='" + taskProcessName + "'", "id", 0, Integer.MAX_VALUE);

		TaskCustomizableColumns taskCustomizableColumns = new TaskCustomizableColumns();
		taskCustomizableColumns.setColumns(columns);
		taskCustomizableColumns.setTaskType(taskType);
		taskCustomizableColumns.setTaskSubType(taskSubType);
		taskCustomizableColumns.setTaskProcessName(taskProcessName);

		return taskCustomizableColumns;
	}

	public boolean deleteTaskCustomizableColumns(String taskType, String taskSubType)
	{
		customizableColumnsDAO.deleteByClause("taskType='" + taskType + "' and taskSubType='" + taskSubType + "' ");
		return true;
	}

	public List<TaskCustomizableColumns> searchTaskCustomizableColumns()
	{
		List<TaskCustomizableColumns> list = new ArrayList<TaskCustomizableColumns>();

		List<TaskCustomizableColumnDbObj> columns = customizableColumnsDAO.searchByClause(TaskCustomizableColumnDbObj.class, null, "taskType,taskSubType,taskProcessName", 0,
				Integer.MAX_VALUE);

		String taskType = "";
		String taskSubType = "";
		String taskProcessName = "";
		TaskCustomizableColumns taskCustomizableColumns = null;

		for (int i = 0; i < columns.size(); i++)
		{
			if (!taskType.equals(columns.get(i).getTaskType()) || !taskSubType.equals(columns.get(i).getTaskSubType())
					|| !taskProcessName.equals(columns.get(i).getTaskProcessName()))
			{
				taskType = columns.get(i).getTaskType();
				taskSubType = columns.get(i).getTaskSubType();
				taskProcessName = columns.get(i).getTaskProcessName();

				if (i != 0)
				{
					list.add(taskCustomizableColumns);
				}

				taskCustomizableColumns = new TaskCustomizableColumns();
				taskCustomizableColumns.addColumn(columns.get(i));
				taskCustomizableColumns.setTaskType(taskType);
				taskCustomizableColumns.setTaskSubType(taskSubType);
				taskCustomizableColumns.setTaskProcessName(taskProcessName);
			} else
			{
				taskCustomizableColumns.addColumn(columns.get(i));
			}

			if (i == columns.size() - 1)
			{
				list.add(taskCustomizableColumns);
			}
		}

		return list;
	}
}
