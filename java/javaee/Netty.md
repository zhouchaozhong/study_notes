# Netty学习笔记

### Netty概述

> **Netty基本介绍**
>
> * Netty 是由 JBOSS 提供的一个 Java 开源框架，现为 Github上的独立项目。
> * Netty 是一个异步的、基于事件驱动的网络应用框架，用以快速开发高性能、高可靠性的网络 IO 程序。
> *  Netty主要针对在TCP协议下，面向Clients端的高并发应用，或者Peer-to-Peer场景下的大量数据持续传输的应用。
> * Netty本质是一个NIO框架，适用于服务器通讯相关的多种应用场景
> * Netty 可以帮助你快速、简单的开发出一个网络应用，相当于简化和流程化了 NIO 的开发过程
> * Netty 是目前最流行的 NIO 框架，Netty 在互联网领域、大数据分布式计算领域、游戏行业、通信行业等获得了广泛的应用，知名的 Elasticsearch 、Dubbo 框架内部都采用了 Netty。
> *  要透彻理解Netty ， 需要先学习 NIO ， 这样我们才能阅读 Netty 的源码。
>
> **Netty的应用场景**
>
> * 互联网行业
>   * 互联网行业：在分布式系统中，各个节点之间需要远程服务调用，高性能的 RPC 框架必不可少，Netty 作为异步高性能的通信框架，往往作为基础通信组件被这些 RPC 框架使用。
>   * 典型的应用有：阿里分布式服务框架Dubbo 的 RPC 框架使用 Dubbo 协议进行节点间通信，Dubbo 协议默认使用Netty 作为基础通信组件，用于实现各进程节点之间的内部通信
> * 游戏行业
>   *  无论是手游服务端还是大型的网络游戏，Java 语言得到了越来越广泛的应用
>   * Netty 作为高性能的基础通信组件，提供了 TCP/UDP 和 HTTP 协议栈，方便定制和开发私有协议栈，账号登录服务器
>   *  地图服务器之间可以方便的通过 Netty进行高性能的通信
> * 大数据领域
>   *  经典的 Hadoop 的高性能通信和序列化组件 Avro 的 RPC 框架，默认采用 Netty 进行跨界点通信
>   * 它的 Netty Service 基于 Netty 框架二次封装实现。
> * 其它开源项目使用到Netty
>
> **Netty的优点**
>
> Netty 对 JDK 自带的 NIO 的 API 进行了封装，解决了以下问题。
>
> * 设计优雅：适用于各种传输类型的统一 API 阻塞和非阻塞 Socket；基于灵活且可扩展的事件模型，可以清晰地分离关注点；高度可定制的线程模型 - 单线程，一个或多个线程池.
> * 使用方便：详细记录的 Javadoc，用户指南和示例；没有其他依赖项，JDK 5（Netty3.x）或 6（Netty 4.x）就足够了。
> * 高性能、吞吐量更高：延迟更低；减少资源消耗；最小化不必要的内存复制。
> * 安全：完整的 SSL/TLS 和 StartTLS 支持。
> * 社区活跃、不断更新：社区活跃，版本迭代周期短，发现的 Bug 可以被及时修复，同时，更多的新功能会被加入

### I/O模型

