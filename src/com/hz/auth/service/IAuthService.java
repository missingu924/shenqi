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
	 * 用户登录验证
	 * 
	 * @param account
	 * @param password
	 * @return
	 */
	public AuthUser login(String account, String password);

	/**
	 * 根据用户账号获取用户信息
	 * 
	 * @param account
	 */
	public AuthUser getUserInfoByAccount(String account);

	/**
	 * 根据csr的账号获取其对应的工长信息
	 * 
	 * @param csrAccount
	 * @return
	 */
	public AuthUser getForcemanByCsr(String csrAccount);

	/**
	 * 获取下属信息
	 * 
	 * @param account
	 * @return
	 */
	public List<AuthUser> getSubordinateList(String account);

	/**
	 * 获取直属领导信息
	 * 
	 * @param account
	 * @return
	 */
	public AuthUser getImmediateLeader(String account);

	/**
	 * 修改密码
	 * 
	 * @param id
	 * @param oldPassword
	 * @param newPasswod
	 * @return
	 */
	public String modifyPassword(int id, String oldPassword, String newPasswod);

	/**
	 * 根据部门编号获取部门信息
	 * 
	 * @param departmentId
	 * @return
	 */
	public AuthDepartment getDepartmentById(String departmentId);

	/**
	 * 根据区县名获取所辖部门信息
	 * 
	 * @param districtName
	 * @return
	 */
	public PaginationObj searchDepartmentPagination(AuthUser user, List<String> districtNames, String departmentId, String departmentName, int pageNo,
			int pageCount);

	/**
	 * 根据组的编号获取组的信息
	 * 
	 * @param groupId
	 * @return
	 */
	public AuthGroup getGroupById(int groupId);

	/**
	 * 获取部门及人员树
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
