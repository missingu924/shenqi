<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@page import="com.wuyg.common.util.StringUtil"%>
<%@page import="com.wuyg.common.util.TimeUtil"%>
<%@page import="java.util.Date"%>
<%@page import="com.wuyg.common.licence.LicenceUtil"%>
<%@page import="com.wuyg.common.licence.LicenseConstant"%>
<%@page import="com.wuyg.common.licence.LicenseGather"%>
<%@page import="com.wuyg.common.licence.MachineInfoObj"%>

	<%
		String info = StringUtil.getNotEmptyStr(request.getParameter("info"));
	
		// 检查许可
		boolean licenceSucc = false;//许可成功
		String checkResult = LicenceUtil.check();
		if(LicenseConstant.MSG_LIC_SUCC.equals(checkResult))
		{
			licenceSucc = true;
		}
		
		if(!licenceSucc)
		{
			info = "<span class=\"infoStyle\">"+checkResult+"，请复制以下内容并发送至开发商获取授权<br/><br/></span>"+LicenceUtil.encode(LicenseGather.getThisMachineInfo());
		}
		else if ("needReLogin".equalsIgnoreCase(info))
		{
			info = "请重新登录！";
		}
		
		// 读取许可信息
		MachineInfoObj licensedMachine = null;
		try
		{
			licensedMachine = LicenceUtil.getLicencedMachine();
			if(licensedMachine == null)
			{
				licensedMachine = new MachineInfoObj();
			}
		}
		catch(Exception e)
		{
		}
	%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>神器集结地</title>
		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
        </style>
		<link href="css/global.css" rel="stylesheet" type="text/css">
		<style type="text/css">
.STYLE3 {font-size: 16px}
.infoStyle {
	font-family: "黑体";
	font-size: 20px;
	color:#FF0000 ;
}
.infoLittleStyle {
	font-family: "宋体";
	font-size: 12px;
	color:#FF0000 ;
}
.STYLE4 {
	font-size: 36px;
	font-weight: bold;
}
.STYLE5 {
	font-family: "黑体";
	font-size: 36px;
	color: #FF0000;
	font-weight: bold;
}
-->
        </style>
	</head>
<script type="text/javascript" src="js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="../js/jquery-2.0.3.min.js"></script>
<script src="js/utils.js"></script>
	<body>
	<div style="display:<%=(!licenceSucc)?"none":"" %>">
		<form id="loginForm" name="loginForm" method="post"
			action="Auth/Servlet?method=login">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="10" height="100"></td>
					<td colspan="4" align="center"><%=info%></td>
					<td width="10"></td>
				</tr>
				<tr>
					<td></td>
					<td width="15%" height="60" align="left">&nbsp;</td>
					<td align="left"><img src="images/SystemName.png" width="296" height="56"></td>
					<td align="right" class="little_gray_font">神器神器 无比犀利</td>
					<td width="15%" align="left" class="little_gray_font">&nbsp;</td>
					<td></td>
				</tr>
				<tr>
					<td width="0"></td>
					<td height="360" align="center" background="images/background/login_new.jpg">&nbsp;</td>
					<td height="360" align="center" background="images/background/login_new.jpg"></td>
					<td height="360" align="right" background="images/background/login_new.jpg"><table width="320" border="0" cellpadding="0" cellspacing="0"
							bgcolor="#FFFFFF">
                      <tr>
                        <td height="10" align="center">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td bgcolor="#FFFFFF" height="60">&nbsp;</td>
                              <td width="239" align="center"><span class="STYLE3">系统登录</span></td>
                              <td bgcolor="#FFFFFF">&nbsp;</td>
                            </tr>
                        </table>
                        </td>
                      </tr>
                      <tr>
                        <td style="border-top:5px solid #3daeb6" ></td>
                      </tr>
                      <tr>
                        <td height="50" align="center">账号:&nbsp;&nbsp;
                            <input name="account" type="text" 
										id="account" size="25" /></td>
                      </tr>
                      <tr>
                      <tr>
                        <td align="center" height="1px" background="#CCCCCC"></td>
                      </tr>
                      <tr>
                        <td height="50" align="center">密码:&nbsp;&nbsp;
                            <input name="password" type="password"
										 id="password" size="26" /></td>
                      </tr>
                      <tr>
                        <td height="5" align="right" bgcolor="#FFFFFF"></td>
                      </tr>
                      <tr>
                        <td align="center" valign="middle" bgcolor="#FFFFFF">
                        <table width="60%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                              <td height="40"></td>
                              <td height="40" align="center" class="green_button"
												onclick="login()"> 登&nbsp;&nbsp;&nbsp;&nbsp;录 </td>
                              <td></td>
                            </tr>
                        </table>
                        </td>
                      </tr>
                      <tr>
                        <td height="10" align="right" bgcolor="#FFFFFF"></td>
                      </tr>
                    </table>
                    </td>
					<td height="360" align="center" background="images/background/login_new.jpg">&nbsp;</td>
					<td width="0"></td>
				</tr>
				<tr>
				  <td height="50" align="center" class="little_gray_font">&nbsp;</td>
				  <td width="15%" height="60" align="center" class="little_gray_font">&nbsp;</td>
				  <td height="50" align="left" class="little_gray_font">请使用1024*768以上分辨率</td>
				  <td height="50" align="right" class="little_gray_font">授权给武玉刚使用&nbsp;&nbsp;|&nbsp;&nbsp;武玉刚技术支持</td>
				  <td width="15%" align="center" class="little_gray_font">&nbsp;</td>
				  <td height="50" align="center" class="little_gray_font">&nbsp;</td>
				</tr>
		  </table>
		</form>
		</div>
		<div style="display:<%=(!licenceSucc)?"":"none"%>">
			<table width="100%" height="400" border="0" cellpadding="0" cellspacing="0">
			  <tr>
				<td align="center" valign="middle"><%=info %></td>
			  </tr>
			</table>
		</div>
	</body>
<script type="text/javascript">
	// 登录
	function login()
	{
		if(!checkNull("account","用户名")) return;
		//if(!checkNull("password","密码")) return;
		
		$('#loginForm').submit();
	}
	
	// 光标定位在第一个输入框
	$('input:text:first').focus();
        
	// 绑定enter事件
	$('body').keydown(function(){
	   if(event.keyCode == 13)
	   {
		 login();
	   }
	});
	
	if(parent.frames["mainFrame"])
		parent.location.replace(parent.location.href);
</script>
</html>
