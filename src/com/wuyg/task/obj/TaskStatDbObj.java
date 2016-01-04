package com.wuyg.task.obj;

import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;

public class TaskStatDbObj extends BaseDbObj
{
	private String district;// ����
	private String departmentName;// ����
	private String taskSubType;// ����С��
	private int createTotalCount;// �ɵ�����
	private int createNeedAuditCount;// �ɳ���-��������
	private int createNeedRevertCount;//�ɳ���-��������
	private int createNeedArchiveCount;//�ɳ���-���鵵��
	private int createArchivedCount;//�ɳ���-�ѹ鵵��
	private int createProcessSucessCount;//�����-�ɹ���
	private int createProcessFailedCount;//�����-ʧ����
	private int createOverTimeCount;//�ɳ���-��ʱ��
	private int acceptTotalCount;//�����-��������
	private int acceptNeedRevertCount;//�����-���ظ���
	private int acceptNeedAuditCount;//�����-��������
	private int acceptNeedArchiveCount;//�����-���鵵��
	private int acceptArchivedCount;//�����-�ѹ鵵��
	private int acceptProcessSucessCount;//�����-�ɹ���
	private int acceptProcessFailedCount;//�����-ʧ����
	private int acceptOvertimeCount;//�����-��ʱ��

	@Override
	public String findKeyColumnName()
	{
		// TODO Auto-generated method stub
		return null;
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
		return null;
	}
	
	// ����excelʱʹ�õ��ֶ�ӳ���ϵ
	public LinkedHashMap<String, String> getProperties()
	{
		LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
		p.put("district","����");
		p.put("departmentName","����");
		p.put("taskSubType","����С��");
		p.put("createTotalCount","�ɵ�����");
		p.put("createNeedAuditCount","�ɷ���-��������");
		p.put("createNeedRevertCount","�ɷ���-��������");
		p.put("createNeedArchiveCount","�ɷ���-���鵵��");
		p.put("createArchivedCount","�ɷ���-�ѹ鵵��");
		p.put("createProcessSucessCount","�ɷ���-����ɹ���");
		p.put("createProcessFailedCount","�ɷ���-����ʧ����");
		p.put("createOverTimeCount","�ɷ���-��ʱ��");
		p.put("acceptTotalCount","��������");
		p.put("acceptNeedAuditCount","�����-��������");
		p.put("acceptNeedRevertCount","�����-���ظ���");
		p.put("acceptNeedArchiveCount","�����-���鵵��");
		p.put("acceptArchivedCount","�����-�ѹ鵵��");
		p.put("acceptProcessSucessCount","�����-����ɹ���");
		p.put("acceptProcessFailedCount","�����-����ʧ����");
		p.put("acceptOvertimeCount","�����-��ʱ��");
		return p;
	}


	public int getCreateProcessSucessCount()
	{
		return createProcessSucessCount;
	}

	public void setCreateProcessSucessCount(int createProcessSucessCount)
	{
		this.createProcessSucessCount = createProcessSucessCount;
	}

	public int getCreateProcessFailedCount()
	{
		return createProcessFailedCount;
	}

	public void setCreateProcessFailedCount(int createProcessFailedCount)
	{
		this.createProcessFailedCount = createProcessFailedCount;
	}

	public int getAcceptProcessSucessCount()
	{
		return acceptProcessSucessCount;
	}

	public void setAcceptProcessSucessCount(int acceptProcessSucessCount)
	{
		this.acceptProcessSucessCount = acceptProcessSucessCount;
	}

	public int getAcceptProcessFailedCount()
	{
		return acceptProcessFailedCount;
	}

	public void setAcceptProcessFailedCount(int acceptProcessFailedCount)
	{
		this.acceptProcessFailedCount = acceptProcessFailedCount;
	}

	public int getAcceptNeedAuditCount()
	{
		return acceptNeedAuditCount;
	}

	public void setAcceptNeedAuditCount(int acceptNeedAuditCount)
	{
		this.acceptNeedAuditCount = acceptNeedAuditCount;
	}

	public String getTaskSubType()
	{
		return taskSubType;
	}

	public void setTaskSubType(String taskSubType)
	{
		this.taskSubType = taskSubType;
	}

	public String getDistrict()
	{
		return district;
	}

	public void setDistrict(String district)
	{
		this.district = district;
	}

	public String getDepartmentName()
	{
		return departmentName;
	}

	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}

	public int getCreateTotalCount()
	{
		return createTotalCount;
	}

	public void setCreateTotalCount(int createTotalCount)
	{
		this.createTotalCount = createTotalCount;
	}

	public int getCreateNeedAuditCount()
	{
		return createNeedAuditCount;
	}

	public void setCreateNeedAuditCount(int createNeedAuditCount)
	{
		this.createNeedAuditCount = createNeedAuditCount;
	}

	public int getCreateNeedRevertCount()
	{
		return createNeedRevertCount;
	}

	public void setCreateNeedRevertCount(int createNeedRevertCount)
	{
		this.createNeedRevertCount = createNeedRevertCount;
	}

	public int getCreateNeedArchiveCount()
	{
		return createNeedArchiveCount;
	}

	public void setCreateNeedArchiveCount(int createNeedArchiveCount)
	{
		this.createNeedArchiveCount = createNeedArchiveCount;
	}

	public int getCreateArchivedCount()
	{
		return createArchivedCount;
	}

	public void setCreateArchivedCount(int createArchivedCount)
	{
		this.createArchivedCount = createArchivedCount;
	}

	public int getCreateOverTimeCount()
	{
		return createOverTimeCount;
	}

	public void setCreateOverTimeCount(int createOverTimeCount)
	{
		this.createOverTimeCount = createOverTimeCount;
	}

	public int getAcceptTotalCount()
	{
		return acceptTotalCount;
	}

	public void setAcceptTotalCount(int acceptTotalCount)
	{
		this.acceptTotalCount = acceptTotalCount;
	}

	public int getAcceptNeedRevertCount()
	{
		return acceptNeedRevertCount;
	}

	public void setAcceptNeedRevertCount(int acceptNeedRevertCount)
	{
		this.acceptNeedRevertCount = acceptNeedRevertCount;
	}

	public int getAcceptNeedArchiveCount()
	{
		return acceptNeedArchiveCount;
	}

	public void setAcceptNeedArchiveCount(int acceptNeedArchiveCount)
	{
		this.acceptNeedArchiveCount = acceptNeedArchiveCount;
	}

	public int getAcceptArchivedCount()
	{
		return acceptArchivedCount;
	}

	public void setAcceptArchivedCount(int acceptArchivedCount)
	{
		this.acceptArchivedCount = acceptArchivedCount;
	}

	public int getAcceptOvertimeCount()
	{
		return acceptOvertimeCount;
	}

	public void setAcceptOvertimeCount(int acceptOvertimeCount)
	{
		this.acceptOvertimeCount = acceptOvertimeCount;
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