> **I/O模型基本说明**
>
> *  I/O 模型简单的理解：就是用什么样的通道进行数据的发送和接收，很大程度上决定了程序通信的性能
> * Java共支持3种网络编程模型/IO模式：BIO、NIO、AIO
> * Java BIO ： 同步并阻塞( 传统阻塞型)，服务器实现模式为一个连接一个线程，即客户端有连接请求时服务器端就需要启动一个线程进行处理，如果这个连接不做任何事情会造成不必要的线程开销
> *  Java NIO ：  同步非阻塞，服务器实现模式为一个线程处理多个请求(连接)，即客户端发送的连接请求都会注册到多路复用器上，多路复用器轮询到连接有I/O请求就进行处理
> * Java AIO(NIO.2) ：  异步非阻塞，AIO 引入异步通道的概念，采用了 Proactor 模式，简化了程序编写，有效的请求才启动线程，它的特点是先由操作系统完成后才通知服务端程序
>
> **BIO 、NIO 、AIO 适用场景分析**
>
> *  BIO方式适用于连接数目比较小且固定的架构，这种方式对服务器资源要求比较高，并发局限于应用中，JDK1.4以前的唯一选择，但程序简单易理解。
> * NIO方式适用连接数目多且连接比较短（轻操作）的架构，比如聊天服务器，弹幕系统，服务器间通讯等。编程比较复杂，JDK1.4开始支持。
> * AIO方式使用于 连接数目多且连接比较长（重操作）的架构，比如相册服务器，充分调用OS参与并发操作，编程比较复杂，JDK7开始支持。
>
> **Java BIO基本介绍**
>
> *  Java BIO 就是 传统的java io  编程，其相关的类和接口在 java.io
> *  BIO(blocking I/O) ：  同步阻塞 ，服务器实现模式为一个连接一个线程，即客户端有连接请求时服务器端就需要启动一个线程进行处理，如果这个连接不做任何事情会造成不必要的线程开销，可以通过线程池机制改善(实现多个客户连接服务器)。
> *  BIO方式适用于连接数目比较小且固定的架构，这种方式对服务器资源要求比较高，并发局限于应用中，JDK1.4以前的唯一选择，程序简单易理解
>
> **BIO 编程简单流程**
>
> 1. 服务器端启动一个ServerSocket
> 2. 客户端启动Socket对服务器进行通信，默认情况下服务器端需要对每个客户建立一个线程与之通讯
> 3. 客户端发出请求后, 先咨询服务器是否有线程响应，如果没有则会等待，或者被拒绝
>
> **BIO工作原理图**
>
> ![](./images/netty_bio.jpg)
>
> **BIO存在的问题**
>
> * 每个请求都需要创建独立的线程，与对应的客户端进行数据Read，业务处理，数据 Write 。
> * 当并发数较大时，需要 创建大量线程来处理连接，系统资源占用较大。
> * 连接建立后，如果当前线程暂时没有数据可读，则线程就阻塞在Read 操作上，造成线程资源浪费
>
> *BIO代码示例*
>
> ```java
> package com.example.bio;
> import java.io.IOException;
> import java.io.InputStream;
> import java.net.ServerSocket;
> import java.net.Socket;
> import java.util.concurrent.ExecutorService;
> import java.util.concurrent.Executors;
> 
> public class BIOServer {
>     public static void main(String[] args) throws IOException {
>         // 1. 创建一个线程池
>         // 2. 如果有客户端连接，就创建一个线程，与之通讯
>         ExecutorService executorService = Executors.newCachedThreadPool();
>         // 创建ServerSocket
>         ServerSocket serverSocket = new ServerSocket(6666);
>         System.out.println("服务器启动了...");
> 
>         while(true){
>             //监听，等待客户端连接
>             final Socket socket = serverSocket.accept();
>             System.out.println("连接到一个客户端");
>             // 创建一个线程，与之通讯
>             executorService.execute(new Runnable() {
>                 public void run() {
>                     // 可以和客户端通讯
>                     handler(socket);
>                 }
>             });
>         }
>     }
> 
>     /**
>      * 编写一个handler方法，和客户端通讯
>      */
>     public static void handler(Socket socket){
>         byte[] bytes = new byte[1024];
>         System.out.println("线程信息：id = " + Thread.currentThread().getId() + " 线程名：" + Thread.currentThread().getName());
>         InputStream is = null;
>         // 通过socket获取输入流
>         try {
>             is = socket.getInputStream();
>             // 循环读取客户端发送的数据
>             while(true){
>                 System.out.println("线程信息：id = " + Thread.currentThread().getId() + " 线程名：" + Thread.currentThread().getName());
>                 int read = is.read(bytes);
>                 if(read != -1){
>                     // 输出客户端发送的数据
>                     System.out.println(new String(bytes,0,read));
>                 }else{
>                     break;
>                 }
>             }
>         } catch (IOException e) {
>             e.printStackTrace();
>         }finally {
>             System.out.println("关闭client连接");
>             if(is != null){
>                 try {
>                     is.close();
>                 } catch (IOException e) {
>                     e.printStackTrace();
>                 }
>             }
>             try {
>                 socket.close();
>             } catch (IOException e) {
>                 e.printStackTrace();
>             }
>         }
>     }
> }
> ```
>
> **Java NIO 基本介绍**
>
> * Java NIO 全称 java non-blocking IO，是指 JDK 提供的新API。从 JDK1.4 开始，Java 提供了一系列改进的输入/输出的新特性，被统称为 NIO(即 New IO)，是同步非阻塞的
> *  NIO 相关类都被放在 java.nio 包及子包下，并且对原 java.io包中的很多类进行改写。
> * NIO 有三大核心部分：Channel( 通道)，Buffer( 缓冲区),Selector( 选择器)
> *  NIO是 面向缓冲区  ，或者面向块编程的。数据读取到一个它稍后处理的缓冲区，需要时可在缓冲区中前后移动，这就增加了处理过程中的灵活性，使用它可以提供 非阻塞式的高伸缩性网络
> *  Java NIO的非阻塞模式，使一个线程从某通道发送请求或者读取数据，但是它仅能得到目前可用的数据，如果目前没有数据可用时，就什么都不会获取，而 不是保持线程阻塞，所以直至数据变的可以读取之前，该线程可以继续做其他的事情。 非阻塞写也是如此，一个线程请求写入一些数据到某通道，但不需要等待它完全写入，这个线程同时可以去做别的事情。
> *  通俗理解：NIO是可以做到用一个线程来处理多个操作的。假设有10000个请求过来,根据实际情况，可以分配50或者100个线程来处理。不像之前的阻塞IO那样，非得分配10000个。
> *  HTTP2.0使用了多路复用的技术，做到同一个连接并发处理多个请求，而且并发请求的数量比HTTP1.1大了好几个数量级。
>
> **NIO和BIO的比较**
>
> *  BIO以流的方式处理数据,而NIO以块的方式处理数据,块 I/O 的效率比流 I/O 高很多
> *  BIO是阻塞的，NIO则是非阻塞的
> * BIO基于字节流和字符流进行操作，而 NIO 基于 Channel(通道)和 Buffer(缓冲区)进行操作，数据总是从通道读取到缓冲区中，或者从缓冲区写入到通道中。Selector(选择器)用于监听多个通道的事件（比如：连接请求，数据到达等），因此使用单个线程就可以监听多个客户端通道
>
> *NIO代码示例*
>
> ```java
> package com.example.chat;
> import java.io.IOException;
> import java.net.InetSocketAddress;
> import java.nio.ByteBuffer;
> import java.nio.channels.*;
> import java.util.Iterator;
> 
> public class GroupChatServer {
>     private Selector selector;
>     private ServerSocketChannel serverSocketChannel;
>     private static final int PORT = 6667;
> 
>     public GroupChatServer() {
>         try {
>             // 得到选择器
>             selector = Selector.open();
>             // 获取通道
>             serverSocketChannel = ServerSocketChannel.open();
>             // 绑定端口
>             serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
>             // 设置非阻塞模式
>             serverSocketChannel.configureBlocking(false);
>             // 将serverSocketChannel注册到selector
>             serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
>         } catch (IOException e) {
>             e.printStackTrace();
>         }
>     }
> 
>     public void listen(){
>         try {
> 
>             while (true){
>                 int count = selector.select();
>                 // 有事件处理
>                 if(count > 0){
>                     // 遍历得到selectionKey集合
>                     Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
>                     while (iterator.hasNext()){
>                         // 取出selectionKey
>                         SelectionKey key = iterator.next();
>                         // 监听到accept
>                         if(key.isAcceptable()){
>                             SocketChannel sc = serverSocketChannel.accept();
>                             // 设置非阻塞
>                             sc.configureBlocking(false);
>                             // 将该sc注册到selector
>                             sc.register(selector, SelectionKey.OP_READ);
>                             // 提示
>                             System.out.println(sc.getRemoteAddress() + " 上线");
>                         }
>                         // 通道发生read事件，即通道是可读的状态
>                         if(key.isReadable()){
>                             // 处理读
>                             readData(key);
>                         }
>                         // 手动从集合中删除当前的selectionKey，防止重复操作
>                         iterator.remove();
>                     }
>                 }else{
>                     System.out.println("等待...");
>                 }
>             }
> 
>         } catch (Exception e) {
>             e.printStackTrace();
>         }finally {
> 
>         }
>     }
> 
>     /**
>      * 读取客户端消息
>      */
>     private void readData(SelectionKey key){
>         // 定义一个SocketChannel
>         SocketChannel channel = null;
>         try {
>             // 得到channel
>             channel = (SocketChannel) key.channel();
>             // 创建缓冲区
>             ByteBuffer buffer = ByteBuffer.allocate(1024);
>             int count = channel.read(buffer);
>             if(count > 0){
>                 // 把缓冲区的数据转成字符串
>                 String msg = new String(buffer.array());
>                 // 输出该消息
>                 System.out.println("from 客户端：" + msg);
>                 // 向其它的客户端转发消息
>                 sendInfoToOtherClients(msg, channel);
>             }
>         } catch (IOException e) {
>             try {
>                 System.out.println(channel.getRemoteAddress() + " 离线了");
>                 // 取消注册
>                 key.cancel();
>                 // 关闭通道
>                 channel.close();
>             } catch (IOException e1) {
>                 e1.printStackTrace();
>             }
>         }
>     }
> 
>     /**
>      * 转发消息给其它的客户端
>      */
>     private void sendInfoToOtherClients(String msg,SocketChannel self) throws IOException {
>         System.out.println("服务器转发消息中...");
>         System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());
>         // 遍历所有注册到selector上的SocketChannel，并排除self
>         for (SelectionKey key : selector.keys()) {
>             // 通过key取出对应的SocketChannel
>             Channel targetChannel = key.channel();
>             // 排除自己
>             if(targetChannel instanceof SocketChannel && targetChannel != self){
>                 // 转换类型
>                 SocketChannel dest = (SocketChannel) targetChannel;
>                 // 将msg存储到buffer
>                 ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
>                 // 将buffer的数据写入通道
>                 dest.write(buffer);
>             }
>         }
>     }
> 
>     public static void main(String[] args) {
>         GroupChatServer groupChatServer = new GroupChatServer();
>         groupChatServer.listen();
>     }
> }
> ```
>
> ```java
> package com.example.chat;
> import java.io.IOException;
> import java.net.InetSocketAddress;
> import java.nio.ByteBuffer;
> import java.nio.channels.SelectionKey;
> import java.nio.channels.Selector;
> import java.nio.channels.SocketChannel;
> import java.util.Iterator;
> import java.util.Scanner;
> 
> public class GroupChatClient {
>     // 服务器IP
>     private final String HOST = "127.0.0.1";
>     // 服务器端口
>     private final int PORT = 6667;
>     private Selector selector;
>     private SocketChannel socketChannel;
>     private String username;
> 
>     public GroupChatClient() throws IOException {
>         selector = Selector.open();
>         // 连接服务器
>         socketChannel = SocketChannel.open(new InetSocketAddress(HOST,PORT));
>         // 设置非阻塞
>         socketChannel.configureBlocking(false);
>         // 将channel注册到selector
>         socketChannel.register(selector, SelectionKey.OP_READ);
>         // 得到username
>         username = socketChannel.getLocalAddress().toString().substring(1);
>         System.out.println(username + "is OK!");
>     }
> 
>     /**
>      * 向服务器发送消息
>      */
>     public void sendInfo(String info){
>         info = username + "说：" + info;
>         try {
>             socketChannel.write(ByteBuffer.wrap(info.getBytes()));
> 
>         } catch (IOException e) {
>             e.printStackTrace();
>         } finally {
>         }
>     }
> 
>     /**
>      * 读取从服务器回复的消息
>      */
>     public void readInfo(){
>         try {
>             int readCount = selector.select();
>             // 有事件发生的通道
>             if(readCount > 0){
>                 Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
>                 while(iterator.hasNext()){
>                     SelectionKey key = iterator.next();
>                     if(key.isReadable()){
>                         // 得到相关通道
>                         SocketChannel sc = (SocketChannel) key.channel();
>                         // 得到一个buffer
>                         ByteBuffer buffer = ByteBuffer.allocate(1024);
>                         // 读取
>                         sc.read(buffer);
>                         // 把读到的缓冲区的数据转成字符串
>                         String msg = new String(buffer.array());
>                         System.out.println(msg.trim());
>                     }
>                 }
>                 iterator.remove();
>             }else{
> 
>             }
>         } catch (IOException e) {
>             e.printStackTrace();
>         }
>     }
> 
>     public static void main(String[] args) throws IOException {
>         GroupChatClient chatClient = new GroupChatClient();
>         // 启动一个线程，每隔3秒读取从服务器端发送数据
>         new Thread(){
>             @Override
>             public void run() {
>                 while (true){
>                     System.out.println("读取消息中...");
>                     chatClient.readInfo();
>                     try {
>                         Thread.currentThread().sleep(3000);
>                     } catch (InterruptedException e) {
>                         e.printStackTrace();
>                     }
>                 }
>             }
>         }.start();
>         // 发送数据给服务器端
>         Scanner scanner = new Scanner(System.in);
>         while(scanner.hasNextLine()){
>             String s = scanner.nextLine();
>             chatClient.sendInfo(s);
>         }
>     }
> }
> ```
>
> **Java AIO 基本介绍 基本介绍**
>
> * JDK 7 引入了 Asynchronous I/O，即 AIO。在进行 I/O 编程中，常用到两种模式：Reactor和 Proactor。Java 的 NIO 就是 Reactor，当有事件触发时，服务器端得到通知，进行相应的处理
> *  AIO 即 NIO2.0，叫做异步不阻塞的 IO。AIO 引入异步通道的概念，采用了Proactor 模式，简化了程序编写，有效的请求才启动线程，它的特点是先由操作系统完成后才通知服务端程序启动线程去处理，一般适用于连接数较多且连接时间较长的应用
>
> **原生NIO存在的问题**
>
> * NIO 的类库和 API 繁杂，使用麻烦：需要熟练掌握 Selector、ServerSocketChannel、SocketChannel、ByteBuffer 等。
> * 需要具备其他的额外技能：要熟悉 Java 多线程编程，因为 NIO 编程涉及到 Reactor模式，你必须对多线程和网络编程非常熟悉，才能编写出高质量的 NIO 程序。
> * 开发工作量和难度都非常大：例如客户端面临断连重连、网络闪断、半包读写、失败缓存、网络拥塞和异常流的处理等等。
> * JDK NIO 的 Bug：例如臭名昭著的 Epoll Bug，它会导致 Selector 空轮询，最终导致 CPU 100%。直到 JDK 1.7 版本该问题仍旧存在，没有被根本解决。
>
> **线程模型基本介绍**
>
> * 不同的线程模式，对程序的性能有很大影响，为了搞清Netty 线程模式，我们来系统的讲解下各个线程模式，最后看看Netty 线程模型有什么优越性.
> * 目前存在的线程模型有：
>   * 传统阻塞 I/O 服务模型
>   * Reactor 模式
> * 根据 Reactor  的数量和处理资源池线程的数量不同，有3种典型的实现
>   * 单 Reactor 单线程
>   * 单 Reactor 多线程
>   * 主从Reactor 多线程
> *  Netty 线程模式(Netty主要基于主从 Reactor多线程模型做了一定的改进，其中主从Reactor 多线程模型有多个 Reactor)
>
> **Netty模型原理图**
>
> ![](./images/netty_reactor.jpg)
>
> * Netty抽象出两组线程池 BossGroup 专门负责接收客户端的连接,WorkerGroup 专门负责网络的读写
> *  BossGroup 和 WorkerGroup 类型都是 NioEventLoopGroup
> *  NioEventLoopGroup 相当于一个事件循环组, 这个组中含有多个事件循环 ，每一个事件循环是 NioEventLoop
> *  NioEventLoop 表示一个不断循环的执行处理任务的线程， 每个NioEventLoop 都有一个selector , 用于监听绑定在其上的socket的网络通讯
> * NioEventLoopGroup 可以有多个线程, 即可以含有多个NioEventLoop
> * 每个Boss NioEventLoop 循环执行的步骤有3步
>   * 轮询accept 事件
>   * 处理accept 事件 , 与client建立连接 , 生成NioScocketChannel , 并将其注册到某个worker NIOEventLoop 上的 selector
>   * 处理任务队列的任务 ， 即runAllTasks
> * 每个Worker NIOEventLoop 循环执行的步骤
>   * 轮询read, write 事件
>   * 处理i/o事件， 即read , write 事件，在对应NioScocketChannel 处理
>   * 处理任务队列的任务 ， 即 runAllTasks
> * 每个Worker NIOEventLoop 处理业务时，会使用pipeline(管道), pipeline中包含了channel，即通过pipeline可以获取到对应通道，管道中维护了很多的处理器

### Netty入门示例

