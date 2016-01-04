<!DOCTYPE html>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<!-- 引入类 -->
<%@page import="com.hz.auth.obj.AuthUser"%>
<%@page import="java.util.List"%>
<%@page import="com.hz.util.SystemConstant"%>
<%@page import="com.wuyg.common.util.StringUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.wuyg.common.obj.PaginationObj"%>
<%@page import="com.wuyg.common.servlet.AbstractBaseServletTemplate"%>
<%@page import="com.fuli.obj.VillagerObj"%>
<%@page import="com.wuyg.common.util.MySqlUtil"%>
<%@page import="com.inspur.common.dictionary.util.DictionaryUtil"%>
<%@page import="com.inspur.common.dictionary.Dictionary"%>
<%@page import="com.inspur.common.dictionary.pojo.Dict"%>
<%@page import="com.inspur.common.dictionary.pojo.DictItem"%>
<%@page import="com.hz.dict.service.DictionaryService"%>
<!-- 基本信息 -->
<%
	// 当前上下文路径
	String contextPath = request.getContextPath();
	// 该功能路径
	String basePath = "DbModel";
	// 表或视图名
	String tableName = request.getParameter("tableName");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>代码生成器</title>
		<link href="../css/global.css" rel="stylesheet" type="text/css">
		<link href="../css/table.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/jquery-2.0.3.min.js"></script>
		<script type="text/javascript" src="../js/utils.js"></script>
	</head>
	<body>
		<form name="form" id="form" method="post" action="<%=request.getContextPath()%>/<%=basePath%>/Servlet?method=generateSrc">
			<!-- 表格标题 -->
			<table width="90%" align="center" class="title_table">
				<tr>
					<td>
						<img src="../images/svg/heavy/green/tools.png" width="18" height="18" align="absmiddle">
						&nbsp;&nbsp;代码生成神器
					</td>
				</tr>
			</table>
			<!-- 查询条件 -->
			<table class="search_table" align="center" width="90%">
				<tr>
					<td align="right" class="little_gray_font">
						源码Package全名:
					</td>
					<td>
						<input type="text" name="javaPackage" size="40" value="com.">
					</td>
				</tr>
				<tr>
					<td align="right" class="little_gray_font" width="120">
						表/视图名:
					</td>
					<td>
						<input type="hidden" name="tableName" size="40" value="<%=tableName%>" readOnly>
						<%=DictionaryUtil.getSelectHtml(new DictionaryService().getDictItemsByDictName("all_tables", false),"tableName", "", tableName, "notEmpty") %>
					</td>
				</tr>
				<tr>
					<td align="right" class="little_gray_font">
						中文名:
					</td>
					<td>
						<input type="text" name="cnName" size="40">
					</td>
				</tr>
				<tr>
					<td align="right" class="little_gray_font">
						图标路径:
					</td>
					<td>
						<input type="text" name="iconPath" size="40" value="../images/svg/">
					</td>
				</tr>
				<tr>
					<td align="right" class="little_gray_font">
						列表中包含操作列:
					</td>
					<td>
						<input type="checkbox" name="containsOperationCol" checked value="true">
					</td>
				</tr>
				<tr>
					<td align="right" class="little_gray_font">
						生成详情及增改页面:
					</td>
					<td>
						<input type="checkbox" name="forDetail" checked value="true">
					</td>
				</tr>
				<tr>
					<td align="right" class="little_gray_font">
						源码存放路径:
					</td>
					<td>
						<input type="text" name="javaSrcDir" size="40" value="c:/test/shenqi/src">
					</td>
				</tr>
			</table>

			<!-- 查询结果 -->
			<table id="columnsTable" class="table table-bordered table-striped" align="center" width="90%">
				<thead>
					<tr>
						<th>
							列名
						</th>
						<th>
							主键
						</th>
						<th>
							类型
						</th>
						<th>
							中文名
						</th>
						<th>
							字典
						</th>
						<th>
							必填
						</th>
						<th>
							检查唯一性
						</th>
						<th>
							显示
						</th>
						<th>
							用作查询条件
						</th>
					</tr>
				</thead>
				<!-- 查询结果 -->
				<%
					Connection conn = null;
					try
					{
						conn = MySqlUtil.getConnection(SystemConstant.DEFAULT_DB);
						ResultSetMetaData metaData = conn.createStatement().executeQuery("select * from " + tableName).getMetaData();

						for (int i = 1; i < metaData.getColumnCount() + 1; i++)
						{
							String columnName = metaData.getColumnName(i).toLowerCase();
				%>
				<tr>
					<td>
						<%=columnName%>
						<input type="hidden" name="columnName" value="<%=columnName%>">
					</td>
					<td>
						<input type="radio" name="keyColumn" value="<%=columnName%>" <%=(i==1?"checked":"" )%>>
					</td>
					<td>
						<%=metaData.getColumnTypeName(i)%>
						<input type="hidden" name="columnType" value="<%=metaData.getColumnType(i)%>">
						<input type="hidden" name="columnScale" value="<%=metaData.getScale(i)%>">
					</td>
					<td>
						<input type="text" name="columnCnName" size="20" value="" tabindex="<%=i%>">
					</td>
					<td>
						<input type="hidden" name="dictName" size="20" value="">
					</td>
					<td>
						<input type="checkbox" name="notNull" checked value="<%=columnName%>">
					</td>
					<td>
						<input type="radio" name="uniqueColumn" value="<%=columnName%>">
					</td>
					<td>
						<input type="checkbox" name="columnShow" checked value="<%=columnName%>">
					</td>
					<td>
						<input type="checkbox" name="columnForSearch" value="<%=columnName%>">
					</td>
				</tr>
				<%
					}
					} catch (Exception e)
					{
						e.printStackTrace();
					} finally
					{
						MySqlUtil.closeConnection(conn);
					}
				%>

			</table>

			<table align="center" width="90%">
				<tr>
					<td align="right">
						<input type="submit" id="submitButton" class="green_button" value=" 生成代码 ">
					</td>
				</tr>
			</table>
		</form>
		<script type="text/javascript">
		$(document).ready(function() {
			// 使用 jQuery异步提交表单
			$('#form').submit(function() {
			jQuery.ajax({
					url:'Servlet?method=generateSrc',
					data:$('#form').serialize(),
					type:"POST",
					beforeSend:function()
					{  
						$('#submitButton').hide();
					},
					
					success:function()
					{
						$('#submitButton').show();
					}
				});
			return false;
			});
		});
		
	$("#tableName").change(function() {
		parent.mainFrame.location = "<%=contextPath%>/DbModel/list.jsp?tableName="+$(this).val();
	});
	
	
	
	/*表格行拖拽*/
	 var tbody = $('#columnsTable > tbody'); 
	 var rows = tbody.children(); 
	 var selectedRow; 
	 
	 //压下鼠标时选取行 
	 rows.mousedown(function(){ 
		 selectedRow = this; 
		 tbody.css('cursor', 'move'); 
		 this.css('background-color','#EEFCC0');
		 return false; //防止拖动时选取文本内容，必须和 mousemove 一起使用 
		 }); 
	 rows.mousemove(function(){ 
	  	return false; //防止拖动时选取文本内容，必须和 mousedown 一起使用 
		}); 
		
	 //释放鼠标键时进行插入 
	 rows.mouseup(function(){ 
	 	var currentRow = $(this);
		if(selectedRow) 
		{ 
		 if($(currentRow).prevAll().length>$(selectedRow).prevAll().length) 
		 { 
		 	if(currentRow.next())
		 	{
		 		currentRow = $(currentRow).next();
		 	}
		 	$(currentRow).after(selectedRow); //插入 
		 } 
		 
		 if($(currentRow).prevAll().length<$(selectedRow).prevAll().length) 
		 { 
		 	$(currentRow).before(selectedRow); //插入 
		 } 
		 
		 tbody.css('cursor', 'default'); 
		 selectedRow = null; 
		 } 
		 }); 

	 //当用户压着鼠标键移出 tbody 时，清除 cursor 的拖动形状，以前当前选取的 selectedRow 
	 tbody.mouseover(function(event){ 
	 event.stopPropagation(); //禁止 tbody 的事件传播到外层的 div 中 
	}); 
	</script>
	</body>
</html>

