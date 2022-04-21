# Vue学习笔记

### Vue基础

##### 入门程序

> ```html
> <!DOCTYPE html>
> <html lang="zh-CN">
> <head>
>     <meta charset="UTF-8">
>     <meta http-equiv="X-UA-Compatible" content="IE=edge">
>     <meta name="viewport" content="width=device-width, initial-scale=1.0">
>     <title>Document</title>
>     <script src="../src/vue.js"></script>
> </head>
> <body>
>     <div id="root">
>         <h1>大家好，{{ msg }}</h1>
>     </div>
>     <script>
>        Vue.config.productionTip = false;
>        // 创建Vue实例
>        new Vue({
>             el: '#root',  // 用于指定当前Vue实例绑定哪个容器，值通常为CSS选择器字符串
>             data: {     // data用于存储数据，数据供el指定的容器使用
>                 msg: '才是真的好!'
>             }
>         });
>     </script>
> </body>
> </html>
> ```

##### 模板语法

> Vue模板语法有2大类
>
> 1. 差值语法：
>
>    功能：用于解析标签体内容
>
>    写法：{{ xxx }} ，xxx是js表达式，且可以读取到data中的所有属性
>
> 2. 指令语法:
>
>    功能：用于解析标签（包括：标签属性、标签体内容、绑定事件……）
>
>    举例：v-bind:herf = "xxx"  或简写为 :href = "xxx"  xxx 同样要写js表达式且可以读取到data中的所有属性
>
>    备注：Vue中有很多的指令，且形式都是v-????  ，此处，我们只是拿v-bind举例
>
> ```html
> <!DOCTYPE html>
> <html lang="zh-CN">
> <head>
>     <meta charset="UTF-8">
>     <meta http-equiv="X-UA-Compatible" content="IE=edge">
>     <meta name="viewport" content="width=device-width, initial-scale=1.0">
>     <title>Document</title>
>     <script src="../src/vue.js"></script>
> </head>
> <body>
>     <div id="root">
>         <h1>差值语法</h1>
>         <h3>你好，{{name}} </h3>
>         <hr/>
>         <h1>指令语法</h1>
>         <a v-bind:href="url" target="_blank">百度一下</a>
>         <a :href="url2" target="_blank">搜狐</a>
>     </div>
>     <script>
>        Vue.config.productionTip = false;
>        // 创建Vue实例
>        new Vue({
>             el: '#root',
>             data: {
>                 name: 'jack',
>                 url: 'https://www.baidu.com',
>                 url2: 'https://www.sohu.com'
>             }
>         });
>     </script>
> </body>
> </html>
> ```
>
> **动态参数**
>
> 从 2.6.0 开始，可以用方括号括起来的 JavaScript 表达式作为一个指令的参数：
>
> ```html
> <a v-bind:[attributeName]="url"> ... </a>
> ```
>
> 这里的 attributeName 会被作为一个 JavaScript 表达式进行动态求值，求得的值将会作为最终的参数来使用。例如，如果你的 Vue 实例有一个 data property attributeName，其值为 "href"，那么这个绑定将等价于 v-bind:href。同样地，你可以使用动态参数为一个动态的事件名绑定处理函数：
>
> ```html
> <a v-on:[eventName]="doSomething"> ... </a>
> ```
>
> *对动态参数的值的约束*
> 动态参数预期会求出一个字符串，异常情况下值为 null。这个特殊的 null 值可以被显性地用于移除绑定。任何其它非字符串类型的值都将会触发一个警告。
>
> *对动态参数表达式的约束*
> 动态参数表达式有一些语法约束，因为某些字符，如空格和引号，放在 HTML attribute 名里是无效的。例如：
>
> ```html
> <!-- 这会触发一个编译警告 -->
> <a v-bind:['foo' + bar]="value"> ... </a>
> ```
>
> 变通的办法是使用没有空格或引号的表达式，或用计算属性替代这种复杂表达式。
>
> 在 DOM 中使用模板时 (直接在一个 HTML 文件里撰写模板)，还需要避免使用大写字符来命名键名，因为浏览器会把 attribute 名全部强制转为小写：
>
> ```html
> <!--
> 在 DOM 中使用模板时这段代码会被转换为 `v-bind:[someattr]`。
> 除非在实例中有一个名为“someattr”的 property，否则代码不会工作。
> -->
> <a v-bind:[someAttr]="value"> ... </a>
> ```
>
> **修饰符**
>
> 修饰符 (modifier) 是以半角句号 . 指明的特殊后缀，用于指出一个指令应该以特殊方式绑定。例如，.prevent 修饰符告诉 v-on 指令对于触发的事件调用 event.preventDefault()：
>
> ```html
> <form v-on:submit.prevent="onSubmit">...</form>
> ```

