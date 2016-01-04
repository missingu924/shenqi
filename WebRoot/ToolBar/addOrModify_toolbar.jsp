<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage=""%>
<table align="center" width="100%" class="toolbar_table">
	<tr>
		<td>
			<input name="cancleButton" type="button" class="gray_button" value=" 取消 " onClick="javascript:window.close();">
			&nbsp;
			<input name="saveButton" type="button" class="green_button" value=" 保存 " onClick="addOrModify()">
		</td>
	</tr>
</table>
<!-- JS函数 -->
<script>
			// 绑定enter事件
			$('body').keydown(function(){
			   if(event.keyCode == 13)
			   {
				 addOrModify();
			   }
			});
</script>
