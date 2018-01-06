package myapp;

/*
 * synchronized(������� aa)�ĺ����ǣ��ж�aa�Ƿ��Ѿ��������̰߳�ռ����������Ѿ��������̰߳�ռ����ǰ�߳�����ȴ���
 * ���aaû�б������̰߳�ռ����ǰ�̰߳�ռסaa����,��ִ��synchronized�ڵ�ͬ�������,�ڵ�ǰ�߳�ִ��synchronized�ڵ�ͬ�������ʱ������
 * �̲߳���ִ���������飨��Ϊ��ǰ�߳��Ѿ���ռ��aa���󣩣���ǰ�߳�ִ��������󣬻��Զ��ͷŶ�aa����İ�ռ����ʱ�����̻߳ụ�ྺ����aa�İ�ռ��
 * ����CPU��ѡ�����е�ĳһ���߳�ִ�С�
 * ���յ��µĽ���ǣ�һ���߳����ڲ���ĳ��Դ��ʱ�򣬽������������̲߳�������Դ����һ��ֻ����һ���̲߳�������Դ��
 * 
 * */
class Sale implements Runnable{
	public static int tickets = 100;
	public void run() {
		while(true) {
			synchronized(this) {  //�߳�ͬ������ִ��synchronized�ڵĴ���ʱ�������л��������߳� ,synchronize�ڵĲ���Ϊһ���������
				if(tickets > 0) {
					System.out.printf("%s�߳�����������%d��Ʊ\n",Thread.currentThread().getName(),tickets);
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
