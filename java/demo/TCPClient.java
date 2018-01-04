package myapp;

import java.net.*;
import java.io.*;

public class TCPClient
{
	public static void main(String[] args) throws Exception
	{
		Socket s = new Socket("127.0.0.1", 6666);   //8行
					//第一个参数是要连接到的主机IP，即表示要连接到那台机器上，127.0.0.1表示链接到本机， 第二个参数表示要连接到哪个网络程序上
					//new Socket("127.0.0.1", 6666) 一旦构造对象成功，就意味着这时已经产生了一个试图和IP为"127.0.0.1" 端口为6666的网络程序进行连接的请求
					//如果这时IP为"127.0.0.1" 的机器上正好有一个网络程序在监听6666端口，这时连接就会建立成功
					//所谓连接成功是指这两个网络程序建立了一个通信管道，双方都可以通过这个管道写数据和读数据，并且一方写入的数据实际就是另一方要读取的数据，一方要读取的数据实际就是另一放要写入的数据， 即这个管道实际是双向的，任何一方都可以通过getInputStream() 和 getOutputStream()获取输入流和输出流两个流，并且一方的输入流实际就是另一方的输出流，这是同一个流，一方的输出流实际就是另一方的输入流，这也是同一个流 即一个通信管道两个流，双方各自都可以得到两个流，这两个流分别是输入流和输出流
					//记住：一旦new出了Socket对象，该对象就会自动向服务器端发送连接请求，如果连接不成功则程序立即终止
					//因此我们在TCP编程的客户端是找不到请求连接的代码，但是在服务器端却存在监听客户端连接请求的代码, 代码类似于：ss.accept()
		
		OutputStream os = s.getOutputStream(); //16行  一旦连接成功，相当于建立了一根管道，这根管道在客户端相当于输出流管道，在服务器端相当于输入流管道! 
						//程序如果能执行到16行说明已经连接成功，因为8行执行完后就会自动立即发送一个连接请求，如果服务器端没有打开，或者服务器端虽然已经打开但是却没有监听客户端的连接请求，则程序会立即终止，是不会执行到16行的， 如果执行到了16行那说明连接成功了
		
		DataOutputStream dos = new DataOutputStream(os);
		dos.writeUTF("同志们好");
		dos.flush();
		dos.close();
		s.close();		
	}
}