<!DOCTYPE html> 
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%> 
<!-- 引入类 --> 
<%@page import="java.util.List"%> 
<%@page import="java.util.ArrayList"%> 
<%@page import="com.wuyg.common.util.StringUtil"%> 
<%@page import="com.wuyg.common.obj.PaginationObj"%> 
<%@page import="com.inspur.common.dictionary.util.DictionaryUtil"%> 
<%@page import="com.hz.dict.service.DictionaryService"%> 
<%@page import="com.fuli.obj.VWelfareDrawDetailObj"%> 
<!-- 基本信息 --> 
<% 
	// 当前上下文路径 
	String contextPath = request.getContextPath(); 
 
	// 该功能对象实例 
	VWelfareDrawDetailObj domainInstance = (VWelfareDrawDetailObj) request.getAttribute("domainInstance"); 
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
						<%=domainInstance.getPropertyCnName("draw_id") %> 
						<input name="draw_id" type="text" id="draw_id" value="<%=StringUtil.getNotEmptyStr(domainInstance.getDraw_id())%>" size="20" > 
						&nbsp;  
						<%=domainInstance.getPropertyCnName("villager_name") %> 
						<input name="villager_name" type="text" id="villager_name" value="<%=StringUtil.getNotEmptyStr(domainInstance.getVillager_name())%>" size="20" > 
						&nbsp;  
						<%=domainInstance.getPropertyCnName("id_card") %> 
						<input name="id_card" type="text" id="id_card" value="<%=StringUtil.getNotEmptyStr(domainInstance.getId_card())%>" size="20" > 
						&nbsp;  
						<%=domainInstance.getPropertyCnName("welfare_policy_name") %> 
						<input name="welfare_policy_name" type="text" id="welfare_policy_name" value="<%=StringUtil.getNotEmptyStr(domainInstance.getWelfare_policy_name())%>" size="20" > 
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
						<th><%=domainInstance.getPropertyCnName("draw_id") %></th> 
						<th><%=domainInstance.getPropertyCnName("t1_billsn") %></th> 
						<th><%=domainInstance.getPropertyCnName("villager_name") %></th> 
						<th><%=domainInstance.getPropertyCnName("id_card") %></th> 
						<th><%=domainInstance.getPropertyCnName("welfare_policy_name") %></th> 
						<th><%=domainInstance.getPropertyCnName("welfare_policy_start_time") %></th> 
						<th><%=domainInstance.getPropertyCnName("welfare_policy_end_time") %></th> 
						<th><%=domainInstance.getPropertyCnName("draw_type") %></th> 
						<th><%=domainInstance.getPropertyCnName("draw_date") %></th> 
						<th><%=domainInstance.getPropertyCnName("product_name") %></th> 
						<th><%=domainInstance.getPropertyCnName("product_spec") %></th> 
						<th><%=domainInstance.getPropertyCnName("product_measuring_unit") %></th> 
						<th><%=domainInstance.getPropertyCnName("product_price") %></th> 
						<th><%=domainInstance.getPropertyCnName("product_quantity") %></th> 
						<th><%=domainInstance.getPropertyCnName("product_quantity_drawed") %></th> 
						<th><%=domainInstance.getPropertyCnName("draw_villager_name") %></th> 
						<th><%=domainInstance.getPropertyCnName("draw_villager_id_card") %></th> 
						<th><%=domainInstance.getPropertyCnName("proxy_villager_name") %></th> 
						<th><%=domainInstance.getPropertyCnName("draw_comment") %></th> 
					</tr> 
				</thead> 
				<% 
					for (int i = 0; i < list.size(); i++) 
						{ 
							VWelfareDrawDetailObj o = (VWelfareDrawDetailObj) list.get(i); 
				%> 
				<tr> 
					<td> 
						<a href="#" onClick="openBigModalDialog('<%=contextPath%>/<%=basePath%>/Servlet?method=detail4this&<%=o.findKeyColumnName()%>=<%=o.getKeyValue()%>')"> <%=StringUtil.getNotEmptyStr(o.getKeyValue())%> </a> 
					</td> 
					<td><%=StringUtil.getNotEmptyStr(o.getDraw_id())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getT1_billsn())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getVillager_name())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getId_card())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getWelfare_policy_name())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getWelfare_policy_start_time())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getWelfare_policy_end_time())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getDraw_type())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getDraw_date())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getProduct_name())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getProduct_spec())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getProduct_measuring_unit())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getProduct_price())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getProduct_quantity())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getProduct_quantity_drawed())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getDraw_villager_name())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getDraw_villager_id_card())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getProxy_villager_name())%></td> 
					<td><%=StringUtil.getNotEmptyStr(o.getDraw_comment())%></td> 
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
