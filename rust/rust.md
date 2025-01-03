# Rust学习笔记

### 变量与不可变性

> 1. 在Rust中，使用Iet关键字来声明变量
>
> 2. Rust支持类型推导，但你也可以显式指定变量的类型：
>
>    `let x:i32 = 5; //显式指定x的类型为i32`
>
> 3. 变量名蛇形命名法(Snake Case，小写字母加下划线)，而枚举和结构体命名使用帕斯卡命名法(Pascal Case，每个单词首字母都大写) 如果变量没有用到可以前置下划线，消除警告
>
> 4. 强制类型转换
>
>    ` let a = 3.1; let b = a as i32;`
>
> 5. rust中的变量，默认是不可变的
>
> 6. 如果你希望一个变量是可变的，则需要明确使用mut关键字进行明确声明
>
>    ```rust
>    let mut y = 10; // 可变变量
>    y = 20; // 合法的修改
>    ```
>
> 7. rust允许声明一个与现有变量同名的新变量，从而有效的隐藏前一个变量，并且可以改变值，类型和可变性
>
>    ```rust
>    fn main() {
>        let x = 5;
>        println!("{}",x);		// 输出5
>        {
>            let x = 10;
>            println!("{}",x);	// 输出10
>        }
>        println!("{}",x);	// 输出5
>    }
>    ```
>
> 8. 未使用的变量，想屏蔽编译器报错提示，可以在变量名前面加下划线_

### 常量与静态变量

> 1. 常量的值必须是在编译时已知的常量表达式，必须指定类型与值
> 2. 与C语言的宏定义（宏替换）不同，Rust的cost常量的值被直接嵌入到生成的底层机器代码中，而不是进行简单的字符替换
> 3. 常量名与静态变量命名必须全部大写，单词之间加入下划线
> 4. 常量的作用域是块级作用域，它们只在声明它们的作用域内可见
>
> **静态变量**
>
> 1. 与const常量不同，static变量是在运行时分配内存的
> 2. 并不是不可变的，可以使用unsafe修改
> 3. 静态变量的生命周期为整个程序的运行时间
>
> ```rust
> static MY_STATIC: i32 = 100;
> static mut MY_MUT_STATIC: i32 = 50;
> fn main() {
>     const SECOND_HOUR: usize = 3600;
>     const SECOND_DAY: usize = 24 * SECOND_HOUR;
>     println!("{}",SECOND_DAY);
>     {
>         const SE:usize = 1000;
>         println!("{}",SE);
>     }
>     // println!("{}",SE);   // 不在作用域内会报错
>     println!("{MY_STATIC}");
>     unsafe {
>         MY_MUT_STATIC = 60;
>         println!("{MY_MUT_STATIC}");
>     }
> }
> ```

### 基础数据类型

> * 有符号整型：默认推断为i32
>   * i8、i16、i32、i64、i128
> * 无符号整型：u8、u16、u32、u64、u128
> * 平台特定的整型，大小由平台决定
>   * usize、isize
> * 浮点类型
>   * f32和f64
> * 布尔类型：true和false
> * 字符类型
>   * rust支持unicode字符
>   * 表示char类型使用单引号
>
> ```rust
> fn main() {
>     // 整型
>     let a1 = -125;
>     let a2 = 0xff;  // 16进制
>     let a3 = 0o13;  // 8进制
>     let a4 = 0b10;  // 2进制
>     println!("{a1} {a2} {a3} {a4}");
>     println!("u32 max {}",u32::MAX);
>     println!("u32 min {}",u32::MIN);
>     println!("i32 max {}",i32::MAX);
>     println!("i32 min {}",i32::MIN);
>     println!("usize max {}",usize::MAX);
>     println!("u64 max {}",u64::MAX);
> 
>     // 浮点型
>     let f1: f32 = 1.23234;
>     let f2: f64 = 1.888899999;
>     println!("Float f1,f2 are {:.2} {:.2}",f1,f2);  // 四舍五入保留两位小数
> 
>     // 布尔型
>     let is_ok: bool = true;
>     let can_ok: bool = false;
>     println!("{is_ok} {can_ok}");
> 
>     // 字符型
>     let ch: char = 'C';
>     println!("char is {}",ch)
> }
> ```

### 元组与数组

> * 数组是固定长度的同构集合
> * 元组是固定长度的异构集合
>
> ```rust
> fn main() {
>     // 元组
>     let tup: (i32, &str, f64) = (0,"hi",3.4);
>     println!("tup elements {} {} {}",tup.0,tup.1,tup.2);
> 
>     let mut tup2: (i32, &str, f64, bool) = (1,"hello",5.6,true);
>     println!("tup2 elements {} {} {} {}",tup2.0,tup2.1,tup2.2,tup2.3);
>     tup2.1 = "world";
>     println!("tup2 elements {} {} {} {}",tup2.0,tup2.1,tup2.2,tup2.3);
> 
>     // 数组
>   	let mut arr:[i32;5] = [0;5];
>     let mut arr = [1,2,3];
>     arr[0] = 7;
>     println!("arr len = {} first element is {}",arr.len(),arr[0]);
>     for item in arr {
>         println!("{}",item)
>     }
> 
>     let arr1: [i32; 3] = [2;3];
>     for item in arr1 {
>         println!("{}",item)
>     }
> 
>     // ownership
>     let arr3 = [1,2,3];
>     let tup3 = (2,"ff");
>     println!("arr : {:?}",arr3);
>     println!("tup : {:?}",tup3);
>     let arr_ownership = arr3;
>     let tup_ownership = tup3;
>     // 这里简单数据类型进行赋值操作不会进行所有权转移
>     println!("arr : {:?}",arr3);
>     println!("tup : {:?}",tup3);
> 
>     let string_item = String::from("aaa");
>     println!("{}",string_item);
>     let string_item_tt = string_item;
>     // 这里进行了move操作，进行了所有权转移，所以这里使用string_item会报错
>     // println!("{}",string_item)
> }
> ```

### 字符串的两种类型
> * ·Rust的核心语言层面，只有一个字符串类型：字符串切片sr(或&sr)
> * 字符串切片：对存储在其它地方、UTF-8编码的字符串的引用
>   * 字符串字面值：存储在二进制文件中，也是字符串切片
> * String类型：
>   * 来自标准库而不是核心语言
>   * 可增长、可修改、可拥有
>   * UTF-8编码
> * 其他类型字符串：Rust的标准库还包含了很多其它的字符串类型，例如：OsString、OsStr、CString、CStr
>   * String vs Str后缀：拥有或借用的变体
>   * 可存储不同编码的文本或在内存中以不同的形式展现
>   * Library crate（第三方库）针对存储字符串可提供更多的选项
>
> * String是一个堆分配的可变字符串类型
> * &str是指字符串切片引用，是在栈上分配的
>   * 不可变引用，指向存储在其他地方的UTF-8编码的字符串数据
>   * 由指针和长度构成
>
> ```rust
> fn main() {
>    let name = String::from("value C++");
>    let course = "Rust".to_owned();
>    let new_name = name.replace("C++", "CPP");
>    println!("{name} {course} {new_name}");
>    let rust = "\x52\x75\x73\x74";
>    println!("{rust}");
>    let color = "green".to_string();
>    let name = "John";
>    let p = Person{
>       name:name,
>       color:color,
>       age:18,
>    };
> 
>    let value = "hello world!".to_owned();
>    print(&value);
>    print("how are you");
>    print_string_borrow(&value);
> 
>    // print_string_borrow("hi!");   这个会报错
> }
> 
> struct Person<'a> {
>    // 字符串字面量必须要标明生命周期, 'a，这里的name的生命周期表明与结构体Person相同
>    name:&'a str,
>    color:String,
>    age:i32,
> }
> 
> // 可以传&String和&str
> fn print(data:&str){
>    println!("{}",data)
> }
> 
> // 只能传&String
> fn print_string_borrow(data:&String){
>    println!("{}",data)
> }
> ```
> ```rust
> fn main() {
>    let mut s1 = String::from("Hello");
>    let mut s2 = String::from("World");
>    s1.push('a');
>    s1.push_str(" string"); 
> 
>    // 拼接之后，s1被借用，发生了所有权转移，s1将不可用
>    let s3 = s1 + &s2;
>    println!(" s3: {}",s3);
>    println!(" s2: {}",s2);
> 
>    let s4 = String::from("宵宫");
>    let s5 = String::from("雷电将军");
>    let s6 = String::from("刻晴");
>    let s7 = format!("{}-{}-{}",s4,s5,s6);
>    println!(" s7: {}",s7);
> }
> ```
>
> 

### 函数

> **函数的返回值**
>
> * 在->符号后边声明函数返回值的类型，但是不可以为返回值命名
>
> * 在Rust里面，返回值就是函数体里面最后一个表达式的值，若想提前返回，需使用return关键字，并指定一个值
>
>   大多数函数都是默认使用最后一个表达式最为返回值
>
> ```rust
> fn main() {
>     let r = add(1,2);
>     println!("{}",r);
>     let r2 = minus(5,3);
>     println!("{}",r2);
> }
> 
> fn add(a:i32,b:i32) -> i32{
>    // 最后一个表达式作为返回值可以不加分号，会自动加上
>    a + b
> }
> 
> fn minus(a:i32,b:i32) -> i32{
>     return a - b
> }
> ```

