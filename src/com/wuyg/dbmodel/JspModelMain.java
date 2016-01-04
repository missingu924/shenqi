package com.wuyg.dbmodel;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import com.fuli.obj.VWelfareDrawStatObj;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.servlet.AbstractBaseServletTemplate;
import com.wuyg.common.util.StringUtil;
import com.wuyg.dbmodel.obj.ColumnObj;
import com.wuyg.dbmodel.obj.TableObj;

public class JspModelMain
{
	public static String srcBaseDir = "C:\\test";
	public static String basePackage = "com.fuli";

	// public static Class domainObjClz = VWelfareDrawStatObj.class;

	public static void main(String[] args) throws Exception
	{
		// System.out.println(VWelfareForDrawDetailObj.class.getCanonicalName());

		// System.exit(1);

		TableObj tableObj = new TableObj();

		generateJsps(tableObj);

		genarateJavaSrc(tableObj);
	}

	public static void genarateJavaSrc(TableObj tableObj) throws Exception
	{
		System.out.println("==" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " begin==");

		generateServletJavaSrc(tableObj);

		generateSearchConditionJavaSrc(tableObj);

		generateWebXml(tableObj);

		System.out.println("==" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " end==\n");

	}

	private static void generateWebXml(TableObj tableObj) throws Exception
	{
		String clzSimpleName = tableObj.getTableClassName();

		System.out.println("--" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " web.xml begin--");
		StringBuffer src = new StringBuffer();

		src.append("	<servlet>\n");
		src.append("		<servlet-name>" + clzSimpleName + "Servlet</servlet-name>\n");
		src.append("		<servlet-class>" + basePackage + "." + clzSimpleName + "Servlet</servlet-class>\n");
		src.append("	</servlet>\n");
		src.append("	<servlet-mapping>\n");
		src.append("		<servlet-name>" + clzSimpleName + "Servlet</servlet-name>\n");
		src.append("		<url-pattern>/" + clzSimpleName + "/Servlet</url-pattern>\n");
		src.append("	</servlet-mapping>\n");

		File srcFile = new File(tableObj.getJavaSrcDir() + "/webxml/" + tableObj.getTableClassName() + "_web.xml");
		if (!srcFile.getParentFile().exists())
		{
			srcFile.getParentFile().mkdirs();
		}
		PrintWriter pw = new PrintWriter(srcFile, "utf-8");
		pw.print(src);
		pw.close();
		System.out.println("--" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " web.xml end--");
	}

	private static void generateSearchConditionJavaSrc(TableObj tableObj) throws Exception
	{
		String clzSimpleName = tableObj.getTableClassName();

		System.out.println("--" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " SearchConditionJavaSrc begin--");
		StringBuffer src = new StringBuffer();
		src.append("package " + basePackage + ".searchcondition;\n");
		src.append("\n");
		src.append("import com.wuyg.common.obj.BaseSearchCondition;\n");
		src.append("\n");
		src.append("public class " + clzSimpleName + "SearchCondition extends BaseSearchCondition\n");
		src.append("{\n");
		src.append("\n");
		src.append("}");

		File srcFile = new File(tableObj.getJavaSrcDir() + "/" + tableObj.getJavaPackage().replace('.', '/') + "/searchcondition/" + StringUtil.toClassName(tableObj.getTableClassName()) + "SearchCondition.java");
		if (!srcFile.getParentFile().exists())
		{
			srcFile.getParentFile().mkdirs();
		}
		PrintWriter pw = new PrintWriter(srcFile, "utf-8");
		pw.print(src);
		pw.close();
		System.out.println("--" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " SearchConditionJavaSrc end--");
	}

