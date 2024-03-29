# Redis学习笔记

备注：所有命令可以参考   http://redisdoc.com

### 常用命令

| 命令                                                         | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| keys *                                                       | 查看存在的键                                                 |
| exists key名                                                 | 判断某个key是否存在                                          |
| move key db                                                  | 移动key到其他库                                              |
| expire key 时间（秒）                                        | 为给定的key设置过期时间                                      |
| ttl key                                                      | 查看这个key还有多少秒过期                                    |
| type key                                                     | 查看key的类型                                                |
| set key value                                                | 添加或覆盖某个key                                            |
| append key value                                             | 在某个key的值后面追加value的值                               |
| incr key                                                     | 将某个key的值加1(该key的值必须是整数)                        |
| decr key                                                     | 将某个key的值减1                                             |
| incrby key 3                                                 | 将某个key的值加3                                             |
| decrby key 3                                                 | 将某个key的值减3                                             |
| del key                                                      | 删除某个key                                                  |
| GET key                                                      | 返回 key 所关联的字符串值                                    |
| getrange key 0 2                                             | 获取某个key 0 2 区间内的值（例如 getrange k1 0 2  k1的值是123456  则最终的值是123） |
| setrange key 2  xxxx                                         | 设置某个key 从第2个位置的值(例如 setrange k1 2 xxx k1的值是123456 则最终的值是12xxx6) |
| SETEX key seconds value                                      | 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。 如果 key 已经存在， SETEX 命令将覆写旧值。 |
| setnx key value                                              | 设置某个key的值，如果该值存在则不做任何动作，如果不存在则设置 |
| MSET key value [key value ...]                               | 同时设置一个或多个 key-value 对。如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值 |
| MGET key [key ...]                                           | 返回所有(一个或多个)给定 key 的值。如果给定的 key 里面，有某个 key 不存在， 那么这个 key 返回特殊值 nil 。 |
| MSETNX key value [key value ...]                             | 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。即使只有一个给定 key 已存在， MSETNX 也会拒绝执行所有给定 key 的设置操作。 |
| LRANGE key start stop                                        | 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。 |
| LPUSH key value [value ...]                                  | 将一个或多个值 value 插入到列表 key 的表头如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头 |
| RPUSH key value [value ...]                                  | 将一个或多个值 value 插入到列表 key 的表尾(最右边)。         |
| LPOP key                                                     | 移除并返回列表 key 的头元素。                                |
| RPOP key                                                     | 移除并返回列表 key 的尾元素。                                |
| LINDEX key index                                             | 返回列表 key 中，下标为 index 的元素。                       |
| LLEN key                                                     | 返回列表 key 的长度。                                        |
| LREM key count value                                         | 根据参数 count 的值，移除列表中与参数 value 相等的元素。count 的值可以是以下几种：count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。count = 0 : 移除表中所有与 value 相等的值。 |
| LTRIM key start stop                                         | 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。 |
| RPOPLPUSH source destination                                 | 命令 RPOPLPUSH 在一个原子时间内，执行以下两个动作：将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端。将 source 弹出的元素插入到列表 destination ，作为 destination 列表的的头元素。 |
| LSET key index value                                         | 将列表 key 下标为 index 的元素的值设置为 value 。            |
| LINSERT key BEFORE 或 AFTER pivot value                      | 将值 value 插入到列表 key 当中，位于值 pivot 之前或之后。 从左到右寻找，找到第一个值后，执行操作，后面有相同的值不再做任何动作当 pivot 不存在于列表 key 时，不执行任何操作。 当 key 不存在时， key 被视为空列表，不执行任何操作。如果 key 不是列表类型，返回一个错误。 |
| SADD key member [member ...]                                 | 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略 |
| SMEMBERS key                                                 | 返回集合 key 中的所有成员                                    |
| SCARD key                                                    | 返回集合 key 的基数(集合中元素的数量)。                      |
| SREM key member [member ...]                                 | 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。 |
| SRANDMEMBER key [count]                                      | 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。 |
| SPOP key                                                     | 移除并返回集合中的一个随机元素。                             |
| SMOVE source destination member                              | 将 member 元素从 source 集合移动到 destination 集合。        |
| SDIFF key [key ...]                                          | 返回一个集合的全部成员，该集合是所有给定集合之间的差集       |
| SINTER key [key ...]                                         | 返回一个集合的全部成员，该集合是所有给定集合的交集           |
| SUNION key [key ...]                                         | 返回一个集合的全部成员，该集合是所有给定集合的并集。         |
| HSET key field value                                         | 将哈希表 key 中的域 field 的值设为 value 。                  |
| HGET key field                                               | 返回哈希表 key 中给定域 field 的值。                         |
| HMSET key field value [field value ...]                      | 同时将多个 field-value (域-值)对设置到哈希表 key 中。        |
| HMGET key field [field ...]                                  | 返回哈希表 key 中，一个或多个给定域的值。                    |
| HGETALL key                                                  | 返回哈希表 key 中，所有的域和值。                            |
| HDEL key field [field ...]                                   | 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。    |
| HLEN key                                                     | 返回哈希表 key 中域的数量。                                  |
| HEXISTS key field                                            | 查看哈希表 key 中，给定域 field 是否存在。                   |
| HKEYS key                                                    | 返回哈希表 key 中的所有域。                                  |
| HVALS key                                                    | 返回哈希表 key 中所有域的值。                                |
| HINCRBY key field increment                                  | 为哈希表 key 中的域 field 的值加上增量 increment 。增量也可以为负数，相当于对给定域进行减法操作。 |
| HINCRBYFLOAT key field increment                             | 为哈希表 key 中的域 field 加上浮点数增量 increment 。        |
| HSETNX key field value                                       | 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。 |
| ZADD key score member [[score member] [score member] ...]    | 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。 |
| ZRANGE key start stop [WITHSCORES]                           | 返回有序集 key 中，指定区间内的成员。                        |
| ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]  | 返回有序集 key 中，所有 score 值介于 min 和 max 之间 (包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。 |
| ZREM key member [member ...]                                 | 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。    |
| ZCARD key                                                    | 返回有序集 key 的基数。                                      |
| ZCOUNT key min max                                           | 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。 |
| ZRANK key member                                             | 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。 |
| ZSCORE key member                                            | 返回有序集 key 中，成员 member 的 score 值。                 |
| ZREVRANK key member                                          | 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递减(从大到小)排序。 |
| ZREVRANGE key start stop [WITHSCORES]                        | 返回有序集 key 中，指定区间内的成员。其中成员的位置按 score 值递减 (从大到小)来排列。 |
| ZREVRANGEBYSCORE key max min [WITHSCORES] [LIMIT offset count] | 返回有序集 key 中， score 值介于 max 和 min 之间 (默认包括等于 max 或 min )的所有的成员。有序集成员按 score 值递减(从大到小)的次序排列。 |

### 事务

> ```shell
> > MULTI    //开启事务
> > OK
> > INCR foo  //操作指令
> > QUEUED
> 
> > INCR bar
> > QUEUED
> 
> > EXEC   //提交事务
> 
> 1) (integer) 1
> 2) (integer) 1
> 
> 
> redis> SET foo 1
> OK
> 
> redis> MULTI
> OK
> 
> redis> INCR foo
> QUEUED
> 
> redis> DISCARD   //放弃事务  当执行 DISCARD 命令时，事务会被放弃，事务队列会被清空，并且客户端会从事务状态中退出
> OK
> 
> redis> GET foo
> "1"
> 
> 被 WATCH 的键会被监视，并会发觉这些键是否被改动过了。 如果有至少一个被监视的键在 EXEC 执行之前被修改了， 那么整个事务都会被取消， EXEC 返回空多条批量回复（null multi-bulk reply）来表示事务已经失败。
> ```
>
> 