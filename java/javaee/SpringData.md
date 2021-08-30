# Spring Data 学习笔记

### SpringData概述

> **简介**
>
> * Spring Data : Spring 的一个子项目。用于简化数据库访问，支持NoSQL 和 关系数据存储。其主要目标是使数据库的访问变得方便快捷。
> * SpringData 项目所支持 NoSQL 存储：
>   * MongoDB （文档数据库）
>   * Neo4j（图形数据库）
>   * Redis（键/值存储）
>   * Hbase（列族数据库）
> * SpringData 项目所支持的关系数据存储技术：
>   * JDBC
>   * JPA
> * JPA Spring Data : 致力于减少数据访问层 (DAO) 的开发量. 开发者唯一要做的，就只是声明持久层的接口，其他都交给 Spring Data JPA 来帮你完成！
> * 框架怎么可能代替开发者实现业务逻辑呢？比如：当有一个 UserDao.findUserById()  这样一个方法声明，大致应该能判断出这是根据给定条件的 ID 查询出满足条件的 User  对象。Spring Data JPA 做的便是规范方法的名字，根据符合规范的名字来确定方法需要实现什么样的逻辑。
>
> **入门示例**
>
> * 使用 Spring Data JPA 进行持久层开发需要的四个步骤：
>   * 配置 Spring 整合 JPA
>   * 在 Spring 配置文件中配置 Spring Data，让 Spring 为声明的接口创建代理对象。配置了` <jpa:repositories> `后，Spring 初始化容器时将会扫描 base-package  指定的包目录及其子目录，为继承 Repository 或其子接口的接口创建代理对象，并将代理对象注册为 Spring Bean，业务层便可以通过 Spring 自动封装的特性来直接使用该对象。
>   * 声明持久层的接口，该接口继承  Repository，Repository 是一个标记型接口，它不包含任何方法，如必要，Spring Data 可实现 Repository 其他子接口，其中定义了一些常用的增删改查，以及分页相关的方法。
>   * 在接口中声明需要的方法。Spring Data 将根据给定的策略（具体策略稍后讲解）来为其生成实现代码。
>
> applicationContext.xml
>
> ```xml
> <?xml version="1.0" encoding="UTF-8"?>
> <beans xmlns="http://www.springframework.org/schema/beans"
> 	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
> 	xmlns:context="http://www.springframework.org/schema/context"
> 	xmlns:tx="http://www.springframework.org/schema/tx"
> 	xmlns:jpa="http://www.springframework.org/schema/data/jpa"
> 	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
> 		http://www.springframework.org/schema/data/jpa http://www.springframework.org/schema/data/jpa/spring-jpa-1.3.xsd
> 		http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
> 		http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">
> 
> 	<!-- 配置自动扫描的包 -->
> 	<context:component-scan base-package="com.atguigu.springdata"></context:component-scan>
> 
> 	<!-- 1. 配置数据源 -->
> 	<context:property-placeholder location="classpath:db.properties"/>
> 
> 	<bean id="dataSource"
> 		class="com.mchange.v2.c3p0.ComboPooledDataSource">
> 		<property name="user" value="${jdbc.user}"></property>
> 		<property name="password" value="${jdbc.password}"></property>	
> 		<property name="driverClass" value="${jdbc.driverClass}"></property>
> 		<property name="jdbcUrl" value="${jdbc.jdbcUrl}"></property>
> 		
> 		<!-- 配置其他属性 -->
> 	</bean>
> 
> 	<!-- 2. 配置 JPA 的 EntityManagerFactory -->
> 	<bean id="entityManagerFactory" 
> 		class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
> 		<property name="dataSource" ref="dataSource"></property>
> 		<property name="jpaVendorAdapter">
> 			<bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter"></bean>
> 		</property>
> 		<property name="packagesToScan" value="com.atguigu.springdata"></property>
> 		<property name="jpaProperties">
> 			<props>
> 				<!-- 二级缓存相关 -->
> 				<!--  
> 				<prop key="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.EhCacheRegionFactory</prop>
> 				<prop key="net.sf.ehcache.configurationResourceName">ehcache-hibernate.xml</prop>
> 				-->
> 				<!-- 生成的数据表的列的映射策略 -->
> 				<prop key="hibernate.ejb.naming_strategy">org.hibernate.cfg.ImprovedNamingStrategy</prop>
> 				<!-- hibernate 基本属性 -->
> 				<prop key="hibernate.dialect">org.hibernate.dialect.MySQL5InnoDBDialect</prop>
> 				<prop key="hibernate.show_sql">true</prop>
> 				<prop key="hibernate.format_sql">true</prop>
> 				<prop key="hibernate.hbm2ddl.auto">update</prop>
> 			</props>
> 		</property>
> 	</bean>
> 
> 	<!-- 3. 配置事务管理器 -->
> 	<bean id="transactionManager"
> 		class="org.springframework.orm.jpa.JpaTransactionManager">
> 		<property name="entityManagerFactory" ref="entityManagerFactory"></property>	
> 	</bean>
> 
> 	<!-- 4. 配置支持注解的事务 -->
> 	<tx:annotation-driven transaction-manager="transactionManager"/>
> 
> 	<!-- 5. 配置 SpringData -->
> 	<!-- 加入  jpa 的命名空间 -->
> 	<!-- base-package: 扫描 Repository Bean 所在的 package -->
> 	<jpa:repositories base-package="com.atguigu.springdata"
> 		entity-manager-factory-ref="entityManagerFactory"></jpa:repositories>
> </beans>
> ```
>
> ```java
> package com.atguigu.springdata;
> import javax.persistence.Entity;
> import javax.persistence.GeneratedValue;
> import javax.persistence.Id;
> import javax.persistence.Table;
> import java.util.Date;
> 
> @Table(name = "person")
> @Entity
> public class Person {
>     private Integer id;
>     private String lastName;
>     private String email;
>     private Date birth;
> 
>     @GeneratedValue
>     @Id
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
>     public Date getBirth() {
>         return birth;
>     }
> 
>     public void setBirth(Date birth) {
>         this.birth = birth;
>     }
> 
>     @Override
>     public String toString() {
>         return "Person{" +
>                 "id=" + id +
>                 ", lastName='" + lastName + '\'' +
>                 ", email='" + email + '\'' +
>                 ", birth=" + birth +
>                 '}';
>     }
> }
> ```
>
> ```java
> package com.atguigu.springdata;
> import org.springframework.data.repository.Repository;
> 
> public interface PersonRepository extends Repository<Person,Integer> {
>     /**
>      *  根据lastName来获取对应的Person
>      * @param lastName
>      * @return
>      */
>     Person getPersonByLastName(String lastName);
> }
> ```
>
> ```java
> package com.atguigu.springdata.test;
> import com.atguigu.springdata.Person;
> import com.atguigu.springdata.PersonRepository;
> import org.junit.Test;
> import org.springframework.context.ApplicationContext;
> import org.springframework.context.support.ClassPathXmlApplicationContext;
> 
> import javax.sql.DataSource;
> import java.sql.SQLException;
> 
> public class SpringDataTest {
> 
>     private ApplicationContext ctx = null;
>     {
>         ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
>     }
> 
>     @Test
>     public void testJpa(){
>         PersonRepository personRepository = ctx.getBean(PersonRepository.class);
>         Person person = personRepository.getPersonByLastName("AA");
>         System.out.println(person);
>     }
> }
> ```
>
> 