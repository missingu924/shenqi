package com.hz.customer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hz.customer.obj.MyCustomerDbObj;
import com.hz.customer.obj.OtherCustomerDbObj;
import com.hz.customer.obj.ReservedTelNumDbObj;
import com.hz.customer.obj.ReservedTelNumDbObjExtend;
import com.hz.customer.obj.UsableTelNumDbObj;
import com.hz.customer.service.CustomerService;
import com.hz.customer.service.ICustomerService;
import com.wuyg.common.dao.DefaultBaseDAO;
import com.wuyg.common.dao.IBaseDAO;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.common.util.MyBeanUtils;
import com.wuyg.common.util.RequestUtil;
import com.wuyg.common.util.StringUtil;
import com.wuyg.common.util.TimeUtil;
import com.wuyg.excel.ExcelConstructor;
import com.wuyg.task.dao.TaskStatMySqlDAO;
import com.wuyg.task.obj.TaskMainInfoDbObj;
import com.wuyg.task.obj.TaskStatDbObj;

public class CustomerServlet extends HttpServlet
{
	private Logger logger = Logger.getLogger(getClass());
	private IBaseDAO myCustomerBaseDAO = new DefaultBaseDAO(new MyCustomerDbObj());
	private IBaseDAO otherCustomerBaseDAO = new DefaultBaseDAO(new OtherCustomerDbObj());
	private ICustomerService customerService = new CustomerService();

	/**
	 * Constructor of the object.
	 */
	public CustomerServlet()
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
		response.setCharacterEncoding("utf-8");

		try
		{
			// 获取执行的方法
			String method = request.getParameter("method");

			if ("getCustomerInfo".equals(method))
			{
				getCustomerInfo(request, response);
			} else if ("getUsableTelNums".equals(method))
			{
				getUsableTelNums(request, response);
			} else if ("allUsableTelNumList".equals(method))
			{
				allUsableTelNumList(request, response);
			} else if ("allReserveTelNumList".equals(method))
			{
				allReserveTelNumList(request, response);
			} else if ("releasedTelNums4BossList".equals(method))
			{
				releasedTelNums4BossList(request, response);
			}
			else if ("reservedTelNums4BossList".equals(method))
			{
				reservedTelNums4BossList(request, response);
			}
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	private void reservedTelNums4BossList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int pageNo = RequestUtil.getPageNo(request);
		int pageCount = RequestUtil.getPageCount(request);

		// 是否导出
		boolean export = "true".equalsIgnoreCase(request.getParameter("export"));
		if (export)
		{
			pageCount = Integer.MAX_VALUE;
		}

		// 查询条件
		String reserveTelNum = RequestUtil.getParameterAndSetAttribute(request, "reserveTelNum");
		String otherTelNum = RequestUtil.getParameterAndSetAttribute(request, "otherTelNum");

		// 查询数据
		PaginationObj pagination = customerService.searchReservedTelNums4Boss(reserveTelNum, otherTelNum, pageNo, pageCount);

		// 导出或展现
		if (export)
		{
			RequestUtil.downloadFile(this, response, pagination.getDataList(), new ReservedTelNumDbObjExtend().getProperties(), "调拨号码表");
		} else
		{
			request.setAttribute("pagination", pagination);
			request.getRequestDispatcher("/Customer/reservedTelNums4BossList.jsp").forward(request, response);
		}
	}

	private void releasedTelNums4BossList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int pageNo = RequestUtil.getPageNo(request);
		int pageCount = RequestUtil.getPageCount(request);

		// 是否导出
		boolean export = "true".equalsIgnoreCase(request.getParameter("export"));
		if (export)
		{
			pageCount = Integer.MAX_VALUE;
		}

		// 查询条件
		String reserveTelNum = RequestUtil.getParameterAndSetAttribute(request, "reserveTelNum");
		String otherTelNum = RequestUtil.getParameterAndSetAttribute(request, "otherTelNum");

		// 查询数据
		PaginationObj pagination = customerService.searchReleasedTelNums4Boss(reserveTelNum, otherTelNum, pageNo, pageCount);

