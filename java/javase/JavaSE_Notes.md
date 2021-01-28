# JavaSE学习笔记

### 多线程

##### 线程的创建

> **Thread中的常用方法**
>
> 1. start():启动当前线程，调用当前线程的run()
>
> 2. run():通常需要重写Thread类中的此方法，将创建的线程要执行的操作声明在此方法中
>
> 3. currentThread(): 静态方法，返回执行当前代码的线程
>
> 4. getName(): 获取当前线程的名字
>
> 5. setName(): 设置当前线程的名字
>
> 6. yield(): 释放当前CPU的执行权
>
> 7. join(): 等待该线程终止，在线程A中调用线程B的join()，此时线程A就进入阻塞状态，直到线程B完全执行完以后，线程A才结束阻塞状态
>
> 8. sleep(long millitime): 让当前线程睡眠指定的millitime毫秒
>
> 9. isAlive(): 判断当前线程是否存活
>
> 10. getPriority():  获取当前线程的优先级
>
> 11. setPriority(int p): 设置当前线程的优先级(MAX_PRIORITY 10  MIN_PRIORITY 1  NORM_PRIORITY 5 默认优先级)
>
>     **创建多线程方式一：继承Thread类**
>
>     ```java
>     package com.thread;
>     
>     /**
>      *  多线程的创建，方式一：继承于Thread类
>      *  1.创建一个继承于Thread类的子类
>      *  2.重写Thread类的run()
>      *  3.创建Thread类的子类的对象
>      *  4.通过此对象调用start()
>      */
>     public class ThreadTest {
>     
>         public static void main(String[] args) {
>             MyThread t = new MyThread();
>             t.start();
>     
>             for (int i = 0; i < 100; i++) {
>                 if(i % 2 == 0){
>                     System.out.println(Thread.currentThread().getName() + " : " + i);
>                 }
>     
>                 if(i == 20){
>                     try {
>                         t.join();
>                     } catch (InterruptedException e) {
>                         e.printStackTrace();
>                     }
>                 }
>             }
>     
>         }
>     }
>     
>     class MyThread extends Thread{
>     
>         @Override
>         public void run() {
>     
>             for (int i = 0; i < 100; i++) {
>                 if(i % 2 == 0){
>                     System.out.println(Thread.currentThread().getName() + " : " + i);
>                 }
>     
>                 if(i % 20 == 0){
>                     yield();
>                 }
>             }
>         }
>     
>     }
>     ```
>
>     **创建多线程方式二：实现Runnable接口**
>
>     ```java
>     package com.thread;
>     
>     /**
>      *  多线程的创建，方式二：实现Runnable接口
>      *  1.创建一个实现了Runnable接口的类
>      *  2.实现类去实现Runnable中的抽象方法run()
>      *  3.创建实现类的对象
>      *  4.将此对象作为参数传递到Thread类的构造器中，创建Thread类的对象
>      *  5.通过Thread类的对象调用start()
>      *  比较创建线程的两种方式
>      *  开发中优先选择实现Runnable接口的方式
>      *  原因：1. 实现的方式没有类的单继承的局限性
>      *  2. 实现的方式更适合来处理多个线程有共享数据的情况。
>      */
>     public class ThreadTest {
>     
>         public static void main(String[] args) {
>     
>             // 创建实现类的对象
>             MyThread myThread = new MyThread();
>     
>             // 将此对象作为参数传入Thread类的构造器中，创建Thread类的对象
>             Thread t = new Thread(myThread);
>     
>             // 通过Thread类的对象调用start()
>             t.start();
>     
>         }
>     }
>     
>     class MyThread implements Runnable{
>     
>         @Override
>         public void run() {
>     
>             for (int i = 0; i < 100; i++) {
>                 if(i % 2 == 0){
>                     System.out.println(Thread.currentThread().getName() + " : " + i);
>                 }
>             }
>         }
>     
>     }
>     ```
>
>     **创建多线程方式三：实现Callable接口**
>
>     与Runnable相比，Callable功能更强大些
>
>     - 相比run()方法，可以有返回值
>     - 方法可以抛出异常
>     - 支持泛型的返回值
>     - 需要借助Future Task类，比如获取返回结果
>     - 支持泛型
>
>     Future接口
>
>     - 可以对具体Runnable、Callable任务的执行结果进行取消、查询是否完成、获取结果等
>     - FutureTask是Future接口的唯一的实现类
>     - FutureTask同时实现了Runnable、Future接口。它既可以作为Runnable被线程执行，又可以作为Future得到Callable的返回值
>
>     ```java
>     package com.thread;
>     
>     import java.util.concurrent.Callable;
>     import java.util.concurrent.ExecutionException;
>     import java.util.concurrent.FutureTask;
>     
>     public class ThreadNew {
>     
>         public static void main(String[] args) {
>             //创建Callable接口实现类的对象
>             NumThread numThread = new NumThread();
>             // 将此Callable接口实现类的对象作为传递到FutureTask构造器中，创建FutureTask的对象
>             FutureTask<Integer> futureTask = new FutureTask<Integer>(numThread);
>             // FutureTask的对象作为参数传递到Thread类的构造器中，创建Thread对象，并start()
>             new Thread(futureTask).start();
>             try {
>                 // get() 的返回值即为FutureTask构造器Callable实现类重写的call()的返回值
>                 Integer sum = futureTask.get();
>                 System.out.println("总和为：" + sum);
>             } catch (InterruptedException e) {
>                 e.printStackTrace();
>             } catch (ExecutionException e) {
>                 e.printStackTrace();
>             }
>         }
>     }
>     
>     class NumThread implements Callable<Integer>{
>     
>         // 实现call方法，将此线程需要执行的操作声明在call()中
>         @Override
>         public Integer call() throws Exception {
>             int sum = 0;
>             for (int i = 0; i < 100; i++) {
>                 if(i % 2 == 0){
>                     System.out.println(i);
>                     sum += i;
>                 }
>             }
>             return sum;
>         }
>     }
>     ```
>
>     **创建多线程方式四：使用线程池**
>
>     ```java
>     package com.thread;
>     
>     import java.util.concurrent.ExecutorService;
>     import java.util.concurrent.Executors;
>     import java.util.concurrent.ThreadPoolExecutor;
>     
>     public class ThreadPool {
>     
>         public static void main(String[] args) {
>             // 提供指定线程数量的线程池
>             ExecutorService service = Executors.newFixedThreadPool(10);
>     
>             // 设置线程池属性
>             ThreadPoolExecutor service1 = (ThreadPoolExecutor) service;
>             service1.setCorePoolSize(15);
>             service1.setMaximumPoolSize(20);
>     
>             // 执行指定的线程操作，需要提供实现Runnable接口或Callable接口实现类的对象
>             service.execute(new NumberThread());    // 适用于Runnable
>             service.execute(new NumberThread1());    // 适用于Runnable
>     //        service.submit(Callable callable);   // 适用于Callable
>             service.shutdown(); // 关闭线程池
>         }
>     
>     }
>     
>     class NumberThread implements Runnable {
>     
>         @Override
>         public void run() {
>             for (int i = 0; i < 100; i++) {
>                 if(i % 2 == 0){
>                     System.out.println(Thread.currentThread().getName() + " : " + i);
>                 }
>             }
>         }
>     }
>     
>     class NumberThread1 implements Runnable {
>     
>         @Override
>         public void run() {
>             for (int i = 0; i < 100; i++) {
>                 if(i % 2 != 0){
>                     System.out.println(Thread.currentThread().getName() + " : " + i);
>                 }
>             }
>         }
>     }
>     ```
>
>     


##### 线程安全

