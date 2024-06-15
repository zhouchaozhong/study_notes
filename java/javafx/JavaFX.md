# JavaFX学习笔记

### 入门示例

项目结构

<img src="./images/project_structure.jpg" alt="项目结构" style="zoom:50%;" />

main.java

```java
package org.example;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        System.out.println("main ===== " + Thread.currentThread().getName());
        Application.launch(Launch.class,args);
    }
}
```

Launch.java

```java
package org.example;

import cn.hutool.core.io.resource.ResourceUtil;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.URL;

public class Launch extends Application {
    @Override
    public void init() throws Exception {
        System.out.println("init ===== " + Thread.currentThread().getName());
    }

    @Override
    public void stop() throws Exception {
        System.out.println("stop ===== " + Thread.currentThread().getName());
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("start ===== " + Thread.currentThread().getName());
        // 设置标题
        stage.setTitle("JavaFX入门");
        // 获取图标资源
        System.out.println(this.getClass().getResource("/icon/info.png"));
        // 设置标题栏图标
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon/info.png")));
        // 设置窗口宽度
        stage.setWidth(1000);
        // 设置窗口高度
        stage.setHeight(500);
        // 设置最小化
//        stage.setIconified(true);
        // 设置最大化
        stage.setMaximized(true);
        stage.show();
        stage.close();
    }
}
```

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>javafxdemo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <dependencies>
        <!-- https://mvnrepository.com/artifact/org.openjfx/javafx-controls -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>22.0.1</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>22.0.1</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/cn.hutool/hutool-all -->
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>5.8.27</version>
        </dependency>
    </dependencies>


