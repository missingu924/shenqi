package com.wuyg.task.service;

import java.sql.Timestamp;

import com.hz.auth.obj.AuthUser;
import com.hz.auth.service.AuthService;
import com.hz.auth.service.IAuthService;
import com.hz.teamworktask.obj.TeamworkTaskDbObj;
import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.task.TaskConstant;
import com.wuyg.task.obj.TaskMainInfoDbObj;
import com.wuyg.task.obj.TaskObj;
import com.wuyg.task.obj.TaskProcessSubject;

public class TaskServiceTest
{
	private static ITaskService service = new TaskService();

	public static void main(String[] args)
	{
		IAuthService authService = new AuthService();
		AuthUser _10088dianHuaJingLi = authService.getUserInfoByAccount("10088dhjl");
		AuthUser _10088Zhibanzhang = authService.getUserInfoByAccount("10088zbz");
		AuthUser yingYeTingBanZhang = authService.getUserInfoByAccount("yytbz");
		AuthUser YingYeYuan = authService.getUserInfoByAccount("wuxiuyun");
		TaskProcessSubject yingyeyuanSub = getYingyeyuan(YingYeYuan);

		// 创建
		for (int i = 0; i < 10000; i++)
		{
			long taskId = create(_10088dianHuaJingLi, _10088Zhibanzhang, yingYeTingBanZhang, i);

			// 审批-通过
			service.taskAudit(taskId, _10088Zhibanzhang, true, "ok。");
			// 审批-不通过
//			service.taskAudit(taskId, _10088Zhibanzhang, false, "ok。");
			// 阶段反馈-营业厅班长阶段反馈
			service.taskFeedback(taskId, yingYeTingBanZhang, "阶段处理，近期将安排处理。");
			// 转发-营业厅班长转发给营业员
			service.taskForward(taskId, yingYeTingBanZhang, yingyeyuanSub, "转发给营业员处理。");
			// 回复-营业员回复
//			service.taskRevert(taskId, YingYeYuan, "成功", "已联系客户，赠送手机。");
			// 审核不通过-电话经理审核不通过
			service.taskArchiveVerify(taskId, _10088dianHuaJingLi, false, "不同意，还需要继续赠话费。");
			// 回复-营业员回复
//			service.taskRevert(taskId, YingYeYuan, "成功", "已赠送1000元话费。");
			// 审核通过
			service.taskArchiveVerify(taskId, _10088dianHuaJingLi, true, "同意。");
		}

	}

	private static TaskProcessSubject getYingyeyuan(AuthUser user)
	{
		TaskProcessSubject yingyeyuan = new TaskProcessSubject();
		yingyeyuan.setSubjectType(TaskConstant.SUBJECT_TYPE_PERSON);
		yingyeyuan.setSubjectId(user.getAccount());
		yingyeyuan.setSubjectName(user.getName());
		yingyeyuan.setSubjectDepartmentId(user.getDepartmentId());
		yingyeyuan.setSubjectDepartmentName(user.getDepartmentName());
		yingyeyuan.setSubjectDistrict(user.getDistrict());
		return yingyeyuan;
	}

	private static AuthUser getProcessUser()
	{
		AuthUser user = new AuthUser();
		user.setAccount("yyy");
		user.setName("武玉刚");
		user.setDepartmentId("MUDANQU-YINGYETING");
		user.setDepartmentName("牡丹区公司营业厅");
		return user;
	}

	private static AuthUser getAcceptUser()
	{
		AuthUser user = new AuthUser();
		user.setAccount("yytbz");
		user.setName("武秀云");
		user.setDepartmentId("MUDANQU-YINGYETING");
		user.setDepartmentName("牡丹区公司营业厅");
		return user;
	}

	private static AuthUser getAuditUser()
	{
		AuthUser user = new AuthUser();
		user.setAccount("10088zbz");
		user.setName("武悦晗");
		user.setDepartmentId("10088-ZUOXI");
		user.setDepartmentName("10088坐席");
		return user;
	}

	private static AuthUser getCreateUser()
	{
		AuthUser user = new AuthUser();
		user.setAccount("10088dhjl");
		user.setName("高文婷");
		user.setDepartmentId("10088-ZUOXI");
		user.setDepartmentName("10088坐席");
		return user;
	}

	private static long create(AuthUser createUser, AuthUser auditUser, AuthUser acceptUser, long taskId)
	{
		// 建单
		TaskObj taskObj = new TaskObj();
		// 主数据
		TaskMainInfoDbObj mainInfo = new TaskMainInfoDbObj();
		taskObj.setTaskMainInfo(mainInfo);

		// 主数据-通用信息
		mainInfo.setTaskType("我网客户维系");
		mainInfo.setTaskSubType("终端活动");
		// mainInfo.setTitle("测试工单" + taskId);
		mainInfo.setCriticalLevel("紧急");
		mainInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		mainInfo.setTimeLimit(3);

		// 主数据-创建人信息
		mainInfo.setCreateUserAccount(createUser.getAccount());
		mainInfo.setCreateUserName(createUser.getName());
		mainInfo.setCreateUserDepartmentId(createUser.getDepartmentId());
		mainInfo.setCreateUserDepartmentName(createUser.getDepartmentName());
		mainInfo.setCreateUserDistrict(createUser.getDistrict());

		// 主数据-审批人信息
		mainInfo.setAuditSubjectType(TaskConstant.SUBJECT_TYPE_PERSON);
		mainInfo.setAuditSubjectId(auditUser.getAccount());
		mainInfo.setAuditSubjectName(auditUser.getName());
		mainInfo.setAuditSubjectDepartmentId(auditUser.getDepartmentId());
		mainInfo.setAuditSubjectDepartmentName(auditUser.getDepartmentName());
		mainInfo.setAuditSubjectDistrict(auditUser.getDistrict());

		// 主数据-受理人信息
		mainInfo.setAcceptSubjectType(TaskConstant.SUBJECT_TYPE_PERSON);
		mainInfo.setAcceptSubjectId(acceptUser.getAccount());
		mainInfo.setAcceptSubjectName(acceptUser.getName());
		mainInfo.setAcceptSubjectDepartmentId(acceptUser.getDepartmentId());
		mainInfo.setAcceptSubjectDepartmentName(acceptUser.getDepartmentName());
		mainInfo.setAcceptSubjectDistrict(acceptUser.getDistrict());

		// 业务数据
		TeamworkTaskDbObj teamworkTask = new TeamworkTaskDbObj();
		taskObj.setBusinessData(teamworkTask);

		teamworkTask.setTelNum(13906411551l);
		teamworkTask.setWorkDiscription("客户需要新手机，建议促销。");
		teamworkTask.setAttachment("附件-01.txt");

		return service.taskCreate(taskObj, true);
	}
}