	private static void generateServletJavaSrc(TableObj tableObj) throws Exception
	{
		String clzSimpleName = tableObj.getTableClassName();

		System.out.println("--" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " ServletJavaSrc begin--");
		StringBuffer src = new StringBuffer();
		src.append("package " + basePackage + ";\n");
		src.append("\n");
		src.append("import java.util.ArrayList;\n");
		src.append("import java.util.List;\n");
		src.append("\n");
		src.append("import javax.servlet.http.HttpServletRequest;\n");
		src.append("import javax.servlet.http.HttpServletResponse;\n");
		src.append("\n");
		src.append("import org.apache.commons.beanutils.MethodUtils;\n");
		src.append("import org.apache.log4j.Logger;\n");
		src.append("\n");
		src.append("import com.wuyg.common.dao.DefaultBaseDAO;\n");
		src.append("import com.wuyg.common.dao.IBaseDAO;\n");
		src.append("import com.wuyg.common.obj.PaginationObj;\n");
		src.append("import com.wuyg.common.servlet.AbstractBaseServletTemplate;\n");
		src.append("import com.wuyg.common.util.StringUtil;\n");
		src.append("\n");
		src.append("public class " + clzSimpleName + "Servlet extends AbstractBaseServletTemplate\n");
		src.append("{\n");
		src.append("	private Logger logger = Logger.getLogger(getClass());\n");
		src.append("\n");
		src.append("	@Override\n");
		src.append("	public String getBasePath()\n");
		src.append("	{\n");
		src.append("		return domainInstance.getBasePath();\n");
		src.append("	}\n");
		src.append("\n");
		src.append("	@Override\n");
		src.append("	public IBaseDAO getDomainDao()\n");
		src.append("	{\n");
		src.append("		return new DefaultBaseDAO(getDomainInstanceClz());\n");
		src.append("	}\n");
		src.append("\n");
		src.append("	@Override\n");
		src.append("	public Class getDomainInstanceClz()\n");
		src.append("	{\n");
		src.append("		return " + tableObj.getJavaBeanFullName() + ".class;\n");
		src.append("	}\n");
		src.append("\n");
		src.append("	@Override\n");
		src.append("	public Class getDomainSearchConditionClz()\n");
		src.append("	{\n");
		src.append("		return " + tableObj.getSearchconditionBeanFullName() + ".class;\n");
		src.append("	}\n");
		src.append("\n");
		src.append("	// 查询\n");
		src.append("	public void list4this(HttpServletRequest request, HttpServletResponse response) throws Exception\n");
		src.append("	{\n");
		src.append("		super.list(request,response);\n");
		src.append("	}\n");
		src.append("\n");
		src.append("	// 检查ID是否已录入系统\n");
		src.append("	public void checkId4this(HttpServletRequest request, HttpServletResponse response) throws Exception\n");
		src.append("	{\n");
		src.append("		super.checkId(request,response);\n");
		src.append("	}\n");
		src.append("\n");
		src.append("	// 增加 or 修改\n");
		src.append("	public void addOrModify4this(HttpServletRequest request, HttpServletResponse response) throws Exception\n");
		src.append("	{\n");
		src.append("		super.addOrModify(request,response);\n");
		src.append("	}\n");
		src.append("\n");
		src.append("	// 修改前查询领域对象信息\n");
		src.append("	public void preModify4this(HttpServletRequest request, HttpServletResponse response) throws Exception\n");
		src.append("	{\n");
		src.append("		super.preModify(request,response);\n");
		src.append("	}\n");
		src.append("\n");
		src.append("	// 详情\n");
		src.append("	public void detail4this(HttpServletRequest request, HttpServletResponse response) throws Exception\n");
		src.append("	{\n");
		src.append("		super.detail(request,response);\n");
		src.append("	}\n");
		src.append("\n");
		src.append("	// 删除\n");
		src.append("	public void delete4this(HttpServletRequest request, HttpServletResponse response) throws Exception\n");
		src.append("	{\n");
		src.append("		super.delete(request,response);\n");
		src.append("	}\n");
		src.append("\n");
		src.append("	// 导出\n");
		src.append("	public void export4this(HttpServletRequest request, HttpServletResponse response) throws Exception\n");
		src.append("	{\n");
		src.append("		super.export(request,response);\n");
		src.append("	}\n");
		src.append("\n");
		src.append("}\n");

		File srcFile = new File(tableObj.getJavaSrcDir() + "/" + tableObj.getJavaPackage().replace('.', '/') + "/" + StringUtil.toClassName(tableObj.getTableClassName()) + "Servlet.java");
		if (!srcFile.getParentFile().exists())
		{
			srcFile.getParentFile().mkdirs();
		}
		PrintWriter pw = new PrintWriter(srcFile, "utf-8");
		pw.print(src);
		pw.close();
		System.out.println("--" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " ServletJavaSrc end--");
	}