##### 数据绑定

> ```html
> <!DOCTYPE html>
> <html lang="zh-CN">
> <head>
>  <meta charset="UTF-8">
>  <meta http-equiv="X-UA-Compatible" content="IE=edge">
>  <meta name="viewport" content="width=device-width, initial-scale=1.0">
>  <title>Document</title>
>  <script src="../src/vue.js"></script>
> </head>
> <body>
>  <div id="root">
>      单向数据绑定：<input type="text" v-bind:value="name"><br/>
>      双向数据绑定：<input type="text" v-model:value="name">
>      <hr/>
>      <h1>简写</h1>
>      单向数据绑定：<input type="text" :value="name"><br/>
>      双向数据绑定：<input type="text" v-model="name">
>  </div>
>  <script>
>     Vue.config.productionTip = false;
>     // 创建Vue实例
>     new Vue({
>          el: '#root',
>          data: {
>              name: '君莫笑',
>          }
>      });
>  </script>
> </body>
> </html>
> ```
>
> **el 的两种写法**
>
> ```html
> <div id="root">
>     <h1>你好 {{ name }}</h1>
> </div>
> <script>
>     Vue.config.productionTip = false;
>     // 创建Vue实例
>     // el的两种写法
>     const v = new Vue({
>         // el: '#root',     // 第一种写法
>         data: {
>             name: '君莫笑',
>         }
>     });
>     console.log(v);
>     v.$mount('#root')   // 第二种写法
> </script>
> ```
>
> **data 的两种写法**
>
> ```html
> <div id="root">
>     <h1>你好 {{ name }}</h1>
> </div>
> <script>
>     Vue.config.productionTip = false;
>     const v = new Vue({
>         el: '#root',
>         // data的第一种写法，对象式
>         /**
>             data: {
>                 name: '君莫笑',
>             }
>             */
>         // data的第二种写法，函数式
>         data(){
>             console.log("@@@",this);  // 此处的this是Vue实例对象
>             return {
>                 name: '君莫笑',
>             }
>         }
>     });
> </script>
> ```

##### 理解MVVM

> ![](./images/vue_mvvm2.jpg)
>
> ![](./images/vue_mvvm.jpg)
>
> ![](./images/vue_mvvm1.jpg)
>
> ```html
> <div id="root">
>     <h1>你好 {{ $options }}</h1>
>     {{ $emit }}
>     <!-- 本质上花括号里面能写Vue实例和Vue实例原型上面的所有属性 -->
> </div>
> <script>
>     Vue.config.productionTip = false;
>     const v = new Vue({
>         el: '#root',
>         // data的第一种写法，对象式
>         /**
>             data: {
>                 name: '君莫笑',
>             }
>             */
>         // data的第二种写法，函数式
>         data(){
>             console.log("@@@",this);  // 此处的this是Vue实例对象
>             return {
>                 name: '君莫笑',
>             }
>         }
>     });
> </script>
> ```
>

##### 数据代理

> Object.defineProperty()
>
> ```javascript
> <div id="root">
>     <h1>你好 {{ $options }}</h1>
> {{ $emit }}
> <!-- 本质上花括号里面能写Vue实例上面的所有属性 -->
> </div>
> <script>
>     //    Vue.config.productionTip = false;
>     //    const v = new Vue({
>     //         el: '#root',
>     //         data(){
>     //             return {
>     //                 name: '君莫笑',
>     //             }
>     //         }
>     //     });
>     let person = {
>         name:'张三',
>         sex:'男',
>     }
> let number = 18
> Object.defineProperty(person,'age',{
>     // value:18,
>     // enumerable:true,  // 控制该属性是否可枚举，默认是false
>     // writable:true,  // 控制属性是否可以被修改，默认值是false
>     // configurable:true,  // 控制属性是否可以被删除，默认值是false
> 
>     // 当有人读取person的age属性时，get函数(getter)就会被调用，且返回值就是age的值
>     get(){
>         return number;
>     },
>     // 当有人修改person的age属性时，set函数(setter)就会被调用，且返回值就是age的值
>     set(value){
>         console.log("设置的值是：",value)
>     }
> 
> })
> // console.log(Object.keys(person))
> </script>
> ```

##### 事件处理

