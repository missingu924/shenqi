<!DOCTYPE html> 
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%> 
<!-- 引入类 -->  
<%@page import="java.util.List"%> 
<%@page import="java.util.ArrayList"%> 
<%@page import="com.wuyg.common.util.StringUtil"%> 
<%@page import="com.inspur.common.dictionary.util.DictionaryUtil"%> 
<%@page import="com.hz.dict.service.DictionaryService"%> 
<%@page import="com.hz.dict.service.IDictionaryService"%> 
<%@page import="com.fuli.obj.VWelfareDrawDetailObj"%> 
<!-- 基本信息 -->  
<% 
	// 上下文路径 
	String contextPath = request.getContextPath(); 
	 
	// 对象实例 
	VWelfareDrawDetailObj domainInstance = new VWelfareDrawDetailObj(); 
	// 该功能基本路径 
	String basePath = domainInstance.getBasePath(); 
 
	// 如果是修改，则获取对象信息 
	Object domainInstanceObj = request.getAttribute("domainInstance"); 
	if (domainInstanceObj != null) 
	{ 
		domainInstance = (VWelfareDrawDetailObj) domainInstanceObj; 
	} 
 
	// 是否是修改 
	boolean isModify = domainInstance.getKeyValue() > 0; 
	// 唯一性检查用的字段 
	String keyCol = "null"; 