> ```xml
> <?xml version="1.0" encoding="UTF-8"?>
> <project xmlns="http://maven.apache.org/POM/4.0.0"
>    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
> <modelVersion>4.0.0</modelVersion>
> 
> <groupId>com.example</groupId>
> <artifactId>nettydemo</artifactId>
> <version>1.0-SNAPSHOT</version>
> <build>
>   <plugins>
>       <plugin>
>           <groupId>org.apache.maven.plugins</groupId>
>           <artifactId>maven-compiler-plugin</artifactId>
>           <configuration>
>               <source>7</source>
>               <target>7</target>
>           </configuration>
>       </plugin>
>   </plugins>
> </build>
> 
> <dependencies>
>   <!-- https://mvnrepository.com/artifact/io.netty/netty-all -->
>   <dependency>
>       <groupId>io.netty</groupId>
>       <artifactId>netty-all</artifactId>
>       <version>4.1.67.Final</version>
>   </dependency>
> </dependencies>
> </project>
> ```
>
> ```java
> package com.example.netty;
> import io.netty.bootstrap.ServerBootstrap;
> import io.netty.channel.ChannelFuture;
> import io.netty.channel.ChannelInitializer;
> import io.netty.channel.ChannelOption;
> import io.netty.channel.EventLoopGroup;
> import io.netty.channel.nio.NioEventLoopGroup;
> import io.netty.channel.socket.SocketChannel;
> import io.netty.channel.socket.nio.NioServerSocketChannel;
> 
> public class NettyServer {
>  public static void main(String[] args) throws InterruptedException {
>      // 创建 BossGroup 和 WorkerGroup
>      // 说明：
>      // 1. 创建两个线程组 bossGroup 和 workerGroup
>      // 2. bossGroup只是处理连接请求，真正的业务处理，会交给workerGroup处理
>      // 3. 两个都是无限循环
>      // bossGroup 和 workerGroup 含有的子线程（NioEventLoop）的个数，默认是实际的逻辑CPU核数 * 2
>      EventLoopGroup bossGroup = new NioEventLoopGroup(1);
>      EventLoopGroup workerGroup = new NioEventLoopGroup(3);
>      try {
>          // 创建服务端的启动对象，配置参数
>          ServerBootstrap bootstrap = new ServerBootstrap();
>          // 使用链式编程来进行设置
>          // 这是设置两个线程组
>          bootstrap.group(bossGroup,workerGroup)
>                    // 使用NioSocketChannel作为服务器的通道实现
>                   .channel(NioServerSocketChannel.class)
>                    // 设置线程队列等待连接的个数
>                   .option(ChannelOption.SO_BACKLOG, 128)
>                     // 设置保持活动连接状态
>                   .childOption(ChannelOption.SO_KEEPALIVE, true)
>                    // 创建一个通道初始化对象
>              	  .handler(null)
>              	  // 注意：handler方法对应bossGroup，childHandler方法对应workerGroup
>                   .childHandler(new ChannelInitializer<SocketChannel>() {
>                       // 给pipeline设置处理器
>                       @Override
>                       protected void initChannel(SocketChannel ch) {
>                          ch.pipeline().addLast(new NettyServerHandler());
>                       }
>                   }); // 给workerGroup的EventLoop对应的管道设置处理器
> 
>          System.out.println("...服务器 is ready ...");
>          // 绑定一个端口，并且同步,生成一个ChannelFuture对象（这里相当于启动服务器，并绑定端口）
>          ChannelFuture cf = bootstrap.bind(6668).sync();
>          // 对关闭通道事件进行监听
>          cf.channel().closeFuture().sync();
>      } catch (InterruptedException e) {
>          e.printStackTrace();
>      }finally {
>          bossGroup.shutdownGracefully();
>          workerGroup.shutdownGracefully();
>      }
>  }
> }
> ```
>
> ```java
> package com.example.netty;
> import io.netty.bootstrap.Bootstrap;
> import io.netty.channel.ChannelFuture;
> import io.netty.channel.ChannelInitializer;
> import io.netty.channel.EventLoopGroup;
> import io.netty.channel.nio.NioEventLoopGroup;
> import io.netty.channel.socket.SocketChannel;
> import io.netty.channel.socket.nio.NioSocketChannel;
> 
> public class NettyClient {
> public static void main(String[] args) throws InterruptedException {
>   // 客户端需要一个事件循环组
>   EventLoopGroup group = new NioEventLoopGroup();
>   try {
>       // 创建客户端启动对象
>       Bootstrap bootstrap = new Bootstrap();
>       // 设置相关参数
>       // 设置线程组
>       bootstrap.group(group)
>                // 设置客户端通道的实现类
>                .channel(NioSocketChannel.class)
>                .handler(new ChannelInitializer<SocketChannel>() {
>                    @Override
>                    protected void initChannel(SocketChannel ch) {
>                        // 加入自己的处理器
>                        ch.pipeline().addLast(new NettyClientHandler());
>                    }
>                });
>       System.out.println("客户端 is OK ...");
>       // 启动客户端去连接服务端
>       ChannelFuture cf = bootstrap.connect("127.0.0.1", 6668).sync();
>       // 监听关闭通道事件
>       cf.channel().closeFuture().sync();
>   } catch (InterruptedException e) {
>       e.printStackTrace();
>   } finally {
>       group.shutdownGracefully();
>   }
> }
> }
> ```
>
> ```java
> package com.example.netty;
> import io.netty.buffer.ByteBuf;
> import io.netty.buffer.Unpooled;
> import io.netty.channel.Channel;
> import io.netty.channel.ChannelHandlerContext;
> import io.netty.channel.ChannelInboundHandlerAdapter;
> import io.netty.channel.ChannelPipeline;
> import io.netty.util.CharsetUtil;
> 
> /**
>  *  1. 我们自定义一个Handler，需要继承netty规定好的某个HandlerAdapter
>  *  2. 这时我们自定义的Handler，才能称之为一个Handler
>  */
> public class NettyServerHandler extends ChannelInboundHandlerAdapter {
>     /**
>      *  读取数据事件（这里我们可以读取客户端发送的消息）
>      *  1. ChannelHandlerContext ctx ：上下文对象，含有管道pipeline，通道channel，地址
>      *  2. Object msg ： 客户端发送的数据，默认Object
>      */
>     @Override
>     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
>         System.out.println("服务器读取线程： " + Thread.currentThread().getName());
>         System.out.println("server ctx = " + ctx);
> 
>         Channel channel = ctx.channel();
>         // 本质上底层是一个双向链表
>         ChannelPipeline pipeline = ctx.pipeline();
> 
> 
>         // 将msg转成一个ByteBuf，ByteBuf是netty提供的，不是NIO提供的
>         ByteBuf buf = (ByteBuf) msg;
>         System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
>         System.out.println("客户端地址：" + ctx.channel().remoteAddress());
>     }
> 
>     /**
>      * 数据读取完毕
>      */
>     @Override
>     public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
>         // 将数据写入到缓存并刷新，通常我们要对发送的数据进行编码
>         ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端!",CharsetUtil.UTF_8));
>     }
> 
>     /**
>      * 异常处理
>      */
>     @Override
>     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
>         ctx.close();
>     }
> }
> ```
>
> ```java
> package com.example.netty;
> import io.netty.buffer.ByteBuf;
> import io.netty.buffer.Unpooled;
> import io.netty.channel.ChannelHandlerContext;
> import io.netty.channel.ChannelInboundHandlerAdapter;
> import io.netty.util.CharsetUtil;
> 
> public class NettyClientHandler extends ChannelInboundHandlerAdapter {
> 
>     /**
>      *  当通道就绪就会触发该方法
>      */
>     @Override
>     public void channelActive(ChannelHandlerContext ctx) throws Exception {
>         System.out.println("Client ctx : " + ctx);
>         ctx.writeAndFlush(Unpooled.copiedBuffer("hello,server!",CharsetUtil.UTF_8));
>     }
> 
>     /**
>      * 当通道有读取事件时，会触发
>      */
>     @Override
>     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
>         ByteBuf buf = (ByteBuf) msg;
>         System.out.println("服务器回复的消息： " + buf.toString(CharsetUtil.UTF_8));
>         System.out.println("服务器地址： " + ctx.channel().remoteAddress());
>     }
> 
>     /**
>      * 异常处理
>      */
>     @Override
>     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
>         cause.printStackTrace();
>         ctx.close();
>     }
> }
> ```
>

### taskQueue

> **任务队列中的Task有3种典型使用场景**
>
> * 用户程序自定义的普通任务
> * 用户自定义定时任务
> * 非当前 Reactor 线程调用 Channel 的各种方法
>   * 例如在==推送系统==的业务线程里面，根据==用户的标识==，找到对应的==Channel 引用==，然后调用 Write 类方法向该用户推送消息，就会进入到这种场景。最终的 Write 会提交到任务队列中后被==异步消费==
>
> ```java
> @Override
> public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
>     // 这里我们有一个耗时长的业务 -> 异步执行 -> 提交到该channel对应的NioEventLoop的taskQueue中
>     // 解决方案1，用户程序自定义的普通任务
>     ctx.channel().eventLoop().execute(new Runnable() {
>         @Override
>         public void run() {
>             try {
>                 Thread.sleep(5 * 1000);
>                 ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端，喵喵喵111...", CharsetUtil.UTF_8));
>             } catch (InterruptedException e) {
>                 System.out.println("发生异常：" + e.getMessage());
>             }
>         }
>     });
> 
>     // 解决方案2 用户自定义定时任务，这个任务是放在scheduleTaskQueue中的
>     ctx.channel().eventLoop().schedule(new Runnable() {
>         @Override
>         public void run() {
>             try {
>                 Thread.sleep(5 * 1000);
>                 ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端，schedule定时任务...", CharsetUtil.UTF_8));
>             } catch (InterruptedException e) {
>                 System.out.println("发生异常：" + e.getMessage());
>             }
>         }
>     }, 5, TimeUnit.SECONDS);
> 
> 
>     System.out.println("go on ...");
> }
> ```
>
> **Netty模型方案再说明**
>
> *  Netty 抽象出两组 线程池，BossGroup 专门负责接收客户端连接，WorkerGroup 专门负责网络读写操作。
> *  NioEventLoop 表示一个不断循环执行处理任务的线程，每个 NioEventLoop 都有一个selector，用于监听绑定在其上的 socket 网络通道。
> *  NioEventLoop 内部采用串行化设计，从消息的读取->解码->处理->编码->发送，始终由IO线程NioEventLoop负责
>   * NioEventLoopGroup下包含多个NioEventLoop
>   * 每个NioEventLoop 中包含有一个 Selector，一个 taskQueue
>   * 每个NioEventLoop 的 Selector 上可以注册监听多个 NioChannel
>   * 每个NioChannel 只会绑定在唯一的 NioEventLoop 上
>   * 每个NioChannel 都绑定有一个自己的 ChannelPipeline

### 异步模型

> **异步模型基本介绍**
>
> *  异步的概念和同步相对。当一个异步过程调用发出后，调用者不能立刻得到结果。实际处理这个调用的组件在完成后，通过状态、通知和回调来通知调用者。
> * Netty 中的 I/O 操作是异步的，包括 Bind、Write、Connect 等操作会简单的返回一个ChannelFuture。
> * 调用者并不能立刻获得结果，而是通过 Future-Listener 机制，用户可以方便的主动获取或者通过通知机制获得 IO 操作结果
> *  Netty 的异步模型是建立在 future 和 callback 的之上的。callback 就是回调。重点说Future，它的核心思想是：假设一个方法 fun，计算过程可能非常耗时，等待 fun返回显然不合适。那么可以在调用 fun 的时候，立马返回一个 Future，后续可以通过Future去监控方法 fun 的处理过程(即 ： Future-Listener 机制)
>
> **Future 说明**
>
> * 表示异步的执行结果, 可以通过它提供的方法来检测执行是否完成，比如检索计算等等
> *  ChannelFuture 是一个接口 ： public interface ChannelFuture extends Future<Void>我们可以添加监听器，当监听的事件发生时，就会通知到监听器
>
> **Future-Listener 机制**
>
> *  当 Future 对象刚刚创建时，处于非完成状态，调用者可以通过返回的 ChannelFuture来获取操作执行的状态，注册监听函数来执行完成后的操作。
> * 常见有如下操作
>   * 通过 isDone 方法来判断当前操作是否完成
>   * 通过 isSuccess 方法来判断已完成的当前操作是否成功
>   * 通过 getCause 方法来获取已完成的当前操作失败的原因
>   * 通过 isCancelled 方法来判断已完成的当前操作是否被取消
>   * 通过 addListener 方法来注册监听器，当操作已完成(isDone 方法返回完成)，将会通知指定的监听器；如果 Future 对象已完成，则通知指定的监听器
> * 相比传统阻塞 I/O，执行 I/O 操作后线程会被阻塞住, 直到操作完成；异步处理的好处是不会造成线程阻塞，线程在 I/O 操作期间可以执行别的程序，在高并发情形下会更稳定和更高的吞吐量
>
> ```java
> // 绑定一个端口，并且同步,生成一个ChannelFuture对象（这里相当于启动服务器，并绑定端口）
> ChannelFuture cf = bootstrap.bind(6668).sync();
> // 给cf注册监听器，监听我们关心的事件
> cf.addListener(new ChannelFutureListener() {
>     @Override
>     public void operationComplete(ChannelFuture future) throws Exception {
>         if(cf.isSuccess()){
>             System.out.println("监听端口6668成功！");
>         }else{
>             System.out.println("监听端口失败！");
>         }
>     }
> });
> ```
>

### http服务

