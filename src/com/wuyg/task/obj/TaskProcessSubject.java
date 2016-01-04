package com.wuyg.task.obj;

/**
 * 工单处理的主体对象，可以是具体人员、部门、虚拟组
 * 
 * @author wuyugang
 * 
 */
public class TaskProcessSubject
{
	private String subjectType;// 人员、部门、虚拟组
	private String subjectId;// 处理主体的编号
	private String subjectName;// 处理主体的名字
	private String subjectDepartmentId;// 处理主体的部门编号
	private String subjectDepartmentName;// 处理主体的部门名称
	private String subjectDistrict;// 处理主体的区县

	
	public String getSubjectDistrict()
	{
		return subjectDistrict;
	}

	public void setSubjectDistrict(String subjectDistrict)
	{
		this.subjectDistrict = subjectDistrict;
	}

	public String getSubjectDepartmentId()
	{
		return subjectDepartmentId;
	}

	public void setSubjectDepartmentId(String subjectDepartmentId)
	{
		this.subjectDepartmentId = subjectDepartmentId;
	}

	public String getSubjectDepartmentName()
	{
		return subjectDepartmentName;
	}

	public void setSubjectDepartmentName(String subjectDepartmentName)
	{
		this.subjectDepartmentName = subjectDepartmentName;
	}

	public String getSubjectType()
	{
		return subjectType;
	}

	public void setSubjectType(String subjectType)
	{
		this.subjectType = subjectType;
	}

	public String getSubjectId()
	{
		return subjectId;
	}

	public void setSubjectId(String subjectId)
	{
		this.subjectId = subjectId;
	}

	public String getSubjectName()
	{
		return subjectName;
	}

	public void setSubjectName(String subjectName)
	{
		this.subjectName = subjectName;
	}

}
