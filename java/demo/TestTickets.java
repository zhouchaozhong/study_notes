package myapp;

/*
 * synchronized(类对象名 aa)的含义是：判断aa是否已经被其他线程霸占，如果发现已经被其他线程霸占，则当前线程陷入等待中
 * 如果aa没有被其他线程霸占，则当前线程霸占住aa对象,并执行synchronized内的同步代码块,在当前线程执行synchronized内的同步代码块时，其他
 * 线程不能执行这个代码块（因为当前线程已经霸占了aa对象），当前线程执行完代码块后，会自动释放对aa对象的霸占，此时其他线程会互相竞争对aa的霸占，
 * 最终CPU会选择其中的某一个线程执行。
 * 最终导致的结果是：一个线程正在操作某资源的时候，将不允许其他线程操作该资源，即一次只允许一个线程操作该资源。
 * 
 * */
class Sale implements Runnable{
	public static int tickets = 100;
	public void run() {
		while(true) {
			synchronized(this) {  //线程同步，在执行synchronized内的代码时，不能切换到其他线程 ,synchronize内的参数为一个类对象名
				if(tickets > 0) {
					System.out.printf("%s线程正在卖出第%d张票\n",Thread.currentThread().getName(),tickets);
					tickets--;
				}else {
					break;
				}
			}
		}
	}
}

public class TestTickets {
	public static void main(String[] args) {
		Sale s1 = new Sale();
		Thread t1 = new Thread(s1);
		t1.start();

		Thread t2 = new Thread(s1);
		t2.start();
	}
}
