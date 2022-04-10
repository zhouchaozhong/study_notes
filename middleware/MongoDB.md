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

##### 插入文档

> **单条插入**
>
> 可以使用`insert/insertOne/save`插入单条文档
>
> * `db.c1.insert({"name":"a"})`
> * `db.c1.insertOne({"name":"a"})`
> * `db.c1.save({"name":"a"})`
>
> 通过`db.COLLECTION_NAME.insert(document)`插入文档
>
> 插入文档时如果没有指定 `_id` 则默认为`ObjectId` 类型，`_id`不能重复，且在插入后不可变
>
> ```javascript
> user1 = {
>     "name" : "zhangsan",
>     "age" : 18, 
>     "hobbies" : ["music","read"],
>     "addr" : {
>         "country" : "China",
>         "city" : "BJ"
>     }
> }
> db.user.insert(user1)
> ```
>
> **批量插入**
>
> 可以使用`insert/insertMany/save`插入多条文档，区别在于把单条插入时函数参数的对象类型`{}` 变成组类型`[{}]`
>
> * `db.c1.insert([{"name":"a"},{"name":"b"}])`
> * `db.c1.insertMany([{"name":"a"},{"name":"b"}])`
> * `db.c1.save([{"name":"a"},{"name":"b"}])`
>
> ```javascript
> user1 = {
>     "_id":1,
>     "name":"zhangsan",
>     "age":10,
>     "hobbies":["music","read"],
>     "addr":{
>         "country":"China",
>         "city":"BJ"
>     }
> }
> user2 = {
>     "_id":2,
>     "name":"lisi",
>     "age":15,
>     "hobbies":["music","read"],
>     "addr":{
>         "country":"China",
>         "city":"SH"
>     }
> }
> user3 = {
>     "_id":3,
>     "name":"wangwu",
>     "age":18,
>     "hobbies":["music","read"],
>     "addr":{
>         "country":"China",
>         "city":"GZ"
>     }
> }
> db.user.insert([user1,user2,user3])
> ```

##### 更新文档

> 通过`update`系列函数或者`save`函数可以更新集合中的文档
>
> `update()`函数用于更新已存在的文档，语法格式如下：
>
> `db.COLLECTION_NAME.update(query,update,options)`
>
> * `query:update的查询条件，类似SQL update 语句中的where部分`
> * `update:update的对象和一些更新的操作符（如$,$inc）等，也可以理解为SQL update语句中的set部分`
> * `upsert:可选，如果不存在update的文档，是否插入该文档。true为插入，默认是false,不插入`
> * `multi:可选，是否批量更新。true表示按条件查询出来的多条记录全部更新，false只更新找到的第一条记录，默认是false`
>
> ```javascript
> user = {
>     "name":"wangwu",
>     "age":20,
>     "hobbies":["music","read"],
>     "addr":{
>         "country":"China",
>         "city":"BJ"
>     }
> }
> // 修改单条
> db.user.updateOne({"name":"lisi"},{"$set":user})
> // 查找到的匹配数据如果是多条，只会修改第一条，修改单条等价于updateOne()
> db.user.update({"name":"lisi"},user)
> // 查找到的匹配数据如果是多条，修改所有匹配到的记录
> db.user.update({"name":"lisi"},{"$set":user},false,true) // 修改多条
> db.user.updateMany({"name":"zhangsan123123"},{"$set":user}) // 修改多条
> ```
>
> **数据更新操作符**
>
> | 操作符    | 用法                           | 作用                                                         |
> | --------- | ------------------------------ | ------------------------------------------------------------ |
> | $inc      | {$inc:{field:value}}           | 对一个数字字段的某个field增加value                           |
> | $set      | {$set:{field:value}}           | 把文档中某个字段field的值设为value                           |
> | $unset    | {$unset:{field:1}}             | 删除某个字段field                                            |
> | $push     | {$push:{field:value}}          | 把value追加到field里，注：field只能是数组类型，如果field不存在，会自动插入一个数组类型 |
> | $pushAll  | {$pushAll:{field:value_array}} | 用法同push一样，只是pushAll可以一次追加多个值到一个数组字段内 |
> | $addToSet | {$addToSet:{field:value}}      | 加一个值到数组内，而且只有当这个值在数组中不存在时才增加     |
> | $pop      | {$pop:{field:-1}}              | 用于删除数组内的一个值                                       |
> | $pull     | {$pull:{field:value}}          | 从数组field内删除一个等于value的值                           |
> | $pullAll  | {$pullAll:{field:value_array}} | 同pull,可以一次删除数组内多个值                              |
>
> 

