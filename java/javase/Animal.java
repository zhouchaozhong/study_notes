package myapp;

import java.io.InputStream;
import java.util.List;

public class Animal {
	public String name="aaa";
	private int password = 123;
	private static int age = 18;
	private String room;
	private int stu_no;
	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public int getStu_no() {
		return stu_no;
	}

	public void setStu_no(int stu_no) {
		this.stu_no = stu_no;
	}

	public String getAb() {
		return null;
	}
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
