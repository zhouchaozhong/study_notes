package myapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TestFor {

	public static void main(String[] args) {
		//增强for循环
		int [] arr = {1,2,3};
		for(int num : arr) {
			System.out.println(num);
		}
		
		List list = new ArrayList();
		list.add("a");
		list.add("b");
		list.add("c");
		for(Object obj : list) {
			String i = (String)obj;
			System.out.println(i);
		}
		
		
		Map map = new HashMap();
		map.put("1", "aaa");
		map.put("2", "bbb");
		map.put("3", "ccc");
		
		//传统方式
		Set set = map.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()) {
			String key = (String)it.next();
			String value = (String)map.get(key);
			System.out.println(key + "=" + value);
		}
		
		//传统方式2
		Set set2 = map.entrySet();
		Iterator it2 = set2.iterator();
		while(it2.hasNext()) {
			Map.Entry entry = (Entry) it2.next();
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			System.out.println(key + "=" + value);
		}
		
		
		// 增强for提取map的键值对
		for(Object obj : map.keySet()) {
			String key = (String)obj;
			String value = (String) map.get(key);
			System.out.println(key + "=" + value);
		}
		

	}

}
