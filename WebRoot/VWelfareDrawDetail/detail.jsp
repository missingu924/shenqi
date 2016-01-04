<!DOCTYPE html> 
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%> 
<!-- 引入类 -->  
<%@page import="java.util.List"%> 
<%@page import="com.wuyg.common.util.StringUtil"%> 
<%@page import="com.fuli.obj.VWelfareDrawDetailObj"%> 
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
					<img src="../images/svg/heavy/green/list.png" width="18" height="18" align="absmiddle"> 
					&nbsp;&nbsp;<%=domainInstance.getCnName()%>信息 
				</td> 
			</tr> 
		</table> 
		<!-- 详细信息 --> 
		<table width="600" align="center" class="detail_table detail_table-bordered detail_table-striped"> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("id") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getId())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("draw_id") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getDraw_id())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("t1_billsn") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getT1_billsn())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("villager_name") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getVillager_name())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("id_card") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getId_card())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("welfare_policy_name") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getWelfare_policy_name())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("welfare_policy_start_time") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getWelfare_policy_start_time())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("welfare_policy_end_time") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getWelfare_policy_end_time())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("draw_type") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getDraw_type())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("draw_date") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getDraw_date())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("product_name") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getProduct_name())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("product_spec") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getProduct_spec())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("product_measuring_unit") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getProduct_measuring_unit())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("product_price") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getProduct_price())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("product_quantity") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getProduct_quantity())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("product_quantity_drawed") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getProduct_quantity_drawed())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("draw_villager_name") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getDraw_villager_name())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("draw_villager_id_card") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getDraw_villager_id_card())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("proxy_villager_name") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getProxy_villager_name())%></td> 
			</tr> 
			<tr> 
				<td> 
					<%=domainInstance.getPropertyCnName("draw_comment") %>: 
				</td> 
				<td><%=StringUtil.getNotEmptyStr(domainInstance.getDraw_comment())%></td> 
			</tr> 
		</table> 
		 
		<!-- 工具栏 --> 
		<jsp:include page="../ToolBar/detail_toolbar.jsp"/> 
	</body> 
</html> 
