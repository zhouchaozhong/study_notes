package myapp;

import java.io.*;

public class TestDataWrite {
	public static void main(String[] args) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		DataOutputStream dos = new DataOutputStream(baos);
		long m = 1234567;
		dos.writeLong(m);
		byte[] buf2 = baos.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(buf2,0,buf2.length);
		DataInputStream dis = new DataInputStream(bais);
		long n;
		n = dis.readLong();
		
		System.out.println("n= "+n);
		
		
		// ‰»Î¡˜
		String str = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		str = br.readLine();
		System.out.println("str= "+str);
		
		//print¡˜
		DataOutputStream dos1 = new DataOutputStream(new FileOutputStream("D:/WebDev/www/java/test.txt"));
		dos1.writeLong(12345);
		dos1.close();
		System.out.printf("%#x\n",12345);
		PrintStream ps = new PrintStream(new FileOutputStream("D:/WebDev/www/java/test1.txt"));
		ps.println(12345);
		ps.close();
		
	}
}
