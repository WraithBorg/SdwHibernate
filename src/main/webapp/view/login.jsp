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

	<script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="js/login.js"></script>
  </head>

  <body>

    <c:if test="${!empty error} }">
    	<font color="red" > <c:out value="${error}"></c:out> </font>
    </c:if>

    <form action="<c:url value="user/login" />" method="post">
    	用户名：
    	<input type="text" name="username">
    	<br>
    	密码:
    	<input type="password" name="password">
    	<br>
    	<input type="submit" value="登陆" />
    	<input type="reset"  value="重置" />
    </form>
    <a href="view/register.jsp">注册</a>
    <form action="<c:url value="user" />" method="post">
    	<input type="hidden" name="m" value="pageList">
    	<input type="hidden" name="num" value="1">
    	<input type="submit" value="查询用户1" />
    </form>

    <br><br>
    <input id="toregister" type="button" value="注册" />
    <input id="download"    type="button" value="下载" />
    <a href="user?m=testResponseEntity">超链接下载</a>
    <br><br>
    <table id="example" class="display" style="width: 700px;">
		<thead>
			<tr>
				<th>Column 1</th>
				<th>Column 2</th>
				<th>Column 2</th>
				<th>Column 2</th>
				<th>Column 2</th>
				<th>Column 2</th>
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
			</tr>
		</tbody>
	</table>
	<br><br>
	功能列表
	<br>
	<a href="view/module/systemInit/main.jsp">系统设置</a>
	<a href="view/module/utils/webSocket.jsp">测试WebSocket</a>

  </body>
</html>
