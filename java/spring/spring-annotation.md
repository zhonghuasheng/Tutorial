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
```
PACKAGE 用于包
TYPE 用于类、接口（包括注解类型）或enum声明
CONSTRUCTOR 用于构造方法
METHOD 用于方法
FIELD 用于成员变量（包括枚举常量）
LOCAL_VARIABLE 用于局部变量
PARAMETER 用于类型参数（JDK1.8新增）
```

* `@Retention`

@Retention用于描述注解的生命周期，也就是该注解被保留的时间长短。@Rentention注解中的成员变量value用来设置保留策略，value是java.lang.annotation.RententionPolicy枚举类型，RententionPolicy有3个枚举常量：
```
SOURCE 在源文件中有效，当Java文件编译成class文件的时候，注解被遗弃
CLASS 在class文件中有效， 但jvm加载class文件时被遗弃，这是默认的生命周期
RUNTIME 在运行时有效，注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在
这3个生命周期分别对应于：Java源文件 -> .class文件 -> 内存中的字节码
```

* `@Inherited`

@Inherited是一个标记注解，用来指定该注解可以被继承。使用@Inherited注解的Class类，表示这个注解可以被用于该Class类的子类。

* `@Repeatable`

@Repeatable注解是Java8新增的，解决了在方法，变量或者类上重复使用注解的问题
```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Values {
    Value[] value();
}

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Values.class)
public @interface Value {
    String value() default "value";
}

public class AnnotationClass {

    @Value("hello") // 这里通过反射获取方法的注解class是Values，而不是Value
    @Value("world")
    public void test(String var1, String var2) {
        System.out.println(var1 + " " + var2);
    }
}
```

* `@Native`

使用@Native注解修饰的成员变量，便是可以被本地代码引用

#### Spring常用的注解有哪些

* 声明bean的注解
    * @Component 组件，没有明确的角色
    * @Controller 在展示层使用，控制器的声明
    * @RestController 在展示层使用，返回的是JSON格式的数据
    * @Service 在业务逻辑层使用
    * @Repository 在数据访问层使用
* 注入bean的注解
    * @Autowired 由Spring提供
    * @Resource 由JSR-250提供
    * @Inject 由JSR-330提供
* java配置类相关注解
    * @Configuration 声明当前类为配置类，相当于xml形式的Spring配置
    * @Bean 注解在方法上，声明当前方法的返回值为一个bean, 替代xml的方式
        * @Scope注解设置Spring容器如何新建Bean实例
            * Singleton 默认模式，单例，一个Spring容器中只有一个bean实例
            * Protetype 每次调用新建一个bean
            * Request（web项目中，给每个http request新建一个bean）
            * Session（web项目中，给每个http session新建一个bean）
            * GlobalSession（给每个global http session新建一个bean实例）
        * @PostConstruct由JSR-250提供，在构造函数执行完之后执行，等价于xml中bean配置的的initMethod
        * @PreDestory由JSR-250提供，在Bean销毁之前执行，等价于xml中bean配置的destroyMethod
    * @Configuration 声明当前类为配置类，其中内部组合了@Component注解，表明这个类是一个bean
    * @ComponentScan用于对Component进行扫描，替代xml的方式
    * @WishlyConfiguration为@Configuration与@ComponentScan的组合注解，用的很少
* 切面（AOP）相关注解
    * @Aspect声明一个切面（类上），使用@After，@Before，@Around定义建言（advice），可直接将拦截规则（切点）作为参数。在java配置类中使用@EnableAspectJAutoProxy注解开启Spring对AspectJ代理的支持（类上）
        * @After在方法执行之后执行（方法上）
        * @Before在方法执行前执行（方法上）
        * @Around在方法执行之前与之后执行（方法上）
        * @PointCut声明切点
* 环境切换
    * @Profile通过设定Environment的ActiveProfiles来设定当前context需要使用的配置环境（类或者方法上）
    * @Conditional使用此注解定义激活bean的条件，通过实现Condition接口，并重写matches方法，从而决定该bean是否被实例化（方法上）
* 配置注解
    * @Value注解
        * 普通字符串 `@Value("xxxx")`
        * 操作系统属性 `@Value("#{systemProperties['os.name']}")`
        * 注入表达式 `@Value("#{T(java.lang.Math).random() * 100}")`
        * 注入bean属性 `@Value("#{xxxClass.name})`
        * 注入文件资源
        ```java
        @Value("classpath:com/zhonghausheng/value/test.txt")
        Resource file;
        ```
        * 注入url
        ```java
        @Value("http://")
        Resource url;
        ```
        * 配置文件
        ```java
        @Value("${book.name}")
        String bookName;
        ```
    * @PropertySource加载配置文件（类上），`@PropertySource("classpath:com/zhonghuasheng/value/test.properties")`
### 参考文章
* http://m.biancheng.net/view/7009.html