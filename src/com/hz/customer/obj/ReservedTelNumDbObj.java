package com.hz.customer.obj;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;

public class ReservedTelNumDbObj extends BaseDbObj
{
	private String otherTelNum;// 他网号码
	private String otherTelNumLevel;// 他网号码等级
	private String reserveTelNum;// 预约号码
	private String reserveTelNumLevel;// 预约号码等级
	private String reserveTelNumDesc;// 预约号码描述
	private long taskId;// 工单号
//	private String reserveDistrict;// 预约区县
//	private String reserveDepartmentId;// 预约部门id
//	private String reserveDepartmentName;// 预约部门名称
//	private String reserveUserAccount;// 预约人账号
//	private String reserveUserName;// 预约人姓名
	private Timestamp reserveTime;// 预约时间
	private Timestamp reserveOutTime;// 预约超时时间，默认为预约后72小时超时
	private String status;// 预约中，预约超时，预约成功

	@Override
	public String findKeyColumnName()
	{
		// TODO Auto-generated method stub
		return "taskId";
	}

	@Override
	public String findParentKeyColumnName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findTableName()
	{
		// TODO Auto-generated method stub
		return "telnum_reserved";
	}

	public String getOverTime()
	{
		return isTimeOut()?"是":"否";
	}

	public boolean isTimeOut()
	{
		return System.currentTimeMillis() > reserveOutTime.getTime();
	}

	public String getOtherTelNum()
	{
		return otherTelNum;
	}

	public void setOtherTelNum(String otherTelNum)
	{
		this.otherTelNum = otherTelNum;
	}

	public String getOtherTelNumLevel()
	{
		return otherTelNumLevel;
	}

	public void setOtherTelNumLevel(String otherTelNumLevel)
	{
		this.otherTelNumLevel = otherTelNumLevel;
	}

	public String getReserveTelNum()
	{
		return reserveTelNum;
	}

	public void setReserveTelNum(String reserveTelNum)
	{
		this.reserveTelNum = reserveTelNum;
	}

	public String getReserveTelNumLevel()
	{
		return reserveTelNumLevel;
	}

	public void setReserveTelNumLevel(String reserveTelNumLevel)
	{
		this.reserveTelNumLevel = reserveTelNumLevel;
	}

	public String getReserveTelNumDesc()
	{
		return reserveTelNumDesc;
	}

	public void setReserveTelNumDesc(String reserveTelNumDesc)
	{
		this.reserveTelNumDesc = reserveTelNumDesc;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public long getTaskId()
	{
		return taskId;
	}

	public void setTaskId(long taskId)
	{
		this.taskId = taskId;
	}

//	public String getReserveDistrict()
//	{
//		return reserveDistrict;
//	}
//
//	public void setReserveDistrict(String reserveDistrict)
//	{
//		this.reserveDistrict = reserveDistrict;
//	}
//
//	public String getReserveDepartmentId()
//	{
//		return reserveDepartmentId;
//	}
//
//	public void setReserveDepartmentId(String reserveDepartmentId)
//	{
//		this.reserveDepartmentId = reserveDepartmentId;
//	}
//
//	public String getReserveDepartmentName()
//	{
//		return reserveDepartmentName;
//	}
//
//	public void setReserveDepartmentName(String reserveDepartmentName)
//	{
//		this.reserveDepartmentName = reserveDepartmentName;
//	}
//
//	public String getReserveUserAccount()
//	{
//		return reserveUserAccount;
//	}
//
//	public void setReserveUserAccount(String reserveUserAccount)
//	{
//		this.reserveUserAccount = reserveUserAccount;
//	}
//
//	public String getReserveUserName()
//	{
//		return reserveUserName;
//	}
//
//	public void setReserveUserName(String reserveUserName)
//	{
//		this.reserveUserName = reserveUserName;
//	}

	public Timestamp getReserveTime()
	{
		return reserveTime;
	}

	public void setReserveTime(Timestamp reserveTime)
	{
		this.reserveTime = reserveTime;
	}

	public Timestamp getReserveOutTime()
	{
		return reserveOutTime;
	}

	public void setReserveOutTime(Timestamp reserveOutTime)
	{
		this.reserveOutTime = reserveOutTime;
	}

	@Override
	public String getBasePath()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCnName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LinkedHashMap<String, String> getProperties()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
