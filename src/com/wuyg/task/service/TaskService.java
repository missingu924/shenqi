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

		// ��������Ϣ
		TaskMainInfoDbObj taskMainInfo = (TaskMainInfoDbObj) taskMainInfoBaseDAO.searchByKey(TaskMainInfoDbObj.class, taskId + "");
		taskObj.setTaskMainInfo(taskMainInfo);

		// ҵ������
		// IBaseDAO businessBaseDAO = new DefaultBaseDAO((BaseDbObj)
		// businessDataClz.newInstance());
		// Object businessData = businessBaseDAO.searchByKey(businessDataClz,
		// taskId + "");
		// taskObj.setBusinessData(businessData);

		// ��������
		IBaseDAO tpBaseDAO = new DefaultBaseDAO(new TaskProcessDbObj());
		List<TaskProcessDbObj> tpList = tpBaseDAO.searchByParentKey(TaskProcessDbObj.class, taskId + "", (new TaskProcessDbObj().findKeyColumnName() + " asc"));
		taskObj.setTaskProcessList(tpList);

		return taskObj;
	}

	// ����������ѯ
	public PaginationObj searchTaskPaginationByClause(TaskSearchComplexCondition condition)
	{
		try
		{
			StringBuffer where = new StringBuffer(" 1=1 ");

			// 1�����ȰѷǿյĻ�������������
			TaskMainInfoDbObj taskMainInfo = condition.getTaskMainInfo();
			// ����Ĭ��ʱ��Ҫ�ÿգ�ʹ��ǰ̨ҳ�����õ�ʱ��
			taskMainInfo.setLatestProcessTime(null);
			List<PropertyDescriptor> notNullProperties = MyBeanUtils.getNotNullPropertyDescriptors(taskMainInfo, null, null);
			for (int i = 0; i < notNullProperties.size(); i++)
			{
				PropertyDescriptor p = notNullProperties.get(i);
				String pName = p.getName();
				String pValue = BeanUtils.getProperty(taskMainInfo, pName);
				where.append(" and " + pName + "='" + pValue + "'");
			}

			// 2��Ȼ����������������

			// 2.1�ɵ�ʱ��
			if (condition.getCreateStartTime() != null)
			{
				where.append(" and createTime>='" + TimeUtil.date2str(condition.getCreateStartTime()) + "'");
			}
			if (condition.getCreateEndTime() != null)
			{
				where.append(" and createTime<='" + TimeUtil.date2str(condition.getCreateEndTime()) + "'");
			}

			// 2.2�Ƿ�ʱ
			if ("��".equals(condition.getOverTime()))
			{
				where.append(" and " + getOverTimeClause());
			} else if ("��".equals(condition.getOverTime()))
			{
				where.append(" and " + getNotOverTimeClause());
			}

			// 2.3�뵱ǰ�û��Ĺ�ϵ
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

			// 2.4��Сʱ�ڽ�����
			if (condition.getToBeOverTimeHours() > 0)
			{
				where.append(" and " + getToBeOverTimeClause(condition.getToBeOverTimeHours()));
			}

			// 2.5Ȩ������
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
		// 1����ȡ�������
		long taskId = taskMainInfoBaseDAO.getMaxKeyValue() + 1;
		taskObj.setTaskId(taskId);

		// 2�����湤����Ϣ
		TaskMainInfoDbObj taskMainInfo = taskObj.getTaskMainInfo();
		taskMainInfoBaseDAO.save(taskMainInfo);

		// 3������ҵ������
		// BaseDbObj businessData = (BaseDbObj) taskObj.getBusinessData();
		// IBaseDAO businessDataBaseDAO = new DefaultBaseDAO(businessData);
		// businessDataBaseDAO.save(businessData);

		if (submit)
		{
			// 4���ύ����
			taskSubmit(taskId, taskMainInfo);
		} else
		{
			// 4������ݸ�
			taskSaveDraft(taskId, taskMainInfo);
		}

		return taskId;
	}

	public boolean taskModify(TaskObj taskObj, boolean submit)
	{
		// 1�����¹�������Ϣ
		TaskMainInfoDbObj taskMainInfo = taskObj.getTaskMainInfo();
		taskMainInfoBaseDAO.update(taskMainInfo);

		// 2������ҵ������
		BaseDbObj businessData = (BaseDbObj) taskObj.getBusinessData();
		IBaseDAO businessDataBaseDAO = new DefaultBaseDAO(businessData);
		businessDataBaseDAO.update(businessData);

		if (submit)
		{
			// 3���ύ����
			taskSubmit(taskObj.getTaskId(), taskObj.getTaskMainInfo());
		} else
		{
			// 4������ݸ�
			taskSaveDraft(taskObj.getTaskId(), taskMainInfo);
		}

		return true;
	}

	public boolean taskAudit(long taskId, AuthUser user, boolean agreed, String auditInfomation)
	{
		// ��ˣ�������ͨ��������ת�� ������ ����������˵���һ����

		if (agreed)
		{
			TaskMainInfoDbObj taskMainInfo = (TaskMainInfoDbObj) taskMainInfoBaseDAO.searchByKey(TaskMainInfoDbObj.class, taskId + "");

			// ��ǰ���ڣ���������
			TaskProcessDbObj auditTp = new TaskProcessDbObj(taskId, user, TaskConstant.TP_AUDIT, TaskConstant.RESULT_AGREED, auditInfomation, null);

			// ����ͨ������ת�� ������ ����
			TaskProcessDbObj revertPt = new TaskProcessDbObj(taskId, taskMainInfo.getAcceptSubject(), TaskConstant.TP_REVERT, null, null, null);

			return commonProcess(taskId, auditTp, revertPt, TaskConstant.STATUS_NEED_REVERT);
		} else
		{
			// ������ͨ������ת�� ������ ���±༭

			return taskReject(taskId, user, TaskConstant.TP_AUDIT, auditInfomation);
		}

	}

	public boolean taskForward(long taskId, AuthUser user, TaskProcessSubject processSubject, String forwardInfomation)
	{
		// ת��:ת�ɸ���һ����������

		// ��ǰ���ڣ�ת������
		TaskProcessDbObj forwardTp = new TaskProcessDbObj(taskId, user, TaskConstant.TP_FORWRD, null, forwardInfomation, null);

		// ��һ���ڣ�������
		TaskProcessDbObj processTp = new TaskProcessDbObj(taskId, processSubject, TaskConstant.TP_REVERT, null, null, null);

		return commonProcess(taskId, forwardTp, processTp, TaskConstant.STATUS_FORWRDED);
	}

	public boolean taskFeedback(long taskId, AuthUser user, String feedbackInformation)
	{
		// �׶η���:����״̬���䣬ֻ��¼������Ϣ
		TaskProcessDbObj feedbackTp = new TaskProcessDbObj(taskId, user, TaskConstant.TP_FEEDBACK, null, feedbackInformation, null);
		// ��ʱ
		long usedTime = getUsedTime(feedbackTp);
		feedbackTp.setProcessUsedTime(usedTime);

		// ��һ���ڣ�������
		TaskProcessDbObj processTp = new TaskProcessDbObj(taskId, user, TaskConstant.TP_REVERT, null, null, null);

		return commonProcess(taskId, feedbackTp, processTp, TaskConstant.STATUS_FEEDBACKED);
	}

	public boolean taskRevert(long taskId, AuthUser user, String processResult, String revertInformation, String attachment)
	{
		// �����ظ������빤�������˽��й鵵��ʵ

		boolean processSuccess = TaskConstant.RESULT_SUCCESS.equals(processResult);

		// ��ǰ���ڣ������ظ�
		TaskProcessDbObj revertTp = new TaskProcessDbObj(taskId, user, TaskConstant.TP_REVERT, processResult, revertInformation + (processSuccess ? "" : " ����������ʧ�ܣ���Ҫ�𼶻ظ���"),
				attachment);

		// ���������Ϊ ����ʧ�� ������Ҫ������ת�����𼶻ظ�
		TaskProcessDbObj nextTp = null;
		boolean canArchiveVerify = false;// �Ƿ���Խ��й鵵���
		if (processSuccess)// ����ɹ�
		{
			canArchiveVerify = true;
		} else
		// ����ʧ��
		{
			// ��ȡ��ת������ ת�ɸ���ǰ�û� �Ĵ����ˣ�����һ��ת����
			String clause = "taskId=" + taskId + " and processName='" + TaskConstant.TP_FORWRD + "' and ((nextProcessSubjectType='" + TaskConstant.SUBJECT_TYPE_PERSON
					+ "' and nextProcessSubjectId='" + user.getAccount() + "') or (nextProcessSubjectType='" + TaskConstant.SUBJECT_TYPE_DEPARTMENT
					+ "' and nextProcessSubjectDepartId='" + user.getDepartmentId() + "'))";
			List<TaskProcessDbObj> preForwardProcessList = taskProcessBaseDAO.searchByClause(TaskProcessDbObj.class, clause, "processId desc", 0, 1);
			if (preForwardProcessList.size() > 0)
			{
				// ֮ǰ��ת����
				TaskProcessDbObj preForwardProcess = preForwardProcessList.get(0);
				// ��һ���ڣ�ǰһ��ת���˼����ظ�
				TaskProcessDbObj revertProcess = new TaskProcessDbObj(taskId, preForwardProcess.getProcessSubject(), TaskConstant.TP_REVERT, null, null, null);
				nextTp = revertProcess;
			} else
			{
				// ��ǰ�û��Ѿ���һ��������
				canArchiveVerify = true;
			}
		}

		if (canArchiveVerify)
		{
			// ��һ���ڣ����빤�������˽��й鵵��ʵ
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
			return taskArchive(taskId, user, "�鵵���ͨ����" + archiveVerifytInformation);
		} else
		{
			return taskReject(taskId, user, TaskConstant.TP_ARCHIVE_VERIFY, archiveVerifytInformation);
		}
	}

	// ͨ�ô������
	public boolean commonProcess(long taskId, TaskProcessDbObj currentTp, TaskProcessDbObj nextTp, String taskStatus)
	{
		// 1�����¹�������Ϣ:��Ҫ�Ǹ��µ�ǰ�����ڡ������˵���Ϣ
		TaskMainInfoDbObj task4Update = new TaskMainInfoDbObj(taskId, currentTp, nextTp, taskStatus);
		boolean rst = taskMainInfoBaseDAO.update(task4Update);

		TaskMainInfoDbObj taskInDb = (TaskMainInfoDbObj) taskMainInfoBaseDAO.searchByKey(TaskMainInfoDbObj.class, taskId + "");

		// 2��������������Ϣ��⣺�������ڱ� task_process
		if (rst)
		{
			// ��ʱ
			long usedTime = getUsedTime(currentTp);
			currentTp.setProcessUsedTime(usedTime);
			// ��һ���ڴ������
			currentTp.setNextProcessSubject(nextTp.getProcessSubject());
			// ����
			long processId = taskProcessBaseDAO.getMaxKeyValue() + 1;
			currentTp.setProcessId(processId);
			rst = taskProcessBaseDAO.save(currentTp);

			// 3��ִ�иû��ڵĸ��Ӷ���
			try
			{
				List<TaskAddProcessDbObj> taskAddProcessList = taskAddProcessBaseDAO.searchByClause(TaskAddProcessDbObj.class, "taskType='" + taskInDb.getTaskType()
						+ "' and taskSubType='" + taskInDb.getTaskSubType() + "' and processName='" + currentTp.getProcessName() + "'", null, 0, 1);

				if (taskAddProcessList.size() > 0)
				{
					ITaskProcess addProcess = (ITaskProcess) Thread.currentThread().getContextClassLoader().loadClass(taskAddProcessList.get(0).getProcessClass()).newInstance();
					logger.info("ִ�и��Ӷ�����taskType=" + taskInDb.getTaskType() + ", taskSubType=" + taskInDb.getTaskSubType() + ", processName=" + currentTp.getProcessName()
							+ ", process=" + addProcess.getClass());
					addProcess.process(taskInDb.getTaskType(), taskInDb.getTaskSubType(), currentTp.getProcessName(), taskInDb);
				}
			} catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}

			// 4������һ���ڵĴ����˷��Ͷ��ţ�1����һ�������ύ���������ݸ�״̬�����س��⣩���鵵��ʱ���÷���2����ǰ�����˺���һ���ڴ�������ͬ���÷�
			sendSms(taskInDb, currentTp, nextTp);

		}
		return rst;
	}

	private void taskSubmit(long taskId, TaskMainInfoDbObj taskMainInfo)
	{
		// ��ǰ����Ϊ�����ύ����
		TaskProcessDbObj taskSubmitTp = new TaskProcessDbObj(taskId, taskMainInfo.getCreateSubject(), TaskConstant.TP_SUBMIT, null, "�ύ����", null);

		// ��һ�����ڸ�����������������ڻ��Ǵ�����
		TaskProcessDbObj nextTp = new TaskProcessDbObj();
		if (!StringUtil.isEmpty(taskMainInfo.getAuditSubjectId()))
		{
			// ��Ҫ�����Ĺ�����ת�������˽�������
			TaskProcessDbObj taskAuditTp = new TaskProcessDbObj(taskId, taskMainInfo.getAuditSubject(), TaskConstant.TP_AUDIT, null, null, null);

			nextTp = taskAuditTp;
		} else if (!StringUtil.isEmpty(taskMainInfo.getAcceptSubjectId()))
		{
			// ����Ҫ�����Ĺ�����ת�������˴���
			TaskProcessDbObj taskProcessTp = new TaskProcessDbObj(taskId, taskMainInfo.getAcceptSubject(), TaskConstant.TP_REVERT, null, null, null);

			nextTp = taskProcessTp;
		}
		this.commonProcess(taskId, taskSubmitTp, nextTp, TaskConstant.STATUS_NEED_AUDIT);
	}

	private boolean taskReject(long taskId, AuthUser user, String currentProcessName, String rejectInformation)
	{
		// ���أ���¼������Ϣ���������˵���һ����

		// ��ǰ���ڣ����ػ�����Ϣ
		TaskProcessDbObj rejectTp = new TaskProcessDbObj(taskId, user, currentProcessName, TaskConstant.RESULT_REJECT, rejectInformation, null);

		// ��һ���ڣ��������˵���һ�����˻���
		TaskMainInfoDbObj taskMainInfo = (TaskMainInfoDbObj) taskMainInfoBaseDAO.searchByKey(TaskMainInfoDbObj.class, taskId + "");
		TaskProcessDbObj nextTp = new TaskProcessDbObj(taskId, taskMainInfo.getPreviousProcessSubject(), taskMainInfo.getPreviousProcessName(), null, null, null);

		return commonProcess(taskId, rejectTp, nextTp, currentProcessName + TaskConstant.STATUS_REJECTED);
	}

	private boolean taskArchive(long taskId, AuthUser user, String archiveInfomation)
	{
		// �鵵����������

		// ��ǰ���ڣ��鵵��˻���
		TaskProcessDbObj archiveVerifyTp = new TaskProcessDbObj(taskId, user, TaskConstant.TP_ARCHIVE_VERIFY, TaskConstant.RESULT_AGREED, archiveInfomation, null);

		// �����鵵
		AuthUser systemAuthUser = new AuthUser();
		systemAuthUser.setAccount("SYSTEM");
		systemAuthUser.setName("ϵͳ");
		systemAuthUser.setDepartmentName("����ϵͳ");
		systemAuthUser.setDistrict("�й�˾");
		TaskProcessDbObj archiveTp = new TaskProcessDbObj(taskId, systemAuthUser, TaskConstant.TP_ARCHIVE, null, archiveInfomation, null);

		return commonProcess(taskId, archiveVerifyTp, archiveTp, TaskConstant.STATUS_ARCHIVED);
	}

	private void taskSaveDraft(long taskId, TaskMainInfoDbObj taskMainInfo)
	{
		// ����ݸ�

		// ��ǰ���ڣ�����ݸ�
		TaskProcessDbObj draftTp = new TaskProcessDbObj(taskId, taskMainInfo.getCreateSubject(), TaskConstant.TP_DRAFT, null, "����ݸ�", null);

		// һ�»��ڣ��ύ����
		TaskProcessDbObj submitTp = new TaskProcessDbObj(taskId, taskMainInfo.getCreateSubject(), TaskConstant.TP_SUBMIT, null, null, null);

		commonProcess(taskId, draftTp, submitTp, TaskConstant.STATUS_NEED_SUBMIT);
	}

	// ������
	private void sendSms(TaskMainInfoDbObj task, TaskProcessDbObj currentTp, TaskProcessDbObj nextTp)
	{
		if ((!TaskConstant.TP_SUBMIT.equals(nextTp.getProcessName())// ��һ���ڲ����ύ���������ݸ�״̬�����س��⣩
				&& !TaskConstant.TP_ARCHIVE.equals(nextTp.getProcessName())// ��һ���ڲ��ǹ鵵�����ѹ鵵״̬��
		&& !currentTp.getProcessSubjectId().equals(nextTp.getProcessSubjectId()))// ��ǰ�����˺���һ���ڴ����˲�ͬ
				|| (TaskConstant.TP_SUBMIT.equals(nextTp.getProcessName()) && TaskConstant.TP_AUDIT.equals(currentTp.getProcessName()))// �ݸ�״̬�����س���
		)
		{
			// ������
			List<AuthUser> nextUsers = new ArrayList<AuthUser>();
			if (TaskConstant.SUBJECT_TYPE_PERSON.equals(nextTp.getProcessSubjectType()))
			{
				// ����
				nextUsers = authUserBaseDAO.searchByClause(AuthUser.class, "account='" + nextTp.getProcessSubjectId() + "'", null, 0, 1);
			}
			if (TaskConstant.SUBJECT_TYPE_DEPARTMENT.equals(nextTp.getProcessSubjectType()))
			{
				// ����
				nextUsers = authUserBaseDAO.searchByClause(AuthUser.class, "departmentId='" + nextTp.getProcessSubjectId() + "'", null, 0, Integer.MAX_VALUE);
			}

			List<String> userPhones = new ArrayList<String>();
			for (int i = 0; i < nextUsers.size(); i++)
			{
				userPhones.add(nextUsers.get(i).getTelephone());
			}
			try
			{
				// xx�Ź�����Ҫ������xx���ɵ���xx���û�����xx���ɷ���xx����10088Эͬ����ϵͳ��
				String smsContent = task.getTaskSubType() + currentTp.getTaskId() + "�Ź�����Ҫ��"
						+ (TaskConstant.SUBJECT_TYPE_DEPARTMENT.equals(nextTp.getProcessSubjectType()) ? "����" : "") + "����" + nextTp.getProcessName() + "���ɵ���"
						+ task.getCreateUserDepartmentName() + task.getCreateUserName() + "��"//
						+ "��" + SystemConstant.SYSTEM_NAME + "��";
				messageSender.sendMessage(userPhones, smsContent);
			} catch (Exception e)
			{
				logger.error(e.getMessage(), e);
			}
		}
	}

	private long getUsedTime(TaskProcessDbObj currentTp)
	{
		// ��ȡ��һ�����ڵ���Ϣ������û���ʹ�õ�ʱ�䣬��λΪ����o
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

	// ��ȡ������ʱ�Ĺ��� ��ѯ����
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

	// ��ȡ��ʱ�Ĺ��� ��ѯ����
	private String getOverTimeClause()
	{
		// String where = " (currentProcessName!='" + TaskConstant.TP_ARCHIVE +
		// "' and date_add(ifnull(auditTime,now()),INTERVAL timeLimit
		// day)<=now()) ";// δ�鵵�ĳ�ʱ������ʱ��͵�ǰʱ��Ƚ�
		// where += " or (currentProcessName='" + TaskConstant.TP_ARCHIVE + "'
		// and date_add(auditTime,INTERVAL timeLimit day)<=revertTime)";//
		// �ѹ鵵�ĳ�ʱ������ʱ��͹����ظ�ʱ��Ƚ�

		String where = "";
		if (MySqlUtil.isMySql(taskMainInfoBaseDAO.getDbName()))
		{
			where = " (revertTime is null and date_add(ifnull(createTime,now()),INTERVAL timeLimit day)<=now()) ";// δ�ظ��ĳ�ʱ���ɵ�ʱ��͵�ǰʱ��Ƚ�
			where += " or (revertTime is not null and date_add(createTime,INTERVAL timeLimit day)<=revertTime)";// �ѻظ��ĳ�ʱ���ɵ�ʱ��͹����ظ�ʱ��Ƚ�

		} else if (MySqlUtil.isOracle(taskMainInfoBaseDAO.getDbName()))
		{
			where = " (revertTime is null and createTime<=(sysdate-timeLimit)) ";// δ�ظ��ĳ�ʱ���ɵ�ʱ��͵�ǰʱ��Ƚ�
			where += " or (revertTime is not null and createTime<=(revertTime-timeLimit))";// �ѻظ��ĳ�ʱ���ɵ�ʱ��͹����ظ�ʱ��Ƚ�

		}
		return " (" + where + ") ";
	}

	// ��ȡδ��ʱ�Ĺ��� ��ѯ����
	private String getNotOverTimeClause()
	{
		// String where = " (currentProcessName!='" + TaskConstant.TP_ARCHIVE +
		// "' and date_add(ifnull(auditTime,now()),INTERVAL timeLimit
		// day)>now()) ";// δ�鵵�ĳ�ʱ������ʱ��͵�ǰʱ��Ƚ�
		// where += " or (currentProcessName='" + TaskConstant.TP_ARCHIVE + "'
		// and date_add(auditTime,INTERVAL timeLimit day)>revertTime)";//
		// �ѹ鵵�ĳ�ʱ������ʱ��͹����ظ�ʱ��Ƚ�

		String where = "";
		if (MySqlUtil.isMySql(taskMainInfoBaseDAO.getDbName()))
		{
			where = " (revertTime is null and date_add(ifnull(createTime,now()),INTERVAL timeLimit day)>now()) ";// δ�ظ��ĳ�ʱ���ɵ�ʱ��͵�ǰʱ��Ƚ�
			where += " or (revertTime is not null and date_add(createTime,INTERVAL timeLimit day)>revertTime)";// �ѻظ��ĳ�ʱ���ɵ�ʱ��͹����ظ�ʱ��Ƚ�

		} else if (MySqlUtil.isOracle(taskMainInfoBaseDAO.getDbName()))
		{
			where = " (revertTime is null and (createTime+timeLimit)>sysdate) ";// δ�ظ��ĳ�ʱ���ɵ�ʱ��͵�ǰʱ��Ƚ�
			where += " or (revertTime is not null and (createTime+timeLimit)>revertTime)";// �ѻظ��ĳ�ʱ���ɵ�ʱ��͹����ظ�ʱ��Ƚ�

		}
		return " (" + where + ") ";
	}

	// ��ȡ������ص����й��� ��ѯ����
	private String getRelatedTaskClause(AuthUser user)
	{
		// �����¼���и��û��������Ļ���
		String where = "taskId in (select taskId from task_process where processSubjectId='" + user.getAccount() + "') ";
		where += " or ";
		// ���� ��Ҫ���û����������
		where += getRelatedNeedProcessTaskClause(user);

		return " (" + where + ") ";
	}

	// ��ȡ���������"�ѹ鵵"�Ĺ��� ��ѯ����
	private String getRelatedArchivedTaskClause(AuthUser user)
	{
		// �����¼���и��û��������Ļ��� �� ������ǰ������ �鵵
		String where = "taskId in (select taskId from task_process where processSubjectId='" + user.getAccount() + "') and currentProcessName='" + TaskConstant.TP_ARCHIVE + "'";
		return " (" + where + ") ";
	}

	// ��ȡ���������"�Ѵ���δ�鵵"�Ĺ��� ��ѯ����
	private String getRelatedProcessedTaskClause(AuthUser user)
	{
		// �����¼���и��û��������Ļ��� �� ������ǰ���ڲ��ǹ鵵 �� ��ǰ�����˲��Ǹ��û�
		String where = "taskId in (select taskId from task_process where processSubjectId='" + user.getAccount() + "') and currentProcessName!='" + TaskConstant.TP_ARCHIVE
				+ "' and currentProcessSubjectId!='" + user.getAccount() + "'";
		return " (" + where + ") ";
	}

	// ��ȡ���������"���������� ��ѯ����
	private String getRelatedNeedProcessTaskClause(AuthUser user)
	{
		// ��ǰ������Ϊ���û�
		String where = " (currentProcessSubjectType='" + TaskConstant.SUBJECT_TYPE_PERSON + "' and currentProcessSubjectId='" + user.getAccount() + "') ";
		// ���� ��ǰ������Ϊ���û����ڲ���
		where += "or (currentProcessSubjectType='" + TaskConstant.SUBJECT_TYPE_DEPARTMENT + "' and currentProcessSubjectId='" + user.getDepartmentId() + "') ";
		// ���� ��ǰ������Ϊ���û�������
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
		// add by wuyg 2013-10-25 ��ֹ�û��������ű仯�󿴲������칤��������ǰ������Ϊ���û�
		where += " or (currentProcessSubjectType='" + TaskConstant.SUBJECT_TYPE_PERSON + "' and currentProcessSubjectId='" + user.getAccount() + "') ";

		return " (" + where + ") ";
	}

	public boolean saveOrUpdateCustomizableColumns(TaskCustomizableColumns taskCustomizableColumns)
	{
		// ��ɾ��ԭ�е��ֶ�
		customizableColumnsDAO.deleteByClause(" taskType='" + taskCustomizableColumns.getTaskType() + "' and taskSubType='" + taskCustomizableColumns.getTaskSubType()
				+ "' and taskProcessName='" + taskCustomizableColumns.getTaskProcessName() + "'");

		// Ȼ�󱣴��µ��ֶ�
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
