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