##### 删除文档

> 通过`remove()` 函数来移除集合中的数据，语法格式如下：
>
> `db.user.remove(<query>,{justOne:<boolean>})`
>
> * query :  可选，删除文档的条件
> * jusetOne : 可选，如果设为true，则只删除一个文档，false删除所有匹配的数据，等价于db.user.deleteOne(<query>) : 删除符合条件的第一个文档
> * 删除所有数据命令，db.user.remove({})  等价于db.user.deleteMany({}) 等于是清空该集合

##### 查询文档

> **查询所有**
>
> 先插入查询的数据
>
> ```javascript
> user1 = {
>     "_id":1,
>     "name":"令狐冲",
>     "age":16,
>     "hobbies":["独孤九剑","重剑"],
>     "gender":"男",
>     "addr":{
>         "country":"中国",
>         "city":"华山派"
>     }
> }
> user2 = {
>     "_id":2,
>     "name":"乔峰",
>     "age":18,
>     "hobbies":["打狗棒法","降龙十八掌"],
>     "gender":"男",
>     "addr":{
>         "country":"中国",
>         "city":"丐帮"
>     }
> }
> user3 = {
>     "_id":3,
>     "name":"段誉",
>     "age":19,
>     "hobbies":["凌波微步","北冥神功"],
>     "gender":"男",
>     "addr":{
>         "country":"中国",
>         "city":"大理皇室"
>     }
> }
> user4 = {
>     "_id":4,
>     "name":"王语嫣",
>     "age":15,
>     "hobbies":["打狗棒法","斗转星移"],
>     "gender":"女",
>     "addr":{
>         "country":"中国",
>         "city":"南方"
>     }
> }
> user5 = {
>     "_id":5,
>     "name":"小龙女",
>     "age":19,
>     "hobbies":["玉女心经","九阴真经"],
>     "gender":"女",
>     "addr":{
>         "country":"中国",
>         "city":"古墓派"
>     }
> }
> user6 = {
>     "_id":6,
>     "name":"小龙女",
>     "age":19,
>     "hobbies":["玉女心经","九阴真经"],
>     "gender":"女",
>     "addr":{
>         "country":"中国",
>         "city":"古墓派"
>     }
> }
> db.user.insert([user1,user2,user3,user4,user5,user6])
> 
> ```
>
> MongoDB查询数据的语法格式如下：
>
> ```javascript
> db.user.find()   // 等同于db.user.find({})
> // 去重
> db.user.distinct("name")
> ```
>
> find()方法以非结构化的方式来显示所有文档
>
> 如果你需要以易读的方式来读取数据，可以使用pretty()方法，语法格式如下：
>
> `db.user.find().pretty()`
>
> pretty()方法以格式化的方式来显示所有文档
>
> *注：在MongoDB中，用到方法都得用$符号开头*
>
> **比较运算**
>
> ```javascript
> =  , !=  ('$ne') , > ('$gt') , < ('$lt') , >= ('$gte') , <= ('$lte')
> db.user.find({"_id":3})
> db.user.find({"age":{"$gt":16}})
> ```
>
> **逻辑运算**
>
> MongoDB中字典内用逗号分隔多个条件是and关系，或者直接用`$and , $or, $not（与或非）`
>
> ```javascript
> // select * from user where id >= 3 and id <= 4;
> db.user.find({"_id":{"$gte":3,"$lte":4}})
> // select * from user where id >=3 and id <=4 and age >=4;
> db.user.find({
>     "_id":{"$gte":3,"$lte":4},
>     "age":{"$gte":4}
> })
> db.user.find({
>     "$and":[
>         {"_id":{"$gte":3,"$lte":4}},
>         {"age":{"$gte":4}}
>     ]
> })
> // select * from user where id >= 0 and id <=1 or id >=4 or name = "乔峰"
> db.user.find({
>    $or:[
>        {"_id":{$gte:0,$lte:1}},
>        {"_id":{$lte:4}},
>        {"name":"乔峰"}
>    ]
> })
> 
> ```
>
> **$type操作符**
>
> MongoDB中可以使用的类型如下表所示：
>
> | 类型                   | 数字 | 备注          |
> | ---------------------- | ---- | ------------- |
> | Double                 | 1    |               |
> | String                 | 2    |               |
> | Object                 | 3    |               |
> | Array                  | 4    |               |
> | Binary data            | 5    |               |
> | Undefined              | 6    | 已废弃        |
> | Object id              | 7    |               |
> | Boolean                | 8    |               |
> | Date                   | 9    |               |
> | Null                   | 10   |               |
> | Regular Expression     | 11   |               |
> | JavaScript             | 13   |               |
> | Symbol                 | 14   |               |
> | JavaScript(with scope) | 15   |               |
> | 32-bit integer         | 16   |               |
> | Timestamp              | 17   |               |
> | 64-bit integer         | 18   |               |
> | Min key                | 255  | Query with -1 |
> | Max key                | 127  |               |
>
> ```javas
> // 查询name是字符串类型的数据
> db.user.find({"name":{$type:2}}).pretty()
> ```
>
> **正则**
>
> 正则定义在`/   /`内
>
> ```javascript
> // select * from user where name regexp '^z.*?(n|u)$/i'
> 匹配规则：z开头,n或者u结尾，不区分大小写
> db.user.find({'name':/^z.*?(n|u)$/i})
> ```
>
> **投影**
>
> MongoDB投影意思是只选择必要的数据而不是选择一整个文件的数据。在MongoDB中，当执行find()方法，那么它会显示一个文档所有字段。要限制这一点，需要设置字段列表值1或者0。 1 用来显示字段 而0是用来隐藏字段，_id会默认显示出来。
>
> ```javascript
> db.user.find({'_id':3},{'_id':0,'name':1,'age':1})
> ```
>
> **数组**
>
> ```javascript
> // 查询数组相关
> // 查询hobbies中有打狗棒法的人
> db.user.find({
>     "hobbies":"打狗棒法"
> })
> // 查询既有打狗棒法又有斗转星移的人
> db.user.find({"hobbies":{$all:["打狗棒法","斗转星移"]}})
> // 查看索引第1个hobby为斗转星移的人（索引从0开始计算）
> db.user.find({"hobbies.1":"斗转星移"})
> // 查看所有人的第1个到第3个爱好，第一个{}表示查询条件为所有，第二个是显示条件（左闭右开）
> db.user.find(
>     {},
>     {
>         "_id":0,
>         "name":0,
>         "age":0,
>         "addr":0,
>         "hobbies":{"$slice":[0,2]},
>     }
> )
> // 查询所有人最后两个爱好
> db.user.find(
>     {},
>     {
>         "_id":0,
>         "name":0,
>         "age":0,
>         "addr":0,
>         "hobbies":{"$slice":-2},
>     }
> )
> // 查询子文档有"country":"中国"的人
> db.user.find({"addr.country":"中国"})
> ```
>
> **排序**
>
> 在MongoDB中使用sort()方法对数据进行排序，sort()方法可以通过参数指定排序的字段，并用1和-1来指定排序的方式，其中1为升序排列，-1为降序排列
>
> ```javascript
> // 按姓名升序排序
> db.user.find().sort({"name":1})
> // 按年龄降序，按ID升序排序
> db.user.find().sort({"age":-1,"_id":1})
> ```
>
> **分页**
>
> limit表示取多少个document，skip代表跳过几个document
>
> 分页公式：`db.user.find().skip((pageNum-1)*pageSize).limit(pageSize)`
>
> **统计**
>
> ```javascript
> // 查询id大于3的人数
> // 方式一
> db.user.count({'_id':{'$gt':3}})
> // 方式二
> db.user.find({'_id':{'$gt':3}}).count()
> ```
>
> **聚合**
>
> 在MongoDB中聚合为aggregate，聚合函数主要用到`$match  $group  $avg  $project(投影，设置哪些字段显示，哪些不显示)  $concat，可以加$match也可以不加$match`
>
> | 表达式    | 描述                                         |
> | --------- | -------------------------------------------- |
> | $sum      | 计算总和                                     |
> | $avg      | 计算平均值                                   |
> | $min      | 获取集合中所有文档对应值的最小值             |
> | $max      | 获取集合中所有文档对应值的最大值             |
> | $push     | 在结果文档中插入值到一个数组中               |
> | $addToSet | 在结果文档中插入值到一个数组中，但不创建副本 |
> | $first    | 根据资源文档的排序获取第一个文档数据         |
> | $last     | 根据资源文档的排序获取最后一个文档数据       |
>
> ```javascript
> // 根据性别分组，算平均年龄和最大年龄
> db.user.aggregate([
>   {$match:{"_id":{$gt:1}}},
>   {$group:{"_id":"$gender","avg_age":{$avg:"$age"},"max_age":{$max:"$age"}}},
> ])
> ```
>
> 