### 控制流

> **if表达式**
>
> ```rust
> fn main() {
>  let num = 3;
>  if num < 5 {
>      println!("条件为真");
>  }else {
>      println!("条件为假");
>  }
> }
> ```
>
> ```rust
> fn main() {
>  let num = 6;
>  if num % 4 == 0 {
>    println!("num is divisible by 4");
>  } else if num % 3 == 0 {
>    println!("num is  divisible by 3");
>  }else if num % 2 == 0 {
>    println!("num is divisible by 2");
>  }else {
>    println!("num is not divisible by 4, 3 or 2");
>  }
> }
> ```
>
> ```rust
> fn main() {
>  let condition = true;
>  let number = if condition { 5 } else { 6 };
>  println!("The value of number is: {}", number);
> }
> ```
>
> **循环**
>
> ```rust
> fn main() {
>     let mut counter = 0;
>     loop {
>         counter += 1;
>         if counter >= 10 {
>             break;
>         }
>         println!("loop循环 {}", counter);
>     }
> 
>     let mut counter = 0;
>     while counter < 10 {
>         counter += 1;
>         println!("while条件循环 {}", counter);
>     }
> 
>     for i in (1..10).rev() {
>         println!("for循环 {}", i);
>     }
> }
> ```

### 内存分配与所有权

> Copy trait：可以用于像整数这样完全存放在stack上面的类型，如果一个类型实现了Copy这个trait,.那么旧的变量在赋值后仍然可用
>
> Drop trait：如果一个类型或者该类型的一部分实现了Drop trait，那么RUst不允许让它再去实现Copy trait了
>
> 一些拥有Copy trait的类型：
>
> * 任何简单标量的组合类型都可以是Copy的
> * 任何需要分配内存或某种资源的都不是Copy的
> * 所有的整数类型，例如U32
> * bool
> * char
> * 所有的浮点类型，例如f64
> * Tuple(元组)，如果其所有的字段都是Copy的
>   * (i32,i32)是
>   * (i32,String)不是
>
> **函数与所有权**
>
> ```rust
> fn main() {
>     let s = String::from("hello");
>     take_ownership(s);
>     // println!("{}", s);  这里所有权发生了转移，所以不能使用了
>     let x = 5;
>     make_copy(x);
>     // 这里是i32类型，会执行copy操作，所以没有发生所有权转移，依然可以使用
>     println!("{}", x);
> }
> 
> fn take_ownership(some_string: String) {
>     println!("{}", some_string);
> }
> 
> fn make_copy(some_integer: i32) -> i32 {
>     some_integer
> }
> ```
>
> **返回值与作用域**
>
> * 函数在返回值的过程中同样也会发生所有权的转移
> * 一个变量的所有权总是遵循同样的模式
> * 把一个值赋给其它变量时就会发生移动
> * 当一个包含heap数据的变量离开作用域时，它的值就会被drop函数清理，除非数据的所有权移动到另一个变量上了
>
> **函数使用某个值，但不获得所有权**
>
> ```rust
> fn main() {
>     let s = String::from("hello");
>     let (s2, len) = calc_length(s);
>     println!("{} {}", s2, len);
> }
> 
> fn calc_length(s: String) -> (String, usize) {
>     let length = s.len();
>     (s, length)
> }
> ```
>
> **引用与借用**
>
> * &符号就表示引用：允许你引用某些值而不取得其所有权
> * 我们把引用作为函数参数这个行为叫做借用
> * 默认是不可以修改借用的变量
> * 可变引用有一个重要的限制：在特定作用域内，对某一块数据，只能有一个可变的引用( 同一个作用域内，针对同一个变量，只能有一个可变引用，这是为了防止数据竞争 )
> * 不可以同时拥有一个可变引用和一个不变的引用
>
> ```rust
> fn main() {
>     let s1 = String::from("hello");
>     let len = calc_length(&s1);
>     println!("The length of '{}' is {}.", s1, len);
> }
> 
> fn calc_length(s: &String) -> usize {
>     s.len()
> }
> ```
>
> ```rust
> fn main() {
>     let mut s1 = String::from("hello");
>     let len = calc_length(&mut s1);
>     println!("The length of '{}' is {}.", s1, len);
> }
> 
> fn calc_length(s: &mut String) -> usize {
>     s.push_str(",world");
>     s.len()
> }
> ```
>
> **字符串切片**
>
> ```rust
> fn main() {
>     let mut s = String::from("hello world");
>     let hello = &s[0..5];
>     let hello1 = &s[..5];
>     let world = &s[6..11];
>     let world1 = &s[6..];
>     let whole = &s[..];
>     println!("{} {} {} {} {} ", hello,hello1, world,world1, whole);
>     let first = first_word(&s);
>     println!("{}", first);
> }
> 
> fn first_word(s: &String) -> &str {
>     let bytes = s.as_bytes();
>     for (i, &item) in bytes.iter().enumerate() {
>         if item == b' ' {
>             return &s[..i];
>         }
>     }
>     &s[..]
> }
> ```
>
> 注意：
>
> * 字符串切片的范围索引必须发生在有效的UTF-8字符边界内。
> * 如果尝试从一个多字节的字符中创建字符串切片，程序会报错并退出

### 结构体struct

> ```rust
> fn main() {
>     let rect = Rectangle {
>         width: 30,
>         height: 10,
>     };
>     println!("{}",area(&rect));
>     println!("{:#?}",rect);
> }
> 
> fn area(rect: &Rectangle) -> u32 {
>     rect.width * rect.height
> }
> 
> #[derive(Debug)]
> struct Rectangle {
>     width: u32,
>     height: u32,
> }
> ```
>
> ```rust
> fn main() {
>     let rect = Rectangle {
>         width: 30,
>         height: 10,
>     };
>     let rect2 = Rectangle {
>         width: 10,
>         height: 40,
>     };
>     let s = Rectangle::square(3);
>     println!("{}",rect.area());
>     println!("{}",rect.can_hold(&rect2));
>     println!("{:#?}",rect);
>     println!("{:#?}",s);
> }
> 
> #[derive(Debug)]
> struct Rectangle {
>     width: u32,
>     height: u32,
> }
> 
> impl Rectangle {
>     // 方法必须以self作为第一个参数
>     fn area(&self) -> u32 {
>         self.width * self.height
>     }
> 
>     fn can_hold(&self, other: &Rectangle) -> bool {
>         self.width > other.width && self.height > other.height
>     }
> 
>     // 关联函数可以不需要self参数
>     fn square(size: u32) -> Rectangle {
>         Rectangle {
>             width: size,
>             height: size,
>         }
>     }
> }
> ```

### 枚举

> ```rust
> fn main() {
>     let four = IPAddrKind::V4;
>     let six = IPAddrKind::V6;
>     route(four);
>     route(six);
> }
> 
> enum IPAddrKind {
>     V4,
>     V6,
> }
> 
> fn route(ip_kind: IPAddrKind) {
>     match ip_kind {
>         IPAddrKind::V4 => println!("ipv4"),
>         IPAddrKind::V6 => println!("ipv6"),
>     }
> }
> ```
>
> ```rust
> fn main() {
>     let four = IPAddrKind::V4(127,0,0,1);
>     let six = IPAddrKind::V6(String::from("::1"));
> }
> 
> enum IPAddrKind {
>     V4(u8,u8,u8,u8),
>     V6(String),
> }
> ```
>
> 定义枚举方法
>
> ```rust
> fn main() {
>     let q = Message::Quit;
>     let m = Message::Move { x: 100, y: 100 };
>     let w = Message::Write(String::from("hello"));
>     let c = Message::ChangeColor(0, 160, 230);
>     q.call();
>     m.call();
>     w.call();
>     c.call();
> }
> 
> #[derive(Debug)]
> enum Message {
>     Quit,
>     Move { x: i32, y: i32 },
>     Write(String),
>     ChangeColor(i32, i32, i32),
> }
> 
> impl Message {
>     fn call(&self) {
>         println!("{:?}", self);
>     }
> }
> ```
>
> **Option枚举**
>
> * rust中类似Null的概念Option<T>
>
> * 标准库中的定义
>
>   * ```rust
>     enum Option<T>{
>       Some(T),
>       None,
>     }
>     ```
>
>   * 它包含在Prelude（预导入模块）中，可直接使用Option<T>，Some<T>，None
>
> ```rust
> fn main() {
>    let some_number = Some(5);
>    let some_string = Some("a string");
>    let absent_number: Option<i32> = None;
> }
> ```
>
> ```rust
> fn main() {
>    let x: i8 = 5;
>    let y: Option<i8> = Some(10);
> //    let sum = x + y;  这里会报错，若想使用Option中的T，必须先把Option<T>转换成T
>    let sum = x + y.unwrap_or(0);
>    println!("sum: {}", sum);
> }
> ```

### match

