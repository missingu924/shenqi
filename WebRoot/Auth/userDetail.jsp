<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@page import="com.hz.auth.obj.AuthUser"%>
<%@page import="com.hz.util.SystemConstant"%>
<%@page import="com.wuyg.task.TaskConstant"%>
<%@page import="com.wuyg.common.util.RequestUtil"%>
<%@page import="com.wuyg.common.util.StringUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>我的账号</title>
		<link href="../style.css" rel="stylesheet" type="text/css" /></head>

	<script src="../js/jquery-2.0.3.min.js"></script>
	<script src="../js/utils.js"></script>
	<%
		AuthUser userInfo = (AuthUser) request.getAttribute(SystemConstant.AUTH_USER_INFO);
		
		String tableWidth = "500";
		boolean is4m = RequestUtil.is4m(request);
		if(is4m) tableWidth="96%";
	%>
	<body>
	<!-- 头文件 -->
	<%if(is4m){ %>
	<jsp:include page="../mobileHeader.jsp">
		<jsp:param name="menuId" value="wdzh"/>
	</jsp:include>
	<%} %>
	<div style="height:8px;"></div>
	<table width="<%=tableWidth %>" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#3DAEB6">
      <tr>
        <td height="4" bgcolor="#3DAEB6"></td>
      </tr>
      <tr>
        <td height="25" align="center" class="green_font">我的账号</td>
      </tr>
    </table>
	<table width="<%=tableWidth %>" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#3DAEB6">
      <tr>
        <td bgcolor="#FFFFFF"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr class="list_table_tr0">
              <td width="100" height="30" align="right" class="little_gray_font">账号:</td>
              <td><%=userInfo.getAccount() %></td>
            </tr>
            <tr class="list_table_tr2">
              <td height="30" align="right" class="little_gray_font">姓名:</td>
              <td><%=userInfo.getName() %></td>
            </tr>
            <tr class="list_table_tr0">
              <td height="30" align="right" class="little_gray_font">联系电话:</td>
              <td><%=StringUtil.getNotEmptyStr(userInfo.getTelephone()) %></td>
            </tr>
			 <tr class="list_table_tr2">
              <td height="30" align="right" class="little_gray_font">供应商:</td>
              <td><%=userInfo.getDistrict() %></td>
            </tr>
            <!-- 
            <tr class="list_table_tr0">
              <td height="30" align="right">权限级别：</strong></td>
              <td><%=userInfo.getRoleLevel() %></td>
            </tr>
            -->
            <tr >
            
              <td height="30" colspan="2" align="center" class="tab_bg">&nbsp;</td>
            </tr>
        </table></td>
      </tr>
    </table>
	</body>
</html>
