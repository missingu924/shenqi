package com.wuyg.dbmodel;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;

import com.fuli.obj.VWelfareDrawStatObj;
import com.wuyg.common.dao.BaseDbObj;
import com.wuyg.common.servlet.AbstractBaseServletTemplate;
import com.wuyg.common.util.StringUtil;

public class JspModelMainOld
{
	public static String srcBaseDir = "C:\\test";
	public static String basePackage = "com.fuli";
	public static Class domainObjClz = VWelfareDrawStatObj.class;

	public static void main(String[] args) throws Exception
	{
		// System.out.println(VWelfareForDrawDetailObj.class.getCanonicalName());

		// System.exit(1);

		generateJsps(domainObjClz);

		genarateJavaSrc(domainObjClz);
	}

	private static void genarateJavaSrc(Class domainObjClz) throws Exception
	{
		BaseDbObj domainInstance = (BaseDbObj) domainObjClz.newInstance();

		System.out.println("==" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " begin==");

		generateServletJavaSrc(domainInstance);

		generateSearchConditionJavaSrc(domainInstance);

		generateWebXml(domainInstance);

		System.out.println("==" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " end==\n");

	}

	private static void generateWebXml(BaseDbObj domainInstance) throws Exception
	{
		String clzSimpleName = getClzSimpleName(domainInstance);

		System.out.println("--" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " web.xml begin--");
		StringBuffer src = new StringBuffer();

		src.append("	<servlet>\n");
		src.append("		<servlet-name>" + clzSimpleName + "Servlet</servlet-name>\n");
		src.append("		<servlet-class>\n");
		src.append("			" + basePackage + "." + clzSimpleName + "Servlet\n");
		src.append("		</servlet-class>\n");
		src.append("	</servlet>\n");
		src.append("	<servlet-mapping>\n");
		src.append("		<servlet-name>" + clzSimpleName + "Servlet</servlet-name>\n");
		src.append("		<url-pattern>/" + clzSimpleName + "/Servlet</url-pattern>\n");
		src.append("	</servlet-mapping>\n");

		File srcFile = new File(srcBaseDir + "/webxml/" + domainInstance.getBasePath() + "/web.xml");
		if (!srcFile.getParentFile().exists())
		{
			srcFile.getParentFile().mkdirs();
		}
		PrintWriter pw = new PrintWriter(srcFile, "GBK");
		pw.print(src);
		pw.close();
		System.out.println("--" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " web.xml end--");
	}

	private static void generateSearchConditionJavaSrc(BaseDbObj domainInstance) throws Exception
	{
		String clzSimpleName = domainInstance.getClass().getSimpleName();

		System.out.println("--" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " SearchConditionJavaSrc begin--");
		StringBuffer src = new StringBuffer();
		src.append("package " + basePackage + ".searchcondition;\n");
		src.append("\n");
		src.append("import com.wuyg.common.obj.BaseSearchCondition;\n");
		src.append("\n");
		src.append("public class " + clzSimpleName + "SearchCondition extends BaseSearchCondition\n");
		src.append("{\n");
		src.append("\n");
		src.append("}");

		File srcFile = new File(srcBaseDir + "/java/" + domainInstance.getBasePath() + "/" + clzSimpleName + "SearchCondition.java");
		if (!srcFile.getParentFile().exists())
		{
			srcFile.getParentFile().mkdirs();
		}
		PrintWriter pw = new PrintWriter(srcFile, "GBK");
		pw.print(src);
		pw.close();
		System.out.println("--" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " SearchConditionJavaSrc end--");
	}

	private static void generateServletJavaSrc(BaseDbObj domainInstance) throws Exception
	{
		String clzSimpleName = getClzSimpleName(domainInstance);

		System.out.println("--" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " ServletJavaSrc begin--");
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
		src.append("		return \"" + clzSimpleName + "\";\n");
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
		src.append("		return " + domainInstance.getClass().getCanonicalName() + ".class;\n");
		src.append("	}\n");
		src.append("\n");
		src.append("	@Override\n");
		src.append("	public Class getDomainSearchConditionClz()\n");
		src.append("	{\n");
		src.append("		return " + basePackage + ".searchcondition." + clzSimpleName + "ObjSearchCondition.class;\n");
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
		src.append("	private void preModify4this(HttpServletRequest request, HttpServletResponse response) throws Exception\n");
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
		src.append("	protected void delete4this(HttpServletRequest request, HttpServletResponse response) throws Exception\n");
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

		File srcFile = new File(srcBaseDir + "/java/" + domainInstance.getBasePath() + "/" + clzSimpleName + "Servlet.java");
		if (!srcFile.getParentFile().exists())
		{
			srcFile.getParentFile().mkdirs();
		}
		PrintWriter pw = new PrintWriter(srcFile, "GBK");
		pw.print(src);
		pw.close();
		System.out.println("--" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " ServletJavaSrc end--");
	}