> ```java
> package com.example.http;
> import io.netty.bootstrap.ServerBootstrap;
> import io.netty.channel.ChannelFuture;
> import io.netty.channel.nio.NioEventLoopGroup;
> import io.netty.channel.socket.nio.NioServerSocketChannel;
> 
> public class TestServer {
>     public static void main(String[] args) throws Exception{
>         NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
>         NioEventLoopGroup workerGroup = new NioEventLoopGroup();
>         try{
>             ServerBootstrap serverBootstrap = new ServerBootstrap();
>             serverBootstrap.group(bossGroup,workerGroup ).channel(NioServerSocketChannel.class).childHandler(new TestServerInitializer());
>             ChannelFuture cf = serverBootstrap.bind(9999).sync();
>             cf.channel().closeFuture().sync();
>         }finally {
>             bossGroup.shutdownGracefully();
>             workerGroup.shutdownGracefully();
>         }
>     }
> }
> ```
>
> ```java
> package com.example.http;
> import io.netty.buffer.ByteBuf;
> import io.netty.buffer.Unpooled;
> import io.netty.channel.ChannelHandlerContext;
> import io.netty.channel.SimpleChannelInboundHandler;
> import io.netty.handler.codec.http.*;
> import io.netty.util.CharsetUtil;
> 
> import java.net.URI;
> 
> /**
>  *  说明：
>  *  1. SimpleChannelInboundHandler是ChannelInboundHandlerAdapter的子类
>  *  2. HttpObject客户端和服务器端相互通讯的数据被封装成HttpObject
>  */
> public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
> 
>     /**
>      * 读取客户端数据
>      */
>     @Override
>     protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
>         // 判断msg是不是HttpRequest请求
>         if(msg instanceof HttpRequest){
>             System.out.println("pipeline hashcode = " + ctx.pipeline().hashCode() + " TestHttpServerHandler hashcode = " + this.hashCode());
>             System.out.println("msg 类型=" + msg.getClass());
>             System.out.println("客户端地址：" + ctx.channel().remoteAddress());
>             HttpRequest httpRequest = (HttpRequest) msg;
>             URI uri = new URI(httpRequest.uri());
>             if("/favicon.ico".equals(uri.getPath())){
>                 System.out.println("请求了favicon.ico，不做响应");
>                 return;
>             }
> 
>             // 回复信息给浏览器【http协议】
>             ByteBuf content = Unpooled.copiedBuffer("hello,我是服务器！", CharsetUtil.UTF_8);
>             // 构造一个http的响应，即HttpResponse
>             FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
>             response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=UTF-8");
>             response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
>             // 将构建好的response返回
>             ctx.writeAndFlush(response);
>         }
>     }
> }
> ```
>
> ```java
> package com.example.http;
> import io.netty.channel.ChannelInitializer;
> import io.netty.channel.ChannelPipeline;
> import io.netty.channel.socket.SocketChannel;
> import io.netty.handler.codec.http.HttpServerCodec;
> 
> public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
> 
>     @Override
>     protected void initChannel(SocketChannel ch) throws Exception {
>         // 向管道加入处理器
> 
>         // 得到管道
>         ChannelPipeline pipeline = ch.pipeline();
>         // 加入一个netty提供的httpServerCodec
>         // 1. HttpServerCodec 是netty提供的处理http的编码解码器
>         pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
>         // 2. 增加一个自定义的handler
>         pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());
> 
>         System.out.println("ok...");
>     }
> }
> ```

### Netty核心模块

> **Bootstrap 、ServerBootstrap**
>
> * Bootstrap 意思是引导，一个 Netty 应用通常由一个 Bootstrap 开始，主要作用是配置整个 Netty 程序，串联各个组件，Netty 中 Bootstrap 类是客户端程序的启动引导类，ServerBootstrap 是服务端启动引导类
> * 常见的方法有：
>   * public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup)，该方法用于服务器端，用来设置两个 EventLoop
>   * public B group(EventLoopGroup group) ，该方法用于客户端，用来设置一个 EventLoop
>   * public B channel(Class<? extends C> channelClass)，该方法用来设置一个服务器端的通道实现
>   * public <T> B option(ChannelOption<T> option, T value)，用来给 ServerChannel 添加配置
>   * public <T> ServerBootstrap childOption(ChannelOption<T> childOption, T value)，用来给接收到的通道添加配置
>   * public ServerBootstrap childHandler(ChannelHandler childHandler)，该方法用来设置业务处理类（自定义的 handler）
>   * public ChannelFuture bind(int inetPort) ，该方法用于服务器端，用来设置占用的端口号
>   * public ChannelFuture connect(String inetHost, int inetPort) ，该方法用于客户端，用来连接服务器端
>
> **Future 、ChannelFuture**
>
> * Netty 中所有的 IO 操作都是异步的，不能立刻得知消息是否被正确处理。但是可以过一会等它执行完成或者直接注册一个监听，具体的实现就是通过 Future 和ChannelFutures，他们可以注册一个监听，当操作执行成功或失败时监听会自动触发注册的监听事件
> * 常见的方法有：
>   * Channel channel()，返回当前正在进行 IO 操作的通道
>   * ChannelFuture sync()，等待异步操作执行完毕
>
> **Channel**
>
> * Netty网络通信的组件，能够用于执行网络 I/O 操作。
> * 通过Channel可获得当前网络连接的通道的状态
> * 通过Channel 可获得 网络连接的配置参数 （例如接收缓冲区大小）
> * Channel 提供异步的网络 I/O 操作(如建立连接，读写，绑定端口)，异步调用意味着任何 I/O 调用都将立即返回，并且不保证在调用结束时所请求的 I/O 操作已完成
> * 调用立即返回一个 ChannelFuture 实例，通过注册监听器到 ChannelFuture 上，可以I/O 操作成功、失败或取消时回调通知调用方
> * 支持关联 I/O 操作与对应的处理程序
> * 不同协议、不同的阻塞类型的连接都有不同的 Channel 类型与之对应，常用的Channel 类型:
>   * NioSocketChannel，异步的客户端 TCP Socket 连接。
>   * NioServerSocketChannel，异步的服务器端 TCP Socket 连接。
>   * NioDatagramChannel，异步的 UDP 连接。
>   * NioSctpChannel，异步的客户端 Sctp 连接。
>   * NioSctpServerChannel，异步的 Sctp 服务器端连接，这些通道涵盖了 UDP 和 TCP 网络 IO以及文件 IO。
>
> **Selector**
>
> * Netty 基于Selector 对象实现 I/O 多路复用，通过 Selector 一个线程可以监听多个连接的Channel 事件。
> * 当向一个 Selector 中注册 Channel 后，Selector 内部的机制就可以自动不断地查询(Select) 这些注册的 Channel 是否有已就绪的 I/O 事件（例如可读，可写，网络连接完成等），这样程序就可以很简单地使用一个线程高效地管理多个 Channel
>
> **ChannelHandler及其实现类**
>
> *  ChannelHandler 是一个接口，处理 I/O 事件或拦截 I/O 操作，并将其转发到其ChannelPipeline(业务处理链)中的下一个处理程序。
> *  ChannelHandler 本身并没有提供很多方法，因为这个接口有许多的方法需要实现，方便使用期间，可以继承它的子类
> *  ChannelHandler  及其实现 类一览图
>   * ![](./images/netty_handler1.jpg)
>   * ChannelInboundHandler 用于处理入站 I/O 事件。
>   * ChannelOutboundHandler 用于处理出站 I/O 操作。
>   * ChannelInboundHandlerAdapter用于处理入站 I/O 事件。
>   * ChannelOutboundHandlerAdapter 用于处理出站 I/O 操作。
>   * ChannelDuplexHandler 用于处理入站和出站事件。
>   * 我们经常需要自定义一个 Handler 类去继承ChannelInboundHandlerAdapter，然后通过重写相应方法实现业务逻辑
>
> **Pipeline 和 ChannelPipeline**
>
> *  ChannelPipeline 是一个 Handler 的集合，它负责处理和拦截 inbound 或者outbound 的事件和操作，相当于一个贯穿 Netty 的链。( 也可以这样理解：ChannelPipeline  是保存ChannelHandler的 List ，用于处理或拦截 Channel 的入站事件和出站操作)
> * ChannelPipeline 实现了一种高级形式的拦截过滤器模式，使用户可以完全控制事件的处理方式，以及 Channel 中各个的 ChannelHandler 如何相互交互
> * 在 Netty 中每个 Channel 都有且仅有一个 ChannelPipeline 与之对应，它们的组成关系如下
>   * ![](./images/netty_pipeline1.jpg)
>   *  一个 Channel 包含了一个 ChannelPipeline，而 ChannelPipeline 中又维护了一个由 ChannelHandlerContext组成的双向链表，并且每个 ChannelHandlerContext 中又关联着一个 ChannelHandler
>   * 入站事件和出站事件在一个双向链表中，入站事件会从链表 head 往后传递到最后一个入站的 handler，出站事件会从链表 tail 往前传递到最前一个出站的 handler，两种类型的 handler 互不干扰
> * 常用方法
>   * ChannelPipeline addFirst(ChannelHandler... handlers)，把一个业务处理类（handler）添加到链中的第一个位置
>   * ChannelPipeline addLast(ChannelHandler... handlers)，把一个业务处理类（handler）添加到链中的最后一个位置
>
> **ChannelHandlerContext**
>
> * 保存 Channel 相关的所有上下文信息，同时关联一个 ChannelHandler 对象
> *  即ChannelHandlerContext 中 包 含 一 个 具 体 的 事 件 处 理 器 ChannelHandler ，同时ChannelHandlerContext 中也绑定了对应的 pipeline 和 Channel 的信息，方便对 ChannelHandler进行调用
> * 常用方法
>   * ChannelFuture close()，关闭通道
>   * ChannelOutboundInvoker flush()，刷新
>   * ChannelFuture writeAndFlush(Object msg) ， 将 数 据 写 到 ChannelPipeline 中当前ChannelHandler 的下一个 ChannelHandler 开始处理（出站）
>
> **ChannelOption**
>
> *  Netty 在创建 Channel 实例后,一般都需要设置 ChannelOption 参数。
> * ChannelOption 参数如下:
>   * ChannelOption.SO_BACKLOG：对应 TCP/IP 协议 listen 函数中的 backlog 参数，用来初始化服务器可连接队列大小。服务端处理客户端连接请求是顺序处理的，所以同一时间只能处理一个客户端连接。多个客户
>     端来的时候，服务端将不能处理的客户端连接请求放在队列中等待处理，backlog 参数指定了队列的大小。
>   * ChannelOption.SO_KEEPALIVE：一直保持连接活动状态
>
> **EventLoopGroup  和其实现类 NioEventLoopGroup**
>
> *  EventLoopGroup 是一组 EventLoop 的抽象，Netty 为了更好的利用多核 CPU 资源，一般会有多个 EventLoop 同时工作，每个 EventLoop 维护着一个 Selector 实例。
> *  EventLoopGroup 提供 next 接口，可以从组里面按照一定规则获取其中一个EventLoop来处理任务。在 Netty 服务器端编程中，我们一般都需要提供两个EventLoopGroup，例如：BossEventLoopGroup 和 WorkerEventLoopGroup。
> * 通常一个服务端口即一个 ServerSocketChannel对应一个Selector 和一个EventLoop线程。BossEventLoop 负责接收客户端的连接并将 SocketChannel 交给WorkerEventLoopGroup 来进行 IO 处理，如下图所示
>   * ![](./images/netty_eventlop.jpg)
>   * BossEventLoopGroup 通常是一个单线程的 EventLoop，EventLoop 维护着一个注册了ServerSocketChannel 的Selector 实例BossEventLoop 不断轮询Selector 将连接事件分离出来
>   * 通常是 OP_ACCEPT 事件，然后将接收到的 SocketChannel 交给WorkerEventLoopGroup
>   * WorkerEventLoopGroup 会由 next 选择其中一个 EventLoop来将这个SocketChannel 注册到其维护的Selector 并对其后续的 IO 事件进行处理
> * 常用方法
>   * public NioEventLoopGroup()，构造方法
>   * public Future<?> shutdownGracefully()，断开连接，关闭线程
>
> **Unpooled 类**
>
> *  Netty 提供一个专门用来操作缓冲区(即Netty的数据容器)的工具类
>
> * 常用方法如下所示
>
>   * ```java
>     //通过给定的数据和字符编码返回一个 ByteBuf 对象（ 类似于 NIO  中的 ByteBuffer  但有区别）
>     public static ByteBuf copiedBuffer(CharSequence string, Charset charset)
>     ```
>
>
> ```java
> package com.example.test;
> import io.netty.buffer.ByteBuf;
> import io.netty.buffer.Unpooled;
> import io.netty.util.CharsetUtil;
> import org.junit.Test;
> import java.nio.charset.Charset;
> 
> public class NettyTest {
> 
>     @Test
>     public void test(){
>         // 1. 创建对象，该对象包含一个数组arr，是一个byte[10]；
>         // 2. 在netty的buffer中，不需要使用flip进行反转
>         // 底层维护了readerIndex和writerIndex
>         // 通过readerIndex、writerIndex、capacity将buffer分成3个区域
>         // 0 - readerIndex已经读取的区域，readerIndex-writerIndex可读取的区域，writerIndex-capacity可写的区域
>         ByteBuf buffer = Unpooled.buffer(10);
>         for (int i = 0; i < 10; i++) {
>             buffer.writeByte(i);
>         }
> 
>         System.out.println("capacity = " + buffer.capacity()); // 10
>         // 输出
> //        for (int i = 0; i < buffer.capacity(); i++) {
> //            System.out.println(buffer.getByte(i));
> //        }
>         for (int i = 0; i < buffer.capacity(); i++) {
>             System.out.println(buffer.readByte());
>         }
>         System.out.println("执行完毕！");
>     }
> 
>     @Test
>     public void test2(){
>         // 创建ByteBuf
>         ByteBuf byteBuf = Unpooled.copiedBuffer("Hello,World!", CharsetUtil.UTF_8);
>         // 使用相关的方法
>         if(byteBuf.hasArray()){ // true
>             byte[] content = byteBuf.array();
>             // 将content转成字符串
>             System.out.println(new String(content,Charset.forName("utf-8")));
>             System.out.println("byteBuf = " + byteBuf);
>             System.out.println(byteBuf.arrayOffset());     // 0
>             System.out.println(byteBuf.readerIndex());     // 0
>             System.out.println(byteBuf.writerIndex());     // 12
>             System.out.println(byteBuf.capacity());
> 
>             int len = byteBuf.readableBytes();  // 可读的字节数  12
> 
>             // 使用for循环取出各个字节
>             for (int i = 0; i < len; i++) {
>                 System.out.println((char) byteBuf.getByte(i));
>             }
> 
>             System.out.println(byteBuf.getCharSequence(0, 4, CharsetUtil.UTF_8));   // 从0位置开始取，长度为4的字符序列  Hell
>         }
>     }
> }
> ```
>

