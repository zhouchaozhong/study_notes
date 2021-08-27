# JPA学习笔记

### JPA概述

> **JPA 是什么**
>
> * Java Persistence API：用于对象持久化的 API
> * Java EE 5.0 平台标准的 ORM 规范，使得应用程序以统一的方式访问持久层
>
> <img src="./images/jpa1.jpg" style="zoom:50%;" />
>
> **JPA和Hibernate的关系**
>
> * JPA 是 hibernate 的一个抽象（就像JDBC和JDBC驱动的关系）：
>   * JPA 是规范：JPA 本质上就是一种  ORM 规范，不是ORM 框架 —— 因为 JPA 并未提供 ORM 实现，它只是制订了一些规范，提供了一些编程的 API 接口，但具体实现则由 ORM 厂商提供实现
>   * Hibernate 是实现：Hibernate 除了作为 ORM 框架之外，它也是一种 JPA 实现
> * 从功能上来说， JPA 是 Hibernate 功能的一个子集
>
> **JPA 的供应商**
>
> * JPA 的目标之一是制定一个可以由很多供应商实现的 API，目前Hibernate 3.2+、TopLink 10.1+ 以及 OpenJPA 都提供了 JPA 的实现
> * Hibernate
>   * JPA 的始作俑者就是 Hibernate 的作者
>   * Hibernate 从 3.2 开始兼容 JPA
> * OpenJPA
>   * OpenJPA  是 Apache 组织提供的开源项目
> * TopLink
>   * TopLink 以前需要收费，如今开源了
>
> **JPA的优势**
>
> * 标准化:  提供相同的 API，这保证了基于JPA 开发的企业应用能够经过少量的修改就能够在不同的 JPA 框架下运行。
> * 简单易用，集成方便:  JPA 的主要目标之一就是提供更加简单的编程模型，在 JPA 框架下创建实体和创建 Java  类一样简单，只需要使用 javax.persistence.Entity 进行注释；JPA 的框架和接口也都非常简单
> * 可媲美JDBC的查询能力:  JPA的查询语言是面向对象的，JPA定义了独特的JPQL，而且能够支持批量更新和修改、JOIN、GROUP BY、HAVING 等通常只有 SQL 才能够提供的高级查询特性，甚至还能够支持子查询。
> * 支持面向对象的高级特性: JPA 中能够支持面向对象的高级特性，如类之间的继承、多态和类之间的复杂
>
> **JPA 包括 3方面的技术**
>
> * ORM  映射元数据：JPA 支持 XML 和  JDK 5.0 注解两种元数据的形式，元数据描述对象和表之间的映射关系，框架据此将实体对象持久化到数据库表中。  
> * JPA 的 API：用来操作实体对象，执行CRUD操作，框架在后台完成所有的事情，开发者从繁琐的 JDBC和 SQL代码中解脱出来。
> * 查询语言（JPQL）：这是持久化操作中很重要的一个方面，通过面向对象而非面向数据库的查询语言查询数据，避免程序和具体的  SQL 紧密耦合。
>
> **使用JPA持久化对象的步骤**
>
> * 创建 persistence.xml, 在这个文件中配置持久化单元
>   * 需要指定跟哪个数据库进行交互;
>   * 需要指定 JPA 使用哪个持久化的框架以及配置该框架的基本属性
> * 创建实体类, 使用 annotation 来描述实体类跟数据库表之间的映射关系.
> * 使用 JPA API 完成数据增加、删除、修改和查询操作
>   * 创建 EntityManagerFactory (对应 Hibernate 中的 SessionFactory);
>   * 创建 EntityManager (对应 Hibernate 中的Session);
>
> **入门示例**
>
> 项目结构图
>
> ![](./images/jpa2.jpg)
>
> pom.xml
>
> ```xml
> <?xml version="1.0" encoding="UTF-8"?>
> <project xmlns="http://maven.apache.org/POM/4.0.0"
>          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
>     <modelVersion>4.0.0</modelVersion>
> 
>     <groupId>groupId</groupId>
>     <artifactId>jpa</artifactId>
>     <version>1.0-SNAPSHOT</version>
> 
>     <dependencies>
>         <!--  hibernate 依赖 -->
>         <!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-entitymanager -->
>         <dependency>
>             <groupId>org.hibernate</groupId>
>             <artifactId>hibernate-entitymanager</artifactId>
>             <version>5.4.1.Final</version>
>         </dependency>
> 
>         <!-- mysql 驱动 -->
>         <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
>         <dependency>
>             <groupId>mysql</groupId>
>             <artifactId>mysql-connector-java</artifactId>
>             <version>8.0.13</version>
>         </dependency>
>     </dependencies>
> </project>
> ```
>
> persistence.xml
>
> ```xml
> <?xml version="1.0" encoding="UTF-8"?>
> <persistence xmlns="http://java.sun.com/xml/ns/persistence" version="2.0">
> 
>     <persistence-unit name="JPA-1" transaction-type="RESOURCE_LOCAL">
>         <!--
>         配置使用什么 ORM 产品来作为 JPA 的实现
>         1. 实际上配置的是  javax.persistence.spi.PersistenceProvider 接口的实现类
>         2. 若 JPA 项目中只有一个 JPA 的实现产品, 则也可以不配置该节点.
>         -->
>         <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
> 
>         <!--添加持久化类-->
>         <class>com.example.helloworld.Customer</class>
>         <properties>
>             <!-- 连接数据库的基本信息 -->
>             <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
>             <property name="javax.persistence.jdbc.url" value="jdbc:mysql://192.168.0.199/test"/>
>             <property name="javax.persistence.jdbc.user" value="root"/>
>             <property name="javax.persistence.jdbc.password" value="root"/>
> 
>             <!-- 配置 JPA 实现产品的基本属性. 配置 hibernate 的基本属性 -->
>             <property name="hibernate.format_sql" value="true"/>
>             <property name="hibernate.show_sql" value="true"/>
>             <property name="hibernate.hbm2ddl.auto" value="update"/>
>         </properties>
>     </persistence-unit>
> </persistence>
> ```
>
> ```java
> package com.example.helloworld;
> import javax.persistence.*;
> 
> @Table(name = "CUSTOMERS")
> @Entity
> public class Customer {
> 
>     private Integer id;
>     private String lastName;
>     private String email;
>     private Integer age;
> 
>     @Column(name = "ID")
>     @GeneratedValue(strategy = GenerationType.AUTO)
>     @Id
>     public Integer getId() {
>         return id;
>     }
> 
>     public void setId(Integer id) {
>         this.id = id;
>     }
> 
>     @Column(name = "LAST_NAME")
>     public String getLastName() {
>         return lastName;
>     }
> 
>     public void setLastName(String lastName) {
>         this.lastName = lastName;
>     }
> 
>     /**
>      *  如果数据库字段名和属性名一致，则不用写@Column注解
>      */
>     public String getEmail() {
>         return email;
>     }
> 
>     public void setEmail(String email) {
>         this.email = email;
>     }
> 
>     public Integer getAge() {
>         return age;
>     }
> 
>     public void setAge(Integer age) {
>         this.age = age;
>     }
> }
> ```
>
> ```java
> package com.example.helloworld;
> import javax.persistence.EntityManager;
> import javax.persistence.EntityManagerFactory;
> import javax.persistence.EntityTransaction;
> import javax.persistence.Persistence;
> 
> public class Main {
> 
>     public static void main(String[] args) {
>         // 1. 创建EntityManagerFactory
>         String persistenceUnitName = "JPA-1";
>         EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
>         // 2. 创建EntityManager
>         EntityManager entityManager = entityManagerFactory.createEntityManager();
>         // 3. 开启事务
>         EntityTransaction transaction = entityManager.getTransaction();
>         transaction.begin();
>         // 4. 进行持久化操作
>         Customer customer = new Customer();
>         customer.setAge(12);
>         customer.setEmail("tom@gmail.com");
>         customer.setLastName("tom");
>         entityManager.persist(customer);
>         // 5. 提交事务
>         transaction.commit();
>         // 6. 关闭EntityManager
>         entityManager.close();
>         // 7. 关闭EntityManagerFactory
>         entityManagerFactory.close();
>     }
> }
> ```

