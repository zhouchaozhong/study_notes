package myapp;

import java.io.*;
public class TestFileReader {
	public static void main(String[] args) throws Exception{
		
		//FileReader 字符流
		FileReader fr = new FileReader("D:/WebDev/www/java/test.html");
		int ch;
		ch = fr.read();
		while(-1 != ch) {
			System.out.printf("%c",(char)ch);
			ch = fr.read();
		}
		fr.close();
		
		//FileInputStream 字节流
		FileInputStream fis = new FileInputStream("D:/WebDev/www/java/test.html");
		int chs;
		int cnt = 0;
		chs = fis.read();
		while(-1 != chs) {
			++cnt;
			System.out.printf("%c",(char)chs);
			chs = fis.read();
		}
		
		System.out.printf("该文件字符的个数是：%d \n",cnt);
		fis.close();
	}
}
