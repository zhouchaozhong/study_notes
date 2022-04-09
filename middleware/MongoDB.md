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

##### 创建普通用户

> 普通用户需要由管理员用户创建，所以先使用管理员用户登录数据库
>
> **管理员登录数据库**
>
> ```javascript
> > use admin
> switched to db admin
> > db.auth("uadd","uaad")
> ```
>
> **创建数据库**
>
> MongoDB没有特定创建数据库的语法，在使用use切换数据库时，如果对应的数据库不存在则直接创建并切换
>
> ```javascript
> > use test
> switched to db test
> > use admin
> > db.createUser({user"testuser",pwd:"12345",roles:[{role:"readWrite",db:"test"}]});
> > use test;
> > db.auth("testuser","12345");
> ```
>
> **插入数据**
>
> ```javascript
> > db.user.insert({"name":"zhangsan"})
> // 这里的user是创建一个集合叫user，相当于mysql里面的表
> ```
>
> **查找数据**
>
> ```javascript
> > db.user.find()
> // 这里是查找user集合里面的所有数据，相当于select * from user
> ```

##### 更新用户

> 如果我们需要对已存在的用户进行角色更改，可以使用` db.updateUser()`函数来更新用户角色。注意：执行该函数需要当前用户具有`userAdmin`或`userAdminAnyDatabase`或`root`角色。
>
> ```javascript
> db.updateUser("用户名",{"roles":[{"role":"角色名称",db:"数据库"},{"更新项2":"更新内容"}]})
> ```
>
> 比如给之前的uadd用户添加`readWriteAnyDatabase`和`dbAdminAnyDatabase`权限。
>
> ```javascript
> db.updateUser("uadd",{roles:[{role:"userAdminAnyDatabase",db:"admin"},{role:"readWriteAnyDatabase",db:"admin"}]})
> ```

##### 更新密码

> 更新密码有以下两种方式，更新密码时需要切换到该用户所在的数据库。注意：需要使用具有`userAdmin`或`userAdminAnyDatabase`或`root`角色的用户执行：
>
> * 使用`db.updateUser("用户名",{pwd:"新密码"})` 函数更新密码
> * 使用`db.changeUserPassword("用户名","新密码")` 函数更新密码

##### 删除用户

> 通过`db.dropUser()`函数可以删除指定用户，删除成功以后会返回true。删除用户时，需要切换到该用户所在的数据库。注意：需要具有`userAdmin`或`userAdminAnyDatabase`或`root`角色的用户才可以删除其他用户
>
> ```javascript
> db.dropUser("testuser")
> ```

### 数据库操作

> **创建数据库**
>
> `use 数据库名`
>
> **显示数据库**
>
> `show dbs`或`show databases`
>
> **删除数据库**
>
> ```javascript
> use test  // 先切换到该数据库
> db.dropDatabase() // 然后删除
> ```

### Collection操作

> **创建集合**
>
> 可以使用db.COLLECTION_NAME来隐式创建集合如`db.user`
>
> 也可以显式创建集合
>
> `db.createCollection(name,options)`
>
> name: 要创建的集合名称  options:可选参数，指定有关内存大小及索引的选项
>
> | 字段        | 类型 | 描述                                                         |
> | ----------- | ---- | ------------------------------------------------------------ |
> | capped      | 布尔 | （可选）如果为true，则创建固定集合。固定集合是指有固定大小的集合，当达到最大值时，它会自动覆盖最早的文档。当该值为true时，必须指定size参数 |
> | size        | 数值 | （可选）限制集合空间的大小，默认为没有限制（以字节计）。如果capped为true，必须指定该字段 |
> | autoIndexId | 布尔 | （可选）如果为true，自动在 _id 字段创建索引。默认为true      |
> | max         | 数值 | （可选）限制集合中包含文档的最大数量，默认为没有限制         |
>
> **查看集合**
>
> `show tables` 或者`show collections`
>
> **删除集合**
>
> 使用`db.COLLECTION_NAME.drop()` 来删除集合，例如：`db.user.drop()`就把user集合删除掉了

### Document操作