### JPA基本注解

> **@Entity**
>
> * @Entity 标注用于实体类声明语句之前，指出该Java 类为实体类，将映射到指定的数据库表。如声明一个实体类 Customer，它将映射到数据库中的 customer 表上。
>
> **@Table**
>
> * 当实体类与其映射的数据库表名不同名时需要使用 @Table 标注说明，该标注与 @Entity 标注并列使用，置于实体类声明语句之前，可写于单独语句行，也可与声明语句同行。
> * @Table 标注的常用选项是 name，用于指明数据库的表名
> * @Table标注还有两个选项 catalog 和 schema 用于设置表所属的数据库目录或模式，通常为数据库名。uniqueConstraints 选项用于设置约束条件，通常不须设置。
>
> **@Id**
>
> * @Id 标注用于声明一个实体类的属性映射为数据库的主键列。该属性通常置于属性声明语句之前，可与声明语句同行，也可写在单独行上。
> * @Id标注也可置于属性的getter方法之前。
>
> **@GeneratedValue**
>
> * @GeneratedValue  用于标注主键的生成策略，通过 strategy 属性指定。默认情况下，JPA 自动选择一个最适合底层数据库的主键生成策略：SqlServer 对应 identity，MySQL 对应 auto increment。
> * 在 javax.persistence.GenerationType 中定义了以下几种可供选择的策略：
>   * IDENTITY：采用数据库 ID自增长的方式来自增主键字段，Oracle 不支持这种方式；
>   
>   * AUTO： JPA自动选择合适的策略，是默认选项；
>   
>   * SEQUENCE：通过序列产生主键，通过 @SequenceGenerator 注解指定序列名，MySql 不支持这种方式
>   
>   * TABLE：通过表产生主键，框架借由表模拟序列产生主键，使用该策略可以使应用更易于数据库移植。
>   
>     * 将当前主键的值单独保存到一个数据库的表中，主键的值每次都是从指定的表中查询来获得
>     * 这种方法生成主键的策略可以适用于任何数据库，不必担心不同数据库不兼容造成的问题。
>   
>     ```java
>     @Column(name = "ID")
>     @TableGenerator(name = "ID_GENERATOR",table = "id_generators",pkColumnName = "PK_NAME",pkColumnValue = "CUSTOMER_ID",valueColumnName = "PK_VALUE",allocationSize = 100)
>     @GeneratedValue(strategy = GenerationType.TABLE,generator = "ID_GENERATOR")
>     @Id
>     public Integer getId() {
>         return id;
>     }
>     ```
>   
>     ![](./images/jpa5.jpg)
>   
>     <img src="./images/jpa6.jpg" style="zoom:50%;" />
>   
>     * name 属性表示该主键生成策略的名称，它被引用在@GeneratedValue中设置的generator 值中
>     * table 属性表示表生成策略所持久化的表名
>     * pkColumnName 属性的值表示在持久化表中，该主键生成策略所对应键值的名称
>     * valueColumnName 属性的值表示在持久化表中，该主键当前所生成的值，它的值将会随着每次创建累加
>     * pkColumnValue 属性的值表示在持久化表中，该生成策略所对应的主键
>     * allocationSize 表示每次主键值增加的大小, 默认值为 50
>
> **@Basic**
>
> * @Basic 表示一个简单的属性到数据库表的字段的映射,对于没有任何标注的 getXxxx() 方法,默认即为@Basic
> * fetch: 表示该属性的读取策略,有 EAGER 和 LAZY 两种,分别表示主支抓取和延迟加载,默认为 EAGER.
> * optional:表示该属性是否允许为null, 默认为true
>
> **@Column**
>
> * 当实体的属性与其映射的数据库表的列不同名时需要使用@Column 标注说明，该属性通常置于实体的属性声明语句之前，还可与 @Id 标注一起使用。
> * @Column 标注的常用属性是 name，用于设置映射数据库表的列名。此外，该标注还包含其它多个属性，如：unique 、nullable、length 等。
> * @Column 标注的 columnDefinition 属性: 表示该字段在数据库中的实际类型.通常 ORM 框架可以根据属性类型自动判断数据库中字段的类型,但是对于Date类型仍无法确定数据库中字段类型究竟是DATE,TIME还是TIMESTAMP.此外,String的默认映射类型为VARCHAR, 如果要将 String 类型映射到特定数据库的 BLOB 或TEXT 字段类型.
> * @Column标注也可置于属性的getter方法之前
>
> **@Transient**
>
> * 表示该属性并非一个到数据库表的字段的映射,ORM框架将忽略该属性.
> * 如果一个属性并非数据库表的字段映射,就务必将其标示为@Transient,否则,ORM框架默认其注解为@Basic
>
> **@Temporal**
>
> * 在核心的 Java API 中并没有定义 Date 类型的精度(temporal precision).  而在数据库中,表示 Date 类型的数据有 DATE, TIME, 和 TIMESTAMP 三种精度(即单纯的日期,时间,或者两者 兼备). 在进行属性映射时可使用@Temporal注解来调整精度.
>
> ![](./images/jpa3.jpg)
>
> ![](./images/jpa4.jpg)