> 在Java中，通过同步机制，来解决线程安全问题。
>
> **方式一：同步代码块**
>
> synchronized(同步监视器){
>
> ​	//需要被同步的代码块
>
> }
>
> 说明：
>
> 1. 操作共享数据的代码，即为需要被同步的代码。
> 2. 共享数据： 多个线程共同操作的变量。
> 3. 同步监视器，俗称：锁。任何一个类的对象，都可以充当锁。（要求：多个线程必须要共用同一把锁）
>
> ```java
> package com.thread;
> 
> public class WindowTest {
> 
>     public static void main(String[] args) {
>         Window w = new Window();
>         Thread t1 = new Thread(w);
>         Thread t2 = new Thread(w);
>         Thread t3 = new Thread(w);
>         t1.setName("窗口1");
>         t2.setName("窗口2");
>         t3.setName("窗口3");
> 
>         t1.start();
>         t2.start();
>         t3.start();
>     }
> 
> }
> 
> class Window implements Runnable {
>     private int ticket = 100;
>     @Override
>     public void run() {
>         while(true){
>             synchronized(this){	// 代表Window对象w
>                 if(ticket > 0){
>                     try {
>                         Thread.sleep(100);
>                     } catch (InterruptedException e) {
>                         e.printStackTrace();
>                     }
>                     System.out.println(Thread.currentThread().getName() + "：卖票，票号为：" + ticket);
>                     ticket--;
>                 }else{
>                     break;
>                 }
>             }
>         }
>     }
> }
> 
> 
> //====================================================================
> // 类继承方式
> package com.thread;
> 
> public class WindowTest {
> 
>     public static void main(String[] args) {
>         Window w1 = new Window();
>         Window w2 = new Window();
>         Window w3 = new Window();
>         w1.setName("窗口1");
>         w2.setName("窗口2");
>         w3.setName("窗口3");
> 
>         w1.start();
>         w2.start();
>         w3.start();
>     }
> 
> }
> 
> 
> class Window extends Thread {
> 
>     private static int ticket = 100;
> 
>     @Override
>     public void run() {
> 
>         while(true){
> 
>             synchronized(Window.class){
>                 if(ticket > 0){
>                     try {
>                         Thread.sleep(100);
>                     } catch (InterruptedException e) {
>                         e.printStackTrace();
>                     }
>                     System.out.println(Thread.currentThread().getName() + "：卖票，票号为：" + ticket);
>                     ticket--;
>                 }else{
>                     break;
>                 }
>             }
> 
>         }
> 
>     }
> }
> ```
>
> 
>
> **方式二：同步方法**
>
> 如果操作共享数据的代码完整的声明在一个方法中，我们可以将此方法声明为同步的。
>
> 1. 同步方法仍然涉及到同步监视器，只是不需要我们显式的声明。
> 2. 非静态的同步方法，同步监视器是this。
> 3. 静态的同步方法，同步监视器是当前类本身。
>
> ```java
> package com.thread;
> 
> public class WindowTest {
> 
>     public static void main(String[] args) {
>         Window w = new Window();
>         Thread t1 = new Thread(w);
>         Thread t2 = new Thread(w);
>         Thread t3 = new Thread(w);
>         t1.setName("窗口1");
>         t2.setName("窗口2");
>         t3.setName("窗口3");
>         t1.start();
>         t2.start();
>         t3.start();
>     }
> 
> }
> 
> 
> class Window implements Runnable {
>     private int ticket = 100;
>     @Override
>     public void run() {
>         while(true){
>             show();
>         }
>     }
> 
>     private synchronized void show(){   // 同步监视器：this
>         if(ticket > 0){
>             System.out.println(Thread.currentThread().getName() + "：卖票，票号为：" + ticket);
>             ticket--;
>         }
>     }
> }
> 
> 
> //=======================================================================================
> //使用同步方法解决继承Thread类的多线程
> package com.thread;
> 
> public class WindowTest {
> 
>     public static void main(String[] args) {
>         Window w1 = new Window();
>         Window w2 = new Window();
>         Window w3 = new Window();
>         w1.setName("窗口1");
>         w2.setName("窗口2");
>         w3.setName("窗口3");
>         w1.start();
>         w2.start();
>         w3.start();
>     }
> 
> }
> 
> 
> class Window extends Thread {
> 
>     private static int ticket = 100;
> 
>     @Override
>     public void run() {
> 
>         while(true){
> 
>             show();
>         }
> 
>     }
> 
>     private static synchronized void show(){   // 同步监视器：Window.class
> 
>         if(ticket > 0){
>             System.out.println(Thread.currentThread().getName() + "：卖票，票号为：" + ticket);
>             ticket--;
>         }
>     }
> }
> ```
>
> **方式三：Lock锁**
>
> ```java
> package com.thread;
> import java.util.concurrent.locks.ReentrantLock;
> public class WindowTest {
>     public static void main(String[] args) {
>         Window w = new Window();
>         Thread t1 = new Thread(w);
>         Thread t2 = new Thread(w);
>         Thread t3 = new Thread(w);
>         t1.setName("窗口1");
>         t2.setName("窗口2");
>         t3.setName("窗口3");
>         t1.start();
>         t2.start();
>         t3.start();
>     }
> }
> class Window implements Runnable {
>     private int ticket = 100;
>     private ReentrantLock lock = new ReentrantLock(true);
>     @Override
>     public void run() {
>         while(true){
>             try{
>                 // 调用lock方法
>                 lock.lock();
>                 if(ticket > 0){
>                     try {
>                         Thread.sleep(100);
>                     } catch (InterruptedException e) {
>                         e.printStackTrace();
>                     }
>                     System.out.println(Thread.currentThread().getName() + "：卖票，票号为：" + ticket);
>                     ticket--;
>                 }else{
>                     break;
>                 }
>             }finally {
>                 lock.unlock();
>             }
>         }
>     }
> }
> ```

##### 线程通信

>  **线程休眠与唤醒**
>
>  ```java
>  package com.thread;
>  public class CommunicationTest {
>   public static void main(String[] args) {
>       Number number = new Number();
>       Thread t1 = new Thread(number);
>       Thread t2 = new Thread(number);
>       t1.setName("线程1");
>       t2.setName("线程2");
>       t1.start();
>       t2.start();
>   }
>  }
>  
>  class Number implements Runnable{
>   private int number = 1;
>   @Override
>   public void run() {
>       while (true){
>           synchronized (this) {
>               notifyAll();    // 必须用同步监视器调用(Object类中的方法)
>               if(number <= 100){
>  
>                   try {
>                       Thread.sleep(100);
>                   } catch (InterruptedException e) {
>                       e.printStackTrace();
>                   }
>                   System.out.println(Thread.currentThread().getName() + " : " + number);
>                   number++;
>                   try {
>                       // 使得调用如下wait()方法的线程进入阻塞状态，并且释放当前线程持有的锁，必须用同步监视器调用
>                       wait();
>                   } catch (InterruptedException e) {
>                       e.printStackTrace();
>                   }
>               }else{
>                   break;
>               }
>           }
>       }
>   }
>  }
>  ```
>
>  **生产者与消费者经典例子**
>
>  ```java
>  package com.thread;
>  
>  public class ProductTest {
>  
>      public static void main(String[] args) {
>          Clerk clerk = new Clerk();
>          Producer p1 = new Producer(clerk);
>          p1.setName("生产者1");
>          Consumer c1 = new Consumer(clerk);
>          c1.setName("消费者1");
>          Consumer c2 = new Consumer(clerk);
>          c2.setName("消费者2");
>          p1.start();
>          c1.start();
>          c2.start();
>      }
>  }
>  
>  class Clerk{
>  
>      private int productCount = 0;
>  
>      public synchronized void produceProduct() {
>          if(productCount < 20){
>              productCount++;
>              System.out.println(Thread.currentThread().getName() + "：开始生产第" + productCount + "个产品");
>              notify();
>          }else{
>              try {
>                  wait();
>              } catch (InterruptedException e) {
>                  e.printStackTrace();
>              }
>          }
>      }
>  
>      public synchronized void consumeProduct() {
>          if(productCount > 0){
>              System.out.println(Thread.currentThread().getName() + "：开始消费第" + productCount + "个产品");
>              productCount--;
>              notify();
>          }else{
>              try {
>                  wait();
>              } catch (InterruptedException e) {
>                  e.printStackTrace();
>              }
>          }
>      }
>  }
>  
>  class Producer extends Thread {  // 生产者
>      private Clerk clerk;
>  
>      public Producer(Clerk clerk){
>          this.clerk = clerk;
>      }
>  
>      @Override
>      public void run() {
>          System.out.println(getName() + "：开始生产产品...");
>          while(true){
>  
>              try {
>                  Thread.sleep(100);
>              } catch (InterruptedException e) {
>                  e.printStackTrace();
>              }
>              clerk.produceProduct();
>          }
>      }
>  }
>  
>  class Consumer extends Thread { // 消费者
>      private Clerk clerk;
>  
>      public Consumer(Clerk clerk){
>          this.clerk = clerk;
>      }
>  
>      @Override
>      public void run() {
>          System.out.println(getName() + "：开始消费产品...");
>          while(true){
>  
>              try {
>                  Thread.sleep(150);
>              } catch (InterruptedException e) {
>                  e.printStackTrace();
>              }
>              clerk.consumeProduct();
>          }
>      }
>  }
>  ```
>
>  

### 常用类

##### String（字符串）

> 常用方法
>
> ```java
> String s1 = "HelloWorld";
> String s2 = "helloworld";
> 
> // 返回字符串长度
> System.out.println(s1.length());
> 
> // 返回某索引处的字符
> System.out.println(s1.charAt(1));
> 
> // 将String中的所有字符转换为小写
> System.out.println(s1.toLowerCase());
> 
> // 将String中的所有字符转换为大写
> System.out.println(s1.toUpperCase());
> 
> // 判断是否是空字符串
> System.out.println(s1.isEmpty());
> 
> // 返回字符串的副本，忽略前面和后面的空白
> System.out.println(s1.trim());
> 
> // 比较字符串内容是否相同
> System.out.println(s1.equals(s2));
> 
> // 比较字符串内容是否相同（忽略大小写）
> System.out.println(s1.equalsIgnoreCase(s2));
> 
> // 将指定字符串连接到此字符串的结尾。等价于用"+"
> System.out.println(s1.concat(",Tom"));
> 
> // 比较两个字符串的大小
> System.out.println(s1.compareTo(s2));
> 
> // 返回字符串的子串
> System.out.println(s1.substring(3));
> System.out.println(s1.substring(3, 6));
> 
> // 测试此字符串是否以指定的后缀结束
> System.out.println(s1.endsWith("orld"));
> 
> // 测试此字符串是否以指定的前缀开始
> System.out.println(s1.startsWith("Hello"));
> 
> // 测试此字符串从指定索引开始的子字符串是否以指定的前缀开始
> System.out.println(s1.startsWith("ll", 2));
> 
> // 判断此字符串是否包含某个子串
> System.out.println(s1.contains("llo"));
> 
> // 返回子字符串在此字符串中第一次出现的索引
> System.out.println(s1.indexOf("llo"));
> 
> // 从某个位置开始查找子串的位置
> System.out.println(s1.indexOf("ld", 5));
> 
> // 返回指定子串在此字符串中最右边出现的索引
> System.out.println(s1.lastIndexOf("ld"));
> 
> // 替换字符串中指定的子串（所有的）
> System.out.println(s1.replace("l", "你"));
> 
> // 使用给定的 replacement 替换此字符串所有匹配给定的正则表达式的子字符串。
> String s3 = "java111111java2343242hello";
> System.out.println(s3.replaceAll("\\d+", ","));
> 
> // 判断此字符串是否匹配给定的正则表达式。
> String s4 = "12345";
> System.out.println(s4.matches("\\d+"));
> 
> // 根据给定正则表达式的匹配拆分此字符串
> String s5 = "java|javascript|golang|c|c++|php|python";
> String[] arr = s5.split("\\|");
> for (int i = 0; i < arr.length; i++) {
>     System.out.println(arr[i]);
> }
> ```
>
> String与char[]之间的转换
>
> ```java
> String s1 = "HelloWorld";
> char[] arr = s1.toCharArray();
> for (int i = 0; i < arr.length; i++) {
>     System.out.println(arr[i]);
> }
> char[] arr1 = new char[]{'h','e','l','l','o'};
> String s2 = new String(arr1);
> System.out.println(s2);
> ```
>
> String与byte[]之间的转换
>
> ```java
> String s1 = "HelloWorld中国";
> byte[] bytes = s1.getBytes();
> System.out.println(Arrays.toString(bytes));
> String s2 = new String(bytes);
> System.out.println(s2);
> ```
>
> StringBuffer
>
> ```java
> // StringBuffer常用方法
> StringBuffer s1 = new StringBuffer("abc");
> // 设置指定位置内容
> s1.setCharAt(0,'m');
> // 拼接字符串
> s1.append("hello");
> // 删除指定位置的内容
> s1.delete(2,4);
> // 替换指定位置内容
> s1.replace(2,4,"hello");
> // 指定位置插入字符串
> s1.insert(1,"aaa");
> // 获取字符串长度
> System.out.println(s1.length());
> // 字符串反转
> s1.reverse();
> // 获取子串
> String s2 = s1.substring(2,6);
> System.out.println(s2);
> System.out.println(s1);
> ```

