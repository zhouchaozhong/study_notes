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
    #include <sys/wait.h>

    int var = 100;

    int main(void){

        pid_t pid;
        int *p;
        int fd = open("temp",O_RDWR|O_CREAT|O_TRUNC,0644);
        if(fd < 0){
            perror("open error");
            exit(1);
        }

        unlink("temp");
        ftruncate(fd,4);
        p = (int *)mmap(NULL,4,PROT_READ|PROT_WRITE,MAP_SHARED,fd,0);
        //匿名映射区
        //p = (int *)mmap(NULL,4,PROT_READ|PROT_WRITE,MAP_SHARED|MAP_ANON,-1,0);
        //MAP_ANONYMOUS 和 MAP_ANON这两个宏是Linux操作系统特有的宏，unix下可以通过关联/dev/zero 文件 来实现映射
        if(p == MAP_FAILED){
            perror("mmap error");
            exit(1);
        }

        close(fd); 
        pid = fork();
        if(pid == 0){
            *p = 2000;
            var = 1000;
            printf("child ,*p = %d,var = %d \n",*p,var);
        }
        else{
            sleep(1);
            printf("parent ,*p = %d,var = %d \n",*p,var);
            wait(NULL);

            int ret = munmap(p,4);  //释放映射区
            if(ret == -1){
                perror("munmap error");
                exit(1);
            }
        }

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
    >> 5. munmap传入的地址一定是mmap的返回地址，坚决杜绝指针++/--操作。
    >> 6. 文件偏移量必须为4k的整数倍。
    >> 7. mmap创建映射区出错概率非常高，一定要检查返回值，确保映射区建立成功再进行后续操作。


5. 信号
    
    5.1 信号的概念
    
    > 基本的属性

    > 信号四要素

    > 信号的处理方式
    >> 1. 执行默认动作  2. 忽略（丢弃） 3. 捕捉（调用户处理函数）

    5.2 产生5种信号
    >> 1.按键产生(如:ctrl+c ,ctrl+z)   2.系统调用产生(如：kill,raise) 3. 软件条件产生(如：定时器 alarm)  4. 硬件异常产生(非法访问内存)  5.命令产生(如：kill命令)

    > kill
    
    ```
        #include <stdio.h>
        #include <stdlib.h>
        #include <unistd.h>
        #include <signal.h>

        #define N 5

        int main(void){
            
            int i;
            pid_t pid,q;

            for(i = 0;i < N;i++){
                pid = fork();
                if(pid == 0) break;
                if(i == 2) q = pid;
            }

            if(i < 5){
                while(1){
                    printf("I'm child %d ,getpid = %u \n",i,getpid());
                    sleep(1);
                }
            }
            else{
                sleep(1);
                kill(q,SIGKILL);
                while(1);
            }

            return 0;
        }

    ```

    > alarm函数

    ```
        //每个进程都有且只有一个定时器
        // unsigned int alarm(unsigned int seconds);  返回0 或剩余的秒数，无失败。
        //常用，取消定时器 alarm(0);
        #include <stdio.h>
        #include <stdlib.h>
        #include <unistd.h>
        #include <signal.h>

        #define N 5

        int main(void){
            
            int i;
            alarm(1);
            for(i = 0;;i++)
                printf("%d \n",i);

            return 0;
        }

    ```

    > setitimer函数
    >> int setitimer(int which,const struct itimerval *new_value,struct itimerval *old_value);
    >>> 参数: which 指定定时方式

    >>> a. 自然定时: ITIMER_REAL  -> 14 (SIGLARM)    计算自然时间

    >>> b. 虚拟空间计时(用户空间)：  ITIMER_VIRTUAL  -> 26 ） SIGVTALRM   只计算进程占用cpu的时间

    >>> c. 运行时计时（用户+内核）：ITIMER_PROF   -> 27 ) SIGPROF  计算占用cpu及执行系统调用的时间

    ```

    #include <stdio.h>
    #include <stdlib.h>
    #include <unistd.h>
    #include <signal.h>
    #include <sys/time.h>

    unsigned int my_alarm(unsigned int sec){

        struct itimerval it,oldit;
        int ret;

        it.it_value.tv_sec = sec;
        it.it_value.tv_usec = 0;
        it.it_interval.tv_sec = 0;
        it.it_interval.tv_usec = 0;

        ret = setitimer(ITIMER_REAL,&it,&oldit);
        if(ret == -1){
            perror("setitimer");
            exit(1);
        }

        return oldit.it_value.tv_sec;
    }

    int main(void){
        
        int i;
        my_alarm(1);
        for(i = 0;;i++)
            printf("%d \n",i);

        return 0;
    }

    ```

    5.3 信号集操作函数

    > 信号屏蔽字

    > 未决信号集

    ```
        //打印未决信号集
        #include <stdio.h>
        #include <signal.h>

        void printped(sigset_t *ped){
            int i;
            for(i = 1;i < 32;i++){
                if(sigismember(ped,i) == 1) {
                    putchar('1');
                }
                else{
                    putchar('0');
                }
            }

            printf("\n");

        }

        int main(void){
            
            sigset_t myset,oldset,ped;

            sigemptyset(&myset);
            sigaddset(&myset,SIGQUIT);
            sigaddset(&myset,SIGTSTP);
            sigprocmask(SIG_BLOCK,&myset,&oldset);

            while(1){
                sigpending(&ped);
                printped(&ped);

                sleep(1);
            }
            

            return 0;
        }


    ```

    5.4 信号的捕捉

    > 注册信号捕捉函数

    ```
        #include <stdio.h>
        #include <sys/time.h>
        #include <signal.h>

        void myfunc(int signo){
            printf("hello world \n");
        }

        int main(void){
            
            struct itimerval it,oldit;
            signal(SIGALRM,myfunc); //注册SIGALRM信号的捕捉处理函数

            it.it_value.tv_sec = 5;
            it.it_value.tv_usec = 0;

            it.it_interval.tv_sec = 3;
            it.it_interval.tv_usec = 0;

            if(setitimer(ITIMER_REAL,&it,&oldit) == -1){
                perror("setitimer error");
                return -1;
            }

            while(1);
            
            return 0;
        }




        #include <stdio.h>
        #include <stdlib.h>
        #include <signal.h>
        #include <unistd.h>

        typedef void (*sighandler_t)(int);

        void myfunc(int signo){
        printf("catch sigint !");
        }

        int main(void){
            sighandler_t handler;
            handler = signal(SIGINT,myfunc);

            if(handler == SIG_ERR){
                perror("signal error");
                exit(1);
            }

            while(1);
            return 0;
        }

    ```

    > sigaction函数

    ```

        #include <stdio.h>
        #include <stdlib.h>
        #include <signal.h>
        #include <unistd.h>

        void docatch(int signo){
            printf("%d signal is catched \n",signo);
        }

        int main(void){
            
            int ret;

            struct sigaction act;
            act.sa_handler = docatch;
            sigemptyset(&act.sa_mask);
            sigaddset(&act.sa_mask,SIGQUIT);
            act.sa_flags = 0;   //默认属性 信号捕捉函数执行期间，自动屏蔽本信号

            ret = sigaction(SIGINT,&act,NULL);
            if(ret < 0){
                perror("sigaction error");
                exit(1);
            }

            while(1);

            return 0;
        }

    ```

