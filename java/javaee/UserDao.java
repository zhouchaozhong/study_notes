package cn.myapp;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

//如果读取资源文件的程序不是servlet，最好用类装载器去读文件
public class UserDao {
	private static Properties dbconfig = new Properties();
	static {
		try {
			InputStream in = UserDao.class.getClassLoader().getResourceAsStream("db.properties");
			dbconfig.load(in);
		}catch(Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public void update(){
		System.out.println(dbconfig.getProperty("url"));
	}
	
	
}
