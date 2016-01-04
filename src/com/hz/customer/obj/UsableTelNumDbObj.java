package com.hz.customer.obj;

import java.sql.Timestamp;
import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;

public class UsableTelNumDbObj extends BaseDbObj
{
	private String telNum;// �ֻ���
	private String belongedUnit;// ������λ
	private String telNumLevel;// ����ȼ�
	private String telNumDesc;// ��������
	private Timestamp timeStamp;// ���ݵ���ʱ��

	@Override
	public String findKeyColumnName()
	{
		// TODO Auto-generated method stub
		return "telNum";
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
		return "telNum_Usable";
	}
	
	// ����excelʱʹ�õ��ֶ�ӳ���ϵ
	public LinkedHashMap<String, String> getProperties()
	{
		LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
		p.put("telNum", "����");
		p.put("belongedUnit", "������λ");
		p.put("telNumLevel", "����ȼ�");
		return p;
	}

	public Timestamp getTimeStamp()
	{
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp)
	{
		this.timeStamp = timeStamp;
	}

	public String getTelNumDesc()
	{
		return telNumDesc;
	}

	public void setTelNumDesc(String telNumDesc)
	{
		this.telNumDesc = telNumDesc;
	}

	public String getTelNum()
	{
		return telNum;
	}

	public void setTelNum(String telNum)
	{
		this.telNum = telNum;
	}

	public String getBelongedUnit()
	{
		return belongedUnit;
	}

	public void setBelongedUnit(String belongedUnit)
	{
		this.belongedUnit = belongedUnit;
	}

	public String getTelNumLevel()
	{
		return telNumLevel;
	}

	public void setTelNumLevel(String telNumLevel)
	{
		this.telNumLevel = telNumLevel;
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
}