### 索引

##### 创建索引

> 索引通常能够极大的提高查询效率,MongoDB使用ensureIndex()方法来创建索引，其基本语法格式如下所示：
>
> `db.COLLECTION_NAME.ensureIndex({KEY:1})`
>
> 语法中Key值为你要创建的索引字段，1为指定按升序创建索引，-1则为按降序来创建索引。
>
> db.user.ensureIndex({"name":-1})
>
> 我们可以指定所建立索引的名字，如下所示：
>
> `db.user.ensureIndex({"name":1},{"name":"nameIndex"})`
>
> ensureIndex()接收可选参数，可选参数列表如下：
>
> | 参数名称           | 类型          | 描述                                                         |
> | ------------------ | ------------- | ------------------------------------------------------------ |
> | background         | Boolean       | 建索引过程会阻塞其它数据库操作，background可指定以后台方式创建索引，即增加"background"可选参数，"background"默认值为false |
> | unique             | Boolean       | 建立的索引是否唯一，指定为true创建唯一索引，默认值为false    |
> | name               | String        | 索引的名称，如果未指定，MongoDB通过连接索引的字段名和排序顺序生成一个索引名称 |
> | dropDups           | Boolean       | 在建立唯一索引时是否删除重复记录，指定true创建唯一索引，默认值为false |
> | sparse             | Boolean       | 对文档中不存在的字段数据不启用索引；这个参数需要特别注意，如果设置为true的话，在索引字段中不会查询出不包含对应字段的文档。默认值为false |
> | expireAfterSeconds | Integer       | 指定一个以秒为单位的数值，完成TTL设定，设定集合的生存时间    |
> | v                  | index version | 索引的版本号，默认的索引版本取决于mongod创建索引时运行的版本 |
> |                    |               |                                                              |
>
> 

##### 查询索引

> 查询索引的语法格式如下所示：
>
> ```javascript
> db.COLLECTION_NAME.getIndexes()
> db.user.getIndexes()
> ```
>
> 

##### 删除索引

> 删除索引的语法格式如下所示：
>
> ```javascript
> db.COLLECTION_NAME.dropIndex(INDEX_NAME)
> db.user.dropIndex("nameIndex")
> ```
>
> 

