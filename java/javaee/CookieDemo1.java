package cn.myapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cookie1")
public class CookieDemo1 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print("你上次访问时间是：");
		
		Cookie cookies[] = req.getCookies();
		for(int i = 0;cookies != null && i < cookies.length;i++) {
			if(cookies[i].getName().equals("lastAccessTime")) {
				long cookieValue = Long.parseLong(cookies[i].getValue());  //得到用户的上次访问时间
				Date date = new Date(cookieValue);
				DateFormat df = DateFormat.getDateTimeInstance();
				out.print(df.format(date));
			}
		}
		
		//向用户的cookie中写入最新时间
		Cookie cookie = new Cookie("lastAccessTime",System.currentTimeMillis()+"");
		cookie.setMaxAge(3600);
		cookie.setPath("/MyServlet/cookie1");  //设置cookie的有效访问路径
		resp.addCookie(cookie);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	}
	
}