	private static String getClzSimpleName(BaseDbObj domainInstance)
	{
		String clzSimpleName = domainInstance.getClass().getSimpleName().replaceAll("Obj", "");
		return clzSimpleName;
	}

	private static void generateJsps(Class domainObjClz) throws Exception
	{

		BaseDbObj domainInstance = (BaseDbObj) domainObjClz.newInstance();

		System.out.println("==" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " begin==");

		generateListJsp(domainInstance);

		generateAddOrModifyJsp(domainInstance);

		generateDetailJsp(domainInstance);

		System.out.println("==" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " end==\n");
	}

	private static void generateDetailJsp(BaseDbObj domainInstance) throws Exception
	{
		System.out.println("--" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " detail begin--");
		StringBuffer src = new StringBuffer();
		src.append("<%@ page contentType=\"text/html; charset=GBK\" language=\"java\" import=\"java.sql.*\" errorPage=\"\"%>\n");
		src.append("<%@page import=\"com.hz.auth.obj.AuthUser\"%>\n");
		src.append("<%@page import=\"com.hz.util.SystemConstant\"%>\n");
		src.append("<%@page import=\"com.wuyg.common.util.StringUtil\"%>\n");
		src.append("<%@page import=\"com.wuyg.common.servlet.AbstractBaseServletTemplate\"%>\n");
		src.append("\n");
		src.append("<%@page import=\"" + domainInstance.getClass().getCanonicalName() + "\"%>\n");
		src.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
		src.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
		src.append("	<head>\n");
		src.append("		<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n");
		src.append("		<title>" + domainInstance.getCnName() + "</title>\n");
		src.append("		<link href=\"../style.css\" rel=\"stylesheet\" type=\"text/css\" />\n");
		src.append("	</head>\n");
		src.append("	<script src=\"../js/jquery-2.0.3.min.js\"></script>\n");
		src.append("	<script src=\"../js/utils.js\"></script>\n");
		src.append("	<%\n");
		src.append("			String basePath=\"" + domainInstance.getBasePath() + "\";// 每个功能都不同\n");
		src.append("			" + domainInstance.getClass().getSimpleName() + " domainInstance = (" + domainInstance.getClass().getSimpleName()
				+ ")request.getAttribute(\"domainInstance\");\n");
		src.append("\n");
		src.append("			String contextPath = request.getContextPath();\n");
		src.append("			AuthUser user = (AuthUser) request.getSession().getAttribute(SystemConstant.AUTH_USER_INFO);\n");
		src.append("	%>\n");
		src.append("	<body>\n");
		src.append("		<div style=\"height: 8px;\"></div>\n");
		src.append("		<table width=\"500\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#3DAEB6\">\n");
		src.append("			<tr>\n");
		src.append("				<td height=\"4\" bgcolor=\"#3DAEB6\"></td>\n");
		src.append("			</tr>\n");
		src.append("			<tr>\n");
		src.append("				<td height=\"25\" align=\"center\" class=\"green_font\">" + domainInstance.getCnName() + "</td>\n");
		src.append("			</tr>\n");
		src.append("		</table>\n");
		src.append("		<table width=\"500\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#3DAEB6\">\n");
		src.append("			<tr>\n");
		src.append("				<td bgcolor=\"#FFFFFF\">\n");
		src.append("					<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n");

		Iterator keys = domainInstance.getProperties().keySet().iterator();
		int i = 0;
		while (keys.hasNext())
		{
			String key = keys.next().toString();
			String name = domainInstance.getProperties().get(key).toString();
			src.append("						<tr class=\"list_table_tr" + ((i++) % 2 == 0 ? "0" : "2") + "\">\n");
			src.append("							<td width=\"100\" height=\"30\" align=\"right\" class=\"little_gray_font\"><%=domainInstance.getProperties().get(\"" + key + "\")%>:</td>\n");
			src.append("							<td><%=StringUtil.getNotEmptyStr(domainInstance.get" + StringUtil.upperFirstChar(key) + "())%></td>\n");
			src.append("						</tr>\n");
		}

		src.append("						<tr>\n");
		src.append("						<td height=\"30\" colspan=\"2\" align=\"center\" class=\"tab_bg\">&nbsp;</td>\n");
		src.append("						</tr>\n");
		src.append("					</table>\n");
		src.append("				</td>\n");
		src.append("			</tr>\n");
		src.append("		</table>\n");
		src.append("		<script>\n");
		src.append("			// 绑定关闭事件\n");
		src.append("			$(window).unload(function(){\n");
		src.append("			  	// 父窗口刷新\n");
		src.append("				var parent = window.dialogArguments; \n");
		src.append("				parent.execScript(\"toPage(1)\",\"javascript\"); \n");
		src.append("			});\n");
		src.append("		</script>\n");
		src.append("	</body>\n");
		src.append("</html>\n");

		File srcFile = new File(srcBaseDir + "/jsp/" + domainInstance.getBasePath() + "/" + AbstractBaseServletTemplate.BASE_METHOD_DETAIL + ".jsp");
		if (!srcFile.getParentFile().exists())
		{
			srcFile.getParentFile().mkdirs();
		}
		PrintWriter pw = new PrintWriter(srcFile, "GBK");
		pw.print(src);
		pw.close();

		System.out.println("--" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " detail end--");
	}

