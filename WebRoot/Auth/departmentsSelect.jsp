<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@page import="com.hz.auth.obj.*"%>
<%@page import="com.hz.util.SystemConstant"%>
<%@page import="com.inspur.common.tree.Node"%>
<%@page import="java.util.List"%>
<%@page import="com.wuyg.task.TaskConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>选择部门</title>
		<link href="../style.css" rel="stylesheet" type="text/css" />
	</head>

	<script src="../js/jquery-2.0.3.min.js"></script>
	<script src="../js/utils.js"></script>
	<script>
	// 显示或隐藏某个分公司的内容
	function toggleContent(id)
	{
		var upImgSrc="table_up.png";
		var downImgSrc="table_down.png";
		
		var imgSrc = $("#button_"+id).attr("src");
		
		if(imgSrc.indexOf(upImgSrc)>0)
		{
			$("#button_"+id).attr("src","../images/"+downImgSrc);
		}
		else
		{
			$("#button_"+id).attr("src","../images/"+upImgSrc);
		}
		
		
		$("#div_"+id).toggle();
	}
	
	// 选中一个部门
	function selectSubject(departmentId,departmentName,district)
	{			
		$('#selectedSubjectName').html('&nbsp;&nbsp;您选则的部门为&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;'+departmentName);
		
		// 选中的对象的信息，用于返回给父页面
		$('#selectedSubject').val(departmentId+','+departmentName+','+district);
	}
	
	// 选完之后确定
	function subjectSelectedOk()
	{
		if($('#selectedSubject').val()=='')
		{
			alert('您没有选则部门');
			return;
		}
		
		window.returnValue = $('#selectedSubject').val();
		
		window.close();
	}
	</script>
	<%
		AuthUser userInfo = (AuthUser) request.getAttribute(SystemConstant.AUTH_USER_INFO);

		Node authTree = (Node) request.getAttribute("departments");

		List<Node> level1Departments = authTree.getCh();
	%>
	<body>
		<input type="hidden" id="selectedSubject" value="" />
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
			<td width="40" align="right" valign="middle" class="orange_bg"><img src="../images/attention.png" width="28" height="28" align="absmiddle" /></td>
				<td class="orange_bg" align="right" id="selectedSubjectName"></td>
				<td width="60" align="right" valign="middle" class="orange_bg">
					<input name="submit" type="submit" class="green_button" id="submit"
						value="确定" onclick="subjectSelectedOk()" />
&nbsp;				</td>
			</tr>
			<tr>
			  <td height="5"></td>
			  <td></td>
		  </tr>
	</table>

		<%
			for (int i = 0; i < level1Departments.size(); i++)
			{

				Node level1Department = level1Departments.get(i);
		%>
		<table width="95%" border="0" align="center" cellpadding="0"
			cellspacing="1" bgcolor="#258ece">
			<tr>
				<td bgcolor="#FFFFFF">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0">
						<tr onclick="toggleContent(<%=i%>)">
							<td width="100" class="blue_font"><%=level1Department.getV()%></td>
							<td align="right" class="blue_font"
								>
								<img id="button_<%=i%>" src="../images/table_down.png"
									width="32" height="32" align="absmiddle" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
	</table>
		<div id="div_<%=i%>" style="display: none">
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="1" bgcolor="#258ece">
				<tr>
					<td bgcolor="#FFFFFF">
						<table width="100%" border="0" align="center" cellpadding="0"
							cellspacing="0">
							<%
								List<Node> level2Departments = level1Department.getCh();
									for (int j = 0; j < level2Departments.size(); j++)
									{
										Node level2Department = level2Departments.get(j);
							%>
							<tr class="<%=j % 2 == 0 ? "list_table_tr0" : "list_table_tr2"%>">
								<td width="5"></td>
								<td align="left" width="130">
									<strong>
									<!-- 如果没有员工，则该部门不能被选中 -->
									<%if(level2Department.getCh().size()>0){ %>
									<a href="#"
										onclick="selectSubject('<%=TaskConstant.SUBJECT_TYPE_DEPARTMENT%>','<%=level2Department.getK()%>','<%=level2Department.getV()%>','<%=level1Department.getK()%>','<%=level1Department.getV()%>')">
										<%}%>
										<%=level2Department.getV()%></a>
									</strong>
								</td>
							</tr>
							<%
								}
							%>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<table width="95%" height="5px" border="0" cellpadding="0"
			cellspacing="0" align="center">
			<tr>
				<td></td>
			</tr>
		</table>
		<%
			}
		%>
	</body>
</html>