##### 日期和时间

> ```java
> // 获取以毫秒为单位的时间戳
> long time = System.currentTimeMillis();
> System.out.println(time);
> 
> Date date1 = new Date();
> System.out.println(date1.toString());
> 
> // 也是获取以毫秒为单位的时间戳
> System.out.println(date1.getTime());
> 
> Date date2 = new Date(1610898997886L);
> System.out.println(date2.toString());
> 
> // 创建java.sql.Date 对象
> java.sql.Date date3 = new java.sql.Date(1610898997886L);
> System.out.println(date3);
> 
> // 将java.util.Date对象转换为java.sql.Date对象
> Date date4 = new Date();
> java.sql.Date date5 = new java.sql.Date(date4.getTime());
> System.out.println(date5);
> ```
>
> **日期和时间的格式化**
>
> ```java
> package com.usual;
> import org.junit.Test;
> import java.text.ParseException;
> import java.text.SimpleDateFormat;
> import java.util.Date;
> public class DateTest {
>     @Test
>     public void test1() throws ParseException {
> 
>         // 默认格式化
>         SimpleDateFormat sdf = new SimpleDateFormat();
>         Date date = new Date();
>         System.out.println(date);
>         String format = sdf.format(date);
>         System.out.println(format);
> 
>         // 按照指定的格式，格式化
>         SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
>         String format1 = sdf1.format(date);
>         System.out.println(format1);
> 
>         // 解析
>         String str = "2021/01/18 15:04:05";
>         Date date1 = sdf1.parse(str);
>         System.out.println(date1);
> 
>     }
> }
> ```
>
> **Calendar日历类(抽象类)的使用**
>
> ```java
> package com.usual;
> import org.junit.Test;
> import java.util.Calendar;
> import java.util.Date;
> 
> public class DateTest {
> 
>     @Test
>     public void test1() {
> 
>         //方式一：创建其子类(GregorianCalendar)的对象
>         // 方式二：调用其静态方法getInstance()
>         Calendar calendar = Calendar.getInstance();
>         System.out.println(calendar.getClass());
> 
>         //get()
>         int days = calendar.get(Calendar.DAY_OF_MONTH); // 获取这个日期是这一个月的第几天
>         System.out.println(days);
> 
>         // set()
>         calendar.set(Calendar.DAY_OF_MONTH,22);
>         int days2 = calendar.get(Calendar.DAY_OF_MONTH);
>         System.out.println(days2);
> 
>         // add()
>         calendar.add(Calendar.DAY_OF_MONTH,3);
>         int days3 = calendar.get(Calendar.DAY_OF_MONTH);
>         System.out.println(days3);
> 
>         // getTime()
>         Date date = calendar.getTime();
>         System.out.println(date);
> 
>         // setTime()
>         Date date1 = new Date();
>         calendar.setTime(date1);
>         int days4 = calendar.get(Calendar.DAY_OF_MONTH);
>         System.out.println(days4);
>     }
> }
> ```
>
> **LocalDate、LocalTime、LocalDateTime(JDK8及以上)**
>
> ```java
> package com.usual;
> import org.junit.Test;
> import java.time.LocalDate;
> import java.time.LocalDateTime;
> import java.time.LocalTime;
> 
> public class DateTest {
>     @Test
>     public void test1() {
>         LocalDate localDate = LocalDate.now();
>         LocalTime localTime = LocalTime.now();
>         LocalDateTime localDateTime = LocalDateTime.now();
>         System.out.println(localDate);
>         System.out.println(localTime);
>         System.out.println(localDateTime);
> 
>         // 设置年月日，时分秒，没有偏移量
>         LocalDateTime localDateTime1 = LocalDateTime.of(2021, 1, 6, 10, 10, 20);
>         System.out.println(localDateTime1);
> 
>         // getXXX() 获取相关属性
>         int day = localDateTime.getDayOfMonth();
>         System.out.println(day);
>         System.out.println(localDateTime.getDayOfWeek());
> 
>         // withXXX() 设置相关属性
>         // 体现不可变性，原来的日期不变，返回新的日期
>         LocalDate localDate1 = localDate.withDayOfMonth(22);
>         System.out.println(localDate);
>         System.out.println(localDate1);
> 
>         LocalDateTime localDateTime2 = localDateTime.withHour(5);
>         System.out.println(localDateTime);
>         System.out.println(localDateTime2);
> 
>         // plusXXX() 相加
>         LocalDateTime localDateTime3 = localDateTime.plusMonths(3);
>         System.out.println(localDateTime);
>         System.out.println(localDateTime3);
>     }
> }
> ```
>
> **Instant**
>
> 用来记录应用程序的事件时间戳
>
> ```java
> package com.usual;
> import org.junit.Test;
> import java.time.Instant;
> import java.time.OffsetDateTime;
> import java.time.ZoneOffset;
> public class DateTest {
> 
>     @Test
>     public void test1() {
>         Instant instant = Instant.now();
>         System.out.println(instant);
>         OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8));
>         System.out.println(offsetDateTime);
> 
>         // 获取自1970年1月1日0时0分0秒（UTC）开始的毫秒数
>         long milli = instant.toEpochMilli();
>         System.out.println(milli);
> 
>         // 将以毫秒计数的时间戳，转换为Instant
>         Instant instant1 = Instant.ofEpochMilli(1610948728902L);
>         System.out.println(instant1);
>     }
> }
> ```
>
> **DateTimeFormatter**
>
> ```java
> LocalDateTime localDateTime = LocalDateTime.now();
> DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
> String str = formatter.format(localDateTime);
> TemporalAccessor parse = formatter.parse(str);
> System.out.println(parse);
> 
> // 本地化相关的格式
> DateTimeFormatter formatter1 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
> 
> // 格式化
> String str2 = formatter1.format(localDateTime);
> System.out.println(str2);
> 
> DateTimeFormatter formatter2 = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
> String str3 = formatter2.format(LocalDate.now());
> System.out.println(str3);
> 
> // 自定义格式
> DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
> String str4 = formatter3.format(localDateTime);
> System.out.println(str4);
> 
> TemporalAccessor parse1 = formatter3.parse("2021-01-18 02:31:36");
> System.out.println(parse1);
> ```
>
> **Comparable自然排序**
>
> ```java
> //Goods.java
> package com.usual;
> public class Goods implements Comparable{
> 
>     private String name;
>     private double price;
>     public Goods(){
> 
>     }
>     public Goods(String name, double price) {
>         this.name = name;
>         this.price = price;
>     }
>     public String getName() {
>         return name;
>     }
>     public double getPrice() {
>         return price;
>     }
>     public void setName(String name) {
>         this.name = name;
>     }
>     public void setPrice(double price) {
>         this.price = price;
>     }
> 
>     // 指明商品比较大小的方式
>     @Override
>     public int compareTo(Object o) {
>         if(o instanceof Goods){
>             Goods goods = (Goods) o;
>             if(this.price > goods.price){
>                 return 1;
>             }else if(this.price < goods.price){
>                 return -1;
>             }else{
>                 return 0;
>             }
>         }
> 
>         throw new RuntimeException("传入的数据类型不一致！");
>     }
> 
>     @Override
>     public String toString() {
>         return "Goods{" +
>                 "name='" + name + '\'' +
>                 ", price=" + price +
>                 '}';
>     }
> }
> 
> // CompareTest.java
> package com.usual;
> import org.junit.Test;
> import java.util.Arrays;
> public class CompareTest {
>     @Test
>     public void test(){
>         Goods[] arr = new Goods[4];
>         arr[0] = new Goods("lenovoMouse",34);
>         arr[1] = new Goods("dellMouse",43);
>         arr[2] = new Goods("xiaomiMouse",12);
>         arr[3] = new Goods("huaweiMouse",65);
>         Arrays.sort(arr);
>         System.out.println(Arrays.toString(arr));
>     }
> }
> ```
>
> **Comparator接口**
>
> Comparator接口的使用：定制排序
>
> 1. 背景：当元素的类型没有实现java.lang.Comparable接口而又不方便修改代码，或者实现了java.lang.Comparable接口的排序规则不适合当前操作，那么可以考虑使用Comparator的对象来排序。
> 2. 重写compare(Object o1,Object o2)方法，比较o1和o2的大小：如果方法返回正整数，则表示o1大于o2；如果返回0，表示相等；返回负整数，表示o1小于o2。
>
> ```java
> // Goods.java
> package com.usual;
> 
> public class Goods implements Comparable{
> 
>     private String name;
>     private double price;
> 
>     public Goods(){
> 
>     }
> 
>     public Goods(String name, double price) {
>         this.name = name;
>         this.price = price;
>     }
> 
>     public String getName() {
>         return name;
>     }
> 
>     public double getPrice() {
>         return price;
>     }
> 
>     public void setName(String name) {
>         this.name = name;
>     }
> 
>     public void setPrice(double price) {
>         this.price = price;
>     }
> 
>     // 指明商品比较大小的方式
>     @Override
>     public int compareTo(Object o) {
>         if(o instanceof Goods){
>             Goods goods = (Goods) o;
>             if(this.price > goods.price){
>                 return 1;
>             }else if(this.price < goods.price){
>                 return -1;
>             }else{
>                 return 0;
>             }
>         }
> 
>         throw new RuntimeException("传入的数据类型不一致！");
>     }
> 
>     @Override
>     public String toString() {
>         return "Goods{" +
>                 "name='" + name + '\'' +
>                 ", price=" + price +
>                 '}';
>     }
> }
> 
> 
> // CompareTest.java
> package com.usual;
> import org.junit.Test;
> import java.util.Arrays;
> import java.util.Comparator;
> public class CompareTest {
>     @Test
>     public void test(){
> 
>         Goods[] arr = new Goods[4];
>         arr[0] = new Goods("lenovoMouse",34);
>         arr[1] = new Goods("dellMouse",43);
>         arr[2] = new Goods("xiaomiMouse",12);
>         arr[3] = new Goods("huaweiMouse",65);
>         Arrays.sort(arr, new Comparator<Goods>() {
>             @Override
>             public int compare(Goods o1, Goods o2) {
>                 if(o1.getName().equals(o2.getName())){
>                     return -Double.compare(o1.getPrice(),o2.getPrice());
>                 }else{
>                     return o1.getName().compareTo(o2.getName());
>                 }
>             }
>         });
>         System.out.println(Arrays.toString(arr));
>     }
> }
> ```

