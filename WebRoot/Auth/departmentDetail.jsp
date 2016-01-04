<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@page import="com.hz.auth.obj.AuthUser"%>
<%@page import="com.hz.util.SystemConstant"%>
<%@page import="com.hz.auth.obj.AuthDepartment"%>
<%@page import="com.wuyg.common.util.StringUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>部门详细信息</title>
		<link href="../style.css" rel="stylesheet" type="text/css" />
	</head>

	<script src="../js/jquery-2.0.3.min.js"></script>
	<script src="../js/utils.js"></script>
	<%
		String contextPath = request.getContextPath();
	
		AuthUser userInfo = (AuthUser) request.getAttribute(SystemConstant.AUTH_USER_INFO);
		
		AuthDepartment department = (AuthDepartment)request.getAttribute("department");
	%>
	<body>
	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#258ece">
      <tr>
        <td height="4" bgcolor="#258ece"></td>
      </tr>
      <tr>
        <td height="25" align="center" class="blue_font">部门详请</td>
      </tr>
    </table>
	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#258ece">
      <tr>
        <td bgcolor="#FFFFFF"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr class="list_table_head_blue">
            <td height="25" colspan="2">&gt;部门基本信息</td>
          </tr>
          <tr class="list_table_tr0">
            <td width="100" align="right"><strong>部门编号：</strong></td>
            <td><%=department.getDepartmentId() %></td>
          </tr>
          <tr class="list_table_tr2">
            <td align="right"><strong>部门名称：</strong></td>
            <td><%=department.getDepartmentName() %></td>
          </tr>
          <tr class="list_table_tr0">
            <td align="right"><strong>所属地市：</strong></td>
            <td><%=StringUtil.getNotEmptyStr(department.getCity()) %></td>
          </tr>
          <tr class="list_table_tr2">
            <td align="right"><strong>所属区县：</strong></td>
            <td><%=department.getDistrictName() %></td>
          </tr>
          <tr class="list_table_tr0">
            <td align="right"><strong>其他描述：</strong></td>
            <td><%=StringUtil.getNotEmptyStr(department.getCommentInfo()) %><br /></td>
          </tr>
        </table></td>
      </tr>
    </table>
	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#258ece">
	      <tr class="list_table_head_blue">
        <td colspan="5">&gt;部门人员信息</td>
      </tr>
      <tr class="list_table_head">
        <td>姓名</td>
        <td>账号</td>
        <td>职务</td>
        <td>联系电话</td>
		<td>性别</td>
      </tr>
      <%for(int i=0;i<department.getUsers().size();i++){ 
      	AuthUser user = department.getUsers().get(i);
      %>
      <tr class="<%=i%2==0?"list_table_tr0":"list_table_tr1" %>">
        <td><a href="#" onclick="openBigModalDialog('<%=contextPath %>/Auth/Servlet?method=userDetail&account=<%=user.getAccount() %>')"><%=user.getName() %></a></td>
        <td><%=user.getAccount() %></td>
        <td><%=user.getOffice() %></td>
        <td><%=user.getTelephone() %></td>
		<td><img
						src="<%="女".equalsIgnoreCase(user.getSex()) ? "../images/female02.png" : "../images/male02.png"%>"
						width="22" height="22" /></td>
      </tr>
      <%} %>
    </table>
	<table width="95%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#258ece">
            <tr >
              <td height="30" align="center" class="tab_bg"><input name="Submit2" type="button" class="gray_button"
										value="关闭" onclick="javascript:window.close();" /></td>
            </tr>
</table>

	</body>
</html>
