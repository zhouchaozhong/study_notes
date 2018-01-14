<%@ page language="java" import="java.util.*,java.io.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="/error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jsp</title>
</head>
<body>
	<%!
		public void fun(){  //jsp声明指令，将代码定义在service方法外面
		
		}
	%>
	
	<!-- jsp静态包含指令 -->
	
	<%@include file="/header.jsp" %>  <%--jsp注释--%>
	<h1>中间内容</h1>
	
	<!-- jsp跳转标签 -->
	<jsp:forward page="/error.jsp">
		<jsp:param value="username" name="xxx"/> <!-- 跳转的时候带上参数username -->
	</jsp:forward>
	
	
	<!-- 动态包含标签 -->
	<jsp:include page="/footer.jsp"></jsp:include> <%-- pageContext.include() 动态包含--%>
	
	
	
	<%@include file="/footer.jsp" %>
</body>
</html>