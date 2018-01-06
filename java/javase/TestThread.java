package myapp;

//创建线程的第一种方式  继承Thread类
class A extends Thread{
	public void run(){
		System.out.printf("%s在执行！",Thread.currentThread().getName());  //获取当前线程名
		for(int i = 0;i < 50;i++) {
			System.out.println("AAAAAAAA");
		}
		
	}
}

//创建线程的第二种方式 实现Runnable接口
class B implements Runnable{
	public void run() {
		System.out.printf("%s在执行！",Thread.currentThread().getName());
		for(int i = 0;i < 50;i++) {
			System.out.println("BBBBBBBB");
		}
		
	}
}

public class TestThread {
	public static void main(String[] args){
		//第一种启动方法
		A aa = new A();
		aa.setName("线程一");   //设置线程名
		aa.start();  //aa.start() 会自动调用run方法
		
		//第二种启动方法
		B bb = new B();
		Thread t = new Thread(bb);
		t.setName("线程二");
		t.start();
		for(int i = 0;i < 50;i++) {
			System.out.println("TTTTTTTT");
		}
		
		System.out.printf("%s在执行！",Thread.currentThread().getName());
		
		
	}
}
