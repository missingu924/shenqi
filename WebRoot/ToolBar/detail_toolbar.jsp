<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<table align="center" width="100%" class="toolbar_table">
	<tr>
		<td>
			<input name="closeButton" type="button" class="gray_button" value=" 关闭 " onClick="javascript:window.close();" />
		</td>
	</tr>
</table>

<script>
		<%if("true".equalsIgnoreCase(request.getAttribute("needRefresh")+"")){%>
			// 绑定关闭事件
			$(window).unload(function(){
			  	// 父窗口刷新
				var parent = window.dialogArguments; 
				parent.execScript("toPage(1)","javascript"); 
			});
		<%}%>
</script>
