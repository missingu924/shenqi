package com.hz.teamworktask.addprocess;

import java.sql.Timestamp;

import com.hz.customer.obj.OtherCustomerDbObj;
import com.hz.customer.obj.ReservedTelNumDbObj;
import com.hz.customer.service.CustomerService;
import com.hz.customer.service.ICustomerService;
import com.wuyg.common.util.StringUtil;
import com.wuyg.common.util.TimeUtil;
import com.wuyg.task.TaskConstant;
import com.wuyg.task.obj.TaskMainInfoDbObj;
import com.wuyg.task.process.ITaskProcess;

public class OtherCustomerBackSubmitProcess implements ITaskProcess
{
	// 将预约号码保存入预约号码表中 reserved_telnum
	public boolean process(String taskType, String taskSubType, String processName, TaskMainInfoDbObj task) throws Exception
	{
		// 预约号码信息
		// 接续情况=正常接通;占位=;现用资费-市话额度（元）=100;现用资费-长途额度（元）=20;现用资费-漫游拨打（元）=30;现用资费-漫游接听（元）=40;现用资费-套餐（元）=50;现用资费-月消费金额（元）=60;客户通话需求=主要为市话;占位=;客户使用范围=本地;具体使用范围=牡丹区;推荐的资费套餐=全球通套餐;推荐的套餐档次=68;是否有意向=有意向;占位=;是否单位员工=否;单位名称=;预约号码=13905307157
		// | 五类号码 |
		// 非吉祥号;占位=;手机需求-品牌=苹果;手机需求-型号=iphone5;现有宽带-有无宽带=有;现有宽带-带宽=2M;现有宽带-到期时间=2013-10-12;占位=;有无意向办理移动宽带=有;意向宽带类型=WLAN;客户详细地址=济南市高新区康虹路
		String reserveTelNumInfo = StringUtil.substr(task.getCustColValues(), "预约号码=", ";");
		if (StringUtil.isEmpty(reserveTelNumInfo))
		{
			return true;
		}
		
		ICustomerService service = new CustomerService();

		// 构造预约号码信息
		ReservedTelNumDbObj reservedTelNum = new ReservedTelNumDbObj();
		reservedTelNum.setTaskId(task.getTaskId());

		// 他网客户号码信息
		// 区县=2,乡镇=cq11,主叫移动=13,接听移动=4,最早通话时间=20130801,最晚通话时间=20130829,号码等级=非吉祥号码
		String otherTelNumLevel = StringUtil.substr(task.getCustomerInfo() + ";", "号码等级=", ";");
		reservedTelNum.setOtherTelNum(task.getTelNum() + "");
		reservedTelNum.setOtherTelNumLevel(otherTelNumLevel);

		String[] ss = reserveTelNumInfo.split(" \\| ");
		if (ss.length >= 3)
		{
			reservedTelNum.setReserveTelNum(ss[0].trim());
			reservedTelNum.setReserveTelNumLevel(ss[1].trim());
			reservedTelNum.setReserveTelNumDesc(ss[2].trim());
		}

		reservedTelNum.setReserveTime(new Timestamp(System.currentTimeMillis()));
		reservedTelNum.setReserveOutTime(new Timestamp(TimeUtil.computeDate("3D").getTime()));// 3天后失效
		reservedTelNum.setStatus(TaskConstant.TEL_REVERSE_STATUS_REVERSING);// 预约中

		return service.reserveTelNum(reservedTelNum);
	}

}
