package cn.myapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/res1")
public class ResponseDemo1 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//程序以什么编码输出，就控制浏览器以什么编码打开
		//resp.setHeader("Content-type", "text/html;charset=UTF-8");
		
		//通过response使用的编码，来控制response以什么编码向浏览器写数据
		resp.setCharacterEncoding("UTF-8");
		//控制浏览器以什么编码打开数据
		resp.setHeader("Content-type", "text/html;charset=UTF-8");
		String data = "中国";
		//resp.getOutputStream().write(data.getBytes("UTF-8"));
		PrintWriter out = resp.getWriter();
		out.write(data);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
	
}