### JPA的API

##### Persistence

> **JPA相关接口/类：Persistence**
>
> * Persistence  类是用于获取 EntityManagerFactory 实例。该类包含一个名为 createEntityManagerFactory 的 静态方法 。
> * createEntityManagerFactory 方法有如下两个重载版本。
>   * 带有一个参数的方法以 JPA 配置文件 persistence.xml 中的持久化单元名为参数
>   * 带有两个参数的方法：前一个参数含义相同，后一个参数 Map类型，用于设置 JPA 的相关属性，这时将忽略其它地方设置的属性。Map 对象的属性名必须是 JPA 实现库提供商的名字空间约定的属性名。

##### EntityManagerFactory

> **EntityManagerFactory**
>
> * EntityManagerFactory 接口主要用来创建 EntityManager 实例。该接口约定了如下4个方法：
>   * createEntityManager()：用于创建实体管理器对象实例。
>   * createEntityManager(Map map)：用于创建实体管理器对象实例的重载方法，Map 参数用于提供 EntityManager 的属性。
>   * isOpen()：检查 EntityManagerFactory 是否处于打开状态。实体管理器工厂创建后一直处于打开状态，除非调用close()方法将其关闭。
>   * close()：关闭 EntityManagerFactory 。 EntityManagerFactory 关闭后将释放所有资源，isOpen()方法测试将返回 false，其它方法将不能调用，否则将导致IllegalStateException异常。

