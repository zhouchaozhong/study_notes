package myapp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

//反射类的构造函数，创建类的对象
public class TestReflect {
	
	//反射构造函数  public Animal()
	@Test
	public void test1() throws Exception {
		Class clazz = Class.forName("myapp.Animal");
		Constructor c = clazz.getConstructor(null);
		Animal a = (Animal)c.newInstance(null);
		
		System.out.println(a.name);
		
	}
	
	//反射构造函数 public Animal(String name)
	@Test
	public void test2() throws Exception {
		Class clazz = Class.forName("myapp.Animal");
		Constructor c = clazz.getConstructor(String.class);
		Animal a = (Animal)c.newInstance("xxx");
		System.out.println(a.name);
	}
	
	//反射构造函数 public Animal(String name,int age)
	@Test
	public void test3() throws Exception {
		Class clazz = Class.forName("myapp.Animal");
		Constructor c = clazz.getConstructor(String.class,int.class);
		Animal a = (Animal)c.newInstance("xxx",18);
		System.out.println(a.name);
	}
	
	//反射构造函数 public Animal(List list)
	@Test
	public void test4() throws Exception {
		Class clazz = Class.forName("myapp.Animal");
		Constructor c = clazz.getDeclaredConstructor(List.class);
		c.setAccessible(true); //设置私有属性或方法可以访问
		Animal a = (Animal)c.newInstance(new ArrayList());
		System.out.println(a.name);
	}
	
	//创建对象的另外一种方式
	@Test
	public void test5() throws Exception {
		Class clazz = Class.forName("myapp.Animal");
		Animal a = (Animal)clazz.newInstance();
		System.out.println(a);
	}
	
	
	
	//反射类的方法  public void aa1()
	@Test
	public void test6() throws Exception {
		Animal a = new Animal();
		Class clazz = Class.forName("myapp.Animal");
		Method method = clazz.getMethod("aa1",null);
		method.invoke(a,null);
		
	}
	
	
	//反射类的方法  public void aa1(String name,int age)
	@Test
	public void test7() throws Exception {
		Animal a = new Animal();
		Class clazz = Class.forName("myapp.Animal");
		Method method = clazz.getMethod("aa1",String.class,int.class);
		method.invoke(a,"一叶之秋",18);
		
	}
	
	//反射类的方法  public void aa1(String name,int [] msg)
	@Test
	public void test8() throws Exception {
		Animal a = new Animal();
		Class clazz = Class.forName("myapp.Animal");
		Method method = clazz.getMethod("aa1",String.class,int[].class);
		Class cs[] = (Class[]) method.invoke(a,"一叶之秋",new int[]{1,2,3});
		System.out.println(cs[0]);
		
	}
	
	
	//反射类的方法  public void aa1(InputStream in)
	@Test
	public void test9() throws Exception {
		Animal a = new Animal();
		Class clazz = Class.forName("myapp.Animal");
		Method method = clazz.getDeclaredMethod("aa1",InputStream.class);
		method.setAccessible(true);
		method.invoke(a,new FileInputStream("d:/webdev/www/java/test.html"));	
		
	}	
	
	//反射类的方法  public static void aa1(int num)
	@Test
	public void test10() throws Exception {
		Animal a = new Animal();
		Class clazz = Class.forName("myapp.Animal");
		Method method = clazz.getMethod("aa1",int.class);
		method.invoke(null,23);	
	}
	
	
	//反射类的方法  public static void main(String[] args)
	@Test
	public void test11() throws Exception {
		Animal a = new Animal();
		Class clazz = Class.forName("myapp.Animal");
		Method method = clazz.getMethod("main",String[].class);
		method.invoke(null,(Object)new String[] {"aa","bb","cc"});	
	}
	
	
	
	//反射字段 public String name
	@Test
	public void test12() throws Exception {
		Animal a = new Animal();
		Class clazz = Class.forName("myapp.Animal");
		Field f = clazz.getField("name");
		//获取字段的值
		Object value = f.get(a);
		//获取字段的类型
		Class type = f.getType();
		if(type.equals(String.class)) {
			String svalue = (String)value;
			System.out.println(svalue);
		}
		
		//设置字段的值
		f.set(a, "xxxxxxxxxxx");
		System.out.println(a.name);
		
	}
	
	
	//反射字段 private int password
	@Test
	public void test13() throws Exception {
		Animal a = new Animal();
		Class clazz = Class.forName("myapp.Animal");
		Field f = clazz.getDeclaredField("password");
		f.setAccessible(true);
		System.out.println(f.get(a));
	
		
	}
	
	
	
	
	
	
	
}
