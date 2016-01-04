package com.hz.customer.obj;

import java.util.LinkedHashMap;

public class ReservedTelNumDbObjExtend extends ReservedTelNumDbObj
{
	private String createUserDistrict;// 预约区县
	private String createUserDepartmentId;// 预约部门id
	private String createUserDepartmentName;// 预约部门名称
	private String createUserAccount;// 预约人账号
	private String createUserName;// 预约人姓名

	private String currentProcessSubjectDistrict;// 处理人区县
	private String currentProcessSubjectDepartId;// 处理人部门id
	private String currentProcessSubjectDepartNm;// 处理人部门名称
	private String currentProcessSubjectId;// 处理人人账号
	private String currentProcessSubjectName;// 处理人姓名
	
	@Override
	public String findTableName()
	{
		return "v_telnum_reserved";
	}

	// 导出excel时使用的字段映射关系
	public LinkedHashMap<String, String> getProperties()
	{
		LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();

		p.put("reserveTelNum", "预约号码");
		p.put("otherTelNum", "他网号码");
		p.put("reserveTelNumLevel", "预约号码等级");
		p.put("otherTelNumLevel", "他网号码等级");
		p.put("reserveTelNumDesc", "预约号码描述");
		p.put("taskId", "工单号");
		
		p.put("createUserDistrict", "预约人区县");
		p.put("createUserDepartmentId", "预约人部门id");
		p.put("createUserDepartmentName", "预约人部门名称");
		p.put("createUserAccount", "预约人账号");
		p.put("createUserName", "预约人姓名");

		p.put("currentProcessSubjectDistrict", "当前处理人区县");
		p.put("currentProcessSubjectDepartId", "当前处理人部门id");
		p.put("currentProcessSubjectDepartNm", "当前处理人部门名称");
		p.put("currentProcessSubjectId", "当前处理人账号");
		p.put("currentProcessSubjectName", "当前处理人姓名");

		p.put("reserveTime", "预约时间");
		p.put("reserveOutTime", "预约超时时间");
		p.put("overTime", "是否超时");
		p.put("status", "预约状态");
		return p;
	}

	public String getCreateUserDistrict()
	{
		return createUserDistrict;
	}

	public void setCreateUserDistrict(String createUserDistrict)
	{
		this.createUserDistrict = createUserDistrict;
	}

	public String getCreateUserDepartmentId()
	{
		return createUserDepartmentId;
	}

	public void setCreateUserDepartmentId(String createUserDepartmentId)
	{
		this.createUserDepartmentId = createUserDepartmentId;
	}

	public String getCreateUserDepartmentName()
	{
		return createUserDepartmentName;
	}

	public void setCreateUserDepartmentName(String createUserDepartmentName)
	{
		this.createUserDepartmentName = createUserDepartmentName;
	}

	public String getCreateUserAccount()
	{
		return createUserAccount;
	}

	public void setCreateUserAccount(String createUserAccount)
	{
		this.createUserAccount = createUserAccount;
	}

	public String getCreateUserName()
	{
		return createUserName;
	}

	public void setCreateUserName(String createUserName)
	{
		this.createUserName = createUserName;
	}

	public String getCurrentProcessSubjectDistrict()
	{
		return currentProcessSubjectDistrict;
	}

	public void setCurrentProcessSubjectDistrict(String currentProcessSubjectDistrict)
	{
		this.currentProcessSubjectDistrict = currentProcessSubjectDistrict;
	}

	public String getCurrentProcessSubjectDepartId()
	{
		return currentProcessSubjectDepartId;
	}

	public void setCurrentProcessSubjectDepartId(String currentProcessSubjectDepartId)
	{
		this.currentProcessSubjectDepartId = currentProcessSubjectDepartId;
	}

	public String getCurrentProcessSubjectDepartNm()
	{
		return currentProcessSubjectDepartNm;
	}

	public void setCurrentProcessSubjectDepartNm(String currentProcessSubjectDepartNm)
	{
		this.currentProcessSubjectDepartNm = currentProcessSubjectDepartNm;
	}

	public String getCurrentProcessSubjectId()
	{
		return currentProcessSubjectId;
	}

	public void setCurrentProcessSubjectId(String currentProcessSubjectId)
	{
		this.currentProcessSubjectId = currentProcessSubjectId;
	}

	public String getCurrentProcessSubjectName()
	{
		return currentProcessSubjectName;
	}

	public void setCurrentProcessSubjectName(String currentProcessSubjectName)
	{
		this.currentProcessSubjectName = currentProcessSubjectName;
	}

}
