package com.wuyg.task.obj;

import java.util.LinkedHashMap;

import com.wuyg.common.dao.BaseDbObj;

public class TaskStatDbObj extends BaseDbObj
{
	private String district;// 区县
	private String departmentName;// 部门
	private String taskSubType;// 工单小类
	private int createTotalCount;// 派单总数
	private int createNeedAuditCount;// 派出的-待审批数
	private int createNeedRevertCount;//派出的-待处理数
	private int createNeedArchiveCount;//派出的-待归档数
	private int createArchivedCount;//派出的-已归档数
	private int createProcessSucessCount;//受理的-成功数
	private int createProcessFailedCount;//受理的-失败数
	private int createOverTimeCount;//派出的-超时数
	private int acceptTotalCount;//受理的-受理总数
	private int acceptNeedRevertCount;//受理的-待回复数
	private int acceptNeedAuditCount;//受理的-待审批数
	private int acceptNeedArchiveCount;//受理的-待归档数
	private int acceptArchivedCount;//受理的-已归档数
	private int acceptProcessSucessCount;//受理的-成功数
	private int acceptProcessFailedCount;//受理的-失败数
	private int acceptOvertimeCount;//受理的-超时数

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
	
	// 导出excel时使用的字段映射关系
	public LinkedHashMap<String, String> getProperties()
	{
		LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();
		p.put("district","区县");
		p.put("departmentName","部门");
		p.put("taskSubType","工单小类");
		p.put("createTotalCount","派单总数");
		p.put("createNeedAuditCount","派发的-待审批数");
		p.put("createNeedRevertCount","派发的-待处理数");
		p.put("createNeedArchiveCount","派发的-待归档数");
		p.put("createArchivedCount","派发的-已归档数");
		p.put("createProcessSucessCount","派发的-处理成功数");
		p.put("createProcessFailedCount","派发的-处理失败数");
		p.put("createOverTimeCount","派发的-超时数");
		p.put("acceptTotalCount","受理总数");
		p.put("acceptNeedAuditCount","受理的-待审批数");
		p.put("acceptNeedRevertCount","受理的-待回复数");
		p.put("acceptNeedArchiveCount","受理的-待归档数");
		p.put("acceptArchivedCount","受理的-已归档数");
		p.put("acceptProcessSucessCount","受理的-处理成功数");
		p.put("acceptProcessFailedCount","受理的-处理失败数");
		p.put("acceptOvertimeCount","受理的-超时数");
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
