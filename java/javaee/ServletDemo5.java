package cn.myapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/demo5")
public class ServletDemo5 extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String value = (String) this.getServletContext().getAttribute("data");
		
		
		//获取web应用的初始化参数
		String param1 = (String) this.getServletContext().getInitParameter("data");
		
		//获取web应用的所有初始化参数
		Enumeration e = this.getServletContext().getInitParameterNames();
		while(e.hasMoreElements()) {
			String name = (String) e.nextElement();
			String value1 = this.getServletContext().getInitParameter(name);
			//System.out.println(value1);
		}
		
		//读取资源文件
		InputStream in = this.getServletContext().getResourceAsStream("/WEB-INF/classes/cn/myapp/db.properties");
		Properties props = new Properties();
		props.load(in);
		String url = props.getProperty("url");
		String username = props.getProperty("username");
		String password = props.getProperty("password");
		
		//得到资源的绝对路径
		String path = this.getServletContext().getRealPath("/WEB-INF/classes/cn/myapp/db.properties");
		System.out.println(path);
		System.out.println("url:"+url+"  username:"+username+" password:"+password);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
