<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,cn.myapp.Person"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.myapp.cn" prefix="my"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自定义标签</title>
</head>
<body>
	${fn:toLowerCase('AAAAA')}<br/>
	${fn:toUpperCase('aaaaa')}<br/>
	${fn:trim('    afsds  sdfsdfdfsd    ')}<br/>
	
	
	
	<%
		request.setAttribute("arr",new String[6]);
	%>
	${fn:length('abcde')}<br/>
	${fn:length(arr)}<br/>
	
	${fn:split("www.myapp.cn",".")[0]}<br/>
	
	${fn:join(fn:split("www,myapp,cn",","),".")}<br/>
	
	${fn:indexOf("www.myapp.cn","app")}<br/>
	
	${fn:contains("aasfsdfsdfeegere","fs")}<br/>
	
	${fn:startsWith("www.myapp.cn","aa")}<br/>
	
	${fn:replace("www.myapp.cn",".",",")}<br/>
	
	${fn:substring("www.myapp.cn",4,9)}<br/>
	
	${fn:substringAfter("www.myapp.cn",".")}<br/>
	
	${fn:escapeXml("<a href='http://www.myapp.cn'>欢迎来到我的主页！</a>")}
</body>
</html>