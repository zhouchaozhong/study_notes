package cn.myapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//文件下载

@WebServlet("/res2")
public class ResponseDemo2 extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = this.getServletContext().getRealPath("/download/1.jpg");
		String filename = path.substring(path.lastIndexOf("\\")+1);
		resp.setHeader("Content-disposition", "attachment;filename="+filename);
		
		//如果是中文文件
		//resp.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(filename,"UTF-8"));
		
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(path);
			int len = 0;
			byte buffer[] = new byte[1024];
			out = resp.getOutputStream();
			while((len = in.read(buffer)) > 0) {
				out.write(buffer,0,len);
			}
		}finally {
			if(in != null) {
				try {
					in.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(out != null) {
				try {
					out.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}


	
}