6. 竟态条件（时序竟态）

    6.1 pause函数

    > 调用该函数可以造成进程主动挂起，等待信号唤醒。调用该系统调用的进程将处于阻塞状态（主动放弃cpu）直到有信号递达将其唤醒。
    >> a. 如果信号的默认处理动作是终止进程，则进程终止，pause函数没有机会返回。

    >> b. 如果信号的默认处理动作是忽略，进程继续处于挂起状态,pause函数不返回。

    >> c. 如果信号的处理动作是捕捉，则【调用完信号处理函数之后，pause返回-1】 errno设置为EINTR，表示“被信号中断”。

    >> d. pause收到的信号不能被屏蔽，如果被屏蔽，那么pause就不能被唤醒。

    ```
        #include <stdio.h>
        #include <stdlib.h>
        #include <signal.h>
        #include <unistd.h>
        #include <errno.h>

        void catch_sigalrm(int signo){

        }

        unsigned int mysleep(unsigned int seconds){

            int ret;
            struct sigaction act,oldact;
            act.sa_handler = catch_sigalrm;
            sigemptyset(&act.sa_mask);
            act.sa_flags = 0;

            ret = sigaction(SIGALRM,&act,&oldact);
            if(ret == -1){
                perror("sigaction error");
                exit(1);
            }

            alarm(seconds);
            ret = pause();  //主动挂起，等信号
            if(ret == -1 && errno == EINTR){
                printf("pause success \n");
            }

            ret = alarm(0);
            sigaction(SIGALRM,&oldact,NULL); //恢复SIGALRM信号旧有的处理方式。

            return ret;
        }

        int main(void){
            
            while(1){
                mysleep(3);
                printf("----------------------------------\n");
            }
            
            return 0;
        }

    ```