##### System类

> **成员方法**
>
> - native long currentTimeMillis()  ： 该方法的作用是返回当前计算机时间，以毫秒计的Unix时间戳
> - void exit(int status)：该方法的作用是退出程序，其中status的值为0代表正常退出，非零代表异常退出
> - void gc()：请求系统进行垃圾回收
> - String getProperty(String key)：该方法的作用是获得系统中属性名为key的属性对应的值（例如：java.version  java.home  os.name）

##### Math类

> **成员方法**
>
> - abs 绝对值
> - acos  asin  atan cos sin tan  三角函数
> - sqrt 平方根
> - pow(double a,double b)  a的b次幂
> - log  自然对数
> - exp  e为底指数
> - max(double a,double b)
> - min(double a,double b)
> - random()  返回0.0到1.0的随机数
> - long round(double a)  double型数据a转换为long型（四舍五入）
> - toDegrees(double angrad)  弧度--->角度
> - toRadians(doubole angdeg)  角度--->弧度

##### 枚举类

> **自定义枚举类**
>
> ```java
> package com.enumtest;
> public class SeasonTest {
>     public static void main(String[] args) {
>         Season spring = Season.SPRING;
>         System.out.println(spring);
>     }
> }
> 
> // 自定义枚举类
> class Season {
>     private final String name;
>     private final String desc;
> 
>     private Season(String name,String desc) {
>         this.name = name;
>         this.desc = desc;
>     }
>     public static final Season SPRING = new Season("春天","春暖花开");
>     public static final Season SUMMER = new Season("夏天","夏日炎炎");
>     public static final Season AUTUMN = new Season("秋天","秋高气爽");
>     public static final Season WINTER  = new Season("冬天","冰天雪地");
> 
>     public String getName() {
>         return name;
>     }
> 
>     public String getDesc() {
>         return desc;
>     }
>     @Override
>     public String toString() {
>         return "Season{" +
>                 "name='" + name + '\'' +
>                 ", desc='" + desc + '\'' +
>                 '}';
>     }
> }
> ```
>
> **使用enum关键字定义枚举类**
>
> 定义的枚举类默认继承于java.lang.Enum类
>
> ```java
> package com.enumtest;
> public class SeasonTest {
>     public static void main(String[] args) {
> 
>         Season[] values = Season.values();
>         for (int i = 0; i < values.length; i++) {
>             System.out.println(values[i]);
>         }
>         Season winter = Season.valueOf("WINTER");
>         System.out.println(winter);
>     }
> }
> 
> interface Info {
>     void show();
> }
> 
> // 自定义枚举类
> enum  Season implements Info{
> 
>     // 提供当前枚举类的对象，多个对象之间用“,”隔开，末尾对象“;”结束
>     // 这里每个对象也可以实现show方法
>     SPRING("春天","春暖花开"),
>     SUMMER("夏天","夏日炎炎"),
>     AUTUMN("秋天","秋高气爽"),
>     WINTER("冬天","冰天雪地");
> 
>     private final String name;
>     private final String desc;
> 
>     private Season(String name,String desc) {
>         this.name = name;
>         this.desc = desc;
>     }
> 
>     public String getName() {
>         return name;
>     }
> 
>     public String getDesc() {
>         return desc;
>     }
> 
>     @Override
>     public void show() {
>         System.out.println("This is a Season!");
>     }
> 
> //    @Override
> //    public String toString() {
> //        return "Season{" +
> //                "name='" + name + '\'' +
> //                ", desc='" + desc + '\'' +
> //                '}';
> //    }
> }
> ```
>
> **Enum类的主要方法**
>
> - values()方法：返回枚举类型的对象数组。该方法可以很方便地遍历所有的枚举值
>
> - valueOf(String str)：可以把一个字符串转为对应的枚举类对象。要求字符串必须是枚举类对象的“名字”。如不是，会有运行时异常：IllegalArgumentException
>
> - toString()：返回当前枚举类对象常量的名称
>
>   ```java
>   package com.enumtest;
>   
>   public class SeasonTest {
>   
>       public static void main(String[] args) {
>   
>           Season[] values = Season.values();
>           for (int i = 0; i < values.length; i++) {
>               System.out.println(values[i]);
>           }
>   
>           Season winter = Season.valueOf("WINTER");
>           System.out.println(winter);
>   
>       }
>   }
>   
>   // 自定义枚举类
>   enum  Season {
>   
>       // 提供当前枚举类的对象，多个对象之间用“,”隔开，末尾对象“;”结束
>       SPRING("春天","春暖花开"),
>       SUMMER("夏天","夏日炎炎"),
>       AUTUMN("秋天","秋高气爽"),
>       WINTER("冬天","冰天雪地");
>   
>       private final String name;
>       private final String desc;
>   
>       private Season(String name,String desc) {
>           this.name = name;
>           this.desc = desc;
>       }
>   
>       public String getName() {
>           return name;
>       }
>   
>       public String getDesc() {
>           return desc;
>       }
>   
>   //    @Override
>   //    public String toString() {
>   //        return "Season{" +
>   //                "name='" + name + '\'' +
>   //                ", desc='" + desc + '\'' +
>   //                '}';
>   //    }
>   }
>   ```

### 注解

> **JDK内置的三个基本注解**
>
> - @Override	限定重写父类方法，该注解只能用于方法
> - @Deprecated    用于表示所修饰的元素已过时
> - @SupressWarnings    抑制编译器警告
>
> **自定义注解**
>
> ```java
> // MyAnnotation.java
> package com.anno;
> public @interface MyAnnotation {
>     String value() default "hello";
> }
> 
> @MyAnnotation(value = "hi")
> class Person {
>     private String name;
>     private int age;
> 
>     public Person() {
>     }
> 
>     public Person (String name, int age){
>         this.name = name;
>         this.age = age;
>     }
> 
>     public void walk (){
>         System.out.println("人走路");
>     }
> 
>     public void eat() {
>         System.out.println("人吃饭");
>     }
> }
> ```
>
> **JDK元注解**
>
> 元注解：对现有的注解进行解释说明的注解
>
> @Retention：指定所修饰的Annotation的生命周期：SOURCE   CLASS(默认行为)    RUNTIME只有声明为RUNTIME生命周期的注解，才能通过反射获取。
>
> @Target：用于修饰Annotation定义，用于指定被修饰的Annotation能用于修饰哪些程序元素。@Target也包含一个名为value的成员变量
>
> @Document：用于指定被该元Annotation修饰的Annotation类将被javadoc工具提取成文档。默认情况下，javadoc是不包括注解的
>
> @Inherited：被它修饰的Annotation类将具有继承性
>
> @Repeatable：可重复注解（JDK8新增）

### 集合

##### 集合概述

> 1. 集合、数组都是对多个数据进行存储操作的结构，简称Java容器
>
> 2. 数组在存储多个数据方面的特点：一旦初始化以后，其长度就确定了。数组一旦定义好，其元素的类型也就确定了。我们只能操作指定的数据类型了。数组中提供的方法非常有限，对于添加、删除、插入数据等操作，非常不便。同时效率不高。获取数组中实际元素的个数的需求，数组没有现成的属性或方法可用。数组存储数据是有序，重复的。
>
>    
>
>    **Java集合可分为Collection和Map两种体系**
>
>    - Collection接口：单列数据，定义了存取一组对象的方法的集合
>      - List：元素有序，可重复的集合
>      - Set：元素无序，不可重复的集合
>
>    - Map接口：双列数据，保存具有映射关系“key-value对”的集合
>
>    **常用方法**
>
>    ```java
>    package com.collect;
>    import org.junit.Test;
>    import java.util.ArrayList;
>    import java.util.Arrays;
>    import java.util.Collection;
>    import java.util.Date;
>    
>    public class CollectionTest {
>    
>        @Test
>        public void test1() {
>            Collection coll = new ArrayList();
>            coll.add("AA");
>            coll.add("BB");
>            coll.add(123);
>            coll.add(456);
>            coll.add(new Date());
>            coll.add("AA");
>            coll.add(new String("Tom"));
>    
>            // size(): 获取添加的元素的个数
>            System.out.println(coll.size());
>    
>            Collection coll1 = new ArrayList();
>            coll1.add(456);
>            coll1.add("CC");
>    
>            // addAll() : 将coll1集合中的元素添加到当前集合中
>            coll.addAll(coll1);
>    
>            System.out.println(coll.size());
>            System.out.println(coll);
>    
>            // isEmpty(): 判断当前集合是否为空（集合中是否有元素）
>            System.out.println(coll.isEmpty());
>    
>            // contains(Object obj) : 判断当前集合中是否包含obj
>            // 如果需要判断集合中是否包含自定义的类的对象，需要重写类中的equals方法
>            boolean contains = coll.contains(123);
>            System.out.println(contains);   // true
>            System.out.println(coll.contains(new String("Tom")));  // true ,这里判断是否包含某个对象，调用的是equals方法
>    
>            // containsAll(Collection coll2) : 判断形参coll2是否都存在于当前集合中
>            Collection coll2 = new ArrayList();
>            coll2.add(123);
>            coll2.add(456);
>            System.out.println(coll.containsAll(coll2));
>    
>            // remove(Object obj) : 移除某个元素，有重复元素只会移除第一个,这里删除元素也会调用equals方法
>            coll.remove("AA");
>            System.out.println(coll);
>    
>            // removeAll(Collection coll2) : 从当前集合中移除col2中的所有元素   差集操作 A-B
>            coll.removeAll(coll2);
>            System.out.println(coll);
>    
>            // clear() : 清空集合元素
>            coll.clear();
>            System.out.println(coll);
>    
>            //retainAll() :  求交集，获取当前集合和coll4集合的交集，并返回给当前集合
>            Collection coll3 = new ArrayList();
>            coll3.add(12);
>            coll3.add(34);
>            coll3.add(56);
>            coll3.add(78);
>            coll3.add(90);
>            Collection coll4 = Arrays.asList(34,56,90,23,45);   // asList返回的集合，只能遍历，不能执行add、remove操作
>            coll3.retainAll(coll4);
>            System.out.println(coll3);
>    
>            // hashCode() ： 返回当前对象的hash值
>    
>    
>            // 集合 -----> 数组   toArray()
>            Collection coll5 = new ArrayList();
>            coll5.add(12);
>            coll5.add(34);
>            coll5.add(56);
>            coll5.add("AA");
>            coll5.add(new String("CC"));
>            Object[] arr = coll5.toArray();
>            for (int i = 0; i < arr.length; i++) {
>                System.out.println(arr[i]);
>            }
>        }
>    }
>    ```