### 群聊示例

> ```java
> package com.example.groupchat;
> 
> import io.netty.bootstrap.ServerBootstrap;
> import io.netty.channel.*;
> import io.netty.channel.nio.NioEventLoopGroup;
> import io.netty.channel.socket.SocketChannel;
> import io.netty.channel.socket.nio.NioServerSocketChannel;
> import io.netty.handler.codec.string.StringDecoder;
> import io.netty.handler.codec.string.StringEncoder;
> 
> public class GroupChatServer {
> 
>     // 监听端口
>     private int port;
> 
>     public GroupChatServer(int port) {
>         this.port = port;
>     }
> 
>     // 编写run方法，处理客户端请求
>     public void run() throws Exception {
>         // 创建两个线程组
>         NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
>         NioEventLoopGroup workerGroup = new NioEventLoopGroup(8);
>         try {
>             ServerBootstrap serverBootstrap = new ServerBootstrap();
>             serverBootstrap.group(bossGroup, workerGroup)
>                            .channel(NioServerSocketChannel.class)
>                            .option(ChannelOption.SO_BACKLOG, 128)
>                            .childOption(ChannelOption.SO_KEEPALIVE, true)
>                            .childHandler(new ChannelInitializer<SocketChannel>() {
>                                @Override
>                                protected void initChannel(SocketChannel ch) throws Exception {
>                                    // 获取pipeline
>                                    ChannelPipeline pipeline = ch.pipeline();
>                                    // 向pipeline加入解码器
>                                    pipeline.addLast("decoder",new StringDecoder());
>                                    // 向pipeline加入编码器
>                                    pipeline.addLast("encoder",new StringEncoder());
>                                    // 加入自己的业务处理handler
>                                    pipeline.addLast(new GroupChatServerHandler());
>                                }
>                            });
>             System.out.println("服务器启动...");
>             ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
>             // 监听关闭事件
>             channelFuture.channel().closeFuture().sync();
>         } finally {
>             bossGroup.shutdownGracefully();
>             workerGroup.shutdownGracefully();
>         }
>     }
> 
>     public static void main(String[] args) throws Exception {
>         new GroupChatServer(7000).run();
>     }
> }
> ```
>
> ```java
> package com.example.groupchat;
> import io.netty.channel.Channel;
> import io.netty.channel.ChannelHandlerContext;
> import io.netty.channel.SimpleChannelInboundHandler;
> import io.netty.channel.group.ChannelGroup;
> import io.netty.channel.group.DefaultChannelGroup;
> import io.netty.util.concurrent.GlobalEventExecutor;
> import java.text.SimpleDateFormat;
> import java.util.Date;
> 
> public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {
> 
>     /**
>      *  定义一个channel组，管理所有的channel
>      *  GlobalEventExecutor.INSTANCE是全局的事件执行器，是一个单例
>      */
>     private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
>     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
> 
>     /**
>      *  handlerAdded 表示连接建立，一旦连接，第一个被执行
>      *  将当前channel加入到channelGroup
>      */
>     @Override
>     public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
>         Channel channel = ctx.channel();
>         // 将该客户加入聊天的信息推送给其他客户端
>         // 该方法会将channelGroup中所有的channel遍历，并发送消息
>         channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 加入聊天" + sdf.format(new Date()) + "\n");
>         channelGroup.add(channel);
>     }
> 
>     /**
>      * 断开连接，将xx离开信息推送给当前在线的客户端
>      */
>     @Override
>     public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
>         Channel channel = ctx.channel();
>         channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 离开了\n");
>         System.out.println("channelGroup size" + channelGroup.size());
>     }
> 
>     /**
>      * 表示channel处于活动状态，提示xx上线
>      */
>     @Override
>     public void channelActive(ChannelHandlerContext ctx) throws Exception {
>         System.out.println(ctx.channel().remoteAddress() + " 上线了~");
>     }
> 
>     /**
>      * 表示channel处于不活动状态，提示xx离线了
>      */
>     @Override
>     public void channelInactive(ChannelHandlerContext ctx) throws Exception {
>         System.out.println(ctx.channel().remoteAddress() + " 离线了~");
>     }
> 
>     @Override
>     protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
>         Channel channel = ctx.channel();
>         // 这时我们遍历channelGroup，根据不同的情况，回送不同的消息
>         channelGroup.forEach(ch -> {
>             if(channel != ch){  // 不是当前的channel，转发消息
>                 ch.writeAndFlush("[客户端]" + channel.remoteAddress() + " 发送消息 ：" + msg + "\n");
>             }else{
>                 ch.writeAndFlush("[自己]发送了消息：" + msg + "\n");
>             }
>         });
>     }
> 
>     /**
>      * 异常处理
>      */
>     @Override
>     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
>         // 关闭通道
>         ctx.close();
>     }
> }
> ```
>
> ```java
> package com.example.groupchat;
> import io.netty.bootstrap.Bootstrap;
> import io.netty.channel.Channel;
> import io.netty.channel.ChannelFuture;
> import io.netty.channel.ChannelInitializer;
> import io.netty.channel.ChannelPipeline;
> import io.netty.channel.nio.NioEventLoopGroup;
> import io.netty.channel.socket.SocketChannel;
> import io.netty.channel.socket.nio.NioSocketChannel;
> import io.netty.handler.codec.string.StringDecoder;
> import io.netty.handler.codec.string.StringEncoder;
> import java.util.Scanner;
> 
> public class GroupChatClient {
>     private final String host;
>     private final int port;
> 
>     public GroupChatClient(String host, int port) {
>         this.host = host;
>         this.port = port;
>     }
> 
>     public void run() throws Exception {
>         NioEventLoopGroup group = new NioEventLoopGroup();
>         try {
>             Bootstrap bootstrap = new Bootstrap()
>                     .group(group)
>                     .channel(NioSocketChannel.class)
>                     .handler(new ChannelInitializer<SocketChannel>() {
>                         @Override
>                         protected void initChannel(SocketChannel ch) throws Exception {
>                             // 得到pipeline
>                             ChannelPipeline pipeline = ch.pipeline();
>                             // 加入相关的handler
>                             pipeline.addLast("decoder",new StringDecoder());
>                             pipeline.addLast("encoder",new StringEncoder());
>                             pipeline.addLast(new GroupChatClientHandler());
>                         }
>                     });
>             ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
>             // 得到channel
>             Channel channel = channelFuture.channel();
>             System.out.println("------------- " + channel.localAddress() + " ------------------");
>             // 客户端需要输入信息，创建一个扫描器
>             Scanner scanner = new Scanner(System.in);
>             while(scanner.hasNextLine()){
>                 String msg = scanner.nextLine();
>                 // 通过channel发送到服务器端
>                 channel.writeAndFlush(msg + "\r\n");
>             }
>         } finally {
>             group.shutdownGracefully();
>         }
>     }
> 
>     public static void main(String[] args) throws Exception {
>         new GroupChatClient("127.0.0.1", 7000).run();
>     }
> }
> ```
>
> ```java
> package com.example.groupchat;
> import io.netty.channel.ChannelHandlerContext;
> import io.netty.channel.SimpleChannelInboundHandler;
> 
> public class GroupChatClientHandler extends SimpleChannelInboundHandler<String> {
>     @Override
>     protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
>         System.out.println(msg.trim());
>     }
> }
> ```

### Netty心跳处理

> ```java
> package com.example.heartbeat;
> import io.netty.bootstrap.ServerBootstrap;
> import io.netty.channel.ChannelFuture;
> import io.netty.channel.ChannelInitializer;
> import io.netty.channel.ChannelPipeline;
> import io.netty.channel.nio.NioEventLoopGroup;
> import io.netty.channel.socket.SocketChannel;
> import io.netty.channel.socket.nio.NioServerSocketChannel;
> import io.netty.handler.logging.LogLevel;
> import io.netty.handler.logging.LoggingHandler;
> import io.netty.handler.timeout.IdleStateHandler;
> import java.util.concurrent.TimeUnit;
> 
> public class MyServer {
>     public static void main(String[] args) throws Exception {
>         // 创建两个线程组
>         NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
>         NioEventLoopGroup workerGroup = new NioEventLoopGroup();
>         try {
>             ServerBootstrap serverBootstrap = new ServerBootstrap();
>             serverBootstrap.group(bossGroup, workerGroup)
>                            .channel(NioServerSocketChannel.class)
>                            // 在bossGroup增加一个日志处理器
>                            .handler(new LoggingHandler(LogLevel.INFO))
>                            .childHandler(new ChannelInitializer<SocketChannel>() {
>                                @Override
>                                protected void initChannel(SocketChannel ch) throws Exception {
>                                    ChannelPipeline pipeline = ch.pipeline();
>                                    // 加入一个netty提供的IdleStateHandler
>                                    // long readerIdleTime：表示server多长时间没有读操作，就会发送一个心跳检测包检测是否还是连接状态
>                                    // long writerIdleTime：表示server多长时间没有写操作，也会发送一个心跳检测包
>                                    // long allIdleTime：表示server多长时间既没有读也没有写
>                                    // 当IdleStateEvent触发后，就会传递给管道的下一个handler去处理,通过触发下一个handler的userEventTriggered，在该方法中取处理IdleStateEvent
>                                    pipeline.addLast(new IdleStateHandler(3,5,7,TimeUnit.SECONDS));
>                                    pipeline.addLast(new MyServerHandler());
>                                }
>                            });
>             ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
>             channelFuture.channel().closeFuture().sync();
>         } finally {
>             bossGroup.shutdownGracefully();
>             workerGroup.shutdownGracefully();
>         }
>     }
> }
> ```
>
> ```java
> package com.example.heartbeat;
> import io.netty.channel.ChannelHandlerContext;
> import io.netty.channel.ChannelInboundHandlerAdapter;
> import io.netty.handler.timeout.IdleStateEvent;
> 
> public class MyServerHandler extends ChannelInboundHandlerAdapter {
>     /**
>      * @param ctx  上下文
>      * @param evt  事件
>      * @throws Exception
>      */
>     @Override
>     public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
>         if(evt instanceof IdleStateEvent){
>             // 将evt向下转型IdleStateEvent
>             IdleStateEvent event = (IdleStateEvent) evt;
>             String eventType = null;
>             switch (event.state()){
>                 case READER_IDLE:
>                     eventType = "读空闲";
>                     break;
>                 case WRITER_IDLE:
>                     eventType = "写空闲";
>                     break;
>                 case ALL_IDLE:
>                     eventType = "读写空闲";
>                     break;
>             }
>             System.out.println(ctx.channel().remoteAddress() + "超时事件发生：" + eventType);
>         }
>     }
> }
> ```

