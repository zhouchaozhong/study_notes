# Spring学习笔记

-----

### Spring基本介绍

##### Spring框架概述

> 1. Spring是轻量级的开源的JavaEE框架
>
> 2. Spring可以解决企业应用开发的复杂性
>
> 3. Spring有两个核心部分：IOC和AOP
>
>    (1) IOC：控制反转，把创建对象的过程交给Spring进行管理
>
>    (4) AOP：面向切面，不修改源代码进行功能增强
>
> 4. Spring特点：
>
>    (1) 方便解耦，简化开发
>
>    (2) AOP编程支持
>
>    (3) 方便程序测试
>
>    (4) 方便和其他框架进行整合
>
>    (5) 方便进行事务的操作
>
>    (6) 降低JavaEE API开发难度
>
> **入门案例**
>
> 需要导入的包：
>
> 1. commons-logging-1.1.1.jar
> 2. spring-beans-5.2.13.RELEASE.jar
> 3. spring-context-5.2.13.RELEASE.jar
> 4. spring-core-5.2.13.RELEASE.jar
> 5. spring-expression-5.2.13.RELEASE.jar
>
> ```java
> package com.atguigu.spring5.test;
> 
> import com.atguigu.spring5.User;
> import org.junit.Test;
> import org.springframework.context.ApplicationContext;
> import org.springframework.context.support.ClassPathXmlApplicationContext;
> 
> public class TestSpring5 {
> 
>     @Test
>     public void testAdd(){
>         // 1.加载Spring配置文件
>         ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
>         // 2.获取配置创建的对象
>         User user = context.getBean("user", User.class);
>         System.out.println(user);
>         user.add();
>     }
> }
> ```
>
> ```java
> package com.atguigu.spring5;
> public class User {
>     public void add(){
>         System.out.println("add......");
>     }
> }
> ```
>
> ```xml
> <?xml version="1.0" encoding="UTF-8"?>
> <beans xmlns="http://www.springframework.org/schema/beans"
>        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
> 
>     <bean id="user" class="com.atguigu.spring5.User"></bean>
> </beans>
> ```

##### IOC容器

