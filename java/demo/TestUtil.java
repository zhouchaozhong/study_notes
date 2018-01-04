package myapp;

import java.util.*;

class AU{

}

class TestHash{
	private int i;
	public TestHash(int i) {
		this.i = i;
	}
	
	public boolean equals(Object ob) {
		TestHash th = (TestHash)ob;
		return this.i == th.i;
	}
	
	public int hashCode() {
		return new Integer(i).hashCode();
	}
}
public class TestUtil {
	
	public static void main(String[] args) {
		ArrayList al = new ArrayList();
		al.add(12345);
		al.add("张三");
		al.add(66.66);
		al.add(new AU());
		System.out.println(al.get(2));
		
		//hashCode和equals测试
		
		TestHash h1 = new TestHash(1);
		TestHash h2 = new TestHash(10);
		System.out.println(h1.equals(h2));
		System.out.println(h1.hashCode() == h2.hashCode());
		
		//Map测试
		Map m = new HashMap();
		m.put("one","zhangsan");
		m.put(66.6, 70);
		m.put(new AU(),"12");
		
		System.out.println(m);
		System.out.println(m.get("one"));
		
		
		//map遍历
		Map hm = new HashMap();
		hm.put(1001, "张三");
		hm.put(1002, "李四");
		hm.put(1003, "王五");
		Set s = hm.keySet();
		Iterator it = s.iterator();
		while(it.hasNext()) {
			Integer Key = (Integer)it.next();
			System.out.println(hm.get(Key));
		}
		
		
	}
	
}
