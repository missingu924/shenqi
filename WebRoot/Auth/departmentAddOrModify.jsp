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
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<%
		String contextPath = request.getContextPath();

		// 用户信息
		AuthUser user = (AuthUser) request.getSession().getAttribute(SystemConstant.AUTH_USER_INFO);

		// 获取对象信息，用于进行对象修改，如果是新增对象，那么初始化的时候要把相关属性的默认值设好
		AuthDepartment o = new AuthDepartment();

		// 初始化属性信息
		o.setCity("菏泽");

		// 如果是修改，则获取对象信息
		String id = request.getParameter("id");
		if (id != null)
		{
			o = new AuthService().getDepartmentById(id);
		}

		// 字典服务
		IDictionaryService dict = new DictionaryService();

		// 是否是修改
		boolean isModify = !StringUtil.isEmpty(o.getDepartmentId());
	%>
	<head>
		<base target="_self" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><%=isModify ? "修改部门信息" : "增加部门"%></title>
		<link href="../style.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/jquery-2.0.3.min.js"></script>
		<script type="text/javascript" src="../js/utils.js"></script>
	</head>
	<body>
		<form name="addOrModifyForm" id="addOrModifyForm"
			action="<%=contextPath%>/Auth/Servlet?method=departmentAddOrModify"
			method="post">
			<table width="500" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#258ece">

				<tr class="list_table_head_blue">
					<td>
						&gt;<%=isModify ? "修改部门信息" : "增加部门"%>
					</td>
				</tr>
				<tr>
					<td bgcolor="#FFFFFF">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr class="list_table_tr0">
								<td height="50" align="right">
									<strong> 所属地市: </strong>
								</td>
								<td>
									<label>
										<input name="city" type="text" id="city"
											value="<%=o.getCity()%>" class="notEmpty">
									</label>
								</td>
							</tr>
							<tr class="list_table_tr2">
								<td height="50" align="right">
									<strong> 所属区县: </strong>
								</td>
								<td>
									<%
										if (user.isCityUser())
										{
									%>
									<%=DictionaryUtil.getSelectHtml(dict.getDictItemsByDictName("district", false), "districtName", "所属区县", o.getDistrictName(),
										"notEmpty")%>
									<%
										} else if (user.isDistrictUser())
										{
									%>
									<input name="districtName" type="text" id="districtName"
										value="<%=StringUtil.isEmpty(o.getDistrictName()) ? user.getDistrict() : o.getDistrictName()%>"
										class="notEmpty" readOnly="readOnly">
									<%
										}
									%>
								</td>
							</tr>
							<tr class="list_table_tr0">
								<td height="50" align="right">
									<strong> 部门名称: </strong>
								</td>
								<td>
									<label>
										<input name="departmentName" type="text" id="departmentName"
											value="<%=StringUtil.getNotEmptyStr(o.getDepartmentName())%>"
											class="notEmpty" label="部门名称">
									</label>
								</td>
							</tr>
							<tr class="list_table_tr2">
								<td height="50" align="right">
									<strong> 部门编码: </strong>
								</td>
								<td>
									<label>
										<input type="text" name="departmentId" id="departmentId"
											value="<%=StringUtil.getNotEmptyStr(o.getDepartmentId())%>"
											class="notEmpty" label="部门编码" <%=isModify ? "readonly" : ""%>></input>

									</label>
								</td>
							</tr>
							<tr class="list_table_tr0">
								<td height="50" align="right">
									<strong> 其他描述: </strong>
								</td>
								<td>
									<label>
										<textarea name="commentInfo" cols="30" rows="3"
											id="commentInfo"><%=StringUtil.getNotEmptyStr(o.getCommentInfo())%></textarea>
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
		</form>
		<script>
		// 提交保存或修改
		function submit()
		{
			if(!checkAllNotEmptyInput()) return false;
			
			$("#addOrModifyForm").submit();
		}
		
		// 新增或修改
		function addOrModify()
		{	
			// 修改
			if("true"=="<%=isModify%>")
			{
				submit();
			}
			// 新建
			else
			{
				// 然后检查账号是否已被使用 
					$.get(
					"Servlet?method=checkDepartmentId&departmentId="+$("#departmentId").val(), 
					{Action:"get"}, 
					function (data, textStatus){
						this;
						if(data=="true") 
						{
							alert("该部门编码已被使用，请您选用其他的账号。"); 
							return false;
						}
						else
						{
							submit();
						}
					});
			}
		}
	
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