> ```rust
> fn main() {
>     let r = value_in_cents(Coin::Penny);
>     println!("{}", r);
> }
> 
> enum Coin {
>     Penny,
>     Nickel,
>     Dime,
>     Quarter,
> }
> 
> fn value_in_cents(coin: Coin) -> u8 {
>     match coin {
>         Coin::Penny => {
>             println!("Lucky penny!");
>             1
>         },
>         Coin::Nickel => 5,
>         Coin::Dime => 10,
>         Coin::Quarter => 25,
>     }
> }
> ```
>
> 绑定值的模式匹配
>
> ```rust
> fn main() {
>     let c = Coin::Quarter(UsState::Alaska);
>     println!("{}", value_in_cents(c));
> }
> 
> enum Coin {
>     Penny,
>     Nickel,
>     Dime,
>     Quarter(UsState),
> }
> 
> #[derive(Debug)]
> enum UsState {
>     Alabama,
>     Alaska,
> }
> 
> fn value_in_cents(coin: Coin) -> u8 {
>     match coin {
>         Coin::Penny => {
>             println!("Lucky penny!");
>             1
>         },
>         Coin::Nickel => 5,
>         Coin::Dime => 10,
>         Coin::Quarter(state) => {
>             println!("State quarter from {:?}!", state);
>             25
>         },
>     }
> }
> ```
>
> Option模式匹配
>
> ```rust
> fn main() {
>     let five = Some(5);
>     let six = plus_one(five);
>     let none = plus_one(None);
> }
> 
> fn plus_one(x: Option<i32>) -> Option<i32> {
>     match x {
>         None => None,
>         Some(i) => Some(i + 1),
>     }
> }
> ```
>
> **match匹配必须穷举所有的可能**
>
> 可以使用_通配符替代其余没出现的值
>
> ```rust
> fn main() {
>     let v = 0u8;
>     match v {
>         0 => println!("zero"),
>         1 => println!("one"),
>         2 => println!("two"),
>         3 => println!("three"),
>         _ => println!("other"),
>     }
> }
> ```

### if let

> 可以把if let看做match的一种语法糖，当只有一种情况需要匹配处理，适合使用if let
>
> ```rust
> fn main() {
>     let v = Some(0u8);
>     if let Some(3) = v {
>         println!("three");
>     }
> }
> ```

### Rust代码组织

> 代码组织主要包括：
>
> * 哪些细节可以暴露，哪些细节是私有的
> * 作用域内哪些名称有效
>
> **模块系统**
>
> * Package(包)：Cargo的特性，让你构建、测试、共享crate
> * Crate(单元包)：一个模块树，它可产生一个library或可执行文件
> * Module(模块)、Use:让你控制代码的组织、作用域、私有路径
> * Path(路径)：为struct、function或nodule等项命名的方式
>
> **Package和Crate**
>
> Crate的类型：
>
> * binary
> * library
>
> Crate Root:
>
> * 是源代码文件
> * Rust编译器从这里开始，组成你的Crate的根Module
>
> 一个Package:
>
> * 包含1个Cargo.toml,它描述了如何构建这些Crates
> * 只能包含0-1个library crate
> * 可以包含任意数量的binary crate
> * 但必须至少包含一个crate(library或binary)
>
> **Module**
>
> * 在一个crate内，将代码进行分组
> * 增加可读性，易于复用
> * 控制项目(item)的私有性。public、private
> * src/main.rs和src/Iib.rs叫做crate roots
>
> **建立module**
>
> * mod关键字
> * 可嵌套
> * 可包含其它项(struct、.enum、常量、trait、函数等)的定义
>
> lib.rs
>
> ```rust
> mod front_of_house{
>     mod hosting{
>         fn add_to_waitlist(){
>             println!("add to waitlist");
>         }
>         fn seat_at_table(){
>             println!("seat at table");
>         }
>     }
>     mod serving{
>         fn take_order(){
>             println!("take order");
>         }
>         fn serve_order(){
>             println!("serve order");
>         }
>     }
> }
> ```
>
> **路径(path)**
>
> * 为了在Rust的模块中找到某个条目，需要使用路径。
> * 路径的两种形式：
>   * 绝对路径：从crate root开始，使用crate名或字面值crate
>   * 相对路径：从当前模块开始，使用sef,super或当前模块的标识符
> * 路径至少由一个标识符组成，标识符之间使用::
>
> **私有边界**
>
> * 模块不仅可以组织代码，还可以定义私有边界
> * 如果想把函数或struct等设为私有，可以将它放到某个模块中。
> * Rust中所有的条目（函数，方法，struct,enum,模块，常量）默认是私有的。
> * 父级模块无法访问子模块中的私有条目
> * 子模块里可以使用所有祖先模块中的条目
> * 使用pub关键字来将某些条目标记为公共的
>
> lib.rs
>
> ```rust
> mod front_of_house{
>     pub mod hosting{
>         pub fn add_to_waitlist(){
>             println!("add to waitlist");
>         }
>         pub fn seat_at_table(){
>             println!("seat at table");
>         }
>     }
>     mod serving{
>         fn take_order(){
>             println!("take order");
>         }
>         fn serve_order(){
>             println!("serve order");
>         }
>     }
> }
> 
> pub fn eat_at_restaurant(){
>     crate::front_of_house::hosting::add_to_waitlist();
>     front_of_house::hosting::seat_at_table();
> }
> ```
>
> super关键字
>
> * supr:用来访问父级模块路径中的内容，类似文件系统中的..
>
> pub struct
>
> * pub放在struct前：
>   * struct是公共的
>   * struct的字段默认是私有的
> * struct的字段需要单独设置pub来变成共有。
>
> ```rust
> mod back_of_house{
>     pub struct Breakfast{
>         pub toast: String,
>         seasonal_fruit: String,
>     }
>     impl Breakfast{
>         pub fn summer(toast: &str) -> Breakfast{
>             Breakfast{
>                 toast: String::from(toast),
>                 seasonal_fruit: String::from("peaches"),
>             }
>         }
> 
>     }
> }
> 
> pub fn eating_at_restaurant(){
>     let mut meal = back_of_house::Breakfast::summer("Rye");
>     meal.toast = String::from("Wheat");
>     println!("I'd like {} toast please", meal.toast);
>     // println!("And my seasonal fruit is {}", meal.seasonal_fruit);  访问私有的字段会报错
> 
> }
> ```
>
> pub enum
>
> * pub放在enum前
> * enum是公共的
> * erum里的元素也都是公共的
>
> **use关键字**
>
> * 可以使用use关键字将路径导入到作用域内
>   * 仍遵循私有性规则
> * 使用use来指定相对路径
>
> 绝对路径指定
>
> ```rust
> mod front_of_house{
>     pub mod hosting{
>         pub fn add_to_waitlist(){
>             println!("Please add to waitlist");
>         }
>     }
> }
> 
> use crate::front_of_house::hosting;
> pub fn eat_at_restaurant(){
>     hosting::add_to_waitlist();
> }
> ```
>
> 相对路径指定
>
> ```rust
> mod front_of_house{
>     pub mod hosting{
>         pub fn add_to_waitlist(){
>             println!("Please add to waitlist");
>         }
>     }
> }
> 
> use front_of_house::hosting;
> pub fn eat_at_restaurant(){
>     hosting::add_to_waitlist();
> }
> ```
>
> use的习惯用法
>
> * 函数：将函数的父级模块引入作用域（指定到父级）
> * struct,enum,其它：指定完整路径（指定到本身）
> * 同名条目：指定到父级
>
> ```rust
> use std::collections::HashMap;
> fn main() {
>     let mut map = HashMap::new();
>     map.insert(1,2);
> }
> ```
>
> ```rust
> use std::fmt;
> use std::io;
> fn main() {
> 
> }
> 
> fn f1() -> fmt::Result{}
> fn f2() -> io::Result{}
> ```
>
> **as关键字**
>
> * as关键字可以为引入的路径指定本地的别名
>
> ```rust
> use std::fmt::Result;
> use std::io::Result as IOResult;
> fn main() {
> 
> }
> 
> fn f1() -> Result{}
> fn f2() -> IOResult{}
> ```
>
> 使用pub use重新导出名称
>
> * 使用use将路径（名称）导入到作用域内后，该名称在此作用域内是私有的。
> * pub use:重导出
>   * 将条目引入作用域
>   * 该条目可以被外部代码引入到它们的作用域
>
> ```rust
> mod front_of_house{
>     pub mod hosting{
>         pub fn add_to_waitlist(){
>             println!("Please add to waitlist");
>         }
>     }
> }
> 
> pub use front_of_house::hosting;    // 这里使用pub use之后，外部也能使用hosting名称
> pub fn eat_at_restaurant(){
>     hosting::add_to_waitlist();
> }
> ```
>
> **使用外部包(package)**
>
> * Cargo.toml添加依赖的包(package)
>   * https://crates.io/
>
> * 将特定条目引入作用域
>
> * 标准库(sd)也被当做外部包
>   * 不需要修改Cargo.toml来包含std
>   * 需要使用use将std中的特定条目引入当前作用域
>
> 使用嵌套路径清理大量的use语句
>
> * 如果使用同一个包或模块下的多个条目
> * 可使用嵌套路径在同一行内将上述条目进行引入
>   * 路径相同的部分::{路径差异的部分}
> * 如果两个Use路径之一是另一个的子路径
>   * 使用self
>
> ```rust
> // use std::io;
> // use std::cmp::Ordering;
> use std::{cmp::Ordering, io};
> fn main() {}
> ```
>
> ```rust
> // use std::io;
> // use std::io::Write;
> use std::io::{self, Write};
> fn main() {}
> ```
>
> 通配符*
>
> * 使用*可以把路径中所有的公共条目都引入到作用域。
>
> ```rust
> use std::collections::*;
> fn main() {}
> ```
>
> 将模块内容移动到其他文件
>
> * 模块定义时，如果模块名后边是” ; “【分号】，而不是代码块：
>   * Rust会从与模块同名的文件中加载内容
>   * 模块树的结构不会变化
> * 随着模块逐渐变大，该技术让你可以把模块的内容移动到其它文件中
>
> <img src="./images/mod_1.jpg" style="zoom:50%;" />
>
> <img src="./images/mod_2.jpg" style="zoom:50%;" />
>
> <img src="./images/mod_3.jpg" style="zoom:50%;" />