	private static String getClzSimpleName(BaseDbObj domainInstance)
	{
		String clzSimpleName = domainInstance.getClass().getSimpleName().replaceAll("Obj", "");
		return clzSimpleName;
	}

	public static void generateJsps(TableObj tableObj) throws Exception
	{
		System.out.println("==" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " begin==");

		generateListJsp(tableObj);

		generateAddOrModifyJsp(tableObj);

		generateDetailJsp(tableObj);

		System.out.println("==" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " end==\n");
	}

	private static void generateDetailJsp(TableObj tableObj) throws Exception
	{
		System.out.println("--" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " detail begin--");
		StringBuffer src = new StringBuffer();
		src.append("<!DOCTYPE html> \n");
		src.append("<%@ page contentType=\"text/html; charset=utf-8\" language=\"java\" import=\"java.sql.*\" errorPage=\"\"%> \n");
		src.append("<!-- 引入类 -->  \n");
		src.append("<%@page import=\"java.util.List\"%> \n");
		src.append("<%@page import=\"com.wuyg.common.util.StringUtil\"%> \n");
		src.append("<%@page import=\"" + tableObj.getJavaBeanFullName() + "\"%> \n");
		src.append("<% \n");
		src.append("	// 当前上下文路径  \n");
		src.append("	String contextPath = request.getContextPath();  \n");
		src.append("  \n");
		src.append("	// 该功能对象实例  \n");
		src.append("	" + tableObj.getJavaBeanSimpleName() + " domainInstance = (" + tableObj.getJavaBeanSimpleName() + ") request.getAttribute(\"domainInstance\");  \n");
		src.append("	// 该功能路径  \n");
		src.append("	String basePath = domainInstance.getBasePath();  \n");
		src.append("%> \n");
		src.append("<html> \n");
		src.append("	<head> \n");
		src.append("		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />  \n");
		src.append("		<title><%=domainInstance.getCnName()%>详情</title> \n");
		src.append("		<link href=\"../css/global.css\" rel=\"stylesheet\" type=\"text/css\" /> \n");
		src.append("		<link href=\"../css/table.css\" rel=\"stylesheet\" type=\"text/css\" /> \n");
		src.append("		<script src=\"../js/jquery-2.0.3.min.js\"></script> \n");
		src.append("		<script src=\"../js/utils.js\"></script> \n");
		src.append("	</head> \n");
		src.append("	<body> \n");
		src.append("		<!-- 表格标题 --> \n");
		src.append("		<table width=\"600\" align=\"center\" class=\"title_table\"> \n");
		src.append("			<tr> \n");
		src.append("				<td> \n");
		src.append("					<img src=\"" + tableObj.getIconPath() + "\" width=\"18\" height=\"18\" align=\"absmiddle\"> \n");
		src.append("					&nbsp;&nbsp;<%=domainInstance.getCnName()%>信息 \n");
		src.append("				</td> \n");
		src.append("			</tr> \n");
		src.append("		</table> \n");
		src.append("		<!-- 详细信息 --> \n");
		src.append("		<table width=\"600\" align=\"center\" class=\"detail_table detail_table-bordered detail_table-striped\"> \n");

		List<ColumnObj> showColumns = tableObj.getShowColumns();
		for (int i = 0; i < showColumns.size(); i++)
		{
			ColumnObj column = showColumns.get(i);
			String columnName = column.getColumnName();
			src.append("			<tr> \n");
			src.append("				<td> \n");
			src.append("					<%=domainInstance.getPropertyCnName(\"" + columnName + "\") %>: \n");
			src.append("				</td> \n");
			src.append("				<td><%=StringUtil.getNotEmptyStr(domainInstance.get" + StringUtil.upperFirstChar(columnName) + "())%></td> \n");
			src.append("			</tr> \n");
		}

		src.append("		</table> \n");
		src.append("		 \n");
		src.append("		<!-- 工具栏 --> \n");
		src.append("		<jsp:include page=\"../ToolBar/detail_toolbar.jsp\"/> \n");
		src.append("	</body> \n");
		src.append("</html> \n");

		File srcFile = new File(tableObj.getJavaSrcDir() + "/jsp/" + tableObj.getTableClassName() + "/" + AbstractBaseServletTemplate.BASE_METHOD_DETAIL + ".jsp");
		if (!srcFile.getParentFile().exists())
		{
			srcFile.getParentFile().mkdirs();
		}
		PrintWriter pw = new PrintWriter(srcFile, "utf-8");
		pw.print(src);
		pw.close();

		System.out.println("--" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " detail end--");
	}

