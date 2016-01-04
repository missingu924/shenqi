<!DOCTYPE html> 
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%> 
<!-- 引入类 --> 
<%@page import="java.util.List"%> 
<%@page import="java.util.ArrayList"%> 
<%@page import="com.wuyg.common.util.StringUtil"%> 
<%@page import="com.wuyg.common.obj.PaginationObj"%> 
<%@page import="com.inspur.common.dictionary.util.DictionaryUtil"%> 
<%@page import="com.hz.dict.service.DictionaryService"%> 
<%@page import="com.fuli.obj.VillagerObj"%> 
<!-- 基本信息 --> 
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
		<title><%=domainInstance.getCnName() %>列表</title> 
		<link href="../css/global.css" rel="stylesheet" type="text/css"> 
		<link href="../css/table.css" rel="stylesheet" type="text/css"> 
		<script type="text/javascript" src="../js/jquery-2.0.3.min.js"></script> 
		<script type="text/javascript" src="../js/utils.js"></script> 
		<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script> 
	</head> 
	<body> 
		<form name="pageForm" id="pageForm" method="post" action="<%=request.getContextPath()%>/<%=basePath%>/Servlet?method=list4this"> 
			 
			<!-- 查询条件 --> 
			<table class="search_table" align="center" width="98%"> 
				<tr> 
					<td align="left"> 
						<%=domainInstance.getPropertyCnName("villager_name") %> 
						<input name="villager_name" type="text" id="villager_name" value="<%=StringUtil.getNotEmptyStr(domainInstance.getVillager_name())%>" size="20" > 
						&nbsp;  
						<%=domainInstance.getPropertyCnName("id_card") %> 
						<input name="id_card" type="text" id="id_card" value="<%=StringUtil.getNotEmptyStr(domainInstance.getId_card())%>" size="20" > 
						&nbsp;  
						<%=domainInstance.getPropertyCnName("villager_sex") %> 
						<%=DictionaryUtil.getSelectHtml(new DictionaryService().getDictItemsByDictName("sex", false), "villager_sex", "", StringUtil.getNotEmptyStr(domainInstance.getVillager_sex(), ""), "notEmpty")%> 
						&nbsp;  
						<%=domainInstance.getPropertyCnName("enable") %> 
						<%=DictionaryUtil.getSelectHtml(new DictionaryService().getDictItemsByDictName("enable", false), "enable", "", StringUtil.getNotEmptyStr(domainInstance.getEnable(), ""), "notEmpty")%> 
						&nbsp;  
						<input name="searchButton" type="button" class="blue_button" value=" 查询 " onClick="toPage(1)"> 
					</td> 
					<td align="right"> 
						<input name="addButton" type="button" class="green_button" value=" 添加 " onClick="openBigModalDialog('<%=contextPath%>/<%=basePath%>/Servlet?method=preModify4this')"> 
					</td> 
				</tr> 
			</table> 
 
			<!-- 查询结果 --> 
			<% 
				PaginationObj pagination = null; 
				List list = new ArrayList(); 
 
				Object paginationObj = request.getAttribute("domainPagination"); 
				if (paginationObj != null) 
				{ 
					pagination = (PaginationObj) paginationObj; 
					list = (List) pagination.getDataList(); 
				} 
				if (paginationObj == null) 
				{ 
					out.print("没有符合条件的数据，请重新设置条件再查询。"); 
				} else 
				{ 
			%> 
			<table class="table table-bordered table-striped" align="center" width="98%"> 
				<thead> 
					<tr> 
						<th><%=domainInstance.getPropertyCnName("id") %></th> 
						<th><%=domainInstance.getPropertyCnName("villager_name") %></th> 
						<th><%=domainInstance.getPropertyCnName("id_card") %></th> 
						<th><%=domainInstance.getPropertyCnName("villager_sex") %></th> 
						<th><%=domainInstance.getPropertyCnName("villager_telephone") %></th> 
						<th><%=domainInstance.getPropertyCnName("villager_omment") %></th> 
						<th><%=domainInstance.getPropertyCnName("enable") %></th> 
						<th><%=domainInstance.getPropertyCnName("binding_to_id") %></th> 
						<th><%=domainInstance.getPropertyCnName("last_modify_account") %></th> 
						<th><%=domainInstance.getPropertyCnName("last_modify_time") %></th> 
						<th>操作</th> 
					</tr> 
				</thead> 
				<% 
					for (int i = 0; i < list.size(); i++) 
						{ 
							VillagerObj o = (VillagerObj) list.get(i); 
				%> 
				<tr> 
					<td> 
						<a href="#" onClick="openBigModalDialog('<%=contextPath%>/<%=basePath%>/Servlet?method=detail4this&<%=o.findKeyColumnName()%>=<%=o.getKeyValue()%>')"> <%=StringUtil.getNotEmptyStr(o.getKeyValue())%> </a> 
					</td> 
					<td><%=StringUtil.getNotEmptyStr(o.getVillager_name())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getId_card())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getVillager_sex())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getVillager_telephone())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getVillager_omment())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getEnable())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getBinding_to_id())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getLast_modify_account())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getLast_modify_time())%></td> 
					<td align="left" style="cursor: pointer"> 
						<img src="../images/list_edit.png" width="14" height="14" alt="修改" title="修改" onClick="openBigModalDialog('<%=contextPath%>/<%=basePath%>/Servlet?method=preModify4this&<%=o.findKeyColumnName()%>=<%=o.getKeyValue()%>')" /> 
						&nbsp; 
						<img src="../images/list_delete.png" width="14" height="14" alt="删除" title="删除" 
							onClick="javascript: 
								$('#pageForm').attr('action','<%=contextPath%>/<%=basePath%>/Servlet?method=delete4this&<%=o.findKeyColumnName()%>_4del=<%=o.getKeyValue()%>'); 
								$('#pageForm').submit(); 
								" /> 
					</td> 
				</tr> 
				<% 
					} 
				%> 
			</table> 
 
			<!-- 翻页操作栏 --> 
			<jsp:include page="../ToolBar/pagination_toolbar.jsp"> 
				<jsp:param name="basePath" value="<%=basePath%>"/> 
			</jsp:include> 
 
			<% 
				} 
			%> 
		</form>  
 
	</body> 
</html> 
