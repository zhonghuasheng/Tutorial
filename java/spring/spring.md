`目录`
## Spring
* [为什么要有Spring](http://note.youdao.com/noteshare?id=a61d1330cec20afe21d369f0526756a2&sub=wcp1556526473148138)
* [为什么要有Spring AOP](http://note.youdao.com/noteshare?id=21f2b64abb67a24ad45baca5456648a5&sub=wcp1556587241813493)
* [关于Spring IOC和AOP的理解](http://note.youdao.com/noteshare?id=d0aa79a9af96f02aad6e25c1d3f192b3&sub=wcp1582173498187944)
* [Spring历史版本变迁和如今的生态帝国](http://note.youdao.com/noteshare?id=80509c0296c54ccb86c1848b95c9c530&sub=wcp15565934311825)
* [手写一个Spring IOC的简单实现](https://github.com/zhonghuasheng/JAVA/tree/master/basic/src/main/java/com/zhonghuasheng/ioc)
* [什么是Spring]()
* [Spring 依赖注入](http://note.youdao.com/noteshare?id=91ac0b573c1898e8fa3b47ebfdfffbf1&sub=wcp1582257498880783)
* [Spring Java配置](http://note.youdao.com/noteshare?id=4808a867c7b646f7f4d41a1bbe2f79fa&sub=wcp1582272695540930)
* [Spring Bean Scope](http://note.youdao.com/noteshare?id=f0c541d742d9548342dbca9e04607758&sub=wcp1582292894805554)
* [Spring AOP与AspectJ的对比](http://note.youdao.com/noteshare?id=b75baf23ad8073d69527838449b259c1&sub=wcp1582290319293279)


### 依赖注入
Spring IoC容器(ApplicationContext)负责创建Bean，并通过容器将功能类Bean注入到你需要的Bean中。Spring提供使用xml，注解，Java配置，groovy配置实现Bean的创建和注入。
无论是xml配置，注解配置还是Java配置，都被称为配置元数据，所谓元数据即描述数据的数据。元数据本身不具备任何可执行的能力，只能通过外界代码来对这些元数据行解析后进行一些有意义的操作。Spring容器解析这些配置元数据进行Bean初始化，配置和管理依赖。
> 声明Bean的注解:
@Component 组件，没有明确的角色。
@Service 在业务逻辑层(service层)使用。
@Repository 在数据访问层(dao层)使用。
@Controller在展现层(MVC——>SpringMVC)使用。

> 注入Bean的注解，一般情况下通用
@Autowired:Spring提供的注解。
@Inject:JSR-330提供的注解。
@Resource:JSR-250提供的注解。

@Autowired，@Inject，@Resource可注解在set方法或者属性上

* [Spring依赖注入之Setter注入 - Setter Injection](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/springdiwithxmlexample/setterinject)
* [Spring依赖注入之构造函数注入 - Constructor Injection](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/springdiwithxmlexample/constructorinject)
* [Spring自动装配]() ***[byName](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/autowiringusingxml/autowirebyname)*** ***[byType](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/autowiringusingxml/autowirebytype)***
     ***[constructor](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/autowiringusingxml/constructor)***
     ***[no](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/autowiringusingxml/no)***
* [Spring通过注解实现自动装配-example1](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/annotationinjectexample)
     ***[@Resource](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/annotationinjectexample/resource)***
     ***[@Qualifier](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/annotationinjectexample/qualifier)***
     ***[@Autowired](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/annotationinjectexample/autowired)***
* [Spring通过注解实现自动装配-example2-@Service @Repository的使用](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/autodetection)



`正文`

# Spring
http://blog.csdn.net/yangyangiud/article/details/52368712
## Factory Pattern
### Simple Factory
### Factory Method
### Abstract Factory

## IOC/DI
### IOC简介
```
Spring通过DI（依赖注入）实现IOC（控制反转），常用的注入方式主要有三种：构造函数注入、setter注入、基于注解的注入。
```

### 注入方式(常用的有三种)
`1. Setter Injection`
```java
public class Communication {

    private Messaging messaging;

     /*
     * DI via Setter
     */
    public void setMessaging(Messaging messaging){
        this.messaging = messaging;
    }

    public void communicate(){
        messaging.sendMessage();
    }
}
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
                            http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd">

    <bean id="activeMqMessaging" class="com.websystique.spring.domain.impl.ActiveMQMessaging" />

    <bean id="communication" class="com.websystique.spring.Communication">
        <property name="messaging">
            <ref bean="activeMqMessaging" />
        </property>
    </bean>

</beans>
```

`2. Constructor Injection`
```java
package com.websystique.spring;

import com.websystique.spring.domain.Encryption;

public class Communication {

    private Encryption encryption;
    /*
     * DI via Constructor Injection
     */
    public Communication(Encryption encryption){
        this.encryption = encryption;
    }
    public void communicate(){
        encryption.encryptData();
    }
}
```
```xml
<bean id="rsaEncryption" class="com.websystique.spring.domain.impl.RSAEncryption" />

<bean id="communication" class="com.websystique.spring.Communication">
    <constructor-arg>
        <ref bean="rsaEncryption" />
    </constructor-arg>
</bean>
```
`3. Annotation Injection`
Go to [here](#Spring注解)

### IOC实现基本原理

## AOP
### AOP引入
### JDK代理
### CGLib代理
### 模拟Spring-IOC
### 模拟Spring-AOP

## Spring 基础
### Hello Spring
### Spring Singleton
### Spring Factory Method
### Spring Inject Method

## Spring Liferay Cycle
## Spring Autowire

Bean wiring corresponds to providing the dependencies a bean might need to complete it’s job. In Spring, beans can be wired together in two ways : `Manually` and `Autowiring`.

`Manual wiring` : using ref attribute in <property> or <constructor> tag.

```xml
<!-- default example (autowire="no") -->
<bean id="driver" class="com.websystique.spring.domain.Driver">
    <property name="license" ref="license"/>
</bean>

<bean id="license" class="com.websystique.spring.domain.License" >
    <property name="number" value="123456ABCD"/>
</bean>
```

`Autowiring` : using autowire attribute in <bean> tag
```xml
<bean id="application" class="com.websystique.spring.domain.Application" autowire="byName"/>
```
In this approach, beans can be automatically wired using Spring autowire feature. There are 4 supported options for autowiring.

* `autowire="byName"` : Autowiring using property name. If a bean found with same name as the property of other bean, this bean will be wired into other beans property
    ```xml
    <bean id="application" class="com.websystique.spring.domain.Application" autowire="byName"/>
    ```
* `autowire="byType"` : If a bean found with same type as the type of property of other bean, this bean will be wired into other beans property
    ```xml
    <bean id="application" class="com.websystique.spring.domain.Application" autowire="byType"/>
    ```
* `autowire="constructor"` : If a bean found with same type as the constructor argument of other bean, this bean will be wired into other bean constructor
    ```xml
    <bean id="application" class="com.websystique.spring.domain.Application" autowire="constructor"/>
    ```
* `autowire="no"` : No Autowiring. Same as explicitly specifying bean using ‘ref’ attribute
	```xml
    <bean id="application" class="com.websystique.spring.domain.Application" autowire="no"/>
    ```
## Abstract Parent Import
## Spring Annotation
## Spring Collections

### Spring AOP
#### Spring AOP引入


看execution表示式的格式：
括号中各个pattern分别表示`修饰符匹配（modifier-pattern?）`、
`返回值匹配（ret-type-pattern）`、`类路径匹配（declaring-type-pattern?）`、`方法名匹配（name-pattern）`、`参数匹配（(param-pattern)）`、`异常类型匹配（throws-pattern?）`，`其中后面跟着“?”`的是可选项。
在各个pattern中可以使用`“*”`来表示匹配所有。在(param-pattern)中，可以指定具体的参数类型，多个参数间用“,”隔开，各个也可以用“*”来表示匹配任意类型的参数，
如(String)表示匹配一个String参数的方法；(*,String)表示匹配有两个参数的方法，第一个参数可以是任意类型，而第二个参数是String类型；可以用(..)表示零个或多个任意参数。
现在来看看几个例子：
* execution(* *(..))
表示匹配所有方法
* execution(public * com. savage.service.UserService.*(..))
表示匹配com.savage.server.UserService中所有的公有方法
* execution(* com.savage.server..*.*(..))
表示匹配com.savage.server包及其子包下的所有方法

除了execution表示式外，还有within、this、target、args等Pointcut表示式。一个Pointcut定义由Pointcut表示式和Pointcut签名组成，例如：
java 代码
execution(modifier-pattern?
ret-type-pattern
declaring-type-pattern?
name-pattern(param-pattern)
throws-pattern?)

//Pointcut表示式
@Pointcut("execution(* com.savage.aop.MessageSender.*(..))")
//Point签名
private void log(){} 然后要使用所定义的Pointcut时，可以指定Pointcut签名，如上面的定义等同与：

在使用spring框架配置AOP的时候，不管是通过XML配置文件还是注解的方式都需要定义pointcut"切入点"
例如定义切入点表达式  execution (* com.sample.service.impl..*.*(..))
execution()是最常用的切点函数，其语法如下所示：
 整个表达式可以分为五个部分：
 1、execution(): 表达式主体。
 2、第一个*号：表示返回类型，*号表示所有的类型。
 3、包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.sample.service.impl包、子孙包下所有类的方法。
 4、第二个*号：表示类名，*号表示所有的类。
 5、*(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。

 AspectJ的Execution表达式
execution()
execution()是最常用的切点函数，其语法如下所示：

execution(<修饰符模式>? <返回类型模式> <方法名模式>(<参数模式>) <异常模式>?)  除了返回类型模式、方法名模式和参数模式外，其它项都是可选的。与其直接讲解该方法的使用规则，还不如通过一个个具体的例子进行理解。下面，我们给出各种使用execution()函数实例。

1)通过方法签名定义切点
 execution(public * *(..))l
匹配所有目标类的public方法，但不匹配SmartSeller和protected void showGoods()方法。第一个*代表返回类型，第二个*代表方法名，而..代表任意入参的方法；

 execution(* *To(..))l
匹配目标类所有以To为后缀的方法。它匹配NaiveWaiter和NaughtyWaiter的greetTo()和serveTo()方法。第一个*代表返回类型，而*To代表任意以To为后缀的方法；

2)通过类定义切点
 execution(* com.baobaotao.Waiter.*(..))l
匹配Waiter接口的所有方法，它匹配NaiveWaiter和NaughtyWaiter类的greetTo()和serveTo()方法。第一个*代表返回任意类型，com.baobaotao.Waiter.*代表Waiter接口中的所有方法；

 execution(* com.baobaotao.Waiter+.*(..))l
匹 配Waiter接口及其所有实现类的方法，它不但匹配NaiveWaiter和NaughtyWaiter类的greetTo()和serveTo()这 两个Waiter接口定义的方法，同时还匹配NaiveWaiter#smile()和NaughtyWaiter#joke()这两个不在Waiter 接口中定义的方法。

3)通过类包定义切点
在类名模式串中，“.*”表示包下的所有类，而“..*”表示包、子孙包下的所有类。
 execution(* com.baobaotao.*(..))l
匹配com.baobaotao包下所有类的所有方法；

 execution(* com.baobaotao..*(..))l
匹 配com.baobaotao包、子孙包下所有类的所有方法，如com.baobaotao.dao，com.baobaotao.servier以及 com.baobaotao.dao.user包下的所有类的所有方法都匹配。“..”出现在类名中时，后面必须跟“*”，表示包、子孙包下的所有类；

 execution(* com..*.*Dao.find*(..))l
匹配包名前缀为com的任何包下类名后缀为Dao的方法，方法名必须以find为前缀。如com.baobaotao.UserDao#findByUserId()、com.baobaotao.dao.ForumDao#findById()的方法都匹配切点。

4)通过方法入参定义切点
切点表达式中方法入参部分比较复杂，可以使用“*”和“ ..”通配符，其中“*”表示任意类型的参数，而“..”表示任意类型参数且参数个数不限。

 execution(* joke(String,int)))l
匹 配joke(String,int)方法，且joke()方法的第一个入参是String，第二个入参是int。它匹配 NaughtyWaiter#joke(String,int)方法。如果方法中的入参类型是java.lang包下的类，可以直接使用类名，否则必须使用全限定类名，如joke(java.util.List,int)；

 execution(* joke(String,*)))l
匹 配目标类中的joke()方法，该方法第一个入参为String，第二个入参可以是任意类型，如joke(String s1,String s2)和joke(String s1,double d2)都匹配，但joke(String s1,double d2,String s3)则不匹配；

 execution(* joke(String,..)))l
匹配目标类中的joke()方法，该方法第 一个入参为String，后面可以有任意个入参且入参类型不限，如joke(String s1)、joke(String s1,String s2)和joke(String s1,double d2,String s3)都匹配。

 execution(* joke(Object+)))l
匹 配目标类中的joke()方法，方法拥有一个入参，且入参是Object类型或该类的子类。它匹配joke(String s1)和joke(Client c)。如果我们定义的切点是execution(* joke(Object))，则只匹配joke(Object object)而不匹配joke(String cc)或joke(Client c)。
#### Spring AOP引入2
#### Spring AOP基本概念

### Spring Web
#### Spring MVC流程
#### Hello Spring MVC与自定义url
#### 接受请求参数
#### Spring MVC返回html
#### Spring response body
#### Spring json ignore
#### Spring json serialize
#### Spring json deserialize
#### 静态资源处理
#### 注解调用service
#### 异常处理@exceptionhandler
#### 获得beanFactory对象

#### Spring Controller
package org.springframework.web.bind.annotation;

@RequestMapping来映射URL
    注解 @RequestMapping 可以用在类定义处和方法定义处。
    `类定义处`：规定初步的请求映射，相对于web应用的根目录；
    `方法定义处`：进一步细分请求映射，相对于类定义处的URL。如果类定义处没有使用该注解，则方法标记的URL相对于根目录而言；
```java
package com.springmvc.helloworld_1;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/example")
public class HelloWorld {

    @RequestMapping("/helloworld")
    public String hello(){
        System.out.println("hello world");

        return "success";
    }
}
```

上面代码在类定义处指定映射为"/example"，在hello()方法处指定为"/helloworld"。那么hello()方法的URL映射地址为：http://localhost:8080/springMVC/example/helloworld
如果去掉类定义处的@RequestMapping(value="/example")，那么hello()方法的映射地址就变为了：http://localhost:8080/springMVC/helloworld
还有一个注意的，@RequestMapping的默认属性为value，所以@RequestMapping(value="/example")和@RequestMapping("/example")是等价的。

2、@RequestMapping除了可以指定URL映射外，还可以指定“请求方法、请求参数和请求头”的映射请求
    注解的value、method、params及headers分别指定“请求的URL、请求方法、请求参数及请求头”。它们之间是与的关系，联合使用会使得请求的映射更加精细。
　　2.1  method属性可以指定请求的类型，http中规定请求有四种类型：get，post，put，delete。其值在枚举类型RequestMethod中有规定。

　　　　例如：@RequestMapping(value="/helloworld", method=RequestMethod.DELETE) 指定只有DELETE方式的helloworld请求才能够执行该处理方法。
```java
package com.springmvc.RequestMapping_2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/springmvc")
public class RequestMappingTest {

    private static final String SUCCESS = "success";

    /**
     * 注解 @RequestMapping 可以用在类定义处和方法定义处
     * 1、类定义处：规定初步的请求映射，相对于web应用的根目录
     * 2、方法定义处：进一步细分请求映射，相对于类定义处的URL。如果类定义处没有使用该注解，则方法标记的URL相对于根目录而言
     *
     * 所以，testRequestMappingURL方法对应的URL目录为：/springmvc/testRequestMappingURL
     */
    @RequestMapping("/testRequestMappingURL")
    public String testRequestMappingURL(){
        System.out.println("testRequestMappingURL 方法...");

        return SUCCESS;
    }

    /**
     * 1、了解：可以指定params和headers参数。
     *
     * params和headers的值规定了：
     * ①、请求参数必须包含param，和view。而且，view的值必须为true
     * ②、请求头中必须包含有Accept-Language，而且其值必须为zh-CN,zh;q=0.8
     */
    @RequestMapping(value="/testParamsAndHearders",
                    params={"view=true","param"},
                    headers={"Accept-Language=zh-CN,zh;q=0.8"})
    public String testParamsAndHearders(){
        System.out.println("testParamsAndHearders 方法...");

        return SUCCESS;
    }
}
```

#### web.xml的完整配置是这样的：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="3.0"
	xmlns="http://java.sun.com/xml/ns/javaee"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
	http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd">
  <display-name></display-name>
  <!-- 404错误拦截 -->
  <error-page>
	<error-code>404</error-code>
	<location>/error404.jsp</location>
  </error-page>
  <!-- 500错误拦截 -->
  <error-page>
	<error-code>500</error-code>
	<location>/error500.jsp</location>
  </error-page>
  <!-- 加载spring容器 -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>WEB-INF/classes/spring/applicationContext-*.xml</param-value>
	</context-param>
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
  <!-- 配置前端控制器 -->
  <servlet>
	  <servlet-name>spring</servlet-name>
	  <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	  <init-param>
		  <!-- ContextconfigLocation配置springmvc加载的配置文件
		  适配器、处理映射器等
		   -->
		  <param-name>contextConfigLocation</param-name>
		  <param-value>WEB-INF/classes/spring/springmvc.xml</param-value>
  </init-param>
  </servlet>
  <servlet-mapping>
	  <servlet-name>spring</servlet-name>
	  <!-- 1、.action访问以.action结尾的  由DispatcherServlet进行解析
		   2、/,所有访问都由DispatcherServlet进行解析
	   -->
	  <url-pattern>/</url-pattern>
  </servlet-mapping>
  <!-- 解决post乱码问题的过滤器 -->
  <filter>
	  <filter-name>CharacterEncodingFilter</filter-name>
	  <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
	  <init-param>
		  <param-name>encoding</param-name>
		  <param-value>utf-8</param-value>
	  </init-param>
  </filter>
  <filter-mapping>
	  <filter-name>CharacterEncodingFilter</filter-name>
	  <url-pattern>/*</url-pattern>
  </filter-mapping>
  <welcome-file-list>
	<welcome-file>welcome.jsp</welcome-file>
  </welcome-file-list>
</web-app>
```

http://www.tuicool.com/articles/eyINveF

#### 整个applicationContext-dao.xml配置文件应该是这样的：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:context="http://www.springframework.org/schema/context"
xmlns:mvc="http://www.springframework.org/schema/mvc"
xmlns:jdbc="http://www.springframework.org/schema/jdbc"
xmlns:jee="http://www.springframework.org/schema/jee"
xmlns:aop="http://www.springframework.org/schema/aop"
xmlns:tx="http://www.springframework.org/schema/tx"
xsi:schemaLocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd
http://www.springframework.org/schema/context
http://www.springframework.org/schema/context/spring-context.xsd
http://www.springframework.org/schema/mvc
http://www.springframework.org/schema/mvc/spring-mvc.xsd
http://www.springframework.org/schema/tx
http://www.springframework.org/schema/tx/spring-tx.xsd
http://www.springframework.org/schema/aop
http://www.springframework.org/schema/aop/spring-aop.xsd">
<!-- 加载数据库连接的资源文件 -->
<context:property-placeholder location="/WEB-INF/classes/jdbc.properties"/>
<!-- 配置数据源   dbcp数据库连接池 -->
<bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
	<property name="driverClassName" value="${jdbc.driver}"/>
	<property name="url" value="${jdbc.url}"/>
	<property name="username" value="${jdbc.username}"/>
	<property name="password" value="${jdbc.password}"/>
</bean>
<!-- 配置sqlSessionFactory -->
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
	<!-- 数据库连接池 -->
	<property name="dataSource" ref="dataSource"/>
	<!-- 加载Mybatis全局配置文件 -->
	<property name="configLocation" value="/WEB-INF/classes/mybatis/SqlMapConfig.xml"/>
</bean>
<!-- 配置mapper扫描器 -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
	<!-- 扫描包路径，如果需要扫描多个包中间用半角逗号隔开 -->
	<property name="basePackage" value="com.wxisme.ssm.mapper"></property>
	<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
</bean>
</beans>
```
#### 整个事务控制的配置是这样的：
applicationContext-transaction.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
xmlns:context="http://www.springframework.org/schema/context"
xmlns:mvc="http://www.springframework.org/schema/mvc"
xmlns:jdbc="http://www.springframework.org/schema/jdbc"
xmlns:jee="http://www.springframework.org/schema/jee"
xmlns:aop="http://www.springframework.org/schema/aop"
xmlns:tx="http://www.springframework.org/schema/tx"
xsi:schemaLocation="http://www.springframework.org/schema/beans
http://www.springframework.org/schema/beans/spring-beans.xsd
http://www.springframework.org/schema/context
http://www.springframework.org/schema/context/spring-context.xsd
http://www.springframework.org/schema/mvc
http://www.springframework.org/schema/mvc/spring-mvc.xsd
http://www.springframework.org/schema/tx
http://www.springframework.org/schema/tx/spring-tx.xsd
http://www.springframework.org/schema/aop
http://www.springframework.org/schema/aop/spring-aop.xsd">
<!-- 事务控制  对MyBatis操作数据库  spring使用JDBC事务控制类 -->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	<!-- 配置数据源 -->
	<property name="dataSource" ref="dataSource"/>
</bean>
	<!-- 通知 -->
	<tx:advice id="txAdvice" transaction-manager="transactionManager">
		<tx:attributes>
			<!-- 传播行为 -->
			<tx:method name="save*" propagation="REQUIRED"/>
			<tx:method name="insert*" propagation="REQUIRED"/>
			<tx:method name="update*" propagation="REQUIRED"/>
			<tx:method name="delete*" propagation="REQUIRED"/>
			<tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
			<tx:method name="select*" propagation="SUPPORTS" read-only="true"/>
		</tx:attributes>
	</tx:advice>
	<!-- 配置aop  -->
	<aop:config>
		<aop:advisor advice-ref="txAdvice" pointcut="execution(* com.wxisme.ssm.service.impl.*.*(..))"/>
	</aop:config>
</beans>
```
#### SqlMapConfig.xml的配置
SqlMapConfig.xml的配置
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
<!-- 将数据库连接数据抽取到属性文件中方便测试 -->
<!-- <properties resource="/WEB-INF/classes/jdbc.properties"></properties> -->
<!-- 别名的定义 -->
<typeAliases>
	<!-- 批量定义别名 ，指定包名，自动扫描包中的类，别名即为类名，首字母大小写无所谓-->
	<package name="com.wxisme.ssm.po"/>
</typeAliases>
<!-- 数据库连接用数据库连接池 -->
<mappers>
	<!-- 通过扫描包的方式来进行批量加载映射文件 -->
	<package name="com.wxisme.ssm.mapper"/>
</mappers>
</configuration>
```

## Mybatis
http://www.mybatis.org/mybatis-3/zh/configuration.html
### Environment
### Configuration XML
### Mapper XML
### Create Operation
### Read Operation
### Update Operation
### Delete Operation
### Annotations
### Stored Procedures
### Dynamic Query



### 配置文件的基本结构
* configuration —— 根元素
    * properties —— 定义配置外在化
    * settings —— 一些全局性的配置
    * typeAliases —— 为一些类定义别名
    * typeHandlers —— 定义类型处理，也就是定义java类型与数据库中的数据类型之间的转换关系
    * objectFactory —— 对象工厂
    * plugins —— Mybatis的插件，插件可以修改Mybatis内部的运行规则
    * environments —— 配置Mybatis的环境
        * environment
             * transactionManager —— 事务管理器
             * dataSource —— 数据源
    * databaseIdProvider
    * mappers —— 指定映射文件或映射类


首先回顾一下hibernate中操作数据库的流程：

1）读取配置

2）获取SessionFactory（重量级，只有一个）

3）获取session

4）开启事务

5）进行CRUD操作

6）提交事务

7）关闭session



在我们的mybatis中，也有类似的步骤：

1）获取SqlSessionFactory

2）获取SqlSession

3）进行CURD操作

4）提交事务

5）关闭SqlSession

通过核心配置XML方式

Reader reader = Resources.getResourceAsReader("ibatis-config.xml");
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
通过Configuration方式（其实就是将XML配置转化为对应的对象）

DataSource dataSource = ...
TransactionFactory transactionFactory = new JdbcTransactionFactory();
Environment environment = new Environment("development", transactionFactory, dataSource);
Configuration configuration = new Configuration(environment);
configuration.addMapper(BlogMapper.class);
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
获取SqlSession

SqlSession sqlSession = factory.openSession();

### @Component和@Bean的区别
@Component and @Bean do two quite different things, and shouldn't be confused.

@Component (and @Service and @Repository) are used to auto-detect and auto-configure beans using classpath scanning. There's an implicit one-to-one mapping between the annotated class and the bean (i.e. one bean per class). Control of wiring is quite limited with this approach, since it's purely declarative.

@Bean is used to explicitly declare a single bean, rather than letting Spring do it automatically as above. It decouples the declaration of the bean from the class definition, and lets you create and configure beans exactly how you choose.

# Spring注解
## 组件类注解
* @ComponentScan
    * Spring将会自动监测指定包下那些应用了@Resource和Autowired的类
    * @ComponentScan(basePackages = "com.zhonghuasheng.spring4") @ComponentScan basePackages attribute takes package name[s] as input which will be search for to find any class annotated with Spring specific annotations.
* @Repository
    * 主要用于注册dao层的bean
* @Service
    * 主要用于注册服务层的bean
* @Controller
    * 主要用于注册控制层的bean
* @Configuration
    * Used to mark a bean as Configuration Component
* @Component
    * 可用于注册所有的bean。General purpose annotation, can be used as a replacement for above annotations.

## 装配类注解
@Autowired
@Resource
@PostConstruct
@PreDestory

## MVC模块注解
@Controller
@RequestMapping
@RequestParam
@PathVariable
@RequestBody
@ResponseBody
@RestController
@ModelAttribute

## 事务模块注解
@Transactional

## AOP模块注解
@Aspect
@Pointcut
@Around
@Before
@After
@AfterReturning
@AfterThrowing

# Reference
[Spring注入的常用的三种方式](https://blog.csdn.net/a909301740/article/details/78379720)