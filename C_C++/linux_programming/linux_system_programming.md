linux系统编程学习笔记
=======================================================================================

1. 进程相关的概念
    
    1.1 程序与进程

    >> 程序，是指编译好的二进制文件，在磁盘上不占用系统资源（cpu，内存，打开的文件，设备，锁……）
       进程是一个抽象的概念，与操作系统原理联系紧密。进程是活跃的程序，占用系统资源。在内存中执行。（程序运行起来产生一个进程）

    1.2 并发

    >> 并发，在操作系统中一个时间段中有多个进程都处于已启动运行到运行完毕之间的状态。但，任一个时刻点上仍只有一个进程在运行。

    1.3 单道程序设计

    >> 单道程序设计技术是指在内存一次只能允许一个程序进行运行，在这次程序运行结束前，其它程序不允许使用内存。这是早期的操作系统所使用的技术。

    1.4 多道程序设计

    >> 多道程序设计是在计算机内存中同时存放几道相互独立的程序，使它们在管理程序控制之下，相互穿插的运行。 两个或两个以上程序在计算机系统中同处于开始到结束之间的状态。这就称为多道程序设计。多道程序技术运行的特征：多道、宏观上并行、微观上串行。

    1.5 CPU/MMU

    1.6 进程控制块PCB

    >> 每个进程在内核中都有一个进程控制块(PCB)来维护进程相关的信息,Linux内核的进程控制块是task_struct结构体。在sched.h文件中可以查看到struct task_struct结构体定义。其内部成员有很多。重点有以下：

    * 进程id。系统中每个进程有唯一的id,在c语言中用pid_t类型表示，其实就是一个非负整数。
    * 进程的状态,有就绪、运行、挂起、停止等状态。
    * 进程切换时需要保存和恢复的一些cpu寄存器。
    * 描述虚拟地址空间的信息。
    * 描述控制终端的信息。
    * 当前工作目录(Current Working Directory)。
    * umask掩码
    * 文件描述符表，包含很多指向file结构体的指针。
    * 和信号相关的信息。
    * 用户id和组id。
    * 会话(Session)和进程组。
    * 进程可以使用的资源上限(Resource Limit)。

    1.7 进程状态

2. 环境变量

    2.1 常用环境变量及其作用

        * PATH  记录可执行文件的搜索路径。
        * SHELL 当前Shell,它的值通常是/bin/bash。
        * TERM 当前终端类型

    2.2 函数

    ```
        #include <stdio.h>

        int main(void){

            const char * name = "testenv";
            char * val = "hello,world";

            char * sval = "";

            setenv(name,val,1);

            sval = getenv(name);

            printf("%s = %s",name,sval);

            return 0;
        }

    ```

3. 进程控制原语

    3.1 fork函数

        3.1.1 循环创建子进程的架构

        >> 返回值有2个：1个进程----> 2个进程----.各自对fork做返回

            a. 返回子进程的pid(非负整数 > 0) (父进程)

            b. 返回 0 （子进程）

        >> 父子进程之间遵循读时共享写时复制的原则    
        >> 父子进程共享： a. 文件描述符 b. mmap建立的映射区（进程间通信详解）

        3.1.2 相关函数
        
        >> uid_t getuid(void) 获取当前进程实际用户id
        >> uid_t geteuid(void) 获取当前进程有效用户id
        >> gid_t getgid(void) 获取当前进程使用用户组id
        >> gid_t getegid(void) 获取当前进程有效用户组id


        ```

            #include <stdio.h>
            #include <stdlib.h>
            #include <unistd.h>

            int main(void){

                //循环创建n个子进程
                int i;
                pid_t pid;
                printf("xxxxxxxxxxxxx\n");

                for(i = 0;i < 5;i++){

                    pid = fork();

                    if(pid == -1){
                        perror("fork error");
                        exit(1);
                    }
                    else if(pid == 0){
                        break;
                        // printf("this is %dth child process! pid = %u ppid = %u \n",i+1,getpid(),getppid());
                    }
                    else if(pid > 0){
                        // printf("this is parent process! pid = %u ppid = %u \n",getpid(),getppid());
                        // sleep(1);
                    }

                }

                if(i < 5){
                    sleep(i);
                    printf("this is %dth child process! pid = %u ppid = %u \n",i+1,getpid(),getppid());
                }
                else{
                    sleep(i);
                    printf("this is parent process! \n");
                }
            

                return 0;
            }


        ``` 
           

    3.2 exec函数族
        
        3.2.1 各个函数参数的使用方法、作用

    3.3 wait/waitpid

        3.3.1 回收子进程的一般方式




