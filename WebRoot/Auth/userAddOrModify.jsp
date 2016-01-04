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

		// 用户信息
		AuthUser user = (AuthUser) request.getSession().getAttribute(SystemConstant.AUTH_USER_INFO);

		// 获取对象信息，用于进行对象修改，如果是新增对象，那么初始化的时候要把相关属性的默认值设好
		AuthUser o = new AuthUser();

		// 初始化属性信息
		o.setCity("菏泽");

		// 如果是修改，则获取对象信息
		String account = request.getParameter("account");
		if (account != null)
		{
			o = new AuthService().getUserInfoByAccount(account);
		}

		// 字典服务
		IDictionaryService dict = new DictionaryService();

		// 是否是修改
		boolean isModify = !StringUtil.isEmpty(o.getAccount());
	%>
	<head>
		<base target="_self" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><%=isModify ? "修改人员信息" : "增加人员"%></title>
		<link href="../style.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/jquery-2.0.3.min.js"></script>
		<script type="text/javascript" src="../js/utils.js"></script>
		<script>
		//  新增或修改
		function addOrModify()
		{	
			// 修改账号
			if("true"=="<%=isModify%>")
			{
				submit();
			}
			// 新建账号
			else
			{
				// 先检查账号格式
				if(!checkAccount("account","账号")) 
				{
					return false;
				} 
				else 
				{
				// 然后检查账号是否已被使用
					$.get(
					"Servlet?method=checkAccount&account="+$("#account").val(), 
					{Action:"get"}, 
					function (data, textStatus){
						this;
						if(data=="true") 
						{
							alert("该账号已被使用，请您选用其他的账号。"); 
							return false;
						}
						else
						{
							submit();
						}
					});
				};
			}
		}
		
		// 提交保存或修改
		function submit()
		{
						// 如果账号符合要求且未被使用，则监测其他的项
						if(!checkNull("password","密码")) return false;
						if(!checkNull("district","供应商")) return false;
						$("#addOrModifyForm").submit();
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
		<form name="addOrModifyForm" id="addOrModifyForm"
			action="<%=contextPath%>/Auth/Servlet?method=userAddOrModify"
			method="post">
						<div style="height:8px;"></div>
        <table width="500" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#3DAEB6">
          <tr>
            <td height="4" bgcolor="#3DAEB6"></td>
          </tr>
          <tr>
            <td height="25" align="center" class="green_font">添加账号</td>
          </tr>
        </table>
			<table width="500" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#3DAEB6">

				
				<tr>
					<td bgcolor="#FFFFFF">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr class="list_table_tr0">
								<td width="130" height="30" align="right" class="little_gray_font">
									账号:								</td>
								<td>
									<label>
										<input name="id" type="hidden" id="id"
											value="<%=StringUtil.getNotEmptyStr(o.getId())%>">
										<input name="account" type="text" id="account"
											value="<%=StringUtil.getNotEmptyStr(o.getAccount())%>"
											<%=isModify ? "readonly" : ""%>>

										<%
											if (isModify)
											{
										%>
										<span class="STYLE1">账号不可修改</span>
										<%
											} else
											{
										%>
										<div>
											5~18个字符，可使用字母、数字、下划线，需以字母开头
										</div>
										<%
											}
										%>
									
								</td>
							</tr>
							<tr class="list_table_tr2">
								<td width="130" height="30" align="right" class="little_gray_font">
									密码:								</td>
								<td>
									<label>
										<input name="password" type="text" id="password"
											value="<%=StringUtil.getNotEmptyStr(o.getPassword())%>"
											class="notEmpty">
									</label>
								</td>
							</tr>
							<tr class="list_table_tr0">
								<td width="130" height="30" align="right" class="little_gray_font">
									姓名:								</td>
								<td>
									<label>
										<input name="name" type="text" id="name"
											value="<%=StringUtil.getNotEmptyStr(o.getName())%>">
									</label>
								</td>
							</tr>
							<tr class="list_table_tr2">
								<td width="130" height="30" align="right" class="little_gray_font">
									联系电话:								</td>
								<td>
									<label>
										<input name="telephone" type="text" id="telephone"
											value="<%=StringUtil.getNotEmptyStr(o.getTelephone())%>">
									</label>
								</td>
							</tr>
							<tr class="list_table_tr0">
								<td width="130" height="30" align="right" class="little_gray_font">
									 供应商:								</td>
								<td>
									<%=DictionaryUtil.getSelectHtml(new DictionaryService().getDictItemsByDictName("vendor", true), "district", "供应商", StringUtil
							.getNotEmptyStr(o.getDistrict()), "notEmpty")%>
								</td>
							</tr>
							<!-- 
							<tr class="list_table_tr2">
								<td width="130" height="30" align="right">
									 权限级别: 
								</td>
								<td>
									<%=DictionaryUtil.getSelectHtml(dict.getDictItemsByDictName("userRole", false), "roleLevel", "权限级别", o.getRoleLevel(), "notEmpty")%>
								</td>
							</tr>
							 -->

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
		    <p class="little_gray_font">&nbsp;</p>
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