### Websocket

> ```java
> package com.example.websocket;
> import com.example.heartbeat.MyTextWebSocketFrameHandler;
> import io.netty.bootstrap.ServerBootstrap;
> import io.netty.channel.ChannelFuture;
> import io.netty.channel.ChannelInitializer;
> import io.netty.channel.ChannelPipeline;
> import io.netty.channel.nio.NioEventLoopGroup;
> import io.netty.channel.socket.SocketChannel;
> import io.netty.channel.socket.nio.NioServerSocketChannel;
> import io.netty.handler.codec.http.HttpObjectAggregator;
> import io.netty.handler.codec.http.HttpServerCodec;
> import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
> import io.netty.handler.logging.LogLevel;
> import io.netty.handler.logging.LoggingHandler;
> import io.netty.handler.stream.ChunkedWriteHandler;
> 
> public class MyServer {
> 
>     public static void main(String[] args) throws Exception {
>         // 创建两个线程组
>         NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
>         NioEventLoopGroup workerGroup = new NioEventLoopGroup();
>         try {
>             ServerBootstrap serverBootstrap = new ServerBootstrap();
>             serverBootstrap.group(bossGroup, workerGroup)
>                     .channel(NioServerSocketChannel.class)
>                     // 在bossGroup增加一个日志处理器
>                     .handler(new LoggingHandler(LogLevel.INFO))
>                     .childHandler(new ChannelInitializer<SocketChannel>() {
>                         @Override
>                         protected void initChannel(SocketChannel ch) throws Exception {
>                             ChannelPipeline pipeline = ch.pipeline();
>                             // 因为基于http协议，使用http的编码和解码器
>                             pipeline.addLast(new HttpServerCodec());
>                             // 是以块方式写，添加ChunkedWrite处理器
>                             pipeline.addLast(new ChunkedWriteHandler());
>                             // http在传输过程中是分段的，HttpObjectAggregator就是可以将多个段聚合起来
>                             pipeline.addLast(new HttpObjectAggregator(8192));
>                             // 对于websocket，它的数据是以帧的形式传递的，WebSocketServerProtocolHandler会把http协议升级为ws协议
>                             pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
>                             // 自定义的handler，处理业务逻辑,
>                             pipeline.addLast(new MyTextWebSocketFrameHandler());
>                         }
>                     });
>             ChannelFuture channelFuture = serverBootstrap.bind(7000).sync();
>             channelFuture.channel().closeFuture().sync();
>         } finally {
>             bossGroup.shutdownGracefully();
>             workerGroup.shutdownGracefully();
>         }
>     }
> }
> ```
>
> ```java
> package com.example.heartbeat;
> import io.netty.channel.ChannelHandlerContext;
> import io.netty.channel.SimpleChannelInboundHandler;
> import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
> import java.time.LocalDateTime;
> 
> /**
>  *  TextWebSocketFrame表示一个文本帧
>  */
> public class MyTextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
>     @Override
>     protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
>         System.out.println("服务器端收到消息 ：" + msg.text());
>         // 回复消息
>         ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间：" + LocalDateTime.now() + msg.text()));
>     }
> 
>     /**
>      * 当web客户端连接后，触发方法
>      */
>     @Override
>     public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
>         // id表示唯一的值，LongText是唯一的，ShortText不是唯一的
>         System.out.println("handlerAdded被调用..." + ctx.channel().id().asLongText());
>     }
> 
>     /**
>      *
>      */
>     @Override
>     public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
>         System.out.println("handlerRemoved被调用..." + ctx.channel().id().asLongText());
>     }
> 
>     @Override
>     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
>         System.out.println("异常发生 " + cause.getMessage());
>     }
> }
> ```
>
> ```html
> <!DOCTYPE html>
> <html lang="en">
> <head>
>     <meta charset="UTF-8">
>     <title>Title</title>
> </head>
> <body>
> <script>
>     var socket;
>     // 判断当前浏览器是否支持websocket
>     if(window.WebSocket){
>         socket = new WebSocket("ws://localhost:7000/hello")
>         socket.onmessage = function (ev) {
>             var rt = document.getElementById("responseText")
>             rt.value = rt.value + "\n" + ev.data;
>         }
> 
>         socket.onopen = function (ev) {
>             var rt = document.getElementById("responseText")
>             rt.value = "连接开启...";
>         }
> 
>         socket.onclose = function (ev) {
>             var rt = document.getElementById("responseText")
>             rt.value = rt.value + "\n" + "连接关闭...";
>         }
>     }else{
>         alert("当前浏览器不支持websocket")
>     }
> 
>     function send(message){
>         if(!window.WebSocket){
>             return;
>         }
>         if(socket.readyState == WebSocket.OPEN){
>             // 通过socket发送消息
>             socket.send(message);
>         }else{
>             alert("连接未开启!");
>         }
>     }
> </script>
>     <form onsubmit="return false">
>         <textarea name="message" style="height: 300px;width: 300px;"></textarea>
>         <input type="button" value="发送" onclick="send(this.form.message.value)">
>         <textarea id="responseText" style="height: 300px;width: 300px;"></textarea>
>         <input type="button" value="清空内容" onclick="document.getElementById('responseText').value = ''">
>     </form>
> </body>
> </html>
> ```

### ProtoBuf

