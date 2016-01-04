package com.hz.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hz.auth.obj.AuthDepartment;
import com.hz.auth.obj.AuthUser;
import com.hz.auth.service.AuthService;
import com.hz.auth.service.IAuthService;
import com.hz.teamworktask.obj.TeamworkTaskDbObj;
import com.hz.util.SystemConstant;
import com.inspur.common.tree.Node;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.common.util.MyBeanUtils;
import com.wuyg.common.util.RequestUtil;
import com.wuyg.common.util.StringUtil;
import com.wuyg.task.TaskConstant;
import com.wuyg.task.obj.TaskMainInfoDbObj;
import com.wuyg.task.obj.TaskObj;

public class AuthServlet extends HttpServlet
{
	private Logger logger = Logger.getLogger(getClass());
	private IAuthService authService = new AuthService();
	private AuthDepartment department;
	private AuthUser user4addOrModify;
	private AuthUser currentUser;
	private String is4m = "false";

	/**
	 * Constructor of the object.
	 */
	public AuthServlet()
	{
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy()
	{
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		try
		{
			request.setCharacterEncoding("UTF-8");

			// 获取当前用户信息
			currentUser = (AuthUser) request.getSession().getAttribute(SystemConstant.AUTH_USER_INFO);

			String method = request.getParameter("method");

			// 从页面传递过来的参数里面获取基本信息
			parseParameters(request);

			if ("login".equalsIgnoreCase(method))
			{
				login(request, response);
			} else if ("logout".equalsIgnoreCase(method))
			{
				logout(request, response);
			} else if ("needLogin".equalsIgnoreCase(method))
			{
				needLogin(request, response);
			} else if ("userDetail".equalsIgnoreCase(method))
			{
				userDetail(request, response);
			} else if ("modifyPassword".equalsIgnoreCase(method))
			{
				modifyPassword(request, response);
			} else if ("getDepartmentsAndUsers".equalsIgnoreCase(method))
			{
				getDepartmentsAndUsers(request, response);

			} else if ("subjectDetail".equalsIgnoreCase(method))
			{
				subjectDetail(request, response);
			} else if ("departmentList".equalsIgnoreCase(method))
			{
				departmentList(request, response);
			} else if ("departmentAddOrModify".equalsIgnoreCase(method))
			{
				departmentAddOrModify(request, response);
			} else if ("departmentDelete".equalsIgnoreCase(method))
			{
				departmentDelete(request, response);
			} else if ("userList".equalsIgnoreCase(method))
			{
				userList(request, response);
			} else if ("userAddOrModify".equalsIgnoreCase(method))
			{
				userAddOrModify(request, response);
			} else if ("userDelete".equalsIgnoreCase(method))
			{
				userDelete(request, response);
			} else if ("checkAccount".equalsIgnoreCase(method))
			{
				checkAccount(request, response);
			} else if ("checkDepartmentId".equalsIgnoreCase(method))
			{
				checkDepartmentId(request, response);
			}
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	private void checkDepartmentId(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String departmentId = department.getDepartmentId().trim();
		AuthDepartment department = authService.getDepartmentById(departmentId);
		if (department != null)
		{
			response.getWriter().print("true");
		} else
		{
			response.getWriter().print("false");
		}
	}

	private void checkAccount(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String account = user4addOrModify.getAccount().trim();
		AuthUser user = authService.getUserInfoByAccount(account);
		if (user != null)
		{
			response.getWriter().print("true");
		} else
		{
			response.getWriter().print("false");
		}
	}

	private void userDelete(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = request.getParameter("id");
		List<String> ids = new ArrayList<String>();
		ids.add(id);

		authService.deleteUser(ids);

		userList(request, response);
	}

	private void userAddOrModify(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		boolean success = authService.saveOrUpdateUser(user4addOrModify);
		if (success)
		{
			request.setAttribute(SystemConstant.AUTH_USER_INFO, user4addOrModify);
			request.getRequestDispatcher("/Auth/userDetail.jsp").forward(request, response);
		} else
		{
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	private void userList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		// 区县
		String district = request.getParameter("district");
		request.setAttribute("district", district);
		List<String> districtNames = new ArrayList<String>();
		if (!StringUtil.isEmpty(district) && !"ALL".equalsIgnoreCase(district))
		{
			districtNames.add(district);
		}

		// 姓名
		String name = request.getParameter("name");
		request.setAttribute("name", name);

		// 账号
		String account = request.getParameter("account");
		request.setAttribute("account", account);

		int pageNo = getPageNo(request);
		int pageCount = getPageCount(request);

		PaginationObj pagination = authService.searchUserPaginationByDistrict(currentUser, districtNames, name, account, pageNo, pageCount);

		request.setAttribute("pagination", pagination);
		request.getRequestDispatcher("/Auth/userList.jsp").forward(request, response);
	}

	private void departmentDelete(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String id = request.getParameter("id");
		List<String> ids = new ArrayList<String>();
		ids.add(id);

		authService.deleteDepartment(ids);

		departmentList(request, response);
	}

	private void departmentAddOrModify(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		boolean success = authService.saveOrUpdateDepartment(department);
		if (success)
		{
			department = authService.getDepartmentById(department.getDepartmentId());
			request.setAttribute("department", department);
			request.getRequestDispatcher("/Auth/departmentDetail.jsp").forward(request, response);
		} else
		{
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	private void departmentList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 区县
		String district = request.getParameter("district");
		request.setAttribute("district", district);
		List<String> districtNames = new ArrayList<String>();
		if (!StringUtil.isEmpty(district) && !"ALL".equalsIgnoreCase(district))
		{
			districtNames.add(district);
		}
		// 部门名称
		String departmentName = request.getParameter("departmentName");
		request.setAttribute("departmentName", departmentName);
		String departmentId = request.getParameter("departmentId");
		request.setAttribute("departmentId", departmentId);

		int pageNo = getPageNo(request);
		int pageCount = getPageCount(request);

		PaginationObj pagination = authService.searchDepartmentPagination(currentUser, districtNames, departmentId, departmentName, pageNo, pageCount);

		request.setAttribute("pagination", pagination);
		request.getRequestDispatcher("/Auth/departmentList.jsp").forward(request, response);
	}

	private void subjectDetail(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String subjectType = request.getParameter("subjectType");
		String subjectId = request.getParameter("subjectId");

		if (TaskConstant.SUBJECT_TYPE_DEPARTMENT.equals(subjectType))
		{
			AuthDepartment department = authService.getDepartmentById(subjectId);
			request.setAttribute("department", department);
			request.getRequestDispatcher("/Auth/departmentDetail.jsp").forward(request, response);
		} else if (TaskConstant.SUBJECT_TYPE_PERSON.equals(subjectType))
		{
			AuthUser userInfo = authService.getUserInfoByAccount(subjectId);
			request.setAttribute(SystemConstant.AUTH_USER_INFO, userInfo);
			getServletContext().getRequestDispatcher("/Auth/userDetail.jsp").forward(request, response);
		}
	}

	private void departmentDetail(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String departmentId = request.getParameter("departmentId");

		AuthDepartment department = authService.getDepartmentById(departmentId);

		request.getRequestDispatcher("/Auth/departmentsAndUsersSelect.jsp").forward(request, response);
	}

	private void getDepartmentsAndUsers(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String toSubTree = request.getParameter("toSubTree");

		String district = request.getParameter("district");
		if (!StringUtil.isEmpty(district))
		{
			district = new String(district.getBytes("iso-8859-1"), "utf-8");
		}
		String departmentId = request.getParameter("departmentId");
		String userAccount = request.getParameter("userAccount");

		Node departmentsAndUsers = authService.getAuthTree(null, toSubTree, district, departmentId, userAccount);

		request.setAttribute("departmentsAndUsers", departmentsAndUsers);

		request.getRequestDispatcher("/Auth/departmentsAndUsersSelect.jsp").forward(request, response);
	}

	private void modifyPassword(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd1");
		AuthUser userInfo = (AuthUser) request.getSession(true).getAttribute(SystemConstant.AUTH_USER_INFO);

		String message = authService.modifyPassword(userInfo.getId(), oldPwd, newPwd);

		request.setAttribute("message", message);

		request.getRequestDispatcher("modifyPassword-result.jsp").forward(request, response);
	}

	private void userDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String account = request.getParameter(SystemConstant.AUTH_USER_ACCOUNT);
		AuthUser userInfo = authService.getUserInfoByAccount(account);
		request.setAttribute(SystemConstant.AUTH_USER_INFO, userInfo);
		getServletContext().getRequestDispatcher("/Auth/userDetail.jsp").forward(request, response);
	}

	private void needLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// getServletContext().getRequestDispatcher("login.jsp").forward(request,
		// response);
		if ("true".equalsIgnoreCase(is4m))
		{
			response.sendRedirect(request.getContextPath() + "/login4m.jsp?info=needReLogin");
		} else
		{
			response.sendRedirect(request.getContextPath() + "/login.jsp?info=needReLogin");
		}

	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.getSession().removeAttribute(SystemConstant.AUTH_USER_INFO);
		if (RequestUtil.is4m(request))
		{
			response.sendRedirect(request.getContextPath() + "/login4m.jsp");
		} else
		{
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		}

	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AuthUser user = userLogin(request);

		if (user != null)
		{
			if ("true".equalsIgnoreCase(is4m))
			{
				response.sendRedirect(request.getContextPath() + "/main4m.jsp");
			} else
			{
				response.sendRedirect(request.getContextPath() + "/main.jsp");
			}

		} else
		{
			needLogin(request, response);
		}

	}

	// 用户登录
	public AuthUser userLogin(HttpServletRequest request)
	{

		String account = request.getParameter(SystemConstant.AUTH_USER_ACCOUNT);
		String password = request.getParameter(SystemConstant.AUTH_USER_PASSWORD);

		if (StringUtil.isEmpty(account))
		{
			return null;
		}

		logger.info("用户" + account + "登陆。");

		AuthUser user = authService.login(account, password);
		request.getSession(true).setAttribute(SystemConstant.AUTH_USER_INFO, user);
		request.getSession().setAttribute(SystemConstant.AUTH_USER_ACCOUNT, account);// 账号密码再放回session中
		request.getSession().setAttribute(SystemConstant.AUTH_USER_PASSWORD, password);

		return user;
	}

	// 解析前台传递过来的数据
	private void parseParameters(HttpServletRequest request) throws Exception
	{
		// 获取部门数据
		department = (AuthDepartment) MyBeanUtils.createInstanceFromHttpRequest(request, AuthDepartment.class, false);

		// 获取用户数据
		user4addOrModify = (AuthUser) MyBeanUtils.createInstanceFromHttpRequest(request, AuthUser.class, false);

		// 4m
		is4m = request.getParameter("4m");
		if (StringUtil.isEmpty(is4m))
		{
			is4m = request.getSession().getAttribute("4m") + "";
		}
		request.getSession().setAttribute("4m", is4m);
	}

	private int getPageNo(HttpServletRequest request)
	{
		int pageNo = 1;
		String pageNoStr = request.getParameter("pageNo");
		if (!StringUtil.isEmpty(pageNoStr))
		{
			pageNo = Integer.parseInt(pageNoStr);
		}
		return pageNo;
	}

	private int getPageCount(HttpServletRequest request)
	{
		int pageCount = 10;
		String pageCountStr = request.getParameter("pageCount");
		if (!StringUtil.isEmpty(pageCountStr))
		{
			pageCount = Integer.parseInt(pageCountStr);
		}
		return pageCount;
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException
	{
		// Put your code here
	}

}
