package com.hz.customer.obj;

import java.util.LinkedHashMap;

public class ReservedTelNumDbObjExtend extends ReservedTelNumDbObj
{
	private String createUserDistrict;// ԤԼ����
	private String createUserDepartmentId;// ԤԼ����id
	private String createUserDepartmentName;// ԤԼ��������
	private String createUserAccount;// ԤԼ���˺�
	private String createUserName;// ԤԼ������

	private String currentProcessSubjectDistrict;// ����������
	private String currentProcessSubjectDepartId;// �����˲���id
	private String currentProcessSubjectDepartNm;// �����˲�������
	private String currentProcessSubjectId;// ���������˺�
	private String currentProcessSubjectName;// ����������
	
	@Override
	public String findTableName()
	{
		return "v_telnum_reserved";
	}

	// ����excelʱʹ�õ��ֶ�ӳ���ϵ
	public LinkedHashMap<String, String> getProperties()
	{
		LinkedHashMap<String, String> p = new LinkedHashMap<String, String>();

		p.put("reserveTelNum", "ԤԼ����");
		p.put("otherTelNum", "��������");
		p.put("reserveTelNumLevel", "ԤԼ����ȼ�");
		p.put("otherTelNumLevel", "��������ȼ�");
		p.put("reserveTelNumDesc", "ԤԼ��������");
		p.put("taskId", "������");
		
		p.put("createUserDistrict", "ԤԼ������");
		p.put("createUserDepartmentId", "ԤԼ�˲���id");
		p.put("createUserDepartmentName", "ԤԼ�˲�������");
		p.put("createUserAccount", "ԤԼ���˺�");
		p.put("createUserName", "ԤԼ������");

		p.put("currentProcessSubjectDistrict", "��ǰ����������");
		p.put("currentProcessSubjectDepartId", "��ǰ�����˲���id");
		p.put("currentProcessSubjectDepartNm", "��ǰ�����˲�������");
		p.put("currentProcessSubjectId", "��ǰ�������˺�");
		p.put("currentProcessSubjectName", "��ǰ����������");

		p.put("reserveTime", "ԤԼʱ��");
		p.put("reserveOutTime", "ԤԼ��ʱʱ��");
		p.put("overTime", "�Ƿ�ʱ");
		p.put("status", "ԤԼ״̬");
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
