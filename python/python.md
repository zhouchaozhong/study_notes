# Python学习笔记

### 基本语法

> 1. 在Python中严格区分大小写
> 2. 在Python中的每一行就是一条语句，每条语句以换行结束
> 3. Python中一条语句可以分多行编写，语句末尾以 \ 结束
> 4. Python是缩进严格的语言，所以在Python中不要随便缩进
> 5. 在Python中使用#来表示注释

### 数值类型

> 1. 整数（Python中所有整数都是int类型，Python整数大小没有限制）
>    * 如果数字过大，可以用下划线分割，如：a = 123_456_789
>    * 二进制以0b开头，如：0b10
>    * 八进制以0o开头，如：0o7
>    * 十六机制以0x开头，如：0x10
> 2. 浮点数（小数），在Python中所有小数都是float类型
> 3. 复数

### 字符串

> **注意事项：**
>
> 1. Python中的字符串，必须用引号包裹 。如 `s = 'abc'`或`s = "abc"`
>
> 2. 单引号和双引号不能跨行使用，如果要跨行，必须使用换行符连接。如：
>
>    ```python
>    s = 'abcdefg \
>    cefg'
>    
>    str = "fdfdfee \
>    fffff"
>    ```
>
> 3. 使用三重引号（3个单引号或者3个双引号都行）来表示一个长字符可以换行，并且会保留字符串中的格式。如：
>
>    ```python
>    str = '''abcdefff
>    '''
>    ```
>
> 4. 可以使用 \ 作为转义字符，在字符串中使用特殊字符
>
> **字符串格式化**
>
> 1. 可以使用print()函数，传多个参数的形式，如：print("a = ",b,c)
>
> 2. 创建字符串，可以在字符串中指定占位符
>
>    ```python
>    # %s在字符串中表示任意字符
>    # %f 浮点数占位符
>    # %d 整数占位符
>    a = "你好 %s"%"神里绫华"
>    b = "你好 %s 吃饭了吗？%s"%("胡桃","甘雨")
>    print("c = %s"%c)
>    
>    # 可以通过在字符串前添加一个f来创建一个格式化字符串
>    # 在格式化字符串中可以直接嵌入变量
>    a = '神里绫华'
>    b = '甘雨'
>    c = f'hello {a} {b}'
>    print(c)
>    ```
>
> **字符串复制**
>
> ```python
> # 将字符串与数字相乘可以完成字符串的复制
> a = "abc"
> b = a*3
> print(b) # 这里会输出abcabcabc
> ```

### 布尔值和空值

> 1. 布尔值有2个，分别是True和False，布尔值实际上也相当于整型，True相当于1，False相当于0
>
>    ```python
>    a = True
>    b = False
>    ```
>
> 2. 空值用None表示，专门用来表示不存在
>
>    ```python
>    b = None
>    ```

### 类型检查

> type()用来检查值的类型，该函数会将检查的结果作为返回值返回
>
> ```python
> a = 123
> print(type(a))
> ```
>

### 类型转换

> 类型转换的四个函数，int()  float()  str()  bool()

### 运算符

