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
    
    <title>论坛登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script src="${basePath}/js/jquery-3.1.1.js" type="text/javascript"></script>
	<script type="text/javascript" src="${basePath}/lib/DataTables-1.10.13/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
  </head>
  
  <body>
    <br><br>
	功能列表
	<br>
	<a href="view/module/systemInit/main.jsp">系统设置</a>
  </body>
</html>
