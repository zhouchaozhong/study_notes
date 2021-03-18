# MyBatis学习笔记

### MyBatis基本介绍

##### 入门案例

> mybatis-config.xml
>
> ```xml
> <?xml version="1.0" encoding="UTF-8" ?>
> <!DOCTYPE configuration
>         PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
>         "http://mybatis.org/dtd/mybatis-3-config.dtd">
> <configuration>
>     <environments default="development">
>         <environment id="development">
>             <transactionManager type="JDBC"/>
>             <dataSource type="POOLED">
>                 <property name="driver" value="com.mysql.jdbc.Driver"/>
>                 <property name="url" value="jdbc:mysql://192.168.100.199/test"/>
>                 <property name="username" value="root"/>
>                 <property name="password" value="root"/>
>             </dataSource>
>         </environment>
>     </environments>
>     <!--将我们写好的sql映射文件一定要注册到全局配置文件中-->
>     <mappers>
>         <mapper resource="EmployeeMapper.xml"/>
>     </mappers>
> </configuration>
> ```
>
> EmployeeMapper.xml
>
> ```xml
> <?xml version="1.0" encoding="UTF-8" ?>
> <!DOCTYPE mapper
>         PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
>         "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
> <mapper namespace="com.example.mybatis.EmployeeMapper">
>     <!--
>         namespace：名称空间（随便起）
>         id：唯一标识
>         resultType：返回值类型
>         #{id}：从传递过来的参数中取出id值【占位符】
>     -->
>     <select id="selectEmp" resultType="com.example.mybatis.bean.Employee">
>       select id,last_name as lastName,email,gender from tbl_employee where id = #{id}
>     </select>
> </mapper>
> ```
>
> ```java
> package com.example.mybatis.test;
> import com.example.mybatis.bean.Employee;
> import org.apache.ibatis.io.Resources;
> import org.apache.ibatis.session.SqlSession;
> import org.apache.ibatis.session.SqlSessionFactory;
> import org.apache.ibatis.session.SqlSessionFactoryBuilder;
> import org.junit.Test;
> import java.io.IOException;
> import java.io.InputStream;
> 
> public class MyBatisTest {
>     /**
>      *  1.根据xml配置文件（全局配置文件）创建一个SqlSessionFactory对象，有数据源一些运行环境信息
>      *  2.sql映射文件，配置了每一个sql，以及sql的封装规则
>      *  3.将sql的映射文件注册在全局配置文件中
>      *  4.写代码：
>      *      1）根据全局配置文件得到SqlSessionFactory
>      *      2）使用SqlSessionFactory，获取到SqlSession对象，使用它来执行增删改查
>      *         一个sqlSession就是代表和数据库的一次会话，用完关闭
>      *      3）使用sql的唯一标识来告诉MyBatis执行哪个sql，sql语句都是保存在sql映射文件中的
>      *
>      */
>     @Test
>     public void test() throws IOException {
>         String resource = "mybatis-config.xml";
>         InputStream inputStream = Resources.getResourceAsStream(resource);
>         SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
>         // 获取sqlSession实例，能直接执行已经映射的sql语句
>         SqlSession openSession = sqlSessionFactory.openSession();
>         // selectOne()第一个参数：sql的唯一标识（namespace + id）; 第二个参数：执行sql要用的参数
>         try {
>             Employee employee = openSession.selectOne("com.example.mybatis.EmployeeMapper.selectEmp", 1);
>             System.out.println(employee);
>         } catch (Exception e) {
>             e.printStackTrace();
>         } finally {
>             openSession.close();
>         }
>     }
> }
> ```
>
> ```java
> package com.example.mybatis.bean;
> 
> public class Employee {
>     private Integer id;
>     private String lastName;
>     private String email;
>     private String gender;
> 
>     public Integer getId() {
>         return id;
>     }
> 
>     public void setId(Integer id) {
>         this.id = id;
>     }
> 
>     public String getLastName() {
>         return lastName;
>     }
> 
>     public void setLastName(String lastName) {
>         this.lastName = lastName;
>     }
> 
>     public String getEmail() {
>         return email;
>     }
> 
>     public void setEmail(String email) {
>         this.email = email;
>     }
> 
>     public String getGender() {
>         return gender;
>     }
> 
>     public void setGender(String gender) {
>         this.gender = gender;
>     }
> 
>     @Override
>     public String toString() {
>         return "Employee{" +
>                 "id=" + id +
>                 ", lastName='" + lastName + '\'' +
>                 ", email='" + email + '\'' +
>                 ", gender='" + gender + '\'' +
>                 '}';
>     }
> }
> 
> ```
>
> 