</project>
```

module-info.java

```java
module org.example{
    requires javafx.controls;
    requires cn.hutool;
    exports org.example;
}
```

### Stage窗口，窗口风格，模态框

> **Stage常用方法**
>
> ```java
>     public void start(Stage stage) throws Exception{
>         // 设置标题
>         stage.setTitle("JavaFX入门");
>         // 设置窗口宽度
>         stage.setWidth(300);
>         // 设置窗口高度
>         stage.setHeight(300);
>         // 设置透明度
>         // stage.setOpacity(0.5);
>         // 设置窗口置顶
>         // stage.setAlwaysOnTop(true);
>         // 设置X，Y坐标
>         stage.setX(100);
>         stage.setY(100);
>         // 监听窗口x轴坐标的变化
>         stage.xProperty().addListener(new ChangeListener<Number>() {
>             @Override
>             public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
>                 System.out.println("oldValue: " + oldValue);
>                 System.out.println("newValue: " + newValue);
>             }
>         });
>       	// 设置窗口风格,可选值：DECORATED，TRANSPARENT，UNDECORATED，UNIFIED，UTILITY
>       	// 常用的有DECORATED和UTILITY
>         stage.initStyle(StageStyle.DECORATED);
>         stage.show();
>     }
> ```
>
> **模态框**
>
> ```java
> public void start(Stage stage) throws Exception{
>   stage.show();
>   Stage s1 = new Stage();
>   s1.setTitle("s1");
>   // 设置模态框，当s1窗口没有关闭时，不能操作stage窗口
>   s1.initOwner(stage);
>   s1.initModality(Modality.WINDOW_MODAL);
>   s1.show();
> }
> ```
>

### Platform类的使用

> ```java
> package org.example;
> 
> import javafx.application.Application;
> import javafx.application.ConditionalFeature;
> import javafx.application.Platform;
> import javafx.stage.Stage;
> 
> public class Launch extends Application {
>     @Override
>     public void init() throws Exception {
>     }
> 
>     @Override
>     public void stop() throws Exception {
>     }
> 
>     @Override
>     public void start(Stage stage) throws Exception{
>         // 在空闲的时间执行Runnable接口里面的内容，适合在后台执行轻量级的任务，底层是用队列实现的
>         Platform.runLater(()->{
>             System.out.println("run later test!!!");
>             System.out.println(Thread.currentThread().getName());
>         });
>         // 设置是否隐式退出，设置为true，关闭窗口即关闭程序，设置为false时，关闭窗口时，程序不会退出
>         Platform.setImplicitExit(true);
>         // 检查目标运行平台是否支持某些特性
>         boolean supported = Platform.isSupported(ConditionalFeature.SCENE3D);
>         System.out.println("is supported scene3D  :  " + supported);
>         stage.show();
>     }
> }
> ```

### Screen类

> ```java
> public void start(Stage stage) throws Exception{
>   Screen screen = Screen.getPrimary();
>   Rectangle2D rec1 = screen.getBounds();
>   Rectangle2D rec2 = screen.getVisualBounds();
>   // 下面是全部屏幕，宽高和坐标
>   System.out.println("左上角x = " + rec1.getMinX() + "  左上角y = " + rec1.getMinX());
>   System.out.println("右下角x = " + rec1.getMaxX() + "  右下角y = " + rec1.getMaxY());
>   System.out.println("宽度 = " + rec1.getWidth() + "  高度 = " + rec1.getHeight());
> 
>   // 下面是可视区域屏幕，宽高和坐标
>   System.out.println("左上角x = " + rec2.getMinX() + "  左上角y = " + rec2.getMinX());
>   System.out.println("右下角x = " + rec2.getMaxX() + "  右下角y = " + rec2.getMaxY());
>   System.out.println("宽度 = " + rec2.getWidth() + "  高度 = " + rec2.getHeight());
> 
>   // 当前屏幕DPI
>   double dpi = screen.getDpi();
>   System.out.println("当前屏幕DPI = " + dpi);
> }
> ```
>

### Scene类

> ```java
> public void start(Stage stage) throws Exception{
>   HostServices host = getHostServices();
>   host.showDocument("http://cn.bing.com");
>   URL url = getClass().getResource("/icon/info.png");
>   String path = url.toExternalForm();
>   Button button = new Button("按钮");
>   button.setCursor(Cursor.MOVE);
>   button.setPrefWidth(100);
>   button.setPrefHeight(50);
>   Group group = new Group();
>   group.getChildren().add(button);
>   Scene scene = new Scene(group);
>   // 设置鼠标形状
>   // scene.setCursor(Cursor.CLOSED_HAND);
>   scene.setCursor(Cursor.cursor(path));
>   stage.setScene(scene);
>   stage.setWidth(300);
>   stage.setHeight(300);
>   stage.show();
> }
> ```

### Group容器的使用

> ```java
> public void start(Stage stage) throws Exception{
>   Button b1 = new Button("btn1");
>   Button b2 = new Button("btn2");
>   Button b3 = new Button("btn3");
>   b1.setLayoutX(0);
>   b1.setLayoutY(20);
>   b2.setLayoutX(150);
>   b2.setLayoutY(20);
>   b3.setLayoutX(300);
>   b3.setLayoutY(20);
> 
>   b1.setPrefWidth(100);
>   b1.setPrefHeight(50);
>   Group group = new Group();
>   // 往Group容器中添加子组件
>   group.getChildren().addAll(b1,b2,b3);
>   group.setOpacity(0.5);
>   Object[] arrs = group.getChildren().toArray();
>   for (Object obj : arrs) {
>     Button btn = (Button) obj;
>     btn.setPrefWidth(100);
>     btn.setPrefHeight(50);
>   }
>   // 给按钮增加点击事件
>   b1.setOnAction(new EventHandler<ActionEvent>() {
>     @Override
>     public void handle(ActionEvent actionEvent) {
>       Button b4 = new Button("b4");
>       group.getChildren().add(b4);
>     }
>   });
>   // 给Group容器添加监听器
>   group.getChildren().addListener(new ListChangeListener<Node>() {
>     @Override
>     public void onChanged(Change<? extends Node> change) {
>       System.out.println("当前子组件数量 = " + change.getList().size());
>     }
>   });
>   // 设置子组件是否有默认尺寸
>   // group.setAutoSizeChildren(false);
>   // 移除Group容器中的组件
>   // group.getChildren().remove(0);
>   // 移除Group容器中所有子组件
>   // group.getChildren().clear();
>   Scene scene = new Scene(group);
>   stage.setScene(scene);
>   stage.setWidth(500);
>   stage.setHeight(300);
>   stage.show();
> }
> ```

### Button按钮

> ```java
> Button b1 = new Button();
> // 设置按钮上的文本
> b1.setText("按钮一");
> // 设置按钮位置
> b1.setLayoutX(100);
> b1.setLayoutY(100);
> // 设置按钮的宽和高
> b1.setPrefWidth(100);
> b1.setPrefHeight(50);
> // 设置字体
> //        b1.setFont(Font.font("sans-serif", 20));
> //        b1.setTextFill(Paint.valueOf("#CD0000"));
> // 设置背景色，圆角,背景四个角的空白填充
> //        BackgroundFill bgf = new BackgroundFill(Paint.valueOf("#8FBC8F"),new CornerRadii(10),new Insets(5));
> //        Background bg = new Background(bgf);
> //        b1.setBackground(bg);
> //        // 设置边框颜色，边框样式，边框圆角，边框宽度
> //        BorderStroke bos = new BorderStroke(Paint.valueOf("#7B68EE"), BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(5));
> //        Border border = new Border(bos);
> //        b1.setBorder(border);
> b1.setStyle("-fx-background-color: #7CCD7C;-fx-background-radius: 20;-fx-text-fill: #CD0000");
> // 设置按钮的单击事件
> b1.setOnAction(new EventHandler<ActionEvent>() {
>   @Override
>   public void handle(ActionEvent event) {
>     Button btn = (Button) event.getSource();
>     System.out.println("按钮文本是：" + btn.getText());
>   }
> });
> Group group = new Group();
> group.getChildren().add(b1);
> Scene scene = new Scene(group);
> stage.setScene(scene);
> stage.setWidth(500);
> stage.setHeight(300);
> stage.show();
> ```

### 双击事件和检测键盘按键

> ```java
> public void start(Stage stage) throws Exception{
>   Button b1 = new Button();
>   // 设置按钮上的文本
>   b1.setText("按钮一");
>   // 设置按钮位置
>   b1.setLayoutX(100);
>   b1.setLayoutY(100);
>   // 设置按钮的宽和高
>   b1.setPrefWidth(100);
>   b1.setPrefHeight(50);
>   b1.setStyle("-fx-background-color: #7CCD7C;-fx-background-radius: 20;-fx-text-fill: #CD0000");
>   // 设置按钮的单击事件
>   b1.setOnAction(new EventHandler<ActionEvent>() {
>     @Override
>     public void handle(ActionEvent event) {
>       Button btn = (Button) event.getSource();
>       System.out.println("按钮文本是：" + btn.getText());
>     }
>   });
>   b1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
>     @Override
>     public void handle(MouseEvent mouseEvent) {
>       System.out.println("鼠标按键 = " + mouseEvent.getButton().name());
>       if(mouseEvent.getClickCount() == 2){
>         System.out.println("鼠标双击了！");
>       }
>     }
>   });
>   // 监听键盘按键按下事件
>   b1.setOnKeyPressed(new EventHandler<KeyEvent>() {
>     @Override
>     public void handle(KeyEvent keyEvent) {
>       System.out.println("按下的按键是：" + keyEvent.getCode().getName());
>     }
>   });
>   // 监听键盘按键释放事件
>   b1.setOnKeyReleased(new EventHandler<KeyEvent>() {
>     @Override
>     public void handle(KeyEvent keyEvent) {
>       System.out.println("释放");
>     }
>   });
>   Group group = new Group();
>   group.getChildren().add(b1);
>   Scene scene = new Scene(group);
>   stage.setScene(scene);
>   stage.setWidth(500);
>   stage.setHeight(300);
>   stage.show();
> }
> ```
>

### 设置快捷键

> ```java
> @Override
> public void start(Stage stage) throws Exception{
> Button b1 = new Button();
> // 设置按钮上的文本
> b1.setText("按钮一");
> // 设置按钮位置
> b1.setLayoutX(100);
> b1.setLayoutY(100);
> // 设置按钮的宽和高
> b1.setPrefWidth(100);
> b1.setPrefHeight(50);
> b1.setStyle("-fx-background-color: #7CCD7C;-fx-background-radius: 20;-fx-text-fill: #CD0000");
> b1.setOnAction(new EventHandler<ActionEvent>() {
> @Override
> public void handle(ActionEvent event) {
> System.out.println("setOnAction");
> }
> });
> Group group = new Group();
> group.getChildren().add(b1);
> Scene scene = new Scene(group);
> // 设置快捷键
> // 第一种
> KeyCombination kc1 = new KeyCodeCombination(KeyCode.C, KeyCombination.ALT_DOWN, KeyCombination.CONTROL_DOWN);
> Mnemonic m1 = new Mnemonic(b1, kc1);
> scene.addMnemonic(m1);
> // 第二种 不常用
> KeyCombination kc2 = new KeyCharacterCombination("A", KeyCombination.ALT_DOWN);
> Mnemonic m2 = new Mnemonic(b1, kc2);
> scene.addMnemonic(m2);
> // 第三种 不常用
> KeyCombination kc3 = new KeyCodeCombination(KeyCode.K, KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN,KeyCombination.ALT_DOWN,KeyCombination.META_DOWN,KeyCombination.SHORTCUT_DOWN);
> Mnemonic m3 = new Mnemonic(b1, kc3);
> scene.addMnemonic(m3);
> // 第四种 推荐使用
> KeyCombination kcc = new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN);
> scene.getAccelerators().put(kcc, new Runnable() {
> @Override
> public void run() {
> System.out.println("run 方法");
> b1.fire();
> }
> });
> 
> stage.setScene(scene);
> stage.setWidth(500);
> stage.setHeight(300);
> stage.show();
> }
> ```

### 文本框，密码框，标签

> ```java
> @Override
> public void start(Stage stage) throws Exception{
>   TextField tf = new TextField();
>   tf.setText("这是文本框");
>   tf.setLayoutX(100);
>   tf.setLayoutY(20);
>   tf.setFont(Font.font(14));
>   Tooltip tip = new Tooltip("这是提示！");
>   tip.setFont(Font.font(12));
>   tf.setTooltip(tip);
>   tf.setPromptText("请输入七个字以下");
>   tf.setFocusTraversable(false);
>   tf.textProperty().addListener(new ChangeListener<String>() {
>     @Override
>     public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
>       System.out.println(newValue);
>     }
>   });
>   // 监听文本框选择了哪几个字
>   tf.selectedTextProperty().addListener(new ChangeListener<String>() {
>     @Override
>     public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
>       System.out.println(newValue);
>     }
>   });
> 
>   // 密码框
>   PasswordField pwf = new PasswordField();
>   pwf.setLayoutX(100);
>   pwf.setLayoutY(60);
>   // 标签
>   Label label = new Label();
>   label.setText("这是标签");
>   label.setLayoutX(100);
>   label.setLayoutY(90);
>   label.setOnMouseClicked(new EventHandler<MouseEvent>() {
>     @Override
>     public void handle(MouseEvent mouseEvent) {
>       System.out.println("鼠标单击事件触发！");
>     }
>   });
>   // 设置标签文字颜色
>   label.setTextFill(Paint.valueOf("red"));
>   Group root = new Group();
>   root.getChildren().addAll(tf,pwf,label);
>   Scene scene = new Scene(root);
>   stage.setScene(scene);
>   stage.setWidth(500);
>   stage.setHeight(300);
>   stage.show();
> }
> ```

### AnchorPane布局类

> ```java
> @Override
> public void start(Stage stage) throws Exception{
>   Button b1 = new Button("button1");
>   b1.setLayoutX(100.0);
>   b1.setLayoutY(100.0);
>   Button b2 = new Button("Button2");
>   Button b3 = new Button("Button3");
>   Button b4 = new Button("Button4");
>   AnchorPane ap = new AnchorPane();
>   ap.getChildren().addAll(b2,b1);
>   ap.setStyle("-fx-background-color: #FF3E96");
> 
>   Group g1 = new Group();
>   Group g2 = new Group();
>   g1.getChildren().addAll(b1,b2);
>   g2.getChildren().addAll(b3,b4);
>   // 距离参考点上面100个像素
>   AnchorPane.setTopAnchor(g1,100.0);
>   // 距离参考点左边100个像素
>   AnchorPane.setLeftAnchor(g1,100.0);
>   AnchorPane.setTopAnchor(g2,200.0);
>   AnchorPane.setLeftAnchor(g2,200.0);
>   ap.getChildren().addAll(g1,g2);
>   Scene scene = new Scene(ap);
>   stage.setScene(scene);
>   stage.setWidth(500);
>   stage.setHeight(300);
>   stage.show();
> }
> ```

>```java
>public void start(Stage stage) throws Exception{
>  AnchorPane ap = new AnchorPane();
>  ap.setStyle("-fx-background-color: #FF3E96");
>  AnchorPane ap2 = new AnchorPane();
>  ap2.setStyle("-fx-background-color: #80aaff");
>  ap.getChildren().add(ap2);
>  Scene scene = new Scene(ap);
>  stage.setScene(scene);
>  stage.setWidth(500);
>  stage.setHeight(300);
>  stage.show();
>
>  // 因为stage  show才会进行宽高初始化，所以获取stage宽高要在show之后
>  // 距离参考点上面100个像素
>  AnchorPane.setTopAnchor(ap2,0.0);
>  // 距离参考点左边100个像素
>  AnchorPane.setLeftAnchor(ap2,0.0);
>  AnchorPane.setRightAnchor(ap2,ap.getWidth() / 2);
>  AnchorPane.setBottomAnchor(ap2,ap.getHeight() / 2);
>  stage.widthProperty().addListener(new ChangeListener<Number>() {
>    @Override
>    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
>      AnchorPane.setRightAnchor(ap2,ap.getWidth() / 2);
>    }
>  });
>  stage.heightProperty().addListener(new ChangeListener<Number>() {
>    @Override
>    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
>      AnchorPane.setBottomAnchor(ap2,ap.getHeight() / 2);
>    }
>  });
>}
>```

### HBox和VBox

> ```java
> public void start(Stage stage) throws Exception{
>   Button b1 = new Button("按钮一");
>   Button b2 = new Button("按钮二");
>   Button b3 = new Button("按钮三");
>   AnchorPane ap = new AnchorPane();
>   ap.setStyle("-fx-background-color: #AEEEEE");
>   // 水平布局
>   HBox hb = new HBox();
>   // 垂直布局
>   // VBox hb = new VBox();
>   hb.setPrefWidth(200);
>   hb.setPrefHeight(100);
>   // 设置内边距
>   hb.setPadding(new Insets(10));
>   // 子组件之间的间距
>   hb.setSpacing(10);
>   // 设置外边距
>   HBox.setMargin(b1,new Insets(10));
>   // 设置对齐方式【下方居中对齐】
>   hb.setAlignment(Pos.BOTTOM_CENTER);
>   hb.setStyle("-fx-background-color: #E066FF");
>   hb.getChildren().addAll(b1,b2,b3);
>   ap.getChildren().addAll(hb);
>   Scene scene = new Scene(ap);
>   stage.setScene(scene);
>   stage.setWidth(500);
>   stage.setHeight(300);
>   stage.show();
> }
> ```

### setManaged和setVisible以及setOpacity

* setManaged:控制组件是否存在，类似于css中的display:none，设置为false，组件不再占用空间
* setVisible: 控制组件的可见性：类似于css中的visibility:hidden，设置为false，组件依然占用空间，只是不可见
* setOpacity:控制组件的透明度

### BorderPane布局类

> ```java
> public void start(Stage stage) throws Exception{
>   AnchorPane ap1 = new AnchorPane();
>   ap1.setStyle("-fx-background-color: #00FF7F");
>   ap1.setPrefSize(100, 100);
> 
>   AnchorPane ap2 = new AnchorPane();
>   ap2.setStyle("-fx-background-color: #8B658B");
>   ap2.setPrefSize(100, 100);
> 
>   AnchorPane ap3 = new AnchorPane();
>   ap3.setStyle("-fx-background-color: #FF6A6A");
>   ap3.setPrefSize(100, 100);
> 
>   AnchorPane ap4 = new AnchorPane();
>   ap4.setStyle("-fx-background-color: #FFA500");
>   ap4.setPrefSize(100, 100);
> 
>   AnchorPane ap5 = new AnchorPane();
>   ap5.setStyle("-fx-background-color: #8B8989");
>   ap5.setPrefSize(100, 100);
> 
>   // 设置上下左右中间的布局方式
>   BorderPane bp = new BorderPane();
>   bp.setStyle("-fx-background-color: #EECBAD");
>   bp.setTop(ap1);
>   bp.setBottom(ap2);
>   bp.setLeft(ap3);
>   bp.setRight(ap4);
>   bp.setCenter(ap5);
> 
>   Scene scene = new Scene(bp);
>   stage.setScene(scene);
>   stage.setWidth(500);
>   stage.setHeight(300);
>   stage.show();
> }
> ```

### FlowPane布局类

> ```java
> // 流式布局，子组件根据空间自动调整位置
> public void start(Stage stage) throws Exception{
>   Button b1 = new Button("按钮一");
>   Button b2 = new Button("按钮二");
>   Button b3 = new Button("按钮三");
>   Button b4 = new Button("按钮四");
>   Button b5 = new Button("按钮五");
>   Button b6 = new Button("按钮六");
>   FlowPane fp = new FlowPane();
>   fp.getChildren().addAll(b1,b2,b3,b4,b5,b6);
>   // fp.setMargin(b1,new Insets(0,10,0,10));
>   // 设置子组件的水平间距
>   fp.setHgap(10);
>   // 设置子组件的垂直间距
>   fp.setVgap(10);
>   Scene scene = new Scene(fp);
>   stage.setScene(scene);
>   stage.setWidth(500);
>   stage.setHeight(300);
>   stage.show();
> }
> ```

### GridPane布局类

> ```java
> public void start(Stage stage) throws Exception{
>   Button b1 = new Button("按钮一");
>   Button b2 = new Button("按钮二");
>   Button b3 = new Button("按钮三");
>   Button b4 = new Button("按钮四");
>   Button b5 = new Button("按钮五");
>   Button b6 = new Button("按钮六");
>   Button b7 = new Button("按钮七");
>   Button b8 = new Button("按钮八");
>   // 网格布局
>   GridPane grid = new GridPane();
>   grid.setStyle("-fx-background-color: #EE6AA7");
>   grid.setHgap(10);
>   grid.setVgap(10);
>   // 设置子组件在GridPane里的第几列第几行
>   grid.add(b1, 0, 0);
>   grid.add(b2, 1, 0);
>   grid.add(b3, 2, 0);
>   grid.add(b4, 3, 0);
>   grid.add(b5, 0, 1);
>   grid.add(b6, 1, 1);
>   grid.add(b7, 2, 1);
>   grid.add(b8, 3, 1);
>   Scene scene = new Scene(grid);
>   stage.setScene(scene);
>   stage.setWidth(500);
>   stage.setHeight(300);
>   stage.show();
> }
> ```

### StackPane布局类

> ```java
> public void start(Stage stage) throws Exception{
>   Button b1 = new Button("按钮一");
>   Button b2 = new Button("按钮二");
>   Button b3 = new Button("按钮三");
>   Button b4 = new Button("按钮四");
>   Button b5 = new Button("按钮五");
>   // 只显示最后一个加入容器的子组件
>   StackPane stack = new StackPane();
>   stack.setStyle("-fx-background-color: #EE6AA7");
>   stack.getChildren().addAll(b1,b2,b3,b4,b5);
>   stack.setPadding(new Insets(10));
>   stack.setAlignment(Pos.BOTTOM_LEFT);
>   Scene scene = new Scene(stack);
>   stage.setScene(scene);
>   stage.setWidth(500);
>   stage.setHeight(300);
>   stage.show();
> }
> ```

### TextFlow布局类

> ```java
> public void start(Stage stage) throws Exception{
>   // 文字布局
>   Text t1 = new Text("刻晴\n");
>   Text t2 = new Text("宵宫\n");
>   Text t3 = new Text("神里绫华\n");
>   // 设置字体
>   t1.setFont(Font.font(20));
>   // 设置颜色
>   t1.setFill(Paint.valueOf("#FF82AB"));
>   t2.setFont(Font.font(20));
>   t3.setFont(Font.font(20));
>   TextFlow tf = new TextFlow();
>   tf.setStyle("-fx-background-color: #EECFA1");
>   tf.getChildren().addAll(t1, t2, t3);
>   // 设置行间距
>   tf.setLineSpacing(10);
>   // 设置文字对齐方式
>   tf.setTextAlignment(TextAlignment.CENTER);
>   Scene scene = new Scene(tf);
>   stage.setScene(scene);
>   stage.setWidth(500);
>   stage.setHeight(300);
>   stage.show();
> }
> ```

### TilePane布局类

> ```java
> public void start(Stage stage) throws Exception{
>   Button b1 = new Button("按钮一");
>   Button b2 = new Button("按钮二");
>   Button b3 = new Button("按钮三");
>   Button b4 = new Button("按钮四");
>   Button b5 = new Button("按钮五");
>   Button b6 = new Button("按钮六");
>   // 瓷砖瓦片布局，每块都保持一样
>   TilePane tile = new TilePane();
>   tile.setStyle("-fx-background-color: #EE6AA7");
>   tile.getChildren().addAll(b1,b2,b3,b4,b5,b6);
>   tile.setHgap(10);
>   tile.setVgap(10);
>   tile.setMargin(b1,new Insets(20));
>   Scene scene = new Scene(tile);
>   stage.setScene(scene);
>   stage.setWidth(500);
>   stage.setHeight(300);
>   stage.show();
> }
> ```

### DialogPane布局类

> ```java
> public void start(Stage stage) throws Exception{
>   Button btn = new Button("点击弹出窗口");
>   AnchorPane ap = new AnchorPane();
>   ap.setStyle("-fx-background-color: #fff");
>   AnchorPane.setTopAnchor(btn,100.00);
>   AnchorPane.setLeftAnchor(btn,100.00);
>   ap.getChildren().add(btn);
>   btn.setOnAction(new EventHandler<ActionEvent>() {
>     @Override
>     public void handle(ActionEvent event) {
>       // 对话框面板
>       DialogPane dp = new DialogPane();
>       dp.setHeaderText("提示信息");
>       dp.setContentText("提示详细内容");
>       // 设置按钮
>       dp.getButtonTypes().add(ButtonType.APPLY);
>       dp.getButtonTypes().add(ButtonType.CANCEL);
>       Button applyBtn = (Button) dp.lookupButton(ButtonType.APPLY);
>       Button cancelBtn = (Button) dp.lookupButton(ButtonType.CANCEL);
>       // 设置左上角图片
>       ImageView iv = new ImageView("file:src/main/resources/icon/info.png");
>       iv.setFitHeight(30);
>       iv.setFitWidth(30);
>       dp.setGraphic(iv);
>       dp.setExpandableContent(new Text("这是扩展内容"));
>       Stage stage1 = new Stage();
>       Scene sc = new Scene(dp);
>       stage1.setScene(sc);
>       stage1.initOwner(stage);
>       stage1.initModality(Modality.WINDOW_MODAL);
>       stage1.initStyle(StageStyle.UTILITY);
>       stage1.setResizable(false);
>       stage1.show();
>       applyBtn.setOnAction(new EventHandler<ActionEvent>() {
>         @Override
>         public void handle(ActionEvent event) {
>           System.out.println("apply按钮被点击了！");
>         }
>       });
>       cancelBtn.setOnAction(new EventHandler() {
>         @Override
>         public void handle(Event event) {
>           stage1.close();
>         }
>       });
>     }
>   });
>   Scene scene = new Scene(ap);
>   stage.setScene(scene);
>   stage.setWidth(500);
>   stage.setHeight(300);
>   stage.show();
> }
> ```

### 多任务

> ```java
> package org.example;
> 
> import javafx.application.Application;
> import javafx.concurrent.ScheduledService;
> import javafx.concurrent.Task;
> import javafx.event.ActionEvent;
> import javafx.event.EventHandler;
> import javafx.scene.Scene;
> import javafx.scene.control.Button;
> import javafx.scene.control.Label;
> import javafx.scene.layout.AnchorPane;
> import javafx.scene.paint.Paint;
> import javafx.scene.text.Font;
> import javafx.stage.Stage;
> import javafx.util.Duration;
> 
> public class Launch extends Application {
>     @Override
>     public void init() throws Exception {
> 
>     }
> 
>     @Override
>     public void stop() throws Exception {
> 
>     }
> 
>     @Override
>     public void start(Stage stage) throws Exception{
>         Button btn = new Button("点击运行多任务");
>         AnchorPane ap = new AnchorPane();
>         ap.setStyle("-fx-background-color: #fff");
>         AnchorPane.setTopAnchor(btn,100.00);
>         AnchorPane.setLeftAnchor(btn,100.00);
>         Label label = new Label();
>         label.setTextFill(Paint.valueOf("#FF00FF"));
>         label.setFont(Font.font(50));
>         label.setText("0");
>         AnchorPane.setTopAnchor(label,30.00);
>         AnchorPane.setLeftAnchor(label,150.00);
>         ap.getChildren().addAll(btn,label);
>         // 运行多任务
>         btn.setOnAction(new EventHandler<ActionEvent>() {
>             @Override
>             public void handle(ActionEvent event) {
>                 MyScheduledService myService = new MyScheduledService(label);
>                 // 设置2秒后执行
>                 myService.setDelay(Duration.seconds(2.00));
>                 // 设置运行间隔为1秒
>                 myService.setPeriod(Duration.seconds(1.00));
>                 myService.start();
>             }
>         });
> 
>         Scene scene = new Scene(ap);
>         stage.setScene(scene);
>         stage.setWidth(500);
>         stage.setHeight(300);
>         stage.show();
>     }
> 
> }
> 
> class MyScheduledService extends ScheduledService<Integer> {
>     public static int num = 0;
>     public Label label;
>     MyScheduledService(Label label) {
>         this.label = label;
>     }
>     @Override
>     protected Task<Integer> createTask() {
>         return new Task<Integer>() {
>             // 此方法不是UI线程执行的
>             @Override
>             protected Integer call() throws Exception {
>                 System.out.println("call method " + Thread.currentThread().getName());
>                 return num++;
>             }
> 
>             // 这个方法是UI线程执行的
>             @Override
>             protected void updateValue(Integer value) {
>                 System.out.println("updateValue method " + Thread.currentThread().getName());
>                 System.out.println("updateValue " + value);
>                 label.setText(String.valueOf(num));
>                 super.updateValue(value);
>             }
>         };
>     }
> }
> ```

### 简单登录窗口示例

> ```java
> public void start(Stage stage) throws Exception{
>   Label lName = new Label("账号：");
>   Label lPassword = new Label("密码：");
> 
>   TextField tName = new TextField();
>   PasswordField pPassword = new PasswordField();
> 
>   Button loginBtn = new Button("登录");
>   Button clearBtn = new Button("清除");
> 
>   GridPane gp = new GridPane();
>   gp.setStyle("-fx-background-color: #FFEBCD");
>   gp.add(lName,0,0);
>   gp.add(tName,1,0);
>   gp.add(lPassword,0,1);
>   gp.add(pPassword,1,1);
>   gp.add(clearBtn,0,2);
>   gp.add(loginBtn,1,2);
>   gp.setHgap(10);
>   gp.setVgap(10);
>   gp.setAlignment(Pos.CENTER);
>   GridPane.setMargin(loginBtn,new Insets(0,0,0,120));
> 
>   clearBtn.setOnAction(new EventHandler<ActionEvent>() {
>     @Override
>     public void handle(ActionEvent event) {
>       tName.setText("");
>       pPassword.setText("");
>     }
>   });
> 
>   loginBtn.setOnAction(new EventHandler<ActionEvent>() {
>     @Override
>     public void handle(ActionEvent event) {
>       tName.setUserData("admin");
>       pPassword.setUserData("12345");
>       String name = tName.getText();
>       String password = pPassword.getText();
>       if(tName.getUserData().equals(name) && pPassword.getUserData().equals(password)){
>         System.out.println("登录成功！");
>         stage.close();
>       }else{
>         System.out.println("登录失败！");
>         // 设置渐入动画
>         FadeTransition ft = new FadeTransition();
>         ft.setDuration(Duration.seconds(0.5));
>         ft.setNode(gp);
>         ft.setFromValue(0);
>         ft.setToValue(1);
>         ft.play();
>       }
>     }
>   });
> 
>   Scene scene = new Scene(gp);
>   stage.setScene(scene);
>   stage.setWidth(500);
>   stage.setHeight(300);
>   stage.show();
> }
> ```

### Hyperlink超链接

> ```java
> // 点击这个链接默认不会打开浏览器
> Hyperlink link = new Hyperlink("http://www.sina.com.cn");
> // 触发点击事件，打开浏览器
> link.setOnAction(new EventHandler<ActionEvent>() {
>   @Override
>   public void handle(ActionEvent event) {
>     System.out.println(link.getText());
>     HostServices host = getHostServices();
>     host.showDocument(link.getText());
>   }
> });
> ```

### 菜单的使用

> **MenuBar,Menu,MenuItem【菜单栏，菜单，菜单项】**
>
> ```java
> public void start(Stage stage) throws Exception{
>   AnchorPane ap = new AnchorPane();
>   ap.setStyle("-fx-background-color: #ffffff");
>   // 创建菜单栏
>   MenuBar menuBar = new MenuBar();
>   // 创建菜单
>   Menu menu1 = new Menu("菜单一");
>   Menu menu2 = new Menu("菜单二");
>   Menu menu3 = new Menu("菜单三");
>   Menu menu4 = new Menu("菜单四");
> 
>   ImageView iv = new ImageView("file:src/main/resources/icon/info.png");
>   iv.setFitHeight(20);
>   iv.setFitWidth(20);
>   // 创建菜单项
>   MenuItem item1 = new MenuItem("菜单项一",iv);
>   item1.setAccelerator(KeyCombination.valueOf("Ctrl+1"));
>   MenuItem item2 = new MenuItem("菜单项二");
>   MenuItem item3 = new MenuItem("菜单项三");
>   MenuItem item4 = new MenuItem("菜单项四");
>   MenuItem item5 = new MenuItem("菜单项五");
> 
>   item1.setOnAction(new EventHandler<ActionEvent>() {
>     @Override
>     public void handle(ActionEvent event) {
>       System.out.println("菜单项一被点击了～");
>     }
>   });
> 
>   menu1.getItems().addAll(item1, item2, item3);
>   menu2.getItems().addAll(item4, item5);
>   menuBar.getMenus().addAll(menu1, menu2, menu3, menu4);
>   ap.getChildren().add(menuBar);
>   Scene scene = new Scene(ap);
>   stage.setScene(scene);
>   stage.setWidth(500);
>   stage.setHeight(300);
>   stage.show();
>   menuBar.setPrefWidth(ap.getWidth());
>   ap.widthProperty().addListener((observable, oldValue, newValue) -> {
>     menuBar.setPrefWidth(newValue.doubleValue());
>   });
> }
> ```
>
> 