package com.hz.customer.obj;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;

public class ReservedTelNumDbObj extends BaseDbObj
{
	private String otherTelNum;// ��������
	private String otherTelNumLevel;// ��������ȼ�
	private String reserveTelNum;// ԤԼ����
	private String reserveTelNumLevel;// ԤԼ����ȼ�
	private String reserveTelNumDesc;// ԤԼ��������
	private long taskId;// ������
//	private String reserveDistrict;// ԤԼ����
//	private String reserveDepartmentId;// ԤԼ����id
//	private String reserveDepartmentName;// ԤԼ��������
//	private String reserveUserAccount;// ԤԼ���˺�
//	private String reserveUserName;// ԤԼ������
	private Timestamp reserveTime;// ԤԼʱ��
	private Timestamp reserveOutTime;// ԤԼ��ʱʱ�䣬Ĭ��ΪԤԼ��72Сʱ��ʱ
	private String status;// ԤԼ�У�ԤԼ��ʱ��ԤԼ�ɹ�

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
		return isTimeOut()?"��":"��";
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