##### Iterator（迭代器）

> ```java
> package com.collect;
> import org.junit.Test;
> import java.util.ArrayList;
> import java.util.Collection;
> import java.util.Date;
> import java.util.Iterator;
> 
> public class IteratorTest {
>     @Test
>     public void test1(){
>         Collection coll = new ArrayList();
>         coll.add("AA");
>         coll.add("BB");
>         coll.add(123);
>         coll.add(456);
>         coll.add(new Date());
>         coll.add("AA");
>         coll.add(new String("Tom"));
> 
>         Iterator iterator = coll.iterator();
>         while(iterator.hasNext()){
>             Object obj = iterator.next();
>             if("Tom".equals(obj)){
>                 iterator.remove();
>             }
>         }
> 
>         Iterator iterator1 = coll.iterator();
>         while(iterator1.hasNext()){
>             System.out.println(iterator1.next());
>         }
>         
>         // 另一种遍历方式
>         for(Object item : coll){
>             System.out.println(item);
>         }
> 
>         int[] arr = new int[]{1,2,3,4,5};
>         for(int ele : arr){
>             System.out.println(ele);
>         }
>     }
> }
> ```
>

##### List

> - 鉴于Java中数组用来存储数据的局限性，我们通常使用List替代数组
> - List集合类中元素有序，且可重复，集合中每个元素都有其对应的顺序索引
> - List容器中的元素都对应一个整数型的序号记载其在容器中的位置，可以根据序号存取容器中的元素
> - JDK API中List接口的实现类常用的有：ArrayList、LinkedList和Vector
>
> ArrayList、LinkedList、Vector三者的异同
>
> 相同：三个类都实现了List接口，存储数据的特点相同：存储有序的，可重复的数据
>
> 不同：
>
> - ArrayList：作为List接口的主要实现类，非线程安全，效率高，底层使用Object[] elementData存储
> - LinkedList：对于频繁的插入，删除操作，使用此类效率比ArrayList高，底层使用双向链表存储
> - Vector：作为List接口的古老实现类，线程安全，效率低，底层使用Object[] elementData存储
>
> **List接口常用方法**
>
> ```java
> package com.collect;
> import org.junit.Test;
> import java.util.ArrayList;
> import java.util.Arrays;
> import java.util.List;
> 
> public class IteratorTest {
>     @Test
>     public void test1(){
>         ArrayList list = new ArrayList();
>         list.add(123);
>         list.add(456);
>         list.add("AA");
>         list.add(new String("Tom"));
>         list.add(456);
> 
>         System.out.println(list);
>         // void add(int index,Object ele) ： 在index位置插入ele元素
>         list.add(1,"BB");
>         System.out.println(list);
> 
>         // boolean addAll(int index,Collection eles)：从index位置开始将eles中的所有元素添加进来
>         List<Integer> list1 = Arrays.asList(1, 2, 3);
>         list.addAll(list1);
>         System.out.println(list);
> 
>         // Object get(int index)：获取指定index位置的元素
>         System.out.println(list.get(1));
> 
>         // int indexOf(Object obj)：返回obj在集合中首次出现的位置
>         System.out.println(list.indexOf("AA"));
> 
>         // int lastIndexOf(Object obj)：返回obj在集合中最后一次出现的位置
>         System.out.println(list.lastIndexOf(456));
> 
>         // Object remove(int index)：移除指定index位置的元素，并返回此元素
>         System.out.println(list.remove(1));
> 
>         // Object set(int index,Object ele)：设置指定index位置的元素为ele，返回旧的元素
>         System.out.println(list.set(1, "CC"));
>         System.out.println(list);
> 
>         // List subList(int fromIndex,int toIndex)：返回从fromIndex到toIndex位置的子集合
>         System.out.println(list.subList(1, 3));
>        /*
>        *    遍历方法：
>        *    Iterator迭代器方式
>        *    增强for循环
>        *    普通for循环
>        */
>     }
> }
> ```

##### Set

> Set接口特点：存储无序的，不可重复的数据
>
> - HashSet：作为Set接口的主要实现类，是非线程安全的，可以存储null值
> - LinkedHashSet：作为HashSet的子类；遍历其内部数据时，可以按照添加的顺序遍历
> - TreeSet：可以按照添加对象的指定属性，进行排序
>
> ```java
> package com.collect;
> import org.junit.Test;
> import java.util.HashSet;
> import java.util.Iterator;
> import java.util.LinkedHashSet;
> import java.util.Set;
> 
> public class SetTest {
> 
>     @Test
>     public void test(){
> 
>         // Set接口没有额外定义新的方法，使用的都是Collection中声明过的方法
>         // Set是通过调用equals方法来判断是否重复，对于自定义类，需要重写equals方法和hashCode方法
>         Set set = new HashSet();
>         set.add(456);
>         set.add(123);
>         set.add(123);
>         set.add(123);
>         set.add("AA");
>         set.add(12);
> 
>         Iterator iterator = set.iterator();
>         while(iterator.hasNext()){
>             System.out.println(iterator.next());
>         }
> 
>         Set set1 = new LinkedHashSet();
>         set1.add(456);
>         set1.add(123);
>         set1.add(123);
>         set1.add("AA");
>         set1.add(12);
>         System.out.println(set1);   // [456, 123, AA, 12]
>     }
> }
> ```
>
> **TreeSet**
>
> ```java
> package com.collect;
> import org.junit.Test;
> import java.util.TreeSet;
> public class TreeSetTest {
>     @Test
>     public void test(){
>         TreeSet set = new TreeSet();
>         // 不能添加不同类的对象
>         set.add(new User("Tom",12));
>         set.add(new User("Jerry",32));
>         set.add(new User("Jim",2));
>         set.add(new User("Mike",65));
>         set.add(new User("Jack",33));
>         set.add(new User("Jack",56));
>         System.out.println(set);
>     }
> }
> 
> // User.java
> package com.collect;
> public class User implements Comparable{
>     private String name;
>     private int age;
>     public User() {
>     }
> 
>     public User(String name, int age) {
>         this.name = name;
>         this.age = age;
>     }
> 
>     @Override
>     public String toString() {
>         return "User{" +
>                 "name='" + name + '\'' +
>                 ", age='" + age + '\'' +
>                 '}';
>     }
> 
>     // 按照姓名从小到大排列
>     @Override
>     public int compareTo(Object o) {
>         if(o instanceof User){
>             User user = (User) o;
>             int compare = -this.name.compareTo(user.name);
>             if(compare != 0){
>                 return compare;
>             }else{
>                 return Integer.compare(this.age,user.age);
>             }
>         }
>         throw new RuntimeException("类型错误！");
>     }
> }
> ```

##### Map

