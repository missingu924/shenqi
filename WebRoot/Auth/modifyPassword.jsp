<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@page import="com.hz.auth.obj.AuthUser"%>
<%@page import="com.wuyg.common.util.RequestUtil"%>
<%@page import="com.hz.util.SystemConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>修改密码</title>
		<link href="../style.css" rel="stylesheet" type="text/css" />
	</head>

	<script src="../js/jquery-2.0.3.min.js"></script>
	<script
		src="../jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="../js/utils.js"></script>
	<script type="text/javascript">
	function modifyPassword()
	{
		if($("#oldPwd").val()==null)
		{
			alert("请输入原来的密码");
			return;
		}
		
		if($("#newPwd1").val()==null)
		{
			alert("第一次设置的新密码不能为空");
			return;
		}
		
		if($("#newPwd2").val()==null)
		{
			alert("第二次设置的新密码不能为空");
			return;
		}
		
		if($("#newPwd1").val()!=$("#newPwd2").val())
		{
			alert("两次输入的新密码不一致");
			return;
		}
	
		$.ajax({   
			type:"post",     
			url:"Servlet?method=modifyPassword",   
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
	<link rel="stylesheet"
		href="../jquery-ui-1.10.3.custom/css/ui-lightness/jquery-ui-1.10.3.custom.min.css" />
	
	<%
		AuthUser userInfo = (AuthUser) request.getAttribute(SystemConstant.AUTH_USER_INFO);
		
		String tableWidth = "500";
		boolean is4m = RequestUtil.is4m(request);
		if(is4m) tableWidth="96%";
	%>
	<!-- 头文件 -->
	<%if(is4m){ %>
	<jsp:include page="../mobileHeader.jsp">
		<jsp:param name="menuId" value="xgmm"/>
	</jsp:include>
	<%} %>
	<body>
	        <form id="mainForm" name="form1" method="post" action="servlet?method=modifyPassword">
			<div style="height:8px;"></div>
        <table width="<%=tableWidth %>" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#3DAEB6">
          <tr>
            <td height="4" bgcolor="#3DAEB6"></td>
          </tr>
          <tr>
            <td height="25" align="center" class="green_font">修改密码</td>
          </tr>
        </table>
        <table width="<%=tableWidth %>" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#3DAEB6">
          <tr>
            <td bgcolor="#FFFFFF"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr class="list_table_tr0">
                <td align="right" class="little_gray_font">现在的密码:</td>
                <td><input name="oldPwd" type="password" class="input_box" id="oldPwd" /></td>
              </tr>
              <tr class="list_table_tr2">
                <td align="right" class="little_gray_font">设置新的密码:</td>
                <td><input name="newPwd1" type="password" class="input_box" id="newPwd1" /></td>
              </tr>
              <tr class="list_table_tr2">
                <td align="right" class="little_gray_font">重复新的密码:</td>
                <td><input name="newPwd2" type="password" class="input_box" id="newPwd2" /></td>
              </tr>
            </table></td>
          </tr>
        </table>
        <table width="<%=tableWidth %>" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td height="30" align="center"  class="tab_bg"><input name="button" type="button" class="green_button" value="修改"
						onclick="javascript:modifyPassword();" /></td>
          </tr>
        </table>
	        </form>
		<br/>
		<div align="center" id="result" style="color:#FF6600"></div>
</body>
</html>
