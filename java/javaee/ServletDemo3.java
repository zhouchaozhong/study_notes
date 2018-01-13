package cn.myapp;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDemo3 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//得到指定的参数
		String value = this.getServletConfig().getInitParameter("data");
		
		//得到所有的参数
		Enumeration e = this.getServletConfig().getInitParameterNames();
		while(e.hasMoreElements()) {
			String name = (String)e.nextElement();
			String value1 = this.getServletConfig().getInitParameter(name);
			System.out.println(name+"="+value1);
		}
		
		
		resp.getOutputStream().write(value.getBytes());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	
}