> Map：双列数据，存储key-value对的数据
>
> - HashMap：作为Map的主要实现类；非线程安全，效率高；存储null的key和value
> - LinkedHashMap：保证在遍历map元素时，可以按照添加的顺序实现遍历
> - TreeMap：保证按照添加的key-value对进行排序，实现排序遍历。此时考虑key的自然排序或定制排序
> - Hashtable：作为古老的实现类；线程安全，效率低；不能存储null的key和value
>
> **常用方法**
>
> ```java
> package com.collect;
> import org.junit.Test;
> import java.util.*;
> public class MapTest {
>     @Test
>     public void test(){
> 
>         Map map = new HashMap();
>         map.put(123,"AA");
>         map.put(345,"BB");
>         map.put(12,"CC");
> 
>         Map map1 = new HashMap();
>         map1.put(12,"CC");
>         map1.put(56,"DD");
>         map1.put(78,"EE");
> 
>         // 将集合map1的元素添加到当前集合
>         map.putAll(map1);
> 
>         System.out.println(map.remove(12));
> 
>         // Object get(Object key)：获取指定key对应的value
>         System.out.println(map.get(123));
> 
>         // boolean containsKey(Object key)：是否包含指定的key
>         System.out.println(map.containsKey(123));
> 
>         // boolean containsValue(Object value)：是否包含指定的value
>         System.out.println(map.containsValue("CC"));
> 
>         // boolean isEmpty()：判断当前map是否为空
>         System.out.println(map.isEmpty());
> 
>         // int size()：返回map中key-value对的个数
>         System.out.println(map.size());
> 
>         // 清空当前集合
>         map.clear();
>         System.out.println(map);
> 
>         // boolean equals(Object obj)：判断当前map和参数对象obj是否相等
>         Map map2 = new HashMap();
>         map2.put("AA",12);
>         map2.put("BB",13);
> 
>         Map map3 = new HashMap();
>         map3.put("AA",12);
>         map3.put("BB",13);
> 
>         System.out.println(map2.equals(map3));
> 
>         /**
>          *  Set keySet()：返回所有key构成的Set集合
>          *  Collection values()：返回所有value构成的Collection集合
>          *  Set entrySet()：返回所有key-value对构成的Set集合
>          */
>         Map map4 = new HashMap();
>         map4.put("AA",123);
>         map4.put(45,123);
>         map4.put("BB",56);
> 
>         // 遍历所有的key集：keySet()
>         Set set = map4.keySet();
>         Iterator iterator = set.iterator();
>         while(iterator.hasNext()){
>             System.out.println(iterator.next());
>         }
> 
>         // 遍历所有的value集：values()
>         Collection values = map4.values();
>         for(Object obj : values){
>             System.out.println(obj);
>         }
> 
>         // 遍历所有的key-value
>         System.out.println("================");
>         Set entrySet = map4.entrySet();
>         Iterator iterator1 = entrySet.iterator();
>         while(iterator1.hasNext()){
>             Object obj = iterator1.next();
>             // entrySet集合中的元素都是entry
>             Map.Entry entry = (Map.Entry) obj;
>             System.out.println(entry.getKey());
>             System.out.println(entry.getValue());
>         }
>     }
> }
> ```
>
> **TreeMap**
>
> ```java
> package com.collect;
> 
> import org.junit.Test;
> 
> import java.util.*;
> 
> public class MapTest {
> 
>     // 自然排序
>     @Test
>     public void test(){
> 
>         TreeMap map = new TreeMap();
>         User u1 = new User("Tom",23);
>         User u2 = new User("Jerry",32);
>         User u3 = new User("Jack",20);
>         User u4 = new User("Rose",18);
> 
>         map.put(u1,98);
>         map.put(u2,89);
>         map.put(u3,76);
>         map.put(u4,100);
> 
>         Set set = map.entrySet();
>         Iterator iterator = set.iterator();
>         while(iterator.hasNext()){
>             Object obj = iterator.next();
>             Map.Entry entrySet = (Map.Entry) obj;
>             System.out.println(entrySet.getKey());
>             System.out.println(entrySet.getValue());
>         }
>     }
> 
>     // 定制排序
>     @Test
>     public void test1(){
> 
>         TreeMap map = new TreeMap(new Comparator() {
>             @Override
>             public int compare(Object o1, Object o2) {
>                 if(o1 instanceof User && o2 instanceof User){
>                     User u1 = (User) o1;
>                     User u2 = (User) o2;
>                     return Integer.compare(u1.getAge(),u2.getAge());
>                 }
>                 throw new RuntimeException("类型不匹配");
>             }
>         });
>         User u1 = new User("Tom",23);
>         User u2 = new User("Jerry",32);
>         User u3 = new User("Jack",20);
>         User u4 = new User("Rose",18);
> 
>         map.put(u1,98);
>         map.put(u2,89);
>         map.put(u3,76);
>         map.put(u4,100);
> 
>         Set set = map.entrySet();
>         Iterator iterator = set.iterator();
>         while(iterator.hasNext()){
>             Object obj = iterator.next();
>             Map.Entry entrySet = (Map.Entry) obj;
>             System.out.println(entrySet.getKey());
>             System.out.println(entrySet.getValue());
>         }
>     }
> }
> 
> 
> // User.java
> package com.collect;
> 
> import java.util.Objects;
> 
> public class User implements Comparable{
> 
>     private String name;
>     private int age;
> 
>     public String getName() {
>         return name;
>     }
> 
>     public int getAge() {
>         return age;
>     }
> 
>     public User() {
>     }
> 
>     public User(String name, int age) {
>         this.name = name;
>         this.age = age;
>     }
> 
>     @Override
>     public String toString() {
>         return "User{" +
>                 "name='" + name + '\'' +
>                 ", age='" + age + '\'' +
>                 '}';
>     }
> 
>     // 按照姓名从小到大排列
>     @Override
>     public int compareTo(Object o) {
>         if(o instanceof User){
>             User user = (User) o;
>             int compare = -this.name.compareTo(user.name);
>             if(compare != 0){
>                 return compare;
>             }else{
>                 return Integer.compare(this.age,user.age);
>             }
>         }
>         throw new RuntimeException("类型错误！");
>     }
> 
>     @Override
>     public boolean equals(Object o) {
>         if (this == o) return true;
>         if (o == null || getClass() != o.getClass()) return false;
>         User user = (User) o;
>         return age == user.age &&
>                 Objects.equals(name, user.name);
>     }
> 
>     @Override
>     public int hashCode() {
> 
>         return Objects.hash(name, age);
>     }
> }
> ```

##### Properties

> ```java
> package com.collect;
> import java.io.FileInputStream;
> import java.util.Properties;
> 
> public class PropertiesTest {
> 
>     public static void main(String[] args) throws Exception {
> 
>         Properties pros = new Properties();
>         FileInputStream fis = new FileInputStream("jdbc.properties");
>         pros.load(fis);  // 加载流对应的文件
>         String name = pros.getProperty("name");
>         String password = pros.getProperty("password");
>         System.out.println("name = " + name + " password = " + password);
>     }
> }
> 
> //jdbc.properties
> name=Tom
> password=abc123
> ```

##### Collections工具类

> **常用方法**
>
> ```java
> package com.collect;
> 
> import org.junit.Test;
> import java.util.*;
> 
> public class CollectionsTest {
> 
>     @Test
>     public void test() {
>         List list = new ArrayList();
>         list.add(123);
>         list.add(43);
>         list.add(765);
>         list.add(-97);
>         list.add(0);
>         list.add(123);
>         System.out.println(list);
>         // reverse(List)：反转List中元素的顺序
>         Collections.reverse(list);
>         System.out.println(list);
> 
>         // shuffle(List)：对List集合元素进行随机排序
>         Collections.shuffle(list);
>         System.out.println(list);
> 
>         // sort(List)：根据元素的自然顺序对指定List集合元素按升序排序
>         Collections.sort(list);
>         System.out.println(list);
> 
>         // sort(List,Comparator)：根据指定的Comparator产生的顺序对List集合元素进行排序
> 
>         // swap(List,int,int)：将指定list集合中的i处元素和j处元素进行交换
>         Collections.swap(list,1,2);
>         System.out.println(list);
> 
>         // Object max(Collection)：根据元素的自然顺序，返回给定集合中的最大元素
>         System.out.println(Collections.max(list));
> 
>         // Object max(Collection,Comparator)：根据Comparator指定的顺序，返回给定集合中的最大元素
> 
>         // Object min(Collection) ：根据元素的自然顺序，返回给定集合中的最小元素
>         System.out.println(Collections.min(list));
>         // Object min(Collection,Comparator)：根据Comparator指定的顺序，返回给定集合中的最小元素
> 
>         // int frequency(Collection,Object)：返回指定集合中指定元素的出现次数
>         System.out.println(Collections.frequency(list, 123));
>         // void copy(List dest,List src)：将src中的内容复制到dest中
> //        List dest = new ArrayList();    // 写法错误，会有异常。java.lang.IndexOutOfBoundsException: Source does not fit in dest
> //        List dest = Arrays.asList(new Object[list.size()]);     // 正确写法，但是dest不能进行写操作
>         List dest = new ArrayList(Arrays.asList(new Object[list.size()]));   // 写法正确，dest也可以进行写操作
>         Collections.copy(dest,list);
>         System.out.println(dest);
> 
>         // boolean replaceAll(List list,Object oldVal,Object newVal)：使用新值替换指定的List中的所有值
> 
>         /**
>          *  Collections类中提供了多个synchronizedXxx()方法，
>          *  该方法可使将指定集合包装成线程同步的集合，从而可以解决多线程并发访问集合时的线程安全问题
>          */
>         List list1 = Collections.synchronizedList(list);    // 这里返回的list1，就是线程安全的List
>     }
> }
> ```

### 泛型

> ```java
> package com.gene;
> import org.junit.Test;
> import java.util.ArrayList;
> import java.util.Iterator;
> 
> public class GenericTest {
> 
> @Test
> public void test() {
> 
>   // 泛型是一个类型，不能是基础数据类型，要用基础数据类型的包装类
>   ArrayList<Integer> list = new ArrayList<Integer>();
>   list.add(12);
>   list.add(34);
>   list.add(56);
> 
>   Iterator<Integer> iterator = list.iterator();
>   while(iterator.hasNext()){
>       System.out.println(iterator.next().getClass());
>   }
> }
> 
> @Test
> public void test1() {
>   // 如果定义了泛型类，实例化没有指明类的泛型，则认为此泛型类型为Object类型
>   // 静态方法不能使用泛型
>   Order<String> o = new Order<String>("Tom",1,"order info");
>   System.out.println(o);
> }
> 
> }
> 
> // Order.java
> package com.gene;
> public class Order<T> {
> String orderName;
> int orderId;
> // 类的内部结构就可以使用泛型
> T orderT;
> 
> public Order() {}
> public Order(String orderName,int orderId,T orderT) {
>   this.orderName = orderName;
>   this.orderId = orderId;
>   this.orderT = orderT;
> }
> public T getOrderT() {
>   return orderT;
> }
> public void setOrderT(T orderT) {
>   this.orderT = orderT;
> }
> @Override
> public String toString() {
>   return "Order{" +
>           "orderName='" + orderName + '\'' +
>           ", orderId=" + orderId +
>           ", orderT=" + orderT +
>           '}';
> }
> }
> ```
>
> **泛型方法**
>
> ```java
> package generic;
> 
> import java.util.ArrayList;
> import java.util.List;
> 
> public class GenericTest {
>  public static void main(String[] args) {
>      Order o = new Order();
>      Integer[] arr = new Integer[]{1,2,3,4,5};
>      System.out.println(o.copyFromArrayToList(arr));
>    	List<Object> list1 = null;
>    	List<String> list2 = null;
>    	list1 = list2;	// 这里会报错，list1和list2的类型不是子父类的关系，不能赋值
>  }
> }
> 
> // 泛型方法：在方法中出现了泛型结构，泛型参数与类的泛型参数没有任何关系
> // 换句话说，泛型方法所属的类是不是泛型类都没有关系
> // 泛型方法，可以声明为静态的，原因：泛型参数是在调用方法的时候确定的，并非在实例化类的时候确定的
> // 泛型在继承方面的体现：
> // 类A是类B的父类，G<A>和G<B>之间不具备子父类关系，二者是并列关系
> class Order {
> 
>  public void show(){
> 
>  }
> 
>  public <E> List<E> copyFromArrayToList(E[] arr){
>      ArrayList<E> list = new ArrayList<>();
>      for(E e : arr){
>          list.add(e);
>      }
>      return list;
>  }
> }
> ```
>
> **通配符**
>
> ```java
> package generic;
> 
> import org.junit.Test;
> 
> import java.util.ArrayList;
> import java.util.Iterator;
> import java.util.List;
> 
> public class GenericTest2 {
>     @Test
>     public void test3(){
>         List<Object> list1 = null;
>         List<String> list2 = new ArrayList<String>();
>         List<?> list = null;
>         list2.add("AA");
>         list2.add("BB");
>         list = list2;
>         // 对于List<?>不能向其内部添加数据
> //        list.add("CC");  // 这句会报错
>         // 允许读取数据，读取的数据类型为Object
>         Object o = list.get(0);
>         System.out.println(o);
> 
>     }
> 
>     public void print(List<?> list){
>         Iterator<?> iterator = list.iterator();
>         while(iterator.hasNext()){
>             Object obj = iterator.next();
>             System.out.println(obj);
>         }
>     }
> }
> 
> ```
>
> **有限制条件的通配符**
>
> 1. ? extends A: 	G<? extends A>可以作为G<A>和G<B>的父类，其中B是A的子类
> 2. ? super A:     G<? super A>可以作为G<A>和G<B>的父类，其中B是A的父类

