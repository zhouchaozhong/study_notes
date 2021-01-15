# JavaSE学习笔记

### 多线程
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
> 7. join(): 等待该线程终止
>
>    ```java
>    package com.thread;
>    
>    /**
>     *  多线程的创建，方式一：继承于Thread类
>     *  1.创建一个继承于Thread类的子类
>     *  2.重写Thread类的run()
>     *  3.创建Thread类的子类的对象
>     *  4.通过此对象调用start()
>     */
>    public class ThreadTest {
>    
>        public static void main(String[] args) {
>            MyThread t = new MyThread();
>            t.start();
>    
>            for (int i = 0; i < 100; i++) {
>                if(i % 2 == 0){
>                    System.out.println(Thread.currentThread().getName() + " : " + i);
>                }
>    
>                if(i == 20){
>                    try {
>                        t.join();
>                    } catch (InterruptedException e) {
>                        e.printStackTrace();
>                    }
>                }
>            }
>    
>        }
>    }
>    
>    class MyThread extends Thread{
>    
>        @Override
>        public void run() {
>    
>            for (int i = 0; i < 100; i++) {
>                if(i % 2 == 0){
>                    System.out.println(Thread.currentThread().getName() + " : " + i);
>                }
>    
>                if(i % 20 == 0){
>                    yield();
>                }
>            }
>        }
>    
>    }
>    ```
>
>    

