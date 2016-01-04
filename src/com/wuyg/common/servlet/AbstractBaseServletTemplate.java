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

	// ��ǰ��½�û�
	public AuthUser currentUser;

	// ����ʵ������ֵ
	public String domainInstanceKeyValue;
	// ����ʵ��
	public BaseDbObj domainInstance;
	// ��ѯ����
	public BaseSearchCondition domainSearchCondition;

	// �鷽������ȡ����DAO�����弯����ʵ��
	public abstract IBaseDAO getDomainDao();

	// �鷽������ȡ��������࣬���弯����ʵ��
	public abstract Class getDomainInstanceClz();

	// �鷽������ȡ�����ѯ�����࣬���弯����ʵ��
	public abstract Class getDomainSearchConditionClz();

	// �鷽������ȡ�������·����url��ʹ�ã����弯����ʵ��
	public abstract String getBasePath();

	public static final String METHOD = "method";

	public static final String BASE_METHOD_LIST = "list";// ��ѯ
	public static final String BASE_METHOD_ADD_OR_MODIFY = "addOrModify";// ��ӻ��޸�
	public static final String BASE_METHOD_PRE_MODIFY = "preModify";// �޸�ǰ��ѯ���������Ϣ
	public static final String BASE_METHOD_DETAIL = "detail";// ����
	public static final String BASE_METHOD_DELETE = "delete";// ɾ��
	public static final String BASE_METHOD_EXPORT = "export";// ����
	public static final String BASE_METHOD_CHECK_ID = "checkId";// ����

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
			// ��ȡ��ǰ�û���Ϣ
			currentUser = (AuthUser) request.getSession().getAttribute(SystemConstant.AUTH_USER_INFO);

			// ��ȡִ�еķ���
			String method = request.getParameter(METHOD);

			// ��ҳ�洫�ݹ����Ĳ��������ȡ������Ϣ
			parseParameters(request, method);

			// ִ����ط���
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
				// ִ����Ӧ���������˻�������ɾ�Ĳ鷽�����⣬��������Ҫ�ڼ̳и���ľ�������ʵ��
				MethodUtils.invokeMethod(this, method, new Object[]
				{ request, response }, new Class[]
				{ request.getClass(), response.getClass() });
			}

		} catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}

	// ��ѯ
	public void list(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// ��ѯ
		PaginationObj domainPagination = getDomainDao().searchPaginationByDomainInstance(domainInstance, domainInstance.findDefaultOrderBy(), domainSearchCondition.getPageNo(), domainSearchCondition.getPageCount());

		request.setAttribute(DOMAIN_INSTANCE, domainInstance);
		request.setAttribute(DOMAIN_PAGINATION, domainPagination);

		// ת��
		if ("true".equalsIgnoreCase(request.getParameter("4m")))
		{
			request.getRequestDispatcher("/" + getBasePath() + "/" + BASE_METHOD_LIST + "4m.jsp").forward(request, response);
		} else
		{
			request.getRequestDispatcher("/" + getBasePath() + "/" + BASE_METHOD_LIST + ".jsp").forward(request, response);
		}
	}

	// ���ID�Ƿ���¼��ϵͳ
	public void checkId(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PaginationObj pagination = getDomainDao().searchPaginationByDomainInstance(domainInstance, null, 1, 1);
		if (pagination.getDataList().size() > 0)
		{
			response.getWriter().write("true");
			response.flushBuffer();
		}
	}

	// ���� or �޸�
	public void addOrModify(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// ��������
		if (domainInstance.getKeyValue() < 0)
		{
			long keyValue = getDomainDao().getMaxKeyValue() + 1;
			domainInstance.setId(keyValue);
		}

		boolean success = getDomainDao().saveOrUpdate(domainInstance);
		
		// ������������ת��������ҳ��
		request.setAttribute("needRefresh", true);

		// ת��
		if (success)
		{
			detail(request, response);
			// list(request, response);
		} else
		{
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	// �޸�ǰ��ѯ���������Ϣ
	public void preModify(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// ��ѯ
		Object domainObj = getDomainDao().searchByKey(getDomainInstanceClz(), domainInstance.getKeyValue() + "");

		if (domainObj != null)
		{
			request.setAttribute(DOMAIN_INSTANCE, domainObj);
		}

		// ת��
		if ("true".equalsIgnoreCase(request.getParameter("4m")))
		{
			request.getRequestDispatcher("/" + getBasePath() + "/" + BASE_METHOD_ADD_OR_MODIFY + "4m.jsp").forward(request, response);
		} else
		{
			request.getRequestDispatcher("/" + getBasePath() + "/" + BASE_METHOD_ADD_OR_MODIFY + ".jsp").forward(request, response);
		}
	}

	// ����
	public void detail(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// ��ѯ
		Object domainObj = getDomainDao().searchByKey(getDomainInstanceClz(), domainInstance.getKeyValue() + "");

		if (domainObj != null)
		{
			request.setAttribute(DOMAIN_INSTANCE, domainObj);
		}

		// ת��
		if ("true".equalsIgnoreCase(request.getParameter("4m")))
		{
			request.getRequestDispatcher("/" + getBasePath() + "/" + BASE_METHOD_DETAIL + "4m.jsp").forward(request, response);
		} else
		{
			request.getRequestDispatcher("/" + getBasePath() + "/" + BASE_METHOD_DETAIL + ".jsp").forward(request, response);
		}
	}

	// ɾ��
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int successCount = getDomainDao().deleteByKey(domainInstanceKeyValue);

		// ת��
		if (successCount > 0)
		{
			list(request, response);
		} else
		{
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	// ����
	public void export(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// ����ʱ�����������ŵ����ֵ
		PaginationObj domainPagination = getDomainDao().searchPaginationByDomainInstance(domainInstance, domainInstance.findDefaultOrderBy(), 1, Integer.MAX_VALUE);

		RequestUtil.downloadFile(this, response, domainPagination.getDataList(), domainInstance.getProperties(), "��ϸ");
	}

	// ����ǰ̨���ݹ���������
	private void parseParameters(HttpServletRequest request, String method) throws Exception
	{
		// �����Ƿ���ͨ��url���ݹ�����
		boolean isFromUrl = "true".equalsIgnoreCase(request.getParameter("isFromUrl"));

		// ��ȡ������������Ϣ
		domainInstance = (BaseDbObj) MyBeanUtils.createInstanceFromHttpRequest(request, getDomainInstanceClz(), isFromUrl);

		// ��ȡ�����������ֵ
		domainInstanceKeyValue = request.getParameter(domainInstance.findKeyColumnName());
		if (BASE_METHOD_DELETE.equalsIgnoreCase(method) || StringUtil.isEmpty(domainInstanceKeyValue))
		{
			domainInstanceKeyValue = request.getParameter(domainInstance.findKeyColumnName() + "_4del");
		}

		// ��ȡ��������ѯ��ѯ����
		domainSearchCondition = (BaseSearchCondition) MyBeanUtils.createInstanceFromHttpRequest(request, getDomainSearchConditionClz(), isFromUrl);

		// ���ò�ѯ������ҵ�����
		domainSearchCondition.setDomainObj(domainInstance);

		// ���õ�ǰ�˺�
		domainSearchCondition.setUser(currentUser);
	}

	private String getParameterFromUrl(HttpServletRequest request, String parameterName) throws UnsupportedEncodingException
	{
		String pValueISO88591 = request.getParameter(parameterName);
		String pValueUtf8 = new String(StringUtil.getNotEmptyStr(pValueISO88591).getBytes("iso-8859-1"), "utf-8");
		return pValueUtf8;
	}
}
