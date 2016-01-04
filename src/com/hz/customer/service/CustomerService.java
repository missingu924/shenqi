package com.hz.customer.service;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import com.hz.customer.obj.MyCustomerDbObj;
import com.hz.customer.obj.OtherCustomerDbObj;
import com.hz.customer.obj.ReservedTelNumDbObj;
import com.hz.customer.obj.ReservedTelNumDbObjExtend;
import com.hz.customer.obj.UsableTelNumDbObj;
import com.inspur.common.tree.Node;
import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.common.util.MySqlUtil;
import com.wuyg.common.util.StringUtil;
import com.wuyg.common.util.TimeUtil;
import com.wuyg.task.TaskConstant;
import com.wuyg.task.obj.TaskMainInfoDbObj;

public class CustomerService implements ICustomerService
{
	private Logger logger = Logger.getLogger(getClass());
	private IBaseDAO reservedTelBaseDAO = new DefaultBaseDAO(new ReservedTelNumDbObj());
	private IBaseDAO reservedTelExtendBaseDAO = new DefaultBaseDAO(new ReservedTelNumDbObjExtend());
	private IBaseDAO usableTelBaseDAO = new DefaultBaseDAO(new UsableTelNumDbObj());
	private IBaseDAO otherCustomerBaseDAO = new DefaultBaseDAO(new OtherCustomerDbObj());

