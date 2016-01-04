package com.wuyg.common.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.log4j.Logger;

import com.hz.auth.obj.AuthUser;
import com.hz.util.SystemConstant;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.common.obj.BaseSearchCondition;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.common.util.MyBeanUtils;
import com.wuyg.common.util.RequestUtil;
import com.wuyg.common.util.StringUtil;

public abstract class AbstractBaseServletTemplate extends HttpServlet
{
	private Logger log = Logger.getLogger(getClass());

	// 当前登陆用户
	public AuthUser currentUser;

	// 领域实例主键值
	public String domainInstanceKeyValue;
	// 领域实例
	public BaseDbObj domainInstance;
	// 查询条件
	public BaseSearchCondition domainSearchCondition;

	// 虚方法：获取领域DAO，具体集成类实现
	public abstract IBaseDAO getDomainDao();

	// 虚方法：获取领域对象类，具体集成类实现
	public abstract Class getDomainInstanceClz();

	// 虚方法：获取领域查询条件类，具体集成类实现
	public abstract Class getDomainSearchConditionClz();

	// 虚方法：获取领域基本路径，url中使用，具体集成类实现
	public abstract String getBasePath();

	public static final String METHOD = "method";

	public static final String BASE_METHOD_LIST = "list";// 查询
	public static final String BASE_METHOD_ADD_OR_MODIFY = "addOrModify";// 添加或修改
	public static final String BASE_METHOD_PRE_MODIFY = "preModify";// 修改前查询领域对象信息
	public static final String BASE_METHOD_DETAIL = "detail";// 详情
	public static final String BASE_METHOD_DELETE = "delete";// 删除
	public static final String BASE_METHOD_EXPORT = "export";// 导出
	public static final String BASE_METHOD_CHECK_ID = "checkId";// 导出

	public static final String DOMAIN_INSTANCE = "domainInstance";
	public static final String DOMAIN_PAGINATION = "domainPagination";

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

		doPost(request, response);
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
		try
		{
			// 获取当前用户信息
			currentUser = (AuthUser) request.getSession().getAttribute(SystemConstant.AUTH_USER_INFO);

			// 获取执行的方法
			String method = request.getParameter(METHOD);

			// 从页面传递过来的参数里面获取基本信息
			parseParameters(request, method);

			// 执行相关方法
			if (BASE_METHOD_LIST.equals(method))
			{
				list(request, response);
			} else if (BASE_METHOD_CHECK_ID.equals(method))
			{
				checkId(request, response);
			} else if (BASE_METHOD_ADD_OR_MODIFY.equals(method))
			{
				addOrModify(request, response);
			} else if (BASE_METHOD_PRE_MODIFY.equals(method))
			{
				preModify(request, response);
			} else if (BASE_METHOD_DETAIL.equals(method))
			{
				detail(request, response);
			} else if (BASE_METHOD_EXPORT.equals(method))
			{
				export(request, response);
			} else if (BASE_METHOD_DELETE.equals(method))
			{
				delete(request, response);
			} else
			{
				// 执行相应方法，除了基本的增删改查方法以外，其他方法要在继承该类的具体类中实现
				MethodUtils.invokeMethod(this, method, new Object[]
				{ request, response }, new Class[]
				{ request.getClass(), response.getClass() });
			}

		} catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	// 查询
	public void list(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 查询
		PaginationObj domainPagination = getDomainDao().searchPaginationByDomainInstance(domainInstance, domainInstance.findDefaultOrderBy(), domainSearchCondition.getPageNo(), domainSearchCondition.getPageCount());

		request.setAttribute(DOMAIN_INSTANCE, domainInstance);
		request.setAttribute(DOMAIN_PAGINATION, domainPagination);

		// 转向
		if ("true".equalsIgnoreCase(request.getParameter("4m")))
		{
			request.getRequestDispatcher("/" + getBasePath() + "/" + BASE_METHOD_LIST + "4m.jsp").forward(request, response);
		} else
		{
			request.getRequestDispatcher("/" + getBasePath() + "/" + BASE_METHOD_LIST + ".jsp").forward(request, response);
		}
	}

	// 检查ID是否已录入系统
	public void checkId(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PaginationObj pagination = getDomainDao().searchPaginationByDomainInstance(domainInstance, null, 1, 1);
		if (pagination.getDataList().size() > 0)
		{
			response.getWriter().write("true");
			response.flushBuffer();
		}
	}