> Student.proto
>
> ```protobuf
> syntax = "proto3"; // 协议版本
> option java_outer_classname = "StudentPOJO"; // 生成的外部类名，同时也是文件名
> message Student { // 会在StudentPOJO外部类生成一个内部类Student，他是真正发送的POJO对象
>     int32 id = 1; // Student类中有一个属性，名字为id，类型为int32(protobuf类型)，1表示属性序号，不是值
>     string name = 2;
> }
> ```
>
> ```xml
> <?xml version="1.0" encoding="UTF-8"?>
> <project xmlns="http://maven.apache.org/POM/4.0.0"
>          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
>          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
>     <modelVersion>4.0.0</modelVersion>
> 
>     <groupId>com.example</groupId>
>     <artifactId>nettydemo</artifactId>
>     <version>1.0-SNAPSHOT</version>
>     <build>
>         <plugins>
>             <plugin>
>                 <groupId>org.apache.maven.plugins</groupId>
>                 <artifactId>maven-compiler-plugin</artifactId>
>                 <configuration>
>                     <source>7</source>
>                     <target>7</target>
>                 </configuration>
>             </plugin>
>         </plugins>
>     </build>
> 
>     <dependencies>
>         <!-- https://mvnrepository.com/artifact/io.netty/netty-all -->
>         <dependency>
>             <groupId>io.netty</groupId>
>             <artifactId>netty-all</artifactId>
>             <version>4.1.67.Final</version>
>         </dependency>
>         <dependency>
>             <groupId>junit</groupId>
>             <artifactId>junit</artifactId>
>             <version>4.13</version>
>             <scope>test</scope>
>         </dependency>
>         <!-- https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java -->
>         <dependency>
>             <groupId>com.google.protobuf</groupId>
>             <artifactId>protobuf-java</artifactId>
>             <version>3.19.1</version>
>         </dependency>
>     </dependencies>
> </project>
> ```
>
> ```java
> package com.example.netty;
> import io.netty.bootstrap.Bootstrap;
> import io.netty.channel.ChannelFuture;
> import io.netty.channel.ChannelInitializer;
> import io.netty.channel.ChannelPipeline;
> import io.netty.channel.EventLoopGroup;
> import io.netty.channel.nio.NioEventLoopGroup;
> import io.netty.channel.socket.SocketChannel;
> import io.netty.channel.socket.nio.NioSocketChannel;
> import io.netty.handler.codec.protobuf.ProtobufEncoder;
> 
> public class NettyClient {
>     public static void main(String[] args) throws InterruptedException {
>         // 客户端需要一个事件循环组
>         EventLoopGroup group = new NioEventLoopGroup();
>         try {
>             // 创建客户端启动对象
>             Bootstrap bootstrap = new Bootstrap();
>             // 设置相关参数
>             // 设置线程组
>             bootstrap.group(group)
>                      // 设置客户端通道的实现类
>                      .channel(NioSocketChannel.class)
>                      .handler(new ChannelInitializer<SocketChannel>() {
>                          @Override
>                          protected void initChannel(SocketChannel ch) {
>                              // 加入自己的处理器
>                              ChannelPipeline pipeline = ch.pipeline();
>                              pipeline.addLast("encoder",new ProtobufEncoder());
>                              pipeline.addLast(new NettyClientHandler());
>                          }
>                      });
>             System.out.println("客户端 is OK ...");
>             // 启动客户端去连接服务端
>             ChannelFuture cf = bootstrap.connect("127.0.0.1", 6668).sync();
>             // 监听关闭通道事件
>             cf.channel().closeFuture().sync();
>         } catch (InterruptedException e) {
>             e.printStackTrace();
>         } finally {
>             group.shutdownGracefully();
>         }
> 
>     }
> }
> ```
>
> ```java
> package com.example.netty;
> import io.netty.buffer.ByteBuf;
> import io.netty.buffer.Unpooled;
> import io.netty.channel.ChannelHandlerContext;
> import io.netty.channel.ChannelInboundHandlerAdapter;
> import io.netty.util.CharsetUtil;
> 
> public class NettyClientHandler extends ChannelInboundHandlerAdapter {
> 
>     /**
>      *  当通道就绪就会触发该方法
>      */
>     @Override
>     public void channelActive(ChannelHandlerContext ctx) throws Exception {
>         // 发送一个Student对象到服务器
>         StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(3).setName("轻狂书生").build();
>         ctx.writeAndFlush(student);
> //        System.out.println("Client ctx : " + ctx);
> //        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,server!",CharsetUtil.UTF_8));
>     }
> 
>     /**
>      * 当通道有读取事件时，会触发
>      */
>     @Override
>     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
>         ByteBuf buf = (ByteBuf) msg;
>         System.out.println("服务器回复的消息： " + buf.toString(CharsetUtil.UTF_8));
>         System.out.println("服务器地址： " + ctx.channel().remoteAddress());
>     }
> 
>     /**
>      * 异常处理
>      */
>     @Override
>     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
>         cause.printStackTrace();
>         ctx.close();
>     }
> }
> 
> ```
>
> ```java
> package com.example.netty;
> import io.netty.bootstrap.ServerBootstrap;
> import io.netty.channel.*;
> import io.netty.channel.nio.NioEventLoopGroup;
> import io.netty.channel.socket.SocketChannel;
> import io.netty.channel.socket.nio.NioServerSocketChannel;
> import io.netty.handler.codec.protobuf.ProtobufDecoder;
> 
> public class NettyServer {
>     public static void main(String[] args) throws InterruptedException {
>         // 创建 BossGroup 和 WorkerGroup
>         // 说明：
>         // 1. 创建两个线程组 bossGroup 和 workerGroup
>         // 2. bossGroup只是处理连接请求，真正的业务处理，会交给workerGroup处理
>         // 3. 两个都是无限循环
>         // bossGroup 和 workerGroup 含有的子线程（NioEventLoop）的个数，默认是实际的CPU核数 * 2
>         EventLoopGroup bossGroup = new NioEventLoopGroup(1);
>         EventLoopGroup workerGroup = new NioEventLoopGroup(3);
>         try {
>             // 创建服务端的启动对象，配置参数
>             ServerBootstrap bootstrap = new ServerBootstrap();
>             // 使用链式编程来进行设置
>             // 这是设置两个线程组
>             bootstrap.group(bossGroup,workerGroup)
>                       // 使用NioSocketChannel作为服务器的通道实现
>                      .channel(NioServerSocketChannel.class)
>                       // 设置线程队列等待连接的个数
>                      .option(ChannelOption.SO_BACKLOG, 128)
>                        // 设置保持活动连接状态
>                      .childOption(ChannelOption.SO_KEEPALIVE, true)
>                       // 创建一个通道初始化对象
>                      .childHandler(new ChannelInitializer<SocketChannel>() {
>                          // 给pipeline设置处理器
>                          @Override
>                          protected void initChannel(SocketChannel ch) {
>                              ChannelPipeline pipeline = ch.pipeline();
>                              // 指定对哪种对象进行解码
>                              pipeline.addLast("decoder",new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
>                              pipeline.addLast(new NettyServerHandler());
>                          }
>                      }); // 给workerGroup的EventLoop对应的管道设置处理器
> 
>             System.out.println("...服务器 is ready ...");
>             // 绑定一个端口，并且同步,生成一个ChannelFuture对象（这里相当于启动服务器，并绑定端口）
>             ChannelFuture cf = bootstrap.bind(6668).sync();
>             // 给cf注册监听器，监听我们关心的事件
>             cf.addListener(new ChannelFutureListener() {
>                 @Override
>                 public void operationComplete(ChannelFuture future) throws Exception {
>                     if(cf.isSuccess()){
>                         System.out.println("监听端口6668成功！");
>                     }else{
>                         System.out.println("监听端口失败！");
>                     }
>                 }
>             });
> 
>             // 对关闭通道事件进行监听
>             cf.channel().closeFuture().sync();
>         } catch (InterruptedException e) {
>             e.printStackTrace();
>         }finally {
>             bossGroup.shutdownGracefully();
>             workerGroup.shutdownGracefully();
>         }
>     }
> }
> 
> ```
>
> ```java
> package com.example.netty;
> import io.netty.buffer.Unpooled;
> import io.netty.channel.ChannelHandlerContext;
> import io.netty.channel.ChannelInboundHandlerAdapter;
> import io.netty.util.CharsetUtil;
> import java.util.concurrent.TimeUnit;
> 
> /**
>  *  1. 我们自定义一个Handler，需要继承netty规定好的某个HandlerAdapter
>  *  2. 这时我们自定义的Handler，才能称之为一个Handler
>  */
> public class NettyServerHandler extends ChannelInboundHandlerAdapter {
> 
>     @Override
>     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
>         // 读取从客户端发送的StudentPOJO.Student
>         StudentPOJO.Student student = (StudentPOJO.Student) msg;
>         System.out.println("客户端发送的数据：id = " + student.getId() + " name = " + student.getName());
>     }
> 
>     /**
>      * 数据读取完毕
>      */
>     @Override
>     public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
>         // 将数据写入到缓存并刷新，通常我们要对发送的数据进行编码
>         ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,客户端，喵喵喵222...",CharsetUtil.UTF_8));
>     }
> 
>     /**
>      * 异常处理
>      */
>     @Override
>     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
>         ctx.close();
>     }
> }
> 
> ```
>
> StudentPOJO.java  这个是由protoBuf程序自动生成的java文件，不要进行编辑
>
> ```java
> package com.example.netty;// Generated by the protocol buffer compiler.  DO NOT EDIT!
> // source: Student.proto
> 
> public final class StudentPOJO {
>   private StudentPOJO() {}
>   public static void registerAllExtensions(
>       com.google.protobuf.ExtensionRegistryLite registry) {
>   }
> 
>   public static void registerAllExtensions(
>       com.google.protobuf.ExtensionRegistry registry) {
>     registerAllExtensions(
>         (com.google.protobuf.ExtensionRegistryLite) registry);
>   }
>   public interface StudentOrBuilder extends
>       // @@protoc_insertion_point(interface_extends:Student)
>       com.google.protobuf.MessageOrBuilder {
> 
>     /**
>      * <pre>
>      * Student类中有一个属性，名字为id，类型为int32(protobuf类型)，1表示属性序号，不是值
>      * </pre>
>      *
>      * <code>int32 id = 1;</code>
>      */
>     int getId();
> 
>     /**
>      * <code>string name = 2;</code>
>      */
>     String getName();
>     /**
>      * <code>string name = 2;</code>
>      */
>     com.google.protobuf.ByteString
>         getNameBytes();
>   }
>   /**
>    * <pre>
>    * 会在StudentPOJO外部类生成一个内部类Student，他是真正发送的POJO对象
>    * </pre>
>    *
>    * Protobuf type {@code Student}
>    */
>   public  static final class Student extends
>       com.google.protobuf.GeneratedMessageV3 implements
>       // @@protoc_insertion_point(message_implements:Student)
>       StudentOrBuilder {
>   private static final long serialVersionUID = 0L;
>     // Use Student.newBuilder() to construct.
>     private Student(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
>       super(builder);
>     }
>     private Student() {
>       id_ = 0;
>       name_ = "";
>     }
> 
>     @Override
>     public final com.google.protobuf.UnknownFieldSet
>     getUnknownFields() {
>       return this.unknownFields;
>     }
>     private Student(
>         com.google.protobuf.CodedInputStream input,
>         com.google.protobuf.ExtensionRegistryLite extensionRegistry)
>         throws com.google.protobuf.InvalidProtocolBufferException {
>       this();
>       if (extensionRegistry == null) {
>         throw new NullPointerException();
>       }
>       int mutable_bitField0_ = 0;
>       com.google.protobuf.UnknownFieldSet.Builder unknownFields =
>           com.google.protobuf.UnknownFieldSet.newBuilder();
>       try {
>         boolean done = false;
>         while (!done) {
>           int tag = input.readTag();
>           switch (tag) {
>             case 0:
>               done = true;
>               break;
>             case 8: {
> 
>               id_ = input.readInt32();
>               break;
>             }
>             case 18: {
>               String s = input.readStringRequireUtf8();
> 
>               name_ = s;
>               break;
>             }
>             default: {
>               if (!parseUnknownFieldProto3(
>                   input, unknownFields, extensionRegistry, tag)) {
>                 done = true;
>               }
>               break;
>             }
>           }
>         }
>       } catch (com.google.protobuf.InvalidProtocolBufferException e) {
>         throw e.setUnfinishedMessage(this);
>       } catch (java.io.IOException e) {
>         throw new com.google.protobuf.InvalidProtocolBufferException(
>             e).setUnfinishedMessage(this);
>       } finally {
>         this.unknownFields = unknownFields.build();
>         makeExtensionsImmutable();
>       }
>     }
>     public static final com.google.protobuf.Descriptors.Descriptor
>         getDescriptor() {
>       return StudentPOJO.internal_static_Student_descriptor;
>     }
> 
>     @Override
>     protected FieldAccessorTable
>         internalGetFieldAccessorTable() {
>       return StudentPOJO.internal_static_Student_fieldAccessorTable
>           .ensureFieldAccessorsInitialized(
>               Student.class, Builder.class);
>     }
> 
>     public static final int ID_FIELD_NUMBER = 1;
>     private int id_;
>     /**
>      * <pre>
>      * Student类中有一个属性，名字为id，类型为int32(protobuf类型)，1表示属性序号，不是值
>      * </pre>
>      *
>      * <code>int32 id = 1;</code>
>      */
>     public int getId() {
>       return id_;
>     }
> 
>     public static final int NAME_FIELD_NUMBER = 2;
>     private volatile Object name_;
>     /**
>      * <code>string name = 2;</code>
>      */
>     public String getName() {
>       Object ref = name_;
>       if (ref instanceof String) {
>         return (String) ref;
>       } else {
>         com.google.protobuf.ByteString bs = 
>             (com.google.protobuf.ByteString) ref;
>         String s = bs.toStringUtf8();
>         name_ = s;
>         return s;
>       }
>     }
>     /**
>      * <code>string name = 2;</code>
>      */
>     public com.google.protobuf.ByteString
>         getNameBytes() {
>       Object ref = name_;
>       if (ref instanceof String) {
>         com.google.protobuf.ByteString b = 
>             com.google.protobuf.ByteString.copyFromUtf8(
>                 (String) ref);
>         name_ = b;
>         return b;
>       } else {
>         return (com.google.protobuf.ByteString) ref;
>       }
>     }
> 
>     private byte memoizedIsInitialized = -1;
>     @Override
>     public final boolean isInitialized() {
>       byte isInitialized = memoizedIsInitialized;
>       if (isInitialized == 1) return true;
>       if (isInitialized == 0) return false;
> 
>       memoizedIsInitialized = 1;
>       return true;
>     }
> 
>     @Override
>     public void writeTo(com.google.protobuf.CodedOutputStream output)
>                         throws java.io.IOException {
>       if (id_ != 0) {
>         output.writeInt32(1, id_);
>       }
>       if (!getNameBytes().isEmpty()) {
>         com.google.protobuf.GeneratedMessageV3.writeString(output, 2, name_);
>       }
>       unknownFields.writeTo(output);
>     }
> 
>     @Override
>     public int getSerializedSize() {
>       int size = memoizedSize;
>       if (size != -1) return size;
> 
>       size = 0;
>       if (id_ != 0) {
>         size += com.google.protobuf.CodedOutputStream
>           .computeInt32Size(1, id_);
>       }
>       if (!getNameBytes().isEmpty()) {
>         size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, name_);
>       }
>       size += unknownFields.getSerializedSize();
>       memoizedSize = size;
>       return size;
>     }
> 
>     @Override
>     public boolean equals(final Object obj) {
>       if (obj == this) {
>        return true;
>       }
>       if (!(obj instanceof Student)) {
>         return super.equals(obj);
>       }
>       Student other = (Student) obj;
> 
>       boolean result = true;
>       result = result && (getId()
>           == other.getId());
>       result = result && getName()
>           .equals(other.getName());
>       result = result && unknownFields.equals(other.unknownFields);
>       return result;
>     }
> 
>     @Override
>     public int hashCode() {
>       if (memoizedHashCode != 0) {
>         return memoizedHashCode;
>       }
>       int hash = 41;
>       hash = (19 * hash) + getDescriptor().hashCode();
>       hash = (37 * hash) + ID_FIELD_NUMBER;
>       hash = (53 * hash) + getId();
>       hash = (37 * hash) + NAME_FIELD_NUMBER;
>       hash = (53 * hash) + getName().hashCode();
>       hash = (29 * hash) + unknownFields.hashCode();
>       memoizedHashCode = hash;
>       return hash;
>     }
> 
>     public static Student parseFrom(
>         java.nio.ByteBuffer data)
>         throws com.google.protobuf.InvalidProtocolBufferException {
>       return PARSER.parseFrom(data);
>     }
>     public static Student parseFrom(
>         java.nio.ByteBuffer data,
>         com.google.protobuf.ExtensionRegistryLite extensionRegistry)
>         throws com.google.protobuf.InvalidProtocolBufferException {
>       return PARSER.parseFrom(data, extensionRegistry);
>     }
>     public static Student parseFrom(
>         com.google.protobuf.ByteString data)
>         throws com.google.protobuf.InvalidProtocolBufferException {
>       return PARSER.parseFrom(data);
>     }
>     public static Student parseFrom(
>         com.google.protobuf.ByteString data,
>         com.google.protobuf.ExtensionRegistryLite extensionRegistry)
>         throws com.google.protobuf.InvalidProtocolBufferException {
>       return PARSER.parseFrom(data, extensionRegistry);
>     }
>     public static Student parseFrom(byte[] data)
>         throws com.google.protobuf.InvalidProtocolBufferException {
>       return PARSER.parseFrom(data);
>     }
>     public static Student parseFrom(
>         byte[] data,
>         com.google.protobuf.ExtensionRegistryLite extensionRegistry)
>         throws com.google.protobuf.InvalidProtocolBufferException {
>       return PARSER.parseFrom(data, extensionRegistry);
>     }
>     public static Student parseFrom(java.io.InputStream input)
>         throws java.io.IOException {
>       return com.google.protobuf.GeneratedMessageV3
>           .parseWithIOException(PARSER, input);
>     }
>     public static Student parseFrom(
>         java.io.InputStream input,
>         com.google.protobuf.ExtensionRegistryLite extensionRegistry)
>         throws java.io.IOException {
>       return com.google.protobuf.GeneratedMessageV3
>           .parseWithIOException(PARSER, input, extensionRegistry);
>     }
>     public static Student parseDelimitedFrom(java.io.InputStream input)
>         throws java.io.IOException {
>       return com.google.protobuf.GeneratedMessageV3
>           .parseDelimitedWithIOException(PARSER, input);
>     }
>     public static Student parseDelimitedFrom(
>         java.io.InputStream input,
>         com.google.protobuf.ExtensionRegistryLite extensionRegistry)
>         throws java.io.IOException {
>       return com.google.protobuf.GeneratedMessageV3
>           .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
>     }
>     public static Student parseFrom(
>         com.google.protobuf.CodedInputStream input)
>         throws java.io.IOException {
>       return com.google.protobuf.GeneratedMessageV3
>           .parseWithIOException(PARSER, input);
>     }
>     public static Student parseFrom(
>         com.google.protobuf.CodedInputStream input,
>         com.google.protobuf.ExtensionRegistryLite extensionRegistry)
>         throws java.io.IOException {
>       return com.google.protobuf.GeneratedMessageV3
>           .parseWithIOException(PARSER, input, extensionRegistry);
>     }
> 
>     @Override
>     public Builder newBuilderForType() { return newBuilder(); }
>     public static Builder newBuilder() {
>       return DEFAULT_INSTANCE.toBuilder();
>     }
>     public static Builder newBuilder(Student prototype) {
>       return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
>     }
>     @Override
>     public Builder toBuilder() {
>       return this == DEFAULT_INSTANCE
>           ? new Builder() : new Builder().mergeFrom(this);
>     }
> 
>     @Override
>     protected Builder newBuilderForType(
>         BuilderParent parent) {
>       Builder builder = new Builder(parent);
>       return builder;
>     }
>     /**
>      * <pre>
>      * 会在StudentPOJO外部类生成一个内部类Student，他是真正发送的POJO对象
>      * </pre>
>      *
>      * Protobuf type {@code Student}
>      */
>     public static final class Builder extends
>         com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
>         // @@protoc_insertion_point(builder_implements:Student)
>         StudentOrBuilder {
>       public static final com.google.protobuf.Descriptors.Descriptor
>           getDescriptor() {
>         return StudentPOJO.internal_static_Student_descriptor;
>       }
> 
>       @Override
>       protected FieldAccessorTable
>           internalGetFieldAccessorTable() {
>         return StudentPOJO.internal_static_Student_fieldAccessorTable
>             .ensureFieldAccessorsInitialized(
>                 Student.class, Builder.class);
>       }
> 
>       // Construct using StudentPOJO.Student.newBuilder()
>       private Builder() {
>         maybeForceBuilderInitialization();
>       }
> 
>       private Builder(
>           BuilderParent parent) {
>         super(parent);
>         maybeForceBuilderInitialization();
>       }
>       private void maybeForceBuilderInitialization() {
>         if (com.google.protobuf.GeneratedMessageV3
>                 .alwaysUseFieldBuilders) {
>         }
>       }
>       @Override
>       public Builder clear() {
>         super.clear();
>         id_ = 0;
> 
>         name_ = "";
> 
>         return this;
>       }
> 
>       @Override
>       public com.google.protobuf.Descriptors.Descriptor
>           getDescriptorForType() {
>         return StudentPOJO.internal_static_Student_descriptor;
>       }
> 
>       @Override
>       public Student getDefaultInstanceForType() {
>         return Student.getDefaultInstance();
>       }
> 
>       @Override
>       public Student build() {
>         Student result = buildPartial();
>         if (!result.isInitialized()) {
>           throw newUninitializedMessageException(result);
>         }
>         return result;
>       }
> 
>       @Override
>       public Student buildPartial() {
>         Student result = new Student(this);
>         result.id_ = id_;
>         result.name_ = name_;
>         onBuilt();
>         return result;
>       }
> 
>       @Override
>       public Builder clone() {
>         return (Builder) super.clone();
>       }
>       @Override
>       public Builder setField(
>           com.google.protobuf.Descriptors.FieldDescriptor field,
>           Object value) {
>         return (Builder) super.setField(field, value);
>       }
>       @Override
>       public Builder clearField(
>           com.google.protobuf.Descriptors.FieldDescriptor field) {
>         return (Builder) super.clearField(field);
>       }
>       @Override
>       public Builder clearOneof(
>           com.google.protobuf.Descriptors.OneofDescriptor oneof) {
>         return (Builder) super.clearOneof(oneof);
>       }
>       @Override
>       public Builder setRepeatedField(
>           com.google.protobuf.Descriptors.FieldDescriptor field,
>           int index, Object value) {
>         return (Builder) super.setRepeatedField(field, index, value);
>       }
>       @Override
>       public Builder addRepeatedField(
>           com.google.protobuf.Descriptors.FieldDescriptor field,
>           Object value) {
>         return (Builder) super.addRepeatedField(field, value);
>       }
>       @Override
>       public Builder mergeFrom(com.google.protobuf.Message other) {
>         if (other instanceof Student) {
>           return mergeFrom((Student)other);
>         } else {
>           super.mergeFrom(other);
>           return this;
>         }
>       }
> 
>       public Builder mergeFrom(Student other) {
>         if (other == Student.getDefaultInstance()) return this;
>         if (other.getId() != 0) {
>           setId(other.getId());
>         }
>         if (!other.getName().isEmpty()) {
>           name_ = other.name_;
>           onChanged();
>         }
>         this.mergeUnknownFields(other.unknownFields);
>         onChanged();
>         return this;
>       }
> 
>       @Override
>       public final boolean isInitialized() {
>         return true;
>       }
> 
>       @Override
>       public Builder mergeFrom(
>           com.google.protobuf.CodedInputStream input,
>           com.google.protobuf.ExtensionRegistryLite extensionRegistry)
>           throws java.io.IOException {
>         Student parsedMessage = null;
>         try {
>           parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
>         } catch (com.google.protobuf.InvalidProtocolBufferException e) {
>           parsedMessage = (Student) e.getUnfinishedMessage();
>           throw e.unwrapIOException();
>         } finally {
>           if (parsedMessage != null) {
>             mergeFrom(parsedMessage);
>           }
>         }
>         return this;
>       }
> 
>       private int id_ ;
>       /**
>        * <pre>
>        * Student类中有一个属性，名字为id，类型为int32(protobuf类型)，1表示属性序号，不是值
>        * </pre>
>        *
>        * <code>int32 id = 1;</code>
>        */
>       public int getId() {
>         return id_;
>       }
>       /**
>        * <pre>
>        * Student类中有一个属性，名字为id，类型为int32(protobuf类型)，1表示属性序号，不是值
>        * </pre>
>        *
>        * <code>int32 id = 1;</code>
>        */
>       public Builder setId(int value) {
>         
>         id_ = value;
>         onChanged();
>         return this;
>       }
>       /**
>        * <pre>
>        * Student类中有一个属性，名字为id，类型为int32(protobuf类型)，1表示属性序号，不是值
>        * </pre>
>        *
>        * <code>int32 id = 1;</code>
>        */
>       public Builder clearId() {
>         
>         id_ = 0;
>         onChanged();
>         return this;
>       }
> 
>       private Object name_ = "";
>       /**
>        * <code>string name = 2;</code>
>        */
>       public String getName() {
>         Object ref = name_;
>         if (!(ref instanceof String)) {
>           com.google.protobuf.ByteString bs =
>               (com.google.protobuf.ByteString) ref;
>           String s = bs.toStringUtf8();
>           name_ = s;
>           return s;
>         } else {
>           return (String) ref;
>         }
>       }
>       /**
>        * <code>string name = 2;</code>
>        */
>       public com.google.protobuf.ByteString
>           getNameBytes() {
>         Object ref = name_;
>         if (ref instanceof String) {
>           com.google.protobuf.ByteString b = 
>               com.google.protobuf.ByteString.copyFromUtf8(
>                   (String) ref);
>           name_ = b;
>           return b;
>         } else {
>           return (com.google.protobuf.ByteString) ref;
>         }
>       }
>       /**
>        * <code>string name = 2;</code>
>        */
>       public Builder setName(
>           String value) {
>         if (value == null) {
>     throw new NullPointerException();
>   }
>   
>         name_ = value;
>         onChanged();
>         return this;
>       }
>       /**
>        * <code>string name = 2;</code>
>        */
>       public Builder clearName() {
>         
>         name_ = getDefaultInstance().getName();
>         onChanged();
>         return this;
>       }
>       /**
>        * <code>string name = 2;</code>
>        */
>       public Builder setNameBytes(
>           com.google.protobuf.ByteString value) {
>         if (value == null) {
>     throw new NullPointerException();
>   }
>   checkByteStringIsUtf8(value);
>         
>         name_ = value;
>         onChanged();
>         return this;
>       }
>       @Override
>       public final Builder setUnknownFields(
>           final com.google.protobuf.UnknownFieldSet unknownFields) {
>         return super.setUnknownFieldsProto3(unknownFields);
>       }
> 
>       @Override
>       public final Builder mergeUnknownFields(
>           final com.google.protobuf.UnknownFieldSet unknownFields) {
>         return super.mergeUnknownFields(unknownFields);
>       }
> 
> 
>       // @@protoc_insertion_point(builder_scope:Student)
>     }
> 
>     // @@protoc_insertion_point(class_scope:Student)
>     private static final Student DEFAULT_INSTANCE;
>     static {
>       DEFAULT_INSTANCE = new Student();
>     }
> 
>     public static Student getDefaultInstance() {
>       return DEFAULT_INSTANCE;
>     }
> 
>     private static final com.google.protobuf.Parser<Student>
>         PARSER = new com.google.protobuf.AbstractParser<Student>() {
>       @Override
>       public Student parsePartialFrom(
>           com.google.protobuf.CodedInputStream input,
>           com.google.protobuf.ExtensionRegistryLite extensionRegistry)
>           throws com.google.protobuf.InvalidProtocolBufferException {
>         return new Student(input, extensionRegistry);
>       }
>     };
> 
>     public static com.google.protobuf.Parser<Student> parser() {
>       return PARSER;
>     }
> 
>     @Override
>     public com.google.protobuf.Parser<Student> getParserForType() {
>       return PARSER;
>     }
> 
>     @Override
>     public Student getDefaultInstanceForType() {
>       return DEFAULT_INSTANCE;
>     }
> 
>   }
> 
>   private static final com.google.protobuf.Descriptors.Descriptor
>     internal_static_Student_descriptor;
>   private static final 
>     com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
>       internal_static_Student_fieldAccessorTable;
> 
>   public static com.google.protobuf.Descriptors.FileDescriptor
>       getDescriptor() {
>     return descriptor;
>   }
>   private static  com.google.protobuf.Descriptors.FileDescriptor
>       descriptor;
>   static {
>     String[] descriptorData = {
>       "\n\rStudent.proto\"#\n\007Student\022\n\n\002id\030\001 \001(\005\022\014" +
>       "\n\004name\030\002 \001(\tB\rB\013StudentPOJOb\006proto3"
>     };
>     com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
>         new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
>           public com.google.protobuf.ExtensionRegistry assignDescriptors(
>               com.google.protobuf.Descriptors.FileDescriptor root) {
>             descriptor = root;
>             return null;
>           }
>         };
>     com.google.protobuf.Descriptors.FileDescriptor
>       .internalBuildGeneratedFileFrom(descriptorData,
>         new com.google.protobuf.Descriptors.FileDescriptor[] {
>         }, assigner);
>     internal_static_Student_descriptor =
>       getDescriptor().getMessageTypes().get(0);
>     internal_static_Student_fieldAccessorTable = new
>       com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
>         internal_static_Student_descriptor,
>         new String[] { "Id", "Name", });
>   }
> 
>   // @@protoc_insertion_point(outer_class_scope)
> }
> ```
>
> 
