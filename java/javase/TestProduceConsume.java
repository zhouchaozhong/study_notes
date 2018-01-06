package myapp;

/*
 *  生产和消费问题示例
 * */


class SyncStack{
	private char[] data = new char[6];
	private int cnt = 0;
	
	public synchronized void push(char ch) {
		while(cnt == data.length) {
			try {
				this.wait();
			}catch(Exception e) {
				
			}
		}
		this.notify();
		data[cnt] = ch;
		cnt++;
		System.out.printf("生产线程正在生产第%d个产品，该产品是:%c \n",cnt,ch);
	}
	
	public synchronized char pop() {
		char ch;
		while(cnt == 0) {
			try {
				this.wait();
			}catch(Exception e) {
				
			}
		}
		this.notify();
		
		ch = data[cnt-1];
		System.out.printf("消费线程正在消费第%d个产品，该产品是：%c \n", cnt,ch);
		cnt--;
		return ch;
	}
}

class Producer implements Runnable{
	private SyncStack ss = null;
	public Producer(SyncStack ss) {
		this.ss = ss;
	}
	public void run() {
		char ch;
		try {
			Thread.sleep(200);
		}catch(Exception e) {
			
		}
		for(int i = 0;i < 20;i++) {
			ch = (char)('a'+i);
			ss.push(ch);
		}
		
	}
}

class Consumer implements Runnable{
	private SyncStack ss = null;
	public Consumer(SyncStack ss) {
		this.ss = ss;
	}
	public void run() {
		for(int i = 0;i < 20;i++) {
			ss.pop();
		}
	}
}


public class TestProduceConsume {
	public static void main(String[] args) {
		SyncStack ss = new SyncStack();
		Producer p = new Producer(ss);
		Consumer c = new Consumer(ss);
		Thread t1 = new Thread(p);
		t1.start();
		Thread t2 = new Thread(c);
		t2.start();
	}
}
