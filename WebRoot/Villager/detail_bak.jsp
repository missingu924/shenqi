<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!-- 引入类 --> 
<%@page import="com.hz.auth.obj.AuthUser"%>
<%@page import="com.hz.util.SystemConstant"%>
<%@page import="com.wuyg.common.util.StringUtil"%>
<%@page import="com.fuli.obj.VillagerObj"%>
<%@page import="java.util.List"%>
<%@page import="com.fuli.obj.WelfarePolicyObj"%>
<%
	// 当前上下文路径 
	String contextPath = request.getContextPath(); 
 
	// 该功能对象实例 
	VillagerObj domainInstance = (VillagerObj) request.getAttribute("domainInstance"); 
	// 该功能路径 
	String basePath = domainInstance.getBasePath(); 
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
		<title><%=domainInstance.getCnName()%>详情</title>
		<link href="../css/global.css" rel="stylesheet" type="text/css" />
		<link href="../css/table.css" rel="stylesheet" type="text/css" />
		<script src="../js/jquery-2.0.3.min.js"></script>
		<script src="../js/utils.js"></script>
	</head>
	<body>
		<!-- 表格标题 -->
		<table width="600" align="center" class="title_table">
			<tr>
				<td>
					<img src="../images/svg/heavy/green/user_list.png" width="18" height="18" align="absmiddle">
					&nbsp;&nbsp;<%=domainInstance.getCnName()%>信息
				</td>
			</tr>
		</table>
		<!-- 详细信息 -->
		<table width="600" align="center" class="detail_table detail_table-bordered detail_table-striped">
			<tr>
				<td>
					<%=domainInstance.getPropertyCnName("id_card") %>:
				</td>
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getId_card())%></td>
			</tr>
		</table>
		
		<!-- 工具栏 -->
		<jsp:include page="../ToolBar/detail_toolbar.jsp"/>
	</body>
</html>