### Vector

> ```rust
> fn main() {
>     // let v: Vec<i32> = Vec::new();
>     // let v = vec![1,2,3];
>     let mut v: Vec<i32> = Vec::new();
>     v.push(1);
>     v.push(2);
>     v.push(3);
>     v.push(4);
>     v.push(5);
>     let third = &v[2];
>     println!("the third element is {}", third);
> 
>     match v.get(2) {
>         Some(num) => println!("the third element is {}", num),
>         None => println!("there is no third element"),
>     }
> 
>     for i in &v {
>         println!("{}", i);
>     }
> }
> ```
>
> ```rust
> fn main() {
>     let mut v = vec![10, 20, 30];
> 
>     for i in &mut v {
>         *i += 50;
>     }
> 
>     for i in &v {
>         println!("{}", i);
>     }
> }
> ```
>
> ```rust
> fn main() {
>    let row = vec![
>       SpreadsheetCell::Int(3),
>       SpreadsheetCell::Text(String::from("blue")),
>       SpreadsheetCell::Float(10.32),
>    ];
> }
> 
> enum SpreadsheetCell {
>     Int(i32),
>     Float(f64),
>     Text(String),
> }
> ```
>
> 

### HashMap

> * HashMap用的较少，不在Prelude【预导入模块】中
> * 标准库对其支持较少，没有内置的宏来创建HashMap
> * 数据存储在heap上
> * 同构的。一个HashMap中：
>   * 所有的K必须是同一种类型
>   * 所有的V必须是同一种类型
>
> **HashMap和所有权**
>
> * 对于实现了Copy trait的类型（例如i32),值会被复制到HashMap中
> * 对于拥有所有权的值（例如String)，值会被移动，所有权会转移给HashMap
> * 如果将值的引用插入到HashMap,值本身不会移动
>   * 在HashMap有效的期间，被引用的值必须保持有效
>
> ```rust
> use std::collections::HashMap;
> 
> fn main() {
>    let mut scores: HashMap<String,i32>  = HashMap::new();
>    scores.insert(String::from("Blue"), 10);
>    scores.insert(String::from("Yellow"), 50);
> 
>    let teams = vec![String::from("Red"), String::from("Orange")];
>    let initial_scores = vec![10, 50];
>    let scores2: HashMap<_, _> = teams.iter().zip(initial_scores.iter()).collect();
> 
>    // 获取值
>    let val: Option<&i32> = scores.get("Blue");
>    match val {
>       Some(score) => println!("Blue score: {}", score),
>       None => println!("No Blue score"),
>    }
> 
>    // 遍历
>    for (key, value) in &scores {
>       println!("{}: {}", key, value);
>    }
>    
>    // 所有权
>    let field_name: String = String::from("Favorite color");
>    let field_value: String = String::from("Blue");
>    let mut map: HashMap<String, String> = HashMap::new();
>    map.insert(field_name, field_value);
>    // println!("{}", field_name);   // 报错，field_name所有权被转移到map中
> 
>    // 更新
>    let mut scores3: HashMap<String,i32>  = HashMap::new();
>    scores3.insert(String::from("Blue"), 10);
>    // 插入相同的key，会覆盖原来的值
>    scores3.insert(String::from("Blue"), 20);
>    // 只在key不存在时插入
>    scores3.entry(String::from("Yellow")).or_insert(50);
>    println!("{:?}", scores3);
> 
>    let text = "hello world wonderful world";
>    let mut map = HashMap::new();
>    for word in text.split_whitespace() {
>       let count = map.entry(word).or_insert(0);
>       *count += 1;
>    }
>    println!("{:?}", map);
> }
> ```

### 错误处理

> * Rust的可靠性：错误处理
>   * 大部分情况下：在编译时提示错误，并处理
> * 错误的分类：
>   * 可恢复【例如文件未找到，可再次尝试】
>   * 不可恢复 【bug,例如访问的索引超出范围】
> * Rust没有类似异常的机制
>   * 可恢复错误：Result<T,E>
>   * 不可恢复：panic!宏
>
> **为应对panic,展开或中止(abort)调用栈**
>
> * 默认情况下，当panic发生：
>   * 程序展开调用栈（工作量大）
>   * Rust沿着调用栈往回走
>   * 清理每个遇到的函数中的数据
> * 或立即中止调用栈
>   * 不进行清理，直接停止程序
>   * 内存需要OS进行清理
>   * 想让二进制文件更小，把设置从“展开”改为“中止”
> * 在Cargo.toml中适当的profile部分设置：
>   * panic=‘abort'
>
> ```toml
> [profile.release]
> panic = 'abort'
> ```
>
> ```rust
> use std::fs::File;
> fn main() {
>    let f = File::open("hello.txt");
>    let f = match f {
>        Ok(file) => {
>          println!("{:?}", file);
>          file
>        },
>        Err(e) => {
>          panic!("打开文件出错了！ 详细错误信息：{}", e)
>        },
>    };
> }
> ```
>
> 匹配不同错误
>
> ```rust
> use std::{fs::File, io::ErrorKind};
> fn main() {
>    let f = File::open("hello.txt");
>    let f = match f {
>        Ok(file) => {
>          println!("{:?}", file);
>          file
>        },
>        Err(e) => match e.kind(){
>          ErrorKind::NotFound => match File::create("hello.txt"){
>            Ok(fc) => {
>              println!("创建文件成功！{:?}", fc);
>              fc
>            },
>            Err(e) => panic!("创建文件失败！{:?}", e)
>          },
>          other_error => {
>            panic!("打开文件出错！ {:?}", other_error)
>          }
>        }
>    };
> }
> ```
>
> 简化后
>
> ```rust
> use std::{fs::File, io::ErrorKind};
> fn main() {
>    let f = File::open("hello.txt").unwrap_or_else(|error|{
>        if error.kind() == ErrorKind::NotFound{
>            println!("file not found");
>            File::create("hello.txt").unwrap()
>        }else{
>            panic!("problem opening the file: {:?}", error);
>        }
>    });
> }
> ```
>
> **unwrap**
>
> * unwrap : match表达式的一个快捷方法：
> * 如果Result结果是Ok,返回Ok里面的值
> * 果Result结果是Err,调用panic!宏
> * 缺点：不能自定义错误信息
>
> ```rust
> use std::fs::File;
> fn main() {
>    let f = File::open("hello.txt").unwrap();
> }
> ```
>
> **expect**
>
> * expect：和unwrap类似，但可指定错误信息
>
> ```rust
> use std::fs::File;
> fn main() {
>    let f = File::open("hello.txt").expect("无法打开文件");
> }
> ```
>
> 传播错误
>
> ```rust
> use std::io::{self, Read};
> use std::fs::File;
> fn main() {
>    let s = read_username_from_file();
>    match s {
>       Ok(s) => println!("{}",s),
>       Err(e) => panic!("Failed to read from file: {}",e),
>    };
> }
> 
> fn read_username_from_file() -> Result<String,io::Error>{
>    let f = File::open("hello.txt");
>    let mut f = match f {
>       Ok(file) => file,
>       Err(e) => return Err(e),
>    };
>    let mut s = String::new();
>    match f.read_to_string(&mut s) {
>       Ok(_) => Ok(s),
>       Err(e) => Err(e),
>    }
> }
> ```
>
> **?运算符**
>
> * ？ 运算符：传播错误的一种快捷方式
> * 如果Result是Ok：Ok中的值就是表达式的结果，然后继续执行程序
> * 如果Result是Er：Err就是整个函数的返回值，就像使用了return
> * ？运算符只能用于返回Result的函数
>
> 利用？运算符，上述传播错误代码可以简写为：
>
> ```rust
> use std::io::{self, Read};
> use std::fs::File;
> fn main() {
>    let s = read_username_from_file();
>    match s {
>       Ok(s) => println!("{}",s),
>       Err(e) => panic!("Failed to read from file: {}",e),
>    };
> }
> 
> fn read_username_from_file() -> Result<String,io::Error>{
>    let mut f = File::open("hello.txt")?;
>    let mut s = String::new();
>    f.read_to_string(&mut s)?;
>    Ok(s)
> }
> ```
>
> **?与from函数**
>
> * Trait std::convert::From上的from函数：
>   * 用于错误之间的转换
> * 被？所应用的错误，会隐式的被from函数处理
> * 当？调用from函数时：
>   * 它所接收的错误类型会被转化为当前函数返回类型所定义的错误类型
> * 用于：针对不同错误原因，返回同一种错误类型
>   * 只要每个错误类型实现了转换为所返回的错误类型的from函数
>
> **？运算符链式调用**
>
> ```rust
> use std::io::{self, Read};
> use std::fs::File;
> fn main() {
>    let s = read_username_from_file();
>    match s {
>       Ok(s) => println!("{}",s),
>       Err(e) => panic!("Failed to read from file: {}",e),
>    };
> }
> 
> fn read_username_from_file() -> Result<String,io::Error>{
>    let mut s = String::new();
>    File::open("hello.txt")?.read_to_string(&mut s)?;
>    Ok(s)
> }
> ```
>
> **panic和Result使用场景**
>
> * 调用你的代码，传入无意义的参数值：panic!
> * 调用外部不可控代码，返回非法状态，你无法修复：panic!
> * 如果失败是可预期的：Result
> * 当你的代码对值进行操作，首先应该验证这些值：panic!
>
> ```rust
> use std::io::stdin;
> fn main() {
>    loop {
>       println!("Please input your guess.");
> 
>       let mut guess = String::new();
>       stdin().read_line(&mut guess).expect("expected a string");
>       let guess: i32 = match guess.trim().parse(){
>           Ok(num) => num,
>           Err(_) => continue,
>       };
>       let guess = Guess::new(guess);
>       println!("You guessed: {}", guess.value());
>    }
> }
> 
> pub struct Guess {
>     value: i32,
> }
> 
> impl Guess {
>     pub fn new(value: i32) -> Guess {
>         if value < 1 || value > 100 {
>             panic!("Guess value must be between 1 and 100, got {}.", value);
>         }
>         Guess { value }
>     }
>     pub fn value(&self) -> i32 {
>         self.value
>     }
> }
> ```