	// 增加 or 修改
	public void addOrModify(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 保存或更新
		if (domainInstance.getKeyValue() < 0)
		{
			long keyValue = getDomainDao().getMaxKeyValue() + 1;
			domainInstance.setId(keyValue);
		}

		boolean success = getDomainDao().saveOrUpdate(domainInstance);
		
		// 声明是新增后转到的详情页面
		request.setAttribute("needRefresh", true);

		// 转向
		if (success)
		{
			detail(request, response);
			// list(request, response);
		} else
		{
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	// 修改前查询领域对象信息
	public void preModify(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 查询
		Object domainObj = getDomainDao().searchByKey(getDomainInstanceClz(), domainInstance.getKeyValue() + "");

		if (domainObj != null)
		{
			request.setAttribute(DOMAIN_INSTANCE, domainObj);
		}

		// 转向
		if ("true".equalsIgnoreCase(request.getParameter("4m")))
		{
			request.getRequestDispatcher("/" + getBasePath() + "/" + BASE_METHOD_ADD_OR_MODIFY + "4m.jsp").forward(request, response);
		} else
		{
			request.getRequestDispatcher("/" + getBasePath() + "/" + BASE_METHOD_ADD_OR_MODIFY + ".jsp").forward(request, response);
		}
	}

	// 详情
	public void detail(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 查询
		Object domainObj = getDomainDao().searchByKey(getDomainInstanceClz(), domainInstance.getKeyValue() + "");

		if (domainObj != null)
		{
			request.setAttribute(DOMAIN_INSTANCE, domainObj);
		}

		// 转向
		if ("true".equalsIgnoreCase(request.getParameter("4m")))
		{
			request.getRequestDispatcher("/" + getBasePath() + "/" + BASE_METHOD_DETAIL + "4m.jsp").forward(request, response);
		} else
		{
			request.getRequestDispatcher("/" + getBasePath() + "/" + BASE_METHOD_DETAIL + ".jsp").forward(request, response);
		}
	}

	// 删除
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int successCount = getDomainDao().deleteByKey(domainInstanceKeyValue);

		// 转向
		if (successCount > 0)
		{
			list(request, response);
		} else
		{
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	// 导出
	public void export(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 导出时不限条数，放到最大值
		PaginationObj domainPagination = getDomainDao().searchPaginationByDomainInstance(domainInstance, domainInstance.findDefaultOrderBy(), 1, Integer.MAX_VALUE);

		RequestUtil.downloadFile(this, response, domainPagination.getDataList(), domainInstance.getProperties(), "明细");
	}

	// 解析前台传递过来的数据
	private void parseParameters(HttpServletRequest request, String method) throws Exception
	{
		// 参数是否是通过url传递过来的
		boolean isFromUrl = "true".equalsIgnoreCase(request.getParameter("isFromUrl"));

		// 获取领域对象基本信息
		domainInstance = (BaseDbObj) MyBeanUtils.createInstanceFromHttpRequest(request, getDomainInstanceClz(), isFromUrl);

		// 获取领域对象主键值
		domainInstanceKeyValue = request.getParameter(domainInstance.findKeyColumnName());
		if (BASE_METHOD_DELETE.equalsIgnoreCase(method) || StringUtil.isEmpty(domainInstanceKeyValue))
		{
			domainInstanceKeyValue = request.getParameter(domainInstance.findKeyColumnName() + "_4del");
		}

		// 获取领域对象查询查询条件
		domainSearchCondition = (BaseSearchCondition) MyBeanUtils.createInstanceFromHttpRequest(request, getDomainSearchConditionClz(), isFromUrl);

		// 设置查询条件的业务对象
		domainSearchCondition.setDomainObj(domainInstance);

		// 设置当前账号
		domainSearchCondition.setUser(currentUser);
	}

	private String getParameterFromUrl(HttpServletRequest request, String parameterName) throws UnsupportedEncodingException
	{
		String pValueISO88591 = request.getParameter(parameterName);
		String pValueUtf8 = new String(StringUtil.getNotEmptyStr(pValueISO88591).getBytes("iso-8859-1"), "utf-8");
		return pValueUtf8;
	}
}
