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
		<title>部门列表</title>
		<link href="../style.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/jquery-2.0.3.min.js"></script>
		<script type="text/javascript" src="../js/utils.js"></script>
		<%
			String contextPath = request.getContextPath();
			// 账号信息
			AuthUser user = (AuthUser) request.getSession().getAttribute(SystemConstant.AUTH_USER_INFO);
		%>
	</head>
	<body>

		<form name="pageForm" id="pageForm" method="post"
			action="<%=request.getContextPath()%>/Auth/Servlet?method=departmentList">
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#258ece">
				<tr>
					<td>
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<tr class="list_table_tr2">
								<td width="50" align="left">
									<strong>区县:</strong>
								</td>
								<td width="50"><%=DictionaryUtil.getSelectHtml(new DictionaryService().getDictItemsByDictName("district", true), "district", "区县", StringUtil
							.getNotEmptyStr(request.getAttribute("district")), "notEmpty")%>
								</td>
								<td width="60" align="right">
									<label></label>
									<strong>部门编号:</strong>
								</td>
								<td width="100">
									<label>
										<input name="departmentId" type="text" id="departmentId"
											value="<%=StringUtil.getNotEmptyStr(request.getAttribute("departmentId"))%>">
									</label>
								</td>
								<td width="60" align="right">
									<label></label>
									<strong>部门名称:</strong>
								</td>
								<td>
									<label>
										<input name="departmentName" type="text" id="departmentName"
											value="<%=StringUtil.getNotEmptyStr(request.getAttribute("departmentName"))%>">
										<input name="btnSearch" type="button" class="blue_button"
											value="模糊查询" onclick="toPage(1)">
									</label>
								</td>
								<td align="right">
									<%
										// 地市级用户 或 区县级用户 可以增加部门
										if (user.isCityUser() || user.isDistrictUser())
										{
									%>
									<input name="Submit2" type="button" class="green_button"
										value="增加部门"
										onClick="openBigModalDialog('<%=contextPath%>/Auth/departmentAddOrModify.jsp')">
									<%
										}
									%>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td height="5px"></td>
				</tr>
			</table>
			<%
				// 数据信息
				PaginationObj pagination = null;
				List<AuthDepartment> list = new ArrayList<AuthDepartment>();

				Object paginationObj = request.getAttribute("pagination");
				if (paginationObj != null)
				{
					pagination = (PaginationObj) paginationObj;
					list = (List<AuthDepartment>) pagination.getDataList();
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
						部门编号
					</td>
					<td>
						部门名称
					</td>
					<td>
						所属地市
					</td>
					<td>
						所属区县
					</td>
					<td>
						所辖人员数
					</td>
					<td>
						其他描述
					</td>
					<td>
						操作
					</td>
				</tr>
				<%
					for (int i = 0; i < list.size(); i++)
						{
							AuthDepartment o = list.get(i);
				%>
				<tr class="<%=i % 2 == 0 ? "list_table_tr0" : "list_table_tr1"%>">
					<td><%=o.getDepartmentId()%>
					</td>
					<td><%=o.getDepartmentName()%>
					</td>
					<td><%=o.getCity()%>
					</td>
					<td><%=o.getDistrictName()%>
					</td>
					<td><%=o.getUsers().size()%></td>
					<td><%=StringUtil.getNotEmptyStr(o.getCommentInfo())%></td>
					<td width="90" align="center" style="padding: 0; cursor: pointer">
						<img src="../images/list_detail.png" width="25" height="25"
							alt="详情" title="详情"
							onClick="openBigModalDialog('<%=contextPath%>/Auth/Servlet?method=subjectDetail&subjectType=<%=TaskConstant.SUBJECT_TYPE_DEPARTMENT%>&subjectId=<%=o.getDepartmentId()%>')">
						<%if(user.isCityUser()||(user.isDistrictUser()&&user.getDistrict().equals(o.getDistrictName()))){ %>
						<img src="../images/list_edit.png" width="25" height="25" alt="修改"
							title="修改"
							onClick="openBigModalDialog('<%=contextPath%>/Auth/departmentAddOrModify.jsp?id=<%=o.getDepartmentId()%>')">
						<img src="../images/list_delete.png" width="25" height="25"
							alt="删除" title="删除"
							onClick="javascript:$('#pageForm').attr('action','<%=contextPath%>/Auth/Servlet?method=departmentDelete&id=<%=o.getDepartmentId()%>');$('#pageForm').submit();">
					<%} %>
					</td>
				</tr>
				<%
					}
				%>
				<tr class="list_table_foot">
					<td colspan="7">
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
		<!--用于增加或者显示详情的层-->
		<div id="contentDiv"></div>
	</body>
</html>