### 泛型

> ```rust
> fn main() {
>    let v = vec![10,5,8,20,15,6,30,50];
>    let num = largest(&v);
>    println!("The largest number is {}", num);
> }
> 
> fn largest<T: std::cmp::PartialOrd>(list:&[T]) -> &T {
>     let mut largest = &list[0];
>     for item in list {
>         if item > largest {
>             largest = item;
>         }
>     }
>     largest
> }
> ```
>
> ```rust
> fn main() {
>    let p1 = Point {x: 5, y: 10.5};
>    let p2 = Point {x: 1.0, y: 4.0};
> }
> 
> struct Point<T,U> {
>     x: T,
>     y: U,
> }
> ```
>
> ```rust
> fn main() {
>    let p1 = Point {x: 5, y: 10};
>    let p2 = Point {x: "Hello", y: 'c'};
>    let p3 = p1.mixup(p2);
>    println!("p3.x = {} p3.y = {}", p3.x, p3.y);
> }
> 
> struct Point<T,U> {
>     x: T,
>     y: U,
> }
> 
> impl <T,U> Point<T,U> {
>     fn x(&self)-> &T {
>         &self.x
>     }
>     fn y(&self) -> &U {
>         &self.y
>     }
> }
> 
> impl Point<i32,i32>{
>    fn x1(&self) -> &i32 {
>        &self.x
>    }
>    fn y1(&self) -> &i32 {
>        &self.y
>    }
> }
> 
> impl <T,U> Point<T,U>{
>     fn mixup<V,W>(self, other: Point<V,W>) -> Point<T,W> {
>         Point {
>             x: self.x,
>             y: other.y,
>         }
>     }
> }
> ```

### Trait

> * Trait告诉Rust编译器：
>   * 某种类型具有哪些并且可以与其它类型共享的功能
> * Trait:抽象的定义共享行为
> * Trait bounds(约束)：泛型类型参数指定为实现了特定行为的类型
> * Trait与其它语言的接口(interface)类似，但有些区别。
> * 默认实现的方法可以调用Trait中其它的方法，即使这些方法没有默认实现。
> * 注意：无法从方法的重写实现里面调用默认的实现。
>
> **实现Trait的约束**
>
> * 可以在某个类型上实现某个rat的前提条件是：
>   * 这个类型或这个trait是在本地crate里定义的
> * 无法为外部类型来实现外部的trait
>   * 这个限制是程序属性的一部分（也就是一致性）
>   * 更具体地说是孤儿规则：之所以这样命名是因为父类型不存在。
>   * 此规则确保其他人的代码不能破坏您的代码，反之亦然。
>   * 如果没有这个规则，两个crate可以为同一类型实现同一个trait,Rust就不知道应该使用哪个实现了。
>
> ```rust
> fn main() {
>    let tweet = Tweet {
>      username: String::from("horse_ebooks"),
>      content: String::from("of course, as you probably already know, people"),
>      reply: false,
>      retweet: false,
>    };
>    println!("1 new tweet: {}", tweet.summarize());
> }
> 
> pub trait Summary{
> //   fn summarize(&self) -> String;
> 
>    // 默认实现  默认实现的方法可以调用Trait中其它的方法，即使这些方法没有默认实现。
>   // 注意：无法从方法的重写实现里面调用默认的实现。
>   fn summarize(&self) -> String{
>     String::from("read more...")
>   }
> }
> 
> pub struct NewsArticle {
>   pub headline: String,
>   pub location: String,
>   pub author: String,
>   pub content: String,
> }
> 
> impl Summary for NewsArticle {
>   fn summarize(&self) -> String {
>     format!("{}, by {} ({})", self.headline, self.author, self.location)
>   }
> }
> 
> struct Tweet {
>   pub username: String,
>   pub content: String,
>   pub reply: bool,
>   pub retweet: bool,
> }
> 
> impl Summary for Tweet {
>   fn summarize(&self) -> String {
>     format!("{}: {}", self.username, self.content)
>   }
> }
> ```
>
> **Trait作为参数**
>
> * impl Trait语法：适用于简单情况
> * Trait bound语法：可用于复杂情况
> * impl Trait语法是Trait bound的语法糖
> * 使用+指定多个Trait bound
> * Trait bound使用where子句
>   * 在方法签名后指定where子句
>
> ```rust
> // pub fn notify(item : impl Summary){
> //   println!("notify! {}", item.summarize());
> // }
> 
> // 实现多个Trait
> // pub fn notify(item : impl Summary + Display){
> //   println!("notify! {}", item.summarize());
> // }
> 
> // 实现多个Trait，用加号 + 连接多个 Trait
> // pub fn notify<T: Summary + Display>(item : T){
> //   println!("notify! {}", item.summarize());
> // }
> 
> pub fn notify<T: Summary + Display,U: Clone + Debug>(a: T,b: U) -> String{
>   format!("notify! {}", a.summarize())
> }
> 
> pub fn notify2<T,U>(a: T,b: U) -> String
> where T: Summary + Display,
>       U: Clone + Debug
> {
>   format!("notify! {}", a.summarize())
> }
> ```
>
> **使用Trait作为返回类型**
>
> * impl Trait语法
> * 注意：impl Trait只能返回确定的同一种类型，返回可能不同类型的代码会报错
>
> ```rust
> fn notify(flag: bool) -> impl Summary{
>   // 这里NewsArticle和Tweet虽然都实现了Summary Trait，但是有可能返回两种类型，会报错
>   // impl Trait这种形式只能返回一种类型
>   if flag {
>       NewsArticle {
>         headline: String::from("Penguins win the Stanley Cup Championship!"),
>         location: String::from("Pittsburgh, PA, USA"),
>         author: String::from("Iceburgh"),
>         content: String::from(
>           "The Pittsburgh Penguins once again are the best \
>            hockey team in the NHL.",
>         ),
>       }
>   }else {
>       Tweet {
>         username: String::from("horse_ebooks"),
>         content: String::from("of course, as you probably already know, people"),
>         reply: false,
>         retweet: false,
>       }
>   }
> }
> ```

### 生命周期

