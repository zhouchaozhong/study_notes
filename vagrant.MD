Vagrant使用教程
==================
注意
------------------
* 快速打造跨平台开发环境 vagrant + virtualbox + box](http://www.54php.cn/default/26.html)

目标
------------------
* 成果是打包一个box镜像
* 这个box镜像需要运行 LAMP(Linux + Apache + Mysql + PHP ) 和 LNMP( Linux + Nginx + Mysql + PHP) 
* 配置好3个比较流行的PHP框架运行环境
    * Yii2
    * Laravel5
    * ThinkPHP5

基础环境
--------------------

* VirtualBox 5.1.8
    * 下载地址 https://www.virtualbox.org/wiki/Download_Old_Builds_5_1


    * 在Windows环境中安装VirtualBox时，需要将VirtualBox的安装路径添加到path环境变量中，否则vagrant会找不到VirtualBox的路径   
    
* Vagrant 1.8.6  
    * 下载地址：https://releases.hashicorp.com/vagrant/1.8.6/
    * 切记根据自己的操作系统下载，同时分32位和64位

* Windows 额外工作
    * 可能需要配置环境变量
    * 安装Xshell命令行工具
    * [注意，一定要开启 VT-x/AMD-V 硬件加速](https://zhidao.baidu.com/question/1383660674073569340.html)
    
    
启动虚拟机
-------------------
* box下载
    * ﻿链接一：http://pan.baidu.com/s/1i5BfL45 密码：himr
    * 链接二：https://pan.baidu.com/s/1kU8fUlD
    * 下载之后请确保文件名为ubuntu1404.box
* 添加box
    * vagrant box add ubuntu1404 ubuntu1404.box
* 初始化box
    * vagrant init ubuntu1404
    * vagrant ssh

对虚拟机优化
-------------------
* 替换源
    * sudo cp /etc/apt/sources.list /etc/apt/sources.list.bak #备份
    * sudo vim /etc/apt/sources.list #修改源
        * 将文件内容替换成[源文件内容](./ubuntu/apt/sources.list)
    * sudo apt-get update #更新列表
    
* L(A|N)MP
    * Ngnix
        * sudo apt-get install nginx
        * nginx -v
        
            `nginx version: nginx/1.4.6 (Ubuntu)`
        * 测试Nginx
            * curl -I 'http://127.0.0.1'
            ```    
            
                HTTP/1.1 200 OK
                Server: nginx/1.4.6 (Ubuntu)
                Date: Sat, 07 Jan 2017 07:49:41 GMT
                Content-Type: text/html
                Content-Length: 612
                Last-Modified: Tue, 04 Mar 2014 11:46:45 GMT
                Connection: keep-alive
                ETag: "5315bd25-264"
                Accept-Ranges: bytes
            ```

    * Apache
        * sudo apt-get install apache2
        * apache2 -v
            
            `Server version: Apache/2.4.7 (Ubuntu)
             Server built:   Jul 15 2016 15:34:04`
        * 测试访问，此时不能访问，因为80端口被nginx占用了,可以先把nginx停掉
            * curl -I 'http://127.0.0.1'
            ``` 
            
                 HTTP/1.1 200 OK
                 Date: Sat, 07 Jan 2017 08:15:26 GMT
                 Server: Apache/2.4.7 (Ubuntu)
                 Last-Modified: Sat, 07 Jan 2017 07:17:45 GMT
                 ETag: "2cf6-5457bee51dd0d"
                 Accept-Ranges: bytes
                 Content-Length: 11510
                 Vary: Accept-Encoding
                 Content-Type: text/html`
            ```
             
        * 更改端口，将端口设置为8888
            * 修改 ports.conf 文件
            * curl -I 'http://127.0.0.1:8888'
            ```     
            
                 HTTP/1.1 200 OK
                 Date: Sat, 07 Jan 2017 08:24:15 GMT
                 Server: Apache/2.4.7 (Ubuntu)
                 Last-Modified: Sat, 07 Jan 2017 07:17:45 GMT
                 ETag: "2cf6-5457bee51dd0d"
                 Accept-Ranges: bytes
                 Content-Length: 11510
                 Vary: Accept-Encoding
                 Content-Type: text/html`
            ``` 
            
    * Mysql
        * sudo apt-get install mysql-server  #服务器端
            * 安装期间会提示输入为mysql设置root密码，我这边不操作，直接enter 不设置密码
        * sudo apt-get install mysql-client  #客户端
            * mysql -uroot -p #测试连接库，上面安装服务端没有设置密码，这里直接enter进入
    * php
        * 安装php
            * sudo apt-get install php5-cli 
                * php -v
                ```   
                
                      PHP 5.5.9-1ubuntu4.20 (cli) (built: Oct  3 2016 13:00:37)
                      Copyright (c) 1997-2014 The PHP Group
                      Zend Engine v2.5.0, Copyright (c) 1998-2014 Zend Technologies
                      with Zend OPcache v7.0.3, Copyright (c) 1999-2014, by Zend Technologies
                ``` 
        * PHP扩展
            * php5-mcrypt 
            * php5-mysql
            * php5-gd
        * 支持apache2的php模块
            * sudo apt-get install libapache2-mod-php5
            * 开启rewrite功能
                * sudo a2enmod rewrite

        * 支持nginx fastcgi
            * sudo apt-get install php5-cgi php5-fpm
            * 修改成9000端口 ，默认sock模式
                * cd /etc/php5/fpm/pool.d
                * sudo vim www.conf  # search listen = 127.0.0.1:9000
                * sudo /etc/init.d/php5-fpm restart
            
* 端口转发 为了验证nginx是否按照成功~~
    *  8888 端口转发到 80 
        * http://127.0.0.1:8888/
    *  8889 端口转发到 8888
        * http://127.0.0.1:8889/
            
Vagrant高级配置
------------------------
* 端口转发
    * 配置
    ``` 
    
         config.vm.network "forwarded_port", guest: 80, host: 8888 
         config.vm.network "forwarded_port", guest: 8888, host: 8889
    ``` 
     
    * 测试效果
        * http://127.0.0.1:8888/
        * http://127.0.0.1:8889/
     
* 网络设置
    * 配置
    
        `config.vm.network "private_network", ip: "192.168.199.101"`
    
    * 效果
        * http://192.168.199.101/ 或者 http://192.168.199.101:80/
        * http://192.168.199.101:8888/
        
* 共享目录
    * 配置        
        
        `config.vm.synced_folder "/Users/vincent/code/", "/home/www"`
        
        
优化
----------------
* 虚拟机名称
    * vb.name = "ubuntu_mooc"
* 虚拟机主机名
    * config.vm.hostname = "mooc"
* 配置虚拟机内存和CPU
    * vb.memory = "1024"  
    * vb.cpus = 2
    
打包命令
----------------
* vagrant package  --output  xxx.box



windows下vagrant的注意事项（热心同学整理的）
--------------
* 笔者最近在使用vagrant时遇到了一些问题,当然也解决了，故把一些要注意的事情点出来,.本人使用的是windows 64位操作系统，故以此环境展开.

* 必备工具:  
    * vagrant(最新版,截止2017/3/18)
    * virtual box(用5.1.14,截止2017/3/18的virtualbox版本有Bug,无法挂载文件)
    * powershell(最好是大于2.0的版本)
    * putty(windows下没有自带ssh工具,所以推荐这个小巧)
    * box文件(ubuntu14.04版本,下载链接:https://pan.baidu.com/s/1kU8fUlD)

* 常见问题:  
    * 为啥ssh登不进linux
    
        这个问题首先我们需要4号工具,最好将其加入环境变量,然后直接在shell下敲入putty.  
        在putty中hostname记住是127.0.0.1 端口是2222(虚拟机会映射到22).点击open后,会要你输入name和password,你都输入vagrant(默认的)就可以了.

    * 共享目录挂载不成功  
        
        先执行命令 vagrant plugin install vagrant-vbguest,再看看virtualbox版本对到了2号工具否.
        其他参考:
        http://www.cnblogs.com/fengchi/p/6549784.html
        http://stackoverflow.com/questions/27992354/vagrant-error-failed-to-mount-folders-in-linux-guest-after-halt-or-reload
        * 在windows环境下虚拟机系统为centos7，要实现smb类型的文件共享，centos7中必须安装cifs-utils.x86_64这个包，
          yum install cifs-utils.x86_64。同时，如果是win10系统，windows账户的密码必须是微软邮箱的密码，不能是pin密码。


    * 默认共享方式和rsync共享方式
      rsync同步方式在windows上的同步存在一些问题，在宿主机改动文件，虚拟机的文件会同步。当虚拟机内文件改动时，宿主机上的文件不会同步。且只有在vagrant up 和vagrant reload时才会同步文件。或者用以下两个命令才会同步文件
    * vagrant rsync  将宿主机文件与虚拟机内映射的文件同步
    * vagrant rsync-auto 监听宿主机文件的改动，当宿主机文件改动时，自动同步文件改动到虚拟机


Vagrant在windows10系统中的配置示例（VirtualBox5.2.2,Vagrant2.0.1）
------------------------          
    
Vagrant.configure("2") do |config|

#所使用的box的名称
  config.vm.box = "centos/7"
  #linux系统中显示的主机名
  config.vm.hostname = "centos7"

  #配置私有网络ip和共享文件夹
  #config.vm.network "private_network", ip: "192.168.33.10"
 
  config.vm.synced_folder "d:/vagrant/centos7", "/vagrant",type:"smb",
  #windows系统用户名
  smb_username:"*********",
  #windows系统密码
  smb_password:"********",
  #同步到虚拟机内的文件所有者
  owner:"vagrant",
  #同步到虚拟机内的文件所属群组
  group:"vagrant"

  #mount_options: ["dmode=775,fmode=664"]



  config.vm.provider "virtualbox" do |vb|
  #   # Display the VirtualBox GUI when booting the machine
  #   vb.gui = true
  #配置在VirtualBox中显示的系统名字
    vb.name = "centos7_dev_env"
  #   # Customize the amount of memory on the VM:
  #   vb.memory = "1024"
  end


end
   
    
    