%> 
<html> 
	<head> 
		<base target="_self" /> 
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
		<title><%=isModify ? "修改" + domainInstance.getCnName() : "增加" + domainInstance.getCnName()%></title> 
		<link href="../css/global.css" rel="stylesheet" type="text/css"> 
		<link href="../css/table.css" rel="stylesheet" type="text/css"> 
		<script type="text/javascript" src="../js/jquery-2.0.3.min.js"></script> 
		<script type="text/javascript" src="../js/utils.js"></script> 
		<script type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script> 
		<script> 
		//  新增或修改 
		function addOrModify() 
		{	 
				// 做必要的检查 
		if(!checkNull("id","<%=domainInstance.getPropertyCnName("id")%>")) return false; 
		if(!checkNull("draw_id","<%=domainInstance.getPropertyCnName("draw_id")%>")) return false; 
		if(!checkNull("t1_billsn","<%=domainInstance.getPropertyCnName("t1_billsn")%>")) return false; 
		if(!checkNull("villager_id","<%=domainInstance.getPropertyCnName("villager_id")%>")) return false; 
		if(!checkNull("villager_name","<%=domainInstance.getPropertyCnName("villager_name")%>")) return false; 
		if(!checkNull("id_card","<%=domainInstance.getPropertyCnName("id_card")%>")) return false; 
		if(!checkNull("welfare_policy_id","<%=domainInstance.getPropertyCnName("welfare_policy_id")%>")) return false; 
		if(!checkNull("welfare_policy_name","<%=domainInstance.getPropertyCnName("welfare_policy_name")%>")) return false; 
		if(!checkNull("welfare_policy_start_time","<%=domainInstance.getPropertyCnName("welfare_policy_start_time")%>")) return false; 
		if(!checkNull("welfare_policy_end_time","<%=domainInstance.getPropertyCnName("welfare_policy_end_time")%>")) return false; 
		if(!checkNull("draw_type","<%=domainInstance.getPropertyCnName("draw_type")%>")) return false; 
		if(!checkNull("draw_date","<%=domainInstance.getPropertyCnName("draw_date")%>")) return false; 
		if(!checkNull("product_id","<%=domainInstance.getPropertyCnName("product_id")%>")) return false; 
		if(!checkNull("product_name","<%=domainInstance.getPropertyCnName("product_name")%>")) return false; 
		if(!checkNull("product_spec","<%=domainInstance.getPropertyCnName("product_spec")%>")) return false; 
		if(!checkNull("product_measuring_unit","<%=domainInstance.getPropertyCnName("product_measuring_unit")%>")) return false; 
		if(!checkNull("product_price","<%=domainInstance.getPropertyCnName("product_price")%>")) return false; 
		if(!checkNull("product_quantity","<%=domainInstance.getPropertyCnName("product_quantity")%>")) return false; 
		if(!checkNull("product_quantity_drawed","<%=domainInstance.getPropertyCnName("product_quantity_drawed")%>")) return false; 
		if(!checkNull("draw_villager_id","<%=domainInstance.getPropertyCnName("draw_villager_id")%>")) return false; 
		if(!checkNull("draw_villager_name","<%=domainInstance.getPropertyCnName("draw_villager_name")%>")) return false; 
		if(!checkNull("draw_villager_id_card","<%=domainInstance.getPropertyCnName("draw_villager_id_card")%>")) return false; 
		if(!checkNull("proxy_villager_name","<%=domainInstance.getPropertyCnName("proxy_villager_name")%>")) return false; 
		if(!checkNull("draw_comment","<%=domainInstance.getPropertyCnName("draw_comment")%>")) return false; 
		if(!checkNull("villager_welfare_id","<%=domainInstance.getPropertyCnName("villager_welfare_id")%>")) return false; 
		if(!checkNull("welfare_policy_detail_id","<%=domainInstance.getPropertyCnName("welfare_policy_detail_id")%>")) return false; 
					 
			// 修改 
			if("true"=="<%=isModify%>") 
			{ 
				submit(); 
			} 
			// 新增 
			else 
			{ 
					// 新增时检查唯一性 
					$.post( 
					encodeURI("Servlet?method=checkId4this&isFromUrl=true&<%=keyCol%>="+$("#<%=keyCol%>").val()),  
					{Action:"post"},  
					function (data, textStatus){ 
						this; 
						if(data=="true")  
						{ 
							alert("该<%=domainInstance.getPropertyCnName(keyCol)%>已录入系统");  
							return false; 
						} 
						else 
						{ 
							submit(); 
						} 
					}); 
				}; 
		} 
		 
		// 提交保存或修改 
		function submit() 
		{ 
					$("#addOrModifyForm").submit(); 
		} 
		</script> 
	</head> 
	<body> 
		<form name="addOrModifyForm" id="addOrModifyForm" action="<%=contextPath%>/<%=basePath%>/Servlet?method=addOrModify4this" method="post"> 
			<!-- 表格标题 --> 
			<table width="700" align="center" class="title_table"> 
				<tr> 
					<td> 
						<img src="../images/svg/heavy/green/user_list.png" width="18" height="18" align="absmiddle"> 
						&nbsp;&nbsp;<%=isModify ? "修改" : "增加"%><%=domainInstance.getCnName()%> 
					</td> 
				</tr> 
			</table> 
 
			<!-- 详细信息 --> 
			<table width="700" align="center" class="detail_table detail_table-bordered detail_table-striped"> 
				<input type="hidden" id="<%=domainInstance.findKeyColumnName()%>" name="<%=domainInstance.findKeyColumnName()%>" value="<%=domainInstance.getKeyValue()%>"> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("draw_id") %>: 
					</td> 
					<td> 
						<input name="draw_id" type="text" id="draw_id" value="<%=StringUtil.getNotEmptyStr(domainInstance.getDraw_id(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("t1_billsn") %>: 
					</td> 
					<td> 
						<input name="t1_billsn" type="text" id="t1_billsn" value="<%=StringUtil.getNotEmptyStr(domainInstance.getT1_billsn(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("villager_name") %>: 
					</td> 
					<td> 
						<input name="villager_name" type="text" id="villager_name" value="<%=StringUtil.getNotEmptyStr(domainInstance.getVillager_name(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("id_card") %>: 
					</td> 
					<td> 
						<input name="id_card" type="text" id="id_card" value="<%=StringUtil.getNotEmptyStr(domainInstance.getId_card(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("welfare_policy_name") %>: 
					</td> 
					<td> 
						<input name="welfare_policy_name" type="text" id="welfare_policy_name" value="<%=StringUtil.getNotEmptyStr(domainInstance.getWelfare_policy_name(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("welfare_policy_start_time") %>: 
					</td> 
					<td> 
						<input name="welfare_policy_start_time" type="text" id="welfare_policy_start_time" value="<%=StringUtil.getNotEmptyStr(domainInstance.getWelfare_policy_start_time(),"")%>" size="20" onFocus="WdatePicker({isShowClear:false,readOnly:false,highLineWeekDay:true,dateFmt:'yyyy-MM-dd'})" > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("welfare_policy_end_time") %>: 
					</td> 
					<td> 
						<input name="welfare_policy_end_time" type="text" id="welfare_policy_end_time" value="<%=StringUtil.getNotEmptyStr(domainInstance.getWelfare_policy_end_time(),"")%>" size="20" onFocus="WdatePicker({isShowClear:false,readOnly:false,highLineWeekDay:true,dateFmt:'yyyy-MM-dd'})" > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("draw_type") %>: 
					</td> 
					<td> 
						<input name="draw_type" type="text" id="draw_type" value="<%=StringUtil.getNotEmptyStr(domainInstance.getDraw_type(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("draw_date") %>: 
					</td> 
					<td> 
						<input name="draw_date" type="text" id="draw_date" value="<%=StringUtil.getNotEmptyStr(domainInstance.getDraw_date(),"")%>" size="20" onFocus="WdatePicker({isShowClear:false,readOnly:false,highLineWeekDay:true,dateFmt:'yyyy-MM-dd'})" > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("product_name") %>: 
					</td> 
					<td> 
						<input name="product_name" type="text" id="product_name" value="<%=StringUtil.getNotEmptyStr(domainInstance.getProduct_name(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("product_spec") %>: 
					</td> 
					<td> 
						<input name="product_spec" type="text" id="product_spec" value="<%=StringUtil.getNotEmptyStr(domainInstance.getProduct_spec(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("product_measuring_unit") %>: 
					</td> 
					<td> 
						<input name="product_measuring_unit" type="text" id="product_measuring_unit" value="<%=StringUtil.getNotEmptyStr(domainInstance.getProduct_measuring_unit(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("product_price") %>: 
					</td> 
					<td> 
						<input name="product_price" type="text" id="product_price" value="<%=StringUtil.getNotEmptyStr(domainInstance.getProduct_price(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("product_quantity") %>: 
					</td> 
					<td> 
						<input name="product_quantity" type="text" id="product_quantity" value="<%=StringUtil.getNotEmptyStr(domainInstance.getProduct_quantity(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("product_quantity_drawed") %>: 
					</td> 
					<td> 
						<input name="product_quantity_drawed" type="text" id="product_quantity_drawed" value="<%=StringUtil.getNotEmptyStr(domainInstance.getProduct_quantity_drawed(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("draw_villager_name") %>: 
					</td> 
					<td> 
						<input name="draw_villager_name" type="text" id="draw_villager_name" value="<%=StringUtil.getNotEmptyStr(domainInstance.getDraw_villager_name(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("draw_villager_id_card") %>: 
					</td> 
					<td> 
						<input name="draw_villager_id_card" type="text" id="draw_villager_id_card" value="<%=StringUtil.getNotEmptyStr(domainInstance.getDraw_villager_id_card(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("proxy_villager_name") %>: 
					</td> 
					<td> 
						<input name="proxy_villager_name" type="text" id="proxy_villager_name" value="<%=StringUtil.getNotEmptyStr(domainInstance.getProxy_villager_name(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
				<tr> 
					<td> 
						<%=domainInstance.getPropertyCnName("draw_comment") %>: 
					</td> 
					<td> 
						<input name="draw_comment" type="text" id="draw_comment" value="<%=StringUtil.getNotEmptyStr(domainInstance.getDraw_comment(),"")%>" size="20"  > 
						<font color="red">*</font> 
					</td> 
				</tr> 
			</table> 
			 
			<!-- 工具栏 --> 
			<jsp:include page="../ToolBar/addOrModify_toolbar.jsp" /> 
		</form> 
	</body> 
</html> 
