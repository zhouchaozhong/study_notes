linux系统编程学习笔记
=======================================================================================

1. 进程相关的概念
    
    1.1 程序与进程

    > 程序，是指编译好的二进制文件，在磁盘上不占用系统资源（cpu，内存，打开的文件，设备，锁……）
       进程是一个抽象的概念，与操作系统原理联系紧密。进程是活跃的程序，占用系统资源。在内存中执行。（程序运行起来产生一个进程）

    1.2 并发

    > 并发，在操作系统中一个时间段中有多个进程都处于已启动运行到运行完毕之间的状态。但，任一个时刻点上仍只有一个进程在运行。

    1.3 单道程序设计

    > 单道程序设计技术是指在内存一次只能允许一个程序进行运行，在这次程序运行结束前，其它程序不允许使用内存。这是早期的操作系统所使用的技术。

    1.4 多道程序设计

    > 多道程序设计是在计算机内存中同时存放几道相互独立的程序，使它们在管理程序控制之下，相互穿插的运行。 两个或两个以上程序在计算机系统中同处于开始到结束之间的状态。这就称为多道程序设计。多道程序技术运行的特征：多道、宏观上并行、微观上串行。

    1.5 CPU/MMU

    1.6 进程控制块PCB

    > 每个进程在内核中都有一个进程控制块(PCB)来维护进程相关的信息,Linux内核的进程控制块是task_struct结构体。在sched.h文件中可以查看到struct task_struct结构体定义。其内部成员有很多。重点有以下：

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

        ```

            #include <stdio.h>
            #include <stdlib.h>
            #include <unistd.h>

            int main(void){
                
                pid_t pid;
                pid = fork();
                if(pid == -1){
                    perror("fork error");
                    exit(1);
                }
                else if(pid > 0){
                    sleep(1);
                    printf("parent \n");
                }
                else if(pid == 0){
                    // execlp("ls","ls","-l","-a",NULL);
                    // execl("/bin/ls","ls","-l","-a",NULL);
                    execlp("php","php","-v",NULL);
                }


                return 0;
            }


        ```

    3.3 wait/waitpid

        3.3.1 回收子进程的一般方式

            * 孤儿进程
            孤儿进程：父进程先与子进程结束,则子进程成为孤儿进程，子进程的父进程成为init进程,称为init进程领养孤儿进程。

            * 僵尸进程
            僵尸进程：进程终止，父进程尚未回收,子进程残留资源（PCB）存放于内核中，变成僵尸(Zombie)进程。


        ```

            //回收子进程的方法

            #include <stdio.h>
            #include <stdlib.h>
            #include <unistd.h>
            #include <sys/wait.h>

            int main(void){
                
                pid_t pid,wpid;
                int status;
                pid = fork();
                if(pid == -1){
                    perror("fork error");
                    exit(1);
                }
                else if(pid == 0){

                    printf("This is child process parent = %d \n",getppid());
                    sleep(5);
                    printf("Child process is die! \n");
                    exit(1);
                
                }
                else if(pid > 0){
                    wpid = wait(&status);   //调用一次wait函数，只回收一个子进程
                    if(wpid == -1){
                        perror("wait error:");
                        exit(1);
                    }
                    if(WIFEXITED(status)){  //判断是否正常结束
                        printf("child exit with %d \n",WEXITSTATUS(status));  //打印正常退出值
                    }
                    if(WIFSIGNALED(status)){  //判断是否异常终止
                        printf("child killed with %d \n",WTERMSIG(status));  //打印异常退出值
                    }
                    while(1){
                        printf("This is parent process ,pid = %d child = %d \n",getpid(),pid);
                        sleep(1);
                    }
                }


                return 0;
            }



            #include <stdio.h>
            #include <stdlib.h>
            #include <unistd.h>
            #include <sys/wait.h>

            int main(void){

                int i;
                int n = 20;
                pid_t pid,q;
                pid_t wpid;
                for(i = 0;i < n;i++){
                    pid = fork();
                    if(pid == 0){
                        break;
                    }
                    else if(i == 3){
                        q = pid;
                    }
                }

                if(i == n){
                    sleep(n);
                    printf("This is parent process,pid = %d \n",getpid());

                    do{
                        
                        wpid = waitpid(-1,NULL,WNOHANG);    //非阻塞回收子进程
                        if(wpid > 0){
                            n--;
                        }
                        //if wpid == 0 说明子进程正在运行
                        sleep(1);

                    }while(n>0);

                    printf("wait finish ! \n");
                }
                else
                {
                    sleep(i);
                    printf("This is the %dth child,pid = %d,gpid = %d \n",i+1,getpid(),getgid());
                }

                return 0;
            }


        ```

