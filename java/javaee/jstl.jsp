<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,cn.myapp.Person"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自定义标签</title>
</head>
<body>
	<%
		request.setAttribute("data", "aaaaa");
	
	%>
	<br/>-------------------------------------c:out标签-----------------<br/>
	<c:out value="${data}"/>
	
	<br/>-------------------------------------c:set标签-----------------<br/>
	<c:set var="data" value="xxx" scope="page"/>
	${data}
	
	<% 
		Map map = new HashMap();
		request.setAttribute("map", map);
	%>
	<c:set property="z" value="zzzzz" target="${map}" />
	${map.z}
	
	<%
		Person p = new Person();
		request.setAttribute("p", p);
		
	%>
	
	<c:set property="name" value="uuu" target="${p}"/>
	${p.name}
	
	
	<br/>-------------------------------------c:catch标签-----------------<br/>
	<c:catch var="myex">
		<%
			int x = 1/0;
		%>
	</c:catch>
	
	${myex.message}
	
	<br/>-------------------------------------c:if标签-----------------<br/>
	<c:if var="aaa" test="${user==null}" scope="page"/>
	
	<br/>-------------------------------------c:forEach标签-----------------<br/>
	<%
		List list = new ArrayList();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		list.add("eee");
		
		request.setAttribute("list", list);
	
	%>
	
	<c:forEach var="item" items="${list}">
		${item}
	</c:forEach>
	
	<br/>
	
	<c:forEach var="num" begin="1" end="9" step="1">
		${num}
	</c:forEach>
	
	<br/>-------------------------------------c:url标签-----------------<br/>
	<c:url var="url" value="/xxx">
		<c:param name="name" value="中国"></c:param>
	</c:url>
	<a href="${url}">购买</a>
</body>
</html>