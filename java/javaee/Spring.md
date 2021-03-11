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
>  public static UserDao getDao(){
>      String classValue = class属性值;  // xml解析
>      Class clazz = Class.forName(classValue);	// 通过反射创建对象
>      return (UserDao)clazz.newInstance();
>  }
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
>    内部bean
>    
>    ```xml
>    <?xml version="1.0" encoding="UTF-8"?>
>    <beans xmlns="http://www.springframework.org/schema/beans"
>           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
>        <!--内部bean-->
>        <bean id="emp" class="com.atguigu.spring5.bean.Emp">
>            <property name="ename" value="lucy"></property>
>            <property name="gender" value="女"></property>
>            <!--设置对象类型属性-->
>            <property name="dept">
>                <bean id="dept" class="com.atguigu.spring5.bean.Dept">
>                    <property name="dname" value="客服部"></property>
>                </bean>
>            </property>
>        </bean>
>    </beans>
>    ```
>    
>    级联赋值
>    
>    第一种写法
>    
>    ```xml
>    <?xml version="1.0" encoding="UTF-8"?>
>    <beans xmlns="http://www.springframework.org/schema/beans"
>           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
>        <!--内部bean-->
>        <bean id="emp" class="com.atguigu.spring5.bean.Emp">
>            <property name="ename" value="lucy"></property>
>            <property name="gender" value="女"></property>
>            <!--设置对象类型属性-->
>            <property name="dept" ref="dept"></property>
>        </bean>
>        <bean id="dept" class="com.atguigu.spring5.bean.Dept">
>            <property name="dname" value="财务部"></property>
>        </bean>
>    </beans>
>    ```
>    
>    第二种写法
>    
>    ```xml
>    <?xml version="1.0" encoding="UTF-8"?>
>    <beans xmlns="http://www.springframework.org/schema/beans"
>           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
>        <!--内部bean-->
>        <bean id="emp" class="com.atguigu.spring5.bean.Emp">
>            <property name="ename" value="lucy"></property>
>            <property name="gender" value="女"></property>
>            <!--设置对象类型属性-->
>            <property name="dept" ref="dept"></property>
>            <!--需要在Emp类中生成dept的get方法getDept()-->
>            <property name="dept.dname" value="销售部"></property>
>        </bean>
>        <bean id="dept" class="com.atguigu.spring5.bean.Dept">
>            <property name="dname" value="财务部"></property>
>        </bean>
>    </beans>
>    ```
>    
>    xml注入集合属性
>    
>    ```xml
>    <?xml version="1.0" encoding="UTF-8"?>
>    <beans xmlns="http://www.springframework.org/schema/beans"
>           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
>        <!--集合类型属性注入-->
>        <bean id="stu" class="com.atguigu.spring5.collecttype.Stu">
>            <!--数组类型注入-->
>            <property name="courses">
>                <array>
>                    <value>英语</value>
>                    <value>数学</value>
>                </array>
>            </property>
>            <!--List类型注入-->
>            <property name="list">
>                <list>
>                    <value>宁毅</value>
>                    <value>宁立恒</value>
>                    <value>血手人屠</value>
>                </list>
>            </property>
>            <!--Map类型注入-->
>            <property name="maps">
>                <map>
>                    <entry key="java" value="java课程"></entry>
>                    <entry key="dbms" value="数据库课程"></entry>
>                </map>
>            </property>
>            <!--Set类型注入-->
>            <property name="sets">
>                <set>
>                    <value>MySQL</value>
>                    <value>Redis</value>
>                </set>
>            </property>
>            <!--注入list集合类型，值是对象-->
>            <property name="courseList">
>                <list>
>                    <ref bean="course1"></ref>
>                    <ref bean="course2"></ref>
>                </list>
>            </property>
>        </bean>
>        <!--创建多个course对象-->
>        <bean id="course1" class="com.atguigu.spring5.collecttype.Course">
>            <property name="cno" value="1"></property>
>            <property name="cname" value="Spring5框架"></property>
>        </bean>
>        <bean id="course2" class="com.atguigu.spring5.collecttype.Course">
>            <property name="cno" value="2"></property>
>            <property name="cname" value="MyBatis框架"></property>
>        </bean>
>    </beans>
>    ```
>    
>    抽取集合公共部分
>    
>    ```xml
>    <?xml version="1.0" encoding="UTF-8"?>
>    <beans xmlns="http://www.springframework.org/schema/beans"
>           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>           xmlns:util="http://www.springframework.org/schema/util"
>           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
>                               http://www.springframework.org/schema/util  http://www.springframework.org/schema/util/spring-util.xsd">
>        <!--提取list集合类型属性注入-->
>        <util:list id="bookList">
>            <value>易筋经</value>
>            <value>九阴真经</value>
>            <value>九阳神功</value>
>        </util:list>
>        <!--提取list集合类型属性注入-->
>        <bean id="book" class="com.atguigu.spring5.collecttype.Book">
>            <property name="list" ref="bookList"></property>
>        </bean>
>    </beans>
>    ```
>    
>    工厂Bean
>    
>    ```java
>    package com.atguigu.spring5.factorybean;
>    
>    import com.atguigu.spring5.collecttype.Course;
>    import org.springframework.beans.factory.FactoryBean;
>    
>    public class MyBean implements FactoryBean<Course> {
>        @Override
>        public Course getObject() throws Exception {
>            // 定义返回的bean
>            Course course = new Course();
>            course.setCno(1);
>            course.setCname("数学");
>            return course;
>        }
>    
>        @Override
>        public Class<?> getObjectType() {
>            return null;
>        }
>    
>        @Override
>        public boolean isSingleton() {
>            return false;
>        }
>    }
>    ```
>    
>    ```java
>    package com.atguigu.spring5.collecttype;
>    
>    public class Course {
>    
>        private Integer cno;
>        private String cname;
>    
>        public void setCname(String cname) {
>            this.cname = cname;
>        }
>    
>        public void setCno(Integer cno) {
>            this.cno = cno;
>        }
>    
>        @Override
>        public String toString() {
>            return "Course{" +
>                    "cno=" + cno +
>                    ", cname='" + cname + '\'' +
>                    '}';
>        }
>    }
>    ```
>    
>    ```java
>    public void test5(){
>        ApplicationContext context = new ClassPathXmlApplicationContext("bean3.xml");
>        Course course = context.getBean("myBean", Course.class);
>        System.out.println(course);
>    }
>    ```
>    
>    ```xml
>    <?xml version="1.0" encoding="UTF-8"?>
>    <beans xmlns="http://www.springframework.org/schema/beans"
>           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
>        <bean id="myBean" class="com.atguigu.spring5.factorybean.MyBean"></bean>
>    </beans>
>    ```
>    
>    如何设置bean是单实例还是多实例
>    
>    a. 在Spring配置文件bean标签里面有属性（scope）用于设置单实例还是多实例
>    
>    b. scope属性值
>    
>    第一个值：默认值，singleton，表示单实例对象（加载配置文件时，创建对象）
>    
>    第二个值：prototype，表示多实例对象（获取对象的时候创建）
>    
>    bean生命周期
>    
>    a. 通过构造器创建bean实例（无参构造）
>    
>    b. 为bean的属性设置值和对其他bean引用（调用set方法）
>    
>    c. 把bean实例传递给bean前置处理器的方法
>    
>    d. 调用bean的初始化方法（需要进行配置初始化方法）
>    
>    e. 把bean实例传递给bean后置处理器的方法
>    
>    f. bean可以使用了（对象获取到了）
>    
>    g. 当容器关闭的时候，调用bean的销毁方法（需要进行配置销毁的方法） 
>    
>    xml自动装配
>    
>    什么是自动装配
>    
>    a. 根据指定装配规则（属性名称或者属性类型）,Spring自动将匹配的属性值进行注入
>    
>    bean标签属性autowire，配置自动装配
>    
>    autowire属性常用两个值：
>    
>    ​	byName根据属性名称注入，注入值bean的id值和类属性名称一样
>    
>    ​	byType根据属性类型注入
>    
>    ```xml
>    <?xml version="1.0" encoding="UTF-8"?>
>    <beans xmlns="http://www.springframework.org/schema/beans"
>           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
>        <bean id="emp" class="com.atguigu.spring5.autowire.Emp" autowire="byType"></bean>
>        <bean id="dept" class="com.atguigu.spring5.autowire.Dept"></bean>
>    </beans>
>    ```
>    
>    引入外部属性文件
>    
>    ```xml
>    <?xml version="1.0" encoding="UTF-8"?>
>    <beans xmlns="http://www.springframework.org/schema/beans"
>           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>           xmlns:util="http://www.springframework.org/schema/util"
>           xmlns:context="http://www.springframework.org/schema/context"
>           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
>                               http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd
>                               http://www.springframework.org/schema/context  http://www.springframework.org/schema/context/spring-context.xsd">
>        <!-- 直接配置 -->
>        <!--<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">-->
>            <!--<property name="driverClassName" value="com.alibaba.druid.pool.DruidDataSource"></property>-->
>            <!--<property name="url" value="jdbc:mysql://localhost:3306/userDb"></property>-->
>            <!--<property name="username" value="root"></property>-->
>            <!--<property name="password" value="root"></property>-->
>        <!--</bean>-->
>        <!--引入外部属性文件-->
>        <context:property-placeholder location="classpath:jdbc.properties" />
>        <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
>            <property name="driverClassName" value="${prop.driverClass}"></property>
>            <property name="url" value="${prop.url}"></property>
>            <property name="username" value="${prop.userName}"></property>
>            <property name="password" value="${prop.password}"></property>
>        </bean>
>    </beans>
>    ```
>    
>    ```properties
>    prop.driverClass=com.mysql.jdbc.Driver
>    prop.url=jdbc:mysql://localhost:3306/userDb
>    prop.userName=root
>    prop.password=root
>    ```
>    
>    (2) 基于注解方式实现
>    
>    Spring针对Bean管理中创建对象提供注解
>    
>    a. @Component
>    
>    b. @Service
>    
>    c. @Controller
>    
>    d. @Repository
>    
>    上面四个注解功能是一样的，都可以用来创建bean实例
>    
>    注解方式创建对象示例：
>    
>    ```xml
>    <?xml version="1.0" encoding="UTF-8"?>
>    <beans xmlns="http://www.springframework.org/schema/beans"
>           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>           xmlns:context="http://www.springframework.org/schema/context"
>           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
>                               http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
>        <!--开启组件扫描
>            1. 如果扫描多个包，多个包使用逗号隔开
>            2. 扫描包上层目录
>        -->
>        <context:component-scan base-package="com.atguigu.spring5.dao,com.atguigu.spring5.service"></context:component-scan>
>    </beans>
>    ```
>    
>    ```java
>    package com.atguigu.spring5.service;
>    
>    import org.springframework.stereotype.Component;
>    
>    // 在注解里面value属性值可以省略不写
>    // 默认值是类名称，首字母小写
>    @Component(value = "userService")
>    public class UserService {
>        public void add(){
>            System.out.println("service add...");
>        }
>    }
>    ```
>    
>    ```java
>    package com.atguigu.spring5.test;
>    
>    import com.atguigu.spring5.service.UserService;
>    import org.junit.Test;
>    import org.springframework.context.ApplicationContext;
>    import org.springframework.context.support.ClassPathXmlApplicationContext;
>    
>    public class TestDemo {
>        @Test
>        public void test(){
>            ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
>            UserService userService = context.getBean("userService", UserService.class);
>            userService.add();
>        }
>    }
>    ```
>    
>    
>

