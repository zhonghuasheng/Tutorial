<h2 align="center">声明</h2>
<h4 align="center">该文档为本人工作、学习的总结，有些解决方案不一定适合所有情况，取者自思。另外，笔记都是闲暇之余总结，很多没有成体系，所以暂时没有发布，今年会陆陆续续发出来。我写了一个自动commit的脚本，国内国外都会定时同步合并我最新的笔记并发布到Tutorial这个repository。Wiki中也有一些详细的文章，同时Issue中记录的为一些面试题，喜欢的话可以Star下，也可以Fork走作为自己笔记的一部分，总之，生活不易，希望对大家学习有帮助。祝好！！！ https://github.com/zhonghuasheng/Tutorial</h4>

<center>

| 英语 | Java | Spring大家族 | 中间件 | 数据库 | 数据结构 | 算法 | 服务器 | 网络 | 其他 |
|:------:|:------:|:------:|:------:|:------:|:------:|:------:|:------:|:------:|:------:|
| [![English](png/icon/english.png)](#英语) | [![JAVA](png/icon/java-coffee-cup-logo.png)](#Java) | [![Spring Framework](png/icon/spring-logo-48.png)](#Spring大家族)| [![中间件](png/icon/plugins.png)](#中间件) | [![数据库](png/icon/database.png)](#数据库) | [![Data Structure](png/icon/data-structure.png)](#数据结构) | [![Algorithm.png](png/icon/algorithm.png)](#算法) | [![服务器](png/icon/server.png)](#服务器) | [![网络](png/icon/network.png)](#网络) | [![其他](png/icon/other.png)](#其他) |

</center>

<h2 align="center">技术栈</h2>

<center>

![](tutorial-2019-06-30.png)

</center>

## 英语
* [计算机行业常用英语积累](english/english.md)
* [日常英语]()
    * [新英语第一册](english/新概念英语第一册.md)

## Java

### Java基础
* [Java基础](java/basic/java-basic.md)
* [Java基础面试题](https://github.com/zhonghuasheng/Tutorial/wiki/Java%E5%9F%BA%E7%A1%80%E9%9D%A2%E8%AF%95%E9%A2%98)
* [JAVA8+版本移除了永久带Permanent Generation](https://github.com/zhonghuasheng/Tutorial/wiki/JAVA8%E4%BB%A5%E4%B8%8A%E7%89%88%E6%9C%AC%E7%A7%BB%E9%99%A4%E6%B0%B8%E4%B9%85%E5%B8%A6Permanent-Generation)
* [JAVA8 新特性 - default方法](http://note.youdao.com/noteshare?id=89fa780dc27b2e39194a7d6ab740d674)


### Java Core
* [Java IO](java/basic/java-io-nio.md)
* [如何学习Java的NIO](http://note.youdao.com/noteshare?id=5ea48ae4fd97f7a7bb4bd9d036ba4d11)
* [并行与并发的区别](http://note.youdao.com/noteshare?id=07f1542ba53ff20ccf6e036a1a8a52d1)
* [线程安全](http://note.youdao.com/noteshare?id=6f65c98d2421430a5faa8e129ee77cb7)
* [JDBC](https://github.com/zhonghuasheng/JAVA/blob/master/jdbc/src/main/java/com/zhonghuasheng/jdbc/learn01/BasicSteps.java)
### Java虚拟机

### Java Web

* [JSP是不是被淘汰](https://github.com/zhonghuasheng/JAVA/wiki/%E5%AF%B9%E5%A4%A7%E5%9E%8BJAVA-Web%E9%A1%B9%E7%9B%AE%E4%B8%8B%E4%BD%BF%E7%94%A8JSP%E7%9A%84%E6%80%9D%E8%80%83)
* [JSP Velocity FreeMarker对比](https://github.com/zhonghuasheng/JAVA/wiki/JSP---Velocity---FreeMarker%E5%AF%B9%E6%AF%94)
* [JSTL库安装](https://www.runoob.com/jsp/jsp-jstl.html)

## Spring大家族

### Spring
`历史（有关Spring的历史）`
* [Spring历史版本以及现在的帝国](http://note.youdao.com/noteshare?id=80509c0296c54ccb86c1848b95c9c530)
* [为什么要有Spring 这里主要是引入Spring IOC](http://note.youdao.com/noteshare?id=a61d1330cec20afe21d369f0526756a2)
* [为什么要有Spring AOP](http://note.youdao.com/noteshare?id=21f2b64abb67a24ad45baca5456648a5)

`控制反转`
* [手写一个Spring IOC的简单实现](https://github.com/zhonghuasheng/JAVA/tree/master/basic/src/main/java/com/zhonghuasheng/ioc)
* [Spring注解大全](java/spring/spring.md/#Spring注解)

`依赖注入`
* [Spring依赖注入之Setter注入 - Setter Injection](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/springdiwithxmlexample/setterinject)
* [Spring依赖注入之构造函数注入 - Constructor Injection](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/springdiwithxmlexample/constructorinject)
* [Spring自动装配]()
    * [byName](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/autowiringusingxml/autowirebyname)
    * [byType](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/autowiringusingxml/autowirebytype)
    * [constructor](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/autowiringusingxml/constructor)
    * [no](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/autowiringusingxml/no)
* [Spring通过注解实现自动装配-example1](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/annotationinjectexample)
    * [@Resource](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/annotationinjectexample/resource)
    * [@Qualifier](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/annotationinjectexample/qualifier)
    * [@Autowired](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/annotationinjectexample/autowired)
* [Spring通过注解实现自动装配-example2-@Service @Repository的使用](https://github.com/zhonghuasheng/JAVA/tree/master/spring4/src/main/java/com/zhonghuasheng/spring4/autodetection)


### Spring Boot
* [SpringBoot历史](spring-boot/0-springboot-history.md)
* [SpringBoot基础](spring-boot/1-springboot-basic.md)
* [SpringBoot统一结果处理](https://github.com/zhonghuasheng/Spring-Boot/wiki/SpringBoot%E7%BB%9F%E4%B8%80%E5%A4%84%E7%90%86%E8%BF%94%E5%9B%9E%E7%BB%93%E6%9E%9C)
* [SpringBoot统一异常处理](https://github.com/zhonghuasheng/Spring-Boot/wiki/SpringBoot%E7%BB%9F%E4%B8%80%E5%BC%82%E5%B8%B8%E5%A4%84%E7%90%86)
* [SpringBoot统计在线人数](https://github.com/zhonghuasheng/Tutorial/wiki/SpringBoot%E7%BB%9F%E8%AE%A1%E5%9C%A8%E7%BA%BF%E4%BA%BA%E6%95%B0)
* [SpringBoot + Redis + 自定义注解 + 拦截器 实现接口幂等性校验](https://github.com/zhonghuasheng/Spring-Boot/blob/master/springbootidempotence/README.md)

### Spring Cloud

## 中间件

### MyBatis
* [MyBatis](mybatis.md)

### 消息队列
* [ActiveMQ](activemq.md)
* [RabbtiMQ延迟队列-消息延迟推送](https://www.cnblogs.com/haixiang/p/10966985.html)

### 日志
* [为什么使用log](architecture/log-note.md)
* [深入理解JAVA日志基础](https://github.com/zhonghuasheng/Tutorial/wiki/%E6%B7%B1%E5%85%A5%E7%90%86%E8%A7%A3JAVA%E6%97%A5%E5%BF%97%E5%9F%BA%E7%A1%80)
* [如何解决Apache Server中Catalina.out文件过大的问题 - Cronolog](tool/cronolog.md)
* [通过查看access日志来了解系统的访问情况](shell/linux命令在tomcat日志中的应用.md)

## 数据库
* [分库分表 如何做到永不迁移数据和避免热点](https://github.com/zhonghuasheng/Tutorial/wiki/%E5%88%86%E5%BA%93%E5%88%86%E8%A1%A8-%E5%A6%82%E4%BD%95%E5%81%9A%E5%88%B0%E6%B0%B8%E4%B8%8D%E8%BF%81%E7%A7%BB%E6%95%B0%E6%8D%AE%E5%92%8C%E9%81%BF%E5%85%8D%E7%83%AD%E7%82%B9)

### MySQL
* [MySQL](database/mysql.md)

### Postgresql

* [Postgresql](database/postgresql.md)
* [Postgresql在Linux中的安装以及常用命令](database/postgresql/postgresql_note.md)
* [解决Postgresql RDS CPU使用率过高的问题](database/postgresql/PostgreSQL_CPU_Usage_High.md)

### Mongodb
* [Mongodb](database/mongodb.md)

### Redis
* [Redis](database/redis.md)

## 数据结构

### 设计模式

#### 创建型
* [单例模式 Singleton Pattern](https://github.com/zhonghuasheng/DesignPattern/wiki/%E5%88%9B%E5%BB%BA%E5%9E%8B---%E5%8D%95%E4%BE%8B%E6%A8%A1%E5%BC%8F-Singleton-Pattern)

#### 结构型
* [代理模式 Proixy Pattern](https://github.com/zhonghuasheng/DesignPattern/wiki/%E7%BB%93%E6%9E%84%E5%9E%8B-%E4%BB%A3%E7%90%86%E6%A8%A1%E5%BC%8F-Proxy-Design-Pattern)

#### 行为型
* [解释器模式](https://github.com/zhonghuasheng/DesignPattern/wiki/%E8%A1%8C%E4%B8%BA%E5%9E%8B-%E8%A7%A3%E6%9E%90%E5%99%A8%E6%A8%A1%E5%BC%8F-Interpreter-Pattern)

## 算法
* [Letcode](letcode/note.md)
* [拜占庭问题](https://github.com/zhonghuasheng/Tutorial/issues/48)

## 服务器
* [Tomcat服务器架构](plugins/一张图了解Tomcat架构.md)
* [Tomcat如何处理一个请求](plugins/一张图了解Tomcat架构.md)
* [Tomcat安全配置建议](https://github.com/zhonghuasheng/Tutorial/wiki/Tomcat%E5%AE%89%E5%85%A8%E9%85%8D%E7%BD%AE)
* [Tomcat Access日志分析](shell/linux命令在tomcat日志中的应用.md)

### Linux
* [Linux常用命令](system/linux.md)


## 网络
* [域名](network/notes/second-level-domain.md)
* [从HTTP到HTTPS到HSTS](network/notes/HTTP_HTTPS_SSL.md)
* [DOS & DDOS的攻与防](https://github.com/zhonghuasheng/Tutorial/wiki/DOS-&-DDOS%E7%9A%84%E6%94%BB%E4%B8%8E%E9%98%B2)
* [为什么是3次握手4次挥手](https://github.com/zhonghuasheng/Tutorial/issues/21)
* [从浏览器输入URL到页面渲染都发生了什么 - 结合Tomcat架构解析](http://note.youdao.com/noteshare?id=cca91d065dc509bae387a16925efa497)
* [彻底了解Cookies](network/彻底了解cookies.md)

### 大数据
* [Note](data/bigdata.md)

### 架构
* [系统设计注意事项](architecture/系统设计注意事项.md)
* [集群环境下日志合并方案](architecture/集群环境下日志合并方案.md)
* [数据中台架构随想](architecture/数据中台架构随想.md)
* [分布式与微服务有关系吗](https://github.com/zhonghuasheng/Tutorial/wiki/%E5%BE%AE%E6%9C%8D%E5%8A%A1%E4%B8%8E%E5%88%86%E5%B8%83%E5%BC%8F%E6%9C%89%E5%85%B3%E7%B3%BB%E5%90%97)
* [20万用户同时访问一个热点Key，如何优化缓存架构？](http://note.youdao.com/noteshare?id=76894225f153d8f0c96c3318aeb90b6b)

### 工具
* [API测试工具](tool/api-testing-tool.md)
    * [SoapUI](tool/api-testing-tool.md###SoapUI)
    * [Postman](tool/api-testing-tool.md###Postman)
* [流量统计，网站分析](tool/common-tools.md)
* [日志管理工具](tool/cronolog.md)
* [Git](tool/git.md)
* [Intellij](tool/intellij.md)
    * [常用设置](tool/intellij.md##常用设置)
    * [常用快捷键](tool/intellij.md##快捷键)
    * [FAQ](tool/intellij.md##FAQ)
* [Maven](tool/maven.md)
* [VSCode](tool/vscode-settings.md)
* [ELK](elasticsearch.md)
* [CloudFlare免费的CDS服务]()
* [常见部署方式](tool/deployment.md)
    * [蓝绿部署 Blue/Green](tool/deployment.md#蓝绿部署)
    * [金丝雀发布/灰度部署](tool/deployment.md#金丝雀发布)

### APP
* [混合开发](mobile/app.md)

### Shell脚本
* [Shell基础编程](shell/shell.md)

### 下一代产品
* [ServiceMesh](ServiceMesh.md)

### VUE
* [Vue笔记](Vue.md)

### 其他
#### `RULE`
* [雅虎前端34条军规](http://note.youdao.com/noteshare?id=b59d0da4f7bb2b7ba5f73129d85b1ba1)
* [Google Java Coding Style](https://google.github.io/styleguide/javaguide.html)
* [阿里巴巴代码规范](https://github.com/alibaba/p3c/blob/master/%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4Java%E5%BC%80%E5%8F%91%E6%89%8B%E5%86%8C%EF%BC%88%E8%AF%A6%E5%B0%BD%E7%89%88%EF%BC%89.pdf)

