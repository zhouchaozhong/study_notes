package myapp;

import org.junit.Test;

public class TestJunit {
	//可变参数
	@Test
	public void testSum() {
		sum(1,2,3,4,5,6);
	}
	
	public void sum(int ...nums) {
		int sum = 0;
		for(int i : nums) {
			sum += i;
		}
		
		System.out.println(sum);
	}
	
	
	//枚举
	@Test
	public void testEnum() {
		print(Grade.A);
	}
	
	public void print(Grade g) {
		String value = g.getValue();
		String locale = g.localeValue();
		System.out.println(locale+":"+value);
		for(Object j : g.values()) {//遍历枚举类里面的所有对象
			System.out.println(j);
		}
	}
	
	
}

//如何定义枚举的构造函数、方法和字段，去封装更多信息
//带抽象方法的枚举

/*
 * 枚举类是一种特殊形式的java类
 * 枚举类声明的每一个枚举值代表枚举类的一个实例对象
 * 与java中的普通类一样，在声明枚举类时，也可以声明属性、方法和构造函数，但枚举类的构造函数必须为私有的
 * 枚举类也可以实现接口、或继承抽象类
 * 
 * 
 * 
 * */
enum Grade{
	A("100-90"){
		public String localeValue() {
			return "优";
		}
	},
	B("89-80"){
		public String localeValue() {
			return "良";
		}
	},
	C("79-70"){
		public String localeValue() {
			return "中";
		}
	},
	D("69-60"){
		public String localeValue() {
			return "差";
		}
	},
	E("59-0"){
		public String localeValue() {
			return "不及格";
		}
	};
	private String value;  //封装每个对象对应的分数
	private Grade(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public abstract String localeValue();
	
}
