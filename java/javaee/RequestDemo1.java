package cn.myapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/req1")
public class RequestDemo1 extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream in = req.getInputStream();
		int len = 0;
		byte[] buffer = new byte[1024];
		while((len = in.read(buffer)) > 0) {
			System.out.println(new String(buffer,0,len));
		}
		
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	
	//获取http头的相关信息
	public void test1(HttpServletRequest req) {
		System.out.println(req.getRequestURI());
		System.out.println(req.getRequestURL());
		System.out.println(req.getQueryString());
		System.out.println(req.getRemoteAddr());
		System.out.println(req.getMethod());
		
		String headValue = req.getHeader("Accept-Encoding");
		System.out.println(headValue);
		
		Enumeration e = req.getHeaders("Accept-Encoding");
		while(e.hasMoreElements()) {
			String value = (String) e.nextElement();
			System.out.println(value);
		}
		
		Enumeration ele = req.getHeaderNames();
		while(ele.hasMoreElements()) {
			String name = (String) ele.nextElement();
			String value = req.getHeader(name);
			System.out.println(name+":"+value);
		}
	}
	
	public void test2(HttpServletRequest req) {
		Enumeration e = req.getParameterNames();
		while(e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value1 = req.getParameter(name);
			System.out.println(name+"="+value1);
		}
	}
	
	
	
	
	
	
}
