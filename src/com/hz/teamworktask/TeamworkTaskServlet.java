package com.hz.teamworktask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.hz.auth.obj.AuthUser;
import com.hz.teamworktask.obj.TeamworkTaskDbObj;
import com.hz.util.SystemConstant;
import com.wuyg.common.obj.PaginationObj;
import com.wuyg.common.util.MyBeanUtils;
import com.wuyg.common.util.RequestUtil;
import com.wuyg.common.util.StringUtil;
import com.wuyg.common.util.TimeUtil;
import com.wuyg.excel.ExcelConstructor;
import com.wuyg.task.TaskConstant;
import com.wuyg.task.dao.TaskStatMySqlDAO;
import com.wuyg.task.obj.TaskCustomizableColumnDbObj;
import com.wuyg.task.obj.TaskCustomizableColumns;
import com.wuyg.task.obj.TaskMainInfoDbObj;
import com.wuyg.task.obj.TaskObj;
import com.wuyg.task.obj.TaskProcessSubject;
import com.wuyg.task.obj.TaskSearchComplexCondition;
import com.wuyg.task.obj.TaskStatDbObj;
import com.wuyg.task.obj.TaskStatSearchCondition;
import com.wuyg.task.service.ITaskService;
import com.wuyg.task.service.TaskService;

public class TeamworkTaskServlet extends HttpServlet
{
	private Logger logger = Logger.getLogger(getClass());
	private TaskMainInfoDbObj taskMainInfo;
	private TeamworkTaskDbObj teamworkTask;
	private TaskObj taskObj;
	private ITaskService taskService = new TaskService();
	private AuthUser currentUser;
	private long taskId;
	private TaskSearchComplexCondition taskSearchCondition;
	private TaskStatSearchCondition taskStatSearchCondition;

	/**
	 * Constructor of the object.
	 */
	public TeamworkTaskServlet()
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
		try
		{
			// ��ȡ��ǰ�û���Ϣ
			currentUser = (AuthUser) request.getSession().getAttribute(SystemConstant.AUTH_USER_INFO);

			// ��ȡִ�еķ���
			String method = request.getParameter("method");

			// ��ȡ���ݹ����Ĺ������
			String taskIdStr = request.getParameter("taskId");
			if (!StringUtil.isEmpty(taskIdStr))
			{
				taskId = Long.parseLong(taskIdStr);
			} else
			{
				taskId = 0;
			}

			// ��ҳ�洫�ݹ����Ĳ��������ȡ������Ϣ
			parseParameters(request);

			if (method.equals("taskCreateOrModify"))
			{
				taskCreateOrModify(request, response);
			} else if (method.equals("taskAudit"))
			{
				taskAudit(request, response);
			} else if (method.equals("taskFeedback"))
			{
				taskFeedback(request, response);
			} else if (method.equals("taskForward"))
			{
				taskForward(request, response);
			} else if (method.equals("taskRevert"))
			{
				taskRevert(request, response);
			} else if (method.equals("taskArchiveVerify"))
			{
				taskArchiveVerify(request, response);
			} else if (method.equals("searchByCondition"))
			{
				searchByCondition(request, response);
			} else if (method.equals("exportTaskList"))
			{
				exportTaskList(request, response);
			} else if (method.equals("taskCustColAddOrModify"))
			{
				taskCustColAddOrModify(request, response);
			} else if (method.equals("taskCustColDelete"))
			{
				taskCustColDelete(request, response);
			} else if (method.equals("taskCustColList"))
			{
				taskCustColList(request, response);
			} else if (method.equals("taskCustColDetail"))
			{
				taskCustColDetail(request, response);
			} else if (method.equals("getTaskCustColsInputHtml"))
			{
				getTaskCustColsInputHtml(request, response);
			} else if (method.equals("searchTaskStatByCondition"))
			{
				searchTaskStatByCondition(request, response);
			} else if (method.equals("exportTaskStatList"))
			{
				exportTaskStatList(request, response);
			} else if (method.equals("uploadFile"))
			{
				uploadFile(request, response);
			} else if (method.equals("getAttachment"))
			{
				getAttachment(request, response);
			}

		} catch (Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}