	private static void generateAddOrModifyJsp(TableObj tableObj) throws Exception
	{
		System.out.println("--" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " addOrModify begin--");
		StringBuffer src = new StringBuffer();
		src.append("<!DOCTYPE html> \n");
		src.append("<%@ page contentType=\"text/html; charset=utf-8\" language=\"java\" import=\"java.sql.*\" errorPage=\"\"%> \n");
		src.append("<!-- 引入类 -->  \n");
		src.append("<%@page import=\"java.util.List\"%> \n");
		src.append("<%@page import=\"java.util.ArrayList\"%> \n");
		src.append("<%@page import=\"com.wuyg.common.util.StringUtil\"%> \n");
		src.append("<%@page import=\"com.inspur.common.dictionary.util.DictionaryUtil\"%> \n");
		src.append("<%@page import=\"com.hz.dict.service.DictionaryService\"%> \n");
		src.append("<%@page import=\"com.hz.dict.service.IDictionaryService\"%> \n");
		src.append("<%@page import=\"" + tableObj.getJavaBeanFullName() + "\"%> \n");
		src.append("<!-- 基本信息 -->  \n");
		src.append("<% \n");
		src.append("	// 上下文路径 \n");
		src.append("	String contextPath = request.getContextPath(); \n");
		src.append("	 \n");
		src.append("	// 对象实例 \n");
		src.append("	" + tableObj.getJavaBeanSimpleName() + " domainInstance = new " + tableObj.getJavaBeanSimpleName() + "(); \n");
		src.append("	// 该功能基本路径 \n");
		src.append("	String basePath = domainInstance.getBasePath(); \n");
		src.append(" \n");
		src.append("	// 如果是修改，则获取对象信息 \n");
		src.append("	Object domainInstanceObj = request.getAttribute(\"domainInstance\"); \n");
		src.append("	if (domainInstanceObj != null) \n");
		src.append("	{ \n");
		src.append("		domainInstance = (" + tableObj.getJavaBeanSimpleName() + ") domainInstanceObj; \n");
		src.append("	} \n");
		src.append(" \n");
		src.append("	// 是否是修改 \n");
		src.append("	boolean isModify = domainInstance.getKeyValue() > 0; \n");
		src.append("	// 唯一性检查用的字段 \n");
		src.append("	String keyCol = \"" + tableObj.getUniqueColumn() + "\"; \n");
		src.append("%> \n");
		src.append("<html> \n");
		src.append("	<head> \n");
		src.append("		<base target=\"_self\" /> \n");
		src.append("		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /> \n");
		src.append("		<title><%=isModify ? \"修改\" + domainInstance.getCnName() : \"增加\" + domainInstance.getCnName()%></title> \n");
		src.append("		<link href=\"../css/global.css\" rel=\"stylesheet\" type=\"text/css\"> \n");
		src.append("		<link href=\"../css/table.css\" rel=\"stylesheet\" type=\"text/css\"> \n");
		src.append("		<script type=\"text/javascript\" src=\"../js/jquery-2.0.3.min.js\"></script> \n");
		src.append("		<script type=\"text/javascript\" src=\"../js/utils.js\"></script> \n");
		src.append("		<script type=\"text/javascript\" src=\"../My97DatePicker/WdatePicker.js\"></script> \n");
		src.append("		<script> \n");
		src.append("		//  新增或修改 \n");
		src.append("		function addOrModify() \n");
		src.append("		{	 \n");
		src.append("				// 做必要的检查 \n");
		List<ColumnObj> notNullColumns = tableObj.getNotNullColumns();
		for (int i = 0; i < notNullColumns.size(); i++)
		{
			ColumnObj column = notNullColumns.get(i);
			src.append("		if(!checkNull(\"" + column.getColumnName() + "\",\"<%=domainInstance.getPropertyCnName(\"" + column.getColumnName() + "\")%>\")) return false; \n");
		}
		src.append("					 \n");
		src.append("			// 修改 \n");
		src.append("			if(\"true\"==\"<%=isModify%>\") \n");
		src.append("			{ \n");
		src.append("				submit(); \n");
		src.append("			} \n");
		src.append("			// 新增 \n");
		src.append("			else \n");
		src.append("			{ \n");
		src.append("					// 新增时检查唯一性 \n");
		src.append("					$.post( \n");
		src.append("					encodeURI(\"Servlet?method=checkId4this&isFromUrl=true&<%=keyCol%>=\"+$(\"#<%=keyCol%>\").val()),  \n");
		src.append("					{Action:\"post\"},  \n");
		src.append("					function (data, textStatus){ \n");
		src.append("						this; \n");
		src.append("						if(data==\"true\")  \n");
		src.append("						{ \n");
		src.append("							alert(\"该<%=domainInstance.getPropertyCnName(keyCol)%>已录入系统\");  \n");
		src.append("							return false; \n");
		src.append("						} \n");
		src.append("						else \n");
		src.append("						{ \n");
		src.append("							submit(); \n");
		src.append("						} \n");
		src.append("					}); \n");
		src.append("				}; \n");
		src.append("		} \n");
		src.append("		 \n");
		src.append("		// 提交保存或修改 \n");
		src.append("		function submit() \n");
		src.append("		{ \n");
		src.append("					$(\"#addOrModifyForm\").submit(); \n");
		src.append("		} \n");
		src.append("		</script> \n");
		src.append("	</head> \n");
		src.append("	<body> \n");
		src.append("		<form name=\"addOrModifyForm\" id=\"addOrModifyForm\" action=\"<%=contextPath%>/<%=basePath%>/Servlet?method=addOrModify4this\" method=\"post\"> \n");
		src.append("			<!-- 表格标题 --> \n");
		src.append("			<table width=\"700\" align=\"center\" class=\"title_table\"> \n");
		src.append("				<tr> \n");
		src.append("					<td> \n");
		src.append("						<img src=\"../images/svg/heavy/green/user_list.png\" width=\"18\" height=\"18\" align=\"absmiddle\"> \n");
		src.append("						&nbsp;&nbsp;<%=isModify ? \"修改\" : \"增加\"%><%=domainInstance.getCnName()%> \n");
		src.append("					</td> \n");
		src.append("				</tr> \n");
		src.append("			</table> \n");
		src.append(" \n");
		src.append("			<!-- 详细信息 --> \n");
		src.append("			<table width=\"700\" align=\"center\" class=\"detail_table detail_table-bordered detail_table-striped\"> \n");
		src.append("				<input type=\"hidden\" id=\"<%=domainInstance.findKeyColumnName()%>\" name=\"<%=domainInstance.findKeyColumnName()%>\" value=\"<%=domainInstance.getKeyValue()%>\"> \n");
		List<ColumnObj> showColumns = tableObj.getShowColumns();
		for (int j = 0; j < showColumns.size(); j++)
		{
			ColumnObj column = showColumns.get(j);

			if (!column.getIsKeyColumn())
			{
				src.append("				<tr> \n");
				src.append("					<td> \n");
				src.append("						<%=domainInstance.getPropertyCnName(\"" + column.getColumnName() + "\") %>: \n");
				src.append("					</td> \n");

				src.append("					<td> \n");

				// 字典字段
				if (!StringUtil.isEmpty(column.getDictName()))
				{
					src.append("						<%=DictionaryUtil.getSelectHtml(new DictionaryService().getDictItemsByDictName(\"" + column.getDictName() + "\", false), \"" + column.getColumnName() + "\", \"\", StringUtil.getNotEmptyStr(domainInstance.get" + StringUtil.upperFirstChar(column.getColumnName())
							+ "(), \"\"), \"notEmpty\")%> \n");
				} else
				{
					// 日期字段使用日期控件，其他类型字段正常处理
					src.append("						<input name=\"" + column.getColumnName() + "\" type=\"text\" id=\"" + column.getColumnName() + "\" value=\"<%=StringUtil.getNotEmptyStr(domainInstance.get" + StringUtil.upperFirstChar(column.getColumnName()) + "(),\"\")%>\" size=\"20\" "
							+ (column.getColumnType().equals("Timestamp") ? "onFocus=\"WdatePicker({isShowClear:false,readOnly:false,highLineWeekDay:true,dateFmt:'yyyy-MM-dd'})\"" : "") + " " + (column.getColumnName().equals(tableObj.getUniqueColumn()) ? "<%=isModify?\"readOnly\":\"\"%>" : "") + "> \n");
				}
				// 不能为空
				if (column.getNotNull())
				{
					src.append("						<font color=\"red\">*</font> \n");
				}
				// 保持唯一性
				if (column.getColumnName().equals(tableObj.getUniqueColumn()))
				{
					src.append("						<font color=\"red\"><%=isModify?\"(不可修改)\":\"(不能重复)\"%></font> \n");
				}

				src.append("					</td> \n");
				src.append("				</tr> \n");
			}
		}

		src.append("			</table> \n");
		src.append("			 \n");
		src.append("			<!-- 工具栏 --> \n");
		src.append("			<jsp:include page=\"../ToolBar/addOrModify_toolbar.jsp\" /> \n");
		src.append("		</form> \n");
		src.append("	</body> \n");
		src.append("</html> \n");

		File srcFile = new File(tableObj.getJavaSrcDir() + "/jsp/" + tableObj.getTableClassName() + "/" + AbstractBaseServletTemplate.BASE_METHOD_ADD_OR_MODIFY + ".jsp");
		if (!srcFile.getParentFile().exists())
		{
			srcFile.getParentFile().mkdirs();
		}
		PrintWriter pw = new PrintWriter(srcFile, "utf-8");
		pw.print(src);
		pw.close();

		System.out.println("--" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " addOrModify end--");
	}

