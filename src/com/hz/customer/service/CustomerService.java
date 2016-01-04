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
		// 1、查询他网客户号码的等级等基本信息
		String otherTelNumLevel = TaskConstant.TEL_LEVEL_NONE;
		Object otherCustomerObject = otherCustomerBaseDAO.searchByKey(OtherCustomerDbObj.class, telNum);
		if (otherCustomerObject != null)
		{
			OtherCustomerDbObj otherTel = (OtherCustomerDbObj) otherCustomerObject;
			otherTelNumLevel = otherTel.getHaoMaDengJi();
		}
		// 2、查询本网可用号码中后9、8、7、6、5、4位相同的号码
		// 3、查询本网可用号码中等级>=他网号码等级+1的号码
		// 4、随机查询20个号码

		String notReservedTelNumClause = " telNum not in (select reserveTelNum from telnum_reserved where reserveOutTime>sysdate)";

		StringBuffer s = new StringBuffer();
		s.append(" select telnum,belongedunit,telnumlevel,telnumdesc,timeStamp \n");
		s.append(" from \n");
		s.append(" ( \n");

		s
				.append(" select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'后9位相同' telNumDesc,'01' orderByValue from telnum_usable t where belongedunit='SD.LJ' and telnum like '%"
						+ telNum.substring(2, 11) + "' and " + notReservedTelNumClause + " \n");
		s.append(" union \n");
		s
				.append(" select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'后8位相同' telNumDesc,'02' orderByValue  from telnum_usable t where belongedunit='SD.LJ' and telnum like '%"
						+ telNum.substring(3, 11) + "' and telnum not like '%" + telNum.substring(2, 11) + "'  and " + notReservedTelNumClause + " \n");
		s.append(" union \n");
		s
				.append(" select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'后7位相同' telNumDesc,'03' orderByValue  from telnum_usable t where belongedunit='SD.LJ' and telnum like '%"
						+ telNum.substring(4, 11) + "' and telnum not like '%" + telNum.substring(3, 11) + "'  and " + notReservedTelNumClause + " \n");
		s.append(" union \n");
		s
				.append(" select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'后6位相同' telNumDesc,'04' orderByValue  from telnum_usable t where belongedunit='SD.LJ' and telnum like '%"
						+ telNum.substring(5, 11) + "' and telnum not like '%" + telNum.substring(4, 11) + "'  and " + notReservedTelNumClause + " \n");
		s.append(" union \n");
		s
				.append(" select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'后5位相同' telNumDesc,'05' orderByValue  from telnum_usable t where belongedunit='SD.LJ' and telnum like '%"
						+ telNum.substring(6, 11) + "' and telnum not like '%" + telNum.substring(5, 11) + "'  and " + notReservedTelNumClause + " \n");
		s.append(" union \n");
		s
				.append(" select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'后4位相同' telNumDesc,'06' orderByValue  from telnum_usable t where belongedunit='SD.LJ' and telnum like '%"
						+ telNum.substring(7, 11) + "' and telnum not like '%" + telNum.substring(6, 11) + "'  and " + notReservedTelNumClause + " \n");

		if (TaskConstant.TEL_LEVEL_1.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_2.equals(otherTelNumLevel))
		{
			s.append(" union \n");
			s
					.append("  select * from (select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'尾号不同' telNumDesc,'07' orderByValue  from telnum_usable t where telNumLevel='一类号码' and belongedunit='SD.LJ'  and "
							+ notReservedTelNumClause + "  order by dbms_random.value()) where rownum<=10 \n");

		}
		if (TaskConstant.TEL_LEVEL_1.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_2.equals(otherTelNumLevel)
				|| TaskConstant.TEL_LEVEL_3.equals(otherTelNumLevel))
		{
			s.append(" union \n");
			s
					.append(" select * from (select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'尾号不同' telNumDesc,'08' orderByValue  from telnum_usable t where telNumLevel='二类号码' and belongedunit='SD.LJ' and "
							+ notReservedTelNumClause + " order by dbms_random.value()) where rownum<=10 \n");
		}
		if (TaskConstant.TEL_LEVEL_1.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_2.equals(otherTelNumLevel)
				|| TaskConstant.TEL_LEVEL_3.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_4.equals(otherTelNumLevel))
		{
			s.append(" union \n");
			s
					.append("  select * from (select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'尾号不同' telNumDesc,'09' orderByValue  from telnum_usable t where telNumLevel='三类号码' and belongedunit='SD.LJ' and "
							+ notReservedTelNumClause + " order by dbms_random.value()) where rownum<=10 \n");
		}
		if (TaskConstant.TEL_LEVEL_1.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_2.equals(otherTelNumLevel)
				|| TaskConstant.TEL_LEVEL_3.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_4.equals(otherTelNumLevel)
				|| TaskConstant.TEL_LEVEL_5.equals(otherTelNumLevel))
		{
			s.append(" union \n");
			s
					.append("  select * from (select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'尾号不同' telNumDesc,'10' orderByValue  from telnum_usable t where telNumLevel='四类号码' and belongedunit='SD.LJ' and "
							+ notReservedTelNumClause + " order by dbms_random.value()) where rownum<=10 \n");
		}
		if (TaskConstant.TEL_LEVEL_1.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_2.equals(otherTelNumLevel)
				|| TaskConstant.TEL_LEVEL_3.equals(otherTelNumLevel) || TaskConstant.TEL_LEVEL_4.equals(otherTelNumLevel)
				|| TaskConstant.TEL_LEVEL_5.equals(otherTelNumLevel))
		{
			s.append(" union \n");
			s
					.append("  select * from (select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'尾号不同' telNumDesc,'11' orderByValue  from telnum_usable t where telNumLevel='五类号码' and belongedunit='SD.LJ' and "
							+ notReservedTelNumClause + " order by dbms_random.value()) where rownum<=10 \n");
		}
		s.append(" union \n");
		s
				.append("  select * from (select t.telnum,t.belongedunit,t.telnumlevel,t.timeStamp,'尾号不同' telNumDesc,'12' orderByValue  from telnum_usable t where telNumLevel='非吉祥号码' and belongedunit='SD.LJ' and "
						+ notReservedTelNumClause + " order by dbms_random.value()) where rownum<=10 \n");
		s.append(" ) \n");
		s.append(" order by orderbyvalue,telNum \n");

		// 5、构造返回结果

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
		// "预约已过期" 但 "可用号码表中仍然存在" 且 "可用号码表中归属单位!=SD.LJ"
		String where = " reserveOutTime<=sysdate and reserveTelNum in (select telNum from telnum_usable where belongedUnit!='SD.LJ') and "
				+ MySqlUtil.getLikeClause("reserveTelNum", reserveTelNum) + " and " + MySqlUtil.getLikeClause("otherTelNum", otherTelNum);

		return reservedTelExtendBaseDAO.searchPaginationByClause(ReservedTelNumDbObjExtend.class, where, "taskId asc", pageNo, pageCount);
	}

	public PaginationObj searchReservedTelNums4Boss(String reserveTelNum, String otherTelNum, int pageNo, int pageCount) throws Exception
	{
		// "预约未过期" 且 "工单当前处理单位是一个具体部门" 且 "可用号码表中也存在" 且 "可用号码表中归属单位==SD.LJ"
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
