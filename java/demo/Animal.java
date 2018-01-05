package myapp;

import java.io.InputStream;
import java.util.List;

public class Animal {
	public String name="aaa";
	private int password = 123;
	private static int age = 18;
	public Animal() {
		System.out.println("Animal class has been created!");
	}
	
	public Animal(String name) {
		System.out.println(name);
	}
	
	public Animal(String name,int age) {
		System.out.println(name+":"+age);
	}
	
	private Animal(List list) {
		System.out.println("Animal List");
	}
	
	public void aa1() {
		System.out.println("welcome!");
	}
	
	public void aa1(String name,int age) {
		System.out.println(name+":"+age);
	}
	
	public Class[] aa1(String name,int [] msg) {
		return new Class[]{String.class};
	}
	
	private void aa1(InputStream in) {
		System.out.println(in);
	}
	
	public static void aa1(int num) {
		System.out.println(num);
	}
	
	public static void main(String[] args) {
		System.out.println("main");
	}
	
	
}
