<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/lib/DataTables-1.10.13/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/view/module/userData/main.js"></script>

  </head>
  <body>
  	<br><br>
	   <a href="add.jsp">添加用户</a>
	       用户列表<br>
   	<br><br>
    <input id="toquery"    type="button" value="查询" />
    <br><br>
    <table id="example" class="display" style="width: 700px;">
		<thead>
			<tr>
				<th>username</th>
				<th>password</th>
				<th>credit</th>
				<th>locked</th>
				<th>createDate</th>
				<th>modifyDate</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><a href=""></a> </td>
			</tr>
		</tbody>
	</table>
  </body>
</html>















