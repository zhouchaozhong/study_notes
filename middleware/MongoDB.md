# MongoDB学习笔记

### MongoDB基础知识

> MongoDB启动
>
> `mongod -f XXX/mongodb.conf`
>
> MongoDB关闭
>
> `mongod -f XXX/mongodb.conf --shutdown`
>
> 配置文件
>
> mongodb.conf
>
> ```shell
> # 数据文件存放目录
> dbpath = /usr/local/app/mongodb/data/db
> # 日志文件存放目录
> logpath = /usr/local/app/mongodb/logs/mongodb.log
> # 以追加的方式记录日志
> logappend = true
> # 端口默认为27017
> port = 27017
> # 对访问IP地址不做限制，默认为本机地址
> bind_ip = 0.0.0.0
> # 以守护进程的方式启用，即在后台运行
> fork = true
> # 开启身份认证
> auth = true
> ```
>
> 

### MongoDB用户与权限管理

##### 常用权限

| 权限                 | 说明                                                         |
| -------------------- | ------------------------------------------------------------ |
| read                 | 允许用户读取指定数据库                                       |
| readWrite            | 允许用户读写指定数据库                                       |
| userAdmin            | 允许用户向system.users集合写入，可以在指定数据库里创建、删除和管理用户 |
| dbAdmin              | 允许用户在指定数据库中执行管理函数，如索引创建，删除，查看统计或访问system.profile |
| clusterAdmin         | 必须在admin数据库中定义，赋予用户所有分片和复制集相关函数的管理权限 |
| readAnyDatabase      | 必须在admin数据库中定义，赋予用户所有数据库的读权限          |
| readWriteAnyDatabase | 必须在admin数据库中定义，赋予用户所有数据库的读写权限        |
| userAdminAnyDatabase | 必须在admin数据库中定义，赋予用户所有数据库的userAdmin权限   |
| dbAdminAnyDatabase   | 必须在admin数据库中定义，赋予用户所有数据库的dbAdmin权限     |
| root                 | 必须在admin数据库中定义，超级账号，超级权限                  |

##### 创建管理用户

>   MongoDB有一个用户管理机制，简单描述为管理用户组，这个组的用户是专门为管理普通用户而设的，暂且称之为管理员。管理员通常没有数据库的读写权限，只有操作用户的权限，我们只需要赋予管理员`userAdminAnyDatabase`角色即可。另外管理员账户必须在admin数据库下创建。
>
> >   由于用户被创建在哪个数据库下，就只能在哪个数据库登录，所以把所有的用户都创建在admin数据库下。这样我们切换数据库时，就不需要频繁的进行登录了。
> >
> >   先`use admin`切换到admin数据库进行登录，登录后再use切换到其他数据库进行操作即可。第二次的use就不需要再次登录了。MongoDB设定use第二个数据库时，如果登录用户权限比较高就可以直接操作第二个数据库，而不需要登录。
>
> **切换数据库**
>
> 管理员需要在admin数据库下创建，所以我们需要切换到admin数据库
>
> ` > use admin `
>
> **查看用户**
>
> 通过` db.system.users.find()` 函数查看admin数据库中的所有用户信息
>
> **创建用户**
>
> 在MongoDB中可以使用` db.createUser({用户信息}) ` 函数创建用户。
>
> ```javascript
> db.createUser({
>     user:"<name>",
>     pwd:"<cleartext password>",
>     customData:{<any information>},
>     roles:[
>         {role:“<role>”,db:"<database>"} | "<role>",
>     ]
> });
> ```
>
> * user : 用户名
> * pwd : 密码
> * customData : 存放用户相关的自定义数据，该属性也可忽略
> * roles : 数组类型，配置用户权限
>
> 示例如下：
>
> ```javascript
> db.createUser({user:"uadd",pwd:"uaad",roles:[{role:"userAdminAnyDatabase",db:"admin"}]});
> ```
>
> 