	// ��ѯͳ����Ϣ
	private void searchTaskStatByCondition(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PaginationObj pagination = new TaskStatMySqlDAO().searchPaginationByClause(taskStatSearchCondition);

		request.setAttribute("taskStatSearchCondition", taskStatSearchCondition);

		request.setAttribute("pagination", pagination);

		request.getRequestDispatcher("/TeamworkTask/taskStatList.jsp").forward(request, response);
	}

	// ������ѯ�󵼳�
	private void exportTaskStatList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// ����ʱ�����������ŵ����ֵ
		taskStatSearchCondition.setPageNo(1);
		taskStatSearchCondition.setPageCount(Integer.MAX_VALUE);

		PaginationObj taskPagination = new TaskStatMySqlDAO().searchPaginationByClause(taskStatSearchCondition);

		RequestUtil.downloadFile(this, response, taskPagination.getDataList(), new TaskStatDbObj().getProperties(), "����ͳ��");
	}

	private void getAttachment(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String attachment = getParameterFromUrl(request, "attachment");

		String filePath = this.getServletContext().getRealPath("/upload") + "/" + attachment;

		RequestUtil.downloadFile(response, filePath);

	}

	// �ϴ��ļ�
	public void uploadFile(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String baseDir = this.getServletConfig().getServletContext().getRealPath("/upload/");

		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = upload.parseRequest(request);

		String name = "";

		for (int i = 0; i < fileList.size(); i++)
		{
			FileItem item = fileList.get(i);
			if (!item.isFormField())
			{
				name = item.getName();
				if (name == null || name.trim().equals(""))
				{
					continue;
				}

				String fileName = TimeUtil.nowTime2str("yyyyMMddHHmmss_") + name;

				File saveFile = new File(baseDir + "/" + fileName);
				if (!saveFile.getParentFile().exists())
				{
					saveFile.getParentFile().mkdirs();
				}
				item.write(saveFile);

				response.getWriter().print(URLEncoder.encode(fileName, "utf-8"));
				break;
			}
		}
	}

	// ������ѯ
	private void searchByCondition(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// ��ѯ
		PaginationObj taskPagination = taskService.searchTaskPaginationByClause(taskSearchCondition);

		request.setAttribute("searchCondition", taskSearchCondition);
		request.setAttribute("taskPagination", taskPagination);

		// ת��
		request.getRequestDispatcher("/TeamworkTask/search.jsp").forward(request, response);
	}

	// ������ѯ�󵼳�
	private void exportTaskList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// ����ʱ�����������ŵ����ֵ
		taskSearchCondition.setPageNo(1);
		taskSearchCondition.setPageCount(Integer.MAX_VALUE);

		PaginationObj taskPagination = taskService.searchTaskPaginationByClause(taskSearchCondition);

		RequestUtil.downloadFile(this,response, taskPagination.getDataList(), taskMainInfo.getProperties(),"������ϸ");
	}

	// �׶η���
	private void taskFeedback(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// ��ȡ�׶η�����Ϣ
		String information = request.getParameter("information");

		// ��ȡ���ƻ��ֶ�����
		String custColValues = getCustColValues(request, taskMainInfo.getTaskType(), taskMainInfo.getTaskSubType(), TaskConstant.TP_FEEDBACK);

		// ���϶��ƻ��ֶ�����
		information = (!StringUtil.isEmpty(custColValues) ? (custColValues + "��") : ("")) + information;

		// ������Ϣ����
		taskService.taskFeedback(taskId, currentUser, information);

		// ���taskId��Sesson,ת��Ҫ��redirect,��ֹ�ظ��ύ
		request.getSession().setAttribute("taskId", taskId);
		response.sendRedirect(request.getContextPath() + "/TeamworkTask/taskDetail.jsp");
	}

	// �鵵���
	private void taskArchiveVerify(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// ��ȡ������Ϣ
		String agreed = request.getParameter("agreed");
		String information = request.getParameter("information");

		// ������ת
		taskService.taskArchiveVerify(taskId, currentUser, TaskConstant.TP_ARCHIVE.equals(agreed) ? true : false, information);

		// ���taskId��Sesson,ת��Ҫ��redirect,��ֹ�ظ��ύ
		request.getSession().setAttribute("taskId", taskId);
		response.sendRedirect(request.getContextPath() + "/TeamworkTask/taskDetail.jsp");
	}

	// �����ظ�
	private void taskRevert(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// ��ȡ������Ϣ
		String processResult = request.getParameter("processResult");
		String information = request.getParameter("information");
		String attachment = request.getParameter("attachment");

		// ��ȡ���ƻ��ֶ�����
		String custColValues = getCustColValues(request, taskMainInfo.getTaskType(), taskMainInfo.getTaskSubType(), TaskConstant.TP_REVERT);

		// ���϶��ƻ��ֶ�����
		information = (!StringUtil.isEmpty(custColValues) ? (custColValues + "��") : ("")) + information;

		// ������ת
		taskService.taskRevert(taskId, currentUser, processResult, information, attachment);

		// ���taskId��Sesson,ת��Ҫ��redirect,��ֹ�ظ��ύ
		request.getSession().setAttribute("taskId", taskId);
		response.sendRedirect(request.getContextPath() + "/TeamworkTask/taskDetail.jsp");
	}

	// ����ת��
	private void taskForward(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// ��ȡ������Ϣ
		String information = request.getParameter("information");
		TaskProcessSubject nextProcessSubject = getSubjectFromRequest(request, "forward");

		// ��ȡ���ƻ��ֶ�����
		String custColValues = getCustColValues(request, taskMainInfo.getTaskType(), taskMainInfo.getTaskSubType(), TaskConstant.TP_FORWRD);

		// ���϶��ƻ��ֶ�����
		information = (!StringUtil.isEmpty(custColValues) ? (custColValues + "��") : ("")) + information;

		// ������ת
		taskService.taskForward(taskId, currentUser, nextProcessSubject, information);

		// ���taskId��Sesson,ת��Ҫ��redirect,��ֹ�ظ��ύ
		request.getSession().setAttribute("taskId", taskId);
		response.sendRedirect(request.getContextPath() + "/TeamworkTask/taskDetail.jsp");
	}

	private TaskProcessSubject getSubjectFromRequest(HttpServletRequest request, String prefix)
	{
		String subjectName = request.getParameter(prefix + "SubjectName");
		String subjectType = request.getParameter(prefix + "SubjectType");
		String subjectId = request.getParameter(prefix + "SubjectId");
		String subjectDepartmentId = request.getParameter(prefix + "SubjectDepartmentId");
		String subjectDepartmentName = request.getParameter(prefix + "SubjectDepartmentName");
		String subjectDistrict = request.getParameter(prefix + "SubjectDistrict");

		TaskProcessSubject subject = new TaskProcessSubject();
		subject.setSubjectDepartmentId(subjectDepartmentId);
		subject.setSubjectDepartmentName(subjectDepartmentName);
		subject.setSubjectId(subjectId);
		subject.setSubjectName(subjectName);
		subject.setSubjectType(subjectType);
		subject.setSubjectDistrict(subjectDistrict);

		return subject;
	}

	// �ɵ�����
	private void taskAudit(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		// ��ȡ������Ϣ
		String agreed = request.getParameter("agreed");
		String information = request.getParameter("information");

		// ������ת
		taskService.taskAudit(taskId, currentUser, TaskConstant.RESULT_AGREED.equals(agreed) ? true : false, information);

		// ���taskId��Sesson,ת��Ҫ��redirect,��ֹ�ظ��ύ
		request.getSession().setAttribute("taskId", taskId);
		response.sendRedirect(request.getContextPath() + "/TeamworkTask/taskDetail.jsp");
	}

	// �������޸Ĺ���
	private void taskCreateOrModify(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// �ж��Ǳ��滹���ύ
		String saveOrSubmit = request.getParameter("saveOrSubmit");

		// ��ȡ���ƻ��ֶ�����
		String custColValues = getCustColValues(request, taskMainInfo.getTaskType(), taskMainInfo.getTaskSubType(), TaskConstant.TP_SUBMIT);
		if (!StringUtil.isEmpty(custColValues))// ��Ϊ�յ�ʱ������ã����⸲�ǵ�ԭ����ֵ
		{
			taskMainInfo.setCustColValues(custColValues);
		}

		// �ж����½������޸�
		if (taskId > 0)
		{
			// �޸Ĺ���
			taskService.taskModify(taskObj, "submit".equalsIgnoreCase(saveOrSubmit) ? true : false);
		} else
		{
			// ��������
			taskObj.getTaskMainInfo().setCreateTime(new Timestamp(System.currentTimeMillis()));
			taskId = taskService.taskCreate(taskObj, "submit".equalsIgnoreCase(saveOrSubmit) ? true : false);
			logger.info("���������ɹ����������Ϊ:" + taskId);
		}

		// ���taskId��Sesson,ת��Ҫ��redirect,��ֹ�ظ��ύ
		request.getSession().setAttribute("taskId", taskId);
		response.sendRedirect(request.getContextPath() + "/TeamworkTask/taskDetail.jsp");
	}

	// ������޸Ĺ������ƻ��ֶ�
	private void taskCustColAddOrModify(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String taskType = request.getParameter("taskType");
		String taskSubType = request.getParameter("taskSubType");
		String taskProcessName = request.getParameter("taskProcessName");

		TaskCustomizableColumns taskCustomizableColumns = new TaskCustomizableColumns();
		taskCustomizableColumns.setTaskType(taskType);
		taskCustomizableColumns.setTaskSubType(taskSubType);
		taskCustomizableColumns.setTaskProcessName(taskProcessName);

		for (int i = 0; i < TaskConstant.MAX_CUST_COL_COUNT; i++)
		{
			String columnId = "customizableColumn" + i;
			String columnName = request.getParameter(columnId);
			String notEmpty = request.getParameter("notEmpty" + i);
			String selectValues = request.getParameter("selectValues" + i);
			String jsFunction = request.getParameter("jsFunction" + i);

			if (!StringUtil.isEmpty(columnName))
			{
				TaskCustomizableColumnDbObj column = new TaskCustomizableColumnDbObj(taskType, taskSubType, taskProcessName, columnId, columnName, notEmpty,
						selectValues, jsFunction);
				taskCustomizableColumns.addColumn(column);
			}
		}

		// �������
		boolean success = taskService.saveOrUpdateCustomizableColumns(taskCustomizableColumns);

		if (success)
		{
			request.setAttribute("taskCustomizableColumns", taskCustomizableColumns);
			request.getRequestDispatcher("/TeamworkTask/taskCustColDetail.jsp").forward(request, response);
		} else
		{
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}
	}

	// �������ƻ��ֶ��б�
	private void taskCustColList(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		List<TaskCustomizableColumns> taskCustColList = taskService.searchTaskCustomizableColumns();

		request.setAttribute("taskCustColList", taskCustColList);
		request.getRequestDispatcher("/TeamworkTask/taskCustColList.jsp").forward(request, response);
	}

	// �������ƻ��ֶ�ɾ��
	private void taskCustColDelete(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String taskType = getParameterFromUrl(request, "taskType");
		String taskSubType = getParameterFromUrl(request, "taskSubType");

		taskService.deleteTaskCustomizableColumns(taskType, taskSubType);

		taskCustColList(request, response);
	}

	// �������ƻ��ֶ���ϸ
	private void taskCustColDetail(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// �������� �� ����С�࣬ע��ת�룬��Ϊͨ���˵�URLֱ�Ӵ��������ģ�����Ҫ����ת��
		String taskType = getParameterFromUrl(request, "taskType");
		String taskSubType = getParameterFromUrl(request, "taskSubType");
		String taskProcessName = getParameterFromUrl(request, "taskProcessName");

		TaskCustomizableColumns taskCustomizableColumns = taskService.getTaskCustomizableColumns(taskType, taskSubType, taskProcessName);

		request.setAttribute("taskCustomizableColumns", taskCustomizableColumns);

		request.getRequestDispatcher("/TeamworkTask/taskCustColDetail.jsp").forward(request, response);
	}

	private String getParameterFromUrl(HttpServletRequest request, String parameterName) throws UnsupportedEncodingException
	{
		String pValueISO88591 = request.getParameter(parameterName);
		String pValueUtf8 = new String(StringUtil.getNotEmptyStr(pValueISO88591).getBytes("iso-8859-1"), "utf-8");
		return pValueUtf8;
	}

	// �������ƻ��ֶ�HTML
	private void getTaskCustColsInputHtml(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String taskType = getParameterFromUrl(request, "taskType");
		String taskSubType = getParameterFromUrl(request, "taskSubType");
		String taskProcessName = getParameterFromUrl(request, "taskProcessName");
		String custColValues = getParameterFromUrl(request, "custColValues");
		String forInput = getParameterFromUrl(request, "forInput");

		TaskCustomizableColumns taskCustomizableColumns = taskService.getTaskCustomizableColumns(taskType, taskSubType, taskProcessName);

		response.setCharacterEncoding("utf-8");
		String inputHtml = taskCustomizableColumns.toHtml(custColValues, "true".equalsIgnoreCase(forInput));
		response.getWriter().print(inputHtml);
	}

	// ����ǰ̨���ݹ���������
	private void parseParameters(HttpServletRequest request) throws Exception
	{
		boolean isFromUrl = "true".equalsIgnoreCase(request.getParameter("isFromUrl"));
		// ��ȡ����������
		taskMainInfo = (TaskMainInfoDbObj) MyBeanUtils.createInstanceFromHttpRequest(request, TaskMainInfoDbObj.class, isFromUrl);

		// ��ȡҵ������
		teamworkTask = (TeamworkTaskDbObj) MyBeanUtils.createInstanceFromHttpRequest(request, TeamworkTaskDbObj.class, isFromUrl);

		// ������������
		taskObj = new TaskObj();
		taskObj.setTaskMainInfo(taskMainInfo);
		taskObj.setBusinessData(teamworkTask);

		// ��ȡ��������Ĳ�ѯ����
		taskSearchCondition = (TaskSearchComplexCondition) MyBeanUtils.createInstanceFromHttpRequest(request, TaskSearchComplexCondition.class, isFromUrl);
		taskSearchCondition.setUser(currentUser);
		taskSearchCondition.setTaskMainInfo(taskMainInfo);
		if (taskSearchCondition.getPageNo() == 0)
		{
			taskSearchCondition.setPageNo(1);
			taskSearchCondition.setPageCount(10);
		}

		// ��ȡ����ͳ�ƵĲ�ѯ����
		taskStatSearchCondition = (TaskStatSearchCondition) MyBeanUtils.createInstanceFromHttpRequest(request, TaskStatSearchCondition.class, isFromUrl);
		taskStatSearchCondition.setUser(currentUser);
		if (taskStatSearchCondition.getPageNo() == 0)
		{
			taskStatSearchCondition.setPageNo(1);
			taskStatSearchCondition.setPageCount(10);
		}
	}

	// ���ݹ������ࡢ����С�ࡢ����������ȡ���ƻ��ֶε���ֵ�Ա�ʾ
	private String getCustColValues(HttpServletRequest request, String taskType, String taskSubType, String processName)
	{
		// colsAndValues��ʽΪ�� key1=value1;key2=value2;key3=value3
		String custColValues = "";
		TaskCustomizableColumns taskCustomizableColumns = taskService.getTaskCustomizableColumns(taskType, taskSubType, processName);
		for (int i = 0; i < taskCustomizableColumns.getColumns().size(); i++)
		{
			TaskCustomizableColumnDbObj custCol = taskCustomizableColumns.getColumns().get(i);
			String columnValue = request.getParameter(custCol.getColumnId());

			custColValues += custCol.getColumnName() + "=" + (StringUtil.getNotEmptyStr(columnValue).replaceAll(";", ",")) + ";";// ��ð�źͷֺ������ķָ���滻Ϊ����
		}
		if (custColValues.length() > 0)
		{
			custColValues = custColValues.substring(0, custColValues.length() - 1);
		}
		return custColValues;
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
