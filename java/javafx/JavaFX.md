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