### IO流

##### File类

> **File类的构造器和常用方法**
>
> ```java
> package IOTest;
> import org.junit.Test;
> import java.io.File;
> import java.io.IOException;
> 
> /**
>  *  File类的使用
>  *  1. File类的一个对象代表一个文件或一个文件目录
>  *  2. File类声明在java.io包下
>  *  如何创建File实例
>  *  File(String filePath)
>  *  File(String parentPath,String childPath)
>  *  File(File parentFile,String childPath)
>  */
> public class FileTest {
> 
>     @Test
>     public void test1(){
>         File file1 = new File("hello.txt");
>         System.out.println(File.separator);
>         System.out.println(file1);
>     }
> 
>     @Test
>     public void test2(){
>         // public File getAbsoluteFile()  获取绝对路径
>         File file = new File("hello.txt");
>         File path = file.getAbsoluteFile();
>         System.out.println(path);
>         // public String getPath() 获取路径
>         String path2 = file.getPath();
>         System.out.println(path2);
>         // public String getName() 获取名称
>         String name = file.getName();
>         System.out.println(name);
>         // public String getParent() 获取上层文件目录路径，若无则返回null
>         String parent = file.getParent();
>         System.out.println(parent);
>         // public long length() 获取文件长度（即字节数），不能获取目录的长度
>         long length = file.length();
>         System.out.println(length);
>         //public long lastModified(): 获取最后一次的修改时间，毫秒值
>         long modifiedTime = file.lastModified();
>         System.out.println(modifiedTime);
>         // public String[] list() 获取指定目录下的所有文件或者文件目录的名称数组(适用于文件目录)
>         File file1 = new File("/Users/charles/Development/java");
>         String[] list = file1.list();
>         for(String f : list){
>             System.out.println(f);
>         }
>         // public File[] listFiles() 获取指定目录下的所有文件或者文件目录的File数组（适用于文件目录）
>         File[] files = file1.listFiles();
>         for(File f : files){
>             System.out.println(f);
>         }
>     }
> 
>     @Test
>     public void test3(){
>         File file = new File("hello.txt");
>         File file1 = new File("hi.txt");
>         // rename 重命名，要想保证true，要求file存在，且file1不能存在
>         boolean b = file.renameTo(file1);
>         System.out.println(b);
>         // isDirectory 判断是否是目录
>         boolean directory = file.isDirectory();
>         System.out.println(directory);
>         // isFile 判断是否是文件
>         boolean isF = file1.isFile();
>         System.out.println(isF);
>         // exists 判断文件是否存在
>         boolean isE = file1.exists();
>         System.out.println(isE);
>         // canRead 判断是否可读
>         boolean b1 = file1.canRead();
>         System.out.println(b1);
>         // canWrite 判断是否可写
>         boolean b2 = file1.canWrite();
>         // isHidden 判断是否隐藏
>         boolean hidden = file1.isHidden();
>         System.out.println(hidden);
>     }
> 
>     @Test
>     public void test4(){
>         // public boolean createNewFile() 创建文件，若文件存在则不创建，返回false
>         File file = new File("hello.txt");
>         if(!file.exists()){
>             try {
>                 file.createNewFile();
>             } catch (IOException e) {
>                 e.printStackTrace();
>             }
>         }else{
>             // public boolean delete() 删除文件或者文件夹
>             file.delete();
>         }
> 
>         // public boolean mkdir() 创建文件目录，若文件目录存在则不创建，如果上层文件目录不存在也不创建
>         File file1 = new File("/Users/charles/Development/java/javase/io1");
>         file1.mkdir();
>         // public boolean mkdirs() 创建文件目录，如果上层文件目录不存在则一并创建
>         File file2 = new File("/Users/charles/Development/java/javase/io2/test3");
>         file2.mkdirs();
>     }
> }
> ```

##### 流的分类

> - 按操作*数据单位*不同分为：==字节流==（8bit）、==字符流==（16bit）
> - 按数据流的*流向*分为：==输入流==、==输出流==
> - 按流的*角色*不同分为：==节点流==、==处理流==
>
> Java的IO流涉及40多个类，但都是由以下四个抽象基类派生的：
>
> | 抽象基类 |    字节流    | 字符流 |
> | :------: | :----------: | :----: |
> |  输入流  | InputStream  | Reader |
> |  输出流  | OutputStream | Writer |
>
> 流的体系结构
>
> | 抽象基类     | 节点流（或文件流） | 缓冲流（处理流的一种） |
> | ------------ | ------------------ | ---------------------- |
> | InputStream  | FileInputStream    | BufferedInputStream    |
> | OutputStream | FileOutputStream   | BufferedOutputStream   |
> | Reader       | FileReader         | BufferedReader         |
> | Writer       | FileWriter         | BufferedWriter         |
>

##### FileReader

> ```java
> package IOTest;
> import org.junit.Test;
> import java.io.File;
> import java.io.FileReader;
> import java.io.IOException;
> 
> public class FileReaderWriterTest {
> 
>     @Test
>     public void testFileReader(){
>         // 实例化File类对象，指明要操作的文件
>         File file = new File("hello.txt");
>         FileReader fr = null;
>         try {
>             // 提供具体的流
>            fr = new FileReader(file);
>             // 数据的读入
>             // read() 返回读入的一个字符，如果到达文件末尾，返回-1
> //            int data = fr.read();
> //            while(data != -1){
> //                System.out.print((char) data);
> //                data = fr.read();
> //            }
>             int data;
>             while((data = fr.read()) != -1){
>                 System.out.print((char) data);
>             }
>         } catch (IOException e) {
>             e.printStackTrace();
>         }finally {
>             // 流的关闭操作
>             try {
>                 if(fr != null)
>                     fr.close();
>             } catch (IOException e) {
>                 e.printStackTrace();
>             }
>         }
>     }
> 
>     @Test
>     public void testFileReader1(){
>         // File类的实例化
>         File file = new File("hello.txt");
>         // FileReader流的实例化
>         FileReader fr = null;
>         try {
>             fr = new FileReader(file);
>             // 读入的操作
>             char[] cbuf = new char[5];
>             int len;
>             // read(char[] cbuf) 返回每次读入数组中的字符个数
>             while((len = fr.read(cbuf)) != -1){
>                 // 错误的写法
> //                for (int i = 0; i < cbuf.length; i++) {
> //                    System.out.print(cbuf[i]);
> //                }
>                 // 正确的写法
> //                for (int i = 0; i < len; i++) {
> //                    System.out.print(cbuf[i]);
> //                }
>                 // 方式二
>                 String str = new String(cbuf,0,len);
>                 System.out.print(str);
>             }
>         } catch (IOException e) {
>             e.printStackTrace();
>         }finally {
>             // 资源的关闭
>             if(fr != null){
>                 try {
>                     fr.close();
>                 } catch (IOException e) {
>                     e.printStackTrace();
>                 }
>             }
>         }
>     }
> }
> ```
>
> 

##### FileWriter

> ```java
> package IOTest;
> import org.junit.Test;
> import java.io.File;
> import java.io.FileReader;
> import java.io.FileWriter;
> import java.io.IOException;
> 
> public class FileWriterTest {
> 
>     @Test
>     public void testFileWriter() throws IOException {
>         // 提供File类的对象，指明写出到的文件
>         File file = new File("hello1.txt");
>         // 提供FileWriter的对象，用于数据的写出
>         FileWriter fw = new FileWriter(file,true);
>         // 写出的操作
>         fw.write("I have a dream!\n");
>         fw.write("you need a dream!");
>         // 流的关闭
>         fw.close();
>     }
> 
>     @Test
>     public void testFileReaderWriter(){
>         FileReader fr = null;
>         FileWriter fw = null;
>         try {
>             File srcFile = new File("hello.txt");
>             File destFile = new File("hello1.txt");
>             fr = new FileReader(srcFile);
>             fw = new FileWriter(destFile);
>             char[] cbuf = new char[10];
>             int len;
>             while((len = fr.read(cbuf)) != -1){
>                 fw.write(cbuf,0,len);
>             }
>         } catch (IOException e) {
>             e.printStackTrace();
>         } finally {
>             try {
>                 fr.close();
>             } catch (IOException e) {
>                 e.printStackTrace();
>             }
>             try {
>                 fw.close();
>             } catch (IOException e) {
>                 e.printStackTrace();
>             }
>         }
>     }
> }
> ```

##### FileInputStream和FileOutputStream