##### EntityManager

> **EntityManager**
>
> * 在 JPA 规范中, EntityManager 是完成持久化操作的核心对象。实体作为普通 Java 对象，只有在调用 EntityManager 将其持久化后才会变成持久化对象。EntityManager 对象在一组实体类与底层数据源之间进行 O/R 映射的管理。它可以用来管理和更新 Entity Bean, 根椐主键查找 Entity Bean, 还可以通过JPQL语句查询实体。
>
> * 实体的状态:
>
>   * 新建状态:   新创建的对象，尚未拥有持久性主键。
>   * 持久化状态：已经拥有持久性主键并和持久化建立了上下文环境
>   * 游离状态：拥有持久化主键，但是没有与持久化建立上下文环境
>   * 删除状态:  拥有持久化主键，已经和持久化建立上下文环境，但是从数据库中删除。
>
> * 常用方法：
>
>   * find (Class<T> entityClass,Object primaryKey)：返回指定的 OID 对应的实体类对象，如果这个实体存在于当前的持久化环境，则返回一个被缓存的对象；否则会创建一个新的 Entity, 并加载数据库中相关信息；若 OID 不存在于数据库中，则返回一个 null。第一个参数为被查询的实体类类型，第二个参数为待查找实体的主键值。
>   * getReference (Class<T> entityClass,Object primaryKey)：与find()方法类似，不同的是：如果缓存中不存在指定的 Entity, EntityManager 会创建一个 Entity 类的代理，但是不会立即加载数据库中的信息，只有第一次真正使用此 Entity 的属性才加载，所以如果此 OID 在数据库不存在，getReference() 不会返回 null 值, 而是抛出EntityNotFoundException
>   * persist (Object entity)：用于将新创建的 Entity 纳入到 EntityManager 的管理。该方法执行后，传入 persist() 方法的 Entity 对象转换成持久化状态。
>     * 如果传入 persist() 方法的 Entity 对象已经处于持久化状态，则 persist() 方法什么都不做。
>     * 如果对删除状态的 Entity 进行 persist() 操作，会转换为持久化状态。
>     * 如果对游离状态的实体执行 persist() 操作，可能会在 persist() 方法抛出 EntityExistException(也有可能是在flush或事务提交后抛出)。
>   * remove (Object entity)：删除实例。如果实例是被管理的，即与数据库实体记录关联，则同时会删除关联的数据库记录。
>   * merge (T entity)：merge() 用于处理 Entity 的同步。即数据库的插入和更新操作
>     * <img src="./images/jpa_em1.jpg" style="zoom:50%;" />
>   * flush ()：同步持久上下文环境，即将持久上下文环境的所有未保存实体的状态信息保存到数据库中。
>   * setFlushMode (FlushModeType flushMode)：设置持久上下文环境的Flush模式。参数可以取2个枚举
>     * FlushModeType.AUTO 为自动更新数据库实体
>     * FlushModeType.COMMIT 为直到提交事务时才更新数据库记录。
>   * getFlushMode ()：获取持久上下文环境的Flush模式。返回FlushModeType类的枚举值。
>   * refresh (Object entity)：用数据库实体记录的值更新实体对象的状态，即更新实例的属性值。
>   * clear ()：清除持久上下文环境，断开所有关联的实体。如果这时还有未提交的更新则会被撤消。
>   * contains (Object entity)：判断一个实例是否属于当前持久上下文环境管理的实体。
>   * isOpen ()：判断当前的实体管理器是否是打开状态。
>   * getTransaction ()：返回资源层的事务对象。EntityTransaction实例可以用于开始和提交多个事务。
>   * close ()：关闭实体管理器。之后若调用实体管理器实例的方法或其派生的查询对象的方法都将抛出 IllegalstateException 异常，除了getTransaction 和 isOpen方法(返回 false)。不过，当与实体管理器关联的事务处于活动状态时，调用 close 方法后持久上下文将仍处于被管理状态，直到事务完成。
>   * createQuery (String qlString)：创建一个查询对象。
>   * createNamedQuery (String name)：根据命名的查询语句块创建查询对象。参数为命名的查询语句。
>   * createNativeQuery (String sqlString)：使用标准 SQL语句创建查询对象。参数为标准SQL语句字符串。
>   * createNativeQuery (String sqls, String resultSetMapping)：使用标准SQL语句创建查询对象，并指定返回结果集 Map的 名称。
>
> ```xml
> <?xml version="1.0" encoding="UTF-8"?>
> <persistence xmlns="http://java.sun.com/xml/ns/persistence" version="2.0">
>     <persistence-unit name="JPA-1" transaction-type="RESOURCE_LOCAL">
>         <!--
>         配置使用什么 ORM 产品来作为 JPA 的实现
>         1. 实际上配置的是  javax.persistence.spi.PersistenceProvider 接口的实现类
>         2. 若 JPA 项目中只有一个 JPA 的实现产品, 则也可以不配置该节点.
>         -->
>         <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
> 
>         <!--添加持久化类-->
>         <class>com.example.helloworld.Customer</class>
>         <properties>
>             <!-- 连接数据库的基本信息 -->
>             <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
>             <property name="javax.persistence.jdbc.url" value="jdbc:mysql://192.168.0.199/test"/>
>             <property name="javax.persistence.jdbc.user" value="root"/>
>             <property name="javax.persistence.jdbc.password" value="root"/>
> 
>             <!-- 配置 JPA 实现产品的基本属性. 配置 hibernate 的基本属性 -->
>             <property name="hibernate.format_sql" value="true"/>
>             <property name="hibernate.show_sql" value="true"/>
>             <property name="hibernate.hbm2ddl.auto" value="update"/>
>         </properties>
>     </persistence-unit>
> </persistence>
> ```
>
> ```java
> package com.example.helloworld;
> import javax.persistence.*;
> import java.util.Date;
> 
> @Table(name = "CUSTOMERS")
> @Entity
> public class Customer {
> 
>     private Integer id;
>     private String lastName;
>     private String email;
>     private Integer age;
>     private Date createdTime;
>     private Date birth;
> 
>     @Column(name = "ID")
>     @GeneratedValue(strategy = GenerationType.AUTO)
>     @Id
>     public Integer getId() {
>         return id;
>     }
> 
>     public void setId(Integer id) {
>         this.id = id;
>     }
> 
>     @Column(name = "LAST_NAME")
>     public String getLastName() {
>         return lastName;
>     }
> 
>     public void setLastName(String lastName) {
>         this.lastName = lastName;
>     }
> 
>     /**
>      *  如果数据库字段名和属性名一致，则不用写@Column注解
>      */
>     public String getEmail() {
>         return email;
>     }
> 
>     public void setEmail(String email) {
>         this.email = email;
>     }
> 
>     public Integer getAge() {
>         return age;
>     }
> 
>     public void setAge(Integer age) {
>         this.age = age;
>     }
> 
>     @Transient
>     public String getInfo(){
>         return "lastName : " + lastName + " email : " + email;
>     }
> 
>     @Temporal(TemporalType.TIMESTAMP)
>     public Date getCreatedTime() {
>         return createdTime;
>     }
> 
>     public void setCreatedTime(Date createdTime) {
>         this.createdTime = createdTime;
>     }
> 
>     @Temporal(TemporalType.DATE)
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
>         return "Customer{" +
>                 "id=" + id +
>                 ", lastName='" + lastName + '\'' +
>                 ", email='" + email + '\'' +
>                 ", age=" + age +
>                 ", createdTime=" + createdTime +
>                 ", birth=" + birth +
>                 '}';
>     }
> }
> ```
>
> ```java
> package com.example.test;
> import com.example.helloworld.Customer;
> import org.junit.After;
> import org.junit.Before;
> import org.junit.Test;
> import javax.persistence.EntityManager;
> import javax.persistence.EntityManagerFactory;
> import javax.persistence.EntityTransaction;
> import javax.persistence.Persistence;
> import java.util.Date;
> 
> public class JPATest {
> 
>     private EntityManagerFactory entityManagerFactory;
>     private EntityManager entityManager;
>     private EntityTransaction transaction;
> 
>     @Before
>     public void init(){
>         entityManagerFactory = Persistence.createEntityManagerFactory("JPA-1");
>         entityManager = entityManagerFactory.createEntityManager();
>         transaction = entityManager.getTransaction();
>         transaction.begin();
>     }
> 
>     @After
>     public void destroy(){
>         transaction.commit();
>         entityManager.close();
>         entityManagerFactory.close();
>     }
> 
>     /**
>      *  类似于hibernate中Session的get方法，直接发送SQL语句
>      */
>     @Test
>     public void testFind(){
>         Customer customer = entityManager.find(Customer.class, 1);
>         System.out.println(customer);
>     }
> 
>     /**
>      *  类似于Hibernate中Session的load方法，获取的是Customer对象一个代理类，当真正用到这个对象时，才会发送SQL语句(懒加载)
>      */
>     @Test
>     public void testGetReference(){
>         Customer customer = entityManager.getReference(Customer.class, 1);
>         System.out.println(customer);
>     }
> 
>     /**
>      *   类似于Hibernate的save方法，使对象由临时状态变为持久化状态
>      *   和Hibernate的save方法的不同之处：若对象有id，则不能执行insert操作，而会抛出异常
>      */
>     @Test
>     public void testPersist(){
>         Customer customer = new Customer();
>         customer.setAge(15);
>         customer.setBirth(new Date());
>         customer.setCreatedTime(new Date());
>         customer.setEmail("bb@163.com");
>         customer.setLastName("bb");
>         entityManager.persist(customer);
>         System.out.println(customer.getId());
>     }
> 
>     /**
>      *  类似于Hibernate的delete方法，把对象对应的记录从数据库中移除
>      *  注意：该方法只能移除持久化对象，而Hibernate的delete方法实际上还可以移除游离对象
>      */
>     @Test
>     public void testRemove(){
>         Customer customer = entityManager.find(Customer.class, 5);
>         entityManager.remove(customer);
>     }
> 
>     /**
>      *  总的来说：类似于Hibernate Session的saveOrUpdate方法
>      *  1. 若传入的是一个临时对象，会创建一个新的对象，把临时对象的属性复制到新的对象中，然后对新的对象执行持久化操作
>      *      所以新的对象中有id,但以前的临时对象中没有id
>      *  2. 若传入的是一个游离对象，即传入的对象有OID
>      *      (1) 若在EntityManager缓存中没有该对象，并且在数据库中也没有对应的记录，则JPA会创建一个新的对象，然后把当前游离对象的属性复制到新的对象，然后对新创建的对象执行insert操作
>      *     （2）若在EntityManager缓存中没有该对象,并且在数据库中有对应的记录，JPA会查询对应的记录，然后返回该记录对应的对象，再然后会把游离对象的属性复制到查询到的对象中，对查询到的对象执行update操作
>      *     （3）若在EntityManager缓存中有该对象，JPA会把游离对象的属性复制到EntityManager缓存中的对象，然后对EntityManager缓存中的对象执行update
>      */
>     @Test
>     public void testMerge1(){
>         Customer customer = new Customer();
>         customer.setAge(18);
>         customer.setBirth(new Date());
>         customer.setCreatedTime(new Date());
>         customer.setEmail("cc@163.com");
>         customer.setLastName("CC");
>         Customer customer1 = entityManager.merge(customer);
>         System.out.println(" customer id : " + customer.getId());
>         System.out.println("customer1 id : " + customer1.getId());
>     }
> 
>     @Test
>     public void testMerge2(){
>         Customer customer = new Customer();
>         customer.setAge(18);
>         customer.setBirth(new Date());
>         customer.setCreatedTime(new Date());
>         customer.setEmail("dd@163.com");
>         customer.setLastName("DD");
>         customer.setId(6);
>         Customer customer1 = entityManager.merge(customer);
>         System.out.println(" customer id : " + customer.getId());
>         System.out.println("customer1 id : " + customer1.getId());
>     }
> 
>     @Test
>     public void testMerge3(){
>         Customer customer = new Customer();
>         customer.setAge(18);
>         customer.setBirth(new Date());
>         customer.setCreatedTime(new Date());
>         customer.setEmail("EE@163.com");
>         customer.setLastName("EE");
>         customer.setId(6);
> 
>         Customer customer1 = entityManager.find(Customer.class, 6);
>         entityManager.merge(customer);
>     }
> 
>     /**
>      * 同Hibernate中Session的flush方法
>      */
>     @Test
>     public void testFlush(){
>         Customer customer = entityManager.find(Customer.class, 1);
>         System.out.println(customer);
>         customer.setLastName("AA");
>         entityManager.flush();
>     }
> 
>     /**
>      * 同Hibernate中Session的refresh方法
>      */
>     @Test
>     public void testRefresh(){
>         Customer customer = entityManager.find(Customer.class, 1);
>         customer = entityManager.find(Customer.class, 1);
>         entityManager.refresh(customer);
>     }
> }
> ```

