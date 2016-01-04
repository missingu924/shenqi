package com.hz.auth.obj;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.IREM;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.common.util.StringUtil;

public class AuthUser extends BaseDbObj
{
	private int id;
	private String account;// �˺�
	private String password;// ����
	private String name;// ����
	private String telephone;// �绰
	private String sex;// �Ա�
	private String province;// ����ʡ
	private String city;// ���ڵ���
	private String district;// ��������
	private String departmentId;// ���ڲ���ID
	private String departmentName;// ���ڲ�����
	private String office;// ����ְ��
	private List<AuthGroup> groups = new ArrayList<AuthGroup>();// ������ID�б�
	private List<AuthUserRole> roles = new ArrayList<AuthUserRole>();// ���߱��Ľ�ɫID�б�
	private List<String> functions = new ArrayList<String>();// ���߱���Ȩ����ID�б�
	private String roleLevel;// Ȩ�޼���
	
	// add for t1
	private Integer t1_user_id=1;
	private Integer t1_employee_id=1;
	
	public boolean hasRole(String roleName)
	{
		if (StringUtil.isEmpty(roleName))
		{
			return true;
		}
		for (int i = 0; i < roles.size(); i++)
		{
			if (roleName.equals(roles.get(i).getRoleName()))
			{
				return true;
			}
		}
		return false;
	}

	public boolean hasGroup(String functionName)
	{
		if (StringUtil.isEmpty(functionName))
		{
			return true;
		}
		for (int i = 0; i < functions.size(); i++)
		{
			if (functionName.equals(functions.get(i)))
			{
				return true;
			}
		}
		return false;
	}

	public boolean hasFunction(String groupName)
	{
		if (StringUtil.isEmpty(groupName))
		{
			return true;
		}
		for (int i = 0; i < groups.size(); i++)
		{
			if (groupName.equals(groups.get(i)))
			{
				return true;
			}
		}
		return false;
	}

	// ���м��û�
	public boolean isCityUser()
	{
		return this.departmentId.equals("SD.LJ") || this.district.equalsIgnoreCase("����");
	}

	// ���ؼ��û�
	public boolean isDistrictUser()
	{
		IBaseDAO districtBaseDAO = new DefaultBaseDAO(new AuthDistrict());

		Object district = districtBaseDAO.searchByKey(AuthDistrict.class, this.departmentId);

		return district != null && !isCityUser();
	}

	// ���ż��û�
	public boolean isDepartmentUser()
	{
		return !isCityUser() && !isDistrictUser();
	}

	public String getRoleLevel()
	{
		return roleLevel;
	}

	public void setRoleLevel(String roleLevel)
	{
		this.roleLevel = roleLevel;
	}

	public String getDistrict()
	{
		return district;
	}
	
	public String getCvencode()
	{
		//district=cvencode+'|'+cvenname
		return district.substring(0, district.indexOf("|"));
	}

	public void setDistrict(String district)
	{
		this.district = district;
	}

	public List<AuthGroup> getGroupIds()
	{
		return groups;
	}

	public void fillGroups(List<AuthGroup> groups)
	{
		this.groups = groups;
	}

	public List<AuthUserRole> getRoleIds()
	{
		return roles;
	}

	public void fillRoles(List<AuthUserRole> roles)
	{
		this.roles = roles;
	}

	public List<String> getFunctionIds()
	{
		return functions;
	}

	public void fillFunctions(List<String> functions)
	{
		this.functions = functions;
	}

	public String getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(String departmentId)
	{
		this.departmentId = departmentId;
	}

	public String getDepartmentName()
	{
		return departmentName;
	}

	public void setDepartmentName(String departmentName)
	{
		this.departmentName = departmentName;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTelephone()
	{
		return telephone;
	}

	public void setTelephone(String telephone)
	{
		this.telephone = telephone;
	}

	public String getSex()
	{
		return sex;
	}

	public void setSex(String sex)
	{
		this.sex = sex;
	}

	public String getProvince()
	{
		return province;
	}

	public String getOffice()
	{
		return office;
	}

	public void setOffice(String office)
	{
		this.office = office;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
	
	public Integer getT1_user_id()
	{
		return t1_user_id;
	}

	public void setT1_user_id(Integer t1_user_id)
	{
		this.t1_user_id = t1_user_id;
	}

	public Integer getT1_employee_id()
	{
		return t1_employee_id;
	}

	public void setT1_employee_id(Integer t1_employee_id)
	{
		this.t1_employee_id = t1_employee_id;
	}


	@Override
	public String findKeyColumnName()
	{
		return "id";
	}

	@Override
	public String findParentKeyColumnName()
	{
		return "departmentId";
	}

	@Override
	public String findTableName()
	{
		return "auth_user";
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
