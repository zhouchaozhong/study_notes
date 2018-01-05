package myapp;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.junit.Test;

//使用内省api操作bean的属性
public class TestIntrosepector {
	
	//得到bean的所有属性
	@Test
	public void test1() throws Exception{
		BeanInfo info = Introspector.getBeanInfo(Animal.class,Object.class);//得到bean自己的属性，过滤掉Object那里继承来的属性
		PropertyDescriptor[] pds = info.getPropertyDescriptors();
		
		for(PropertyDescriptor pd : pds) {
			System.out.println(pd.getName());
		}
	}
	
	//操纵bean的指定属性：stu_no
	@Test
	public void test2() throws Exception{
		
		Animal aa = new Animal();
		PropertyDescriptor pd = new PropertyDescriptor("room",Animal.class);
		
		//得到属性的写方法，为属性赋值
		Method method = pd.getWriteMethod(); //public void setRoom(String room)
		method.invoke(aa, "103");
		
		//获取属性的值
		method = pd.getReadMethod();   //public String getRoom()
		System.out.println(method.invoke(aa, null));
		
	}
	
	//获取当前操作的属性的类型
	@Test
	public void test3() throws Exception{
		Animal aa = new Animal();
		PropertyDescriptor pd = new PropertyDescriptor("room",Animal.class);
		System.out.println(pd.getPropertyType());
	}
	
	
	
	
	
}
