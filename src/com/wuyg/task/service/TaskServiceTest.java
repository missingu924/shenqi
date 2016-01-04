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

		// ����
		for (int i = 0; i < 10000; i++)
		{
			long taskId = create(_10088dianHuaJingLi, _10088Zhibanzhang, yingYeTingBanZhang, i);

			// ����-ͨ��
			service.taskAudit(taskId, _10088Zhibanzhang, true, "ok��");
			// ����-��ͨ��
//			service.taskAudit(taskId, _10088Zhibanzhang, false, "ok��");
			// �׶η���-Ӫҵ���೤�׶η���
			service.taskFeedback(taskId, yingYeTingBanZhang, "�׶δ������ڽ����Ŵ���");
			// ת��-Ӫҵ���೤ת����ӪҵԱ
			service.taskForward(taskId, yingYeTingBanZhang, yingyeyuanSub, "ת����ӪҵԱ����");
			// �ظ�-ӪҵԱ�ظ�
//			service.taskRevert(taskId, YingYeYuan, "�ɹ�", "����ϵ�ͻ��������ֻ���");
			// ��˲�ͨ��-�绰������˲�ͨ��
			service.taskArchiveVerify(taskId, _10088dianHuaJingLi, false, "��ͬ�⣬����Ҫ���������ѡ�");
			// �ظ�-ӪҵԱ�ظ�
//			service.taskRevert(taskId, YingYeYuan, "�ɹ�", "������1000Ԫ���ѡ�");
			// ���ͨ��
			service.taskArchiveVerify(taskId, _10088dianHuaJingLi, true, "ͬ�⡣");
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
		user.setName("�����");
		user.setDepartmentId("MUDANQU-YINGYETING");
		user.setDepartmentName("ĵ������˾Ӫҵ��");
		return user;
	}

	private static AuthUser getAcceptUser()
	{
		AuthUser user = new AuthUser();
		user.setAccount("yytbz");
		user.setName("������");
		user.setDepartmentId("MUDANQU-YINGYETING");
		user.setDepartmentName("ĵ������˾Ӫҵ��");
		return user;
	}

	private static AuthUser getAuditUser()
	{
		AuthUser user = new AuthUser();
		user.setAccount("10088zbz");
		user.setName("������");
		user.setDepartmentId("10088-ZUOXI");
		user.setDepartmentName("10088��ϯ");
		return user;
	}

	private static AuthUser getCreateUser()
	{
		AuthUser user = new AuthUser();
		user.setAccount("10088dhjl");
		user.setName("������");
		user.setDepartmentId("10088-ZUOXI");
		user.setDepartmentName("10088��ϯ");
		return user;
	}

	private static long create(AuthUser createUser, AuthUser auditUser, AuthUser acceptUser, long taskId)
	{
		// ����
		TaskObj taskObj = new TaskObj();
		// ������
		TaskMainInfoDbObj mainInfo = new TaskMainInfoDbObj();
		taskObj.setTaskMainInfo(mainInfo);

		// ������-ͨ����Ϣ
		mainInfo.setTaskType("�����ͻ�άϵ");
		mainInfo.setTaskSubType("�ն˻");
		// mainInfo.setTitle("���Թ���" + taskId);
		mainInfo.setCriticalLevel("����");
		mainInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		mainInfo.setTimeLimit(3);

		// ������-��������Ϣ
		mainInfo.setCreateUserAccount(createUser.getAccount());
		mainInfo.setCreateUserName(createUser.getName());
		mainInfo.setCreateUserDepartmentId(createUser.getDepartmentId());
		mainInfo.setCreateUserDepartmentName(createUser.getDepartmentName());
		mainInfo.setCreateUserDistrict(createUser.getDistrict());

		// ������-��������Ϣ
		mainInfo.setAuditSubjectType(TaskConstant.SUBJECT_TYPE_PERSON);
		mainInfo.setAuditSubjectId(auditUser.getAccount());
		mainInfo.setAuditSubjectName(auditUser.getName());
		mainInfo.setAuditSubjectDepartmentId(auditUser.getDepartmentId());
		mainInfo.setAuditSubjectDepartmentName(auditUser.getDepartmentName());
		mainInfo.setAuditSubjectDistrict(auditUser.getDistrict());

		// ������-��������Ϣ
		mainInfo.setAcceptSubjectType(TaskConstant.SUBJECT_TYPE_PERSON);
		mainInfo.setAcceptSubjectId(acceptUser.getAccount());
		mainInfo.setAcceptSubjectName(acceptUser.getName());
		mainInfo.setAcceptSubjectDepartmentId(acceptUser.getDepartmentId());
		mainInfo.setAcceptSubjectDepartmentName(acceptUser.getDepartmentName());
		mainInfo.setAcceptSubjectDistrict(acceptUser.getDistrict());

		// ҵ������
		TeamworkTaskDbObj teamworkTask = new TeamworkTaskDbObj();
		taskObj.setBusinessData(teamworkTask);

		teamworkTask.setTelNum(13906411551l);
		teamworkTask.setWorkDiscription("�ͻ���Ҫ���ֻ������������");
		teamworkTask.setAttachment("����-01.txt");

		return service.taskCreate(taskObj, true);
	}
}
