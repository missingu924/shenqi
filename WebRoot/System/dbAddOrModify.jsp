<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@page import="com.hz.auth.obj.AuthUser"%>
<%@page import="java.util.List"%>
<%@page import="com.hz.util.SystemConstant"%>
<%@page import="com.wuyg.common.util.StringUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.wuyg.common.obj.PaginationObj"%>
<%@page import="com.hz.auth.obj.AuthDepartment"%>
<%@page import="com.inspur.common.dictionary.util.DictionaryUtil"%>
<%@page import="com.hz.dict.service.DictionaryService"%>
<%@page import="com.hz.auth.service.AuthService"%>
<%@page import="com.hz.dict.service.IDictionaryService"%>
<%@page import="java.net.URLEncoder"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<%
		String contextPath = request.getContextPath();

		String ip =  StringUtil.getNotEmptyStr(request.getAttribute("ip"));
		String port =  StringUtil.getNotEmptyStr(request.getAttribute("port"));
		String dbUser =  StringUtil.getNotEmptyStr(request.getAttribute("dbUser"));
		String dbPassword=  StringUtil.getNotEmptyStr(request.getAttribute("dbPassword"));
		String dbName =  StringUtil.getNotEmptyStr(request.getAttribute("dbName"));
	%>
	<head>
		<base target="_self" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>账套管理</title>
		<link href="../style.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/jquery-2.0.3.min.js"></script>
		<script type="text/javascript" src="../js/utils.js"></script>
		<script>
		//  新增或修改
		function addOrModify()
		{
						// 如果账号符合要求且未被使用，则监测其他的项
						if(!checkAllNotEmptyInput()) return false;
						
			$.ajax({   
			type:"post",     
			url:$('#mainForm').attr("action"),   
			data:$('#mainForm').serialize(),
			success:function(result){     
				 $("#result").html(result);     
			 },   
			 error:function(){   
				 alert("出现了点小问题哦");   
			 }   
		 }); 
		}
		</script>
		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>
	</head>
	<body>
		<form name="mainForm" id="mainForm"
			action="<%=contextPath%>/System/Servlet?method=dbAddOrModify"
			method="post">
						<div style="height:8px;"></div>
        <table width="500" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#3DAEB6">
          <tr>
            <td height="4" bgcolor="#3DAEB6"></td>
          </tr>
          <tr>
            <td height="25" align="center" class="green_font">账套管理</td>
          </tr>
        </table>
			<table width="500" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#3DAEB6">

				
				<tr>
					<td bgcolor="#FFFFFF">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr class="list_table_tr0">
								<td width="130" height="30" align="right" class="little_gray_font"><p>IP: </p>
							    </td>
								<td>
								<input name="ip" type="text" id="ip"
											value="<%=ip%>" class="notEmpty" label="IP"
											></td>
							</tr>
							<tr class="list_table_tr2">
								<td width="130" height="30" align="right" class="little_gray_font">端口:								</td>
								<td>
									<label>
										<input name="port" type="text" id="port" label="端口"
											value="<%=StringUtil.getNotEmptyStr(port)%>"
											class="notEmpty">
									</label>
								</td>
							</tr>
							<tr class="list_table_tr0">
								<td width="130" height="30" align="right" class="little_gray_font">用户名:								</td>
								<td>
									<label>
										<input name="dbUser" type="text" id="dbUser"
											value="<%=StringUtil.getNotEmptyStr(dbUser)%>" class="notEmpty"  label="用户名">
									</label>
								</td>
							</tr>
							<tr class="list_table_tr2">
								<td width="130" height="30" align="right" class="little_gray_font">密码:								</td>
								<td>
									<label>
										<input name="dbPassword" type="password" class="notEmpty" id="dbPassword" value="<%=StringUtil.getNotEmptyStr(dbPassword)%>" label="密码">
									</label>
								</td>
							</tr>
							<tr class="list_table_tr0">
								<td width="130" height="30" align="right" class="little_gray_font">账套:								</td>
								<td>
									<label>
										<input name="dbName" type="text" id="dbName"
											value="<%=StringUtil.getNotEmptyStr(dbName)%>" class="notEmpty"  label="账套">
									</label>
								</td>
							</tr>
							<tr class="tab_bg">
								<td height="30" colspan="2" align="center">
									<input name="Submit" type="button" class="gray_button"
										value="取消" onClick="javascript:window.close();">
									&nbsp;

									<input name="Submit2" type="button" class="green_button"
										value="保存" onClick="addOrModify()">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		    <div align="center" id="result" style="color:#FF6600"></div>
		</form>
		<script>
			// 绑定enter事件
			$('body').keydown(function(){
			   if(event.keyCode == 13)
			   {
				 addOrModify();
			   }
			});
		</script>
	</body>
</html>