	public MyCustomerDbObj getMyCustomerInfo(String telNum) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	public OtherCustomerDbObj getOtherCustomerInfo(String telNum) throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List<UsableTelNumDbObj> getUsableTelNum(String telNum) throws Exception
	{
		// 1����ѯ�����ͻ�����ĵȼ��Ȼ�����Ϣ
		String otherTelNumLevel = TaskConstant.TEL_LEVEL_NONE;
		Object otherCustomerObject = otherCustomerBaseDAO.searchByKey(OtherCustomerDbObj.class, telNum);
		if (otherCustomerObject != null)
		{
			OtherCustomerDbObj otherTel = (OtherCustomerDbObj) otherCustomerObject;
			otherTelNumLevel = otherTel.getHaoMaDengJi();
		}
		// 2����ѯ�������ú����к�9��8��7��6��5��4λ��ͬ�ĺ���
		// 3����ѯ�������ú����еȼ�>=��������ȼ�+1�ĺ���
		// 4�������ѯ20������

		String notReservedTelNumClause = " telNum not in (select reserveTelNum from telnum_reserved where reserveOutTime>sysdate)";

		StringBuffer s = new StringBuffer();
		s.append(" select telnum,belongedunit,telnumlevel,telnumdesc,timeStamp \n");
		s.append(" from \n");
		s.append(" ( \n");

		s
				.append(" select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'��9λ��ͬ' telNumDesc,'01' orderByValue from telnum_usable t where belongedunit='SD.LJ' and telnum like '%"
						+ telNum.substring(2, 11) + "' and " + notReservedTelNumClause + " \n");
		s.append(" union \n");
		s
				.append(" select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'��8λ��ͬ' telNumDesc,'02' orderByValue  from telnum_usable t where belongedunit='SD.LJ' and telnum like '%"
						+ telNum.substring(3, 11) + "' and telnum not like '%" + telNum.substring(2, 11) + "'  and " + notReservedTelNumClause + " \n");
		s.append(" union \n");
		s
				.append(" select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'��7λ��ͬ' telNumDesc,'03' orderByValue  from telnum_usable t where belongedunit='SD.LJ' and telnum like '%"
						+ telNum.substring(4, 11) + "' and telnum not like '%" + telNum.substring(3, 11) + "'  and " + notReservedTelNumClause + " \n");
		s.append(" union \n");
		s
				.append(" select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'��6λ��ͬ' telNumDesc,'04' orderByValue  from telnum_usable t where belongedunit='SD.LJ' and telnum like '%"
						+ telNum.substring(5, 11) + "' and telnum not like '%" + telNum.substring(4, 11) + "'  and " + notReservedTelNumClause + " \n");
		s.append(" union \n");
		s
				.append(" select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'��5λ��ͬ' telNumDesc,'05' orderByValue  from telnum_usable t where belongedunit='SD.LJ' and telnum like '%"
						+ telNum.substring(6, 11) + "' and telnum not like '%" + telNum.substring(5, 11) + "'  and " + notReservedTelNumClause + " \n");
		s.append(" union \n");
		s
				.append(" select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'��4λ��ͬ' telNumDesc,'06' orderByValue  from telnum_usable t where belongedunit='SD.LJ' and telnum like '%"
						+ telNum.substring(7, 11) + "' and telnum not like '%" + telNum.substring(6, 11) + "'  and " + notReservedTelNumClause + " \n");

		if (TaskConstant.TEL_LEVEL_1.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_2.equals(otherTelNumLevel))
		{
			s.append(" union \n");
			s
					.append("  select * from (select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'β�Ų�ͬ' telNumDesc,'07' orderByValue  from telnum_usable t where telNumLevel='һ�����' and belongedunit='SD.LJ'  and "
							+ notReservedTelNumClause + "  order by dbms_random.value()) where rownum<=10 \n");

		}
		if (TaskConstant.TEL_LEVEL_1.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_2.equals(otherTelNumLevel)
				|| TaskConstant.TEL_LEVEL_3.equals(otherTelNumLevel))
		{
			s.append(" union \n");
			s
					.append(" select * from (select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'β�Ų�ͬ' telNumDesc,'08' orderByValue  from telnum_usable t where telNumLevel='�������' and belongedunit='SD.LJ' and "
							+ notReservedTelNumClause + " order by dbms_random.value()) where rownum<=10 \n");
		}
		if (TaskConstant.TEL_LEVEL_1.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_2.equals(otherTelNumLevel)
				|| TaskConstant.TEL_LEVEL_3.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_4.equals(otherTelNumLevel))
		{
			s.append(" union \n");
			s
					.append("  select * from (select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'β�Ų�ͬ' telNumDesc,'09' orderByValue  from telnum_usable t where telNumLevel='�������' and belongedunit='SD.LJ' and "
							+ notReservedTelNumClause + " order by dbms_random.value()) where rownum<=10 \n");
		}
		if (TaskConstant.TEL_LEVEL_1.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_2.equals(otherTelNumLevel)
				|| TaskConstant.TEL_LEVEL_3.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_4.equals(otherTelNumLevel)
				|| TaskConstant.TEL_LEVEL_5.equals(otherTelNumLevel))
		{
			s.append(" union \n");
			s
					.append("  select * from (select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'β�Ų�ͬ' telNumDesc,'10' orderByValue  from telnum_usable t where telNumLevel='�������' and belongedunit='SD.LJ' and "
							+ notReservedTelNumClause + " order by dbms_random.value()) where rownum<=10 \n");
		}
		if (TaskConstant.TEL_LEVEL_1.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_2.equals(otherTelNumLevel)
				|| TaskConstant.TEL_LEVEL_3.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_4.equals(otherTelNumLevel)
				|| TaskConstant.TEL_LEVEL_5.equals(otherTelNumLevel))
		{
			s.append(" union \n");
			s
					.append("  select * from (select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'β�Ų�ͬ' telNumDesc,'11' orderByValue  from telnum_usable t where telNumLevel='�������' and belongedunit='SD.LJ' and "
							+ notReservedTelNumClause + " order by dbms_random.value()) where rownum<=10 \n");
		}
		s.append(" union \n");
		s
				.append("  select * from (select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'β�Ų�ͬ' telNumDesc,'12' orderByValue  from telnum_usable t where telNumLevel='�Ǽ������' and belongedunit='SD.LJ' and "
						+ notReservedTelNumClause + " order by dbms_random.value()) where rownum<=10 \n");
		s.append(" ) \n");
		s.append(" order by orderbyvalue,telNum \n");

		// 5�����췵�ؽ��

		List<UsableTelNumDbObj> usableTelNums = usableTelBaseDAO.searchBySql(UsableTelNumDbObj.class, s.toString());

		if (usableTelNums.size() >= 20)
		{
			usableTelNums = usableTelNums.subList(0, 20);
		}

		return usableTelNums;
	}