> **什么是IOC**
>
> (1) 控制反转，把对象创建和对象之间的调用过程，交给Spring进行管理
>
> (2) 使用IOC目的：为了降低耦合度
>
> **IOC底层原理**
>
> (1) xml解析、工厂模式、反射
>
> **IOC过程**
>
> 第一步：xml配置文件，配置创建的对象
>
> ```xml
> <bean id="dao" class="com.atguigu.UserDao"></bean>
> ```
>
> 第二步：有Service类和dao类，创建工厂类
>
> ```java
> class UserFactory {
>     public static UserDao getDao(){
>         String classValue = class属性值;  // xml解析
>         Class clazz = Class.forName(classValue);	// 通过反射创建对象
>         return (UserDao)clazz.newInstance();
>     }
> }
> ```
>
> **IOC接口**
>
> 1. IOC思想基于IOC容器完成，IOC容器底层就是对象工厂
>
> 2. Spring提供IOC容器实现两种方式：（两个接口）
>
>    (1) BeanFactory：IOC容器基本实现，是Spring内部使用接口，不提供给开发人员使用，在加载配置文件的时候不会创建对象，而是在获取对象时才去创建对象
>
>    (2) ApplicationContext：BeanFactory接口的子接口，提供更多更强大的功能，一般由开发人员进行使用，在加载配置文件的时候就会根据配置创建对象
>
> 3. ApplicationContext接口有实现类（FileSystemXmlApplicationContext、ClassPathXmlApplicationContext）
>
> **IOC操作---Bean管理**
>
> 1. 什么是Bean管理
>
>    (1) Spring创建对象
>
>    (2) Spring注入属性
>
> 2. Bean管理操作有两种方式
>
>    (1) 基于xml配置文件方式实现
>
>    *基于xml方式创建对象*
>
>    a. 在Spring配置文件中，使用bean标签，标签里面添加对应属性，就可以实现对象创建
>
>    b. 在bean标签有很多属性，介绍常用的属性
>
>    	* id属性：唯一标识
>    	* class属性：类全路径(包类路径)
>    	* 创建对象的时候，默认也是执行无参数构造方法完成对象创建
>
>    *基于xml方式注入属性*
>
>    a. DI：依赖注入，就是注入属性
>
>    第一种注入方式：使用set方法进行注入
>
>    第二种方式：使用有参构造进行注入
>
>    ```java
>    package com.atguigu.spring5;
>    public class Book {
>    
>        private String bname;
>        private String bauthor;
>    
>        public Book() {
>        }
>    
>        public Book(String bname, String bauthor) {
>            this.bname = bname;
>            this.bauthor = bauthor;
>        }
>    
>        public String getBname() {
>            return bname;
>        }
>    
>        public void setBname(String bname) {
>            this.bname = bname;
>        }
>    
>        public String getBauthor() {
>            return bauthor;
>        }
>    
>        public void setBauthor(String bauthor) {
>            this.bauthor = bauthor;
>        }
>    
>        public void test(){
>            System.out.println(bname + "::" + bauthor);
>        }
>    }
>    ```
>
>    ```java
>    package com.atguigu.spring5.test;
>    
>    import com.atguigu.spring5.Book;
>    import com.atguigu.spring5.User;
>    import org.junit.Test;
>    import org.springframework.context.ApplicationContext;
>    import org.springframework.context.support.ClassPathXmlApplicationContext;
>    
>    public class TestSpring5 {
>    
>        @Test
>        public void testAdd(){
>            // 1.加载Spring配置文件
>            ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
>            // 2.获取配置创建的对象
>            User user = context.getBean("user", User.class);
>            System.out.println(user);
>            user.add();
>        }
>    
>        @Test
>        public void testBook1(){
>            ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
>            Book book = context.getBean("book", Book.class);
>            book.test();
>        }
>    }
>    ```
>
>    ```xml
>    <?xml version="1.0" encoding="UTF-8"?>
>    <beans xmlns="http://www.springframework.org/schema/beans"
>           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
>        <bean id="user" class="com.atguigu.spring5.User"></bean>
>        <!--set方法注入属性-->
>     <!--   <bean id="book" class="com.atguigu.spring5.Book">
>            &lt;!&ndash;使用property完成属性注入
>                name : 类的属性名称
>                value : 类的属性的值
>            &ndash;&gt;
>            <property name="bname" value="易筋经"></property>
>            <property name="bauthor" value="达摩老祖"></property>
>        </bean>-->
>    
>        <!--有参构造注入属性-->
>        <bean id="book" class="com.atguigu.spring5.Book">
>            <constructor-arg name="bname" value="北冥神功"></constructor-arg>
>            <constructor-arg name="bauthor" value="远古大能"></constructor-arg>
>        </bean>
>    </beans>
>    ```
>
>    第三种方式：p名称空间注入（底层用的是set方法注入）
>
>    ```xml
>    <?xml version="1.0" encoding="UTF-8"?>
>    <beans xmlns="http://www.springframework.org/schema/beans"
>           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>           xmlns:p="http://www.springframework.org/schema/p"
>           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
>        <bean id="book" class="com.atguigu.spring5.Book" p:bname="九阳神功" p:bauthor="无名氏"></bean>
>    </beans>
>    ```
>
>    特殊情况：
>
>    1. 注入空值
>
>    ```xml
>    <?xml version="1.0" encoding="UTF-8"?>
>    <beans xmlns="http://www.springframework.org/schema/beans"
>           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
>        
>        <bean id="book" class="com.atguigu.spring5.Book">
>            <property name="bname">
>                <null></null>
>            </property>
>            <property name="bauthor">
>                <null></null>
>            </property>
>        </bean>
>    </beans>
>    ```
>
>    2. 属性包含特殊符号
>
>    ```xml
>    <?xml version="1.0" encoding="UTF-8"?>
>    <beans xmlns="http://www.springframework.org/schema/beans"
>           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
>    <!--第一种方式：使用转义字符-->
>    
>    <!--第二种方式，使用CDATA-->
>    <bean id="book" class="com.atguigu.spring5.Book">
>        <property name="bname">
>            <value><![CDATA[<<太极拳>>]]></value>
>        </property>
>        <property name="bauthor">
>            <value><![CDATA["张三丰"]]></value>
>        </property>
>    </bean>
>    </beans>
>    ```
>
>    注入外部Bean
>
>    代码示例如下：
>
>    UserService.java
>
>    ```java
>    package com.atguigu.spring5.service;
>    
>    import com.atguigu.spring5.dao.UserDao;
>    
>    public class UserService {
>        private UserDao userDao;
>    
>        public UserDao getUserDao() {
>            return userDao;
>        }
>    
>        public void setUserDao(UserDao userDao) {
>            this.userDao = userDao;
>        }
>    
>        public void add(){
>            System.out.println("service add ...");
>            userDao.update();
>        }
>    }
>    ```
>
>    UserDao.java
>
>    ```java
>    package com.atguigu.spring5.dao;
>    
>    public interface UserDao {
>        public void update();
>    }
>    ```
>
>    UserDaoImpl.java
>
>    ```java
>    package com.atguigu.spring5.dao;
>    
>    public class UserDaoImpl implements UserDao {
>        @Override
>        public void update() {
>            System.out.println("dao update ...");
>        }
>    }
>    ```
>
>    bean1.xml
>
>    ```xml
>    <?xml version="1.0" encoding="UTF-8"?>
>    <beans xmlns="http://www.springframework.org/schema/beans"
>           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
>    
>        <bean id="userService" class="com.atguigu.spring5.service.UserService">
>            <property name="userDao" ref="userDaoImpl"></property>
>        </bean>
>        <bean id="userDaoImpl" class="com.atguigu.spring5.dao.UserDaoImpl"></bean>
>    </beans>
>    ```
>
>    TestBean.java
>
>    ```java
>    package com.atguigu.spring5.test;
>    
>    import com.atguigu.spring5.service.UserService;
>    import org.junit.Test;
>    import org.springframework.context.ApplicationContext;
>    import org.springframework.context.support.ClassPathXmlApplicationContext;
>    
>    public class TestBean {
>        @Test
>        public void testAdd(){
>            ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
>            UserService userService = context.getBean("userService", UserService.class);
>            userService.add();
>        }
>    }
>    ```
>
>    (2) 基于注解方式实现
>
>    

