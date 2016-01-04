package com.hz.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hz.dict.service.DictionaryService;
import com.hz.dict.service.IDictionaryService;
import com.inspur.common.dictionary.pojo.DictItem;
import com.inspur.common.dictionary.util.DictionaryUtil;
import com.wuyg.common.util.StringUtil;

public class UtilServlet extends HttpServlet
{
	private Logger logger = Logger.getLogger(getClass());
	private IDictionaryService dict = new DictionaryService();

	/**
	 * Constructor of the object.
	 */
	public UtilServlet()
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
			String method = request.getParameter("method");

			if ("getDictHtml".equals(method))
			{
				getDictHtml(request, response);
			}
		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}

	}

	private void getDictHtml(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String dictName = request.getParameter("dictName");

		String fromDictName = request.getParameter("fromDictName");
		String toDictName = request.getParameter("toDictName");
		String fromDictKeyISO88591 = request.getParameter("fromDictKey");
		String fromDictKey = new String(StringUtil.getNotEmptyStr(fromDictKeyISO88591).getBytes("iso-8859-1"), "utf-8");

		String addItemForAllStr = request.getParameter("addItemForAll");
		boolean addItemForAll = "true".equalsIgnoreCase(addItemForAllStr);

		String selectName = request.getParameter("selectName");
		String selectedItemKeyISO88591 = request.getParameter("selectedItemKey");
		String selectedItemKey = new String(StringUtil.getNotEmptyStr(selectedItemKeyISO88591).getBytes("iso-8859-1"), "utf-8");

		String notEmpty = request.getParameter("notEmpty");

		List<DictItem> dictItems = new ArrayList<DictItem>();
		if (!StringUtil.isEmpty(dictName))
		{
			dictItems = dict.getDictItemsByDictName(dictName, addItemForAll);
		} else if (!StringUtil.isEmpty(fromDictName) && !StringUtil.isEmpty(toDictName))
		{
			dictItems = dict.getDictItemsFromTo(fromDictName, toDictName, fromDictKey, addItemForAll);
		}

		request.setAttribute("dictItems", dictItems);

		response.setCharacterEncoding("utf-8");

		String html = DictionaryUtil.getSelectHtml(dictItems, selectName, selectName, selectedItemKey, notEmpty);

		if (!StringUtil.isEmpty(html))
		{
			html = StringUtil.substr(html, ">", "</select>");
		}

		response.getWriter().print(html);
	}

	public static void main(String[] args)
	{
		String a = "<select><option>a</option></select>";
		System.out.println(StringUtil.substr(a, ">", "</select>"));
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