	private static void generateAddOrModifyJsp(BaseDbObj domainInstance) throws Exception
	{
		System.out.println("--" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " addOrModify begin--");
		StringBuffer src = new StringBuffer();
		src.append("<%@ page contentType=\"text/html; charset=GBK\" language=\"java\" import=\"java.sql.*\" errorPage=\"\"%>\n");
		src.append("<%@page import=\"com.hz.auth.obj.AuthUser\"%>\n");
		src.append("<%@page import=\"java.util.List\"%>\n");
		src.append("<%@page import=\"com.hz.util.SystemConstant\"%>\n");
		src.append("<%@page import=\"com.wuyg.common.util.StringUtil\"%>\n");
		src.append("<%@page import=\"java.util.ArrayList\"%>\n");
		src.append("<%@page import=\"com.wuyg.common.obj.PaginationObj\"%>\n");
		src.append("<%@page import=\"com.inspur.common.dictionary.util.DictionaryUtil\"%>\n");
		src.append("<%@page import=\"com.hz.dict.service.DictionaryService\"%>\n");
		src.append("<%@page import=\"com.hz.auth.service.AuthService\"%>\n");
		src.append("<%@page import=\"com.hz.dict.service.IDictionaryService\"%>\n");
		src.append("<%@page import=\"java.net.URLEncoder\"%>\n");
		src.append("\n");
		src.append("<%@page import=\"" + domainInstance.getClass().getCanonicalName() + "\"%>\n");
		src.append("<!DOCTYPE html>\n");
		src.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
		src.append("	<%\n");
		src.append("	\n");
		src.append("		String basePath=\"" + domainInstance.getBasePath() + "\";// 每个功能都不同\n");
		src.append("		" + domainInstance.getClass().getSimpleName() + " domainInstance = new " + domainInstance.getClass().getSimpleName() + "();\n");
		src.append("		\n");
		src.append("		String contextPath = request.getContextPath();\n");
		src.append("		\n");
		src.append("		// 用户信息\n");
		src.append("		AuthUser user = (AuthUser) request.getSession().getAttribute(SystemConstant.AUTH_USER_INFO);\n");
		src.append("\n");
		src.append("		// 如果是修改，则获取对象信息\n");
		src.append("		Object domainInstanceObj = request.getAttribute(\"domainInstance\");\n");
		src.append("		if (domainInstanceObj != null)\n");
		src.append("		{\n");
		src.append("			domainInstance = (" + domainInstance.getClass().getSimpleName() + ")domainInstanceObj;\n");
		src.append("		}\n");
		src.append("\n");
		src.append("		// 字典服务\n");
		src.append("		IDictionaryService dict = new DictionaryService();\n");
		src.append("\n");
		src.append("		// 是否是修改\n");
		src.append("		boolean isModify = domainInstance.getKeyValue()>0;\n");
		src.append("	%>\n");
		src.append("	<head>\n");
		src.append("		<base target=\"_self\" />\n");
		src.append("		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" />\n");
		src.append("		<title><%=isModify ? \"修改" + domainInstance.getCnName() + "\" : \"增加" + domainInstance.getCnName() + "\"%></title>\n");
		src.append("		<link href=\"../style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
		src.append("		<script type=\"text/javascript\" src=\"../js/jquery-2.0.3.min.js\"></script>\n");
		src.append("		<script type=\"text/javascript\" src=\"../js/utils.js\"></script>\n");
		src.append("		<script>\n");
		src.append("		//  新增或修改\n");
		src.append("		function addOrModify()\n");
		src.append("		{	\n");
		src.append("			// 修改账号\n");
		src.append("			if(\"true\"==\"<%=isModify%>\")\n");
		src.append("			{\n");
		src.append("				submit();\n");
		src.append("			}\n");
		src.append("			// 新建账号\n");
		src.append("			else\n");
		src.append("			{\n");
		src.append("				// 做必要的检查\n");
		src.append("				if(!checkSomething(\"" + domainInstance.findKeyColumnName() + "\",\"" + domainInstance.getKeyCnName() + "\")) \n");
		src.append("				{\n");
		src.append("					return false;\n");
		src.append("				} \n");
		src.append("				else \n");
		src.append("				{\n");
		src.append("				// 然后检查ID是否已被使用\n");
		src.append("					$.get(\n");
		src.append("					\"Servlet?method=checkId&" + domainInstance.findKeyColumnName() + "=\"+$(\"#" + domainInstance.findKeyColumnName() + "\").val(), \n");
		src.append("					{Action:\"get\"}, \n");
		src.append("					function (data, textStatus){\n");
		src.append("						this;\n");
		src.append("						if(data==\"true\") \n");
		src.append("						{\n");
		src.append("							alert(\"该" + domainInstance.getKeyCnName() + "已录入系统\"); \n");
		src.append("							return false;\n");
		src.append("						}\n");
		src.append("						else\n");
		src.append("						{\n");
		src.append("							submit();\n");
		src.append("						}\n");
		src.append("					});\n");
		src.append("				};\n");
		src.append("			}\n");
		src.append("		}\n");
		src.append("		\n");
		src.append("		// 提交保存或修改\n");
		src.append("		function submit()\n");
		src.append("		{\n");
		src.append("						// 如果账号符合要求且未被使用，则检测其他的项\n");
		Iterator keys = domainInstance.getProperties().keySet().iterator();
		while (keys.hasNext())
		{
			String key = keys.next().toString();
			String name = domainInstance.getProperties().get(key).toString();
			src.append("					if(!checkNull(\"" + key + "\",\"" + name + "\")) return false;\n");
		}
		src.append("						$(\"#addOrModifyForm\").submit();\n");
		src.append("		}\n");
		src.append("		</script>\n");
		src.append("	</head>\n");
		src.append("	<body>\n");
		src.append("		<form name=\"addOrModifyForm\" id=\"addOrModifyForm\" action=\"<%=contextPath%>/<%=basePath %>/Servlet?method=addOrModify\" method=\"post\">\n");
		src.append("		<div style=\"height:8px;\"></div>\n");
		src.append("        <table width=\"500\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#3DAEB6\">\n");
		src.append("          <tr>\n");
		src.append("            <td height=\"4\" bgcolor=\"#3DAEB6\"></td>\n");
		src.append("          </tr>\n");
		src.append("          <tr>\n");
		src.append("            <td height=\"25\" align=\"center\" class=\"green_font\">添加" + domainInstance.getCnName() + "</td>\n");
		src.append("          </tr>\n");
		src.append("        </table>\n");
		src.append("			<table width=\"500\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#3DAEB6\">\n");
		src.append("				<tr>\n");
		src.append("					<td bgcolor=\"#FFFFFF\">\n");
		src.append("						<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n");
		src.append("							<tr class=\"list_table_tr0\">\n");
		src.append("								<td width=\"130\" height=\"30\" align=\"right\" class=\"little_gray_font\">" + domainInstance.getKeyCnName() + ":</td>\n");
		src.append("								<td>\n");
		src.append("										<input name=\"" + domainInstance.findKeyColumnName() + "\" type=\"text\" id=\"" + domainInstance.findKeyColumnName()
				+ "\" value=\"<%=domainInstance.getKeyValue()%>\"\n");
		src.append("											<%=isModify ? \"readonly\" : \"\"%> class=\"notEmpty\">\n");
		src.append("\n");
		src.append("										<%\n");
		src.append("											if (isModify)\n");
		src.append("											{\n");
		src.append("										%>\n");
		src.append("										<span class=\"red_font\">(不可修改)</span>\n");
		src.append("										<%\n");
		src.append("											} else\n");
		src.append("											{\n");
		src.append("										%>\n");
		src.append("										<%\n");
		src.append("											}\n");
		src.append("										%>\n");
		src.append("								</td>\n");
		src.append("							</tr>\n");

		keys = domainInstance.getProperties().keySet().iterator();
		int i = 0;
		while (keys.hasNext())
		{
			String key = keys.next().toString();
			String name = domainInstance.getProperties().get(key).toString();
			if (key.equalsIgnoreCase(domainInstance.findKeyColumnName()))
			{
				continue;
			}
			src.append("							<tr class=\"list_table_tr" + ((i++) % 2 == 0 ? "2" : "0") + "\">\n");
			src.append("								<td width=\"130\" height=\"30\" align=\"right\" class=\"little_gray_font\"><%=domainInstance.getProperties().get(\"" + key + "\")%>:</td>\n");
			src.append("								<td>\n");
			src.append("										<input name=\"" + key + "\" type=\"text\" id=\"" + key + "\" value=\"<%=StringUtil.getNotEmptyStr(domainInstance.get"
					+ StringUtil.upperFirstChar(key) + "())%>\" class=\"notEmpty\">\n");
			src.append("								</td>\n");
			src.append("							</tr>\n");
		}

		src.append("							<tr class=\"tab_bg\">\n");
		src.append("								<td height=\"30\" colspan=\"2\" align=\"center\">\n");
		src.append("									<input name=\"Submit\" type=\"button\" class=\"gray_button\" value=\"取消\" onClick=\"javascript:window.close();\">\n");
		src.append("									&nbsp;\n");
		src.append("									<input name=\"Submit2\" type=\"button\" class=\"green_button\" value=\"保存\" onClick=\"addOrModify()\">\n");
		src.append("								</td>\n");
		src.append("							</tr>\n");
		src.append("						</table>\n");
		src.append("					</td>\n");
		src.append("				</tr>\n");
		src.append("			</table>\n");
		src.append("		</form>\n");
		src.append("		<script>\n");
		src.append("			// 绑定enter事件\n");
		src.append("			$('body').keydown(function(){\n");
		src.append("			   if(event.keyCode == 13)\n");
		src.append("			   {\n");
		src.append("				 addOrModify();\n");
		src.append("			   }\n");
		src.append("			});\n");
		src.append("		</script>\n");
		src.append("	</body>\n");
		src.append("</html>\n");

		File srcFile = new File(srcBaseDir + "/jsp/" + domainInstance.getBasePath() + "/" + AbstractBaseServletTemplate.BASE_METHOD_ADD_OR_MODIFY + ".jsp");
		if (!srcFile.getParentFile().exists())
		{
			srcFile.getParentFile().mkdirs();
		}
		PrintWriter pw = new PrintWriter(srcFile, "GBK");
		pw.print(src);
		pw.close();

		System.out.println("--" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " addOrModify end--");
	}

	private static void generateListJsp(BaseDbObj domainInstance) throws Exception
	{
		System.out.println("--" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " list begin--");
		StringBuffer src = new StringBuffer();

		src.append("<%@ page contentType=\"text/html; charset=GBK\" language=\"java\" import=\"java.sql.*\" errorPage=\"\"%>\n");
		src.append("<%@page import=\"com.hz.auth.obj.AuthUser\"%>\n");
		src.append("<%@page import=\"java.util.List\"%>\n");
		src.append("<%@page import=\"com.hz.util.SystemConstant\"%>\n");
		src.append("<%@page import=\"com.wuyg.common.util.StringUtil\"%>\n");
		src.append("<%@page import=\"java.util.ArrayList\"%>\n");
		src.append("<%@page import=\"com.wuyg.common.obj.PaginationObj\"%>\n");
		src.append("<%@page import=\"com.inspur.common.dictionary.util.DictionaryUtil\"%>\n");
		src.append("<%@page import=\"com.hz.dict.service.DictionaryService\"%>\n");
		src.append("<%@page import=\"com.wuyg.common.servlet.AbstractBaseServletTemplate\"%>\n");
		src.append("\n");
		src.append("<%@page import=\"" + domainInstance.getClass().getCanonicalName() + "\"%>\n");
		src.append("<!DOCTYPE html>\n");
		src.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
		src.append("	<head>\n");
		src.append("		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" />\n");
		src.append("		<title>" + domainInstance.getCnName() + "列表</title>\n");
		src.append("		<link href=\"../style.css\" rel=\"stylesheet\" type=\"text/css\">\n");
		src.append("		<script type=\"text/javascript\" src=\"../js/jquery-2.0.3.min.js\"></script>\n");
		src.append("		<script type=\"text/javascript\" src=\"../js/utils.js\"></script>\n");
		src.append("		<%\n");
		src.append("			String basePath=\"" + domainInstance.getBasePath() + "\";// 每个功能都不同\n");
		src.append("		" + domainInstance.getClass().getSimpleName() + " domainInstance = new " + domainInstance.getClass().getSimpleName() + "();\n");
		src.append("\n");
		src.append("			String contextPath = request.getContextPath();\n");
		src.append("			AuthUser user = (AuthUser) request.getSession().getAttribute(SystemConstant.AUTH_USER_INFO);\n");
		src.append("		%>\n");
		src.append("	</head>\n");
		src.append("	<body>\n");
		src.append("\n");
		src.append("		<form name=\"pageForm\" id=\"pageForm\" method=\"post\" action=\"<%=request.getContextPath()%>/<%=basePath%>/Servlet?method=list\">\n");
		src.append("			<table width=\"98%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n");
		src.append("				<tr>\n");
		src.append("					<td height=\"9px\"></td>\n");
		src.append("				</tr>\n");
		src.append("			</table>\n");
		src.append("			<table width=\"98%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#3DAEB6\">\n");
		src.append("				<tr>\n");
		src.append("					<td>\n");
		src.append("						<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n");
		src.append("							<tr class=\"list_table_tr2\">\n");
		src.append("								<td align=\"left\">\n");
		src.append("									" + domainInstance.getProperties().get(domainInstance.findKeyColumnName()) + " <input name=\"" + domainInstance.findKeyColumnName()
				+ "\" type=\"text\" id=\"" + domainInstance.findKeyColumnName() + "\" value=\"<%=StringUtil.getNotEmptyStr(domainInstance.getKeyValue())%>\" size=\"20\">\n");
		src.append("									&nbsp;\n");
		src.append("									<input name=\"searchButton\" type=\"button\" class=\"blue_button\" value=\"查询\" onClick=\"toPage(1)\">\n");
		src.append("							  	</td>\n");
		src.append("							    <td align=\"right\">\n");
		if (!StringUtil.isEmpty(domainInstance.findKeyColumnName()))
		{
			src
					.append("							      <input name=\"addButton\" type=\"button\" class=\"green_button\" value=\"添加\" onClick=\"openBigModalDialog('<%=contextPath%>/<%=basePath%>/addOrModify.jsp')\">\n");

		}
		src.append("							    </td>\n");
		src.append("							</tr>\n");
		src.append("						</table>\n");
		src.append("					</td>\n");
		src.append("				</tr>\n");
		src.append("			</table>\n");
		src.append("			<table width=\"98%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n");
		src.append("				<tr>\n");
		src.append("					<td height=\"10px\"></td>\n");
		src.append("				</tr>\n");
		src.append("			</table>\n");
		src.append("			<%\n");
		src.append("				// 数据信息\n");
		src.append("				PaginationObj pagination = null;\n");
		src.append("				List list = new ArrayList();\n");
		src.append("\n");
		src.append("				Object paginationObj = request.getAttribute(\"domainPagination\");\n");
		src.append("				if (paginationObj != null)\n");
		src.append("				{\n");
		src.append("					pagination = (PaginationObj) paginationObj;\n");
		src.append("					list = (List) pagination.getDataList();\n");
		src.append("				}\n");
		src.append("				if (paginationObj == null)\n");
		src.append("				{\n");
		src.append("					out.print(\"没有查询到数据。\");\n");
		src.append("				} else\n");
		src.append("				{\n");
		src.append("			%>\n");
		src.append("			<table width=\"98%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#CCCCCC\">\n");
		src.append("				<tr class=\"list_table_head\">\n");

		Iterator keys = domainInstance.getProperties().keySet().iterator();
		while (keys.hasNext())
		{
			String key = keys.next().toString();
			String name = domainInstance.getProperties().get(key).toString();
			// src.append(" <td>" + name + "</td>\n");
			src.append("					<td><%=domainInstance.getProperties().get(\"" + key + "\")%></td>\n");
		}
		if (!StringUtil.isEmpty(domainInstance.findKeyColumnName()))
		{
			src.append("					<td>操作</td>\n");
		}

		src.append("				</tr>\n");
		src.append("				<%\n");
		src.append("					for (int i = 0; i < list.size(); i++)\n");
		src.append("						{\n");
		src.append("							" + domainInstance.getClass().getSimpleName() + " o = (" + domainInstance.getClass().getSimpleName() + ")list.get(i);\n");
		src.append("				%>\n");
		src.append("				<tr class=\"<%=i % 2 == 0 ? \"list_table_tr0\" : \"list_table_tr1\"%>\">\n");

		keys = domainInstance.getProperties().keySet().iterator();
		while (keys.hasNext())
		{
			String key = keys.next().toString();
			src.append("					<td><%=StringUtil.getNotEmptyStr(o.get" + StringUtil.upperFirstChar(key) + "())%></td>\n");
		}

		if (!StringUtil.isEmpty(domainInstance.findKeyColumnName()))
		{
			src.append("					<td width=\"90\" align=\"center\" style=\"padding: 0; cursor: pointer\">\n");
			src.append("						<img src=\"../images/list_detail.png\" width=\"25\" height=\"25\"\n");
			src.append("							alt=\"详情\" title=\"详情\"\n");
			src
					.append("							onClick=\"openBigModalDialog('<%=contextPath%>/<%=basePath%>/Servlet?method=<%=AbstractBaseServletTemplate.BASE_METHOD_DETAIL %>&<%=o.findKeyColumnName() %>=<%=o.getKeyValue()%>')\">\n");
			src.append("						<%\n");
			src.append("							if (SystemConstant.ROLE_ADMIN.equalsIgnoreCase(user.getRoleLevel()))\n");
			src.append("									{\n");
			src.append("						%>\n");
			src.append("						<img src=\"../images/list_edit.png\" width=\"25\" height=\"25\" alt=\"修改\"\n");
			src.append("							title=\"修改\"\n");
			src
					.append("							onClick=\"openBigModalDialog('<%=contextPath%>/<%=basePath%>/Servlet?method=<%=AbstractBaseServletTemplate.BASE_METHOD_PRE_MODIFY %>&<%=o.findKeyColumnName() %>=<%=o.getKeyValue()%>')\"/>\n");
			src.append("						<img src=\"../images/list_delete.png\" width=\"25\" height=\"25\"\n");
			src.append("							alt=\"删除\" title=\"删除\"\n");
			src.append("							onClick=\"javascript:\n");
			src
					.append("								$('#pageForm').attr('action','<%=contextPath%>/<%=basePath%>/Servlet?method=<%=AbstractBaseServletTemplate.BASE_METHOD_DELETE %>&<%=o.findKeyColumnName() %>_4del=<%=o.getKeyValue()%>');\n");
			src.append("								$('#pageForm').submit();\n");
			src.append("								\"/>\n");
			src.append("						<%\n");
			src.append("							}\n");
			src.append("						%>\n");
			src.append("					</td>\n");
		}

		src.append("				</tr>\n");
		src.append("				<%\n");
		src.append("					}\n");
		src.append("				%>\n");
		src.append("				<tr class=\"list_table_foot\">\n");
		src.append("					<td colspan=\"" + (domainInstance.getProperties().size() + 1) + "\">\n");
		src.append("						<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n");
		src.append("							align=\"center\">\n");
		src.append("							<tr>\n");
		src.append("								<td align=\"right\">\n");
		src
				.append("								    <img src=\"../images/pagination_icons_save.png\"  title=\"导出全部数据\"  class=\"image_button\" align=\"absmiddle\" onClick=\"exportData('<%=pagination.getTotalCount()%>','<%=request.getContextPath()%>/<%=basePath%>/Servlet?method=export')\" />&nbsp;&nbsp;&nbsp;&nbsp;\n");
		src.append("									<input name=\"pageNo\" type=\"hidden\" id=\"pageNo\" value=\"<%=pagination.getPageNo()%>\" size=\"3\" />\n");
		src.append("									<input name=\"pageCount\" type=\"hidden\" value=\"<%=pagination.getPageCount()%>\" size=\"3\" />\n");
		src.append("									<%\n");
		src.append("										if (pagination.isPrevious())\n");
		src.append("											{\n");
		src.append("									%>\n");
		src.append("									<img src=\"../images/pagination_icons_first.png\" align=\"absmiddle\" onClick=\"toPage(1);\" class=\"image_button\"/>\n");
		src.append("									&nbsp;&nbsp;\n");
		src.append("									<img src=\"../images/pagination_icons_pre.png\" align=\"absmiddle\" onclick=\"toPage(<%=pagination.getPageNo() - 1%>);\" class=\"image_button\"/>\n");
		src.append("									&nbsp;&nbsp;\n");
		src.append("									<%\n");
		src.append("										} else\n");
		src.append("											{\n");
		src.append("									%>\n");
		src.append("									<img src=\"../images/pagination_icons_first_gray.png\" align=\"absmiddle\" />\n");
		src.append("									&nbsp;&nbsp;\n");
		src.append("									<img src=\"../images/pagination_icons_pre_gray.png\" align=\"absmiddle\" />\n");
		src.append("									&nbsp;&nbsp;\n");
		src.append("									<%\n");
		src.append("										}\n");
		src.append("									%>\n");
		src.append("									<%\n");
		src.append("										if (pagination.isNext())\n");
		src.append("											{\n");
		src.append("									%>\n");
		src.append("									<img src=\"../images/pagination_icons_next.png\" align=\"absmiddle\" onclick=\"toPage(<%=pagination.getPageNo() + 1%>);\" class=\"image_button\"/>\n");
		src.append("									&nbsp;&nbsp;\n");
		src.append("									<img src=\"../images/pagination_icons_last.png\" align=\"absmiddle\" onclick=\"toPage(<%=pagination.getTotalPage()%>);\" class=\"image_button\"/>\n");
		src.append("									&nbsp;&nbsp;\n");
		src.append("									<%\n");
		src.append("										} else\n");
		src.append("											{\n");
		src.append("									%>\n");
		src.append("									<img src=\"../images/pagination_icons_next_gray.png\" align=\"absmiddle\" />\n");
		src.append("									&nbsp;&nbsp;\n");
		src.append("									<img src=\"../images/pagination_icons_last_gray.png\" align=\"absmiddle\" />\n");
		src.append("									&nbsp;&nbsp;\n");
		src.append("									<%\n");
		src.append("										}\n");
		src.append("									%>\n");
		src.append("									<img src=\"../images/pagination_icons_fresh.png\" align=\"absmiddle\" onClick=\"toPage(1);\" class=\"image_button\"/>\n");
		src.append("								</td>\n");
		src
				.append("								<td width=\"180\" align=\"right\" valign=\"middle\">第<%=pagination.getPageNo()%>/<%=pagination.getTotalPage()%>页&nbsp;共<%=pagination.getTotalCount()%>条数据&nbsp;</td>\n");
		src.append("							</tr>\n");
		src.append("						</table>\n");
		src.append("					</td>\n");
		src.append("				</tr>\n");
		src.append("			</table>\n");
		src.append("			<%\n");
		src.append("				}\n");
		src.append("			%>\n");
		src.append("		</form>\n");
		src.append("		<script>\n");
		src.append("			// 绑定enter事件\n");
		src.append("			$('body').keydown(function(){\n");
		src.append("			   if(event.keyCode == 13)\n");
		src.append("			   {\n");
		src.append("				 $(\"#pageForm\").submit();\n");
		src.append("			   }\n");
		src.append("			});\n");
		src.append("		</script>\n");
		src.append("	</body>\n");
		src.append("</html>\n");

		File srcFile = new File(srcBaseDir + "/jsp/" + domainInstance.getBasePath() + "/" + AbstractBaseServletTemplate.BASE_METHOD_LIST + ".jsp");
		if (!srcFile.getParentFile().exists())
		{
			srcFile.getParentFile().mkdirs();
		}
		PrintWriter pw = new PrintWriter(srcFile, "GBK");
		pw.print(src);
		pw.close();

		System.out.println("--" + domainInstance.getClass().getSimpleName() + "\t" + domainInstance.getCnName() + " list end--");
	}
}
