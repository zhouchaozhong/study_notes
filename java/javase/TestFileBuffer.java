package myapp;

import java.io.*;
public class TestFileBuffer {
	public static void main(String[] args) throws Exception{
		
		FileInputStream fis = new FileInputStream("F:/����/�Զ� - �ҵ�һ����������.mp3");
		
		BufferedInputStream bis = new BufferedInputStream(fis);
		
		FileOutputStream fos = new FileOutputStream("D:/WebDev/www/java/test.mp3");
		
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		byte[] buf = new byte[1024];
		
		int len;
		
		len = bis.read(buf);
		
		while(-1 != len) {
			fos.write(buf,0,len);
			len = bis.read(buf);
		}
		
		bos.flush();
		bis.close();
		bos.close();
	}
}
