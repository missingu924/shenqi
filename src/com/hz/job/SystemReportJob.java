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

public class SystemReportJob implements Job
{

	private Logger logger = Logger.getLogger(getClass());

	public void execute(JobExecutionContext arg0) throws JobExecutionException
	{
		Connection conn = null;
		try
		{
			conn = MySqlUtil.getConnection(SystemConstant.DEFAULT_DB);

			StringBuffer s = new StringBuffer();
			s.append("select * from\n");
			s.append("(select count(*) taskTotalCount from task_main_info) t0\n");
			s.append("left join \n");
			s.append("(select count(*) taskCountYestoday from task_main_info where createtime>=trunc(sysdate-1) and createtime<=trunc(sysdate)) t1\n");
			s.append("on 1=1\n");
			s.append("left join\n");
			s.append("(select count(*) loginTotalCount from log_login) t2\n");
			s.append("on 1=1\n");
			s.append("left join\n");
			s.append("(select count(*) longinCountYestoday from log_login where timestamp>=trunc(sysdate-1) and timestamp<=trunc(sysdate)) t2\n");
			s.append("on 1=1");

			logger.info("系统使用情况统计短信：" + s.toString());

			ResultSet rst = conn.createStatement().executeQuery(s.toString());

			IMessageSender messageSender = new SmsModemMessageSender();
			while (rst.next())
			{
				String telephone = "13906411551";
				String message = "昨日新增工单" + rst.getInt("taskCountYestoday") + "张，累计" + rst.getInt("taskTotalCount") + "张；昨日登录"
						+ rst.getInt("longinCountYestoday") + "次，累计" + rst.getInt("loginTotalCount") + "次。";

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
