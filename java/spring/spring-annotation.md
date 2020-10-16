### 相关问题
* 为什么要引入注解
* 元注解有哪些
* Spring常用的注解有哪些
* SpringBoot常用注解有哪些
* SpringCloud常用注解有哪些

#### 为什么要引入注解
注解是JDK1.5引入的功能。注解可以看作是对类和方法的扩展标识，这些标识可以在编译，类加载，运行时被读取，并执行相应的处理。在注解之前，我们只能通过xml配置的方式来做。由于XML的配置诸多不便，并且配置和代码分离，不便于代码阅读，慢慢的注解就替代了xml配置的方式。

#### 元注解有哪些

元注解是负责对其它注解进行说明的注解，自定义注解时可以使用元注解。Java 5 定义了 4 个注解，分别是 @Documented、@Target、@Retention 和 @Inherited。Java 8 又增加了 @Repeatable 和 @Native 两个注解
* `@Documented`

@Documented 是一个标记注解，没有成员变量。用 @Documented 注解修饰的注解类会被 JavaDoc 工具提取成文档。默认情况下，JavaDoc 是不包括注解的，但如果声明注解时指定了 @Documented，就会被 JavaDoc 之类的工具处理，所以注解类型信息就会被包括在生成的帮助文档中。使用`javadoc -d doc MyAnnotation.java XXXX.java`就生成了javadoc了
```java
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface MyDocumented {
    public String value() default "这是@Documented注解";
}
// Target为什么要加个ElementType.TYPE，是因为生成javadoc的时候你得知道是哪个类啊
@MyDocumented
public class DocumentedTest {
    /**
        * 测试document
        */
    @MyDocumented
    public String Test() {
        return "C语言中文网Java教程";
    }
}

javac MyDocumented.java DocumentedTest.java
javadoc -d doc MyDocumented.java DocumentedTest.java
```
![](image/documented-annotation.png)

* `@Target`

@Target 注解用来指定一个注解的使用范围，即被 @Target 修饰的注解可以用在什么地方。@Target 注解有一个成员变量（value）用来设置适用目标，value 是 java.lang.annotation.ElementType 枚举类型的数组，下表为 ElementType 常用的枚举常量:
PACKAGE 用于包
TYPE 用于类、接口（包括注解类型）或enum声明
CONSTRUCTOR 用于构造方法
METHOD 用于方法
FIELD 用于成员变量（包括枚举常量）
LOCAL_VARIABLE 用于局部变量
PARAMETER 用于类型参数（JDK1.8新增）

* `@Retention`



### 参考文章
* http://m.biancheng.net/view/7009.html