> ​	**算术运算符**
>
> 1. 加法运算符（+），如果是2个字符进行操作，会进行拼接操作
> 2. 减法运算符 ( - )
> 3. 乘法运算符 ( * ) ，如果将字符串与数字进行乘法操作，会将字符串复制指定次数，并返回一个新字符串
> 4. 除法运算符( / ) ，运算结果会返回一个浮点数类型
> 5. 整除运算符(  //  )，运算只会保留整数部分
> 6. 幂运算符 ( ** ) ，求一个值的N次幂
> 7. 取余运算符( % )
>
> **赋值运算符**
>
> 1. 赋值运算符（ =  ,  + =  , /=  , //=  ,  *=  , **=  , %= ）
>
> **关系运算符**
>
> 1. 关系运算符（>  ,   >=  , <  , <= ,  ==,  != ）
> 2. 当对字符串进行比较时，实际上比的是字符串的Unicode编码，比较2个字符串的Unicode编码是逐位比较的
> 3. 相等和不等比较的是对象的值，而不是id
> 4. is 比较2个对象是否是同一个对象，比较的是对象的id
> 5. is not 比较2个对象是否不是同一个对象
>
> **逻辑运算符**
>
> 1. not 逻辑非 (相当于其他语言 ! )
> 2. and  逻辑与 （相当于其他语言 && ）
> 3. or  逻辑或   (相当于其他语言 || )
>
> **条件运算符**
>
> 1. 语法   ： 语句1   if   条件表达式  else  语句2
> 2. 如果条件表达式结果为True则执行语句1 否则执行语句2
>
> ```python
> print("hello") if True else print("how are you?") # 这里输出的结果是hello
> ```

### 条件判断语句

> **if语句**
>
> 语法：if 条件表达式  : 语句
>
> 1. 如果条件表达式结果为True，则会执行冒号后面的语句
> 2. 默认情况下，if只会控制冒号后面的那条语句，如果希望if可以控制多条语句，则可以在if后面紧跟一个代码块
>
> ```python
> # 这里if只会控制 : 后面的1条语句
> num1 = 10
> if num1 > 20: print("num1 > 20")
> 
> # 这里 : 后面的两条语句必须使用缩进，来表示这2条语句都受if控制
> num2 = 20
> if num2 > 10:
>     print("num2 > 10")
>     print("success")
> if num2 > 10 and num2 < 20:
>     print("10 < num2 < 20")
> if 10 < num2 < 20:
>     print("10 < num2 < 20")
> ```
>
> **if-else语句**
>
> ```python
> # 语法
> # if 条件表达式 : 
> # 	代码块
> # else:
> # 	代码块
> num = int(input())
> if num > 10:
>     print("num > 10")
> else:
>     print("num <= 10")
> 
> ```

### 循环语句

> **while语句**
>
> ```python
> # 语法
> # while 条件表达式:
> # 	代码块
> i = 0
> while i < 10:
>  i += 1
>  print(i)
> 
> j = 0
> 
> # 如果while语句后接else，则退出循环后执行else后的代码块
> while j < 10:
>  j += 1
>  print(j)
> else:
>  print("退出循环")
> 
> # break可以用来立即退出循环
> k = 0
> while k < 10:
>  k += 1
>  print(k)
>  if k == 3:
>      break
> 
> # continue表示立即完成本次循环，continue后的语句不再执行
> k = 0
> while k < 10:
>  k += 1
>  if k == 3:
>      continue
>  print(k)
> ```
>
> **for语句**
>
> for 语句用于对序列（例如字符串、元组或列表）或其他可迭代对象中的元素进行迭代

### 序列

> **序列的分类**
>
> 1. 可变序列
>    * 列表【List】
> 2. 不可变序列
>    * 字符串【str】
>    * 元组 【tuple】

### 列表(list)

> 创建列表，通过[]创建一个列表【列表类似于Java中的List】
>
> ```python
> # 创建空列表
> myList = []
> 
> # 创建包含5个元素的列表
> myList2 = [1, 2, 3, 4, 5]
> print(myList2)
> 
> # 列表中可以保存任意类型的对象，但一般保存同一类型的对象
> # 列表中的对象都会按照插入的顺序有序存储到列表中
> myList3 = [1, "hello", 1.1, True, [1, 2, 3]]
> print(myList3)
> 
> # 我们可以通过索引来访问列表中的元素，如果使用的索引超出了最大范围会抛出异常
> print(myList3[1])
> 
> # len()函数可以获取列表长度
> print(len(myList3))
> 
> # 向list中添加元素
> myList.append("你")
> myList.append("好")
> print(myList)
> 
> # 截取list部分内容【切片】
> print(myList2[1:])
> ```
>
> **通用操作**
>
> ```python
> myList1 = [1, 2, 3]
> myList2 = [4, 5, 6]
> # 拼接列表
> myList3 = myList1 + myList2
> print(myList3)  # 输出[1, 2, 3, 4, 5, 6]
> # 将列表重复N次
> myList4 = myList1 * 2
> print(myList4)  # 输出 [1, 2, 3, 1, 2, 3]
> 
> # in 和 not in 检查指定元素是否存在于和不存在于列表中
> print(1 in myList1)  # 输出True
> print(2 not in myList1)  # 输出False
> 
> # len() 获取列表中元素个数
> print(len(myList1))  # 输出3
> 
> # max() 获取列表中最大值
> print(max(myList1))  # 输出3
> 
> # min() 获取列表中最小值
> print(min(myList1))  # 输出1
> 
> # index()方法，获取指定元素在列表中第一次出现的索引
> print(myList1.index(2))  # 输出1
> 
> # count()方法，统计指定元素在列表中出现的次数
> print(myList1.count(2))  # 输出1，因为这里2在列表中总共出现了1次
> 
> # 删除元素
> del myList1[2] # 删除索引为2的元素
> 
> # 通过切片修改列表，给切片赋值，必须是一个序列
> myList1[0:1]=[5, 6] # myList1变为[5,6,2,3]
> 
> # 遍历列表
> myList5 = [5, 6, 7]
> for item in myList5:
>     print(item) # 输出5 6 7
> ```

### range

> range()是一个函数，可以用来生成一个自然数序列
>
> ```python
> r = range(5)
> print(list(r))  # 输出[0, 1, 2, 3, 4]
> 
> for i in range(5):
>     print(i)  # 输出 0 1 2 3 4
> ```

### 元组(tuple)

> 元组是一个不可变序列，它的操作方式基本上跟list一样
>
> ```python
> # 创建空元组
> myTuple = ()
> myTuple = (1, 2, 3)
> print(myTuple)
> # myTuple[0] = 2 这里不能重新赋值，会报错，因为元组是不可变序列
> 
> # 当元组不为空时，括号可以省略
> myTuple1 = 1, 2, 3, 4, 5
> myTuple2 = 1,  # 当元组中只有一个元素，并且要省略括号时，必须要有一个逗号
> print(myTuple1)
> print(myTuple2)
> 
> # 元组的解构
> a, b, c, d, e = myTuple1  # 这里的变量个数和元组里面的元组个数必须一样，否则会报错
> print(a)  # 输出 1
> print(b)  # 输出 2
> print(c)  # 输出 3
> print(d)  # 输出 4
> print(e)  # 输出 5
> 
> # 交换a和b的值，可以利用元组的解构
> a, b = b, a
> print(a)
> 
> # 如果我们只想要解构元组的部分元组可以这样写，这样变量个数可以不和元组里的元素个数一样
> # 不能同时出现2个及以上的*变量
> a, b, *c = myTuple1
> *a, b, c = myTuple1
> a, *b, c = myTuple1
> ```

### ==和is

> ```python
> == 和 != 比较的是对象的值是否相等
> is 和 is not 比较的是对象的id是否相等，也就是判断是否是同一个对象
> ```

### 字典（dict）

> - 字典属于一种新的数据结构，称为映射
> - 字典的作用和列表类似，都是用来存储对象的容器【类似于Java中的Map】
>
> **基本使用**
>
> ```python
> # 创建空字典
> d = {}
> print(type(d))
> # 字典的value可以是任意对象
> # 字典的key可以是任意的不可变对象
> d1 = {'name': '神里绫华', 'age': 18, 'gender': 'female'}
> print(d1['name'])
> 
> # 可以使用dict()函数来创建字典
> d2 = dict(name='胡桃', age=18, gender='female')
> print(d2)
> 
> # 也可以将一个包含双值子序列的序列转换为字典
> d3 = dict([('name', '宵宫'), ('country', 'DaoQi')])
> print(d3)
> 
> # len()获取字典键值对个数
> print(len(d3))
> 
> # in 和 not in 检查字典中是否包含指定的key
> print('name' in d3)
> 
> # 获取字典值 这种方式获取字典值，如果key不存在会报错
> print(d3['name'])
> 
> # 获取字典值方式2 这种方式获取，如果key不存在不会报错，会返回None
> # 也可以为第二个参数设置默认值，如果找不到会返回默认值
> print(d3.get('a'))
> print(d3.get('a', '默认值'))
> 
> # 修改字典值
> d3['name'] = '雷电将军'
> print(d3)
> 
> # setDefault() ，如果字典中存在该key，则返回key的值，不会对字典做任何操作
> # 如果key不存在，会向字典中添加key，并设置值
> t = d3.setdefault('name', '八重神子')
> print(t)
> 
> # update() 将其他字典的key - value添加到当前字典中
> # 如果有重复的key，则后面的会替换前面的
> d5 = {'name': '申鹤', 'age': 16}
> d6 = {'country': 'LiYue', 'hobby': 'music'}
> d5.update(d6)
> print(d5)
> 
> # 删除字典值
> del d5['hobby']
> print(d5)
> 
> # 根据key删除字典中的key - value
> # pop(key,default) 如果没有指定默认值，如果key不存在会报错，如果指定了默认值，key不存在不会报错，而是会直接返回默认值
> t = d5.pop('country')
> print(t)
> 
> # clear() 清空字典，会删除字典中的所有key-value
> d5.clear()
> ```
>
> **遍历字典**
>
> ```python
> # keys()该方法会返回字典的所有key
> d = {'name': '甘雨', 'age': 16}
> print(d.keys())
> for k in d.keys():
>     print(d[k])
> # values()该方法会返回字典的所有value
> for v in d.values():
>     print(v)
> # items() 会返回一个包含双值子序列的序列
> for k, v in d.items():
>     print(k, '=', v)
> ```

### 集合（set）

> - 集合和列表非常相似
> - 不同点
>   - 集合只能存储不可变对象
>   - 集合中存储的对象是无序的
>   - 集合中不能出现重复元素
>
> ```python
> # 使用{}创建集合
> s = {1, 2, 3}
> print(type(s))
> 
> # 可以使用set()函数创建空集合，也可以将序列和字典转换为集合
> s1 = set()
> print(type(s1))
> 
> s2 = set([1, 2, 3, 1, 2])
> print(s2)  # 输出 {1, 2, 3}
> 
> # 使用in 和 not in 来检查集合中的元素
> print(1 in s2)
> 
> # 使用len()获取集合中元素的个数
> print(len(s2))
> 
> # add()向集合中添加元素
> s2.add(5)
> print(s2)
> 
> # update()将一个集合中的元素添加到当前集合中
> # update()可以传递序列和字典作为参数，传递字典只会加入key
> s3 = {5, 6, 8}
> s2.update(s3)
> print(s2)
> 
> # pop() 删除集合中的一个元素并返回
> t = s2.pop()
> print(t)
> 
> # remove()删除集合中的指定元素
> s2.remove(3)
> print(s2)
> 
> # clear()清空集合
> s2.clear()
> print(s2)
> 
> # copy() 对集合进行浅复制
> ```
>
> **集合的运算**
>
> ```python
> # 创建2个集合
> s1 = {1, 2, 3, 5, 8, 10}
> s2 = {1, 5, 8, 11, 12}
> 
> # 对集合求交集
> t = s1 & s2
> print(t)
> 
> # 对集合求并集
> t = s1 | s2
> print(t)
> 
> # 对集合求差集
> t = s1 - s2
> print(t)
> 
> # 异或集,即两个集合不一样的元素组成的集合
> t = s1 ^ s2
> print(t)
> 
> # <= 检查一个集合是否是另一个集合的子集
> s3 = {1, 2}
> s4 = {1, 2, 3}
> print(s3 <= s4)
> ```

### 函数

> ```python
> # 定义函数
> def fn():
>     print("hello world")
> 
> 
> # 可以为形参指定默认值
> def add(a, b=2):
>     print(a)
>     print(b)
>     print(a + b)
> 
> 
> # 不定长参数
> # 带*的形参只能有一个
> # 可变参数不是必须写在最后，但带*参数之后的参数，必须以关键字参数形式传递
> # *形参只能接收位置参数，而不能接收关键字参数
> def fn2(*a):
>     print(a)
> 
> 
> # **形参可以接收其他的关键字参数
> # **形参只能有一个，并且必须写在所有参数的最后
> def fn4(**a):
>     print(type(a))
> 
> 
> def fn3(a, *b, c):
>     print(b)
> 
> 
> # 调用函数
> fn()
> add(1, 5)
> 
> # 关键字参数，可以在调用函数时加上关键字，就可以不用按形参定义的顺序来传递参数
> add(b=5, a=1)
> 
> fn2(1, 2, 3, 5)
> 
> # 可以通过*来对元素进行解包来传递参数
> myTuple = (1, 5)
> add(*myTuple)
> 
> # 可以通过**来对字典进行解包传参
> d = {'a': 100, 'b': 200}
> add(**d)
> 
> 
> # 函数的返回值
> # 可以通过return来指定函数返回值，也可以通过一个变量来接收函数返回值
> # 如果仅仅写return后面不跟值，或者不写return相当于返回return None
> def fn6(a, b):
>     return a + b
> 
> 
> t = fn6(3, 5)
> print(t)
> ```
>

### 文档字符串

> 在定义函数时，可以在函数内部编写文档字符串，文档字符串就是函数的说明，当我们编写了文档字符串时，就可以通过help()函数来查看函数的说明
>
> ```python
> def fn(a: int, b: bool, c: str) -> int:
>     """
>    这是一个文档字符串说明
>    参数a 作用 类型:
>    参数b:
>    参数c:
>    """
> 
> 
> help(fn)
> ```
>

### 高阶函数

> 概念：接收函数作为参数，或者将函数作为返回值的函数是高阶函数，将函数作为返回值返回的函数也叫闭包
>
> ```python
> def fn(func, v):
>     func(v)
> 
> 
> def fn3(v):
>     if v % 2 == 0:
>         print("%d是偶数！" % v)
> 
> 
> fn(fn3, 2)
> ```

### 匿名函数

> lambda 表达式（有时称为 lambda 构型）被用于创建匿名函数。 表达式 lambda parameters: expression 会产生一个函数对象 。 该未命名对象的行为类似于用以下方式定义的函数:
>
> ```python
> def <lambda>(parameters):
>     return expression
> ```
>
> ```python
> myList = [1, 2, 3, 4, 5, 6]
> r = filter(lambda i: i % 3 == 0, myList)
> print(list(r))
> ```

### 装饰器

> 装饰器本质上是一个Python函数(其实就是闭包)，它可以让其他函数在不需要做任何代码变动的前提下增加额外功能，装饰器的返回值也是一个函数对象。装饰器用于有以下场景，比如:插入日志、性能测试、事务处理、缓存、权限校验等场景，装饰器可以嵌套使用
>
> ```python
> def fnA(fn):
>     def decorator(*args, **kwargs):
>         print("=========start calculate==========")
>         fn(*args, **kwargs)
>         print("==========end============")
>     return decorator
> 
> 
> @fnA
> def fnB(a, b):
>     print("a + b = ", a + b)
> 
> 
> fnB(2, 3)
> 
> ```

### 类

> **类的定义**
>
> ```python
> class Person:
>     print("代码块中的代码执行！")
> 
>     # 在类中可以定义一些特殊方法（魔术方法）
>     # 特殊方法都是以__开头，__结尾
>     # 特殊方法不需要自己调用
> 
>     # 创建对象的流程
>     # 1. 创建一个变量
>     # 2. 在内存中创建一个新对象
>     # 3. 执行类的代码块中的代码（实际上不创建对象也会执行代码块中的代码，这个属于类的固有属性，定义类的时候就会执行）
>     # 4. __init__(self)方法执行
>     # 5. 将对象的id复制给变量
>     def __init__(self, name, age, city, address):
>         self.name = name
>         self.age = age
>         # 以双下划线开头的属性表示隐藏属性，不能在类外部访问
>         # 一把以下划线开头的属性都是私有属性
>         self.__address = address
>         self._city = city
> 
>     def getAddress(self):
>         return self.__address
> 
>     def setAddress(self, address):
>         self.__address = address
> 
>     # 这里的self相当于其他语言的this指针
>     def sayHello(self):
>         print(self.name + "hello")
> 
>     # 添加property装饰器之后，可以像调用属性一样，使用get方法
>     @property
>     def city(self):
>         print("get方法执行了----")
>         return self._city
> 
>     # setter方法装饰器 @属性名.setter
>     @city.setter
>     def city(self, city):
>         print("set 方法执行了=====")
>         self._city = city
> 
> 
> p1 = Person("宵宫", 16, "稻妻城", "稻妻")
> # 实际上以__开头的属性也能在类外部访问(通过_类名__属性名访问)
> print(p1._Person__address)
> 
> 
> print(p1.getAddress())
> p2 = Person("影", 18, "海祇岛", "稻妻")
> p2.setAddress("璃月")
> print(p2.getAddress())
> 
> p1.sayHello()
> p2.sayHello()
> 
> p1.city = "蒙德城"
> print(p1.city)
> 
> ```
>
> 