package myapp;

import java.io.*;
public class TestFileReader {
	public static void main(String[] args) throws Exception{
		
		//FileReader �ַ���
		FileReader fr = new FileReader("D:/WebDev/www/java/test.html");
		int ch;
		ch = fr.read();
		while(-1 != ch) {
			System.out.printf("%c",(char)ch);
			ch = fr.read();
		}
		fr.close();
		
		//FileInputStream �ֽ���
		FileInputStream fis = new FileInputStream("D:/WebDev/www/java/test.html");
		int chs;
		int cnt = 0;
		chs = fis.read();
		while(-1 != chs) {
			++cnt;
			System.out.printf("%c",(char)chs);
			chs = fis.read();
		}
		
		System.out.printf("���ļ��ַ��ĸ����ǣ�%d \n",cnt);
		fis.close();
	}
}
