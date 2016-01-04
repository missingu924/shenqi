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
	// ��ԤԼ���뱣����ԤԼ������� reserved_telnum
	public boolean process(String taskType, String taskSubType, String processName, TaskMainInfoDbObj task) throws Exception
	{
		// ԤԼ������Ϣ
		// �������=������ͨ;ռλ=;�����ʷ�-�л���ȣ�Ԫ��=100;�����ʷ�-��;��ȣ�Ԫ��=20;�����ʷ�-���β���Ԫ��=30;�����ʷ�-���ν�����Ԫ��=40;�����ʷ�-�ײͣ�Ԫ��=50;�����ʷ�-�����ѽ�Ԫ��=60;�ͻ�ͨ������=��ҪΪ�л�;ռλ=;�ͻ�ʹ�÷�Χ=����;����ʹ�÷�Χ=ĵ����;�Ƽ����ʷ��ײ�=ȫ��ͨ�ײ�;�Ƽ����ײ͵���=68;�Ƿ�������=������;ռλ=;�Ƿ�λԱ��=��;��λ����=;ԤԼ����=13905307157
		// | ������� |
		// �Ǽ����;ռλ=;�ֻ�����-Ʒ��=ƻ��;�ֻ�����-�ͺ�=iphone5;���п��-���޿��=��;���п��-����=2M;���п��-����ʱ��=2013-10-12;ռλ=;������������ƶ����=��;����������=WLAN;�ͻ���ϸ��ַ=�����и���������·
		String reserveTelNumInfo = StringUtil.substr(task.getCustColValues(), "ԤԼ����=", ";");
		if (StringUtil.isEmpty(reserveTelNumInfo))
		{
			return true;
		}
		
		ICustomerService service = new CustomerService();

		// ����ԤԼ������Ϣ
		ReservedTelNumDbObj reservedTelNum = new ReservedTelNumDbObj();
		reservedTelNum.setTaskId(task.getTaskId());

		// �����ͻ�������Ϣ
		// ����=2,����=cq11,�����ƶ�=13,�����ƶ�=4,����ͨ��ʱ��=20130801,����ͨ��ʱ��=20130829,����ȼ�=�Ǽ������
		String otherTelNumLevel = StringUtil.substr(task.getCustomerInfo() + ";", "����ȼ�=", ";");
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
		reservedTelNum.setReserveOutTime(new Timestamp(TimeUtil.computeDate("3D").getTime()));// 3���ʧЧ
		reservedTelNum.setStatus(TaskConstant.TEL_REVERSE_STATUS_REVERSING);// ԤԼ��

		return service.reserveTelNum(reservedTelNum);
	}

}
