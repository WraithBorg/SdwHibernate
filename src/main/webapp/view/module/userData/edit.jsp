<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'register.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/lib/DataTables-1.10.13/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/view/module/userData/edit.js"></script>

  </head>
  
  <body>
  	Edit
   <form action="<c:url value="user/register" />" method="post">
    	<table>
			<tr>
				<td>name</td>
				<td><input name="name" type="text" /></td>
			</tr>
			<tr>
				<td>username</td>
				<td><input name="username" type="text" /></td>
			</tr>
			<tr>
				<td>password</td>
				<td><input name="password" type="text" /></td>
			</tr>
		</table>
    	<input type="submit" value="注册" />
    	<input type="reset"  value="重置" />
    </form>
  </body>
</html>
