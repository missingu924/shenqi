package com.wuyg.task.dao;

import com.hz.auth.obj.AuthUser;
import com.wuyg.common.dao.AbstractBaseDAOTemplate;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.common.util.MySqlUtil;
import com.wuyg.common.util.StringUtil;
import com.wuyg.common.util.TimeUtil;
import com.wuyg.task.obj.TaskStatDbObj;
import com.wuyg.task.obj.TaskStatSearchCondition;

public class TaskStatMySqlDAO extends AbstractBaseDAOTemplate
{
	private TaskStatSearchCondition condition;

	public PaginationObj searchPaginationByClause(TaskStatSearchCondition condition)
	{
		this.condition = condition;

		return searchPaginationByClause(TaskStatDbObj.class, "", "", condition.getPageNo(), condition.getPageCount());
	}

	@Override
	public String getSelectSql(String whereClause, String orderBy)
	{
		// 系统默认按照区县统计，如果当前用户只具备部门级权限，则自动设置为按部门统计
		AuthUser user = condition.getUser();
		if (user.isDepartmentUser())
		{
			condition.setGroupByDepartment(true);
		}

		// 时间条件
		StringBuffer timeClause = new StringBuffer();
		if (condition.getCreateStartTime() != null)
		{

			timeClause.append(" and createTime>='" + TimeUtil.date2str(condition.getCreateStartTime()) + "' \n");

		}
		if (condition.getCreateEndTime() != null)
		{

			timeClause.append(" and createTime<='" + TimeUtil.date2str(condition.getCreateEndTime()) + "' \n");

		}

		// 地市
		StringBuffer createUserDistrictClause = new StringBuffer();
		StringBuffer acceptDistrictClause = new StringBuffer();
		if (!StringUtil.isEmpty(condition.getDistrict()))
		{
			createUserDistrictClause.append(" and createUserDistrict='" + condition.getDistrict() + "' \n");
			acceptDistrictClause.append(" and acceptSubjectDistrict='" + condition.getDistrict() + "' \n");
		}

		StringBuffer s = new StringBuffer();
		s.append("select  \n");
		if (condition.isGroupByDistrict())
		{
			s.append("t99.name district, \n");
		} else
		{
			s.append("'' district, \n");
		}
		if (condition.isGroupByDepartment())
		{
			s.append("t98.departmentName departmentName, \n");
		} else
		{
			s.append("'' departmentName, \n");
		}
		if (condition.isGroupByTaskSubType())
		{
			s.append("t97.taskSubType taskSubType, \n");
		} else
		{
			s.append("'' taskSubType, \n");
		}
		String nvl = "nvl";
		if (MySqlUtil.isMySql(dbName))
		{
			nvl = "ifnull";
		}
		s.append("sum(" + nvl + "(t00.totalCount,0)) createTotalCount, \n");
		s.append("sum(" + nvl + "(t00.auditCount,0)) createNeedAuditCount, \n");
		s.append("sum(" + nvl + "(t00.revertCount,0)) createNeedRevertCount, \n");
		s.append("sum(" + nvl + "(t00.archiveVerifyCount,0)) createNeedArchiveCount, \n");
		s.append("sum(" + nvl + "(t00.archivedCount,0)) createArchivedCount, \n");
		s.append("sum(" + nvl + "(t00.processSucessCount,0)) createProcessSucessCount, \n");
		s.append("sum(" + nvl + "(t00.processFailedCount,0)) createProcessFailedCount, \n");
		s.append("sum(" + nvl + "(t11.taskCount,0)) createOverTimeCount, \n");
		s.append("sum(" + nvl + "(t22.totalCount,0)) acceptTotalCount, \n");
		s.append("sum(" + nvl + "(t22.auditCount,0)) acceptNeedAuditCount, \n");
		s.append("sum(" + nvl + "(t22.revertCount,0)) acceptNeedRevertCount, \n");
		s.append("sum(" + nvl + "(t22.archiveVerifyCount,0)) acceptNeedArchiveCount, \n");
		s.append("sum(" + nvl + "(t22.archivedCount,0)) acceptArchivedCount, \n");
		s.append("sum(" + nvl + "(t22.processSucessCount,0)) acceptProcessSucessCount, \n");
		s.append("sum(" + nvl + "(t22.processFailedCount,0)) acceptProcessFailedCount, \n");
		s.append("sum(" + nvl + "(t33.taskCount,0)) acceptOvertimeCount \n");
		s.append("from dict_district t99 \n");
		s.append("left join auth_department t98 on t99.name=t98.districtName \n");
		s.append("left join dict_task_subtype t97 on 1=1 \n");
		s.append("left join \n");
		s.append("( \n");
		// 派发工单基本情况
		s.append("	select t0.createUserDistrict createUserDistrict, t0.createUserDepartmentId createUserDepartmentId,t0.taskType,t0.taskSubType, \n");
		s.append("	sum(t0.taskCount) as totalCount, \n");
		s.append("	sum(case t0.currentProcessName when '派单审批' then t0.taskCount else 0 end) as auditCount, \n");
		s.append("	sum(case t0.currentProcessName when '工单回复' then t0.taskCount else 0 end) as revertCount, \n");
		s.append("	sum(case t0.currentProcessName when '归档审核' then t0.taskCount else 0 end) as archiveVerifyCount, \n");
		s.append("	sum(case t0.currentProcessName when '归档' then t0.taskCount else 0 end) as archivedCount, \n");
		s.append("	sum(case t0.processResult when '处理成功' then t0.taskCount else 0 end) as processSucessCount, \n");
		s.append("	sum(case t0.processResult when '处理失败' then t0.taskCount else 0 end) as processFailedCount \n");
		s.append("	from( \n");
		s.append("		select createUserDistrict,createUserDepartmentId,taskType,taskSubType,currentProcessName,processResult,count(*) taskCount  \n");
		s.append("		from task_main_info where 1=1 ").append(timeClause).append(createUserDistrictClause);
		s.append("		group by createUserDistrict,createUserDepartmentId,taskType,taskSubType,currentProcessName,processResult \n");
		s.append("	) t0 group by t0.createUserDistrict,t0.createUserDepartmentId,t0.taskType,t0.taskSubType \n");
		s
				.append(") t00 on t00.createUserDistrict=t99.name and t00.createUserDepartmentId=t98.departmentId and t00.taskType=t97.taskType and t00.taskSubType=t97.taskSubType \n");
		s.append("left join  \n");
		// 派发工单超时情况
		s.append("( \n");
		s.append("	select createUserDistrict,createUserDepartmentId,taskType,taskSubType,count(*) taskCount   \n");
		s.append("	from task_main_info  \n");
		s.append("	where  \n");
		s.append("	( \n");
		if (MySqlUtil.isMySql(dbName))
		{
			s.append("		(revertTime is null and date_add(ifnull(createTime,now()),INTERVAL timeLimit day)<=now())   \n");
			s.append("	 or (revertTime is not null and date_add(createTime,INTERVAL timeLimit day)<=revertTime) \n");
		} else if (MySqlUtil.isOracle(dbName))
		{
			s.append("		(revertTime is null and (createTime+timeLimit)<=sysdate)   \n");
			s.append("	 or (revertTime is not null and (createTime+timeLimit)<=revertTime) \n");
		}
		s.append("	) ").append(timeClause).append(createUserDistrictClause);
		s.append("	group by createUserDistrict,createUserDepartmentId,taskType,TaskSubType \n");
		s
				.append(") t11 on t11.createUserDistrict=t99.name and t11.createUserDepartmentId=t98.departmentId and t11.taskType=t97.taskType and t11.taskSubType=t97.taskSubType \n");
		s.append("left join \n");
		// 受理工单基本情况
		s.append("( \n");
		s.append("	select t2.acceptSubjectDistrict, t2.acceptSubjectDepartmentId,t2.taskType,t2.taskSubType, \n");
		s.append("	sum(t2.taskCount) as totalCount, \n");
		s.append("	sum(case t2.currentProcessName when '派单审批' then t2.taskCount else 0 end) as auditCount, \n");
		s.append("	sum(case t2.currentProcessName when '工单回复' then t2.taskCount else 0 end) as revertCount, \n");
		s.append("	sum(case t2.currentProcessName when '归档审核' then t2.taskCount else 0 end) as archiveVerifyCount, \n");
		s.append("	sum(case t2.currentProcessName when '归档' then t2.taskCount else 0 end) as archivedCount, \n");
		s.append("	sum(case t2.processResult when '处理成功' then t2.taskCount else 0 end) as processSucessCount, \n");
		s.append("	sum(case t2.processResult when '处理失败' then t2.taskCount else 0 end) as processFailedCount \n");
		s.append("	from( \n");
		s.append("		select acceptSubjectDistrict,acceptSubjectDepartmentId,taskType,taskSubType,currentProcessName,processResult,count(*) taskCount  \n");
		s.append("		from task_main_info where 1=1 ").append(timeClause).append(acceptDistrictClause);
		s.append("		group by acceptSubjectDistrict,acceptSubjectDepartmentId,taskType,taskSubType,currentProcessName,processResult \n");
		s.append("	) t2 group by t2.acceptSubjectDistrict,t2.acceptSubjectDepartmentId,t2.taskType,t2.taskSubType \n");
		s
				.append(") t22 on t22.acceptSubjectDistrict=t99.name and t22.acceptSubjectDepartmentId=t98.departmentId and t22.taskType=t97.taskType and t22.taskSubType=t97.taskSubType \n");
		s.append("left join \n");
		// 受理工单超时情况
		s.append("( \n");
		s.append("	select acceptSubjectDistrict,acceptSubjectDepartmentId,taskType,taskSubType,count(*) taskCount   \n");
		s.append("	from task_main_info  \n");
		s.append("	where  \n");
		s.append("	( \n");
		if (MySqlUtil.isMySql(dbName))
		{
			s.append("		(revertTime is null and date_add(ifnull(createTime,now()),INTERVAL timeLimit day)<=now())   \n");
			s.append("	 or (revertTime is not null and date_add(createTime,INTERVAL timeLimit day)<=revertTime) \n");
		} else if (MySqlUtil.isOracle(dbName))
		{
			s.append("		(revertTime is null and (createTime+timeLimit)<=sysdate)   \n");
			s.append("	 or (revertTime is not null and (createTime+timeLimit)<=revertTime) \n");
		}
		s.append("	) ").append(timeClause).append(acceptDistrictClause);
		s.append("	group by acceptSubjectDistrict,acceptSubjectDepartmentId,taskType,taskSubType \n");
		s
				.append(") t33 on t33.acceptSubjectDistrict=t99.name and t33.acceptSubjectDepartmentId=t98.departmentId and t33.taskType=t97.taskType and t33.taskSubType=t97.taskSubType \n");
		// where
		s.append(" where 1=1 ");
		if (!StringUtil.isEmpty(condition.getDistrict()))
		{
			s.append(" and t99.name='" + condition.getDistrict() + "' \n");
		}
		// 权限限定
		if (user.isDistrictUser())
		{
			s.append(" and t99.name='" + user.getDistrict() + "' \n");
		}
		if (user.isDepartmentUser())
		{
			s.append(" and t98.departmentId='" + user.getDepartmentId() + "' \n");
		}
		// group by
		String groupByClause = "";
		if (condition.isGroupByDistrict())
		{
			groupByClause = "t99.id, t99.name ";
		}
		if (condition.isGroupByDepartment())
		{
			groupByClause += (StringUtil.isEmpty(groupByClause) ? "" : ",") + "t98.departmentName ";
		}
		if (condition.isGroupByTaskSubType())
		{
			groupByClause += (StringUtil.isEmpty(groupByClause) ? "" : ",") + "t97.taskType,t97.taskSubType ";
		}
		if (!StringUtil.isEmpty(groupByClause))
		{
			s.append("group by " + groupByClause);
		}

		// order by
		if (!StringUtil.isEmpty(groupByClause))
		{
			s.append("order by " + groupByClause);
		}

		return s.toString();
	}

	@Override
	public String getKeyColumnName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParentKeyColumnName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTalbName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public PaginationObj searchPaginationByCondition(Object condition)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
