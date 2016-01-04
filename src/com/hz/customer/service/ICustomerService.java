package com.hz.customer.service;

import java.sql.Timestamp;
import java.util.List;

import com.hz.customer.obj.MyCustomerDbObj;
import com.hz.customer.obj.OtherCustomerDbObj;
import com.hz.customer.obj.ReservedTelNumDbObj;
import com.hz.customer.obj.UsableTelNumDbObj;
import com.inspur.common.tree.Node;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.task.obj.TaskMainInfoDbObj;

public interface ICustomerService
{
	/**
	 * �����û������ȡ�����ͻ���Ϣ
	 * 
	 * @param telNum
	 * @return
	 * @throws Exception
	 */
	public MyCustomerDbObj getMyCustomerInfo(String telNum) throws Exception;

	/**
	 * �����û������ȡ�����ͻ���Ϣ
	 * 
	 * @param telNum
	 * @return
	 * @throws Exception
	 */
	public OtherCustomerDbObj getOtherCustomerInfo(String telNum) throws Exception;

	/**
	 * ���ݶ��ֺ����ȡ��ԤԼ�ĺ��룬��������nλ��9��8��7��6��5��4����ͬ�ĺ���+������루����ŵȼ�<=��������ȼ�+1����+�����20���Ǽ����
	 * 
	 * @param telNum
	 * @return
	 * @throws Exception
	 */
	public List<UsableTelNumDbObj> getUsableTelNum(String telNum) throws Exception;

	/**
	 * ԤԼ����
	 * 
	 * @param telNum
	 * @return
	 * @throws Exception
	 */
	public boolean reserveTelNum(ReservedTelNumDbObj reservedTelNum) throws Exception;

	/**
	 * �ͷ�ԤԼ��ʱ�ĺ��롣ɾ��ԤԼ������г���������ĺ���
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean releaseTimeOutReservedTelNum() throws Exception;

	/**
	 * ��ѯ��Ҫ��BOSS������ԤԼ���롣"ԤԼδ����" �� "������ǰ����λ��һ�����岿��" �� "���ú������Ҳ����" �� "���ú�����й�����λ==SD.LJ"
	 * 
	 * @return
	 * @throws Exception
	 */
	public PaginationObj searchReservedTelNums4Boss(String reserveTelNum, String otherTelNum, int pageNo, int pageCount) throws Exception;

	/**
	 * ��ѯ���ͷŵ�ԤԼ��ʱ���룬�Ż�BOSS�Ŀ��ú����С�"ԤԼ�ѹ���" �� "���ú��������Ȼ����" �� "���ú�����й�����λ!=SD.LJ" 
	 * 
	 * @return
	 * @throws Exception
	 */
	public PaginationObj searchReleasedTelNums4Boss(String reserveTelNum, String otherTelNum, int pageNo, int pageCount) throws Exception;

	/**
	 * ��ѯ���ͷŵ�ԤԼ��ʱ���룬�Ż�BOSS�Ŀ��ú����С�ԤԼ��������ѹ��� �� ���ú������
	 * 
	 * @return
	 * @throws Exception
	 */
	public PaginationObj searchReservedTelNumPagintion(String startTime, String endTime, String reserveTelNum, String otherTelNum, String taskId, int pageNo,
			int pageCount) throws Exception;

	/**
	 * ��ѯ���ú�����е�ǰ������
	 * 
	 * @return
	 * @throws Exception
	 */
	public PaginationObj searchAllUsableTelNumPagintion(boolean exceptReversedTelNum, String telNum, int pageNo, int pageCount) throws Exception;
}