> ```java
> package IOTest;
> import org.junit.Test;
> import java.io.File;
> import java.io.FileInputStream;
> import java.io.FileOutputStream;
> import java.io.IOException;
> 
> public class FileInputStreamTest {
> 
>     /**
>      * 对于文本文件使用字符流处理
>      * 对于非文本文件使用字节流处理
>      */
>     @Test
>     public void testFileInputOutputStream(){
>         FileInputStream fis = null;
>         FileOutputStream fos = null;
>         try {
>             File srcFile = new File("1.png");
>             File destFile = new File("2.png");
>             fis = new FileInputStream(srcFile);
>             fos = new FileOutputStream(destFile);
>             byte[] buffer = new byte[5];
>             int len;
>             while((len = fis.read(buffer)) != -1){
>                 fos.write(buffer,0,len);
>             }
>         } catch (IOException e) {
>             e.printStackTrace();
>         } finally {
>             if(fis != null){
>                 try {
>                     fis.close();
>                 } catch (IOException e) {
>                     e.printStackTrace();
>                 }
>             }
>             if(fos != null){
>                 try {
>                     fos.close();
>                 } catch (IOException e) {
>                     e.printStackTrace();
>                 }
>             }
>         }
>     }
> }
> ```

##### 缓冲流

> BufferedInputStream和BufferedOutputStream
>
> ```java
> package IOTest;
> import org.junit.Test;
> import java.io.*;
> 
> public class BufferedTest {
> 
>  // 缓冲流是为了提高文件的读取和写入速度的
>  @Test
>  public void testBufferedStream(){
>      long start = System.currentTimeMillis();
>      BufferedInputStream bis = null;
>      BufferedOutputStream bos = null;
>      try {
>          // 文件对象
>          File srcFile = new File("1.avi");
>          File destFile = new File("2.avi");
>          // 字节流
>          FileInputStream fis = new FileInputStream(srcFile);
>          FileOutputStream fos = new FileOutputStream(destFile);
>          // 缓冲流
>          bis = new BufferedInputStream(fis);
>          bos = new BufferedOutputStream(fos);
>          // 复制细节：读取，写入
>          byte[] buffer = new byte[1024];
>          int len;
>          while((len = bis.read(buffer)) != -1){
>              bos.write(buffer,0,len);
>          }
>      } catch (IOException e) {
>          e.printStackTrace();
>      } finally {
>          // 资源关闭
>          // 要求：先关闭外层的流，再关闭外层的流
>          if(bis != null){
>              try {
>                  bis.close();
>              } catch (IOException e) {
>                  e.printStackTrace();
>              }
>          }
>          if(bos != null){
>              try {
>                  bos.close();
>              } catch (IOException e) {
>                  e.printStackTrace();
>              }
>          }
>          // 关闭外层流的同时会自动关闭内层流，所以关于内层流的关闭可以省略
> //        fis.close();
> //        fos.close();
>      }
>      long end = System.currentTimeMillis();
>      System.out.println("cost time: " + (end-start));
>  }
> }
> ```
>
> BufferedReader和BufferedWriter
>
> ```java
> @Test
> public void testBufferedReaderWriter(){
>   BufferedReader br = null;
>   BufferedWriter bw = null;
>   try {
>     // 创建文件和相应的流
>     br = new BufferedReader(new FileReader(new File("hello.txt")));
>     bw = new BufferedWriter(new FileWriter(new File("hello1.txt")));
>     // 读写操作
>     // 方式一
>     //            char[] cbuf = new char[1024];
>     //            int len;
>     //            while((len = br.read(cbuf)) != -1){
>     //                bw.write(cbuf,0,len);
>     //            }
>     // 方式二
>     String data;
>     while((data = br.readLine()) != null){
>       bw.write(data); // data不包含换行符
>       bw.newLine();   // 加上换行符
>     }
>   } catch (IOException e) {
>     e.printStackTrace();
>   } finally {
>     // 关闭资源
>     if(br != null){
>       try {
>         br.close();
>       } catch (IOException e) {
>         e.printStackTrace();
>       }
>     }
>     if(bw != null){
>       try {
>         bw.close();
>       } catch (IOException e) {
>         e.printStackTrace();
>       }
>     }
>   }
> }
> ```

##### 转换流

> ```java
> package IOTest;
> import org.junit.Test;
> import java.io.*;
> 
> public class InputStreamReaderTest {
>     @Test
>     public void test(){
>         // InputStreamReader ： 实现字节输入流到字符输入流的转换
>         InputStreamReader isr = null;
>         try {
>             FileInputStream fis = new FileInputStream("hello.txt");
>             isr = new InputStreamReader(fis,"UTF-8");
>             char[] cbuf = new char[10];
>             int len;
>             while((len = isr.read(cbuf)) != -1){
>                 String str = new String(cbuf,0,len);
>                 System.out.print(str);
>             }
>         } catch (IOException e) {
>             e.printStackTrace();
>         } finally {
>             if(isr != null){
>                 try {
>                     isr.close();
>                 } catch (IOException e) {
>                     e.printStackTrace();
>                 }
>             }
>         }
>     }
> 
>     @Test
>     public void test1(){
>         InputStreamReader isr = null;
>         OutputStreamWriter osw = null;
>         try {
>             File srcFile = new File("hello.txt");
>             File destFile = new File("hello_gbk.txt");
>             FileInputStream fis = new FileInputStream(srcFile);
>             FileOutputStream fos = new FileOutputStream(destFile);
>             isr = new InputStreamReader(fis,"UTF-8");
>             osw = new OutputStreamWriter(fos,"GBK");
>             char[] cbuf = new char[10];
>             int len;
>             while((len = isr.read(cbuf)) != -1){
>                 osw.write(cbuf,0,len);
>             }
>         } catch (IOException e) {
>             e.printStackTrace();
>         } finally {
>             if(isr != null){
>                 try {
>                     isr.close();
>                 } catch (IOException e) {
>                     e.printStackTrace();
>                 }
>             }
>             if(osw != null){
>                 try {
>                     osw.close();
>                 } catch (IOException e) {
>                     e.printStackTrace();
>                 }
>             }
>         }
>     }
> }
> ```

##### 标准输入输出流

> **标准输入、输出流**
>
> - System.in : 标准的输入流，默认从键盘输入
> - System.out : 标准的输出流，默认从控制台输出
> - System类的setIn/setOut方法重新指定输入和输出的流
>
> ```java
> package IOTest;
> import org.junit.Test;
> import java.io.BufferedReader;
> import java.io.IOException;
> import java.io.InputStreamReader;
> 
> public class StdStreamTest {
> 
>     @Test
>     public void test(){
>         BufferedReader br = null;
>         try {
>             InputStreamReader isr = new InputStreamReader(System.in);
>             br = new BufferedReader(isr);
>             while(true){
>                 System.out.println("请输入字符串：");
>                 String data = br.readLine();
>                 if("e".equalsIgnoreCase(data) || "exit".equalsIgnoreCase(data)){
>                     System.out.println("程序结束");
>                     break;
>                 }
>                 String upperCase = data.toUpperCase();
>                 System.out.println(upperCase);
>             }
>         } catch (IOException e) {
>             e.printStackTrace();
>         } finally {
>             if(br != null){
>                 try {
>                     br.close();
>                 } catch (IOException e) {
>                     e.printStackTrace();
>                 }
>             }
>         }
>     }
> }
> ```

##### 打印流

> - 实现将==基本数据==类型的数据格式转换为==字符串==输出
>
> - 打印流：PrintStream和PrintWriter
>
>   * 提供了一系列重载的print()和println()方法，用于多种数据类型的输出
>   * PrintStream和PrintWriter的输出，不会抛出IOException异常
>   * PrintStream和PrintWriter有自动flush功能
>   * PrintStream打印的所有字符都使用平台默认的字符编码转换为字节，在需要写入字符而不是写入字节的情况下，应该使用PrintWriter类
>   * System.out 返回的是PrintStream的实例
>
>   ```java
>   public void test1(){
>     PrintStream ps = null;
>     try {
>       FileOutputStream fos = new FileOutputStream(new File("hello.txt"));
>       // 创建打印流，设置为自动刷新模式（写入换行符或字符"\n",时，都会刷新输出缓冲区）
>       ps = new PrintStream(fos,true);
>       if(ps != null){
>         System.setOut(ps);  // 把标准输出流（控制台输出）改成文件
>       }
>       for (int i = 0; i < 255; i++) {
>         System.out.print((char) i);
>         if(i % 50 == 0){    // 每50个数据换一行
>           System.out.println();
>         }
>       }
>     } catch (FileNotFoundException e) {
>       e.printStackTrace();
>     } finally {
>       if(ps != null){
>         ps.close();
>       }
>     }
>   }
>   ```

##### 数据流

> - 为了方便操作Java语言的基本数据类型和String的数据，可以使用数据流
> - 数据流有两个类（用于读取和写出基本数据类型、String）
>   * DataInputStream和DataOutputStream
>   * 分别“套接”在InputStream和OutputStream子类的流上
> - DataInputStream中的方法
>   * boolean readBoolean()		byte readByte()
>   * char readChar()                    float readFloat()
>   * double readDouble()           short readShort()
>   * long readLong()                    int readInt()
>   * String readUTF()                    void readFully(byte[] b)
> - DataOutputStream中的方法
>   * 把上述方法read改成相应write即可
>
> ```java
> // 将内存中的基本数据类型、字符串写出到文件中
> @Test
> public void test3() throws IOException {
>   DataOutputStream dos = new DataOutputStream(new FileOutputStream("data.txt"));
>   dos.writeUTF("张三");
>   dos.flush();
>   dos.writeInt(23);
>   dos.flush();
>   dos.writeBoolean(true);
>   dos.flush();
>   dos.close();
> }
> // 读取数据的顺序要与写入的顺序一致
> @Test
> public void test4() throws IOException {
>   DataInputStream dis = new DataInputStream(new FileInputStream("data.txt"));
>   String name = dis.readUTF();
>   int age = dis.readInt();
>   boolean b = dis.readBoolean();
>   System.out.println("name = " + name);
>   System.out.println("age = " + age);
>   System.out.println("b = " + b);
> }
> ```
>
> 

