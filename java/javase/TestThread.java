package myapp;

//�����̵߳ĵ�һ�ַ�ʽ  �̳�Thread��
class A extends Thread{
	public void run(){
		System.out.printf("%s��ִ�У�",Thread.currentThread().getName());  //��ȡ��ǰ�߳���
		for(int i = 0;i < 50;i++) {
			System.out.println("AAAAAAAA");
		}
		
	}
}

//�����̵߳ĵڶ��ַ�ʽ ʵ��Runnable�ӿ�
class B implements Runnable{
	public void run() {
		System.out.printf("%s��ִ�У�",Thread.currentThread().getName());
		for(int i = 0;i < 50;i++) {
			System.out.println("BBBBBBBB");
		}
		
	}
}

public class TestThread {
	public static void main(String[] args){
		//��һ����������
		A aa = new A();
		aa.setName("�߳�һ");   //�����߳���
		aa.start();  //aa.start() ���Զ�����run����
		
		//�ڶ�����������
		B bb = new B();
		Thread t = new Thread(bb);
		t.setName("�̶߳�");
		t.start();
		for(int i = 0;i < 50;i++) {
			System.out.println("TTTTTTTT");
		}
		
		System.out.printf("%s��ִ�У�",Thread.currentThread().getName());
		
		
	}
}
