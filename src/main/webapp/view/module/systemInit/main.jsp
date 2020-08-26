<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'main.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
		
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/view/module/systemInit/main.js"></script>
	
  </head>
  
  <body>
  	${basePath}
   	<button id="system-init">系统初始化</button> <br>
   	<button id="system-restart">系统重启</button> 
  </body>
</html>
