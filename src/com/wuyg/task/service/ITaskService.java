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
	 * 创建工单
	 * 
	 * @param taskObj
	 * @return 工单编号
	 */
	public long taskCreate(TaskObj taskObj, boolean submit);

	/**
	 * 工单编辑
	 * 
	 * @param taskObj
	 * @return
	 */
	public boolean taskModify(TaskObj taskObj, boolean submit);

	/**
	 * 工单审核
	 * 
	 * @param taskId
	 * @param user
	 * @param agreed
	 * @return
	 */
	public boolean taskAudit(long taskId, AuthUser user, boolean agreed, String auditInfomation);

	/**
	 * 工单转派
	 * 
	 * @param taskId
	 * @param user
	 * @param processSubject
	 * @param forwardInfomation
	 * @return
	 */
	public boolean taskForward(long taskId, AuthUser user, TaskProcessSubject processSubject, String forwardInfomation);

	/**
	 * 工单转派
	 * 
	 * @param taskId
	 * @param user
	 * @param feedbackInformation
	 * @return
	 */
	public boolean taskFeedback(long taskId, AuthUser user, String feedbackInformation);

	/**
	 * 工单回复
	 * 
	 * @param taskId
	 * @param user
	 * @param processResult
	 * @param feedbackInformation
	 * @return
	 */
	public boolean taskRevert(long taskId, AuthUser user, String processResult, String revertInformation,String attachment);

	/**
	 * 工单归档审核
	 * 
	 * @param taskId
	 * @param user
	 * @param feedbackInformation
	 * @return
	 */
	public boolean taskArchiveVerify(long taskId, AuthUser user, boolean agreed, String archiveVerifytInformation);

	// /**
	// * 驳回
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
	// * 归档
	// *
	// * @param taskId
	// * @param user
	// * @param archiveInfomation
	// * @return
	// */
	// public boolean taskArchive(long taskId, UserInfo user, String
	// archiveInfomation);

	/**
	 * 普通流转
	 * 
	 * @param taskId
	 * @param currentTp
	 * @param nextProcessDbObj
	 * @return
	 */
	public boolean commonProcess(long taskId, TaskProcessDbObj currentTp, TaskProcessDbObj nextTp, String taskStatus);

	/**
	 * 根据工单编号查询工单详情
	 * 
	 * @param taskId
	 * @param businessDataClz
	 *            业务数据的db对象
	 * @return
	 */
	public TaskObj getTaskById(long taskId, Class businessDataClz) throws Exception;

	/**
	 * 工单内容更新，仅用于草稿阶段的工单编辑，受理人一旦接单则不能再改变
	 * 
	 * @param taskObj
	 * @return
	 */
	public boolean updateTask(TaskObj taskObj);

	/**
	 * 查询工单基本信息，不含环节信息
	 * 
	 */
	public PaginationObj searchTaskPaginationByClause(TaskSearchComplexCondition condition);

	/**
	 * 保存或修改工单的定制化字段信息
	 * 
	 * @param taskType
	 * @param taskSubType
	 * @param customizablColumns
	 * @return
	 */
	public boolean saveOrUpdateCustomizableColumns(TaskCustomizableColumns taskCustomizableColumns);

	/**
	 * 根据工单大类工单小类查询定制化字段信息
	 * 
	 * @param taskType
	 * @param taskSubType
	 * @return
	 */
	public TaskCustomizableColumns getTaskCustomizableColumns(String taskType, String taskSubType, String taskProcessName);

	/**
	 * 根据工单大类工单小类删除定制化字段信息
	 * 
	 * @param taskType
	 * @param taskSubType
	 * @return
	 */
	public boolean deleteTaskCustomizableColumns(String taskType, String taskSubType);

	/**
	 * 查询工单定制化字段列表
	 * 
	 * @param user
	 * @param pageNo
	 * @param pageCount
	 * @return
	 */
	public List<TaskCustomizableColumns> searchTaskCustomizableColumns();
}