> ```javascript
> <div id="root">
>  <h2>欢迎来到 {{name}} 学习</h2>
> <button v-on:click="showInfo(66,$event)">点我提示信息</button>
> </div>
> <script>
>  Vue.config.productionTip = false;
> const v = new Vue({
>  el: '#root',
>  data(){
>      return {
>          name: '白鹿书院',
>      }
>  },
>  methods:{
>      showInfo(num,e){
>          console.log(num)
>          console.log(e)
>          // alert('同学你好！')
>      }
>  }
> });
> </script>
> ```
>
> **事件修饰符**
>
> Vue中的事件修饰符
>
> 1. prevent ：阻止默认事件（常用）
> 2. stop ：阻止事件冒泡
> 3. once：事件只触发一次
> 4. capture：使用事件的捕获模式
> 5. self：只有event.target是当前操作的元素时才触发事件
> 6. passive：事件的默认行为立即执行，无需等待事件回调执行完毕
>
> ```html
> <div id="root">
>     <h2>欢迎来到 {{name}} 学习</h2>
>     <a href="http://www.baidu.com" @click.prevent="showInfo">点我提示信息</a>
>    <!-- 事件修饰符可以连写-->
>     <a href="http://www.baidu.com" @click.prevent.stop="showInfo">点我提示信息</a>
> </div>
> <script>
>     Vue.config.productionTip = false;
>     const v = new Vue({
>         el: '#root',
>         data(){
>             return {
>                 name: '白鹿书院',
>             }
>         },
>         methods:{
>             showInfo(){
>                 alert('同学你好！')
>             }
>         }
>     });
> </script>
> ```
>
> **键盘事件**
>
> Ⅰ. Vue中常用的按键别名
>
> 1. 回车：enter
> 2. 删除：delete：（捕获“删除”和“退格”键）
> 3. 退出：esc
> 4. 空格：space
> 5. 换行：tab（特殊，必须配合keydown使用）
> 6. 上：up
> 7. 下：down
> 8. 左：left 
> 9. 右：right
>
> Ⅱ. Vue未提供别名的按键，可以使用按键原始的key值去绑定，但注意要转为kebab-case(短横线命名)
>
> Ⅲ. 系统修饰键（用法特殊）：ctrl、alt、shift、meta
>
> 1. 配合keyup使用：按下修饰键的同时，再按下其他键，随后释放其他键，事件才被触发。
> 2. 配合keydown使用：正常触发事件。
>
> Ⅳ. 也可以使用keyCode去指定具体的按键（不推荐）
>
> Ⅴ. Vue.config.keyCodes.自定义键名 = 键码，可以定制按键别名
>
> ```html
> <div id="root">
>     <h2>欢迎来到 {{name}} 学习</h2>
>     <input type="text" placeholder="按下回车，提示输入" @keyup.enter="showInfo">
> </div>
> <script>
>     Vue.config.productionTip = false;
>     const v = new Vue({
>         el: '#root',
>         data(){
>             return {
>                 name: '白鹿书院',
>             }
>         },
>         methods:{
>             showInfo(e){
>                 console.log(e.key);
>             }
>         }
>     });
> </script>
> ```

##### 计算属性

> **定义**：要用的属性不存在，要通过已有的属性计算得来
>
> **原理**：底层借助了Object.defineProperty方法提供的getter和setter
>
> **优势**：与methods实现相比，内部有缓存机制（复用），效率更高，调试方便
>
> **备注**：
>
> 1. 计算属性最终会出现在vm上，直接读取使用即可
> 2. 如果计算属性要被修改，就必须写set函数响应修改，且set中要引起计算时依赖的数据发生改变
>
> ```html
> <div id="root">
>     长：<input type="text" v-model="length"><br/>
>     宽：<input type="text" v-model="width"><br/>
>     高：<input type="text" v-model="height"><br/>
>     体积：<span>{{volume}}</span>
> </div>
> <script>
>     Vue.config.productionTip = false;
>     const v = new Vue({
>         el: '#root',
>         data(){
>             return {
>                 length:0,
>                 width:0,
>                 height:0
>             }
>         },
>         computed:{
>             // 完整写法
>             // volume:{
>             //     // 当读取volume，get就会被调用，且返回值就是volume的值
>             //     // get调用时机，1.初次读取volume时 2.所依赖的数据发生变化时
>             //     get(){
>             //         // 此处的this是vm
>             //         console.log("volume被调用");
>             //         return this.length * this.width * this.height
>             //     },
>             //     // set调用时机：当volume被修改时
>             //     set(value){
>             //         this.length = 2;
>             //         this.width = 1;
>             //         this.height = value / this.length / this.width;
>             //     }
>             // }
> 
>             // 简写（前提：只需要get方法，不需要set方法）
>             // 函数名即为计算属性名
>             volume(){
>                 return this.length * this.width * this.height
>             }
>         },
>         methods:{
> 
>         }
>     });
> </script>
> ```
>
> 