4. 进程间通信（IPC）

    4.1 管道

    > 管道的概念：管道是一种最基本的IPC机制，作用于有血缘关系的进程之间，完成数据传递。调用pipe系统函数即可创建一个管道。有如下特质：
    >> a. 其本质是一个伪文件（实为内核缓冲区）。

    >> b. 由两个文件描述符引用，一个表示读端，一个表示写端。

    >> c. 规定数据从管道的写端流入管道，从读端流出。

    > 管道的原理：管道实为内核使用环形队列机制，借助内核缓冲区（4k）实现。

    > 管道的局限性：
    >> a. 数据自己读，不能自己写。

    >> b. 数据一旦被读走，便不在管道中存在，不可反复读取。

    >> c. 由于管道采用半双工通信方式。因此，数据只能在一个方向上流动。

    >> d. 只能在有公共祖先的进程间使用管道。

    ```
        /*
            简单管道通信示例代码
        
        */

        #include <stdio.h>
        #include <stdlib.h>
        #include <unistd.h>
        #include <string.h>

        int main(void){

            int fd[2];
            pid_t pid;

            int ret = pipe(fd);
            if(ret == -1){
                perror("pipe error");
                exit(1);
            }

            pid = fork();
            if(pid == -1){
                perror("pipe error");
                exit(1);
            }
            else if(pid == 0){  //子进程 读数据
                sleep(1);
                close(fd[1]);
                char buf[1024];
                ret = read(fd[0],buf,sizeof(buf));
                if(ret == 0){
                    printf("------------ \n");
                }
                write(STDOUT_FILENO,buf,ret);
            }
            else{   //父进程  写数据
                close(fd[0]);
                write(fd[1],"hello pipe \n",strlen("hello pipe \n"));
            }

            return 0;
        }


    ```

    4.2 共享存储映射

    > void *mmap(void *addr,size_t length,int prot,int flags,int fd,off_t offset);
    >> 返回： 成功：返回创建的映射区首地址；失败：MAP_FAILED宏
    
    >> 参数： 
    >>> addr:建立映射区的首地址，由Linux内核指定。使用时，直接传递NULL

    >>> length: 欲创建映射区的大小。

    >>> prot: 映射区权限PROT_READ、PROT_WRITE、PROT_READ|PROT_WRITE
    
    >>> flags: 标志位参数（常用于更新设定物理区域、设置共享、创建匿名映射区）
    >>>> MAP_SHARED:会将映射区所做的操作反映到物理设备（磁盘）上。

    >>>> MAP_PRIVATE:映射区所做的修改不会反映到物理设备。

    >>> fd: 用来建立映射区的文件描述符。

    >>> offset: 映射文件的偏移(4k的整数倍)。

    ```

    #include <stdio.h>
    #include <stdlib.h>
    #include <fcntl.h>
    #include <unistd.h>
    #include <string.h>
    #include <sys/mman.h>

    int main(void){

    int len,ret;
    char * p = NULL;
    int fd = open("mmaptest.txt",O_CREAT|O_RDWR,0644);
    if(fd < 0){
        perror("open error");
        exit(1);
    }
    
    len = ftruncate(fd,4);
    if(len == -1){
        perror("ftruncate error");
        exit(1);
    }
    p = mmap(NULL,4,PROT_READ|PROT_WRITE,MAP_SHARED,fd,0);
    if(p == MAP_FAILED){
        perror("mmap error");
        exit(1);
    }
    strcpy(p,"abc"); //写数据
    ret = munmap(p,4); 
    if(ret == -1){
        perror("munmap error");
        exit(1);
    } 

    close(fd); 


    return 0;
    }


    ```

    > 总结：使用mmap时务必注意以下事项
    >> 1. 创建映射区的过程中，隐含着一次对文件的读操作。
    >> 2. 当MAP_SHARED时，要求：映射区的权限应 <= 文件打开的权限(出于对映射区的保护)。而MAP_PRIVATE则无所谓,
    >> 因为mmap中的权限是对内存的限制。
    >> 3. 映射区的释放与文件关闭无关。只要映射建立成功,文件可以立即关闭。
    >> 4. 特别注意,当映射文件大小为0时，不能创建映射区。所以：用于映射的文件必须要有实际大小!
    >> mmap使用时常常会出现总线错误,通常是由于共享文件存储空间大小引起的。
    >> 5. munmap传入的地址一定是mmap的返回地址，坚决杜绝指针++操作。
    >> 6. 文件偏移量必须为4k的整数倍。
    >> 7. mmap创建映射区出错概率非常高，一定要检查返回值，确保映射区建立成功再进行后续操作。


    





