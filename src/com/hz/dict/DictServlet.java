package com.hz.dict;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hz.util.SystemConstant;
import com.inspur.common.dictionary.Dictionary;
import com.inspur.common.dictionary.pojo.DictItem;
import com.wuyg.common.util.MySqlUtil;

public class DictServlet extends HttpServlet
{
	private String dictName;
	private String fromDictName;
	private String toDictName;
	private String fromDictKey;
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * Constructor of the object.
	 */
	public DictServlet()
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
		String method = request.getParameter("method");

		String dictName = request.getParameter("dictName");
		String fromDictName = request.getParameter("fromDictName");
		String toDictName = request.getParameter("toDictName");
		String fromDictKey = request.getParameter("fromDictKey");

		Connection conn = null;
		try
		{
			conn = MySqlUtil.getConnection(SystemConstant.DEFAULT_DB);
			Dictionary dictionary = new Dictionary();
			List<DictItem> items = new ArrayList<DictItem>();

			if ("getDict".equalsIgnoreCase(method))
			{
				items = dictionary.getDictItemsByDictName(dictName, true, conn);
			} else if ("getSubDict".equalsIgnoreCase(method))
			{
				items = dictionary.getDictItemsFromTo(fromDictName, toDictName, fromDictKey, true, conn);
			}

			request.setAttribute("items", items);

		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
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
