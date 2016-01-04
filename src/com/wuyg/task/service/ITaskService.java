package com.wuyg.task.service;

import java.sql.Timestamp;
import java.util.List;

import com.hz.auth.obj.AuthUser;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.task.obj.TaskCustomizableColumnDbObj;
import com.wuyg.task.obj.TaskCustomizableColumns;
import com.wuyg.task.obj.TaskObj;
import com.wuyg.task.obj.TaskProcessDbObj;
import com.wuyg.task.obj.TaskProcessSubject;
import com.wuyg.task.obj.TaskSearchComplexCondition;

public interface ITaskService
{
	/**
	 * ��������
	 * 
	 * @param taskObj
	 * @return �������
	 */
	public long taskCreate(TaskObj taskObj, boolean submit);

	/**
	 * �����༭
	 * 
	 * @param taskObj
	 * @return
	 */
	public boolean taskModify(TaskObj taskObj, boolean submit);

	/**
	 * �������
	 * 
	 * @param taskId
	 * @param user
	 * @param agreed
	 * @return
	 */
	public boolean taskAudit(long taskId, AuthUser user, boolean agreed, String auditInfomation);

	/**
	 * ����ת��
	 * 
	 * @param taskId
	 * @param user
	 * @param processSubject
	 * @param forwardInfomation
	 * @return
	 */
	public boolean taskForward(long taskId, AuthUser user, TaskProcessSubject processSubject, String forwardInfomation);

	/**
	 * ����ת��
	 * 
	 * @param taskId
	 * @param user
	 * @param feedbackInformation
	 * @return
	 */
	public boolean taskFeedback(long taskId, AuthUser user, String feedbackInformation);

	/**
	 * �����ظ�
	 * 
	 * @param taskId
	 * @param user
	 * @param processResult
	 * @param feedbackInformation
	 * @return
	 */
	public boolean taskRevert(long taskId, AuthUser user, String processResult, String revertInformation,String attachment);

	/**
	 * �����鵵���
	 * 
	 * @param taskId
	 * @param user
	 * @param feedbackInformation
	 * @return
	 */
	public boolean taskArchiveVerify(long taskId, AuthUser user, boolean agreed, String archiveVerifytInformation);

	// /**
	// * ����
	// *
	// * @param taskId
	// * @param user
	// * @param rejectInformation
	// * @return
	// */
	// public boolean taskReject(long taskId, UserInfo user, String taskReject,
	// String rejectInformation);
	//
	// /**
	// * �鵵
	// *
	// * @param taskId
	// * @param user
	// * @param archiveInfomation
	// * @return
	// */
	// public boolean taskArchive(long taskId, UserInfo user, String
	// archiveInfomation);

	/**
	 * ��ͨ��ת
	 * 
	 * @param taskId
	 * @param currentTp
	 * @param nextProcessDbObj
	 * @return
	 */
	public boolean commonProcess(long taskId, TaskProcessDbObj currentTp, TaskProcessDbObj nextTp, String taskStatus);

	/**
	 * ���ݹ�����Ų�ѯ��������
	 * 
	 * @param taskId
	 * @param businessDataClz
	 *            ҵ�����ݵ�db����
	 * @return
	 */
	public TaskObj getTaskById(long taskId, Class businessDataClz) throws Exception;

	/**
	 * �������ݸ��£������ڲݸ�׶εĹ����༭��������һ���ӵ������ٸı�
	 * 
	 * @param taskObj
	 * @return
	 */
	public boolean updateTask(TaskObj taskObj);

	/**
	 * ��ѯ����������Ϣ������������Ϣ
	 * 
	 */
	public PaginationObj searchTaskPaginationByClause(TaskSearchComplexCondition condition);

	/**
	 * ������޸Ĺ����Ķ��ƻ��ֶ���Ϣ
	 * 
	 * @param taskType
	 * @param taskSubType
	 * @param customizablColumns
	 * @return
	 */
	public boolean saveOrUpdateCustomizableColumns(TaskCustomizableColumns taskCustomizableColumns);

	/**
	 * ���ݹ������๤��С���ѯ���ƻ��ֶ���Ϣ
	 * 
	 * @param taskType
	 * @param taskSubType
	 * @return
	 */
	public TaskCustomizableColumns getTaskCustomizableColumns(String taskType, String taskSubType, String taskProcessName);

	/**
	 * ���ݹ������๤��С��ɾ�����ƻ��ֶ���Ϣ
	 * 
	 * @param taskType
	 * @param taskSubType
	 * @return
	 */
	public boolean deleteTaskCustomizableColumns(String taskType, String taskSubType);

	/**
	 * ��ѯ�������ƻ��ֶ��б�
	 * 
	 * @param user
	 * @param pageNo
	 * @param pageCount
	 * @return
	 */
	public List<TaskCustomizableColumns> searchTaskCustomizableColumns();
}