	private static void generateListJsp(TableObj tableObj) throws Exception
	{
		System.out.println("--" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " list begin--");
		StringBuffer src = new StringBuffer();

		src.append("<!DOCTYPE html> \n");
		src.append("<%@ page contentType=\"text/html; charset=utf-8\" language=\"java\" import=\"java.sql.*\" errorPage=\"\"%> \n");
		src.append("<!-- 引入类 --> \n");
		src.append("<%@page import=\"java.util.List\"%> \n");
		src.append("<%@page import=\"java.util.ArrayList\"%> \n");
		src.append("<%@page import=\"com.wuyg.common.util.StringUtil\"%> \n");
		src.append("<%@page import=\"com.wuyg.common.obj.PaginationObj\"%> \n");
		src.append("<%@page import=\"com.inspur.common.dictionary.util.DictionaryUtil\"%> \n");
		src.append("<%@page import=\"com.hz.dict.service.DictionaryService\"%> \n");
		src.append("<%@page import=\"" + tableObj.getJavaBeanFullName() + "\"%> \n");
		src.append("<!-- 基本信息 --> \n");
		src.append("<% \n");
		src.append("	// 当前上下文路径 \n");
		src.append("	String contextPath = request.getContextPath(); \n");
		src.append(" \n");
		src.append("	// 该功能对象实例 \n");
		src.append("	" + tableObj.getJavaBeanSimpleName() + " domainInstance = (" + tableObj.getJavaBeanSimpleName() + ") request.getAttribute(\"domainInstance\"); \n");
		src.append("	// 该功能路径 \n");
		src.append("	String basePath = domainInstance.getBasePath(); \n");
		src.append("%> \n");
		src.append("<html> \n");
		src.append("	<head> \n");
		src.append("		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /> \n");
		src.append("		<title><%=domainInstance.getCnName() %>列表</title> \n");
		src.append("		<link href=\"../css/global.css\" rel=\"stylesheet\" type=\"text/css\"> \n");
		src.append("		<link href=\"../css/table.css\" rel=\"stylesheet\" type=\"text/css\"> \n");
		src.append("		<script type=\"text/javascript\" src=\"../js/jquery-2.0.3.min.js\"></script> \n");
		src.append("		<script type=\"text/javascript\" src=\"../js/utils.js\"></script> \n");
		src.append("		<script type=\"text/javascript\" src=\"../My97DatePicker/WdatePicker.js\"></script> \n");
		src.append("	</head> \n");
		src.append("	<body> \n");
		src.append("		<form name=\"pageForm\" id=\"pageForm\" method=\"post\" action=\"<%=request.getContextPath()%>/<%=basePath%>/Servlet?method=list4this\"> \n");
		src.append("			 \n");
		src.append("			<!-- 查询条件 --> \n");
		src.append("			<table class=\"search_table\" align=\"center\" width=\"98%\"> \n");
		src.append("				<tr> \n");
		src.append("					<td align=\"left\"> \n");

		// 构造查询条件
		List<ColumnObj> forSearchColumns = tableObj.getForSearchColumns();
		for (int i = 0; i < forSearchColumns.size(); i++)
		{
			ColumnObj column = forSearchColumns.get(i);
			String columnName = column.getColumnName();
			src.append("						<%=domainInstance.getPropertyCnName(\"" + columnName + "\") %> \n");

			// 字典字段
			if (!StringUtil.isEmpty(column.getDictName()))
			{
				src.append("						<%=DictionaryUtil.getSelectHtml(new DictionaryService().getDictItemsByDictName(\"" + column.getDictName() + "\", false), \"" + columnName + "\", \"\", StringUtil.getNotEmptyStr(domainInstance.get" + StringUtil.upperFirstChar(columnName)
						+ "(), \"\"), \"notEmpty\")%> \n");
			} else
			{
				// 日期字段使用日期控件，其他类型字段正常处理
				src.append("						<input name=\"" + columnName + "\" type=\"text\" id=\"" + columnName + "\" value=\"<%=StringUtil.getNotEmptyStr(domainInstance.get" + StringUtil.upperFirstChar(columnName) + "())%>\" size=\"20\" "
						+ (column.getColumnType().equals("Timestamp") ? "onFocus=\"WdatePicker({isShowClear:false,readOnly:false,highLineWeekDay:true,dateFmt:'yyyy-MM-dd'})\"" : "") + "> \n");
			}
			src.append("						&nbsp;  \n");
		}

		src.append("						<input name=\"searchButton\" type=\"button\" class=\"blue_button\" value=\" 查询 \" onClick=\"toPage(1)\"> \n");
		src.append("					</td> \n");
		src.append("					<td align=\"right\"> \n");
		src.append("						<input name=\"addButton\" type=\"button\" class=\"green_button\" value=\" 添加 \" onClick=\"openBigModalDialog('<%=contextPath%>/<%=basePath%>/Servlet?method=preModify4this')\"> \n");
		src.append("					</td> \n");
		src.append("				</tr> \n");
		src.append("			</table> \n");
		src.append(" \n");
		src.append("			<!-- 查询结果 --> \n");
		src.append("			<% \n");
		src.append("				PaginationObj pagination = null; \n");
		src.append("				List list = new ArrayList(); \n");
		src.append(" \n");
		src.append("				Object paginationObj = request.getAttribute(\"domainPagination\"); \n");
		src.append("				if (paginationObj != null) \n");
		src.append("				{ \n");
		src.append("					pagination = (PaginationObj) paginationObj; \n");
		src.append("					list = (List) pagination.getDataList(); \n");
		src.append("				} \n");
		src.append("				if (paginationObj == null) \n");
		src.append("				{ \n");
		src.append("					out.print(\"没有符合条件的数据，请重新设置条件再查询。\"); \n");
		src.append("				} else \n");
		src.append("				{ \n");
		src.append("			%> \n");
		src.append("			<table class=\"table table-bordered table-striped\" align=\"center\" width=\"98%\"> \n");
		src.append("				<thead> \n");
		src.append("					<tr> \n");

		// 构造查询结果表头
		List<ColumnObj> showColumns = tableObj.getShowColumns();
		for (int i = 0; i < showColumns.size(); i++)
		{
			String columnName = showColumns.get(i).getColumnName();
			src.append("						<th><%=domainInstance.getPropertyCnName(\"" + columnName + "\") %></th> \n");
		}

		if (tableObj.getContainsOperationCol())
		{
			src.append("						<th>操作</th> \n");
		}

		src.append("					</tr> \n");
		src.append("				</thead> \n");
		src.append("				<% \n");
		src.append("					for (int i = 0; i < list.size(); i++) \n");
		src.append("						{ \n");
		src.append("							" + tableObj.getJavaBeanSimpleName() + " o = (" + tableObj.getJavaBeanSimpleName() + ") list.get(i); \n");
		src.append("				%> \n");
		src.append("				<tr> \n");

		// 构造查询结果中的各列
		for (int i = 0; i < showColumns.size(); i++)
		{
			ColumnObj column = showColumns.get(i);
			if (column.getIsKeyColumn())
			{
				src.append("					<td> \n");
				src.append("						<a href=\"#\" onClick=\"openBigModalDialog('<%=contextPath%>/<%=basePath%>/Servlet?method=detail4this&<%=o.findKeyColumnName()%>=<%=o.getKeyValue()%>')\"> <%=StringUtil.getNotEmptyStr(o.getKeyValue())%> </a> \n");
				src.append("					</td> \n");
			} else
			{
				src.append("					<td><%=StringUtil.getNotEmptyStr(o.get" + StringUtil.upperFirstChar(column.getColumnName()) + "())%></td> \n");
			}
		}

		// 操作列
		if (tableObj.getContainsOperationCol())
		{

			src.append("					<td align=\"left\" style=\"cursor: pointer\"> \n");
			src.append("						<img src=\"../images/list_edit.png\" width=\"14\" height=\"14\" alt=\"修改\" title=\"修改\" onClick=\"openBigModalDialog('<%=contextPath%>/<%=basePath%>/Servlet?method=preModify4this&<%=o.findKeyColumnName()%>=<%=o.getKeyValue()%>')\" /> \n");
			src.append("						&nbsp; \n");
			src.append("						<img src=\"../images/list_delete.png\" width=\"14\" height=\"14\" alt=\"删除\" title=\"删除\" \n");
			src.append("							onClick=\"javascript: \n");
			src.append("								$('#pageForm').attr('action','<%=contextPath%>/<%=basePath%>/Servlet?method=delete4this&<%=o.findKeyColumnName()%>_4del=<%=o.getKeyValue()%>'); \n");
			src.append("								$('#pageForm').submit(); \n");
			src.append("								\" /> \n");
			src.append("					</td> \n");
		}
		src.append("				</tr> \n");
		src.append("				<% \n");
		src.append("					} \n");
		src.append("				%> \n");
		src.append("			</table> \n");
		src.append(" \n");
		src.append("			<!-- 翻页操作栏 --> \n");
		src.append("			<jsp:include page=\"../ToolBar/pagination_toolbar.jsp\"> \n");
		src.append("				<jsp:param name=\"basePath\" value=\"<%=basePath%>\"/> \n");
		src.append("			</jsp:include> \n");
		src.append(" \n");
		src.append("			<% \n");
		src.append("				} \n");
		src.append("			%> \n");
		src.append("		</form>  \n");
		src.append(" \n");
		src.append("	</body> \n");
		src.append("</html> \n");

		File srcFile = new File(tableObj.getJavaSrcDir() + "/jsp/" + tableObj.getTableClassName() + "/" + AbstractBaseServletTemplate.BASE_METHOD_LIST + ".jsp");
		if (!srcFile.getParentFile().exists())
		{
			srcFile.getParentFile().mkdirs();
		}
		PrintWriter pw = new PrintWriter(srcFile, "utf-8");
		pw.print(src);
		pw.close();

		System.out.println("--" + tableObj.getTableClassName() + "\t" + tableObj.getCnName() + " list end--");
	}
}