	public boolean releaseTimeOutReservedTelNum() throws Exception
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean reserveTelNum(ReservedTelNumDbObj reservedTelNum) throws Exception
	{
		return reservedTelBaseDAO.saveOrUpdate(reservedTelNum);
	}

	public PaginationObj searchReleasedTelNums4Boss(String reserveTelNum, String otherTelNum, int pageNo, int pageCount) throws Exception
	{
		// "ԤԼ�ѹ���" �� "���ú��������Ȼ����" �� "���ú�����й�����λ!=SD.LJ"
		String where = " reserveOutTime<=sysdate and reserveTelNum in (select telNum from telnum_usable where belongedUnit!='SD.LJ') and "
				+ MySqlUtil.getLikeClause("reserveTelNum", reserveTelNum) + " and " + MySqlUtil.getLikeClause("otherTelNum", otherTelNum);

		return reservedTelExtendBaseDAO.searchPaginationByClause(ReservedTelNumDbObjExtend.class, where, "taskId asc", pageNo, pageCount);
	}

	public PaginationObj searchReservedTelNums4Boss(String reserveTelNum, String otherTelNum, int pageNo, int pageCount) throws Exception
	{
		// "ԤԼδ����" �� "������ǰ����λ��һ�����岿��" �� "���ú������Ҳ����" �� "���ú�����й�����λ==SD.LJ"
		String where = " reserveOutTime>sysdate ";
		where += " and ( CURRENTPROCESSSUBJECTDEPARTID not in(select id from dict_district) or PREVIOUSPROCESSUSERDEPARTID not in(select id from dict_district) ) ";
		where += " and reserveTelNum in (select telNum from telnum_usable where belongedUnit='SD.LJ') ";
		where += " and " + MySqlUtil.getLikeClause("reserveTelNum", reserveTelNum) + " and " + MySqlUtil.getLikeClause("otherTelNum", otherTelNum);

		return reservedTelExtendBaseDAO.searchPaginationByClause(ReservedTelNumDbObjExtend.class, where, "taskId asc", pageNo, pageCount);
	}

	public PaginationObj searchReservedTelNumPagintion(String startTime, String endTime, String reserveTelNum, String otherTelNum, String taskId, int pageNo,
			int pageCount) throws Exception
	{
		String where = " 1=1 ";
		if (!StringUtil.isEmpty(startTime))
		{
			where += " and reserveTime>='" + startTime + "'";
		}
		if (!StringUtil.isEmpty(endTime))
		{
			where += " and reserveTime<='" + endTime + "'";
		}
		if (!StringUtil.isEmpty(reserveTelNum))
		{
			where += " and " + MySqlUtil.getLikeClause("reserveTelNum", reserveTelNum);
		}
		if (!StringUtil.isEmpty(otherTelNum))
		{
			where += " and " + MySqlUtil.getLikeClause("otherTelNum", otherTelNum);
		}
		if (!StringUtil.isEmpty(taskId))
		{
			where += " and taskId='" + taskId + "'";
		}

		return reservedTelExtendBaseDAO.searchPaginationByClause(ReservedTelNumDbObjExtend.class, where, "taskId desc", pageNo, pageCount);
	}

	public PaginationObj searchAllUsableTelNumPagintion(boolean exceptReversedTelNum, String telNum, int pageNo, int pageCount) throws Exception
	{
		String where = MySqlUtil.getLikeClause("telNum", telNum);
		if (exceptReversedTelNum)
		{
			where += " and telNum not in (select reserveTelNum from telnum_reserved where reserveOutTime>sysdate)";
		}
		return usableTelBaseDAO.searchPaginationByClause(UsableTelNumDbObj.class, where, "telNum", pageNo, pageCount);
	}
}
