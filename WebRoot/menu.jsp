<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@page import="com.hz.auth.obj.AuthUser"%>
<%@page import="com.hz.util.SystemConstant"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="com.wuyg.task.TaskConstant"%>
<%@page import="com.wuyg.common.util.TimeUtil"%>
<%@page import="com.wuyg.common.util.StringUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>系统菜单</title>
		<link href="css/global.css" rel="stylesheet" type="text/css" />
		<style type="text/css">
<!--
body {
	background-color: #D8F2F3;
	margin-top: 0px;
}
-->
</style>
	</head>
	<script src="js/jquery-2.0.3.min.js" type="text/javascript"></script>
	<script>
function openInMainFrame(td,url)
{	
	$(".menu_selected").attr("class","menu_not_selected");
	$(td).attr("class","menu_selected");
	parent.mainFrame.location = url;
}

function toggleMenuGroup(td,menuGroupId)
{
	$("#menu_group_" + menuGroupId).toggle();
	if($(td).attr("class")=="menu_header_not_expand")
	{
		$(td).attr("class","menu_header_expand");
	}
	else
	{
		$(td).attr("class","menu_header_not_expand");
	}
}
</script>
	<%
		String contextPath = request.getContextPath();

		// 用户信息
		AuthUser user = (AuthUser) request.getSession().getAttribute(SystemConstant.AUTH_USER_INFO);

		boolean isAdmin = SystemConstant.ROLE_ADMIN.equals(user.getRoleLevel());
	%>
	<body>
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td class="menu_header_expand"
					onclick="toggleMenuGroup(this,'01')">
					神器广场
				</td>
			</tr>
			<tr>
				<td>

					<table id="menu_group_01" width="100%" cellpadding="0"
						cellspacing="0" border="0">
						<tr>
							<td class="menu_selected"
								onclick="openInMainFrame(this,'<%=request.getContextPath()%>/DdModel/list.jsp')">
								<img src="images/svg/heavy/green/tools.png" width="18"
									height="18" align="middle" />
								&nbsp;&nbsp;代码生成神器
							</td>
						</tr>
					</table>

				</td>
			</tr>
			<%
				if (isAdmin)
				{
			%>
			<tr>
				<td class="menu_header_not_expand"
					onclick="toggleMenuGroup(this,'02')">
					个人管理
				</td>
			</tr>
			<tr>
				<td>
					<table id="menu_group_02" width="100%" cellpadding="0"
						cellspacing="0" border="0" class="hidden">
						<tr>
							<td class="menu_not_selected"
								onclick="openInMainFrame(this,'Auth/Servlet?method=userDetail&account=<%=user.getAccount()%>')">
								<img src="images/svg/heavy/green/user.png" width="18"
									height="18" align="middle" />
								&nbsp;&nbsp;我的账号
							</td>
						</tr>
						<tr>
							<td class="menu_not_selected"
								onclick="openInMainFrame(this,'Auth/modifyPassword.jsp')">
								<img src="images/svg/heavy/green/locked.png" width="18"
									height="18" align="middle" />
								&nbsp;&nbsp;修改密码
							</td>
						</tr>
						<%
							}
						%>
						<%
							if (isAdmin)
							{
						%>
						<!-- 
  <tr>
    <td height="36"class="menu_header">系统管理</td>
  </tr>
  <tr>
    <td class="menu_not_selected" onclick="openInMainFrame(this,'Auth/Servlet?method=userList')"><img src="images/4917/18.png" width="18" height="18" align="middle" />&nbsp;&nbsp;管理账号</td>
   </tr>
  <tr>
    <td class="menu_not_selected" onclick="openInMainFrame(this,'System/Servlet?method=preDbAddOrModify')"><img src="images/4917/18.png" width="18" height="18" align="middle" />&nbsp;&nbsp;管理账套</td>
  </tr>
   -->
						<%
							}
						%>
					</table>
				</td>
			</tr>
		</table>
	</body>