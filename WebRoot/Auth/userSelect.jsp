<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@page import="com.hz.auth.obj.*"%>
<%@page import="com.hz.util.SystemConstant"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>用户详细信息</title>
		<link href="../style.css" rel="stylesheet" type="text/css" />
	</head>

	<script src="../js/jquery-2.0.3.min.js"></script>
	<script
		src="../jquery-ui-1.10.3.custom/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="../js/utils.js"></script>
	<link rel="stylesheet"
		href="../jquery-ui-1.10.3.custom/css/ui-lightness/jquery-ui-1.10.3.custom.min.css" />
	<script>
	function toggleContent(id)
	{
		var upImgSrc="../images/table_up.png";
		var downImgSrc="../images/table_down.png";
		
		var imgSrc = $("#button_"+id).attr("src");
		
		if(imgSrc==upImgSrc)
		{
			$("#button_"+id).attr("src",downImgSrc);
		}
		else
		{
			$("#button_"+id).attr("src",upImgSrc);
		}
		
		
		$("#div_"+id).toggle();
	}
	</script>
	<%
		AuthUser userInfo = (AuthUser) request.getAttribute(SystemConstant.AUTH_USER_INFO);
	%>
	<body>
	<%for(int i=0;i<10;i++){ %>
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#258ece">
      <tr>
        <td bgcolor="#FFFFFF">
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
		<td class="blue_font">牡丹区</td>
		<td align="right" class="blue_font"  onclick="toggleContent(<%=i%>)"><img id="button_<%=i%>" src="../images/table_down.png" width="32" height="32" align="absmiddle"/></td>
	  </tr>
  </table>
  </td>
  </tr>
  </table>
	<div id="div_<%=i%>" style="display:none">
	<table width="98%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#258ece">
      <tr>
        <td bgcolor="#FFFFFF"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		  
          <tr>
            <td height="1" bgcolor="#258ECE"></td>
            <td bgcolor="#258ECE"></td>
            <td bgcolor="#258ECE"></td>
          </tr>
          <tr class="list_table_tr0">
            <td width="10"></td>
            <td><strong>三角花园营业</strong></td>
            <td><a href="#" onclick="selectSubject()">班长</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员1</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;2&nbsp;<a href="#" onclick="selectSubject()">营业员3</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员4</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;5&nbsp;<a href="#" onclick="selectSubject()">营业员6</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员7</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员8</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;9&nbsp;</a><a href="#" onclick="selectSubject()">营业员10</a></td>
          </tr>
          <tr class="list_table_tr2">
            <td></td>
            <td><strong>康庄路营业厅</strong></td>
            <td><a href="#" onclick="selectSubject()">班长</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员1</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;2&nbsp;<a href="#" onclick="selectSubject()">营业员3</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员4</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;5&nbsp;<a href="#" onclick="selectSubject()">营业员6</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员7</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员8</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;9&nbsp;</a><a href="#" onclick="selectSubject()">营业员10</a></td>
          </tr>
          <tr class="list_table_tr0">
            <td></td>
            <td><strong>丹阳路营业厅</strong></td>
            <td><a href="#" onclick="selectSubject()">班长</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员1</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员2</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员3</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员4</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;5&nbsp;<a href="#" onclick="selectSubject()">营业员6</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员7</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员8</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;9&nbsp;</a><a href="#" onclick="selectSubject()">营业员10</a></td>
          </tr>
          <tr class="list_table_tr2">
            <td></td>
            <td><strong>大学路营业厅</strong></td>
            <td><a href="#" onclick="selectSubject()">班长</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员1</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员2</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员3</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员4</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;5&nbsp;<a href="#" onclick="selectSubject()">营业员6</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员7</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员8</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;9&nbsp;</a><a href="#" onclick="selectSubject()">营业员10</a></td>
          </tr>
          <tr class="list_table_tr0">
            <td></td>
            <td><strong>中华西路营业厅</strong></td>
            <td><a href="#" onclick="selectSubject()">班长</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员1</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员2</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员3</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员4</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;5&nbsp;<a href="#" onclick="selectSubject()">营业员6</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员7</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员8</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;9&nbsp;</a><a href="#" onclick="selectSubject()">营业员10</a></td>
          </tr>
          <tr class="list_table_tr2">
            <td></td>
            <td><strong>乐园营业厅</strong></td>
            <td><a href="#" onclick="selectSubject()">班长</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员1</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员2</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员3</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员4</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;5&nbsp;<a href="#" onclick="selectSubject()">营业员6</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员7</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员8</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;9&nbsp;</a><a href="#" onclick="selectSubject()">营业员10</a></td>
          </tr>
          <tr class="list_table_tr0">
            <td></td>
            <td><strong>牡丹南路营业厅</strong></td>
            <td><a href="#" onclick="selectSubject()">班长</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员1</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员2</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员3</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员4</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员&nbsp;5&nbsp;<a href="#" onclick="selectSubject()">营业员6</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员7</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员8</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员9&nbsp;&nbsp;</a><a href="#" onclick="selectSubject()">营业员10</a></td>
          </tr>
          <tr class="list_table_tr2">
            <td></td>
            <td><strong>杨庄营业厅</strong></td>
            <td><a href="#" onclick="selectSubject()">班长</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员1</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员2</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员3</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员4</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员5</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员6</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员7</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员8</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员9</a>&nbsp;&nbsp;<a href="#" onclick="selectSubject()">营业员10</a></td>
          </tr>
        </table></td>
      </tr>
  </table>
    </div>
<table width="98%" height="5px" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td></td>
  </tr>
</table>
	<%} %>
	</body>
</html>