> * Rust的每个引用都有自己的生命周期。
> * 生命周期：引用保持有效的作用域。
> * 大多数情况：生命周期是隐式的、可被推断的
> * 当引用的生命周期可能以不同的方式互相关联时：手动标注生命周期。
>
> ```rust
> fn main() {
>    let string1 = String::from("abcd");
>    let string2 = "xyz";
>    let result = longest(string1.as_str(), string2);
>    println!("The longest string is {}", result);
> }
> 
> fn longest<'a>(x: &'a str, y: &'a str) -> &'a str {
>     if x.len() > y.len() {
>         x
>     } else {
>         y
>     }
> }
> ```
>
> **生命周期标注语法**
>
> * 生命周期的标注不会改变引用的生命周期长度
> * 当指定了泛型生命周期参数，函数可以接收带有任何生命周期的引用
> * 生命周期的标注：描述了多个引用的生命周期间的关系，但不影响生命周期
> * ·生命周期参数名：
>   * 以 ' 开头
>   * 通常全小写且非常短
>   * 很多人使用 'a
> * 生命周期标注的位置：
>   * 在引用的&符号后
>   * 使用空格将标注和引用类型分开
>
> **深入理解生命周期**
>
> * 指定生命周期参数的方式依赖于函数所做的事情
> * 从函数返回引用时，返回类型的生命周期参数需要与其中一个参数的生命周期匹配：
> * 如果返回的引用没有指向任何参数，那么它只能引用函数内创建的值：
>   * 这就是悬垂引用：该值在函数结束时就走出了作用域
>
> ```rust
> fn longest<'a>(x: &'a str, y: &'a str) -> &'a str {
>     let result = String::from("abc");
>     // result.as_str() 这里会报错，因为引用了函数内创建的值，而函数内创建的值离开函数生命周期就结束了
> }
> ```
>
> ```rust
> fn main() {
>    let novel = String::from("Call me Ishmael. Some years ago...");
>    let first_sentence = novel.split('.')
>      .next()
>      .expect("Could not find a '.'");
> 
>    let i = ImportantExcerpt { part: first_sentence };
> }
> 
> struct ImportantExcerpt<'a> {
>    part: &'a str,
> }
> ```
>
> **生命周期省略规则**
>
> * 在Rust引用分析中所编入的模式称为生命周期省略规则。
>   * 这些规则无需开发者来遵守
>   * 它们是一些特殊情况，由编译器来考虑
>   * 如果你的代码符合这些情况，那么就无需显式标注生命周期
> * 生命周期省略规则不会提供完整的推断：
>   * 如果应用规则后，引用的生命周期仍然模糊不清→编译错误
>   * 解决办法：添加生命周期标注，表明引用间的相互关系
>
> **输入、输出生命周期**
>
> * 生命周期在：
>   * 函数/方法的参数：输入生命周期
>   * 函数/方法的返回值：输出生命周期
>
> **生命周期省略的3个规则**
>
> * 编译器使用3个规则在没有显式标注生命周期的情况下，来确定引用的生命周期
>   * 规则1应用于输入生命周期
>   * 规则2、3应用于输出生命周期
>   * 如果编译器应用完3个规则之后，仍然有无法确定生命周期的引用→报错
>   * 这些规则适用于fn定义和impl块
> * 规则1：每个引用类型的参数都有自己的生命周期
> * 规则2：如果只有1个输入生命周期参数，那么该生命周期被赋给所有的输出生命周期参数
> * 规则3：如果有多个输入生命周期参数，但其中一个是&self或&mut self (是方法)，那么self的生命周期会被赋给所有的输出生命周期参数
>
> **静态生命周期**
>
> * 'static是一个特殊的生命周期：整个程序的持续时间。
>   * 例如：所有的字符串字面值都拥有'static生命周期
>   * `let s:&'static str "I have a static lifetime."`
> * 为引用指定'static生命周期前要三思：
>   * 是否需要引用在程序整个生命周期内都存活。

### 测试

> **测试的分类**
>
> * ·Rust对测试的分类：
>   * 单元测试
>   * 集成测试
> * 单元测试：
>   * 小、专注
>   * 一次对一个模块进行隔离的测试
>   * 可测试private接口
> * 集成测试：
>   * 在库外部。和其它外部代码一样使用你的代码
>   * 只能使用public接口
>   * 可能在每个测试中使用到多个模块
>
> **#[cfg(test)]标注**
>
> * tests模块上的#[cfg(test)]标注：
>   * 只有运行cargo test才编译和运行代码
>   * 运行cargo build则不会
> * 集成测试在不同的目录，它不需要#[cfg(test)]标注
> * cfg:configuration(配置)
>   * 告诉Rust下面的条目只有在指定的配置选项下才被包含
>   * 配置选项test:由Rust提供，用来编译和运行测试。
>   * 只有cargo test才会编译代码，包括模块中的helper函数和#[test]标注的函数
>
> 单元测试示例
>
> ```rust
> pub fn add(left: u64, right: u64) -> u64 {
>     left + right
> }
> 
> pub fn bigger(left:i32,right: i32) -> bool{
>     if left > right{
>         return true;
>     }
>     false
> }
> 
> pub fn divide(a:i32,b:i32) -> i32{
>     a/b
> }
> 
> #[cfg(test)]
> mod tests {
>     use super::*;
> 
>     #[test]
>     fn exploration() {
>         let result = add(2, 2);
>         assert_eq!(result, 4,"出错了，值不相等");
>     }
> 
>     #[test]
>     fn test_add() {
>         assert_ne!(4, add(2, 2),"出错了！");
>     }
> 
>     #[test]
>     fn another() {
>         panic!("Make this test fail");
>     }
> 
>     #[test]
>     fn test_bigger() {
>         assert!(bigger(1,2),"断言出错!")
>     }
> 
>     #[test]
>     #[should_panic]
>     fn test_should_panic(){
>         // 发生panic，该测试才会通过
>         divide(2,1);
>     }
> 
>     #[test]
>     #[should_panic(expected = "divide by zero")]
>     fn test_should_panic2(){
>         // 发生panic，该测试才会通过
>         // expected 用来筛选panic的错误信息
>         divide(2,0);
>     }
> 
>     #[test]
>     #[ignore]
>     fn test_ignore() {
>         // 忽略该测试
>         divide(2,0);
>     }
>     
>     #[test]
>     fn it_works() -> Result<(), String> {
>         // 测试通过返回 Ok(())，测试失败返回 Err(String)
>         if 2 + 3 == 4 {
>             Ok(())
>         } else {
>             Err(String::from("two plus two does not equal four"))
>         }
>     }
> }
> ```
>
> 集成测试示例
>
> ```rust
> // 必须在跟src目录同级的目录创建tests目录，在tests目录下创建测试文件，需要将被测试库导入
> use adder;
> #[test]
> fn test_add() {
>     assert_eq!(adder::add(2, 3), 5);
> }
> ```

### 闭包

> **什么是闭包(closure)**
>
> * 闭包：可以捕获其所在环境的匿名函数。
> * 闭包：
>   * 是匿名函数
>   * 保存为变量、作为参数
>   * 可在一个地方创建闭包，然后在另一个上下文中调用闭包来完成运算
>   * 可从其定义的作用域捕获值
>
> **闭包的类型推断**
>
> * 闭包不要求标注参数和返回值的类型
> * 闭包通常很短小，只在狭小的上下文中工作，编译器通常能推断出类型
> * 可以手动添加类型标注
>
> ```rust
> fn main() {
>     let example_closure = |x| x;
>     println!("{}",example_closure(5));
> 
>     let example_closure2 = |x: i32,y: i32| -> i32 {
>         x + y
>     };
>     println!("{}",example_closure2(5,6));
> 
>     let mut closure = Cacher::new(|num|{
>         println!("Calculating slowly...");
>         num * 2
>     });
>     let v1 = closure.value(5);
>     let v2 = closure.value(5);
>     let v3 = closure.value(5);
>     // 这里无论调用多少次，都只会执行一次闭包
>     println!("{} {} {}",v1,v2,v3);
> }
> 
> struct Cacher<T>
> where T: Fn(u32) -> u32,
> {
>     calculation: T,
>     value: Option<u32>,
> }
> 
> impl<T> Cacher<T>
> where T: Fn(u32) -> u32,
> {
>    fn new(calculation: T) -> Cacher<T>{
>        Cacher{
>            calculation,
>            value: None,
>        }
>    }
> 
>    fn value(&mut self,arg: u32) -> u32{
>        match self.value{
>            Some(v) => v,
>            None => {
>                let v = (self.calculation)(arg);
>                self.value = Some(v);
>                v
>            }
>        }
> 
>    }
> }
> ```
>
> **闭包捕获上下文**
>
> ```rust
> fn main() {
>    let x = vec![1,2,3];
>    // move关键字，将x的所有权转移给闭包，闭包拥有x的所有权
>    let equal_to_x = move |z| z == x;
>    // println!("{}",x);   报错，x的所有权已经转移给闭包
> }
> ```

### 迭代器

> 几个迭代方法
>
> * iter方法：在不可变引用上创建迭代器
> * into_iter方法：创建的迭代器会获得所有权
> * iter_mut方法：迭代可变的引用
>
> ```rust
> fn main() {
>    let v = vec![1,2,3];
>    let iter_1 = v.iter();
>    for val in iter_1 {
>        println!("{}",val);
>    }
> 
>    let v2 = vec![1,2,3];
>    let iter_2 = v2.iter();
>    let mut iter_2 = iter_2.map(|x| x*2);
>    for val in iter_2 {
>        println!("{}",val);
>    }
> 
>    let v3 = vec![1,2,3,4,5,6,7,8,9,10];
>    let iter_3 = v3.into_iter();
>    let v4: Vec<_> = iter_3.filter(|x| x%2 == 0).collect();
>    println!("{:?}",v4);
> 
>    // 自定义迭代器
>    let mut counter = Counter::new();
>    println!("{:?}",counter.next());
>    println!("{:?}",counter.next());
> }
> 
> // 创建自定义迭代器
> struct Counter {
>     count: u32,
> }
> 
> impl Counter {
>     fn new() -> Counter {
>         Counter { count: 0 }
>     }
> }
> 
> impl Iterator for Counter {
>    // 定义迭代器返回的类型
>     type Item = u32;
>     fn next(&mut self) -> Option<Self::Item> {
>         if self.count < 5 {
>             self.count += 1;
>             Some(self.count)
>         } else {
>             None
>         }
>     }
> }
> ```
>