		// 导出或展现
		if (export)
		{
			RequestUtil.downloadFile(this, response, pagination.getDataList(), new ReservedTelNumDbObjExtend().getProperties(), "取消调拨号码表");
		} else
		{
			request.setAttribute("pagination", pagination);
			request.getRequestDispatcher("/Customer/releasedTelNums4BossList.jsp").forward(request, response);
		}
	}

	private void allUsableTelNumList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int pageNo = RequestUtil.getPageNo(request);
		int pageCount = RequestUtil.getPageCount(request);

		// 是否导出
		boolean export = "true".equalsIgnoreCase(request.getParameter("export"));
		if (export)
		{
			pageCount = Integer.MAX_VALUE;
		}

		// 查询条件
		String telNum = RequestUtil.getParameterAndSetAttribute(request, "telNum");
		boolean exceptReversedTelNum = "true".equalsIgnoreCase(RequestUtil.getParameterAndSetAttribute(request, "exceptReversedTelNum"));

		// 查询数据
		PaginationObj pagination = customerService.searchAllUsableTelNumPagintion(exceptReversedTelNum, telNum, pageNo, pageCount);

		// 导出或展现
		if (export)
		{
			RequestUtil.downloadFile(this, response, pagination.getDataList(), new UsableTelNumDbObj().getProperties(), "可用号码表数据");
		} else
		{
			request.setAttribute("pagination", pagination);
			request.getRequestDispatcher("/Customer/allUsableTelNumList.jsp").forward(request, response);
		}

	}

	private void allReserveTelNumList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int pageNo = RequestUtil.getPageNo(request);
		int pageCount = RequestUtil.getPageCount(request);

		// 是否导出
		boolean export = "true".equalsIgnoreCase(request.getParameter("export"));
		if (export)
		{
			pageCount = Integer.MAX_VALUE;
		}

		// 查询条件
		String startTime = RequestUtil.getParameterAndSetAttribute(request, "startTime");
		String endTime = RequestUtil.getParameterAndSetAttribute(request, "endTime");
		String reserveTelNum = RequestUtil.getParameterAndSetAttribute(request, "reserveTelNum");
		String otherTelNum = RequestUtil.getParameterAndSetAttribute(request, "otherTelNum");
		String taskId = RequestUtil.getParameterAndSetAttribute(request, "taskId");

		// 查询数据
		PaginationObj pagination = customerService.searchReservedTelNumPagintion(startTime, endTime, reserveTelNum, otherTelNum, taskId, pageNo, pageCount);

		// 导出或展现
		if (export)
		{
			RequestUtil.downloadFile(this, response, pagination.getDataList(), new ReservedTelNumDbObjExtend().getProperties(), "预约号码数据");
		} else
		{
			request.setAttribute("pagination", pagination);
			request.getRequestDispatcher("/Customer/allReserveTelNumList.jsp").forward(request, response);
		}

	}

	private void getUsableTelNums(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String otherTelNum = request.getParameter("otherTelNum");

		List<UsableTelNumDbObj> usableTelNums = customerService.getUsableTelNum(otherTelNum);

		request.setAttribute("usableTelNums", usableTelNums);

		request.getRequestDispatcher("/Customer/usableTelNumSelect.jsp").forward(request, response);
	}

	private void getCustomerInfo(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String myCustomerTelNum = request.getParameter("myCustomerTelNum");
		String otherCustomerTelNum = request.getParameter("otherCustomerTelNum");

		String notice = "<font color=\"red\">没有检索到该号码的附加信息。</font><input type=\"hidden\" id=\"customerInfo\" name=\"customerInfo\" value=\"没有检索到该号码的附加信息。\"/>";

		if (!StringUtil.isEmpty(myCustomerTelNum))
		{
			Object o = myCustomerBaseDAO.searchByKey(MyCustomerDbObj.class, myCustomerTelNum);
			if (o == null)
			{
				response.getWriter().print(notice);
			} else
			{
				MyCustomerDbObj customer = (MyCustomerDbObj) o;
				response.getWriter().print(customer.toHtml(null));
			}
		} else if (!StringUtil.isEmpty(otherCustomerTelNum))
		{
			Object o = otherCustomerBaseDAO.searchByKey(OtherCustomerDbObj.class, otherCustomerTelNum);
			if (o == null)
			{
				response.getWriter().print(notice);
			} else
			{
				OtherCustomerDbObj customer = (OtherCustomerDbObj) o;
				response.getWriter().print(customer.toHtml(null));
			}
		}
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
