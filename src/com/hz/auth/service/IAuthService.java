package com.hz.auth.service;

import java.util.List;

import com.hz.auth.obj.AuthDepartment;
import com.hz.auth.obj.AuthGroup;
import com.hz.auth.obj.AuthUser;
import com.inspur.common.tree.Node;
import com.wuyg.common.obj.PaginationObj;

public interface IAuthService
{
	/**
	 * �û���¼��֤
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	public AuthUser login(String account, String password);

	/**
	 * �����û��˺Ż�ȡ�û���Ϣ
	 * 
	 * @param account
	 */
	public AuthUser getUserInfoByAccount(String account);

	/**
	 * ����csr���˺Ż�ȡ���Ӧ�Ĺ�����Ϣ
	 * 
	 * @param csrAccount
	 * @return
	 */
	public AuthUser getForcemanByCsr(String csrAccount);

	/**
	 * ��ȡ������Ϣ
	 * 
	 * @param account
	 * @return
	 */
	public List<AuthUser> getSubordinateList(String account);

	/**
	 * ��ȡֱ���쵼��Ϣ
	 * 
	 * @param account
	 * @return
	 */
	public AuthUser getImmediateLeader(String account);

	/**
	 * �޸�����
	 * 
	 * @param id
	 * @param oldPassword
	 * @param newPasswod
	 * @return
	 */
	public String modifyPassword(int id, String oldPassword, String newPasswod);

	/**
	 * ���ݲ��ű�Ż�ȡ������Ϣ
	 * 
	 * @param departmentId
	 * @return
	 */
	public AuthDepartment getDepartmentById(String departmentId);

	/**
	 * ������������ȡ��Ͻ������Ϣ
	 * 
	 * @param districtName
	 * @return
	 */
	public PaginationObj searchDepartmentPagination(AuthUser user, List<String> districtNames, String departmentId, String departmentName, int pageNo,
			int pageCount);

	/**
	 * ������ı�Ż�ȡ�����Ϣ
	 * 
	 * @param groupId
	 * @return
	 */
	public AuthGroup getGroupById(int groupId);

	/**
	 * ��ȡ���ż���Ա��
	 * 
	 * @param user
	 * @return
	 */
	public Node getAuthTree(AuthUser user, String toSubTree, String district, String departmentId, String userAccount);

	public boolean saveOrUpdateDepartment(AuthDepartment department);

	public boolean deleteDepartment(List<String> departmentIds);

	public PaginationObj searchUserPaginationByDistrict(AuthUser user, List<String> districtNames, String name, String account, int pageNo, int pageCount);

	public boolean saveOrUpdateUser(AuthUser user);

	public boolean deleteUser(List<String> userIds);

	public boolean save(AuthUser userInfo);

	public boolean update(AuthUser userInfo);

	public boolean delete(List<String> userIds);
}
