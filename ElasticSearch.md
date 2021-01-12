# ElasticSearch学习笔记

> 字段类型
>
> * 字符串类型   【 ==text==   ==keyword== 】
> * 数值类型       【  ==long==  ==integer==  ==short==  ==byte==  ==double==  ==float==   ==half float==   ==scaled float== 】
> * 日期类型       【  ==date== 】
> * 布尔值类型    【   ==boolean== 】
> * 二进制类型    【  ==binary==  】



> 创建索引规则
>
> ```json
> PUT /test2
> {
>   "mappings": {
>     "properties": {
>       "name" : {
>         "type":"text"
>       },
>       "age" : {
>         "type" : "long"
>       },
>       "birthday" : {
>         "type" : "date"
>       }
>     }
>   }
> }
> ```
>
> 创建数据
>
> ```json
> PUT /charles/user/3
> {
>   "name" : "赵飞燕",
>   "age" : 20,
>   "desc" : "沉鱼落雁",
>   "tags": ["漂亮","四大美人","靓女"]
>   
> }
> ```
>
> 更新数据
>
> ```json
> POST /test3/_doc/1/_update
> {
>   "doc":{
>     "name":"法外狂徒张三"
>   }
> }
> ```
>
> 删除数据
>
> ```json
> DELETE /test3/_doc/1
> ```
>
> 查询数据
>
> ```json
> GET /charles/user/_search?q=name:轻狂书生
> 
> GET /charles/user/_search
> {
>   "query": {
>     "match": {
>       "name": "轻狂"
>     }
>   },
>   "_source": ["name","desc"],
>   "sort": [
>     {
>       "age": {
>         "order": "desc"
>       }
>     }
>   ],
>   "from": 0,
>   "size": 2
> }
> 
> 
> GET /charles/user/_search
> {
>   "query": {
>     "bool": {
>       "must": [
>         {
>           "match": {
>             "name": "轻狂"
>           }
>         },
>         {
>           "match": {
>             "age": 23
>           }
>         }
>       ]
>     }
>   }
> }
> 
> 
> GET /charles/user/_search
> {
>   "query": {
>     "bool": {
>       "must": [
>         {
>           "match": {
>             "name": "轻狂"
>           }
>         }
>       ],
>       "filter": [
>         {
>           "range": {
>             "age": {
>               "gte": 10,
>               "lte": 20
>             }
>           }
>           
>         }
>       ]
>     }
>   }
> }
> ```
>
> 两个类型 ==text==     ==keyword==       
>
> text会被分词器解析，keyword会被当成一个整体搜索



**关于分词**

* term  直接查询精确的
* match   会使用分词器解析！（先分析文档，然后在通过分析的文档进行查询！）

 **关键词高亮功能**

```json
GET /charles/user/_search
{
  "query": {
    "match": {
      "name": "轻狂"
    }
  },
  "highlight": {
    "pre_tags": "<p class='key' style='color:red'>", 
    "post_tags": "</p>", 
    "fields": {
      "name":{}
    }
  }
}
```



