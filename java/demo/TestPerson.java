package myapp;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

public class TestPerson {
	private Person p;
	
	@Before   //每一个测试方法运行之前都运行一次   @BeforeClass是在类加载的时候在测试方法之前运行一次
	public void before() {
		p = new Person();
	}
	
	@Test
	public void testRun() {
		Person p = new Person();
		p.run();
	}
	
	@Test
	public void testEat() {
		Person p = new Person();
		p.eat();
	}
	
	@After  //每一个测试方法运行之后都运行一次   @AfterClass是在类加载的时候，所以测试方法之后运行一次
	public void after() {
		p = null;
	}

}
