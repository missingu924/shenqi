package com.hz.job;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.hz.util.SystemConstant;
import com.wuyg.common.message.IMessageSender;
import com.wuyg.common.message.SmsModemMessageSender;
import com.wuyg.common.util.MySqlUtil;
import com.wuyg.common.util.StringUtil;

public class TaskRemainJob implements Job
{

	private Logger logger = Logger.getLogger(getClass());

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		Connection conn = null;
		try
		{
			conn = MySqlUtil.getConnection(SystemConstant.DEFAULT_DB);

			StringBuffer s = new StringBuffer();
			s.append("select * from auth_user t0 left join\n");
			s.append("(                  \n");
			s.append("select currentprocesssubjectid,currentprocesssubjectname,count(*) overtimeCount\n");
			s.append("from task_main_info where \n");
			s.append("( \n");
			s.append("  (revertTime is null and createTime<=(sysdate-timeLimit))\n"); // δ�ظ��ĳ�ʱ���ɵ�ʱ��͵�ǰʱ��Ƚ�
			s.append("  or \n");
			s.append("  (revertTime is not null and createTime<=(revertTime-timeLimit)) \n");// �ѻظ��ĳ�ʱ���ɵ�ʱ��͹����ظ�ʱ��Ƚ�
			s.append(")\n");
			s.append("group by currentprocesssubjectid,currentprocesssubjectname\n");
			s.append(") t1\n");
			s.append("on t0.account=t1.currentprocesssubjectid\n");
			s.append("left join \n");
			s.append("(\n");
			s.append("select currentprocesssubjectid,currentprocesssubjectname,count(*) toBeOvertimeCount\n");
			s.append("from task_main_info where \n");
			s.append("( \n");
			s.append("   (createTime+timeLimit-3/24)<=sysdate--3Сʱ�ڽ���ʱ\n");
			s.append("    and (createTime+timeLimit)>sysdate \n");
			s.append("    and currentProcessName!='�鵵'\n");
			s.append(")\n");
			s.append("group by currentprocesssubjectid,currentprocesssubjectname\n");
			s.append(") t2\n");
			s.append("on t0.account=t2.currentprocesssubjectid\n");
			s.append("where overtimeCount>0 or toBeOverTimeCount>0");
			
			logger.info("��ʱ�����������ѣ�"+s.toString());

			ResultSet rst = conn.createStatement().executeQuery(s.toString());

			IMessageSender messageSender = new SmsModemMessageSender();
			while (rst.next())
			{
				String userName = rst.getString("name");
				String telephone = rst.getString("telephone");
				String message = userName+"���ã�����" + rst.getInt("overtimecount") + "�Ź����ѳ�ʱ��" + rst.getInt("tobeovertimecount") + "�Ź���3Сʱ�ڻᳬʱ���뾡�촦��";

				if (!StringUtil.isEmpty(telephone))
				{
					List<String> telephoneList = new ArrayList<String>();
					telephoneList.add(telephone);
					messageSender.sendMessage(telephoneList, message);
				}
			}

		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		} finally
		{
			MySqlUtil.closeConnection(conn);
		}

	}

}
