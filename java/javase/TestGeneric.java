package myapp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class TestGeneric {
	@Test
	public void test1() {
		Map<Integer,String> map = new HashMap();
		map.put(1, "a");
		map.put(2, "b");
		map.put(3, "c");
		
		//传统keyset entryset
		Set<Map.Entry<Integer,String>> set = map.entrySet();
		Iterator<Map.Entry<Integer, String>> it = set.iterator();
		while(it.hasNext()) {
			Map.Entry<Integer, String> entry = it.next();
			int key = entry.getKey();
			String value = entry.getValue();
			System.out.println(key + " = " + value);
		}
		
		
		//增强for
		for(Map.Entry<Integer, String> entry : map.entrySet()) {
			int key = entry.getKey();
			String value = entry.getValue();
			System.out.println(key + " = " + value);
		}
		
		
	}
	
	//编写一个泛型方法，实现指定位置上的数组元素的交换
	public <T> void swap(T arr[],int pos1,int pos2) {
		T temp = arr[pos1];
		arr[pos1] = arr[pos2];
		arr[pos2] = temp;
	}
	
	//编写一个泛型方法，接收一个任意数组，并颠倒数组中的所有元素
	public <T> void reverse(T arr[]) {
		int start = 0;
		int end = arr.length-1;
		while(true) {
			if(start >= end) {
				break;
			}
			
			T temp = arr[start];
			arr[start] = arr[end];
			arr[end] = temp;
			
			start++;
			end--;
		}
	}
	
	
	
	
	
	
	
	
	
	
}