##### EntityTransaction

> **概念**
>
> * EntityTransaction 接口用来管理资源层实体管理器的事务操作。通过调用实体管理器的getTransaction方法 获得其实例。
> * 常用方法：
>   * begin ()：用于启动一个事务，此后的多个数据库操作将作为整体被提交或撤消。若这时事务已启动则会抛出 IllegalStateException 异常。
>   * commit ()：用于提交当前事务。即将事务启动以后的所有数据库更新操作持久化至数据库中。
>   * rollback ()：撤消(回滚)当前事务。即撤消事务启动后的所有数据库更新操作，从而不对数据库产生影响。
>   * setRollbackOnly ()：使当前事务只能被撤消。
>   * getRollbackOnly ()：查看当前事务是否设置了只能撤消标志。
>   * isActive ()：查看当前事务是否是活动的。如果返回true则不能调用begin方法，否则将抛出 IllegalStateException 异常；如果返回 false 则不能调用 commit、rollback、setRollbackOnly 及 getRollbackOnly 方法，否则将抛出 IllegalStateException 异常。

### 映射关联关系

##### 单向多对一

> ```xml
> 
> <?xml version="1.0" encoding="UTF-8"?>
> <persistence xmlns="http://java.sun.com/xml/ns/persistence" version="2.0">
>     <persistence-unit name="JPA-1" transaction-type="RESOURCE_LOCAL">
>         <!--
>         配置使用什么 ORM 产品来作为 JPA 的实现
>         1. 实际上配置的是  javax.persistence.spi.PersistenceProvider 接口的实现类
>         2. 若 JPA 项目中只有一个 JPA 的实现产品, 则也可以不配置该节点.
>         -->
>         <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
> 
>         <!--添加持久化类-->
>         <class>com.example.helloworld.Customer</class>
>         <class>com.example.helloworld.Order</class>
>         <properties>
>             <!-- 连接数据库的基本信息 -->
>             <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
>             <property name="javax.persistence.jdbc.url" value="jdbc:mysql://192.168.0.199/test"/>
>             <property name="javax.persistence.jdbc.user" value="root"/>
>             <property name="javax.persistence.jdbc.password" value="root"/>
> 
>             <!-- 配置 JPA 实现产品的基本属性. 配置 hibernate 的基本属性 -->
>             <property name="hibernate.format_sql" value="true"/>
>             <property name="hibernate.show_sql" value="true"/>
>             <property name="hibernate.hbm2ddl.auto" value="update"/>
>         </properties>
>     </persistence-unit>
> </persistence>
> ```
>
> ```java
> package com.example.helloworld;
> 
> import javax.persistence.*;
> import java.util.Date;
> 
> @Table(name = "CUSTOMERS")
> @Entity
> public class Customer {
> 
>     private Integer id;
>     private String lastName;
>     private String email;
>     private Integer age;
>     private Date createdTime;
>     private Date birth;
> 
>     @Column(name = "ID")
>     @GeneratedValue(strategy = GenerationType.AUTO)
>     @Id
>     public Integer getId() {
>         return id;
>     }
> 
>     public void setId(Integer id) {
>         this.id = id;
>     }
> 
>     @Column(name = "LAST_NAME")
>     public String getLastName() {
>         return lastName;
>     }
> 
>     public void setLastName(String lastName) {
>         this.lastName = lastName;
>     }
> 
>     /**
>      *  如果数据库字段名和属性名一致，则不用写@Column注解
>      */
>     public String getEmail() {
>         return email;
>     }
> 
>     public void setEmail(String email) {
>         this.email = email;
>     }
> 
>     public Integer getAge() {
>         return age;
>     }
> 
>     public void setAge(Integer age) {
>         this.age = age;
>     }
> 
>     @Transient
>     public String getInfo(){
>         return "lastName : " + lastName + " email : " + email;
>     }
> 
>     @Temporal(TemporalType.TIMESTAMP)
>     public Date getCreatedTime() {
>         return createdTime;
>     }
> 
>     public void setCreatedTime(Date createdTime) {
>         this.createdTime = createdTime;
>     }
> 
>     @Temporal(TemporalType.DATE)
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
>         return "Customer{" +
>                 "id=" + id +
>                 ", lastName='" + lastName + '\'' +
>                 ", email='" + email + '\'' +
>                 ", age=" + age +
>                 ", createdTime=" + createdTime +
>                 ", birth=" + birth +
>                 '}';
>     }
> }
> ```
>
> ```java
> package com.example.helloworld;
> import javax.persistence.*;
> 
> @Table(name = "ORDERS")
> @Entity
> public class Order {
>     private Integer id;
>     private String orderName;
>     private Customer customer;
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
>     @Column(name = "ORDER_NAME")
>     public String getOrderName() {
>         return orderName;
>     }
> 
>     public void setOrderName(String orderName) {
>         this.orderName = orderName;
>     }
> 
>     /**
>      *  映射单向1-n关联关系
>      *  使用@ManyToOne来映射多对一的关联关系
>      *  使用@JoinColumn来映射外键
>      *  可以使用@ManyToOne的fetch属性来修改默认的关联属性的加载策略
>      * @return
>      */
>     @JoinColumn(name = "CUSTOMER_ID")
>     @ManyToOne(fetch = FetchType.LAZY)
>     public Customer getCustomer() {
>         return customer;
>     }
> 
>     public void setCustomer(Customer customer) {
>         this.customer = customer;
>     }
> }
> ```
>
> ```java
> package com.example.test;
> import com.example.helloworld.Customer;
> import com.example.helloworld.Order;
> import org.junit.After;
> import org.junit.Before;
> import org.junit.Test;
> import javax.persistence.EntityManager;
> import javax.persistence.EntityManagerFactory;
> import javax.persistence.EntityTransaction;
> import javax.persistence.Persistence;
> import java.util.Date;
> 
> public class JPATest {
> 
>     private EntityManagerFactory entityManagerFactory;
>     private EntityManager entityManager;
>     private EntityTransaction transaction;
> 
>     @Before
>     public void init(){
>         entityManagerFactory = Persistence.createEntityManagerFactory("JPA-1");
>         entityManager = entityManagerFactory.createEntityManager();
>         transaction = entityManager.getTransaction();
>         transaction.begin();
>     }
> 
>     @After
>     public void destroy(){
>         transaction.commit();
>         entityManager.close();
>         entityManagerFactory.close();
>     }
> 
>     /**
>      *  保存多对一关联关系时，建议先保存1的一端，后保存n的一端，这样不会多出额外的update语句
>      */
>     @Test
>     public void testManyToOne(){
>         Customer customer = new Customer();
>         customer.setAge(18);
>         customer.setBirth(new Date());
>         customer.setCreatedTime(new Date());
>         customer.setEmail("lucy@gmail.com");
>         customer.setLastName("lucy");
> 
>         Order order1 = new Order();
>         order1.setOrderName("order-1");
> 
>         Order order2 = new Order();
>         order2.setOrderName("order-2");
> 
>         // 设置关联关系
>         order1.setCustomer(customer);
>         order2.setCustomer(customer);
>         // 执行保存操作
>         entityManager.persist(customer);
>         entityManager.persist(order1);
>         entityManager.persist(order2);
>     }
> 
>     /**
>      *  默认情况下使用左外连接的方式来获取n的一端的对象和其关联的1的一端的对象
>      */
>     @Test
>     public void testManyToOneFind(){
>         Order order = entityManager.find(Order.class, 9);
>         System.out.println(order.getOrderName());
>         System.out.println(order.getCustomer().getLastName());
>     }
> 
>     /**
>      *  不能直接删除1的一端，因为有外键约束
>      */
>     @Test
>     public void testManyToOneRemove(){
> //        Order order = entityManager.find(Order.class, 9);
> //        entityManager.remove(order);
>         Customer customer = entityManager.find(Customer.class, 8);
>         entityManager.remove(customer);
>     }
> 
>     @Test
>     public void testManyToOneUpdate(){
>         Order order = entityManager.find(Order.class, 10);
>         order.getCustomer().setLastName("charles");
>     }
> 
> }
> ```