### 智能指针

> **智能指针的实现**
>
> * 智能指针通常使用struct实现，并且实现了：
>   * Deref和Drop这两个trait
> * Deref trait：允许智能指针struct的实例像引用一样使用
> * Drop trait：允许你自定义当智能指针实例走出作用域时的代码
>
> 标准库中常见智能指针
>
> * Box<T>：在heap内存上分配值
> * Rc<T>：启用多重所有权的引用计数类型
> * Ref<T>和RefMut<T>,通过RefCell<T>访问：在运行时而不是编译时强制借用规则的类型
>
> 常见问题：
>
> * 内部可变模式(interior mutability pattern)：不可变类型暴露出可修改其内部值的API
> * 引用循环(reference cycles):它们如何泄露内存，以及如何防止其发生。
>
> **Box<T>**
>
> * Box<T>是最简单的智能指针：
> * 允许你在heap上存储数据（而不是stack)
> * stack上是指向heap数据的指针
> * 没有性能开销
> * 没有其它额外功能
>
> Box<T>的常用场景
>
> * 在编译时，某类型的大小无法确定。但使用该类型时，上下文却需要知道它的确切大小。
> * 当你有大量数据，想移交所有权，但需要确保在操作时数据不会被复制。
> * 使用某个值时，你只关心它是否实现了特定的trait,,而不关心它的具体类型。
>
> ```rust
> use crate::List::{Cons, Nil};
> fn main() {
>    let b = Box::new(5);
>    println!("b = {}", b);
>    let list = Cons(1,Box::new(Cons(2,Box::new(Cons(3,Box::new(Nil))))));
> }
> 
> enum List {
>     Cons(i32,Box<List>),
>     Nil,
> }
> ```
>
> **Deref Trait**
>
> * 实现Deref Trait使我们可以自定义解引用运算符*的行为
> * 通过实现Deref，智能指针可像常规引用一样来处理
>
> ```rust
> use std::ops::Deref;
> fn main() {
>    let x = 5;
>    let y = MyBox::new(x);
>    assert_eq!(5,*y);
> }
> 
> struct MyBox<T>(T);
> 
> impl <T> MyBox<T> {
>     fn new(x:T) -> MyBox<T> {
>         MyBox(x)
>     }
> }
> 
> impl <T> Deref for MyBox<T> {
>     type Target = T;
>     fn deref(&self) -> &Self::Target {
>         &self.0
>     }
> }
> ```
>
> **Drop Trait**
>
> * 实现Drop Trait,可以让我们自定义当值将要离开作用域时发生的动作。
>   * 例如：文件、网络资源释放等
>   * 任何类型都可以实现Drop trait
> * Drop trait只要求你实现drop方法
>   * 参数：对self的可变引用
> * Drop trait在预导入模块里(prelude)
>
> ```rust
> fn main() {
>    let c = CustomSmartPointer { data: String::from("my stuff") };
>    let d = CustomSmartPointer { data: String::from("other stuff") };
> }
> 
> struct CustomSmartPointer {
>     data: String
> }
> 
> impl Drop for CustomSmartPointer {
>     fn drop(&mut self) {
>         println!("Dropping CustomSmartPointer with data `{}`!", self.data);
>     }
> }
> ```
>
> 使用std::mem::drop来提前drop值
>
> * 很难直接禁用自动的drop功能，也没必要
>   * Drop trait的目的就是进行自动的释放处理逻辑
> * Rust不允许手动调用Drop trait的drop方法
> * 但可以调用标准库的std::mem::drop函数，来提前drop值
>
> **Rc<T>引用计数智能指针**
>
> * 有时，一个值会有多个所有者
> * 为了支持多重所有权：Rc<T>
>   * reference couting(引用计数)
>   * 追踪所有到值的引用
>   * 0个引用：该值可以被清理掉
>
> **Rc<T>使用场景**
>
> * 需要在heap上分配数据，这些数据被程序的多个部分读取（只读)，但在编译时无法确定哪个部分最后使用完这些数据
> * Rc<T>只能用于单线程场景
>
> ```rust
> use crate::List::{Cons, Nil};
> use std::rc::Rc;
> fn main() {
>    let a = Rc::new(Cons(5, Rc::new(Cons(10, Rc::new(Nil)))));
>    println!("count after creating b = {}", Rc::strong_count(&a));
>    let b = Cons(3, Rc::clone(&a));
>    let c = Cons(4, Rc::clone(&a));
>    println!("count after creating c = {}", Rc::strong_count(&a));
> }
> 
> enum List {
>     Cons(i32, Rc<List>),
>     Nil,
> }
> ```
>
> **RefCell<T>**
>
> * 与Rc<T>相似，只能用于单线程场景
>
> 选择Box<T>、Rc<T>、RefCell<T>的依据
>
> |                  | Box<T>                        | Rc<T>                   | RefCell<T>                    |
> | ---------------- | ----------------------------- | ----------------------- | ----------------------------- |
> | 同一数据的所有者 | 一个                          | 多个                    | 一个                          |
> | 可变性、借用检查 | 可变、不可变借用 (编译时检查) | 不可变借用 (编译时检查) | 可变、不可变借用 (运行时检查) |
>
> 其中：即便RefCell<T>本身不可变，但仍能修改其中存储的值
>
> 使用RefCell<T>在运行时记录借用信息
>
> 两个方法（安全接口）：
>
> * borrow方法
>   * 返回智能指针Ref<T>,它实现了Deref
> * borrow_mut方法
>   * 返回智能指针RefMut<T>,它实现了Deref
>
> 规则：
>
> * RefCell<T>会记录当前存在多少个活跃的Ref<T>和RefMut<T>智能指针
>   * 每次调用borrow:不可变借用计数加1
>   * 任何一个Rf<T>的值离开作用域被释放时：不可变借用计数减1
>   * 每次调用borrow_mut:可变借用计数加1
>   * 任何一个RefMut<T>的值离开作用域被释放时：可变借用计数减1
>
> RefCell<T>实现修改不可变的值
>
> ```rust
> pub trait Messager{
>     fn send(&self,msg:&str);
> }
> 
> pub struct LimitTracker<'a,T:'a + Messager>{
>     messager:&'a T,
>     value:usize,
>     max:usize,
> }
> 
> impl<'a,T> LimitTracker<'a,T>
>     where T:Messager{
>     pub fn new(messager:&T,max:usize)->LimitTracker<T>{
>         LimitTracker{
>             messager,
>             value:0,
>             max,
>         }
>     }
>     pub fn set_value(&mut self,value:usize){
>         self.value = value;
>         let percentage_of_max = self.value as f64 / self.max as f64;
>         if percentage_of_max >= 1.0{
>             self.messager.send("Error: You are over your quota!");
>         }else if percentage_of_max >= 0.9{
>             self.messager.send("Urgent warning: You've used up over 90% of your quota!");
>         }else if percentage_of_max >= 0.75{
>             self.messager.send("Warning: You've used up over 75% of your quota!");
>         }
>     }
> }
> 
> #[cfg(test)]
> mod tests{
>     use super::*;
>     use std::cell::RefCell;
>     struct MockMessager{
>         sent_messages:RefCell<Vec<String>>,
>     }
>     impl MockMessager{
>         fn new()->MockMessager{
>             MockMessager{
>                 sent_messages:RefCell::new(vec![]),
>             }
>         }
>     }
>     impl Messager for MockMessager{
>         fn send(&self,msg:&str){
>             let mut temp = String::from(msg);
>             self.sent_messages.borrow_mut().push(temp);
>         }
>     }
> 
>     #[test]
>     fn it_sends_an_over_75_percent_warning_message(){
>         let mock_messager = MockMessager::new();
>         let mut limit_tracker = LimitTracker::new(&mock_messager,100);
>         limit_tracker.set_value(80);
>         assert_eq!(mock_messager.sent_messages.borrow().len(),1);
>     }
> }
> ```
>
> ```rust
> use std::{cell::RefCell, rc::Rc};
> use crate::List::{Cons,Nil};
> fn main() {
>    let value = Rc::new(RefCell::new(5));
>    let a = Rc::new(Cons(Rc::clone(&value),Rc::new(Nil)));
>    let b = Cons(Rc::new(RefCell::new(6)),Rc::clone(&a));
>    let c = Cons(Rc::new(RefCell::new(10)),Rc::clone(&a));
>    *value.borrow_mut() += 10;
>    println!("a after = {:?}",a);
>    println!("b after = {:?}",b);
>    println!("c after = {:?}",c);
> }
> 
> #[derive(Debug)]
> enum List{
>     Cons(Rc<RefCell<i32>>,Rc<List>),
>     Nil,
> }
> ```
>
> ​	

### 多线程

