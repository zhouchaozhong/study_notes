package myapp;

import java.io.*;
public class TestFileReaderWriterCopy {
	public static void main(String[] args) throws Exception{
		
		//�ַ���ʵ���ļ��ĸ���
		FileReader fr = new FileReader("D:/WebDev/www/java/test.html");
		FileWriter fw = new FileWriter("D:/WebDev/www/java/test1.html");
		int ch;
		ch = fr.read();
		while(-1 != ch) {
			fw.write(ch);
			ch = fr.read();
		}
		fw.flush();
		fr.close();
		fw.close();
		
		//�ֽ���ʵ���ļ��ĸ���
		FileInputStream fis = new FileInputStream("D:/WebDev/www/java/test.html");
		FileOutputStream fos = new FileOutputStream("D:/WebDev/www/java/test2.html");
		int chs;
		chs = fis.read();
		while(-1 != chs) {
			fos.write(chs);
			chs = fis.read();
		}
		fos.flush();
		fis.close();
		fos.close();
	}
}
