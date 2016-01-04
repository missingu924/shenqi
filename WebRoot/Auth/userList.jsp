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
<%@page import="com.wuyg.task.TaskConstant"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>账号列表</title>
		<link href="../style.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/jquery-2.0.3.min.js"></script>
		<script type="text/javascript" src="../js/utils.js"></script>
		<%
			String contextPath = request.getContextPath();

			AuthUser user = (AuthUser) request.getSession().getAttribute(SystemConstant.AUTH_USER_INFO);
		%>
	</head>
	<body>

		<form name="pageForm" id="pageForm" method="post"
			action="<%=request.getContextPath()%>/Auth/Servlet?method=userList">
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td height="9px"></td>
				</tr>
			</table>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#3DAEB6">
				<tr>
					<td>
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr class="list_table_tr2">
								<td align="left">
									供应商<%=DictionaryUtil.getSelectHtml(new DictionaryService().getDictItemsByDictName("vendor", true), "district", "供应商", StringUtil
							.getNotEmptyStr(request.getAttribute("district")), "notEmpty")%>&nbsp;姓名
									<input name="name" type="text" id="name"
										value="<%=StringUtil.getNotEmptyStr(request.getAttribute("name"))%>" size="15">
									&nbsp;账号
									<input name="account" type="text" id="account"
										value="<%=StringUtil.getNotEmptyStr(request.getAttribute("account"))%>" size="15">
									&nbsp;<input name="Submit" type="button" class="blue_button"
										value="查询" onClick="toPage(1)">
							  </td>
							    <td align="right"><strong>
							      <input name="Submit2" type="button" class="green_button"
										value="添加"
										onClick="openBigModalDialog('<%=contextPath%>/Auth/userAddOrModify.jsp')">
							    </strong></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td height="10px"></td>
				</tr>
			</table>
			<%
				// 数据信息
				PaginationObj pagination = null;
				List<AuthUser> list = new ArrayList<AuthUser>();

				Object paginationObj = request.getAttribute("pagination");
				if (paginationObj != null)
				{
					pagination = (PaginationObj) paginationObj;
					list = (List<AuthUser>) pagination.getDataList();
				}
				if (paginationObj == null)
				{
					out.print("没有查询到数据。");
				} else
				{
			%>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#CCCCCC">
				<tr class="list_table_head">
					<td>
						账号
					</td>
					<td>
						姓名
					</td>
					<td>
						联系电话
					</td>
					<td>
						供应商
					</td>
					<td width="90">
						操作
					</td>
				</tr>
				<%
					for (int i = 0; i < list.size(); i++)
						{
							AuthUser o = list.get(i);
				%>
				<tr class="<%=i % 2 == 0 ? "list_table_tr0" : "list_table_tr1"%>">
					<td><%=o.getAccount()%>
					</td>
					<td><%=o.getName()%>
					</td>
					<td><%=StringUtil.getNotEmptyStr(o.getTelephone())%>
					</td>
					<td><%=o.getDistrict()%></td>
					<td width="90" align="center" style="padding: 0; cursor: pointer">
						<img src="../images/list_detail.png" width="25" height="25"
							alt="详情" title="详情"
							onClick="openBigModalDialog('<%=contextPath%>/Auth/Servlet?method=userDetail&account=<%=o.getAccount()%>')">
						<%
							if (SystemConstant.ROLE_ADMIN.equalsIgnoreCase(user.getRoleLevel()))
									{
						%>
						<img src="../images/list_edit.png" width="25" height="25" alt="修改"
							title="修改"
							onClick="openBigModalDialog('<%=contextPath%>/Auth/userAddOrModify.jsp?account=<%=o.getAccount()%>')">
						<img src="../images/list_delete.png" width="25" height="25"
							alt="删除" title="删除"
							onClick="javascript:
								$('#pageForm').attr('action','<%=contextPath%>/Auth/Servlet?method=userDelete&id=<%=o.getId()%>');
								$('#pageForm').submit();
								">
						<%
							}
						%>
					</td>
				</tr>
				<%
					}
				%>
				<tr class="list_table_foot">
					<td colspan="9">
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							align="center">
							<tr>
								<td align="right">
									<input name="pageNo" type="hidden" id="pageNo"
										value="<%=pagination.getPageNo()%>" size="3" />
									<input name="pageCount" type="hidden"
										value="<%=pagination.getPageCount()%>" size="3" />
									<%
										if (pagination.isPrevious())
											{
									%>
									<img src="../images/pagination_icons_first.png"
										align="absmiddle" onClick="toPage(1);" />
									&nbsp;&nbsp;
									<img src="../images/pagination_icons_pre.png" align="absmiddle"
										onclick="toPage(<%=pagination.getPageNo() - 1%>);" />
									&nbsp;&nbsp;
									<%
										} else
											{
									%>
									<img src="../images/pagination_icons_first_gray.png"
										align="absmiddle" />
									&nbsp;&nbsp;
									<img src="../images/pagination_icons_pre_gray.png"
										align="absmiddle" />
									&nbsp;&nbsp;
									<%
										}
									%>
									<%
										if (pagination.isNext())
											{
									%>
									<img src="../images/pagination_icons_next.png"
										align="absmiddle"
										onclick="toPage(<%=pagination.getPageNo() + 1%>);" />
									&nbsp;&nbsp;
									<img src="../images/pagination_icons_last.png"
										align="absmiddle"
										onclick="toPage(<%=pagination.getTotalPage()%>);" />
									&nbsp;&nbsp;
									<%
										} else
											{
									%>
									<img src="../images/pagination_icons_next_gray.png"
										align="absmiddle" />
									&nbsp;&nbsp;
									<img src="../images/pagination_icons_last_gray.png"
										align="absmiddle" />
									&nbsp;&nbsp;
									<%
										}
									%>
									<img src="../images/pagination_icons_fresh.png"
										align="absmiddle" onClick="toPage(1);" />
								</td>
								<td width="180" align="right" valign="middle">
									第<%=pagination.getPageNo()%>/<%=pagination.getTotalPage()%>页&nbsp;共<%=pagination.getTotalCount()%>条数据&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<%
				}
			%>
		</form>
		<script>
			// 绑定enter事件
			$('body').keydown(function(){
			   if(event.keyCode == 13)
			   {
				 $("#pageForm").submit();
			   }
			});
		</script>
	</body>
</html>