> ```rust
> use std::thread;
> use std::time::Duration;
> 
> fn main() {
>    let handle = thread::spawn(|| {
>       for i in 1..10 {
>          println!("hi number {} from the spawned thread!", i);
>          thread::sleep(Duration::from_millis(1000));
>       }
>    });
> 
>    for i in 1..5 {
>       println!("hi number {} from the main thread!", i);
>       thread::sleep(Duration::from_millis(1000));
>    }
> 
>    handle.join().unwrap();
> }
> ```
>
> **move关键字转移所有权**
>
> ```rust
> use std::thread;
> use std::time::Duration;
> 
> fn main() {
>    let v = vec![1,2,3];
>    // move 关键字将v的所有权从 main 函数转移到新线程中
>    let handle = thread::spawn(move ||{
>         println!("Here's a vector: {:?}", v);
>    });
>    handle.join().unwrap();
> }
> ```
>
> **使用消息传递来跨线程传递数据【Channel】**
>
> * 发送端的send方法
>   * 参数：想要发送的数据
>   * 返回：Result<T,E>
> * 接收端recv方法
>   * recv方法：阻止当前线程执行，直到Channel中有值被送来
>     * 一旦有值收到，就返回Result<T,E>
>     * 当发送端关闭，就会收到一个错误
> * try_recv方法：不会阻塞，
>   * 立即返回Result<T,E>:
>   * 有数据达到：返回Ok,里面包含着数据
>   * 否则，返回错误
>   * 通常会使用循环调用来检查try_recv的结果
>
> ```rust
> use std::{sync::mpsc, thread};
> 
> fn main() {
>    let (tx,rx) = mpsc::channel();
>    thread::spawn(move || {
>        let val = String::from("hello");
>        tx.send(val).unwrap();
>    });
>    let received = rx.recv().unwrap();
>    println!("Got: {}", received);
> }
> ```
>
> 通过克隆创建多个发送者
>
> ```rust
> use std::{sync::mpsc, thread};
> 
> fn main() {
>    let (tx,rx) = mpsc::channel();
>    let tx1 = mpsc::Sender::clone(&tx);
>    thread::spawn(move || {
>        let vals = vec![
>            String::from("1: hi"),
>            String::from("1: from"),
>            String::from("1: the"),
>            String::from("1: thread"),
>        ];
>        for val in vals {
>            tx.send(val).unwrap();
>            thread::sleep(std::time::Duration::from_millis(1000));
>        }
>    });
> 
>    thread::spawn(move || {
>         let vals = vec![
>             String::from("2: hi"),
>             String::from("2: from"),
>             String::from("2: the"),
>             String::from("2: thread"),
>         ];
>         for val in vals {
>             tx1.send(val).unwrap();
>             thread::sleep(std::time::Duration::from_millis(500));
>         }
>     });
>    for received in rx {
>        println!("Got: {}", received);
>    }
> }
> ```
>
> **互斥锁**
>
> ```rust
> use std::{sync::{Arc, Mutex}, thread};
> 
> fn main() {
>     // Arc<T>可用于多线程多重所有权，Rc<T>只用于单线程多重所有权
>    let counter = Arc::new(Mutex::new(0));
>    let mut handles = vec![];
>    for _ in 0..10 {
>       let counter = Arc::clone(&counter);
>       let handle = thread::spawn(move || {
>          let mut num = counter.lock().unwrap();
>          *num += 1;
>       });
>       handles.push(handle);
>    }
>    for handle in handles {
>       handle.join().unwrap();
>    }
>    println!("Reuslt : {}", *counter.lock().unwrap());
> }
> ```
>
> **Send和Sync trait**
>
> Send:允许线程间转移所有权
>
> * 实现Send trait的类型可在线程间转移所有权
> * Rust中几乎所有的类型都实现了Send
>   * 但Rc<T>没有实现Send,它只用于单线程情景
> * 任何完全由Send类型组成的类型也被标记为Send
> * 除了原始指针之外，几乎所有的基础类型都是Send
>
> Sync:允许从多线程访问
>
> * 实现Sync的类型可以安全的被多个线程引用
> * 也就是说：如果T是Sync,那么&T就是Send
>   * 引用可以被安全的送往另一个线程
> * 基础类型都是Sync
> * 完全由Sync类型组成的类型也是Sync
>   * 但Rc<T>不是Sync的
>   * RefCell<T>和Cell<T>家族也不是Sync的
>

### unsafe

> **Unsafe超能力**
>
> * 使用unsafe关键字来切换到unsafe Rust,开启一个块，里面放着unsafe代码
> * Unsafe Rust里可执行的四个动作(unsafe超能力)：
>   * 解引用原始指针
>   * 调用unsafe函数或方法
>   * 访问或修改可变的静态变量
>   * 实现unsafe trait
> * 注意：
>   * unsafe并没有关闭借用检查或停用其它安全检查
>   * 任何内存安全相关的错误必须留在unsafe块里
>   * 尽可能隔离unsafe代码，最好将其封装在安全的抽象里，提供安全的API
>
> **解引用原始指针**
>
> * 原始指针
>   * 可变的：*mutT
>   * 不可变的：*const T。意味着指针在解引用后不能直接对其进行赋值
>   * 注意：这里的*不是解引用符号，它是类型名的一部分。
> * 与引用不同，原始指针：
>   * 允许通过同时具有不可变和可变指针或多个指向同一位置的可变指针来忽略借用规则
>   * 无法保证能指向合理的内存
>   * 允许为null
>   * 不实现任何自动清理
> * 放弃保证的安全，换取更好的性能/与其它语言或硬件接口的能力
>
> ```rust
> fn main() {
>     let mut num = 5;
>     let r1: *const i32 = &num as *const i32;
>     let r2: *mut i32 = &mut num as *mut i32;
> 
>     unsafe{
>         println!("r1: {}", *r1);
>         println!("r2: {}", *r2);
>     }
> 
>     let address = 0x012345usize;
>     let r3: *const i32 = address as *const i32;
>     unsafe{
>         println!("r3: {}", *r3);
>     }
> }
> ```
>
> ```rust
> static HELLO_WORLD: &str = "Hello, world!"; // 全局变量
> static mut COUNTER: u32 = 0; // 对可变的静态变量操作是不安全的
> fn main() {
>    println!("{}", HELLO_WORLD);
>    add_to_counter(3);
>    unsafe {
>       println!("Counter = {}", COUNTER);
>    }
> }
> 
> fn add_to_counter(inc: u32) -> u32 {
>    unsafe {
>       COUNTER += inc;
>       COUNTER
>    }
> }
> ```

### 高级Trait

> ```rust
> use std::ops::Add;
> fn main() {
>    let p1 = Point{x:1,y:2};
>    let p2 = Point{x:5,y:6};
>    let p3 = p1 + p2;
>    println!("{:?}",p3);
> }
> 
> #[derive(Debug)]
> struct Point{
>     x: i32,
>     y: i32,
> }
> 
> impl Add for Point{
>     type Output = Point;
>     fn add(self, other: Point) -> Point{
>         Point{
>             x: self.x + other.x,
>             y: self.y + other.y,
>         }
>     }
> }
> ```

### 高级类型

> **类型别名**
>
> ```rust
> type Kilometers = i32;
> type Thunk = Box<dyn Fn() + Send + 'static>;
> fn main() {
>     let x: i32 = 5;
>     let y: Kilometers = 6;
>     println!("x + y = {}", x + y);
> }
> 
> fn takes_long_type(f: Thunk){}
> ```

### 高级函数

> **函数指针**
>
> * 可以将函数传递给其它函数
> * 函数在传递过程中会被强制转换成fn类型
> * fn类型就是“函数指针(function pointer)
>
> ```rust
> fn main() {
>     let answer = do_twice(add_one, 5);
>     println!("The answer is: {}", answer);
> }
> 
> fn add_one(x: i32) -> i32 {
>     x + 1
> }
> 
> fn do_twice(f: fn(i32) -> i32, arg: i32) -> i32 {
>     f(arg) + f(arg)
> }
> ```
>
> **函数指针与闭包的不同点**
>
> * fn是一个类型，不是一个trait
>   * 可以直接指定fn为参数类型，不用声明一个以Fn trait为约束的泛型参数
> * 函数指针实现了全部3种闭包trait(Fn,FnMut,FnOnce):
>   * 总是可以把函数指针用作参数传递给一个接收闭包的函数
>   * 所以，倾向于搭配闭包trait的泛型来编写函数：可以同时接收闭包和普通函数
> * 某些情景，只想接收f而不接收闭包：
>   * 与外部不支持闭包的代码交互：C函数

### 宏

> * 宏在Rust里指的是一组相关特性的集合称谓：
> * 使用macro_rules！构建的声明宏(declarative macro)
> * 3种过程宏
>   * 自定义#[derive]宏，用于struct或enum,可以为其指定随derive属性添加的代码
>   * 类似属性的宏，在任何条目上添加自定义属性
>   * 类似函数的宏，看起来像函数调用，对其指定为参数的token进行操作
>
> **函数与宏的差别**
>
> * 本质上，宏是用来编写可以生成其它代码的代码（元编程，metaprogramming)
> * 函数在定义签名时，必须声明参数的个数和类型，宏可处理可变的参数
> * 编译器会在解释代码前展开宏
> * 宏的定义比函数复杂得多，难以阅读、理解、维护在某个文件调用宏时，必须提前定义宏或将宏引入当前作用域；
> * 数可以在任何位置定义并在任何位置使用
>
> 

